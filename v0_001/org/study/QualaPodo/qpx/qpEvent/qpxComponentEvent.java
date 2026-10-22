package org.study.QualaPodo.qpx.qpEvent;

import java.awt.Component;

public class qpxComponentEvent {
	private final Component component;
	private final int x, y, width, height;
	
	public qpxComponentEvent(Component c) {
		this.component = c;
		this.x = c.getX();
		this.y = c.getY();
		this.width = c.getWidth();
		this.height = c.getHeight();
	}
	
	public Component getComponent() { return component; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
}
