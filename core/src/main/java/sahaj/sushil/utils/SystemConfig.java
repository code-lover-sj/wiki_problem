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

    public int getIntNoOfQuestions() {
        return getIntNo(getNoOfQuestions());
    }

    public String getNoOfStatementsInPara() {
        return properties.getPropertyWithoutException(NO_OF_STATEMENTS_IN_PARA,
                String.valueOf(DEFAULT_NO_STATEMENTS_IN_PARA));
    }

    public int getIntNoOfStatementsInPara() {
        return getIntNo(getNoOfStatementsInPara());
    }

    public String getNoOfAnswers() {
        return properties.getPropertyWithoutException(NO_OF_ANSWERS,
                String.valueOf(DEFAULT_NO_OF_ANSWERS));
    }

    public int getIntNoOfAnswers() {
        return getIntNo(getNoOfAnswers());
    }

    public String getStatementDelimiter() {
        return properties.getPropertyWithoutException(STATEMENT_DELIMITER, DEF_STMT_DELIMITER);
    }

    public String getAnswerDelimiter() {
        return properties.getPropertyWithoutException(ANSWER_DELIMITER, DEF_ANSWER_DELIMITER);
    }

    public String getQuestionCollisionResolverStrategy() {
        return properties.getPropertyWithoutException(QUESTION_COLLISION_RESOLVER_STRATEGY, DEF_QUE_COLLISION_STRATEGY);
    }

    private int getIntNo(final String propertyValue) {
        return Integer.parseInt(propertyValue);
    }
}
