package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Razred <code>RequestContext</code> čuva informacije (kontekst) potrebne za
 * ispis http zahtjeva. Zahtjev se sastoji od klasičnog zaglavalja te od samog
 * teksta.
 * 
 * @author Filip
 *
 */
public class RequestContext {
	/**
	 * Veličina datoteke.
	 */
	private Long contentLength = null;
	/**
	 * Output stream za ispis http zahtjeva.
	 */
	private OutputStream outputStream;
	/**
	 * Charset.
	 */
	private Charset charset;
	/**
	 * Vrijednost za stvaranje charstea.
	 */
	public String encoding = "UTF-8";
	/**
	 * Statusni kod.
	 */
	public int statusCode = 200;
	/**
	 * Statusni tekst.
	 */
	public String statusText = "OK";
	/**
	 * Mime tip.
	 */
	public String mimeType = "text/html";
	/**
	 * Mapa čuva parametre.
	 */
	private Map<String, String> parameters;
	/**
	 * Mapa čuva privremene parametre.
	 */
	private Map<String, String> temporaryParameters = new HashMap<>();
	/**
	 * Mapa čuva trajne parametre
	 */
	private Map<String, String> persistentParameters = new HashMap<>();
	/**
	 * Lista s output Cookies-ima.
	 */
	private List<RCCookie> outputCookies;
	/**
	 * Zastavica, govori je li do sada generirano zaglavlje ili nije.
	 */
	private boolean headerGenerated;
	/**
	 * {@link IDispatcher}
	 */
	private IDispatcher dispatcher;

	/**
	 * Postavlja vrijednost kodiranja.
	 * 
	 * @param encoding
	 *            vrijednost kodiranja
	 */
	public void setEncoding(String encoding) {
		checkHeader(headerGenerated);
		this.encoding = encoding;
	}

	/**
	 * Postavlja statusni kod.
	 * 
	 * @param statusCode
	 *            statusni kod
	 */
	public void setStatusCode(int statusCode) {
		checkHeader(headerGenerated);
		this.statusCode = statusCode;
	}

	/**
	 * Postavlja statusni tekst.
	 * 
	 * @param statusText
	 *            statusni tekst
	 */
	public void setStatusText(String statusText) {
		checkHeader(headerGenerated);
		this.statusText = statusText;
	}

	/**
	 * Postavlja mime tip.
	 * 
	 * @param mimeType
	 *            mime tip
	 */
	public void setMimeType(String mimeType) {
		checkHeader(headerGenerated);
		this.mimeType = mimeType;
	}

	/**
	 * Postavlja veličinu datoteke.
	 * 
	 * @param contentLength
	 *            veličina datoteke
	 */
	public void setContentLength(Long contentLength) {
		checkHeader(headerGenerated);
		this.contentLength = contentLength;
	}

	/**
	 * Pomoćna metoda koja provjerava je li do sada generirano zaglavlje, ako je
	 * baca RunntimeException.
	 * 
	 * @param headerGenerated
	 *            zastavica postavljena
	 * @throws RuntimeException
	 *             ako je zastavica zaglavlja postavljene
	 */
	private void checkHeader(boolean headerGenerated) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
	}

	/**
	 * Vraća vrijednost ključa iz mape koja čuva parametre.
	 * 
	 * @param name
	 *            ključ
	 * @return vrijednost parametra
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Vraća skup ključeva parametara.
	 * 
	 * @return skup ključeva
	 */
	public Set<String> getParameterNames() {
		return parameters.keySet();
	}

	/**
	 * Vraća vrijednost ključa iz mape koja čuva trajne parametre.
	 * 
	 * @param name
	 *            ključ
	 * @return vrijednost parametra
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Vraća skup ključeva trajnih parametara.
	 * 
	 * @return skup ključeva
	 */
	public Set<String> getPersistentParameterNames() {
		return persistentParameters.keySet();
	}

	/**
	 * Unosi u mapu trajnih parametara novi par (ključ-vrijednost).
	 * 
	 * @param name
	 *            ključ
	 * @param value
	 *            vrijednost
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Uklanja element iz mape trajnih parametara.
	 * 
	 * @param name
	 *            ključ
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Vraća vrijednost ključa iz mape koja čuva privremene parametre.
	 * 
	 * @param name
	 *            ključ
	 * @return vrijednost parametra
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Vraća skup ključeva mepe s privremenim parametrima
	 * 
	 * @return skup ključeva
	 */
	public Set<String> getTemporaryParameterNames() {
		return temporaryParameters.keySet();
	}

	/**
	 * Dodaje novi uređeni par (ključ-vrijednost) u mapu s privremenim parametrima.
	 * 
	 * @param name
	 *            ključ
	 * @param value
	 *            vrijednost
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Uklanja element iz mape privremenih parametara.
	 * 
	 * @param name
	 *            ključ
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Dodaje novi cookie u listu kolačića.
	 * 
	 * @param cookie
	 *            kolačić
	 */
	public void addRCCookie(RCCookie cookie) {
		outputCookies.add(cookie);
	}

	/**
	 * Zapisuje polje bajtova pomoću output streama te stvara zaglavlje ako do sada
	 * još nije stvoreno.
	 * 
	 * @param data
	 *            polje bajtova
	 * @return {@link RequestContext}
	 * @throws IOException
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			createHeader(encoding, statusCode, statusText, mimeType, outputCookies);
		}
		outputStream.write(data, 0, data.length);
		return this;
	}

	/**
	 * Zapisuje tekst kao polje bajtova pomoću output streama te stvara zaglavlje
	 * ako do sada još nije stvoreno.
	 * 
	 * @param text
	 *            tekst
	 * @return {@link RequestContext}
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			createHeader(encoding, statusCode, statusText, mimeType, outputCookies);
		}
		outputStream.write(text.getBytes(charset), 0, text.getBytes(charset).length);
		return this;
	}

	/**
	 * Zapisuje polje bajtova pomoću output streama od offseta do len te stvara
	 * zaglavlje ako do sada još nije stvoreno.
	 * 
	 * @param data
	 *            polje bajtova
	 * @param offset
	 *            odmak
	 * @param len
	 *            duljina
	 * @return {@link RequestContext}
	 * @throws IOException
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated) {
			createHeader(encoding, statusCode, statusText, mimeType, outputCookies);
		}
		outputStream.write(data, offset, len);
		return this;
	}

	/**
	 * Stvara zaglavlje oblika: <BR>
	 * <BR>
	 * HTTP/1.1 statusCode statusText <BR>
	 * Content-type: mimeType encoding <BR>
	 * Set-cookie: name value domain path maxAge <BR>
	 * <BR>
	 * 
	 * @param encoding
	 *            vrijednost za šifriranje
	 * @param statusCode
	 *            statusni kod
	 * @param statusText
	 *            statusni tekst
	 * @param mimeType
	 *            mime tip
	 * @param outputCookies
	 *            lista kolačića
	 * @throws IOException
	 */
	private void createHeader(String encoding, int statusCode, String statusText, String mimeType,
			List<RCCookie> outputCookies) throws IOException {
		headerGenerated = true;
		charset = Charset.forName(encoding);
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("HTTP/1.1 %s %s\r\n", statusCode, statusText));
		sb.append(String.format("Content-Type: %s", mimeType));
		if (mimeType.startsWith("text/")) {
			sb.append(String.format("; charset=%s", encoding));
		}
		if (contentLength != null) {
			sb.append(String.format("\r\nContent-Length: %d", contentLength));
		}
		sb.append("\r\n");
		for (RCCookie cookie : outputCookies) {
			sb.append(String.format("Set-Cookie:  %s=\"%s\"", cookie.getName(), cookie.getValue()));
			sb.append(cookie.getDomain() == null ? "" : String.format("; Domain= %s", cookie.getDomain()));
			sb.append(cookie.getPath() == null ? "" : String.format("; Path= %s", cookie.getPath()));
			sb.append(cookie.getMaxAge() == null ? "" : String.format("; Max-Age= %s", cookie.getMaxAge()));
			sb.append("\r\n");
		}
		sb.append("\r\n");
		byte[] buffer = sb.toString().getBytes(StandardCharsets.ISO_8859_1);
		outputStream.write(buffer, 0, buffer.length);
	}

	/**
	 * Konstruktor.
	 * 
	 * @param outputStream
	 *            output stream (ne smije biti null referenca)
	 * @param parameters
	 *            mapa parametara
	 * @param persistentParameters
	 *            mapa trajnih parametara
	 * @param outputCookies
	 *            lista kolačića
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		Objects.requireNonNull(outputStream);
		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
	}

	/**
	 * Konstruktor.
	 * 
	 * @param outputStream
	 *            output stream (ne smije biti null referenca)
	 * @param parameters
	 *            mapa parametara
	 * @param persistentParameters
	 *            mapa trajnih parametara
	 * @param outputCookies
	 *            lista kolačića
	 * @param temporaryParameters
	 *            privremeni parametri
	 * @param dispatcher
	 *            {@link IDispatcher}
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {

		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
	}

	/**
	 * Vraća output stream.
	 * 
	 * @return output stream
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * Postavlja output stream.
	 * 
	 * @param outputStream
	 *            output stream
	 */
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	/**
	 * Vraća charset.
	 * 
	 * @return charset
	 */
	public Charset getCharset() {
		return charset;
	}

	/**
	 * Postavlja charset.
	 * 
	 * @param charset
	 *            charset
	 */
	public void setCharset(Charset charset) {
		this.charset = charset;
	}
	
	/**
	 * Vraća {@link IDispatcher}.
	 * 
	 * @return {@link IDispatcher}
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}


	/**
	 * Razred <code>RCCookie</code> predstavlja kolačić. Čuva ime, vrijednost,
	 * domenu, putanju i maximalnu starost kolačića.
	 * 
	 * @author Filip
	 *
	 */
	public static class RCCookie {
		/**
		 * Ime.
		 */
		private String name;
		/**
		 * Vrijednost.
		 */
		private String value;
		/**
		 * Domena.
		 */
		private String domain;
		/**
		 * Putanja.
		 */
		private String path;
		/**
		 * Maksimalna starost (u sekundama).
		 */
		private Integer maxAge;

		/**
		 * Konstruktor.
		 * 
		 * @param name
		 *            ime
		 * @param value
		 *            vrijednost
		 * @param maxAge
		 *            maksimalna starost
		 * @param domain
		 *            domena
		 * @param path
		 *            putanja
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Vraća ime kolačića.
		 * 
		 * @return ime
		 */
		public String getName() {
			return name;
		}

		/**
		 * Vraća vrijednost kolačića.
		 * 
		 * @return vrijednost
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Vraća domenu kolačića.
		 * 
		 * @return domena
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Vraća putanju.
		 * 
		 * @return putanja
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Vraća maksimalnu starost.
		 * 
		 * @return maksimalna starost
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
	}
}
