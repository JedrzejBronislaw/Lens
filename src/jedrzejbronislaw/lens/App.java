package jedrzejbronislaw.lens;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class App {

	public static void main(String[] args) {
		System.out.println("Lens");

		String dirPath = "D:\\temp\\foto";

		List<Photo> x = listFiles(dirPath);
		printFileList(x);

		File dirRatio = new File(dirPath + "\\ratio");
		RatioChanger changer = new RatioChanger(1.5f);
		if (dirRatio.mkdir()) {

			for (int i = 0; i < x.size(); i++) {
				Photo photo = x.get(i);

				System.out.print((i + 1) + "/" + x.size() + " ");

				BufferedImage image, image2;
				try {
					image = ImageIO.read(new File(photo.getPath().toString()));
					image2 = changer.execute(image);
					saveImage(image2, dirRatio.getAbsolutePath() + "\\" + photo.getFileName());
					System.out.println(":)");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("image open error (" + photo.getFileName().toString() + ")");
				}

			}

		}
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
