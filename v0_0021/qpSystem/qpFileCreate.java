package qpSystem;

import java.nio.*;
import java.nio.channels.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class qpFileCreate
{
	public qpFileCreate()
	{
		
	}
	public qpFileCreate(File sfile,BufferedImage simage,Component parent)
	{
		qpSaveFast(sfile,simage,parent);
	}
	public qpFileCreate(BufferedImage simage,Component parent)
	{
		qpSaveFile(simage,parent);
	}
	
	public void qpSaveFast(File sfile,BufferedImage simage,Component parent)
	{
		StringBuilder writeTemp;
		String tmp;
		BufferedImage image;
		try{
			int testRGB = 0;
			int paste =0; //임시저장공간
			int[] nd = new int[4];//nd는 not dead 아직 죽지 않았다.
			char[] savetemp = new char[2];
			image = simage;
			writeTemp = new StringBuilder();
			writeTemp.append((char)4425);//처음 파일인것을 인식
			writeTemp.append((char)4989);//처음 파일인것을 인식
			writeTemp.append((char)6989);//처음 파일인것을 인식
			writeTemp.append((char)4548);//처음 파일인것을 인식
			writeTemp.append((char)17);//시작지점1
			writeTemp.append((char)0);//시작지점2(<<16)
			writeTemp.append((char)image.getWidth());//가로크기
			writeTemp.append((char)image.getHeight());//세로크기
			writeTemp.append((char)0);//이미지좌표x
			writeTemp.append((char)0);//이미지좌표y
			writeTemp.append((char)0);//imfor1
			writeTemp.append((char)0);//imfor2
			writeTemp.append((char)0);//imfor3
			writeTemp.append((char)0);//imfor4
			writeTemp.append((char)0);//imfor5
			writeTemp.append((char)0);//imfor6
			writeTemp.append((char)0);//imfor7
			for(int i =0; i<image.getHeight(); i++)
			{
				for(int j=0; j<image.getWidth(); j++)
				{
					testRGB = image.getRGB(j,i);
					if(testRGB == -0x00000000) testRGB = 0xffffffff;
					if(testRGB<0x00000000) testRGB = Math.abs(testRGB);
					nd[0] = (int)((testRGB & 0xff000000));
					nd[1] = (int)((testRGB & 0x00ff0000));
					nd[2] = (int)((testRGB & 0x0000ff00));
					nd[3] = (int)(testRGB & 0x000000ff);
					savetemp[0] = (char)((nd[0]+nd[1])>>8);
					savetemp[1] = (char)(nd[2]+nd[3]);
					writeTemp.append((char)savetemp[0]);
					writeTemp.append((char)savetemp[1]);
				}
			}
					
			tmp = "";
			tmp = writeTemp.toString();
			String fchoose;
			int returnvalue;
			File selectedFile= sfile;
			
			if(selectedFile != null)
			{
				String filePath = selectedFile.getPath();
				
				if(!filePath.endsWith(".qp"))
				{
					filePath += ".qp";	
				}
				File fw = new File(filePath);
				if(fw.exists()){
					FileOutputStream fos = new FileOutputStream(fw);
					fos.write(new byte[0]);
					fos.close();
				}
			
				RandomAccessFile raf = new RandomAccessFile(fw,"rw");
				FileChannel channel = raf.getChannel();
				MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE,0,tmp.getBytes("utf-8").length);
				buffer.clear();
				byte[] data = tmp.getBytes("UTF-8");
				if(buffer.remaining()>=data.length){
				
					buffer.put(data);
				}else
				{
					System.out.println("버퍼에 충분한 공간이 없습니다.");
				}
				channel.close();
			
				raf.close();
		
						
			}else
			{
				System.out.println("선택한 파일이 없습니다.");
			}
			
		}catch(IOException ioe)
		{
			
		}
	}
	public void qpSaveFile(BufferedImage simage,Component parent)
	{
		StringBuilder writeTemp;
		String tmp;
		BufferedImage image;
		try{
			int testRGB = 0;
			int paste =0; //임시저장공간
			int[] nd = new int[4];//nd는 not dead 아직 죽지 않았다.
			char[] savetemp = new char[2];
			image = simage;
			writeTemp = new StringBuilder();
			writeTemp.append((char)4425);//처음 파일인것을 인식
			writeTemp.append((char)4989);//처음 파일인것을 인식
			writeTemp.append((char)6989);//처음 파일인것을 인식
			writeTemp.append((char)4548);//처음 파일인것을 인식
			writeTemp.append((char)17);//시작지점1
			writeTemp.append((char)0);//시작지점2(<<16)
			writeTemp.append((char)image.getWidth());//가로크기
			writeTemp.append((char)image.getHeight());//세로크기
			writeTemp.append((char)0);//이미지좌표x
			writeTemp.append((char)0);//이미지좌표y
			writeTemp.append((char)0);//imfor1
			writeTemp.append((char)0);//imfor2
			writeTemp.append((char)0);//imfor3
			writeTemp.append((char)0);//imfor4
			writeTemp.append((char)0);//imfor5
			writeTemp.append((char)0);//imfor6
			writeTemp.append((char)0);//imfor7
			for(int i =0; i<image.getHeight(); i++)
			{
				for(int j=0; j<image.getWidth(); j++)
				{
					testRGB = image.getRGB(j,i);
					if(testRGB == -0x00000000) testRGB = 0xffffffff;
					if(testRGB<0x00000000) testRGB = testRGB;
					nd[0] = (int)((testRGB & 0xff000000));
					nd[1] = (int)((testRGB & 0x00ff0000));
					nd[2] = (int)((testRGB & 0x0000ff00));
					nd[3] = (int)(testRGB & 0x000000ff);
					savetemp[0] = (char)((nd[0]+nd[1])>>8);
					savetemp[1] = (char)(nd[2]+nd[3]);
					writeTemp.append((char)savetemp[0]);
					writeTemp.append((char)savetemp[1]);
				}
			}
					
			tmp = "";
			tmp = writeTemp.toString();
			String fchoose;
			int returnvalue;
			File selectedFile= null;
			JFileChooser filechooser = new JFileChooser();
			// 파일 필터 추가 (.qp 파일만 보이게 하기)
			FileNameExtensionFilter filter = new FileNameExtensionFilter("QP 파일", "qp");
			filechooser.setFileFilter(filter);
			// 초기 디렉토리 설정 (현재 디렉토리)
			filechooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			returnvalue = filechooser.showSaveDialog(parent);
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
				File fw = new File(filePath);
				if(fw.exists()){
					FileOutputStream fos = new FileOutputStream(fw);
					fos.write(new byte[0]);
					fos.close();
				}
			
				RandomAccessFile raf = new RandomAccessFile(fw,"rw");
				FileChannel channel = raf.getChannel();
				MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE,0,tmp.getBytes("utf-8").length);
				buffer.clear();
				byte[] data = tmp.getBytes("UTF-8");
				if(buffer.remaining()>=data.length){
				
					buffer.put(data);
				}else
				{
					System.out.println("버퍼에 충분한 공간이 없습니다.");
				}
				channel.close();
			
				raf.close();
		
						
			}else
			{
				System.out.println("선택한 파일이 없습니다.");
			}
			
		}catch(IOException ioe)
		{
			
		}
	}

}
