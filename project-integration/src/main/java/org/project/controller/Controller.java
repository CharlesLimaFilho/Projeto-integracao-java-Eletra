package org.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.Initializable;

import org.project.DataBaseHelper;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.project.models.CategoryTable;
import org.project.models.LineTable;
import org.project.models.ModelTable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    private ComboBox<String> cbLines;

    @FXML
    private TitledPane tpModels;

    @FXML
    private TreeView<String> tvModels;

    private RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<String> response;

    private ObjectMapper mapper = new ObjectMapper();

    private JsonNode linesMap;

    private JsonNode categoriesMap;

    private JsonNode modelsMap;

    private final String resouceUrl = "http://localhost:8080/";

    private List<String> lines;

    private List<String> categories;

    private List<ModelTable> models;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        response = restTemplate.getForEntity(resouceUrl + "line", String.class);


        try {
            linesMap = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        lines = extractData(response, "line_name");

        cbLines.setItems(FXCollections.observableArrayList(lines));
        cbLines.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                changed(newValue);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public void changed(String lineName) throws JsonProcessingException {

        TreeItem<String> addTree = new TreeItem<>();
        if (lineName != null) {

            int cont = 0;
            tpModels.setDisable(false);


//            for (LineTable line : lines) {
//                if (lineName.equals(line.getLine_name())) {
//                    categories = restTemplate.getForEntity(resouceUrl + );
//
//                    for (CategoryTable category : categories) {
//                        addTree.getChildren().add(new TreeItem<>(category.getCategory_name()));
//                        models = ModelTable.getModelsByCategoryId(category.getId_category());
//
//                        for (ModelTable model : models) {
//                            addTree.getChildren().get(cont).getChildren().add(new TreeItem<>(model.getModel_name()));
//                        }
//                        cont++;
//                    }
//                }
//            }

            for (JsonNode linesFind : linesMap) {
                if (linesFind.get("line_name").asText().equals(lineName)) {
                    response = restTemplate.getForEntity(resouceUrl + "category/" + linesFind.get("id_line"),
                            String.class);

                    categoriesMap = mapper.readTree(response.getBody());

                    for (JsonNode categoriesFind : categoriesMap) {
                        addTree.getChildren().add(new TreeItem<>(categoriesFind.get("category_name").asText()));
                        response = restTemplate.getForEntity(resouceUrl + "model/" + categoriesFind.get("id_category"),
                                        String.class);

                        modelsMap = mapper.readTree(response.getBody());

                        for (JsonNode modelsFind : modelsMap) {
                            addTree.getChildren().get(cont).getChildren().add(new TreeItem<>(modelsFind.get("model_name").asText()));
                        }
                        cont++;
                    }
                }
            }

            addTree.setValue(lineName);
            addTree.setExpanded(true);
            tvModels.setRoot(addTree);
            tvModels.setShowRoot(false);
        }
    }

    public List<String> extractData(ResponseEntity<String> response, String dataName) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> data = new ArrayList<>();

        try {
            JsonNode root = mapper.readTree(response.getBody());
            //int a = root.get(1).get("id_line").asInt();

            for (JsonNode rootData : root) {
                data.add(rootData.get(dataName).textValue());
            }

            return data;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}