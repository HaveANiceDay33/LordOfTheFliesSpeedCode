package com.samuel;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.painter.painter2d.HvlPainter2D;

public class Zone {
	
	public float x;
	public float y;
	public Zone(float x, float y){
		this.x = x;
		this.y = y;
	}
	public void display(){
		HvlPainter2D.hvlDrawQuad(this.x, this.y, 350, 350, Color.transparent);
	}

}
