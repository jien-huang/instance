package com.automationtest.instance.utils;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class Config {

    private final Properties properties = new Properties();
    private static final Config instance = new Config();
    private final Logger logger = LoggerFactory.getLogger("Configuration");

    /***
     * load config files, by alphabet order, so my.properties will overwrite
     * config.properties; then load environment variables, it will overwrite all
     * properties; but the env var should contain value, empty value will be
     * ignored.
     */
    private Config() {
        File[] files = new File(".").listFiles();
        Arrays.sort(Objects.requireNonNull(files));
        for (File file : files) {
            if (file.getName().endsWith(".properties")) {
                Properties config = new Properties();
                try {
                    config.load(new FileInputStream(file));
                    properties.putAll(config);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        // system env will overrite things from properties
        for (String key : System.getenv().keySet()) {
            String value = System.getenv(key);
            if (!Strings.isNullOrEmpty(value)) {
                properties.put(key, value);
            }
        }
        for (Object key : System.getProperties().keySet()) {
            String value = System.getProperty(key.toString());
            if (!Strings.isNullOrEmpty(value)) {
                properties.put(key, value);
            }
        }
        if (!properties.containsKey("client.id")) {
            properties.put("client.id", Utils.uuid());
        }

    }

    public static Config getInstance() {
        return instance;
    }

    public boolean is(String key) {
        Object value = get(key);
        if (value == null)
            return false;
        return value.toString().equalsIgnoreCase("True");
    }

    public boolean is(String key, boolean defaultValue) {
        Object value = get(key);
        if (value == null)
            return defaultValue;
        return value.toString().equalsIgnoreCase("True");
    }

    public Object get(String key) {
        return properties.getProperty(key);
    }

    public Object get(String key, Object defaultValue) {
        Object value = get(key);
        if (value == null)
            value = defaultValue;
        return value;
    }

    public void set(String key, String value) {
        properties.put(key, value);
        logger.debug("set property " + key + "=" + value);
    }

    public Properties getAll() {
        return properties;
    }

}
