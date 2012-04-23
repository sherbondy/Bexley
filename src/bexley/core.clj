(ns bexley.core
  (:use [hiccup.core]))

(def people
  [{:name "Ethan Sherbondy" :suite 406}
   {:name "Dennis Wilson" :suite 406}
   {:name "Sophie Diehl" :suite 406}
   {:name "Chris Sarabalis" :suite 408}
   {:name "Kristjan Kaseniit" :suite 408}])

(defn suitemates [person]
  (let [suite (person :suite)]
  (filter (fn [other] (and (= (other :suite) suite) (not (= other person)))) 
          people)))

(defn page
  "Returns some html, dude"
  ([]
   (html [:span {:class "foo"} "bar"]))
  ([nom & others]
   (html [:p#buddy (str "hello there " nom)]
         (if others
           [:p "You have friends!"
             [:ul 
              (for [other others]
                [:li other])]]
           [:p "You have no friends"]
           ))))