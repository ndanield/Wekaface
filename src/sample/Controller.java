package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;
import java.util.Random;

public class Controller {

    @FXML
    private Button btnClasify = new Button();

    @FXML
    private TextArea resultArea = new TextArea();

    @FXML
    private void handleButtonAction(ActionEvent e) {
        if (e.getSource() == btnClasify)
        {
            try {
                classify();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void classify() throws Exception {
        DataSource source = new DataSource("datasets/weather.nominal.arff");
        Instances data = source.getDataSet();

        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);

        J48 tree = new J48();
        tree.buildClassifier(data);
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(tree, data, 10, new Random(1));

        StringBuffer        result;

        result = new StringBuffer();
        result.append("Weka - Demo\n===========\n\n");

        result.append("Classifier...: "
                + tree.getClass().getName() + " "
                + Utils.joinOptions(tree.getOptions()) + "\n");

        result.append(tree.toString() + "\n");
        result.append(eval.toSummaryString() + "\n");

        result.append(eval.toMatrixString() + "\n");
        result.append(eval.toClassDetailsString() + "\n");

        resultArea.setText(result.toString());
    }
}