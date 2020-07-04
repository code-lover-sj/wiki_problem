package sahaj.wiki.sushil.exception;

public class InternalError extends Exception {
    private final String errorMessage;

    public InternalError(final String errorMsg) {
        super(errorMsg);
        this.errorMessage = errorMsg;
    }
}
