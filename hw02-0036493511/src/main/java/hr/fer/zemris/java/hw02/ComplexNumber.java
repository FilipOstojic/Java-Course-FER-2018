package hr.fer.zemris.java.hw02;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.pow;
import static java.lang.Math.atan2;
import static java.lang.Math.abs;

/**
 * Razred <code>ComplexNumber</code> predstavlja kompleksni broj oblika a+b*i.
 * Kutovi se u radijanima.
 * @author Filip Ostojić
 *
 */
public class ComplexNumber {
	private double real;
	private double imaginary;
	
	/**
	 * Konstruktor koji stvara kompleksni broj.
	 * @param real realni dio 
	 * @param imaginary imaginarni dio
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Metoda stavara novi kompleksni broj.
	 * @param real realni dio
	 * @return kompleksni broj
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Metoda stavara novi kompleksni broj.
	 * @param imaginary imaginarni dio
	 * @return kompleksni broj
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	} 
	
	/**
	 * Metoda prima koordinate u polarnom zapisu kompleksnog broja. 
	 * @param magnitude udaljenost od ishodišta
	 * @param angle kut u radijanima
	 * @return kompleksni broj
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if (magnitude < 0) throw new IllegalArgumentException("Magnitude should be positive.");
		if (angle > 2*PI) {
			angle = angle%(2*PI);
		}
		double real = magnitude * cos(angle);
		double imaginary = magnitude * sin(angle);
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Metoda prima zapis kompleksnog broja kao String.
	 * @param string
	 * @return kompleksni broj
	 */
	public static ComplexNumber parse(String string) {
		String numberNew = string.replaceAll("\\s","");
		
		Pattern patternRealAndIm = Pattern.compile("([-]?[0-9]+\\.?[0-9]*)([-|+]+[0-9]*\\.?[0-9]*)[i$]+");
		Pattern patternReal = Pattern.compile("([-]?[0-9]+\\.?[0-9]*)$");
		Pattern patternIm = Pattern.compile("([-]?[0-9]+\\.?[0-9]*)[i$]");
		Pattern patternI = Pattern.compile("([-|+]?)[i$]");
		
		Matcher matcherRealAndIm = patternRealAndIm.matcher(numberNew);
		Matcher matcherReal = patternReal.matcher(numberNew);
		Matcher matcherIm = patternIm.matcher(numberNew);
		Matcher matcherI = patternI.matcher(numberNew);
		
		double real = 0, imaginary = 0;
		
		if (matcherRealAndIm.find()) {
			real = Double.parseDouble(matcherRealAndIm.group(1));
			if (matcherRealAndIm.group(2).equals("+")) {
				imaginary = 1;
			} else if (matcherRealAndIm.group(2).equals("-")) {
				imaginary = -1;
			} else {
				imaginary = Double.parseDouble(matcherRealAndIm.group(2));
			}
		} else if (matcherReal.find()) {
			real = Double.parseDouble(matcherReal.group(1));
			imaginary = 0;
		} else if (matcherIm.find()) {
			real = 0;
			imaginary = Double.parseDouble(matcherIm.group(1));
		} else if(matcherI.find()) {
			real = 0;
			if (matcherI.group(1).equals("-")) {
				imaginary = -1;
			} else {
				imaginary = 1;
			}
		} else {
			throw new IllegalArgumentException("No number can be made from this entry.");
		}
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Vraća realni dio kompleksnog broja.
	 * @return
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Vraća imaginarni dio kompleksnog broja.
	 * @return
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Izračunava udaljenost od središta do kompleksnog broja.
	 * @return
	 */
	public double getMagnitude() {
		return sqrt(pow(this.getReal(), 2) + pow(this.getImaginary(), 2));
	}
	
	/**
	 * Izračunava kut (u radijanima). Kut je kut između
	 * polupravca kroz ishodište u smjeru pozitivne x osi te polupravca kroz ishodište
	 * i kompleksni broj.
	 * @return kut u radijanima (double)
	 */
	public double getAngle() {
		double angle = atan2(this.getImaginary(), this.getReal());
		return angle; 		//dodat pi za 2 i 3 kvadrant, 2pi za 4 kv?? NE to je za kalk samo
	}
	
	/**
	 * Zbraja dva kompleksna broja.
	 * @param other
	 * @return kompleksni broj
	 */
	public ComplexNumber add(ComplexNumber other) {
		return new ComplexNumber(this.getReal()+other.getReal(), this.getImaginary()+other.getImaginary());
	}
	
	/**
	 * Oduzima dva kompleksna broja.
	 * @param other
	 * @return kompleksni broj
	 */
	public ComplexNumber sub(ComplexNumber other) {
		return new ComplexNumber(this.getReal()-other.getReal(), this.getImaginary()-other.getImaginary());
	}
	
	/**
	 * Množi dva kompleksna broja.
	 * @param other
	 * @return kompleksni broj
	 */
	public ComplexNumber mul(ComplexNumber other) {
		double realReal = this.getReal()*other.getReal();
		double realIm = this.getReal()*other.getImaginary();
		double imReal = this.getImaginary()*other.getReal();
		double imIm = this.getImaginary()*other.getImaginary();
		
		return new ComplexNumber(realReal-imIm, realIm+imReal);
	}
	
	/**
	 * Dijeli dva kompleksna broja.
	 * @param other
	 * @return kompleksni broj
	 */
	public ComplexNumber div(ComplexNumber other) {
		double denominator = pow(other.getReal(),2)+pow(other.getImaginary(),2);
		if ( denominator == 0) {
			throw new IllegalArgumentException("Dividing with zero is not allowed.");
		}
		double realPart = (double) ((this.getReal()*other.getReal()+this.getImaginary()*other.getImaginary())/denominator);
		double imPart = (double) ((this.getImaginary()*other.getReal()-this.getReal()*other.getImaginary())/denominator);
	
		return new ComplexNumber(realPart, imPart);
	}
	
	/**
	 * Potencira kompleksni broj. Potencija je pozitivni cijeli broj ili 0.
	 * @param n
	 * @return
	 */
	public ComplexNumber power(int n) {
		if(n<0) {
			throw new IllegalArgumentException("The required power must be positive or zero.");
		} else if(n==0) {
			return new ComplexNumber(0, 0);
		} else if(n==1) {
			return this;
		}
		ComplexNumber tmpComplex = new ComplexNumber(this.getReal(), this.getImaginary());
		ComplexNumber result = tmpComplex;
		for(int i = 1 ; i < n ; i++) {
			result=result.mul(tmpComplex);
		}
		return result;
	}
	
	/**
	 * Izračun korijena kompleksnog broja korištenjem De Moivreovog teorema.
	 * @param n broj željenog korijena (n>0)
	 * @return polje kopleksnih brojeva koji su korijeni početnog broja
	 */
	public ComplexNumber[] root(int n) {
		if(n<=0) {
			throw new IllegalArgumentException("The required root must be positive.");
		}
		ComplexNumber[] resultArray = new ComplexNumber[n];
		double magnitudeNew = pow(this.getMagnitude(), (double) 1.0/n);
		double realPart, imPart;
		
		for (int i = 0;  i < n; i++) {
			realPart = magnitudeNew * cos((double)(this.getAngle()+2*i*PI)/n);
			imPart = magnitudeNew * sin((double)(this.getAngle()+2*i*PI)/n);
			resultArray[i]= new ComplexNumber(realPart, imPart);
		}
		return resultArray;
	}
	
	@Override
	public String toString() {
		if (this.getReal()!=0 && this.getImaginary()!=0) {
			if(this.getImaginary()<0) {
				return String.format("%f-%fi", this.real, abs(this.getImaginary()));
			} else {
				return String.format("%f+%fi", this.real, this.getImaginary());
			}
		} else if(this.getReal()==0 && this.getImaginary()!=0) {
			return String.format("%fi", this.getImaginary());
		} else if(this.getReal()!=0 && this.getImaginary()==0) {
			return String.format("%f", this.getReal());
		} else {
			return "0";
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ComplexNumber)) return false;
		ComplexNumber other = (ComplexNumber) obj;
		if(this.getReal()!=other.getReal()) return false;
		if(this.getImaginary()!=other.getImaginary()) return false;
		return true;
	}
	
}
