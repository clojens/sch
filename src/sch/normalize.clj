(ns sch.normalize
  (:require (clojure [pprint :refer [pprint]]
                     [string :as str]
                     [edn :as edn]))
  (:import (com.google.common.net UrlEscapers)
           (com.google.common.escape Escaper)
           (org.apache.commons.codec.binary Base64)
           (com.google.common.base Charsets)))

;; utf-8 and byte64 en-/decoding
(defn utf8-bytes
  "Takes a string and returns the UTF-8 encoded bytes."
  [s] (.getBytes s (Charsets/UTF-8)))

(defn utf8->base64
  "Takes a string of utf-8 text and returns a base64 encoded string."
  [str8] (. Base64 encodeBase64String (utf8-bytes str8)))

(defn base64->utf8
  "Takes a base64 encoded text and decodes it to utf-8 string."
  [str64] (String. (byte-array (map byte (. Base64 decodeBase64 str64)))))

(defn first-keywords
  "Takes a sequence of strings and returns the first words as keywords."
  [seq-in] (map #(keyword (str/lower-case (first (str/split % #" ")))) seq-in))

(defn url-escape
  "Takes a URI and properly escapes it using Google Guava libraries."
  [u] (.escape (UrlEscapers/urlFragmentEscaper) u))
