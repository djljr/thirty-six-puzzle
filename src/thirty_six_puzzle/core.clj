(ns thirty-six-puzzle.core)

(def field [[5 3 2 1 4 6]
            [4 1 6 2 5 3]
            [6 5 3 4 1 2]
            [1 2 5 3 6 4]
            [2 4 1 6 3 5]
            [3 6 4 5 2 1]])

(def empty-board (vec (repeat 6 (vec (repeat 6 :none)))))

(def colors [:green :blue :red :yellow :purple :orange])

(def color-string {:none "-" :green "G" :blue "B" :red "R" :yellow "Y" :purple "P" :orange "O"})

(defn place-cube [board row col color]
  (update-in board [row col] (fn [_] color)))

(defn print-board [board]
  (clojure.string/join "\n" (map #(apply str (map color-string %)) board)))
