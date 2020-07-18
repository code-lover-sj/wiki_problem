package sahaj.wiki.sushil.keyword.exception;

@SuppressWarnings("serial")
public class InvalidQuestionException extends RuntimeException {
    public InvalidQuestionException(final String message) {
        super(message);
    }
}
