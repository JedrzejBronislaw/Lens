package jedrzejbronislaw.lens.lensViewer.controllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import jedrzejbronislaw.lens.Languages;
import jedrzejbronislaw.lens.Task;
import jedrzejbronislaw.lens.Tasks;
import jedrzejbronislaw.lens.lensViewer.Photo;
import jedrzejbronislaw.lens.tools.MyFXMLLoader;
import jedrzejbronislaw.lens.tools.MyFXMLLoader.NodeAndController;
import lombok.Setter;

public class MainWindowController implements Initializable{

	@FXML
	private Label sourceLabel;
	@FXML
	private Label destinationLabel;
	@FXML
	private Label photosNumberLabel;
	@FXML
	private VBox mainBox;
	@FXML
	private Button executeButton;
	@FXML
	private MenuItem englishItem;
	@FXML
	private MenuItem polishItem;
	@FXML
	private Menu taskMenu;
	
	
	@Setter
	private Consumer<File> selectSourceDirEvent;
	@Setter
	private Consumer<File> selectDestinationDirEvent;
//	@Setter
//	private Runnable executeClick;
	@Setter
	private Consumer<Task> executeTask;
	
	@Setter
	private Consumer<Languages> changeGUILanguage;
	
	
	public void setTasks(Tasks[] tasks) {
		MenuItem item;
		
		for(int i = 0; i<tasks.length; i++) {

			Tasks task = tasks[i];
			item = new MenuItem(task.getName());
			item.setOnAction(e -> {
				if(executeTask != null)
					executeTask.accept(task.create());
			});
			taskMenu.getItems().add(item);
			
		}
	}

	private void chooseSource() {
		DirectoryChooser chooser = new DirectoryChooser();
		
		File dir = chooser.showDialog(null);
		
		if(dir != null) {
			sourceLabel.setText(dir.getPath());
			
			if(selectSourceDirEvent != null)
				selectSourceDirEvent.accept(dir);
		}
	}
	
	private void chooseDestination() {
		DirectoryChooser chooser = new DirectoryChooser();
		
		File dir = chooser.showDialog(null);

		if(dir != null) {
			destinationLabel.setText(dir.getPath());
			
			if(selectDestinationDirEvent != null)
				selectDestinationDirEvent.accept(dir);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		sourceLabel.setOnMouseClicked(e -> chooseSource());
		destinationLabel.setOnMouseClicked(e -> chooseDestination());
//		executeButton.setOnAction(e -> {
//			if (executeClick != null) executeClick.run();
//			});
		englishItem.setOnAction(e -> changeGUILang(Languages.ENGLISH));
		polishItem.setOnAction(e -> changeGUILang(Languages.POLISH));
	}

	private void changeGUILang(Languages language) {
		if(changeGUILanguage != null)
			changeGUILanguage.accept(language);
	}

	public void setFileList(List<Photo> list) {
		photosNumberLabel.setText(Integer.toString(list.size()));
		
		mainBox.getChildren().clear();		
		list.forEach(photo -> mainBox.getChildren().add(newPhotoItem(photo)));
	}

	private Node newPhotoItem(Photo photo) {
		MyFXMLLoader loader = new MyFXMLLoader();
		NodeAndController nac = loader.create("lensViewer/view/PhotoItem.fxml");
		
		PhotoItemController controller = (PhotoItemController) nac.getController();
		controller.set(photo);
		
		return nac.getNode();
	}

}
