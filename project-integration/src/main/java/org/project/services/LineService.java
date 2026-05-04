package org.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class LineService {

    private RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<String> response;

    private ObjectMapper mapper = new ObjectMapper();

    private JsonNode linesMap;

    public List<String> getLineNames() {

        response = restTemplate.getForEntity(ServiceUtils.getResourceUrl() + "line", String.class);

        try {
            linesMap = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ServiceUtils.extractData(response, "line_name");
    }

    public int getLineIdByName(String lineName) {
        for (JsonNode line : linesMap) {
            if (line.get("line_name").asText().equals(lineName)) {
                return line.get("id_line").asInt();
            }
        }
        return -1;
    }
}
