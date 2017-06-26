package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button btnClassify = new Button();

    @FXML
    private Button btnOpen = new Button();

    @FXML
    private TextArea resultArea = new TextArea();

    @FXML
    private Label lblOpenedFile = new Label();

    private final FileChooser fileChooser = new FileChooser();
    private File file;

    static String filePath;

    ObservableList tablemodel = FXCollections.observableArrayList();

    @FXML
    TableView<ObservableList<String>> tableView = new TableView<>();

//controles de la instancia

    @FXML
    private JFXTextField rainyF;

    @FXML
    private JFXTextField coolF;

    @FXML
    private JFXTextField highF;

    @FXML
    private JFXTextField falseF;

    @FXML
    private JFXTextField yesF;

    @FXML
    private JFXTextField agregarF;




    @FXML
    private void handleButtonOpen() {
        btnClassify.setDisable(false);


        file = fileChooser.showOpenDialog(btnClassify.getScene().getWindow());
        lblOpenedFile.setText(file.getName());
        filePath = file.getAbsolutePath();

    }

    @FXML
    private void handleButtonClassify() {
        try {
            classify(filePath);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    private void handleButtonAgregar() {
        try {
            classify(filePath);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void classify(String path) throws Exception {


        DataSource source = new DataSource(path);
        Instances data = source.getDataSet();
        //tablemodel.add(data);
        //tableView.getItems().addAll(tablemodel);


        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);


        String[] atributos = {"rainy", "cool", "high", "FALSE", "yes"};
        Instance inst = new DenseInstance(data.numAttributes());

        inst.setDataset(data);
        for (int i = 0; i < data.numAttributes(); i++) {
            inst.setValue(i, atributos[i]);
        }
        data.add(inst);

        J48 tree = new J48();
        tree.buildClassifier(data);
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(tree, data, 10, new Random(1));

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.numInstances(); i++) {
            double pred = tree.classifyInstance(data.instance(i));
            result.append("No. " + (i + 1) + "\n");
            result.append(data.instance(i).toString() + "\n");
            result.append("Valor predecido: " + data.classAttribute().value((int) pred) + "\n");
            result.append("Valor real: " + data.classAttribute().value((int) data.instance(i).classValue()) + "\n\n");
        }

        /*
        StringBuilder       result = new StringBuilder();
        result.append("\n================================\nPrueeeeeeeeba\n===============================\n\n");

        result.append("Classifier...: " + tree.getClass().getName() +"\n");
        result.append(eval.toClassDetailsString() + "\n");
        result.append(tree.toString() + "\n");
        result.append(eval.toSummaryString() + "\n");
        result.append(eval.toMatrixString() + "\n");
  */

        resultArea.setText(result.toString());



    for( int i = 0; i<data.numAttributes(); i++) {
        final int finalIdx = i;
        TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                data.attribute(finalIdx).name()
        );
        column.setCellValueFactory(param ->
                new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
        );
        tableView.getColumns().add(column);
    }



}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnClassify.setDisable(true);
        //fileChooser.setInitialDirectory(new File("C:\\Users\\NelsonDaniel\\workspace\\Wekaface\\datasets"));
    }







}