(ns etiquetas.web.app
  (:require ["google-map-react" :as GoogleMap]
            [re-frame.core :as rf]
            [reagent.core :as r]
            [reagent.dom]))

(def paris-pos [48.8566 2.3522])
(def louvre-pos [48.8606 2.3376])
(def hotel-de-ville-pos [48.8566 2.3522])

(defn pos->map
  [[lat lng]]
  {:lat lat :lng lng})

(defn simple-map
  []
  [:div {:style {:width "100vh" :height "100%"}}
   [:> GoogleMap {:zoom             11
                  :defaultCenter    (pos->map paris-pos)
                  ;; For some reason, if I change this key name format to
                  ;; kebab-case, it doesn't work.
                  :bootstrapURLKeys {:key ""}
                  :on-child-click (fn [key props]
                                    (js/console.log "onChildClick" key props))}

    ;; 1. `lat` and `lng` props must be at the parent marker; otherwise, the it
    ;; doesn't stick on the map when scrolling and zooming.
    ;; 2. Props are passed to `:on-child-click` handler as a JavaScript object.
    [:div (merge (pos->map hotel-de-ville-pos)
                 {:slug "hotel-de-ville"})
     "Hotel de Ville"]

    [:div (pos->map louvre-pos)
     "Louvre Museum"]]])

(defn mount-root
  []
  (reagent.dom/render [simple-map]
                      (js/document.getElementById "main")))

(defn reload
  []
  (rf/clear-subscription-cache!)
  (mount-root))

(defn main
  []
  (mount-root))
