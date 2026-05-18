package org.project.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ModelServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ModelService modelService;

    private String cronosOldModelsJson =
            "[{\"id_model\":1,\"model_name\":\"Cronos 6001-A\",\"id_category\":1}," +
            "{\"id_model\":2,\"model_name\":\"Cronos 6003\",\"id_category\":1}," +
            "{\"id_model\":3,\"model_name\":\"Cronos 7023\",\"id_category\":1}]";

    private String cronosLModelsJson =
            "[{\"id_model\":4,\"model_name\":\"Cronos 6021L\",\"id_category\":2}," +
            "{\"id_model\":5,\"model_name\":\"Cronos 7023L\",\"id_category\":2}]";

    private String cronosNGModelsJson =
            "[{\"id_model\":6,\"model_name\":\"Cronos 6001-NG\",\"id_category\":3}," +
            "{\"id_model\":7,\"model_name\":\"Cronos 6003-NG\",\"id_category\":3}," +
            "{\"id_model\":8,\"model_name\":\"Cronos 6021-NG\",\"id_category\":3}," +
            "{\"id_model\":9,\"model_name\":\"Cronos 6031-NG\",\"id_category\":3}," +
            "{\"id_model\":10,\"model_name\":\"Cronos 7021-NG\",\"id_category\":3}," +
            "{\"id_model\":11,\"model_name\":\"Cronos 7023-NG\",\"id_category\":3}]";

    private String aresTBModelsJson =
            "[{\"id_model\":12,\"model_name\":\"Ares 7021\",\"id_category\":4}," +
            "{\"id_model\":13,\"model_name\":\"Ares 7031\",\"id_category\":4}," +
            "{\"id_model\":14,\"model_name\":\"Ares 7023\",\"id_category\":4}]";

    private String aresTHSModelsJson =
            "[{\"id_model\":15,\"model_name\":\"Ares 8023 15\",\"id_category\":5}," +
            "{\"id_model\":16,\"model_name\":\"Ares 8023 200\",\"id_category\":5}," +
            "{\"id_model\":17,\"model_name\":\"Ares 8023 2,5\",\"id_category\":5}]";

    @Before
    public void setUp() {
        modelService = spy(ModelService.class);
        modelService.restTemplate = restTemplate;
    }

    @After
    public void tearDown() {
        modelService.restTemplate = null;
        modelService = null;
    }

    @Test
    public void getLineNamesTest01() {
        when(modelService.restTemplate.getForEntity("http://localhost:8080/model/1", String.class)).thenReturn(new ResponseEntity<>(cronosOldModelsJson,
                HttpStatus.OK));

        List<String> data = new ArrayList<>(Arrays.asList("Cronos 6001-A", "Cronos 6003", "Cronos 7023"));
        assertEquals(data, modelService.getLineNames(1));
    }

    @Test
    public void getLineNamesTest02() {
        when(modelService.restTemplate.getForEntity("http://localhost:8080/model/2", String.class)).thenReturn(new ResponseEntity<>(cronosLModelsJson,
                HttpStatus.OK));

        List<String> data = new ArrayList<>(Arrays.asList("Cronos 6021L", "Cronos 7023L"));
        assertEquals(data, modelService.getLineNames(2));
    }

    @Test
    public void getLineNamesTest03() {
        when(modelService.restTemplate.getForEntity("http://localhost:8080/model/3", String.class)).thenReturn(new ResponseEntity<>(cronosNGModelsJson,
                HttpStatus.OK));

        List<String> data = new ArrayList<>(Arrays.asList("Cronos 6001-NG","Cronos 6003-NG", "Cronos 6021-NG",
                "Cronos 6031-NG",
                "Cronos 7021-NG", "Cronos 7023-NG"));
        assertEquals(data, modelService.getLineNames(3));
    }
    @Test
    public void getLineNamesTest04() {
        when(modelService.restTemplate.getForEntity("http://localhost:8080/model/4", String.class)).thenReturn(new ResponseEntity<>(aresTBModelsJson,
                HttpStatus.OK));

        List<String> data = new ArrayList<>(Arrays.asList("Ares 7021", "Ares 7031", "Ares 7023"));
        assertEquals(data, modelService.getLineNames(4));
    }

    @Test
    public void getLineNamesTest05() {
        when(modelService.restTemplate.getForEntity("http://localhost:8080/model/5", String.class)).thenReturn(new ResponseEntity<>(aresTHSModelsJson,
                HttpStatus.OK));

        List<String> data = new ArrayList<>(Arrays.asList("Ares 8023 15", "Ares 8023 200", "Ares 8023 2,5"));
        assertEquals(data, modelService.getLineNames(5));
    }

}