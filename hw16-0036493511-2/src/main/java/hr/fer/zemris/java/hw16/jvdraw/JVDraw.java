package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.color.ColorLabel;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.model.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleDrawer;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleDrawer;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineDrawer;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;

/**
 * Razred <code>JVDraw</code> predstavlja program sličan Paint-u. Ima mogućnost
 * crtanja linija, kržnica i kružnica s ispunom. Također sve boje se mogu
 * mijanjati po želji. Sadrži i listu s imenima i svojstvima svih objekata koji
 * postoje. Omogućava brisanje objekata te promjenu. Ne očekuju se argumenti.
 * 
 * @author Filip
 *
 */
public class JVDraw extends JFrame {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Naslov.
	 */
	private static final String TITLE = "JVDraw";
	/**
	 * X kordinata prozora.
	 */
	private static final int LOCATION_X = 50;
	/**
	 * Y koordinata prozora.
	 */
	private static final int LOCATION_Y = 50;
	/**
	 * Pretpostavljena širnina.
	 */
	private static final int DEFAULT_WIDTH = 1000;
	/**
	 * Pretpostavljena visina.
	 */
	private static final int DEFAULT_HEIGHT = 600;
	/**
	 * Trenutno stanje.
	 */
	private Tool currentTool;
	/**
	 * {@link DocumentModel} model.
	 */
	private DocumentModel documentModel;
	/**
	 * {@link JDrawingCanvas}
	 */
	private JDrawingCanvas canvas;
	/**
	 * Komponenta za boju obruba.
	 */
	private JColorArea fgColorArea;
	/**
	 * Komponenta za boju pozadine.
	 */
	private JColorArea bgColorArea;
	/**
	 * Putanja.
	 */
	private Path docPath;

	/**
	 * Konstruktor.
	 */
	public JVDraw() {
		setTitle(TITLE);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(LOCATION_X, LOCATION_Y);
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		pack();

		initGUI();
	}

	/**
	 * Inicijalizira grafičko korisničko sučelje.
	 */
	private void initGUI() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());

		JPanel centralPanel = new JPanel(new BorderLayout());
		cp.add(centralPanel, BorderLayout.CENTER);

		createMenues();
		createToolBar(centralPanel);
		createCanvasAndList(centralPanel);
		createActions();
	}

	/**
	 * Postavlja imena primjercima {@link Action}-a.
	 */
	private void createActions() {
		openDocumentAction.putValue(Action.NAME, "Open");
		saveDocumentAction.putValue(Action.NAME, "Save");
		saveAsDocumentAction.putValue(Action.NAME, "Save as");
		exportDocumentAction.putValue(Action.NAME, "Export");
	}

	/**
	 * Stvara crtaću površinu i listu. Dodaje promatrače.
	 * 
	 * @param centralPanel
	 *            središnji panel
	 */
	private void createCanvasAndList(JPanel centralPanel) {
		JPanel panel = new JPanel(new BorderLayout());

		documentModel = new DocumentModel();
		canvas = new JDrawingCanvas(documentModel);
		canvas.setState(currentTool = new LineDrawer(fgColorArea, documentModel));

		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentTool.mouseClicked(e);
			}
		});
		canvas.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				currentTool.mouseMoved(e);
				repaint();
			}
		});

		DrawingObjectListModel listModel = new DrawingObjectListModel(documentModel);
		JList<GeometricalObject> list = new JList<>(listModel);

		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					GeometricalObject object = list.getSelectedValue();
					GeometricalObjectEditor editor = object.createGeometricalObjectEditor();
					int response = JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit geometrical object",
							JOptionPane.OK_CANCEL_OPTION);
					if (response == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage(), "Warning",
									JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
		});

		list.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_DELETE:
					documentModel.remove(list.getSelectedValue());
					break;
				case KeyEvent.VK_PLUS:
					documentModel.changeOrder(list.getSelectedValue(), 1);
					break;
				case KeyEvent.VK_MINUS:
					documentModel.changeOrder(list.getSelectedValue(), -1);
					break;
				}
			}
		});

		panel.add(canvas, BorderLayout.CENTER);
		panel.add(new JScrollPane(list), BorderLayout.LINE_END);

		centralPanel.add(panel, BorderLayout.CENTER);
	}

	/**
	 * Stvara toolBar s dva izbornika boja {@link JColorArea} te 3 gumba za izbor
	 * crtanja: dužina, kružnica i kružnica s ispunom. Dodaje labelu s vrijednostima
	 * boja iz izbornika boja.
	 * 
	 * @param centralPanel
	 *            središnji panel
	 */
	private void createToolBar(JPanel centralPanel) {
		JToolBar toolbar = new JToolBar();

		fgColorArea = new JColorArea(Color.BLACK);
		bgColorArea = new JColorArea(Color.WHITE);

		ButtonGroup buttonGroup = new ButtonGroup();
		JToggleButton lineButton = new JToggleButton("Line");

		lineButton.addActionListener(e -> {
			currentTool = new LineDrawer(fgColorArea, documentModel);
			canvas.setState(currentTool);
		});
		JToggleButton circleButton = new JToggleButton("Circle");
		circleButton.addActionListener(e -> {
			currentTool = new CircleDrawer(fgColorArea, documentModel);
			canvas.setState(currentTool);
		});
		JToggleButton filledCircleButton = new JToggleButton("Filled circle");
		filledCircleButton.addActionListener(e -> {
			currentTool = new FilledCircleDrawer(fgColorArea, bgColorArea, documentModel);
			canvas.setState(currentTool);
		});

		buttonGroup.add(lineButton);
		buttonGroup.add(circleButton);
		buttonGroup.add(filledCircleButton);

		toolbar.add(fgColorArea);
		toolbar.add(bgColorArea);
		toolbar.addSeparator();
		toolbar.add(lineButton);
		toolbar.add(circleButton);
		toolbar.add(filledCircleButton);

		JPanel bottomPanel = new JPanel();
		ColorLabel colorInfo = new ColorLabel(fgColorArea, bgColorArea);
		bottomPanel.add(colorInfo, BorderLayout.CENTER);

		centralPanel.add(toolbar, BorderLayout.PAGE_START);
		centralPanel.add(bottomPanel, BorderLayout.PAGE_END);
	}

	/**
	 * Stvara meni.
	 */
	private void createMenues() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(exportDocumentAction));

		this.setJMenuBar(menuBar);
	}

	/**
	 * Akcija za otvaranje (učitavanje) datoteke. Ponuđena opcija datoteka je s .jvd
	 * ekstenzijom.
	 */
	private final Action openDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JVD", "jvd");
			fc.setFileFilter(filter);

			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			docPath = Paths.get(filePath.toString());

			if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
				errorReport("Can not open file.");
				return;
			}
			if (!filePath.toString().endsWith(".jvd")) {
				errorReport("Selected file is not .jvd file.");
				return;
			}
			parseFile(filePath);
		}

		/**
		 * Metoda parsira ulaznu datoteku te puni {@link DocumentModel} s geometrijskim
		 * likovima zapisanih u datoteci. Prihvaćene datoteke s ekstenzijom .jvd.
		 * 
		 * @param filePath
		 *            putanja do datoteke
		 */
		private void parseFile(Path filePath) {
			try {
				deleteDocumentModel();
				List<String> lines = Files.readAllLines(filePath);
				for (String line : lines) {
					String[] chunks = line.split("\\s+");
					switch (chunks[0]) {
					case "LINE":
						if (chunks.length != 8)
							errorReport("Wrong number of arguments");
						Line lin = new Line(new Point(Integer.parseInt(chunks[1]), Integer.parseInt(chunks[2])),
								new Point(Integer.parseInt(chunks[3]), Integer.parseInt(chunks[4])),
								new Color(Integer.parseInt(chunks[5]), Integer.parseInt(chunks[6]),
										Integer.parseInt(chunks[7])));
						documentModel.add(lin);
						break;
					case "CIRCLE":
						if (chunks.length != 7)
							errorReport("Wrong number of arguments");
						Circle circle = new Circle(new Point(Integer.parseInt(chunks[1]), Integer.parseInt(chunks[2])),
								Integer.parseInt(chunks[3]), new Color(Integer.parseInt(chunks[4]),
										Integer.parseInt(chunks[5]), Integer.parseInt(chunks[6])));
						documentModel.add(circle);
						break;
					case "FCIRCLE":
						if (chunks.length != 10)
							errorReport("Wrong number of arguments");
						FilledCircle fcircle = new FilledCircle(
								new Point(Integer.parseInt(chunks[1]), Integer.parseInt(chunks[2])),
								Integer.parseInt(chunks[3]),
								new Color(Integer.parseInt(chunks[4]), Integer.parseInt(chunks[5]),
										Integer.parseInt(chunks[6])),
								new Color(Integer.parseInt(chunks[7]), Integer.parseInt(chunks[8]),
										Integer.parseInt(chunks[9])));
						documentModel.add(fcircle);
						break;
					default:
						errorReport("File contains unreadable command.");
						break;
					}
				}
			} catch (IOException e) {
				errorReport(e.getMessage());
			}
		}

		/**
		 * Metoda briše sve objekte iz {@link DocumentModel}-a.
		 */
		private void deleteDocumentModel() {
			if (documentModel.getSize() != 0) {
				for (int i = 0, limit = documentModel.getSize(); i < limit; i++) {
					documentModel.remove(documentModel.getObject(0));
				}
			}
		}
	};

	/**
	 * Akcija za spremanje datoteke ili ažuriranje već spremljene.
	 */
	private final Action saveDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (docPath == null) {
				saveAsDocumentAction.actionPerformed(null);
			} else {
				saveDocument(docPath, false);
			}
		}
	};

	/**
	 * Akcija koja sprema datoteku na disk.
	 */
	private final Action saveAsDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JVD", "jvd");
			jfc.setFileFilter(filter);

			jfc.setDialogTitle("Save document");
			if (jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JVDraw.this, "Nothing is saved.", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}

			File fileName = jfc.getSelectedFile();
			Path filePath = fileName.toPath();

			if (Files.exists(filePath)) {
				int answer = JOptionPane.showConfirmDialog(JVDraw.this, "Would you like to overwrite this file?",
						"Warning", JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					saveDocument(filePath, false);
					return;
				}
			}
			saveDocument(filePath, true);
		}
	};

	/**
	 * Metoda stvara datoteku s ekstezijom .jvd. Zapisuje sve geometrijske likove
	 * koje {@link DocumentModel} sadrži.
	 * 
	 * @param filePath
	 *            putanja do datoteke
	 * @param addExtension
	 *            zastavica
	 */
	private void saveDocument(Path filePath, boolean addExtension) {
		PrintWriter writer;
		try {
			String name = addExtension ? filePath.toString() + ".jvd" : filePath.toString();
			docPath = Paths.get(name);
			writer = new PrintWriter(name, "UTF-8");
		} catch (Exception e1) {
			errorReport(e1.getMessage());
			return;
		}
		if (documentModel.getSize() != 0) {
			for (int i = 0, limit = documentModel.getSize(); i < limit; i++) {
				GeometricalObject obj = documentModel.getObject(i);
				if (obj instanceof Line) {
					writer.println(String.format("LINE %d %d %d %d %d %d %d", (int) ((Line) obj).getStartPoint().getX(),
							(int) ((Line) obj).getStartPoint().getY(), (int) ((Line) obj).getEndPoint().getX(),
							(int) ((Line) obj).getEndPoint().getY(), ((Line) obj).getColor().getRed(),
							((Line) obj).getColor().getGreen(), ((Line) obj).getColor().getBlue()));
				} else if (obj instanceof Circle) {
					writer.println(String.format("CIRCLE %d %d %d %d %d %d", (int) ((Circle) obj).getCenter().getX(),
							(int) ((Circle) obj).getCenter().getY(), ((Circle) obj).getRadius(),
							((Circle) obj).getColor().getRed(), ((Circle) obj).getColor().getGreen(),
							((Circle) obj).getColor().getBlue()));
				} else {
					writer.println(String.format("FCIRCLE %d %d %d %d %d %d %d %d %d",
							(int) ((FilledCircle) obj).getCenter().getX(),
							(int) ((FilledCircle) obj).getCenter().getY(), ((FilledCircle) obj).getRadius(),
							((FilledCircle) obj).getColor().getRed(), ((FilledCircle) obj).getColor().getGreen(),
							((FilledCircle) obj).getColor().getBlue(), ((FilledCircle) obj).getFillColor().getRed(),
							((FilledCircle) obj).getFillColor().getGreen(),
							((FilledCircle) obj).getFillColor().getBlue()));
				}
			}
		} else {
			writer.println("");
		}
		writer.close();
	}

	/**
	 * Akcija koja exporta crtež kao .jpg, .png ili .gif datoteku na disk.
	 */
	private final Action exportDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
			if (documentModel.getSize() == 0) {
				JOptionPane.showMessageDialog(JVDraw.this, "Nothing no export.", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			for (int i = 0, limit = documentModel.getSize(); i < limit; i++) {
				documentModel.getObject(i).accept(bbcalc);
			}
			Rectangle box = bbcalc.getBoundingBox();
			BufferedImage imageP = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = imageP.createGraphics();
			g.translate(-box.x, -box.y);
			GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
			for (int i = 0, limit = documentModel.getSize(); i < limit; i++) {
				documentModel.getObject(i).accept(painter);
			}
			g.dispose();

			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter1 = new FileNameExtensionFilter("png", "png");
			FileNameExtensionFilter filter2 = new FileNameExtensionFilter("jpg", "jpg");
			FileNameExtensionFilter filter3 = new FileNameExtensionFilter("gif", "gif");
			jfc.addChoosableFileFilter(filter1);
			jfc.addChoosableFileFilter(filter2);
			jfc.addChoosableFileFilter(filter3);

			jfc.setDialogTitle("Export document");
			if (jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JVDraw.this, "Nothing had been exported.", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			String extension = jfc.getFileFilter().getDescription();
			File file = new File(jfc.getSelectedFile() + "." + extension);

			try {
				ImageIO.write(imageP, extension, file);
				JOptionPane.showMessageDialog(JVDraw.this, "Picture has been saved.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e1) {
				errorReport(e1.getMessage());
			}
		}
	};

	/**
	 * Pomoćna metoda koja stvara novi dijalog. U njemu se nalazii opis pogreške.
	 * 
	 * @param message
	 *            opis pogreške
	 */
	private void errorReport(String message) {
		JOptionPane.showMessageDialog(JVDraw.this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Metoda se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komande linije
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}
}
