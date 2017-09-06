package prog03;

public class ConstantFib implements Fib {

	protected double sqrt5 = Math.sqrt(5);
	protected double g1 = (1 + sqrt5) / 2;
	protected double g2 = (1 - sqrt5) / 2;

	public double fib(int n) {
		return (Math.pow(g1, n) - Math.pow(g2, n)) / sqrt5;
	}

	public double o(int n) {
		return 1;
	}

}
