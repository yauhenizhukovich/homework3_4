package com.gmail.supersonicleader.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPropertyUtil {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String CONFIG_PROPERTY_FILE_LOCATION = "config.properties";
    private static Properties properties;

    private ConnectionPropertyUtil() {
    }

    public static String getProperty(String name) {
        if (properties == null) {
            properties = new Properties();
            try (InputStream propertiesStream = ConnectionPropertyUtil.class.getClassLoader()
                    .getResourceAsStream(CONFIG_PROPERTY_FILE_LOCATION)) {
                properties.load(propertiesStream);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new IllegalArgumentException("Config property file is not found");
            }
        }
        return properties.getProperty(name);

    }

}