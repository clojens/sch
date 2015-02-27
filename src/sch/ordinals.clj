
(defn step-by [x y] (range x y x))
(def i>s (comp seq str))
(defn remainers [i]
  (let [[h t d :as all] (mapv #(mod i %) [1000 100 10])]
all
    ))

(defn xxx
  [x]
  (map #(list (when-not (= (val %) "een") (val %)) (key %))
   (map-vals ordinal-digits
             (into {} (map #(hash-map  %2
                                       (nth (seq (str x)) %1))
                           [0 2 1] ["honderd" "en" "tig"])))))

(xxx 155)


(defn ordinal-numbers
  [x]
  (letfn [(ord [n] ({0 "" 1 "een" 2 "twee" 3 "drie" 4 "vier" 5 "vijf" 6 "zes" 7 "zeven" 8 "acht" 9 "negen"}
                       (Integer. (str n))))]
    (let [[h d i] (-> x str seq)
          h (str (if (= h \1) "" (ord h)) "honderd")
          d (str (condp = d
                   \2 "twin"
                   \3 "der"
                   \4 "veer"
                   :else (ord d)) (if (= d \0) "tien" "tig"))
          i (str (ord i) "en")
          ]
      (str h i d))))































































;; (pull 'prismatic/plumbing)
;; (use 'plumbing.core)


;; (defn between
;;   "Returns a fn which takes a single argument which is taken as the value
;;   to test if it is between the earlier provided two values."
;;   [x y] (fn [n] (and (> n x) (< n y))))

;; (def decimals? (between 9 100))
;; (def hundreds? (between 99 1000))
;; (def seq->int (comp #(Integer. %) clojure.string/join))
;; (def fints (comp first seq str))

;; (def ordinal-irregular {1 "een" 2 "twee" 3 "drie" 4 "vier" 5 "vijf" 6 "zes" 7 "zeven" 8 "acht" 9 "negen"
;;                            10 "tien" 11 "elf" 12 "twaalf" 13 "dertien" 14 "veertien" 20 "twintig"})

;; (defn irregular? [n]
;;   (contains? ordinal-irregular n))

;; (defn regular
;;   [n]
;;   (if (irregular? n)
;;     (ordinal-irregular n)
;;     (-> n str seq)))

;; (regular 21)
;; (def not-zero? (complement zero?))

;; (defn forms
;;   [i]
;;   (let [x (reverse (map #(mod i %) [10 100 1000]))
;;         [hundrs decs digs :as result]
;;         [(- (first x) (second x))
;;          (- (second x) (nth x 2))
;;          (last x)]
;;         result (if (= digs 0)
;;                  (butlast result) result)
;;         mr (list (when (not-zero? (first result)) "honderd")
;;                  (when (not-zero? (last result)) (list (last result) "en")))
;;         ]
;;     result
;;   ))

;; (forms 90)

;; (rounder 940)

;; (mod 2219 10)

;;   (let [in 1234574 ;; input sample
;;         sin (seq (str in)) ; digit to char seq
;;         modin (mod (count sin) 3) ; modulo 3 the total digits
;;         prefix-digit (- 3 modin) ; check remainer 3-0 or 3-1 or 3-2
;;         sin+ (flatten (conj sin (repeat prefix-digit nil))) ; flatten list after substitution of the missing nr by nils to partition evenly
;;         grp (partition 3 sin+) ; pairs of 3 guaranteed e.g. ((nil nil 1) (2 3 4))
;;         mapgrp (zipmap (reverse grp) ; having pairs of 3, now flip them and zip back up
;;                        (reverse '[triljard triljoen biljard biljoen miljard miljoen duizend honderd]))
;;         ;; results in {[nil nil 1] duizend [1 2 3] honderd}
;;         newgrp (map-keys #(remove nil? %) mapgrp)
;;         cntgrp (map count (keys newgrp))
;;         cntmod (comp #(mod % 3) count)
;;         modgrp (map cntmod (keys newgrp))

;;         fjoin (map-keys seq->int newgrp)


;;         f (map #(< % 3) cntgrp)

;;         ste? (contains? #{\1 \8} (last sin))
;;         suffix (if ste? "ste" "de")


;;         ]
;;     (letfn [


;;             ]

;;       modgrp
;;       fjoin
;;       ))



;; ;;   (mod 443444 10)

;; ;;   4 vier
;; ;;   40 veertig
;; ;;   4 00 vier honderd
;; ;;   4 000 vier duizend
;; ;;   40 000 veertig duizend
;; ;;   400 000 vier honderd duizend
;; ;;   4 000 000 vier miljoen

