package hr.fer.zemris.java.raytracer.model;

/**
 * Razred <code>Sphere</code> definira predstavlja kuglu u 3D sceni.
 * 
 * @author Filip
 *
 */
public class Sphere extends GraphicalObject {
	/**
	 * Točka središta kugle.
	 */
	private Point3D center;
	/**
	 * Radijus kugle.
	 */
	private double radius;
	/**
	 * Konstanta difuzne komponente za crvenu boju.
	 */
	private double kdr;
	/**
	 * Konstanta difuzne komponente za zelenu boju.
	 */
	private double kdg;
	/**
	 * Konstanta difuzne komponente za plavu boju.
	 */
	private double kdb;
	/**
	 * Konstanta refleksivne komponente za crvenu boju.
	 */
	private double krr;
	/**
	 * Konstanta refleksivne komponente za zelenu boju.
	 */
	private double krg;
	/**
	 * Konstanta refleksivne komponente za plavu boju.
	 */
	private double krb;
	/**
	 * Hrapavost podloge.
	 */
	private double krn;

	/**
	 * Konstruktor.
	 * 
	 * @param center
	 *            središte kugle
	 * @param radius
	 *            radijus kugle
	 * @param kdr
	 *            konstanta difuzne komponente za crvenu boju
	 * @param kdg
	 *            konstanta diifuzne komponente za zelenu boju
	 * @param kdb
	 *            konstanta difuzne komponente za plavu boju
	 * @param krr
	 *            konstanta refleksivne komponente za crvenu boju
	 * @param krg
	 *            konstanta refleksivne komponente za zelenu boju
	 * @param krb
	 *            konstanta refleksivne komponente za plavu boju
	 * @param krn
	 *            hrapavost
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * Metoda računa najbliži presjek zrake i kugle.
	 * 
	 * @return {@link RayIntersection}
	 */
	public RayIntersection findClosestRayIntersection(Ray ray) {
		double a = ray.direction.scalarProduct(ray.direction);
		double b = 2 * ray.direction.scalarProduct(ray.start.sub(center));
		double c = center.scalarProduct(center) + ray.start.scalarProduct(ray.start)
				- 2 * (center.scalarProduct(ray.start)) - radius * radius;
		double discriminant = b * b - 4 * a * c;
		double shortestDistance;
		Point3D realIntersection;
		if (discriminant < 0) {
			return null;
		} else if (discriminant > 0) {
			double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
			double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
			Point3D intersection1 = ray.start.add(ray.direction.scalarMultiply(t1));
			Point3D intersection2 = ray.start.add(ray.direction.scalarMultiply(t2));
			double distance1 = intersection1.sub(ray.start).norm();
			double distinct2 = intersection2.sub(ray.start).norm();
			shortestDistance = distance1 - distinct2 < 0 ? distance1 : distinct2;
			realIntersection = distance1 - distinct2 < 0 ? intersection1 : intersection2;
		} else {
			double t = -b / (2 * a);
			Point3D intersection = ray.start.add(ray.direction.scalarMultiply(t));
			shortestDistance = intersection.sub(ray.start).norm();
			realIntersection = ray.start.add(ray.direction.scalarMultiply(t));
		}
		return new RayIntersection(realIntersection, shortestDistance, true) {

			@Override
			public Point3D getNormal() {
				return realIntersection.sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

	/**
	 * Metoda vraća središte kugle.
	 * 
	 * @return center
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * Metoda vraća radijus kugle.
	 * 
	 * @return radijus
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Metoda vraća kdr koeficijent.
	 * 
	 * @return kdr
	 */
	public double getKdr() {
		return kdr;
	}

	/**
	 * Metoda vraća kdg koeficijent.
	 * 
	 * @return kdg
	 */
	public double getKdg() {
		return kdg;
	}

	/**
	 * Metoda vraća kdb koeficijent.
	 * 
	 * @return kdb
	 */
	public double getKdb() {
		return kdb;
	}

	/**
	 * Metoda vraća krr koeficijent.
	 * 
	 * @return krr
	 */
	public double getKrr() {
		return krr;
	}

	/**
	 * Metoda vraća krg koeficijent.
	 * 
	 * @return krg
	 */
	public double getKrg() {
		return krg;
	}

	/**
	 * Metoda vraća krb koeficijent.
	 * 
	 * @return krb
	 */
	public double getKrb() {
		return krb;
	}

	/**
	 * Metoda vraća krn koeficijent.
	 * 
	 * @return krn
	 */
	public double getKrn() {
		return krn;
	}
}