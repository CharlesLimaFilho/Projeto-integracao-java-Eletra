package controller;

import javafx.fxml.Initializable;

import mapping.CategoryTable;
import mapping.LineTable;
import mapping.ModelTable;
import org.project.model.DataBaseHelper;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    private DataBaseHelper dataBaseHelper = new DataBaseHelper();

    private List<LineTable> lines;

    private List<CategoryTable> categories;

    private List<ModelTable> models;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dataBaseHelper.startConnection();

        lines = LineTable.getLines();


        ArrayList<String> lineNames = new ArrayList<>();
        for (LineTable line : lines) {
            lineNames.add(line.getLine_name());
        }


        cbLines.setItems(FXCollections.observableArrayList(lineNames));
        cbLines.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> changed(newValue)));

    }

    public void changed(String lineName) {

        TreeItem<String> addTree = new TreeItem<>();
        if (lineName != null) {

            int cont = 0;
            tpModels.setDisable(false);

            for (LineTable line : lines) {
                if (lineName.equals(line.getLine_name())) {
                    categories = CategoryTable.getCategoriesByLineId(line.getId_line());

                    for (CategoryTable category : categories) {
                        addTree.getChildren().add(new TreeItem<>(category.getCategory_name()));
                        models = ModelTable.getModelsByCategoryId(category.getId_category());

                        for (ModelTable model : models) {
                            addTree.getChildren().get(cont).getChildren().add(new TreeItem<>(model.getModel_name()));
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
}