package org.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

public class CategoryService {

    protected RestTemplate restTemplate = new RestTemplate();

    protected ResponseEntity<String> response;

    protected ObjectMapper mapper = new ObjectMapper();

    protected JsonNode categoriesMap;

    public CategoryService() {}

    public List<String> getCategoryNames(int lineId) {
        response = restTemplate.getForEntity(ServiceUtils.getResourceUrl() + "category/" + lineId, String.class);

        try {
            categoriesMap = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            return Collections.emptyList();
        }

        return ServiceUtils.extractData(response, "category_name");
    }

    public int getCategoryIdByName(String categoryName) {
        for (JsonNode line : categoriesMap) {
            if (line.get("category_name").asText().equals(categoryName)) {
                return line.get("id_category").asInt();
            }
        }
        return -1;
    }
}
