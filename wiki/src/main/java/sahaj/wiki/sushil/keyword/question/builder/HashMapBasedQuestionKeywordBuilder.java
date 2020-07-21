package sahaj.wiki.sushil.keyword.question.builder;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.sushil.utils.Constants;
import sahaj.wiki.sushil.keyword.KeywordsBuilder;
import sahaj.wiki.sushil.keyword.exception.InvalidQuestionException;
import sahaj.wiki.sushil.parser.GeneralParser;
import sahaj.wiki.sushil.parser.Parser;

/**
 * This class simply matches each keyword with the question ids it appears in.
 */
public class HashMapBasedQuestionKeywordBuilder implements KeywordsBuilder<Map<String, HashSet<Integer>>> {
    private static final Logger logger = LogManager.getLogger(HashMapBasedQuestionKeywordBuilder.class);

    @Override
    public Map<String, HashSet<Integer>> buildKeywords(final List<String> questions) {
        return buildKeywords(questions, new HashMap<>());
    }

    @Override
    public Map<String, HashSet<Integer>> buildKeywords(final List<String> questions,
            Map<String, HashSet<Integer>> questionKeywords) {
        if (CollectionUtils.isEmpty(questions)) {
            logger.warn("No questions were passed to generate keywords from...");
            return Collections.emptyMap();
        }

        if (null == questionKeywords) {
            logger.warn("Null map was passed to store keywords from question. Creating new...");
            questionKeywords = new HashMap<>();
        }


        for (int cnt = 0; cnt < questions.size(); cnt++) {
            final String question = questions.get(cnt);
            parseQuestion(question, cnt, questionKeywords);
        }

        return questionKeywords;
    }

    private void parseQuestion(final String question, final int questionNo,
            final Map<String, HashSet<Integer>> questionKeywords) {
        if (StringUtils.isBlank(question)) {
            final String errMsg = String.format("Question no %d is blank or invalid.", questionNo + 1);
            logger.error(errMsg);
            throw new InvalidQuestionException(errMsg);
        }

        final Parser parser = new GeneralParser();

        final String[] parsedQuestion = parser.parse(question, Constants.SPACE);

        populateMapData(questionNo, questionKeywords, parsedQuestion);
    }

    private void populateMapData(final int questionNo, final Map<String, HashSet<Integer>> questionKeywords,
            final String[] parsedQuestion) {
        for (String keyword : parsedQuestion) {
            keyword = sahaj.sushil.utils.StringUtils.toLowerCaseWithChoppedPunctuation(keyword);

            if (StringUtils.isBlank(keyword) || Constants.COMMON_WORDS.contains(keyword)) {
                logger.debug("Omitting blank or common word {} in question no {}.", keyword, questionNo);
                continue;
            }

            final HashSet<Integer> singleton = new HashSet<>();
            singleton.add(questionNo);

            questionKeywords.merge(keyword, singleton, (v1, v2) -> {
                v1.addAll(v2);
                return v1;
            });
        }
    }
}
