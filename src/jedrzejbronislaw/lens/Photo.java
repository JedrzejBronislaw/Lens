package jedrzejbronislaw.lens;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Photo {

	@NonNull
	@Getter
	private Path path;

	private int width;
	private int height;
	private float ratio;

	private void imageRatio() {
		int h;
		int w;

		try {
			BufferedImage image = ImageIO.read(path.toFile());
			h = image.getHeight();
			w = image.getWidth();
		} catch (IOException e) {
			e.printStackTrace();
			w = h = 0;
		}
		width = w;
		height = h;
		ratio = (h > w) ? (float) h / w : (float) w / h;

	}

	@Override
	public String toString() {
		imageRatio();
		return path.getFileName().toString() + "\t" + width + " x " + height + "\t" + ratio;
	}

	public String getFileName() {
		return path.getFileName().toString();
	}
}
