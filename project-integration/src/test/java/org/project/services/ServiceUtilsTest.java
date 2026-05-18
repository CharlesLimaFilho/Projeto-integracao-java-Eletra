package org.project.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ServiceUtilsTest {

    private final String responseBodyJson =
            "[{\"id_line\": 1, \"line_name\": \"Cronos\"}, " +
            "{\"id_line\": 2, \"line_name\": \"Ares\"}]";

    private final String incorrectResponseBodyJson =
            "[{\"id_line\": 1, \"line_name\": \"Cronos\"}, " +
            "{\"id_line\": 2, \"line_name\" \"Ares\"}]"; // missing ":" in line_name

    @Test
    public void extractDataLineIdTest() {
        assertEquals(Arrays.asList("1","2"), ServiceUtils.extractData(new ResponseEntity<>(responseBodyJson,
                        HttpStatus.OK),
                "id_line"));
    }

    @Test
    public void extractDataLineNameTest() {
        assertEquals(Arrays.asList("Cronos","Ares"), ServiceUtils.extractData(new ResponseEntity<>(responseBodyJson,
                        HttpStatus.OK),
                "line_name"));
    }

    @Test
    public void extractDataException() {
        assertTrue(ServiceUtils.extractData(new ResponseEntity<>(incorrectResponseBodyJson,
                        HttpStatus.OK),
                "line_name").isEmpty());
    }
}