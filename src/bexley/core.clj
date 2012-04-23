(ns bexley.core
  (:require [clojure.string :as str])
  (:use hiccup.core))

(def people
  [{:name "Ethan Sherbondy" :suite 406 :}
   {:name "Dennis Wilson" :suite 406}
   {:name "Sophie Diehl" :suite 406}
   {:name "Chris Sarabalis" :suite 408}
   {:name "Kristjan Kaseniit" :suite 408}])

(defn splitname [person]
  (str/split (person :name) #"\s"))

(defn fname [person]
  (first (splitname person)))

(defn lname [person]
  (last (splitname person)))

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
     (html [:p#buddy (str "Hello there " (fname person))]
       (if (nil? mates)
         [:p "You have no friends"]
         [:div
          [:p "You have suitemates!"]
          [:ul 
            (for [mate mates]
              [:li (mate :name)])]]
         )))))
