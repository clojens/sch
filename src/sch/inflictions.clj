(ns sch.inflictions)

(def patronen
  {:achtervoegsel (fn [w] (re-pattern (str "\\b(.*)" w "\\b")))

   })

(defn parse-rules
  [s]
  (map #(vector ((patronen :achtervoegsel) (first %))
                :->
                (last %))
       s))



(defn woord-matcher
  [woord van naar]
  (let [m (re-find (re-matcher (re-pattern (str "\\b(.*)" van "\\b")) woord))
        ]))



(comment
  +en voor de meeste substantieven
  De bank -> de banken
  Het boek -> de boeken)

;; Predicaat

(defn laatste-letter?
  "Neem een enkel letter als teken, symbool of tekst and controleert deze ten opzichte
  van de laatste letter van het daaropvolgende woord. Geeft waar terug indien gelijk."
  [w l]
  (= (str l) (str (last (sequence w)))))

(defn inflectie
  [w]
  (if (laatste-letter? w "s")
     (transformeer w "s" "zen") nil))

  (defn parse-rulex
    [sq]
    (map #(vector (condp = (-> % first second)
                    :- (re-pattern (str "\\b(.*)" (-> % ffirst) "\\b"))
                    :+ (re-pattern (str "\\b\\(.*" (-> % ffirst) "\\)\\b")))
                  (-> % second second)
                  (-> % second first))
                  (seq sq)))


(def pxc
(parse-rulex [{"s" :- "zen" :+}
              {"f" :- "ven" :+}
              {"heid" :- "heden" :+}
              {"[a|i|o|u|y]" :+ "s" :+}]))


(map #(re-find (re-matcher (first %) "cadeau")) pxc)





(comment
  -s wordt –zen
  de buis -> de buizen
  het huis -> de huizen

  -f wordt –ven
  de korf -> de korven

  -heid wordt –heden
  de grootheid -> de grootheden
  de waarheid -> de waarheden

  meervoud met -s

  +s voor substantieven met meer dan 1 lettergreep die eindigen op -e, -el, -en, -er, -em, -ie
  De wafel -> de wafels

  +s voor substantieven die eindigen op é, eau:
  Het cadeau -> De cadeaus
  Het café -> de cafés

  +’s voor substantieven die eindigen op –a, -i, -o, -u, -y:
  De paraplu -> De paraplu’s
  voorbeelden in context

  +’s voor afkortingen, +'en als afkorting eindigt –s of -x:
  tv -> tv's
  GPS -> gps'en
  bmx -> BMX'en
  voorbeelden in context

  )
