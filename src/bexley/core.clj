(ns bexley.core
  (:require [clojure.string :as str])
  (:use hiccup.core hiccup.page
        clojure.java.shell))

(def people
  [{:name "Ethan Sherbondy" :suite 406 :athena "ethanis"}
   {:name "Dennis Wilson" :suite 406 :athena "dennisw"}
   {:name "Sophie Diehl" :suite 406 :athena "sldiehl"}
   {:name "Chris Sarabalis" :suite 408 :athena "dreambig"}
   {:name "Kristjan Kaseniit" :suite 408 :athena "kristjan"}])

(defn splitname [person]
  (str/split (:name person) #"\s"))

(defn fname [person]
  (first (splitname person)))

(defn lname [person]
  (last (splitname person)))

(defn email [person]
  (str (:athena person) "@mit.edu"))

(defn suitemates [person]
  (let [suite (:suite person)]
    (filter #(and (= (:suite %) suite) (not= % person)) 
            people)))

(defn page
  "Returns some html, dude"
  ([]
   (html [:span {:class "foo"} "bar"]))

  ([person]
   (let [mates (suitemates person)]
     (html5
       [:body
        [:p#buddy (str "Hello there, " (fname person))]
         (if (nil? mates)
           [:p "You have no friends"]
           [:div
            [:p "You have suitemates!"]
            [:ul 
              (for [mate mates]
                [:li [:a {:href (email mate)} (:name mate)]])]]
           )]))))

(def ethan
  "just for quick testing"
  (first people))

(def raw-residents (atom nil))

(defn parse-residents []
  (letfn [(parse-resident [resident]
            [(mapcat #(vector (keyword (%1 1)) (%1 2)) 
                    (re-seq #"\n(\w+): (.*)\n" resident))])]
    (vector (mapcat parse-resident (str/split @raw-residents #"\n#.*\n")))))

(defn get-residents []
  (reset! raw-residents (:out (sh "ldapsearch" "-h" "ldap.mit.edu" 
                                  "-b" "dc=mit,dc=edu" 
                                  "-x" "street=Bexley Hall *"))))
