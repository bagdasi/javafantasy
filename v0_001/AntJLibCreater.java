import java.io.File;
import javax.swing.*;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.*;
import java.util.List;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import Version.Version;

class AntJLibCreater
{
	public Version version = new Version();
	private static AntJLibCreater ajlc;
	private JFrame frame;
	private String currentDir = ".";
	public AntJLibCreater() {
		initUI();
	}
	
	private void initUI() {
		Font uiFont = new Font("Serif", Font.PLAIN, 20);
		
		frame = new JFrame("앤트JLib생성기 v" + version.app_Version);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setIconImage(new ImageIcon("antjlib.png").getImage());
		// 1. 생성경로
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l1 = new JLabel("생성경로 : ");
		l1.setFont(uiFont);
		JTextField t1 = new JTextField(20);
		t1.setFont(uiFont);
		JButton b1 = new JButton("...");
		b1.setFont(uiFont);
		p1.add(l1); p1.add(t1); p1.add(b1);
		frame.add(p1);
		b1.addActionListener(e -> {
			File file = new File("currentDir.txt");
			
			if (file.exists()) {
				try {
					List<String> lines = java.nio.file.Files.readAllLines(file.toPath());
					if (!lines.isEmpty()) {
						currentDir = lines.get(0).trim();
					}
				} catch (Exception aafe) {
					aafe.printStackTrace();
				}
			}
			JFileChooser chooser = new JFileChooser();
			// currentDir이 유효한 디렉토리인지 확인
			File baseDir = new File(currentDir);
			if (baseDir.exists() && baseDir.isDirectory()) {
				chooser = new JFileChooser(baseDir);   // ★ currentDir에서 열기
			} else {
				chooser = new JFileChooser(".");       // ★ fallback: 현재 실행 폴더
			}
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // ★ 폴더만 선택
			chooser.setDialogTitle("디렉토리 선택");
			
			int result = chooser.showOpenDialog(frame);
			
			if (result == JFileChooser.APPROVE_OPTION) {
				File dir = chooser.getSelectedFile();
				String path = dir.getAbsolutePath();
				
				t1.setText(path);  // ★ TextField에 표시
				
				// ★ currentDir 변수 업데이트
				currentDir = path;
				
				// ★ currentDir.txt 파일에 저장
				try (PrintWriter pw = new PrintWriter(file)) {
					pw.println(path);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		// 2. 모듈경로
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l2 = new JLabel("모듈경로 : ");
		l2.setFont(uiFont);
		JTextField t2 = new JTextField(20);
		t2.setFont(uiFont);
		JButton b2 = new JButton("추가");
		b2.setFont(uiFont);
		p2.add(l2); p2.add(t2); p2.add(b2);
		frame.add(p2);
		b2.addActionListener(e -> {
			
			JDialog dialog = new JDialog(frame, "Path 설정", true);
			dialog.setSize(400, 300);
			dialog.setLocationRelativeTo(frame);
			dialog.setLayout(new BorderLayout(10, 10));
			
			// ------------------ 상단 ------------------
			JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel label = new JLabel("Path");
			JButton helpBtn = new JButton("사용법");
			topPanel.add(label);
			topPanel.add(helpBtn);
			helpBtn.addActionListener(he -> {
				
				// 모달 다이얼로그 생성
				JDialog helpDialog = new JDialog(frame, "사용법", true);
				helpDialog.setSize(350, 250);
				helpDialog.setLocationRelativeTo(frame);
				helpDialog.setLayout(new BorderLayout(10, 10));
				
				// 텍스트 영역
				JTextArea textArea = new JTextArea();
				textArea.setEditable(false);
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				
				textArea.append("jmod파일이 들어있는 폴더를 선택하세요.\n");
				textArea.append("앞으로 계속 수정하겠습니다.\n");
				
				JScrollPane scrollPane = new JScrollPane(textArea);
				
				// 확인 버튼
				JButton okBtn = new JButton("확인");
				okBtn.addActionListener(ae -> helpDialog.dispose());
				
				JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				bottomPanel.add(okBtn);
				
				// 조립
				helpDialog.add(scrollPane, BorderLayout.CENTER);
				helpDialog.add(bottomPanel, BorderLayout.SOUTH);
				
				helpDialog.setVisible(true);
			});
			
			// ------------------ 중단 ------------------
			DefaultListModel<String> listModel = new DefaultListModel<>();
			JList<String> list = new JList<>(listModel);
			JScrollPane scrollPane = new JScrollPane(list);
			
			JButton btnAdd = new JButton("추가");
			JButton btnDelete = new JButton("삭제");
			JButton btnDeleteAll = new JButton("모두삭제");
			JButton btnUp = new JButton("위로");
			JButton btnDown = new JButton("아래로");
			
			JPanel rightBtnPanel = new JPanel();
			rightBtnPanel.setLayout(new GridLayout(5, 1, 5, 5));
			rightBtnPanel.add(btnAdd);
			rightBtnPanel.add(btnDelete);
			rightBtnPanel.add(btnDeleteAll);
			rightBtnPanel.add(btnUp);
			rightBtnPanel.add(btnDown);
			
			JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
			centerPanel.add(scrollPane, BorderLayout.CENTER);
			centerPanel.add(rightBtnPanel, BorderLayout.EAST);
			
			// ------------------ 하단 ------------------
			JButton okBtn = new JButton("확인");
			JButton cancelBtn = new JButton("취소");
			
			JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			bottomPanel.add(okBtn);
			bottomPanel.add(cancelBtn);
			
			// ------------------ 조립 ------------------
			dialog.add(topPanel, BorderLayout.NORTH);
			dialog.add(centerPanel, BorderLayout.CENTER);
			dialog.add(bottomPanel, BorderLayout.SOUTH);
			
			// ------------------ 이벤트 ------------------
			
			// 추가
			btnAdd.addActionListener(ae -> {
				
				
				File file = new File("currentDir.txt");
				
				if (file.exists()) {
					try {
						List<String> lines = java.nio.file.Files.readAllLines(file.toPath());
						if (!lines.isEmpty()) {
							currentDir = lines.get(0).trim();
						}
					} catch (Exception aafe) {
						aafe.printStackTrace();
					}
				}
				JFileChooser chooser = new JFileChooser();
				// currentDir이 유효한 디렉토리인지 확인
				File baseDir = new File(currentDir);
				if (baseDir.exists() && baseDir.isDirectory()) {
					chooser = new JFileChooser(baseDir);   // ★ currentDir에서 열기
				} else {
					chooser = new JFileChooser(".");       // ★ fallback: 현재 실행 폴더
				}
				chooser.setDialogTitle("jmods 폴더 선택");
				chooser.setMultiSelectionEnabled(true); // ★ 멀티 선택
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				
				int result = chooser.showOpenDialog(dialog);
				
				if (result == JFileChooser.APPROVE_OPTION) {
					
					File[] files = chooser.getSelectedFiles(); // ★ 여러 개 선택됨
					
					for (File f : files) {
						String path = f.getAbsolutePath();
						
						
						
						// ★ 중복 방지 (원하면 제거 가능)
						if (!listModel.contains(path)) {
							listModel.addElement(path);
						}
					}
				}
			});
			
			
			// 삭제
			btnDelete.addActionListener(ae -> {
				int idx = list.getSelectedIndex();
				if (idx >= 0) {
					listModel.remove(idx);
					list.setSelectedIndex(idx);
				}
				
			});
			
			// 모두 삭제
			btnDeleteAll.addActionListener(ae -> {
				listModel.clear();
			});
			
			// 위로 이동
			btnUp.addActionListener(ae -> {
				int idx = list.getSelectedIndex();
				if (idx > 0) {
					String item = listModel.get(idx);
					listModel.remove(idx);
					listModel.add(idx - 1, item);
					list.setSelectedIndex(idx - 1);
				}
			});
			
			// 아래로 이동
			btnDown.addActionListener(ae -> {
				int idx = list.getSelectedIndex();
				if (idx >= 0 && idx < listModel.size() - 1) {
					String item = listModel.get(idx);
					listModel.remove(idx);
					listModel.add(idx + 1, item);
					list.setSelectedIndex(idx + 1);
				}
			});
			
			// 확인
			okBtn.addActionListener(ae -> {
				// 여기서 listModel 내용을 가져와서 처리하면 됨
				String os = System.getProperty("os.name").toLowerCase();
				String semi = "";
				if(os.contains("win"))
				{
					semi = ";";
				}else
				{
					semi = ":";
				}
				// listModel → "A;B;C;D" 형태로 변환
				StringBuilder sb = new StringBuilder();
				
				for (int i = 0; i < listModel.size(); i++) {
					if (i > 0) sb.append(semi);   // 앞에는 세미콜론 안 붙임
					sb.append(listModel.get(i));
				}
				
				// JTextField에 넣기
				t2.setText(sb.toString());
				dialog.dispose();
			});
			
			// 취소
			cancelBtn.addActionListener(ae -> dialog.dispose());
			
			dialog.setVisible(true);
		});
		
		// 3. 모듈추가
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l3 = new JLabel("모듈추가 : ");
		l3.setFont(uiFont);
		JTextField t3 = new JTextField(20);
		t3.setFont(uiFont);
		JButton b3 = new JButton("추가");
		b3.setFont(uiFont);
		p3.add(l3); p3.add(t3); p3.add(b3);
		frame.add(p3);
		b3.addActionListener(e -> {
			
			JDialog dialog = new JDialog(frame, "Path 설정", true);
			dialog.setSize(400, 300);
			dialog.setLocationRelativeTo(frame);
			dialog.setLayout(new BorderLayout(10, 10));
			
			// ------------------ 상단 ------------------
			JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel label = new JLabel("Path");
			JButton helpBtn = new JButton("사용법");
			topPanel.add(label);
			topPanel.add(helpBtn);
			helpBtn.addActionListener(he -> {
				
				// 모달 다이얼로그 생성
				JDialog helpDialog = new JDialog(frame, "사용법", true);
				helpDialog.setSize(350, 250);
				helpDialog.setLocationRelativeTo(frame);
				helpDialog.setLayout(new BorderLayout(10, 10));
				
				// 텍스트 영역
				JTextArea textArea = new JTextArea();
				textArea.setEditable(false);
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				
				textArea.append("jmod파일을 선택하세요.\n");
				textArea.append("콘솔프로그램 : java.base\n");
				textArea.append("Swing GUI : java.desktop\n");
				textArea.append("파일입출력 : java.base\n");
				textArea.append("네트워크 : java.net\n");
				textArea.append("XML : java.xml\n");
				textArea.append("SQL : java.sql\n");
				textArea.append("JavaFX : javafx.controls, javafx.graphics, javafx.fxml\n");
				textArea.append("앞으로 계속 수정하겠습니다.\n");
				
				JScrollPane scrollPane = new JScrollPane(textArea);
				
				// 확인 버튼
				JButton okBtn = new JButton("확인");
				okBtn.addActionListener(ae -> helpDialog.dispose());
				
				JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				bottomPanel.add(okBtn);
				
				// 조립
				helpDialog.add(scrollPane, BorderLayout.CENTER);
				helpDialog.add(bottomPanel, BorderLayout.SOUTH);
				
				helpDialog.setVisible(true);
			});
			
			// ------------------ 중단 ------------------
			DefaultListModel<String> listModel = new DefaultListModel<>();
			JList<String> list = new JList<>(listModel);
			JScrollPane scrollPane = new JScrollPane(list);
			
			JButton btnAdd = new JButton("추가");
			JButton btnDelete = new JButton("삭제");
			JButton btnDeleteAll = new JButton("모두삭제");
			JButton btnUp = new JButton("위로");
			JButton btnDown = new JButton("아래로");
			
			JPanel rightBtnPanel = new JPanel();
			rightBtnPanel.setLayout(new GridLayout(5, 1, 5, 5));
			rightBtnPanel.add(btnAdd);
			rightBtnPanel.add(btnDelete);
			rightBtnPanel.add(btnDeleteAll);
			rightBtnPanel.add(btnUp);
			rightBtnPanel.add(btnDown);
			
			JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
			centerPanel.add(scrollPane, BorderLayout.CENTER);
			centerPanel.add(rightBtnPanel, BorderLayout.EAST);
			
			// ------------------ 하단 ------------------
			JButton okBtn = new JButton("확인");
			JButton cancelBtn = new JButton("취소");
			
			JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			bottomPanel.add(okBtn);
			bottomPanel.add(cancelBtn);
			
			// ------------------ 조립 ------------------
			dialog.add(topPanel, BorderLayout.NORTH);
			dialog.add(centerPanel, BorderLayout.CENTER);
			dialog.add(bottomPanel, BorderLayout.SOUTH);
			
			// ------------------ 이벤트 ------------------
			
			// 추가
			btnAdd.addActionListener(ae -> {
				
				File file = new File("currentDir.txt");
				
				if (file.exists()) {
					try {
						List<String> lines = java.nio.file.Files.readAllLines(file.toPath());
						if (!lines.isEmpty()) {
							currentDir = lines.get(0).trim();
						}
					} catch (Exception aafe) {
						aafe.printStackTrace();
					}
				}
				JFileChooser chooser = new JFileChooser();
				// currentDir이 유효한 디렉토리인지 확인
				File baseDir = new File(currentDir);
				if (baseDir.exists() && baseDir.isDirectory()) {
					chooser = new JFileChooser(baseDir);   // ★ currentDir에서 열기
				} else {
					chooser = new JFileChooser(".");       // ★ fallback: 현재 실행 폴더
				}
				chooser.setDialogTitle("jmod 파일 선택");
				chooser.setMultiSelectionEnabled(true); // ★ 멀티 선택
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				// ★ jmod 파일만 보이도록 필터 추가
				chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
					@Override
					public boolean accept(File f) {
						return f.isDirectory() || f.getName().toLowerCase().endsWith(".jmod");
					}
					
					@Override
					public String getDescription() {
						return "JMOD Files (*.jmod)";
					}
				});
				
				int result = chooser.showOpenDialog(dialog);
				
				if (result == JFileChooser.APPROVE_OPTION) {
					
					File[] files = chooser.getSelectedFiles(); // ★ 여러 개 선택됨
					
					for (File f : files) {
						String name = f.getName();
						
						// ★ 확장자 .jmod 제거
						if (name.toLowerCase().endsWith(".jmod")) {
							name = name.substring(0, name.length() - 5);
						}
						
						// ★ 중복 방지 (원하면 제거 가능)
						if (!listModel.contains(name)) {
							listModel.addElement(name);
						}
					}
				}
			});
			
			
			// 삭제
			btnDelete.addActionListener(ae -> {
				int idx = list.getSelectedIndex();
				if (idx >= 0)
				{
					listModel.remove(idx);
					list.setSelectedIndex(idx);
				}
			});
			
			// 모두 삭제
			btnDeleteAll.addActionListener(ae -> {
				listModel.clear();
			});
			
			// 위로 이동
			btnUp.addActionListener(ae -> {
				int idx = list.getSelectedIndex();
				if (idx > 0) {
					String item = listModel.get(idx);
					listModel.remove(idx);
					listModel.add(idx - 1, item);
					list.setSelectedIndex(idx - 1);
				}
			});
			
			// 아래로 이동
			btnDown.addActionListener(ae -> {
				int idx = list.getSelectedIndex();
				if (idx >= 0 && idx < listModel.size() - 1) {
					String item = listModel.get(idx);
					listModel.remove(idx);
					listModel.add(idx + 1, item);
					list.setSelectedIndex(idx + 1);
				}
			});
			
			// 확인
			okBtn.addActionListener(ae -> {
				// 여기서 listModel 내용을 가져와서 처리하면 됨
				String semi = ",";
				// listModel → "A;B;C;D" 형태로 변환
				StringBuilder sb = new StringBuilder();
				
				for (int i = 0; i < listModel.size(); i++) {
					if (i > 0) sb.append(semi);   // 앞에는 세미콜론 안 붙임
					sb.append(listModel.get(i));
				}
				
				// JTextField에 넣기
				t3.setText(sb.toString());
				dialog.dispose();
			});
			
			// 취소
			cancelBtn.addActionListener(ae -> dialog.dispose());
			
			dialog.setVisible(true);
		});
		// 4. 라이브러리 이름
		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l4 = new JLabel("JLIB이름 : ");
		l4.setFont(uiFont);
		JTextField t4 = new JTextField(20);
		t4.setFont(uiFont);
		p4.add(l4); p4.add(t4);
		frame.add(p4);
		
		// 5. 생성 / 종료 버튼
		JPanel p5 = new JPanel();
		p5.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton bCreate = new JButton("생성");
		JButton bExit = new JButton("종료");
		bCreate.setFont(uiFont);
		bExit.setFont(uiFont);
		p5.add(bCreate); p5.add(bExit);
		frame.add(p5);
		bCreate.addActionListener(e -> {
			
			String workDir = t1.getText().trim();        // 실행 경로
			String modulePath = t2.getText().trim();     // --module-path
			String addModules = t3.getText().trim();     // --add-modules
			String outputDir = t4.getText().trim();      // --output
			
			// OS 구분 (Windows는 jlink.exe)
			boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
			String jlinkCmd = isWindows ? "jlink.exe" : "jlink";
			
			// jlink 명령어 구성
			List<String> command = new ArrayList<>();
			command.add(jlinkCmd);
			command.add("--module-path");
			command.add(modulePath);
			command.add("--add-modules");
			command.add(addModules);
			command.add("--output");
			command.add(outputDir);
			
			try {
				ProcessBuilder pb = new ProcessBuilder(command);
				
				// 실행 경로 설정
				pb.directory(new File(workDir));
				
				// 에러 스트림도 합치기
				pb.redirectErrorStream(true);
				
				Process process = pb.start();
				
				// 출력 읽기
				BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream(), "UTF-8")
					);
				
				StringBuilder output = new StringBuilder();
				String line;
				
				while ((line = reader.readLine()) != null) {
					output.append(line).append("\n");
				}
				
				int exitCode = process.waitFor();
				
				// 메시지 출력
				JOptionPane.showMessageDialog(frame,
					"Exit Code: " + exitCode + "\n\n" + output.toString(),
					"jlink 실행 결과",
					JOptionPane.INFORMATION_MESSAGE);
				
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(frame,
					"오류 발생:\n" + ex.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
			}
		});
		
		bExit.addActionListener(e -> {
			System.exit(0);
		});
		// 6. 앱 정보
		JPanel p6 = new JPanel();
		JLabel info1 = new JLabel("앱 이름 : 앤트JLib생성기");
		info1.setFont(uiFont);
		p6.add(info1);
		frame.add(p6);
		
		JPanel p7 = new JPanel();
		JLabel info2 = new JLabel("버전 : " + version.app_Version);
		info2.setFont(uiFont);
		p7.add(info2);
		frame.add(p7);
		
		JPanel p8 = new JPanel();
		JLabel info3 = new JLabel("만든이 : 컴퓨터판타지연구소");
		info3.setFont(uiFont);
		p8.add(info3);
		frame.add(p8);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		ajlc = new AntJLibCreater();
		System.out.println("Hello World!");
	}
}
