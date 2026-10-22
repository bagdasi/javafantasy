import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class PasteButton extends JButton implements ActionListener 
{
	QPPv0_001 qpp;
	public PasteButton(String title)
	{
		super(title);
		addActionListener(this);
	}
	@Override
    public void actionPerformed(ActionEvent e) {
        // 저장 동작 구현
        System.out.println("붙여놓기 버튼이 클릭되었습니다!");
		qpp = QPPv0_001.getInstance();
		qpp.drawPanel.drawselect = 5;
		
	}
}