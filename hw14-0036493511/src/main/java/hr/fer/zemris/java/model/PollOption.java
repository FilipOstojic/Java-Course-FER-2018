package hr.fer.zemris.java.model;

/**
 * Razred <code>PollOption</code> predstavlja implementaciju sadržaja ankete.
 * Sadrži njen id, naslov, poveznicu, id ankete kojoj pripada i broj glasova
 * koje je prikupila.
 * 
 * @author Filip
 *
 */
public class PollOption {
	/**
	 * ID.
	 */
	private String id;
	/**
	 * Naslov.
	 */
	private String title;
	/**
	 * Poveznica.
	 */
	private String link;
	/**
	 * ID ankete kojoj pripada.
	 */
	private String pollID;
	/**
	 * Broj glasova.
	 */
	private String noOfVotes;

	/**
	 * Konstruktor.
	 */
	public PollOption() {
	}

	/**
	 * Vraća ID.
	 * 
	 * @return ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Postavlja ID.
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Vraća naslov.
	 * 
	 * @return naslov
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Postavlja naslov.
	 * 
	 * @param title
	 *            naslov
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Vraća poveznicu.
	 * 
	 * @return poveznica
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Postavlja poveznicu.
	 * 
	 * @param link
	 *            poveznica
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Vraća ID ankete kojoj pripada.
	 * 
	 * @return ID ankete kojoj pripada
	 */
	public String getPollID() {
		return pollID;
	}

	/**
	 * Postavlja ID ankete kojoj pripada.
	 * 
	 * @param pollID
	 *            ID ankete kojoj pripada
	 */
	public void setPollID(String pollID) {
		this.pollID = pollID;
	}

	/**
	 * Vraća broj glasova.
	 * 
	 * @return broj glasova
	 */
	public String getNoOfVotes() {
		return noOfVotes;
	}

	/**
	 * Postavlja broj glasova.
	 * 
	 * @param noOfVotes
	 *            broj glasova
	 */
	public void setNoOfVotes(String noOfVotes) {
		this.noOfVotes = noOfVotes;
	}

}
