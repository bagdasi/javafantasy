import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


class FuseDBAdminerv0_001 {
    public static JButton qexebtn, qnewbtn,jopenbtn,jsavebtn, qopenbtn, qsavebtn, exitbtn;
    public static JTextArea querytxt, logtxt;
    public static JTable sqltable;
    public static JLabel qlabel, tablelabel, loglabel;
    public static JFrame frame;
    public static JPanel panel;

    public void Init() {
        frame = new JFrame("퓨즈DB관리기 v0.001");
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        Font font = new Font("맑은 고딕", Font.PLAIN, 14);

        // 버튼 생성
        qexebtn = new JButton("실행");
        qnewbtn = new JButton("새로 만들기");
        qopenbtn = new JButton("쿼리 열기");
		jopenbtn = new JButton("json열기");
		jsavebtn = new JButton("json저장");
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
						
					break;
					case "새로 만들기":
						
					break;
					case "json열기":
						
					break;
					case "json저장":
						try {
							int rowCount = sqltable.getRowCount();
							int colCount = sqltable.getColumnCount();
							JsonArray tableData = new JsonArray();

							for (int i = 0; i < rowCount; i++) {
								JsonObject rowObject = new JsonObject();
								for (int j = 0; j < colCount; j++) {
									Object value = sqltable.getValueAt(i, j);
									String columnName = "col" + j;
									rowObject.addProperty(columnName, value != null ? value.toString() : "");
								}
								tableData.add(rowObject);
							}

							Gson gson = new Gson();
							String jsonOutput = gson.toJson(tableData);

							JFileChooser fileChooser = new JFileChooser();
							fileChooser.setDialogTitle("JSON 파일 저장");
							int userSelection = fileChooser.showSaveDialog(frame);

							if (userSelection == JFileChooser.APPROVE_OPTION) {
								java.io.File fileToSave = fileChooser.getSelectedFile();
								java.nio.file.Files.write(fileToSave.toPath(), jsonOutput.getBytes());
								logtxt.append("JSON 저장 완료: " + fileToSave.getAbsolutePath() + "\n");
							}
						} catch (Exception ex) {
							logtxt.append("JSON 저장 오류: " + ex.getMessage() + "\n");
						}
					break;
					case "쿼리 열기":
						
					break;
					
					case "쿼리 저장":
						
					break;
					
					case "종료":
						
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

        sqltable = new JTable(5, 5); // 임시 테이블
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
        FuseDBAdminerv0_001 app = new FuseDBAdminerv0_001();
        app.Init();
    }
}