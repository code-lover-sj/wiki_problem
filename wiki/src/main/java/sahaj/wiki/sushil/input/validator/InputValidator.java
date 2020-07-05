package sahaj.wiki.sushil.input.validator;

import sahaj.wiki.sushil.validator.Validator;

public interface InputValidator<T> extends Validator<T> {
    void validateParsedInput(final T[] parsedInput);
}
