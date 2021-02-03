package projet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import transforms.Composition;
import transforms.LibraryException;
import transforms.mobile.Motif;
import transforms.mobile.MotifConcret;

public class MainController {
	

	@FXML
    Pane pane;

    @FXML
    Button displayAll;

    @FXML
    ToggleButton zoom;
    
    @FXML
    Button transform;
    
    @FXML
    Button create;
    
    @FXML
    Button validation;
    
    @FXML
    Button help;
    @FXML
    Button reset;
    
    
    
    
	public static final double maxX = 10.0;
	public static final double maxY = 10.0;
	public static Composition composition = new Composition();
    public List<Node> allNodes;
    private Polyline poly = new Polyline();
    private TransformationController transformations = new TransformationController();
    private ArrayList<Double> motifInitial = new ArrayList<Double>();

	public void initialize() {
        /*
        Construction de la composition
         */
		poly.setStroke(Color.BLACK);
		validation.setVisible(false);
		//CREATION DU MOTIF INITIAL
		motifInitial.add(0.0);
		motifInitial.add(0.0);
		motifInitial.add(0.0);
		motifInitial.add(1.0);
		motifInitial.add(0.5);
		motifInitial.add(1.5);
		motifInitial.add(1.0);
		motifInitial.add(1.0);
		motifInitial.add(1.0);
		motifInitial.add(0.0);
		motifInitial.add(0.75);
		motifInitial.add(0.0);
		motifInitial.add(0.75);
		motifInitial.add(0.75);
		motifInitial.add(0.5);
		motifInitial.add(0.75);
		motifInitial.add(0.5);
		motifInitial.add(0.0);
		motifInitial.add(0.75);
		motifInitial.add(0.0);
		motifInitial.add(0.0);
		motifInitial.add(0.0);
		composition.setMotif(new MotifConcret(composition, motifInitial));

        pane.getChildren().add(composition.getGrille());
    }

    /*
    Affichage de l'éta initial (0) et des étapes 1 et 3
     */
    public void doMontrer(ActionEvent actionEvent) {
        
            // Affichage des états 0 (initial), 1 et 3
            System.out.println(composition.getSequence());
            try {
            	
            	for(int i = 0; i < composition.getSequence().size()+1; i++) {
                	pane.getChildren().add(composition.getStep(i).toGroup());
                }
            } catch (LibraryException e) {
                e.printStackTrace();
            }
    }
    
    public void setTransformation(ActionEvent actionEvent) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("transfo.fxml"));
    	final Stage trans = new Stage();
		trans.setResizable(false);
        Scene rulesScene = new Scene(root);
        trans.setScene(rulesScene);
        trans.show();
    }
    
    public void onCreate() {
    	onReset();
    	validation.setVisible(true);
 
    	pane.setOnMouseClicked(e->{
			System.out.println(poly.getPoints());
			if(composition.xMouseToMath(e.getX())>maxX) {
				poly.getPoints().add(maxX);
			} else if(composition.xMouseToMath(e.getX())<(-maxX)){
				poly.getPoints().add(-maxX);
			} else {
				poly.getPoints().add(composition.xMouseToMath(e.getX()));
			}
			if(composition.yMouseToMath(e.getY())>maxY) {
				poly.getPoints().add(maxY);
			} else if(composition.yMouseToMath(e.getY())<(-maxY)){
				poly.getPoints().add(-maxY);
			} else {
				poly.getPoints().add(composition.yMouseToMath(e.getY()));
			}
		});
    }
    
    public void onCreateValidation() {
    	composition.setMotif(new MotifConcret(composition,poly.getPoints()));
    	validation.setVisible(false);
    }

    /*
    Affichage de quelques matrices
     */
    public void doMatrices(ActionEvent actionEvent) {
        try {
        	for(int i =0; i < composition.getSequence().size(); i++) {
        		System.out.println(Arrays.deepToString(composition.getAtomicMatrix(i)));
        		System.out.println(Arrays.deepToString(composition.getComposedMatrix(i)));
        	}
        } catch (LibraryException e) {
            e.printStackTrace();
        }
    }

    /*
    Animation entre l'étape 1 et l'étape 3
     */
    public void doAnimer(ActionEvent actionEvent) {
    	System.out.println(transformations.getTransformations());
        try {
            final Motif mobile = composition.getStep(0);
            mobile.setStroke(Color.BLUE);
            pane.getChildren().add(mobile.toGroup());
            composition.animate(
                    mobile.toGroup(),
                    0,
                    composition.getSequence().size(),
                    e -> pane.getChildren().remove(mobile.toGroup())
            ).play();    // Animation entre les étapes 1 et 3
        } catch (LibraryException e) {
            e.printStackTrace();
        }
    }

    public void showCoord(MouseEvent mouseEvent) {
        double xMouse = mouseEvent.getX();
        double yMouse = mouseEvent.getY();
        System.out.println("X vaut : " + composition.xMouseToMath(xMouse));
        System.out.println("Y vaut : " + composition.yMouseToMath(yMouse));
    }

    public void doZoom(ActionEvent actionEvent) {
        if (!zoom.isSelected()) {
            composition.setZoom(40.0, 400.0, 400.0);
        } else {
            composition.setZoom(20.0, 200.0, 300.0);
        }
    }
    //reinitialise la grille avec la maison de départ
    //CREER SA PROPRE MAISON INITIALE POUR RESET CORRECTEMENT
    public void onReset() {
    	
    	composition.getSequence().remove(0, composition.getSequence().size());
    	poly.getPoints().removeAll(poly.getPoints());
    	transformations.getTransformations().removeAll(transformations.getTransformations());
    	pane.getChildren().removeAll(pane.getChildren());
    	initialize();
    	
    }
    
    public void onHelp() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("help.fxml"));
    	final Stage trans = new Stage();
		trans.setResizable(false);
        Scene rulesScene = new Scene(root);
        trans.setScene(rulesScene);
        trans.show();
    }
}