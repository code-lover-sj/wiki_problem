package sahaj.wiki.sushil.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import sahaj.wiki.sushil.keyword.KeywordsBuilder;
import sahaj.wiki.sushil.keyword.answer.holder.TrieBasedAnswerKeywordBuilder;
import sahaj.wiki.sushil.keyword.question.holder.HashMapBasedQuestionKeywordHolder;
import sahaj.wiki.sushil.keyword.trie.Trie;

public class TestHelper {
    public TestHelper() {
    }

    public static Trie<String> getAnswerTrie() {
        final String answer1 = "Pt Jawaharlal Neharu was the first Prime Minister of India.";
        final String answer2 = "Hirakund is the largest dam in the country.";
        final String answer3 = "Cricket!";

        final KeywordsBuilder<Trie<String>> answerKeywordsHolder = new TrieBasedAnswerKeywordBuilder();
        return answerKeywordsHolder.buildKeywords(Arrays.asList(answer1, answer2, answer3));
    }

    public static Map<String, HashSet<Integer>> getQuestionKeywords() {
        final String q1 = "Which is the largest dam in country?";
        final String q2 = "Which game is most popular in the nation?";
        final String q3 = "Who was the first Prime Minister of India? ";

        final KeywordsBuilder<Map<String, HashSet<Integer>>> questionKeywords = new HashMapBasedQuestionKeywordHolder();
        return questionKeywords.buildKeywords(Arrays.asList(q1, q2, q3));
    }
}
