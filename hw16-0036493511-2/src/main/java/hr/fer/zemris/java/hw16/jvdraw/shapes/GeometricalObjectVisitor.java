package hr.fer.zemris.java.hw16.jvdraw.shapes;

/**
 * Sučelje, definira metode koje visitori trebaju imati.
 * 
 * @author Filip
 *
 */
public interface GeometricalObjectVisitor {
	/**
	 * Ako posjećuje element {@link Line}
	 * 
	 * @param line
	 *            {@link Line}
	 */
	public abstract void visit(Line line);

	/**
	 * Ako posjećuje element {@link Circle}
	 * 
	 * @param circle
	 *            {@link Circle}
	 */
	public abstract void visit(Circle circle);

	/**
	 * Ako posjećuje element {@link FilledCircle}
	 * 
	 * @param filledCircle
	 *            {@link FilledCircle}
	 */
	public abstract void visit(FilledCircle filledCircle);
}