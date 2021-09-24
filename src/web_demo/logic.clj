(ns web-demo.logic
  (:require [next.jdbc :as jdbc]))

(defn get-all-address [ds]
  (str (jdbc/execute! ds ["select * from address"])))

(defn get-address-by-id [ds id]
  (str (jdbc/execute! ds ["select * from address where id = ?" id])))


