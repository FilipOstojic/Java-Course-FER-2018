package hr.fer.zemris.java.hw17;

import java.util.List;

/**
 * Razred predstavlja sliku. Svaka slika sadrži ime, opis i listu tagova koji ju
 * opisuju. Početna stanica galerije: <br>
 * <a href="http://localhost:8080/gallery/">visit gallery</a>
 * 
 * @author Filip
 *
 */
public class Picture {
	/**
	 * Ime slike.
	 */
	private String name;
	/**
	 * Opis slike.
	 */
	private String description;
	/**
	 * Lista tagova.
	 */
	private List<String> tags;

	/**
	 * Konstruktor.
	 * 
	 * @param name
	 *            ime slike
	 * @param description
	 *            opis slike
	 * @param tags
	 *            lista tagova
	 */
	public Picture(String name, String description, List<String> tags) {
		this.name = name;
		this.description = description;
		this.tags = tags;
	}

	/**
	 * Getter za ime.
	 * 
	 * @return ime
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter za ime.
	 * 
	 * @param name
	 *            ime
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter za opis.
	 * 
	 * @return opis
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter za opis.
	 * 
	 * @param description
	 *            opis
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter za listu tagova.
	 * 
	 * @return lista tagova
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * Setter za listu tagova.
	 * 
	 * @param tags
	 *            lista tagova
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
