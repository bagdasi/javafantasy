package home.my.gumhuan.qpImageSystem.imageProcessing;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.io.File;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;

public class QPDFileSys
{
	private String[] qpstr;
	
	private Point qpdp;
	
	private static BufferedImage[] qpimage;
	
	public QPDFileSys()
	{
		
	}
	public QPDFileSys(String path)
	{
		setQPDFile(path);
	}
	
	public void setQPDFile(String path)
	{
		char[] tc;
		int color;
		int getpoints =0,getpointe  =0;
		try
		{
			File file = new File(path);
			StringBuilder readTemp;
			BufferedReader br = null;
			int readC = 0;
			long qpfs = 0;
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()),"utf-8"));
			//File file = new File(file.getAbsolutePath());
			
			if(file.exists() && file.isFile())
			{
				qpfs = file.length();	
							
							//System.out.println("size : " + qpfs);
			}else
			{
				System.out.println("파일이 존재하지 않습니다.");
				return;	
			}
			readTemp = new StringBuilder();
			while((readC = br.read())!=-1)
			{
				readTemp.append((char)readC);
					
			}
			br.close();
						
			tc = new char[readTemp.length()];
			tc = readTemp.toString().toCharArray();
			
			if(tc[0]==(char)21242 && tc[1] == (char)25442 && tc[2] == (char)26785 && tc[3] == (char)27888)
			{
				qpstr = new String[tc[4]*tc[5]];
				qpdp = new Point();
				qpdp.x = tc[4];
				qpdp.y = tc[5];
				getpoints = 0;
				getpointe = 6+qpstr.length*2;
				
				System.out.println("strlength : " + qpstr.length);
				for(int i=0;i<qpstr.length;i++)
				{
					if (getpoints < 0 || getpointe < 0 || getpointe > readTemp.length() || getpoints > getpointe) {
						System.out.println("잘못된 인덱스 범위: getpoints = " + getpoints + ", getpointe = " + getpointe);
						break; // 루프를 중단하거나 예외를 처리
					}
					
					getpoints=getpointe;
					getpointe += 17+((int)tc[6+(i*2)]*(int)tc[7+(i*2)]*2);
					
					qpstr[i] = readTemp.substring(getpoints,getpointe);
					//System.out.println("i : " + i + ":getpoints " + getpoints + ":getpointe " + getpointe + " ");
				}
				//System.out.println("--------------------a--------------");
				
				//여기서부터 mapx mapy설정
				qpimage = new BufferedImage[tc[4]*tc[5]];
				for(int i = 0; i<qpstr.length;i++)
				{
					int inc = 0;
					qpimage[i] = new BufferedImage((int)qpstr[i].charAt(6),(int)qpstr[i].charAt(7),BufferedImage.TYPE_INT_ARGB);
					Graphics2D tmpg = qpimage[i].createGraphics();
					for(int j=0;j<(int)qpstr[i].charAt(7);j++)
					{
						for(int k=0;k<(int)qpstr[i].charAt(6);k++)
						{
							color = (int)(qpstr[i].charAt(17+inc)<<8)+qpstr[i].charAt(18+inc);
							tmpg.setColor(new Color(color));
							tmpg.fillRect((int)k,(int)j,1,1);	
							
							inc+=2;
						}
					}
					tmpg.dispose();
					
				}
				
				
				
			}
		}catch(IOException ioe)
		{
			
		}

	}
	
	
	public int getQPInfo(int qpnum, int infonum)
	{
		int info = (int)qpstr[qpnum].charAt(10+infonum);
		if(infonum<=7&&infonum>=0) return info;
		return 0;
	}
	
	
	public int[][] getQPColor(int qpnum)
	{
		int qpx = (int)qpstr[qpnum].charAt(6);
		int qpy = (int)qpstr[qpnum].charAt(7);
		int[][] qpcolor = new int[qpy][qpx];
		int color;
		int inc =0;
		for(int i=0;i<qpy;i++)
		{
			for (int j =0;j<qpx ;j++ )
			{
				color = (int)(qpstr[qpnum].charAt(17+inc)<<8)+qpstr[qpnum].charAt(18+inc);
				qpcolor[i][j] = color;
				inc+=2;
			}	
		}
		return qpcolor;
	}
	
	
	public int[][] getQPScaleBig(int qpnum, int bigscale)
	{
		int scalex = (int)qpstr[qpnum].charAt(6);
		int scaley = (int)qpstr[qpnum].charAt(7);
		int[][] scolor = new int[scaley*bigscale][scalex*bigscale];
		int color;
		int inc=0;
	
		//System.out.println("9/2 = " + (9/2));
		int[][] qpcolor = getQPColor(qpnum);
		for(int i = 0;i<scaley;i++)
		{
			for (int j =0;j<scalex ;j++ )
			{
				
				color = qpcolor[i][j];
				int newi = i*bigscale;
				int newj = j*bigscale;
				for(int di=0;di<bigscale;di++)
				{
					for (int dj=0;dj<bigscale ;dj++ )
					{
						scolor[newi+di][newj+dj] = color;
					}	
				}
			}	
		}
		return scolor;

	}
	
	public int[][] getQPScaleBig(int[][] qppixel, int bigscale)
	{
		int scalex = qppixel[0].length;
		int scaley = qppixel.length;
		int[][] scolor = new int[scaley*bigscale][scalex*bigscale];
		int color;
		int inc=0;
	
		//System.out.println("9/2 = " + (9/2));
		int[][] qpcolor = qppixel;
		for(int i = 0;i<scaley;i++)
		{
			for (int j =0;j<scalex ;j++ )
			{
				
				color = qpcolor[i][j];
				int newi = i*bigscale;
				int newj = j*bigscale;
				for(int di=0;di<bigscale;di++)
				{
					for (int dj=0;dj<bigscale ;dj++ )
					{
						scolor[newi+di][newj+dj] = color;
					}	
				}
			}	
		}
		return scolor;

	}
	
	public int[][] getQPScaleSmooth(int qpnum, int width, int height)
	{
		Image scaledImage = qpimage[qpnum].getScaledInstance(width, height, Image.SCALE_SMOOTH);
		int qpcolor[][] = new int[height][width];
		
		// 새 BufferedImage에 그리기
		BufferedImage smoothimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = smoothimage.createGraphics();
		g2d.drawImage(scaledImage, 0, 0, null);
		g2d.dispose();
		for(int i =0; i<height; i++)
			{
				for(int j=0; j<width; j++)
				{
					qpcolor[i][j] = smoothimage.getRGB(j,i);
					//if(qpcolor[i][j] == -0x00000000) qpcolor[i][j] = 0xffffffff;
					//if(qpcolor[i][j]<0x00000000) qpcolor[i][j] = Math.abs(qpcolor[i][j]);
					
				}
			}
		return qpcolor;

	}
	
	public int[][] getQPScale(int qpnum,double scale)
	{
		int[][] oldarray = getQPColor(qpnum);
		
		int[][] small1 = new int[oldarray.length/2][oldarray[0].length/2];
		for(int i = 0;i<oldarray.length/2;i++)
		{
			for(int j=0;j<oldarray[i].length/2;j++)
			{
				small1[i][j] = oldarray[i*2][j*2];
			}
		}
		int[][] small2 = new int[small1.length/2][small1[0].length/2];
		for(int i = 0;i<small1.length/2;i++)
		{
			for(int j=0;j<small1[i].length/2;j++)
			{
				small2[i][j] = small1[i*2][j*2];
			}
		}
		
		int[][] smallscale = small2;
		int value=0;
		for(int i=0;i<100;i++)
		{
			if(0.25*i>=scale)	
			{
				value = i;
				break;
				
			}
		}
		int[][] qpscale = new int[smallscale.length*value][smallscale[0].length*value];
		int color;
		for(int i = 0;i<smallscale.length;i++)
		{
			for (int j =0;j<smallscale[i].length ;j++ )
			{
				
				color = smallscale[i][j];
				int newi = i*value;
				int newj = j*value;
				for(int di=0;di<value;di++)
				{
					for (int dj=0;dj<value ;dj++ )
					{
						qpscale[newi+di][newj+dj] = color;
					}	
				}
			}	
		}
		return qpscale;

		
			
	}
	
	public int[][] getQPScale(int[][] qppixel,double scale)
	{
		int[][] oldarray = qppixel;
		
		int[][] small1 = new int[oldarray.length/2][oldarray[0].length/2];
		for(int i = 0;i<oldarray.length/2;i++)
		{
			for(int j=0;j<oldarray[i].length/2;j++)
			{
				small1[i][j] = oldarray[i*2][j*2];
			}
		}
		int[][] small2 = new int[small1.length/2][small1[0].length/2];
		for(int i = 0;i<small1.length/2;i++)
		{
			for(int j=0;j<small1[i].length/2;j++)
			{
				small2[i][j] = small1[i*2][j*2];
			}
		}
		
		int[][] smallscale = small2;
		int value=0;
		for(int i=0;i<100;i++)
		{
			if(0.25*i>=scale)	
			{
				value = i;
				break;
				
			}
		}
		int[][] qpscale = new int[smallscale.length*value][smallscale[0].length*value];
		int color;
		for(int i = 0;i<smallscale.length;i++)
		{
			for (int j =0;j<smallscale[i].length ;j++ )
			{
				
				color = smallscale[i][j];
				int newi = i*value;
				int newj = j*value;
				for(int di=0;di<value;di++)
				{
					for (int dj=0;dj<value ;dj++ )
					{
						qpscale[newi+di][newj+dj] = color;
					}	
				}
			}	
		}
		return qpscale;

		
			
	}
	
	public int[][] getQPLeftRight(int qpnum)
	{
		int[][] qptmp = getQPColor(qpnum);
		int[][] qplr = new int[qptmp.length][qptmp[0].length];
		for(int i = 0 ; i<qptmp.length ; i++)
		{
			for(int j = 0; j<qptmp[0].length ; j++)
			{
				qplr[i][j] = qptmp[i][qptmp[0].length-1-j];
			}	
		}
		return qplr;

	}
	
	public int[][] getQPLeftRight(int[][] qppixel)
	{
		int[][] qptmp = qppixel;
		int[][] qplr = new int[qptmp.length][qptmp[0].length];
		for(int i = 0 ; i<qptmp.length ; i++)
		{
			for(int j = 0; j<qptmp[0].length ; j++)
			{
				qplr[i][j] = qptmp[i][qptmp[0].length-1-j];
			}	
		}
		return qplr;

	}
	
	
	public int[][] getQPUpDown(int qpnum)
	{
		int[][] qptmp = getQPColor(qpnum);
		int[][] qpud = new int[qptmp.length][qptmp[0].length];
		for(int i = 0 ; i<qptmp.length ; i++)
		{
			for(int j = 0; j<qptmp[0].length ; j++)
			{
				qpud[i][j] = qptmp[qptmp.length-1-i][j];
			}	
		}
		return qpud;
		
	}
	
	public int[][] getQPUpDown(int[][] qppixel)
	{
		int[][] qptmp = qppixel;
		int[][] qpud = new int[qptmp.length][qptmp[0].length];
		for(int i = 0 ; i<qptmp.length ; i++)
		{
			for(int j = 0; j<qptmp[0].length ; j++)
			{
				qpud[i][j] = qptmp[qptmp.length-1-i][j];
			}	
		}
		return qpud;
		
	}
	
	public int[][] getQPBlackWhite(int qpnum,int blacknum)
	{
		int[][] pixel = getQPColor(qpnum);
		int[][] gray = new int[pixel.length][pixel[0].length];
		for(int i=0;i<pixel.length;i++)
		{
			for (int j=0;j<pixel[i].length ;j++ )
			{
				int a = (int)0xff;
				int r = (int)(((pixel[i][j]<<8>>24)&0xff));
				int g = (int)(((pixel[i][j]<<16>>24)&0xff));
				int b = (int)((pixel[i][j]<<24>>24&0xff));
				gray[i][j] = (a<<24)+(r/blacknum << 16) + (g/blacknum << 8) + (b/blacknum);


			}	
		}
		
		return gray;	
	}
	
	
	public int[][] getQPBlackWhite(int[][] qppixel,int blacknum)
	{
		int[][] pixel = qppixel;
		int[][] gray = new int[pixel.length][pixel[0].length];
		for(int i=0;i<pixel.length;i++)
		{
			for (int j=0;j<pixel[i].length ;j++ )
			{
				int a = (int)0xff;
				int r = (int)(((pixel[i][j]<<8>>24)&0xff));
				int g = (int)(((pixel[i][j]<<16>>24)&0xff));
				int b = (int)((pixel[i][j]<<24>>24&0xff));
				gray[i][j] = (a<<24)+(r/blacknum << 16) + (g/blacknum << 8) + (b/blacknum);


			}	
		}
		
		return gray;	
	}
	
	public Point[][] getQPLotate(int qpnum,double angle)
	{
		Point[][] lotp = new Point[(int)qpstr[qpnum].charAt(7)][(int)qpstr[qpnum].charAt(6)];
		int qpx = (int)qpstr[qpnum].charAt(6);
		int qpy = (int)qpstr[qpnum].charAt(7);
		double radian;
		for(int i=0;i<qpy;i++)
		{
			for(int j=0;j<qpx;j++)
			{
				lotp[i][j] = new Point();
				radian = Math.toRadians(angle);
				lotp[i][j].x = (int)((qpx/2)+(j-(qpx/2))*Math.cos(radian)-(i-(qpy/2))*Math.sin(radian));
				lotp[i][j].y = (int)((qpy/2)+(j-(qpx/2))*Math.sin(radian)+(i-(qpy/2))*Math.cos(radian));
				
			}
		}
		return lotp;

	}
	
	public Point[][] getQPLotate(int[][] qppixel, double angle)
	{
		Point[][] lotp = new Point[qppixel.length][qppixel[0].length];
		int qpx = qppixel[0].length;
		int qpy = qppixel.length;
		double radian;
		for(int i=0;i<qpy;i++)
		{
			for(int j=0;j<qpx;j++)
			{
				lotp[i][j] = new Point();
				radian = Math.toRadians(angle);
				lotp[i][j].x = (int)((qpx/2)+(j-(qpx/2))*Math.cos(radian)-(i-(qpy/2))*Math.sin(radian));
				lotp[i][j].y = (int)((qpy/2)+(j-(qpx/2))*Math.sin(radian)+(i-(qpy/2))*Math.cos(radian));
				
			}
		}
		return lotp;
	}
	
	public Point getQPSize(int qpnum)
	{
		Point p = new Point();
		p.x = (int)qpstr[qpnum].charAt(6);
		p.y = (int)qpstr[qpnum].charAt(7);
		return p;
	}
	
	public Point getQPDSize()
	{
		return qpdp;
	}
	
	public static BufferedImage getQPBuffereImage(int qpnum)
	{
		return qpimage[qpnum];
	}
	
}
