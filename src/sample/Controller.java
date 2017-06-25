package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable{

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

    @FXML
    private void handleButtonOpen() {
        btnClassify.setDisable(false);


        file = fileChooser.showOpenDialog(btnClassify.getScene().getWindow());
        lblOpenedFile.setText(file.getName());
    }

    @FXML
    private void handleButtonClassify() {
        try {
            classify(file.getAbsolutePath());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void classify(String path) throws Exception {


        DataSource source = new DataSource(path);
        Instances data = source.getDataSet();

        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);

        /*
        String atributosNewInst = "rainy,mild,high,FALSE,yes";
        String [] atributos = atributosNewInst.split(",");

        Instance inst = new DenseInstance(data.numAttributes());
        for (int i = 0; i < data.numAttributes(); i++) {
            inst.setValue(i, atributos[i]);
        }
        data.add(inst);
        */

        J48 tree = new J48();
        tree.buildClassifier(data);
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(tree, data, 10, new Random(1));


        StringBuffer        result;

        result = new StringBuffer();
        result.append("\n================================\nPrueeeeeeeeba\n===============================\n\n");

        result.append("Classifier...: " + tree.getClass().getName() +"\n");
        result.append(eval.toClassDetailsString() + "\n");
        result.append(tree.toString() + "\n");
        result.append(eval.toSummaryString() + "\n");
        result.append(eval.toMatrixString() + "\n");


        resultArea.setText(result.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnClassify.setDisable(true);
        fileChooser.setInitialDirectory(new File("C:\\Users\\NelsonDaniel\\workspace\\Wekaface\\datasets"));
    }
}