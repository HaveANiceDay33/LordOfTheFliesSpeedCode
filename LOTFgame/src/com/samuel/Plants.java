package com.samuel;

import com.osreboot.ridhvl.painter.painter2d.HvlPainter2D;

public class Plants {
	
	public float x;
	public float y;
	public Plants(float x,float y){
		this.x = x;
		this.y = y;
	}
	public void display(){
		HvlPainter2D.hvlDrawQuad(x, y, 75, 75, MainLOTF.getTexture(0));
	}
}
