package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Razred <code>Newton</code> predstavlja vrstu fraktalnih slika: fraktali
 * izvedeni iz iteracije Newton-Raphson. Kroz argumentnu liniju se očekuje onos
 * barem dvije nultočke polinoma koji mogu biti kompleksni brojevi. Kompleksni
 * brojevi su oblika: a+ib.
 * 
 * @author Filip
 *
 */
public class Newton {

	/**
	 * Polinom koji nastaje nultočkama.
	 */
	private static ComplexRootedPolynomial polynom;
	/**
	 * Derivacija polinoma koji nastaje nultočkama.
	 */
	private static ComplexPolynomial derivate;
	/**
	 * Prag konvergencije.
	 */
	private static double convergenceThreshold = 1E-3;
	/**
	 * Prag za traženje najbližeg polinoma.
	 */
	private static double rootThreshold = 1E-3;

	/**
	 * Metoda se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Complex> roots = new ArrayList<>();
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		int counter = 0;
		while (true) {
			System.out.print("Root " + ++counter + "> ");
			String input = sc.nextLine().trim();
			if (input.equals("done")) {
				counter--;
				sc.close();
				break;
			}
			try {
				Complex c = parse(input);
				roots.add(c);
			} catch (Exception e) {
				System.out.println("Input can not be parsed: " + input);
				counter--;
				continue;
			}
		}
		if (counter < 2) {
			System.out.println("Expected at last 2 arguments. Program  will be terminated.");
			System.exit(1);
		}
		polynom = new ComplexRootedPolynomial(getArray(roots));
		derivate = polynom.toComplexPolynom().derive();
		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(new MyProducer());
	}

	/**
	 * Metoda pretvara listu kompleksnih brojeva u polje.
	 * 
	 * @param roots
	 *            lista
	 * @return polje
	 */
	private static Complex[] getArray(List<Complex> roots) {
		Complex[] rootsArray = new Complex[10];
		int i = 0;
		for (Complex c : roots) {
			rootsArray[i++] = c;
		}
		int len = 0;
		for (Complex c : rootsArray) {
			if (c != null) {
				len++;
			}
		}
		return Arrays.copyOf(rootsArray, len);
	}

	/**
	 * Razred <code>PosaoIzračuna</code> puni polje shortova s brojevima koji
	 * predstavlju 8-bitni zapis boje.
	 * 
	 * @author Filip
	 *
	 */
	public static class PosaoIzracuna implements Callable<Void> {
		/**
		 * Maksimalni realni dio.
		 */
		double reMin;
		/**
		 * Minimalni realni dio.
		 */
		double reMax;
		/**
		 * Minimalni imaginarni dio.
		 */
		double imMin;
		/**
		 * Maksimalni imaginarni dio.
		 */
		double imMax;
		/**
		 * Ukupna širina.
		 */
		int width;
		/**
		 * Ukupna visina.
		 */
		int height;
		/**
		 * Mininmalna visina do kojeg ide svaka traka.
		 */
		int yMin;
		/**
		 * Maksimalna visina do kojeg ide svaka traka.
		 */
		int yMax;
		/**
		 * Maksimalni dopušteni broj iteracija.
		 */
		int m;
		/**
		 * Polje koje čuva 8-bitne vrijednosti boja.
		 */
		short[] data;

		/**
		 * Konstruktor.
		 * 
		 * @param reMin
		 *            minimalnin realni dio
		 * @param reMax
		 *            maksimalni realni dio
		 * @param imMin
		 *            minimalni imaginarni dio
		 * @param imMax
		 *            maksimalni imaginarni dio
		 * @param width
		 *            širina
		 * @param height
		 *            visina
		 * @param yMin
		 *            minimalni y trake
		 * @param yMax
		 *            maksimalni y trake
		 * @param m
		 *            maksimalni broj iteracija
		 * @param data
		 *            polje 8-bitnih vrijednosti boja
		 */
		public PosaoIzracuna(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
		}

		@Override
		public Void call() {
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x <= width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1.) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);
					Complex zn1 = Complex.ZERO;
					double module = 0;
					int iters = 0;
					do {
						Complex numerator = polynom.apply(zn);
						Complex denominator = derivate.apply(zn);
						Complex fraction = numerator.div(denominator);
						zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;
						iters++;
					} while (module > convergenceThreshold && iters < m);
					int index = polynom.indexOfClosestRootFor(zn1, rootThreshold);
					data[y * width + x] = (index == -1) ? 0 : (short) (index + 1);
				}
			}
			return null;
		}
	}

	/**
	 * Razred <code>MyProducer</code> pomoću "bazena dretvi" (eng. ThreadPool) puni
	 * listu s poslovima koje dretve moraju napraviti.
	 * 
	 * @author Filip
	 *
	 */
	public static class MyProducer implements IFractalProducer {
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			final int noOfLines = 8 * Runtime.getRuntime().availableProcessors();
			int noOfYInLine = height / noOfLines;

			ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			List<Future<Void>> results = new ArrayList<>();

			for (int i = 0; i < noOfLines; i++) {
				int yMin = i * noOfYInLine;
				int yMax = (i + 1) * noOfYInLine - 1;
				if (i == noOfLines - 1) {
					yMax = height - 1;
				}
				PosaoIzracuna job = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data);
				results.add(pool.submit(job));
			}
			for (Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException ignorable) {
				}
			}
			pool.shutdown();
			observer.acceptResult(data, (short) (polynom.toComplexPolynom().order() + 1), requestNo);
		}
	}

	/**
	 * Metoda parsira ulazni string i pretvara ga u kompleksni broj. Očekivani zapis
	 * kompleksnog broja je: a+ib.
	 * 
	 * @param input
	 *            string koji treba parsirati
	 * @return kompleksni broj
	 */
	private static Complex parse(String input) {
		String numberNew = input.replaceAll("\\s", "");

		Pattern patternRealAndIm = Pattern.compile("(^[-]?[0-9]+\\.?[0-9]*)([-|+]+[i]?[0-9]*\\.?[0-9]*$)");
		Pattern patternReal = Pattern.compile("^([-|+]?[0-9]+\\.?[0-9]*$)");
		Pattern patternIm = Pattern.compile("^([-|+]?[i][0-9]+\\.?[0-9]*)$");
		Pattern patternI = Pattern.compile("^([-|+]?)[i$]$");

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
				if (matcherRealAndIm.group(2).replace("i", "").equals("-")) {
					imaginary = -1;
				} else if (matcherRealAndIm.group(2).replace("i", "").equals("+")) {
					imaginary = 1;
				} else {
					imaginary = Double.parseDouble(matcherRealAndIm.group(2).replace("i", ""));
				}
			}
		} else if (matcherReal.find()) {
			real = Double.parseDouble(matcherReal.group(1));
			imaginary = 0;
		} else if (matcherIm.find()) {
			real = 0;
			imaginary = Double.parseDouble(matcherIm.group(1).replaceAll("i", ""));
		} else if (matcherI.find()) {
			real = 0;
			if (matcherI.group(1).replaceAll("i", "").equals("-")) {
				imaginary = -1;
			} else {
				imaginary = 1;
			}
		} else {
			throw new IllegalArgumentException("No number can be made from this entry.");
		}
		return new Complex(real, imaginary);
	}
}
