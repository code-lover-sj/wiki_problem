package sahaj.wiki.sushil.exception;

@SuppressWarnings("serial")
public class InternalError extends Exception {
    public InternalError(final String errorMsg) {
        super(errorMsg);
    }
}
