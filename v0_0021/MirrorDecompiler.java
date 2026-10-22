import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.ViewFactory;
import javax.swing.text.View;
import javax.swing.text.Element;
import javax.swing.text.ParagraphView;
import javax.swing.text.LabelView;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.IconView;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.StyleConstants;
import javax.swing.text.AbstractDocument;

import org.benf.cfr.reader.Main;

import Version.Version;
public class MirrorDecompiler {
	JTextPane textPane = new JTextPane();
	Version ver = new Version();
	
	class NoWrapFactory implements ViewFactory {
		@Override
		public View create(Element elem) {
			String kind = elem.getName();
			
			if (kind != null) {
				if (kind.equals(AbstractDocument.ContentElementName)) {
					return new LabelView(elem);
				} else if (kind.equals(AbstractDocument.ParagraphElementName)) {
					return new NoWrapParagraphView(elem);
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
	
	class NoWrapParagraphView extends ParagraphView {
		public NoWrapParagraphView(Element elem) {
			super(elem);
		}
		
		@Override
		public void layout(int width, int height) {
			super.layout(Integer.MAX_VALUE, height);  // wrap OFF
		}
		
		@Override
		public float getMinimumSpan(int axis) {
			return super.getPreferredSpan(axis);
		}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(MirrorDecompiler::new);
	}
	
	public MirrorDecompiler() {
		JFrame frame = new JFrame("미러디컴파일러v" + ver.app_Version);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		ImageIcon icon = new ImageIcon("res/icon.png");  // 아이콘 파일 경로
		frame.setIconImage(icon.getImage());
		
		// ===== 상단 버튼 패널 =====
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JButton openBtn = new JButton("열기");
		JButton infoBtn = new JButton("정보");
		JButton exitBtn = new JButton("종료");
		
		
		openBtn.addActionListener(e -> {
			
			try {
				// path.txt 파일 준비
				File pathFile = new File("path.txt");
				String initialPath;
				
				if (pathFile.exists()) {
					// path.txt 읽기
					initialPath = new String(java.nio.file.Files.readAllBytes(pathFile.toPath())).trim();
					if (initialPath.isEmpty()) {
						initialPath = System.getProperty("user.dir");
					}
				} else {
					// path.txt 없으면 생성
					initialPath = System.getProperty("user.dir");
					java.nio.file.Files.write(pathFile.toPath(), initialPath.getBytes());
				}
				
				// JFileChooser 생성
				JFileChooser chooser = new JFileChooser(initialPath);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Class Files (*.class)", "class"
					);
				chooser.setFileFilter(filter);
				int result = chooser.showOpenDialog(frame);
				
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					
					// 선택한 디렉토리를 path.txt에 저장
					String newPath = selectedFile.getParent();
					java.nio.file.Files.write(pathFile.toPath(), newPath.getBytes());
					
					// 디컴파일 실행
					String decompiled = decompileWithCFR(selectedFile.getAbsolutePath());
					
					// 결과 출력
					textPane.setText(decompiled);
					textPane.setCaretPosition(0);   // ★ 맨 위로 이동
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		infoBtn.addActionListener(e -> {
			
			// 다이얼로그 생성
			JDialog dialog = new JDialog(frame, "정보", true);
			dialog.setSize(600, 300);
			dialog.setLayout(new BorderLayout());
			
			// 텍스트 영역 생성
			JTextArea textArea = new JTextArea();
			textArea.setFont(new Font("Serif", Font.PLAIN, 14));
			
			// 10줄 append (for 사용하지 말라고 했으니까 그냥 직접 넣음)
			textArea.append("미러디컴파일러\n");
			textArea.append("app_Name : " + ver.app_Name +"\n");
			textArea.append("app_Version : " + ver.app_Version +"\n");
			textArea.append("app_Code : " + ver.app_Code +"\n");
			textArea.append("미러디컴파일러입니다.\n");
			textArea.append("디컴파일러 된다고 보안에 문제가 생기는것은 아닙니다.\n");
			textArea.append("변수이름만 fasdklfjlasdjldfjasljf1~100까지로 바꿔도 못알아봅니다.\n");
			textArea.append("한번 분석해보니깐 이미 난독이 모두 되어 있습니다.\n");
			textArea.append("아파치2.0라이센스를 따르고 있습니다.\n");
			textArea.append("간단하게 만들었습니다.\n");
			textArea.append("0.0021버전에 호리존탈스크롤바가 되게 만들었습니다.\n");
			
			
			// 스크롤 추가
			JScrollPane scrollPane = new JScrollPane(textArea);
			
			// 확인 버튼
			JButton okBtn = new JButton("확인");
			okBtn.addActionListener(ev -> dialog.dispose());
			
			// 배치
			dialog.add(scrollPane, BorderLayout.CENTER);
			dialog.add(okBtn, BorderLayout.SOUTH);
			
			dialog.setLocationRelativeTo(frame); // 화면 중앙 배치
			dialog.setVisible(true);
		});
		
		exitBtn.addActionListener(e -> {
			
			System.exit(0); // 프로그램 종료
		});
		
		topPanel.add(openBtn);
		topPanel.add(infoBtn);
		topPanel.add(exitBtn);
		
		// ===== 중앙 텍스트 영역 =====
		textPane.setEditable(false);
		textPane.setEditorKit(new StyledEditorKit() {
			@Override
			public ViewFactory getViewFactory() {
				return new NoWrapFactory();
			}
		});
		// ScrollPane 추가 + 스크롤 정책 AS_NEEDED
		JScrollPane scrollPane = new JScrollPane(
			textPane,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
			);
		// ===== 줄번호 영역(NumberArea) =====
		JPanel numberArea = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.GRAY);
				
				int lineHeight = g.getFontMetrics().getHeight();
				int start = 0;
				int end = textPane.getDocument().getDefaultRootElement().getElementCount();
				
				for (int i = 0; i < end; i++) {
					int y = (i + 1) * lineHeight;
					g.drawString(String.valueOf(i + 1), 5, y);
				}
			}
		};
		numberArea.setPreferredSize(new Dimension(40, 0));
		
		scrollPane.setRowHeaderView(numberArea);
		textPane.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				onTextChanged();
			}
			
			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				onTextChanged();
			}
			
			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				onTextChanged();
			}
			
			private void onTextChanged() {
				int lineHeight = numberArea.getFontMetrics(numberArea.getFont()).getHeight();
				int lineCount = textPane.getDocument().getDefaultRootElement().getElementCount();
				
				numberArea.setPreferredSize(new Dimension(40, lineCount * lineHeight));
				numberArea.revalidate();  // 레이아웃 갱신
				numberArea.repaint();
			}
		});
		// ===== 텍스트 + 줄번호를 담는 패널 =====
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		
		// ===== 전체 프레임 구성 =====
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(centerPanel, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}
	public String decompileWithCFR(String classFilePath) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			
			// 기존 stdout 저장
			PrintStream oldOut = System.out;
			System.setOut(ps);
			
			// CFR 실행 (stdout 옵션 제거!)
			org.benf.cfr.reader.Main.main(new String[]{
				classFilePath
			});
			
			// stdout 복구
			System.setOut(oldOut);
			
			return baos.toString("UTF-8");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return "디컴파일 오류 발생";
		}
	}
	
	
	
}
