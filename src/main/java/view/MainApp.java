package view;

import client.ViaCepClient;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CepResponse;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends Application {

    private static final Logger logger = Logger.getLogger(MainApp.class.getName());
    private TabPane tabPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("BUSCA CEP DO RUAN");

            VBox root = new VBox();
            root.setPadding(new Insets(20));
            root.setSpacing(10);
            root.setAlignment(Pos.CENTER);
            root.getStyleClass().add("root");


            Label titleLabel = new Label("BUSCA CEP DO RUAN");
            titleLabel.getStyleClass().add("title");

            TextField cepInput = new TextField();
            cepInput.setPromptText("Digite o CEP");
            cepInput.getStyleClass().add("text-field");

            cepInput.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    cepInput.setText(newValue.replaceAll("\\D", ""));
                }
                if (newValue.length() > 8) {
                    cepInput.setText(oldValue);
                }
            });


            Button consultarButton = new Button("Consultar");
            consultarButton.getStyleClass().add("button");
            consultarButton.setOnAction(e -> {
                String cep = cepInput.getText();
                if (cep != null && cep.length() == 8) {
                    consultaCep(cep);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Por favor, digite um CEP válido com 8 dígitos.");
                    alert.show();
                }
            });

            HBox inputBox = new HBox();
            inputBox.setSpacing(10);
            inputBox.setAlignment(Pos.CENTER);
            inputBox.getStyleClass().add("input-box");
            inputBox.getChildren().addAll(cepInput, consultarButton);


            tabPane = new TabPane();

            root.getChildren().addAll(titleLabel, inputBox, tabPane);

            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro:", e);
        }
    }

    private void consultaCep(String cep) {
        try {
            ViaCepClient client = new ViaCepClient();
            String jsonResponse = client.getCepInfo(cep);

            if (jsonResponse != null) {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                CepResponse cepResponse = new CepResponse(jsonObject);
                Tab tab = new Tab("CEP: " + cep);

                VBox vbox = new VBox();
                vbox.setPadding(new Insets(10));
                vbox.setSpacing(5);
                vbox.getStyleClass().add("tab-content");

                Map<String, String> infoMap = getStringStringMap(cepResponse);

                for (Map.Entry<String, String> entry : infoMap.entrySet()) {
                    vbox.getChildren().add(createLabel(entry.getKey() + ": " + entry.getValue()));
                }

                tab.setContent(vbox);
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Não foi possível obter informações para o CEP informado.");
                alert.show();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao consultar o CEP:", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao consultar o CEP: " + e.getMessage());
            alert.show();
        }
    }

    @NotNull
    private static Map<String, String> getStringStringMap(CepResponse cepResponse) {
        Map<String, String> infoMap = new LinkedHashMap<>();
        infoMap.put("CEP", cepResponse.getCep());
        infoMap.put("Logradouro", cepResponse.getLogradouro());
        infoMap.put("Complemento", cepResponse.getComplemento());
        infoMap.put("Bairro", cepResponse.getBairro());
        infoMap.put("Localidade", cepResponse.getLocalidade());
        infoMap.put("UF", cepResponse.getUf());
        infoMap.put("IBGE", cepResponse.getIbge());
        infoMap.put("GIA", cepResponse.getGia());
        infoMap.put("DDD", cepResponse.getDdd());
        infoMap.put("SIAFI", cepResponse.getSiafi());
        return infoMap;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("label");
        return label;
    }
}
