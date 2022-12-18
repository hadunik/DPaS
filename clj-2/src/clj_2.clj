(ns clj_2)

(defn prime [num array]
  (->> (take-while #(<= (* % %) num) array)
       (map #(mod num %))
       (every? #(not (= % 0)))))

(defn sieve
  ([] (sieve 2 [2]))
  ([num arr]
   (if (prime num arr)
     (lazy-seq
           (cons num (sieve (inc num) (conj arr num))))
     (do
       (sieve (inc num) arr)
       )
     )
   )
  )


(print (take 20 (sieve)))