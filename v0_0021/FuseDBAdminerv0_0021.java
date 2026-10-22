

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import FuseDB.*;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class FuseDBAdminerv0_0021 
{
	public static List<TableData> tablelist = new ArrayList<>();
	public static JButton qexebtn, qnewbtn,jopenbtn,jsavebtn, qopenbtn, qsavebtn, exitbtn;
    public static JTextArea querytxt, logtxt;
    public static JTable sqltable;
    public static JLabel qlabel, tablelabel, loglabel;
    public static JFrame frame;
    public static JPanel panel;
	public static DefaultTableModel model;
	public static FuseDBFile fusefile = new FuseDBFile();
    public void Init() {
        frame = new JFrame("퓨즈DB관리기 v0.0021");
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        Font font = new Font("맑은 고딕", Font.PLAIN, 14);

        // 버튼 생성
        qexebtn = new JButton("실행");
        qnewbtn = new JButton("새로 만들기");
        qopenbtn = new JButton("쿼리 열기");
		jopenbtn = new JButton("fdbf열기");
		jsavebtn = new JButton("fdbf저장");
        qsavebtn = new JButton("쿼리 저장");
        exitbtn = new JButton("종료");

        JButton[] buttons = { qexebtn, qnewbtn, jopenbtn,jsavebtn,qopenbtn, qsavebtn, exitbtn };
        for (JButton btn : buttons) {
            btn.setFont(font);
            panel.add(btn);
			
            btn.addActionListener(e -> {
				switch(btn.getText())
				{
					case "실행":
						String command = querytxt.getText();
						FuseDBsql sql = new FuseDBsql();
						
						try{
							String tablename = extractTableName(command);
							
							if(command.trim().startsWith("create table"))
							{
								TableData table = new TableData();
								table = sql.create(command);
								tablelist.add(table);
							}else if(command.trim().startsWith("alter table"))
							{
								sql.alter(command,tablelist);
							}else if(command.trim().startsWith("drop table"))
							{
								sql.drop(command,tablelist);
							}else if(command.trim().startsWith("insert into"))
							{
								
								List<Object> data = new ArrayList<>();
								data = sql.insert(command,tablename,tablelist);
								System.out.println(tablename);
								int num = 0;
								if(data != null)
								{
									for(TableData table : tablelist)
									{
										System.out.println("test name : " + tablelist.get(num).name);
										if(table.name.equals(tablename))
										{
											tablelist.get(num).tableset(data);
											System.out.println(tablelist.get(num).datas.get(0));
											break;
										}
										num++;
									}
								}
							}else if(command.trim().startsWith("update"))
							{
								sql.update(command,tablename,tablelist);
							}else if(command.trim().startsWith("delete from"))
							{
								sql.delete(command,tablename,tablelist);
							}
							int num = 0;
							
							for(TableData table : tablelist)
							{
								System.out.println("table : " + table.name);
								if(table.name.equals(tablename))
								{
									System.out.println("model : " + table.name);
									// 컬럼 이름 추출
									String[] columnNames = table.columns.stream()
											.map(c -> c.name)
											.toArray(String[]::new);

									model = new DefaultTableModel(columnNames, 0);
									for (List<Object> row : table.datas) {
										model.addRow(row.toArray());
									}
									int restart = 0;
									sqltable.setModel(model);
									frame.revalidate();
									break;
								}
							}
							if(command.trim().startsWith("select"))
							{
								TableData temptable = sql.select(command,tablename,tablelist);
								// 컬럼 이름 추출
								String[] columnNames = temptable.columns.stream().map(c -> c.name).toArray(String[]::new);
								model = new DefaultTableModel(columnNames, 0);
								for (List<Object> row : temptable.datas) {
									model.addRow(row.toArray());
								}
									
								sqltable.setModel(model);
								frame.revalidate();
								
							}else if(command.trim().startsWith("view table"))
							{
								TableData temptable = sql.view(command,tablelist);
								if(temptable!=null)
								{
									// 컬럼 이름 추출
									String[] columnNames = temptable.columns.stream().map(c -> c.name).toArray(String[]::new);
									model = new DefaultTableModel(columnNames, 0);
									for (List<Object> row : temptable.datas) {
										model.addRow(row.toArray());
									}
									
									sqltable.setModel(model);
									frame.revalidate();
								}
							}
							
						}catch(Exception ioe)
						{
							
						}
						
						
						
						
					break;
					case "새로 만들기":
						
					break;
					case "fdbf열기":
						File folder = new File("database"); // 검색할 폴더 경로

						if (folder.exists() && folder.isDirectory()) {
							File[] files = folder.listFiles();
							tablelist.clear();
							if (files != null) {
								for (File file : files) {
									if (file.isFile() && file.getName().toLowerCase().endsWith(".fdbf")) {
										StringBuilder sb = new StringBuilder();
										try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
											String line;
											while ((line = reader.readLine()) != null) {
												sb.append(line).append("\n"); // 줄 단위로 읽어서 추가
											}
											System.out.println("file read success!");
											String data = sb.toString();
											int index = data.indexOf("data:");
											if (index != -1) {
												String before = data.substring(0, index); // "abcd"
												String after = data.substring(index + "data:".length()); // "ghijk"
												TableData table = fusefile.FuseDBLoadDataTable(before);
												table.datas = fusefile.FuseDBLoadData(table , after).datas;  
												tablelist.add(table);
												
											} else {
												System.out.println("data: is no search.");
											}
										} catch (IOException ioe) {
											System.out.println("file read error " + ioe.getMessage());
										}


									} else if (file.isDirectory()) {
										System.out.println("하위 폴더: " + file.getName());
									}
								}
							} else {
								System.out.println("folder not search");
							}
						} else {
							System.out.println("forder not");
						}

						for(TableData table : tablelist)
						{
							System.out.println("table name : " + table.name);	
							System.out.println("table colum : " + table.columns.get(0).name + " , " + table.columns.get(1).name);
							System.out.println("table data : " + table.datas.get(0));
						}
						break;
					case "fdbf저장":
						for(TableData table : tablelist)
						{
							StringBuilder savesb = new StringBuilder();
							savesb.append(fusefile.FuseDBSaveDataTable(table.name, table.columns));
							savesb.append("data:\n");
							
							savesb.append(fusefile.FuseDBSaveData(table.columns, table.datas));
							try {
								String filename = "database\\"+table.name+".fdbf";
								File file = new File(filename); // 삭제할 파일 경로

								if (file.exists()) {
									if (file.delete()) {
										System.out.println("파일 삭제 완료!");
									} else {
										System.out.println("파일 삭제 실패!");
									}
								} else {
									System.out.println("파일이 존재하지 않습니다.");
								}


								FileWriter writer = new FileWriter(filename); // 저장할 파일 이름
								writer.write(savesb.toString()); // StringBuilder → String
								writer.close(); // 파일 닫기
								System.out.println("파일 저장 완료!");
							} catch (IOException ioe) {
								System.out.println("파일 저장 중 오류 발생: " + ioe.getMessage());
							}


						}
					break;
					case "쿼리 열기":
						try {
							JFileChooser fileChooser = new JFileChooser();
							fileChooser.setDialogTitle("쿼리 열기");
							fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.dir")));

							// .fqr 확장자 필터 추가
							fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fuse Query 파일 (*.fqr)", "fqr"));

							int userSelection = fileChooser.showOpenDialog(frame);
							if (userSelection == JFileChooser.APPROVE_OPTION) {
								java.io.File fileToOpen = fileChooser.getSelectedFile();
								String queryContent = new String(java.nio.file.Files.readAllBytes(fileToOpen.toPath()));

								querytxt.setText(queryContent);
								logtxt.append("쿼리 열기 완료: " + fileToOpen.getAbsolutePath() + "\n");
							}
						} catch (Exception ex) {
							logtxt.append("쿼리 열기 오류: " + ex.getMessage() + "\n");
						}
						break;
					
					case "쿼리 저장":
						try {
							JFileChooser fileChooser = new JFileChooser();
							fileChooser.setDialogTitle("쿼리 저장");
							fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.dir")));

							// .fqr 확장자 필터 추가
							fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fuse Query 파일 (*.fqr)", "fqr"));

							int userSelection = fileChooser.showSaveDialog(frame);
							if (userSelection == JFileChooser.APPROVE_OPTION) {
								java.io.File fileToSave = fileChooser.getSelectedFile();

								// 확장자 자동 추가
								if (!fileToSave.getName().toLowerCase().endsWith(".fqr")) {
									fileToSave = new java.io.File(fileToSave.getAbsolutePath() + ".fqr");
								}

								String queryContent = querytxt.getText();
								java.nio.file.Files.write(fileToSave.toPath(), queryContent.getBytes());

								logtxt.append("쿼리 저장 완료: " + fileToSave.getAbsolutePath() + "\n");
							}
						} catch (Exception ex) {
							logtxt.append("쿼리 저장 오류: " + ex.getMessage() + "\n");
						}
						break;
					
					case "종료":
						System.exit(0);
					break;
					
					default:
						
					break;
				}
			});
        }
		frame.add(panel);
        // 쿼리 라벨 및 텍스트 영역
        qlabel = new JLabel("쿼리 : ");
        qlabel.setFont(font);
        frame.add(qlabel);

        querytxt = new JTextArea(5, 40);
        querytxt.setFont(font);
        frame.add(new JScrollPane(querytxt));

        // 테이블 라벨 및 테이블
        tablelabel = new JLabel("테이블 : ");
        tablelabel.setFont(font);
        frame.add(tablelabel);
		
        sqltable = new JTable(1,5); // 임시 테이블
        sqltable.setFont(font);
        sqltable.setRowHeight(20);
        frame.add(new JScrollPane(sqltable));

        // 로그 라벨 및 텍스트 영역
        loglabel = new JLabel("로그/에러 : ");
        loglabel.setFont(font);
        frame.add(loglabel);

        logtxt = new JTextArea(5, 40);
        logtxt.setFont(font);
        frame.add(new JScrollPane(logtxt));

        // 프레임 설정
        
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        FuseDBAdminerv0_0021 app = new FuseDBAdminerv0_0021();
        app.Init();
    }
	
	
	public boolean isDuplicatePrimaryKey(JTable table, String primaryKeyColumn, Object newValue) {
		for (int i = 0; i < table.getRowCount(); i++) {
			Object value = table.getValueAt(i, table.getColumnModel().getColumnIndex(primaryKeyColumn));
			if (newValue.equals(value)) {
				return true; // 중복 발견!
			}
		}
		return false; // 중복 없음
	}
	public static String extractTableName(String command) {
		command = command.trim().toLowerCase();

		String[] tokens = command.split("\\s+");
		String tableName = null;

		if (command.startsWith("create table") ||
			command.startsWith("alter table") ||
			command.startsWith("drop table") ||
			command.startsWith("insert into")) {
        
			tableName = tokens[2];
		} else if (command.startsWith("update")) {
			tableName = tokens[1];
		} else if (command.startsWith("delete from")) {
			tableName = tokens[2];
		}else if (command.startsWith("select")) {
			// FROM 위치 찾기
			for (int i = 0; i < tokens.length - 1; i++) {
				if (tokens[i].equals("from")) {
					tableName = tokens[i + 1];
					break;
				}
			}
		}



		// 특수문자 제거 (예: {, (, 등)
		if (tableName != null) {
			//tableName = tableName.replaceAll("[^a-zA-Z0-9_]", "");
		}

		return tableName;
	}
}
