(ns bexley.core
  (:require [clojure.string :as str])
  (:use hiccup.core hiccup.page))

(def people
  [{:name "Ethan Sherbondy" :suite 406 :athena "ethanis"}
   {:name "Dennis Wilson" :suite 406 :athena "dennisw"}
   {:name "Sophie Diehl" :suite 406 :athena "sldiehl"}
   {:name "Chris Sarabalis" :suite 408 :athena "dreambig"}
   {:name "Kristjan Kaseniit" :suite 408 :athena "kristjan"}])

(defn splitname [person]
  (str/split (person :name) #"\s"))

(defn fname [person]
  (first (splitname person)))

(defn lname [person]
  (last (splitname person)))

(defn email [person]
  (str (person :athena) "@mit.edu"))

(defn suitemates [person]
  (let [suite (person :suite)]
    (filter (fn [other] (and (= (other :suite) suite) (not (= other person)))) 
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
                [:li [:a {:href (email mate)} (mate :name)]])]]
           )]))))
