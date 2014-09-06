;; Report 0MQ version

(ns zguide.version
  (:require [zeromq.zmq :as zmq]
            [clojure.string :as str]))

(defn -main []
  (println "Current 0MQ version is" (str/join "." [(:major zmq/version)
                                                   (:minor zmq/version)
                                                   (:patch zmq/version)])))
