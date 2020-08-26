(ns hooks.monad
  (:require [clj-kondo.hooks-api :as api]))

(defn monadic
  [{:keys [:node]}]
  (letfn [(rewrite-monadic-form
            [forms]
            (let [form (first forms)
                  forms (rest forms)]
              (if (empty? forms)
                form
                (cond
                  (= :vector (:tag form))
                  (api/list-node
                   (list (api/token-node 'let)
                         (api/vector-node (:children form))
                         (rewrite-monadic-form forms)))
                  (and (= :list (:tag form))
                       (= (api/token-node 'let) (first (:children form))))
                  (api/list-node
                   (list (api/token-node 'let)
                         (api/vector-node (:children (second (:children form))))
                         (rewrite-monadic-form forms)))
                  :else
                  (rewrite-monadic-form forms)))))]
    (let [[& forms] (rest (:children node))
          new-node (rewrite-monadic-form forms)]
      {:node new-node})))
