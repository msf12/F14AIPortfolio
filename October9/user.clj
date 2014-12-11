;; Definition for user agents. This is a good template to build on to produce
;; smarter agents.

(ns pucks.agents.user  
  (:use [pucks globals util]
        [pucks.agents active]))

(defn velocity-magnitude [[x y]]
  (java.lang.Math/sqrt (+ (java.lang.Math/pow x 2) (java.lang.Math/pow y 2))))

(defn user-proposals [p]
  {:acceleration 
   (let [linear-vel (velocity-magnitude (:velocity p))]
     (if (empty? 
           (filter #(or (:stone %)
                        (:zapper %))
                   (:sensed p)))
       
       (if (< linear-vel 20)
         1
         0)
         
       (if (not= linear-vel 0)
           
         (if (> 0 linear-vel)
           1
           -1))))
       
   :rotation (direction->rotation (:velocity p))})

(defn user []
  (merge (active)
         {:user true
          :proposal-function user-proposals}))
