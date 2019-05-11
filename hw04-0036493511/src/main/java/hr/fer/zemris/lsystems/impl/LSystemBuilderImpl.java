package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Razred <code>LSystemBuilderImpl</code> predstavlja razred koji stvara
 * primjerke razreda koji implementiraju sučelje <code>LSystem</code>.
 * 
 * @author Filip
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Riječnik koji sprema za određeni simbol pripadajuću komandu.
	 */
	private Dictionary symbolCommand = new Dictionary();
	/**
	 * Riječnik koji sprema za određeni simbol pripadajuću produkciju.
	 */
	private Dictionary symbolProduction = new Dictionary();
	/**
	 * Pokazuje koliko je dugačak jedinični pomak kornjače.
	 */
	private double unitLength = 0.1;
	/**
	 * Služi za skaliranje jediničnog pomaka.
	 */
	private double unitLengthDegreeScaler = 1;
	/**
	 * Predstavlja točku iz koje kornjača kreće.
	 */
	private Vector2D origin = new Vector2D(0, 0);
	/**
	 * Kut vektora smijera.
	 */
	private double angle = 0;
	/**
	 * Početni niz iz kojeg kreće razvoj sustava; može biti samo jedan simbol ali
	 * može biti niz.
	 */
	private String axiom = "";

	/**
	 * Metoda vrača primjerak razreda <code>LSystemImpl</code>.
	 */
	@Override
	public LSystem build() {
		
		/**
		 * Pomoćni razred koji implementira sučelje <code>LSystem</code>.
		 * 
		 * @author Filip
		 *
		 */
		class LSystemImpl implements LSystem {
			/**
			 * Metoda stvara kontekst i stanje kornjače. Za svaki znak koji je generiran
			 * izvrši zadanu komandu.
			 */
			@Override
			public void draw( int level, Painter painter) {
				Context cntx = new Context();
				TurtleState ts = new TurtleState(origin.copy(), (new Vector2D(1, 0)).rotated(angle), Color.decode("#000000"),
						unitLength * (Math.pow(unitLengthDegreeScaler, level)));
				cntx.pushState(ts);
				String output = generate(level);
				char[] array = output.toCharArray();
				for (char c : array) {
					if (symbolCommand.get(c) != null) {
						Command command = ((Command) symbolCommand.get(c));
						command.execute(cntx, painter);
					}
				}
			}
			
			/**
			 * Metoda generira String tako što zamjenjuje axiom produkcijom na svakom nivou.
			 */
			@Override
			public String generate(int level) {
				if (level < 0) {
					throw new IllegalArgumentException("Level must be 0 or greater.");
				} else {
					if (level == 0) {
						return axiom;
					} else {
						StringBuilder sb = new StringBuilder();
						sb.append(generate(level - 1));
						char[] array = sb.toString().toCharArray();
						StringBuilder sb2 = new StringBuilder();
						for (int i = 0, size = array.length; i < size; i++) {
							if (symbolProduction.get(array[i]) != null) {
								sb2.append(symbolProduction.get(array[i]));
							} else {
								sb2.append(array[i]);
							}
						}
						return sb2.toString();
					}
				}
			}
		}
		
		return new LSystemImpl();
	}

	/**
	 * Metoda ulazni tekst parsira i prevodi u metode te na taj način inicijalizira
	 * primjerak razreda <code>LSystemBuilder</code>.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for (String line : arg0) {
			line = line.trim().replaceAll("\\s{2,}", " ");
			if (line.isEmpty())
				continue;
			String[] chunks = line.split(" ");
			switch (chunks[0]) {
			case "origin":
				double x = Double.parseDouble(chunks[1]);
				double y = Double.parseDouble(chunks[2]);
				setOrigin(x, y);
				break;
			case "angle":
				double angle = Double.parseDouble(chunks[1]);
				setAngle(angle);
				break;
			case "unitLength":
				double unitLength = Double.parseDouble(chunks[1]);
				setUnitLength(unitLength);
				break;
			case "unitLengthDegreeScaler":
				String[] array = Arrays.copyOfRange(chunks, 1, chunks.length);
				double unitLengthDegreeScaler = getScaler(array);
				setUnitLengthDegreeScaler(unitLengthDegreeScaler);
				break;
			case "command":
				char c = chunks[1].charAt(0);
				if (chunks.length == 3) {
					registerCommand(c, chunks[2]);
				} else {
					registerCommand(c, chunks[2] + " " + chunks[3]);
				}
				break;
			case "axiom":
				String axiom = chunks[1].toString();
				setAxiom(axiom);
				break;
			case "production":
				registerProduction(chunks[1].charAt(0), chunks[2]);
				break;
			default:
				throw new IllegalArgumentException("Input String does not match any special word, was:" + chunks[0]);
			}
		}
		return this;
	}

	/**
	 * Pomoćna metoda za dobivanje scalara iz texta.
	 * 
	 * @param array
	 *            dio linije iz koje treba napraviti scalar
	 * @return scalar
	 */
	private double getScaler(String[] array) {
		if (array.length == 1) {
			String input = array[0];
			return calculateDouble(input);
		} else {
			String input = "";
			for (int i = 0; i < array.length; i++) {
				input += array[i];
			}
			return calculateDouble(input);
		}
	}

	/**
	 * Pomoćna metoda za računanje scalarnog zapisa.
	 * 
	 * @param input
	 *            dio linije sa scalarom
	 * @return scalar
	 */
	private double calculateDouble(String input) {
		if (input.contains("/")) {
			String[] arrayTmp = input.split("/");
			double x = Double.parseDouble(arrayTmp[0]);
			double y = Double.parseDouble(arrayTmp[1]);
			return (double) x / y;
		} else {
			return Double.parseDouble(input);
		}
	}

	/**
	 * Metoda koja dodaje u riječnik znak i pridruženu joj naredbu.
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		arg1 = arg1.trim().replaceAll("\\s{2,}", " ");
		String[] chunks = arg1.split(" ");

		Command command = getCommand(chunks);
		symbolCommand.put(arg0, command);
		return this;
	}

	/**
	 * Pomoćna metoda za prepoznavanje vrste komande koju je potrebno stvoriti.
	 * 
	 * @param chunks
	 *            dio linije s komandom
	 * @return komanda
	 */
	private Command getCommand(String[] chunks) {
		switch (chunks[0]) {
		case "color":
			Color color = Color.decode("#" + chunks[1]);
			return new ColorCommand(color);
		case "draw":
			double step = Double.parseDouble(chunks[1]);
			return new DrawCommand(step);
		case "rotate":
			double angle = Double.parseDouble(chunks[1]);
			return new RotateCommand(angle);
		case "pop":
			return new PopCommand();
		case "push":
			return new PushCommand();
		case "scale":
			double scale = Double.parseDouble(chunks[1]);
			return new ScaleCommand(scale);
		case "skip":
			double stepp = Double.parseDouble(chunks[1]);
			return new SkipCommand(stepp);
		default:
			throw new IllegalArgumentException("Input can not mathc any command.");
		}
	}

	/**
	 * Metoda dodaje u riječnik znak s pridruženom produkcijom.
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		symbolProduction.put(arg0, arg1);
		return this;

	}

	/**
	 * Metoda postavlja kut te vraća <code>LSystemBuilder</code>.
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		this.angle = arg0;
		return this;
	}

	/**
	 * Metoda postavlja Axiom te vraća <code>LSystemBuilder</code>.
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		this.axiom = arg0;
		return this;
	}

	/**
	 * Metoda postavlja početnu točku te vraća <code>LSystemBuilder</code>.
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		this.origin = new Vector2D(arg0, arg1);
		return this;
	}

	/**
	 * Metoda postavlja duljinu koraka kornjače te vraća <code>LSystemBuilder</code>.
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		this.unitLength = arg0;
		return this;
	}

	/**
	 * Metoda postavlja vrijednost kojom se skalira korak kornjače te vraća <code>LSystemBuilder</code>.
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		this.unitLengthDegreeScaler = arg0;
		return this;
	}
}
