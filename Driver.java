import java.io.File;

public class Driver {
	public static void main(String [] args) { 
		
		// set up new polynomials
		
		double [] c1 = {2,4,1};
		int [] e1 = {0,1,2};
		Polynomial p1 = new Polynomial(c1, e1);
		
		double [] c2 = {1,2,1,2};
		int [] e2 = {3,1,4,0};
		Polynomial p2 = new Polynomial(c2, e2);
		
		// check new file constructor
		
		System.out.println("Checking new file constructor...");
		File file = new File("testfile.txt");						// replace with your own file name
		Polynomial file_p = new Polynomial(file);
		for (int i = 0; i < file_p.coefficients.length; i++) {
			System.out.println(file_p.coefficients[i] + "x" + file_p.exponents[i]);
		}
		
		// check add and multiply
		
		System.out.println("Checking add or multiply method...");
		Polynomial s = p2.add(p1); 				// leave one commented when testing the other
		//Polynomial s = p2.multiply(p1);
		for (int i = 0; i < s.coefficients.length; i++) {
			System.out.println(s.coefficients[i] + "x" + s.exponents[i]);
		}
		
		// check evaluate
		
		System.out.println("Checking evaluate method...");
		System.out.println("p1(1.0) = " + p1.evaluate(1.0));
		
		// check hasRoot
		
		System.out.println("Checking hasRoot method...");
		double [] c3 = {6,1,-5};
		int [] e3 = {0,2,1};
		Polynomial p3 = new Polynomial(c3, e3);
		if(p3.hasRoot(1)) {
			System.out.println("1 is a root of p3");
		}
		else {
			System.out.println("1 is not a root of p3");
		}
		
		// check saveToFile
		
		System.out.println("Checking saveToFile method...");
		p3.saveToFile("testfile.txt");		// replace with your own file name
	
	}
}