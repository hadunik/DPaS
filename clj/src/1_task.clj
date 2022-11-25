(defn possibleChars [word alphabet]
  (filter #(not (clojure.string/ends-with? word %))
          alphabet
          )
  )

(defn createWords [word alphabet]
  (
    map (fn [c] (str word c))
        (possibleChars word alphabet)
        )
  )

(defn catWords [xs alphabet]
  (reduce concat
          (map (fn [x] (createWords x alphabet)) xs)
          )
  )

(defn main [n alphabet]
  (
    reduce (fn [xs _] (catWords xs alphabet))
           (list "")
           (range n)
           )
  )

(println (main 3 '("a" "b" "c" "d")))