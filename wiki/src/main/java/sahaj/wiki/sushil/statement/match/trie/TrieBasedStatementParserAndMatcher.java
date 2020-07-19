package sahaj.wiki.sushil.statement.match.trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
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


        final Set<TrieNode<String>> foundAnswerTerms = new HashSet<>();
        final Map<Integer, Integer> questionToCountMap = new HashMap<>();

        for (final String term : parsedStmt) {
            final String lowerCaseChoopedTerm = sahaj.sushil.utils.StringUtils.toLowerCaseWithChoppedPunctuation(term);
            populateMatchedAnswerTerms(answerKeywords, foundAnswerTerms, lowerCaseChoopedTerm);
            populateMatchedQuestions(questionKeywords, questionToCountMap, lowerCaseChoopedTerm);
        }

        return populateStmtMatcherData(stmtId, foundAnswerTerms, questionToCountMap);
    }

    private StatementToAnswerAndQuestionMatcher populateStmtMatcherData(final int stmtId,
            final Set<TrieNode<String>> foundAnswerTerms, final Map<Integer, Integer> questionToCountMap) {
        final StatementToAnswerAndQuestionMatcher stmtMatcher = new StatementToAnswerAndQuestionMatcher(stmtId);

        setAnswerIds(foundAnswerTerms, stmtMatcher);

        setQuestionIds(questionToCountMap, stmtMatcher);

        return stmtMatcher;
    }

    private void setQuestionIds(final Map<Integer, Integer> questionToCountMap,
            final StatementToAnswerAndQuestionMatcher stmtMatcher) {
        if (MapUtils.isNotEmpty(questionToCountMap)) {
            Integer highestCountSoFar = Integer.MIN_VALUE;

            final Set<Integer> questionIds = new HashSet<>(Integer.parseInt(sysConfig.getNoOfQuestions()) * 2);

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

            stmtMatcher.setQuestionIds(questionIds);
        }
    }

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

        for (final TrieNode<String> trieNode : foundAnswerTerms) {
            containsAnswer = trieNode.isLeaf();

            // final TrieNode<String> foundAnsTerm = answerKeywords.find(trieNode, term);
            final TrieNode<String> foundAnsTerm = trieNode.getChildren().get(term);

            if (null != foundAnsTerm) {
                logger.debug("Term {} is found under {}. Adding to the found answer terms.", term, trieNode);
                newFoundTerms.add(foundAnsTerm);
            }
        }

        if (CollectionUtils.isNotEmpty(newFoundTerms)) {
            foundUnderNode = true;
            foundAnswerTerms.addAll(newFoundTerms);
        }

        final TrieNode<String> foundAnsTerm = answerKeywords.findUnderRoot(term);
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
