(ns twenty48.core)

(def ^:private dir->rot {:left 0
                         :down 1
                         :right 2
                         :up 3})

(defn- filter-not-nil [row]
  (filter identity row))

(defn- nil-pad-row [row]
  (take 4 (concat row (repeat nil))))

(defn- add-pairs [row]
  (loop [r row
         output []]
    (if (empty? r) output
        (let [cur (first r)
              next (second r)]
          (if (= cur next)
            (recur (drop 2 r) (conj output (* 2 cur)))
            (recur (drop 1 r) (conj output cur)))))))

(defn- rotate-right [board]
  (apply mapv #(into [] %&)
         (reverse board)))

(defn- rotate-board [n board]
  (nth (iterate rotate-right board) n))

(defn slide [direction board]
  (->> (partition 4 board)
       (rotate-board (dir->rot direction))
       (map filter-not-nil)
       (map add-pairs)
       (map nil-pad-row)
       (rotate-board (- 4 (dir->rot direction)))
       (apply concat)
       (vec)))

(defn- random-element []
  (rand-nth [2 2 2 4]))

(defn- get-empty-positions [board]
  (reduce-kv (fn [s k v] (if (nil? v) (conj s k) s))
             []
             board))

(defn- assoc-on-random-empty-pos [board elem]
  (let [pos (rand-nth (get-empty-positions board))]
    (assoc board pos elem)))

(defn create-game []
  (-> (repeat 16 nil)
      (vec)
      (assoc-on-random-empty-pos (random-element))
      (assoc-on-random-empty-pos (random-element))))

(defn move [direction board]
  (let [newboard (slide direction board)]
    (if (= board newboard) board
        (assoc-on-random-empty-pos newboard (random-element)))))
