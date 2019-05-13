;;;; Quick demo of simple Autodiff (automatic differentiation)


(ns astest.core)

;;; Datatype for extended numbers
(defrecord Dual [r d])


;;; Extend maths functions to duals (only 2 implemented here).
;;; add
(defmulti add (fn [a b] [(type a) (type b)]))
(defmethod add [Float Float] ([a b] (+ a b)))
(defmethod add [Dual Dual] ([a b] (Dual. (+ (:r a) (:r b)) (+ (:d a) (:d b)))))

;;; multiply
(defmulti mul (fn [a b] [(type a) (type b)]))
(defmethod mul [Float Float] ([a b] (+ a b)))
(defmethod mul [Dual Dual] ([a b] (Dual. (* (:r a) (:r b)) (+ (* (:r a) (:d b)) (* (:r b) (:d a))))))


;;; To autodiff the function x^2 + 2*x at x, call it with (Dual. x 1) 
(defn mrfunc [x] (add (mul x x) (mul (Dual. 2 0) x)))
;; Convenient to input ordinary number x, not a dual
(defn dmrfunc [x] (mrfunc (Dual. x 1)))

;;; Repl example (returns the function and derivative at 3.5)
;;; => (dmrfunc 3.5)
;;; #astest.core.Dual{:r 19.25, :d 9.0}


;;; This works for any function using the overloaded maths:
;;; e.g. a composite function: 2*x*mrfunc(x)
(defn mrsfunc [x] (mul (mul (Dual. 2 0) x) (mrfunc x)))
(defn dmrsfunc [x] (mrsfunc (Dual. x 1)))

;;; Repl example (returns the function and derivative at 3.5)
;;; => (dmrsfunc 3.5)
;;; #astest.core.Dual{:r 134.75, :d 101.5}





