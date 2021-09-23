(ns web-demo.routes
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [web-demo.logic :as logic]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]))

(defn create-routes [ds]
 (->> (defroutes app
        (GET "/mark" [] (response {:a "mark"}))
        (GET "/" [] (logic/get-all-address ds))
        (GET "/user/:id" [id] (str "USER ID: " id))
        ;; (GET "/address/:id" [id] (logic/get-address-by-id ds id))
        (route/not-found "Page Not Found"))
      (wrap-json-response)))



(defn start [{:keys [ds] :as config}]
  (assoc config :handler (create-routes ds)))

(defn stop [{:keys [ds] :as config}]
  (dissoc config :handler))