import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.Timer;
import java.util.List;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.nio.*;
import java.nio.channels.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class QPPv0_0026 
{
	public static QPPv0_0026 qpp;
	JPanel menuPanel,rightPanel,workPanel,statusbarPanel;
	JButton mFileButton,mEditButton,mDrawButton,mOptionButton,mViewButton,mHelpButton;
	JButton newFileButton,qpFileOpenButton,qpFileSaveButton,exitButton;
	JButton selectButton,cutButton,copyButton,pasteButton,preButton,nextButton;
	JButton penButton,fillButton,eraseButton,stringButton,colorButton;
	//v0.0023업데이트 사각형,타원,중심점 업데이트 종료할때 메시지창도 그리고 굵기도
	JButton rectbtn,circlebtn,strokebtn,centerbtn;//v0.0023
	JButton linebtn,getrgbbtn;//v0.0024
	JButton arcbtn;//v0.0026
	JButton settingButton,imageOptionButton;
	JButton markButton,gridButton,stateButton;
	JButton licenseButton,colorHelpButton,supportButton,inforButton;
	JPanel drawPanel;
	JFrame frame;
	public static BufferedImage image;
	public static int dpwidth = 800, dpheight = 600;
	public static int right_num = 0;
	public static int drawSelect = 0;
	public static boolean cutbool = false;
	public static int penst = 3;
	public static Point statusp;
	//draw정의
	private Path2D currentPath;
	private ArrayList<Path2D> paths;
	public int mx,my;
	private int prevx,prevy;
	public int rgbget;
	public static int replacementColor = Color.BLACK.getRGB();
	private Path2D selectPath;
	private ArrayList<Path2D> selpaths;
	private List<Integer> savergb;
	private Stack<BufferedImage> undoStack = new Stack<>();
	private Stack<BufferedImage> redoStack = new Stack<>();
	private Stack<BufferedImage> selectundoStack = new Stack<>();
	private Stack<BufferedImage> tempStack = new Stack<>();
	private double startX,startY,endX,endY;
	public String dpstr="",shapestr="";
	public int fontsize=0;
	public Point point=null,spoint;
	private boolean clicked = false;
	public static boolean statusbar = true;
	public static boolean markb = false;
	public static int zoomscale = 1;
	public static boolean gridb = false,centerb = false,rectb = false,circleb = false,lineb = false;//업데이트centerb,rectb,circleb
	public static boolean arcb = false , arcmode = false, arcsec = false;//0.0026업데이트
	public static int arcx=0,arcy=0,arcwidth=0,archeight=0,startarc=0,endarc=0;
	//v0.0025
	int tmpsel;
	
	public String filename = "";
	void Init()
	{
		statusp = new Point();
		statusp.x = 0;
		statusp.y = 0;
		currentPath = null; // 경로를 null로 초기화
		paths = new ArrayList<>();
		selectPath = null;
		selpaths = new ArrayList<>();
		image = new BufferedImage(dpwidth,dpheight,BufferedImage.TYPE_INT_ARGB);
		//여기서부터 프레임
		frame = new JFrame("콰라포도 프로젝트v0.0025");
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		mFileButton = new JButton("파일");
		mEditButton = new JButton("편집");
		mDrawButton = new JButton("그리기");
		mOptionButton = new JButton("옵션");
		mViewButton = new JButton("보기");
		mHelpButton = new JButton("도움말");
		
		menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		menuPanel.setBackground(Color.BLUE);
		menuPanel.add(mFileButton);
		menuPanel.add(mEditButton);
		menuPanel.add(mDrawButton);
		menuPanel.add(mOptionButton);
		menuPanel.add(mViewButton);
		menuPanel.add(mHelpButton);
		frame.add(menuPanel,BorderLayout.NORTH);
		
		mFileButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//System.out.println("파일버튼이 눌렀습니다.");
				right_num = 0;
				rightPanel.removeAll();
				rightPanel.add(newFileButton);
				rightPanel.add(qpFileOpenButton);
				rightPanel.add(qpFileSaveButton);
				rightPanel.add(exitButton);
				frame.setVisible(true);
				frame.repaint();
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		mEditButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//System.out.println("편집버튼이 눌렀습니다.");
				right_num = 1;
				rightPanel.removeAll();
				rightPanel.add(selectButton);
				rightPanel.add(cutButton);
				rightPanel.add(copyButton);
				rightPanel.add(pasteButton);
				rightPanel.add(preButton);
				rightPanel.add(nextButton);
				frame.setVisible(true);
				frame.repaint();
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		mDrawButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				right_num = 2;
				rightPanel.removeAll();
				rightPanel.add(penButton);
				rightPanel.add(rectbtn);
				rightPanel.add(circlebtn);
				rightPanel.add(linebtn);
				rightPanel.add(arcbtn);
				rightPanel.add(fillButton);
				rightPanel.add(eraseButton);
				rightPanel.add(stringButton);
				rightPanel.add(strokebtn);
				rightPanel.add(colorButton);
				frame.setVisible(true);
				frame.repaint();
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		mOptionButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				right_num = 3;
				rightPanel.removeAll();
				rightPanel.add(settingButton);
				rightPanel.add(imageOptionButton);
				frame.setVisible(true);
				frame.repaint();
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		mViewButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				right_num = 4;
				rightPanel.removeAll();
				rightPanel.add(markButton);
				rightPanel.add(gridButton);
				rightPanel.add(centerbtn);
				rightPanel.add(stateButton);
				rightPanel.add(getrgbbtn);
				frame.setVisible(true);
				frame.repaint();
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		mHelpButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				right_num = 5;
				rightPanel.removeAll();
				rightPanel.add(licenseButton);
				rightPanel.add(colorHelpButton);
				rightPanel.add(supportButton);
				rightPanel.add(inforButton);
				frame.setVisible(true);
				frame.repaint();
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		
		//newFileButton,qpFileOpenButton,qpFileSaveButton,exitButton;
		newFileButton = new JButton("새로 만들기");
		qpFileOpenButton = new JButton("qp파일열기");
		qpFileSaveButton = new JButton("qp파일저장");
		exitButton = new JButton("끝내기");
		
		//selectButton,cutButton,copyButton,pasteButton,preButton,nextButton;
		selectButton = new JButton("선택");
		cutButton = new JButton("잘라내기");
		copyButton = new JButton("복사하기");
		pasteButton = new JButton("붙여놓기");
		preButton = new JButton("이전(z)");
		nextButton = new JButton("다음(x)");
		
		//penButton,fillButton,eraseButton,stringButton,colorButton;
		penButton = new JButton("펜(1)");
		rectbtn = new JButton("사각형(2)");
		circlebtn = new JButton("타원(3)");
		linebtn = new JButton("직선(4)");
		arcbtn = new JButton("원호(9)");
		fillButton = new JButton("채우기(7)");
		eraseButton = new JButton("지우개(8)");
		stringButton = new JButton("문자(6)");
		strokebtn = new JButton("굵기(q)");
		colorButton = new JButton("색선택(w)");
		
		//settingButton,imageOptionButton;
		settingButton = new JButton("이미지설정");
		imageOptionButton = new JButton("이미지정보");
		
		//markButton,gridButton,stateButton;
		markButton = new JButton("눈금(c)");
		gridButton = new JButton("격자(v)");
		centerbtn = new JButton("중심점(b)");
		stateButton = new JButton("상태");
		getrgbbtn = new JButton("RGB값");
		
		//licenseButton,colorHelpButton,supportButton,inforButton;
		licenseButton = new JButton("약관");
		colorHelpButton = new JButton("사용법");
		supportButton = new JButton("제품지원");
		inforButton = new JButton("제품정보");
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
		rightPanel.setPreferredSize(new Dimension(120,800));
		rightPanel.setBackground(Color.GREEN);
		
		// 상태 표시줄 생성
        JPanel statusbarPanel = new JPanel(new BorderLayout());
        JLabel statusLabel = new JLabel("상태 표시줄 메시지");
        
		statusLabel.setFont(new Font("Serif", Font.BOLD, 20)); // 크기 30의 폰트 설정statusbarPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        statusbarPanel.add(statusLabel, BorderLayout.WEST);
		String statusText = String.format("상태표시줄입니다. 너비 : %d 높이 : %d 마우스X : %d 마우스Y : %d",dpwidth,dpheight,statusp.x,statusp.y);
		statusLabel.setText(statusText);
        // 프레임의 하단에 추가
        frame.add(statusbarPanel, BorderLayout.SOUTH);
		System.out.println("statusbar : " + statusbar);
		switch(right_num)
		{
			case 0:
				rightPanel.removeAll();
				rightPanel.add(newFileButton);
				rightPanel.add(qpFileOpenButton);
				rightPanel.add(qpFileSaveButton);
				rightPanel.add(exitButton);
				break;
			case 1:
				rightPanel.removeAll();
				rightPanel.add(selectButton);
				rightPanel.add(cutButton);
				rightPanel.add(copyButton);
				rightPanel.add(pasteButton);
				rightPanel.add(preButton);
				rightPanel.add(nextButton);
				
				break;
				
			case 2:
				rightPanel.removeAll();
				rightPanel.add(penButton);
				rightPanel.add(rectbtn);
				rightPanel.add(circlebtn);
				rightPanel.add(linebtn);
				rightPanel.add(arcbtn);
				rightPanel.add(fillButton);
				rightPanel.add(eraseButton);
				rightPanel.add(stringButton);
				rightPanel.add(strokebtn);
				rightPanel.add(colorButton);
				break;
				
			case 3:
				rightPanel.removeAll();
				rightPanel.add(settingButton);
				rightPanel.add(imageOptionButton);
				break;
				
			case 4:
				rightPanel.removeAll();
				rightPanel.add(markButton);
				rightPanel.add(gridButton);
				rightPanel.add(centerbtn);
				rightPanel.add(stateButton);
				rightPanel.add(getrgbbtn);
				break;
				
			case 5:
				rightPanel.removeAll();
				rightPanel.add(licenseButton);
				rightPanel.add(colorHelpButton);
				rightPanel.add(supportButton);
				rightPanel.add(inforButton);
				break;
				
			default:
				
				break;
		}
		frame.add(rightPanel,BorderLayout.EAST);
		
		newFileButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try{
					
					JFrame nfFrame = new JFrame();
					nfFrame.setTitle("새로만들기");
					nfFrame.setSize(300,200);
					nfFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					nfFrame.setLayout(null);
					JLabel widthLabel = new JLabel("너비 : ");
					widthLabel.setBounds(20,20,50,25);
					nfFrame.add(widthLabel);
					JTextField widthText = new JTextField();
					widthText.setBounds(60,20,60,25);
					widthText.setText(Integer.toString(dpwidth));
					nfFrame.add(widthText);
					JLabel heightLabel = new JLabel("높이 : ");
					heightLabel.setBounds(130,20,160,25);
					nfFrame.add(heightLabel);
					JTextField heightText = new JTextField();
					heightText.setBounds(170,20,60,25);
					heightText.setText(Integer.toString(dpheight));
					nfFrame.add(heightText);
		
					JButton okButton = new JButton("확인");
					okButton.setBounds(40,60,60,30);
					nfFrame.add(okButton);
					JButton closeButton = new JButton("닫기");
					closeButton.setBounds(120,60,60,30);
					nfFrame.add(closeButton);
					okButton.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e)
						{
							try{
								String Sw,Sh;
								Sw = widthText.getText();
								Sh = heightText.getText();
								dpwidth = Integer.parseInt(Sw);
								dpheight = Integer.parseInt(Sh);
								//frame.dispose();
								//Init();
								drawPanel.setPreferredSize(new Dimension(dpwidth,dpheight));
								drawPanel.setSize(dpwidth,dpheight);
								drawPanel.setBackground(Color.WHITE);
								//drawPanel.setOpaque(true); // 불투명 설정
								frame.revalidate();
								frame.repaint();
								image = new BufferedImage(dpwidth,dpheight,BufferedImage.TYPE_INT_ARGB);
								Graphics2D g2d = image.createGraphics();
								g2d.setColor(Color.WHITE);
								g2d.fillRect(0,0,dpwidth,dpheight);
								g2d.setColor(new Color(replacementColor));
								g2d.dispose();
								nfFrame.dispose();
							}catch(NumberFormatException nfe)
							{
								System.out.println("숫자를 눌려주세요");
							}
						}
					});
					closeButton.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e)
						{
					
				
							nfFrame.dispose();
						}
					});
					nfFrame.setLocation(400,400);
		
					nfFrame.setVisible(true);
					
				}catch(NumberFormatException nfe)
				{
			
				}
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		qpFileOpenButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				char[] tc;
				int[] color;
				
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
					returnvalue = filechooser.showOpenDialog(frame);
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
						filename = filePath;
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
							String hexString;
							for(int i=0;i< tc.length-start;i+=2)
							{
								//hexString = Integer.toUnsignedString((int)((tc[start+i]<<8)+tc[start+i+1]), 16);
								//color[colorarray] = Integer.parseInt(hexString,16);
								
								color[colorarray] = (int)((-0x00000000)^((tc[start+i]<<8)+tc[start+i+1]));
								colorarray++;
							}
							//System.out.println("color:"+color[110]);
							dpwidth = tc[6];
							dpheight = tc[7];
							drawPanel.setPreferredSize(new Dimension(dpwidth,dpheight));
							drawPanel.setSize(dpwidth,dpheight);
							drawPanel.setBackground(Color.WHITE);
							//drawPanel.setOpaque(true); // 불투명 설정
							frame.revalidate();
							frame.repaint();
							//drawPanel.setBackground(Color.WHITE);
							image = new BufferedImage(dpwidth, dpheight, BufferedImage.TYPE_INT_ARGB);
							Graphics g = image.getGraphics();
							g.setColor(Color.WHITE);
							g.fillRect(0,0,dpwidth, dpheight);
							
							
							/*try{
								Thread.sleep(100);
							}catch(InterruptedException ie)
							{
								
							}*/
							colorarray=0;
							for(int i=0;i<tc[7];i++)
							{
								for (int j=0;j<tc[6] ;j++ )
								{
									
									//image.setRGB(j,i,color[colorarray]);
									
									g.setColor(new Color(color[colorarray]));
									g.drawLine(j,i,j,i);
									colorarray++;
								}	
							}
							g.dispose();
							drawPanel.repaint();
							
						}
					}
					
				}
				catch (IOException ioe)
				{
				}
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
	
			}
		});
		qpFileSaveButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				StringBuilder writeTemp;
				String tmp;
				try{
					int testRGB = 0;
					int paste =0; //임시저장공간
					int[] nd = new int[4];//nd는 not dead 아직 죽지 않았다.
					char[] savetemp = new char[2];
					image = qpp.image;
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
					returnvalue = filechooser.showSaveDialog(frame);
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
						filename = filePath;
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
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		exitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				int result = JOptionPane.showConfirmDialog(null, "정말 끝내겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		
		selectButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				drawSelect = 1;
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		cutButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				cutbool = true;
				drawSelect = 3;
				qpp.drawPaint();
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		copyButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				drawSelect = 4;
				qpp.drawPaint();
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		pasteButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				drawSelect = 5;
				qpp.drawPaint();
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		preButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				drawSelect = 6;
				qpp.drawPaint();
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		nextButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				drawSelect = 7;
				qpp.drawPaint();
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		
		penButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				drawSelect = 0;
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
				
			
			}
		});
		rectbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				drawSelect = 20;
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		circlebtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				drawSelect = 21;
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		linebtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				drawSelect = 22;
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		arcbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				drawSelect = 26;
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		fillButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("채우기버튼");
				
				drawSelect = 8;
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		eraseButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				drawSelect = 9;
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		stringButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				JFrame cframe = new JFrame("문자");
				cframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				cframe.setSize(500, 300);
				cframe.setLayout(new BorderLayout());
				// 상단 패널 생성 (글꼴, 크기, 문자 입력)
				JPanel topPanel = new JPanel();
				topPanel.setLayout(new FlowLayout()); // 컴포넌트 정렬을 위한 FlowLayout 사용
				cframe.add(topPanel, BorderLayout.NORTH);
				JLabel strLabel = new JLabel("글꼴:");
				//strLabel.setBounds(30,30,30,20);
				topPanel.add(strLabel);
				//JComboBox 생성
				String[] items = {"Serif","SansSerif","Monospaced","Dialog","DialogInput"};
				JComboBox<String> strCombo = new JComboBox<>(items);
				//strCombo.setBounds(70,30,100,20);
				topPanel.add(strCombo);
				strCombo.addActionListener(event->{
					
				});
				
				JLabel sizeLabel = new JLabel("크기:");
				//sizeLabel.setBounds(250,30,30,20);
				topPanel.add(sizeLabel);
				// JTextField 생성
				JTextField sizeField = new JTextField(5);
				//sizeField.setBounds(300,30,80,30);
				topPanel.add(sizeField);
				JPanel midPanel = new JPanel();
				midPanel.setLayout(new FlowLayout());
				cframe.add(midPanel,BorderLayout.CENTER);
				JLabel textLabel = new JLabel("문자:");
				//textLabel.setBounds(30,70,30,20);
				midPanel.add(textLabel);
				
				JTextField textField = new JTextField(20);
				//textField.setBounds(70,70,350,30);
				midPanel.add(textField);
				
				
				// 확인 및 취소 버튼 생성
				JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new FlowLayout());
				//buttonPanel.setBounds(0, 100, 500, 100); // buttonPanel의 위치와 크기 설정
				cframe.add(buttonPanel, BorderLayout.SOUTH);
				JButton confirmButton = new JButton("확인");
				JButton cancelButton = new JButton("취소");
				//confirmButton.setBounds(150,20,90,30);
				//cancelButton.setBounds(260,20,90,30);
				buttonPanel.add(confirmButton);
				buttonPanel.add(cancelButton);
				//cframe.add(buttonPanel);
				// 확인 버튼 액션 리스너
				confirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String text = sizeField.getText();
						if(!text.matches("\\d+"))
						{
							JOptionPane.showMessageDialog(frame, "크기란에 숫자만 입력해주세요!", "입력 오류", JOptionPane.ERROR_MESSAGE);
						}else
						{
							drawSelect = 19;
							dpstr="";
							dpstr = textField.getText();
							fontsize = Integer.parseInt(sizeField.getText());
							shapestr = (String)strCombo.getSelectedItem();
							cframe.dispose();
						}
						
						
						
					}
				});
				// 취소 버튼 액션 리스너
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cframe.dispose();
					}
				});
				cframe.setLocation(400,400);
				cframe.setVisible(true);
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		strokebtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try{
			
					
					JFrame pFrame = new JFrame();
					pFrame.setTitle("굵기");
					pFrame.setSize(400,200);
					pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
					// Group 1
					JRadioButton stroke1 = new JRadioButton("굵기1");
					JRadioButton stroke2 = new JRadioButton("굵기2");
					JRadioButton stroke3 = new JRadioButton("굵기3");
					JRadioButton stroke4 = new JRadioButton("굵기4");
					JRadioButton stroke5 = new JRadioButton("굵기5");
					ButtonGroup group1 = new ButtonGroup();
					group1.add(stroke1);
					group1.add(stroke2);
					group1.add(stroke3);
					group1.add(stroke4);
					group1.add(stroke5);

					JPanel panel1 = new JPanel();
					panel1.setLayout(new FlowLayout());
					panel1.add(stroke1);
					panel1.add(stroke2);
					panel1.add(stroke3);
					panel1.add(stroke4);
					panel1.add(stroke5);
					
					stroke3.setSelected(true);
        
					// Confirm and Close buttons
					JButton confirmButton = new JButton("확인");
					JButton closeButton = new JButton("닫기");

					confirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if (stroke1.isSelected()) {
							//System.out.println("굵기1 선택됨");
							penst = 1;
						} else if (stroke2.isSelected()) {
							//System.out.println("굵기2 선택됨");
							penst = 2;
						} else if (stroke3.isSelected()) {
							//System.out.println("굵기3 선택됨");
							penst = 3;
						} else if (stroke4.isSelected()) {
							//System.out.println("굵기4 선택됨");
							penst = 4;
						} else if (stroke5.isSelected()) {
							//System.out.println("굵기5 선택됨");
							penst = 5;
						}
				
				
				
						pFrame.dispose();
					}
				});

				closeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						pFrame.dispose();
					}
				});

				JPanel panel3 = new JPanel();
				panel3.add(confirmButton);
				panel3.add(closeButton);

				pFrame.setLayout(new BoxLayout(pFrame.getContentPane(), BoxLayout.Y_AXIS));
				pFrame.add(panel1);
       
				pFrame.add(panel3);

				pFrame.setLocation(400, 400);
				pFrame.setVisible(true);
				
				
				
				}catch(NullPointerException npe)
				{
			
				}
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		colorButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Color SelectColor;
				SelectColor = JColorChooser.showDialog(null, "색상 선택", Color.BLACK);
				if (SelectColor != null) {
					replacementColor = SelectColor.getRGB();
				}
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		
		settingButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try{
					
					JFrame sFrame = new JFrame();
					sFrame.setTitle("이미지설정");
					sFrame.setSize(300,200);
					sFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					sFrame.setLayout(null);
					JLabel widthLabel = new JLabel("너비 : ");
					widthLabel.setBounds(20,20,50,25);
					sFrame.add(widthLabel);
					JTextField widthText = new JTextField();
					widthText.setBounds(60,20,60,25);
					widthText.setText(Integer.toString(dpwidth));
					sFrame.add(widthText);
					JLabel heightLabel = new JLabel("높이 : ");
					heightLabel.setBounds(130,20,160,25);
					sFrame.add(heightLabel);
					JTextField heightText = new JTextField();
					heightText.setBounds(170,20,60,25);
					heightText.setText(Integer.toString(dpheight));
					sFrame.add(heightText);
		
					JButton okButton = new JButton("확인");
					okButton.setBounds(40,60,60,30);
					sFrame.add(okButton);
					JButton closeButton = new JButton("닫기");
					closeButton.setBounds(120,60,60,30);
					sFrame.add(closeButton);
					okButton.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e)
						{
							try{
								System.out.println("이전너비 : " + dpwidth + " 이전높이 : " + dpheight);
								BufferedImage resizeimage = new BufferedImage(dpwidth,dpheight,BufferedImage.TYPE_INT_ARGB);
								
								Graphics2D g2d = resizeimage.createGraphics();
								g2d.drawImage(image,0,0,dpwidth,dpheight,null);
								
								
								g2d.dispose();
								String Sw,Sh;
								Sw = widthText.getText();
								Sh = heightText.getText();
								dpwidth = Integer.parseInt(Sw);
								dpheight = Integer.parseInt(Sh);
								//frame.dispose();
								//Init();
								drawPanel.setPreferredSize(new Dimension(dpwidth,dpheight));
								drawPanel.setSize(dpwidth,dpheight);
								drawPanel.setBackground(Color.WHITE);
								//drawPanel.setOpaque(true); // 불투명 설정
								frame.revalidate();
								frame.repaint();
								System.out.println("지금너비 : " + dpwidth + " 지금높이 : " + dpheight);
								image = new BufferedImage(dpwidth,dpheight,BufferedImage.TYPE_INT_ARGB);
								Graphics2D g2di = image.createGraphics();
								g2di.setColor(Color.WHITE);
								g2di.fillRect(0,0,dpwidth,dpheight);
								g2di.drawImage(resizeimage,0,0,dpwidth,dpheight,null);
								g2di.dispose();
								sFrame.dispose();
							}catch(NumberFormatException nfe)
							{
								System.out.println("숫자를 눌려주세요");
							}
						}
					});
					closeButton.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e)
						{
					
				
							sFrame.dispose();
						}
					});
					sFrame.setLocation(400,400);
		
					sFrame.setVisible(true);
				}catch(NumberFormatException nfe)
				{
			
				}
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		
		imageOptionButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// JFrame 생성
				String sizestr = String.format("너비 : %d 높이 : %d",dpwidth,dpheight);
				String filenamestr = String.format("파일 : %s",filename);
				String colorstr = String.format("색상 : %X", replacementColor);
				JFrame oframe = new JFrame("이미지정보");
				oframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				oframe.setSize(400, 500); // 창 크기 설정
				oframe.setLayout(new BorderLayout()); // 레이아웃 설정

				// JList 생성 및 아이템 추가
				DefaultListModel<String> listModel = new DefaultListModel<>();
				listModel.addElement(sizestr);
				listModel.addElement(filenamestr);
				listModel.addElement(colorstr);

				JList<String> itemList = new JList<>(listModel);
				JScrollPane scrollPane = new JScrollPane(itemList); // 스크롤 가능하도록

				// 확인 버튼 생성
				JButton confirmButton = new JButton("확인");

				// 컴포넌트 배치
				oframe.add(scrollPane, BorderLayout.CENTER); // JList (스크롤 포함)를 중앙에 배치
				oframe.add(confirmButton, BorderLayout.SOUTH); // 확인 버튼을 아래쪽에 배치
				confirmButton.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e)
						{
					
				
							oframe.dispose();
						}
					});
				// 창 표시
				oframe.setLocation(200,300);
				oframe.setVisible(true);
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		
		markButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(markb) markb = false;
				else if(!markb) markb = true;
				drawPanel.repaint();
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
				System.out.println("markb : " + markb);
			}
		});
		gridButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(gridb) gridb = false;
				else if(!gridb) gridb = true;
				drawPanel.repaint();
				System.out.println("gridb : " + gridb);
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
				/*JFrame gframe = new JFrame("돋보기");
				gframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				gframe.setSize(300, 200);
				gframe.setLayout(new BorderLayout());
				JPanel topPanel = new JPanel();
				topPanel.setLayout(new FlowLayout()); // 컴포넌트 정렬을 위한 FlowLayout 사용
				gframe.add(topPanel, BorderLayout.NORTH);
				JLabel zoomlabel = new JLabel("돋보기 줌 : ");
				topPanel.add(zoomlabel);
				int[] zoom = {1,10,20,30,40,50,60};
				Vector<Integer> zoomVector = new Vector<>();
				// 배열을 Vector로 변환
				for (int value : zoom) {
					zoomVector.add(value);
				}
				JComboBox<Integer> zoomCombo = new JComboBox<>(zoomVector);
				
				topPanel.add(zoomCombo);
				zoomCombo.addActionListener(event->{
					
				});
				
				// 확인 및 취소 버튼 생성
				JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new FlowLayout());
				//buttonPanel.setBounds(0, 100, 500, 100); // buttonPanel의 위치와 크기 설정
				gframe.add(buttonPanel, BorderLayout.SOUTH);
				JButton confirmButton = new JButton("확인");
				JButton cancelButton = new JButton("취소");
				//confirmButton.setBounds(150,20,90,30);
				//cancelButton.setBounds(260,20,90,30);
				buttonPanel.add(confirmButton);
				buttonPanel.add(cancelButton);
				//cframe.add(buttonPanel);
				// 확인 버튼 액션 리스너
				confirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						zoomscale = (int)zoomCombo.getSelectedItem();
						gframe.dispose();
						System.out.println("zoomscale : " + zoomscale);
						drawSelect = 20;
					}
				});
				// 취소 버튼 액션 리스너
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						gframe.dispose();
					}
				});
				gframe.setLocation(400,300);
				gframe.setVisible(true);*/
			}
		});
		centerbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(centerb) centerb = false;
				else if(!centerb) centerb = true;
				drawPanel.repaint();
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		stateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				if(statusbarPanel != null)
				{
					statusbar = statusbarPanel.isVisible();
					statusbarPanel.setVisible(!statusbar);
					frame.revalidate();
					frame.repaint();
					System.out.println("btnstatusbar : " + statusbar);
				}else{
					System.out.println("statusbarPanel이 null입니다.");	
				}
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		getrgbbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				drawSelect = 30;
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		licenseButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				 // 새로운 JFrame 생성
                JFrame licenseFrame = new JFrame("라이센스");
                licenseFrame.setSize(500, 400);
                licenseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                licenseFrame.setLayout(new BorderLayout());

                // TextArea와 스크롤바 추가
                JTextArea licenseTextArea = new JTextArea();
                licenseTextArea.setText("이 앱은 자유앱입니다. 자유앱이며 사용하는것은 자유입니다.\n 만약 이 앱으로 인한 금전적인 손해나 어떤 손해도 책임을 지지 않습니다.\n이 앱에 대한 사용료나 법적인 문제는 이 앱과 전혀 관계가 없습니다.\n감사합니다.");
                licenseTextArea.setLineWrap(true); // 텍스트 줄 바꿈
                licenseTextArea.setWrapStyleWord(true); // 단어 단위로 줄 바꿈
                licenseTextArea.setEditable(false); // 텍스트 수정 불가
				
				// 폰트 변경
				licenseTextArea.setFont(new Font("Serif", Font.PLAIN, 20)); // Serif 폰트, 크기 20
                JScrollPane scrollPane = new JScrollPane(licenseTextArea);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 항상 스크롤바 보이기

                // 확인 버튼 추가
                JButton confirmButton = new JButton("확인");
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        licenseFrame.dispose(); // 창 닫기
                    }
                });

                // 컴포넌트 배치
                licenseFrame.add(scrollPane, BorderLayout.CENTER); // TextArea (스크롤 포함) 중앙
                licenseFrame.add(confirmButton, BorderLayout.SOUTH); // 확인 버튼 아래

                // 창 표시
                licenseFrame.setVisible(true);
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
            
			}
		});
		colorHelpButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// 새로운 JFrame 생성
                JFrame useFrame = new JFrame("사용법");
                useFrame.setSize(500, 400);
                useFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                useFrame.setLayout(new BorderLayout());

                // TextArea와 스크롤바 추가
                JTextArea useTextArea = new JTextArea();
                useTextArea.setText("");
				useTextArea.append("콰라포도프로젝트v0.0021버전 : 새로만들기,qp파일열기,qp파일저장,끝내기,선택,잘라내기,복사하기,붙여놓기,이전,다음,펜,채우기,지우개,문자,색상,이미지설정,이미지정보를 만들었습니다.\n");
				useTextArea.append("v0.0021버전이 초기버전입니다.");
				useTextArea.append("콰라포도프로젝트v0.0023버전 : 중심점,사각형,타원,굵기를 추가하였습니다.\n");
				useTextArea.append("콰라포도프로젝트v0.0024버전 : 직선,rgb값을 추가하였습니다.\n");
				useTextArea.append("콰라포도프로젝트v0.0025버전 : 키보드추가,cpu과작동방지\n");
				useTextArea.append("콰라포도프로젝트v0.0026버전 : 원호추가\n");
                useTextArea.setLineWrap(true); // 텍스트 줄 바꿈
                useTextArea.setWrapStyleWord(true); // 단어 단위로 줄 바꿈
                useTextArea.setEditable(false); // 텍스트 수정 불가
				
				// 폰트 변경
				useTextArea.setFont(new Font("Serif", Font.PLAIN, 20)); // Serif 폰트, 크기 20
                JScrollPane scrollPane = new JScrollPane(useTextArea);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 항상 스크롤바 보이기

                // 확인 버튼 추가
                JButton confirmButton = new JButton("확인");
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        useFrame.dispose(); // 창 닫기
                    }
                });

                // 컴포넌트 배치
                useFrame.add(scrollPane, BorderLayout.CENTER); // TextArea (스크롤 포함) 중앙
                useFrame.add(confirmButton, BorderLayout.SOUTH); // 확인 버튼 아래

                // 창 표시
                useFrame.setVisible(true);
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		supportButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFrame cframe = new JFrame("제품지원");
				cframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				cframe.setSize(500, 500);
				cframe.setLayout(new BoxLayout(cframe.getContentPane(), BoxLayout.Y_AXIS));
				JLabel label1 = new JLabel("제품지원 : 컴퓨터판타지연구소");
				label1.setAlignmentX(JLabel.CENTER_ALIGNMENT);
				cframe.add(label1);
				JLabel label2 = new JLabel("지원언어 : Java21 SE이상");
				label2.setAlignmentX(JLabel.CENTER_ALIGNMENT);
				cframe.add(label2);
				JLabel label3 = new JLabel("하고싶은말 : 열심히 만들었습니다.");
				label3.setAlignmentX(JLabel.CENTER_ALIGNMENT);
				cframe.add(label3);
				JLabel label4 = new JLabel("이 앱을 사랑해주세요.");
				label4.setAlignmentX(JLabel.CENTER_ALIGNMENT);
				cframe.add(label4);
				// 확인 버튼 생성
				JButton confirmButton = new JButton("확인");
				confirmButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
				cframe.add(confirmButton);

				// 확인 버튼 동작: 창 종료
				confirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cframe.dispose();
					}
				});
				cframe.setVisible(true);
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		inforButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFrame cframe = new JFrame("APP 정보");
				cframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				cframe.setSize(500, 500);
				cframe.setLayout(new BoxLayout(cframe.getContentPane(), BoxLayout.Y_AXIS));
				JLabel label1 = new JLabel("제품명 : 콰라포도프로젝트v0.0026");
				label1.setAlignmentX(JLabel.CENTER_ALIGNMENT);
				cframe.add(label1);
				JLabel label2 = new JLabel("버전 : 미완성버전 v0.0026");
				label2.setAlignmentX(JLabel.CENTER_ALIGNMENT);
				cframe.add(label2);
				JLabel label3 = new JLabel("만든이 : 컴퓨터판타지연구소");
				label3.setAlignmentX(JLabel.CENTER_ALIGNMENT);
				cframe.add(label3);
				JLabel label4 = new JLabel("저작권은 컴퓨터판타지연구소에 있습니다.");
				label4.setAlignmentX(JLabel.CENTER_ALIGNMENT);
				cframe.add(label4);
				// 확인 버튼 생성
				JButton confirmButton = new JButton("확인");
				confirmButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
				cframe.add(confirmButton);

				// 확인 버튼 동작: 창 종료
				confirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cframe.dispose();
					}
				});
				cframe.setVisible(true);
				drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정
			}
		});
		
		workPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		workPanel.setPreferredSize(new Dimension(1280,1024));
		workPanel.setBackground(Color.GRAY);
		
		drawPanel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.WHITE); // 배경 색상 설정
				g.fillRect(0, 0, dpwidth, dpheight); // 전체 영역을 덮음
				Timer timer = new Timer(400,e->qpp.drawPaint());
				timer.start();
				g.drawImage(image,0,0,null);
				
				if(markb)
				{
					g.setColor(Color.BLACK);
					for (int x = 20; x < dpwidth + 20; x += 20) {
						g.drawLine(x, 0, x, 20); // 짧은 세로선
						//g.drawString(String.valueOf(x - 50), x - 10, 30); // 숫자 표시
					}
					// 왼쪽 눈금
					for (int y = 20; y < dpheight + 20; y += 20) {
						g.drawLine(0, y, 20, y); // 짧은 가로선
						//g.drawString(String.valueOf(y - 50), 10, y + 5); // 숫자 표시
					}
				}else {
					//g.setColor(Color.WHITE); // 배경 색상 설정
					//g.fillRect(0, 0, dpwidth, dpheight); // 전체 영역을 덮음
				}
				if(gridb)
				{
					g.setColor(Color.BLACK);
					for (int x = 0; x < dpwidth; x += 10) { // 세로선
						g.drawLine(x, 0, x, dpheight);
					}
					for (int y = 0; y < dpheight; y += 10) { // 가로선
						g.drawLine(0, y, dpwidth, y);
					}
				}else{
					//g.setColor(Color.WHITE); // 배경 색상 설정
					//g.fillRect(0, 0, dpwidth, dpheight); // 전체 영역을 덮음
				}
				if(centerb)
				{
					g.setColor(Color.RED);
					g.fillOval(dpwidth/2-5,dpheight/2-5,10,10);
				}else{
					
				}
				if(rectb)
				{
					g.setColor(Color.BLACK);
					g.drawRect(spoint.x,spoint.y,point.x-spoint.x,point.y-spoint.y);
				}else if(circleb)
				{
					//System.out.println("circleb가 왔어요");
					g.setColor(Color.BLACK);
					g.drawOval(spoint.x,spoint.y,point.x-spoint.x,point.y-spoint.y);
				}else if(lineb)
				{
					//System.out.println("lineb가 왔어요");
					g.setColor(Color.BLACK);
					g.drawLine(spoint.x,spoint.y,point.x,point.y);
				}else if(arcb)
				{
					
					g.setColor(Color.BLACK);
					g.drawRect(arcx,arcy,arcwidth,archeight);
					if(arcmode)
					{
						
					}
				}
					
				
				
				
				
				
			}
		};
		drawPanel.setPreferredSize(new Dimension(dpwidth,dpheight));
		drawPanel.setSize(dpwidth,dpheight);
		drawPanel.setBackground(Color.WHITE);
		
		JScrollPane scrollPane = new JScrollPane(workPanel);

        // 스크롤 방향 설정 (필요에 따라 조정 가능)
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		workPanel.add(drawPanel,BorderLayout.WEST);
		frame.add(scrollPane,BorderLayout.WEST);
		
		
		
		frame.setVisible(true);
		drawPanel.addMouseListener(new MouseListener(){
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
				switch(drawSelect)//0:pen 1:select 3:cut 4:copy 5:paste 6:pre 7:next 8:fill 9:erase 10:string
				{
					case 0:
						saveUndoState();
						currentPath = null;
						currentPath = new Path2D.Double(); // 새로운 경로 생성
						currentPath.reset();
						currentPath.moveTo(e.getX(), e.getY());
					break;
					case 1:
						selectUndo();
						selectUndoState();
						selectPath = null;
						selectPath = new Path2D.Double();
						selectPath.reset();
						selectPath.moveTo(e.getX(), e.getY());
					break;
					case 2:
					
					break;
					case 3:
					
					break;
					case 4:
						
					break;
					case 5:
					
					break;
					case 6:
					
					break;
					case 7:
					
					break;
					case 8:
					
					break;
					case 9:
						saveUndoState();
						currentPath = null;
						currentPath = new Path2D.Double(); // 새로운 경로 생성
						currentPath.reset();
						currentPath.moveTo(e.getX(), e.getY());
					break;
					case 19:
						
					break;
					case 20:
					saveUndoState();
					spoint = new Point();
					spoint.x = e.getX();
					spoint.y = e.getY();
					point = new Point();
					point.x = e.getX();
					point.y = e.getY();
					rectb = true;	
					break;
					
					case 21:
					saveUndoState();
					spoint = new Point();
					spoint.x = e.getX();
					spoint.y = e.getY();
					point = new Point();
					point.x = e.getX();
					point.y = e.getY();
					circleb = true;		
					break;
					
					case 22:
					saveUndoState();
					spoint = new Point();
					spoint.x = e.getX();
					spoint.y = e.getY();
					point = new Point();
					point.x = e.getX();
					point.y = e.getY();
					lineb = true;
					break;
					case 26:
						if(!arcmode && !arcsec)
						{
							saveUndoState();
							spoint = new Point(e.getX(), e.getY()); // 드래그 시작점
							point = new Point(e.getX(), e.getY());  // 현재 위치

							arcmode = false;
							arcb = true;
						}	
					break;
					default:
						
					break;
					
				}
			}
		}

    
		@Override
		public void mouseReleased(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				Graphics2D g2d = image.createGraphics();
				switch(drawSelect)//0:pen 1:select 3:cut 4:copy 5:paste 6:pre 7:next 8:fill 9:erase 10:string
				{
					case 0:
						if(currentPath != null)
						{
							paths.add((Path2D) currentPath.clone());
						  // 새로운 경로를 추가할 때는 채워지지 않도록 설정
							currentPath = null; // 경로를 null로 재설정
						}	
					break;
					case 1:
						if(selectPath != null)
					{	
						selpaths.add((Path2D) selectPath.clone());
						selectPath = null;
						
						drawPanel.repaint();
						System.out.println("test마우스떼기.");
					}
					break;
					case 2:
					
					break;
					case 3:
					
					break;
					case 4:
						
					break;
					case 5:
					
					break;
					case 6:
					
					break;
					case 7:
					
					break;
					case 8:
					
					break;
					case 9:
						if(currentPath != null)
						{
							paths.add((Path2D) currentPath.clone());
						  // 새로운 경로를 추가할 때는 채워지지 않도록 설정
							currentPath = null; // 경로를 null로 재설정
						}	
					break;
					case 19:
						
					break;	
					case 20:
						point = new Point();
						point.x = e.getX();
						point.y = e.getY();
						
						g2d.setStroke(new BasicStroke(penst));
						g2d.setColor(new Color(replacementColor));
						g2d.drawRect(spoint.x,spoint.y,point.x-spoint.x,point.y-spoint.y);
						//g2d.fillOval(20,20,40,40);
						rectb = false;
						//circleb = false;
					break;
					case 21:
						point = new Point();
						point.x = e.getX();
						point.y = e.getY();
						
						g2d.setStroke(new BasicStroke(penst));
						g2d.setColor(new Color(replacementColor));
						g2d.drawOval(spoint.x,spoint.y,point.x-spoint.x,point.y-spoint.y);
						//g2d.fillOval(20,20,40,40);
						//rectb = false;
						circleb = false;
					break;
					case 22:
						point = new Point();
						point.x = e.getX();
						point.y = e.getY();
						
						g2d.setStroke(new BasicStroke(penst));
						g2d.setColor(new Color(replacementColor));
						g2d.drawLine(spoint.x,spoint.y,point.x,point.y);
						//g2d.fillOval(20,20,40,40);
						//rectb = false;
						lineb = false;
					break;
					case 26:
						if(!arcmode && !arcsec)
						{
							point = new Point();
							point.x = e.getX();
							point.y = e.getY();
							arcx = Math.min(spoint.x, point.x);
							arcy = Math.min(spoint.y, point.y);
							arcwidth = Math.abs(spoint.x - point.x);
							archeight = Math.abs(spoint.y - point.y);

						
							arcb = true;
							arcmode = true;
						}
					break;
					default:
					break;
					
				}
				g2d.dispose();
			
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		
			if (SwingUtilities.isLeftMouseButton(e)) {
			
				switch(drawSelect)//0:pen 1:select 3:cut 4:copy 5:paste 6:pre 7:next 8:fill 9:erase 10:string
				{
					case 0:
					
					break;
					case 1:
					
					break;
					case 2:
					
					break;
					case 3:
					
					case 4:
						
					break;
					case 5:
						restoreShapeRGB(selpaths.get(selpaths.size()-1),savergb,e.getX(),e.getY());
						saveUndoState();
						drawPanel.repaint();
					break;
					case 6:
					
					break;
					case 7:
					
					break;
					case 8:
						saveUndoState();
						System.out.println("마우스클릭");
						mx = e.getX(); my = e.getY();
						int targetColor = image.getRGB(mx, my);
						floodFill(image, mx, my, targetColor, replacementColor);
						
						drawPanel.repaint();
					break;
					case 9:
					
					break;
					case 19:
						saveUndoState();
						point = new Point();
						clicked = true;
						point.x = e.getX();
						point.y = e.getY();
						System.out.println("x:"+point.x+"y:"+point.y);
						drawPanel.repaint();
					break;	
					case 20:
						
					break;
					
					case 21:
						
					break;
					
					case 22:
						
					break;
					case 26:
						if(arcb && arcmode)
						{
							point = new Point(e.getX(),e.getY());
							if(!arcsec)
							{
								int centerX = arcx + arcwidth / 2;
								int centerY = arcy + archeight / 2;

								startarc = calculateAngle(point.x, point.y, centerX, centerY);
								arcsec = true;
							}else
							{
								int centerX = arcx + arcwidth / 2;
								int centerY = arcy + archeight / 2;

								endarc = calculateAngle(point.x, point.y, centerX, centerY);
								int anglearc = -((startarc - endarc + 360)%360);
								Graphics2D g2d2 = image.createGraphics();
								g2d2.setColor(new Color(replacementColor));
								g2d2.setStroke(new BasicStroke(penst));
								g2d2.drawArc(arcx,arcy,arcwidth,archeight,startarc,anglearc);
								g2d2.dispose();
								arcsec = false;
								arcmode = false;
								arcb = false;
							}
							
						}
					break;
					case 30:
					String temp = "";
					int rgb = image.getRGB(e.getX(),e.getY());
					if(rgb ==-0) rgb = 0xffffffff;
					temp = "x : " + Integer.toString(e.getX()) + " y : " + Integer.toString(e.getY()) + " RGB : " + Integer.toHexString(rgb);
					JOptionPane.showMessageDialog(null, temp, "rgb값", JOptionPane.INFORMATION_MESSAGE);

					break;
					default:
					break;
					
				}
			
			}
		
        
        
		}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}
		
			
			
		});
		drawPanel.addMouseMotionListener(new MouseMotionListener(){
			
			@Override
			public void mouseDragged(MouseEvent e) {
        
				switch(drawSelect)//0:pen 1:select 3:cut 4:copy 5:paste 6:pre 7:next 8:fill 9:erase 10:string
				{
					case 0:
						if (currentPath != null) {
							currentPath.lineTo(e.getX(), e.getY());
							qpp.drawPaint();
							drawPanel.repaint();
						}
					
					break;
					case 1:
						if (selectPath != null)
						{
							selectPath.lineTo(e.getX(),e.getY());
							qpp.drawPaint();
							drawPanel.repaint();
						}
					break;
					case 2:
					
					break;
					case 3:
					
					break;
					case 4:
						
					break;
					case 5:
					
					break;
					case 6:
					
					break;
					case 7:
					
					break;
					case 8:
					
					break;
					case 9:
						if (currentPath != null) {
							currentPath.lineTo(e.getX(), e.getY());
							qpp.drawPaint();
							drawPanel.repaint();
						}
					break;
					case 19:
						
					break;	
					case 20:
					if(rectb)
					{
						point = new Point();
						point.x = e.getX();
						point.y = e.getY();
						drawPanel.repaint();
					}
					break;
					
					case 21:
					if(circleb)
					{
						point = new Point();
						point.x = e.getX();
						point.y = e.getY();
						drawPanel.repaint();
					}	
					break;
					case 22:
					if(lineb)
					{
						point = new Point();
						point.x = e.getX();
						point.y = e.getY();
						drawPanel.repaint();
					}	
					break;
					case 26:
					if(arcb)
					{	
						if(!arcmode && !arcsec)
						{
							point = new Point();
							point.x = e.getX();
							point.y = e.getY();
							arcx = Math.min(spoint.x, point.x);
							arcy = Math.min(spoint.y, point.y);
							arcwidth = Math.abs(spoint.x - point.x);
							archeight = Math.abs(spoint.y - point.y);
							drawPanel.repaint();
						}
					}
					break;
					default:
					break;
					
				}
		
		}

			@Override
			public void mouseMoved(MouseEvent e) {
				statusp.x = e.getX();
				statusp.y = e.getY();
				String statusText = String.format("상태표시줄입니다. 너비 : %d 높이 : %d 마우스X : %d 마우스Y : %d",dpwidth,dpheight,statusp.x,statusp.y);
				statusLabel.setText(statusText);
				frame.revalidate();
				frame.repaint();
				//System.out.println("x : " +statusp.x + " y : " + statusp.y);
			}		
			
		});
		//testPaint();
		drawPanel.setFocusable(true); // 🔥 키 입력을 받을 수 있도록 설정
        drawPanel.requestFocusInWindow(); // 🔥 창이 열릴 때 바로 포커스 받도록 설정

		drawPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("키 입력됨: " + e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("키 눌림: " + e.getKeyCode());
				switch(e.getKeyCode())
				{
					case 90://z이전
					tmpsel = drawSelect;
					drawSelect = 6;
					qpp.drawPaint();	
					break;
					
					case 88://x다음
					tmpsel = drawSelect;
					drawSelect = 7;
					qpp.drawPaint();	
					break;
					
					case 49://1펜
					drawSelect = 0;	
					break;
					
					case 50://2사각형
					drawSelect = 20;		
					break;
					
					case 51://3타원
					drawSelect = 21;		
					break;
					
					case 52://4직선
					drawSelect = 22;		
					break;
					
					case 53://5원호
					//drawselect = 9;		
					break;
					
					case 54://6문자
					drawSelect = 19;		
					break;
					
					case 55://7채우기
					drawSelect = 8;	
					System.out.println("채우기누름");
					break;
					case 56://8지우개
					drawSelect = 9;		
					break;
					case 57://9원호
					drawSelect = 26;		
					break;
					case 81://q굵기
					if(penst==5) penst = 1;
					else penst++;
					break;
					
					case 87://w색상
					Color SelectColor;
					SelectColor = JColorChooser.showDialog(null, "색상 선택", Color.BLACK);
					if (SelectColor != null) {
						replacementColor = SelectColor.getRGB();
					}	
					break;
					
					case 67://c눈금
					if(markb) markb = false;
					else if(!markb) markb = true;
					drawPanel.repaint();	
					break;
					
					case 86://v격자
					if(gridb) gridb = false;
					else if(!gridb) gridb = true;
					drawPanel.repaint();	
					break;
					case 66://b중심점
					if(centerb) centerb = false;
					else if(!centerb) centerb = true;
					drawPanel.repaint();	
					break;
					
					default:
						
					break;
				}
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("키에서 손을 뗌: " + e.getKeyCode());
            }
        });
		
	}
	//여기서부터 나의 함수
	public void testPaint()
	{
		Graphics g = image.getGraphics();
		g.setColor(Color.RED);
		g.fillRect(50,50,500,500);
		g.dispose();
		drawPanel.repaint();
	}
	private void floodFill(BufferedImage image, int x, int y, int targetColor, int replacementColor) {
        if (targetColor == replacementColor) return;
        int width = image.getWidth();
        int height = image.getHeight();

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(x, y));

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int currentX = p.x;
            int currentY = p.y;

            if (currentX < 0 || currentX >= width || currentY < 0 || currentY >= height) continue;
            if (image.getRGB(currentX, currentY) != targetColor) continue;

            image.setRGB(currentX, currentY, replacementColor);

            queue.add(new Point(currentX + 1, currentY));
            queue.add(new Point(currentX - 1, currentY));
            queue.add(new Point(currentX, currentY + 1));
            queue.add(new Point(currentX, currentY - 1));
        }
    }
	
	public List<Integer> saveShapeRGB(Path2D shape) {
	     List<Integer> rgbList = new ArrayList<>();
		    if (shape != null && image != null) {
			    Rectangle bounds = shape.getBounds();
				for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
					for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
						if (shape.contains(x, y)) {
							int rgb = image.getRGB(x, y);
							rgbList.add(rgb);
		                }
			        }
				}
	        }
		    return rgbList;
    }
	public void restoreShapeRGB(Path2D shape, List<Integer> rgbList,int mx,int my) {
		if (shape != null && image != null && rgbList != null) {
			Rectangle bounds = shape.getBounds();
			
			// 중심 좌표 계산
			int centerX = bounds.x + (bounds.width / 2);
			int centerY = bounds.y + (bounds.height / 2);
			// mx, my를 중심을 기준으로 조정
			//mx -= centerX;
			//my -= centerY;
			
			int index = 0;
			for (int x = mx; x < mx + bounds.width; x++) {
				for (int y = my; y < my + bounds.height; y++) {
					if (shape.contains((x-mx)+bounds.x, (y-my)+bounds.y) && index < rgbList.size()) {
                   
						image.setRGB(x, y, rgbList.get(index));
						index++;
					}
				}
			}
			drawPanel.repaint();
		}else
		{
			if(shape == null) System.out.println("shape가 null입니다.");
			if(image == null) System.out.println("image가 null입니다.");
			if(rgbList == null) System.out.println("rgbList가 null입니다.");
		}
	}
	public void saveUndoState() {
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics g = copy.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        undoStack.push(copy);
        redoStack.clear(); // 새로운 작업을 하면 redo 스택을 비웁니다.
		System.out.println("Undo Stack Size: " + undoStack.size());
    }
	public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(image);
            image = undoStack.pop();
	System.out.println("Undo Stack Popped: New Size: " + undoStack.size());		
            drawPanel.repaint();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(image);
            image = redoStack.pop();
			//System.out.println("redostack pop했습니다.");
            drawPanel.repaint();
        }
    } 
	public void selectUndoState()
	{
			BufferedImage selcopy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
			Graphics g = selcopy.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			selectundoStack.push(selcopy);
			tempStack.clear();
		System.out.println("selectUndo Stack Size: " + selectundoStack.size());
	}
	public void selectUndo()
	{
		if(!selectundoStack.isEmpty()) 
		{
			tempStack.push(image);
			image = selectundoStack.pop();
			drawPanel.repaint();
			System.out.println("Undo Stack Popped: New Size: " + undoStack.size());	
		}
	}
	
	public void drawPaint()
	{
		//BufferedImage copy = new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
		Graphics2D g2d = image.createGraphics();
		//g2d.drawImage(image,0,0,null);
		switch(drawSelect)//0:pen 1:select 3:cut 4:copy 5:paste 6:pre 7:next 8:fill 9:erase 10:string
		{
			case 0:
				if(currentPath != null)
				{
					g2d.setStroke(new BasicStroke(penst));
					g2d.setColor(new Color(replacementColor));
					g2d.draw(currentPath);
					
				}
			break;
			
			case 1:
				if(selectPath != null)
				{
					g2d.setStroke(new BasicStroke(3));
					g2d.setColor(Color.BLUE);
					g2d.draw(selectPath);
				}
			break;
			
			case 2:
				
				
			break;
			
			case 3:
				if(cutbool)
				{
					selectUndo();
					savergb = saveShapeRGB(selpaths.get(selpaths.size()-1));
					cutbool = false;
					
					System.out.println("case3들어왔습니다.");
				}
				g2d.setColor(Color.WHITE);
				g2d.fill(selpaths.get(selpaths.size()-1));
				drawPanel.repaint();
			break;
			
			case 4:
				selectUndo();
				savergb = saveShapeRGB(selpaths.get(selpaths.size()-1));
				drawPanel.repaint();
			break;
				
			case 5:
				
			break;
			
			case 6:
				undo();
				drawSelect = tmpsel;
			break;
			
			case 7:
				redo();
				drawSelect = tmpsel;
			break;
			
			case 8:
				
			break;
			
			case 9:
				if(currentPath != null)
				{
					g2d.setStroke(new BasicStroke(5));
					g2d.setColor(Color.WHITE);
					g2d.draw(currentPath);
				}
			break;
			
			case 19:
				//여기까지 실행되는건 맞음...그런데 draw가 여기서 안됨 무슨 이유때문이지?
				//System.out.println("drawselect : " + drawSelect);
				if(clicked == true)
				{
					if(replacementColor == -0x1000000) replacementColor = 0;
					g2d.setColor(new Color(replacementColor));
				
					Font font = new Font(shapestr,Font.BOLD,fontsize);
					g2d.setFont(font);
					if(dpstr != null && point != null) g2d.drawString(dpstr,point.x,point.y);
					System.out.println("Color : " + replacementColor + " Shapestr : " + shapestr + " size : " + fontsize);
					//drawPanel.repaint();
					clicked = false;
				}
					
					
					
				
			break;
			case 20:
				
						
			break;
			default:
				
			break;
		}
		g2d.dispose();
		//image = copy;
		drawPanel.repaint();
	}
	
	//마우스이벤트
	
	public int calculateAngle(int px, int py, int cx, int cy) {
		double dx = px - cx;
		double dy = cy - py; // y축 반전 (그래픽 좌표계는 아래로 증가)
		double angleRad = Math.atan2(dy, dx);
		int angleDeg = (int) Math.toDegrees(angleRad);
		return (angleDeg + 360) % 360; // 0~359 범위로 정규화
	}
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run()
			{
				qpp = new QPPv0_0026();
				qpp.Init();
				qpp.frame.revalidate();
				qpp.frame.repaint();
			}
		});
			System.out.println("Hello World!");
	}
}
