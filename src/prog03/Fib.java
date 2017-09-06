package prog03;

public interface Fib {
	/** The Fibonacci number generator 0, 1, 1, 2, 3, 5, ...
	@param n index
	@return nth Fibonacci number
	 */
	double fib (int n);

	/** The order O() of the implementation.
	@param n index
	@return the function of n inside the O()
	 */
	double o (int n);
}

/*
a = 1;
b = 1;
for(int i = 0; i < n; i++){
	c = a + b;
	a = b;
	b = c;
}
return c;

f(n) = O(n) = c * n;
O(5) = 5
O(10) = 10


desired accuracy 3.00 +- 0.001

Error <= time/1000

1 / sqrt(n)
1 / sqrt(9)
1 / 3

1 / sqrt(n) <= time / 1000
pow(1000, 2) / pow(time, 2) <= n 

n = number of trials

g(n) = O(pow(n, 2)) ==> c * pow(n, 2)
f(n) = O(n) ==> c * n





*/