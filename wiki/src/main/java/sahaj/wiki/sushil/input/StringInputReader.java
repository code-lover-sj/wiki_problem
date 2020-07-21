package sahaj.wiki.sushil.input;

import static sahaj.sushil.utils.Constants.ZERO;
import static sahaj.wiki.sushil.constant.ElementType.ANSWER;
import static sahaj.wiki.sushil.constant.ElementType.QUESTION;
import static sahaj.wiki.sushil.constant.ElementType.STATEMENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.sushil.utils.SystemConfig;
import sahaj.wiki.sushil.constant.ElementType;
import sahaj.wiki.sushil.exception.InternalServerException;
import sahaj.wiki.sushil.input.exception.InvalidInputException;
import sahaj.wiki.sushil.parser.GeneralParser;
import sahaj.wiki.sushil.parser.Parser;

/**
 * This class will parse the given String input. It will parse all the statements into as separate {@link String} from
 * the single line paragraph. It will also parse the answers into an individual {@link String} instead of being in a
 * single {@link String} separated by delimiter.
 */
public class StringInputReader extends AbstractInputReader {
    private static final Logger logger = LogManager.getLogger(StringInputReader.class);

    private final Parser parser = new GeneralParser();
    private final SystemConfig sysConfig = new SystemConfig();

    @Override
    public Map<ElementType, List<String>> _readInput(final String source, final List<Exception> errors) {
        logger.info("Parsing the input - {}", source);

        final String[] parsedInput = getParsedInput(source);

        final Map<ElementType, List<String>> parsedInputMap = new EnumMap<>(ElementType.class);

        final int noOfStmts = sysConfig.getIntNoOfStatementsInPara();
        final int noOfQuestions = sysConfig.getIntNoOfQuestions();

        getParsedStatements(parsedInput, parsedInputMap, errors);

        getParsedQuestions(parsedInput, parsedInputMap, noOfStmts, noOfQuestions);

        getParsedAnswers(parsedInput, parsedInputMap, noOfStmts, noOfQuestions, errors);

        return parsedInputMap;
    }

    private void getParsedStatements(final String[] parsedInput, final Map<ElementType, List<String>> parsedInputMap,
            final List<Exception> errors) {
        try {
            final String[] statements = parser.parse(parsedInput[ZERO], sysConfig.getStatementDelimiter());

            if (ArrayUtils.isEmpty(statements)) {
                final String stmtParseErr = "No statements could be parsed in the paragraph.";
                logger.error(stmtParseErr);
                throw new InternalServerException(stmtParseErr);
            }

            List<String> stmtList = new ArrayList<>(Arrays.asList(statements));
            stmtList = stmtList.stream().map(String::trim).collect(Collectors.toList());
            logger.info("Got the statements {}", stmtList);

            parsedInputMap.put(STATEMENT, stmtList);
        } catch (final Exception e) {
            errors.add(e);
        }
    }

    private void getParsedQuestions(final String[] parsedInput, final Map<ElementType, List<String>> parsedInputMap,
            final int noOfStmts, final int noOfQuestions) {
        final ArrayList<String> questions = new ArrayList<>(noOfQuestions);

        questions.addAll(Arrays.asList(Arrays.copyOfRange(parsedInput, noOfStmts, noOfStmts + noOfQuestions)));

        parsedInputMap.put(QUESTION, questions);
    }

    private void getParsedAnswers(final String[] parsedInput, final Map<ElementType, List<String>> parsedInputMap,
            final int noOfStmts, final int noOfQuestions, final List<Exception> errors) {
        try {
            final String answerStrings = parsedInput[noOfStmts + noOfQuestions];
            final String[] answers = answerStrings.split(sysConfig.getAnswerDelimiter());
            if (answers.length != noOfQuestions) {
                throw new InvalidInputException("Number of answers must be same as number of questions.");
            }

            List<String> answerList = new ArrayList<>(Arrays.asList(answers));
            answerList = answerList.stream().map(String::trim).collect(Collectors.toList());
            parsedInputMap.put(ANSWER, answerList);
        } catch (final Exception e) {
            errors.add(e);
        }
    }

    private String[] getParsedInput(final String source) {
        final String[] parsedInput = parser.parse(source, null);

        validator.validateParsedInput(parsedInput);
        return parsedInput;
    }
}
