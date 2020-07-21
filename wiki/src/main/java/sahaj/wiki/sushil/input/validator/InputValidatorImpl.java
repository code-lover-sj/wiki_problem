package sahaj.wiki.sushil.input.validator;

import static sahaj.sushil.utils.Constants.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.sushil.utils.SystemConfig;
import sahaj.wiki.sushil.exception.InternalServerException;
import sahaj.wiki.sushil.exception.InvalidArgumentException;
import sahaj.wiki.sushil.input.exception.InvalidInputException;

public class InputValidatorImpl implements InputValidator<String> {
    private static final Logger logger = LogManager.getLogger(InputValidatorImpl.class);

    private final SystemConfig sysConfig = new SystemConfig();

    @Override
    public boolean validate(final String input, List<Exception> errors) {
        if (null == errors) {
            errors = new ArrayList<>(ONE);
        }

        logger.debug("Validating input  - {}", input);

        if (StringUtils.isBlank(input)) {
            logger.warn("Null or blank input is passed...");
            errors.add(new InvalidArgumentException("Null or blank input"));
        }

        return errors.isEmpty();
    }

    @Override
    public void validateParsedInput(final String[] parsedInput) {
        if (ArrayUtils.isEmpty(parsedInput)) {
            throw new InternalServerException("Some error occurred while parsing input");
        }

        final int noOfQuestions = sysConfig.getIntNoOfQuestions();
        final int noOfStatements = sysConfig.getIntNoOfStatementsInPara();
        final int noOfAnswers = sysConfig.getIntNoOfAnswers();

        final int totalStrings = noOfAnswers + noOfStatements + noOfQuestions;

        if (parsedInput.length != totalStrings) {
            throw new InvalidInputException(String.format("Invalid input. Expected total %d strings. But were %d.",
                    totalStrings, parsedInput.length));
        }

        validateNoOfQuestions(parsedInput, noOfQuestions, noOfStatements);
    }

    private void validateNoOfQuestions(final String[] parsedInput, final int noOfQuestions, final int noOfStatements) {
        final int questionStartIndex = parsedInput.length - (noOfStatements + noOfQuestions);

        final ArrayList<String> erroneousQuestions = new ArrayList<>(noOfQuestions);

        for (int index = questionStartIndex; index < questionStartIndex + noOfQuestions; index++) {
            if (!parsedInput[index].endsWith(QUESTION_MARK)) {
                erroneousQuestions.add(parsedInput[index]);
            }
        }

        if (CollectionUtils.isNotEmpty(erroneousQuestions)) {
            logger.error("The strings {} don't look like a question. Question must end with {}.", erroneousQuestions,
                    QUESTION_MARK);
            throw new InvalidInputException(
                    String.format("The Strings %s don't look like a question. Question must end with %s.",
                            erroneousQuestions, QUESTION_MARK));
        }
    }
}
