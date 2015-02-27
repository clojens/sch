(ns sch.phonetics
  (:require [alembic.still :refer [distill*]]
            [clojure.string :as string]))


(distill* '[[me.raynes/conch "LATEST"]
            [prismatic/plumbing "0.3.5"]
            ] {:verbose false})
(require '[me.raynes.conch :refer [programs]])

(programs espeak)

(defn l337 [lang words]
  (println (espeak "-v" lang "--ipa" "-m" words)))

(l337 "dutch" "Kat krabt de krullen van de trap")

(l337 "english" "Which switch is the right switch to Ipswich")

