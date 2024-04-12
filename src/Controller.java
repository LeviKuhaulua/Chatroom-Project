import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private TextField tfName;

    @FXML
    void onBtnClick(ActionEvent event) {
        Stage mainWindow = (Stage) tfName.getScene().getWindow(); 
        String title = tfName.getText(); 
        mainWindow.setTitle(title); 
    }

}
