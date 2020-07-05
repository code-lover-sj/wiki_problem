package sahaj.wiki.sushil.input.exception;

@SuppressWarnings("serial")
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(final String errorMessage) {
        super(errorMessage);
    }
}
