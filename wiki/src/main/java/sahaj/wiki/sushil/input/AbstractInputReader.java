package sahaj.wiki.sushil.input;

import static sahaj.sushil.utils.Constants.*;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.wiki.sushil.exception.ErrorMessageConsolidator;
import sahaj.wiki.sushil.exception.InvalidArgumentException;
import sahaj.wiki.sushil.input.constant.InputElementType;
import sahaj.wiki.sushil.input.validator.InputValidator;
import sahaj.wiki.sushil.validator.Validator;

public abstract class AbstractInputReader implements InputReader {
    private static final Logger logger = LogManager.getLogger(AbstractInputReader.class);

    protected final Validator<String> validator = new InputValidator();

    protected abstract Map<InputElementType, ArrayList<String>> _readInput(String source);

    @Override
    public Map<InputElementType, ArrayList<String>> readInput(final String source) {
        final ArrayList<Exception> errors = new ArrayList<>(ONE);

        if (validator.validate(source, errors) && CollectionUtils.isEmpty(errors)) {
            return _readInput(source);
        }

        logger.error("Error/s found while vaidating the input {}. Error/s -> {}", source, errors);
        throw new InvalidArgumentException(ErrorMessageConsolidator.getConsolidatedErrorMessage(errors));
    }
}