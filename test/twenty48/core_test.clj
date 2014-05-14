(ns twenty48.core-test
  (:use midje.sweet)
  (:require [twenty48.core :refer :all]))

(def n nil)

(fact "slides to left"
  (slide :left [n n n n
                n n 2 n
                n n n n
                n n n n])
  => [n n n n
      2 n n n
      n n n n
      n n n n]

  (slide :left [2 4 6 8
                n n n n
                n n 2 4
                n n n n])
  => [2 4 6 8
      n n n n
      2 4 n n
      n n n n])

(fact "adds element pairs"
  (slide :left [n n n n
                2 2 n n
                n n n n
                n n n n])
  => [n n n n
      4 n n n
      n n n n
      n n n n]

  (slide :left [2 4 6 8
                2 2 4 4
                2 4 4 2
                4 2 2 4])
  => [2 4 6 8
      4 8 n n
      2 8 2 n
      4 4 4 n])

(fact "add adds elements greedily"
  (slide :left [2 2 2 n
                n n n n
                n n n n
                n n n n])
  => [4 2 n n
      n n n n
      n n n n
      n n n n])

(fact "creates random boards"
  (count (filter identity (create-game))) => 2
  (->> (repeatedly 1000 create-game)
       (set)
       (count)) =not=> 1)

(fact "creates board with random values"
  (->> (repeatedly 1000 create-game)
       (map frequencies)
       (set)
       (count)) =not=> 1)

(fact "doesnt insert element if a move didnt change the board"
  (move :left [2 4 n n
               n n n n
               n n n n
               n n n n])
  => [2 4 n n
      n n n n
      n n n n
      n n n n])

(fact "inserts element if a move changes the board"
  (-> (move :left [2 2 n n
                   n n n n
                   n n n n
                   n n n n])
      (frequencies)) => {4 2, nil 14}
      (provided (#'twenty48.core/random-element) => 4))

(fact "slides up (rotates and rotates back)"
  (slide :up [n n n n
              2 2 2 2
              n n n n
              n n n n]) => [2 2 2 2
                            n n n n
                            n n n n
                            n n n n])

(fact "slides down but doesnt change"
  (slide :down [n n n n
                n n n n
                n n n n
                2 4 6 8]) => [n n n n
                              n n n n
                              n n n n
                              2 4 6 8])
