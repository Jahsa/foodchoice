(ns main.core
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :as ajax]
            [cljs-time.coerce :refer [from-date to-date from-long]]
		    [cljs-time.core :as time
		     :refer [date-time interval utc within?
		             local-date local-date-time period]]
		    [cljs-time.extend]
		    [cljs-time.format :as format
		     :refer [formatter formatters instant->map parse unparse
		             formatter-local
		             parse-local parse-local-date
		             unparse-local unparse-local-date
		             with-default-year]]
            ))

(enable-console-print!)


;cljs-time formatters
(def built-in-formatter (formatters :basic-date-time))
(def custom-formatter (formatter "EEEE MM/dd/yy"))

(def today (unparse custom-formatter (from-date (js/Date.) )))


;increase count function and reset count function
(def click-count (atom 0))

;adding a div for css on count number
(defn show-count []
	[:div.showNum @click-count
	])

;banner with date and count
(defn info [] 
	[:div.text "Dinner Count for " today ": " [show-count] ]
	)

(defn count-increase []
	[:div 
		[:input.btnIncrease {:type "button" :value "I want dinner and have no preference"
		    :on-click (fn [e] (swap! click-count inc))}]])

(defn count-reset []
    [:div 		
		[:input.btnReset {:type "button" :value "reset count"
		    :on-click (fn [e] (reset! click-count 0))}]])



; iterate through vector of food types, placing into divs
(def food-types [["Chinese"] ["Indian"] ["Japanese"] ["Vietnamese"] ["French"] ["American"] ["Ethiopian"]])


(def v [:div])

(defn mappy [a] 
	(into [:div] (map #(into [:div.items] %) a)))

(defn items [] 
	(mappy food-types)
	)

(defn food-display [] 
	[:div.container {:type "button" 
		    :on-click (fn [e] (swap! click-count inc))}
		[items]

	]
)


;main app render 
(defn app-view []
	[:div
		[count-increase]
		[count-reset] 
		[food-display]
	])






;render components
(reagent/render-component [app-view] (.getElementById js/document "app"))

(reagent/render-component [info] (aget (.getElementsByClassName js/document "container") 0))


