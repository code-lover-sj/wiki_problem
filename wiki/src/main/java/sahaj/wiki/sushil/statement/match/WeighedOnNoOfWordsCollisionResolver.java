package sahaj.wiki.sushil.statement.match;

import java.util.List;
import java.util.Map;

import sahaj.sushil.utils.Constants;
import sahaj.wiki.sushil.constant.ElementType;

/**
 * This class evaluates each collided question based on the total number of words in it. Question with lesser words will
 * be chosen assuming that the match % with given statement is higher.
 */
public enum WeighedOnNoOfWordsCollisionResolver implements QuestionCollisionResolver {
    WEIGHED_ON_NO_OF_WORDS_RESOLVER;

    @Override
    public int resolveCollisionAndGetQuestionNumber(final Map<ElementType, List<String>> input, final StatementToAnswerAndQuestionMatcher matchedStmtData) {
        throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION);
    }
}
