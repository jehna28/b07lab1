import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;

public class Polynomial {

	double[] coefficients;
	int[] exponents;
	
	public Polynomial() {
		coefficients = new double[1];		
		exponents = new int[1];
	}

	public Polynomial(double[] coefficients, int[] exponents) {
		int size = coefficients.length;
		this.coefficients = new double[size];
		this.exponents = new int[size];
		for (int i = 0; i < size; i++) {
			this.coefficients[i] = coefficients[i];
			this.exponents[i] = exponents[i];
		}
	}
	
	public Polynomial(File src) {
		try {
			// reading file and placing terms in array
			Scanner input = new Scanner(src);
			String input_line = input.nextLine();
			String poly_line = input_line.replace("-","+-");
			input.close();
			String[] terms = poly_line.split("\\+");		
			int size = terms.length;
			
			coefficients = new double[size];
			exponents = new int[size];
			
			// saving coef and exp in respective arrays
			for (int i = 0; i < size; i++) {
				if (terms[i].contains("x")) {
					String[] split_term = terms[i].split("x");
					coefficients[i] = Double.parseDouble(split_term[0]);
					exponents[i] = Integer.parseInt(split_term[1]);		
				}
				else {
					coefficients[i] = Double.parseDouble(terms[i]);
					exponents[i] = 0;
				}
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("Uh oh! Something went wrong.");
			e.printStackTrace();
		}
	}
	
	// helper function that returns index of x in array, -1 if x is not found
	public int getIndex(int[] array, int x) {
		int size = array.length;
		for (int i = 0; i < size; i++) {
			if (array[i] == x) {
				return i;
			}
		}
		return -1;
	}
 
	public Polynomial add(Polynomial p) {
		int p_size = p.coefficients.length;
		int q_size = coefficients.length;
		int new_size = p_size + q_size;
		
		double[] sum_coefficients = new double[new_size];
		int[] sum_exponents = new int[new_size];
		
		// copy polynomial p to result
		for (int i = 0; i < p_size; i++) {
			sum_coefficients[i] = p.coefficients[i];
			sum_exponents[i] = p.exponents[i];
		}
		
		// add q to p
		for (int i = 0; i < q_size; i++) {
			int exp_index = getIndex(sum_exponents, exponents[i]);
			if (exp_index != -1) {
				sum_coefficients[exp_index] += coefficients[i];
			}
			else {
				sum_coefficients[p_size + i] = coefficients[i];
				sum_exponents[p_size + i] = exponents[i];
			}
		}
		
		// now get rid of extra 0s
		int final_size = 0;
		for (int i = 0; i < new_size; i++) {
			if (sum_coefficients[i] != 0) {
				final_size++;
			}
		}
		double[] final_coefficients = new double[final_size];
		int[] final_exponents = new int[final_size];
		int j = 0;
		for (int i = 0; i < new_size; i++) {
			if (sum_coefficients[i] != 0) {
				final_coefficients[j] = sum_coefficients[i];
				final_exponents[j] = sum_exponents[i];
				j++;
			}
		}
			
		Polynomial final_poly = new Polynomial(final_coefficients, final_exponents); 
		return final_poly; 
	} 

	public double evaluate(double x) {
		int size = coefficients.length;
		double sum = 0;
		for (int i = 0; i < size; i++) {
			sum += coefficients[i]*(Math.pow(x,exponents[i]));
		}
		return sum;
	}

	public boolean hasRoot(double x) {
		return (evaluate(x) == 0);	
	}
	
	
	// helper function to remove redundant exponents with help of add function
	public Polynomial removeRedundancy(Polynomial p) {
		Polynomial helper = new Polynomial();
		Polynomial result = p.add(helper);
		return result;
	}
	
	public Polynomial multiply(Polynomial p) {
		int p_size = p.coefficients.length;
		int q_size = coefficients.length;
		int new_size = p_size * q_size;
		
		double[] new_coefficients = new double[new_size];
		int[] new_exponents = new int[new_size];
		
		// simulate distributing the terms
		int k = 0;
		for (int i = 0; i < p_size; i++) {
			double coef = p.coefficients[i];
			int exp = p.exponents[i];
			for (int j = 0; j < q_size; j++) {
				new_coefficients[k] = coefficients[j] * coef;
				new_exponents[k] = exponents[j] + exp;
				k++;
			}
		}
		
		// now remove redundant exponents
		Polynomial new_poly = new Polynomial(new_coefficients, new_exponents);
		Polynomial final_poly = removeRedundancy(new_poly);
		return final_poly;
	}
	
	public void saveToFile(String file_name) {
		try {
			FileWriter output = new FileWriter(file_name);
			int size = coefficients.length;
			
			// accumulate characters from polynomial into a string
			String line = "";
			int num_plus = 0;
			for (int i = 0; i < size; i++) {
				line = line + String.valueOf(coefficients[i]);
				if (exponents[i] != 0) {
					line = line + "x" + String.valueOf(exponents[i]);
				}
				if (num_plus < size - 1) {
					line = line + "+";
					num_plus++;
				}
			}
			line = line.replace("+-","-");
			output.write(line);
			output.close();
		}
		catch(IOException e) {
			System.out.println("Uh oh! Something went wrong.");
			e.printStackTrace();
		}
	}
}