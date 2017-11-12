import java.io.IOException;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class InterfaceLoaderFXML extends Application{
    private static final String FXML_FILE = "Interface.fxml";
    private Stage stage;

    @FXML
    private TextField offerStockName;
    @FXML
    private TextField offerStockPrice;
    @FXML
    private TextField offerStocQuantity;
    @FXML
    private TextField offerStockBuyerName;

    @FXML
    private TextField soldStockQuantity;
    @FXML
    private TextField soldStockPrice;
    @FXML
    private TextField soldStockName;
    @FXML
    private TextField soldStockOwnerName;



    @Override
    public void start(Stage stage) {
        this.stage = stage;
        loadFXMl();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() { 
                Platform.exit();
            }
        });
    }

    public void loadFXMl() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(FXML_FILE));
            Scene scene = new Scene(root, 900, 600);
            
            stage.setTitle("Stock market");
            stage.setScene(scene);
            stage.show();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    @FXML
    public void createDemand(ActionEvent event) {
        System.out.println(soldStockName.getText() + " " + soldStockPrice.getText() + " " + soldStockQuantity.getText() + " " + soldStockOwnerName.getText());
    }

    @FXML
    public void createOffer(ActionEvent event) {
        System.out.println(offerStockName.getText() + " " + offerStockPrice.getText() + " " + offerStocQuantity.getText() + " " + offerStockBuyerName.getText());
    }
}