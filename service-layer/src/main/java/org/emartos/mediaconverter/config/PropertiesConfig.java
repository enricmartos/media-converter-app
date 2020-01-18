package org.emartos.mediaconverter.config;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;

@Service
public class PropertiesConfig {

    private static final String API_KEYS_PROPERTY = "apiKeys";
    private static final String DEFAULT_PROPERTIES_PATH = "/opt/conf/mediaconverter.properties";

    private static final CompositeConfiguration CONFIG = new CompositeConfiguration();
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesConfig.class);

    @PostConstruct
    public void load() {
        CONFIG.clear();
        try {
            File externalPropertiesFile = new File(System.getProperty("mediaconverter.configuration.path-content", DEFAULT_PROPERTIES_PATH));
            if (externalPropertiesFile.exists()) {
                CONFIG.addConfiguration(new PropertiesConfiguration(externalPropertiesFile));
            }
        } catch (ConfigurationException ex) {
            LOGGER.error("Unable to load properties file", ex);
        }
    }

    public String getProperty(String key) {
        return CONFIG.getString(key);
    }

    public Collection<String> getApiKeys() {
        return Arrays.asList(CONFIG.getStringArray(API_KEYS_PROPERTY));
    }

}
