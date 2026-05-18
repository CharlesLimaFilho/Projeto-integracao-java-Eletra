package org.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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
public class CategoryServiceTest {

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CategoryService categoryService;

    private final String cronosCategoriesJson =
            "[{\"id_category\": 1,\"category_name\": \"Cronos Old\", \"id_line\": 1}, " +
            "{\"id_category\": 2, \"category_name\": \"Cronos L\", \"id_line\": 1}, " +
            "{\"id_category\": 3, \"category_name\": \"Cronos NG\", \"id_line\": 1}]";

    private final String aresCategoriesJson =
            "[{\"id_category\": 4,\"category_name\": \"Ares TB\", \"id_line\": 2}, " +
            "{\"id_category\": 5, \"category_name\": \"Ares THS\", \"id_line\": 2}]";

    private final String incorrectCronosCategoriesJson =
            "[{\"id_category\": 1,\"category_name\": \"Cronos Old\", \"id_line\": 1}, " +
            "{\"id_category\": 2, \"category_name\": \"Cronos L\", \"id_line\": 1}, " +
            "{\"id_category\": 3, \"category_name\" \"Cronos NG\", \"id_line\": 1}]"; // missing ":" in category_name

    private final String incorrectAresCategoriesJson =
            "[{\"id_category\": 4,\"category_name\": \"Ares TB\", \"id_line\": 2}, " +
            "{\"id_category\": 5, \"category_name\" \"Ares THS\", \"id_line\": 2}]"; // missing ":" in category_name

    @Before
    public void setUp() {
        categoryService = spy(CategoryService.class);
        categoryService.mapper = objectMapper;
        categoryService.restTemplate = restTemplate;
    }

    @After
    public void tearDown() {
        categoryService.mapper = null;
        categoryService.restTemplate = null;
        categoryService = null;
    }

    @Test
    public void getCategoryNamesTest01() {
        when(categoryService.restTemplate.getForEntity("http://localhost:8080/category/1", String.class)).thenReturn(new ResponseEntity<>(cronosCategoriesJson,
                HttpStatus.OK));

        List<String> data = new ArrayList<>(Arrays.asList("Cronos Old", "Cronos L", "Cronos NG"));
        assertEquals(data, categoryService.getCategoryNames(1));
    }

    @Test
    public void getCategoryNamesTest02() {
        when(categoryService.restTemplate.getForEntity("http://localhost:8080/category/2", String.class)).thenReturn(new ResponseEntity<>(aresCategoriesJson,
                HttpStatus.OK));

        List<String> data = new ArrayList<>(Arrays.asList("Ares TB", "Ares THS"));
        assertEquals(data, categoryService.getCategoryNames(2));
    }

    @Test
    public void getCategoryNamesTestJsonException() {
        when(categoryService.restTemplate.getForEntity("http://localhost:8080/category/1", String.class)).thenReturn(new ResponseEntity<>(incorrectCronosCategoriesJson,
                HttpStatus.OK));

        assertTrue(categoryService.getCategoryNames(1).isEmpty());
    }

    @Test
    public void getCategoryIdByName01() throws JsonProcessingException {
        categoryService.categoriesMap = objectMapper.readTree(cronosCategoriesJson);
        assertEquals(1, categoryService.getCategoryIdByName("Cronos Old"));
    }

    @Test
    public void getCategoryIdByName02() throws JsonProcessingException {
        categoryService.categoriesMap = objectMapper.readTree(cronosCategoriesJson);
        assertEquals(2, categoryService.getCategoryIdByName("Cronos L"));
    }

    @Test
    public void getCategoryIdByName03() throws JsonProcessingException {
        categoryService.categoriesMap = objectMapper.readTree(cronosCategoriesJson);
        assertEquals(3, categoryService.getCategoryIdByName("Cronos NG"));
    }

    @Test
    public void getCategoryIdByName04() throws JsonProcessingException {
        categoryService.categoriesMap = objectMapper.readTree(aresCategoriesJson);
        assertEquals(4, categoryService.getCategoryIdByName("Ares TB"));
    }

    @Test
    public void getCategoryIdByName05() throws JsonProcessingException {
        categoryService.categoriesMap = objectMapper.readTree(aresCategoriesJson);
        assertEquals(5, categoryService.getCategoryIdByName("Ares THS"));
    }

    @Test
    public void getCategoryIdByNameFalse() throws JsonProcessingException {
        categoryService.categoriesMap = objectMapper.readTree(cronosCategoriesJson);
        assertEquals(-1, categoryService.getCategoryIdByName(""));
    }
}