package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Razred <code>DefaultSingleDocumentModel</code> predstavlja razred koji čuva
 * informacije o pojedinom dokumentu koji može biti otvoren u JNotepad++-u-
 * 
 * @author Filip
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	/**
	 * Putanja do datoteke.
	 */
	private Path filePath;
	/**
	 * Tekstualna komponenta.
	 */
	private JTextArea textComponent;
	/**
	 * Zastavica je li dokument modificiran ili nije.
	 */
	private boolean modified;
	/**
	 * Lista promatrača.
	 */
	private List<SingleDocumentListener> listeners;

	/**
	 * Konstruktor.
	 * 
	 * @param filePath
	 *            putanja do datoteke
	 * @param textContent
	 *            tekstualna komponeta
	 */
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		if (textContent != null) {
			this.filePath = filePath;
			textComponent = new JTextArea(textContent);
			textComponent.getDocument().addDocumentListener(new MyDocumentListener());
			listeners = new ArrayList<>();
		}
	}

	/**
	 * Vraća putanju do datoteke.
	 */
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * Vraća tekstualnu komponentu.
	 */
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path);
		filePath = path;
		listeners.forEach(a -> a.documentFilePathUpdated(this));
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		if (this.modified != modified) {
			this.modified = modified;
			listeners.forEach(a -> a.documentModifyStatusUpdated(this));
		} else if (modified == false) {
			listeners.forEach(a -> a.documentModifyStatusUpdated(this));
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

	/**
	 * Promatrač dokumenta ondosno razreda {@link SingleDocumentModel}. Dojavljuje o
	 * promjenama unutar datoteke.
	 * 
	 * @author Filip
	 *
	 */
	class MyDocumentListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			setModified(true);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			setModified(true);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			setModified(true);
		}
	}
}
