import javafx.scene.control.TextArea;
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
    Client client = new Client();

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

    @FXML
    private TextArea offerArea;
    @FXML
    private TextArea demandArea;
    @FXML
    private TextArea transactionArea;

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
    public void showOffers(ActionEvent event) {
        offerArea.clear();
        offerArea.setText(client.getRequest(Client.OFFERS_URL));
    }

    @FXML
    public void showDemands(ActionEvent event) {
        demandArea.clear();
        demandArea.setText(client.getRequest(Client.DEMANDS_URL));
    }

    @FXML
    public void showTransactions(ActionEvent event) {
        transactionArea.clear();
        transactionArea.setText(client.getRequest(Client.TRANSACTIONS_URL));
    }

    public void clearOfferFields() {
        soldStockName.clear();
        soldStockOwnerName.clear();
        soldStockPrice.clear();
        soldStockQuantity.clear();
    }

    @FXML
    public void createDemand(ActionEvent event) {
        String demand_parameters = "name="+soldStockName.getText() + "\nvalue="+ soldStockPrice.getText() + "\nquantity=" + soldStockQuantity.getText() + "\ninfo=" + soldStockOwnerName.getText();
        client.postRequest(Client.DEMANDS_URL, demand_parameters);
        System.out.println(demand_parameters);
        clearOfferFields();
    }

    public void clearDemandFields() {
        offerStockBuyerName.clear();
        offerStockName.clear();
        offerStocQuantity.clear();
        offerStockPrice.clear();
    }

    @FXML
    public void createOffer(ActionEvent event) {
        String offer_parameters = "name="+offerStockName.getText() + "\nvalue="+ offerStockPrice.getText() + "\nquantity=" + offerStocQuantity.getText() + "\ninfo=" + offerStockBuyerName.getText();
        client.postRequest(Client.OFFERS_URL, offer_parameters);
        System.out.println(offer_parameters);
        clearDemandFields();
    }
}