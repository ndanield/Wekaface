package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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

import java.io.*;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private Button btnClassify = new Button();

    @FXML
    private Button btnOpen = new Button();

    @FXML
    private JFXButton btnOpen1;

    @FXML
    private TextArea resultArea = new TextArea();

    @FXML
    private JFXTextField txtResult = new JFXTextField();

    @FXML
    private Label lblOpenedFile = new Label();

    private final FileChooser fileChooser = new FileChooser();

    ObservableList tablemodel = FXCollections.observableArrayList();

    @FXML
    private JFXTextField txtFilepath;

    @FXML
    private JFXTextField txtFilepath1;

    @FXML
    private JFXCheckBox defaultset;





    @FXML
    private JFXButton btnAgregar;


    private Instances data;

    @FXML
    private void handleButtonOpen() throws Exception {

        /*File file = fileChooser.showOpenDialog(btnClassify.getScene().getWindow());
        txtFilepath.setText(file.getName());
        String filePath = file.getAbsolutePath();

        DataSource source = new DataSource(filePath);
        data = source.getDataSet();
*/

        }


    @FXML
    private void handleButtonClassify() {
        try{

            String prg = "import sys\nprint int(sys.argv[1])+int(sys.argv[2])\n";
            BufferedWriter out = new BufferedWriter(new FileWriter("test1.py"));
            out.write(prg);
            out.close();
            int number1 = 10;
            int number2 = 32;
            Process p = Runtime.getRuntime().exec("python test1.py "+number1+" "+number2);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int ret = new Integer(in.readLine()).intValue();
            System.out.println("value is : "+ret);
        }

        catch(Exception e){

        }
    }


    @FXML
    private void handleButtonAgregar() {

    }

    private void classify(Instances data) throws Exception {


//
        /*nstance inst = new DenseInstance(data.numAttributes());
        inst.setDataset(data);
        for (int i = 0; i < data.numAttributes(); i++) {
            inst.setValue(i, attr.get(i));
        }
        data.add(inst);
*/

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
//           txtResult.setText(result);
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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        btnClassify.setDisable(false);
        //fileChooser.setInitialDirectory(new File("C:\\Users\\NelsonDaniel\\workspace\\Wekaface\\datasets"));
    }







}