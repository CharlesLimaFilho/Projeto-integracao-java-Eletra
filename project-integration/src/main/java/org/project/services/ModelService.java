package org.project.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ModelService {

    private RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<String> response;

    public List<String> getLineNames(int categoryId) {

        response = restTemplate.getForEntity(ServiceUtils.getResourceUrl() + "model/" + categoryId, String.class);

        return ServiceUtils.extractData(response, "model_name");
    }
}
