import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String [] args) throws IOException {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,5,6};
        int [] e1 = {2,3,7};
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {-2,4,-9,6};
        int [] e2 = {1,2,3,7};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p2.add(p1);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        System.out.println("p2 + p1");

        s.print();

        System.out.println("\n\n\n");

        double [] c3 = {2,-2};
        int [] e3 = {0,2};

        Polynomial p3 = new Polynomial(c3, e3);

        if(p3.hasRoot(-1/2))
            System.out.println("-2 is a root of s\n\n");
        else
            System.out.println("-2 is not a root of s\n\n");

        Polynomial p4 = p3.multiply(p3);
        System.out.println("Squaring p3");
        p4.print();
        p3.saveToFile("write.txt");
        File poly_file = new File("file.txt");

        Polynomial p5 = new Polynomial(poly_file);
        p5.print();
    }
}