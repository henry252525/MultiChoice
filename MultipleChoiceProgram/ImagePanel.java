package MultipleChoiceProgram;

import java.awt.*;
import javax.swing.*;

public class ImagePanel extends JPanel {
	
	private Image image;

	public ImagePanel() { }

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int width = this.getWidth();
		int height = this.getHeight();

		if(this.image == null) {
			g.clearRect(0, 0, width, height);
			return;
		}

		int imageWidth = this.image.getWidth(null);
		int imageHeight = this.image.getHeight(null);

		double scale = Math.min(((double)width)/imageWidth, ((double)height)/imageHeight);
		imageWidth *= scale;
		imageHeight *= scale;

		g.drawImage(
				this.image, 
				0,
				0,
				imageWidth,
				imageHeight,
				null
			);
	}

	public void setImage(Image image) {
		this.image = image;
		repaint(); //TODO: I don't really like this. Think of better solution
	}
}