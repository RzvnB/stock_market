import java.io.IOException;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;


public class InterfaceLoaderFXML extends Application{
    private static final String FXML_FILE = "Interface.fxml";
    private Stage stage;
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
            
            Scene scene = new Scene(root, 400, 300);
            
            stage.setTitle("Stock market");
            stage.setScene(scene);
            stage.show();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }
}