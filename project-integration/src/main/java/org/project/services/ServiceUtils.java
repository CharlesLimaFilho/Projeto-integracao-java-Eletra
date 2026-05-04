package org.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ServiceUtils {

    private static final String resourceUrl = "http://localhost:8080/";

    public static List<String> extractData(ResponseEntity<String> response, String dataName) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> data = new ArrayList<>();

        try {
            JsonNode root = mapper.readTree(response.getBody());

            for (JsonNode rootData : root) {
                data.add(rootData.get(dataName).textValue());
            }

            return data;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getResourceUrl() {
        return resourceUrl;
    }
}
