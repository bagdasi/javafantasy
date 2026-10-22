import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;


class PenButton extends JButton implements ActionListener 
{
	QPPv0_001 qpp;
	public PenButton(String title)
	{
		super(title);
		addActionListener(this);
	}
	@Override
    public void actionPerformed(ActionEvent e) {
        // 저장 동작 구현
        System.out.println("펜 버튼이 클릭되었습니다!");
		try{
			
			qpp = QPPv0_001.getInstance();
			qpp.drawPanel.drawselect = 0;
			JFrame pFrame = new JFrame();
			pFrame.setTitle("펜");
			pFrame.setSize(400,200);
			pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			// Group 1
        JRadioButton stroke1 = new JRadioButton("굵기1");
        JRadioButton stroke2 = new JRadioButton("굵기2");
        JRadioButton stroke3 = new JRadioButton("굵기3");
        JRadioButton stroke4 = new JRadioButton("굵기4");
        JRadioButton stroke5 = new JRadioButton("굵기5");
        ButtonGroup group1 = new ButtonGroup();
        group1.add(stroke1);
        group1.add(stroke2);
        group1.add(stroke3);
        group1.add(stroke4);
        group1.add(stroke5);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(stroke1);
        panel1.add(stroke2);
        panel1.add(stroke3);
        panel1.add(stroke4);
        panel1.add(stroke5);
		
		stroke1.setSelected(true);
        // Group 2
        JRadioButton pen1 = new JRadioButton("펜");
        JRadioButton pen2 = new JRadioButton("직선");
        JRadioButton pen3 = new JRadioButton("원");
        ButtonGroup group2 = new ButtonGroup();
        group2.add(pen1);
        group2.add(pen2);
        group2.add(pen3);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        panel2.add(pen1);
        panel2.add(pen2);
        panel2.add(pen3);
		
		pen1.setSelected(true);
        // Confirm and Close buttons
        JButton confirmButton = new JButton("확인");
        JButton closeButton = new JButton("닫기");

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				if (stroke1.isSelected()) {
					System.out.println("굵기1 선택됨");
					qpp.drawPanel.penst = 1;
				 } else if (stroke2.isSelected()) {
					 System.out.println("굵기2 선택됨");
					qpp.drawPanel.penst = 2;
				 } else if (stroke3.isSelected()) {
					System.out.println("굵기3 선택됨");
					qpp.drawPanel.penst = 3;
				 } else if (stroke4.isSelected()) {
					 System.out.println("굵기4 선택됨");
					qpp.drawPanel.penst = 4;
				} else if (stroke5.isSelected()) {
					System.out.println("굵기5 선택됨");
					qpp.drawPanel.penst = 5;
				}
				
				if (pen1.isSelected())
				{
					qpp.drawPanel.pensh = 1;
				}else if (pen2.isSelected())
				{
					qpp.drawPanel.pensh = 2;
				}else if (pen3.isSelected())
				{
					qpp.drawPanel.pensh = 3;
				}
				
				pFrame.dispose();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pFrame.dispose();
            }
        });

        JPanel panel3 = new JPanel();
        panel3.add(confirmButton);
        panel3.add(closeButton);

        pFrame.setLayout(new BoxLayout(pFrame.getContentPane(), BoxLayout.Y_AXIS));
        pFrame.add(panel1);
        pFrame.add(panel2);
        pFrame.add(panel3);

        pFrame.setLocation(400, 400);
        pFrame.setVisible(true);
		}catch(NullPointerException npe)
		{
			
		}
	}
}