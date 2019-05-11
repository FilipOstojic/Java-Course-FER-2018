package irg;

import hr.fer.zemris.math.Complex;

public class Demo6 {

	public static void main(String[] args) {
		
		double xmin = 0;
		double ymin = 0;
		double xmax = 800;
		double ymax = 600;
		double umin = -2;
		double vmin = -1;
		double umax = 0.25;
		double vmax = 1;
		double xc = 227;
		double yc = 344;
		
		double u = ((xc - xmin) / (xmax - xmin)) * (umax-umin) + umin;
		double v = ((yc - ymin) / (ymax - ymin)) * (vmax-vmin) + vmin;
		
		Complex zc = new Complex(u, v);
		Complex zo = Complex.ZERO;
		Complex z;
		
		int eps = 246;
		int m = 8;
		
		z = zo;
		int counter = 0;
		
		while(counter < m) {
			z = z.power(2).add(zc);
			counter++;
			
			if(z.module() > eps) {
				break;
			}
		}
		System.out.println(z);
		System.out.println(z.module());
		System.out.println(counter);
	}
}
