import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Version.Version;
public class AntColorInfo {
	private static JTextField rField,gField,bField,aField;
	private static JTextField rgbHexField, rgbaHexField, argbHexField, rgbDecField, rgbaDecField, argbDecField;
	private static int mr,mg,mb,ma;
	private static JPanel mainPanel,leftPanel,infoPanel,colorPreview;
	public static void main(String[] args) {
		JFrame frame = new JFrame("앤트색상정보기v"+Version.app_Version);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 600);
		frame.setLayout(null);
		
		// 메인 패널 (512x512)
		// 클릭 좌표 저장 변수
			final int[] clickX = { -1 };
			final int[] clickY = { -1 };
		
		// 메인 패널 (512x512)
		mainPanel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				int pixelSize = 2; // 2x2 pixel
				
				// 색상 그리드
				for (int r = 0; r < 256; r++) {
					for (int gVal = 0; gVal < 256; gVal++) {
						
						Color color = new Color(r, gVal, mb, ma);
						g.setColor(color);
						
						int x = r * pixelSize;
						int y = gVal * pixelSize;
						
						g.fillRect(x, y, pixelSize, pixelSize);
					}
				}
				
				// 클릭한 위치에 4x4 하얀색 원 그리기
				
				g.setColor(Color.WHITE);
				g.drawOval(((mr *2) - 4), ((mg*2) - 4), 8, 8);
				
			}
		};
		
		// 클릭 이벤트 추가
		mainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				clickX[0] = e.getX();
				clickY[0] = e.getY();
				mr = clickX[0] / 2;
				mg = clickY[0] / 2;
				String rString = String.valueOf(mr);
				String gString = String.valueOf(mg);
				rField.setText(rString);
				gField.setText(gString);
				mainPanel.repaint(); // 다시 그리기
				leftPanel.repaint();
				colorPreview.repaint();
				String hex = String.format("0x%02X%02X%02X", mr, mg, mb);
				rgbHexField.setText(hex);
				long decimal = Long.parseLong(hex.substring(2), 16);
				rgbDecField.setText(Long.toString(decimal));
				hex = String.format("0x%02X%02X%02X%02X",mr,mg,mb,ma);
				rgbaHexField.setText(hex);
				decimal = Long.parseLong(hex.substring(2), 16);
				rgbaDecField.setText(Long.toString(decimal));
				hex = String.format("0x%02X%02X%02X%02X", ma, mr, mg, mb);
				argbHexField.setText(hex);
				decimal = Long.parseLong(hex.substring(2), 16);
				argbDecField.setText(Long.toString(decimal));
				
			}
		});
		
		mainPanel.setBounds(10, 10, 512, 512);
		frame.add(mainPanel);
		
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(10, 10, 512, 512);
		frame.add(mainPanel);
		
		// 왼쪽 작은 패널 (30x512)
		// 클릭한 y좌표 저장 변수
			final int[] clickZ = { -1 };
		
		leftPanel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				int blockWidth = 30;  // 가로 30px
				int blockHeight = 2;  // 세로 2px
				
				for (int bVal = 0; bVal < 256; bVal++) {
					
					Color color = new Color(mr, mg, bVal, ma);
					g.setColor(color);
					
					int y = bVal * blockHeight;
					
					g.fillRect(0, y, blockWidth, blockHeight);
				}
				
				// 클릭한 위치에 강조 박스 그리기
				
				g.setColor(Color.WHITE);
				g.drawRect(0,((mb*2) - 4), 30, 8);
				
			}
		};
		
		// 클릭 이벤트 추가
		leftPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				clickZ[0] = e.getY();
				mb = clickZ[0]/2;
				String bString = String.valueOf(mb);
				bField.setText(bString);
				mainPanel.repaint();
				leftPanel.repaint();
				colorPreview.repaint();
				String hex = String.format("0x%02X%02X%02X", mr, mg, mb);
				rgbHexField.setText(hex);
				long decimal = Long.parseLong(hex.substring(2), 16);
				rgbDecField.setText(Long.toString(decimal));
				hex = String.format("0x%02X%02X%02X%02X",mr,mg,mb,ma);
				rgbaHexField.setText(hex);
				decimal = Long.parseLong(hex.substring(2), 16);
				rgbaDecField.setText(Long.toString(decimal));
				hex = String.format("0x%02X%02X%02X%02X", ma, mr, mg, mb);
				argbHexField.setText(hex);
				decimal = Long.parseLong(hex.substring(2), 16);
				argbDecField.setText(Long.toString(decimal));
			}
		});
		leftPanel.setBackground(Color.LIGHT_GRAY);
		leftPanel.setBounds(530, 10, 30, 512);
		frame.add(leftPanel);
		
		// 오른쪽 정보 패널
		infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBounds(570, 10, 300, 512);
		frame.add(infoPanel);
		// 0~255 가로 슬라이더
		JSlider aslider = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
		aslider.setBounds(10, 530, 530, 50); // 원하는 위치로 조정 가능
		//aslider.setMajorTickSpacing(50);
		//aslider.setMinorTickSpacing(10);
		aslider.setPaintTicks(true);
		aslider.setPaintLabels(true);
		aslider.addChangeListener(e -> {
			int value = aslider.getValue();
			
			
			// 예: a값 업데이트
			ma = value;
			aField.setText(Integer.toString(value));
			// 패널 다시 그리기
			mainPanel.repaint();
			leftPanel.repaint();
			colorPreview.repaint();
			String hex = String.format("0x%02X%02X%02X", mr, mg, mb);
			rgbHexField.setText(hex);
			long decimal = Long.parseLong(hex.substring(2), 16);
			rgbDecField.setText(Long.toString(decimal));
			hex = String.format("0x%02X%02X%02X%02X",mr,mg,mb,ma);
			rgbaHexField.setText(hex);
			decimal = Long.parseLong(hex.substring(2), 16);
			rgbaDecField.setText(Long.toString(decimal));
			hex = String.format("0x%02X%02X%02X%02X", ma, mr, mg, mb);
			argbHexField.setText(hex);
			decimal = Long.parseLong(hex.substring(2), 16);
			argbDecField.setText(Long.toString(decimal));
		});
		frame.add(aslider);
		
		// 30x30 패널
		colorPreview = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				int argb = (ma << 24) | (mr << 16) | (mg << 8) | mb;
				Color color = new Color(argb, true);
				g.setColor(color);
				g.fillRect(0,0,30,30);
				
			}
		};
		
		colorPreview.setBounds(10, 10, 30, 30);
		infoPanel.add(colorPreview);
		
		int y = 50;
		
		// r
		JLabel rLabel = new JLabel("r : ");
		rLabel.setBounds(10, y, 30, 25);
		infoPanel.add(rLabel);
		
		rField = new JTextField();
		rField.setBounds(50, y, 80, 25);
		infoPanel.add(rField);
		rField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = rField.getText();
				
				// 빈 문자열이면 그냥 무시
				if (text.isEmpty()) {
					return;
				}
				
				// 숫자가 아니면 경고
				if (!text.matches("\\d+")) {
					JOptionPane.showMessageDialog(null, "숫자만 입력 가능합니다.");
					rField.setText("0");
					// 패널 다시 그리기
					mainPanel.repaint();
					leftPanel.repaint();
					colorPreview.repaint();
					return;
					
				}
				
				// 정수 변환
				int value = Integer.parseInt(text);
				
				// 범위 체크
				if (value < 0 || value > 255) {
					JOptionPane.showMessageDialog(null, "0~255 범위의 값만 입력 가능합니다.");
					rField.setText("0");
					value = 0;
				}
				mr = value;
				// 패널 다시 그리기
				mainPanel.repaint();
				leftPanel.repaint();
				colorPreview.repaint();
				String hex = String.format("0x%02X%02X%02X", mr, mg, mb);
				rgbHexField.setText(hex);
				long decimal = Long.parseLong(hex.substring(2), 16);
				rgbDecField.setText(Long.toString(decimal));
				hex = String.format("0x%02X%02X%02X%02X",mr,mg,mb,ma);
				rgbaHexField.setText(hex);
				decimal = Long.parseLong(hex.substring(2), 16);
				rgbaDecField.setText(Long.toString(decimal));
				hex = String.format("0x%02X%02X%02X%02X", ma, mr, mg, mb);
				argbHexField.setText(hex);
				decimal = Long.parseLong(hex.substring(2), 16);
				argbDecField.setText(Long.toString(decimal));
			}
		});
		
		
		
		y += 35;
		
		// g
		JLabel gLabel = new JLabel("g : ");
		gLabel.setBounds(10, y, 30, 25);
		infoPanel.add(gLabel);
		
		gField = new JTextField();
		gField.setBounds(50, y, 80, 25);
		infoPanel.add(gField);
		gField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = gField.getText();
				
				// 빈 문자열이면 그냥 무시
				if (text.isEmpty()) {
					return;
				}
				
				// 숫자가 아니면 경고
				if (!text.matches("\\d+")) {
					JOptionPane.showMessageDialog(null, "숫자만 입력 가능합니다.");
					gField.setText("0");
					// 패널 다시 그리기
					mainPanel.repaint();
					leftPanel.repaint();
					colorPreview.repaint();
					return;
					
				}
				
				// 정수 변환
				int value = Integer.parseInt(text);
				
				// 범위 체크
				if (value < 0 || value > 255) {
					JOptionPane.showMessageDialog(null, "0~255 범위의 값만 입력 가능합니다.");
					gField.setText("0");
					value = 0;
				}
				mg = value;
				// 패널 다시 그리기
				mainPanel.repaint();
				leftPanel.repaint();
				colorPreview.repaint();
				String hex = String.format("0x%02X%02X%02X", mr, mg, mb);
				rgbHexField.setText(hex);
				long decimal = Long.parseLong(hex.substring(2), 16);
				rgbDecField.setText(Long.toString(decimal));
				hex = String.format("0x%02X%02X%02X%02X",mr,mg,mb,ma);
				rgbaHexField.setText(hex);
				decimal = Long.parseLong(hex.substring(2), 16);
				rgbaDecField.setText(Long.toString(decimal));
				hex = String.format("0x%02X%02X%02X%02X", ma, mr, mg, mb);
				argbHexField.setText(hex);
				decimal = Long.parseLong(hex.substring(2), 16);
				argbDecField.setText(Long.toString(decimal));
			}
		});
		
		
		y += 35;
		
		// b
		JLabel bLabel = new JLabel("b : ");
		bLabel.setBounds(10, y, 30, 25);
		infoPanel.add(bLabel);
		
		bField = new JTextField();
		bField.setBounds(50, y, 80, 25);
		infoPanel.add(bField);
		bField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = bField.getText();
				
				// 빈 문자열이면 그냥 무시
				if (text.isEmpty()) {
					return;
				}
				
				// 숫자가 아니면 경고
				if (!text.matches("\\d+")) {
					JOptionPane.showMessageDialog(null, "숫자만 입력 가능합니다.");
					bField.setText("0");
					// 패널 다시 그리기
					mainPanel.repaint();
					leftPanel.repaint();
					colorPreview.repaint();
					return;
					
				}
				
				// 정수 변환
				int value = Integer.parseInt(text);
				
				// 범위 체크
				if (value < 0 || value > 255) {
					JOptionPane.showMessageDialog(null, "0~255 범위의 값만 입력 가능합니다.");
					bField.setText("0");
					value = 0;
				}
				mb = value;
				// 패널 다시 그리기
				mainPanel.repaint();
				leftPanel.repaint();
				colorPreview.repaint();
				String hex = String.format("0x%02X%02X%02X", mr, mg, mb);
				rgbHexField.setText(hex);
				long decimal = Long.parseLong(hex.substring(2), 16);
				rgbDecField.setText(Long.toString(decimal));
				hex = String.format("0x%02X%02X%02X%02X",mr,mg,mb,ma);
				rgbaHexField.setText(hex);
				decimal = Long.parseLong(hex.substring(2), 16);
				rgbaDecField.setText(Long.toString(decimal));
				hex = String.format("0x%02X%02X%02X%02X", ma, mr, mg, mb);
				argbHexField.setText(hex);
				decimal = Long.parseLong(hex.substring(2), 16);
				argbDecField.setText(Long.toString(decimal));
			}
		});
		y += 35;
		
		// b
		JLabel aLabel = new JLabel("a : ");
		aLabel.setBounds(10, y, 30, 25);
		infoPanel.add(aLabel);
		
		aField = new JTextField();
		aField.setBounds(50, y, 80, 25);
		infoPanel.add(aField);
		aField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = aField.getText();
				
				// 빈 문자열이면 그냥 무시
				if (text.isEmpty()) {
					return;
				}
				
				// 숫자가 아니면 경고
				if (!text.matches("\\d+")) {
					JOptionPane.showMessageDialog(null, "숫자만 입력 가능합니다.");
					aField.setText("255");
					// 패널 다시 그리기
					mainPanel.repaint();
					leftPanel.repaint();
					colorPreview.repaint();
					return;
					
				}
				
				// 정수 변환
				int value = Integer.parseInt(text);
				
				// 범위 체크
				if (value < 0 || value > 255) {
					JOptionPane.showMessageDialog(null, "0~255 범위의 값만 입력 가능합니다.");
					aField.setText("255");
					value = 0;
				}
				ma = value;
				// 패널 다시 그리기
				mainPanel.repaint();
				leftPanel.repaint();
				colorPreview.repaint();
				aslider.setValue(ma);
				String hex = String.format("0x%02X%02X%02X", mr, mg, mb);
				rgbHexField.setText(hex);
				long decimal = Long.parseLong(hex.substring(2), 16);
				rgbDecField.setText(Long.toString(decimal));
				hex = String.format("0x%02X%02X%02X%02X",mr,mg,mb,ma);
				rgbaHexField.setText(hex);
				decimal = Long.parseLong(hex.substring(2), 16);
				rgbaDecField.setText(Long.toString(decimal));
				hex = String.format("0x%02X%02X%02X%02X", ma, mr, mg, mb);
				argbHexField.setText(hex);
				decimal = Long.parseLong(hex.substring(2), 16);
				argbDecField.setText(Long.toString(decimal));
			}
		});
		y += 35;
		
		// rgb(16진수)
		JLabel rgbHexLabel = new JLabel("rgb(16진수) : ");
		rgbHexLabel.setBounds(10, y, 100, 25);
		infoPanel.add(rgbHexLabel);
		
		rgbHexField = new JTextField();
		rgbHexField.setBounds(120, y, 100, 25);
		rgbHexField.setEnabled(false);   // ← 비활성화
		infoPanel.add(rgbHexField);
		JButton rgbHexCopy = new JButton("복사");
		rgbHexCopy.setBounds(240, y, 60, 25);
		infoPanel.add(rgbHexCopy);
		y += 35;
		
		// rgb(10진수)
		JLabel rgbDecLabel = new JLabel("rgb(10진수) : ");
		rgbDecLabel.setBounds(10, y, 100, 25);
		infoPanel.add(rgbDecLabel);
		
		rgbDecField = new JTextField();
		rgbDecField.setBounds(120, y, 100, 25);
		rgbDecField.setEnabled(false);   // ← 비활성화
		infoPanel.add(rgbDecField);
		JButton rgbDecCopy = new JButton("복사");
		rgbDecCopy.setBounds(240, y, 60, 25);
		infoPanel.add(rgbDecCopy);
		y += 35;
		
		// rgba(16진수)
		JLabel rgbaHexLabel = new JLabel("rgba(16진수) : ");
		rgbaHexLabel.setBounds(10, y, 110, 25);
		infoPanel.add(rgbaHexLabel);
		
		rgbaHexField = new JTextField();
		rgbaHexField.setBounds(120, y, 100, 25);
		rgbaHexField.setEnabled(false);   // ← 비활성화
		infoPanel.add(rgbaHexField);
		JButton rgbaHexCopy = new JButton("복사");
		rgbaHexCopy.setBounds(240, y, 60, 25);
		infoPanel.add(rgbaHexCopy);
		y += 35;
		
		// rgba(10진수)
		JLabel rgbaDecLabel = new JLabel("rgba(10진수) : ");
		rgbaDecLabel.setBounds(10, y, 110, 25);
		infoPanel.add(rgbaDecLabel);
		
		rgbaDecField = new JTextField();
		rgbaDecField.setBounds(120, y, 100, 25);
		rgbaDecField.setEnabled(false);   // ← 비활성화
		infoPanel.add(rgbaDecField);
		JButton rgbaDecCopy = new JButton("복사");
		rgbaDecCopy.setBounds(240, y, 60, 25);
		infoPanel.add(rgbaDecCopy);
		y += 35;
		
		// argb(16진수)
		JLabel argbHexLabel = new JLabel("argb(16진수) : ");
		argbHexLabel.setBounds(10, y, 110, 25);
		infoPanel.add(argbHexLabel);
		
		argbHexField = new JTextField();
		argbHexField.setBounds(120, y, 100, 25);
		argbHexField.setEnabled(false);   // ← 비활성화
		infoPanel.add(argbHexField);
		JButton argbHexCopy = new JButton("복사");
		argbHexCopy.setBounds(240, y, 60, 25);
		infoPanel.add(argbHexCopy);
		y += 35;
		
		// argb(10진수)
		JLabel argbDecLabel = new JLabel("argb(10진수) : ");
		argbDecLabel.setBounds(10, y, 110, 25);
		infoPanel.add(argbDecLabel);
		
		argbDecField = new JTextField();
		argbDecField.setBounds(120, y, 100, 25);
		argbDecField.setEnabled(false);   // ← 비활성화
		infoPanel.add(argbDecField);
		JButton argbDecCopy = new JButton("복사");
		argbDecCopy.setBounds(240, y, 60, 25);
		infoPanel.add(argbDecCopy);
		y += 50;
		
		// 종료 버튼
		JButton exitButton = new JButton("종료");
		exitButton.setBounds(10, y, 80, 30);
		infoPanel.add(exitButton);
		
		y += 40;
		JLabel nameLabel = new JLabel();
		nameLabel.setBounds(10,y,200,40);
		nameLabel.setText("app_Name : " + Version.app_Name);
		y += 15;
		JLabel versionLabel = new JLabel();
		versionLabel.setBounds(10,y,200,40);
		versionLabel.setText("app_Version : " + Version.app_Version );
		
		y += 15;
		JLabel codeLabel = new JLabel();
		codeLabel.setBounds(10,y,200,40);
		codeLabel.setText("app_Code : " + Version.app_Code);
		infoPanel.add(nameLabel);
		infoPanel.add(versionLabel);
		infoPanel.add(codeLabel);		
		exitButton.addActionListener(e -> System.exit(0));
		String hex = String.format("0x%02X%02X%02X", mr, mg, mb);
		rgbHexField.setText(hex);
		long decimal = Long.parseLong(hex.substring(2), 16);
		rgbDecField.setText(Long.toString(decimal));
		hex = String.format("0x%02X%02X%02X%02X",mr,mg,mb,ma);
		rgbaHexField.setText(hex);
		decimal = Long.parseLong(hex.substring(2), 16);
		rgbaDecField.setText(Long.toString(decimal));
		hex = String.format("0x%02X%02X%02X%02X", ma, mr, mg, mb);
		argbHexField.setText(hex);
		decimal = Long.parseLong(hex.substring(2), 16);
		argbDecField.setText(Long.toString(decimal));
		frame.setVisible(true);
		mr=0;
		mg=0;
		mb=0;
		ma=255;
		rField.setText("0");
		gField.setText("0");
		bField.setText("0");
		aField.setText("255");
		rgbHexCopy.addActionListener(e -> {
			String text = rgbHexField.getText();
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(new StringSelection(text), null);
		});
		rgbDecCopy.addActionListener(e -> {
			String text = rgbDecField.getText();
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(new StringSelection(text), null);
		});
		rgbaHexCopy.addActionListener(e -> {
			String text = rgbaHexField.getText();
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(new StringSelection(text), null);
		});
		rgbaDecCopy.addActionListener(e -> {
			String text = rgbaDecField.getText();
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(new StringSelection(text), null);
		});
		argbHexCopy.addActionListener(e -> {
			String text = argbHexField.getText();
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(new StringSelection(text), null);
		});
		argbDecCopy.addActionListener(e -> {
			String text = argbDecField.getText();
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(new StringSelection(text), null);
		});
		
	}
}
