package sahaj.wiki.sushil.validator;

import java.util.List;

public interface Validator<T> {
    boolean validate(final T input, final List<Exception> errors);
}
