(ns web-demo.logic
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]))

(defn get-all-address [ds]
  (jdbc/execute! ds ["select * from address"]))

(defn get-address-by-id [ds id]
  (jdbc/execute! ds ["select * from address where id = ?" id]))

(defn post-address [ds address-detail]
  (sql/insert! ds :address address-detail))


