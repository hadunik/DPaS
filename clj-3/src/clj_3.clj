(ns clj_3)

(defn prepare-chunks
  [s] (let [
            core-number (.availableProcessors (Runtime/getRuntime))
            chunk-size 10
            ]
        (partition-all core-number (partition-all chunk-size s))
        )
  )

(defn future-part
  [pred s] (->> s
                (map #(->> %
                           (filter pred)
                           (doall)
                           (future)
                           )
                     )
                (doall)
                (map deref)
                (reduce concat)
                )
  )

(defn pfilter
  ([pred s] (pfilter pred (prepare-chunks s) s))
  ([pred batch s]
   (lazy-seq
     (if (empty? batch)
       (-> ())
       (concat
         (future-part pred (first batch))
         (pfilter pred (rest batch) s)
         )
       )
     )
   )
  )
