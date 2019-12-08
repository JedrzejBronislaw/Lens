package jedrzejbronislaw.lens.sideStripes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SideStripes {

	private static final Color backgroundColor = Color.WHITE;
	@NonNull
	private float ratio;
	
	public BufferedImage execute(BufferedImage image) {
		BufferedImage newImage;
		int w, h;
		float proportion;
		
		w = image.getWidth();
		h = image.getHeight();
		proportion = (w > h) ? (float) w / h : (float) h / w;

		try {
			if (w > h)// horizontal image
				if (ratio > proportion)
					newImage = adaptWidth(image, ratio);
				else
					newImage = adaptHeight(image, ratio);
			else// vertical image
			if (ratio > proportion)
				newImage = adaptHeight(image, ratio);
			else
				newImage = adaptWidth(image, ratio);
		} catch (OutOfMemoryError e) {
			return null;
		}
		
		return newImage;
	}
	
	private BufferedImage adaptHeight(BufferedImage image, float newProportion) {
		int w, h, newH;
		BufferedImage newImage;
		int offsetH;
		// graph

		w = image.getWidth();
		h = image.getHeight();

		newH = (int) ((h >= w) ? (w * newProportion) : (w / newProportion));
		offsetH = (newH - image.getHeight()) / 2;

		newImage = new BufferedImage(w, newH, image.getType());
		newImage.getGraphics().setColor(backgroundColor);
		newImage.getGraphics().fillRect(0, 0, w, newH);
		((Graphics2D) newImage.getGraphics()).drawImage(image, 0, offsetH, null);

		return newImage;
	}

	private BufferedImage adaptWidth(BufferedImage image, float newProportion) {
		int h, w, newW;
		h = image.getHeight();
		BufferedImage newImage;
		int offsetW;

		h = image.getHeight();
		w = image.getWidth();
		newW = (int) ((w >= h) ? (h * newProportion) : (h / newProportion));
		offsetW = (newW - image.getWidth()) / 2;

		newImage = new BufferedImage(newW, h, image.getType());
		newImage.getGraphics().setColor(backgroundColor);
		newImage.getGraphics().fillRect(0, 0, newW, h);
		((Graphics2D) newImage.getGraphics()).drawImage(image, offsetW, 0, null);

		return newImage;
	}
}
