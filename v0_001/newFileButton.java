
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
class newFileButton extends JButton implements ActionListener 
{
	public static QPPv0_001 qpp;
	BufferedImage image;
	public newFileButton(String title)
	{
		super(title);
		addActionListener(this);
	}
	@Override
    public void actionPerformed(ActionEvent e) {
        // 저장 동작 구현
        System.out.println("새로만들기 버튼이 클릭되었습니다!");
		try{
			qpp = QPPv0_001.getInstance();
			JFrame nfFrame = new JFrame();
			nfFrame.setTitle("새로만들기");
			nfFrame.setSize(300,200);
			nfFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			nfFrame.setLayout(null);
			JLabel widthLabel = new JLabel("너비 : ");
			widthLabel.setBounds(20,20,50,25);
			nfFrame.add(widthLabel);
			JTextField widthText = new JTextField();
			widthText.setBounds(60,20,60,25);
			nfFrame.add(widthText);
			JLabel heightLabel = new JLabel("높이 : ");
			heightLabel.setBounds(130,20,160,25);
			nfFrame.add(heightLabel);
			JTextField heightText = new JTextField();
			heightText.setBounds(170,20,60,25);
			nfFrame.add(heightText);
		
			JButton okButton = new JButton("확인");
			okButton.setBounds(40,60,60,30);
			nfFrame.add(okButton);
			JButton closeButton = new JButton("닫기");
			closeButton.setBounds(120,60,60,30);
			nfFrame.add(closeButton);
			okButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e)
				{
					try{
						String Sw,Sh;
						Sw = widthText.getText();
						Sh = heightText.getText();
						qpp.dpwidth = Integer.parseInt(Sw);
						qpp.dpheight = Integer.parseInt(Sh);
						qpp.mainFrame.dispose();
						qpp.Init();
						nfFrame.dispose();
					}catch(NumberFormatException nfe)
					{
						System.out.println("숫자를 눌려주세요");
					}
				}
			});
			closeButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e)
				{
					
				
					nfFrame.dispose();
				}
			});
			nfFrame.setLocation(400,400);
		
			nfFrame.setVisible(true);
		}catch(NumberFormatException nfe)
		{
			
		}
		//qpp.mainFrame.setView();
	}
}
