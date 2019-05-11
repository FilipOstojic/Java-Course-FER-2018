package hr.fer.zemris.java.hw17;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

/**
 * Pomoćni razred koji sadrži sve metode vezerane za obradu slike,
 * incijalizaciju tagova, stvaranje thumbnails-a, učitavanje te pohranu slike.
 * 
 * @author Filip
 *
 */
public class PictureUtil {
	/**
	 * Zastavica, označava je li stvoren folder thumbnails.
	 */
	public static boolean thumbFolderCreated;
	/**
	 * Lista slika.
	 */
	public static List<Picture> pictures = new ArrayList<>();
	/**
	 * Skup tagova.
	 */
	public static Set<String> tags = new HashSet<>();
	/**
	 * Putanja do datoteke WEB-INF.
	 */
	public static String contextPath;

	/**
	 * Metoda vraća sve tagove ne ponavaljajući jednake.
	 * 
	 * @return skup tagova
	 */
	public static Set<String> getTags() {
		if (tags != null && tags.size() != 0)
			return tags;
		if (pictures == null || pictures.size() == 0)
			initPictures();

		for (Picture pic : pictures) {
			for (String tag : pic.getTags()) {
				tags.add(tag);
			}
		}
		return tags;
	}

	/**
	 * Stvara (inicijalizira) listu svih slika, učitavanjem iz datoteke.
	 */
	public static void initPictures() {
		try {
			Path path = Paths.get(contextPath).resolve("opisnik.txt");
			List<String> lines = Files.readAllLines(path);
			for (int i = 0; i < lines.size(); i += 3) {
				String name = lines.get(i);
				String description = lines.get(i + 1);
				String[] tags = lines.get(i + 2).split(",");
				pictures.add(new Picture(name, description, Arrays.asList(tags)));
			}
		} catch (IOException e) {
		}
	}

	/**
	 * Vraća filtrirane slike, tj one slike koje imaju uneseni tag u svojoj listi
	 * tagova.
	 * 
	 * @param tag
	 *            tag
	 * @return lista slika
	 */
	public static List<Picture> getFilteredPictures(String tag) {
		List<Picture> filtered = new ArrayList<>();
		if (pictures == null || pictures.size() == 0)
			initPictures();
		for (Picture pic : pictures) {
			if (pic.getTags().contains(tag)) {
				filtered.add(pic);
			}
		}
		return filtered;
	}

	/**
	 * Inicijalno stvara folder thumbnails.
	 * 
	 * @param path
	 *            putanja do datoteke thumbnails
	 */
	public static void createThumbsFolder(String path) {
		if (thumbFolderCreated)
			return;
		File thumbs = new File(path);
		if (!thumbs.exists()) {
			thumbs.mkdir();
		}
	}

	/**
	 * Metoda prima orginalnu sliku te dimenzije nove umanjene slike.
	 * 
	 * @param img
	 *            orginalna slika
	 * @param height
	 *            visina nove slike
	 * @param width
	 *            širina nove slike
	 * @return umanjena slika (thumbnail)
	 */
	public static BufferedImage resize(Image img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	/**
	 * Učitava sliku sa zadane putanje.
	 * 
	 * @param filePath
	 *            putanja do slike
	 * @return slika
	 */
	public static BufferedImage loadImage(String filePath) {
		try {
			return ImageIO.read(Paths.get(filePath).toUri().toURL());
		} catch (Exception e) {
			return null;
		}
	}
}
