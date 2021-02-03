package projet;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelpController {
	@FXML
	Button close;
	
	public void onClose() {
		// get a handle to the stage
	    Stage stage = (Stage) close.getScene().getWindow();
	    
	    // do what you have to do
	    stage.close();
	}
}