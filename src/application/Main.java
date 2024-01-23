package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	private int dx;
	private int dy;
	private int score = 0;

	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			Scene scene = new Scene(root, 1000, 600);
			scene.setFill(new RadialGradient(0, 0, 500, 300, 500, false, CycleMethod.NO_CYCLE,
					new Stop(0, Color.grayRgb(60)), new Stop(1, Color.BLACK)));

			Circle balle = new Circle(500, 300, 25);
			balle.setFill(Color.DARKOLIVEGREEN);

			Rectangle joueur = new Rectangle(890, 250, 30, 100);
			joueur.setArcHeight(5);
			joueur.setArcWidth(5);
			joueur.setFill(Color.CORNFLOWERBLUE);

			Rectangle IA = new Rectangle(80, 250, 30, 100);
			IA.setFill(Color.CORNFLOWERBLUE);
			IA.setArcHeight(5);
			IA.setArcWidth(5);

			Text perdu = new Text("PERDU !");
			perdu.setFont(Font.font("Verdana", 70));
			perdu.setFill(Color.RED);
			perdu.setTextOrigin(VPos.CENTER);
			perdu.setLayoutX(400);
			perdu.setLayoutY(300);
			perdu.setVisible(false);
			// regardez l'astuce ! on rend le texte invisible !

			root.getChildren().addAll(joueur, IA, balle, perdu);

			dx = 5;
			dy = 5;

			Timeline loop = new Timeline(new KeyFrame(Duration.millis(1), (ActionEvent e) -> {

				balle.setCenterX(balle.getCenterX() + dx);
				balle.setCenterY(balle.getCenterY() + dy);

				if (balle.getCenterY() <= 25) {
					dy = 5;
				}

				// rebond Bas:
				if (balle.getCenterY() >= 575) {
					dy = -5;
				}

				// rebond D:
				if (balle.getCenterX() >= 865) {
					if (balle.getCenterY() >= joueur.getY() && balle.getCenterY() <= joueur.getY() + 100) {
						// cette condition vérifie que la coordonnée Y de la balle est comprise dans
						// l'intervalle:
						// [coordonnée Y haut de barre coordonnée Y bas de barre]
						dx = -5;
					}

				}

				// rebond G:
				if (balle.getCenterX() <= 110) {
					if (balle.getCenterY() >= IA.getY() && balle.getCenterY() <= IA.getY() + 100) {
						dx = 5;
						score++;
						primaryStage.setTitle("Pong SCORE : " + score);
					}

				}
				// deplacement IA:
				if (IA.getY() + 50 < balle.getCenterY()) {
					IA.setY(IA.getY() + 5);
				}
				if (IA.getY() + 50 > balle.getCenterY()) {
					IA.setY(IA.getY() - 5);
				}

				if (balle.getCenterX() >= 890) {
					dx = 0;
					dy = 0;
					perdu.setVisible(true);
				}

			}));

			loop.setCycleCount(Timeline.INDEFINITE);
			loop.play();

			scene.setOnMouseMoved(e -> {

				joueur.setY(e.getSceneY() - 50);

			});

			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Pong SCORE : 0");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
