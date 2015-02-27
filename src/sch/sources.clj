(ns sch.sources
  (:require [sch.normalize :refer [url-escape]]
            [plumbing.core :refer [safe-get-in]]))

(defn get-source-uri
  "Returns a properly sanitized URI where a language resource can be downloaded."
  [txt &{lang :lang semrel :semrel :or {lang :nl semrel :synonyms}}]
  ((safe-get-in
    {:uri {:nl {:synonyms #(url-escape (str "http://www.mijnwoordenboek.nl/synoniemen/" %))
                :verb-tense #(url-escape (str "http://www.vertalen.nu/vervoeging?vervoeg=" % "&taal=nl"))}}}
    [:uri lang semrel]) txt))

;; (get-source-uri "foo bar" :semrel :verb-tense)

