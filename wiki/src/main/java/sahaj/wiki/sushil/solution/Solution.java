package sahaj.wiki.sushil.solution;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.wiki.sushil.solution.facade.SolutionFacade;

/**
 * Main class which starts the proceedings.
 *
 * There are known limitations -
 *
 * 1. The solution will not work if the statement contains character . before actual end of the statement.
 *
 * 2. If the questions have different voices (active/passive), the solution will not work.
 *
 * 3. Collision resolution for the question is fragile and based on the number of total words against the keyword %.
 *
 * 4. It is assumed that only 1 question and answer maps to any statement in the paragraph. So, in case of multiple
 * answers and questions mapping to the same statement the solution can't give the accuracy.
 */
public class Solution {
    private static final Logger logger = LogManager.getLogger(Solution.class);

    public List<String> solve(final String input) {
        final SolutionFacade facade = new SolutionFacade();
        final List<String> sequentialAnswers = facade.getCorrectSequnetialAnswers(input);
        printAnswers(sequentialAnswers);

        return sequentialAnswers;
    }

    private void printAnswers(final List<String> sequentialAnswers) {
        logger.info(sequentialAnswers);
    }
}
