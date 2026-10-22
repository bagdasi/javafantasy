import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.FileFilter;
import java.util.jar.*;
import java.lang.*;
class AntJARCreatev0_0027
{
	public static AntJARCreatev0_0027 antjar;
	public static JFrame frame;
	public static JRadioButton classradio,allradio;
	//JRadioButton cmdradio,guiradio;
	public static ButtonGroup group1;
	public static JLabel inputpathlabel,namelabel,maniverlabel,mainclasslabel,createbylabel,filelistlabel,openlabel;
	public static JPanel inputpanel,namepanel,maniverpanel,mainclasspanel,createbypanel,filelistpanel,radio1panel,btnpanel;
	public static JButton inputpathbtn,fileaddbtn,filedelbtn,makebtn,cancelbtn,filealldelbtn;
	public static JTextField inputpathtxt,nametxt,manivertxt,mainclasstxt,createbytxt;
	//0.0026추가
	public static JLabel classpathlabel;
	public static JPanel classpathpanel;
	public static JTextField classpathtxt;
	public static JButton cpwizardbtn;
	//여기까지...
	public static JList<String> filelist;
	public static List<File> tmplist;
	static boolean AllSelected = false,foldersel = false;;
	public static JPanel infopanel,radio2panel;
	public static JLabel appinfolabel,uselabel,verlabel;
	public static JCheckBox foldercbox;
	public static String currentDir = "";
	
	public static void CreateInit()
	{
		
		tmplist = new ArrayList<File>();
		
		
		frame = new JFrame("앤트JAR생성기v0.0027");
		// 아이콘 이미지 불러오기
		Image icon = Toolkit.getDefaultToolkit().getImage("ant.png");
		frame.setIconImage(icon);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,800);
		frame.setLocation(300,50);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
		inputpanel = new JPanel();
		inputpanel.setLayout(new FlowLayout());
		inputpathlabel = new JLabel("Jar생성경로 : ");
		inputpathlabel.setFont(new Font("Serif",Font.PLAIN,20));
		inputpathtxt = new JTextField();
		inputpathtxt.setColumns(30);
		inputpathtxt.setFont(new Font("Serif",Font.PLAIN,20));
		inputpathbtn = new JButton("...");
		inputpathbtn.setFont(new Font("Serif",Font.PLAIN,20));
		inputpathbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try{
					
					
					
					
					
					try (BufferedReader reader = new BufferedReader(new FileReader("currentDir.txt"))) {
						currentDir = reader.readLine(); // 첫 줄만 읽음
						System.out.println("불러온 currentDir: " + currentDir);
					} catch (IOException ioe) {
						System.out.println("파일 읽기 중 오류 발생: " + ioe.getMessage());
					}
					if (currentDir == null || currentDir.trim().isEmpty()) {
						currentDir = ".";
					}
					File cfile = new File(currentDir);
					if (!cfile.exists() || !cfile.isDirectory()) {
						cfile = new File(".");   // 현재 폴더로 대체
					}
					JFileChooser chooser = new JFileChooser(cfile);
					// 파일 및 폴더 모두 선택 가능하도록 설정
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					chooser.setAcceptAllFileFilterUsed(false);
					
					
					
					//chooser.setCurrentDirectory(new File(currentDir));
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
					currentDir = selectedFile.toString();
					try {
						FileWriter writer = new FileWriter("currentDir.txt");
						writer.write(currentDir);
						writer.close();
						System.out.println("currentDir.txt에 저장 완료!");
					} catch (IOException ioe) {
						System.out.println("파일 저장 중 오류 발생: " + ioe.getMessage());
					}
					inputpathtxt.setText("");
					inputpathtxt.setText(selectedFile.toString());
					//폴더및 파일
					/*if (returnValue == JFileChooser.APPROVE_OPTION) {
					
					} else {
					
					}*/
				}catch(Exception ioe)
				{
					
				}
			}
		});
		inputpanel.add(inputpathlabel);
		inputpanel.add(inputpathtxt);
		inputpanel.add(inputpathbtn);
		frame.add(inputpanel);
		
		namepanel = new JPanel();
		namepanel.setLayout(new FlowLayout());
		namelabel = new JLabel("JAR이름 : ");
		namelabel.setFont(new Font("Serif",Font.PLAIN,20));
		nametxt = new JTextField();
		nametxt.setColumns(30);
		nametxt.setText("test.jar");
		nametxt.setFont(new Font("Serif",Font.PLAIN,20));
		namepanel.add(namelabel);
		namepanel.add(nametxt);
		frame.add(namepanel);
		
		
		maniverpanel = new JPanel();
		maniverpanel.setLayout(new FlowLayout());
		maniverlabel = new JLabel("MANIFEST버전 : ");
		maniverlabel.setFont(new Font("Serif",Font.PLAIN,20));
		manivertxt = new JTextField();
		manivertxt.setColumns(30);
		manivertxt.setText("1.0");
		manivertxt.setFont(new Font("Serif",Font.PLAIN,20));
		maniverpanel.add(maniverlabel);
		maniverpanel.add(manivertxt);
		frame.add(maniverpanel);
		
		mainclasspanel = new JPanel();
		mainclasspanel.setLayout(new FlowLayout());
		mainclasslabel = new JLabel("Main_Class : ");
		mainclasslabel.setFont(new Font("Serif",Font.PLAIN,20));
		mainclasstxt = new JTextField();
		mainclasstxt.setColumns(30);
		mainclasstxt.setFont(new Font("Serif",Font.PLAIN,20));
		mainclasspanel.add(mainclasslabel);
		mainclasspanel.add(mainclasstxt);
		frame.add(mainclasspanel);
		
		//0.0026추가
		classpathpanel = new JPanel();
		classpathpanel.setLayout(new FlowLayout());
		classpathlabel = new JLabel("Class_Path : ");
		classpathlabel.setFont(new Font("Serif",Font.PLAIN,20));
		classpathtxt = new JTextField();
		classpathtxt.setColumns(30);
		classpathtxt.setFont(new Font("Serif",Font.PLAIN,20));
		cpwizardbtn = new JButton("cp선택");
		cpwizardbtn.setFont(new Font("Serif",Font.PLAIN,20));
		cpwizardbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Dialog 생성
				JDialog dialog = new JDialog(frame, "cp선택", true);
				dialog.setSize(500, 400);
				dialog.setLayout(new BorderLayout());
				
				// -------------------------
				// 1. 상단 패널
				// -------------------------
				JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				JLabel topLabel = new JLabel("Class_Path : ");
				topLabel.setFont(new Font("Serif", Font.PLAIN, 18));
				topPanel.add(topLabel);
				dialog.add(topPanel, BorderLayout.NORTH);
				
				// -------------------------
				// 2. 중앙 패널
				// -------------------------
				JPanel centerPanel = new JPanel();
				centerPanel.setLayout(new BorderLayout());
				
				// JList (클래스패스 목록)
				DefaultListModel<String> listModel = new DefaultListModel<>();
				JList<String> cpList = new JList<>(listModel);
				JScrollPane scrollPane = new JScrollPane(cpList);
				
				// 버튼 패널 (세로 정렬)
				JPanel btnPanel = new JPanel();
				btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
				JButton folderaddBtn = new JButton("폴더추가");
				JButton addBtn = new JButton("추가");
				JButton delBtn = new JButton("삭제");
				JButton clearBtn = new JButton("모두삭제");
				
				folderaddBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
				addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
				delBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
				clearBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				btnPanel.add(folderaddBtn);
				btnPanel.add(Box.createVerticalStrut(10));
				btnPanel.add(addBtn);
				btnPanel.add(Box.createVerticalStrut(10));
				btnPanel.add(delBtn);
				btnPanel.add(Box.createVerticalStrut(10));
				btnPanel.add(clearBtn);
				
				// 중앙 패널에 추가
				centerPanel.add(scrollPane, BorderLayout.CENTER);
				centerPanel.add(btnPanel, BorderLayout.EAST);
				
				dialog.add(centerPanel, BorderLayout.CENTER);
				
				// -------------------------
				// 3. 하단 패널
				// -------------------------
				JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
				
				JButton okBtn = new JButton("cp추가");
				JButton cancelBtn = new JButton("취소");
				
				bottomPanel.add(okBtn);
				bottomPanel.add(cancelBtn);
				
				dialog.add(bottomPanel, BorderLayout.SOUTH);
				
				// -------------------------
				// 버튼 이벤트
				// -------------------------
				//폴더추가버튼
				folderaddBtn.addActionListener(ev -> {
					try (BufferedReader reader = new BufferedReader(new FileReader("currentDir.txt"))) {
						currentDir = reader.readLine(); // 첫 줄만 읽음
						System.out.println("불러온 currentDir: " + currentDir);
					} catch (IOException ioe) {
						System.out.println("파일 읽기 중 오류 발생: " + ioe.getMessage());
					}
					if (currentDir == null || currentDir.trim().isEmpty()) {
						currentDir = ".";
					}
					
					File cfile = new File(currentDir);
					if (!cfile.exists() || !cfile.isDirectory()) {
						cfile = new File(".");   // 현재 폴더로 대체
					}
					JFileChooser chooser = new JFileChooser(cfile);
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 폴더만 선택
					chooser.setAcceptAllFileFilterUsed(false);
					
					int result = chooser.showOpenDialog(dialog);
					if (result != JFileChooser.APPROVE_OPTION) return;
					
					File selectedFolder = chooser.getSelectedFile();
					if (selectedFolder == null) return;
					/*
					// -----------------------------
					// 선택한 폴더 + 하위 폴더 전체 추가
					// -----------------------------
					List<File> folderList = new ArrayList<>();
					getAllFolders(selectedFolder, folderList);
					
					for (File folder : folderList) {
					String baseName = selectedFolder.getName(); // libs
					String full =folder.getAbsolutePath();      // jar 파일 전체 경로
					String base = selectedFolder.getAbsolutePath(); // 선택한 폴더
					
					
					String relative = baseName + full.substring(base.length());
					if (relative.startsWith(File.separator)) {
					relative = relative.substring(1);
					}
					listModel.addElement(relative);
					
					}*/
					String baseName = selectedFolder.getName(); // libs
					String full =selectedFolder.getAbsolutePath();      // jar 파일 전체 경로
					String base = selectedFolder.getAbsolutePath(); // 선택한 폴더
					base = inputpathtxt.getText().toString();
					String relative = full.substring(base.length()) + File.separator;
					if (relative.startsWith(File.separator)) {
						relative = relative.substring(1);
					}
					listModel.addElement(relative);
					
					cpList.revalidate();
					cpList.repaint();
				});
				// 추가 버튼
				addBtn.addActionListener(ev -> {
					try (BufferedReader reader = new BufferedReader(new FileReader("currentDir.txt"))) {
						currentDir = reader.readLine(); // 첫 줄만 읽음
						System.out.println("불러온 currentDir: " + currentDir);
					} catch (IOException ioe) {
						System.out.println("파일 읽기 중 오류 발생: " + ioe.getMessage());
					}
					if (currentDir == null || currentDir.trim().isEmpty()) {
						currentDir = ".";
					}
					
					File cfile = new File(currentDir);
					if (!cfile.exists() || !cfile.isDirectory()) {
						cfile = new File(".");   // 현재 폴더로 대체
					}
					JFileChooser chooser = new JFileChooser(cfile);
					chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					chooser.setAcceptAllFileFilterUsed(false);
					
					// JAR 파일 필터
					chooser.addChoosableFileFilter(new FileFilter() {
						@Override
						public boolean accept(File f) {
							return f.isDirectory() || f.getName().toLowerCase().endsWith(".jar");
						}
						
						@Override
						public String getDescription() {
							return "JAR 파일 (*.jar)";
						}
					});
					
					int result = chooser.showOpenDialog(dialog);
					if (result != JFileChooser.APPROVE_OPTION) return;
					
					File selected = chooser.getSelectedFile();
					
					// -----------------------------
					// 1) 선택한 것이 폴더일 때
					// -----------------------------
					if (selected.isDirectory()) {
						
						// 폴더 자체 경로도 추가
						//listModel.addElement(selected.getAbsolutePath());
						
						// 하위 폴더까지 jar 파일 모두 수집
						List<File> jarFiles = new ArrayList<>();
						getJarFiles(selected, jarFiles);
						
						for (File jar : jarFiles) {
							String baseName = selected.getName(); // libs
							String full = jar.getAbsolutePath();      // jar 파일 전체 경로
							
							String base = selected.getAbsolutePath(); // 선택한 폴더
							base = inputpathtxt.getText().toString();
							
							String relative = full.substring(base.length());
							if (relative.startsWith(File.separator)) {
								relative = relative.substring(1);
							}
							listModel.addElement(relative);
						}
						
						cpList.revalidate();
						cpList.repaint();
						return;
					}
					
					// -----------------------------
					// 2) 선택한 것이 파일일 때
					// -----------------------------
					if (selected.isFile()) {
						if (selected.getName().toLowerCase().endsWith(".jar")) {
							String baseName = selected.getName(); // libs
							String full =selected.getAbsolutePath();      // jar 파일 전체 경로
							String base = selected.getAbsolutePath(); // 선택한 폴더
							base = inputpathtxt.getText().toString();
							String relative = full.substring(base.length());
							if (relative.startsWith(File.separator)) {
								relative = relative.substring(1);
							}
							listModel.addElement(relative);
						} else {
							JOptionPane.showMessageDialog(dialog, "JAR 파일만 선택할 수 있습니다.");
						}
					}
					
					cpList.revalidate();
					cpList.repaint();
				});
				
				
				// 삭제 버튼
				delBtn.addActionListener(ev -> {
					int idx = cpList.getSelectedIndex();
					if (idx != -1) {
						listModel.remove(idx);
						// 리스트가 비어있지 않으면 선택 유지
						int size = listModel.getSize();
						if (size > 0) {
							// 삭제한 위치가 리스트 범위를 벗어나면 마지막 요소 선택
							if (idx >= size) {
								idx = size - 1;
							}
							cpList.setSelectedIndex(idx);
						}

					}
				});
				
				// 모두삭제 버튼
				clearBtn.addActionListener(ev -> listModel.clear());
				
				// cp추가 버튼
				okBtn.addActionListener(ev -> {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < listModel.size(); i++) {
						sb.append(listModel.get(i));
						if (i < listModel.size() - 1) sb.append(" ");
					}
					classpathtxt.setText(sb.toString());
					dialog.dispose();
				});
				
				// 취소 버튼
				cancelBtn.addActionListener(ev -> dialog.dispose());
				
				dialog.setLocationRelativeTo(frame);
				dialog.setVisible(true);
			}
		});
		
		classpathpanel.add(classpathlabel);
		classpathpanel.add(classpathtxt);
		classpathpanel.add(cpwizardbtn);
		frame.add(classpathpanel);
		
		createbypanel = new JPanel();
		createbypanel.setLayout(new FlowLayout());
		createbylabel = new JLabel("Create by : ");
		createbylabel.setFont(new Font("Serif",Font.PLAIN,20));
		createbytxt = new JTextField();
		createbytxt.setColumns(30);
		createbytxt.setFont(new Font("Serif",Font.PLAIN,20));
		createbypanel.add(createbylabel);
		createbypanel.add(createbytxt);
		frame.add(createbypanel);
		// JList의 데이터를 관리할 DefaultListModel 생성
		DefaultListModel<String> listModel = new DefaultListModel<>();
		radio1panel = new JPanel();
		radio1panel.setLayout(new FlowLayout());
		classradio = new JRadioButton("class파일");
		classradio.setFont(new Font("Serif",Font.PLAIN,20));
		classradio.setSelected(true);
		allradio = new JRadioButton("모든파일");
		allradio.setFont(new Font("Serif",Font.PLAIN,20));
		group1 = new ButtonGroup();
		group1.add(classradio);
		group1.add(allradio);
		foldercbox = new JCheckBox("상위폴더");
		foldercbox.setFont(new Font("Serif",Font.PLAIN,20));
		radio2panel = new JPanel();
		radio2panel.setLayout(new FlowLayout());
		fileaddbtn = new JButton("추가");
		fileaddbtn.setFont(new Font("Serif",Font.PLAIN,20));
		fileaddbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try{
					
					if(classradio.isSelected())
					{
						AllSelected = false;
					}
					if(allradio.isSelected())
					{
						AllSelected = true;
					}
					
					
					
					
					try (BufferedReader reader = new BufferedReader(new FileReader("currentDir.txt"))) {
						currentDir = reader.readLine(); // 첫 줄만 읽음
						System.out.println("불러온 currentDir: " + currentDir);
					} catch (IOException ioe) {
						System.out.println("파일 읽기 중 오류 발생: " + ioe.getMessage());
					}
					if (currentDir == null || currentDir.trim().isEmpty()) {
						currentDir = ".";
					}
					
					File cfile = new File(currentDir);
					if (!cfile.exists() || !cfile.isDirectory()) {
						cfile = new File(".");   // 현재 폴더로 대체
					}
					JFileChooser chooser = new JFileChooser(cfile);
					
					
					// 파일 및 폴더 모두 선택 가능하도록 설정
					chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					//chooser.setCurrentDirectory(new File(currentDir));
					if(currentDir.equals("."))
					{
						System.out.println(".입니다.");
						chooser.setSelectedFile(null);
						
					}else{
						chooser.setSelectedFile(new File(currentDir));
					}
					chooser.setAcceptAllFileFilterUsed(false); // "모든 파일" 필터 비활성화
					if(!AllSelected)
					{
						chooser.setAcceptAllFileFilterUsed(false); // "모든 파일" 필터 비활성화
					}else {
						chooser.setAcceptAllFileFilterUsed(true); // "모든 파일" 필터 비활성화
					}
					chooser.addChoosableFileFilter(new FileFilter() {
						@Override
						public boolean accept(File file) {
							// 폴더 또는 .qp 파일 허용
							if(!AllSelected)
							{
								return file.isDirectory() || file.getName().toLowerCase().endsWith(".class");
							}else if(AllSelected)
							{
								
								return file.isDirectory() || file.isFile();
							}
							return false;
						}
						
						@Override
						public String getDescription() {
							
							if(!AllSelected)
							{
								return "폴더 및 .class 파일";
							}else if(AllSelected)
							{
								return "폴더 및 모든 파일";
							}
							return "error";
						}
					});
					
					// 다이얼로그 표시
					int returnValue = chooser.showOpenDialog(null);
					
					
					//폴더및 파일
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						// 선택한 폴더 가져오기
						File selectedFolder = chooser.getSelectedFile();
						System.out.println("선택한 폴더: " + selectedFolder.getAbsolutePath());
						if(selectedFolder.isDirectory())
						{
							currentDir = selectedFolder.toString();
							try {
								FileWriter writer = new FileWriter("currentDir.txt");
								writer.write(currentDir);
								writer.close();
								System.out.println("currentDir.txt에 저장 완료!");
							} catch (IOException ioe) {
								System.out.println("파일 저장 중 오류 발생: " + ioe.getMessage());
							}
							// 폴더 내 모든 파일 가져오기
							List<File> allFiles = new ArrayList<>();
							getAllFiles(selectedFolder, allFiles);
							
							// 가져온 파일 출력
							System.out.println("폴더와 하위 폴더의 모든 파일:");
							for (File file : allFiles) {
								if(AllSelected == false && file.getName().endsWith(".class")) {
									
									System.out.println(file.getAbsolutePath());
									listModel.addElement(file.getAbsolutePath());
									tmplist.add(file);
								}else if(AllSelected == true)
								{
									System.out.println(file.getAbsolutePath());
									listModel.addElement(file.getAbsolutePath());
									tmplist.add(file);
									
								}
							}
							
							filelist.revalidate();
							filelist.repaint();
							
						}else //if(!selectedFolder.isFile())
						{
							currentDir = selectedFolder.getParent().toString();
							try {
								FileWriter writer = new FileWriter("currentDir.txt");
								writer.write(currentDir);
								writer.close();
								System.out.println("currentDir.txt에 저장 완료!");
							} catch (IOException ioe) {
								System.out.println("파일 저장 중 오류 발생: " + ioe.getMessage());
							}
							listModel.addElement(selectedFolder.getAbsolutePath());
							tmplist.add(selectedFolder);
						}
						
					} else {
						
					}
				}catch(Exception ioe)
				{
					
				}
			}
		});
		filedelbtn = new JButton("지우기");
		filedelbtn.setFont(new Font("Serif",Font.PLAIN,20));
		filedelbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int selectedIndex = filelist.getSelectedIndex();
				try{
					listModel.remove(selectedIndex);
					tmplist.remove(selectedIndex);
					
					filelist.setSelectedIndex(selectedIndex);
				}catch(ArrayIndexOutOfBoundsException aiobe){
					
				}
			}
		});
		filealldelbtn = new JButton("모두지우기");
		filealldelbtn.setFont(new Font("Serif",Font.PLAIN,20));
		filealldelbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int selectedIndex = filelist.getSelectedIndex();
				try{
					listModel.clear();
					tmplist.clear();
					
					selectedIndex=0;
				}catch(ArrayIndexOutOfBoundsException aiobe){
					
				}
			}
		});
		foldercbox.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				foldersel = true;
			} else {
				foldersel = false;
			}
			
		});
		radio1panel.add(foldercbox);
		radio1panel.add(classradio);
		radio1panel.add(allradio);
		radio2panel.add(fileaddbtn);
		radio2panel.add(filedelbtn);
		radio2panel.add(filealldelbtn);
		filelistpanel = new JPanel();
		filelistpanel.setLayout(new BoxLayout(filelistpanel,BoxLayout.Y_AXIS));
		
		
		
		filelist = new JList<>(listModel);
		filelist.setPreferredSize(null);
		filelist.setFont(new Font("Serif",Font.PLAIN,15));
		filelist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { // 이벤트의 중복 방지
					String selectedItem = filelist.getSelectedValue();
					System.out.println("선택된 항목: " + selectedItem);
				}
			}
		});
		
		// JList를 JScrollPane에 추가
		JScrollPane scrollpane = new JScrollPane(filelist);
		filelistpanel.add(radio1panel);
		filelistpanel.add(radio2panel);
		filelistpanel.add(scrollpane);
		frame.add(filelistpanel);
		
		btnpanel = new JPanel();
		btnpanel.setLayout(new FlowLayout());
		makebtn = new JButton("jar생성");
		makebtn.setFont(new Font("Serif",Font.PLAIN,20));
		makebtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String jarFilePath = inputpathtxt.getText().toString();
				File file = new File(jarFilePath);

				if (!file.exists()) {
					JOptionPane.showMessageDialog(null,"JAR 생성 경로가 존재하지 않습니다.","경고",JOptionPane.WARNING_MESSAGE);
					return; // 함수 종료
				}
				
				String name = nametxt.getText().trim();
				// .jar이 없으면 자동으로 붙이기
				if (!name.toLowerCase().endsWith(".jar")) {
					name += ".jar";
				}
				
				jarFilePath += File.separator+name;
				String basePath = inputpathtxt.getText().toString();
				System.out.println(jarFilePath);
				try{
					jarOutMethod(jarFilePath,basePath);
				}catch(IOException ioe)
				{
					
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
		btnpanel.add(makebtn);
		btnpanel.add(cancelbtn);
		frame.add(btnpanel);
		infopanel = new JPanel();
		infopanel.setLayout(new BoxLayout(infopanel,BoxLayout.Y_AXIS));
		infopanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		verlabel = new JLabel("버전 : v0.0027");
		appinfolabel = new JLabel("만든이 : 컴퓨터판타지연구소");
		uselabel = new JLabel("jar경로는 class파일경로로 해주세요.");
		infopanel.add(verlabel);
		infopanel.add(appinfolabel);
		infopanel.add(uselabel);
		frame.add(infopanel);
		frame.setVisible(true);
		
	}
	private static Manifest createManifest() {
		Manifest manifest = new Manifest();
		Attributes attributes = manifest.getMainAttributes();
		attributes.put(Attributes.Name.MANIFEST_VERSION, manivertxt.getText().toString());
		attributes.put(Attributes.Name.MAIN_CLASS, mainclasstxt.getText().toString());
		attributes.put(new Attributes.Name("Class-Path"), classpathtxt.getText().toString());
		attributes.put(new Attributes.Name("Created-By"), createbytxt.getText().toString()); // 임의 값 설정 가능
		
		
		return manifest;
	}
	
	private static void jarOutMethod(String jarpath,String basepath) throws IOException
	{
		try(JarOutputStream jarOut = new JarOutputStream(new FileOutputStream(jarpath), createManifest())) {
			for (File file : tmplist) {
				if(foldersel)
				{
					folderaddToJar(jarOut,file,basepath);
				}else
				{
					addToJar(jarOut, file, basepath);
				}
			}
			System.out.println("JAR 파일 생성 완료: " + jarpath);
			JOptionPane.showMessageDialog(null, "JAR파일이 생성되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			
		}
	}
	// 폴더 내 모든 파일을 재귀적으로 탐색
	private static void getAllFiles(File folder, List<File> fileList) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					// 하위 폴더 탐색
					getAllFiles(file, fileList);
				} else {
					// 파일 추가
					fileList.add(file);
				}
			}
		}
		
	}
	private static void getJarFiles(File dir, List<File> jarList) {
		File[] files = dir.listFiles();
		if (files == null) return;
		
		for (File f : files) {
			if (f.isDirectory()) {
				getJarFiles(f, jarList); // 하위 폴더 재귀 탐색
			} else if (f.getName().toLowerCase().endsWith(".jar")) {
				jarList.add(f);
			}
		}
	}
	private static void getAllFolders(File dir, List<File> folderList) {
		if (dir == null || !dir.isDirectory()) return;
		
		folderList.add(dir); // 현재 폴더 추가
		
		File[] files = dir.listFiles();
		if (files == null) return;
		
		for (File f : files) {
			if (f.isDirectory()) {
				getAllFolders(f, folderList); // 재귀적으로 하위 폴더 탐색
			}
		}
	}
	
	private static void addToJar(JarOutputStream jarOut, File source, String basePath) throws IOException {
		if (source.isDirectory()) {
			// 폴더라면 내부 파일들을 재귀적으로 추가
			for (File file : Objects.requireNonNull(source.listFiles())) {
				String entryName = source.getAbsolutePath().substring(basePath.length() + 1).replace(File.separator, "/");
				JarEntry entry = new JarEntry(entryName);
				jarOut.putNextEntry(entry);
				
				// 폴더 내부 파일들을 재귀적으로 추가
				for (File subfile : Objects.requireNonNull(source.listFiles())) {
					addToJar(jarOut, subfile, basePath);
				}
				
			}
		} else {
			// 파일 추가
			String entryName = source.getAbsolutePath().substring(basePath.length() + 1).replace(File.separator, "/");
			JarEntry entry = new JarEntry(entryName);
			jarOut.putNextEntry(entry);
			
			try (FileInputStream fis = new FileInputStream(source)) {
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = fis.read(buffer)) != -1) {
					jarOut.write(buffer, 0, bytesRead);
				}
			}
			
			jarOut.closeEntry();
		}
	}
	private static void folderaddToJar(JarOutputStream jarOut, File source, String basePath) throws IOException {
		if (source.isDirectory()) {
			// 폴더라면 내부 파일들을 재귀적으로 추가
			for (File file : Objects.requireNonNull(source.listFiles())) {
				String entryName = source.getAbsolutePath().substring(new File(basePath).getParentFile().getAbsolutePath().length() + 1).replace(File.separator, "/");
				JarEntry entry = new JarEntry(entryName);
				jarOut.putNextEntry(entry);
				
				// 폴더 내부 파일들을 재귀적으로 추가
				for (File subfile : Objects.requireNonNull(source.listFiles())) {
					addToJar(jarOut, subfile, basePath);
				}
				
			}
		} else {
			// 파일 추가
			String entryName = source.getAbsolutePath().substring(new File(basePath).getParentFile().getAbsolutePath().length() + 1).replace(File.separator, "/");
			JarEntry entry = new JarEntry(entryName);
			jarOut.putNextEntry(entry);
			
			try (FileInputStream fis = new FileInputStream(source)) {
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = fis.read(buffer)) != -1) {
					jarOut.write(buffer, 0, bytesRead);
				}
			}
			
			jarOut.closeEntry();
		}
	}
	
	public static void main(String[] args)
	{
		antjar = new AntJARCreatev0_0027();
		antjar.CreateInit();
	}
}
