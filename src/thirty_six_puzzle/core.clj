(ns thirty-six-puzzle.core)

(def board-heights [[5 3 2 1 4 6]
                    [4 1 6 2 5 3]
                    [6 5 3 4 1 2]
                    [1 2 5 3 6 4]
                    [2 4 1 6 3 5]
                    [3 6 4 5 2 1]])

(defn empty-board []
  (vec (map-indexed
        (fn [row-idx row]
          (vec (map-indexed
                (fn [col-idx height]
                  {:height height :color :none :row row-idx :col col-idx})
                row)))
        board-heights)))

(def colors [:green :blue :red :yellow :purple :orange])

(defn color-string [m] (get {:none "-" :green "G" :blue "B" :red "R" :yellow "Y" :purple "P" :orange "O"} (:color m)))

(defn place-cube [board row col color]
  (update-in board [row col :color] (fn [_] color)))

(defn printable-board [board]
  (clojure.string/join "\n" (map #(apply str (map color-string %)) board)))

(defn transpose [m]
  (apply mapv vector m))

(defn row-has-height [row height]
  (some #(and
          (= height (:height %))
          (= :none (:color %))) row))

(defn row-has-color [row color]
  (some #(= color (:color %)) row))

(def col-has-color row-has-color)

(defn valid-heights [board height]
  (map #(row-has-height % height) board))

(defn valid-color [board color]
  (map #(not (row-has-color % color)) board))

(defn valid-row [position-data]
  (map #(every? identity %) (apply mapv vector position-data)))

(defn find-all-of-height [board height]
  (filter (fn [pos] (= height (:height pos))) (flatten board)))

(defn get-row [board row-idx]
  (get board row-idx))

(defn get-col [board col-idx]
  (get (transpose board) col-idx))

(defn valid-rows [board color height]
  (let [possible-moves (find-all-of-height board height)
        possible-moves (filter (fn [pos]
                                 (row-has-color
                                  (get-row board (:row pos))
                                  color)) possible-moves)
        valid-color-col (valid-color (transpose board) color)]
    possible-moves))
