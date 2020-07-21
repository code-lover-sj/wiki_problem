package sahaj.wiki.sushil.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import sahaj.wiki.sushil.constant.ElementType;
import sahaj.wiki.sushil.input.InputReader;
import sahaj.wiki.sushil.input.constant.InputType;
import sahaj.wiki.sushil.input.factory.InputReaderFactory;
import sahaj.wiki.sushil.keyword.KeywordsBuilder;
import sahaj.wiki.sushil.keyword.answer.builder.TrieBasedAnswerKeywordBuilder;
import sahaj.wiki.sushil.keyword.question.builder.HashMapBasedQuestionKeywordBuilder;
import sahaj.wiki.sushil.keyword.trie.Trie;

public class TestHelper {
    public static final String PARA = "This is a valid paragraph. Programming is an art. An skilled artist can only craft a beautiful art."
            + " To create an art, you must be dedicated towards it. "
            + "Josh Bloch, Martin Fowler and Robert Martin are known craftsmen in Java programming. "
            + "Gangs of four takes the programming to next level. Each developer who wants to craft the code must read their books.\n";

    public static final String Q1 = "Who can craft a beautiful art?\n";
    public static final String Q2 = "What is required to create an art?\n";
    public static final String Q3 = "Who are known craftsmen in Java programming?\n";
    public static final String Q4 = "What does the Gangs of four do?\n";
    public static final String Q5 = "What must a developer do to craft the code?\n";

    public static final String ANSWER = "programming to next level; read their books; be dedicated; An skilled artist; "
            + "Josh Bloch, Martin Fowler and Robert Martin";

    public static final String ANSWER1 = "An skilled artist";
    public static final String ANSWER2 = "be dedicated";
    public static final String ANSWER3 = "Josh Bloch, Martin Fowler and Robert Martin";
    public static final String ANSWER4 = "programming to next level";
    public static final String ANSWER5 = "read their books";

    public static final List<String> expectedAnswerList = new ArrayList<>(
            Arrays.asList(ANSWER1, ANSWER2, ANSWER3, ANSWER4, ANSWER5));

    public static String getProblemStatementInput() {
        final String input = "Zebras are several species of African equids (horse family) united by their distinctive black and white stripes. Their stripes come in different patterns, unique to each individual. They are generally social animals that live in small harems to large herds. Unlike their closest relatives, horses and donkeys, zebras have never been truly domesticated. There are three species of zebras: the plains zebra, the Grévy's zebra and the mountain zebra. The plains zebra and the mountain zebra belong to the subgenus Hippotigris, but Grévy's zebra is the sole species of subgenus Dolichohippus. The latter resembles an ass, to which it is closely related, while the former two are more horse-like. All three belong to the genus Equus, along with other living equids. The unique stripes of zebras make them one of the animals most familiar to people. They occur in a variety of habitats, such as grasslands, savannas, woodlands, thorny scrublands, mountains, and coastal hills. However, various anthropogenic factors have had a severe impact on zebra populations, in particular hunting for skins and habitat destruction. Grévy's zebra and the mountain zebra are endangered. While plains zebras are much more plentiful, one subspecies - the Quagga - became extinct in the late 19th century. Though there is currently a plan, called the Quagga Project, that aims to breed zebras that are phenotypically similar to the Quagga, in a process called breeding back.\n"
                + "Which Zebras are endangered?\n" + "What is the aim of the Quagga Project?\n"
                + "Which animals are some of their closest relatives?\n" + "Which are the three species of zebras?\n"
                + "Which subgenus do the plains zebra and the mountain zebra belong to?\n"
                + "subgenus Hippotigris; the plains zebra, the Grévy's zebra and the mountain zebra;horses and donkeys;aims to breed zebras that are phenotypically similar to the quagga; Grévy's zebra and the mountain zebra";

        return input;
    }

    public static List<String> getExpectedOutputForProblemStatement() {
        final List<String> expectedOutput = new ArrayList<>(5);
        expectedOutput.add("Grévy's zebra and the mountain zebra");
        expectedOutput.add("aims to breed zebras that are phenotypically similar to the quagga");
        expectedOutput.add("horses and donkeys");
        expectedOutput.add("the plains zebra, the Grévy's zebra and the mountain zebra");
        expectedOutput.add("subgenus Hippotigris");

        return expectedOutput;
    }

    public static Map<ElementType, List<String>> getParsedInput() {
        final InputReader inputReader = InputReaderFactory.getInputReader(InputType.STRING);
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PARA).append(Q1).append(Q2).append(Q3).append(Q4).append(Q5);
        stringBuilder.append(ANSWER);

        return inputReader.readInput(stringBuilder.toString());
    }

    public static Map<String, HashSet<Integer>> buildQuestionKeywords() {
        final KeywordsBuilder<Map<String, HashSet<Integer>>> questionKeywordBuilder = new HashMapBasedQuestionKeywordBuilder();
        final List<String> questionsList = Stream.of(Q1, Q2, Q3, Q4, Q5).collect(Collectors.toList());
        return questionKeywordBuilder.buildKeywords(questionsList);
    }

    public static Trie<String> buildAnswerKeywords() {
        final KeywordsBuilder<Trie<String>> answerKeywordsBuilder = new TrieBasedAnswerKeywordBuilder();
        // final List<String> answerAsList = Arrays.asList(answer);
        return answerKeywordsBuilder.buildKeywords(getParsedInput().get(ElementType.ANSWER));
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

        final KeywordsBuilder<Map<String, HashSet<Integer>>> questionKeywords = new HashMapBasedQuestionKeywordBuilder();
        return questionKeywords.buildKeywords(Arrays.asList(q1, q2, q3));
    }
}
