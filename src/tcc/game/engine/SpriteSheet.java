package tcc.game.engine;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

@SuppressWarnings("unused")
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
		try {
			imagens.add(ImageIO.read(new File(file)));
		} catch (Exception ex) {
			ex.printStackTrace();
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
