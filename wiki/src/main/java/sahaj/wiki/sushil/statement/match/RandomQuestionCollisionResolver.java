package sahaj.wiki.sushil.statement.match;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import sahaj.wiki.sushil.constant.ElementType;

/**
 *
 * Chooses any random question id from the passed {@link Set} of question ids. Most inaccurate of the available
 * strategies.
 */
public enum RandomQuestionCollisionResolver implements QuestionCollisionResolver {
    RANDOM_QUESTION_COLLISION_RESOLVER;

    private final Random random = new Random();

    @Override
    public int resolveCollisionAndGetQuestionNumber(final Map<ElementType, List<String>> input, final StatementToAnswerAndQuestionMatcher matchedStmtData) {
        Objects.requireNonNull(matchedStmtData, "Can't process null matched statement data");

        /*final Set<Integer> questionIds = matchedStmtData.getQuestionIds();
        if (CollectionUtils.isEmpty(questionIds)) {
            throw new InvalidArgumentException("No question ids were passed to pick from");
        }

        final int index = random.nextInt(questionIds.size());

        int i = 0;
        for (final Integer questionId : questionIds) {
            if (i == index) {
                return questionId;
            }

            i++;
        }*/

        // Useless. Should never happen.
        return 0;
    }
}
