package jedrzejbronislaw.lens.tools;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class MyFXMLLoader{

	@RequiredArgsConstructor
	public class NodeAndController{

		@NonNull
		@Getter
		private Node node;

		@NonNull
		@Getter
		private Initializable controller;
	}

//	private static final String langResourceLocation = "jedrzejbronislaw.lens.lang.Labels";
	private static final String mainDir = "/jedrzejbronislaw/lens/";
	
	
	public NodeAndController create(String fxmlFilePath) {
		FXMLLoader fxmlLoader = new FXMLLoader();

    	fxmlLoader.setLocation(getClass().getResource(mainDir + fxmlFilePath));
//    	fxmlLoader.setResources(ResourceBundle.getBundle(langResourceLocation));
   	
    	Node node;
		try {
			node = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    	
		
		return new NodeAndController(node, fxmlLoader.getController());
	}

}
