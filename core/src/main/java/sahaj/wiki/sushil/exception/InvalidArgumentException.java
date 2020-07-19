package sahaj.wiki.sushil.exception;

@SuppressWarnings("serial")
public class InvalidArgumentException extends RuntimeException {
	public InvalidArgumentException(String errorMessage) {
		super(errorMessage);
	}
}
