package jedrzejbronislaw.lens;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jedrzejbronislaw.lens.lensViewer.Photo;
import jedrzejbronislaw.lens.lensViewer.controllers.MainWindowController;
import jedrzejbronislaw.lens.sideStripes.SideStripesTask;

public class App extends Application{

	private File source;
	private Stage theStage;
	
	public static void main(String[] args) {
    	launch(args);
	}
	
	@Override
	public void start(Stage theStage) throws Exception {
		this.theStage = theStage;
		buildScene(Languages.ENGLISH);
	}
	
	private void buildScene(Languages language) {
		Locale.setDefault(language.getLocale());
    	theStage.setTitle("Lens");
    	
    	Scene scene = new Scene(loadWindow(), 600, 400);
    	scene.getStylesheets().add("/jedrzejbronislaw/lens/style.css");
		theStage.setScene(scene);
    	theStage.show();
    	
    	theStage.setOnCloseRequest(h -> Platform.exit());
    	
    	
    }
	
	private Parent loadWindow() {
		FXMLLoader loader = new FXMLLoader();
		MainWindowController controller;
		Parent parent;
		
		loader.setLocation(getClass().getResource("/jedrzejbronislaw/lens/lensViewer/view/MainWindow.fxml"));
		loader.setResources(ResourceBundle.getBundle("jedrzejbronislaw.lens.lensViewer.lang.lang"));
		try {
			parent = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		controller = loader.getController();
		
		controller.setSelectSourceDirEvent(dir -> {
			List<Photo> list = listFiles(dir.getPath());
			source = dir;
			controller.setFileList(list);
		});
		
		controller.setExecuteClick(() -> execute());
		
		controller.setChangeGUILanguage(lang -> buildScene(lang));
		
		return parent;
	}
    
    private void execute() {
		if(source == null) return;

		String dirPath = source.getAbsolutePath();
    	
    	SideStripesTask stripes = new SideStripesTask(listFiles(dirPath));
    	stripes.setDestinationDir(new File(dirPath + "\\ratio"));
    	stripes.setSaveImage((image, path) -> saveImage(image, path));
    	stripes.execute();
	}

	private static boolean saveImage(BufferedImage newImage, String newPath) {
		File file = new File(newPath);

		try {
			file.getParentFile().mkdirs();
			ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(0.95f);

			ImageOutputStream ios = ImageIO.createImageOutputStream(file);
			writer.setOutput(ios);
			writer.write(null, new IIOImage(newImage, null, null), param);
			ios.close();
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	private static void printFileList(List<Photo> photos) {
		if (photos == null)
			return;

		System.out.println("Photos: " + photos.size());
		photos.forEach(path -> System.out.println(path.toString()));
	}

	private static List<Photo> listFiles(String string) {
		try (Stream<Path> walk = Files.walk(Paths.get(string))) {

			return walk.filter(Files::isRegularFile).map(f -> new Photo(f)).collect(Collectors.toList());

		} catch (SecurityException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
