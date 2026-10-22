import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Version.Version;
public class MLSP {
	private static DefaultListModel<String> listModel = new DefaultListModel<>();
	private static JList<String> speechList = new JList<>(listModel);;
	private static JTextPane textPane = new JTextPane();
	private static MLSP mlsp = new MLSP();
	private static JTextArea area1 = new JTextArea();
	private static JTextArea area2 = new JTextArea();
	private static JTextArea lineNumberArea = new JTextArea("1");
	private static WebData.Dictionary d;
	public static Version ver = new Version();
	public static int lineCount = 0;
	
	
	private static class NoWrapFactory implements ViewFactory {
		@Override
		public View create(Element elem) {
			String kind = elem.getName();
			
			if (kind != null) {
				if (kind.equals(AbstractDocument.ContentElementName)) {
					return new LabelView(elem);
				} else if (kind.equals(AbstractDocument.ParagraphElementName)) {
					return new NoWrapParagraphView(elem);  // ★ wrap 끔
				} else if (kind.equals(AbstractDocument.SectionElementName)) {
					return new BoxView(elem, View.Y_AXIS);
				} else if (kind.equals(StyleConstants.ComponentElementName)) {
					return new ComponentView(elem);
				} else if (kind.equals(StyleConstants.IconElementName)) {
					return new IconView(elem);
				}
			}
			
			return new LabelView(elem);
		}
	}
	
	private static class NoWrapParagraphView extends ParagraphView {
		public NoWrapParagraphView(Element elem) {
			super(elem);
		}
		
		@Override
		public void layout(int width, int height) {
			super.layout(Integer.MAX_VALUE, height);  // ★ wrap 완전 OFF
		}
		
		@Override
		public float getMinimumSpan(int axis) {
			return super.getPreferredSpan(axis);
		}
	}
	
	
	
	
	public static void main(String[] args) {
		//SwingUtilities.invokeLater(() -> new MLSP().createUI());
		
		mlsp.createUI();
	}
	
	public static void createUI() {
		
		JFrame frame = new JFrame("미러연설학습기v" + ver.app_Version);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 700);
		frame.setLayout(new BorderLayout());
		ImageIcon icon = new ImageIcon("res/mlsp.png");
		frame.setIconImage(icon.getImage());
		// ---------------------------
		// 상단 버튼 영역 (Horizontal)
		// ---------------------------
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JButton btnRefresh = new JButton("새로고침");
		btnRefresh.addActionListener(e -> {
			
			listModel.clear();
			
			mlsp.fileRenew();
		});
		
		
		JButton btnTextOption = new JButton("텍스트옵션");
		btnTextOption.addActionListener(e -> {
			File folder = new File("res");
			File optionFile = new File(folder, "textoption.mlspo");
			// 다이얼로그 생성
			JDialog dialog = new JDialog();
			dialog.setTitle("글자 옵션");
			dialog.setSize(300, 250);
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true);
			dialog.setLayout(null);
			
			// 라벨
			JLabel lblSize = new JLabel("글자크기 :");
			lblSize.setBounds(20, 20, 80, 25);
			dialog.add(lblSize);
			
			// 콤보박스 (14~30, 2씩 증가)
			Integer[] sizes = new Integer[9]; // 14~30 → 9개
			int index = 0;
			for (int i = 14; i <= 30; i += 2) {
				sizes[index++] = i;
			}
			
			JComboBox<Integer> comboSize = new JComboBox<>(sizes);
			comboSize.setBounds(110, 20, 80, 25);
			dialog.add(comboSize);
			
			// Bold 체크박스
			JCheckBox chkBold = new JCheckBox("Bold");
			chkBold.setBounds(20, 60, 100, 25);
			dialog.add(chkBold);
			
			// Italic 체크박스
			JCheckBox chkItalic = new JCheckBox("Italic");
			chkItalic.setBounds(20, 90, 100, 25);
			dialog.add(chkItalic);
			
			// 확인 버튼
			JButton btnOk = new JButton("확인");
			btnOk.setBounds(40, 140, 80, 30);
			dialog.add(btnOk);
			
			// 취소 버튼
			JButton btnCancel = new JButton("취소");
			btnCancel.setBounds(150, 140, 80, 30);
			dialog.add(btnCancel);
			// 기본값
			int setsize = 20;
			boolean setbold = false;
			boolean setitalic = false;
			
			// 옵션 파일 읽기
			if (optionFile.exists()) {
				try (BufferedReader br = new BufferedReader(new FileReader(optionFile))) {
					String line;
					
					while ((line = br.readLine()) != null) {
						line = line.trim();
						if (line.isEmpty()) continue;
						
						String[] parts = line.split(":");
						if (parts.length != 2) continue;
						
						String key = parts[0].trim();
						String value = parts[1].trim();
						
						switch (key) {
							case "size":
							setsize = Integer.parseInt(value);
							break;
							case "bold":
							setbold = Boolean.parseBoolean(value);
							break;
							case "italic":
							setitalic = Boolean.parseBoolean(value);
							break;
						}
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			// 읽은 옵션을 UI에 적용
			comboSize.setSelectedItem(setsize);
			chkBold.setSelected(setbold);
			chkItalic.setSelected(setitalic);
			// 확인 버튼 이벤트
			btnOk.addActionListener(ev -> {
				int size = (Integer) comboSize.getSelectedItem();
				
				int style = Font.PLAIN;
				if (chkBold.isSelected() && chkItalic.isSelected()) {
					style = Font.BOLD | Font.ITALIC;
				} else if (chkBold.isSelected()) {
					style = Font.BOLD;
				} else if (chkItalic.isSelected()) {
					style = Font.ITALIC;
				}
				
				// textPane 글자 옵션 적용
				Font newFont = new Font(textPane.getFont().getName(), style, size);
				textPane.setFont(newFont);
				area1.setFont(newFont);
				area2.setFont(newFont);
				lineNumberArea.setFont(newFont);
				saveTextOption(size, chkBold.isSelected(), chkItalic.isSelected());
				dialog.dispose();
				
			});
			
			// 취소 버튼 이벤트
			btnCancel.addActionListener(ev -> dialog.dispose());
			
			dialog.setVisible(true);
		});
		
		JButton btnInfo = new JButton("정보");
		btnInfo.addActionListener(e -> {
			
			// 다이얼로그 생성
			JDialog dialog = new JDialog();
			dialog.setTitle("정보창");
			dialog.setModal(true);
			dialog.setSize(400, 300);
			dialog.setLocationRelativeTo(null);
			dialog.setLayout(new BorderLayout());
			
			// 텍스트 영역
			JTextArea area = new JTextArea();
			area.setFont(new Font("맑은 고딕", Font.BOLD, 16));
			area.setLineWrap(true);
			area.setWrapStyleWord(true);
			area.setText("");      // 텍스트 초기화
			area.append("앱이름 : 미러리더연설학습기" + "\n");
			area.append("app_Name : " + ver.app_Name + "\n");
			area.append("app_Version : " + ver.app_Version + "\n");
			area.append("app_Code : " + ver.app_Code + "\n");
			area.append("미러연설학습기입니다." + "\n");
			area.append("앱 다운로드 : http://javafantasy.kr" + "\n");
			area.append("텍스트를 클릭하면 뜻이 나옵니다." + "\n");
			area.append("텍스트를 드래그하면 번역기가 번역을 합니다." + "\n");
			area.append("아파치2.0라이선스를 사용합니다." + "\n");
			area.append("번역기는 구글번역기로 접속하고, 사전은 스타딕트를 썼습니다." + "\n");
			area.append("연설공부를 편하게 하기위해 만들었습니다." + "\n");
			area.append("사전 뜻이 잘 나오게 할려면 드래그하면 안됩니다." + "\n");
			area.append("클릭을 정확하게 해야 합니다." + "\n");
			area.append("0.0021버전에서 검색이 안되면 소문자로 고쳐서 2번 검색하도록 바꿨습니다." + "\n");
			area.append("0.0021A버전에서 호리존탈스크롤바가 되게 하였고, 텍스트패인에 WSAD키를 누르면 스크롤바가 이동되게 하였습니다." + "\n");
			area.append("0.0021B버전에서 검색 안되는거 조금 수정했습니다." + "\n");
			
			area.setCaretPosition(0);
			// 스크롤
			JScrollPane scroll = new JScrollPane(area);
			dialog.add(scroll, BorderLayout.CENTER);
			
			// 확인 버튼
			JButton btnOk = new JButton("확인");
			dialog.add(btnOk, BorderLayout.SOUTH);
			
			// 확인 버튼 클릭 이벤트
			btnOk.addActionListener(ev -> {
				dialog.dispose();      // 다이얼로그 닫기
				
			});
			
			dialog.setVisible(true);
		});
		
		JButton btnExit = new JButton("종료");
		btnExit.addActionListener(e -> {
			System.exit(0);
		});
		topPanel.add(btnRefresh);
		
		topPanel.add(btnTextOption);
		topPanel.add(btnInfo);
		topPanel.add(btnExit);
		
		frame.add(topPanel, BorderLayout.NORTH);
		
		// ---------------------------
		// 중앙 영역 (JList + JTextPane + LineNumber Panel)
		// ---------------------------
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		
		// 왼쪽 JList
		
		
		
		
		
		
		JScrollPane listScroll = new JScrollPane(speechList);
		listScroll.setPreferredSize(new Dimension(200, 0));
		speechList.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				String selectedName = speechList.getSelectedValue();
				System.out.println(selectedName);
				if (selectedName != null && !selectedName.isEmpty() && !selectedName.equals(""))  {
					String seltext = fileRead(selectedName);
					
					lineCount = 0;
					for (int i = 0; i < seltext.length(); i++) {
						if (seltext.charAt(i) == '\n' ) {
							lineCount++;
						}
					}
					System.out.println("lineCount : " + lineCount);
					textPane.setText(seltext);
					//textPane.setText(fileRead(seltext));
					textPane.setCaretPosition(0);
				}
			}
		});
		JScrollPane textScroll = new JScrollPane(textPane);
		textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		textScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textPane.setEditable(false);
		// 줄번호 Panel
		textPane.setEditorKit(new StyledEditorKit() {
			@Override
			public ViewFactory getViewFactory() {
				return new NoWrapFactory();
			}
		});
		
		textPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				e.consume(); // ★ 키 입력 완전 차단
				JScrollBar vBar = textScroll.getVerticalScrollBar();
				JScrollBar hBar = textScroll.getHorizontalScrollBar();
				
				int amount = 40; // 스크롤 이동량 (원하면 조절 가능)
				
				switch (e.getKeyCode()) {
					case KeyEvent.VK_W: // 위로
					vBar.setValue(vBar.getValue() - amount);
					break;
					
					case KeyEvent.VK_S: // 아래로
					vBar.setValue(vBar.getValue() + amount);
					break;
					
					case KeyEvent.VK_A: // 왼쪽
					hBar.setValue(hBar.getValue() - amount);
					break;
					
					case KeyEvent.VK_D: // 오른쪽
					hBar.setValue(hBar.getValue() + amount);
					break;
				}
			}
			
			
			@Override
			public void keyTyped(KeyEvent e) {
				e.consume();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				e.consume();
			}
		});
		
		
		
		
		
		
		
		lineNumberArea.setEditable(false);
		lineNumberArea.setBackground(new Color(230, 230, 230));
		// 줄번호 영역을 감싸는 패널
		JPanel linePanel = new JPanel(new BorderLayout());
		linePanel.add(lineNumberArea, BorderLayout.CENTER);
		JScrollPane lineScroll = new JScrollPane(linePanel);
		lineScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		lineScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// ★ 핵심: 줄번호를 rowHeaderView로 설정
		textScroll.setRowHeaderView(lineNumberArea);
		
		textPane.getDocument().addDocumentListener(new DocumentListener() {
			public void update() {
				//SwingUtilities.invokeLater(() -> {
				
				//System.out.println("라인카운트 : " + lineCount);
				
				// 줄번호 텍스트 갱신
				StringBuilder sb = new StringBuilder();
				
				for (int i = 1; i <= lineCount+2; i++) {
					sb.append(i).append("\n");
				}
				lineNumberArea.setText(sb.toString());
				
				// ★ 가로 폭 자동 조절
				FontMetrics fm = lineNumberArea.getFontMetrics(lineNumberArea.getFont());
				int maxDigits = String.valueOf(lineCount).length();   // 자릿수 계산
				int newWidth = fm.stringWidth("0".repeat(maxDigits)) + 10; // 여유 10px
				
				linePanel.setPreferredSize(new Dimension(newWidth, linePanel.getHeight()));
				linePanel.revalidate();
				//});
			}
			
			public void insertUpdate(DocumentEvent e) { update(); }
			public void removeUpdate(DocumentEvent e) { update(); }
			public void changedUpdate(DocumentEvent e) { update(); }
		});
		
		
		
		textPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int pos = textPane.getCaretPosition();
				try {
					int start = javax.swing.text.Utilities.getWordStart(textPane, pos);
					int end = javax.swing.text.Utilities.getWordEnd(textPane, pos);
					String word = textPane.getDocument().getText(start, end - start).trim();
					
					System.out.println("선택된 단어: " + word);
					String tempStr = d.search(word);
					if(tempStr.equals("뜻을 찾을 수 없습니다."))
					{
						word = word.toLowerCase();   // ★ 소문자로 변환
						tempStr = d.search(word);
						if(tempStr.equals("뜻을 찾을 수 없습니다."))
						{
							if (word.endsWith("s")) {
								word = word.substring(0, word.length() - 1);
								if (word.endsWith("'")) {
									word = word.substring(0, word.length() - 1);
								}
								tempStr = d.search(word);
								if(tempStr.equals("뜻을 찾을 수 없습니다."))
								{
									if (word.endsWith("e")) {
										word = word.substring(0, word.length() - 1);
									}
									tempStr = d.search(word);
									if(tempStr.equals("뜻을 찾을 수 없습니다."))
									{
										if (word.endsWith("i")) {
											word = word.substring(0, word.length() - 1) + "y";
											System.out.println("수정된 단어 : " +word);
											tempStr = d.search(word);
										}
									}
								}
								
							}else if(word.endsWith("st"))
							{
								word = word.substring(0, word.length() - 2);
								tempStr = d.search(word);
								if(tempStr.equals("뜻을 찾을 수 없습니다."))
								{
									if(word.endsWith("e"))
									{
										word = word.substring(0, word.length() - 1);
										tempStr = d.search(word);
										if(tempStr.equals("뜻을 찾을 수 없습니다."))
										{
											word = word.substring(0, word.length() - 1);
											tempStr = d.search(word);
										}
									}
								}
							}else if(word.endsWith("r"))
							{
								word = word.substring(0, word.length() - 1);
								tempStr = d.search(word);
								if(tempStr.equals("뜻을 찾을 수 없습니다."))
								{
									if(word.endsWith("e"))
									{
										word = word.substring(0, word.length() - 1);
										tempStr = d.search(word);
										if(tempStr.equals("뜻을 찾을 수 없습니다."))
										{
											word = word.substring(0, word.length() - 1);
											tempStr = d.search(word);
										}
									}
								}
							}else if(word.endsWith("d"))
							{
								word = word.substring(0, word.length() - 1);
								tempStr = d.search(word);
								if(tempStr.equals("뜻을 찾을 수 없습니다."))
								{
									if(word.endsWith("e"))
									{
										word = word.substring(0, word.length() - 1);
										tempStr = d.search(word);
										if(tempStr.equals("뜻을 찾을 수 없습니다."))
										{
											word = word.substring(0, word.length() - 1);
											tempStr = d.search(word);
										}
									}
								}
							}else if(word.endsWith("ing"))
							{
								word = word.substring(0, word.length() - 3);
								tempStr = d.search(word);
								if(tempStr.equals("뜻을 찾을 수 없습니다."))
								{
									word = word.substring(0, word.length() - 1);
									tempStr = d.search(word);
								}
							}
						}
					}
					area1.setText(tempStr);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				String selected = textPane.getSelectedText();
				if (selected != null && !selected.isEmpty()) {
					String txt = selected.trim();
					area2.setText(WebData.googleTransSearch(txt));
				}
			}
		});
		
		
		
		
		// 중앙 구성: 왼쪽 리스트 + 가운데 텍스트 + 오른쪽 줄번호
		JPanel textPanel = new JPanel(new BorderLayout());
		textPanel.add(textScroll, BorderLayout.CENTER);
		textPanel.add(lineScroll, BorderLayout.WEST);
		
		centerPanel.add(listScroll, BorderLayout.WEST);
		centerPanel.add(textPanel, BorderLayout.CENTER);
		
		frame.add(centerPanel, BorderLayout.CENTER);
		
		// ---------------------------
		// 하단 영역 (JTextArea 2개, Horizontal)
		// ---------------------------
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		
		
		area1.setEditable(false);
		area2.setEditable(false);
		JScrollPane areaScroll1 = new JScrollPane(area1);
		JScrollPane areaScroll2 = new JScrollPane(area2);
		
		areaScroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		areaScroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		areaScroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		areaScroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		bottomPanel.add(areaScroll1);
		bottomPanel.add(areaScroll2);
		
		bottomPanel.setPreferredSize(new Dimension(1000, 700 * 3 / 10)); // 30%
		
		frame.add(bottomPanel, BorderLayout.SOUTH);
		
		frame.setVisible(true);
		//여기서부터 코드...
		mlsp.fileRenew();
		mlsp.loadTextOption();
		String dictfolder = "res" + File.separator;
		
		try {
			d = new WebData.Dictionary(
				dictfolder + "quick_english-korean.idx",
				dictfolder + "quick_english-korean.dict"
				);
			
			System.out.println(d.search("apple"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void fileRenew()
	{
		// SpeechText 폴더 경로
		File folder = new File("SpeechText");
		
		// 폴더가 없으면 생성
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		// welcome.txt 파일 경로
		File welcomeFile = new File(folder, "welcome.txt");
		
		// welcome.txt가 없으면 새로 생성
		if (!welcomeFile.exists()) {
			try {
				welcomeFile.createNewFile();
				
				// 기본 내용 넣고 싶으면 여기서 작성 가능
				try (FileWriter writer = new FileWriter(welcomeFile)) {
					writer.write("Welcome to the Mirror English Speech Practice Program!");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		// 폴더 존재 여부 확인
		if (folder.exists() && folder.isDirectory()) {
			
			// txt 파일만 필터링
			File[] txtFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
			
			if (txtFiles != null) {
				
				// 파일명 목록 생성 (확장자 제거)
				List<String> names = new ArrayList<>();
				for (File f : txtFiles) {
					String fileName = f.getName();
					String nameWithoutExt = fileName.substring(0, fileName.lastIndexOf("."));
					names.add(nameWithoutExt);
				}
				
				// 내림차순 정렬
				names.sort(Collections.reverseOrder());
				
				// welcome을 맨 위로 올리기
				names.remove("welcome");   // 기존 위치 제거
				names.add(0, "welcome");   // 맨 앞에 추가
				
				// 리스트 모델에 추가
				for (String name : names) {
					listModel.addElement(name);
				}
				if (!listModel.isEmpty()) {
					speechList.setSelectedIndex(0);
					
					
					
					
					if (welcomeFile.exists()) {
						try {
							StringBuilder sb = new StringBuilder();
							BufferedReader br = new BufferedReader(new FileReader(welcomeFile));
							String line;
							
							while ((line = br.readLine()) != null) {
								sb.append(line).append("\n");
							}
							br.close();
							
							textPane.setText(sb.toString());
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			}
		}
		
		
	}
	private static String fileRead(String name)
	{
		// SpeechText 폴더 경로
		File folder = new File("SpeechText");
		
		// 폴더가 없으면 생성
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		// 읽을 파일 경로
		File file = new File(folder, name + ".txt");
		
		// 파일이 없으면 빈 문자열 반환
		if (!file.exists()) {
			return name + "";
			
			
		}
		
		// 파일 읽기
		StringBuilder sb = new StringBuilder();
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	private static File getOptionFile() {
		File folder = new File("res");
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		File optionFile = new File(folder, "textoption.mlspo");
		
		// 파일이 없으면 기본값으로 생성
		if (!optionFile.exists()) {
			try (FileWriter fw = new FileWriter(optionFile)) {
				fw.write("size: 20\n");
				fw.write("bold: false\n");
				fw.write("italic: false\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return optionFile;
	}
	
	private static void saveTextOption(int size, boolean bold, boolean italic) {
		File optionFile = getOptionFile();
		
		try (FileWriter fw = new FileWriter(optionFile)) {
			fw.write("size: " + size + "\n");
			fw.write("bold: " + bold + "\n");
			fw.write("italic: " + italic + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void loadTextOption() {
		
		// res 폴더 준비
		File folder = new File("res");
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		// 옵션 파일
		File optionFile = new File(folder, "textoption.mlspo");
		
		// 파일 없으면 기본 생성
		if (!optionFile.exists()) {
			try (FileWriter fw = new FileWriter(optionFile)) {
				fw.write("size: 20\n");
				fw.write("bold: false\n");
				fw.write("italic: false\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 기본값
		int size = 20;
		boolean bold = false;
		boolean italic = false;
		
		// 파일 읽기
		try (BufferedReader br = new BufferedReader(new FileReader(optionFile))) {
			
			String line;
			
			while ((line = br.readLine()) != null) {
				
				line = line.trim();  // 앞뒤 공백 제거
				if (line.isEmpty()) continue; // 빈 줄 무시
				
				// "size: 20" → ["size", "20"]
				String[] parts = line.split(":");
				
				if (parts.length != 2) continue;
				
				String key = parts[0].trim();
				String value = parts[1].trim();
				
				switch (key) {
					case "size":
					size = Integer.parseInt(value);
					break;
					
					case "bold":
					bold = Boolean.parseBoolean(value);
					break;
					
					case "italic":
					italic = Boolean.parseBoolean(value);
					break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Font 스타일 결정
		int style = Font.PLAIN;
		if (bold && italic) style = Font.BOLD | Font.ITALIC;
		else if (bold) style = Font.BOLD;
		else if (italic) style = Font.ITALIC;
		
		// Font 적용
		Font font = new Font(textPane.getFont().getName(), style, size);
		textPane.setFont(font);
		lineNumberArea.setFont(font);
		area1.setFont(font);
		area2.setFont(font);
	}
	
	
}
