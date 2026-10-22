package qpSystem;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.io.File;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;
public class qpdFileSys 
{
	private String[] qpstr;
	public Point qpdp;
	public static BufferedImage[] qpimage;
	public qpdFileSys()
	{
		
	}
	public qpdFileSys(File file)
	{
		qpdFileGet(file);
	}
	public void qpdFileGet(File file)
	{
		char[] tc;
		int color;
		int getpoints =0,getpointe  =0;
		try
		{
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
	public int qpinfoset(int qpnum,int infonum)
	{
		int info = (int)qpstr[qpnum].charAt(10+infonum);
		if(infonum<=7&&infonum>=0) return info;
		return 0;
	}
	public int[][] qpColorSet(int qpnum)
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
	public int[][] qpScaleBigSet(int qpnum,int bigscale)
	{
		int scalex = (int)qpstr[qpnum].charAt(6);
		int scaley = (int)qpstr[qpnum].charAt(7);
		int[][] scolor = new int[scaley*bigscale][scalex*bigscale];
		int color;
		int inc=0;
	
		//System.out.println("9/2 = " + (9/2));
		int[][] qpcolor = qpColorSet(qpnum);
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
	
	public int[][] qpScaleBigSet(int[][] qppixel,int bigscale)
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
	public int[][] qpScaleSmallSet(int qpnum,int smallscale)
	{
		int scalex = (int)qpstr[qpnum].charAt(6);
		int scaley = (int)qpstr[qpnum].charAt(7);
		int[][] scolor = new int[scaley/smallscale][scalex/smallscale];
		int color;
		int inc=0;
		int[][] qpcolor = qpColorSet(qpnum);
		for(int i = 0;i<scaley/smallscale;i++)
		{
			for (int j =0;j<scalex/smallscale ;j++ )
			{
				
				scolor[i][j] = qpcolor[i*smallscale][j*smallscale];
				
			}	
		}
		return scolor;
	}
	public int[][] qpScaleSmallSet(int[][] qppixel,int smallscale)
	{
		int scalex = qppixel[0].length;
		int scaley = qppixel.length;
		int[][] scolor = new int[scaley/smallscale][scalex/smallscale];
		int color;
		int inc=0;
		int[][] qpcolor = qppixel;
		for(int i = 0;i<scaley/smallscale;i++)
		{
			for (int j =0;j<scalex/smallscale ;j++ )
			{
				
				scolor[i][j] = qpcolor[i*smallscale][j*smallscale];
				
			}	
		}
		return scolor;
	}
	private int[][] qp4SmallSet(int[][] oldarray)
	{
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
		/*int[][] small3 = new int[small2.length/2][small2[0].length/2];
		for(int i = 0;i<small2.length/2;i++)
		{
			for(int j=0;j<small2[i].length/2;j++)
			{
				small3[i][j] = small2[i*2][j*2];
			}
		}*/
		return small2;
	}
	
	public int[][] qpScaleSet(int qpnum,double scale)
	{
		int[][] smallscale = qp4SmallSet(qpColorSet(qpnum));
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
	public int[][] qpScaleSet(int[][] qppixel,double scale)
	{
		int[][] smallscale = qp4SmallSet(qppixel);
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
	public int[][] qpBlackWhite(int qpnum,int blacknum)
	{
		int[][] pixel = qpColorSet(qpnum);
		int[][] gray = new int[pixel.length][pixel[0].length];
		for(int i=0;i<pixel.length;i++)
		{
			for (int j=0;j<pixel[i].length ;j++ )
			{
				int r = (int)(((pixel[i][j]>>16)&0xff));
				int g = (int)(((pixel[i][j]>>8)&0xff));
				int b = (int)((pixel[i][j]&0xff));
				gray[i][j] = (r/blacknum << 16) + (g/blacknum << 8) + (b/blacknum);


			}	
		}
		/*int r = (int)((double)((pixel[0][0]>>16)&0xff));
				int g = (int)((double)((pixel[0][0]>>8)&0xff));
				int b = (int)((double)(pixel[0][0]&0xff));
				gray[0][0] = (int)(0.299 * r + 0.587 * g + 0.114 * b);
				System.out.println("r : "+ r + " g : " + g + " b : " + b);
				System.out.println("gray : " + Integer.toHexString(gray[0][0]));*/
		return gray;
	}
	public int[][] qpBlackWhite(int[][] qppixel,int blacknum)
	{
		int[][] pixel = qppixel;
		int[][] gray = new int[pixel.length][pixel[0].length];
		for(int i=0;i<pixel.length;i++)
		{
			for (int j=0;j<pixel[i].length ;j++ )
			{
				int r = (int)(((pixel[i][j]>>16)&0xff));
				int g = (int)(((pixel[i][j]>>8)&0xff));
				int b = (int)((pixel[i][j]&0xff));
				gray[i][j] = (r/blacknum << 16) + (g/blacknum << 8) + (b/blacknum);


			}	
		}
		/*int r = (int)((double)((pixel[0][0]>>16)&0xff));
				int g = (int)((double)((pixel[0][0]>>8)&0xff));
				int b = (int)((double)(pixel[0][0]&0xff));
				gray[0][0] = (int)(0.299 * r + 0.587 * g + 0.114 * b);
				System.out.println("r : "+ r + " g : " + g + " b : " + b);
				System.out.println("gray : " + Integer.toHexString(gray[0][0]));*/
		return gray;
	}
	public Point[][] qpLotateSet(int qpnum,double angle)
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
	public Point[][] qpLotateSet(int[][] qppixel,double angle)
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
	public Point qpSizeSet(int qpnum)
	{
		Point p = new Point();
		p.x = (int)qpstr[qpnum].charAt(6);
		p.y = (int)qpstr[qpnum].charAt(7);
		return p;
	}
	public Point qpdSizeSet()
	{
		return qpdp;
		
	}
	public static BufferedImage qpImageSet(int qpnum)
	{
		return qpimage[qpnum];
	}
	
}
