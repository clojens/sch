(ns sch.formal
  (:require [sch.normalize :refer [utf8->base64]]
            [sch.system :as system]
            [clojure.string :as string]
            [me.raynes.fs :as fs]))



(def *store64* (((system/system) :paths) :bnf))

(defn save-parser-string
  "Anonymous function closing over hashed (base64) name and content and updates
  if not found, checks each base64 content line."
  [uniq content]
  ((fn [n64 c64] (if (file? n64)
                   (when (nil? (filter #(= c64 %) (string/split (slurp n64) #"(\\n|\\r|\\r\\n)")))
                     (spit n64 (str c64 "\n") :append true))
                   (spit n64 c64)))
   (*store64* uniq)
   (utf8->base64 content)))


(save-parser-string
 "examplex"
"(* NOTE:
One can easily change the entire output using comments like these
just encapsulate them and the next rule in line is taken as sigma*)

(*S          := zin S | Epsilon*)

zin          := woorden punt
woorden      := ((woord | voorwaarde) <ws>?)+
punt         := '.'
voorwaarde   := #'([Aa]ls|[Ii]ndien|[Ww]are|[Mm]ocht|[Hh]ad)'
leestekens   := punt
woord        := #'[A-Za-z-]+'
<ws>         := ' '
")
