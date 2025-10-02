import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Arrays;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Arrays;
import java.io.BufferedReader;


public class Polynomial{

    double [] coeff;
	int [] expo;
    
    public Polynomial() {

        coeff = new double[]{0.0};
		expo = new int[]{0};
    }


    public Polynomial(double [] arr_coeff, int [] arr_expo){
        coeff = arr_coeff.clone();
		expo = arr_expo.clone();
        
    }

	public Polynomial(File file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
        String poly = reader.readLine();
        reader.close();

		String [] poly_arr = poly.split("(?=[+-])");
		
		int len = poly_arr.length;
		coeff = new double[len];
		expo = new int[len];

		for (int j = 0; j < poly_arr.length; j++) {
			double coefficent;
			int exponent;


			String substrings = poly_arr[j];

			if (substrings.contains("x")){
				int index_of_x = substrings.indexOf('x');

				String coeffString = substrings.substring(0, index_of_x);

				if (coeffString.isEmpty() || coeffString.equals("+")) {
					coefficent = 1;
				} 
				else if (coeffString.equals("-")) {
					coefficent = -1;
				} 
				else if (coeffString.charAt(0) == '+')
					coefficent = Double.parseDouble(coeffString.substring(1));
				else
					coefficent = Double.parseDouble(coeffString);
				

				if (index_of_x == substrings.length() - 1) {
					exponent = 1; 
                } else {
                    exponent = Integer.parseInt(substrings.substring(index_of_x + 1));
                }
            } else {

				if (substrings.charAt(0) == '+')
                	coefficent = Double.parseDouble(substrings.substring(1));
				else
					coefficent = Double.parseDouble(substrings);

                exponent = 0;
            }

			
            coeff[j] = coefficent;
            expo[j] = exponent;

		}
	}

    public Polynomial add(Polynomial poly){
        int len = 0;
		
		for (int i = 0; i < coeff.length; i++){
			for (int j = 0; j < poly.coeff.length; j++){
				if (expo[i]==poly.expo[j])
					len++;
			}
			
		}
		
		int max_len = coeff.length + poly.expo.length - len;
		int found = 0;
		int [] final_expo = new int[max_len];
		double [] final_coeff = new double[max_len];
		int f = 0;
		for (int i = 0; i < expo.length; i++){
			found = 0;
			for (int j = 0; j < poly.expo.length; j++){
				if (expo[i] == poly.expo[j]){
					final_expo[f] = expo[i];
					final_coeff[f] = coeff[i] + poly.coeff[j];
					found = 1;
					break;
				}
				
			}
			if (found == 0){
				final_expo[f] = expo[i];
				final_coeff[f] = coeff[i];
				
			}

			f++;
        }
		for (int i = 0; i < poly.expo.length; i++){
			found = 0;
			for (int j = 0; j < f; j++){
				if (poly.expo[i] == final_expo[j]){
					found = 1;
					break;
				}
			}
			if (found == 0){
				final_expo[f] = poly.expo[i];
				final_coeff[f] = poly.coeff[i];
				f++;				
			}
		}
		
		int swapped;
		int tempExpo; 
		double tempCoeff;		

        for (int i = 0; i < max_len; i++) {
            swapped = 0;
            for (int j = 0; j < max_len - i - 1; j++) {
                if (final_expo[j] > final_expo[j + 1]) {
                    
                    tempExpo = final_expo[j];
                    final_expo[j] = final_expo[j + 1];
                    final_expo[j + 1] = tempExpo;
					
					tempCoeff = final_coeff[j];
					final_coeff[j] = final_coeff[j + 1];
                    final_coeff[j + 1] = tempCoeff;
                    swapped = 1;
                }
            }


            if (swapped == 0)
                break;
		}	
        Polynomial new_poly = new Polynomial(final_coeff, final_expo);
        return new_poly;
    }

    public double evaluate(double x){
        double res = 0;

        for (int i = 0; i < coeff.length; i++){
            res += coeff[i]* Math.pow(x,expo[i]);
        }

        return res;

    }

    public boolean hasRoot(double x){
        return 0.0 == evaluate(x);

    }

	public void print(){
		for (int i = 0; i < expo.length ;i++){
			System.out.printf("Expo: %d and Coeff: %f\n", expo[i], coeff[i]);

		}


	}

    public Polynomial multiply(Polynomial poly){
		Polynomial final_poly = null;
		
		for (int i=0; i<expo.length;i++){
			Polynomial temp = new Polynomial();

			if (expo[i]!=0)
				temp.expo  = multi_expo(poly.expo, expo[i]);
			else
				temp.expo  = poly.expo;

			
			temp.coeff = add_coeff(poly.coeff, coeff[i]);
			
			if (final_poly == null) {
				final_poly = temp;
			} else {
				final_poly = final_poly.add(temp);
        }
		}


		return final_poly;
	
	}

	public int [] multi_expo(int [] arr, int exponent){
		int[] new_arr = new int[arr.length];
		for (int i = 0; i < arr.length; i++){
			new_arr[i] = arr[i] + exponent;
		}
		return new_arr;
	}

	public double [] add_coeff(double [] arr, double coefficent){
		double[] new_arr = new double[arr.length];
		for (int i = 0; i < arr.length; i++){
			new_arr[i] = arr[i]* coefficent;
		}
		return new_arr;

	}

	public void saveToFile(String filePath) throws IOException{
		String polyString = "";

		for (int i=0; i <expo.length; i++){
			if (expo[i] == 0)
				if (coeff[i] < 0)
					polyString = polyString + coeff[i];
				else
					polyString = polyString + "+"+ coeff[i];
			else
				if (coeff[i] < 0)
					polyString = polyString + coeff[i] + "x" + expo[i];
				else
					polyString = polyString + "+" + coeff[i] + "x" + expo[i];
		}

		if (polyString.charAt(0)=='+'){
			polyString = polyString.substring(1) ;
		}
		FileWriter Writer = new FileWriter(filePath);
		Writer.write(polyString);
     	Writer.close();

	}
}
