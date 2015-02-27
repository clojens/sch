(ns sch.conditions
  (:require [sch.core :refer :all]
            [instaparse.core :as insta]))

(def lang
  (insta/parser
"
   (* NOTE:
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


"))

(lang "hallo wereld mocht dit zo zijn.")
