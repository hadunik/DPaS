(ns main)

(defn generator [alphabet words]
  (reduce concat ()
          (map
            (fn [word]
              (map
                (fn [sym] (str word sym))
                (filter
                  (fn [sym]
                    (not (clojure.string/ends-with? word sym))
                  )
                  alphabet
                )
              )
            )
            words
          )
  )
)

(defn possible-strings [alphabet n]
  (reduce (fn [xs x] (generator alphabet xs)) (list "") (range 0 n))
  )

(println (possible-strings (list "a" "b" "c") 3))

;(println (generator (list "a" "b" "c") (list "a" "b" "c")))