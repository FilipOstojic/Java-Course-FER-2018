package hr.fer.zemris.java.model;

/**
 * Razred <code>Poll</code> predstavlja implementaciju ankete. Sadrži njen id,
 * naslov te poruku.
 * 
 * @author Filip
 *
 */
public class Poll {
	/**
	 * ID.
	 */
	private String id;
	/**
	 * Naslov ankete.
	 */
	private String title;
	/**
	 * Poruka ankete.
	 */
	private String message;

	/**
	 * Konstruktor.
	 */
	public Poll() {
	}

	/**
	 * Vraća id ankete.
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Postavlja id ankete.
	 * 
	 * @param id
	 *            id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Vraća naslov ankete.
	 * 
	 * @return naslov
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Postavlja naslov ankete.
	 * 
	 * @param title
	 *            naslov
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Vraća poruku ankete.
	 * 
	 * @return poruka
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Postavlja poruku ankete.
	 * 
	 * @param message
	 *            poruka
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
