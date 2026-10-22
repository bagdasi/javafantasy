import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import javax.swing.filechooser.FileNameExtensionFilter;
class MirrorWordPracticev0_0023
{
	private static MirrorWordPracticev0_0023 mp;
	private static JFrame frame;
	private static JPanel rightpanel,leftpanel,viewpanel,viewbtnpanel,tablebtnpanel;
	private static JButton wordaddbtn,worddelbtn,wordchangebtn,wordupbtn,worddownbtn,mwpopenbtn,mwpsavebtn,exitbtn;
	private static JButton previewbtn,nextviewbtn,onlywordbtn,wordbtn,newbtn;
	private static DefaultTableModel model;
	private static JTable table;
	private static JLabel versionlabel,infolabel;
	private static JTree tree;
	String[][] word;
	private static BufferedImage image;
	private boolean onlywordb = false;
	
	
	void Init()
	{
		// 프레임 생성
        JFrame frame = new JFrame("미러영어단어학습기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
		frame.setLocation(300,100);
		
		rightpanel = new JPanel();
		rightpanel.setLayout(new BoxLayout(rightpanel,BoxLayout.Y_AXIS));
		rightpanel.setPreferredSize(new Dimension(350,800));
		newbtn = new JButton("새로하기");
		newbtn.setFont(new Font("Serif",Font.PLAIN,20));
		newbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				model.setRowCount(0);
				viewpanel.repaint();
			}
		});
		mwpopenbtn = new JButton("파일열기");
		mwpopenbtn.setFont(new Font("Serif",Font.PLAIN,20));
		
		mwpopenbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFrame oframe = new JFrame("파일열기");
				oframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				oframe.setSize(400,600);
				oframe.setLocation(800,200);
				DefaultMutableTreeNode root = new DefaultMutableTreeNode("word");
				tree = new JTree(root);
				JScrollPane scrollPane = new JScrollPane(tree);
        
				/*File directory = new File("word\\"); // 원하는 폴더 경로 설정
				if (directory.isDirectory()) {
					for (File file : directory.listFiles()) {
						root.add(new DefaultMutableTreeNode(file.getName()));
					}
				}*/
				loadFiles(root, new File(root.toString())); // 처음 폴더 파일 로드
				
				tree.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 1) {
							mp.treeView();
						}
					}
				});

				JScrollPane oscrollpane = new JScrollPane(tree);
				JButton openbtn = new JButton("열기");
				openbtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						try{
						TreePath path = tree.getSelectionPath();
						if (path != null) {
							Object node = path.getLastPathComponent(); // 선택한 노드 가져오기
							if (node instanceof DefaultMutableTreeNode) { // 노드 타입 확인
								DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) node;
								StringBuilder fullPath = new StringBuilder();
								Object[] nodes = path.getPath(); // 선택한 경로의 모든 노드 가져오기
								for (Object n : nodes) {
									fullPath.append(n.toString()).append(File.separator);

								}
								// 마지막 File.separator 제거
								if (fullPath.length() > 0) {
									fullPath.setLength(fullPath.length() - 1);
								}
								StringBuilder readTemp;
								BufferedReader br = null;
								int readC = 0;
								long mwpfs = 0;
								if(!fullPath.toString().endsWith(".mwp"))
								{
									
								}
								br = new BufferedReader(new InputStreamReader(new FileInputStream(fullPath.toString()),"utf-8"));
								File file = new File(fullPath.toString());
								if(file.exists() && file.isFile())
						{
							mwpfs = file.length();	
							
							//System.out.println("size : " + qpfs);
						}
						readTemp = new StringBuilder();
						while((readC = br.read())!=-1)
						{
							readTemp.append((char)readC);
					
						}
						br.close();
						if((int)(readTemp.toString().charAt(0)) == 9925 &&(int)(readTemp.toString().charAt(1)) ==9989
							&&(int)(readTemp.toString().charAt(2)) == 9989 &&(int)(readTemp.toString().charAt(3))==9948)
						{
							String[] parts = readTemp.toString().split("!|@");
							String[][] wordPairs = new String[parts.length/2][2];
							int inc=1;
							model.setRowCount(0);
							for(int i=0;i<wordPairs.length;i++)
							{
								wordPairs[i][0] = parts[inc];
								wordPairs[i][1] = parts[inc+1];
								inc+=2;
								model.addRow(new Object[]{ wordPairs[i][0], wordPairs[i][1]});

							}
						
						
					}else
					{
						JOptionPane.showMessageDialog(
							null,
							"파일이 없습니다.",
							"알림",
							JOptionPane.INFORMATION_MESSAGE
						);

					}
							}
						}
						}catch(IOException ioe)
						{
							
						}
						table.setRowSelectionInterval(0, 0); // 첫 번째 행 선택 (인덱스 0)
						oframe.dispose();
					}
				});
				JButton addbtn = new JButton("폴더추가");
				addbtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						JFrame forderaddframe = new JFrame("폴더추가");
						forderaddframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						forderaddframe.setSize(400,150);
						forderaddframe.setLocation(600,400);
						JPanel forderpanel = new JPanel(new FlowLayout());
						JLabel forderlabel = new JLabel("폴더이름 : ");
						JTextField fordertxt = new JTextField(20);
						forderpanel.add(forderlabel);
						forderpanel.add(fordertxt);
						JPanel btnpanel = new JPanel(new FlowLayout());
						JButton okbtn = new JButton("추가");
						okbtn.addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent e)
							{
								String forderpath = "word\\"+fordertxt.getText();
								File forder = new File(forderpath);
								if(!forder.exists())
								{
									if(forder.mkdir())
									{
										JOptionPane.showMessageDialog(null, "폴더 생성 성공. " );

									}else{
										JOptionPane.showMessageDialog(null, "폴더 생성 실패.");

									}
								}else
								{
									JOptionPane.showMessageDialog(null, "폴더가 이미 존재합니다", "알림", JOptionPane.INFORMATION_MESSAGE);

								}
								forderaddframe.dispose();
								root.removeAllChildren();
								loadFiles(root, new File(root.toString())); // 처음 폴더 파일 로드
								((DefaultTreeModel) tree.getModel()).reload(root);
								
								oframe.revalidate();
							}
						});
						JButton cancelbtn = new JButton("닫기");
						cancelbtn.addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent e)
							{
								forderaddframe.dispose();
							}
						});
						btnpanel.add(okbtn);
						btnpanel.add(cancelbtn);
						
						forderaddframe.add(forderpanel,BorderLayout.NORTH);
						forderaddframe.add(btnpanel,BorderLayout.SOUTH);
						
						forderaddframe.setVisible(true);
						
					}
				});
				JButton delbtn = new JButton("삭제");
				delbtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						TreePath path = tree.getSelectionPath();
						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
						File selectedFile = new File(path.getPath()[0].toString(), selectedNode.toString());
						if (selectedFile.isDirectory()) {
							mp.deleteFolder(selectedFile);
							root.removeAllChildren(); // 기존 항목 삭제 후 새 목록 추가
							loadFiles(root, new File(root.toString())); // 처음 폴더 파일 로드
							((DefaultTreeModel) tree.getModel()).reload(root);
						}else
						{
							selectedFile.delete();
							root.removeAllChildren(); // 기존 항목 삭제 후 새 목록 추가
							loadFiles(root, new File(root.toString())); // 처음 폴더 파일 로드
							((DefaultTreeModel) tree.getModel()).reload(root);
						}
						oframe.revalidate();
						
					}
				});
				JButton movebtn = new JButton("이동");
				movebtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						JFrame mframe = new JFrame("이동");
						mframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						mframe.setSize(400,300);
						mframe.setLocation(600,300);
						JLabel movelabel = new JLabel("이동 : ");
						JComboBox<String> forderbox = new JComboBox<>();
						JPanel combopanel = new JPanel(new FlowLayout());
						combopanel.add(movelabel);
						combopanel.add(forderbox);
						File forder = new File("word");
						forderbox.addItem("word");
						if(forder.exists() && forder.isDirectory())
						{
							File[] forders = forder.listFiles(File::isDirectory);
							for(File file: forders)
							{
								forderbox.addItem(file.getName());
							}
						}
						mframe.add(combopanel,BorderLayout.NORTH);
						
						JPanel btnpanel = new JPanel(new FlowLayout());
						JButton movebtn = new JButton("이동");
						JButton cancelbtn = new JButton("닫기");
						btnpanel.add(movebtn);
						btnpanel.add(cancelbtn);
						mframe.add(btnpanel,BorderLayout.SOUTH);
						movebtn.addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent e)
							{
								TreePath path = tree.getSelectionPath();
								if (path != null) {
									Object node = path.getLastPathComponent(); // 선택한 노드 가져오기
									if (node instanceof DefaultMutableTreeNode) { // 노드 타입 확인
										String nodeName = node.toString();// 노드 이름 가져오기
										DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) node;
										StringBuilder fullPath = new StringBuilder();
										Object[] nodes = path.getPath(); // 선택한 경로의 모든 노드 가져오기
										for (Object n : nodes) {
											fullPath.append(n.toString()).append(File.separator);

										}
										 // 마지막 File.separator 제거
										 if (fullPath.length() > 0) {
											fullPath.setLength(fullPath.length() - 1);
										}

										if ((String)forderbox.getSelectedItem()!="word") { // 문자열 비교
											String selectedFolder = (String) forderbox.getSelectedItem();
											File fileToMove = new File(fullPath.toString());
											File forderpath = new File("word\\",selectedFolder);
											
											if(fileToMove.exists() && forderpath.exists() && !fileToMove.isDirectory())
											{
												File renamefile = new File(forderpath,fileToMove.getName());
												if(fileToMove.renameTo(renamefile))
												{
													JOptionPane.showMessageDialog(null, "이동 성공. " );
												}else
												{
													JOptionPane.showMessageDialog(null, "이동 실패. " );
												}
											}
										}else
										{
											String selectedFolder = (String) forderbox.getSelectedItem();
											File fileToMove = new File(fullPath.toString());
											File forderpath = new File("word\\");
											System.out.println(fileToMove.toString());
											if(fileToMove.exists() && forderpath.exists() && !fileToMove.isDirectory())
											{
												File renamefile = new File(forderpath,fileToMove.getName());
												if(fileToMove.renameTo(renamefile))
												{
													JOptionPane.showMessageDialog(null, "이동 성공. " );
												}else
												{
													JOptionPane.showMessageDialog(null, "이동 실패. " );
												}
											}
										}
									}
									
								}else{
									JOptionPane.showMessageDialog(null, "폴더나 파일이 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
								}
								mframe.dispose();
								root.removeAllChildren(); // 기존 항목 삭제 후 새 목록 추가
								loadFiles(root, new File(root.toString())); // 처음 폴더 파일 로드
								((DefaultTreeModel) tree.getModel()).reload(root);
							}
						});
						cancelbtn.addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent e)
							{
								mframe.dispose();
							}
						});
						
						mframe.setVisible(true);
					}
				});
				JButton cancelbtn = new JButton("닫기");
				cancelbtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						oframe.dispose();
					}
				});
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
				panel.add(openbtn);
				panel.add(addbtn);
				panel.add(delbtn);
				panel.add(movebtn);
				panel.add(cancelbtn);
				oframe.add(oscrollpane);
				oframe.add(panel,BorderLayout.EAST);
				oframe.setVisible(true);
				viewpanel.repaint();
				
			}
		});
		mwpsavebtn = new JButton("파일저장");
		mwpsavebtn.setFont(new Font("Serif",Font.PLAIN,20));
		mwpsavebtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFrame sframe = new JFrame("파일저장");
				sframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				sframe.setSize(400,300);
				sframe.setLocation(700,300);
				JLabel folderlabel = new JLabel("저장폴더 : ");
				JComboBox<String> folderbox = new JComboBox<>();
				JPanel combopanel = new JPanel(new FlowLayout());
				combopanel.add(folderlabel);
				combopanel.add(folderbox);
				File forder = new File("word");
				folderbox.addItem("word");
				if(forder.exists() && forder.isDirectory())
				{
					File[] forders = forder.listFiles(File::isDirectory);
					for(File file: forders)
					{
						folderbox.addItem(file.getName());
					}
				}
				sframe.add(combopanel,BorderLayout.NORTH);
				JLabel namelabel = new JLabel("이름 : ");
				JTextField nametxt = new JTextField(20);
				JPanel namepanel = new JPanel(new FlowLayout());
				namepanel.add(namelabel);
				namepanel.add(nametxt);
				sframe.add(namepanel);
				JPanel btnpanel = new JPanel(new FlowLayout());
				JButton savebtn = new JButton("저장");
				JButton cancelbtn = new JButton("닫기");
				btnpanel.add(savebtn);
				btnpanel.add(cancelbtn);
				sframe.add(btnpanel,BorderLayout.SOUTH);
				savebtn.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						StringBuilder writeTemp;
						String tmp;
						String[][] word;
						try{
							String filepath;
							
							File fw;
							writeTemp = new StringBuilder();
							writeTemp.append((char)9925);//처음 파일인것을 인식
							writeTemp.append((char)9989);//처음 파일인것을 인식
							writeTemp.append((char)9989);//처음 파일인것을 인식
							writeTemp.append((char)9948);//처음 파일인것을 인식
							word = new String[table.getRowCount()][2];
							for(int i=0;i<table.getRowCount();i++)
							{
								writeTemp.append("!");
								writeTemp.append(model.getValueAt(i,0));
								writeTemp.append("@");
								writeTemp.append(model.getValueAt(i,1));
							}
						
							if(folderbox.getSelectedItem()!="word")
							{
								filepath = "word\\"+folderbox.getSelectedItem()+"\\"+nametxt.getText();
								if(!filepath.endsWith(".mwp"))
								{
									filepath+=".mwp";
								}
								fw = new File(filepath);
								if(fw.exists()){
								FileOutputStream fs = new FileOutputStream(fw);
								fs.write(new byte[0]);
								fs.close();
								}
			
								RandomAccessFile raf = new RandomAccessFile(fw,"rw");
								FileChannel channel = raf.getChannel();
								MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE,0,writeTemp.toString().getBytes("utf-8").length);
								buffer.clear();
								byte[] data = writeTemp.toString().getBytes("UTF-8");
								if(buffer.remaining()>=data.length){
				
									buffer.put(data);
								}
								else
								{
									System.out.println("버퍼에 충분한 공간이 없습니다.");
								}
								channel.close();
			
								raf.close();
							}else if(folderbox.getSelectedItem()=="word")
							{
								filepath = "word\\"+nametxt.getText();
								if(!filepath.endsWith(".mwp"))
								{
									filepath+=".mwp";
								}
								fw = new File(filepath);
								if(fw.exists()){
								FileOutputStream fs = new FileOutputStream(fw);
								fs.write(new byte[0]);
								fs.close();
								}
			
								RandomAccessFile raf = new RandomAccessFile(fw,"rw");
								FileChannel channel = raf.getChannel();
								MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE,0,writeTemp.toString().getBytes("utf-8").length);
								buffer.clear();
								byte[] data = writeTemp.toString().getBytes("UTF-8");
								if(buffer.remaining()>=data.length){
				
									buffer.put(data);
								}
								else
								{
									System.out.println("버퍼에 충분한 공간이 없습니다.");
								}
								channel.close();
			
								raf.close();
							}else
							{
								JOptionPane.showMessageDialog(
									null,
									"파일이 없습니다.",
									"알림",
									JOptionPane.INFORMATION_MESSAGE
								);

							}
								
							
							
							
							
						}catch(IOException ioe)
						{
							System.out.println("IOException 발생");
						}
						sframe.dispose();
					}
				});
				cancelbtn.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						sframe.dispose();
					}
				});
				
				
				
				sframe.setVisible(true);
				viewpanel.repaint();
			}
		});
		String[] columns = {"단어","뜻"};
		
		model = new DefaultTableModel(columns, 0);
		table = new JTable(model);
		table.setFont(new Font("Serif",Font.PLAIN,22));
		table.setRowHeight(24);
		// 테이블의 선택 모델 가져오기
		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// 선택 이벤트 추가
		selectionModel.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) { // 이벤트 중복 방지
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) { // 선택된 행이 있을 경우
					String word = (String) model.getValueAt(selectedRow, 0); // 첫 번째 열 값 가져오기
					String meaning = (String) model.getValueAt(selectedRow, 1); // 두 번째 열 값 가져오기
					//System.out.println("선택된 단어: " + word + ", 뜻: " + meaning);
				}
				viewpanel.repaint();
			}
		});
		// 스크롤 추가
        JScrollPane tablescroll = new JScrollPane(table);
		tablebtnpanel = new JPanel();
		tablebtnpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		wordaddbtn = new JButton("추가");
		wordaddbtn.setFont(new Font("Serif",Font.PLAIN,15));
		wordaddbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addTable();
				viewpanel.repaint();
			}
		});
		wordchangebtn = new JButton("수정");
		wordchangebtn.setFont(new Font("Serif",Font.PLAIN,15));
		wordchangebtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String[] tableword = new String[2];
				int selectedRow = table.getSelectedRow(); // 선택된 행 가져오기
                if (selectedRow != -1) { // 선택된 행이 있는 경우
                    tableword[0] = (String) model.getValueAt(selectedRow, 0);
                    tableword[1] = (String) model.getValueAt(selectedRow, 1);
					changeTable(selectedRow,tableword);
                } else {
                   
                }
				viewpanel.repaint();
				

			}
		});
		worddelbtn = new JButton("삭제");
		worddelbtn.setFont(new Font("Serif",Font.PLAIN,15));
		worddelbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int selectedRow = table.getSelectedRow(); // 선택된 행 가져오기
                if (selectedRow != -1) {
					model.removeRow(selectedRow);
					if(table.getRowCount()>selectedRow)
					{
					table.setRowSelectionInterval(selectedRow,selectedRow);
					}
				}else
				{
					
				}
				viewpanel.repaint();
			}
		});
		wordupbtn = new JButton("위로");
		wordupbtn.setFont(new Font("Serif",Font.PLAIN,15));
		wordupbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int selectedRow = table.getSelectedRow();
                if (selectedRow > 0) { // 첫 번째 행이 아닌 경우 이동 가능
                    model.moveRow(selectedRow, selectedRow, selectedRow - 1);
                    table.setRowSelectionInterval(selectedRow - 1, selectedRow - 1); // 이동 후 선택 유지
                } else {
                    
                }
				viewpanel.repaint();

			}
		});
		worddownbtn = new JButton("아래로");
		worddownbtn.setFont(new Font("Serif",Font.PLAIN,15));
		worddownbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int selectedRow = table.getSelectedRow();
                if (selectedRow < model.getRowCount() - 1) { // 마지막 행이 아닌 경우 이동 가능
                    model.moveRow(selectedRow, selectedRow, selectedRow + 1);
                    table.setRowSelectionInterval(selectedRow + 1, selectedRow + 1); // 이동 후 선택 유지
                } else {
                    
                }
				viewpanel.repaint();

			}
		});
		tablebtnpanel.add(wordaddbtn);
		tablebtnpanel.add(wordchangebtn);
		tablebtnpanel.add(worddelbtn);
		tablebtnpanel.add(wordupbtn);
		tablebtnpanel.add(worddownbtn);
		
		exitbtn = new JButton("끝내기");
		exitbtn.setFont(new Font("Serif",Font.PLAIN,20));
		exitbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int result = JOptionPane.showConfirmDialog(
                null,
                "앱을 종료하시겠습니까?",
                "종료 확인",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
				);

				if (result == JOptionPane.YES_OPTION) {
					System.exit(0); // 앱 종료
				}
				viewpanel.repaint();

			}
		});
		versionlabel = new JLabel("버전 : v0.0023");
		infolabel = new JLabel("만든이 : 컴퓨터판타지연구소");
		rightpanel.add(newbtn,BorderLayout.EAST);
		rightpanel.add(mwpopenbtn,BorderLayout.EAST);
		rightpanel.add(mwpsavebtn,BorderLayout.EAST);
		rightpanel.add(tablescroll,BorderLayout.EAST);
		rightpanel.add(tablebtnpanel,BorderLayout.EAST);
		rightpanel.add(exitbtn,BorderLayout.EAST);
		rightpanel.add(versionlabel,BorderLayout.EAST);
		rightpanel.add(infolabel,BorderLayout.EAST);
		
		leftpanel = new JPanel();
		leftpanel.setLayout(new BoxLayout(leftpanel,BoxLayout.Y_AXIS));
		leftpanel.setPreferredSize(new Dimension(800,700));
		viewpanel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g); // 기본 JPanel의 동작 유지
				g.setColor(Color.CYAN);
				g.fillRect(0,0,800,600);
				
				if(onlywordb)
				{
					for(int i=0;i<15;i++)
					{
						int selectedRow = table.getSelectedRow();
						int rowcount = table.getRowCount();
						if (selectedRow != -1&& selectedRow+i<rowcount) { // 첫 번째 행이 아닌 경우 이동 가능
							Object temp= table.getValueAt(selectedRow+i,0);
							g.setFont(new Font("Serif",Font.BOLD,30));
							g.setColor(Color.RED); 
							g.drawString(temp.toString(),30,30+i*40);
						} else {
                    
						}
						
					}
				}else
				{
					for(int i=0;i<15;i++)
					{
						int selectedRow = table.getSelectedRow();
						int rowcount = table.getRowCount();
						if (selectedRow != -1 && selectedRow+i<rowcount) { // 첫 번째 행이 아닌 경우 이동 가능
							Object temp= table.getValueAt(selectedRow+i,0);
							g.setFont(new Font("Serif",Font.BOLD,30));
							g.setColor(Color.RED); 
							g.drawString(temp.toString(),30,30+i*40);
							temp= table.getValueAt(selectedRow+i,1);
							g.setColor(Color.BLACK); 
							g.drawString(temp.toString(),350,30+i*40);
						} else {
                    
						}
						
					}
				}
				// FPS 제한을 위한 Swing Timer 사용
				//Timer timer = new Timer(400, e -> viewpanel.repaint()); // 16ms 간격 = 약 60FPS
				//timer.start();
			}

		};
		viewpanel.setPreferredSize(new Dimension(800,600));
		viewpanel.setSize(800,600);
		viewbtnpanel = new JPanel();
		viewbtnpanel.setLayout(new FlowLayout());
		previewbtn = new JButton("이전");
		previewbtn.setFont(new Font("Serif",Font.PLAIN,20));
		previewbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int selectedRow = table.getSelectedRow();
				if(selectedRow-15>=0)
				{
					table.setRowSelectionInterval(selectedRow-15,selectedRow-15);
				}else
				{
					if(selectedRow>0)
					{
					table.setRowSelectionInterval(0,0);
					}
				}
				viewpanel.repaint();
			}
		});
		nextviewbtn = new JButton("다음");
		nextviewbtn.setFont(new Font("Serif",Font.PLAIN,20));
		nextviewbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int selectedRow = table.getSelectedRow();
				int rowcount = table.getRowCount();
				if(selectedRow+15<rowcount)
				{
					table.setRowSelectionInterval(selectedRow+15,selectedRow+15);
				}else
				{
					if(rowcount>0)
					{
						table.setRowSelectionInterval(rowcount-1,rowcount-1);
					}
					
				}
				viewpanel.repaint();
			}
		});
		onlywordbtn = new JButton("단어만");
		onlywordbtn.setFont(new Font("Serif",Font.PLAIN,20));
		onlywordbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				onlywordb = true;
				viewpanel.repaint();
			}
		});
		wordbtn = new JButton("단어,뜻");
		wordbtn.setFont(new Font("Serif",Font.PLAIN,20));
		wordbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				onlywordb = false;
				viewpanel.repaint();
			}
		});
		viewbtnpanel.add(previewbtn);
		viewbtnpanel.add(nextviewbtn);
		viewbtnpanel.add(onlywordbtn);
		viewbtnpanel.add(wordbtn);
		
		leftpanel.add(viewpanel);
		leftpanel.add(viewbtnpanel);
		
		
		frame.add(leftpanel,BorderLayout.WEST);
		frame.add(rightpanel,BorderLayout.EAST);
		frame.setVisible(true);
	}
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(() -> {
			mp = new MirrorWordPracticev0_0023();
			mp.initWordFolder();
			mp.Init();
		});

	}
	public static void addTable()
	{
		JFrame addframe = new JFrame("단어추가");
		addframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addframe.setSize(800,200);
		addframe.setLocation(600,300);
		addframe.getContentPane().setLayout(new BoxLayout(addframe.getContentPane(),BoxLayout.Y_AXIS));
		JPanel wordpanel = new JPanel(new FlowLayout());
		JLabel wordlabel = new JLabel("단어 : ");
		wordlabel.setFont(new Font("Serif",Font.PLAIN,20));
		JTextField wordtxt = new JTextField(20);
		wordtxt.setFont(new Font("Serif",Font.PLAIN,20));
		JLabel meanlabel = new JLabel("의미 : ");
		meanlabel.setFont(new Font("Serif",Font.PLAIN,20));
		JTextField meantxt = new JTextField(20);
		meantxt.setFont(new Font("Serif",Font.PLAIN,20));
		wordpanel.add(wordlabel);
		wordpanel.add(wordtxt);
		wordpanel.add(meanlabel);
		wordpanel.add(meantxt);
		
		JPanel btnpanel = new JPanel(new FlowLayout());
		JButton conaddbtn = new JButton("계속추가");
		conaddbtn.setFont(new Font("Serif",Font.PLAIN,20));
		conaddbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				String[] wordtemp = new String[2];
				wordtemp[0] = wordtxt.getText();
				wordtemp[1] = meantxt.getText();
				model.addRow(new Object[]{wordtemp[0],wordtemp[1]});
				addframe.dispose();
				addTable();
			}
		});
		JButton addbtn = new JButton("추가");
		addbtn.setFont(new Font("Serif",Font.PLAIN,20));
		addbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				String[] wordtemp = new String[2];
				wordtemp[0] = wordtxt.getText();
				wordtemp[1] = meantxt.getText();
				model.addRow(new Object[]{wordtemp[0],wordtemp[1]});
				addframe.dispose();
			}
		});
		JButton cancelbtn = new JButton("닫기");
		cancelbtn.setFont(new Font("Serif",Font.PLAIN,20));
		cancelbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				addframe.dispose();
			}
		});
		btnpanel.add(conaddbtn);
		btnpanel.add(addbtn);
		btnpanel.add(cancelbtn);
		
		addframe.add(wordpanel);
		addframe.add(btnpanel);
		addframe.setVisible(true);
	}
	private void changeTable(int index,String[] tablestr)
	{
		JFrame changeframe = new JFrame("단어추가");
		changeframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		changeframe.setSize(800,200);
		changeframe.setLocation(600,300);
		changeframe.getContentPane().setLayout(new BoxLayout(changeframe.getContentPane(),BoxLayout.Y_AXIS));
		JPanel wordpanel = new JPanel(new FlowLayout());
		JLabel wordlabel = new JLabel("단어 : ");
		wordlabel.setFont(new Font("Serif",Font.PLAIN,20));
		JTextField wordtxt = new JTextField(20);
		wordtxt.setFont(new Font("Serif",Font.PLAIN,20));
		wordtxt.setText(tablestr[0]);
		JLabel meanlabel = new JLabel("의미 : ");
		meanlabel.setFont(new Font("Serif",Font.PLAIN,20));
		JTextField meantxt = new JTextField(20);
		meantxt.setFont(new Font("Serif",Font.PLAIN,20));
		meantxt.setText(tablestr[1]);
		wordpanel.add(wordlabel);
		wordpanel.add(wordtxt);
		wordpanel.add(meanlabel);
		wordpanel.add(meantxt);
		
		JPanel btnpanel = new JPanel(new FlowLayout());
		
		JButton changebtn = new JButton("수정");
		changebtn.setFont(new Font("Serif",Font.PLAIN,20));
		changebtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				String[] wordtemp = new String[2];
				wordtemp[0] = wordtxt.getText();
				wordtemp[1] = meantxt.getText();
				
				// 특정 셀 값 변경 (첫 번째 행, 두 번째 열)
				model.setValueAt(wordtemp[0],index,0);
				model.setValueAt(wordtemp[1],index,1);
				changeframe.dispose();
			}
		});
		JButton cancelbtn = new JButton("닫기");
		cancelbtn.setFont(new Font("Serif",Font.PLAIN,20));
		cancelbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				changeframe.dispose();
			}
		});
		
		btnpanel.add(changebtn);
		btnpanel.add(cancelbtn);
		
		changeframe.add(wordpanel);
		changeframe.add(btnpanel);
		changeframe.setVisible(true);
	}
	private static void treeView()
	{
		TreePath path = tree.getSelectionPath();
		if (path != null) {
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
			File selectedFile = new File(path.getPath()[0].toString(), selectedNode.toString());
			if (selectedFile.isDirectory()) {
				selectedNode.removeAllChildren(); // 기존 항목 삭제 후 새 목록 추가
				loadFiles(selectedNode, selectedFile);
				((DefaultTreeModel) tree.getModel()).reload(selectedNode);
			}else{
									
			}
		}
	}
	private static void loadFiles(DefaultMutableTreeNode node, File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                node.add(new DefaultMutableTreeNode(file.getName()));
            }
        }
    }
	public static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					deleteFolder(file);
				} else {
					file.delete();
				}
			}
		}
		folder.delete();
	}
	public static void initWordFolder()
	{
		File wordfolder = new File("word");
		if(!wordfolder.exists()&& !wordfolder.isDirectory())
		{
			wordfolder.mkdir();	
		}
	}
}
