import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame 
{
	public MainFrame(int width,int height)
	{
		this.setTitle("콰라포도프로젝트v0.001");
		this.setSize(width,height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	public void setView()
	{
		this.setVisible(true);	
		this.repaint();
	}
	public void setMax()
	{
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

}
