(ns sch.atomic
  (:require [alembic.still :refer [distill*]]
            [clojure.string :as string]))

(def instellingen
  {:aanwijzen "::="
   :spaties " "})

(def spatie (instellingen :spaties))
(def gelijk (instellingen :aanwijzen))

(defprotocol Grammatica
  (aanwijzen
   [lhz] [lhz rhz] [lhz is rhz])
  (reeks [dingen] dingen)
  (groep
   [binnen] [binnen buiten]))

(extend-protocol Grammatica

  ;; String manipulation and encapsulation + escaping
  java.lang.String

  (aanwijzen
   ([lhz] (str lhz spatie gelijk spatie "'" lhz "'"))
   ([lhz rhz] (str lhz spatie gelijk spatie rhz))
   ([lhz is rhz] (str lhz is rhz)))

  (groep
   ([binnen] (str "'" binnen "'"))
   ([binnen buiten] (str (first buiten) binnen (second buiten))))

  ;; Sequences for and important part of formal grammars
  clojure.lang.PersistentVector

  (reeks [dingen &{teken :teken :or {teken "|"}}]
         (string/join " " (interpose teken dingen))))


(aanwijzen "hallo" (groep "woord" ["(" ")"]))

(groep "Hallo" ".")

(reeks ["hallo" "wereld"])
(zus "hello" "fo")

(defn pull
  [pkg &{version :version :or {version "LATEST"}}]
  (distill* [[pkg version]] {:verbose true}))

(pull 'instaparse)

(require '[instaparse.core :as insta]
         '[clojure.string :as string])

(def consttable {:newline \newline :null \0 :tab \tab})

;; (comment
;;   Intrinsic Attributes
;;   Attribute Entrys
;;   Attribute Lists
;;   BlockId Elements
;;   Macros
;;   API and Command Line Attributes)

(defn productie-regel
  "BNF en varianten bestaan uit verzameling productregels. Linker-/RechterHandZijde vormen expressies.
  LHZ zijn non-terminals en RHZ (non)terminal reeksen. Voorbeeld:

    Zin     := Woorden
    Woorden := *Woord
    Woord   := #'\\b[A-Za-z]+\\b'
  "
  [lhz rhz] (str lhz (definitie) rhz))


(defn definitie
  "Tussen de linker- en rechterhandzijde vindt aanwijzing plaats (links naar rechts) doormiddel van
  het gelijk teken of gelijkwaardige varianten := en ::=. Voorbeeld:

    Zin := Woorden
    Woorden ::= *Woord
    Woord = #'[A-Za-z]'
  "
  [{t :t :or {t "::="}}] (str " " t " "))


;; uitvoer van de naam LHZ kan met <deze> haakjes geblokt worden (onzichtbaar in boom)
(defn stille-regel [lhz rhz] (str "<" lhz "> ::= " rhz))
;; voer in opmerking in, dit werkt over meerde regels dan een
(defn opmerking [txt] (str "(* " txt " *)" ))
(defn regex-teken [c] (str "#'[" c "]'"))
(defn regex-tekens [& cs] (str "#'[" (map str cs) "]+'"))
(defn insluiten [s &{teken :teken :or {teken "'"}}] (str teken s teken))

;; (re-key :dquote \")

;; (map char (range 0 200))

;; (def terminals
;;   {:encapsulation {:tokens [{"[" "]"}{"(" ")"}{"<" ">"}{"{" "}"}{"'" "'"}{"\\"" "\\""}]}})

;; ;;       :dquote \"
;; ;;       :squote \'
;; ;;       :pound \#
;; ;;       :dollar \$
;; ;;       :obelus \%
;; ;;       :amp \&

;; ;;       :equal \=
;; ;;       :qmark \?
;; ;;       :bang \!

;; ;;       :ophar \(
;; ;;       :cphar \)
;; ;;       :star \*
;; ;;       :plus \+
;; ;;       :comma \,
;; ;;       :minus \-
;; ;;       :period \.
;; ;;       :slash \/
;; ;;       :backslash \\

;; ;;       :numbers {:range true
;; ;;                 :synonyms [:num :digits]
;; ;;                 :tokens {:zero \0 :one \1 :two \2 :three \3 :four \4 :five \5 :six \6 :seven \7 :eight \8 :nine \9}
;; ;;       :colon \:
;; ;;       :semicol \;
;; ;;       :lt \<

;; ;;                 :gt \>

;; ;;                 :at \@
;; ;;       :upper {:range true
;; ;;               :synonyms [:capitals :latin-upper :upper-case]
;; ;;               :tokens [\A \B \C \D \E \F \G \H \I \J \K \L \M \N \O \P \Q \R \S \T \U \V \W \X \Y \Z]
;; ;;               }
;; ;;       :lower {:range true
;; ;;               :synonyms [:lower-case :latin-lower :normal-case]}

;; ;;                 :osquare \[
;; ;;       :csquare \]
;; ;;                 :hat \^
;; ;;                 :underscore \_
;; ;;                 :backtick \`
;; ;;                 \{ \| \} \~ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \  \¡ \¢ \£ \¤ \¥ \¦ \§ \¨ \© \ª \« \¬ \­ \® \¯ \° \± \² \³ \´ \µ \¶ \· \¸ \¹ \º \» \¼ \½ \¾ \¿ \À \Á \Â \Ã \Ä \Å \Æ \Ç]

;; ;;       }

;; ;;      ))

;; ;; (defn listing
;; ;;   [sq &{chr :chr :or {chr "|"}}]
;; ;;   (string/join " " (interpose chr
;; ;;                               (map #(str "'" % "'") sq))))

;; ;; (rule "foo" (listing ["foo" "var"]))

;; (def ad
;;   (insta/parser
;;    "
;;    S = S
;;    block-elements = header / title / author-info / first-name / middle-name /
;;                     last-name / email-address / revision-info / revision-number /
;;                     revision-date / revision-remark / attribute-entry / preamble /
;;                     section / title / section-body / block-id / block-title / block-macro /
;;                     block / paragraph / delimited-block / table / list / bulleted-list /
;;                     numbered-list / labeled-list / callout-list / list-entry / list-label /
;;                     list-item / item-text / list-paragraph / list-continuation
;;    inline-elements = quotes / replacements / special-chr / special-words /
;;                      attribute-references / inline-macros

;;    (* <, is replaced with the &lt; >, is replaced with the &gt; &, is replaced with the &amp; *)

;;    emphasis = #'\\b_[A-Za-z0-9]_\\b'


;;    special-chr = '&' / '>' / '<'

;;    attributes = intrinsic-attributes / attribute-entry / attribute-lists / blockid-elements / macros

;;    attribute-entry = attr / attr value

;;    value = #'[A-Za-z0-9][ $|+]'
;;    (* value = substitute-text / content-type / operation *)

;;    attr = ':' '!'? symbolic-name '!'? ':'



;;    "))

