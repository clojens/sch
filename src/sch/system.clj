(ns sch.system
  (:require [sch.normalize :refer [utf8->base64]]
            [me.raynes.fs :as fs]
            [backtick :refer [template]]))

(defn add-path
  "Commute changes to the system paths ref."
  [k tpl]
  (dosync (commute ((system) :paths) conj {k tpl})))

(defn start
  "Performs side effects to initialize the system, acquire resources,
  and start it running. Returns an updated instance of the system."
  [system]
  ;; path adding is side-effectful as it takes *cwd* it depends on this project folder,
  ;; change as you see fit but this way we have 1 central storage with good path defaults
  (do
    (add-path :bnf (template (fn [s] (str ~(str fs/*cwd* "/resources/nl/bnf/") (utf8->base64 s)))))
    (add-path :mdl (template (fn [s] (str ~(str fs/*cwd* "/resources/nl/models/") s))))
    )
  :complete)

(defn stop
  "Performs side effects to shut down the system and release its
  resources. Returns an updated instance of the system."
  [system]
  )

(defn system
  []
  {:paths (ref #{})

   })
