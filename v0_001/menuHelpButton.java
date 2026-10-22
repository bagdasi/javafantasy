
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
public class menuHelpButton extends JButton implements ActionListener
{
	QPPv0_001 qpp;
	public menuHelpButton(String title)
	{
		super(title);
		addActionListener(this);
	}
	@Override
    public void actionPerformed(ActionEvent e) {
        // 저장 동작 구현
        try{
			
			System.out.println("도움말 버튼이 클릭되었습니다!");
			qpp = QPPv0_001.getInstance();
			qpp.rightEvent = 6;
			System.out.println(qpp.rightEvent);
			switch(qpp.rightEvent)
			{
			/*	case 0:
					break;
				case 1:
					qpp.mRightPanel.removeAll();
					qpp.mRightPanel.menuFile();
				
					qpp.mainFrame.setView();
					qpp.mainFrame.repaint();
					break;
				case 2:
					qpp.mRightPanel.removeAll();
					qpp.mRightPanel.menuEdit();
				
					qpp.mainFrame.setView();
					qpp.mainFrame.repaint();
					break;
				case 3:
					qpp.mRightPanel.removeAll();
					qpp.mRightPanel.menuDraw();
				
					qpp.mainFrame.setView();
					qpp.mainFrame.repaint();
					break;
				
				case 4:
					qpp.mRightPanel.removeAll();
					qpp.mRightPanel.menuOption();
					
					qpp.mainFrame.setView();
					qpp.mainFrame.repaint();
					break;
				
				case 5:
					qpp.mRightPanel.removeAll();
					qpp.mRightPanel.menuView();
				
					qpp.mainFrame.setView();
					qpp.mainFrame.repaint();
					break;
				*/
				case 6:
					qpp.mRightPanel.removeAll();
					qpp.mRightPanel.menuHelp();
				
					qpp.mainFrame.setView();
					qpp.mainFrame.repaint();
					break;
			
				default:
					break;
			}
		}catch(NullPointerException npe)
		{
			System.out.println("그리기버튼을 먼저 눌러주세요.NullPointerException입니다.");
		}
	}
}