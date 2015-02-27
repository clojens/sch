(pull 'me.raynes/conch)

(require '(clojure [string :as s])
         '[me.raynes.conch :refer [programs with-programs]])

;; side effects binding to shell program
(programs espeak)

;; compositions
(def not-nil? (complement nil?))
(def trim (comp s/triml s/trimr s/trim-newline))
(def butlast2 (comp s/join butlast butlast))
(def second-last (comp str last butlast))


(def curse-words
  (into #{} (remove empty?
                    (map trim
                         (s/split "fuck fck fuk damn crap shit damnit
                                  damn-it damned fuckers" #" ")))))

(def tokens
  {:vowels #{\A \a \E \e \I \i \O \o \U \u}
   :consonants #{\B \b \C \c \D \d \F \f \G \g \H \h \J \j \K \k \L \l \M \m \N \n
                 \P \p \Q \q \R \r \S \s \T \t \V \v \W \w \X \x \Y \y \Z \z}
   :other #{\space}
   })

(defn tokenize [word] (seq word))

;; predicates
(defn vowel? [t] (not-nil? ((:vowels tokens) t)))
(defn consonant? [t] (not-nil? ((:consonants tokens) t)))
(defn other? [t] (not-nil? ((:other tokens) t)))
(defn curse? [w] (contains? curse-words w))


(defn phone [word] (-> (espeak "-x" word) s/trim-newline s/triml))

(defn cvce-families [word]
  (letfn [(form [prefix charstr] (re-pattern (str "\\b.*" prefix "[" charstr "]" "e\\b")))]
    (let [pdict {:long-a-silent-e (form "a" "c|g|k|l|m|n|p|s|t|v")
                 :long-i-silent-e (form "i" "c|d|f|k|l|m|p|n|s|t")
                 :long-o-silent-e (form "o" "b|d|k|l|m|n|p|s|t")
                 :long-u-silent-e (form "u" "b|d|g|l|m|n|t|s")}]
      (into {} (remove nil? (map #(when (re-matches (val %) word)
                                    (hash-map (key %) word))
                                 pdict))))))

(defn cvce?
  "Predicate returns true if word is part of the families that end with
  consonant-vowel-consonant-silent:e structures."
  [word] (let [m (cvce-families word)
               w (when (vals m) (vals m))
               w? (not-nil? w)] w?))

(defn real-word
  [w]
  (if (cvce? w)
    [(butlast2 w) (second-last w)]
    (if (consonant? (last w))
      [(butlast w) (last w)]
      w
      )))

(real-word "havuu")

(map #(if (curse? %)
        (phone "")
        (phone %)) ["dude" "fuck" "where" "is" "my" "car"])

;; (phone "Welcome")
;; (closing-sound (phone "Welcome"))


(defn split-words [words] (s/split words #" "))

(defn linker [w nw]
  "When a word ends in a consonant sound, we often move the consonant
  sound to the beginning of the next word if it starts with a vowel sound."
  (if (and (consonant? (last w))
           (vowel? (first nw)))
    (list (s/join (butlast w)) (str (last w) (first nw)))
    w))

;; (linker "Can" "I")

;; PROBLEM Sound that counts so have while ending in a vowel,
;; sounds like it ends in a consonant so its broken as hav- not have-
;; and the e is dropped.

(defn link-cv
  [wseq]
  (map (comp consonant? last) (split-words wseq)))

;; (link-cv "can i have")

(defn process1
  [word]
  (map #(if (vowel? %) {:v %}
          (if (consonant? %) {:c %}
            (if (other? %) {:o %}
              {:N/A %}))) (tokenize word)))


;; (frequencies (process1 "welcome"))