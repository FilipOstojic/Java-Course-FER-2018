package irg;

import hr.fer.zemris.math.Vector3;

public class Demo8 {

	public static void main(String[] args) {
		
		int counter = 1;
		
		Vector3 v1 = new Vector3(-4 ,-9, -4);
		Vector3 v2 = new Vector3(6, -6, -4);
		Vector3 v3 = new Vector3(-1 ,1 ,-4);
		Vector3 v4 = new Vector3(9, 4 ,-4);
		Vector3 v5 = new Vector3(2 ,-3 , 2);
		Vector3 v6 = new Vector3(2 ,-3 ,-9);
		
		Vector3 t = new Vector3(-4, -2, -2);
		
		
		Vector3 v15 = v5.sub(v1);
		Vector3 v12 = v2.sub(v1);
		Vector3 n8 = v12.cross(v15);
		Vector3 v152 = new Vector3((v1.getX()+v5.getX()+v2.getX())/3, (v1.getY()+v5.getY()+v2.getY())/3, (v1.getZ()+v5.getZ()+v2.getZ())/3);
		double d8 = -n8.dot(v152);
		System.out.println(counter++ + ":");
		System.out.println(n8.dot(t) + d8);
		
		Vector3 v35 = v5.sub(v3);
		Vector3 v31 = v1.sub(v3);
		Vector3 n7 = v31.cross(v35);
		Vector3 v351 = new Vector3((v3.getX()+v5.getX()+v1.getX())/3, (v3.getY()+v5.getY()+v1.getY())/3, (v3.getZ()+v5.getZ()+v1.getZ())/3);
		double d7 = -n7.dot(v351);
		System.out.println(counter++ + ":");
		System.out.println(n7.dot(t) + d7);
		
		Vector3 v45 = v5.sub(v4);
		Vector3 v43 = v3.sub(v4);
		Vector3 n6 = v43.cross(v45);
		Vector3 v453 = new Vector3((v4.getX()+v5.getX()+v3.getX())/3, (v4.getY()+v5.getY()+v3.getY())/3, (v4.getZ()+v5.getZ()+v3.getZ())/3);
		double d6 = -n6.dot(v453);
		System.out.println(counter++ + ":");
		System.out.println(n6.dot(t) + d6);
		
		Vector3 v25 = v5.sub(v2);
		Vector3 v24 = v4.sub(v2);
		Vector3 n5 = v24.cross(v25);
		Vector3 v254 = new Vector3((v2.getX()+v5.getX()+v4.getX())/3, (v2.getY()+v5.getY()+v4.getY())/3, (v2.getZ()+v5.getZ()+v4.getZ())/3);
		double d5 = -n5.dot(v254);
		System.out.println(counter++ + ":");
		System.out.println(n5.dot(t) + d5);
		
		Vector3 v61 = v1.sub(v6);
		Vector3 v62 = v2.sub(v6);
		Vector3 n4 = v62.cross(v61);
		Vector3 v612 = new Vector3((v6.getX()+v1.getX()+v2.getX())/3, (v6.getY()+v1.getY()+v2.getY())/3, (v6.getZ()+v1.getZ()+v2.getZ())/3);
		double d4 = -n4.dot(v612);
		System.out.println(counter++ + ":");
		System.out.println(n4.dot(t) + d4);
		
		Vector3 v63 = v3.sub(v6);
		Vector3 n3 = v61.cross(v63);
		Vector3 v613 = new Vector3((v6.getX()+v1.getX()+v3.getX())/3, (v6.getY()+v1.getY()+v3.getY())/3, (v6.getZ()+v1.getZ()+v3.getZ())/3);
		double d3 = -n3.dot(v613);
		System.out.println(counter++ + ":");
		System.out.println(n3.dot(t) + d3);
		
		Vector3 v64 = v4.sub(v6);
		Vector3 n2 = v63.cross(v64);
		Vector3 v643 = new Vector3((v6.getX()+v4.getX()+v3.getX())/3, (v6.getY()+v4.getY()+v3.getY())/3, (v6.getZ()+v4.getZ()+v3.getZ())/3);
		double d2 = -n2.dot(v643);
		System.out.println(counter++ + ":");
		System.out.println(n2.dot(t) + d2);
		
		Vector3 n1 = v64.cross(v62);
		Vector3 v642 = new Vector3((v6.getX()+v4.getX()+v2.getX())/3, (v6.getY()+v4.getY()+v2.getY())/3, (v6.getZ()+v4.getZ()+v2.getZ())/3);
		double d1 = -n1.dot(v642);
		System.out.println(counter++ + ":");
		System.out.println(n1.dot(t) + d1);
		
		/*Vector3 vab = vb.sub(va);
		Vector3 vac = vc.sub(va);
		Vector3 n1 = vab.cross(vac);
		double d1 = -n1.dot(va);
		System.out.println(n1.dot(o) + d1);
		
		Vector3 vbd = vd.sub(vb);
		Vector3 vbc = vc.sub(vb);
		Vector3 n2 = vbd.cross(vbc);
		double d2 = -n2.dot(vb);
		System.out.println(n2.dot(o) + d2);
		
		Vector3 vcd = vd.sub(vc);
		Vector3 vca = va.sub(vc);
		Vector3 n3 = vcd.cross(vca);
		double d3 = -n3.dot(vc);
		System.out.println(n3.dot(o) + d3);
		
		Vector3 vad = vd.sub(va);
//		Vector3 vab = vb.sub(va);
		Vector3 n4 = vad.cross(vab);
		double d4 = -n4.dot(vd);
		System.out.println(n4.dot(o) + d4);*/
	}
}
