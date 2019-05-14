;;;; Quick and simple demo of Autodiff (automatic differentiation)


(ns astest.core)

;;; Datatype for extended numbers
(defrecord Dual [r d])


;;; Extend maths functions to dual numbers (only a few examples done here).
;;; add
(defmulti addD (fn [a b] [(type a) (type b)]))
(defmethod addD [Double Double] ([a b] (+ a b)))
(defmethod addD [Dual Dual] ([a b] (Dual. (+ (:r a) (:r b)) (+ (:d a) (:d b)))))

;;; multiply
(defmulti mulD (fn [a b] [(type a) (type b)]))
(defmethod mulD [Double Double] ([a b] (+ a b)))
(defmethod mulD [Dual Dual] ([a b] (Dual. (* (:r a) (:r b)) (+ (* (:r a) (:d b)) (* (:r b) (:d a))))))

;;; a trig function
(defmulti sinD (fn [a] [(type a)]))
(defmethod sinD [Double] ([a] (Math/sin a)))
(defmethod sinD [Dual] ([a] (Dual. (Math/sin (:r a)) (Math/cos (:d a)))))

;;; a hyperbolic function
(defmulti coshD (fn [a] [(type a)]))
(defmethod coshD [Double] ([a] (Math/cosh a)))
(defmethod coshD [Dual] ([a] (Dual. (Math/cosh (:r a)) (Math/sinh (:d a)))))



;;; (1) To autodiff the function x^2 + 2*x at x, call it with (Dual. x 1) 
(defn mrfunc [x] (addD (mulD x x) (mulD (Dual. 2 0) x)))
;; Convenient to input ordinary number x, not a dual
(defn dmrfunc [x] (mrfunc (Dual. x 1)))

;;; Repl example (returns the function and derivative at 3.5)
;;; => (dmrfunc 3.5)
;;; #astest.core.Dual{:r 19.25, :d 9.0}


;;; (2) This works for any function using the overloaded maths:
;;; e.g. a composite function: 2*x*mrfunc(x)
(defn mrsfunc [x] (mulD (mulD (Dual. 2 0) x) (mrfunc x)))
(defn dmrsfunc [x] (mrsfunc (Dual. x 1)))

;;; Repl example (returns the function and derivative at 3.5)
;;; => (dmrsfunc 3.5)
;;; #astest.core.Dual{:r 134.75, :d 101.5}


;;; (3) an example using all the maths functions: 2*x*mrfunc(x) + sin(x) + cosh(x)
(defn allfunc [x] (addD (mulD (mulD (Dual. 2 0) x) (mrfunc x)) (addD (sinD x) (coshD x))))
(defn dallfunc [x] (allfunc (Dual. x 1)))

;;; Repl example (returns the function and derivative at 3.5)
;;; => (dallfunc 3.5)
;;; #astest.core.Dual{:r 150.9720414433677, :d 103.21550349951194}




