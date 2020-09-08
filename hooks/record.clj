(ns hooks.record
  (:require [clj-kondo.hooks-api :as api]))

(defn define-record-type
  [{:keys [:node]}]
  (let [[record-name constructor-spec predicate field-specs] (rest (:children node))
        [constructor & _fields] (if-let [ch (:children constructor-spec)]
                                  ch
                                  [constructor-spec])
        accessors (map second (partition 2 (:children field-specs)))
        new-node
        (api/list-node
         (list* (api/token-node 'do)
                (api/list-node [(api/token-node 'declare) record-name])
                (api/list-node [(api/token-node 'declare) predicate])
                (api/list-node [(api/token-node 'declare) constructor])
                (map (fn [t] (api/list-node [(api/token-node 'declare) t])) accessors)))]
  {:node new-node}))
