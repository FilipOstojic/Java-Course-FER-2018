package hr.fer.zemris.java.hw15.web.servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.crypto.Crypto;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Razred koji služi za validaciju prilikom log in-a postojećeg autora/korisnika
 * te za dojavu mogućih grešaka.
 * 
 * @author Filip
 *
 */
public class FormularL {

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
	public FormularL() {
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

		BlogUser existingUser = null;

		if (this.nickname.isEmpty()) {
			errors.put("nickname", "Nickname is missing!");
		} else {
			existingUser = DAOProvider.getDAO().getBlogUser(nickname);
			if (existingUser == null) {
				errors.put("nickname", "Nickname does not exist in data base!");
			}
		}

		if (this.password.isEmpty()) {
			errors.put("password", "Password is missing!");
		} else if (existingUser != null) {
			if (!Crypto.sha(password).equals(existingUser.getPasswordHash())) {
				errors.put("password", "Incorrect password.");
			}
		}
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
