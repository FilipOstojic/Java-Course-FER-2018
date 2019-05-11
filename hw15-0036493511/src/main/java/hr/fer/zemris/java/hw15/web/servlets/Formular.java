package hr.fer.zemris.java.hw15.web.servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.crypto.Crypto;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Razred koji služi za validaciju prilikom registracije novog autora/korisnika
 * te za dojavu mogućih grešaka.
 * 
 * @author Filip
 *
 */
public class Formular {

	/**
	 * String verzija identifikatora.
	 */
	private String id;
	/**
	 * Prezime osobe.
	 */
	private String lastName;
	/**
	 * Ime osobe.
	 */
	private String firstName;
	/**
	 * E-mail osobe.
	 */
	private String email;
	/**
	 * Password osobe.
	 */
	private String password;
	/**
	 * Password osobe.
	 */
	private String nickname;

	/**
	 * Mapa s pogreškama. Očekuje se da su ključevi nazivi svojstava a vrijednosti
	 * tekstovi pogrešaka.
	 */
	Map<String, String> errors = new HashMap<>();

	/**
	 * Konstruktor.
	 */
	public Formular() {
	}

	/**
	 * Dohvaća poruku pogreške za traženo svojstvo.
	 * 
	 * @param ime
	 *            naziv svojstva za koje se traži poruka pogreške
	 * @return poruku pogreške ili <code>null</code> ako svojstvo nema pridruženu
	 *         pogrešku
	 */
	public String getError(String name) {
		return errors.get(name);
	}

	/**
	 * Provjera ima li barem jedno od svojstava pridruženu pogrešku.
	 * 
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean haveErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Provjerava ima li traženo svojstvo pridruženu pogrešku.
	 * 
	 * @param ime
	 *            naziv svojstva za koje se ispituje postojanje pogreške
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean haveError(String name) {
		return errors.containsKey(name);
	}

	/**
	 * Na temelju parametara primljenih kroz {@link HttpServletRequest} popunjava
	 * svojstva ovog formulara.
	 * 
	 * @param req
	 *            objekt s parametrima
	 */
	public void getFromHttpRequest(HttpServletRequest req) {
		this.id = prepare(req.getParameter("id"));
		this.firstName = prepare(req.getParameter("firstName"));
		this.lastName = prepare(req.getParameter("lastName"));
		this.email = prepare(req.getParameter("email"));
		this.nickname = prepare(req.getParameter("nickname"));
		this.password = prepare(req.getParameter("password"));
	}

	/**
	 * Temeljem sadržaja ovog formulara puni svojstva predanog domenskog objekta.
	 * Metodu ne bi trebalo pozivati ako formular prethodno nije validiran i ako
	 * nije utvrđeno da nema pogrešaka.
	 * 
	 * @param r
	 *            domenski objekt koji treba napuniti
	 */
	public void generateBlogUser(BlogUser r) {
		r.setFirstName(this.firstName);
		r.setLastName(this.lastName);
		r.setEmail(this.email);
		r.setPasswordHash(Crypto.sha(this.password));
		r.setNickname(this.nickname);
	}

	/**
	 * Metoda obavlja validaciju formulara. Formular je prethodno na neki način
	 * potrebno napuniti. Metoda provjerava semantičku korektnost svih podataka te
	 * po potrebi registrira pogreške u mapu pogrešaka.
	 */
	public void validate() {
		errors.clear();

		if (this.firstName.isEmpty()) {
			errors.put("firstName", "First name is missing!");
		}

		if (this.lastName.isEmpty()) {
			errors.put("lastName", "Last name is missing!");
		}

		if (this.email.isEmpty()) {
			errors.put("email", "EMail is missing!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1 || !isValidEmailAddress(email)) {
				errors.put("email", "EMail does not have correct format.");
			}
		}

		if (this.nickname.isEmpty()) {
			errors.put("nickname", "Nickname is missing!");
		} else {
			BlogUser existingUser = DAOProvider.getDAO().getBlogUser(nickname);
			if (existingUser != null) {
				errors.put("nickname", "Nickname already exists!");
			}
		}

		if (this.password.isEmpty()) {
			errors.put("password", "Password is missing!");
		}
	}

	/**
	 * Pomoćna metoda koja do određene razine provjerava ispravnos unosa E-mail
	 * adrese-
	 * 
	 * @param email
	 *            unesena E-mail adresa
	 * @return true ako je ispravna, inače false
	 */
	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * Pomoćna metoda koja <code>null</code> stringove konvertira u prazne
	 * stringove, što je puno pogodnije za uporabu na webu.
	 * 
	 * @param s
	 *            string
	 * @return primljeni string ako je različit od <code>null</code>, prazan string
	 *         inače.
	 */
	private String prepare(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	/**
	 * Dohvat id-a.
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter za id.
	 * 
	 * @param id
	 *            vrijednost na koju ga treba postaviti.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Dohvat prezimena.
	 * 
	 * @return prezime
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter za prezime.
	 * 
	 * @param lastName
	 *            vrijednost na koju ga treba postaviti.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Dohvat imena.
	 * 
	 * @return ime
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter za ime.
	 * 
	 * @param firstName
	 *            vrijednost na koju ga treba postaviti.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Dohvat emaila.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter za email.
	 * 
	 * @param email
	 *            vrijednost na koju ga treba postaviti.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Dohvat lozinke.
	 * 
	 * @return lozinka
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter za lozinku.
	 * 
	 * @param password
	 *            vrijednost na koju ga treba postaviti.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Dohvat nadimka.
	 * 
	 * @return nadimak
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Setter za nadimak.
	 * 
	 * @param password
	 *            vrijednost na koju ga treba postaviti.
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
