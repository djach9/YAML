package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ConfigurationRetriever {
    public static Configuration getConfig() throws IOException {
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        try (InputStream input = ConfigurationRetriever.class.getResourceAsStream("/configuration.yaml")) {
            Map<String, Object> configMap = om.readValue(input, Map.class);
            return new Configuration((Map<String, Object>) configMap.get("globals"), (Map<String, Map<String, Object>>) configMap.get("environments"));
        }
    }
}
