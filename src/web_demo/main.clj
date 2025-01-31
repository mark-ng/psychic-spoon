(ns web-demo.main
  (:require [web-demo.ds :as db]
            [web-demo.routes :as routes]
            [web-demo.http :as http]
            [next.jdbc :as jdbc]
            [honey.sql :as sql]))

(def config {:db {:dbtype "h2" :dbname "example"}
             :port 3001})

(def system (atom nil))

(defn start []
  (reset! system
          (-> config
              (db/start)
              (routes/start)
              (http/start))))

(defn stop []
  (-> @system
      (http/stop)
      (routes/stop)
      (db/stop)))

(defn create-table []
  (jdbc/execute! (@system :ds) ["
create table address (
  id int auto_increment primary key,
  name varchar(32),
  email varchar(255)
)"]))

(defn insert-value []
  (jdbc/execute! (@system :ds) ["
insert into address(name,email)
  values('Sean Corfield','sean@corfield.org'), ('Mark', 'mark@gmail.com')"]))

(defn add-shutdown-hook [f]
  (-> (Runtime/getRuntime)
      (.addShutdownHook (Thread. f))))

(defn -main [& args]
  (add-shutdown-hook (fn []
                       (stop)
                       (println "app-stopped")))
  (start)
  ;; (create-table)
  ;; (insert-value)
  (println "app-started"))


(defn refresh [] (stop) (start))

(comment
  (start)
  (stop)
  (sql/format {:select [:*], :from [:table], :where [:= :id 1]}))

(refresh)