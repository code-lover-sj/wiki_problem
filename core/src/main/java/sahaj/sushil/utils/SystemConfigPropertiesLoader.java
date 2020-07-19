package sahaj.sushil.utils;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.wiki.sushil.exception.InternalError;
import sahaj.wiki.sushil.exception.InvalidArgumentException;

public class SystemConfigPropertiesLoader {
    private static final Logger logger = LogManager.getLogger(SystemConfigPropertiesLoader.class);
    private String fileName;
    private PropertiesConfiguration props;

    public SystemConfigPropertiesLoader(final String fileName) {
        if (StringUtils.isBlank(fileName)) {
            logger.warn("Blank filename was passed to load system properties from. Loading from default {} file.",
                    Constants.DEFAULT_SYS_CONFIG_PROPS_FILE);

            this.fileName = Constants.DEFAULT_SYS_CONFIG_PROPS_FILE;
            return;
        }

        this.fileName = fileName;

        // loadPropertiesFromFile(fileName);
    }


    public String getProperty(final String key) {
        final String errorMessage = "No such property " + key;

        if (null == props) {
            try {
                loadPropertiesFromFile(fileName);
            } catch (final InternalError e) {
                throw new InvalidArgumentException(errorMessage);
            }
        }

        final Object property = props.getProperty(key);
        if (null == property) {
            throw new InvalidArgumentException(errorMessage);
        }

        return (String) property;
    }


    public String getPropertyWithoutException(final String key, final String defaultValue) {
        if (null == props) {
            try {
                loadPropertiesFromFile(fileName);
            } catch (final InternalError e) {
                return defaultValue;
            }
        }

        final Object property = props.getProperty(key);
        if (null == property) {
            return defaultValue;
        }

        return (String) property;
    }

    private void loadPropertiesFromFile(final String fileName) throws InternalError {
        try {
            props = new PropertiesConfiguration(fileName);
            props.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (final Exception e) {
            final String error = "Error occurred while reading from properties file " + fileName + ". Error - "
                    + e.getLocalizedMessage();
            logger.error(error, e);
            throw new InternalError(error);
        }
    }
}
