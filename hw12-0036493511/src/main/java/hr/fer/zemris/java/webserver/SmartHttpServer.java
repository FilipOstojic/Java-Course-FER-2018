package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.scripting.demo.Util;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Smart HTTP Server (poslužitelj) je višenamjenski server te nudi klijentima
 * različite akcije pri pristupanju u server. Kao što su pristupanje datotekama
 * u glavnom direktoriju klijanta poslužitelja koje svi korisnici sustava mogu
 * vidjeti. Popis podržanih ekstenzija nalazi se u mime.properties datoteci. Te
 * isto tako nudi mogućnost izvršavanja smart skripti. Svojstva poslužitelja se
 * mogu mijenjati u datoteci server.properties.
 * 
 * @author Filip
 *
 */
public class SmartHttpServer {
	/**
	 * Adresa servera.
	 */
	private String address;
	/**
	 * Ime domene servera.
	 */
	private String domainName;
	/**
	 * Broj ulaza.
	 */
	private int port;
	/**
	 * Broj dretvi u thread pool-u.
	 */
	private int workerThreads;
	/**
	 * Vremensko ograničenje isteka sesije (u mili sekundama).
	 */
	private int sessionTimeout;
	/**
	 * Mapa ime mima - tip mima.
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Mapa sadržaja server.properties datoteke.
	 */
	private Map<String, String> serverProperties = new HashMap<String, String>();
	/**
	 * Mapa sadržaja workers.properties datoteke (put=referenca na {@link IWebWorker}).
	 */
	private Map<String,IWebWorker> workersMap = new HashMap<>();
	/**
	 * Dretva koja pokreće server (i zaustavlja).
	 */
	private ServerThread serverThread;
	/**
	 * Bazen dretvi (thread pool).
	 */
	private ExecutorService threadPool;
	/**
	 * Putanja do webroot direktorija.
	 */
	private Path documentRoot;
	/**
	 * Zastvaica, govori je li {@link ServerThread} pokrenuta.
	 */
	private boolean alreadyRunning;
	/**
	 * Mapa aktivnih sesija.
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Nasumični broj za generiranje SID-a.
	 */
	private Random sessionRandom = new Random();

	/**
	 * Kostruktor. Stvara HTTP server.
	 * 
	 * @param configFileName
	 *            putanja do server.properties datoteke.
	 */
	public SmartHttpServer(String configFileName) {
		serverProperties = getProperties(configFileName);
		mimeTypes = getProperties(serverProperties.get("server.mimeConfig"));
		workersMap = initWorkers(getProperties(serverProperties.get("server.workers")));
		address = serverProperties.get("serever.address");
		domainName = serverProperties.get("server.domainName");
		port = Integer.parseInt(serverProperties.get("server.port"));
		workerThreads = Integer.parseInt(serverProperties.get("server.workerThreads"));
		documentRoot = Paths.get(serverProperties.get("server.documentRoot"));
		sessionTimeout = Integer.parseInt(serverProperties.get("session.timeout"));
	}

	/**
	 * Čita properties datoteke sa zadane putanje i vraća ih u novoj mapi.
	 * 
	 * @param fileName
	 *            putanja do properties datoteke
	 * @return sadržaj properties datoteke u obliku mape
	 */
	private Map<String, String> getProperties(String fileName) {
		Properties prop = new Properties();
		Map<String, String> map = new HashMap<String, String>();
		try {
			FileInputStream inputStream = new FileInputStream(fileName);
			prop.load(inputStream);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		for (final Entry<Object, Object> entry : prop.entrySet()) {
			map.put((String) entry.getKey(), (String) entry.getValue());
		}
		return map;
	}
	
	/**
	 * Mapirani sadržaj dokumenta workers.preoperties parsira te stvara novu mapu
	 * (put=referenca na {@link IWebWorker}).
	 * 
	 * @param properties
	 *            mapa sadržaja datoteke workers.properties
	 * @return mapa (put=referenca na {@link IWebWorker})
	 */
	private Map<String, IWebWorker> initWorkers(Map<String, String> properties) {
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			try {
				IWebWorker iww = getWebWorker(entry.getValue());
				workersMap.put(entry.getKey(), iww);
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		return workersMap;
	}

	/**
	 * Stvara referencu na primjerak razreda je {@link IWebWorker}.
	 * 
	 * @param name
	 *            ime razreda na kojeg će referenca predstavljati
	 * @return {@linnk IWebWorker}
	 * @throws Exception
	 */
	private IWebWorker getWebWorker(String name) throws Exception {
		Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(name);
		Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
		IWebWorker iww = (IWebWorker) newObject;
		return iww;
	}

	/**
	 * Pokreće glavnu dretvu koja pokreće server ({@link ServerThread}). Ako je
	 * dretva već pokrenuta ništa se ne događa.
	 */
	protected synchronized void start() {
		if (alreadyRunning)	return;
		alreadyRunning = true;
		serverThread = new ServerThread();
		threadPool = Executors.newFixedThreadPool(workerThreads);
		serverThread.start();
	}

	/**
	 * Zaustavlja glavnu dretvu koja je pokrenula server ({@link ServerThread}). Ako
	 * je već dretva zaustavljena ništa se ne događa.
	 */
	protected synchronized void stop() {
		if (!alreadyRunning) return;
		threadPool.shutdown();
		serverThread.interrupt();
		alreadyRunning = false;
	}
	
	/**
	 * Predstavlja glavnu dretvu HTTP servera. Od kako je pokrenuta sluša zahtjeve
	 * za spajanjem na server. Prihvaća ih te prosljeđuje novom
	 * {@link ClientWorker}-u koji ih obrađuje. Također pokreće dretvu čistača
	 * {@link CleanerThread} koja čisti iz memorije zastarjele sesije.
	 * 
	 * @author Filip
	 *
	 */
	protected class ServerThread extends Thread {
		@Override
		public void run() {
			try {
				@SuppressWarnings("resource")
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
				
				CleanerThread cleaner = new CleanerThread();
				cleaner.start();
				
				while (true) {
 					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
	
	/**
	 * Dretva čistač. Svakih 5 minuta prolazi kroz sesije i briše one zastarijele.
	 * 
	 * @author Filip
	 *
	 */
	private class CleanerThread extends Thread {
		/**
		 * Vrijeme spavanja dretve.
		 */
		private static final int SLEEPING_TIME = 300000;
		
		/**
		 * Konstruktor.
		 */
		public CleanerThread() {
			setDaemon(true);
		}

		@Override
		public void run() {
			do {
				for (Map.Entry<String, SessionMapEntry> entry : sessions.entrySet()) {
					long sessionValidUntil = entry.getValue().validUntil;
					long currentTime = System.currentTimeMillis() / 1000;

					if (sessionValidUntil < currentTime) {
						sessions.remove(entry.getKey());
					}

					sleep();
				}
			} while (alreadyRunning);
		}

		/**
		 * Metoda u kojoj dretva spava.
		 */
		private void sleep() {
			try {
				Thread.sleep(SLEEPING_TIME);
			} catch (InterruptedException ignorable) {
			}
		}
	}

	/**
	 * Predstavlja posao koji treba obaviti zbog zahtjeva korisnika za spajanjem na
	 * server. Svaki <code>ClienWorker</code> se dodaje u bazen dretvi.
	 * 
	 * @author Filip
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * Pristumna točka.
		 */
		private Socket csocket;
		/**
		 * Input stream.
		 */
		private PushbackInputStream istream;
		/**
		 * Output stream.
		 */
		private OutputStream ostream;
		/**
		 * Verzija dokumenta.
		 */
		private String version;
		/**
		 * Ime metode sadržane u prvoj liniji zaglavlja.
		 */
		private String method;
		/**
		 * Host.
		 */
		private String host;
		/**
		 * Putanja od documentRoota do datoteke.
		 */
		private String path;
		/**
		 * Sadrži kluč=vrijednost podatke za mapu params.
		 */
		private String paramString;
		/**
		 * Mapa čuva parametre.
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Mapa čuva privremene paramentre.
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * Mapa čuva trajne parametre.
		 */
		private Map<String, String> permPrams = new HashMap<String, String>();
		/**
		 * Lista kolačića.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Trenutni Session ID.
		 */
		private String SID;
		/**
		 * {@link RequestContext}
		 */
		private RequestContext context;

		/**
		 * Konstruktor.
		 * 
		 * @param csocket
		 *            pristupna točka
		 */
		public ClientWorker(Socket csocket) {
			super();
			Objects.requireNonNull(csocket);
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				List<String> request = readRequest();
				if (request == null || request.size() < 1) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				String firstLine = request.get(0);
				String requestedPath = extract(firstLine);
				if (!method.equals("GET") && (!version.equals("HTTP/1.0") || !version.equals("HTTP/1.1"))) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				
				initHost(request);				
				initPathAndParamString(requestedPath);
				checkSession(request);
				parseParameters(paramString);
				internalDispatchRequest(path, true);
				
			} catch (Exception ex) {
				System.out.println("Error: " + ex.getMessage());
				ex.printStackTrace();
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
		}

		/**
		 * Gleda je li dobiveni kandidat za session ID onaj koji se već prijavljivao ili
		 * se mora stvarati novi sid (zbog isteka valjanosti, različitih hostova itd.).
		 * Novom sid-u ažurira njegovo vrijeme valjanosti te pamti njegov kolačić.
		 * 
		 * @param sidCandidate
		 *            session ID
		 */
		private void processSidCandidate(String sidCandidate) {
			if (sidCandidate == null) {
				createSessionAndCookie();
				return;
			}
			
			SessionMapEntry entry = sessions.get(sidCandidate);
			if (entry==null || !entry.host.equals(host)) { 
				createSessionAndCookie();
				return;
			}
			if (entry.validUntil < (System.currentTimeMillis() / 1000)) {
				sessions.remove(sidCandidate);
				createSessionAndCookie();
				return;
			}
			entry.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
			permPrams = entry.map;
		}

		/**
		 * Stvara novi SID, postavlja mu trajanje te pamti njegov kolačić.
		 */
		private void createSessionAndCookie() {
			SID = generateSID();
			SessionMapEntry entry = new SessionMapEntry(SID, sessionTimeout, host);
			sessions.put(SID, entry);
			outputCookies.add(new RCCookie("sid", SID, null, host.split(":")[0], "/"));
			entry.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
			permPrams = entry.map;
		}

		/**
		 * Pretražuje zaglavlje koje počinje s "Cookie: " te parsira tu liniju i
		 * određuje ppotencijalni SID.
		 * 
		 * @param header
		 *            zaglavlje
		 */
		private void checkSession(List<String> header) {
			String sidCandidate = null;
			for (String line : header) {
				if (!line.startsWith("Cookie:")) continue;
				String[] chunks = line.substring(7).trim().split(";");
				for (String chunk : chunks) {
					String[] nameAndValue = chunk.split("=");
					if (nameAndValue[0].equals("sid")) {
						sidCandidate = nameAndValue[1].replaceAll("\"", "");
					}
				}
			}
			processSidCandidate(sidCandidate);
		}

		/**
		 * Metoda koja izvršava skripte ( .smscr datoteke) pomoću
		 * {@link SmartScriptEngine}-a.
		 */
		private void executeScript(RequestContext rc, String urlPath) {
			rc.setMimeType("text/plain");
			String documentBody = Util.readFromDisk("webroot" + urlPath);
			new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		}

		/**
		 * Ažurira mime tip ovisno o ekstenziji. Ako ekstenzija ne postoji u mapi
		 * mimetypes ažurira se na "application/octet-stream".
		 * 
		 * @param requestedPathh
		 *            put do zahtjeva
		 * @return mime tip
		 */
		private String getMimeType(Path requestedPathh) {
			String name = requestedPathh.getFileName().toString();
			String extension = name.substring(name.lastIndexOf(".") + 1);
			String mime = null;
			for (String key : mimeTypes.keySet()) {
				if (key.equals(extension)) {
					mime = mimeTypes.get(key);
				}
			}
			return mime == null? "application/octet-stream" : mime;
		}

		/**
		 * Metoda puni mapu params, parsirajući vrijednosti varijable paramString.
		 * 
		 * @param paramString
		 *            varijabla
		 */
		private void parseParameters(String paramString) {
			if (paramString == null) return;
			String[] chunks = paramString.split("&");
			for (String chunk : chunks) {
				if (!chunk.contains("=")) {
					params.put(chunk, null);
				}
				String[] keyValue = chunk.split("=");
				if (keyValue.length == 1) {
					params.put(keyValue[0], "");
				} else {
					params.put(keyValue[0], keyValue[1]);
				}
			}
		}

		/**
		 * Metoda inicijalizira varijable: path i paramString.
		 * 
		 * @param requestedPath
		 *            putanja do upita
		 */
		private void initPathAndParamString(String requestedPath) {
			if (requestedPath.contains("?")) {
				String[] chunks = requestedPath.split("\\?");
				path = chunks[0];
				paramString = chunks[1];
				return;
			}
			path = requestedPath;
		}

		/**
		 * Metoda inicijalizira vrijednost varijable host, na ono što sljedi iza linije
		 * u zaglavlju koja počinje sa "Host: ". Ako takva linija ne postoji host se
		 * inicijalizira na ime domene.
		 * 
		 * @param request
		 *            lista redaka zaglavlja
		 */
		private void initHost(List<String> request) {
			for (String header : request) {
				if (header.trim().startsWith("Host: ")) {
					host = header.substring(6).trim();
					checkForNumbers(host);
					return;
				}
			}
			host = domainName;
		}

		/**
		 * Metoda provjerava je li unos oblika: [neki tekst : neki brojevi] te ažurira
		 * vrijednost varijable host na "neki tekst".
		 * 
		 * @param host
		 *            unos (dasadašnji host)
		 */
		private void checkForNumbers(String host) {
			if (host.contains(":")) {
				String[] chunks = host.split(":");
				if (chunks.length == 2) {
					Pattern pInt = Pattern.compile("[0-9]+");
					Matcher mInt = pInt.matcher(chunks[1]);
					Pattern pStr = Pattern.compile("[a-zA-Z]+");
					Matcher mStr = pStr.matcher(chunks[0]);
					if (mInt.matches() && mStr.matches()) {
						host = chunks[0];
					}
				}
			}
		}

		/**
		 * Metoda inicijalizira varijable: method i version, iz prve linije zaglavlja.
		 * 
		 * @param firstLine
		 *            prva linija zaglavlja
		 * @return putanju za upit
		 * @throws IOException
		 */
		private String extract(String firstLine) throws IOException {
			String[] chunks = firstLine.trim().split("\\s+");
			if (chunks.length != 3) {
				sendError(ostream, 400, "Bad request");
				return null;
			}
			method = chunks[0].toUpperCase();
			version = chunks[2].toUpperCase();
			return chunks[1];
		}
		
		/**
		 * Metoda za čitanje zahtijeva.
		 * 
		 * @return listu redaka zaglavlja
		 * @throws IOException
		 */
		private List<String> readRequest() throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = istream.read();
				if (b == -1) return null;
				if (b != 13) bos.write(b);

				switch (state) {
				case 0:
					if (b == 13) state = 1;
					else if (b == 10) state = 4;
					break;
				case 1:
					if (b == 10) state = 2;
					else state = 0;
					break;
				case 2:
					if (b == 13) state = 3;
					else state = 0;
					break;
				case 3:
					if (b == 10) break l;
					else state = 0;
					break;
				case 4:
					if (b == 10) break l;
					else state = 0;
					break;
				}
			}
			String text = new String(bos.toByteArray(), StandardCharsets.US_ASCII);
			return extractHeaders(text);
		}

		/**
		 * Metoda stvara zaglavlje u listi.
		 * 
		 * @param requestHeader
		 *            ulazni tekst
		 * @return lista redaka zaglavlja
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty()) break;
				
				char c = s.charAt(0);
				if (c == 9 || c == 32) 
					currentLine += s;
				else {
					if (currentLine != null) 
						headers.add(currentLine);
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) 
				headers.add(currentLine);
			
			return headers;
		}

		/**
		 * Metoda za ispis poruke o pogrešci pomoću izlaznog toka.
		 * 
		 * @param ostream
		 *            izlazni tok
		 * @param statusCode
		 *            statusni kod
		 * @param statusText
		 *            statusni tekst
		 * @throws IOException
		 */
		private void sendError(OutputStream ostream, int statusCode, String statusText) throws IOException {

			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" 
					+ "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" 
					+ "Content-Length: 0\r\n" 
					+ "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();

		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);			
		}
		
		/**
		 * Metoda za obradu zahtjeva klijenta. Zadana putanja se analizira i generira se
		 * odgovor.
		 * 
		 * @param urlPath
		 *            putanja do tražene datoteke
		 * @param directCall
		 *            zastavica, označava je li zahtjev poslan od klijenta (false) ili
		 *            od servera (true)
		 * @throws Exception
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			context = context == null ? new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this) : context;
			
			if (urlPath.startsWith("/ext/")) {
				String[] chunks = urlPath.substring(1).split("/");
				String name = chunks[1];
				IWebWorker worker = getWebWorker("hr.fer.zemris.java.webserver.workers." + name);
				worker.processRequest(context);
				return;
			} else if (urlPath.startsWith("/private") && directCall) {
				sendError(ostream, 404, "File not found.");
				return;
			}
			
			if (workersMap.get(urlPath) != null) {
				workersMap.get(urlPath).processRequest(context);
				return;
			}

			Path absolutePath = documentRoot.resolve(urlPath.substring(1)).toAbsolutePath();
			context.setMimeType(getMimeType(absolutePath));
			context.setStatusCode(200);
			
			if (!absolutePath.startsWith(documentRoot)) {
				sendError(ostream, 403, "Forbidden.");
				return;
			}
			if (!Files.exists(absolutePath) || !Files.isReadable(absolutePath)){
				sendError(ostream, 404, "File not found.");
				return;
			}
			if (urlPath.endsWith(".smscr")) {
				executeScript(context, urlPath);
				return;
			}
			
			send(context, absolutePath);
		}
		
		/**
		 * Metoda generira novi SID. SID se sastoji od 20 random generiranih velikih
		 * slova engleske abecede.
		 * 
		 * @return SID
		 */
		private String generateSID() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 20; i++) {
				char c = (char) ((char) Math.abs(sessionRandom.nextInt()) % 26 + 65);
				sb.append(c);
			}
			return sb.toString();
		}
		
		/**
		 * Metoda čita sadržaj datoteke i ispisuje ga pomoću {@link RequestContext}.
		 * 
		 * @param rc
		 *            {@link RequestContext}
		 * @param path
		 *            putanja zahtjeva
		 * @throws IOException
		 */
		private void send(RequestContext rc, Path path) throws IOException {
			try (InputStream is = Files.newInputStream(path)) {
				byte[] buffer = new byte[1024];
				while (true) {
					int r = is.read(buffer);
					if (r < 1) break;
					rc.write(buffer, 0, r);
				}
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
			ostream.flush();
		}
	}

	/**
	 * Sadrži sve sesije od jednog korisnika. Novi korisnik dobiva novi entry.
	 * 
	 * @author Filip
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Session ID.
		 */
		@SuppressWarnings("unused")
		String sid;
		/**
		 * Host.
		 */
		String host;
		/**
		 * Do kada vrijedi sessija.
		 */
		long validUntil;
		/**
		 * Višedretveno sigurna mapa, čuva aktivne sesije korisnika.
		 */
		Map<String, String> map;

		/**
		 * Konstruktor.
		 * 
		 * @param sid
		 *            Session ID
		 * @param sessionTimeout
		 *            vrijeme trajanja sesije
		 * @param host
		 *            host
		 */
		public SessionMapEntry(String sid, int sessionTimeout, String host) {
			this.sid = sid;
			validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
			map = new ConcurrentHashMap<>();
			this.host = host;
		}
		
	}

	/**
	 * Metoda se poziva kada se pokrenen program.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	public static void main(String[] args) {
		try {
			SmartHttpServer server = new SmartHttpServer("config/server.properties");
			server.start();
		} catch (Exception e) {
			System.out.println("Server Error!");
		}
	}

}
