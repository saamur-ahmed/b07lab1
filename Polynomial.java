public class Polynomial {
    public double[] coefficients;  

   
    public Polynomial() {
        this.coefficients = new double[]{0.0};
    }

    
    public Polynomial(double[] coefficients) {
        if (coefficients == null || coefficients.length == 0) {
            this.coefficients = new double[]{0.0};
        } else {
            this.coefficients = coefficients.clone();
        }
    }

    
    public Polynomial add(Polynomial other) {
        int maxLength = Math.max(this.coefficients.length, other.coefficients.length);
        double[] result = new double[maxLength];

        for (int i = 0; i < maxLength; i++) {
            double a = (i < this.coefficients.length) ? this.coefficients[i] : 0.0;
            double b = (i < other.coefficients.length) ? other.coefficients[i] : 0.0;
            result[i] = a + b;
        }

        return new Polynomial(result);
    }

    
    public double evaluate(double x) {
        double value = 0.0;
        double power = 1.0; // x^0 initially

        for (double coefficient : coefficients) {
            value += coefficient * power;
            power *= x;
        }
        return value;
    }

   
    public boolean hasRoot(double x) {
        return Math.abs(evaluate(x)) < 1e-9; 
    }

    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = coefficients.length - 1; i >= 0; i--) {
            double c = coefficients[i];
            if (Math.abs(c) < 1e-9) continue;
            if (sb.length() > 0) sb.append(c >= 0 ? " + " : " - ");
            if (i == 0) sb.append(Math.abs(c));
            else if (i == 1) sb.append(Math.abs(c)).append("x");
            else sb.append(Math.abs(c)).append("x^").append(i);
        }
        return sb.length() > 0 ? sb.toString() : "0";
    }

    // Small test
    public static void main(String[] args) {
        Polynomial p = new Polynomial(new double[]{6, -2, 0, 5}); // 6 - 2x + 5x^3
        System.out.println("p(x) = " + p);
        System.out.println("p(-1) = " + p.evaluate(-1)); // Expected 3
        System.out.println("Has root at x=1? " + p.hasRoot(1));

        Polynomial q = new Polynomial(new double[]{1, 1}); // 1 + x
        Polynomial r = p.add(q);
        System.out.println("p(x) + q(x) = " + r);
    }
}