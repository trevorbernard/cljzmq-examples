;; Hello World client in Clojure
;; Connects REQ socket to tcp://localhost:5555
;; Sends "Hello" to server, expects "World" back

(ns zguide.hwclient
  (:require [zeromq.zmq :as zmq]))

(defn -main []
  (println "Connecting to hello world server…")
  (with-open [context (zmq/context 1)
              socket (doto (zmq/socket context :req)
                       (zmq/connect "tcp://127.0.0.1:5555"))]
    (dotimes [i 10]
      (let [request "Hello"]
        (println "Sending hello " i "…")
        (zmq/send-str socket request)
        (zmq/receive socket)
        (println "Received world " i)))))
