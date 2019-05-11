package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.internationalization.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.internationalization.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.internationalization.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.internationalization.LocalizationProvider;

/**
 * Razred <code>JNotepadPP</code> predstavlja jednostavni uređivač teksta
 * (Notepad++). Sadrži naredbe poput učitavanja postojećih datoteka, spremanja
 * datoteka, ali i naredbe poput mijenjanje veličine slova, sortiranja teksta
 * itd. Za svaku naredbu ima vlastite akcelereatorske tipke.
 * <code>JNotepadPP</code> omogućuje rad na hrvatskom, engleskom i njemačkom
 * jeziku.
 * 
 * @author Filip
 *
 */
public class JNotepadPP extends JFrame {
	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Lokacija x, gornjeg lijevog ruba prozora.
	 */
	private static int LOCATION_X = 0;
	/**
	 * Lokacija y, gornjeg lijevog ruba prozora.
	 */
	private static int LOCATION_Y = 0;
	/**
	 * Pretpostavljena širina prozora.
	 */
	private static int DEFAULT_WIDTH = 600;
	/**
	 * Pretpostavljena visina prozora.
	 */
	private static int DEFAULT_HEIGH = 600;
	/**
	 * Naslov prozora (ime).
	 */
	private static String TITLE = "JNotepad++";
	/**
	 * Panel koji podržava tabove.
	 */
	private DefaultMultipleDocumentModel model;
	/**
	 * Trenutna površina teksta.
	 */
	private JTextArea editor;
	/**
	 * Čuva tekst od naredaba copy/cut.
	 */
	private static String text;
	/**
	 * Statusbar.
	 */
	private MyStatusBar statusbar;
	/**
	 * Form Localization provider.
	 */
	private FormLocalizationProvider formProvider = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

	/**
	 * Konstruktor.
	 * 
	 * @param language
	 *            inicijalnni jezik u programu
	 */
	public JNotepadPP(String language) {
		setTitle(TITLE);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(LOCATION_X, LOCATION_Y);
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGH));
		pack();

		initGUI();
		setWindowListener();
	}

	/**
	 * Metoda dodaje promatrača na prozor kada se želi ugasiti.
	 */
	private void setWindowListener() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);

			}
		});
	}

	/**
	 * Metoda inicijalicira grafičko korisničko sučelje te raspored komponenti.
	 */
	private void initGUI() {
		model = new DefaultMultipleDocumentModel(formProvider);
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		statusbar = new MyStatusBar(formProvider);

		JPanel center = new JPanel(new BorderLayout());
		center.add(model, BorderLayout.CENTER);
		center.add(statusbar, BorderLayout.PAGE_END);
		cp.add(center, BorderLayout.CENTER);

		editor = model.getCurrentDocument().getTextComponent();
		model.addMultipleDocumentListener(new MyMultipleDocumentListener());

		createActions();
		createMenues();
		createToolbars();
		disableButtons();
	}

	/**
	 * Inicijalizira sve akcije na false.
	 */
	private void disableButtons() {
		copySelectedPartAction.setEnabled(false);
		cutSelectedPartAction.setEnabled(false);
		upperCaseChangeAction.setEnabled(false);
		lowerCaseChangeAction.setEnabled(false);
		caseChangeAction.setEnabled(false);
		ascendingAction.setEnabled(false);
		descendingAction.setEnabled(false);
		uniqueAction.setEnabled(false);
	}

	/**
	 * Metoda provjerava postoji li u prozoru dokument koji je modificiran.
	 * 
	 * @return true ako postoji modificirani objekt, inače false
	 */
	private boolean existsModified() {
		Iterator<SingleDocumentModel> it = model.iterator();
		while (it.hasNext()) {
			if (it.next().isModified()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Metoda stvara akciju za učitavanja datoteke s računala.
	 */
	private final Action openDocumentAction = new LocalizableAction("open", formProvider) {
		/**
		 * Serijaska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepadPP.this, formProvider.getString("op1"),
											  formProvider.getString("error"), JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (model.loadDocument(filePath) == null) {
				Iterator<SingleDocumentModel> it = model.iterator();
				while (it.hasNext()) {
					SingleDocumentModel document = it.next();
					Path pathTab = document.getFilePath();
					if (pathTab != null && pathTab.equals(filePath)) {
						model.setSelectedIndex(model.singleModels.indexOf(document));;
						return;
					}
				}
				return;
			}
		}
	};

	/**
	 * Metoda stvara akciju za stavaranje novog praznog dokumenta.
	 */
	private final Action createNewAction = new LocalizableAction("new", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
		}
	};

	/**
	 * Metoda stvara akciju za zatvaranje trenutne datoteke.
	 */
	private final Action closeAction = new LocalizableAction("close", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model.getCurrentDocument().isModified()) {
				saveAsDocumentAction.actionPerformed(null);
			}
			model.closeDocument(model.getCurrentDocument());
		}
	};

	/**
	 * Metoda stvara akciju za pohranu datoteke na računalo te obavještava korisnika
	 * o uspješnosti pohrane.
	 */
	private Action saveDocumentAction = new LocalizableAction("save", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null || ((JTabbedPane) model).getSelectedIndex() == -1)
				return;
			if (model.getCurrentDocument().getFilePath() == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JNotepadPP.this, formProvider.getString("save1"),
							formProvider.getString("warning"), JOptionPane.WARNING_MESSAGE);
					return;
				}
				Iterator<SingleDocumentModel> it = model.iterator();
				while (it.hasNext()) {
					Path pathTab = it.next().getFilePath();
					if (pathTab != null && pathTab.equals(jfc.getSelectedFile().toPath())) {
						JOptionPane.showMessageDialog(JNotepadPP.this, formProvider.getString("saveAs2"),
								formProvider.getString("warning"), JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				model.getCurrentDocument().setFilePath(jfc.getSelectedFile().toPath());
				model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
			} else if (model.getCurrentDocument().isModified()) {
				model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
			} else {
				JOptionPane.showMessageDialog(JNotepadPP.this, formProvider.getString("save2"),
						formProvider.getString("information"), JOptionPane.INFORMATION_MESSAGE);
			}
		}
	};

	/**
	 * Metoda stvara akciju za pohranu datoteke na računalo te obavještava korisnika
	 * o uspješnosti pohrane.
	 */
	private Action saveAsDocumentAction = new LocalizableAction("saveAs", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null || ((JTabbedPane) model).getSelectedIndex() == -1) return;
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JNotepadPP.this, formProvider.getString("saveAs1"),
						formProvider.getString("warning"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			Iterator<SingleDocumentModel> it = model.iterator();
			while (it.hasNext()) {
				Path pathTab = it.next().getFilePath();
				if (pathTab != null && pathTab.equals(jfc.getSelectedFile().toPath())) {
					JOptionPane.showMessageDialog(JNotepadPP.this, formProvider.getString("saveAs2"),
							formProvider.getString("warning"), JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			model.getCurrentDocument().setFilePath(jfc.getSelectedFile().toPath());
			model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
		}
	};

	/**
	 * Metoda stvara akciju za kopiranje dijela teksta iz datoteke u datoteku.
	 */
	private Action copySelectedPartAction = new LocalizableAction("copy", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null || ((JTabbedPane) model).getSelectedIndex() == -1) return;
			
			editor = model.getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			if (len == 0) return;
			
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				text = doc.getText(offset, len);
				System.out.println(text);
			} catch (BadLocationException ble) {
				ble.printStackTrace();
			}
		}
	};

	/**
	 * Metoda stvara akciju za rezanje dijela teksta iz datoteke.
	 */
	private Action cutSelectedPartAction = new LocalizableAction("cut", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null || ((JTabbedPane) model).getSelectedIndex() == -1)
				return;
			editor = model.getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			if (len == 0)
				return;
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				text = doc.getText(offset, len);
				doc.remove(offset, len);
			} catch (BadLocationException ble) {
				ble.printStackTrace();
			}
		}
	};

	/**
	 * Metoda stvara akciju za ljepljenje dijela teksta iz datoteke.
	 */
	private Action pasteSelectedPartAction = new LocalizableAction("paste", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (text != null) {
				Document doc = editor.getDocument();
				try {
					doc.insertString(editor.getCaret().getDot(), text, null);
				} catch (BadLocationException e1) {
				}
			}
		}
	};

	/**
	 * Metoda stvara akciju za prikaz informacija o trenuto otvorenoj datoteci: broj
	 * linije i stupca u kojem je kursor te broj označenih slova.
	 */
	private Action infoAction = new LocalizableAction("info", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (model == null || ((JTabbedPane) model).getSelectedIndex() == -1)
				return;
			editor = model.getCurrentDocument().getTextComponent();
			if (editor.getText() != null) {
				Document doc = editor.getDocument();
				try {
					int allChars = doc.getLength();
					String tekst = doc.getText(0, allChars).replaceAll("\\s+", "");
					int charsWithoutWhitespaces = tekst.length();
					int noLines = getLines(doc.getText(0, allChars));
					String message = String.format(
							formProvider.getString("info1") + " %d " + formProvider.getString("info2") + " %d "
									+ formProvider.getString("info3") + " %d " + formProvider.getString("info4"),
							allChars, charsWithoutWhitespaces, noLines);
					JOptionPane.showMessageDialog(JNotepadPP.this, message, formProvider.getString("information"),
							JOptionPane.INFORMATION_MESSAGE);
				} catch (BadLocationException e1) {
				}
			}
		}
	};

	/**
	 * Pomoćna metoda za brojanje linija dokumenta.
	 * 
	 * @param text
	 *            ulazni tekst datoteke
	 * @return broj linija
	 */
	private int getLines(String text) {
		String[] lines = text.split("\n");
		return lines.length;
	}

	/**
	 * Metoda stvara akciju za izlaz iz JNotepad++-a.
	 */
	private Action exitAction = new LocalizableAction("exit", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (existsModified()) {
				Iterator<SingleDocumentModel> it = model.iterator();
				while (it.hasNext()) {
					SingleDocumentModel document = it.next();
					if (document.isModified()) {
						model.setSelectedIndex(model.singleModels.indexOf(document));
						saveAsDocumentAction.actionPerformed(null);
					}
				}
			}
			System.exit(0);
		}
	};

	/**
	 * Metoda stvara akciju za promjenu jezika u engleski.
	 */
	private Action hrLanguageAction = new LocalizableAction("hr", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};

	/**
	 * Metoda stvara akciju za promjenu jezika u engleski.
	 */
	private Action enLanguageAction = new LocalizableAction("en", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};

	/**
	 * Metoda stvara akciju za promjenu jezika u njemački.
	 */
	private Action deLanguageAction = new LocalizableAction("de", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};

	/**
	 * Metoda stvara akciju za promjenu veličine slova nad selektiranim tekstom.
	 */
	private Action caseChangeAction = new LocalizableAction("reverse", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor = model.getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			int offset = 0;
			if (len != 0) {
				offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			} else {
				return;
			}

			try {
				String text = doc.getText(offset, len);
				text = changeCase(text);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch (BadLocationException ble) {
			}
		}

		/**
		 * Metoda invertira veličinu svakog selektiranog slova.
		 * 
		 * @param text
		 *            ulazni tekst
		 * @return promjenjeni tekst
		 */
		private String changeCase(String text) {
			char[] znakovi = text.toCharArray();
			for (int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				znakovi[i] = Character.isLowerCase(c) ? Character.toUpperCase(c) : Character.toLowerCase(c);
			}
			return new String(znakovi);
		}
	};

	/**
	 * Metoda stvara akciju za pretvorbu označenog teksta u mala slova.
	 */
	private Action lowerCaseChangeAction = new LocalizableAction("lower", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase(false);
		}
	};

	/**
	 * Metoda dodaje akciju za promjenu velličine slova označenog dijela teksta u
	 * velika slova.
	 */
	private Action upperCaseChangeAction = new LocalizableAction("upper", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase(true);
		}
	};
	
	/**
	 * Metoda ovisno o zstavici mijenja označena slova u velika ili mala (true ili
	 * false).
	 * 
	 * @param upperCase
	 *            zastavica
	 */
	private void changeCase(boolean upperCase) {
		editor = model.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset = 0;
		if (len != 0) {
			offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		} else {
			return;
		}

		try {
			String text = doc.getText(offset, len);
			text = upperCase == true ? text.toUpperCase() : text.toLowerCase();
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ble) {
		}
	}

	/**
	 * Metoda stvara akciju za sortiranje označenih linija uzlazno.
	 */
	private Action ascendingAction = new LocalizableAction("ascending", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			editor = model.getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			int offset = 0;
			if (len != 0) {
				offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			} else {
				return;
			}
			sort(offset, len, doc, true);
		}
	};

	/**
	 * Metoda stvara akciju za sortiranje označenih linija silazno.
	 */
	private Action descendingAction = new LocalizableAction("descending", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			editor = model.getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			int offset = 0;
			if (len != 0) {
				offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			} else {
				return;
			}
			sort(offset, len, doc, false);
		}
	};

	/**
	 * Pomoćna metoda za sortiranje selektiranih linija.
	 * 
	 * @param offset
	 *            odmak
	 * @param len
	 *            duljina
	 * @param doc
	 *            dokument
	 * @param asc
	 *            true-ascending, false-descending
	 */
	private void sort(int offset, int len, Document doc, boolean asc) {
		try {
			List<String> sort = getList(offset, len, doc);
			Collator collator = Collator.getInstance(new Locale(LocalizationProvider.getInstance().getLanguage()));
			if (asc) {
				Collections.sort(sort, (x, y) -> collator.compare(x, y));
			} else {
				Collections.sort(sort, (x, y) -> collator.compare(y, x));
			}
			int line = editor.getLineOfOffset(offset);
			offset = editor.getLineStartOffset(line);
			len = editor.getLineEndOffset(editor.getLineOfOffset(len + offset));
			doc.remove(offset, len - offset);
			for (String string : sort) {
				doc.insertString(offset, string + "\n", null);
				offset += string.length() + 1;
			}
		} catch (BadLocationException e) {
		}
	}

	/**
	 * Pomoćna metoda za dobivanje liste iz selektiranog teksta.
	 * 
	 * @param offset
	 *            odmak
	 * @param len
	 *            duljina
	 * @param doc
	 *            dokument
	 * @return lista redaka
	 * @throws BadLocationException
	 */
	private List<String> getList(int offset, int len, Document doc) throws BadLocationException {
		int line = editor.getLineOfOffset(offset);
		offset = editor.getLineStartOffset(line);
		len = editor.getLineEndOffset(editor.getLineOfOffset(len + offset));
		String text = doc.getText(offset, len - offset);
		return Arrays.asList(text.split("\n"));
	}

	/**
	 * Metoda stvara akciju za filtriranje jednakih redaka u označenom tekstu.
	 * Duplikate uklanja.
	 */
	private Action uniqueAction = new LocalizableAction("unique", formProvider) {
		/**
		 * Serijska vrijednost UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			editor = model.getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			int offset = 0;
			if (len != 0) {
				offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			} else {
				return;
			}
			try {
				List<String> sort = getList(offset, len, doc);
				Set<String> set = new HashSet<>();
				for (String line : sort) {
					set.add(line);
				}
				doc.remove(offset, len - offset);
				for (String string : set) {
					doc.insertString(offset, string + "\n", null);
					offset += string.length() + 1;
				}
			} catch (BadLocationException e) {
			}
		}
	};

	/**
	 * Metoda svim akcijama akceleratorske tipke.
	 */
	private void createActions() {
		createNewAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 0"));
		closeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		copySelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control K"));
		cutSelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		pasteSelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
		infoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		hrLanguageAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control H"));
		enLanguageAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		deLanguageAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control G"));
		caseChangeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt C"));
		lowerCaseChangeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt L"));
		upperCaseChangeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt U"));
		ascendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt A"));
		descendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt D"));
		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt Q"));
	}

	/**
	 * Metoda stvara i inicijalizira meni i dodaje mu kartice za sve postojeće
	 * akcije.
	 */
	private void createMenues() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new LJMenu(formProvider, "file");
		menuBar.add(fileMenu);
		JMenu editMenu = new LJMenu(formProvider, "edit");
		menuBar.add(editMenu);
		JMenu languageMenu = new LJMenu(formProvider, "languages");
		menuBar.add(languageMenu);
		JMenu toolMenu = new LJMenu(formProvider, "tools");
		menuBar.add(toolMenu);

		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(createNewAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(infoAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

		editMenu.add(new JMenuItem(copySelectedPartAction));
		editMenu.add(new JMenuItem(cutSelectedPartAction));
		editMenu.add(new JMenuItem(pasteSelectedPartAction));

		languageMenu.add(new JMenuItem(hrLanguageAction));
		languageMenu.add(new JMenuItem(enLanguageAction));
		languageMenu.add(new JMenuItem(deLanguageAction));

		JMenu changeCases = new LJMenu(formProvider, "change");
		changeCases.add(new JMenuItem(caseChangeAction));
		changeCases.add(new JMenuItem(lowerCaseChangeAction));
		changeCases.add(new JMenuItem(upperCaseChangeAction));

		JMenu sort = new LJMenu(formProvider, "sort");
		sort.add(new JMenuItem(ascendingAction));
		sort.add(new JMenuItem(descendingAction));

		toolMenu.add(changeCases);
		toolMenu.add(sort);
		toolMenu.add(new JMenuItem(uniqueAction));

		this.setJMenuBar(menuBar);
	}

	/**
	 * Metoda inicijalizira alatnu traku s gumbima za svaku akciju.
	 */
	private void createToolbars() {
		JToolBar toolbar = new JToolBar("Alati");
		toolbar.setFloatable(true);

		toolbar.add(new JButton(createNewAction));
		toolbar.add(new JButton(openDocumentAction));
		toolbar.add(new JButton(closeAction));
		toolbar.add(new JButton(saveDocumentAction));
		toolbar.add(new JButton(saveAsDocumentAction));
		toolbar.addSeparator();
		toolbar.add(new JButton(copySelectedPartAction));
		toolbar.add(new JButton(cutSelectedPartAction));
		toolbar.add(new JButton(pasteSelectedPartAction));
		toolbar.add(new JButton(infoAction));
		toolbar.addSeparator();
		toolbar.add(new JButton(hrLanguageAction));
		toolbar.add(new JButton(enLanguageAction));
		toolbar.add(new JButton(deLanguageAction));
		toolbar.addSeparator();
		toolbar.add(new JButton(exitAction));

		this.getContentPane().add(toolbar, BorderLayout.PAGE_START);
	}

	/**
	 * Razred <code>MyMultipleDocumentListener</code> sadrži tri metode: prva se
	 * poziva kad se dogodi promjena trenutno otvorenog dokumenta, druga kada je
	 * došlo do dodavanja nove datoteke i treća kada je došlo do uklanjanja nekek
	 * datoteke.
	 * 
	 * @author Filip
	 *
	 */
	class MyMultipleDocumentListener implements MultipleDocumentListener {

		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			currentModel.getTextComponent().addCaretListener(e -> {
				updateStatusBar(currentModel);
				updateEnabled();
			});
			currentModel.getTextComponent().addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseMoved(MouseEvent e) {
				}

				@Override
				public void mouseDragged(MouseEvent e) {
					updateStatusBar(currentModel);
					updateEnabled();
				}
			});
			updateStatusBar(currentModel);
			updateTitle(currentModel);
			updateEnabled();
		}

		/**
		 * Pomoćna metoda, ažurira gumbe ovisne o selktiranom tekstu.
		 */
		private void updateEnabled() {
			editor = model.getCurrentDocument().getTextComponent();
			int len = editor.getCaret().getDot() - editor.getCaret().getMark();
			copySelectedPartAction.setEnabled(len != 0);
			cutSelectedPartAction.setEnabled(len != 0);
			upperCaseChangeAction.setEnabled(len != 0);
			lowerCaseChangeAction.setEnabled(len != 0);
			caseChangeAction.setEnabled(len != 0);
			ascendingAction.setEnabled(len != 0);
			descendingAction.setEnabled(len != 0);
			uniqueAction.setEnabled(len != 0);
		}

		/**
		 * Pomoćna mettoda ažurira naslov datoteke.
		 * 
		 * @param currentModel
		 *            trenutni dokument
		 */
		private void updateTitle(SingleDocumentModel currentModel) {
			updateStatusBar(currentModel);
			if (JNotepadPP.this.model.getCurrentDocument() == null) {
				return;
			}
			if (currentModel.getFilePath() == null) {
				JNotepadPP.this.setTitle("JNotepad++");
				return;
			}
			JNotepadPP.this.setTitle(currentModel.getFilePath().toAbsolutePath().toString() + " - JNotepad++");
		}

		/**
		 * Pomoćna metoda dinamički ažurira status bar.
		 * 
		 * @param currentModel
		 *            otvoreni dokument
		 */
		private void updateStatusBar(SingleDocumentModel currentModel) {
			if (model == null || ((JTabbedPane) model).getSelectedIndex() == -1) {
				statusbar.reset();
				return;
			}
			int length = currentModel.getTextComponent().getText().length();
			statusbar.setLength(Integer.toString(length));
			int selected = Math.abs(currentModel.getTextComponent().getCaret().getDot()
					- currentModel.getTextComponent().getCaret().getMark());
			statusbar.setSelected(Integer.toString(selected));
			int offset = currentModel.getTextComponent().getCaretPosition();
			try {
				int line = currentModel.getTextComponent().getLineOfOffset(offset);
				int column = offset - currentModel.getTextComponent().getLineStartOffset(line);
				statusbar.setLine(Integer.toString(line + 1));
				statusbar.setColumn(Integer.toString(column + 1));
			} catch (BadLocationException e) {
				JOptionPane.showMessageDialog(JNotepadPP.this, formProvider.getString("update1"),
						formProvider.getString("error"), JOptionPane.ERROR_MESSAGE);
			}
			updateEnabled();
		}

		@Override
		public void documentAdded(SingleDocumentModel model) {
			updateTitle(model);
		}

		@Override
		public void documentRemoved(SingleDocumentModel model) {
			updateTitle(model);
		}
	}

	/**
	 * Metoda koja se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	public static void main(String[] args) {
		checkArguments(args);
		final String language = args[0];
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP(language).setVisible(true);
		});
	}

	/**
	 * Metoda provjerava ispravnost unesenih argumenata.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	private static void checkArguments(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected one argumen, got: " + args.length);
			System.exit(0);
		}
		if (!args[0].equals("hr") && !args[0].equals("en") && !args[0].equals("de")) {
			System.out.println("Expected hr, en or de as language tag, got: " + args[0]);
			System.exit(0);
		}
	}
}
