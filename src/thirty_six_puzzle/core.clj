(ns thirty-six-puzzle.core)

(def empty-board [[{:height 5 :color :none} {:height 3 :color :none} {:height 2 :color :none} {:height 1 :color :none} {:height 4 :color :none} {:height 6 :color :none}]
                  [{:height 4 :color :none} {:height 1 :color :none} {:height 6 :color :none} {:height 2 :color :none} {:height 5 :color :none} {:height 3 :color :none}]
                  [{:height 6 :color :none} {:height 5 :color :none} {:height 3 :color :none} {:height 4 :color :none} {:height 1 :color :none} {:height 2 :color :none}]
                  [{:height 1 :color :none} {:height 2 :color :none} {:height 5 :color :none} {:height 3 :color :none} {:height 6 :color :none} {:height 4 :color :none}]
                  [{:height 2 :color :none} {:height 4 :color :none} {:height 1 :color :none} {:height 6 :color :none} {:height 3 :color :none} {:height 5 :color :none}]
                  [{:height 3 :color :none} {:height 6 :color :none} {:height 4 :color :none} {:height 5 :color :none} {:height 2 :color :none} {:height 1 :color :none}]])

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

(defn valid-heights [board height]
  (map #(row-has-height % height) board))

(defn valid-color [board color]
  (map #(not (row-has-color % color)) board))

(defn valid-row [position-data]
  (map #(every? identity %) (apply mapv vector position-data)))

(defn valid-rows [board color height]
  (let [valid-heights (valid-heights board height)
        valid-color-row (valid-color board color)
        valid-color-col (valid-color (transpose board) color)]
    (valid-row [valid-heights valid-color-row valid-color-col])))
