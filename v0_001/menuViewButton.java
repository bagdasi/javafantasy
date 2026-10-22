import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuViewButton extends JButton implements ActionListener
{
	QPPv0_001 qpp;
	public menuViewButton(String title)
	{
		super(title);
		addActionListener(this);
	}
	@Override
    public void actionPerformed(ActionEvent e) {
        // 저장 동작 구현
        System.out.println("보기 버튼이 클릭되었습니다!");
		qpp.rightEvent = 5;
		switch(qpp.rightEvent)
		{
			case 0:
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
				
			case 6:
				qpp.mRightPanel.removeAll();
				qpp.mRightPanel.menuHelp();
				
				qpp.mainFrame.setView();
				qpp.mainFrame.repaint();
				break;
			
			default:
				break;
		}
	}
}