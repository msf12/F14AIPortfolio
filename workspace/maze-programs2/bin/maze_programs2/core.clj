(ns maze-programs2.core)

;(defn create-keyword
;  ([x y]
;    (keyword (str x "-" y)))
;  ([[x y]]
;    (create-keyword x y)))

(defn maze-dimensions
  "Takes a maze and returns its dimensions"
  [maze]
  [(inc (apply max (map first (filter vector? (keys maze)))))
   (inc (apply max (map last (filter vector? (keys maze)))))])


(def mem-dimensions (memoize maze-dimensions))


(defn valid-neighbors
  "Returns a list of vectors representing the x and y position of a room in a maze"
  ([maze mazex mazey loc]
    (let [locx (first loc)
          locy (last loc)
          north [locx (dec locy)]
          east [(inc locx) locy]
          south [locx (inc locy)]
          west [(dec locx) locy]]
      
      ;filter to valid neighbors
      (keys (into {} (filter #(and (val %) ;thanks Lee!!!!
                                   (zero? (count (val %))))
                             {north (get maze north)
                              east (get maze east)
                              south (get maze south)
                              west (get maze west)})))))
  ([maze [mazex mazey] loc]
    (valid-neighbors maze mazex mazey loc))
  ([maze loc]
    (let [mazex (first (mem-dimensions maze))
          mazey (last (mem-dimensions maze))]
      (valid-neighbors maze mazex mazey loc))))


(defn create-maze
  ([[mazex mazey]]
    (create-maze mazex mazey))
  ([mazex mazey]
    (let [maze (reduce merge (for [y (range mazey)
                                   x (range mazex)]
                               {[x y] []})) ;maze is a map of [x y] location vectors and the locations they connect to
          start [0 (rand-int mazey)] ;chosen starting room (always in the column x=0)
          end [(dec mazex) (rand-int mazey)]  ;chosen ending room (always in the column x=0)
          chosen-neighbor (rand-nth (mem-neighbors maze mazex mazey start))] ;chosen starting connection
      
      ;return the maze with the initial step taken
      (merge maze
             {start [chosen-neighbor]
              chosen-neighbor [start]
              :start start
              :end end}))))


;A complete maze where the path from the start reaches each room once has (num-rooms - 1) * 2 connections
;when both directions of each direction are counted and only one room connects to the end
(defn goal
  ([maze]
    (let [ mazex (first (mem-dimensions maze))
          mazey (last (mem-dimensions maze))]
      (if (and (= (apply +
                         (map count
                              (vals (filter #(vector? (key %)) maze))))
                  (* (dec
                       (* mazex mazey))
                     2))
               (= 1
                  (count
                    (get maze
                         (:end maze)))))
        true
        false))))


(def mem-neighbors (memoize valid-neighbors))


(def mem-goal (memoize goal))


(defn edge-rooms
  "Finds the edge rooms of the maze (all rooms with viable neighbors that are in the path of the maze)"
  [maze]
  (map first (filter #(and (> (count (val %)) 0)
                           (> (count (mem-neighbors maze (key %)))) 0)
                     (filter #(vector? (key %)) maze))))


(defn successors
  [maze]
  (let [mazex (first (mem-dimensions maze))
        mazey (last (mem-dimensions maze))
        edge-rooms (edge-rooms maze)]
    (flatten
      (for [f-num (range (count edge-rooms)) ;which edge room is currently being built from
            :let [current-room (nth edge-rooms f-num) ;the edge room
                  neighbors (mem-neighbors maze current-room)]] ;the list of neighboring locations to the edge room
        (for [num-neighbor (range (count neighbors))] ;which neighbor is to be connected for this branch of the search
          ;connect the rooms
          (merge maze
                 {current-room (conj (get maze current-room) (nth neighbors num-neighbor))
                  (nth neighbors num-neighbor) (vec (conj (get maze num-neighbor) current-room))}))))))


(defn search
  [goal start combiner successors] ;;***
  (loop [frontier [(hash-map :contents start :history [])]]
    (if (empty? frontier)
      false
      (let [f (first frontier)
            r (rest frontier)]
;        (println "Frontier:" (map :contents frontier) "Checking:" (:contents f)) ;this line makes it take extraodrinary amounts longer to run
        (if (goal (:contents f))
          f
          (recur
            (combiner
              (map #(hash-map :contents %
                              :history (conj (:history f) (:contents f)))
                   (successors (:contents f)))
              r)))))))

;(goal (create-maze 5 5))

;(successors (create-maze 2 2))

;(search goal (create-maze 5 5) concat successors)