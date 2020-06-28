package sahaj.wiki.sushil.input.exception;

public class UnsupportedInputTypeException extends RuntimeException {
    private static final long serialVersionUID = -2575788401947551604L;

    public UnsupportedInputTypeException(final String errorMessage) {
        super(errorMessage);
    }
}
