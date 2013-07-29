(ns cljzmq-examples.serialization.clojure-test
  (:require [clojure.test :refer :all]
            [cljzmq-examples.serialization.clojure :refer :all]
            [zeromq.zmq :as zmq]))

(deftest clojure-serialization-test
  (let [context (zmq/zcontext)]
    (with-open [receiver (doto (zmq/socket context :pull)
                           (zmq/bind "tcp://*:8765"))
                sender (doto (zmq/socket context :push)
                         (zmq/connect "tcp://127.0.0.1:8765"))]
      (send-clj sender {:msg "Hello, World!"})
      (let [actual (receive-clj receiver)]
        (is (= {:msg "Hello, World!"} actual))))))
