(ns web-demo.routes
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [web-demo.logic :as logic]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.util.response :refer [response]]))

(defn create-routes [ds]
 (->> (defroutes app
        ;; Address APIs
        (context "/address" []
          (GET "/" [] (response (logic/get-all-address ds)))
          (POST "/" [:as {:keys [body]}]
            (logic/post-address ds body)
            (response {:msg "Post address successfully"}))
          (GET "/:id" [id] (response (logic/get-address-by-id ds id))))

        ;; Not Found APIs
        (route/not-found "Page Not Found"))
      (wrap-json-response)
      (wrap-json-body)))

(defn start [{:keys [ds] :as config}]
  (assoc config :handler (create-routes ds)))

(defn stop [{:keys [ds] :as config}]
  (dissoc config :handler))