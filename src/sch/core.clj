(ns sch.core
  (:require [opennlp.nlp :refer :all]
            [opennlp.treebank :refer :all]
            [me.raynes.fs :as fs]))


(defn mdl [f] (str fs/*cwd* "/resources/nl/models/" f))

(defn nl
  [k rest]
  ((condp = k
    :get-sentences (make-sentence-detector (mdl "sent.bin"))
    :tokenize (make-tokenizer (mdl "token.bin"))
    :pos-maxent (make-pos-tagger (mdl "pos-maxent.bin"))
    :pos-perceptron (make-pos-tagger (mdl "pos-perceptron.bin"))
    :name-find (make-name-finder (mdl "ner-person.bin"))
    :loc-find (make-name-finder (mdl "ner-location.bin"))
    :misc-find (make-name-finder (mdl "ner-misc.bin"))
    :org-find (make-name-finder (mdl "ner-organization.bin"))
    :detokenize (make-detokenizer (mdl "detokenizer.xml"))
    ) rest))

