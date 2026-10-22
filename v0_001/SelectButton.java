import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class SelectButton extends JButton implements ActionListener 
{
	QPPv0_001 qpp;
	public SelectButton(String title)
	{
		super(title);
		addActionListener(this);
	}
	@Override
    public void actionPerformed(ActionEvent e) {
        // 저장 동작 구현
        System.out.println("선택 버튼이 클릭되었습니다!");
		qpp = QPPv0_001.getInstance();
		qpp.drawPanel.drawselect = 1;
		
	}
}
