(ns web-demo.logic
  (:require [next.jdbc :as jdbc]
            [honey.sql :as sql]))

(defn get-all-address [ds]
  (jdbc/execute! ds (sql/format {:select [:*] :from [:address]})))

(defn get-address-by-id [ds id]
  (jdbc/execute! ds (sql/format {:select [:*] :from [:address] :where [:= :id id]})))

(defn post-address [ds address-detail]
  (jdbc/execute! ds
                 (sql/format {:insert-into :address
                              :columns [:name :email]
                              :values [[(address-detail "name") (address-detail "email")]]})))

