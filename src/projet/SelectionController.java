package projet;



import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import transforms.mobile.MotifConcret;

public class SelectionController extends MotifConcret{
	
	@FXML
	Pane pane;
	
	@FXML
	Button validation;
	
	public static ArrayList<Double> points = new ArrayList<Double>();
	public Double posX;
	public Double posY;
	public Polyline poly = new Polyline();
	
	
	public SelectionController() {
		super(MainController.composition, points);
	}
	
	public void initialize() {
		pane.getChildren().add(poly);
		poly.setStroke(Color.BLACK);
	}
	
	public void onValidation(ActionEvent actionEvent) {
		for(Double d : poly.getPoints()) {
			d = d%10;
		}
		System.out.println(poly.getPoints());
		for(Double d : points) {
			d = d%10;
		}
		System.out.println(points);
		MainController.composition.setMotif(new SelectionController());
		// get a handle to the stage
	    Stage stage = (Stage) validation.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	
	public void setOnMouseClicked() {
		System.out.println(poly.getPoints());
		pane.setMinSize(548, 301);
		pane.setOnMouseClicked(e->{
			System.out.println(poly.getPoints());
			System.out.println(points);
			System.out.println("alo");
				poly.getPoints().add(e.getX());
				poly.getPoints().add(e.getY());
				points.add(e.getX());
				points.add(e.getY());
			
		});
	}
	

}