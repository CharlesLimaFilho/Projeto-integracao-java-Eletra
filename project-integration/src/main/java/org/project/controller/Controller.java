package org.project.controller;

import javafx.fxml.Initializable;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.project.services.CategoryService;
import org.project.services.LineService;
import org.project.services.ModelService;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    private ComboBox<String> cbLines;

    @FXML
    private TitledPane tpModels;

    @FXML
    private TreeView<String> tvModels;

    private LineService lineService = new LineService();

    private CategoryService categoryService = new CategoryService();

    private ModelService modelService  = new ModelService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cbLines.setItems(FXCollections.observableArrayList(lineService.getLineNames()));
        cbLines.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> changed(newValue)));
    }

    public void changed(String lineName) {

        TreeItem<String> addTree = new TreeItem<>();
        if (lineName != null) {

            int cont = 0;
            tpModels.setDisable(false);

            for (String categoryName : categoryService.getCategoryNames(lineService.getLineIdByName(lineName))) {
                addTree.getChildren().add(new TreeItem<>(categoryName));

                for (String modelName : modelService.getLineNames(categoryService.getCategoryIdByName(categoryName))) {
                    addTree.getChildren().get(cont).getChildren().add(new TreeItem<>(modelName));
                }
                cont++;
            }

            addTree.setValue(lineName);
            addTree.setExpanded(true);
            tvModels.setRoot(addTree);
            tvModels.setShowRoot(false);
        }
    }
}