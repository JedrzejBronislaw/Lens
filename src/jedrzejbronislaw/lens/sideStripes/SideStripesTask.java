package jedrzejbronislaw.lens.sideStripes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import jedrzejbronislaw.lens.BasicOptions;
import jedrzejbronislaw.lens.Task;
import jedrzejbronislaw.lens.lensViewer.Photo;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class SideStripesTask implements Task {

	@Setter
	private BasicOptions basicOptions;
	
	@Override
	public void showOptionsWindow() {
		
	}

	@Override
	public void execute() {
		exe(new SideStripesOptions());
	}

//	@Override
	public void execute(SideStripesOptions options) {
		exe(options);
	}

	private int exe(SideStripesOptions options) {
		if (basicOptions == null) return 0;
		if (basicOptions.getDestinationDir() == null) return 0;
		if (basicOptions.getSaveImage() == null) return 0;
		
		List<Photo> x = basicOptions.getPhotos();
		
		File dirRatio = basicOptions.getDestinationDir();
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
					basicOptions.getSaveImage().accept(image2, dirRatio.getAbsolutePath() + "\\" + photo.getFileName());
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
