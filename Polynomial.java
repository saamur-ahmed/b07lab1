public class Polynomial{
	
	public double[] coefficients;
	
	public Polynomial() {
		coefficients = new double[1];
		coefficients[0]=0;
	}
	
	public Polynomial(double[] coefficients) {
		this.coefficients = coefficients;	
	}
	
	public Polynomial add(Polynomial other) {
		int maxlength = Math.max(this.coefficients.length, other.coefficients.length);
		double[] result = new double[maxlength];
		
		for (int i = 0; i < maxlength; i++) {
			double one;
			double two;
			
			if (i < this.coefficients.length) {
				one = this.coefficients[i];	
			}
			else {
				one = 0;
			}
			
			if (i < other.coefficients.length) {
				two = other.coefficients[i];
			}
			else {
				two = 0;
			}
			
			
			result[i] = one + two;
		}
		
		return new Polynomial(result);
	}
	
	public double evaluate(double x) {
		double result = 0;
		double currentpower = 1;
		
		for (int i = 0; i < coefficients.length; i++) {
			result = result + (coefficients[i] * currentpower);
			currentpower = currentpower * x;
			
		}
		return result;
		
	}
	
	public boolean hasRoot(double x) {
		double value = evaluate(x);
		return value == 0;
	}
	
	
}