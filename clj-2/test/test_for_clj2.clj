(ns test_for_clj2
  (:require [clj_2 :refer :all]
            [clojure.test :refer :all]))

(deftest check_numbers
  (is (= (vec (take 20 (sieve))) [2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71]))
  )

(def c (sieve))

(deftest time_demonstration
  (time (nth c 1000))
  (time (nth c 1000))
  )