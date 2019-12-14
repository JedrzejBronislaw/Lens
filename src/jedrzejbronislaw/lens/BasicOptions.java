package jedrzejbronislaw.lens;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.function.BiConsumer;

import jedrzejbronislaw.lens.lensViewer.Photo;
import lombok.Builder;
import lombok.Getter;

@Builder
public class BasicOptions {

	@Getter
	private List<Photo> photos;
	@Getter
	private File destinationDir;
	@Getter
	private BiConsumer<BufferedImage, String> saveImage;
}
