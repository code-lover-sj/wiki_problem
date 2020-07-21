package sahaj.wiki.sushil.keyword.answer.builder;

import static sahaj.sushil.utils.Constants.*;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.wiki.sushil.keyword.KeywordsBuilder;
import sahaj.wiki.sushil.keyword.trie.Trie;
import sahaj.wiki.sushil.keyword.trie.TrieNode;

/**
 * This class will build the {@link Trie} of type {@link String} for the keywords appearing in all the questions.
 * 
 *                              root
 *                               |
 *          _____________________________________________                                 
 *         |                                             |
 *       This                                          Answer     
 *         |                                             |
 *        is                                            two(id = 1)
 *         |                                             
 *       answer                                             
 *         |        
 *     ______________
 *    |              |
 *    one(id = 2)  three(id = 0)
 *    
 */
public class TrieBasedAnswerKeywordBuilder implements KeywordsBuilder<Trie<String>> {
    private static final Logger logger = LogManager.getLogger(TrieBasedAnswerKeywordBuilder.class);

    @Override
    public Trie<String> buildKeywords(final List<String> answers) {
        return buildKeywords(answers, null);
    }

    @Override
    public Trie<String> buildKeywords(final List<String> answers, Trie<String> trie) {
        if (null == trie) {
            logger.warn("Null trie was passed to store keywaords from answers. Creating new");
            trie = new Trie<>();
        }

        int answerId = 0;

        for (final String answer : answers) {
            final String[] answerTerms = answer.split(SPACE);

            TrieNode<String> added = null;
            for (String term : answerTerms) {
                term = sahaj.sushil.utils.StringUtils.toLowerCaseWithChoppedPunctuation(term);

                if (StringUtils.isBlank(term)) {
                    continue;
                }

                added = trie.add(added, term);
            }

            if (null != added) {
                added.addId(answerId);
                answerId++;
            }
        }

        return trie;
    }
}
