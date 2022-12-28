(def n-philosophers 5)
;(def n-philosophers 6)
(def think-time 100)
(def eat-time 100)
(def count-eating 6)

(defn create-forks
  []
  (doall
    (map
      (fn [a] (-> {:fork    (ref 0),
                   :counter (atom 0)}))
      (range n-philosophers)))
  )

(def forks (create-forks))

(defn philosopher
  [id left right]
  (let [left-fork (left :fork)
        left-counter (left :counter)
        right-fork (right :fork)
        right-counter (right :counter)
        ]
    (new Thread
         (fn []
           (loop [x 0]
             (when (< x count-eating)
               (println (str "Philosopher " id " start thinking"))
               (Thread/sleep think-time)
               (dosync
                 (println (str "Philosopher " id " tried getting of forks"))
                 (swap! left-counter inc)
                 (alter left-fork inc)
                 (swap! right-counter inc)
                 (alter right-fork inc)
                 (println (str "Philosopher " id " was able to take forks"))
                 (Thread/sleep eat-time)
                 )
               (println (str "Philosopher " id " finished eating"))
               (recur (inc x))))
           )
         )
    )
  )

(def philosophers
  (map (fn [i] (philosopher
                 i
                 (nth forks i)
                 (nth forks (mod (+ i 1) n-philosophers))))
       (range n-philosophers))
  )
;
(defn start-philosophers [philosophers]
  (doall (map #(. % (Thread/start)) philosophers)))

(start-philosophers philosophers)

(time (doall (map (fn [phil] (. phil (Thread/join))) philosophers)))
(def useful ( reduce + (map #( deref % ) (map #(% :fork) forks))))
(def all ( reduce + (map #( deref % ) (map #(% :counter) forks))))
(println useful)
(println all)
(println (- all useful))

;кол-во философов == 5: кло-во лишних рестартов транзакций 48
;кол-во философов == 6: кло-во лишних рестартов транзакций 55
