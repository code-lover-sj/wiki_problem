package sahaj.wiki.sushil.statement.match;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import sahaj.sushil.utils.Constants;

public final class StatementToAnswerAndQuestionMatcher {
    // May be needed for persistence in case the execution needs to be saved to continue later.
    /*
     * private Map<String, Set<TrieNode<String>>> answerMatch; private Map<String, Set<Integer>> questionMatch;
     */

    private final int statementId;
    private Set<Integer> questionIds;
    private Set<Integer> answerIds;

    public StatementToAnswerAndQuestionMatcher(final int statementId) {
        this.statementId = statementId;
    }

    public Set<Integer> getQuestionIds() {
        return CollectionUtils.isEmpty(questionIds) ? Collections.emptySet()
                : Collections.unmodifiableSet(questionIds);
    }

    public void setQuestionIds(final Set<Integer> questionIds) {
        this.questionIds = questionIds;
    }

    public Set<Integer> getAnswerIds() {
        return isAnAnswer() ? Collections.unmodifiableSet(answerIds) : Collections.emptySet();
    }

    public void setAnswerIds(final Set<Integer> answerIds) {
        this.answerIds = answerIds;
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

    public void addQuestion(final int questionId) {
        if (CollectionUtils.isEmpty(questionIds)) {
            questionIds = new HashSet<>(Constants.DEFAULT_NO_OF_QUESTIONS);
        }

        questionIds.add(questionId);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("StatementToAnswerAndQuestionMatcher [statementId=").append(statementId).append(", ");
        if (questionIds != null) {
            builder.append("questionIdToCount=").append(questionIds).append(", ");
        }
        if (answerIds != null) {
            builder.append("answerIds=").append(answerIds);
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerIds, questionIds, statementId);
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
        return Objects.equals(answerIds, other.answerIds) && Objects.equals(questionIds, other.questionIds)
                && statementId == other.statementId;
    }
}
