import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;
import java.awt.*;
import java.lang.*;

import java.io.FileReader;

import Version.*;
class AntEXECreater
{
	public static AntEXECreater exec;
	
	public static JFrame frame;
	public static JPanel gccselpanel,gccpathpanel,exenamepanel,guiselpanel,javapathpanel,icoselpanel,icopathpanel,btnpanel,infopanel,javaoptionpanel,companel;
	public static JLabel gccsellabel,gccpathlabel,exenamelabel,guisellabel,javapathlabel,icosellabel,icopathlabel,btnlabel,infolabel,javaoptionlabel,comlabel;
	public static JTextField gccpathtxt,exenametxt,javapathtxt,icopathtxt,comtxt;
	public static JButton gccpathbtn,javapathbtn,icopathbtn,makebtn,cancelbtn;
	public static JRadioButton cmdradio,guiradio,classradio,jarradio;
	public static ButtonGroup group1,group2;
	public static JCheckBox gccselcheck,icoselcheck;
	//0.0023부터수정
	public static JLabel verlabel,dirlabel;
	public static JPanel dirpanel;
	public static JButton dirbtn;
	public static JTextField dirtxt;
	public static String currentDir = "";
	//0.0025추가
	public static JPanel javaexepathpanel,javaexeselpanel;
	public static JLabel javaexepathlabel,javaexesellabel;
	public static JTextField javaexepathtxt;
	public static JButton javaexepathbtn;
	public static JCheckBox javaexeselcheck;
	
	public static void InitExeCreate()
	{
		Version version = new Version();
		frame = new JFrame("앤트자바EXE생성기v" + version.app_Version);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,800);
		frame.setLocation(300,50);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
		// 아이콘 설정
		Image icon = Toolkit.getDefaultToolkit().getImage("antexe.png");
		frame.setIconImage(icon);
		gccselpanel = new JPanel();
		gccselpanel.setLayout(new FlowLayout());
		gccsellabel = new JLabel("");
		gccsellabel.setFont(new Font("Serif",Font.PLAIN,20));
		gccselcheck = new JCheckBox("GCC PATH");
		gccselcheck.setFont(new Font("Serif",Font.PLAIN,20));
		
		gccselcheck.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("path 선택됨: " + gccselcheck.isSelected());
				if(gccselcheck.isSelected())
				{
					gccpathtxt.setEnabled(false);
					gccpathbtn.setEnabled(false);
					gccpathtxt.setBackground(Color.GRAY);
				}else
				{
					gccpathtxt.setEnabled(true);
					gccpathbtn.setEnabled(true);
					gccpathtxt.setBackground(Color.WHITE);
				}
			}
		});
		gccselpanel.add(gccsellabel);
		gccselpanel.add(gccselcheck);
		frame.add(gccselpanel);
		
		gccpathpanel = new JPanel();
		gccpathpanel.setLayout(new FlowLayout());
		gccpathlabel = new JLabel("gcc경로 : ");
		gccpathlabel.setFont(new Font("Serif",Font.PLAIN,20));
		gccpathtxt = new JTextField();
		gccpathtxt.setColumns(30);
		gccpathtxt.setFont(new Font("Serif",Font.PLAIN,20));
		gccpathbtn = new JButton("...");
		gccpathbtn.setFont(new Font("Serif",Font.PLAIN,20));
		gccpathbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try{
					JFileChooser chooser = new JFileChooser();
					
					// 파일 및 폴더 모두 선택 가능하도록 설정
					chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					
					// .qp 파일만 허용하는 필터 추가
					chooser.setAcceptAllFileFilterUsed(false); // "모든 파일" 필터 비활성화
					try (BufferedReader reader = new BufferedReader(new FileReader("currentDir.txt"))) {
						currentDir = reader.readLine(); // 첫 줄만 읽음
						System.out.println("불러온 currentDir: " + currentDir);
					} catch (IOException ioe) {
						System.out.println("파일 읽기 중 오류 발생: " + ioe.getMessage());
					}
					if(currentDir==null)
					{
						currentDir = "";
					}
					File dir = new File(currentDir);
					if (!dir.exists()) {
						// currentDir이 없으면 현재 작업 디렉터리로 대체
						currentDir = System.getProperty("user.dir");
						dir = new File(currentDir);
					}
					chooser.setCurrentDirectory(new File(currentDir));
					chooser.addChoosableFileFilter(new FileFilter() {
						@Override
						public boolean accept(File file) {
							// 폴더 또는 .qp 파일 허용
							return file.isDirectory();// || file.getName().toLowerCase().endsWith(".class");
						}
						
						@Override
						public String getDescription() {
							return "폴더";// 및 .class 파일";
						}
					});
					
					// 다이얼로그 표시
					int returnValue = chooser.showOpenDialog(null);
					File selectedFile = chooser.getSelectedFile();
					System.out.println("선택한 폴더 : " + selectedFile);
					gccpathtxt.setText("");
					gccpathtxt.setText(selectedFile.toString());
					/*currentDir = selectedFile.toString();
					try {
					FileWriter writer = new FileWriter("currentDir.txt");
					writer.write(currentDir);
					writer.close();
					System.out.println("currentDir.txt에 저장 완료!");
					} catch (IOException ioe) {
					System.out.println("파일 저장 중 오류 발생: " + ioe.getMessage());
					}*/
					
					//폴더및 파일
					/*if (returnValue == JFileChooser.APPROVE_OPTION) {
					
					} else {
					
					}*/
				}catch(Exception ioe)
				{
					
				}
			}
		});
		gccpathpanel.add(gccpathlabel);
		gccpathpanel.add(gccpathtxt);
		gccpathpanel.add(gccpathbtn);
		frame.add(gccpathpanel);
		
		gccselcheck.setSelected(true);
		if(gccselcheck.isSelected())
		{
			gccpathtxt.setEnabled(false);
			gccpathbtn.setEnabled(false);
			gccpathtxt.setBackground(Color.GRAY);
		}else
		{
			gccpathtxt.setEnabled(true);
			gccpathbtn.setEnabled(true);
			gccpathtxt.setBackground(Color.WHITE);
		}
		
		//0.0025버전 추가
		javaexeselpanel = new JPanel();
		javaexeselpanel.setLayout(new FlowLayout());
		javaexesellabel = new JLabel("");
		javaexesellabel.setFont(new Font("Serif",Font.PLAIN,20));
		javaexeselcheck = new JCheckBox("JAVA PATH");
		javaexeselcheck.setFont(new Font("Serif",Font.PLAIN,20));
		
		javaexeselcheck.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("path 선택됨: " + javaexeselcheck.isSelected());
				if(javaexeselcheck.isSelected())
				{
					javaexepathtxt.setEnabled(false);
					javaexepathbtn.setEnabled(false);
					javaexepathtxt.setBackground(Color.GRAY);
				}else
				{
					javaexepathtxt.setEnabled(true);
					javaexepathbtn.setEnabled(true);
					javaexepathtxt.setBackground(Color.WHITE);
				}
			}
		});
		javaexeselpanel.add(javaexesellabel);
		javaexeselpanel.add(javaexeselcheck);
		frame.add(javaexeselpanel);
		
		javaexepathpanel = new JPanel();
		javaexepathpanel.setLayout(new FlowLayout());
		javaexepathlabel = new JLabel("javaexe경로 : ");
		javaexepathlabel.setFont(new Font("Serif",Font.PLAIN,20));
		javaexepathtxt = new JTextField();
		javaexepathtxt.setColumns(30);
		javaexepathtxt.setFont(new Font("Serif",Font.PLAIN,20));
		javaexepathbtn = new JButton("...");
		javaexepathbtn.setFont(new Font("Serif",Font.PLAIN,20));
		javaexepathbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try{
					JFileChooser chooser = new JFileChooser();
					
					// 파일 및 폴더 모두 선택 가능하도록 설정
					chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					
					// .qp 파일만 허용하는 필터 추가
					chooser.setAcceptAllFileFilterUsed(false); // "모든 파일" 필터 비활성화
					try (BufferedReader reader = new BufferedReader(new FileReader("currentDir.txt"))) {
						currentDir = reader.readLine(); // 첫 줄만 읽음
						System.out.println("불러온 currentDir: " + currentDir);
					} catch (IOException ioe) {
						System.out.println("파일 읽기 중 오류 발생: " + ioe.getMessage());
					}
					if(currentDir==null)
					{
						currentDir = "";
					}
					File dir = new File(currentDir);
					if (!dir.exists()) {
						// currentDir이 없으면 현재 작업 디렉터리로 대체
						currentDir = System.getProperty("user.dir");
						dir = new File(currentDir);
					}
					chooser.setCurrentDirectory(new File(currentDir));
					chooser.addChoosableFileFilter(new FileFilter() {
						@Override
						public boolean accept(File file) {
							// 폴더 또는 .qp 파일 허용
							return file.isDirectory();// || file.getName().toLowerCase().endsWith(".class");
						}
						
						@Override
						public String getDescription() {
							return "폴더";// 및 .class 파일";
						}
					});
					
					// 다이얼로그 표시
					int returnValue = chooser.showOpenDialog(null);
					File selectedFile = chooser.getSelectedFile();
					System.out.println("선택한 폴더 : " + selectedFile);
					javaexepathtxt.setText("");
					javaexepathtxt.setText(selectedFile.toString()+"\\");
					/*currentDir = selectedFile.toString();
					try {
					FileWriter writer = new FileWriter("currentDir.txt");
					writer.write(currentDir);
					writer.close();
					System.out.println("currentDir.txt에 저장 완료!");
					} catch (IOException ioe) {
					System.out.println("파일 저장 중 오류 발생: " + ioe.getMessage());
					}*/
					
					//폴더및 파일
					/*if (returnValue == JFileChooser.APPROVE_OPTION) {
					
					} else {
					
					}*/
				}catch(Exception ioe)
				{
					
				}
			}
		});
		javaexepathpanel.add(javaexepathlabel);
		javaexepathpanel.add(javaexepathtxt);
		javaexepathpanel.add(javaexepathbtn);
		frame.add(javaexepathpanel);
		
		javaexeselcheck.setSelected(true);
		if(javaexeselcheck.isSelected())
		{
			javaexepathtxt.setEnabled(false);
			javaexepathbtn.setEnabled(false);
			javaexepathtxt.setBackground(Color.GRAY);
		}else
		{
			javaexepathtxt.setEnabled(true);
			javaexepathbtn.setEnabled(true);
			javaexepathtxt.setBackground(Color.WHITE);
		}
		guiselpanel = new JPanel();
		guiselpanel.setLayout(new FlowLayout());
		guisellabel = new JLabel("");
		guisellabel.setFont(new Font("Serif",Font.PLAIN,20));
		cmdradio = new JRadioButton("콘솔");
		cmdradio.setFont(new Font("Serif",Font.PLAIN,20));
		cmdradio.setSelected(true);
		guiradio = new JRadioButton("GUI");
		guiradio.setFont(new Font("Serif",Font.PLAIN,20));
		group1 = new ButtonGroup();
		group1.add(cmdradio);
		group1.add(guiradio);
		guiselpanel.add(guisellabel);
		guiselpanel.add(cmdradio);
		guiselpanel.add(guiradio);
		frame.add(guiselpanel);
		
		exenamepanel = new JPanel();
		exenamepanel.setLayout(new FlowLayout());
		exenamelabel = new JLabel("exe파일이름 : ");
		exenamelabel.setFont(new Font("Serif",Font.PLAIN,20));
		exenametxt = new JTextField();
		exenametxt.setColumns(30);
		exenametxt.setFont(new Font("Serif",Font.PLAIN,20));
		exenamepanel.add(exenamelabel);
		exenamepanel.add(exenametxt);
		frame.add(exenamepanel);
		exenametxt.setText("test.exe");
		companel = new JPanel(new FlowLayout());
		comlabel = new JLabel("java명령인수 : ");
		comlabel.setFont(new Font("Serif",Font.PLAIN,20));
		
		comtxt = new JTextField();
		comtxt.setColumns(30);
		comtxt.setFont(new Font("Serif",Font.PLAIN,20));
		companel.add(comlabel);
		companel.add(comtxt);
		frame.add(companel);
		dirpanel = new JPanel(new FlowLayout());
		dirlabel = new JLabel("파일생성위치 : ");
		dirlabel.setFont(new Font("Serif",Font.PLAIN,20));
		dirtxt = new JTextField();
		dirtxt.setColumns(30);
		dirtxt.setFont(new Font("Serif",Font.PLAIN,20));
		dirbtn = new JButton("...");
		dirbtn.setFont(new Font("Serif",Font.PLAIN,20));
		dirbtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				try{
					int returnvalue = 0;
					File selecteddir= null;
					JFileChooser filechooser = new JFileChooser();
					
					filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					try (BufferedReader reader = new BufferedReader(new FileReader("currentDir.txt"))) {
						currentDir = reader.readLine(); // 첫 줄만 읽음
						System.out.println("불러온 currentDir: " + currentDir);
					} catch (IOException ioe) {
						System.out.println("파일 읽기 중 오류 발생: " + ioe.getMessage());
					}
					// 초기 디렉토리 설정 (현재 디렉토리)
					if(currentDir==null)
					{
						currentDir = "";
					}
					File dir = new File(currentDir);
					if (!dir.exists()) {
						// currentDir이 없으면 현재 작업 디렉터리로 대체
						currentDir = System.getProperty("user.dir");
						dir = new File(currentDir);
					}
					filechooser.setCurrentDirectory(new File(currentDir));
					returnvalue = filechooser.showOpenDialog(frame);
					if(returnvalue == JFileChooser.APPROVE_OPTION)
					{
						selecteddir = filechooser.getSelectedFile();
						System.out.println("선택한 폴더 : " + selecteddir.getPath());
						currentDir = selecteddir.toString();
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
						System.out.println("선택폴더 취소됨");
					}
					if(selecteddir != null)
					{
						/*String filePath = selectedFile.getPath();
						filename = filePath;
						if(!filePath.endsWith(".class"))
						{
						filePath += ".class";
						}*/
						dirtxt.setText(selecteddir.getPath()+"\\");
					}
				}catch(Exception ie)
				{
					
				}
			}
		});
		dirpanel.add(dirlabel);
		dirpanel.add(dirtxt);
		dirpanel.add(dirbtn);
		frame.add(dirpanel);
		javaoptionpanel = new JPanel();
		javaoptionpanel.setLayout(new FlowLayout());
		javaoptionlabel = new JLabel("");
		javaoptionlabel.setFont(new Font("Serif",Font.PLAIN,20));
		classradio = new JRadioButton("class");
		classradio.setFont(new Font("Serif",Font.PLAIN,20));
		classradio.setSelected(true);
		jarradio = new JRadioButton("jar");
		jarradio.setFont(new Font("Serif",Font.PLAIN,20));
		classradio.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				javapathlabel.setText("MAINCLASS : ");
			}
		});
		jarradio.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				javapathlabel.setText("JAR파일 : ");
			}
		});
		group2 = new ButtonGroup();
		group2.add(classradio);
		group2.add(jarradio);
		javaoptionpanel.add(javaoptionlabel);
		javaoptionpanel.add(classradio);
		javaoptionpanel.add(jarradio);
		frame.add(javaoptionpanel);
		
		
		javapathpanel = new JPanel();
		javapathpanel.setLayout(new FlowLayout());
		if(classradio.isSelected())
		{
			javapathlabel = new JLabel("MAINCLASS : ");
		}else if(jarradio.isSelected())
		{
			javapathlabel = new JLabel("JAR파일경로 : ");
		}
		javapathlabel.setFont(new Font("Serif",Font.PLAIN,20));
		javapathtxt = new JTextField();
		javapathtxt.setColumns(30);
		javapathtxt.setFont(new Font("Serif",Font.PLAIN,20));
		javapathbtn = new JButton("...");
		javapathbtn.setFont(new Font("Serif",Font.PLAIN,20));
		javapathbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int returnvalue;
				if(classradio.isSelected())
				{
					File selectedFile= null;
					JFileChooser filechooser = new JFileChooser();
					// 파일 필터 추가 (.qp 파일만 보이게 하기)
					FileNameExtensionFilter filter = new FileNameExtensionFilter(".class파일", "class");
					filechooser.setFileFilter(filter);
					try (BufferedReader reader = new BufferedReader(new FileReader("currentDir.txt"))) {
						currentDir = reader.readLine(); // 첫 줄만 읽음
						System.out.println("불러온 currentDir: " + currentDir);
					} catch (IOException ioe) {
						System.out.println("파일 읽기 중 오류 발생: " + ioe.getMessage());
					}
					// 초기 디렉토리 설정 (현재 디렉토리)
					if(currentDir==null)
					{
						currentDir = "";
					}
					File dir = new File(currentDir);
					if (!dir.exists()) {
						// currentDir이 없으면 현재 작업 디렉터리로 대체
						currentDir = System.getProperty("user.dir");
						dir = new File(currentDir);
					}
					filechooser.setCurrentDirectory(new File(currentDir));
					returnvalue = filechooser.showOpenDialog(frame);
					if(returnvalue == JFileChooser.APPROVE_OPTION)
					{
						selectedFile = filechooser.getSelectedFile();
						System.out.println("선택한 파일 : " + selectedFile.getPath());
						
						currentDir = selectedFile.getParent().toString();
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
						String mainclass = selectedFile.getPath();
						int lastIndex = mainclass.lastIndexOf('\\'); // '\'는 이스케이프 필요
						String tmp = mainclass.substring(lastIndex + 1);
						// 파일 이름 추출
						lastIndex = tmp.lastIndexOf('.');
						String className = (lastIndex != -1) ? tmp.substring(0,lastIndex) : tmp;
						javapathtxt.setText(className);
					}
				}else if(jarradio.isSelected())
				{
					File selectedFile= null;
					JFileChooser filechooser = new JFileChooser();
					// 파일 필터 추가 (.qp 파일만 보이게 하기)
					FileNameExtensionFilter filter = new FileNameExtensionFilter(".jar파일", "jar");
					filechooser.setFileFilter(filter);
					try (BufferedReader reader = new BufferedReader(new FileReader("currentDir.txt"))) {
						currentDir = reader.readLine(); // 첫 줄만 읽음
						System.out.println("불러온 currentDir: " + currentDir);
					} catch (IOException ioe) {
						System.out.println("파일 읽기 중 오류 발생: " + ioe.getMessage());
					}
					// 초기 디렉토리 설정 (현재 디렉토리)
					if(currentDir==null)
					{
						currentDir = "";
					}
					File dir = new File(currentDir);
					if (!dir.exists()) {
						// currentDir이 없으면 현재 작업 디렉터리로 대체
						currentDir = System.getProperty("user.dir");
						dir = new File(currentDir);
					}
					filechooser.setCurrentDirectory(new File(currentDir));
					returnvalue = filechooser.showOpenDialog(frame);
					if(returnvalue == JFileChooser.APPROVE_OPTION)
					{
						selectedFile = filechooser.getSelectedFile();
						System.out.println("선택한 파일 : " + selectedFile.getPath());
						currentDir = selectedFile.getParent().toString();
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
						String jarfile = selectedFile.getPath();
						int lastIndex = jarfile.lastIndexOf('\\'); // '\'는 이스케이프 필요
						String jarname = jarfile.substring(lastIndex + 1);
						// 파일 이름 추출
						
						
						javapathtxt.setText(jarname);
					}
				}
			}
		});
		javapathpanel.add(javapathlabel);
		javapathpanel.add(javapathtxt);
		javapathpanel.add(javapathbtn);
		frame.add(javapathpanel);
		
		icoselpanel = new JPanel();
		icoselpanel.setLayout(new FlowLayout());
		icosellabel = new JLabel("");
		icosellabel.setFont(new Font("Serif",Font.PLAIN,20));
		icoselcheck = new JCheckBox("기본아이콘");
		icoselcheck.setFont(new Font("Serif",Font.PLAIN,20));
		icoselcheck.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("path 선택됨: " + gccselcheck.isSelected());
				if(icoselcheck.isSelected())
				{
					icopathtxt.setEnabled(false);
					icopathtxt.setBackground(Color.GRAY);
					icopathbtn.setEnabled(false);
				}else
				{
					icopathtxt.setEnabled(true);
					icopathtxt.setBackground(Color.WHITE);
					icopathbtn.setEnabled(true);
				}
			}
		});
		icoselpanel.add(icosellabel);
		icoselpanel.add(icoselcheck);
		frame.add(icoselpanel);
		
		icopathpanel = new JPanel();
		icopathpanel.setLayout(new FlowLayout());
		icopathlabel = new JLabel("ico파일경로 : ");
		icopathlabel.setFont(new Font("Serif",Font.PLAIN,20));
		icopathtxt = new JTextField();
		icopathtxt.setColumns(30);
		icopathtxt.setFont(new Font("Serif",Font.PLAIN,20));
		icopathbtn = new JButton("...");
		icopathbtn.setFont(new Font("Serif",Font.PLAIN,20));
		icopathbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try{
					int returnvalue = 0;
					File selectedFile= null;
					JFileChooser filechooser = new JFileChooser();
					// 파일 필터 추가 (.qp 파일만 보이게 하기)
					FileNameExtensionFilter filter = new FileNameExtensionFilter(".ico파일", "ico");
					filechooser.setFileFilter(filter);
					try (BufferedReader reader = new BufferedReader(new FileReader("currentDir.txt"))) {
						currentDir = reader.readLine(); // 첫 줄만 읽음
						System.out.println("불러온 currentDir: " + currentDir);
					} catch (IOException ioe) {
						System.out.println("파일 읽기 중 오류 발생: " + ioe.getMessage());
					}
					// 초기 디렉토리 설정 (현재 디렉토리)
					if(currentDir==null)
					{
						currentDir = "";
					}
					File dir = new File(currentDir);
					if (!dir.exists()) {
						// currentDir이 없으면 현재 작업 디렉터리로 대체
						currentDir = System.getProperty("user.dir");
						dir = new File(currentDir);
					}
					filechooser.setCurrentDirectory(new File(currentDir));
					returnvalue = filechooser.showOpenDialog(frame);
					if(returnvalue == JFileChooser.APPROVE_OPTION)
					{
						selectedFile = filechooser.getSelectedFile();
						System.out.println("선택한 파일 : " + selectedFile.getPath());
						currentDir = selectedFile.getParent().toString();
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
						/*String filePath = selectedFile.getPath();
						filename = filePath;
						if(!filePath.endsWith(".class"))
						{
						filePath += ".class";
						}*/
						icopathtxt.setText(selectedFile.getPath());
					}
				}catch(Exception ie)
				{
					
				}
			}
		});
		icopathpanel.add(icopathlabel);
		icopathpanel.add(icopathtxt);
		icopathpanel.add(icopathbtn);
		frame.add(icopathpanel);
		
		icoselcheck.setSelected(true);
		if(icoselcheck.isSelected())
		{
			icopathtxt.setEnabled(false);
			icopathbtn.setEnabled(false);
			icopathtxt.setBackground(Color.GRAY);
		}else
		{
			icopathtxt.setEnabled(true);
			icopathbtn.setEnabled(true);
			icopathtxt.setBackground(Color.WHITE);
		}
		btnpanel = new JPanel();
		btnpanel.setLayout(new FlowLayout());
		btnlabel = new JLabel("");
		btnlabel.setFont(new Font("Serif",Font.PLAIN,20));
		makebtn = new JButton("exe파일생성");
		makebtn.setFont(new Font("Serif",Font.PLAIN,20));
		makebtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String windrespathstr="";
				String gccpathstr="";
				String exenamestr = exenametxt.getText();
				String exepathstr = dirtxt.getText().trim();
				String comstr = comtxt.getText().toString().replace("\\","\\\\").replace("\"","\\\"").replace("\'","\\\'")+" ";
				
				
				if(gccselcheck.isSelected())
				{
					// 시스템 변수 PATH 가져오기
					String path = System.getenv("PATH");
					boolean found = false;
					
					// PATH 값 출력
					if (path != null) {
						//System.out.println("시스템 PATH: ");
						String[] paths = path.split(";");
						for (String p : paths) {
							File dir = new File(p);
							File gcc = new File(dir, "gcc.exe");
							File windres = new File(dir,"windres.exe");
							if (gcc.exists() && gcc.isFile()) {
								System.out.println("gcc.exe : " + gcc.getAbsolutePath());
								gccpathstr = gcc.getAbsolutePath();
								found = true;
								if(windres.exists() && windres.isFile())
								{
									windrespathstr = windres.getAbsolutePath();
								}
								break;
							}
							
							//System.out.println(p);
						}
					}
					if (!found) {
						//System.out.println("gcc.exe를 PATH에서 찾을 수 없습니다.");
						JOptionPane.showMessageDialog(null, "gcc.exe를 PATH에서 찾을 수 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
						
					}
				}else
				{
					boolean found = false;
					File gcc = new File(gccpathtxt.getText(),"gcc.exe");
					File windres = new File(gccpathtxt.getText(),"windres.exe");
					if (gcc.exists() && gcc.isFile()) {
						System.out.println("gcc.exe : " + gcc.getAbsolutePath());
						gccpathstr = gcc.getAbsolutePath();
						found = true;
						if(windres.exists() && windres.isFile())
						{
							windrespathstr = windres.getAbsolutePath();
						}
					}
					if(!found){
						JOptionPane.showMessageDialog(null, "gcc.exe를 PATH에서 찾을 수 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				// 만들고자 하는 폴더의 경로
				String folderPath = "temp";
				
				// File 객체 생성
				File folder = new File(folderPath);
				
				// 폴더 생성
				if (folder.exists()) {
					System.out.println("temp폴더가 이미 존재합니다: " + folderPath);
				} else {
					if (folder.mkdir()) {
						System.out.println("temp폴더가 성공적으로 생성되었습니다: " + folderPath);
					} else {
						System.out.println("temp폴더 생성에 실패했습니다.");
					}
				}
				
				String cstr = "";
				String jstr = "";
				String gstr = "";
				
				boolean jexesel=false,guisel=false;
				if(classradio.isSelected())
				{
					
					
					String className = javapathtxt.getText();
					cstr = comstr + className;
				}else if(jarradio.isSelected())
				{
					
					
					String jarName = javapathtxt.getText();
					
					cstr = comstr + "-jar " + jarName;
				}
				if(guiradio.isSelected())
				{
					jstr = "javaw.exe";
					gstr = "CREATE_NO_WINDOW";
				}else if(cmdradio.isSelected())
				{
					jstr = "java.exe";
					gstr = "CREATE_NEW_CONSOLE";
				}
				if(icoselcheck.isSelected())
				{
					if(javaexeselcheck.isSelected())
					{
						
						GCCExcute(icoEdit("temp\\temp.ico"),CodeEdit(cstr,gstr,jstr),gccpathstr,windrespathstr,exenamestr,exepathstr);
					}else
					{
						String cdir = dirtxt.getText().trim().replace("\\","\\\\");
						String jexe = javaexepathtxt.getText().trim().replace("\\","\\\\");
						if(jexe.startsWith(cdir))
						{
							jexe = jexe.substring(cdir.length());
							if(jexe.startsWith("\\\\"))
							{
								jexe = jexe.substring(("\\\\").length());
							}
						}
						GCCExcute(icoEdit("temp\\temp.ico"),CodeEdit2(cstr,gstr,jstr,jexe),gccpathstr,windrespathstr,exenamestr,exepathstr);
					}
				}else
				{
					if(javaexeselcheck.isSelected())
					{
						GCCExcute(icoEdit(icopathtxt.getText()),CodeEdit(cstr,gstr,jstr),gccpathstr,windrespathstr,exenamestr,exepathstr);
					}else
					{
						String cdir = dirtxt.getText().trim().replace("\\","\\\\");
						String jexe = javaexepathtxt.getText().trim().replace("\\","\\\\");
						if(jexe.startsWith(cdir))
						{
							jexe = jexe.substring(cdir.length());
							if(jexe.startsWith("\\\\"))
							{
								jexe = jexe.substring(("\\\\").length());
							}
						}
						GCCExcute(icoEdit(icopathtxt.getText()),CodeEdit2(cstr,gstr,jstr,jexe),gccpathstr,windrespathstr,exenamestr,exepathstr);
					}
				}
				
				
			}
		});
		cancelbtn = new JButton("종료");
		cancelbtn.setFont(new Font("Serif",Font.PLAIN,20));
		cancelbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		btnpanel.add(btnlabel);
		btnpanel.add(makebtn);
		btnpanel.add(cancelbtn);
		frame.add(btnpanel);
		
		infopanel = new JPanel();
		infopanel.setLayout(new BoxLayout(infopanel,BoxLayout.Y_AXIS));
		verlabel = new JLabel("버전 : "+ version.app_Version);
		verlabel.setFont(new Font("Serif",Font.PLAIN,20));
		infolabel = new JLabel("만든이 : 컴퓨터판타지연구소");
		infolabel.setFont(new Font("Serif",Font.PLAIN,20));
		infopanel.add(verlabel);
		infopanel.add(infolabel);
		frame.add(infopanel);
		frame.setVisible(true);
		
	}
	public static String icoEdit(String icopath)
	{
		// 경로 안의 \ 를 \\ 로 변환 (RC 파일 규칙)
		String safePath = icopath.replace("\\", "\\\\");
		
		// 반드시 큰따옴표로 감싸야 windres가 제대로 읽음
		return "IDI_ICON1 ICON \"" + safePath + "\"";
	}
	
	public static String CodeEdit(String classstr,String guistr,String javastr)
	{
		String codecstr = "";
		codecstr = "#include <windows.h>\n";
		codecstr+="#include <stdio.h>\n";
		codecstr+="#include <io.h>\n";
		codecstr+="#include <string.h>\n";
		codecstr+="void get_cpu_vendor(char *vendor) {\n";
		codecstr+="unsigned int ebx, ecx, edx;\n";
		codecstr+="asm volatile(\n";
		codecstr+="\"mov $0, %%eax\\n\\t\" ";
		codecstr+="\"cpuid\\n\\t\"";
		codecstr+="\"mov %%ebx, %0\\n\\t\"";
		codecstr+="\"mov %%ecx, %1\\n\\t\"";
		codecstr+="\"mov %%edx, %2\\n\\t\"";
		codecstr+=": \"=r\" (ebx), \"=r\" (ecx), \"=r\" (edx)\n";
		codecstr+=":\n";
		codecstr+=": \"eax\", \"ebx\", \"ecx\", \"edx\"\n";
		codecstr+=");\n";
		codecstr+="memcpy(vendor, &ebx, 4);\n";
		codecstr+="memcpy(vendor + 4, &edx, 4);\n";
		codecstr+="memcpy(vendor + 8, &ecx, 4);\n";
		codecstr+="vendor[12] = '\\0';\n";
		codecstr+="}\n";
		
		codecstr+="int main(int argc, char *argv[]){\n";
		codecstr+="char vendor[13];\n";
		codecstr+="get_cpu_vendor(vendor);\n";
		codecstr+="char *tmpc=\"\";\n";
		
		codecstr+="if (strcmp(vendor, \"GenuineIntel\") == 0) {\n";
		codecstr+="tmpc=\"\\\\\";\n";
		codecstr+="}else if (strcmp(vendor, \"AuthenticAMD\") == 0) {\n";
		
		codecstr+="}\n";
		codecstr+="FreeConsole();\n";
		codecstr+="STARTUPINFO si = {0};\n";
		codecstr+="PROCESS_INFORMATION pi = {0};\n";
		codecstr+="si.cb = sizeof(si);\n";
		codecstr+="char *path = getenv(\"PATH\");\n";
		codecstr+="char *javaPath = (char*)malloc(1024);\n";
		codecstr+="char *tmpPath = (char*)malloc(1024);\n";
		codecstr+="if (path == NULL) {MessageBox(NULL,\"You don't have Path.\",\"noPath\",MB_OK | MB_ICONINFORMATION);}\n";
		codecstr+="char *token = strtok(path, \";\");\n";
		codecstr+="while (token != NULL) {\n";
		codecstr+="token = strtok(NULL, \";\");\n";
		codecstr+="snprintf(javaPath, 1024, \"%s%s";
		
		codecstr+=javastr;
		//codecstr+="java.exe";//여기도...
		codecstr+="\", token,tmpc);\n";
		codecstr+="if (_access(javaPath, 0) == 0) { \n";
		codecstr+="strcpy(tmpPath,javaPath);\n";
		codecstr+="break;}\n}\n";
		codecstr+="if (CreateProcess((LPSTR)tmpPath,(LPSTR)\" ";
		codecstr+=classstr;
		//codecstr+="-jar test.jar";//여기서 하고 여기서 자바실행
		codecstr+="\",NULL, NULL, FALSE,";
		codecstr+=guistr;
		//codecstr+="CREATE_NEW_CONSOLE";//gui인지 콘솔인지 확인하는 창 "CREATE_NO_WINDOW"하면 gui만
		codecstr+=",NULL, NULL, &si, &pi)) {\n";
		codecstr+="WaitForSingleObject(pi.hProcess, INFINITE);\n";
		codecstr+="CloseHandle(pi.hProcess);\n";
		codecstr+="CloseHandle(pi.hThread);\n}\n";
		codecstr+="free(javaPath);\n";
		codecstr+="free(tmpPath);\n";
		codecstr+="return 0;\n}";
		return codecstr;
	}
	public static String CodeEdit2(String classstr,String guistr,String javastr,String javapath)
	{
		String codecstr = "";
		codecstr = "#include <windows.h>\n";
		codecstr+="#include <stdio.h>\n";
		codecstr+="#include <io.h>\n";
		codecstr+="#include <string.h>\n";
		
		
		codecstr+="int main(int argc, char *argv[]){\n";
		
		
		codecstr+="FreeConsole();\n";
		codecstr+="STARTUPINFO si = {0};\n";
		codecstr+="PROCESS_INFORMATION pi = {0};\n";
		codecstr+="si.cb = sizeof(si);\n";
		codecstr+="char *path = \"";
		codecstr += javapath + "\";\n";
		codecstr+="char *javaPath = (char*)malloc(1024);\n";
		codecstr+="char *tmpPath = (char*)malloc(1024);\n";
		codecstr+="if (path == NULL) {MessageBox(NULL,\"You don't have Path.\",\"noPath\",MB_OK | MB_ICONINFORMATION);}\n";
		
		codecstr+="snprintf(javaPath, 1024, \"%s";
		
		codecstr+=javastr;
		//codecstr+="java.exe";//여기도...
		codecstr+="\", path);\n";
		codecstr+="if (_access(javaPath, 0) == 0) { \n";
		codecstr+="strcpy(tmpPath,javaPath);\n";
		codecstr+="}else {\n";
		codecstr+="MessageBox(NULL,\"You don't have javaPath.\",\"noPath\",MB_OK | MB_ICONINFORMATION);}\n";
		codecstr+="if (CreateProcess((LPSTR)tmpPath,(LPSTR)\" ";
		codecstr+=classstr;
		//codecstr+="-jar test.jar";//여기서 하고 여기서 자바실행
		codecstr+="\",NULL, NULL, FALSE,";
		codecstr+=guistr;
		//codecstr+="CREATE_NEW_CONSOLE";//gui인지 콘솔인지 확인하는 창 "CREATE_NO_WINDOW"하면 gui만
		codecstr+=",NULL, NULL, &si, &pi)) {\n";
		codecstr+="WaitForSingleObject(pi.hProcess, INFINITE);\n";
		codecstr+="CloseHandle(pi.hProcess);\n";
		codecstr+="CloseHandle(pi.hThread);\n}\n";
		codecstr+="free(javaPath);\n";
		codecstr+="free(tmpPath);\n";
		codecstr+="return 0;\n}";
		return codecstr;
	}
	public static void GCCExcute(String rcCode,String cCode,String gccpath,String windrespath,String exename,String exepath)
	{
		String fileName = "temp\\temp.c";
		String rcName = "temp\\temp.rc";
		// 파일 쓰기
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write(cCode);
			System.out.println("temp.c 파일이 생성되었습니다!");
		} catch (IOException e) {
			System.err.println("파일 생성 중 오류가 발생했습니다: " + e.getMessage());
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(rcName))) {
			writer.write(rcCode);
			System.out.println("temp.rc 파일이 생성되었습니다!");
		} catch (IOException e) {
			System.err.println("파일 생성 중 오류가 발생했습니다: " + e.getMessage());
		}
		// GCC 명령어 (main.c를 컴파일하고 실행 파일을 생성)
		String nowdir = System.getProperty("user.dir");
		
		
		/*String windrescommand="";
		windrescommand = windrespath + " " + nowdir+"\\temp\\temp.rc -o "+ nowdir + "\\temp\\temp.o";
		String gccCommand="";
		System.out.println(windrescommand);
		System.out.println(exepath);
		System.out.println(exename);
		
		gccCommand = gccpath + " "+nowdir+"\\temp\\temp.c " + nowdir+"\\temp\\temp.o -o "+ exepath + exename;
		System.out.println(gccCommand);
		*/
		String rcPath = nowdir+"\\temp\\temp.rc";
		String oPath =  nowdir + "\\temp\\temp.o";
		String cPath = nowdir + "\\temp\\temp.c";
		String outExe = exepath + exename;
		/*
		try {
		// 프로세스 실행
		Process process;
		// 새로운 방식 (경고 없음)
		ProcessBuilder builder = new ProcessBuilder(windrescommand.split(" "));
		process = builder.start();
		builder = new ProcessBuilder(gccCommand.split(" "));
		process = builder.start();
		
		//process = Runtime.getRuntime().exec(windrescommand);
		//process = Runtime.getRuntime().exec(gccCommand);
		JOptionPane.showMessageDialog(null, "app이 실행되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
		
		// 명령어 출력 읽기
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		
		String line;
		System.out.println("GCC 출력 결과:");
		while ((line = input.readLine()) != null) {
		System.out.println(line);
		}
		
		System.out.println("GCC 오류 메시지:");
		while ((line = error.readLine()) != null) {
		System.err.println(line);
		}
		
		// 프로세스 종료 대기
		int exitCode = process.waitFor();
		System.out.println("GCC 프로세스 종료 코드: " + exitCode);
		
		} catch (Exception e) {
		e.printStackTrace();
		System.err.println("GCC 실행 중 오류 발생: " + e.getMessage());
		}*/
		try {
			// -----------------------------
			// 1) windres 실행
			// -----------------------------
			ProcessBuilder windresBuilder = new ProcessBuilder(
				windrespath,   // windres.exe
				rcPath,        // temp.rc
				"-o",
				oPath          // temp.o
				);
			
			windresBuilder.redirectErrorStream(true);
			Process windresProcess = windresBuilder.start();
			
			// windres 출력 읽기
			try (BufferedReader br = new BufferedReader(new InputStreamReader(windresProcess.getInputStream()))) {
				br.lines().forEach(System.out::println);
			}
			
			int windresExit = windresProcess.waitFor();
			if (windresExit != 0) {
				JOptionPane.showMessageDialog(null, "windres 실행 실패!", "오류", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// temp.o 존재 확인
			File oFile = new File(oPath);
			if (!oFile.exists()) {
				JOptionPane.showMessageDialog(null, "temp.o 파일이 생성되지 않았습니다!", "오류", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// -----------------------------
			// 2) gcc 실행
			// -----------------------------
			ProcessBuilder gccBuilder = new ProcessBuilder(
				gccpath,   // gcc.exe
				cPath,     // temp.c
				oPath,     // temp.o
				"-o",
				outExe     // 출력 exe
				);
			
			gccBuilder.redirectErrorStream(true);
			Process gccProcess = gccBuilder.start();
			
			// gcc 출력 읽기
			try (BufferedReader br = new BufferedReader(new InputStreamReader(gccProcess.getInputStream()))) {
				br.lines().forEach(System.out::println);
			}
			
			int gccExit = gccProcess.waitFor();
			if (gccExit != 0) {
				JOptionPane.showMessageDialog(null, "gcc 컴파일 실패!", "오류", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			JOptionPane.showMessageDialog(null, "EXE 생성 완료!", "성공", JOptionPane.INFORMATION_MESSAGE);
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "예외 발생: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run()
			{
				exec = new AntEXECreater();
				exec.InitExeCreate();
				
			}
		});
	}
}
