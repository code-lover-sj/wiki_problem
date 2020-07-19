package sahaj.sushil.utils;

import static sahaj.sushil.utils.Constants.*;

public class SystemConfig {
    private final SystemConfigPropertiesLoader properties;

    public SystemConfig() {
        properties = new SystemConfigPropertiesLoader(DEFAULT_SYS_CONFIG_PROPS_FILE);
    }

    public SystemConfig(final String fileName) {
        properties = new SystemConfigPropertiesLoader(fileName);
    }

    public String getNoOfQuestions() {
        return properties.getPropertyWithoutException(NO_OF_QUESTIONS, String.valueOf(DEFAULT_NO_OF_QUESTIONS));
    }

    public String getNoOfStatementsInPara() {
        return properties.getPropertyWithoutException(NO_OF_STATEMENTS_IN_PARA,
                String.valueOf(DEFAULT_NO_STATEMENTS_IN_PARA));
    }

    public String getNoOfAnswers() {
        return properties.getPropertyWithoutException(NO_OF_ANSWERS,
                String.valueOf(DEFAULT_NO_OF_ANSWERS));
    }

    public String getStatementDelimiter() {
        return properties.getPropertyWithoutException(STATEMENT_DELIMITER, DEF_STMT_DELIMITER);
    }

    public String getAnswerDelimiter() {
        return properties.getPropertyWithoutException(ANSWER_DELIMITER, DEF_ANSWER_DELIMITER);
    }
}
