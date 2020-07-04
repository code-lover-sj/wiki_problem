package sahaj.wiki.sushil.input;

import static sahaj.sushil.utils.Constants.*;
import static sahaj.wiki.sushil.input.constant.InputElementType.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.sushil.utils.Builder;
import sahaj.sushil.utils.SystemConfig;
import sahaj.wiki.sushil.exception.InternalServerException;
import sahaj.wiki.sushil.input.constant.InputElementType;
import sahaj.wiki.sushil.input.exception.InvalidInputException;
import sahaj.wiki.sushil.parser.GeneralParser;
import sahaj.wiki.sushil.parser.Parser;

public class StringInputReader extends AbstractInputReader {
    private static final Logger logger = LogManager.getLogger(StringInputReader.class);

    private final Parser parser = new GeneralParser();
    private final SystemConfig sysConfig = new SystemConfig(DEFAULT_SYS_CONFIG_PROPS_FILE);

    private StringInputReader(final StringInputReaderBuilder builder) {
    }

    private void getParsedStatements(final String[] parsedInput,
            final Map<InputElementType, ArrayList<String>> parsedInputMap) {
        final String[] statements = parser.parse(parsedInput[ZERO], sysConfig.getStatementDelimiter());

        if (ArrayUtils.isEmpty(statements)) {
            final String stmtParseErr = "No statements could be parsed in the paragraph.";
            logger.error(stmtParseErr);
            throw new InternalServerException(stmtParseErr);
        }

        final ArrayList<String> stmtList = new ArrayList<>(Arrays.asList(statements));
        logger.info("Got the statements {}", stmtList);

        parsedInputMap.put(STATEMENT, stmtList);
    }

    private void getParsedQuestions(final String[] parsedInput,
            final Map<InputElementType, ArrayList<String>> parsedInputMap, final int noOfStmts,
            final int noOfQuestions) {
        final ArrayList<String> questions = new ArrayList<>(noOfQuestions);

        /*
         * for (int cnt = noOfStmts; cnt < noOfStmts + noOfQuestions; cnt++) { questions.add(parsedInput[cnt]); }
         */

        questions.addAll(Arrays.asList(Arrays.copyOfRange(parsedInput, noOfStmts, noOfStmts + noOfQuestions)));

        parsedInputMap.put(QUESTION, questions);
    }

    private void getParsedAnswers(final String[] parsedInput,
            final Map<InputElementType, ArrayList<String>> parsedInputMap, final int noOfStmts,
            final int noOfQuestions) {
        final String answerStrings = parsedInput[noOfStmts + noOfQuestions];
        final String[] answers = answerStrings.split(sysConfig.getAnswerDelimiter());
        if (answers.length != noOfQuestions) {
            throw new InvalidInputException("Number of answers must be same as number of questions.");
        }

        final ArrayList<String> answerList = new ArrayList<>(Arrays.asList(answers));

        parsedInputMap.put(ANSWER, answerList);
    }

    private String[] getParsedInput(final String source) {
        final String[] parsedInput = parser.parse(source, null);

        validator.validateParsedInput(parsedInput);
        return parsedInput;
    }

    @Override
    public Map<InputElementType, ArrayList<String>> _readInput(final String source) {
        logger.info("Parsing the input - {}", source);

        final String[] parsedInput = getParsedInput(source);

        final Map<InputElementType, ArrayList<String>> parsedInputMap = new EnumMap<>(InputElementType.class);

        final int noOfStmts = Integer.parseInt(sysConfig.getNoOfStatementsInPara());
        final int noOfQuestions = Integer.parseInt(sysConfig.getNoOfQuestions());

        getParsedStatements(parsedInput, parsedInputMap);

        getParsedQuestions(parsedInput, parsedInputMap, noOfStmts, noOfQuestions);

        getParsedAnswers(parsedInput, parsedInputMap, noOfStmts, noOfQuestions);

        return parsedInputMap;
    }

    public static class StringInputReaderBuilder implements Builder<StringInputReader> {
        @Override
        public StringInputReader build() {
            return new StringInputReader(this);
        }
    }
}
