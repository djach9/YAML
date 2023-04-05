package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ConfigurationRetriever {
    private static Configuration configuration;

    public static Configuration getConfig() throws IOException {
        if (configuration == null) {
            ObjectMapper om = new ObjectMapper(new YAMLFactory());
            try (InputStream input = ConfigurationRetriever.class.getResourceAsStream("/configuration.yaml")) {
                configuration = om.readValue(input, Configuration.class);
            }
            saveConfigAsSystemProperties(configuration);
        }
        return configuration;
    }

    private static void saveConfigAsSystemProperties(Configuration configuration) {
        savePropertiesAsSystemProperties(configuration.getGlobals());
        savePropertiesAsSystemProperties(configuration.getEnvironment());
    }

    private static void savePropertiesAsSystemProperties(Map<String, Object> properties) {
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            if (System.getProperty(entry.getKey()) == null) {
                System.setProperty(entry.getKey(), entry.getValue().toString());
            }
        }
    }
}
