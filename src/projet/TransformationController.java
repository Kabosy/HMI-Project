package projet;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import transforms.LibraryException;
import transforms.élémentaires.Homothetie;
import transforms.élémentaires.Rotation;
import transforms.élémentaires.Transformation;
import transforms.élémentaires.Translation;

public class TransformationController {
	@FXML
	ToggleButton choixTransition;
	@FXML
	ToggleButton choixRotation;
	@FXML
	ToggleButton choixHomothetie;
	@FXML
	TextField posX;
	@FXML
	TextField posY;
	@FXML
	TextField degRota;
	@FXML
	Button ajout;
	@FXML
	Text transfosChoisis;
	@FXML
	Text rotationsChoisis;
	@FXML
	Text homothetiesChoisis;
	@FXML
	Text x = new Text("");
	@FXML
	Text y = new Text("");
	@FXML
	Text z = new Text("");
	@FXML
	Button validation;
	
	private int transfo = 0;
	private String listeRotations = "";
	private String listeTranslations = "";
	private String listeHomotheties = "";
	public static ArrayList<Transformation> transfos = new ArrayList<Transformation>();
	
	public ArrayList<Transformation> getTransformations() {
		return transfos;
	}
	
	public TransformationController() {	
	}
	
	public void doTransition() {
		if(choixTransition.isSelected()) {
			choixRotation.setVisible(false);
			choixHomothetie.setVisible(false);
			transfo = 1;
			degRota.setEditable(false);
			degRota.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
			x.setText("Coord. X :");
			y.setText("Coord. Y :");
			z.setText("Degré :");
		} else {
			choixRotation.setVisible(true);
			choixTransition.setSelected(false);
			choixHomothetie.setVisible(true);
		}
		System.out.println(transfo+"transition");
	}
	
	public void doRotation() {
		if(choixRotation.isSelected()) {
			choixTransition.setVisible(false);
			choixHomothetie.setVisible(false);
			transfo = 2;
			degRota.setEditable(true);
			degRota.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
			x.setText("Coord. X :");
			y.setText("Coord. Y :");
			z.setText("Degré :");
		} else {
			choixRotation.setSelected(false);
			choixTransition.setVisible(true);
			choixHomothetie.setVisible(true);
		}
		System.out.println(transfo+"rotation");
	}
	
	public void doHomothetie() {
		if(choixHomothetie.isSelected()) {
			choixRotation.setVisible(false);
			choixTransition.setVisible(false);
			transfo = 3;
			degRota.setEditable(true);
			degRota.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
			x.setText("Scale :");
			y.setText("Center X:");
			z.setText("Center Y:");
		} else {
			choixRotation.setVisible(true);
			choixTransition.setVisible(true);
			choixHomothetie.setSelected(false);
		}
		System.out.println(transfo+"rotation");
	}
	
	public void doAjouter() {
			System.out.println(transfo+"ajout");	
			
			
			switch(transfo) {
			case 1:
				listeTranslations+="T : ("+posX.getText()+","+posY.getText()+")\n";
				transfosChoisis.setText(listeTranslations);
				transfos.add(new Translation(Double.parseDouble(posX.getText()),Double.parseDouble(posY.getText())));
				break;
			case 2:
				listeRotations+="R : "+degRota.getText()+"°,("+posX.getText()+","+posY.getText()+")\n";
				rotationsChoisis.setText(listeRotations);
				transfos.add(new Rotation(Double.parseDouble(degRota.getText()),Double.parseDouble(posX.getText()),Double.parseDouble(posY.getText())));
				break;
			case 3:
				listeHomotheties+="H : Ech. : "+posX.getText()+" ("+posY.getText()+","+degRota.getText()+")\n";
				homothetiesChoisis.setText(listeHomotheties);
				transfos.add(new Homothetie(Double.parseDouble(posX.getText()),Double.parseDouble(posY.getText()),Double.parseDouble(degRota.getText())));
				break;
			}
			
			for(Transformation t : transfos) {
        		MainController.composition.add(t);
        	}
			transfos.removeAll(transfos);
			choixTransition.setSelected(false);
			choixTransition.setVisible(true);
			choixRotation.setSelected(false);
			choixRotation.setVisible(true);
			choixHomothetie.setSelected(false);
			choixHomothetie.setVisible(true);
		
	}
	//L'ANIMATION NE MARCHE PAS DANS LA MAIN PAGE --> LibraryException index (1) hors limite ??
	public void onValidation(ActionEvent actionEvent) throws LibraryException{
		// get a handle to the stage
	    Stage stage = (Stage) validation.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	
}