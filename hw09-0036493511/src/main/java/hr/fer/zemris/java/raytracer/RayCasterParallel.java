package hr.fer.zemris.java.raytracer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

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
 * Razred <code>RayCasterParallel</code> predstavlja pojednostavljeni prikaz 3D scene
 * pomoću praćenja zrake. Crtanje se izvodi višedretveno. Ne čekuju se argumenti.
 * 
 * @author Filip
 *
 */
public class RayCasterParallel {

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
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
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

				Point3D vuv = viewUp.normalize();
				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = vuv.sub(zAxis.scalarMultiply(zAxis.scalarProduct(vuv))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2.0))
						.add(yAxis.scalarMultiply(vertical / 2.0));
				Scene scene = RayTracerViewer.createPredefinedScene();

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new PosaoIzracuna(width, height, eye, screenCorner, horizontal, vertical, xAxis, yAxis,
						scene, red, green, blue, 0, height));
				pool.shutdown();
				observer.acceptResult(red, green, blue, requestNo);
			}
		};
	}

	/**
	 * 
	 * @author Filip
	 *
	 */
	public static class PosaoIzracuna extends RecursiveAction {
		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Veličina trake.
		 */
		private static int SIZE = 16;

		/**
		 * Širina.
		 */
		private int width;

		/**
		 * Visina.
		 */
		private int height;

		/**
		 * Očište.
		 */
		private Point3D eye;

		/**
		 * Gornja ljeva točka scene.
		 */
		private Point3D screenCorner;

		/**
		 * Veličina horizontalne komponente.
		 */
		private double horizontal;

		/**
		 * Veličina vertikalne komponente.
		 */
		private double vertical;

		/**
		 * Jedinični vektor u smjeru apscise.
		 */
		private Point3D xAxis;

		/**
		 * Jedinični vektor u smjeru ordinate.
		 */
		private Point3D yAxis;

		/**
		 * Scena.
		 */
		private Scene scene;

		/**
		 * Polje crvenih boja.
		 */
		short[] red;

		/**
		 * Polje zelenih boja.
		 */
		short[] green;

		/**
		 * Polje plavih boja.
		 */
		short[] blue;

		/**
		 * Minimalna visina trake.
		 */
		int yMin;

		/**
		 * Maksimalna visina trake.
		 */
		int yMax;

		/**
		 * Konstruktor.
		 * 
		 * @param width
		 *            širina
		 * @param height
		 *            visina
		 * @param eye
		 *            očište
		 * @param screenCorner
		 *            točka u gornjem ljevom kutu scene
		 * @param horizontal
		 *            horizontalna velličina
		 * @param vertical
		 *            vertikalna veličina
		 * @param xAxis
		 *            vektor i
		 * @param yAxis
		 *            vektor j
		 * @param scene
		 *            scena
		 * @param red
		 *            polje crvenih boja
		 * @param green
		 *            polje zelenih boja
		 * @param blue
		 *            polje plavih boja
		 * @param yMin
		 *            minimalna visina trake
		 * @param yMax
		 *            maksimalna visina trake
		 */
		public PosaoIzracuna(int width, int height, Point3D eye, Point3D screenCorner, double horizontal,
				double vertical, Point3D xAxis, Point3D yAxis, Scene scene, short[] red, short[] green, short[] blue,
				int yMin, int yMax) {
			this.width = width;
			this.height = height;
			this.eye = eye;
			this.screenCorner = screenCorner;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.yAxis = yAxis;
			this.xAxis = xAxis;
			this.scene = scene;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.yMin = yMin;
			this.yMax = yMax;
		}

		@Override
		protected void compute() {
			if ((yMax - yMin + 1) <= SIZE) {
				computeDirect();
				return;
			}
			invokeAll(
					new PosaoIzracuna(width, height, eye, screenCorner, horizontal, vertical, xAxis, yAxis, scene, red,
							green, blue, yMin, (yMin + yMax) / 2),
					new PosaoIzracuna(width, height, eye, screenCorner, horizontal, vertical, xAxis, yAxis, scene, red,
							green, blue, (yMin + yMax) / 2, yMax));
		}

		/**
		 * Metoda koja se zove kada je uvijet broja dretvi zadovoljen, tj kada je visina
		 * trake dovoljno mala.
		 */
		public void computeDirect() {
			short[] rgb = new short[3];
			int offset = yMin * width;
			for (int y = yMin; y < yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x / (width - 1.0) * horizontal))
							.sub(yAxis.scalarMultiply(y / (height - 1.0) * vertical));
					Ray ray = Ray.fromPoints(eye, screenPoint);
					tracer(scene, ray, rgb);
					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					offset++;
				}
			}
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

}
