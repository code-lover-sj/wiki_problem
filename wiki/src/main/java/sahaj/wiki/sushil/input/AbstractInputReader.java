package sahaj.wiki.sushil.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.wiki.sushil.constant.ElementType;
import sahaj.wiki.sushil.exception.ErrorMessageConsolidator;
import sahaj.wiki.sushil.exception.InvalidArgumentException;
import sahaj.wiki.sushil.input.validator.InputValidator;
import sahaj.wiki.sushil.input.validator.InputValidatorImpl;

/**
 * This class offers the functionality to parse the given input into separated {@link String} objects. It returns
 * {@link Map} containing segregation of each of the type with the {@link String} objects of that type.
 */
public abstract class AbstractInputReader implements InputReader {
    private static final Logger logger = LogManager.getLogger(AbstractInputReader.class);

    protected final InputValidator<String> validator = new InputValidatorImpl();

    protected abstract Map<ElementType, List<String>> _readInput(String source, List<Exception> errors);

    @Override
    public Map<ElementType, List<String>> readInput(final String source) {
        final ArrayList<Exception> errors = new ArrayList<>();

        Map<ElementType, List<String>> parsedInput = Collections.emptyMap();
        if (validator.validate(source, errors) && CollectionUtils.isEmpty(errors)) {
            parsedInput = _readInput(source, errors);
        }

        if (CollectionUtils.isNotEmpty(errors)) {
            logger.error("Error/s found while vaidating the input {}. Error/s -> {}", source, errors);
            throw new InvalidArgumentException(ErrorMessageConsolidator.getConsolidatedErrorMessage(errors));
        }

        return parsedInput;
    }
}
