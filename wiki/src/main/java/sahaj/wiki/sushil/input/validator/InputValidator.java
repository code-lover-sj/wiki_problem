package sahaj.wiki.sushil.input.validator;

import static sahaj.sushil.utils.Constants.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.wiki.sushil.exception.InvalidArgumentException;
import sahaj.wiki.sushil.validator.Validator;

public class InputValidator implements Validator<String> {
    private static final Logger logger = LogManager.getLogger(InputValidator.class);

    @Override
    public boolean validate(final String input, List<Exception> errors) {
        if (null == errors) {
            errors = new ArrayList<>(ONE);
        }

        logger.debug("Validating input {}", input);

        if (StringUtils.isBlank(input)) {
            logger.warn("Null or blank input is passed...");
            errors.add(new InvalidArgumentException("Null or blank input"));
        }

        return errors.isEmpty();
    }
}
