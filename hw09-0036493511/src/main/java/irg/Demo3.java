package irg;

import hr.fer.zemris.math.Vector3;

public class Demo3 {

	public static void main(String[] args) {
		
		Vector3 vb = new Vector3(-0.14, 5.26, -0.08);
		Vector3 va = new Vector3(-7.41, -3.36, -4.14);
		Vector3 vab = vb.sub(va);
		System.out.println(vab);
		
		Vector3 vc = new Vector3(-5.17, 6.46, -2.89);
		Vector3 vac = vb.sub(vc);
		System.out.println(vac);
		
		Vector3 n = vab.cross(vac);
		System.out.println(n);
		
		Vector3 vk = new Vector3(-27.80, -3.47, 3.38);
		Vector3 vp = new Vector3(2.26, 4.66, -3.47);
		Vector3 vpk = vk.sub(vp);
		System.out.println(vpk);
		
		double norm = vpk.norm();
		Vector3 d = new Vector3(vpk.getX()/norm, vpk.getY()/norm, vpk.getZ()/norm);
		System.out.println(d);
		
		double lambda = -((n.dot(va) + 0.01307) / n.dot(d));
		System.out.println(lambda);
		
		System.out.println(d.scale(lambda) );
	}
}
