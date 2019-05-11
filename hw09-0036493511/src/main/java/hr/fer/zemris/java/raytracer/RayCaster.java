package hr.fer.zemris.java.raytracer;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Razred <code>RayCaster</code> predstavlja pojednostavljeni prikaz 3D scene
 * pomoću praćenja zrake. Ne čekuju se argumenti.
 * 
 * @author Filip
 *
 */
public class RayCaster {
	/**
	 * Varijabla koja označava prag tolerancije u usporedbi double vrijednosti.
	 */
	public static double treshold = 10E-6;

	/**
	 * Metoda se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Metoda stavara primjerak razreda <code>IRayTracerProducer</code>.
	 * 
	 * @return {@linkplain IRayTracerProducer}
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = getY(zAxis, viewUp);
				Point3D xAxis = getX(zAxis, yAxis);
				Point3D screenCorner = getCorner(view, horizontal, vertical, yAxis, xAxis);
				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = getSP(screenCorner, x, width, horizontal, xAxis, y, height, vertical,
								yAxis);
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				observer.acceptResult(red, green, blue, requestNo);
			}
			
			private Point3D getSP(Point3D screenCorner, int x, int width, double horizontal, Point3D xAxis, int y,
					int height, double vertical, Point3D yAxis) {
				return screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1)))
						.sub(yAxis.scalarMultiply(vertical * y / (height - 1)));
			}

			private Point3D getCorner(Point3D view, double horizontal, double vertical, Point3D yAxis, Point3D xAxis) {
				return view.sub(xAxis.scalarMultiply(horizontal / 2.0)).add(yAxis.scalarMultiply(vertical / 2.0));
			}

			private Point3D getX(Point3D zAxis, Point3D yAxis) {
				return zAxis.vectorProduct(yAxis).normalize();
			}

			private Point3D getY(Point3D zAxis, Point3D viewUp) {
				Point3D vuv = viewUp.normalize();
				return (vuv.sub(zAxis.scalarMultiply(zAxis.scalarProduct(vuv)))).normalize();
			}
		};
	}

	/**
	 * Metoda popunjava polje s 8-bitnim vrijednostima boja ako presjek postoji
	 * inače je crna boja.
	 * 
	 * @param scene
	 *            scena
	 * @param ray
	 *            zraka
	 * @param rgb
	 *            polje vrijednosti boja
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		Arrays.fill(rgb, (short) 0);
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null) {
			return;
		}
		determineColorFor(scene, ray, closest, rgb);

	}

	/**
	 * Metoda izračunava cijelokupno osvijetljenje objekta kao linearnu komibnaciju
	 * triju komponenati: ambijentalne, difuzne i zrcalne komponente. Svjetlosni
	 * izvori su točkasti te ih može biti više.
	 * 
	 * @param scene
	 *            scena
	 * @param ray
	 *            zraka
	 * @param closest
	 *            najbliža točka presjeka
	 * @param rgb
	 *            polje boja
	 */
	private static void determineColorFor(Scene scene, Ray ray, RayIntersection closest, short[] rgb) {
		Arrays.fill(rgb, (short) 15);
		
		List<LightSource> lightSources = scene.getLights();

		for (LightSource ls : lightSources) {
			Ray rNew = new Ray(ls.getPoint(), closest.getPoint().sub(ls.getPoint()).normalize());
			RayIntersection closestNew = findClosestIntersection(scene, rNew);
			double delta = ls.getPoint().sub(closestNew.getPoint()).norm()
							- ls.getPoint().sub(closest.getPoint()).norm();
			boolean less = Math.abs(delta) > treshold;
			if (closestNew != null && less) {
				continue;
			}
			Point3D l = ls.getPoint().sub(closest.getPoint()).normalize();
			Point3D n = closest.getNormal().normalize();
			double ln = l.scalarProduct(n) < 0 ? 0 : l.scalarProduct(n);

			Point3D v = ray.start.sub(closest.getPoint()).normalize();
			Point3D r = l.sub(n.scalarMultiply(2 * l.scalarProduct(n))).normalize();
			double rv = Math.pow(r.scalarProduct(v), closest.getKrn());

			rgb[0] += closest.getKdr() * ls.getR() * ln + closest.getKrr() * ls.getR() * rv;
			rgb[1] += closest.getKdg() * ls.getG() * ln + closest.getKrg() * ls.getR() * rv;
			rgb[2] += closest.getKdb() * ls.getB() * ln + closest.getKrb() * ls.getR() * rv;
		}
	}

	/**
	 * Metoda vraća onaj presjek zrake i predmeta čija je udaljenost od početka do
	 * sjecišta najmanja.
	 * 
	 * @param scene
	 *            scena
	 * @param ray
	 *            zraka
	 * @return presjek zrake s najkraćom udaljenosti {@linkplain RayIntersection}
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection intersection = null;
		for (GraphicalObject object : scene.getObjects()) {
			RayIntersection newIntersection = object.findClosestRayIntersection(ray);
			if (intersection == null) {
				intersection = newIntersection;
			} else if (newIntersection != null && newIntersection.getDistance() < intersection.getDistance()) {
				intersection = newIntersection;
			}
		}
		return intersection;
	}

}
