package sahaj.wiki.sushil.statement.match;

import java.util.List;
import java.util.Map;

import sahaj.sushil.utils.Constants;
import sahaj.wiki.sushil.constant.ElementType;

/**
 * This strategy will compare each of the question's each character with the current statement. This strategy is likely
 * to give more accurate result compared to others.
 *
 */
public enum CharacterMatchResolver implements QuestionCollisionResolver {
    CHARACTER_MATCH_RESOLVER;

    @Override
    public int resolveCollisionAndGetQuestionNumber(final Map<ElementType, List<String>> input, final StatementToAnswerAndQuestionMatcher matchedStmtData) {
        throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION);
    }
}
