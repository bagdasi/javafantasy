import javax.swing.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.nio.*;
import java.nio.channels.*;
import javax.swing.filechooser.FileNameExtensionFilter;
class qpFileOpenButton extends JButton implements ActionListener 
{
	QPPv0_001 qpp;
	public char[] tc;
	public int[] color;
	public qpFileOpenButton(String title)
	{
		super(title);
		addActionListener(this);
	}
	@Override
    public void actionPerformed(ActionEvent e) {
        // 저장 동작 구현
        System.out.println("qp파일열기 버튼이 클릭되었습니다!");
		qpp = QPPv0_001.getInstance();
		try
		{
			StringBuilder readTemp;
			BufferedReader br = null;
			int readC = 0;
			long qpfs = 0;
			String fchoose;
			int returnvalue;
			File selectedFile= null;
			JFileChooser filechooser = new JFileChooser();
			// 파일 필터 추가 (.qp 파일만 보이게 하기)
			FileNameExtensionFilter filter = new FileNameExtensionFilter("QP 파일", "qp");
			filechooser.setFileFilter(filter);
			// 초기 디렉토리 설정 (현재 디렉토리)
			filechooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			returnvalue = filechooser.showOpenDialog(qpp.mainFrame);
			if(returnvalue == JFileChooser.APPROVE_OPTION)
			{
					selectedFile = filechooser.getSelectedFile();
					System.out.println("선택한 파일 : " + selectedFile.getPath());
			}else
			{
				System.out.println("선택파일 취소됨");
			}
			if(selectedFile != null)
			{
				String filePath = selectedFile.getPath();
				if(!filePath.endsWith(".qp"))
				{
					filePath += ".qp";	
				}
				br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
				File file = new File(filePath);
				if(file.exists() && file.isFile())
				{
					qpfs = file.length();	
					//System.out.println("size : " + qpfs);
				}
				readTemp = new StringBuilder();
				while((readC = br.read())!=-1)
				{
					readTemp.append((char)readC);
					
				}
				br.close();
				tc = new char[readTemp.length()];
				tc = readTemp.toString().toCharArray();
			
				if(tc[0]==(char)4425 && tc[1] == (char)4989 && tc[2] == (char)6989 && tc[3] == (char)4548)
				{
					int start = (tc[5]<<8)+tc[4];
					color = new int[(tc.length-start)/2];
					int colorarray=0;
					for(int i=0;i< tc.length-start;i+=2)
					{
						color[colorarray] = (tc[start+i]<<8)+tc[start+i+1];
						colorarray++;
					}
					colorarray=0;
					
					qpp.dpwidth = tc[6];
					qpp.dpheight = tc[7];
					qpp.drawPanel.Init();
					qpp.image = qpp.drawPanel.DPanel(); 
					for(int i=0;i<tc[7];i++)
					{
						for (int j=0;j<tc[6] ;j++ )
						{
							qpp.drawPanel.setImage(j,i,color[colorarray]);
							colorarray++;
						}	
					}
					qpp.drawPanel.repaint();
				}
			}
		}
		catch (IOException ioe)
		{
		}
		
	}
}
