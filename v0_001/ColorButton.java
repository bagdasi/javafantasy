import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

class ColorButton extends JButton implements ActionListener 
{
	QPPv0_001 qpp;
	public ColorButton(String title)
	{
		super(title);
		addActionListener(this);
	}
	@Override
    public void actionPerformed(ActionEvent e) {
        // 저장 동작 구현
        System.out.println("색상 버튼이 클릭되었습니다!");
		try
		{
			qpp = QPPv0_001.getInstance();
			JFrame cFrame = new JFrame();
			JPanel cPanel = new ColorPanel();
			cFrame.setTitle("색선택");
			cFrame.setSize(500,700);
			cPanel.setSize(500,600);
			cFrame.setLayout(new BorderLayout());
			cFrame.add(cPanel,BorderLayout.CENTER);
			cFrame.setLocation(400,400);
			
			cFrame.setVisible(true);
			cFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		catch (NullPointerException npe)
		{
		}
		
		
	}
}