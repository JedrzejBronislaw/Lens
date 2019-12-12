package jedrzejbronislaw.lens.sideStripes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;

import javax.imageio.ImageIO;

import jedrzejbronislaw.lens.lensViewer.Photo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class SideStripesTask {

	@NonNull
	private List<Photo> photos;
	@Setter
	private File destinationDir;
	@Setter
	private BiConsumer<BufferedImage, String> saveImage;

	public void showOptionsWindow() {
		
	}

	public void execute() {
		exe(new SideStripesOptions());
	}
	
	public void execute(SideStripesOptions options) {
		exe(options);
	}

	private int exe(SideStripesOptions options) {
		if (destinationDir == null) return 0;
		if (saveImage == null) return 0;
		
		List<Photo> x = photos;
		
		File dirRatio = destinationDir;
		SideStripes stripes = new SideStripes(options.ratio);
		int successes = 0;
		if (dirRatio.mkdir()) {

			for (int i = 0; i < x.size(); i++) {
				Photo photo = x.get(i);

				System.out.print((i + 1) + "/" + x.size() + " ");

				BufferedImage image, image2;
				try {
					image = ImageIO.read(new File(photo.getPath().toString()));
					image2 = stripes.execute(image);
					saveImage.accept(image2, dirRatio.getAbsolutePath() + "\\" + photo.getFileName());
					System.out.println(":)");
					successes++;
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("image open error (" + photo.getFileName().toString() + ")");
				}

			}
		}
		
		return successes;
	}

}
