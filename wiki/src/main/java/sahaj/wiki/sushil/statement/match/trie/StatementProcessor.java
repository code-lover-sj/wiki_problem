package sahaj.wiki.sushil.statement.match.trie;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.sushil.utils.Builder;
import sahaj.sushil.utils.Constants;
import sahaj.sushil.utils.SystemConfig;
import sahaj.wiki.sushil.constant.ElementType;
import sahaj.wiki.sushil.exception.InvalidArgumentException;
import sahaj.wiki.sushil.keyword.trie.Trie;
import sahaj.wiki.sushil.statement.match.StatementToAnswerAndQuestionMatcher;

/**
 * Main processing class which goes over each of the input statement and matches question and answer to it. It then
 * returns the correct sequence of answers when method processAndGetCorrectAnswerIds is called. This class needs to be
 * instantiated using {@link StatementProcessorBuilder}.
 */
public class StatementProcessor {
    private static final Logger logger = LogManager.getLogger(StatementProcessor.class);

    private final SystemConfig sysConfig;

    private final Map<ElementType, List<String>> parsedInput;
    private final Trie<String> answerKeywords;
    private final Map<String, HashSet<Integer>> questionKeywords;

    private StatementProcessor(final StatementProcessorBuilder builder) {
        validateThatAllFieldsAreSet(builder);

        this.parsedInput = builder.rawInput;
        this.answerKeywords = builder.answerKeywords;
        this.questionKeywords = builder.questionKeywords;

        sysConfig = new SystemConfig();
    }

    public List<String> processAndGetCorrectAnswerIds() {
        return processStatements();
    }

    /*
     * Iterate over each statement in the input. Try to match the question and answer for each of it. Return the list of
     * correct answers per sequence.
     */
    private List<String> processStatements() {
        final List<String> stmts = parsedInput.get(ElementType.STATEMENT);
        final TrieBasedStatementParserAndMatcher stmtMatcher = new TrieBasedStatementParserAndMatcher();

        final String[] correctAnswerList = new String[sysConfig.getIntNoOfQuestions()];

        for (int stmtId = 0; stmtId < stmts.size(); stmtId++) {
            final String stmt = stmts.get(stmtId);
            final StatementToAnswerAndQuestionMatcher stmtMatched = stmtMatcher
                    .parseAndMatchStatementWithAnswerAndQuestion(stmt, stmtId, answerKeywords, questionKeywords);

            // THIS APPROACH WAS A FAILURE
            // processEachStatement(stmtMatched, correctAnswerList);

            getAnswerFromStatement(stmtMatched, correctAnswerList);
        }

        return Arrays.asList(correctAnswerList);
    }

    private void getAnswerFromStatement(final StatementToAnswerAndQuestionMatcher stmtMatched,
            final String[] correctAnswerList) {
        final Map<Integer, Integer> questionIdToCountMap = stmtMatched.getQuestionIdToCountMap();
        // If no question was mapped, there is no point in processing.
        if (MapUtils.isEmpty(questionIdToCountMap)) {
            logger.debug("No question was mapped to this statement {}", stmtMatched.getStatementId());
            return;
        }

        final Set<Integer> answerIds = stmtMatched.getAnswerIds();

        if (CollectionUtils.isEmpty(answerIds)) {
            return;
        }

        reduceToSingleQuestionMapping(stmtMatched, questionIdToCountMap);

        final List<String> answers = parsedInput.get(ElementType.ANSWER);


        final int mappedNoOfAnswers = answerIds.size();

        // Only 1 answer was mapped. Set it against already store question index in the statement matcher.
        if (mappedNoOfAnswers == Constants.ONE) {
            final String matchedAnswer = answers.get(answerIds.iterator().next());
            logger.info("Setting answer {} to question index {}.", matchedAnswer, stmtMatched.getQuestionId());
            correctAnswerList[stmtMatched.getQuestionId()] = matchedAnswer;
            return;
        }

        if (mappedNoOfAnswers > Constants.ONE) {
            processForMultipleAnswersOneQuestion(correctAnswerList, stmtMatched);
        }
    }

    private void reduceToSingleQuestionMapping(final StatementToAnswerAndQuestionMatcher stmtMatched,
            final Map<Integer, Integer> questionIdToCountMap) {
        /*
         *  Lets' assume that only 1 question maps to a statement.
         *  When there are multiple questions mapping to a statement,
         *  pick up the question containing more % of keywords w.r.t the total no of words in the statement.
         */
        if (questionIdToCountMap.size() > Constants.ONE) {
            final List<String> questions = parsedInput.get(ElementType.QUESTION);

            int questionId = Integer.MIN_VALUE;
            int maxPercent = 0;

            for (final Entry<Integer, Integer> entry : questionIdToCountMap.entrySet()) {
                final String currenQuestion = questions.get(entry.getKey());
                final int currentPercent = entry.getValue() * Constants.HUNDRED
                        / currenQuestion.split(Constants.SPACE).length;
                if (maxPercent < currentPercent) {
                    questionId = entry.getKey();
                    maxPercent = currentPercent;
                }
            }

            logger.info("Setting question {} to the statement {} as its match % is {}.", questionId,
                    stmtMatched.getStatementId(), maxPercent);
            stmtMatched.setQuestionId(questionId);

            return;
        }

        stmtMatched.setQuestionId(questionIdToCountMap.keySet().iterator().next());
    }


    // FAILED APPROACH------>
    /* private void processEachStatement(final StatementToAnswerAndQuestionMatcher stmtMatched,
            final String[] correctAnswerList) {
        final Set<Integer> questionIds = stmtMatched.getQuestionIds();
        if (CollectionUtils.isEmpty(questionIds)) {
            return;
        }

        final Set<Integer> answerIds = stmtMatched.getAnswerIds();

        if (CollectionUtils.isEmpty(answerIds)) {
            return;
        }

        final List<String> answers = parsedInput.get(ElementType.ANSWER);

        logger.info("Mapping of answer(s) {} and question(s) {} is found for this statement {}",
                stmtMatched.getAnswerIds(), stmtMatched.getQuestionIds(), stmtMatched.getStatementId());*/

    /*
     * If 1 question and 1 answer id were found against this statement, it is straightforward.
     *
     *
     * THESE ASSUMPTIONS ARE WRONG -----> Many questions and answers can perfectly map same statement and both be very different.
     * eg.
     * Stmt = I like to play Cricket.
     * Q1 = Who likes to play Cricket?
     * Q2 = What do I like to play?
     * A1 = I
     * A2 = Cricket
     *
     * Not only these assumptions, but whole Trie based solution is wrong as it will also fail to directly relate any question with answer.
     * Above example also highlighted this issue.
     *
     * <strike>
     * If more answers than questions were mapped, that means there are 2 questions which will have same answer.
     * But we have got only 1 of such questions mapped to this statement. Any of the question ids can be thus
     * mapped to this question id for now. If same number of questions and answers were mapped, any answer can be
     * against any of matched question number sequence.
     *
     * Situation gets tricky when there are more questions mapped to an answer. It means there are multiple
     * questions with same number of keywords matched with this statement. Then we need to determine which question
     * maps more closely with this statement. We should provide different strategies to resolve the collision. We
     * should accept the strategy to be used as a property. As of now, only RANDOM strategy is supported.
     * </strike>
     *
     */

    /*final int mappedNoOfQuestions = questionIds.size();
    final int mappedNoOfAnswers = answerIds.size();

    if (mappedNoOfAnswers == Constants.ONE && mappedNoOfQuestions == Constants.ONE) {
        final String matchedAnswer = answers.get(answerIds.iterator().next());
        logger.info("Setting answer {} to all mapped question index {}.", matchedAnswer,
                stmtMatched.getQuestionIds());
        questionIds.forEach(questionId -> correctAnswerList[questionId] = matchedAnswer);
        return;
    }

    if (mappedNoOfAnswers > Constants.ONE && mappedNoOfQuestions == Constants.ONE) {
        processForMultipleAnswersOneQuestion(correctAnswerList, stmtMatched);
        return;
    }*/


    // Below code is crap.

    /*
     * if (mappedNoOfQuestions == mappedNoOfAnswers || mappedNoOfAnswers > mappedNoOfQuestions) { final String
     * matchedAnswer = answers.get(answerIds.iterator().next());
     *
     * logger.debug("Setting same answer {} to all mapped question indices {}.", matchedAnswer,
     * stmtMatched.getQuestionIds()); questionIds.forEach(questionId -> correctAnswerList[questionId] =
     * matchedAnswer); } else { // More questions than answers are mapped final QuestionCollisionResolverStrategy
     * collisionResolver = QuestionCollisionResolverStrategy
     * .valueOf(sysConfig.getQuestionCollisionResolverStrategy()); final int questionId =
     * collisionResolver.resolveQuestionCollision(rawInput, stmtMatched); }
     */
    // }

    private void processForMultipleAnswersOneQuestion(final String[] correctAnswerList,
            final StatementToAnswerAndQuestionMatcher stmtMatched) {
        final int questionId = stmtMatched.getQuestionId();

        final Set<Integer> answerIds = stmtMatched.getAnswerIds();

        final List<String> answers = parsedInput.get(ElementType.ANSWER);
        int maxLength = 0;
        String answer = null;

        for (final Integer answerId : answerIds) {
            final String currentAnswer = answers.get(answerId);

            if (maxLength < currentAnswer.length()) {
                answer = currentAnswer;
                maxLength = answer.length();
            }
        }

        correctAnswerList[questionId] = answer;
    }

    private void validateThatAllFieldsAreSet(final StatementProcessorBuilder builder) {
        if (MapUtils.isEmpty(builder.rawInput)) {
            final String errMsg = "Invalid input (null or empty) was passed.";
            logger.error(errMsg);
            throw new InvalidArgumentException(errMsg);
        }

        final List<String> stmts = builder.rawInput.get(ElementType.STATEMENT);
        if (CollectionUtils.isEmpty(stmts)
                || stmts.stream().filter(StringUtils::isBlank).collect(Collectors.toList()).size() > Constants.ZERO) {
            final String errorMessage = String.format("Invalid statements were passed as input %s", stmts);
            logger.error(errorMessage);
            throw new InvalidArgumentException(errorMessage);
        }


        if (MapUtils.isEmpty(builder.questionKeywords)) {
            final String errMsg = "Invalid question keywords (null or empty) were passed.";
            logger.error(errMsg);
            throw new InvalidArgumentException(errMsg);
        }

        if (null == builder.answerKeywords || builder.answerKeywords.isEmpty()) {
            final String errMsg = "Invalid answer keywords trie (null or empty) was passed.";
            logger.error(errMsg);
            throw new InvalidArgumentException(errMsg);
        }
    }


    public static class StatementProcessorBuilder implements Builder<StatementProcessor> {
        private Map<ElementType, List<String>> rawInput;
        private Trie<String> answerKeywords;
        private Map<String, HashSet<Integer>> questionKeywords;

        public StatementProcessorBuilder input(final Map<ElementType, List<String>> input) {
            this.rawInput = input;
            return this;
        }

        public StatementProcessorBuilder answerKeywords(final Trie<String> answerKeywords) {
            this.answerKeywords = answerKeywords;
            return this;
        }

        public StatementProcessorBuilder questionKeywords(final Map<String, HashSet<Integer>> questionKeywords) {
            this.questionKeywords = questionKeywords;
            return this;
        }

        @Override
        public StatementProcessor build() {
            return new StatementProcessor(this);
        }
    }
}
