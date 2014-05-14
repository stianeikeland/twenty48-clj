(ns twenty48.game
  (:require [reagent.core :as r :refer [atom]]
            [twenty48.core :as game]))

(def game-state (atom (game/create-game)))

(def keycode->direction {38 :up
                         40 :down
                         37 :left
                         39 :right})

(defn Cell [cell]
  [:div.grid-cell
   (when cell [:div {:class (str "tile tile-" cell)}
               [:div.tile-inner (str cell)]])])

(defn Row [row]
  [:div.grid-row
   (map Cell row)])

(defn Grid []
  [:div.grid-container
   (map Row (partition 4 @game-state))])

(defn handle-keys [event]
  (when-let [key (keycode->direction (.-keyCode event))]
    (swap! game-state (partial game/move key))))

(defn ^:export run []
  (.addEventListener js/window "keydown" handle-keys)
  (r/render-component [Grid] (.getElementById js/document "game")))
