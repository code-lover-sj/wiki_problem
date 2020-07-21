package sahaj.wiki.sushil.statement.match.trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.sushil.utils.Constants;
import sahaj.sushil.utils.SystemConfig;
import sahaj.wiki.sushil.exception.InvalidArgumentException;
import sahaj.wiki.sushil.keyword.trie.Trie;
import sahaj.wiki.sushil.keyword.trie.TrieNode;
import sahaj.wiki.sushil.parser.GeneralParser;
import sahaj.wiki.sushil.parser.Parser;
import sahaj.wiki.sushil.statement.match.StatementToAnswerAndQuestionMatcher;

/**
 * This class tries to match each of the word in the statement to that in an answer and question keywords. It returns
 * {@link StatementToAnswerAndQuestionMatcher} with this match data.
 */
public class TrieBasedStatementParserAndMatcher {
    private static final Logger logger = LogManager.getLogger(TrieBasedStatementParserAndMatcher.class);

    private final SystemConfig sysConfig = new SystemConfig();

    public StatementToAnswerAndQuestionMatcher parseAndMatchStatementWithAnswerAndQuestion(final String stmt,
            final int stmtId, final Trie<String> answerKeywords, final Map<String, HashSet<Integer>> questionKeywords) {
        if (StringUtils.isBlank(stmt)) {
            final String errorMessage = "Invalid statement";
            logger.error(errorMessage);
            throw new InvalidArgumentException(errorMessage);
        }

        final Parser parser = new GeneralParser();
        final String[] parsedStmt = parser.parse(stmt, Constants.SPACE);


        return processTerms(stmtId, answerKeywords, questionKeywords, parsedStmt);
    }

    private StatementToAnswerAndQuestionMatcher processTerms(final int stmtId, final Trie<String> answerKeywords,
            final Map<String, HashSet<Integer>> questionKeywords, final String[] parsedStmt) {
        final Set<TrieNode<String>> foundAnswerTerms = new HashSet<>();
        final Map<Integer, Integer> questionToCountMap = new HashMap<>();

        for (final String term : parsedStmt) {
            final String lowerCaseChoopedTerm = sahaj.sushil.utils.StringUtils.toLowerCaseWithChoppedPunctuation(term);
            if (StringUtils.isBlank(term)) {
                continue;
            }
            populateMatchedAnswerTerms(answerKeywords, foundAnswerTerms, lowerCaseChoopedTerm);
            populateMatchedQuestions(questionKeywords, questionToCountMap, lowerCaseChoopedTerm);
        }

        return populateStmtMatcherData(stmtId, foundAnswerTerms, questionToCountMap);
    }

    private StatementToAnswerAndQuestionMatcher populateStmtMatcherData(final int stmtId,
            final Set<TrieNode<String>> foundAnswerTerms, final Map<Integer, Integer> questionToCountMap) {
        final StatementToAnswerAndQuestionMatcher stmtMatcher = new StatementToAnswerAndQuestionMatcher(stmtId);

        setAnswerIds(foundAnswerTerms, stmtMatcher);

        stmtMatcher.setQuestionIdToCountMap(questionToCountMap);

        // setQuestionIds(questionToCountMap, stmtMatcher);

        return stmtMatcher;
    }

    /**
     * THIS METHOD IS WRONG. THERE CAN ALWAYS BE MANY QUESTIONS MAPPING TO SAME STATEMENT. ONLY NO OF KEYWRODS MATCHED
     * ARE OF NO USE.
     *
     * This method sets the question ids in the statement matcher. It sets the question id with maximum count of
     * keywords matched with this statement. If many questions have same count, it sets all of those ids in the matcher.
     *
     */
    /*private void setQuestionIds(final Map<Integer, Integer> questionToCountMap,
            final StatementToAnswerAndQuestionMatcher stmtMatcher) {
        if (MapUtils.isNotEmpty(questionToCountMap)) {
            Integer highestCountSoFar = Integer.MIN_VALUE;

            final Set<Integer> questionIds = new HashSet<>(sysConfig.getIntNoOfQuestions() * 2);

            for (final Entry<Integer, Integer> entry : questionToCountMap.entrySet()) {
                final int comparison = entry.getValue().compareTo(highestCountSoFar);
                if (comparison >= Constants.ZERO) {
                    if (comparison > Constants.ZERO) {
                        questionIds.clear();
                        highestCountSoFar = entry.getValue();
                    }

                    questionIds.add(entry.getKey());
                }
            }

            // stmtMatcher.setQuestionIds(questionIds);
        }
    }*/

    private void setAnswerIds(final Set<TrieNode<String>> foundAnswerTerms,
            final StatementToAnswerAndQuestionMatcher stmtMatcher) {
        if (CollectionUtils.isEmpty(foundAnswerTerms)) {
            return;
        }

        final Set<Integer> answerIds = foundAnswerTerms.stream().filter(TrieNode::isLeaf)
                .flatMap(leaf -> leaf.getIds().stream()).collect(Collectors.toSet());

        if (CollectionUtils.isNotEmpty(answerIds)) {
            stmtMatcher.setAnswerIds(answerIds);
        }
    }

    private void populateMatchedQuestions(final Map<String, HashSet<Integer>> questionKeywords,
            final Map<Integer, Integer> questionToCountMap, final String term) {
        final HashSet<Integer> matchedQuestions = questionKeywords.get(term);

        if (CollectionUtils.isEmpty(matchedQuestions)) {
            logger.debug("Term {} not found in any question.", term);
            return;
        }

        for (final Integer queId : matchedQuestions) {
            questionToCountMap.merge(queId, Constants.ONE, (oldCount, newCount) -> oldCount + newCount);
        }
    }

    private void populateMatchedAnswerTerms(final Trie<String> answerKeywords,
            final Set<TrieNode<String>> foundAnswerTerms, final String term) {
        boolean foundUnderNode = false;
        boolean containsAnswer = false;
        final Set<TrieNode<String>> newFoundTerms = new HashSet<>();
        final Set<TrieNode<String>> parentTerms = new HashSet<>(foundAnswerTerms.size() * 2);

        for (final TrieNode<String> parentTerm : foundAnswerTerms) {
            // If any of the already found terms marks the full given answer, preserve the found terms.
            if (!containsAnswer) {
                containsAnswer = parentTerm.isLeaf();
                // Leaf node must not be cleared as it contains the answer for sure.
                if (containsAnswer) {
                    parentTerms.add(parentTerm);
                }
            }

            final TrieNode<String> foundAnsTerm = parentTerm.getChildren().get(term);
            if (null != foundAnsTerm) {
                logger.debug("Term {} is found under {}. Adding to the found answer terms.", term, parentTerm);
                newFoundTerms.add(foundAnsTerm);
                parentTerms.add(parentTerm);
            }
        }

        if (CollectionUtils.isNotEmpty(newFoundTerms)) {
            // Clear parents under which the keyword was not found.
            foundAnswerTerms.retainAll(parentTerms);
            foundUnderNode = true;
            foundAnswerTerms.addAll(newFoundTerms);
        }


        // Always look for the term under the root as well since each statement may contain more than 1 answers.
        final TrieNode<String> foundAnsTerm = answerKeywords.findUnderRoot(term);

        /*
         * If current term is neither found under root nor under any of the previously found nodes, that means the
         * keyword chain is broken. Since answer is perfect substring of the statement, one of the below needs to be
         * true -
         * 1. We should have already got an answer. </br>
         * 2. This word should be a child of the root to mark possibility of new answer </br>
         * 3. This word should be a child of at least one of the already found nodes
         *
         * If none of above conditions is matched, it means the answer is not possible from found nodes. Clear those.
         */

        if (null == foundAnsTerm && !containsAnswer && CollectionUtils.isNotEmpty(foundAnswerTerms)
                && !foundUnderNode) {
            logger.info(
                    "Term {} is not found under the root or any other previously found node. Clearing all previously saved nodes.",
                    term);
            foundAnswerTerms.clear();
        } else if (null != foundAnsTerm) {
            logger.debug("Term {} found under root. Adding to the found answer terms.", term);
            foundAnswerTerms.add(foundAnsTerm);
        }
    }
}
