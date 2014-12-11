(ns maze-programs.core)

(defn gen-room [[locx locy]
                [mazex mazey]]
  {:location [locx locy]
   
   :neighbors (into [] (map
                         
                         (fn [list]
                           (if (or (some #{true} (map #(< % 0) list))
                                   (> (first list) (dec mazex))
                                   (> (second list) (dec mazey)))
                             nil
                             list))
                         
                         ((fn [[l1 l2]]
                            [[l1 (dec l2)]
                             [(inc l1) l2]
                             [l1 (inc l2)]
                             [(dec l1) l2]])
                           [locx locy])))
   :connected [nil nil nil nil]})

;(gen-room [(rand-int 5) (rand-int 5)] [5 5])

;(for [x (range 3) y (range 3)] [x y])

(defn gen-grid
  ([mazex mazey]
    (for [y (range mazey)
          x (range mazex)]
      (gen-room [x y] [mazex mazey])))
  ([[mazex mazey]]
    (gen-grid mazex mazey)))

;(gen-grid 3 3)

(defn grid-maze [grid]
  (let [start (rand-nth grid)
        end (loop [temp-end (rand-nth grid)]
              (if (= temp-end start)
                (recur (rand-nth grid))
                temp-end))]
    [start end]))

(grid-maze (gen-grid 3 3))

(defn connect-rooms [maze room]
  (let [ind (rand-int 4)]
    (assoc (: