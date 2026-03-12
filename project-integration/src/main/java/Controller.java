import Enums.Categories;
import Enums.Lines;
import Enums.Models;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Arrays;


public class Controller {
    @FXML //fx:id="cbLines"
    private ComboBox<String> cbLines;

    @FXML //fx:id="tpModels"
    private TitledPane tpModels;

    @FXML //fx:id="tvModels"
    private TreeView<String> tvModels;

    public void initialize() {
        cbLines.setItems(FXCollections.observableArrayList(Arrays.asList(Lines.CRONOS.name, Lines.ARES.name)));
        cbLines.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> changed(newValue)));
    }

    public void changed(String lineName) {

        Lines line = null;
        for (Lines lineFind : Lines.values() ) {
            if (lineFind.name.equals(lineName)) {
                line =  lineFind;
            }
        }

        TreeItem<String> addTree = new TreeItem<>();
        if (line != null) {

            int cont = 0;
            tpModels.setDisable(false);

            for (Categories categories : line.categories) {
                addTree.getChildren().add(new TreeItem<>(categories.name));

                int categoryIndex = line.categories.indexOf(categories);

                for (Models models : line.categories.get(categoryIndex).models) {
                    addTree.getChildren().get(cont).getChildren().add(new TreeItem<>(models.name));
                }
                cont++;
            }

            addTree.setValue(line.name);
            addTree.setExpanded(true);
            tvModels.setRoot(addTree);
            tvModels.setShowRoot(false);
        }
    }
}