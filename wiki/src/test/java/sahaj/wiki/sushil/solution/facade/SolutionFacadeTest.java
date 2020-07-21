package sahaj.wiki.sushil.solution.facade;


import static org.junit.Assert.assertEquals;
import static sahaj.wiki.sushil.utils.TestHelper.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SolutionFacadeTest {
    private SolutionFacade testSubject;

    @Before
    public void setUp() throws Exception {
        testSubject = createTestSubject();
    }

    @After
    public void tearDown() throws Exception {

    }

    private SolutionFacade createTestSubject() {
        return new SolutionFacade();
    }

    @Test
    public void testGetCorrectSequnetialAnswersFromProblemStatement() {
        final String input = "Zebras are several species of African equids (horse family) united by their distinctive black and white stripes. Their stripes come in different patterns, unique to each individual. They are generally social animals that live in small harems to large herds. Unlike their closest relatives, horses and donkeys, zebras have never been truly domesticated. There are three species of zebras: the plains zebra, the Grévy's zebra and the mountain zebra. The plains zebra and the mountain zebra belong to the subgenus Hippotigris, but Grévy's zebra is the sole species of subgenus Dolichohippus. The latter resembles an ass, to which it is closely related, while the former two are more horse-like. All three belong to the genus Equus, along with other living equids. The unique stripes of zebras make them one of the animals most familiar to people. They occur in a variety of habitats, such as grasslands, savannas, woodlands, thorny scrublands, mountains, and coastal hills. However, various anthropogenic factors have had a severe impact on zebra populations, in particular hunting for skins and habitat destruction. Grévy's zebra and the mountain zebra are endangered. While plains zebras are much more plentiful, one subspecies - the Quagga - became extinct in the late 19th century. Though there is currently a plan, called the Quagga Project, that aims to breed zebras that are phenotypically similar to the Quagga, in a process called breeding back.\n"
                + "Which Zebras are endangered?\n" + "What is the aim of the Quagga Project?\n"
                + "Which animals are some of their closest relatives?\n" + "Which are the three species of zebras?\n"
                + "Which subgenus do the plains zebra and the mountain zebra belong to?\n"
                + "subgenus Hippotigris; the plains zebra, the Grévy's zebra and the mountain zebra;horses and donkeys;aims to breed zebras that are phenotypically similar to the quagga; Grévy's zebra and the mountain zebra";


        final List<String> expectedOutput = new ArrayList<>(5);
        expectedOutput.add("Grévy's zebra and the mountain zebra");
        expectedOutput.add("aims to breed zebras that are phenotypically similar to the quagga");
        expectedOutput.add("horses and donkeys");
        expectedOutput.add("the plains zebra, the Grévy's zebra and the mountain zebra");
        expectedOutput.add("subgenus Hippotigris");

        final List<String> result = testSubject.getCorrectSequnetialAnswers(input);

        assertEquals(expectedOutput, result);

    }

    @Test
    public void testWithTestHelper() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PARA).append(Q1).append(Q2).append(Q3).append(Q4).append(Q5);
        stringBuilder.append(ANSWER);

        final List<String> result = testSubject.getCorrectSequnetialAnswers(stringBuilder.toString());
        assertEquals(expectedAnswerList, result);
    }

}