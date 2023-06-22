(ns hooks.reacl
  (:require [clj-kondo.hooks-api :as api]))

(defn declare-bindings
  [bindings]
  (mapcat (fn [t]
            (if (:children t)
              (declare-bindings t)
              [(api/list-node [(api/token-node 'declare) t])]))
          (:children bindings)))

(defn defclass
  [{:keys [:node]}]
  (let [[class-name this ?app-state ?bindings & ?clauses] (rest (:children node))
        app-state (if (empty? (:children ?app-state)) ?app-state nil)
        bindings (if (empty? (:children ?app-state)) ?bindings ?app-state)
        clauses (if (empty? (:children ?app-state)) ?clauses (concat [?bindings] ?clauses))
        local-state (get (into {} (mapv (fn [[k v]] [(pr-str k) v]) (partition 2 clauses))) (pr-str (api/token-node 'local-state)))
        new-node
        (api/list-node
         (list* (api/token-node 'do)
                (api/list-node [(api/token-node 'declare) class-name])
                (api/list-node [(api/token-node 'declare) this])
                (concat (when app-state
                          [(api/list-node [(api/token-node 'declare) app-state])])
                        (declare-bindings bindings)
                        (if (empty? local-state)
                          (map second (remove #(= 'local-state (first %)) (partition 2 clauses)))
                          [(api/list-node (list* (api/token-node 'let) local-state
                                                 (map second (remove #(= 'local-state (first %)) (partition 2 clauses)))))]))))]
  {:node new-node}))
