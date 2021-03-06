(ns stl-clojure-nov2018.core
  (:require [quil.core :as q]))

(defn setup []
  (q/frame-rate 30))

(defn pulse [low high rate]
  (let [diff (- high low)
        half (/ diff 2)
        mid (+ low half)
        s (/ (q/mouse-x) 10.0)
        x (q/sin (* s (/ 1.0 rate)))]
    (+ mid (* x half))))

(defn t []
  (* 0.0001 (q/mouse-y) (q/millis)))

(def speed 0.5)

(defn stem [base-x]
  (let [magic (/ 8 (q/width))
        x-max (/ (q/width) 4)
        x-max-top (/ x-max 2)
        y-max (/ (q/height) 2)

        x (+ base-x
             (pulse (- x-max-top) x-max-top 1.0))
        y (+ (- y-max)
             (* 0.5 y-max
                (q/sin (+ (* speed (t))
                          (* magic base-x))))
             (* (/ 3) y-max (q/sin (* 2 (t)))))]
    (q/bezier base-x 0 base-x 0
              0 (- x-max) x y)))
(defn draw []
  (q/background (mod (q/mouse-y) 255)
                (mod (q/mouse-x) 255)
                (mod (- (q/mouse-y)
                        (q/mouse-x)) 255))
  (q/stroke (mod (q/mouse-x) 255)
            (mod (q/mouse-y) 255)
            (mod (- (q/mouse-x)
                    (q/mouse-y)) 255))
  (q/fill (mod (q/mouse-x) 255)
          (mod (q/mouse-y) 255)
          (mod (- (q/mouse-x)
                  (q/mouse-y)) 255))
  (q/stroke-weight 1)
  (q/text-font (q/create-font "Fira Code" 22))
  (q/text (str "mouse x=" (q/mouse-x) " mouse-y=" (q/mouse-y)) 25 25)
  (q/no-fill)
  (let [size (q/width)
        x-max (/ size 4)]
    (q/with-translation [(/ size 2) (q/height)]
      (doseq [x (range (- x-max) x-max 2)]
        (stem x)))))

(defn -main [& args]
  (q/defsketch dancer
    :host "host"
    :size [500 500]
    :setup setup
    :draw draw))

(comment ; CDDD > TDD
  (do
    (-main)
    ))
