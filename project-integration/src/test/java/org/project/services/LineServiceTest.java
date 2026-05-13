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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LineServiceTest {

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LineService lineService;

    private final String lineNamesJson =
            "[{\"id_line\": 1, \"line_name\": \"Cronos\"}, " +
            "{\"id_line\": 2, \"line_name\": \"Ares\"}]";

    private final String incorrectLineNamesJson =
            "[{\"id_line\": 1, \"line_name\": \"Cronos\"}, " +
            "{\"id_line\": 2, \"line_name\" \"Ares\"}]"; // missing ":" in line_name

    @Before
    public void setUp() throws JsonProcessingException {
        lineService = spy(LineService.class);
        lineService.mapper = objectMapper;
        lineService.restTemplate = restTemplate;

        lineService.linesMap = objectMapper.readTree(lineNamesJson);
    }

    @After
    public void tearDown() {
        lineService.mapper = null;
        lineService.restTemplate = null;
        lineService = null;
    }

    @Test
    public void getLineNamesTest() {
        when(lineService.restTemplate.getForEntity("http://localhost:8080/line", String.class)).thenReturn(new ResponseEntity<>(lineNamesJson,
                HttpStatus.OK));

        List<String> data = new ArrayList<>(Arrays.asList("Cronos", "Ares"));

        assertEquals(data, lineService.getLineNames());
    }

    @Test
    public void getLineIdByName01() {
        assertEquals(1, lineService.getLineIdByName("Cronos"));
    }

    @Test
    public void getLineIdByName02() {
        assertEquals(2, lineService.getLineIdByName("Ares"));
    }

    @Test
    public void getLineIdByNameFalse() {
        assertEquals(-1, lineService.getLineIdByName(""));
    }

    @Test
    public void getLineNamesTestJsonException() {
        when(lineService.restTemplate.getForEntity("http://localhost:8080/line", String.class)).thenReturn(new ResponseEntity<>(incorrectLineNamesJson,
                HttpStatus.OK));
        assertTrue(lineService.getLineNames().isEmpty());
    }
}