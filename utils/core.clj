(ns utils.core
  (:require [clj-kondo.hooks-api :as api]))

(defn declare-bindings
  [bindings]
  (mapcat (fn [t]
            (if (:children t)
              (declare-bindings t)
              [(api/list-node [(api/token-node 'declare) t])]))
          (:children bindings)))

(defn no-children? [node]
  (empty? (:children node)))

(defn sequential-node? [node]
  (not (no-children? node)))

(def isnt-binding-vector? no-children?)
(def is-binding-vector? sequential-node?)
