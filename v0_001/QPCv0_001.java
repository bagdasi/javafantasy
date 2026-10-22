import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.*;

import java.util.*;
import java.nio.*;
import java.nio.channels.*;

class QPCv0_001 
{
	public static QPCv0_001 qpc;
	public static String imagefilePath = "";
	public static String qpfilePath = "";
	public static String tempPath = "";
	public char[] tc;
	public StringBuilder rtmp,wtmp,mytmp;
	public String tmp="";
	public int[] itmp;
	public long filesize =0;
	public int start=0;//시작위치
	int gsize,ssize;//가로크기,세로크기
	int imgx=0,imgy=0,imfor1=0,imfor2=0,imfor3=0,imfor4=0,imfor5=0,imfor6=0,imfor7=0;
	int[][] color;
	
	//여기서부터 파일내용
	public void FRead()
	{
		int readC;
		int per = 0;
		try{
			File file = new File(tempPath);
			if(file.exists() && file.isFile())
			{
				filesize = file.length();	
				//System.out.println("size : " + filesize);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tempPath),"cp1252"));
			rtmp = new StringBuilder();
			while((readC = br.read())!=-1)
			{
				
				rtmp.append((char)readC);
				per++;
				int progress = (int)(filesize/10);
				/*for(int i=1;i<=10;i++){
					if((progress*i)==per)
					{
						System.out.println("파일읽기 진행 : " + (i*10) + "%");	
					}
				}*/
			}
			
			br.close();
			
		}catch(IOException ioe)
		{
			
		}
	}
	public void FWrite()
	{
		try{
			int pro=0;
			tmp = rtmp.toString();
			tc = tmp.toCharArray();
			itmp = new int[tc.length];
			wtmp = new StringBuilder();
			int i,j;
			for(i=0;i<tc.length;i++)
			{
		
				itmp[i] = (int)tc[i];
				wtmp.append( Integer.toHexString(itmp[i])).append(" ");
				/*for(j=0;j<10;j++){
					if(((tmp.length())/(10-j))==i)
					{
						pro+=10;
						System.out.println("쓰기진행 : " + pro+"%"); 
					}
				}*/
			}
			//System.out.println(tmp.length());
			System.out.println("파일쓰기");
			tmp = wtmp.toString();
			File fw = new File("txtTest.txt");
			if(fw.exists()){
				FileOutputStream fos = new FileOutputStream(fw);
				fos.write(new byte[0]);
				fos.close();
			}
			RandomAccessFile raf = new RandomAccessFile(fw,"rw");
			FileChannel channel = raf.getChannel();
			MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE,0,tmp.length());
			buffer.clear();
			buffer.put(tmp.getBytes("UTF-8"));
			channel.close();
			raf.close();
		}catch(IOException ioe)
		{
			
		}
	}
	public void MyFWrite()
	{
		try{
			mytmp = new StringBuilder();
			mytmp.append((char)4425);//처음 파일인것을 인식
			mytmp.append((char)4989);//처음 파일인것을 인식
			mytmp.append((char)6989);//처음 파일인것을 인식
			mytmp.append((char)4548);//처음 파일인것을 인식
			//System.out.println("효라만사length : " + mytmp.length());
			int paste = 0;//paste : 저장할 수 임시저장
			int rgbpas = 0;//rgbpas : 색상 임시저장
			
			int[] nd = new int[4];//nd는 not dead 아직 죽지 않았다.
			char[] savetemp = new char[2];
			String hexstr = "";//hexstr 24비트저장
			
			for(int i=0;i<itmp.length;i++)
			{
				if(itmp[i]==0x20ac) itmp[i]=0x80;
			}
			mytmp.append((char)17);//시작지점1
			mytmp.append((char)0);//시작지점2(<<16)
			paste = itmp[21];
			paste <<= 8;			
			paste+= itmp[20];
			paste <<= 8;			
			paste+= itmp[19];
			paste <<= 8;			
			paste+= itmp[18];		
			gsize = paste;
			/*nd[0] = (char)((paste>>16)&0xff);/필요 없어서 지움 65535까지 가능함
			nd[1] = (char)((paste>>8)&0xff);
			nd[2] = (char)(paste&0xff);*/
			if(gsize>65535) gsize = 65535;
			//hexstr = new String((char)gsize);//오류
			mytmp.append((char)gsize);//가로크기
			//System.out.println("gsize 추가 후 length: " + mytmp.length());
			//System.out.println(Integer.toHexString(gsize));
			paste = 0;//paste : 저장할 수 임시저장
			paste = itmp[25];
			paste <<= 8;
			paste+= itmp[24];
			paste <<= 8;
			paste+= itmp[23];
			paste <<= 8;
			paste+= itmp[22];
			ssize = paste;
			if(ssize>65535) ssize = 65535;
			/*nd[0] = (char)((paste>>16)&0xff);
			nd[1] = (char)((paste>>8)&0xff);
			nd[2] = (char)(paste&0xff);*/
			//hexstr = "";
			//hexstr = new String((char)ssize);//오류
			mytmp.append((char)ssize);//세로크기
			//System.out.println("ssize 추가 후 length: " + mytmp.length());
			if(imgx>65535) imgx = 65535;
			mytmp.append((char)imgx);//이미지좌표x
			if(imgy>65535) imgy = 65535;
			mytmp.append((char)imgy);//이미지좌표y
			mytmp.append((char)imfor1);//이미지정보1
			mytmp.append((char)imfor2);//이미지정보2
			mytmp.append((char)imfor3);//이미지정보3
			mytmp.append((char)imfor4);//이미지정보4
			mytmp.append((char)imfor5);//이미지정보5
			mytmp.append((char)imfor6);//이미지정보6
			mytmp.append((char)imfor7);//이미지정보7
			//System.out.println("헤더정보length : " + mytmp.length());//test
			int itmplen = itmp.length;
			System.out.println("itmplength : " + itmplen);
			int calc =0;
			calc = 54+(gsize*3*(ssize))+((gsize%4)*(ssize));//일단 첫줄 구하기.(맞는듯 연구 더해야함.)
			//System.out.println(Integer.toHexString(gsize));//test
			for(int i=0;i<ssize;i++)
			{
				int point = 0;//point : gsize저장할 때 주소
				
				for(int j=0;j<gsize;j++)
				{
					
					calc = 0;
					calc = 54+(gsize*(ssize-i-1))+((gsize%4)*(ssize-i-2))+point;//줄마다 저장하는 주소구하기
					if(i==0 && j == 0) System.out.println(calc);//test
					rgbpas =0;
					/*rgbpas = itmp[calc];
					nd[0] = (char)(rgbpas&0xff);
					//rgbpas <<= 8;
					rgbpas = itmp[calc+1];
					nd[1] = (char)(rgbpas&0xff);
					//rgbpas <<= 8;
					rgbpas = itmp[calc+2];
					nd[2] = (char)(rgbpas&0xff);*/
					//여기서부터 위에거 다 지우고.
					nd[0] = (int)0x00;
					rgbpas = itmp[calc];
					nd[3] = (int)rgbpas;
					rgbpas = itmp[calc+1];
					nd[2] = (int)rgbpas<<8;
					rgbpas = itmp[calc+2];
					nd[1] = (int)rgbpas;
					savetemp[0] = (char)((nd[0]+nd[1]));
					savetemp[1] = (char)(nd[2]+nd[3]);
					mytmp.append(savetemp[0]);
					mytmp.append(savetemp[1]);
					
					//hexstr = "";//지금 아무필요 없어서 지웠음.주석처리
					//hexstr = new String(nd);
					
					//mytmp.append(hexstr);
					point+=3;
					if(i == 0 && j == 0){ 
						//System.out.println("length 1개 : " + mytmp.length());//test
						//System.out.println("첫 rgbpas코드 : " + Integer.toHexString(rgbpas));
					}
				}
				//System.out.println(i+"줄 length : " + mytmp.toString().length());
			}
			tmp = "";
			tmp = mytmp.toString();
			//System.out.println(tmp.length());//test
			File fw = new File(qpfilePath);
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
			
		}catch(IOException ioe)
		{
			
		}
	}
	
	public void myFRead()//qp파일 읽어들이는 것.
	{
		int readC;
		BufferedReader br = null;
		
		long qpfs=0;
		char[] nd = new char[3];
		String hexstr = "";
		try{
			br = new BufferedReader(new InputStreamReader(new FileInputStream("test.qp"),"utf-8"));
			File file = new File("test.qp");
			if(file.exists() && file.isFile())
			{
				qpfs = file.length();	
				//System.out.println("size : " + qpfs);
			}
			
			rtmp = new StringBuilder();
			while((readC = br.read())!=-1)
			{
				/*nd[0] = (char)((readC>>16)&0xff);
				nd[1] = (char)((readC>>8)&0xff);
				nd[2] = (char)(readC&0xff);
				hexstr = new String(nd);*/
				rtmp.append((char)readC);
				
				
			}
			br.close();
			//tc = null;
			tc = new char[rtmp.length()];
			
			tc = rtmp.toString().toCharArray();
			// 제어 문자 제거 
			//String filteredOutput = removeControlCharacters(rtmp.toString());
			//System.out.println("문자출력: " + filteredOutput);
			//System.out.println("tc배열 크기 : " + tc.length);
			//System.out.println("qp파일 첫문자 : " + tc[tc[4]] + " 16진수 : " +Integer.toHexString((int)tc[tc[4]]));
			/*for(int i=0;i<tc.length;i++)
			{
				System.out.print(Integer.toHexString((int)tc[i]) + " ");//일단 여기까지 성공.
			}*/
			start = tc[5]<<16;
			start += tc[4];//시작위치구하기
			System.out.println("start정수 : " + start);//start위치 테스트
			gsize=0;
			gsize = (int)tc[6];
			ssize = 0;
			ssize = (int)tc[7];
			color = new int[((tc.length-start)/3)][3];
			int colorarray = 0;
			for(int i=0;i<tc.length-tc[start];i+=3)
			{
				color[colorarray][0] = tc[start+i];
				color[colorarray][1] = tc[start+i+1];
				color[colorarray][2] = tc[start+i+2];
				colorarray++;
			}
		}catch(IOException ioe)
		{
			
		}finally
		{
			if(br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException ioe)
				{
				}
			}
		}
	}
	// 제어 문자를 확인하여 필터링하는 메서드
	public static String removeControlCharacters(String input) {
		StringBuilder result = new StringBuilder();
		for (char c : input.toCharArray()) {
			if (!Character.isISOControl(c)) {
				result.append(c);
		 }
		}
		return result.toString();
	}
	public static void main(String[] args) 
	{
		qpc = new QPCv0_001();
		// JFrame 생성
        JFrame frame = new JFrame("qp파일변경앱");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(null); // 절대 위치 배치

        // "이미지파일" JLabel 생성
        JLabel imageLabel = new JLabel("이미지파일");
        imageLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        imageLabel.setBounds(20, 20, 80, 25);
        frame.add(imageLabel);

        // JTextField 생성
        JTextField imageTextField = new JTextField();
        imageTextField.setFont(new Font("Serif", Font.PLAIN, 15));
        imageTextField.setBounds(110, 20, 250, 25);
        frame.add(imageTextField);

        // "..." 버튼 생성 (chooseBtn1)
        JButton chooseBtn1 = new JButton("...");
        chooseBtn1.setFont(new Font("Serif", Font.PLAIN, 15));
        chooseBtn1.setBounds(370, 20, 50, 25);
        frame.add(chooseBtn1);

        // "qp파일" JLabel 생성
        JLabel qpLabel = new JLabel("qp파일");
        qpLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        qpLabel.setBounds(20, 60, 80, 25);
        frame.add(qpLabel);

        // JTextField 생성
        JTextField qpTextField = new JTextField();
        qpTextField.setFont(new Font("Serif", Font.PLAIN, 15));
        qpTextField.setBounds(110, 60, 250, 25);
        frame.add(qpTextField);

        // "..." 버튼 생성 (chooseBtn2)
        JButton chooseBtn2 = new JButton("...");
        chooseBtn2.setFont(new Font("Serif", Font.PLAIN, 15));
        chooseBtn2.setBounds(370, 60, 50, 25);
        frame.add(chooseBtn2);

        // "변경하기" 버튼 생성
        JButton changeBtn = new JButton("변경하기");
        changeBtn.setFont(new Font("Serif", Font.PLAIN, 15));
        changeBtn.setBounds(150, 120, 100, 30);
        frame.add(changeBtn);

        // "종료하기" 버튼 생성
        JButton exitBtn = new JButton("종료하기");
        exitBtn.setFont(new Font("Serif", Font.PLAIN, 15));
        exitBtn.setBounds(270, 120, 100, 30);
        frame.add(exitBtn);

        // 버튼 이벤트 리스너 추가
        // chooseBtn1 이벤트
        chooseBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(frame, "이미지파일 선택 버튼 클릭됨!");
				// JFileChooser 생성
                JFileChooser fileChooser = new JFileChooser();

                // 파일 필터 설정 ("bmp", "png", "jpg", "jpeg", "gif"만 표시)
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Image Files (bmp, png, jpg, jpeg, gif)", "bmp", "png", "jpg", "jpeg", "gif");
                fileChooser.setFileFilter(filter);

                // 파일 선택 다이얼로그 열기
                int returnValue = fileChooser.showOpenDialog(frame);

                // 파일 선택 결과 확인
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // 선택된 파일 경로와 이름을 텍스트 필드에 설정
                    imageTextField.setText(selectedFile.getAbsolutePath());
					imagefilePath = "";
					imagefilePath = selectedFile.getAbsolutePath();
                }
            }
        });

        // chooseBtn2 이벤트
        chooseBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //OptionPane.showMessageDialog(frame, "qp파일 선택 버튼 클릭됨!");
				// JFileChooser 생성
				JFileChooser fileChooser = new JFileChooser();

				// ".qp" 파일만 보이도록 파일 필터 설정
				FileNameExtensionFilter filter = new FileNameExtensionFilter("QP Files (.qp)", "qp");
				fileChooser.setFileFilter(filter);

				// 파일 선택 다이얼로그 열기
				int returnValue = fileChooser.showSaveDialog(frame);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					// 파일 선택됨
					File selectedFile = fileChooser.getSelectedFile();

					// 선택된 파일의 확장자가 ".qp"인지 확인하고, 아니면 자동 추가
					String filePath = selectedFile.getAbsolutePath();
					if (!filePath.toLowerCase().endsWith(".qp")) {
						filePath += ".qp";
					}

					// JTextField에 파일 경로 설정
					qpTextField.setText(filePath);
					qpfilePath = "";
					qpfilePath = filePath;
				} else {
					
				}
            }
        });

        // 변경하기 버튼 이벤트
        changeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(frame, "파일을 변경합니다!");
				// 확인 대화 상자 표시
				int result = JOptionPane.showConfirmDialog(frame, "qp파일로 변경(생성)하겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				// 확인 버튼을 눌렀을 경우 실행
				if (result == JOptionPane.YES_OPTION) {
					// 여기에 파일 변경(생성) 로직을 추가
					//JOptionPane.showMessageDialog(frame, "파일이 성공적으로 변경(생성)되었습니다!");
					try {
						// 이미지 파일 경로 설정
						File imageFile = new File(imagefilePath); // 이미지 파일의 경로
            
						// BufferedImage로 이미지 읽기
						BufferedImage image = ImageIO.read(imageFile);

						if (image != null) {
							System.out.println("이미지가 성공적으로 로드되었습니다!");
							// BufferedImage 정보를 출력 (너비와 높이)
							System.out.println("이미지 크기: " + image.getWidth() + "x" + image.getHeight());
							
							// BMP 파일로 저장
							String currentDir = System.getProperty("user.dir");
							File outputBMPFile = new File(currentDir,"temp.bmp"); // 저장될 BMP 파일 경로
							ImageIO.write(image, "bmp", outputBMPFile);
							tempPath = currentDir+"\\temp.bmp";
							qpc.FRead();
							qpc.FWrite();
							qpc.MyFWrite();
							
						} else {
							System.out.println("이미지를 읽을 수 없습니다.");
						}
					} catch (IOException ex) {
						ex.printStackTrace();
						System.out.println("파일 로드 중 오류가 발생했습니다!");
					}
				} else {
					// 취소를 눌렀을 경우
					//JOptionPane.showMessageDialog(frame, "작업이 취소되었습니다.");
				}
            }
        });

        // 종료하기 버튼 이벤트
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // 프로그램 종료
            }
        });

        // JFrame을 보이게 설정
        frame.setVisible(true);
	}
}
