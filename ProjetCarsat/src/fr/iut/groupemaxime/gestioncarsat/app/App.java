package fr.iut.groupemaxime.gestioncarsat.app;

import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.responsable.ResponsableApp;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Carsat - Gestion des déplacement");
		this.primaryStage.getIcons().add(new Image("file:" + Constante.CHEMIN_IMAGES + "logo.png"));
		this.primaryStage.setResizable(false);

		initialiseChoixApp();
	}

	public void initialiseChoixApp() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("ChoixApp.fxml"));
			this.rootLayout = loader.load();
			ChoixAppController controllerRoot = loader.getController();
			controllerRoot.setApp(this);

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void agentAppOpen() {
		AgentApp agentApp = new AgentApp();
		this.primaryStage.close();
		try {
			agentApp.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void responsableAppOpen() {
		ResponsableApp responsableApp = new ResponsableApp();
		this.primaryStage.close();
		try {
			responsableApp.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
