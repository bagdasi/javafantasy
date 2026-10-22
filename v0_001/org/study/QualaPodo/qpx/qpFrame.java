package org.study.QualaPodo.qpx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.study.QualaPodo.qpx.qpEvent.*;
public class qpFrame extends JFrame {
	
	private Point locp = new Point();
	private Dimension sizep = new Dimension();
	private String title = "QPFrame";
	
	private qpxComponentAction action;
	
	public void setComponentAction(qpxComponentAction action) {
		this.action = action;
	}
	
	public qpFrame(String title) {
		super(title);
		this.title = title;
		
		// 아이콘
		ImageIcon icon = new ImageIcon("res/icon.png");
		setIconImage(icon.getImage());
		
		// 최소 크기
		setMinimumSize(new Dimension(200, 0));
		
		// 위치
		setLocation(0, 0);
		locp = new Point(0, 0);
		
		// 크기
		setSize(200, 0);
		sizep = new Dimension(200, 0);
		
		// 종료 옵션
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 이벤트
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				sizep = getSize();
				if (action != null)
				action.onResize(new qpxComponentEvent(qpFrame.this));
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				locp = getLocation();
				if (action != null)
				action.onRelocation(new qpxComponentEvent(qpFrame.this));
			}
			
			@Override
			public void componentShown(ComponentEvent e) {
				if (action != null)
				action.onShown(new qpxComponentEvent(qpFrame.this));
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				if (action != null)
				action.onHidden(new qpxComponentEvent(qpFrame.this));
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (action != null)
				action.onClosing(new qpxComponentEvent(qpFrame.this));
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				if (action != null)
				action.onClosed(new qpxComponentEvent(qpFrame.this));
			}
		});
		
	}
	public void setBackgroundColor(Color color) {
		super.getContentPane().setBackground(color);
	}
	public void setIcon(String path)
	{
		// 아이콘
		ImageIcon icon = new ImageIcon(path);
		super.setIconImage(icon.getImage());
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		locp = new Point(x, y);
		sizep = new Dimension(width, height);
	}
	

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		sizep = new Dimension(width, height);
	}
	
	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		locp = new Point(x, y);
	}
	
	@Override
	public void setLocation(Point p) {
		super.setLocation(p);
		locp = new Point(p);
	}
	
	public void setResizable(boolean bresize) {
		super.setResizable(bresize);
	}
	
	public void setView(boolean bview) {
		super.setVisible(bview);
	}
}
