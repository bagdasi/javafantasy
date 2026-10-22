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
class AntJARCreatev0_0024 
{
	public static AntJARCreatev0_0024 antjar;
	public static JFrame frame;
	public static JRadioButton classradio,allradio;
	//JRadioButton cmdradio,guiradio;
	public static ButtonGroup group1;
	public static JLabel inputpathlabel,namelabel,maniverlabel,mainclasslabel,createbylabel,filelistlabel,openlabel;
	public static JPanel inputpanel,namepanel,maniverpanel,mainclasspanel,createbypanel,filelistpanel,radio1panel,btnpanel;
	public static JButton inputpathbtn,fileaddbtn,filedelbtn,makebtn,cancelbtn,filealldelbtn;
	public static JTextField inputpathtxt,nametxt,manivertxt,mainclasstxt,createbytxt;
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
		
		
		frame = new JFrame("앤트JAR생성기v0.0024");
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
					JFileChooser chooser = new JFileChooser(new File(currentDir));
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
					JFileChooser chooser = new JFileChooser(new File(currentDir));
					
					// 파일 및 폴더 모두 선택 가능하도록 설정
					chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					//chooser.setCurrentDirectory(new File(currentDir));
					chooser.setSelectedFile(new File(currentDir));
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
				jarFilePath += "\\"+nametxt.getText().toString();
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
		verlabel = new JLabel("버전 : v0.0024");
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
        attributes.put(new Attributes.Name("Created-By"), createbytxt.getText()); // 임의 값 설정 가능
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
	private static void addToJar(JarOutputStream jarOut, File source, String basePath) throws IOException {
        if (source.isDirectory()) {
            // 폴더라면 내부 파일들을 재귀적으로 추가
            for (File file : Objects.requireNonNull(source.listFiles())) {
				String entryName = source.getAbsolutePath().substring(basePath.length() + 1).replace("\\", "/");
				JarEntry entry = new JarEntry(entryName);
				jarOut.putNextEntry(entry);

                // 폴더 내부 파일들을 재귀적으로 추가
				for (File subfile : Objects.requireNonNull(source.listFiles())) {
					addToJar(jarOut, subfile, basePath);
				}

            }
        } else {
            // 파일 추가
            String entryName = source.getAbsolutePath().substring(basePath.length() + 1).replace("\\", "/");
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
				String entryName = source.getAbsolutePath().substring(new File(basePath).getParentFile().getAbsolutePath().length() + 1).replace("\\", "/");
				JarEntry entry = new JarEntry(entryName);
				jarOut.putNextEntry(entry);

                // 폴더 내부 파일들을 재귀적으로 추가
				for (File subfile : Objects.requireNonNull(source.listFiles())) {
					addToJar(jarOut, subfile, basePath);
				}

            }
        } else {
            // 파일 추가
            String entryName = source.getAbsolutePath().substring(new File(basePath).getParentFile().getAbsolutePath().length() + 1).replace("\\", "/");
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
		antjar = new AntJARCreatev0_0024();
		antjar.CreateInit();
	}
}
