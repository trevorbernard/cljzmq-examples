; Shows how to handle Ctrl-C

(ns zguide.interrupt
  (:require [zeromq.zmq :as zmq])
  (:import [org.zeromq ZMQException ZMQ$Error]))

(defn -main []
  (let [context (zmq/zcontext)]
    (with-open [socket (doto (zmq/socket context :rep)
                            (zmq/bind "tcp://*:5555"))
                current-thread (. Thread currentThread)]
      (.addShutdownHook (Runtime/getRuntime)
                        (Thread. (fn []
                                   (println "W: interrupt received, killing server...")
                                   (zmq/destroy context)
                                   (try
                                     ((.interrupt current-thread)
                                      (.join current-thread ))
                                     (catch InterruptedException e)))))
      (while (not (.. Thread currentThread isInterrupted))
        (try
          (zmq/receive socket)
          (catch ZMQException e (when
                                  (=
                                   (.getErrorCode e)
                                   (.. ZMQ$Error ETERM getCode))
                                  (.. Thread currentThread interrupt))))))))
