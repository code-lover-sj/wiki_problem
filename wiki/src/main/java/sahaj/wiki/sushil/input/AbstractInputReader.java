package sahaj.wiki.sushil.input;

import static sahaj.sushil.utils.Constants.*;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.wiki.sushil.constant.ElementType;
import sahaj.wiki.sushil.exception.ErrorMessageConsolidator;
import sahaj.wiki.sushil.exception.InvalidArgumentException;
import sahaj.wiki.sushil.input.validator.InputValidator;
import sahaj.wiki.sushil.input.validator.InputValidatorImpl;

public abstract class AbstractInputReader implements InputReader {
    private static final Logger logger = LogManager.getLogger(AbstractInputReader.class);

    protected final InputValidator<String> validator = new InputValidatorImpl();

    protected abstract Map<ElementType, ArrayList<String>> _readInput(String source);

    @Override
    public Map<ElementType, ArrayList<String>> readInput(final String source) {
        final ArrayList<Exception> errors = new ArrayList<>(ONE);

        if (validator.validate(source, errors) && CollectionUtils.isEmpty(errors)) {
            return _readInput(source);
        }

        logger.error("Error/s found while vaidating the input {}. Error/s -> {}", source, errors);
        throw new InvalidArgumentException(ErrorMessageConsolidator.getConsolidatedErrorMessage(errors));
    }
}
