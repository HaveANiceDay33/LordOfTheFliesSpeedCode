package com.samuel;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlFontUtil;
import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.painter.painter2d.HvlPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class MainLOTF extends HvlTemplateInteg2D {
	public static void main(String [] args){
		new MainLOTF();
	}
	public MainLOTF(){
		super(60, 1920, 1080, "LOTF game","blueBallIcon", new HvlDisplayModeDefault());
	}
	
	HvlMenu menu;
	HvlMenu move;
	HvlMenu end;
	float width;
	
	boolean hasPlayed; 
	boolean hasPlayedScare;
	float height;
	int counter;
	int counterZone;
	int soundCounter;
	int counterText;
	float xPos;
	float keySpeed;
	float yPos;
	Color sky;
	ArrayList<Plants> plants;
	ArrayList<Zone> zones;
	HvlFontPainter2D gameFont;
	HvlFontPainter2D endFont;
	
	public void initialize(){
		getSoundLoader().loadResource("creepyMus");//0
		getSoundLoader().loadResource("jumpscare"); //1
		
		getTextureLoader().loadResource("plantLOTF");//plants 0
		getTextureLoader().loadResource("grassbckgrd"); //background grass 1
		getTextureLoader().loadResource("LOTFvillan"); //LOTF 2
		getTextureLoader().loadResource("Simon"); // Simon 3
		getTextureLoader().loadResource("Font"); //4
		getTextureLoader().loadResource("osFont"); //5
		getTextureLoader().loadResource("speechBubble"); //6
		getTextureLoader().loadResource("fainted");//7
		gameFont =  new HvlFontPainter2D(getTexture(5), HvlFontPainter2D.Preset.FP_INOFFICIAL,.5f,8f,0);
		endFont = new HvlFontPainter2D(getTexture(4), HvlFontUtil.DEFAULT,192, 256,0.125f,10);
		counter = 0;
		counterZone = 0;
		counterText = 0;
		soundCounter = 0;
		yPos = 1000f;
		hasPlayed = false;
		hasPlayedScare = false;
		xPos = 960f;
		keySpeed = 100;
		sky = new Color(0, 127, 255);
		width = Display.getWidth();
		height = Display.getHeight();
		plants = new ArrayList<Plants>();
		zones = new ArrayList<Zone>();
		menu = new HvlMenu(){
			public void draw(float delta){
				HvlPainter2D.hvlDrawQuad(0, 0,1920,1080, getTexture(1));
				if(!hasPlayed){
					getSound(0).playAsSoundEffect(1, .05f, false);
					hasPlayed = true;
				}
				if(counter < 150){
					Plants plant = new Plants(HvlMath.randomFloatBetween(20, 1900),HvlMath.randomFloatBetween(20 ,  1060));
					plants.add(plant);
				}
				for(Plants allPlants : plants){
					allPlants.display();
				}
				counter ++;
				gameFont.drawWordc("Find The Lord of the Flies", width/2, height/2 - 400, Color.green, 1.515f);
				gameFont.drawWordc("Find The Lord of the Flies", width/2, height/2 - 400, Color.black, 1.5f);
				gameFont.drawWordc("Press Space to begin", width/2, height/2 +400, Color.green, 1.02f);
				gameFont.drawWordc("Press Space to begin", width/2, height/2 + 400, Color.black, 1f);
				if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
					HvlMenu.setCurrent(move);
					
					counter = 151;
				}
				super.draw(delta);
			}
		};
		move = new HvlMenu(){
			public void draw(float delta){
				counterZone++;
				HvlPainter2D.hvlDrawQuad(0, 0,1920,1080, getTexture(1));
				if(counterZone <= 1){
					Zone zone = new Zone(HvlMath.randomFloatBetween(0, 1550),HvlMath.randomFloatBetween (20 ,  500));
					zones.add(zone);
				}
				for(Zone finalZone : zones){
					finalZone.display();
					if(finalZone.x >= xPos && finalZone.x <= (xPos + 100) 
							|| (finalZone.x + 350) >= xPos && (finalZone.x+350) <= (xPos + 100) 
							|| finalZone.x <= xPos && (finalZone.x + 350) > (xPos + 100)){
						if((finalZone.y + 350) >= yPos && (finalZone.y+350) <= (yPos + 100)
								|| finalZone.y >= yPos && finalZone.y <= (yPos + 100)
								|| finalZone.y <= yPos && (finalZone.y + 350) >= (yPos + 100)){
							if(!hasPlayedScare){
								getSound(1).playAsSoundEffect(1, .25f, false);
								hasPlayedScare = true;
							}
							
							soundCounter++;
							if(soundCounter > 40){
								HvlMenu.setCurrent(end);
							}
							
							
						}
						
					}
				}
			
				if(counter < 150){
					Plants plant = new Plants(HvlMath.randomFloatBetween(20, 1900),HvlMath.randomFloatBetween(20 ,  1060));
					plants.add(plant);
				}
				for(Plants allPlants : plants){
					allPlants.display();
				}
				HvlPainter2D.hvlDrawQuad(xPos, yPos,100,100, getTexture(3));
				if(Keyboard.isKeyDown(Keyboard.KEY_D)){
					xPos += keySpeed * delta;	
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_A)){
					xPos -= keySpeed * delta;	
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_W)){
					yPos -= keySpeed * delta;	
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_S)){
					yPos +=keySpeed * delta;	
				}
				if(xPos <= 0){
					xPos = 0;
				}
				if( xPos + 100 >= width){
					xPos = width - 100;
				}
				if(yPos <= 0){
					yPos = 0;
				}
				if(yPos + 100 >= height){
					yPos = height -100;
				}
				super.draw(delta);
			}
		};
		end = new HvlMenu(){
			public void draw(float delta){
				counterText++;
				HvlPainter2D.hvlDrawQuad(0, 0, 1920, 900, sky);
				HvlPainter2D.hvlDrawQuad(0, 900, 1920, 200, getTexture(1));
				HvlPainter2D.hvlDrawQuad(1500, 320, 300, 600, getTexture(2));
				HvlPainter2D.hvlDrawQuad(200, 600, 900, 300, getTexture(7));
				if(counterText > 25){
					HvlPainter2D.hvlDrawQuad(30, -20, 1900, 550, getTexture(6));
					gameFont.drawWordc("You are a silly little boy, just an ignorant, silly little boy.", width/2, 40, Color.black, .4f); 
					gameFont.drawWordc("Do not you agree? Are not you just a silly little boy?", width/2, 80, Color.black,.40f); 
					gameFont.drawWordc("Well then, you would better run off and play with the others. They think you are batty.", width/2, 120, Color.black,.4f); 
					gameFont.drawWordc("You do not want Ralph to think you are batty, do you? You like Ralph a lot, do not you?", width/2, 160, Color.black,.4f); 
					gameFont.drawWordc("And Piggy, and Jack? What are you doing out here all alone? Are not you afraid of me?", width/2, 200, Color.black,.4f); 
					gameFont.drawWordc("There is not anyone to help you. Only me. And I am the Beast. A Pig head on a stick.", width/2, 240, Color.black,.4f);   
					gameFont.drawWordc("Fancy thinking the Beast was something you could hunt and kill! You knew, did not you?", width/2, 280, Color.black,.4f);  
					gameFont.drawWordc("I am part of you? Close, close, close! I am the reason why it is not good? Why things are what they are?", width/2, 320, Color.black,.4f);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_P)){
					HvlMenu.setCurrent(menu);      
					hasPlayed = false;
					hasPlayedScare = false;
					counter = 0;
					counterZone = 0;
					soundCounter = 0;
					zones.clear();
					plants.clear();
					xPos = 960f;
					yPos = 1000f;
				}
				super.draw(delta);
			}
		};
		HvlMenu.setCurrent(menu);
	}
	public void update(float delta){
		HvlMenu.updateMenus(delta);
	}
}
