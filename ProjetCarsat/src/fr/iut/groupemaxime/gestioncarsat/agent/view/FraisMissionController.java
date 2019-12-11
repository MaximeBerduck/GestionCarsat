package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

public class FraisMissionController {
	
	private SplitPane fraisMissionSplit;
	private AnchorPane pageDate;
	private DateFMController dateController;
	
	public void afficherFMDate()
	{
		if(this.pageDate!=null)
		{
			if(1 < this.fraisMissionSplit.getItems().size())
				this.fraisMissionSplit.getItems().set(1, this.pageDate);
			else
				this.fraisMissionSplit.getItems().add(1, this.pageDate);
		}
		else
		{
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource("FraisDate.fxml"));
				this.pageDate = loader.load();
				
				this.dateController=loader.getController();
				this.dateController.setMainApp(this);
				
				if(1 < this.fraisMissionSplit.getItems().size())
					this.fraisMissionSplit.getItems().set(1, this.pageDate);
				else
					this.fraisMissionSplit.getItems().add(1, this.pageDate);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
	}
}
