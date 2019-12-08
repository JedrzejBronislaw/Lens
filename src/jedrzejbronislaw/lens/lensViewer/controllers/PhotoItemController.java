package jedrzejbronislaw.lens.lensViewer.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jedrzejbronislaw.lens.lensViewer.Photo;

public class PhotoItemController implements Initializable {

	private Photo photo;
	@FXML
	private Label nameLabel = new Label();
	@FXML
	private ImageView view = new ImageView();

	public void set(Photo photo) {
		this.photo = photo;
	
		update();
	}
	

	private void update() {
		InputStream iStream = null;
		nameLabel.setText(photo.getFileName());
		
		try {
			iStream = new FileInputStream(photo.getPath().toFile());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(iStream != null) {
			Image image = new Image(iStream, 100, 100, true, true);
			view.setImage(image);
		}
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

}
