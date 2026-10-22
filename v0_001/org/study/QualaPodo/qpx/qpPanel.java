package org.study.QualaPodo.qpx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class qpPanel extends JPanel;
{
	public qpPanel(int width, int height)
	{
		setPreferredSize(new Dimension(width, height));
        	setSize(width, height);
        	setLayout(null); // 필요하면 레이아웃 비활성화
	}
}