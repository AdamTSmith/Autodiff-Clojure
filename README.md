# Quick demo of Autodiff in Clojure

One approach to autodiff (automatic differentiation) is to extend real numbers x to include an extra part, x + y*D. This is like complex numbers with the rule I^2 = -1, except that here we use use the rule D^2 = 0 instead.

Mathematical fact: f(x+y\*D) = f(x) + y\*D\*f'(x)
where f' is the derivative of f. 

Extending maths functions to these numbers implements (forward style) autodiff. Autodiff costs only a constant factor in computation compared to evaluating the function, and is often much more convenient and efficient than using symbolic differentiation.

(Just an excuse to check out Clojure really. This is a Leiningen project created using the official Clojure Docker image, but you could just copy lines in *src/astest/core.clj* into the Clojure REPL.)


## Usage

Repl example, returns the function x^2 + 2\*x and derivative at 3.5

    => (dmrfunc 3.5)
    #astest.core.Dual{:r 19.25, :d 9.0}


Repl example, returns the function 2\*x\*(previous function) and derivative at 3.5

    => (dmrsfunc 3.5)
    #astest.core.Dual{:r 134.75, :d 101.5}


## License

GPL 3
