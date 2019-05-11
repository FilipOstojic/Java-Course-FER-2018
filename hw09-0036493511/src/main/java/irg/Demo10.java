package irg;

import hr.fer.zemris.java.raytracer.model.Point3D;

public class Demo10 {
	
	static int Ia = 103;
	static double ka = 0.92;
	static double kd = 0.13;
	static double ks = 0.05;
	static double n = 5;
	static int intensity = 103;

	public static void main(String[] args) {
		
		Point3D eye = new Point3D(-4.0, 8.0, -3.0);
		Point3D light = new Point3D(-5.0, 5.0, 7.0);
		
		Point3D v1 = new Point3D(-2.0, 9.0, -7.0);
		Point3D v2 = new Point3D(-4.0, -10.0, 9.0);
		Point3D v3 = new Point3D(-9.0, 1.0, -2.0);
		
		Point3D v12 = v2.sub(v1);
		Point3D v13 = v3.sub(v1);
		Point3D normal = v12.vectorProduct(v13).normalize();
		
		Point3D center = new Point3D((v1.x+v2.x+v3.x)/3, (v1.y+v2.y+v3.y)/3, (v1.z+v2.z+v3.z)/3);
		calculateColor(center, light, eye, normal);
	}
	
	private static void calculateColor(Point3D center, Point3D light, Point3D eye, Point3D normal) {
		
		Point3D l = light.sub(center).normalize();
		double theta = l.scalarProduct(normal);
		
		Point3D r = l.sub(normal.scalarMultiply(theta).scalarMultiply(2));
		Point3D v = eye.sub(center).normalize();
		
		double coefficient = Math.pow(r.scalarProduct(v), n);
		
		double amb = Ia * ka;
		double dif = intensity * kd * theta;
		double zrc = intensity * ks * coefficient;
		
		System.out.println(amb);
		System.out.println(dif);
		System.out.println(zrc);
		System.out.println(amb + dif + zrc);
	}
}
