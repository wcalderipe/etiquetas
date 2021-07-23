(ns etiquetas.web.app
  (:require ["google-map-react" :as GoogleMap]
            [re-frame.core :as rf]
            [reagent.core :as r]
            [reagent.dom]))

(def paris [48.8566 2.3522])

(def louvre [48.8606 2.3376])
(def hotel-de-ville [48.8566 2.3522])
(def eiffel-tower [48.8584 2.2945])
(def arc-de-triomphe [48.8738 2.2950])
(def grand-palais [48.8661 2.3125])
(def pantheon [48.8462 2.3464])
(def opera [48.8720 2.3316])
(def bastille [48.8513 2.3762])
(def republique [48.8677 2.3640])

;;; MARKER
;;
;; TODO: Investigate why the click pointer is out of the container? It's
;; probably something related to the absolute position.

(def marker-styles
  {:container {:width    "45px"
               :height   "45px"
               :position "relative"
               :cursor   "pointer"}
   :pin       {:width         "100%"
               :height        "100%"
               :border-radius "50% 50% 50% 0"
               :background    "#00cae9"
               :position      "absolute"
               :top           "-100%"
               :left          "-50%"
               :transform     "rotate(-45deg)"}
   :icon      {:position      "absolute"
               :background    "#FFF"
               :width         "70%"
               :height        "70%"
               :border-radius "50%"
               :top           "-85%"
               :left          "-35%"}})

(defn marker
  []
  [:div.marker {:style (:container marker-styles)}
   [:div.marker__pin {:style (:pin marker-styles)}]
   [:div.marker__icon {:style (:icon marker-styles)}]])

;;; MAP

(def positions [{:name "Louvre Museum" :pos louvre}
                {:name "Hotel de Ville" :pos hotel-de-ville}
                {:name "Eiffel Tower" :pos eiffel-tower}
                {:name "Arc de Triomphe" :pos arc-de-triomphe}
                {:name "Grand Palais" :pos grand-palais}
                {:name "Pantheon" :pos pantheon}
                {:name "Opéra" :pos opera}
                {:name "Bastille" :pos bastille}
                {:name "République" :pos republique}])

(defn pos->map
  [[lat lng]]
  {:lat lat :lng lng})

(defn map-demo
  []
  [:div {:style {:width "100vh" :height "100%"}}
   [:> GoogleMap {:zoom             11
                  :defaultCenter    (pos->map paris)
                  ;; For some reason, if I change this key name format to
                  ;; kebab-case, it doesn't work.
                  :bootstrapURLKeys {:key ""}
                  :on-child-click (fn [key props]
                                    (js/console.log "onChildClick" key props))}
    (for [{:keys [_ pos]} positions]
      ^{:key (str pos)}
      [:div (merge (pos->map pos))
       [marker]])]])

(defn layout
  []
  [:<>
   [map-demo]])

(defn mount-root
  []
  (reagent.dom/render [layout] (js/document.getElementById "main")))

(defn reload
  []
  (rf/clear-subscription-cache!)
  (mount-root))

(defn main
  []
  (mount-root))
