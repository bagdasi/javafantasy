package home.my.gumhuan.qpImageSystem.imageProcessing;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.io.File;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class ImageSys
{
	private  BufferedImage image;
	private  Point imageSize;
	private int[][] thisARGB;
	private List<BufferedImage> imageList = new ArrayList<>();
	private List<Integer[][]> argbList = new ArrayList<>();
	private List<String> infoList = new ArrayList<>();
	public ImageSys()
	{
		
	}
	public ImageSys(String path)
	{
		try{
			BufferedImage loaded = ImageIO.read(new File(path));

			image = new BufferedImage(loaded.getWidth(),loaded.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = image.createGraphics();
			g2d.drawImage(loaded,0,0,null);
			g2d.dispose();
			imageSize = new Point(image.getWidth(), image.getHeight());
			thisARGB = new int[image.getHeight()][image.getWidth()];
			for(int i = 0; i<image.getHeight(); i++)
			{
				for(int j = 0; j<image.getWidth(); j++)
				{
					thisARGB[i][j] = image.getRGB(j,i);
				}	
			}
		}catch(IOException ioe)
		{
			ioe.printStackTrace();	
			System.out.println("이미지 로드 실패: " + path);

		}
	}
	public void setImage(String path)
	{
		try{
			BufferedImage loaded = ImageIO.read(new File(path));

			image = new BufferedImage(loaded.getWidth(),loaded.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = image.createGraphics();
			g2d.drawImage(loaded,0,0,null);
			g2d.dispose();
			imageSize = new Point(image.getWidth(), image.getHeight());
			thisARGB = new int[image.getHeight()][image.getWidth()];
			for(int i = 0; i<image.getHeight(); i++)
			{
				for(int j = 0; j<image.getWidth(); j++)
				{
					thisARGB[i][j] = image.getRGB(j,i);
				}	
			}
		}catch(IOException ioe)
		{
			ioe.printStackTrace();	
			System.out.println("이미지 로드 실패: " + path);

		}
	}
	
	public int[][] setImageScaleBig(int scale)
	{
		int[][] scaleColor = new int[imageSize.y*scale][imageSize.x*scale];
		int color;
		for(int i=0;i<imageSize.y;i++)
		{
			for(int j=0;j<imageSize.x;j++)
			{
				color = thisARGB[i][j];
				int newi = i*scale;
				int newj = j*scale;
				for(int k = 0; k<scale;k++)
				{
					for(int l=0;l<scale;l++)
					{
						scaleColor[newi+k][newj+l] = color;
					}	
				}
				
				
			}	
		}
		return scaleColor;
		
	}
	public void addListScaleBig(int scale,String info)
	{
		
		int[][] scaleTemp = setImageScaleBig(scale);
		Integer[][] temp = new Integer[scaleTemp.length][scaleTemp[0].length];
		for(int i=0; i <temp.length;i++)
		{
			for(int j =0; j<temp[0].length;j++)
			{
				temp[i][j] = scaleTemp[i][j];
			}	
		}
		argbList.add(temp);
		BufferedImage tempImage = new BufferedImage(temp[0].length,temp.length,BufferedImage.TYPE_INT_ARGB);
		
		for(int i=0;i<temp.length;i++)
		{
			for(int j=0;j<temp[0].length;j++)
			{
				tempImage.setRGB(j,i,scaleTemp[i][j]);
			}	
		}
		imageList.add(tempImage);
		infoList.add(info);
		
		
	}
	public int[][] setSmoothScale(int width, int height)
	{
		Image scaleImage = image.getScaledInstance(width,height,BufferedImage.TYPE_INT_ARGB);
		int[][] smoothScale = new int[height][width];
		BufferedImage smoothImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = smoothImage.createGraphics();
		g2d.drawImage(scaleImage,0,0,null);
		g2d.dispose();
		
		for(int i = 0; i< height; i++)
		{
			for(int j=0; j<width; j++)
			{	
				smoothScale[i][j] = smoothImage.getRGB(j,i);
			}	
		}
		return smoothScale;
			
	}
	public void addListSmoothScale(int width, int height, String info)
	{
		Integer[][] temp = new Integer[height][width];
		int[][] scaleTemp = setSmoothScale(width,height);
		for(int i =0; i< temp.length; i++)
		{
			for(int j=0; j<temp[0].length; j++)
			{
				temp[i][j] = scaleTemp[i][j];	
			}	
		}
		argbList.add(temp);
		BufferedImage tempImage = new BufferedImage(temp[0].length,temp.length,BufferedImage.TYPE_INT_ARGB);
		for(int i=0;i<temp.length;i++)
		{
			for(int j=0;j<temp[0].length;j++)
			{
				tempImage.setRGB(j,i,scaleTemp[i][j]);
			}	
		}
		imageList.add(tempImage);
		infoList.add(info);
	}
	
	public int[][] setScale(float scale)
	{
		int[][] small1 = new int[imageSize.y/2][imageSize.x/2];
		for(int i = 0; i < small1.length; i++)
		{
			for(int j =0; j<small1[0].length; j++)
			{
				small1[i][j] = thisARGB[i*2][j*2];
			}	
		}
		int[][] small2 = new int[small1.length/2][small1[0].length/2];
		for(int i = 0; i<small2.length; i++)
		{
			for(int j = 0; j<small2[0].length; j++)
			{
				small2[i][j] = small1[i*2][j*2];	
			}	
		}
		int value = 0;
		for(int i=0; i< 100; i++)
		{
			if(0.25*i >= scale)
			{
				value=i;
				break;	
			}	
		}
		int[][] setScale = new int[small2.length*value][small2[0].length*value];
		int color;
		for(int i = 0; i<small2.length; i++)
		{
			for(int j =0; j<small2[0].length; j++)
			{
				color = small2[i][j];
				int newi = i*value;
				int newj = j*value;
				for(int k =0; k<value; k++)
				{
					for(int l=0; l<value; l++)
					{
						setScale[newi+k][newj+l] = color;	
					}
				}
				
					
			}	
		}
		return setScale;
		
	}
	public void addListScale(float scale, String info)
	{
		
		int[][] scaleTemp = setScale(scale);
		Integer[][] temp = new Integer[scaleTemp.length][scaleTemp[0].length];
		for(int i =0; i< temp.length; i++)
		{
			for(int j=0; j<temp[0].length; j++)
			{
				temp[i][j] = scaleTemp[i][j];	
			}	
		}
		argbList.add(temp);
		BufferedImage tempImage = new BufferedImage(temp[0].length,temp.length,BufferedImage.TYPE_INT_ARGB);
		for(int i=0;i<temp.length;i++)
		{
			for(int j=0;j<temp[0].length;j++)
			{
				tempImage.setRGB(j,i,scaleTemp[i][j]);
			}	
		}
		imageList.add(tempImage);
		infoList.add(info);
	}
	public int[][] setLeftRight()
	{
		int[][] lrTemp = new int[imageSize.y][imageSize.x];
		for(int i=0; i<imageSize.y; i++)
		{
			for(int j=0; j<imageSize.x; j++)
			{
				lrTemp[i][j] = thisARGB[i][thisARGB[0].length-1-j];
			}	
		}
		return lrTemp;
	}
	public void addListLeftRight(String info)
	{
		int[][] scaleTemp = setLeftRight();
		Integer[][] temp = new Integer[scaleTemp.length][scaleTemp[0].length];
		for(int i =0; i< temp.length; i++)
		{
			for(int j=0; j<temp[0].length; j++)
			{
				temp[i][j] = scaleTemp[i][j];	
			}	
		}
		argbList.add(temp);
		BufferedImage tempImage = new BufferedImage(temp[0].length,temp.length,BufferedImage.TYPE_INT_ARGB);
		for(int i=0;i<temp.length;i++)
		{
			for(int j=0;j<temp[0].length;j++)
			{
				tempImage.setRGB(j,i,scaleTemp[i][j]);
			}	
		}
		imageList.add(tempImage);
		infoList.add(info);
	}
	public int[][] setUpDown()
	{
		int[][] udTemp = new int[imageSize.y][imageSize.x];
		for(int i=0; i<imageSize.y; i++)
		{
			for(int j=0; j<imageSize.x; j++)
			{
				udTemp[i][j] = thisARGB[thisARGB.length-1-i][j];
			}	
		}
		return udTemp;
	}
	public void addListUpDown(String info)
	{
		int[][] scaleTemp = setUpDown();
		Integer[][] temp = new Integer[scaleTemp.length][scaleTemp[0].length];
		for(int i =0; i< temp.length; i++)
		{
			for(int j=0; j<temp[0].length; j++)
			{
				temp[i][j] = scaleTemp[i][j];	
			}	
		}
		argbList.add(temp);
		BufferedImage tempImage = new BufferedImage(temp[0].length,temp.length,BufferedImage.TYPE_INT_ARGB);
		for(int i=0;i<temp.length;i++)
		{
			for(int j=0;j<temp[0].length;j++)
			{
				tempImage.setRGB(j,i,scaleTemp[i][j]);
			}	
		}
		imageList.add(tempImage);
		infoList.add(info);
	}
	public int[][] setBlackWhite(int black)
	{
		int[][] gray = new int[imageSize.y][imageSize.x];
		for(int i =0; i<gray.length; i++)
		{
			for(int j=0;j<gray[0].length; j++)
			{
				int a = (int)0xff;
				int r = (int)((thisARGB[i][j]<<8>>24)&0xff);
				int g = (int)((thisARGB[i][j]<<16>>24)&0xff);
				int b = (int)((thisARGB[i][j]<<24>>24)&0xff);
				gray[i][j] = (a<<24)+(r/black<<16)+(g/black<<8)+(b/black);	
			}	
		}
		return gray;
	}
	public void addListBlackWhite(int black, String info)
	{
		int[][] scaleTemp = setBlackWhite(black);
		Integer[][] temp = new Integer[scaleTemp.length][scaleTemp[0].length];
		for(int i =0; i< temp.length; i++)
		{
			for(int j=0; j<temp[0].length; j++)
			{
				temp[i][j] = scaleTemp[i][j];	
			}	
		}
		argbList.add(temp);
		BufferedImage tempImage = new BufferedImage(temp[0].length,temp.length,BufferedImage.TYPE_INT_ARGB);
		for(int i=0;i<temp.length;i++)
		{
			for(int j=0;j<temp[0].length;j++)
			{
				tempImage.setRGB(j,i,scaleTemp[i][j]);
			}	
		}
		imageList.add(tempImage);
		infoList.add(info);
	}
	public Point[][] setLotate(double angle)
	{
		Point[][] lotp = new Point[imageSize.y][imageSize.x];
		double radian;
		for(int i=0; i<lotp.length; i++)
		{
			for(int j=0; j<lotp[0].length; j++)
			{
				lotp[i][j] = new Point();
				radian = Math.toRadians(angle);
				lotp[i][j].x = (int)((imageSize.x/2)+(j - (imageSize.x/2))*Math.cos(radian)-(i-(imageSize.y/2))*Math.sin(radian));
				lotp[i][j].y = (int)((imageSize.y/2)+(j - (imageSize.x/2))*Math.sin(radian)-(i-(imageSize.y/2))*Math.cos(radian));
			}	
		}
		return lotp;
		
	}
	public void addListLotate(double angle, String info)
	{
		
		Point[][] rotateTemp = setLotate(angle);
		
		
		int[][] scaleTemp = new int[imageSize.y][imageSize.x];
		for(int i = 0; i < scaleTemp.length; i++)
		{
			for(int j = 0; j< scaleTemp[0].length; j++)
			{
				if((rotateTemp[i][j].x>=0&&rotateTemp[i][j].x<imageSize.x)&&(rotateTemp[i][j].y>=0&&rotateTemp[i][j].y<imageSize.y))
				{
					scaleTemp[i][j] = thisARGB[rotateTemp[i][j].y][rotateTemp[i][j].x];
				}else
				{
					scaleTemp[i][j] = 0xff010101;	
				}	
			}	
		}
		Integer[][] temp = new Integer[scaleTemp.length][scaleTemp[0].length];
		for(int i =0; i< temp.length; i++)
		{
			for(int j=0; j<temp[0].length; j++)
			{
				temp[i][j] = scaleTemp[i][j];	
			}	
		}
		argbList.add(temp);
		BufferedImage tempImage = new BufferedImage(temp[0].length,temp.length,BufferedImage.TYPE_INT_ARGB);
		for(int i=0;i<temp.length;i++)
		{
			for(int j=0;j<temp[0].length;j++)
			{
				tempImage.setRGB(j,i,scaleTemp[i][j]);
			}	
		}
		imageList.add(tempImage);
		infoList.add(info);
	}
	public BufferedImage getImage()
	{
		return image;	
	}
	public Point getSize()
	{
		return imageSize;	
	}
	public BufferedImage getImageList(int num)
	{
		return imageList.get(num);
	}
	public Integer[][] getARGBList(int num)
	{
		return argbList.get(num);	
	}
	public String getInfoList(int num)
	{
		return infoList.get(num);	
	}
	
	
}
