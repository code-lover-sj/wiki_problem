package sahaj.wiki.sushil.statement.match;

import java.util.List;
import java.util.Map;

import sahaj.wiki.sushil.constant.ElementType;

public interface QuestionCollisionResolver {
    int resolveCollisionAndGetQuestionNumber(Map<ElementType, List<String>> input, StatementToAnswerAndQuestionMatcher matchedStmtData);
}
