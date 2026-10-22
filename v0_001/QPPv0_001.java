
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
public class QPPv0_001 
{
	public static QPPv0_001 qpp= null;
	JPanel menuPanel,workPanel;
	public static MainFrame mainFrame;
	menuFileButton mFileButton;
	menuEditButton mEditButton;
	menuDrawButton mDrawButton;
	menuOptionButton mOptionButton;
	menuViewButton mViewButton;
	menuHelpButton mHelpButton;
	public static int rightEvent = 0;
	public static menuRightPanel mRightPanel;
	public DrawingPanel drawPanel;
	public static int dpwidth = 500, dpheight = 400;
	BufferedImage image;
	
	QPPv0_001()
	{
		
	}
	public static QPPv0_001 getInstance()
	{
		if(qpp == null){
			qpp = new QPPv0_001();
		}
		return qpp;
	}
	void Init()
	{
		mainFrame = new MainFrame(800,600);
		
		mFileButton = new menuFileButton("파일");
		mEditButton = new menuEditButton("편집");
		mDrawButton = new menuDrawButton("그리기");
		mOptionButton = new menuOptionButton("옵션");
		mViewButton = new menuViewButton("보기");
		mHelpButton = new menuHelpButton("도움말");
		menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		menuPanel.setBackground(Color.BLUE);
		menuPanel.add(mFileButton);
		menuPanel.add(mEditButton);
		menuPanel.add(mDrawButton);
		menuPanel.add(mOptionButton);
		menuPanel.add(mViewButton);
		menuPanel.add(mHelpButton);
		mainFrame.add(menuPanel,BorderLayout.NORTH);
		mRightPanel = new menuRightPanel(120,600);
		mainFrame.add(mRightPanel,BorderLayout.EAST);
		workPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		workPanel.setPreferredSize(new Dimension(1280,1024));
		workPanel.setBackground(Color.GRAY);
		drawPanel = new DrawingPanel();
		image = drawPanel.DPanel();
		workPanel.add(drawPanel,BorderLayout.WEST);
		mainFrame.add(workPanel,BorderLayout.WEST);
		mainFrame.setMax();
		mainFrame.setView();
		rightEvent = 0;
	}
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run()
			{
				qpp = new QPPv0_001();
				qpp.Init();
				
			}
		});
	}
}
