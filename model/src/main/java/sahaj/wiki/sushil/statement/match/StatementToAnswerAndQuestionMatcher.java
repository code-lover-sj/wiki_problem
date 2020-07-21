package sahaj.wiki.sushil.statement.match;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import sahaj.sushil.utils.Constants;

/**
 * This class holds the mapping of ids of answers and question mapped with a statement id. While parsing each statement,
 * we may get multiple questions and answers pointing to current statement. This class encapsulates that mapping and
 * allows processor to make decisions based on it.
 */
public final class StatementToAnswerAndQuestionMatcher {
    // May be needed for persistence in case the execution needs to be saved to continue later.
    /*
     * private Map<String, Set<TrieNode<String>>> answerMatch; private Map<String, Set<Integer>> questionMatch;
     */

    private final int statementId;
    private int questionId;
    private Set<Integer> answerIds;
    private Map<Integer, Integer> questionIdToCountMap;

    public StatementToAnswerAndQuestionMatcher(final int statementId) {
        this.statementId = statementId;

        questionId = Integer.MIN_VALUE;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(final int questionId) {
        this.questionId = questionId;
    }

    public Set<Integer> getAnswerIds() {
        return isAnAnswer() ? Collections.unmodifiableSet(answerIds) : Collections.emptySet();
    }

    public void setAnswerIds(final Set<Integer> answerIds) {
        this.answerIds = answerIds;
    }

    public Map<Integer, Integer> getQuestionIdToCountMap() {
        return MapUtils.isEmpty(questionIdToCountMap) ? Collections.emptyMap()
                : Collections.unmodifiableMap(questionIdToCountMap);
    }

    public void setQuestionIdToCountMap(final Map<Integer, Integer> questionToCountMap) {
        this.questionIdToCountMap = questionToCountMap;
    }

    public int getStatementId() {
        return statementId;
    }

    public boolean isAnAnswer() {
        return CollectionUtils.isNotEmpty(answerIds) && answerIds.size() >= Constants.ONE;
    }

    public void addAnswer(final int answerId) {
        if (CollectionUtils.isEmpty(answerIds)) {
            answerIds = new HashSet<>(Constants.DEFAULT_NO_OF_ANSWERS);
        }

        answerIds.add(answerId);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("StatementToAnswerAndQuestionMatcher [statementId=").append(statementId).append(", questionId=")
        .append(questionId).append(", ");
        if (answerIds != null) {
            builder.append("answerIds=").append(answerIds).append(", ");
        }
        if (questionIdToCountMap != null) {
            builder.append("questionIdToCountMap=").append(questionIdToCountMap);
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerIds, questionId, questionIdToCountMap, statementId);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StatementToAnswerAndQuestionMatcher)) {
            return false;
        }
        final StatementToAnswerAndQuestionMatcher other = (StatementToAnswerAndQuestionMatcher) obj;
        return Objects.equals(answerIds, other.answerIds) && questionId == other.questionId
                && Objects.equals(questionIdToCountMap, other.questionIdToCountMap) && statementId == other.statementId;
    }
}
