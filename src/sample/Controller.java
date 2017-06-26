package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;

import java.io.File;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private Button btnClassify = new Button();

    @FXML
    private JFXTextField txtFilepath = new JFXTextField();

    @FXML
    private Button btnOpen = new Button();

    @FXML
    private TextArea resultArea = new TextArea();

    @FXML
    private JFXTextField txtResult = new JFXTextField();

    @FXML
    private Label lblOpenedFile = new Label();

    private final FileChooser fileChooser = new FileChooser();

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
    private JFXComboBox<String> cmbOutlook;
    @FXML
    private JFXComboBox<String> cmbTemperature;
    @FXML
    private JFXComboBox<String> cmbHumidity;
    @FXML
    private JFXComboBox<String> cmbWindy;
    @FXML
    private JFXComboBox<String> cmbPlay;



    @FXML
    private JFXButton btnAgregar;


    private Instances data;

    @FXML
    private void handleButtonOpen() throws Exception {
        btnClassify.setDisable(false);
//        btnAgregar.setDisable(false);
        if (tableView.getColumns() != null) {
            tableView.getColumns().clear();
        }
        if (tableView.getItems() != null) {
            tableView.getItems().clear();
        }

        File file = fileChooser.showOpenDialog(btnClassify.getScene().getWindow());
        txtFilepath.setText(file.getName());
        String filePath = file.getAbsolutePath();

        DataSource source = new DataSource(filePath);
        data = source.getDataSet();

        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);

        setComboBoxes(data);
        int index;
        for(index = 0; index < data.numAttributes()+1; index++) {
            final int finalIdx = index;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    (index != data.numAttributes()) ? data.attribute(finalIdx).name() : "Valor predicho"
            );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            tableView.getColumns().add(column);
        }
    }

    @FXML
    private void handleButtonClassify() {
        try {
            classify(data);
        } catch (IllegalArgumentException e1) {
            e1.getCause();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @FXML
    private void handleButtonAgregar() {
        ObservableList<String> attr = FXCollections.observableArrayList(
                cmbOutlook.getSelectionModel().getSelectedItem(),
                cmbTemperature.getSelectionModel().getSelectedItem(),
                cmbHumidity.getSelectionModel().getSelectedItem(),
                cmbWindy.getSelectionModel().getSelectedItem(),
                cmbPlay.getSelectionModel().getSelectedItem()
        );

        tableView.getItems().add(attr);
    }

    private void classify(Instances data) throws Exception {


//        ObservableList<String> attr = tableView.getSelectionModel().getSelectedItem();
        ObservableList<String> attr = FXCollections.observableArrayList(
                cmbOutlook.getSelectionModel().getSelectedItem(),
                cmbTemperature.getSelectionModel().getSelectedItem(),
                cmbHumidity.getSelectionModel().getSelectedItem(),
                cmbWindy.getSelectionModel().getSelectedItem(),
                cmbPlay.getSelectionModel().getSelectedItem()
        );

        Instance inst = new DenseInstance(data.numAttributes());
        inst.setDataset(data);
        for (int i = 0; i < data.numAttributes(); i++) {
            inst.setValue(i, attr.get(i));
        }
        data.add(inst);


        J48 tree = new J48();
        tree.buildClassifier(data);
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(tree, data, 10, new Random(1));

        int last = data.numInstances()-1;
        double pred = tree.classifyInstance(data.instance(last));
        String prediccion = data.classAttribute().value((int) pred);
        attr.add(prediccion);
        tableView.getItems().add(attr);
        String result = "Valor predecido: " + prediccion + "\n";


//        StringBuilder result = new StringBuilder();
//        for (int i = 0; i < data.numInstances(); i++) {
//            double pred = tree.classifyInstance(data.instance(i));
//            result.append("No. " + (i + 1) + "\n");
//            result.append(data.instance(i).toString() + "\n");
//            result.append("Valor predecido: " + data.classAttribute().value((int) pred) + "\n");
//            result.append("Valor real: " + data.classAttribute().value((int) data.instance(i).classValue()) + "\n\n");
//        }

        /*
        StringBuilder       result = new StringBuilder();
        result.append("\n================================\nPrueeeeeeeeba\n===============================\n\n");

        result.append("Classifier...: " + tree.getClass().getName() +"\n");
        result.append(eval.toClassDetailsString() + "\n");
        result.append(tree.toString() + "\n");
        result.append(eval.toSummaryString() + "\n");
        result.append(eval.toMatrixString() + "\n");
  */

//        resultArea.setText(result.toString());
        txtResult.setText(result);
    }

    private void setComboBoxes(Instances data) {

        ArrayList<Attribute> materia = new ArrayList<>();
        for (int i = 0; i < data.numAttributes(); i++) {
            materia.add(data.attribute(i));
        }

        ArrayList<String[]> materiaPrima= new ArrayList<>();
        for (int i = 0; i < materia.size(); i++) {
            Attribute a = materia.get(i);
            String[] op = new String[a.numValues()];
            for (int j = 0; j < a.numValues(); j++) {
                op[j] = a.value(j);
            }
            materiaPrima.add(op);
        }


        cmbOutlook.getItems().clear();
        cmbTemperature.getItems().clear();
        cmbHumidity.getItems().clear();
        cmbWindy.getItems().clear();
        cmbPlay.getItems().clear();

        cmbOutlook.getItems().addAll(materiaPrima.get(0));
        cmbTemperature.getItems().addAll(materiaPrima.get(1));
        cmbHumidity.getItems().addAll(materiaPrima.get(2));
        cmbWindy.getItems().addAll(materiaPrima.get(3));
        cmbPlay.getItems().addAll(materiaPrima.get(4));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        btnClassify.setDisable(false);
        //fileChooser.setInitialDirectory(new File("C:\\Users\\NelsonDaniel\\workspace\\Wekaface\\datasets"));
    }







}