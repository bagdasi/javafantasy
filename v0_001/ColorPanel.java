import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

class ColorPanel extends JPanel implements MouseListener, MouseMotionListener 
{
	BufferedImage cimage;
	ColorPanel()
	{
		cimage = new BufferedImage(400,500,BufferedImage.TYPE_INT_ARGB);
		int rgb=0;
		Graphics cg = cimage.getGraphics();
		int rotx=0,roty=0;
		for(int b = 0; b<256 ; b+=4) 
		{
			for(int g =0 ; g <256 ; g+=4) 
			{
				for(int r=0; r<256; r+=4)
				{
					rgb = (0xff<<24)+(r<<16)+(g<<8)+b;
					cg.setColor(new Color(rgb,true));
					cg.fillRect(10+(4*rotx),10+(4*roty),14+(4*rotx),14+(4*roty));
					rotx++;
					if(rotx>95)
					{
						rotx = 0;
						roty++;
					}
				}
			}
		}
		cg.dispose();
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.drawImage(cimage,0,0,null);
	}
	@Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
			
		}
    }

    @Override
    public void mouseDragged(MouseEvent e) {
	
		
		
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       if (SwingUtilities.isLeftMouseButton(e)) {
        
			
       }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
		
		if (SwingUtilities.isLeftMouseButton(e)) {
			
		}
        
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
