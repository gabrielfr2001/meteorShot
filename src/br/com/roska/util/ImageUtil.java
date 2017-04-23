package br.com.roska.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImageUtil {
	private Image image;

	public ImageUtil(Image image) {
		this.image = image;
	}

	public ImageUtil(BufferedImage image) {
		this.image = (Image) image;
	}

	public ImageUtil() {
	}

	public Image transform(String str, int width, int height, Color color) {

		this.image = loadImage(str);

		return transformNative(width, height, color);
	}

	public Image transform(URL url, int width, int height, Color color) {

		this.image = loadImage(url);

		return transformNative(width, height, color);
	}

	public Image transform(int width, int height, Color color) {
		return this.transformNative(width, height, color);
	}

	private Image transformNative(int width, int height, Color color) {

		this.image = resize(width, height);
		this.image = dye(color);

		return this.image;
	}

	public BufferedImage toBufferedImage() {

		if (this.image instanceof BufferedImage) {
			return (BufferedImage) this.image;
		}

		BufferedImage bimage = new BufferedImage(this.image.getWidth(null), this.image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(this.image, 0, 0, null);
		bGr.dispose();

		return bimage;
	}

	public Image resize(int newW, int newH) {
		return resizeNative(newW, newH);
	}

	public Image resize(String str, int newW, int newH) {

		this.image = loadImage(str);

		return resizeNative(newW, newH);
	}

	public Image resize(URL url, int newW, int newH) {

		this.image = loadImage(url);

		return resizeNative(newW, newH);
	}

	private Image resizeNative(int newW, int newH) {

		BufferedImage img = toBufferedImage();

		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		this.image = (Image) dimg;

		return this.image;
	}

	public Image loadImage(String url) {
		image = new ImageIcon(url).getImage();
		return image;
	}

	public Image loadImage(URL url) {
		try {
			image = new ImageIcon(url).getImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	public Image dye(Color color) {

		BufferedImage bImage = toBufferedImage();

		int w = bImage.getWidth();
		int h = bImage.getHeight();
		BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = dyed.createGraphics();
		g.drawImage(bImage, 0, 0, null);
		g.setComposite(AlphaComposite.SrcAtop);
		g.setColor(color);
		g.fillRect(0, 0, w, h);
		g.dispose();

		this.image = dyed;

		return this.image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
