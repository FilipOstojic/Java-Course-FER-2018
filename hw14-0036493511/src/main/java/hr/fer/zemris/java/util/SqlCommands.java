package hr.fer.zemris.java.util;

/**
 * Pomoćni razred. Sadrži konstante s SQL naredbama potrebnih u ostatku
 * programa.
 * 
 * @author Filip
 *
 */
public class SqlCommands {
	/**
	 * Stvara tablicu s anketama ({@link Poll}).
	 */
	public static final String CREATE_POLL = "CREATE TABLE Polls" +
			" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
			" title VARCHAR(150) NOT NULL," +
			" message CLOB(2048) NOT NULL)";
	
	/**
	 * Stvara tablicu sa sadržajem anketa ({@link PollOption}).
	 */
	public static final String CREATE_POLL_OPTIONS = "CREATE TABLE PollOptions"+
			"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"+
			"optionTitle VARCHAR(100) NOT NULL,"+
			"optionLink VARCHAR(150) NOT NULL,"+
			"pollID BIGINT,"+
			"votesCount BIGINT,"+
			"FOREIGN KEY (pollID) REFERENCES Polls(id))";
	
	/**
	 * Broji sve n-torke u tablici Polls.
	 */
	public static final String POLLS_SIZE = "SELECT COUNT(*) AS count FROM Polls";
	
	/**
	 * Broji sve n-torke u tablici PollOptions.
	 */
	public static final String POLL_OPTIONS_SIZE = "SELECT COUNT(*) AS count FROM PollOptions";
	
	/**
	 * Dio naredbe za umetanje n-torke u tablicu Polls.
	 */
	private static final String INSERT_INTO_POLLS = "INSERT INTO Polls(title, message) VALUES ";
	
	/**
	 * Unosi anketu o bendovima u Polls.
	 */
	public static final String BAND_POLL = 
			INSERT_INTO_POLLS + "('Vote for your favourite band', "
							  + "'Of the following bands, which band is your favourite? Click to vote!') ";
	
	/**
	 * Unosi anketu o igricama u Polls.
	 */
	public static final String GAMING_POLL = 
			INSERT_INTO_POLLS + "('Vote for your favourite game', "
							  + "'Of the following games, which game is your favourite? Click to vote!') ";
		
	
	/**
	 * Dio naredbe za umetanje n-torke u tablicu PollOptions.
	 */
	private static final String INSERT_INTO_POLL_OPTIONS = "INSERT INTO PollOptions(optionTitle, optionLink, pollID, votesCount) VALUES ";
	
	/**
	 * Umeće bendove u tablicu PollOptions.
	 */
	public static final String[] FILL_BANDS = {
			INSERT_INTO_POLL_OPTIONS + "('The Beatles', 'https://www.youtube.com/watch?v=z9ypq6_5bsg', ?, 0)",
			INSERT_INTO_POLL_OPTIONS + "('The Platters', 'https://www.youtube.com/watch?v=H2di83WAOhU', ?, 0)",
			INSERT_INTO_POLL_OPTIONS + "('The Beach Boys', 'https://www.youtube.com/watch?v=2s4slliAtQU', ?, 0)",
			INSERT_INTO_POLL_OPTIONS + "('The Four Seasons', 'https://www.youtube.com/watch?v=y8yvnqHmFds', ?, 0)",
			INSERT_INTO_POLL_OPTIONS + "('The Marcels', 'https://www.youtube.com/watch?v=qoi3TH59ZEs', ?, 0)",
			INSERT_INTO_POLL_OPTIONS + "('The Everly Brothers', 'https://www.youtube.com/watch?v=tbU3zdAgiX8', ?, 0)",
			INSERT_INTO_POLL_OPTIONS + "('The Mamas And The Papas', 'https://www.youtube.com/watch?v=N-aK6JnyFmk', ?, 0)"
	};
	
	/**
	 * Umeće igrice u tablicu POllOptions.
	 */
	public static final String[] FILL_GAMES = {
			INSERT_INTO_POLL_OPTIONS + "('Fortnite', 'https://www.youtube.com/watch?v=8w-oyX-5WHk', ?, 0)",
			INSERT_INTO_POLL_OPTIONS + "('PUBG', 'https://www.youtube.com/watch?v=M4AT7HcECf0', ?, 0)",
			INSERT_INTO_POLL_OPTIONS + "('FIFA18', 'https://www.youtube.com/watch?v=FKCex_VwXSE', ?, 0)",
			INSERT_INTO_POLL_OPTIONS + "('HALO5', 'https://www.youtube.com/watch?v=gvn1k6__yTc', ?, 0)",
			INSERT_INTO_POLL_OPTIONS + "('COD:WWII', 'https://www.youtube.com/watch?v=VM2krppk9dA', ?, 0)"
	};
	
	/**
	 * Vraća n-torku iz Polls ovisno o id-u.
	 */
	public static final String GET_POLL = "SELECT * FROM Polls WHERE id=?";
	
	/**
	 * Vraća sve n-torke iz Polls.
	 */
	public static final String GET_ALL_POLLS = "SELECT * FROM Polls";
	
	/**
	 * Vraća n-torku iz PollOptions ovisno o id-u.
	 */
	public static final String GET_OPTION_POLL = "SELECT * FROM PollOptions WHERE id=?";
	
	/**
	 * Vraća sve n-torke iz PollOptions.
	 */
	public static final String GET_ALL_OPTION_POLLES = "SELECT * FROM PollOptions";
	
	/**
	 * Povećava broj glasova u PollOptions ovisno o id-u.
	 */
	public static final String INCREMENT_VOTE = "UPDATE PollOptions SET votesCount = votesCount + 1 WHERE id=?";
}
