package org.project.controller;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.services.CategoryService;
import org.project.services.LineService;
import org.project.services.ModelService;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    @BeforeClass
    public static void setUpClass() {
        new JFXPanel();
    }

    @Mock
    private LineService lineService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ModelService modelService;

    @InjectMocks
    private Controller controller;

    private TreeItem<String> treeItem;

    private TreeView<String> expectedTreeView;

    @Before
    public void setUp() {
        controller = spy(Controller.class);

        controller.cbLines = new ComboBox<>();
        controller.tpModels = new TitledPane();
        controller.tvModels = new TreeView<>();

        controller.lineService = lineService;
        controller.categoryService = categoryService;
        controller.modelService = modelService;
    }

    @After
    public void tearDown() {
        controller.lineService = null;
        controller.categoryService = null;
        controller.modelService = null;
        controller = null;
    }

    @Test
    public void initializeTest() {
        controller.initialize(getClass().getResource("/view/Screen.fxml"), null);
        verify(lineService, times(1)).getLineNames();
    }

    @Test
    public void changedTest01() {
        when(lineService.getLineIdByName("Cronos")).thenReturn(1);

        when(categoryService.getCategoryNames(1)).thenReturn(Arrays.asList("Cronos Old", "Cronos L", "Cronos NG"));

        when(categoryService.getCategoryIdByName("Cronos Old")).thenReturn(1);
        when(categoryService.getCategoryIdByName("Cronos L")).thenReturn(2);
        when(categoryService.getCategoryIdByName("Cronos NG")).thenReturn(3);

        when(modelService.getLineNames(1)).thenReturn(Arrays.asList(
                "Cronos 6001-A", "Cronos 6003", "Cronos 7023"));

        when(modelService.getLineNames(2)).thenReturn(Arrays.asList("Cronos 6021L", "Cronos 7023L"));

        when(modelService.getLineNames(3)).thenReturn(Arrays.asList("Cronos 6001-NG","Cronos 6003-NG", "Cronos 6021-NG",
                "Cronos 6031-NG",
                "Cronos 7021-NG", "Cronos 7023-NG"));

        controller.changed("Cronos");

        assertTrue(compareCategories(getCronosTree(), controller.tvModels));
    }

    @Test
    public void changedTest02() {
        when(lineService.getLineIdByName("Ares")).thenReturn(2);

        when(categoryService.getCategoryNames(2)).thenReturn(Arrays.asList("Ares TB", "Ares THS"));

        when(categoryService.getCategoryIdByName("Ares TB")).thenReturn(4);
        when(categoryService.getCategoryIdByName("Ares THS")).thenReturn(5);

        when(modelService.getLineNames(4)).thenReturn(Arrays.asList(
                "Ares 7021", "Ares 7031", "Ares 7023"));

        when(modelService.getLineNames(5)).thenReturn(Arrays.asList("Ares 8023 15", "Ares 8023 200", "Ares 8023 2,5"));

        controller.changed("Ares");

        assertTrue(compareCategories(getAresTree(), controller.tvModels));
    }

    @Test
    public void changedTest03() {
        controller.changed(null);

        assertNull(controller.tvModels.getRoot());
    }

    public TreeView<String> getCronosTree() {
        treeItem = new TreeItem<>();
        expectedTreeView = new TreeView<>();


        treeItem.getChildren().addAll(
                new TreeItem<>("Cronos Old"),
                new TreeItem<>("Cronos L"),
                new TreeItem<>("Cronos NG")
        );

        treeItem.getChildren().get(0).getChildren().addAll(
                new TreeItem<>("Cronos 6001-A"),
                new TreeItem<>("Cronos 6003"),
                new TreeItem<>("Cronos 7023")
        );

        treeItem.getChildren().get(1).getChildren().addAll(
                new TreeItem<>("Cronos 6021L"),
                new TreeItem<>("Cronos 7023L")
        );

        treeItem.getChildren().get(2).getChildren().addAll(
                new TreeItem<>("Cronos 6001-NG"),
                new TreeItem<>("Cronos 6003-NG"),
                new TreeItem<>("Cronos 6021-NG"),
                new TreeItem<>("Cronos 6031-NG"),
                new TreeItem<>("Cronos 7021-NG"),
                new TreeItem<>("Cronos 7023-NG")
        );

        treeItem.setValue("Cronos");
        treeItem.setExpanded(true);
        expectedTreeView.setRoot(treeItem);
        expectedTreeView.setShowRoot(false);

        return expectedTreeView;
    }

    public TreeView<String> getAresTree() {
        treeItem = new TreeItem<>();
        expectedTreeView = new TreeView<>();


        treeItem.getChildren().addAll(
                new TreeItem<>("Ares TB"),
                new TreeItem<>("Ares THS")
        );

        treeItem.getChildren().get(0).getChildren().addAll(
                new TreeItem<>("Ares 7021"),
                new TreeItem<>("Ares 7031"),
                new TreeItem<>("Ares 7023")
        );

        treeItem.getChildren().get(1).getChildren().addAll(
                new TreeItem<>("Ares 8023 15"),
                new TreeItem<>("Ares 8023 200"),
                new TreeItem<>("Ares 8023 2,5")
        );

        treeItem.setValue("Ares");
        treeItem.setExpanded(true);
        expectedTreeView.setRoot(treeItem);
        expectedTreeView.setShowRoot(false);

        return expectedTreeView;
    }

    public boolean compareCategories(TreeView<String> expectedTreeView, TreeView<String> treeView) {
        int cont = 0;
        for (TreeItem<String> treeItem : treeView.getRoot().getChildren()) {
            assertEquals(expectedTreeView.getRoot().getChildren().get(cont).getValue(), treeItem.getValue());
            compareModels(expectedTreeView.getRoot().getChildren().get(cont), treeItem);
            cont++;
        }

        return true;
    }

    public void compareModels(TreeItem<String> expectedTreeItem, TreeItem<String> treeItem) {
        int cont = 0;
        for (TreeItem<String> treeItemChild : treeItem.getChildren()) {
            assertEquals(expectedTreeItem.getChildren().get(cont).getValue(), treeItemChild.getValue());
            cont++;
        }
    }
}