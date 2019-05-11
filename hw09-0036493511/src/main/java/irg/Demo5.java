package irg;

import hr.fer.zemris.math.Vector3;

public class Demo5 {

	public static void main(String[] args) {
		
		double r = 4.;
		Vector3 s = new Vector3(3.0, 3.0, 5.0);
		Vector3 v1 = new Vector3(3.0, 1.0, 5.0);
		Vector3 v2 = new Vector3(-1.0, -1.0, 8.0);
		
		Vector3 d = v2.sub(v1).normalized();
		
		double b = 2 * d.dot(v1.sub(s));
		double c = (v1.sub(s)).dot(v1.sub(s)) - r*r;
		
		double lambda1 = (-b + Math.sqrt(b*b - 4 * c)) / 2;
		double lambda2 = (-b - Math.sqrt(b*b - 4 * c)) / 2;
		
		System.out.println(lambda1);
		System.out.println(lambda2);
		
		System.out.println(v1.add(d.scale(lambda1)));
		System.out.println(v1.add(d.scale(lambda2)));
	}
}
