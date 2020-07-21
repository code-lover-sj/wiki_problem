package sahaj.wiki.sushil.solution.facade;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.wiki.sushil.constant.ElementType;
import sahaj.wiki.sushil.input.InputReader;
import sahaj.wiki.sushil.input.constant.InputType;
import sahaj.wiki.sushil.input.factory.InputReaderFactory;
import sahaj.wiki.sushil.keyword.KeywordsBuilder;
import sahaj.wiki.sushil.keyword.answer.builder.TrieBasedAnswerKeywordBuilder;
import sahaj.wiki.sushil.keyword.question.builder.HashMapBasedQuestionKeywordBuilder;
import sahaj.wiki.sushil.keyword.trie.Trie;
import sahaj.wiki.sushil.statement.match.trie.StatementProcessor;
import sahaj.wiki.sushil.statement.match.trie.StatementProcessor.StatementProcessorBuilder;

public class SolutionFacade {
    private static final Logger logger = LogManager.getLogger(SolutionFacade.class);

    public List<String> getCorrectSequnetialAnswers(final String source) {
        logger.info("Received request with input {}", source);

        final InputReader inputReader = InputReaderFactory.getInputReader(InputType.STRING);
        final Map<ElementType, List<String>> parsedInput = inputReader.readInput(source);

        // TODO: Create factory to get the needed builder.
        final KeywordsBuilder<Trie<String>> answerKeywordBuilder = new TrieBasedAnswerKeywordBuilder();
        final KeywordsBuilder<Map<String, HashSet<Integer>>> questionKeywordBuilder = new HashMapBasedQuestionKeywordBuilder();

        final Trie<String> answerKeywords = answerKeywordBuilder.buildKeywords(parsedInput.get(ElementType.ANSWER));
        final Map<String, HashSet<Integer>> questionKeywords = questionKeywordBuilder
                .buildKeywords(parsedInput.get(ElementType.QUESTION));

        // TODO: Factory to get the processor
        final StatementProcessorBuilder processorBuilder = new StatementProcessorBuilder();
        final StatementProcessor processor = processorBuilder.input(parsedInput).answerKeywords(answerKeywords)
                .questionKeywords(questionKeywords).build();

        final List<String> answerIds = processor.processAndGetCorrectAnswerIds();
        logger.info("Output answer ids = {}", answerIds);

        return answerIds;
    }
}
