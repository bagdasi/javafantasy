import java.awt.image.BufferedImage;
import javax.swing.JLabel;

public class qpgList 
{
	BufferedImage qpgimage;
	String qpgstr;
	JLabel qpglabel;
	public String qpgstrSet()
	{
		return qpgstr;
	}
	public BufferedImage qpgimageSet()
	{
		return qpgimage;
	}
	public void qpgstrGet(String str)
	{
		qpgstr = str;
	}
	public void qpgimageGet(BufferedImage image)
	{
		qpgimage = image;
	}
	public JLabel qpglabelSet()
	{
		return qpglabel;
	}
	public void qpglabelGet(JLabel label)
	{
		qpglabel = label;
	}
}
