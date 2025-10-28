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
	
	//Construtor
	public SpriteSheet() {
		imagens = new ArrayList<Image>();
		currentImage = 0;
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
	
	public void update(){
		currentImage = (currentImage + 1)%imagens.size();
	}

}
