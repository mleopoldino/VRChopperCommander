package tcc.game.engine;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private ArrayList<Image> imagens;
	private int currentImage;
	private int frameCounter; // FIX: Counter for sprite animation speed control

	//Construtor
	public SpriteSheet() {
		imagens = new ArrayList<Image>();
		currentImage = 0;
		frameCounter = 0;
	}

	//Getters e Setters
	public Image getCurrentImage(){
		// FIX: Defensive check for empty list
		if (imagens.isEmpty()) {
			GameLog.error("SpriteSheet has no images loaded!", null);
			return null;
		}
		return imagens.get(currentImage);
	}
	
	public int getImageNumber(){
		return currentImage;
	}
	
	public void setImageNumber(int imageNumber){
		this.currentImage = imageNumber;
	}
	
	//Metodos
	public void addImage(String file) {
		// FIX: Proper error handling for image loading
		try {
			File imageFile = new File(file);

			// Check if file exists before attempting to load
			if (!imageFile.exists()) {
				String errorMsg = "Image file not found: " + file;
				GameLog.error(errorMsg, null);
				throw new IOException(errorMsg);
			}

			// Load the image
			Image image = ImageIO.read(imageFile);

			// Check if ImageIO successfully loaded the image (could return null)
			if (image == null) {
				String errorMsg = "ImageIO failed to load image (returned null): " + file;
				GameLog.error(errorMsg, null);
				throw new IOException(errorMsg);
			}

			// Successfully loaded - add to list
			imagens.add(image);
			GameLog.debug("Successfully loaded sprite image: " + file);

		} catch (IOException e) {
			// Log the error with proper context
			GameLog.error("Failed to load critical sprite image: " + file, e);
			// Rethrow as RuntimeException to fail fast - game cannot run without sprites
			throw new RuntimeException("Failed to load critical sprite image: " + file, e);
		}
	}
	
	// FIX: Update sprite animation at controlled speed (not every frame)
	public void update(){
		if (imagens.size() <= 1) {
			return; // No animation needed for single image
		}

		frameCounter++;
		// Only update sprite every SPRITE_UPDATE_FRAMES frames (adjusted for 60 FPS)
		if (frameCounter >= GameConfig.SPRITE_UPDATE_FRAMES) {
			currentImage = (currentImage + 1) % imagens.size();
			frameCounter = 0;
		}
	}

}
