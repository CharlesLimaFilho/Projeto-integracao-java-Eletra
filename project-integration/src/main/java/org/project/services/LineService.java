package org.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

public class LineService {

    protected RestTemplate restTemplate = new RestTemplate();

    protected ResponseEntity<String> response;

    protected ObjectMapper mapper = new ObjectMapper();

    protected JsonNode linesMap;

    public LineService() {}

    public List<String> getLineNames() {

        response = restTemplate.getForEntity(ServiceUtils.getResourceUrl() + "line", String.class);

        try {
            linesMap = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            return Collections.emptyList();
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
