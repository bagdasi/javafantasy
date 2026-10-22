import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class CutButton extends JButton implements ActionListener 
{
	QPPv0_001 qpp;
	public CutButton(String title)
	{
		super(title);
		addActionListener(this);
	}
	@Override
    public void actionPerformed(ActionEvent e) {
        // 저장 동작 구현
        System.out.println("잘라내기 버튼이 클릭되었습니다!");
		qpp = QPPv0_001.getInstance();
		qpp.drawPanel.cutbool = true;
		qpp.drawPanel.drawselect = 3;
		qpp.drawPanel.repaint();
		
		
	}
}