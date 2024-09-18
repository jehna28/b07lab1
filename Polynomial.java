public class Polynomial {

	double[] coefficients;
	
	public Polynomial() {
		coefficients = new double[1];
	}

	public Polynomial(double[] values) {
		int size = values.length;
		coefficients = new double[size];
		for (int i = 0; i < size; i++) {
			coefficients[i] = values[i];
		}
	}

	public Polynomial add(Polynomial p) {
		int p_size = p.coefficients.length;
		int og_size = coefficients.length;
		int new_size = Math.max(p_size, og_size);
		double[] sum_coefficients = new double[new_size];
		if (p_size >= og_size) {
			for (int i = 0; i < og_size; i++) {
				sum_coefficients[i] = coefficients[i] + p.coefficients[i];
			}
			for (int i = og_size; i < p_size; i++) {
				sum_coefficients[i] = p.coefficients[i];
			}
		}
		else {
			for (int i = 0; i < p_size; i++) {
				sum_coefficients[i] = p.coefficients[i] + coefficients[i];
			}
			for (int i = p_size; i < og_size; i++) {
				sum_coefficients[i] = coefficients[i];
			}
		}
		Polynomial result = new Polynomial(sum_coefficients);
		return result;
	}

	public double evaluate(double x) {
		int size = coefficients.length;
		double sum = 0;
		for (int i = 0; i < size; i++) {
			sum += coefficients[i]*(Math.pow(x,i));
		}
		return sum;
	}

	public boolean hasRoot(double x) {
		return (evaluate(x) == 0);	
	}
}