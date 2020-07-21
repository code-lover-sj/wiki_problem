package sahaj.wiki.sushil.statement.match;

import java.util.List;
import java.util.Map;
import sahaj.wiki.sushil.constant.ElementType;

/**
 * When many questions have same number of keywords mapped to the same statement, we need to pick 1 question id. This
 * class offers different strategies to select the question id from collided ids.
 *
 * This class also encapsulates the strategy resolver to be used for each respective strategy.
 *
 * Only RANDOM is supported for now.
 *
 */
public enum QuestionCollisionResolverStrategy {
    RANDOM(RandomQuestionCollisionResolver.RANDOM_QUESTION_COLLISION_RESOLVER),
    WEIGHED_ON_NO_OF_WORDS(WeighedOnNoOfWordsCollisionResolver.WEIGHED_ON_NO_OF_WORDS_RESOLVER),
    CHARACTER_MATCH(CharacterMatchResolver.CHARACTER_MATCH_RESOLVER);

    private final QuestionCollisionResolver resolver;

    private QuestionCollisionResolverStrategy(final QuestionCollisionResolver resolver) {
        this.resolver = resolver;
    }

    public int resolveQuestionCollision(final Map<ElementType, List<String>> input,
            final StatementToAnswerAndQuestionMatcher matchedStmtData) {
        return resolver.resolveCollisionAndGetQuestionNumber(input, matchedStmtData);
    }
}