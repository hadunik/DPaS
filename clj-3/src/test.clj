(ns test
  (:require [clj_3 :refer :all]
            [clojure.test :refer :all]))

(defn slow-odd
  [d]
  (Thread/sleep 10)
  (odd? d)
  )

(deftest work-with-empty
  (is (=
        (filter odd? (range 0))
        (pfilter odd? (range 0))
        ))
  )

(deftest normal-work
  (is (=
        (filter odd? (range 1000))
        (pfilter odd? (range 1000))
        ))
  )

(deftest time-test
  (time (doall (take 100 (filter slow-odd (iterate inc 0)))))
  (time (doall (take 100 (pfilter slow-odd (iterate inc 0)))))
  )

