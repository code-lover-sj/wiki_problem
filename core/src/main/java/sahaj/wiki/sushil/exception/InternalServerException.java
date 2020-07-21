package sahaj.wiki.sushil.exception;

@SuppressWarnings("serial")
public class InternalServerException extends RuntimeException {
    public InternalServerException(final String errorMsg) {
        super(errorMsg);
    }
}
