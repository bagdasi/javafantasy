import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import javax.swing.filechooser.FileNameExtensionFilter;
class MirrorWordPracticev0_0021
{
	private static MirrorWordPracticev0_0021 mp;
	private static JFrame frame;
	private static JPanel rightpanel,leftpanel,viewpanel,viewbtnpanel,tablebtnpanel;
	private static JButton wordaddbtn,worddelbtn,wordchangebtn,wordupbtn,worddownbtn,mwpopenbtn,mwpsavebtn,exitbtn;
	private static JButton previewbtn,nextviewbtn,onlywordbtn,wordbtn,newbtn;
	private static DefaultTableModel model;
	private static JTable table;
	private static JLabel infolabel;
	String[][] word;
	private static BufferedImage image;
	private boolean onlywordb = false;
	
	
	void Init()
	{
		// 프레임 생성
        JFrame frame = new JFrame("미러영어단어학습기v0.0021");
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
			}
		});
		mwpopenbtn = new JButton("파일열기");
		mwpopenbtn.setFont(new Font("Serif",Font.PLAIN,20));
		
		mwpopenbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					StringBuilder readTemp;
					BufferedReader br = null;
					int readC = 0;
					long mwpfs = 0;
					String fchoose;
					int returnvalue;
					File selectedFile= null;
					JFileChooser filechooser = new JFileChooser();
					// 파일 필터 추가 (.qp 파일만 보이게 하기)
					FileNameExtensionFilter filter = new FileNameExtensionFilter("MWP 파일", "mwp");
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
						
						if(!filePath.endsWith(".mwp"))
						{
							filePath += ".mwp";	
						}
						br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
						File file = new File(filePath);
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
					
				}catch(IOException ioe) 
				{
					
				}
				
			}
		});
		mwpsavebtn = new JButton("파일저장");
		mwpsavebtn.setFont(new Font("Serif",Font.PLAIN,20));
		mwpsavebtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				StringBuilder writeTemp;
				String tmp;
				try{
					String fchoose;
					int returnvalue;
					File selectedFile= null;
					JFileChooser filechooser = new JFileChooser();
					// 파일 필터 추가 (.qp 파일만 보이게 하기)
					FileNameExtensionFilter filter = new FileNameExtensionFilter("MWP 파일", "mwp");
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
						String filePath = selectedFile.getPath();
						
						if(!filePath.endsWith(".mwp"))
						{
							filePath += ".mwp";	
						}
						File fw = new File(filePath);
						if(fw.exists()){
							FileOutputStream fos = new FileOutputStream(fw);
							fos.write(new byte[0]);
							fos.close();
						}
			
						RandomAccessFile raf = new RandomAccessFile(fw,"rw");
						FileChannel channel = raf.getChannel();
						MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE,0,writeTemp.toString().getBytes("utf-8").length);
						buffer.clear();
						byte[] data = writeTemp.toString().getBytes("UTF-8");
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
						JOptionPane.showMessageDialog(
							null,
							"파일이 없습니다.",
							"알림",
							JOptionPane.INFORMATION_MESSAGE
						);

					}
				}catch(IOException ioe)
				{
					
				}
			}
		});
		String[] columns = {"단어","뜻"};
		
		model = new DefaultTableModel(columns, 0);
		table = new JTable(model);
		table.setFont(new Font("Serif",Font.PLAIN,22));
		table.setRowHeight(24);
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

			}
		});
		infolabel = new JLabel("만든이 : 컴퓨터판타지연구소");
		rightpanel.add(newbtn,BorderLayout.EAST);
		rightpanel.add(mwpopenbtn,BorderLayout.EAST);
		rightpanel.add(mwpsavebtn,BorderLayout.EAST);
		rightpanel.add(tablescroll,BorderLayout.EAST);
		rightpanel.add(tablebtnpanel,BorderLayout.EAST);
		rightpanel.add(exitbtn,BorderLayout.EAST);
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
				viewpanel.repaint();
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
			}
		});
		onlywordbtn = new JButton("단어만");
		onlywordbtn.setFont(new Font("Serif",Font.PLAIN,20));
		onlywordbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				onlywordb = true;
			}
		});
		wordbtn = new JButton("단어,뜻");
		wordbtn.setFont(new Font("Serif",Font.PLAIN,20));
		wordbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				onlywordb = false;
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
			mp = new MirrorWordPracticev0_0021();
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
}
