package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw11.jnotepadpp.internationalization.FormLocalizationProvider;

/**
 * Razred <code>DefaultMultipleDocumentModel</code> predstavlja razred koji čuva
 * podatke o skupu dokumenata ({@link SingleDocumentModel}).
 * 
 * @author Filip
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Lista dokumenata.
	 */
	List<SingleDocumentModel> singleModels;
	/**
	 * Lista promatrača MultipleDocumentListener.
	 */
	private List<MultipleDocumentListener> multipleListeners;
	/**
	 * Trenutno otvoreni dokument.
	 */
	private SingleDocumentModel currentlyOpened;
	/**
	 * Prethodno otvoreni dokument.
	 */
	private SingleDocumentModel previouslyOpened;
	/**
	 * Form Localization provider.
	 */
	private FormLocalizationProvider formProvider;

	/**
	 * Konstruktor.
	 * 
	 * @param formProvider
	 *            FormLocalizationProvider
	 */
	public DefaultMultipleDocumentModel(FormLocalizationProvider formProvider) {
		this.formProvider = formProvider;
		singleModels = new ArrayList<>();
		currentlyOpened = new DefaultSingleDocumentModel(null, "");
		multipleListeners = new ArrayList<>();
		addChangeListener(l -> {
			if (this.getSelectedIndex() != -1) {
				previouslyOpened = currentlyOpened;
				currentlyOpened = singleModels.get(this.getSelectedIndex());
			}
			multipleListeners.forEach(a -> a.currentDocumentChanged(previouslyOpened, currentlyOpened));
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return singleModels.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		DefaultSingleDocumentModel singleModel = new DefaultSingleDocumentModel(null, "");
		singleModels.add(singleModel);
		addTab("New ", new JScrollPane(singleModel.getTextComponent()));
		setSelectedIndex(singleModels.size() - 1);
		setIconAt(singleModels.indexOf(singleModel), getFloppy("icons/green.png"));
		currentlyOpened = singleModel;
		singleModel.addSingleDocumentListener(new MySingleDoucmentListener());
		multipleListeners.forEach(a -> a.currentDocumentChanged(previouslyOpened, singleModel));
		multipleListeners.forEach(a -> a.documentAdded(singleModel));
		return singleModel;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentlyOpened;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		byte[] okteti;
		try {
			okteti = Files.readAllBytes(path);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, formProvider.getString("load1"), formProvider.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		String tekst = new String(okteti, StandardCharsets.UTF_8);
		DefaultSingleDocumentModel singleModel = new DefaultSingleDocumentModel(path, tekst);
		if (pathExists(path)) {
			singleModels.add(singleModel);
			addTab(singleModel.getFilePath().getFileName().toString(), new JScrollPane(singleModel.getTextComponent()));
			setSelectedIndex(singleModels.size() - 1);
			currentlyOpened = singleModel;
			singleModel.addSingleDocumentListener(new MySingleDoucmentListener());
			singleModel.setModified(false);
			setToolTipTextAt(singleModels.indexOf(singleModel),
					singleModels.get(singleModels.indexOf(singleModel)).getFilePath().toAbsolutePath().toString());
			multipleListeners.forEach(a -> a.currentDocumentChanged(previouslyOpened, singleModel));
			multipleListeners.forEach(a -> a.documentAdded(singleModel));
			return singleModel;
		}
		return null;
	}

	/**
	 * Metoda provjerava postoji li zadana putanja među trenutnim dokumentima.
	 * 
	 * @param path
	 *            putanja
	 * @return true ako postoji, inače false
	 */
	private boolean pathExists(Path path) {
		for (int i = 0; i < singleModels.size(); i++) {
			if (singleModels.get(i).getFilePath() == null) {
				continue;
			}
			if (singleModels.get(i).getFilePath().toAbsolutePath().toString()
					.equals(path.toAbsolutePath().toString())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		byte[] podatci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(model.getFilePath(), podatci);
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(this, formProvider.getString("load1"), formProvider.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		multipleListeners.forEach(a -> a.currentDocumentChanged(null, model));
		model.setModified(false);
		JOptionPane.showMessageDialog(this, formProvider.getString("save5"), formProvider.getString("information"),
				JOptionPane.INFORMATION_MESSAGE);
		return;
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		if (getSelectedIndex() != -1) {
			removeTabAt(getSelectedIndex());
			setSelectedIndex(singleModels.size() == 1 ? -1 : 0);
			singleModels.remove(model);
			currentlyOpened = singleModels.size() > 0 ? singleModels.get(0) : null;
			if (currentlyOpened == null)
				return;
			multipleListeners.forEach(a -> a.documentRemoved(currentlyOpened));
			multipleListeners.forEach(a -> a.currentDocumentChanged(null, currentlyOpened));
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		multipleListeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		multipleListeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return singleModels.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return singleModels.get(index);
	}

	/**
	 * Metoda za učitavanje ikona diskete.
	 * 
	 * @param path
	 *            putanja do ikone
	 * @return ikona
	 */
	public ImageIcon getFloppy(String path) {
		InputStream is = this.getClass().getResourceAsStream(path);
		if (is != null) {
			byte[] bytes;
			try {
				bytes = is.readAllBytes();
				is.close();
				return new ImageIcon(bytes);
			} catch (IOException ignorable) {
			}
		}
		return null;
	}

	/**
	 * Promatrač skupa dokumenata ondosno razreda {@link MultipleDocumentModel}.
	 * Dojavljuje o promjenama unutar neke od datoteka.
	 * 
	 * @author Filip
	 *
	 */
	class MySingleDoucmentListener implements SingleDocumentListener {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			int index = singleModels.indexOf(model);
			if (model.isModified()) {
				setIconAt(index, getFloppy("icons/red.png"));
				return;
			}
			setIconAt(index, getFloppy("icons/green.png"));
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			int index = singleModels.indexOf(model);
			setTitleAt(index, singleModels.get(index).getFilePath().getFileName().toString());
			setToolTipTextAt(index, singleModels.get(index).getFilePath().toAbsolutePath().toString());
		}
	}
}
