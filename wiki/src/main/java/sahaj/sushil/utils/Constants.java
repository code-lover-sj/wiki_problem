package sahaj.sushil.utils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constants {
    public static final String DEF_LINE_SEPARATOR = "\n";
    public static final String LINE_SEPARATOR = "line.separator";
    public static final String QUESTION_MARK = "?";
    public static final String COMMA = ",";

    public static final int ONE = 1;
    public static final int ZERO = 0;

    public static final int DEFAULT_NO_OF_QUESTIONS = 5;
    public static final int DEFAULT_NO_STATEMENTS_IN_PARA = ONE;
    public static final int DEFAULT_NO_OF_ANSWERS = ONE;

    public static final String SPACE = " ";

    public static final String NEW_LINE = System.getProperty(LINE_SEPARATOR, DEF_LINE_SEPARATOR);

    public static final String DEFAULT_SYS_CONFIG_PROPS_FILE = "system_config.properties";

    public static final String NO_OF_QUESTIONS = "no_of_questions";
    public static final String NO_OF_STATEMENTS_IN_PARA = "no_of_statements_in_para";
    public static final String NO_OF_ANSWERS = "no_of_answers";

    public static final String ANSWER_DELIMITER = "answer_delimiter";
    public static final String QUESTION_DELIMITER = "question_delimiter";
    public static final String STATEMENT_DELIMITER = "statement_delimiter";

    public static final Set<String> COMMON_WORDS = Stream.of("the", "why", "where", "how", "when", "what", "which",
            "and", "or", "are", "is", "were", "was", "as", "a").collect(Collectors.toSet());

    public static final String DEF_ANSWER_DELIMITER = ";";
    public static final String DEF_QUE_DELIMITER = NEW_LINE;
    public static final String DEF_STMT_DELIMITER = "[.?!]+";

    public static final String LEAF_TRIE_NODE = "LEAF";
}
