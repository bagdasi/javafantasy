import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class EraseButton extends JButton implements ActionListener 
{
	QPPv0_001 qpp;
	public EraseButton(String title)
	{
		super(title);
		addActionListener(this);
	}
	@Override
    public void actionPerformed(ActionEvent e) {
        // 저장 동작 구현
        System.out.println("지우개 버튼이 클릭되었습니다!");
		
		
	}
}