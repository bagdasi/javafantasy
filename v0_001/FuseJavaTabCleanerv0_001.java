import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
public class FuseJavaTabCleanerv0_001 extends JFrame {

    private JTextArea textArea;
    private JSpinner tabSpinner;
	private int brace=0,paren=0,bracket=0,tab = 0;
	private boolean ao=false,bo=false,co = false,eo = false,fo = false,go = false;
    public FuseJavaTabCleanerv0_001() {
        setTitle("퓨즈자바탭정리기v0.001");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
	
        //
        // CENTER PANEL
        //
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        JLabel codeLabel = new JLabel("Code :");
        textArea = new JTextArea();

        centerPanel.add(codeLabel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        //
        // EAST PANEL
        //
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new GridLayout(6, 1, 5, 5));

        JLabel tabLabel = new JLabel("tab개수:");
        tabSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));

        JButton infoButton = new JButton("정보");
        JButton cleanButton = new JButton("정리하기");
        JButton delButton = new JButton("지우기");
        JButton copyButton = new JButton("복사");
        JButton pasteButton = new JButton("붙여놓기");
        JButton exitButton = new JButton("종료");

        eastPanel.add(tabLabel);
        eastPanel.add(tabSpinner);
        eastPanel.add(cleanButton);
        eastPanel.add(delButton);
        eastPanel.add(copyButton);
        eastPanel.add(pasteButton);
        eastPanel.add(infoButton);
        
        eastPanel.add(exitButton);

        add(eastPanel, BorderLayout.EAST);

        // 
        // BUTTON EVENTS
        //

        // 정보 버튼
        infoButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {

        JFrame infoFrame = new JFrame("정보");
        infoFrame.setSize(400, 300);
        infoFrame.setLayout(new BorderLayout());
        infoFrame.setLocationRelativeTo(FuseJavaTabCleanerv0_001.this); // 화면 중앙

        // 상단 라벨
        JLabel label = new JLabel("정보:");
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoFrame.add(label, BorderLayout.NORTH);

        // 가운데 텍스트 영역
        JTextArea infoArea = new JTextArea();
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(infoArea);
        infoFrame.add(scroll, BorderLayout.CENTER);
	infoArea.setEditable(false);
	infoArea.append("만든이 : 컴퓨터판타지연구소\n");
	infoArea.append("앱 이름 : 퓨즈자바탭정리기v0.001\n");
	infoArea.append("다운로드 : http://javafantasy.kr/\n");
	infoArea.append("자바탭정리하는 프로그램입니다.\n");
	infoArea.append("아직 수정할것이 많이 남아있습니다.\n");
	
        // 하단 확인 버튼
        JButton okButton = new JButton("확인");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoFrame.dispose(); // 창 닫기
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(okButton);
        infoFrame.add(bottomPanel, BorderLayout.SOUTH);

        infoFrame.setVisible(true);
    }
});

        // 정리하기 버튼
        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "버튼을 눌렀습니다.");
                ao=false;
				            	bo=false;
				            	tab = 0;
				            	brace =0;
				            	 inString = false;   // "
    inChar = false;     // '
     inLineComment = false; // //
    inBlockComment = false; // /* */
    escape = false;     // \
                tab = (int)tabSpinner.getValue();
                StringBuilder sb = new StringBuilder();
                String thiscode = textArea.getText();
                String[] setcode = thiscode.split("\n");
		for(String str : setcode)
		{
			
    		String trimmed = str.trim();
    
   
			
			
			brace += countBrace(str);
			int total = tab+brace;
			if(total<0) { total = 0;}
			String tmp ="";
			if (trimmed.endsWith("{")) {
        			total--;
    			}
    			if (trimmed.startsWith("}")) {
        			//total--;
    			}
			for(int i =0; i<total; i++)
			{
				tmp += "\t";
					}
					
					tmp += str.trim();
					tmp+="\n";
					sb.append(tmp);
					//countStr(str);
					
			//paren += countParen(str);
			//bracket += countBracket(str);
		}

                	textArea.setText(sb.toString());
            }
        });
	delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "버튼을 눌렀습니다.");
                textArea.setText("");
            }
        });
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "버튼을 눌렀습니다.");
                String all = textArea.getText();
StringSelection ss = new StringSelection(all);
Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);


            }
        });
        pasteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "버튼을 눌렀습니다.");
                
                textArea.setText("");
                textArea.paste();

            }
        });
        // 종료 버튼
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // JOptionPane.showMessageDialog(null, "버튼을 눌렀습니다.");
                System.exit(0);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new FuseJavaTabCleanerv0_001();
    }
    
    private void countStr(String code)
    {
    	int count = 0;
    		for(int i = 0;i<code.length();i++)
    		{
    			char c = code.charAt(i);
    			
    			
			
		}
	}//ghgfhgf
	boolean inString = false;   // "
    boolean inChar = false;     // '
    boolean inLineComment = false; // //
    boolean inBlockComment = false; // /* */
    boolean escape = false;     // \
   private int countBrace(String code) {

    

    int count = 0;

    for (int i = 0; i < code.length(); i++) {
        char c = code.charAt(i);

        // -------------------------
        // 1) 한 줄 주석 처리
        // -------------------------
       

        // -------------------------
        // 2) 블록 주석 처리
        // -------------------------
        if (inBlockComment) {
            if (c == '*' && i + 1 < code.length() && code.charAt(i + 1) == '/') {
                inBlockComment = false;
                i++;
            }
            continue;
        }

        // -------------------------
        // 3) escape 처리
        // -------------------------
        if (escape) {
            escape = false;
            continue;
        }
        if (c == '\\') {
            escape = true;
            continue;
        }

        // -------------------------
        // 4) 문자열 처리
        // -------------------------
        if (!inChar && c == '"') {
            inString = !inString;
            continue;
        }

        // -------------------------
        // 5) 문자 처리
        // -------------------------
        if (!inString && c == '\'') {
            inChar = !inChar;
            continue;
        }

        // 문자열/문자 안이면 brace 무시
        if (inString || inChar) {
            continue;
        }

        // -------------------------
        // 6) 주석 시작 처리
        // -------------------------
        if (c == '/' && i + 1 < code.length()) {
            char next = code.charAt(i + 1);

            if (next == '/') {
                inLineComment = true;
                i++;
                break;
            }
            if (next == '*') {
                inBlockComment = true;
                i++;
                continue;
            }
        }

        // -------------------------
        // 7) brace 카운트
        // -------------------------
        if (c == '{') count++;
        if (c == '}') count--;
    }

    return count;
}
	
    
}
