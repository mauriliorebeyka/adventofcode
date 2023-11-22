package adventofcode.exceptions;

public class InputParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3922896646652376739L;

	public InputParseException(String message, Throwable e) {
		super(message + ": " + e.getMessage(), e);
	}
	
	public InputParseException(Throwable e) {
		super(e);
	}
}
