import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.nio.*;
import java.nio.channels.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;

import net.ifok.image.image4j.codec.ico.*;

import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import Version.Version;
class AntICOFileMaker
{
	public static AntICOFileMaker icom;
	public static JFrame frame;
	public static JPanel outpanel;
	public static JPanel drawpanel;
	public static JPanel rightpanel;
	public static JButton iconewbtn,icosavebtn,icoopenbtn,undobtn,redobtn,penbtn,rectbtn,circlebtn,fillbtn,strbtn,tpbtn,erasebtn,strokebtn,colorbtn,markbtn,gridbtn,centerbtn,inforbtn,exitbtn;
	public static JButton arcbtn,imageopenbtn;
	public static BufferedImage image;
	public static int drawselect = 0;
	
	
	//draw정의
	private Version ver = new Version();
	private Path2D currentPath;
	private ArrayList<Path2D> paths;
	public int mx,my;
	private int prevx,prevy;
	public int rgbget;
	public static int replacementColor = Color.BLACK.getRGB();
	private int penst = 3;
	public String dpstr="",shapestr="";
	public int fontsize=0;
	public Point point,spoint;
	//undo정의
	private Stack<BufferedImage> undoStack = new Stack<>();
	private Stack<BufferedImage> redoStack = new Stack<>();
	//눈금,격자,중심점
	private boolean markb = false,gridb = false, centerb = false;
	private boolean rectb = false,circleb = false,arcb = false;
	private int arcx=0,arcy=0,arcwidth=0,archeight=0;
	private boolean arcmode = false,arcsec = false;
	private int startarc=0,endarc=0;
	public static Point statusp;
	
	//currentDir
	public static String currentDir = "";
	//0.0023추가
	public Color tpColor = new Color(0,0,0);
	public void Init()
	{
		//그리기초기화
		image = new BufferedImage(256,256,BufferedImage.TYPE_INT_ARGB);
		paths = new ArrayList<>();
		
		frame = new JFrame("앤트 ICO파일생성기v" + ver.app_Version);
		frame.setSize(800,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 아이콘 설정
		ImageIcon icon = new ImageIcon("res/icon.png");
		frame.setIconImage(icon.getImage());
		//상태표시줄
		statusp = new Point();
		statusp.x = 0;
		statusp.y = 0;
		JPanel statusbarPanel = new JPanel(new BorderLayout());
		JLabel statusLabel = new JLabel("상태 표시줄 메시지");
		
		statusLabel.setFont(new Font("Serif", Font.BOLD, 20)); // 크기 30의 폰트 설정statusbarPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		statusbarPanel.add(statusLabel, BorderLayout.WEST);
		String statusText = String.format("상태표시줄입니다. 너비 : %d 높이 : %d 마우스X : %d 마우스Y : %d",256,256,statusp.x,statusp.y);
		statusLabel.setText(statusText);
		// 프레임의 하단에 추가
		frame.add(statusbarPanel, BorderLayout.SOUTH);
		rightpanel = new JPanel();
		rightpanel.setLayout(new BoxLayout(rightpanel,BoxLayout.Y_AXIS));
		
		iconewbtn = new JButton("새로만들기");
		iconewbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				int response = JOptionPane.showConfirmDialog(
					null,
					"새로 만드시겠습니까?",
					"확인",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
				
				// 사용자의 선택에 따라 동작 설정
				if (response == JOptionPane.YES_OPTION) {
					// "확인" 버튼이 눌렸을 때 실행될 코드
					System.out.println("새로 만들기 코드 실행 중...");
					// 여기에 실제 실행할 작업의 코드를 추가하세요
					Graphics g = image.getGraphics();
					g.setColor(Color.WHITE);
					g.fillRect(0,0,256,256);
					g.dispose();
				} else {
					// "취소" 버튼이 눌렸을 때
					System.out.println("작업이 취소되었습니다.");
				}
				drawpanel.repaint();
			}
			
		});
		imageopenbtn = new JButton("image파일열기");
		imageopenbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					int returnvalue;
					File selectedFile= null;
					JFileChooser filechooser = new JFileChooser();
					// 파일 필터 추가 (.qp 파일만 보이게 하기)
					FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("이미지 파일 (jpeg, jpg, png, bmp, gif)", "jpeg", "jpg", "png", "bmp", "gif");
					
					filechooser.setFileFilter(imageFilter);
					try (BufferedReader reader = new BufferedReader(new FileReader("currentDir.txt"))) {
						currentDir = reader.readLine(); // 첫 줄만 읽음
						System.out.println("불러온 currentDir: " + currentDir);
					} catch (IOException ioe) {
						System.out.println("파일 읽기 중 오류 발생: " + ioe.getMessage());
					}
					// currentDir이 null이거나 빈 문자열이면 → 현재 앱 디렉토리로 설정
					if (currentDir == null || currentDir.trim().isEmpty()) {
						currentDir = new File(".").getAbsolutePath();
						System.out.println("currentDir이 비어있어 기본 디렉토리로 설정됨: " + currentDir);
					}
					// 초기 디렉토리 설정 (현재 디렉토리)
					filechooser.setCurrentDirectory(new File(currentDir));
					returnvalue = filechooser.showOpenDialog(frame);
					if(returnvalue == JFileChooser.APPROVE_OPTION)
					{
						selectedFile = filechooser.getSelectedFile();
						currentDir = selectedFile.getParent();
						try {
							FileWriter writer = new FileWriter("currentDir.txt");
							writer.write(currentDir);
							writer.close();
							System.out.println("currentDir.txt에 저장 완료!");
						} catch (IOException ioe) {
							System.out.println("파일 저장 중 오류 발생: " + ioe.getMessage());
						}
						System.out.println("선택한 파일 : " + selectedFile.getPath());
					}else
					{
						System.out.println("선택파일 취소됨");
					}
					if(selectedFile != null)
					{
						String filePath = selectedFile.getPath();
						// 원본 이미지 읽기
						BufferedImage originalImage = ImageIO.read(new File(filePath));
						// 임시 패널 생성 및 원본 이미지 그리기
						JPanel tmppanel = new JPanel() {
							@Override
							protected void paintComponent(Graphics g) {
								super.paintComponent(g);
								g.drawImage(originalImage, 0, 0, getWidth(), getHeight(), null);
							}
						};
						tmppanel.setSize(256, 256); // 패널 크기 설정
						
						
						Graphics2D g2d = image.createGraphics();
						
						// tmppanel을 BufferedImage에 그리기
						tmppanel.paint(g2d);
						g2d.dispose();
						
						// 이제 image는 200x200 크기로 리사이즈된 이미지!
						// 필요하면 저장도 가능:
						// ImageIO.write(image, "png", new File("resized.png"));
					}
					
					
					
					
				}catch(IOException ioe)
				{
					
				}
				drawpanel.repaint();
			}
			
		});
		icoopenbtn = new JButton("ico파일열기");
		icoopenbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					int returnvalue;
					File selectedFile= null;
					JFileChooser filechooser = new JFileChooser();
					// 파일 필터 추가 (.qp 파일만 보이게 하기)
					FileNameExtensionFilter filter = new FileNameExtensionFilter("ICO 파일", "ico");
					filechooser.setFileFilter(filter);
					// 초기 디렉토리 설정 (현재 디렉토리)
					try (BufferedReader reader = new BufferedReader(new FileReader("currentDir.txt"))) {
						currentDir = reader.readLine(); // 첫 줄만 읽음
						System.out.println("불러온 currentDir: " + currentDir);
					} catch (IOException ioe) {
						System.out.println("파일 읽기 중 오류 발생: " + ioe.getMessage());
					}
					// currentDir이 null이거나 빈 문자열이면 → 현재 앱 디렉토리로 설정
					if (currentDir == null || currentDir.trim().isEmpty()) {
						currentDir = new File(".").getAbsolutePath();
						System.out.println("currentDir이 비어있어 기본 디렉토리로 설정됨: " + currentDir);
					}
					filechooser.setCurrentDirectory(new File(currentDir));
					returnvalue = filechooser.showOpenDialog(frame);
					if(returnvalue == JFileChooser.APPROVE_OPTION)
					{
						selectedFile = filechooser.getSelectedFile();
						currentDir = selectedFile.getParent();
						try {
							FileWriter writer = new FileWriter("currentDir.txt");
							writer.write(currentDir);
							writer.close();
							System.out.println("currentDir.txt에 저장 완료!");
						} catch (IOException ioe) {
							System.out.println("파일 저장 중 오류 발생: " + ioe.getMessage());
						}
						System.out.println("선택한 파일 : " + selectedFile.getPath());
					}else
					{
						System.out.println("선택파일 취소됨");
					}
					if(selectedFile != null)
					{
						String filePath = selectedFile.getPath();
						
						if(!filePath.endsWith(".ico"))
						{
							filePath += ".ico";
						}
						File icoFile = new File(filePath);
						image = ICODecoder.read(icoFile).get(0); // ICO 파일의 첫 번째 이미지 읽기
						System.out.println("ICO 이미지 크기: " + image.getWidth() + "x" + image.getHeight());
					}
				}catch(IOException ioe)
				{
					
				}
				drawpanel.repaint();
			}
		});
		icosavebtn = new JButton("ico파일저장");
		icosavebtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					int returnvalue;
					File selectedFile= null;
					JFileChooser filechooser = new JFileChooser();
					// 파일 필터 추가 (.qp 파일만 보이게 하기)
					FileNameExtensionFilter filter = new FileNameExtensionFilter("ICO 파일", "ico");
					filechooser.setFileFilter(filter);
					// 초기 디렉토리 설정 (현재 디렉토리)
					try (BufferedReader reader = new BufferedReader(new FileReader("currentDir.txt"))) {
						currentDir = reader.readLine(); // 첫 줄만 읽음
						System.out.println("불러온 currentDir: " + currentDir);
					} catch (IOException ioe) {
						System.out.println("파일 읽기 중 오류 발생: " + ioe.getMessage());
					}
					// currentDir이 null이거나 빈 문자열이면 → 현재 앱 디렉토리로 설정
					if (currentDir == null || currentDir.trim().isEmpty()) {
						currentDir = new File(".").getAbsolutePath();
						System.out.println("currentDir이 비어있어 기본 디렉토리로 설정됨: " + currentDir);
					}
					filechooser.setCurrentDirectory(new File(currentDir));
					returnvalue = filechooser.showSaveDialog(frame);
					if(returnvalue == JFileChooser.APPROVE_OPTION)
					{
						selectedFile = filechooser.getSelectedFile();
						System.out.println("선택한 파일 : " + selectedFile.getPath());
						currentDir = selectedFile.getParent();
						try {
							FileWriter writer = new FileWriter("currentDir.txt");
							writer.write(currentDir);
							writer.close();
							System.out.println("currentDir.txt에 저장 완료!");
						} catch (IOException ioe) {
							System.out.println("파일 저장 중 오류 발생: " + ioe.getMessage());
						}
					}else
					{
						System.out.println("선택파일 취소됨");
					}
					if(selectedFile != null)
					{
						String filePath = selectedFile.getPath();
						
						if(!filePath.endsWith(".ico"))
						{
							filePath += ".ico";
						}
						File outputIcoFile = new File(filePath);
						
						ICOEncoder.write(Arrays.asList(image), outputIcoFile);
						
						System.out.println("ICO 파일 저장 완료: " + outputIcoFile.getAbsolutePath());
						
					}
				}catch(IOException ioe)
				{
					
				}
			}
		});
		undobtn = new JButton("이전");
		undobtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				drawselect = 7;
				icom.drawPaint();
			}
		});
		redobtn = new JButton("다음");
		redobtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				drawselect = 8;
				icom.drawPaint();
			}
		});
		penbtn = new JButton("펜");
		penbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				drawselect = 0;
			}
		});
		rectbtn = new JButton("사각형");
		rectbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				drawselect = 1;
			}
		});
		circlebtn = new JButton("타원");
		circlebtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				drawselect = 2;
			}
		});
		arcbtn = new JButton("원호");
		arcbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				drawselect = 9;
			}
		});
		strbtn = new JButton("문자");
		strbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
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
							drawselect = 3;
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
				
			}
		});
		
		fillbtn = new JButton("채우기");
		fillbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				drawselect = 4;
			}
		});
		//3문자 4채우기 5 지우개
		erasebtn = new JButton("지우개");
		erasebtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				drawselect = 5;
			}
		});
		strokebtn = new JButton("굵기");
		strokebtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				try{
					
					
					JFrame pFrame = new JFrame();
					pFrame.setTitle("펜");
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
							//drawSelect = 0;
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
			}
		});
		colorbtn = new JButton("색상");
		colorbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				Color SelectColor;
				SelectColor = JColorChooser.showDialog(null, "색상 선택", Color.BLACK);
				if (SelectColor != null) {
					replacementColor = SelectColor.getRGB();
				}
			}
		});
		tpbtn = new JButton("투명색");
		tpbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				drawselect = 10;
			}
			
		});
		markbtn = new JButton("눈금");
		markbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				if(markb) markb = false;
				else if(!markb) markb = true;
				drawpanel.repaint();
			}
		});
		gridbtn = new JButton("격자");
		gridbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				if(gridb) gridb = false;
				else if(!gridb) gridb = true;
				drawpanel.repaint();
			}
		});
		centerbtn = new JButton("중심점");
		centerbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				if(centerb) centerb = false;
				else if(!centerb) centerb = true;
				drawpanel.repaint();
			}
		});
		inforbtn = new JButton("제품정보");
		inforbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				// 새로운 JFrame 생성
				JFrame useFrame = new JFrame("제품정보");
				useFrame.setSize(500, 400);
				useFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				useFrame.setLayout(new BorderLayout());
				
				// TextArea와 스크롤바 추가
				JTextArea useTextArea = new JTextArea();
				useTextArea.setText("");
				useTextArea.append("제품명 : 앤트ICO파일생성기\n");
				useTextArea.append("app_Name : " + ver.app_Name + "\n");
				useTextArea.append("app_Version : " + ver.app_Version + "\n");
				useTextArea.append("app_Code : " + ver.app_Code + "\n");
				useTextArea.append("오랜만에 업데이트하였습니다.\n");
				useTextArea.append("0.0023버전에서는 투명색추가하였습니다.\n");
				useTextArea.append("만든이 : 컴퓨터판타지연구소\n");
				useTextArea.append("사용언어 : Java21SE\n");
				useTextArea.setLineWrap(true); // 텍스트 줄 바꿈
				useTextArea.setWrapStyleWord(true); // 단어 단위로 줄 바꿈
				useTextArea.setEditable(false); // 텍스트 수정 불가
				useTextArea.setCaretPosition(0);
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
			}
		});
		exitbtn = new JButton("끝내기");
		exitbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				int result = JOptionPane.showConfirmDialog(null, "정말 끝내겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION);
				
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				
			}
		});
		
		rightpanel.add(iconewbtn);
		rightpanel.add(imageopenbtn);
		rightpanel.add(icoopenbtn);
		rightpanel.add(icosavebtn);
		rightpanel.add(undobtn);
		rightpanel.add(redobtn);
		rightpanel.add(penbtn);
		rightpanel.add(rectbtn);
		rightpanel.add(circlebtn);
		rightpanel.add(arcbtn);
		rightpanel.add(strbtn);
		rightpanel.add(fillbtn);
		rightpanel.add(erasebtn);
		rightpanel.add(strokebtn);
		rightpanel.add(colorbtn);
		rightpanel.add(tpbtn);
		rightpanel.add(markbtn);
		rightpanel.add(gridbtn);
		rightpanel.add(centerbtn);
		rightpanel.add(inforbtn);
		rightpanel.add(exitbtn);
		
		outpanel = new JPanel();
		outpanel.setPreferredSize(new Dimension(700,600));
		outpanel.setBackground(Color.GRAY);
		
		drawpanel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.WHITE); // 배경 색상 설정
				g.fillRect(0, 0, 256, 256); // 전체 영역을 덮음
				icom.drawPaint();
				g.drawImage(image,0,0,null);
				
				if(markb)
				{
					g.setColor(Color.BLACK);
					for (int x = 20; x < 256 + 20; x += 20) {
						g.drawLine(x, 0, x, 20); // 짧은 세로선
						//g.drawString(String.valueOf(x - 50), x - 10, 30); // 숫자 표시
					}
					// 왼쪽 눈금
					for (int y = 20; y < 256 + 20; y += 20) {
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
					for (int x = 0; x < 256; x += 10) { // 세로선
						g.drawLine(x, 0, x, 256);
					}
					for (int y = 0; y < 256; y += 10) { // 가로선
						g.drawLine(0, y, 256, y);
					}
				}else{
					//g.setColor(Color.WHITE); // 배경 색상 설정
					//g.fillRect(0, 0, dpwidth, dpheight); // 전체 영역을 덮음
				}
				if(centerb)
				{
					g.setColor(Color.RED);
					g.fillOval(127,127,3,3);
				}else{
					
				}
				if(rectb)
				{
					g.setColor(Color.BLACK);
					g.drawRect(spoint.x,spoint.y,point.x-spoint.x,point.y-spoint.y);
				}else{
					
				}
				
				if(circleb)
				{
					System.out.println("circleb가 왔어요");
					g.setColor(Color.BLACK);
					g.drawOval(spoint.x,spoint.y,point.x-spoint.x,point.y-spoint.y);
				}else{
					
				}
				if(arcb)
				{
					
					g.setColor(Color.BLACK);
					g.drawRect(arcx,arcy,arcwidth,archeight);
					if(arcmode)
					{
						
					}
				}
				
			}
		};
		drawpanel.setPreferredSize(new Dimension(256,256));
		drawpanel.setSize(256,256);
		drawpanel.setBackground(Color.WHITE);
		
		JScrollPane scrollPane = new JScrollPane(outpanel);
		
		// 스크롤 방향 설정 (필요에 따라 조정 가능)
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		outpanel.add(drawpanel,BorderLayout.CENTER);
		frame.add(rightpanel,BorderLayout.EAST);
		frame.add(scrollPane,BorderLayout.WEST);
		frame.setVisible(true);
		drawpanel.addMouseListener(new MouseListener(){
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					switch(drawselect)
					{
						case 0:
						saveUndoState();
						currentPath = null;
						currentPath = new Path2D.Double(); // 새로운 경로 생성
						currentPath.reset();
						currentPath.moveTo(e.getX(), e.getY());
						break;
						//1사각형 2타원
						case 1:
						saveUndoState();
						spoint = new Point();
						spoint.x = e.getX();
						spoint.y = e.getY();
						rectb = true;
						break;
						
						case 2:
						saveUndoState();
						spoint = new Point();
						spoint.x = e.getX();
						spoint.y = e.getY();
						circleb = true;
						break;
						//원호
						case 3:
						
						
						break;
						case 4:
						
						break;
						
						case 5:
						saveUndoState();
						currentPath = null;
						currentPath = new Path2D.Double(); // 새로운 경로 생성
						currentPath.reset();
						currentPath.moveTo(e.getX(), e.getY());
						break;
						
						case 9:
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
					switch(drawselect)
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
						
						point = new Point();
						point.x = e.getX();
						point.y = e.getY();
						
						g2d.setStroke(new BasicStroke(penst));
						g2d.setColor(new Color(replacementColor));
						g2d.drawRect(spoint.x,spoint.y,point.x-spoint.x,point.y-spoint.y);
						//g2d.fillOval(20,20,40,40);
						rectb = false;
						circleb = false;
						break;
						
						case 2:
						
						point = new Point();
						point.x = e.getX();
						point.y = e.getY();
						
						g2d.setStroke(new BasicStroke(penst));
						g2d.setColor(new Color(replacementColor));
						g2d.drawOval(spoint.x,spoint.y,point.x-spoint.x,point.y-spoint.y);
						//g2d.fillOval(20,20,40,40);
						rectb = false;
						circleb = false;
						
						break;
						
						case 3:
						
						break;
						
						case 4:
						
						break;
						case 5:
						if(currentPath != null)
						{
							paths.add((Path2D) currentPath.clone());
							// 새로운 경로를 추가할 때는 채워지지 않도록 설정
							currentPath = null; // 경로를 null로 재설정
						}
						break;
						case 9:
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
					drawpanel.repaint();
					
				}
			}
			//3문자 4채우기 5 지우개
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (SwingUtilities.isLeftMouseButton(e)) {
					switch(drawselect)
					
					{
						case 0:
						
						break;
						case 1:
						
						break;
						
						case 2:
						
						break;
						
						case 3:
						saveUndoState();
						point = new Point();
						
						point.x = e.getX();
						point.y = e.getY();
						Graphics2D g2d = image.createGraphics();
						if(replacementColor == -0x1000000) replacementColor = 0;
						g2d.setColor(new Color(replacementColor));
						
						Font font = new Font(shapestr,Font.BOLD,fontsize);
						g2d.setFont(font);
						if(dpstr != null && point != null) g2d.drawString(dpstr,point.x,point.y);
						g2d.dispose();
						drawpanel.repaint();
						break;
						
						case 4:
						saveUndoState();
						System.out.println("마우스클릭");
						point = new Point();
						point.x = e.getX(); point.y = e.getY();
						int targetColor = image.getRGB(point.x, point.y);
						floodFill(image, point.x, point.y, targetColor, replacementColor);
						
						drawpanel.repaint();
						break;
						
						case 5:
						
						break;
						case 9:
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
						case 10:
						point = new Point(e.getX(),e.getY());
						// 1. 클릭한 색 가져오기
						tpColor = new Color(image.getRGB(point.x, point.y), true);
						
						// 2. targetColor = tpColor의 RGB (alpha는 무시)
						int tcolor = tpColor.getRGB();
						
						// 3. replacementColor = tpColor의 RGB + alpha=0
						int rcolor = (tpColor.getRGB() & 0x00FFFFFF); // alpha 제거 → 0으로 설정됨
						
						// 4. floodFill 실행
						floodFill(image, point.x, point.y, tcolor, rcolor);
						/*String msg =
						"R: " + tpColor.getRed() +
						"  G: " + tpColor.getGreen() +
						"  B: " + tpColor.getBlue() +
						"  A: " + tpColor.getAlpha() +
						"\n투명색이 저장되었습니다.";
						
						JOptionPane.showMessageDialog(null, msg);*/
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
		drawpanel.addMouseMotionListener(new MouseMotionListener(){
			
			@Override
			public void mouseDragged(MouseEvent e) {
				switch(drawselect)
				{
					case 0:
					if (currentPath != null) {
						currentPath.lineTo(e.getX(), e.getY());
						icom.drawPaint();
						drawpanel.repaint();
					}
					break;
					
					case 1:
					if(rectb)
					{
						point = new Point();
						point.x = e.getX();
						point.y = e.getY();
						drawpanel.repaint();
					}
					break;
					case 2:
					if(circleb)
					{
						point = new Point();
						point.x = e.getX();
						point.y = e.getY();
						drawpanel.repaint();
					}
					break;
					
					case 3:
					
					break;
					case 4:
					
					break;
					
					case 5:
					if (currentPath != null) {
						currentPath.lineTo(e.getX(), e.getY());
						icom.drawPaint();
						drawpanel.repaint();
					}
					break;
					case 9:
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
							drawpanel.repaint();
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
				String statusText = String.format("상태표시줄입니다. 너비 : %d 높이 : %d 마우스X : %d 마우스Y : %d",256,256,statusp.x,statusp.y);
				statusLabel.setText(statusText);
				frame.revalidate();
				frame.repaint();
			}
			
		});
		
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run()
			{
				icom = new AntICOFileMaker();
				icom.Init();
				icom.frame.revalidate();
				icom.frame.repaint();
			}
		});
	}
	
	public void drawPaint()
	{
		Graphics2D g2d = image.createGraphics();
		switch(drawselect)
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
			
			break;
			
			case 2:
			
			break;
			
			case 3:
			
			break;
			
			case 4:
			
			break;
			
			case 5:
			if(currentPath != null)
			{
				g2d.setStroke(new BasicStroke(5));
				g2d.setColor(Color.WHITE);
				g2d.draw(currentPath);
			}
			break;
			
			case 6:
			
			break;
			
			case 7:
			undo();
			drawselect = 0;
			break;
			
			case 8:
			redo();
			drawselect = 0;
			break;
			default:
			
			break;
			
		}
		
		
		g2d.dispose();
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
			drawpanel.repaint();
		}
	}
	
	public void redo() {
		if (!redoStack.isEmpty()) {
			undoStack.push(image);
			image = redoStack.pop();
			//System.out.println("redostack pop했습니다.");
			drawpanel.repaint();
		}
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
	public int calculateAngle(int px, int py, int cx, int cy) {
		double dx = px - cx;
		double dy = cy - py; // y축 반전 (그래픽 좌표계는 아래로 증가)
		double angleRad = Math.atan2(dy, dx);
		int angleDeg = (int) Math.toDegrees(angleRad);
		return (angleDeg + 360) % 360; // 0~359 범위로 정규화
	}
}
