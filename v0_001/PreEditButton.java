import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class PreEditButton extends JButton implements ActionListener 
{
	QPPv0_001 qpp;
	public PreEditButton(String title)
	{
		super(title);
		addActionListener(this);
	}
	@Override
    public void actionPerformed(ActionEvent e) {
        // 저장 동작 구현
        System.out.println("이전 버튼이 클릭되었습니다!");
		qpp = QPPv0_001.getInstance();
		qpp.drawPanel.drawselect = 6;
		System.out.println("drawselect : " + qpp.drawPanel.drawselect);
		
		
	}
}