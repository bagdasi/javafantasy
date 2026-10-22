import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.Priority;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import org.fxmisc.flowless.VirtualizedScrollPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.concurrent.Task;
import javafx.collections.ListChangeListener;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Animation;

import java.io.File;
import java.util.Optional;
import javafx.geometry.Bounds;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.lang.reflect.Method;
import java.lang.reflect.*;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashSet;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;
import java.util.Comparator;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.net.URL;
import java.net.MalformedURLException;

import java.io.FileReader;



import java.io.OutputStream;
import java.net.Socket;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.util.Set;
import java.util.HashSet;
import javafx.application.Platform;
import java.util.concurrent.*;



import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.TwoDimensional.Bias;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.FieldVisitor;

//import org.reflections.Reflections;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import io.github.classgraph.ClassInfo;


import AJEStruct.*;
import Version.Version;
//범위접기는 절대 만들지 말것..... 진짜 만들지말것..... 이것만은 꼭 지킬것.... 진짜 안만들어진다... 동시실행이 잘 안되서 안만들어진다...
//이거 만든다고 엿같다...와...계속 만들었는데... 이제 좀 낫다....
//일단 0.0045버전에서 닫기버튼 누르면 저장하겠습니까?질문하고 닫는 기능 만들었는데... 오류로 인해서 사용중단했습니다.
public class AntJavaEditer extends Application {
	
	Version ver = new Version();
	CodeArea codeArea2;
	VirtualizedScrollPane<CodeArea> codeScrollPane;
	//FoldingArea codeArea2;
	Popup popup;
	ListView<String> listView;
	Label pathLabel;
	String fileDir = ".";
	String fileName = "";
	List<String> importlist = new ArrayList<>();//import가 저장되어있는 리스트....
	List<String> keylist = new ArrayList<>();
	List<String> classlist = new ArrayList<>();
	List<String> aclasslist = new ArrayList<>();
	List<String> jarlist = new ArrayList<>();
	List<String> jarClassList = new ArrayList<>();
	String typeTempStr="";
	List<varSave> varlist = new ArrayList<>();
	String statusNow = "";
	int typepos=0;
	String[] optionStr = new String[7];
	boolean optionUseb = false;
	String useDir = "";
	List<String> jarStyleClass = new ArrayList<>();
	List<String> fileStyleClass = new ArrayList<>();
	List<String> exlsClassList = new ArrayList<>();
	List<String> useStyleClass = new ArrayList<>();
	List<String> tempImportList = new ArrayList<>();
	List<String> useClassList = new ArrayList<>();
	List<String> fileClassList = new ArrayList<>();
	boolean useStyle = true, usePopup = true, useConsoleV = true;
	boolean listViewb = false;
	StringBuilder newsb = new StringBuilder();
	String console_input="", console_line="";
	boolean console_enter = false;
	BufferedWriter runWriter;
	// 전역 변수 선언
	private boolean fromKeyboard = false,init_back = false;
	private int numberKeyboard = 0;
	private int backup_number = 0,back_num = 0;
		private static final Map<Character, Character> BRACKET_MAP = Map.of('{', '}','(', ')','[', ']','<','>', '}' , '{', ')', '(' , ']' , '[','>', '<');
	private int[] bracket_pos = new int[2];
	Process runProcess;
	
	//0.0032추가
	List<String> agoFileList = new ArrayList<>();
	//0.0035A추가
	private String tmpSearch = "";
	private boolean tmpSearchUp = false,tmpSearchLower = false;
	//0.0036추가
	private boolean isPaste = false;
	private boolean isRedo = false;
	private double getScrollX = 0;
	private double getScrollY = 0;
	//0.0036B추가
	private Queue<Process> runProcessList = new ArrayDeque<>();
	Scene mainscene;
	//0.0042추가 탭클리너 추가....
	private int brace=0,paren=0,bracket=0,tab = 0,total =0;
	private boolean ao=false,bo=false,co = false,eo = false,fo = false,go = false;
	private String tabtmp = "";
	//0.0044추가
	private boolean modified = false;//수정....
	private String file_Encoding = "";
	private TabInfo tabinfo;
	private TabPane tabPane = new TabPane();
	//private JFXTabPane tabPane = new JFXTabPane();
	private Tab antTab = new Tab("");//이건 안씀...ㅋㅋㅋ
	Button tab_ClosedButton = new Button("x");//이것도 안씀...ㅋㅋㅋ
	TextArea consoleArea;
	private boolean isSwitchingTab = false;
	//0.0045추가
	private Button jarButton = new Button("Jar");
	private String[] jarTmpStr = new String[10];
	private List<String> jarTmpList = new ArrayList<>();
	private boolean isSaving = false;
	String compareTmpStr = "";
	//0.0046변경
	JARCreater jarCreater = new JARCreater("JAR생성기");
	//0.0047추가
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	String ipString = "";
	NetworkString networkStr = new NetworkString();
	String rtmpstate,stmpstate;
	@Override
	public void start(Stage primaryStage) {
		bracket_pos[0] = 0;
		bracket_pos[1] = 0;
		keyListInit();
		classListInit();
		aclassListInit();
		useImportClassInit();
		BorderPane mainroot = new BorderPane();
		mainscene = new Scene(mainroot, 800, 600);
		codeArea2 = new CodeArea();
		//codeArea2 = new FoldingArea();
		popup = new Popup();
		listView = new ListView<>();
		// 경로 라벨
		pathLabel = new Label("경로 : ");
		
		codeArea2.setParagraphGraphicFactory(LineNumberFactory.get(codeArea2));
		codeArea2.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");
		codeArea2.setMaxWidth(Double.MAX_VALUE);
		codeArea2.setMaxHeight(Double.MAX_VALUE);
		try{
			List<String> lines = Files.readAllLines(Paths.get("src/main/resources/StyleOption.soption"), StandardCharsets.UTF_8);
			String fontstr="",fontsizestr="";
			for(String line : lines)
			{
				String[] parts = line.split("=");
				if (parts.length == 2) {
					String key = parts[0].trim();
					String value = parts[1].trim();
					switch (key) {
						case "Console":
						if(value.equalsIgnoreCase("yes"))
						{
							useConsoleV = true;
						}else if(value.equalsIgnoreCase("no"))
						{
							useConsoleV = false;
						}else {
							useConsoleV = true;
						}
						
						break;
						case "Style":
						if(value.equalsIgnoreCase("yes"))
						{
							useStyle = true;
						}else if(value.equalsIgnoreCase("no"))
						{
							useStyle = false;
						}else {
							useStyle = true;
						}
						
						break;
						case "Popup":
						if(value.equalsIgnoreCase("yes"))
						{
							usePopup = true;
						}else if(value.equalsIgnoreCase("no"))
						{
							usePopup = false;
						}else {
							usePopup = true;
						}
						break;
						case "Font":
						fontstr = value;
						break;
						case "FontSize":
						fontsizestr = value;
						break;
					}
				}
				
			}
			String tempstr = "-fx-font-family: \'"+fontstr+ "\'; -fx-font-size: " + fontsizestr + "px;";
			codeArea2.setStyle(tempstr);
			//elxs파일 불러오기
			exlsListAddAll();
		}catch(IOException mainioe)
		{
			
		}
		
		
		//// 스크롤 가능한 컨테이너로 감싸기
		codeScrollPane = new VirtualizedScrollPane<>(codeArea2);
		//VirtualizedScrollPane<FoldingArea> codeScrollPane = new VirtualizedScrollPane<>(codeArea2);
		HBox.setHgrow(codeScrollPane, Priority.ALWAYS);
		
		
		// 실시간 문법 강조
		codeArea2.textProperty().addListener((obs, oldText, newText) -> {
			if (isSwitchingTab) return; // 🔥 탭 전환 중이면 무시
			
			
			//여기까지 0.0044버전
			newsb = new StringBuilder(newText);
			// 기존 스타일시트 제거
			mainscene.getStylesheets().clear();
			// 다시 추가 (갱신된 style.css 반영)
			
			mainscene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // 스타일시트 연결 (선택)
			codeArea2.getStylesheets().clear();
			codeArea2.getStylesheets().add("bgstyle.css");
			
			// CSS 강제 적용
			mainscene.getRoot().applyCss();
			
			int caretPos = codeArea2.getCaretPosition();
			String typedChar = "";
			if (newText.length() > oldText.length() && caretPos > 0) {
				typedChar = newText.substring(caretPos - 1, caretPos);
				if (typedChar.matches("[A-Za-z0-9_]")) {
					
					
					typeTempStr += typedChar;
					typepos++;
				}
				
				
				//System.out.println("typeTempStr : " + typeTempStr + "  typepos : " + typepos);
			}
			
			cleanProcess();
			onTextChanged(newText);
			
			
			
			
		});
		codeArea2.plainTextChanges().subscribe(change -> {
			String inserted = change.getInserted();
			if (inserted.equals("}")) {
				
			}
			
		});
		
		codeArea2.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER && event.isShiftDown()) {
				
				event.consume(); // 기본 Enter 동작 막고 싶으면 사용
				int currentLine = codeArea2.getCurrentParagraph();
				int lineEnd = codeArea2.getParagraphLength(currentLine);
				codeArea2.moveTo(currentLine, lineEnd);
				
				
			}
			if (event.getCode() == KeyCode.ENTER) {
				event.consume(); // 기본 ENTER 동작 막기
				int caretPosition = codeArea2.getCaretPosition();
				int currentParagraph = codeArea2.getCurrentParagraph();
				String previousLine = currentParagraph > 0 ? codeArea2.getParagraph(currentParagraph - 1).getText() : "";
				String baseindent = previousLine.replaceAll("^([\\t ]*).*", "$1");
				String indent = previousLine.trim().endsWith("{") ? baseindent + "\t" : baseindent;
				codeArea2.insertText(caretPosition, indent);
				
				
				Platform.runLater(() -> 	{
					
					
				});
				typeTempStr = "";
				typepos = 0;
				statusNow = "code";
			}
			
			
			
			if (event.isControlDown() && event.getCode() == KeyCode.V) {
				isPaste = true;   // 붙여넣기 플래그 ON
				//getScrollX = codeArea2.estimatedScrollXProperty().getValue();
				//getScrollY = codeArea2.estimatedScrollYProperty().getValue();
			}
			if (event.isControlDown() && event.getCode() == KeyCode.Y) {
				isRedo = true;   // 리두 플래그 ON
				//getScrollX = codeArea2.estimatedScrollXProperty().getValue();
				//getScrollY = codeArea2.estimatedScrollYProperty().getValue();
			}
			if(event.getCode() == KeyCode.CONTROL){
				typeTempStr = "";
				typepos = 0;
				if(popup.isShowing())
				{
					popup.hide();
				}
			}
			if (event.getCode() == KeyCode.SPACE) {
				
				
				statusNow = "code";
				
				
				
				
				typeTempStr = "";
				typepos = 0;
				
				
			}
			if (event.getCode() == KeyCode.TAB) {
				// 여기에 Tab 눌렀을 때 실행할 코드 작성
				typeTempStr = "";
				typepos = 0;
				statusNow = "code";
				
			}
			if (event.getCode() == KeyCode.BACK_SPACE) {
				// 여기에 Backspace 눌렀을 때 실행할 코드 작성
				/*if (!typeTempStr.isEmpty()) {
				typeTempStr = typeTempStr.substring(0, typeTempStr.length() - 1);
				typepos=0;
				typeTempStr = "";
				statusNow = "code";
				}*/
				if (!typeTempStr.isEmpty() && typepos > 0 && !popup.isShowing()) {
					typeTempStr = typeTempStr.substring(0, typeTempStr.length() - 1);
					typepos--;
				}
				
			}
			if (event.getCode() == KeyCode.DELETE) {
				// 여기에 Delete 눌렀을 때 실행할 코드 작성
				typepos=0;
				
			}
			if (event.getCode() == KeyCode.PAGE_UP) {
				// 여기에 PageUp 눌렀을 때 실행할 코드 작성
				typeTempStr = "";
				typepos = 0;
				statusNow = "code";
				
			}
			if (event.getCode() == KeyCode.PAGE_DOWN) {
				// 여기에 PageDown 눌렀을 때 실행할 코드 작성
				typeTempStr = "";
				typepos = 0;
				statusNow = "code";
				
			}
			if (event.getCode() == KeyCode.PERIOD) {
				// 여기에 . 키 눌렀을 때 실행할 코드 작성
				typeTempStr = "";
				typepos = 0;
				statusNow = "code";
				
			}
			if (event.getCode() == KeyCode.COMMA) {
				// 여기에 쉼표(,) 키 눌렀을 때 실행할 코드 작성
				typeTempStr = "";
				typepos = 0;
				statusNow = "code";
			}
			if (event.getCode() == KeyCode.SEMICOLON) {
				// 여기에 쉼표(,) 키 눌렀을 때 실행할 코드 작성
				typeTempStr = "";
				typepos = 0;
				statusNow = "code";
			}
			if (event.getCode() == KeyCode.UP) {
				typeTempStr = "";
				typepos = 0;
				statusNow = "code";
			} else if (event.getCode() == KeyCode.DOWN) {
				typeTempStr = "";
				typepos = 0;
				statusNow = "code";
			} else if (event.getCode() == KeyCode.LEFT) {
				typeTempStr = "";
				typepos = 0;
				statusNow = "code";
			} else if (event.getCode() == KeyCode.RIGHT) {
				typeTempStr = "";
				typepos = 0;
				statusNow = "code";
			}
			if (event.getCode() == KeyCode.ESCAPE) {
				// 여기에 esc 키 눌렀을 때 실행할 코드 작성
				
				typeTempStr = "";
				typepos = 0;
				statusNow = "code";
				
				
			}
			/*if ("+".equals(event.getText()) || "-".equals(event.getText()) || "*".equals(event.getText()) || "/".equals(event.getText()) || "%".equals(event.getText())
			|| "(".equals(event.getText()) || ")".equals(event.getText()) || "[".equals(event.getText()) || "]".equals(event.getText()) || "{".equals(event.getText()) || "}".equals(event.getText()) )
			{
			
			typeTempStr = "";
			typepos = 0;
			statusNow = "code";
			}*///이거 하면 안됨...쉬프트 - 눌러서 _가 눌러서 _이게 적용이 됨.....
			
			
			if((event.getText().length() == 1 && Character.isLetter(event.getText().charAt(0))))
			{
				//statusNow = "code";
			}
			int pos = codeArea2.getCaretPosition();
			if(pos != bracket_pos[0] && bracket_pos[0] != 0 )
			{
				int tmp1 = bracket_pos[0];
				int tmp2 = bracket_pos[1];
				bracket_pos[0] = 0;
				bracket_pos[1] = 0;
				//codeArea2.setStyle(tmp1, tmp1 + 1, Collections.singleton("normal"));
				//codeArea2.setStyle(tmp2, tmp2 + 1, Collections.singleton("normal"));
				codeArea2.setStyleClass(tmp1, tmp1 + 1, "normala");
				codeArea2.setStyleClass(tmp2, tmp2 + 1, "normala");
				
			}
			int endpos = -1;
			if (pos > 0 && pos < codeArea2.getLength()) {
				char ch = codeArea2.getText().charAt(pos);
				Character pair = BRACKET_MAP.get(ch);
				String text = codeArea2.getText();
				if (pair != null) {
					if(ch == '{')
					{
						int count = 0;
						
						for(int i = pos ; i <text.length();i++)
						{
							
							char braket = text.charAt(i);
							if(braket == '{')
							{
								count ++;
							}else if(braket == '}')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
							
							
						}
						//endpos = findMatchingBrace(text, pos);
						if(endpos != -1)
						{
							
						}
						
					}else if(ch == '}')
					{
						int count = 0;
						
						for(int i = pos ; i >=0;i--)
						{
							
							char braket = text.charAt(i);
							if(braket == '}')
							{
								count ++;
							}else if(braket == '{')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						//endpos = findMatchingBrace(text, pos);
						if(endpos != -1)
						{
							
						}
						
					}else if(ch == '(')
					{
						int count = 0;
						for(int i = pos ; i <text.length();i++)
						{
							
							char braket = text.charAt(i);
							if(braket == '(')
							{
								count ++;
							}else if(braket == ')')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						if(endpos != -1)
						{
							
						}
						
					}else if(ch == ')')
					{
						
						int count = 0;
						for(int i = pos ; i >=0;i--)
						{
							
							char braket = text.charAt(i);
							if(braket == ')')
							{
								count ++;
							}else if(braket == '(')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						if(endpos != -1)
						{
							
						}
					}else if(ch == '[')
					{
						int count = 0;
						for(int i = pos ; i <text.length();i++)
						{
							
							char braket = text.charAt(i);
							if(braket == '[')
							{
								count ++;
							}else if(braket == ']')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						if(endpos != -1)
						{
							
						}
					}else if(ch == ']')
					{
						int count = 0;
						for(int i = pos ; i >=0;i--)
						{
							
							char braket = text.charAt(i);
							if(braket == ']')
							{
								count ++;
							}else if(braket == '[')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						if(endpos != -1)
						{
							
						}
					}else if(ch == '<')
					{
						int count = 0;
						for(int i = pos ; i <text.length();i++)
						{
							
							char braket = text.charAt(i);
							if(braket == '<')
							{
								count ++;
							}else if(braket == '>')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						if(endpos != -1)
						{
							
						}
					}else if(ch == '>')
					{
						int count = 0;
						for(int i = pos ; i >=0;i--)
						{
							
							char braket = text.charAt(i);
							if(braket == '>')
							{
								count ++;
							}else if(braket == '<')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						if(endpos != -1)
						{
							
						}
					}
				}
				
				
				
			}
			if(endpos != -1)
			{
				bracket_pos[0] = pos;
				bracket_pos[1] = endpos;
				//codeArea2.setStyle(pos, pos + 1, Collections.singleton("highlight-char"));
				//codeArea2.setStyle(endpos, endpos + 1, Collections.singleton("highlight-char"));
				codeArea2.setStyleClass(pos, pos + 1, "highlight-char");
				codeArea2.setStyleClass(endpos, endpos + 1, "highlight-char");
				
			}
			
			
		});
		
		codeArea2.addEventHandler(KeyEvent.KEY_TYPED, event -> {
			String character = event.getCharacter();
			
			if (character.equals("}")) {
				//System.out.println("닫는 중괄호 입력됨!");
				int caretPosition = codeArea2.getCaretPosition();
				
				// 커서 이전까지의 텍스트만 가져오기
				String beforeCursorText = codeArea2.getText(0, caretPosition);
				
				// 줄 단위로 분리
				String[] lines = beforeCursorText.split("\n");
				String currentLine = lines.length > 0 ? lines[lines.length - 1] : "";
				boolean onlyTabs = currentLine.replace("}", "").replaceAll("[\\t ]", "").isEmpty();
				
				// 블록 깊이 계산
				// 여기서 BracesParser 사용
				int depth = BracesParser.countDepth(beforeCursorText);
				
				// 현재 }는 닫히는 중괄호이므로 depth를 하나 줄여서 들여쓰기 계산
				int indentLevel = Math.max(0, depth);
				
				// 탭/스페이스로 들여쓰기 문자열 생성 (여기서는 탭 사용)
				StringBuilder indentBuilder = new StringBuilder();
				for (int i = 0; i < indentLevel; i++) {
					indentBuilder.append("\t");
				}
				String finalIndent = indentBuilder.toString();
				
				if (onlyTabs) {
					Platform.runLater(() -> {
						// 현재 줄의 시작 위치 계산
						int lineStart = caretPosition - currentLine.length();
						
						// 현재 줄 삭제
						codeArea2.replaceText(lineStart, caretPosition, "");
						
						// 들여쓰기 맞춰서 } 삽입
						codeArea2.insertText(lineStart, finalIndent + "}");
					});
					event.consume();
				}
			}
			
			if ("%".equals(character) || "{".equals(character) || "}".equals(character) || "[".equals(character) || "]".equals(character) || "+".equals(character) || "-".equals(character) || "*".equals(character) || "/".equals(character) || "(".equals(character) || ")".equals(character) ) {
				statusNow = "code";
				typeTempStr = "";
				typepos = 0;
				event.consume(); // 이미 처리했으니 더 이상 전달하지 않음(선택)
			}
			if (".".equals(character) || ",".equals(character) || ";".equals(character) || "=".equals(character) || "<".equals(character) || ">".equals(character))
			{
				statusNow = "code";
				typeTempStr = "";
				typepos = 0;
				event.consume(); // 이미 처리했으니 더 이상 전달하지 않음(선택)
			}
			
			//이거 범위표시하면 오류 뜸...하면 안됨...
			int pos = codeArea2.getCaretPosition();
			/*if(pos != bracket_pos[0] && bracket_pos[0] != 0 )
			{
			int tmp1 = bracket_pos[0];
			int tmp2 = bracket_pos[1];
			bracket_pos[0] = 0;
			bracket_pos[1] = 0;
			//codeArea2.setStyle(tmp1, tmp1 + 1, Collections.singleton("normal"));
			//codeArea2.setStyle(tmp2, tmp2 + 1, Collections.singleton("normal"));
			codeArea2.setStyleClass(tmp1, tmp1 + 1, "normal");
			codeArea2.setStyleClass(tmp2, tmp2 + 1, "normal");
			
			}*/
			int endpos = -1;
			if (pos > 0 && pos < codeArea2.getLength()) {
				char ch = codeArea2.getText().charAt(pos);
				Character pair = BRACKET_MAP.get(ch);
				String text = codeArea2.getText();
				if (pair != null) {
					if(ch == '{')
					{
						int count = 0;
						for(int i = pos ; i <text.length();i++)
						{
							
							char braket = text.charAt(i);
							if(braket == '{')
							{
								count ++;
							}else if(braket == '}')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						if(endpos != -1)
						{
							
						}
						
					}else if(ch == '}')
					{
						int count = 0;
						for(int i = pos ; i >=0;i--)
						{
							
							char braket = text.charAt(i);
							if(braket == '}')
							{
								count ++;
							}else if(braket == '{')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						if(endpos != -1)
						{
							
						}
						
					}else if(ch == '(')
					{
						int count = 0;
						for(int i = pos ; i <text.length();i++)
						{
							
							char braket = text.charAt(i);
							if(braket == '(')
							{
								count ++;
							}else if(braket == ')')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						if(endpos != -1)
						{
							
						}
						
					}else if(ch == ')')
					{
						
						int count = 0;
						for(int i = pos ; i >=0;i--)
						{
							
							char braket = text.charAt(i);
							if(braket == ')')
							{
								count ++;
							}else if(braket == '(')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						if(endpos != -1)
						{
							
						}
					}else if(ch == '[')
					{
						int count = 0;
						for(int i = pos ; i <text.length();i++)
						{
							
							char braket = text.charAt(i);
							if(braket == '[')
							{
								count ++;
							}else if(braket == ']')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						if(endpos != -1)
						{
							
						}
					}else if(ch == ']')
					{
						int count = 0;
						for(int i = pos ; i >=0;i--)
						{
							
							char braket = text.charAt(i);
							if(braket == ']')
							{
								count ++;
							}else if(braket == '[')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						if(endpos != -1)
						{
							
						}
					}else if(ch == '<')
					{
						int count = 0;
						for(int i = pos ; i <text.length();i++)
						{
							
							char braket = text.charAt(i);
							if(braket == '<')
							{
								count ++;
							}else if(braket == '>')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						if(endpos != -1)
						{
							
						}
					}else if(ch == '>')
					{
						int count = 0;
						for(int i = pos ; i >=0;i--)
						{
							
							char braket = text.charAt(i);
							if(braket == '>')
							{
								count ++;
							}else if(braket == '<')
							{
								count --;
							}
							
							if(count == 0)
							{
								endpos = i;
								break;
							}
							
						}
						if(endpos != -1)
						{
							
						}
					}
				}
				
				
				
				
			}
			if(endpos != -1)
			{
				bracket_pos[0] = pos;
				bracket_pos[1] = endpos;
				//codeArea2.setStyle(pos, pos + 1, Collections.singleton("highlight-char"));
				//codeArea2.setStyle(endpos, endpos + 1, Collections.singleton("highlight-char"));
				codeArea2.setStyleClass(pos, pos + 1, "normala");
				codeArea2.setStyleClass(endpos, endpos + 1, "normala");
				
			}
			
			if(!isSaving)
			{
				Tab selected = tabPane.getSelectionModel().getSelectedItem();
				if (selected != null) {
					TabInfo info = (TabInfo) selected.getUserData();
					info.modified = true;   // 변경됨 표시
				}
			}
			
			
		});
		codeArea2.addEventFilter(ScrollEvent.SCROLL, event -> {
			typeTempStr = "";
			typepos = 0;
			if(popup.isShowing())
			{
				popup.hide();
			}
		});
		codeArea2.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) {
				typeTempStr = "";
				typepos = 0;
				//statusNow = "code";
				popup.hide();
				
				//{중괄호표시.....
				int pos = codeArea2.getCaretPosition();
				if(pos != bracket_pos[0] && bracket_pos[0] != 0)
				{
					int tmp1 = bracket_pos[0];
					int tmp2 = bracket_pos[1];
					bracket_pos[0] = 0;
					bracket_pos[1] = 0;
					//codeArea2.setStyle(tmp1, tmp1 + 1, Collections.singleton("normal"));
					//codeArea2.setStyle(tmp2, tmp2 + 1, Collections.singleton("normal"));
					codeArea2.setStyleClass(tmp1, tmp1 + 1, "normala");
					codeArea2.setStyleClass(tmp2, tmp2 + 1, "normala");
				}
				int endpos = -1;
				if (pos > 0 && pos < codeArea2.getLength()) {
					char ch = codeArea2.getText().charAt(pos);
					Character pair = BRACKET_MAP.get(ch);
					String text = codeArea2.getText();
					if (pair != null) {
						if(ch == '{')
						{
							int count = 0;
							for(int i = pos ; i <text.length();i++)
							{
								
								char braket = text.charAt(i);
								if(braket == '{')
								{
									count ++;
								}else if(braket == '}')
								{
									count --;
								}
								
								if(count == 0)
								{
									endpos = i;
									break;
								}
								
							}
							if(endpos != -1)
							{
								
							}
							
						}else if(ch == '}')
						{
							int count = 0;
							for(int i = pos ; i >=0;i--)
							{
								
								char braket = text.charAt(i);
								if(braket == '}')
								{
									count ++;
								}else if(braket == '{')
								{
									count --;
								}
								
								if(count == 0)
								{
									endpos = i;
									break;
								}
								
							}
							if(endpos != -1)
							{
								
							}
							
						}else if(ch == '(')
						{
							int count = 0;
							for(int i = pos ; i <text.length();i++)
							{
								
								char braket = text.charAt(i);
								if(braket == '(')
								{
									count ++;
								}else if(braket == ')')
								{
									count --;
								}
								
								if(count == 0)
								{
									endpos = i;
									break;
								}
								
							}
							if(endpos != -1)
							{
								
							}
							
						}else if(ch == ')')
						{
							
							int count = 0;
							for(int i = pos ; i >=0;i--)
							{
								
								char braket = text.charAt(i);
								if(braket == ')')
								{
									count ++;
								}else if(braket == '(')
								{
									count --;
								}
								
								if(count == 0)
								{
									endpos = i;
									break;
								}
								
							}
							if(endpos != -1)
							{
								
							}
						}else if(ch == '[')
						{
							int count = 0;
							for(int i = pos ; i <text.length();i++)
							{
								
								char braket = text.charAt(i);
								if(braket == '[')
								{
									count ++;
								}else if(braket == ']')
								{
									count --;
								}
								
								if(count == 0)
								{
									endpos = i;
									break;
								}
								
							}
							if(endpos != -1)
							{
								
							}
						}else if(ch == ']')
						{
							int count = 0;
							for(int i = pos ; i >=0;i--)
							{
								
								char braket = text.charAt(i);
								if(braket == ']')
								{
									count ++;
								}else if(braket == '[')
								{
									count --;
								}
								
								if(count == 0)
								{
									endpos = i;
									break;
								}
								
							}
							if(endpos != -1)
							{
								
							}
						}else if(ch == '<')
						{
							int count = 0;
							for(int i = pos ; i <text.length();i++)
							{
								
								char braket = text.charAt(i);
								if(braket == '<')
								{
									count ++;
								}else if(braket == '>')
								{
									count --;
								}
								
								if(count == 0)
								{
									endpos = i;
									break;
								}
								
							}
							if(endpos != -1)
							{
								
							}
						}else if(ch == '>')
						{
							int count = 0;
							for(int i = pos ; i >=0;i--)
							{
								
								char braket = text.charAt(i);
								if(braket == '>')
								{
									count ++;
								}else if(braket == '<')
								{
									count --;
								}
								
								if(count == 0)
								{
									endpos = i;
									break;
								}
								
							}
							if(endpos != -1)
							{
								
							}
						}
					}
					
					
					
				}
				if(endpos != -1)
				{
					bracket_pos[0] = pos;
					bracket_pos[1] = endpos;
					//codeArea2.setStyle(pos, pos + 1, Collections.singleton("highlight-char"));
					//codeArea2.setStyle(endpos, endpos + 1, Collections.singleton("highlight-char"));
					codeArea2.setStyleClass(pos, pos + 1, "highlight-char");
					codeArea2.setStyleClass(endpos, endpos + 1, "highlight-char");
					
				}
				
			}
		});
		
		//0.0032추가
		ContextMenu contextMenu = new ContextMenu();
		
		// 전체 선택
		MenuItem selectAll = new MenuItem("전체 선택(a)");
		selectAll.setOnAction(e -> codeArea2.selectAll());
		
		// 실행취소
		MenuItem undo = new MenuItem("실행취소(z)");
		undo.setOnAction(e -> codeArea2.undo());
		
		// 다시실행
		MenuItem redo = new MenuItem("다시실행(y)");
		redo.setOnAction(e -> {
			
			isRedo = true;
			//getScrollX = codeArea2.estimatedScrollXProperty().getValue();
			//getScrollY = codeArea2.estimatedScrollYProperty().getValue();
			codeArea2.redo();
		});
		
		// 잘라내기
		MenuItem cut = new MenuItem("잘라내기(x)");
		cut.setOnAction(e -> codeArea2.cut());
		
		// 복사
		MenuItem copy = new MenuItem("복사(c)");
		copy.setOnAction(e -> {
			codeArea2.copy();
		});
		
		// 붙여넣기
		MenuItem paste = new MenuItem("붙여넣기(v)");
		paste.setOnAction(e -> {
			
			isPaste = true;
			//getScrollX = codeArea2.estimatedScrollXProperty().getValue();
			//getScrollY = codeArea2.estimatedScrollYProperty().getValue();
			codeArea2.paste();
			// paste()가 끝난 뒤 caret을 붙여넣기 끝으로 이동
			Platform.runLater(() -> {
				int pos = codeArea2.getCaretPosition();
				codeArea2.moveTo(pos);
			});
		});
		
		
		
		// 메뉴 구성
		contextMenu.getItems().addAll(selectAll, undo,redo, cut, copy, paste );
		
		// CodeArea에 메뉴 적용
		codeArea2.setContextMenu(contextMenu);
		
		
		listView.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.LEFT){
				/*popup.hide();
				typeTempStr = "";
				typepos = 0;*/
				listView.getSelectionModel().selectPrevious();
				
			}
			if(event.getCode() == KeyCode.RIGHT){
				/*popup.hide();
				typeTempStr = "";
				typepos = 0;*/
				listView.getSelectionModel().selectNext();
				
			}
			if (event.getCode() == KeyCode.ESCAPE) {
				popup.hide();
				typeTempStr = "";
				typepos = 0;
			}
			if (event.getCode() == KeyCode.ENTER) {
				insertSelectedItem();
				//typeTempStr = "";
				//typepos = 0;
			}
			if (event.getCode() == KeyCode.SPACE) {
				popup.hide();
				typeTempStr = "";
				typepos = 0;
			}
			if (event.getCode() == KeyCode.COMMA) {
				// 여기에 쉼표(,) 키 눌렀을 때 실행할 코드 작성
				popup.hide();
				typeTempStr = "";
				typepos = 0;
			}
			if (event.getCode() == KeyCode.SEMICOLON) {
				// 여기에 쉼표(,) 키 눌렀을 때 실행할 코드 작성
				popup.hide();
				typeTempStr = "";
				typepos = 0;
			}
			/*if ("+".equals(event.getText()) || "-".equals(event.getText()) || "*".equals(event.getText()) || "/".equals(event.getText()) || "%".equals(event.getText())
			|| "(".equals(event.getText()) || ")".equals(event.getText()) || "[".equals(event.getText()) || "]".equals(event.getText()) || "{".equals(event.getText()) || "}".equals(event.getText()) )
			{
			popup.hide();
			typeTempStr = "";
			typepos = 0;
			}*/
			if(event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.TAB  || event.getCode() == KeyCode.ESCAPE)
			{
				popup.hide();
				typeTempStr = "";
				typepos = 0;
			}
			// 👉 Backspace 추가
			if (event.getCode() == KeyCode.BACK_SPACE) {
				// 원하는 동작 작성
				// 예: 마지막 입력 삭제
				
				if (!typeTempStr.isEmpty() && typepos > 0 && popup.isShowing()) {
					
					typeTempStr = typeTempStr.substring(0, typeTempStr.length() - 1);
					typepos--;
				}
			}
			
			
			
			
		});
		listView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				insertSelectedItem();
			}
		});
		listView.setOnKeyTyped(event -> {
			String ch = event.getCharacter(); // 입력된 실제 문자(조합 포함)
			
			if (ch == null || ch.isEmpty()) return;
			
			// 처리할 기호들
			if ("%".equals(ch) || "{".equals(ch) || "}".equals(ch) || "[".equals(ch) || "]".equals(ch) || "+".equals(ch) || "-".equals(ch) || "*".equals(ch) || "/".equals(ch) || "(".equals(ch) || ")".equals(ch) ) {
				popup.hide();
				typeTempStr = "";
				typepos = 0;
				// 현재 커서 위치에 삽입
				codeArea2.insertText(codeArea2.getCaretPosition(), String.valueOf(ch));
				
				event.consume(); // 이미 처리했으니 더 이상 전달하지 않음(선택)
			}
			
			
		});
		
		
		
		//fileDir = dirFileSet();
		codeArea2.replaceText("public class \n{\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World!\");\n\t}\n}");
		String initialText = codeArea2.getText();
		// 여기서 원하는 이벤트 실행
		onInitialText(initialText);
		codeArea2.getUndoManager().forgetHistory();
		
		ImageView iv;
		// 실행 버튼
		Button newButton = new Button("New");
		//iv = new ImageView(new Image("ButtonImage/NewButton.png"));
		//iv.setFitWidth(70);
		//iv.setFitHeight(70);
		//newButton.setGraphic(iv);
		//newButton.setContentDisplay(ContentDisplay.CENTER);
		Button loadButton = new Button("Load");
		Button saveButton = new Button("save");
		//찾기버튼 추가.
		Button searchButton = new Button("Search");
		Button compileButton = new Button("Compile");
		Button runButton = new Button("Run");
		Button stopButton = new Button("Stop");
		Button optionButton = new Button("Option");
		jarButton = new Button("Jar");
		Button exlsButton = new Button("ExternalList");
		Button styleButton = new Button("style");
		Button informButton = new Button("inform");
		Button gcButton = new Button("Run GC");
		
		Button exitButton = new Button("Exit");
		//0032버전 추가
		Button eraseButton = new Button("지우기");
		Button copyButton = new Button("복사");
		Button agoButton = new Button("ago");
		//0042버전 추가
		Button tabCleanButton = new Button("tabClean");
		//0044버전 추가
		Button saveAsButton = new Button("save As");
		//0047버전 추가
		Button networkButton = new Button("network");
		// 콘솔 출력 영역
		consoleArea = new TextArea();
		//consoleArea.setStyle("-fx-font-family: 'Malgun Gothic';");
		consoleArea.setFont(Font.font("Serif", 14));
		/*ScrollPane consoleScrollPane = new ScrollPane(consoleArea);
		consoleScrollPane.setFitToWidth(true);
		consoleScrollPane.setFitToHeight(true);*/
		SplitPane splitPane = new SplitPane();
		splitPane.setOrientation(Orientation.VERTICAL);
		
		
		
		
		consoleArea.setEditable(false);
		consoleArea.textProperty().addListener((obs, oldText, newText) -> {
			
			/*Platform.runLater( () -> {
			if (runWriter != null && (newText.length() > oldText.length())) {
			String diff = newText.substring(oldText.length()); // 새로 입력된 부분
			try {
			consoleArea.appendText(diff);
			runWriter.write(diff);
			runWriter.flush();
			} catch (IOException asdioe) {
			asdioe.printStackTrace();
			}
			}
			});*/
		});
		consoleArea.setOnInputMethodTextChanged(event -> {
			String committed = event.getCommitted();
			
			if (!committed.isEmpty() && runWriter != null) {
				try {
					baos.write(committed.getBytes("UTF-8"));
					String result = baos.toString("UTF-8");
					
					console_input = committed; // committed만 보내야 중복 없음
					if(useConsoleV){
						consoleArea.appendText(console_input);
					}
					runWriter.write(console_input);
					runWriter.flush();
					numberKeyboard ++;
					backup_number++;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		consoleArea.setOnKeyTyped(event -> {
			//ByteArrayOutputStream baos = new ByteArrayOutputStream();
			String ch = event.getCharacter();
			//byte[] b;
			if(!ch.isEmpty())
			{
				/*try{
				byte[] b = ch.getBytes("CP949"); // 한글도 안전하게 처리
				baos.write(b, 0, b.length);
				console_input = baos.toString();
				//System.out.println(b);
				}catch( UnsupportedEncodingException uee)
				{
				uee.printStackTrace();
				}*/
			}
			//String ch = event.getCharacter();
			console_input = ch;
			//console_line += ch;
			Platform.runLater( () -> {
				
				if(runWriter != null){
					try {
						
						if(!console_input.equals("\b"))
						{
							//Platform.runLater(() -> consoleArea.appendText(console_input));
							if(useConsoleV){
								//System.out.println(numberKeyboard);
								consoleArea.appendText(console_input);
							}
							runWriter.write(console_input);
							
							
							runWriter.flush(); // 반드시 flush 해야 프로세스가 입력을 받음
							
							numberKeyboard ++;
							backup_number++;
						}else if(console_input.equals("\b"))
						{
							if(back_num>0)
							{
								init_back = false;
							}else{
								init_back = true;
							}
							if(numberKeyboard>0)
							{
								
								runWriter.write(console_input);
								
								
								runWriter.flush(); // 반드시 flush 해야 프로세스가 입력을 받음
								//backup_number++;
								numberKeyboard--;
								
								//back_num++;
							}else if(numberKeyboard<=0 && backup_number<=0 && back_num == 0)
							{
								runWriter.write(console_input);
								
								
								runWriter.flush(); // 반드시 flush 해야 프로세스가 입력을 받음
								back_num++;
							}
							
						}
						
					} catch (IOException asdioe) {
						asdioe.printStackTrace();
					}
				}
				
			});
			
			//System.out.println("입력된 키: " + console_line);
		});
		consoleArea.setOnScroll(event -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				popup.hide();
			}
		});
		consoleArea.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				popup.hide();
			}
		});
		consoleArea.setOnKeyPressed(event -> {
			typeTempStr = "";
			typepos = 0;
			if(popup.isShowing())
			{
				popup.hide();
			}
			if (event.getCode() == KeyCode.ENTER) {
				
				
				
				
			}
			if (event.getCode() == KeyCode.BACK_SPACE) {
				Platform.runLater( () -> {
					
					if (runWriter != null && fromKeyboard && numberKeyboard> 0&& backup_number>0) {
						/*
						try{
						// TextArea에서 마지막 글자 제거
						int length = consoleArea.getText().length();
						if (length > 0  && numberKeyboard> 0) {
						if(useConsoleV){
						consoleArea.deleteText(length - 1, length);
						}
						//Platform.runLater(() -> consoleArea.deleteText(length - 1, length));
						
						//runWriter.write("\b");
						//runWriter.flush(); // 반드시 flush 해야 프로세스가 입력을 받음
						numberKeyboard--;
						//System.out.println(numberKeyboard);
						}
						
						// 외부 프로세스에도 Backspace 전달 (필요하다면)
						}catch(IOException ioe)
						{
						
						}*/
						
						int length = consoleArea.getText().length();
						if (length > 0  && numberKeyboard> 0 && backup_number >0) {
							
							if(useConsoleV){
								consoleArea.deleteText(length - 1, length);
							}
							
						}
						
					}
					
					/*int length = consoleArea.getText().length();
					if(runWriter != null && useConsoleV && length >0)
					{
					consoleArea.deleteText(length - 1, length);
					runWriter.write("\b");
					runWriter.flush(); // 반드시 flush 해야 프로세스가 입력을 받음
					}*/
				}); //Platform.runlater
			}
			
			
		});
		
		//컴파일 버튼 클릭시 동작
		newButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			System.gc();
			//codeArea2.clear();//이건 넣으면 안됨...ㅋㅋㅋㅋ
			String fcode = "public class \n{\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World!\");\n\t}\n}";
			//codeArea2.replaceText(fcode);
			TabInfo info = new TabInfo();
			info.file = null;
			info.fileName = "";
			info.fileDir = "";
			info.content = fcode;
			info.modified = false;
			info.pos = 0;
			info.scrollX = codeArea2.estimatedScrollXProperty().getValue();
			info.scrollY = codeArea2.estimatedScrollYProperty().getValue();
			info.encoding = "";
			
			Tab tab = new Tab();
			Label tablabel = new Label("noname");
			Button closeBtn = new Button("x");
			HBox htabbox = new HBox();
			HBox.setHgrow(tablabel, Priority.ALWAYS);
			tablabel.setMaxWidth(Double.MAX_VALUE);
			
			htabbox.getChildren().addAll(tablabel, closeBtn);
			//htabbox.setAlignment(Pos.CENTER_LEFT);
			
			closeBtn.setOnAction(cbe -> {
				// 저장 여부 묻기
				// 저장
				tabPane.getSelectionModel().select(tab);
				String currentCode = codeArea2.getText();
				Tab closingTab = (Tab) tabPane.getSelectionModel().getSelectedItem();   // ← 닫히는 탭
				TabInfo info2 = (TabInfo) closingTab.getUserData();
				if (info.modified) {
					
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("파일 저장");
					alert.setHeaderText(null);
					alert.setContentText("파일 '" + info.fileName + "' 을(를) 저장하시겠습니까?");
					
					ButtonType saveBtn = new ButtonType("확인");
					ButtonType cancelBtn = new ButtonType("취소", ButtonBar.ButtonData.CANCEL_CLOSE);
					cancelBtn = new ButtonType("취소");
					alert.getButtonTypes().setAll(saveBtn, cancelBtn);
					
					Optional<ButtonType> result = alert.showAndWait();
					
					if (result.isPresent() && result.get() == saveBtn) {
						// 확인 눌렀을 때 → 저장은 네가 처리
						// 예: saveFile(info);
						typeTempStr = "";
						typepos = 0;
						if (popup.isShowing()) {
							
							popup.hide();
						}
						//System.gc();
						File selectedFile;
						String selectedEncoding;
						if(fileName.isEmpty())
						{
							FileChooser fileChooser = new FileChooser();
							fileChooser.setTitle("Save Java File");
							fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Files", "*.java"));
							File initialDir = new File(dirFileSet());
							if (initialDir.exists()) {
								fileChooser.setInitialDirectory(initialDir);
							}else
							{
								fileChooser.setInitialDirectory(new File("."));
								fileDir = ".";
							}
							/*
							// 인코딩 선택 UI
							List<String> encodings = Arrays.asList("UTF-8", "MS949", "EUC-KR", "ISO-8859-1");
							ChoiceDialog<String> dialog = new ChoiceDialog<>("UTF-8", encodings);
							dialog.setTitle("Select Encoding");
							dialog.setHeaderText("Choose file encoding");
							dialog.setContentText("Encoding:");
							
							Optional<String> result0 = dialog.showAndWait();
							if (!result0.isPresent()) {
							return; // 사용자가 취소
							}
							
							selectedEncoding = result0.get();*/
							selectedEncoding = "UTF-8";
							selectedFile = fileChooser.showSaveDialog(null);
						}else{
							String fn = fileDir + File.separator + fileName;
							selectedFile = new File(fn);
							selectedEncoding = file_Encoding;
						}
						if (selectedFile != null) {
							try {
								String rawName = selectedFile.getName();
								// 현재 앱 실행 디렉토리
								String appDir = new File("").getAbsolutePath();
								if(fileDir.equals(appDir) && fileName.equals("Welcome.txt"))
								{
									
								}else if (!rawName.toLowerCase().endsWith(".java")) {
									selectedFile = new File(selectedFile.getParent(), rawName + ".java");
								}
								fileDir = selectedFile.getParent();
								fileName = selectedFile.getName();
								file_Encoding = selectedEncoding;
								modified = false;
								String filepath = "javaEditerPath.txt";
								Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
								// 코드 저장
								String code =  currentCode;
								Charset charset = Charset.forName(selectedEncoding);
								Files.write(selectedFile.toPath(), code.getBytes(charset));
								//pathLabel.setText("경로 : " + selectedFile.getAbsolutePath());
								consoleArea.appendText("저장 완료: " + selectedFile.getAbsolutePath() + "\n");
								
							} catch (Exception ex) {
								consoleArea.appendText("저장 오류: " + ex.getMessage() + "\n");
							}
							
							agoFileList = getAgoFile();
							agoFileList = setAddAgoList(selectedFile.getAbsolutePath(),agoFileList);
							setSaveAgoFile(agoFileList);
						}
					}
				}else{
					
				}
				tabPane.getTabs().remove(closingTab);
			});
			//tab.setGraphic(hbox);
			tab.setGraphic(htabbox);
			tab.setClosable(false);
			
			tab.setUserData(info);
			//codeArea2.replaceText(info.content);//이건 넣지 말것...ㅋㅋㅋ
			tabPane.getTabs().add(tab);
			tabPane.getSelectionModel().select(tab);
			
			//SaveInfoFile();
			fileName = "";
			fileDir = "";
			file_Encoding = "";
			pathLabel.setText("경로 : " + " | encoding : ");
			String newText = codeArea2.getText();
			// 여기서 원하는 이벤트 실행
			onInitialText(newText);
			codeArea2.getUndoManager().forgetHistory();
			
		});
		loadButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			//System.gc();
			
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Java File");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Files", "*.java"));
			// 초기 디렉토리 설정
			File initialDir = new File(dirFileSet());
			if (initialDir.exists()) {
				fileChooser.setInitialDirectory(initialDir);
			}else
			{
				fileChooser.setInitialDirectory(new File("."));
				fileDir = ".";
			}
			
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
			if (selectedFile != null) {
				try {
					// 경로와 파일명 저장
					fileDir = selectedFile.getParent();
					fileName = selectedFile.getName();
					tab_OpenFile(selectedFile);
					String filepath = "javaEditerPath.txt";
					Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
					/*
					// 파일 내용 읽어서 코드 영역에 표시
					String encoding = EncodingDetector.detectEncoding(selectedFile);
					file_Encoding = encoding;
					StringBuilder content = new StringBuilder();
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile), encoding));
					String line;
					while ((line = reader.readLine()) != null) {
					content.append(line).append("\n");
					}
					reader.close();
					codeArea2.replaceText(content.toString());
					*/
					String labelStr = "경로 : " +((TabInfo)tabPane.getSelectionModel().getSelectedItem().getUserData()).file.getAbsolutePath()
					+ " | encoding : " + ((TabInfo)tabPane.getSelectionModel().getSelectedItem().getUserData()).encoding;
					tab_OpenFile(selectedFile);
					codeArea2.moveTo(0, 0);
					codeArea2.requestFollowCaret();
					//pathLabel.setText("경로 : " + selectedFile.getAbsolutePath());
					consoleArea.clear();
					consoleArea.appendText("파일 로드 완료: " + ((TabInfo)tabPane.getSelectionModel().getSelectedItem().getUserData()).file.getAbsolutePath() + "\n");
					consoleArea.appendText("인코딩 : " + ((TabInfo)tabPane.getSelectionModel().getSelectedItem().getUserData()).encoding + "\n");
				} catch (Exception ex) {
					consoleArea.appendText("로드 오류: " + ex.getMessage() + "\n");
				}
				String loadText = codeArea2.getText();
				// 여기서 원하는 이벤트 실행
				onInitialText(loadText);
				
				String appDir = new File("").getAbsolutePath();
				if(fileDir.equals(appDir) && fileName.equals("Welcome.txt"))
				{
					
				}else {
					agoFileList = getAgoFile();
					agoFileList = setAddAgoList(selectedFile.getAbsolutePath(),agoFileList);
					setSaveAgoFile(agoFileList);
				}
				
				
				codeArea2.getUndoManager().forgetHistory();
			}
		});
		saveButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			//System.gc();
			File selectedFile;
			String selectedEncoding;
			if(fileName.isEmpty())
			{
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Java File");
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Files", "*.java"));
				File initialDir = new File(dirFileSet());
				if (initialDir.exists()) {
					fileChooser.setInitialDirectory(initialDir);
				}else
				{
					fileChooser.setInitialDirectory(new File("."));
					fileDir = ".";
				}
				/*
				// 인코딩 선택 UI
				List<String> encodings = Arrays.asList("UTF-8", "MS949", "EUC-KR", "ISO-8859-1");
				ChoiceDialog<String> dialog = new ChoiceDialog<>("UTF-8", encodings);
				dialog.setTitle("Select Encoding");
				dialog.setHeaderText("Choose file encoding");
				dialog.setContentText("Encoding:");
				
				Optional<String> result = dialog.showAndWait();
				if (!result.isPresent()) {
				return; // 사용자가 취소
				}
				
				selectedEncoding = result.get();
				*/
				selectedEncoding = "UTF-8";
				selectedFile = fileChooser.showSaveDialog(primaryStage);
			}else{
				String fn = fileDir + File.separator + fileName;
				selectedFile = new File(fn);
				selectedEncoding = file_Encoding;
			}
			if (selectedFile != null) {
				try {
					String rawName = selectedFile.getName();
					// 경로와 파일명 저장
					fileDir = selectedFile.getParent();
					//System.out.println(fileDir);
					fileName = selectedFile.getName();
					file_Encoding = selectedEncoding;
					// 현재 앱 실행 디렉토리
					
					String appDir = new File("").getAbsolutePath();
					if(fileDir.equals(appDir) && fileName.equals("Welcome.txt"))
					{
						
					}else if (!rawName.toLowerCase().endsWith(".java")) {
						selectedFile = new File(selectedFile.getParent(), rawName + ".java");
					}
					
					
					LoadInfoFile(selectedFile);
					
					String filepath = "javaEditerPath.txt";
					Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
					// 코드 저장
					String code = codeArea2.getText();
					Charset charset = Charset.forName(selectedEncoding);
					Files.write(selectedFile.toPath(), code.getBytes(charset));
					//pathLabel.setText("경로 : " + selectedFile.getAbsolutePath());
					consoleArea.appendText("저장 완료: " + selectedFile.getAbsolutePath() + "\n");
					if(!isSaving)
					{
						Tab selected = tabPane.getSelectionModel().getSelectedItem();
						if (selected != null) {
							TabInfo info = (TabInfo) selected.getUserData();
							info.modified = false;   // 변경됨 표시
							//System.out.println("info.modified : " + info.modified);
						}
					}
					
				} catch (Exception ex) {
					consoleArea.appendText("저장 오류: " + ex.getMessage() + "\n");
				}
				
				String appDir = new File("").getAbsolutePath();
				if(fileDir.equals(appDir) && fileName.equals("Welcome.txt"))
				{
					
				}else {
					agoFileList = getAgoFile();
					agoFileList = setAddAgoList(selectedFile.getAbsolutePath(),agoFileList);
					setSaveAgoFile(agoFileList);
				}
				
			}
		});
		saveAsButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			//System.gc();
			File selectedFile;
			String selectedEncoding;
			if(true)
			{
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Java File");
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Files", "*.java"));
				File initialDir = new File(dirFileSet());
				if (initialDir.exists()) {
					fileChooser.setInitialDirectory(initialDir);
				}else
				{
					fileChooser.setInitialDirectory(new File("."));
					fileDir = ".";
				}
				/*
				// 인코딩 선택 UI
				List<String> encodings = Arrays.asList("UTF-8", "MS949", "EUC-KR", "ISO-8859-1");
				ChoiceDialog<String> dialog = new ChoiceDialog<>("UTF-8", encodings);
				dialog.setTitle("Select Encoding");
				dialog.setHeaderText("Choose file encoding");
				dialog.setContentText("Encoding:");
				
				Optional<String> result = dialog.showAndWait();
				if (!result.isPresent()) {
				return; // 사용자가 취소
				}
				
				selectedEncoding = result.get();
				*/
				selectedEncoding = "UTF-8";
				selectedFile = fileChooser.showSaveDialog(primaryStage);
			}
			if (selectedFile != null) {
				try {
					String rawName = selectedFile.getName();
					// 경로와 파일명 저장
					fileDir = selectedFile.getParent();
					fileName = selectedFile.getName();
					file_Encoding = selectedEncoding;
					if (!rawName.toLowerCase().endsWith(".java")) {
						selectedFile = new File(selectedFile.getParent(), rawName + ".java");
					}
					
					
					LoadInfoFile(selectedFile);
					
					String filepath = "javaEditerPath.txt";
					Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
					// 코드 저장
					String code = codeArea2.getText();
					Charset charset = Charset.forName(selectedEncoding);
					Files.write(selectedFile.toPath(), code.getBytes(charset));
					if(!isSaving)
					{
						Tab selected = tabPane.getSelectionModel().getSelectedItem();
						if (selected != null) {
							TabInfo info = (TabInfo) selected.getUserData();
							info.modified = false;   // 변경됨 표시
						}
					}
					//pathLabel.setText("경로 : " + selectedFile.getAbsolutePath());
					consoleArea.appendText("저장 완료: " + selectedFile.getAbsolutePath() + "\n");
				} catch (Exception ex) {
					consoleArea.appendText("저장 오류: " + ex.getMessage() + "\n");
				}
				
				String appDir = new File("").getAbsolutePath();
				if(fileDir.equals(appDir) && fileName.equals("Welcome.txt"))
				{
					
				}else {
					agoFileList = getAgoFile();
					agoFileList = setAddAgoList(selectedFile.getAbsolutePath(),agoFileList);
					setSaveAgoFile(agoFileList);
				}
				
			}
		});
		searchButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			// 새로운 Stage 생성
			Stage searchStage = new Stage();
			searchStage.setTitle("검색");
			searchStage.initModality(Modality.APPLICATION_MODAL); // 모달 창
			
			
			searchStage.initOwner(primaryStage); // 메인 창 기준으로 중앙 배치
			
			// 첫 번째 HBox: 라벨 + 텍스트필드 + 버튼
			Label label = new Label("찾기");
			TextField textField = new TextField();
			Button findButton = new Button("찾기");
			textField.setText(tmpSearch);
			HBox topBox = new HBox(10); // 간격 10px
			topBox.getChildren().addAll(label, textField, findButton);
			
			// 두 번째 HBox: 체크박스 2개
			CheckBox upCheck = new CheckBox("위로");
			CheckBox caseCheck = new CheckBox("대소문자구분");
			upCheck.setSelected(tmpSearchUp);
			caseCheck.setSelected(tmpSearchLower);
			HBox midBox = new HBox(10);
			midBox.getChildren().addAll(upCheck, caseCheck);
			
			// 확인 버튼
			Button okButton = new Button("확인");
			okButton.setOnAction(oe -> {
				tmpSearch = textField.getText();
				tmpSearchUp = upCheck.isSelected();
				tmpSearchLower = caseCheck.isSelected();
				searchStage.close();  // 창 닫기
				
			});
			findButton.setOnAction(fe -> {
				typeTempStr = "";
				typepos = 0;
				if (popup.isShowing()) {
					
					popup.hide();
				}
				// 검색어 가져오기
				String searchText = textField.getText();
				if (searchText == null || searchText.isEmpty()) {
					return; // 검색어 없으면 그냥 리턴
				}
				
				// 현재 커서 위치
				int caretPos = codeArea2.getCaretPosition();
				
				// 전체 텍스트
				String content = codeArea2.getText();
				String lowerContent;
				String lowerSearch;
				// 대소문자 구분 없이 검색
				if(caseCheck.isSelected())
				{
					lowerContent = content;
					lowerSearch = searchText;
					
				} else {
					lowerContent = content.toLowerCase();
					lowerSearch = searchText.toLowerCase();
				}
				// 커서 이후에서 검색
				int foundPos=-1;
				if(upCheck.isSelected())
				{
					foundPos = lowerContent.lastIndexOf(lowerSearch, caretPos - (lowerSearch.length()+1));
					
				} else {
					foundPos = lowerContent.indexOf(lowerSearch, caretPos);
				}
				
				
				if (foundPos != -1) {
					// 검색어 발견 → 커서 이동
					
					codeArea2.moveTo(foundPos);
					codeArea2.selectRange(foundPos, foundPos + searchText.length());
					// 커서 위치의 문단 번호 구하기
					int paragraph = codeArea2.offsetToPosition(foundPos,Bias.Forward).getMajor();
					
					// 화면 스크롤 이동
					codeArea2.showParagraphAtTop(paragraph);
					
					
				} else {
					// 못 찾으면 메시지 출력 (옵션)
					// 메시지창 띄우기
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("검색 결과");
					alert.setHeaderText(null);
					alert.setContentText("검색어를 찾을 수 없습니다.");
					alert.showAndWait();
					
				}
				
			});
			// 전체 레이아웃 (VBox로 세로 배치)
			VBox root = new VBox(15);
			root.setPadding(new Insets(10));
			root.getChildren().addAll(topBox, midBox, okButton);
			
			// Scene 설정
			Scene scene = new Scene(root, 300, 150);
			searchStage.setScene(scene);
			searchStage.show();
			
		});
		compileButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			cleanProcess();
			consoleArea.clear();
			try {
				// 1. 코드 저장
				String code = codeArea2.getText();
				for (String fontName : javafx.scene.text.Font.getFamilies()) {
					//System.out.println(fontName);
				}
				
				
				String filepath = fileDir+File.separator+fileName;
				//System.out.println("filepath : " + filepath);
				Charset charsetC = Charset.forName("UTF-8");
				//System.out.println(charsetC);
				Files.write(Paths.get(filepath), code.getBytes(charsetC));
				String savepath = "javaEditerPath.txt";
				Files.write(Paths.get(savepath), fileDir.getBytes(StandardCharsets.UTF_8));
				if(!isSaving)
				{
					Tab selected = tabPane.getSelectionModel().getSelectedItem();
					if (selected != null) {
						TabInfo info = (TabInfo) selected.getUserData();
						info.modified = false;   // 변경됨 표시
						//System.out.println("info.modified : " + info.modified);
					}
				}
				if(!optionUseb)
				{
					try {
						// 2. 컴파일
						ProcessBuilder compile = new ProcessBuilder("javac", fileName);
						compile.redirectErrorStream(true);
						compile.directory(new File(fileDir)); // fileDir은 실행할 디렉토리 경로
						
						Process compileProcess = compile.start();
						String os = System.getProperty("os.name").toLowerCase();
						Charset charset;
						if (os.contains("win")) {
							charset = Charset.forName("MS949");
						}else {
							// macOS와 Linux는 UTF-8
							charset = StandardCharsets.UTF_8;
						}
						
						BufferedReader compileReader = new BufferedReader(new InputStreamReader(compileProcess.getInputStream(), charset));
						
						int ch;
						StringBuilder sb = new StringBuilder();
						
						consoleArea.clear();
						consoleArea.appendText("컴파일: \n");
						
						while ((ch = compileReader.read()) != -1) {
							char c = (char) ch;
							sb.append(c);
							
							// 줄 단위로 UI 업데이트
							if (c == '\n') {
								final String output = sb.toString();
								sb.setLength(0); // StringBuilder 초기화
								
								Platform.runLater(() -> {
									consoleArea.appendText(output);
									consoleArea.positionCaret(consoleArea.getText().length());
									consoleArea.setScrollTop(Double.MAX_VALUE);
								});
							}
							
						}
						Platform.runLater(() -> {
							consoleArea.appendText("컴파일 완료했습니다.\n");
							
							
							// 커서를 맨 끝으로 이동
							consoleArea.positionCaret(consoleArea.getText().length());
							// 또는 스크롤을 맨 아래로 내리기
							consoleArea.setScrollTop(Double.MAX_VALUE);
						});
						compileProcess.waitFor();
						
					} catch (InterruptedException | IOException te) {
						te.printStackTrace();
						
					}
				}else
				{
					// 입력값들 가져오기
					String arg1 = optionStr[1].trim();
					String arg2 = optionStr[2].trim();
					String mainClass = optionStr[3].trim();
					String path = optionStr[0].trim();
					String javacpath = findJavacFromSystemPath("javac.exe");
					// 명령어 조립
					List<String> command = new ArrayList<>();
					command.add("javac");
					int count = 0;
					for(char c : arg1.toCharArray())
					{
						
						if(c == ' ') count++;
					}
					String[] parts = arg1.split("\\s+");
					
					for(int i=0;i<count+1;i++)
					{
						if(parts[i].equals("")) break;
						command.add(parts[i]);
					}
					count = 0;
					for(char c: mainClass.toCharArray())
					{
						if(c==' ') count++;
					}
					parts = mainClass.split("\\s+");
					for(int i=0;i<count+1;i++)
					{
						if(parts[i].equals("")) break;
						command.add(parts[i]);
					}
					
					count = 0;
					for(char c : arg2.toCharArray())
					{
						if(c == ' ') count++;
					}
					parts = arg2.split(" ");
					for(int i=0;i<count+1;i++)
					{
						if(parts[i].equals("")) break;
						command.add(parts[i]);
					}
					
					try {
						
						ProcessBuilder compile = new ProcessBuilder(command);
						compile.directory(new File(path));
						compile.redirectErrorStream(true);
						Process compileProcess = compile.start();
						String os = System.getProperty("os.name").toLowerCase();
						Charset charset;
						if (os.contains("win")) {
							charset = Charset.forName("MS949");
						}else {
							// macOS와 Linux는 UTF-8
							charset = StandardCharsets.UTF_8;
						}
						BufferedReader compileReader = new BufferedReader(new InputStreamReader(compileProcess.getInputStream(), charset));
						int ch;
						StringBuilder sb = new StringBuilder();
						
						consoleArea.clear();
						consoleArea.appendText("컴파일: \n");
						while ((ch = compileReader.read()) != -1) {
							char c = (char) ch;
							sb.append(c);
							
							// 줄 단위로 UI 업데이트
							if (c == '\n') {
								final String output = sb.toString();
								sb.setLength(0); // StringBuilder 초기화
								
								Platform.runLater(() -> {
									consoleArea.appendText(output);
									consoleArea.positionCaret(consoleArea.getText().length());
									consoleArea.setScrollTop(Double.MAX_VALUE);
								});
							}
							
						}
						Platform.runLater(() -> {
							consoleArea.appendText("컴파일 완료했습니다.\n");
							//consoleArea.appendText(command + "\n");
							
							// 커서를 맨 끝으로 이동
							consoleArea.positionCaret(consoleArea.getText().length());
							// 또는 스크롤을 맨 아래로 내리기
							consoleArea.setScrollTop(Double.MAX_VALUE);
						});
						compileProcess.waitFor();
						
					} catch (InterruptedException | IOException te) {
						te.printStackTrace();
					}
					
				}
				
				
				
			} catch (Exception ex) {
				consoleArea.appendText("오류 발생: " + ex.getMessage() + "\n");
				if(fileName.isEmpty())
				{
					if(!optionUseb)
					{
						consoleArea.appendText("파일이 없습니다. 먼저 save를 해야 합니다.\n");
					}else {
						consoleArea.appendText("옵션을 사용하면 옵션을 설정하세요.\n");
						consoleArea.appendText("파일이 없으면 먼저 save를 해야 합니다.\n");
					}
				}
			}
		});
		// 실행 버튼 클릭 시 동작
		runButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			cleanProcess();
			consoleArea.clear();
			
			try {
				// 1. 코드 저장
				String code = codeArea2.getText();
				for (String fontName : javafx.scene.text.Font.getFamilies()) {
					//System.out.println(fontName);
				}
				
				
				String filepath = fileDir+File.separator+fileName;
				Charset charsetC = Charset.forName("UTF-8");
				Files.write(Paths.get(filepath), code.getBytes(charsetC));
				String savepath = "javaEditerPath.txt";
				Files.write(Paths.get(savepath), fileDir.getBytes(StandardCharsets.UTF_8));
				if(!isSaving)
				{
					Tab selected = tabPane.getSelectionModel().getSelectedItem();
					if (selected != null) {
						TabInfo info = (TabInfo) selected.getUserData();
						info.modified = false;   // 변경됨 표시
						//System.out.println("info.modified : " + info.modified);
					}
				}
				if(!optionUseb)
				{
					
					try {
						// 2. 컴파일
						ProcessBuilder compile = new ProcessBuilder("javac", fileName);
						compile.redirectErrorStream(true);
						compile.directory(new File(fileDir)); // fileDir은 실행할 디렉토리 경로
						
						Process compileProcess = compile.start();
						String os = System.getProperty("os.name").toLowerCase();
						Charset charset;
						if (os.contains("win")) {
							charset = Charset.forName("CP949");
						}else {
							// macOS와 Linux는 UTF-8
							charset = StandardCharsets.UTF_8;
						}
						BufferedReader compileReader = new BufferedReader(new InputStreamReader(compileProcess.getInputStream(), charset));
						int ch;
						StringBuilder sb = new StringBuilder();
						
						consoleArea.clear();
						consoleArea.appendText("컴파일: \n");
						while ((ch = compileReader.read()) != -1) {
							char c = (char) ch;
							sb.append(c);
							
							// 줄 단위로 UI 업데이트
							if (c == '\n') {
								final String output = sb.toString();
								sb.setLength(0); // StringBuilder 초기화
								
								Platform.runLater(() -> {
									consoleArea.appendText(output);
									consoleArea.positionCaret(consoleArea.getText().length());
									consoleArea.setScrollTop(Double.MAX_VALUE);
								});
							}
							
						}
						Platform.runLater(() -> {
							consoleArea.appendText("컴파일 완료했습니다.\n");
							
							
							// 커서를 맨 끝으로 이동
							consoleArea.positionCaret(consoleArea.getText().length());
							// 또는 스크롤을 맨 아래로 내리기
							consoleArea.setScrollTop(Double.MAX_VALUE);
						});
						compileProcess.waitFor();
					} catch (InterruptedException | IOException te) {
						te.printStackTrace();
					}
					
					
					// 3. 실행
					
					
					new Thread(() -> {
						try {
							ProcessBuilder run = new ProcessBuilder("java", fileName.replace(".java",""));
							run.redirectErrorStream(true);
							run.directory(new File(fileDir)); // fileDir은 실행할 디렉토리 경로
							runProcess = run.start();
							runProcessList.add(runProcess);
							String os = System.getProperty("os.name").toLowerCase();
							Charset charset;
							if (os.contains("win")) {
								charset = Charset.forName("CP949");
							}else {
								// macOS와 Linux는 UTF-8
								charset = StandardCharsets.UTF_8;
							}
							BufferedReader runReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream(), charset));
							// 프로세스 입력 쓰기
							runWriter = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream(),Charset.forName("UTF-8")));
							
							
							//String line;
							consoleArea.clear();
							consoleArea.appendText("실행 : \n");
							fromKeyboard = false;
							numberKeyboard = 0;
							int ch;
							StringBuilder buffer = new StringBuilder();
							long start = System.currentTimeMillis();
							
							
							while ((ch = runReader.read()) != -1) {
								final char output = (char) ch;
								
								long end  = System.currentTimeMillis();
								if(runReader.ready())
								{
									//System.out.println("(대기 상태)2");
									start = end;
									fromKeyboard = false;
									
									
									
									
									
								}
								if(!runReader.ready())
								{
									//System.out.println("(대기 상태)1");
									start = end;
									fromKeyboard = true;
									//System.out.println("numberKeyboard : " + numberKeyboard);
									//System.out.println("backup_number : " + backup_number);
									numberKeyboard = 0;
									backup_number = 0;
									back_num =0;
								}
								
								
								//System.out.println((int)ch);
								if (output == (char)8) {
									//System.out.println("numberKeyboard : " + backup_number);
									if (buffer.length() > 0 && backup_number > 0){
										
										buffer.deleteCharAt(buffer.length() - 1);
										backup_number--;
										
									}
									
									//System.out.println("output : " + (int)output);
									
								}else if(fromKeyboard){
									//System.out.println(fromKeyboard);
									buffer.append(output);
									//System.out.println("numberKeyboard : " + backup_number);
									
									
									
									
								}else if(!fromKeyboard)
								{
									//System.out.println(ch);
									buffer.append(output);
									
								}
								
								
								//System.out.print((int)output + " ");
								// 줄 수 체크
								//buffer.append(output);
								
								
								final String textToShow = buffer.toString();
								
								Platform.runLater(() -> {
									/*String[] lines;
									
									
									lines = consoleArea.getText().split("\n");
									int lineCount = lines.length;
									StringBuilder sb = new StringBuilder();
									if (lineCount > 200) {
									// 뒤의 500줄만 남기기
									
									
									for (int i = lineCount - 200; i < lineCount; i++) {
									
									sb.append(lines[i]).append("\n");
									}
									consoleArea.setText(sb.toString());
									
									}else{
									
									consoleArea.setText(textToShow);
									}*/
									consoleArea.setText(textToShow);
									//consoleArea.setText(sb.toString());
									// 또는 스크롤을 맨 아래로 내리기
									// 커서를 맨 끝으로 이동
									consoleArea.positionCaret(consoleArea.getText().length());
									
									consoleArea.setScrollTop(Double.MAX_VALUE);
									
									
									
									
								});
							}
							Platform.runLater(() -> {
								consoleArea.appendText("완료했습니다.\n");
								// 커서를 맨 끝으로 이동
								consoleArea.positionCaret(consoleArea.getText().length());
								// 또는 스크롤을 맨 아래로 내리기
								consoleArea.setScrollTop(Double.MAX_VALUE);
							});
							runWriter = null;
							runProcess.waitFor();
							runProcess = null;
						} catch (InterruptedException | IOException te) {
							te.printStackTrace();
						}
					}).start();
					
				}else{
					// 입력값들 가져오기
					String arg1 = optionStr[1].trim();
					String arg2 = optionStr[2].trim();
					String mainClass = optionStr[3].trim();
					String path = optionStr[0].trim();
					String javacpath = findJavacFromSystemPath("javac.exe");
					String javapath = findJavacFromSystemPath("java.exe");
					// 명령어 조립
					List<String> command = new ArrayList<>();
					command.add("javac");
					int count = 0;
					for(char c : arg1.toCharArray())
					{
						
						if(c == ' ') count++;
					}
					String[] parts = arg1.split("\\s+");
					
					for(int i=0;i<count+1;i++)
					{
						if(parts[i].equals("")) break;
						command.add(parts[i]);
					}
					count = 0;
					for(char c: mainClass.toCharArray())
					{
						if(c==' ') count++;
					}
					parts = mainClass.split("\\s+");
					for(int i=0;i<count+1;i++)
					{
						if(parts[i].equals("")) break;
						command.add(parts[i]);
					}
					count = 0;
					for(char c : arg2.toCharArray())
					{
						if(c == ' ') count++;
					}
					parts = arg2.split(" ");
					for(int i=0;i<count+1;i++)
					{
						if(parts[i].equals("")) break;
						command.add(parts[i]);
					}
					try {
						
						ProcessBuilder compile = new ProcessBuilder(command);
						compile.directory(new File(path));
						compile.redirectErrorStream(true);
						Process compileProcess = compile.start();
						String os = System.getProperty("os.name").toLowerCase();
						Charset charset;
						if (os.contains("win")) {
							charset = Charset.forName("MS949");
						}else {
							// macOS와 Linux는 UTF-8
							charset = StandardCharsets.UTF_8;
						}
						BufferedReader compileReader = new BufferedReader(new InputStreamReader(compileProcess.getInputStream(), charset));
						int ch;
						StringBuilder sb = new StringBuilder();
						
						consoleArea.clear();
						consoleArea.appendText("컴파일: \n");
						while ((ch = compileReader.read()) != -1) {
							char c = (char) ch;
							sb.append(c);
							
							// 줄 단위로 UI 업데이트
							if (c == '\n') {
								final String output = sb.toString();
								sb.setLength(0); // StringBuilder 초기화
								
								Platform.runLater(() -> {
									consoleArea.appendText(output);
									consoleArea.positionCaret(consoleArea.getText().length());
									consoleArea.setScrollTop(Double.MAX_VALUE);
								});
							}
							
						}
						Platform.runLater(() -> {
							consoleArea.appendText("컴파일 완료했습니다.\n");
							
							
							// 커서를 맨 끝으로 이동
							consoleArea.positionCaret(consoleArea.getText().length());
							// 또는 스크롤을 맨 아래로 내리기
							consoleArea.setScrollTop(Double.MAX_VALUE);
						});
						compileProcess.waitFor();
					} catch (InterruptedException | IOException te) {
						te.printStackTrace();
					}
					arg1 = optionStr[4].trim();
					arg2 = optionStr[5].trim();
					mainClass = optionStr[6].trim();
					// 명령어 조립
					List<String> command2 = new ArrayList<>();
					command2.add("java");
					count = 0;
					for(char c : arg1.toCharArray())
					{
						if(c == ' ') count++;
					}
					String[] parts2 = arg1.split("\\s+");
					
					for(int i=0;i<count+1;i++)
					{
						if(parts2[i].equals("")) break;
						command2.add(parts2[i]);
					}
					count = 0;
					for(char c: mainClass.toCharArray())
					{
						if(c==' ') count++;
					}
					parts = mainClass.split("\\s+");
					for(int i=0;i<count+1;i++)
					{
						if(parts[i].equals("")) break;
						command2.add(parts[i]);
					}
					count = 0;
					for(char c : arg2.toCharArray())
					{
						if(c == ' ') count++;
					}
					parts2 = arg2.split(" ");
					for(int i=0;i<count+1;i++)
					{
						if(parts2[i].equals("")) break;
						command2.add(parts2[i]);
					}
					// 3. 실행
					//System.out.println(command2);
					
					new Thread(() -> {
						try {
							ProcessBuilder run = new ProcessBuilder(command2);
							run.redirectErrorStream(true);
							run.directory(new File(path)); // fileDir은 실행할 디렉토리 경로
							runProcess = run.start();
							runProcessList.add(runProcess);
							String os = System.getProperty("os.name").toLowerCase();
							Charset charset;
							if (os.contains("win")) {
								charset = Charset.forName("CP949");
							}else {
								// macOS와 Linux는 UTF-8
								charset = StandardCharsets.UTF_8;
							}
							BufferedReader runReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream(), charset));
							// 프로세스 입력 쓰기
							runWriter = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream(), Charset.forName("UTF-8")));
							
							consoleArea.clear();
							consoleArea.appendText("실행 : \n");
							fromKeyboard = false;
							numberKeyboard = 1;
							int ch;
							
							StringBuilder buffer = new StringBuilder();
							long start = System.currentTimeMillis();
							
							
							while ((ch = runReader.read()) != -1) {
								final char output = (char) ch;
								
								long end  = System.currentTimeMillis();
								if(runReader.ready())
								{
									//System.out.println("(대기 상태)2");
									start = end;
									fromKeyboard = false;
									
									
									
									
									
								}
								if(!runReader.ready())
								{
									//System.out.println("(대기 상태)1");
									start = end;
									fromKeyboard = true;
									//System.out.println("numberKeyboard : " + numberKeyboard);
									//System.out.println("backup_number : " + backup_number);
									numberKeyboard = 0;
									backup_number = 0;
									back_num =0;
								}
								
								
								//System.out.println((int)ch);
								if (output == (char)8) {
									//System.out.println("numberKeyboard : " + backup_number);
									if (buffer.length() > 0 && backup_number > 0){
										
										buffer.deleteCharAt(buffer.length() - 1);
										backup_number--;
										
									}
									
									//System.out.println("output : " + (int)output);
									
								}else if(fromKeyboard){
									//System.out.println(fromKeyboard);
									buffer.append(output);
									//System.out.println("numberKeyboard : " + backup_number);
									
									
									
									
								}else if(!fromKeyboard)
								{
									//System.out.println(fromKeyboard);
									buffer.append(output);
									
								}
								
								
								final String textToShow = buffer.toString();
								
								Platform.runLater(() -> {
									/*String[] lines;
									
									
									lines = consoleArea.getText().split("\n");
									int lineCount = lines.length;
									StringBuilder sb = new StringBuilder();
									if (lineCount > 200) {
									// 뒤의 500줄만 남기기
									
									
									for (int i = lineCount - 200; i < lineCount; i++) {
									
									sb.append(lines[i]).append("\n");
									}
									consoleArea.setText(sb.toString());
									
									}else{
									
									consoleArea.setText(textToShow);
									}*/
									consoleArea.setText(textToShow);
									//consoleArea.setText(sb.toString());
									// 커서를 맨 끝으로 이동
									consoleArea.positionCaret(consoleArea.getText().length());
									// 또는 스크롤을 맨 아래로 내리기
									consoleArea.setScrollTop(Double.MAX_VALUE);
									
									
									
								});
							}
							Platform.runLater(() -> {
								consoleArea.appendText("완료했습니다.\n");
								// 커서를 맨 끝으로 이동
								consoleArea.positionCaret(consoleArea.getText().length());
								// 또는 스크롤을 맨 아래로 내리기
								consoleArea.setScrollTop(Double.MAX_VALUE);
							});
							runWriter = null;
							runProcess.waitFor();
							runProcess = null;
						} catch (InterruptedException | IOException te) {
							te.printStackTrace();
						}
					}).start();
					/*while ((line = runReader.readLine()) != null) {
					consoleArea.appendText(line + "\n");
					}*/
					
					
				}
				
				
			} catch (Exception ex) {
				consoleArea.appendText("오류 발생: " + ex.getMessage() + "\n");
				if(fileName.isEmpty())
				{
					if(!optionUseb)
					{
						consoleArea.appendText("파일이 없습니다. 먼저 save를 해야 합니다.\n");
					}else {
						consoleArea.appendText("옵션을 사용하면 옵션을 설정하세요.\n");
						consoleArea.appendText("파일이 없으면 먼저 save를 해야 합니다.\n");
					}
				}
			}
			
		});
		stopButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			if(!runProcessList.isEmpty())
			{
				try{
					//System.out.println("런프로세스정상작동중");
					//runProcess.waitFor();
					
					while(!runProcessList.isEmpty())
					{
						Thread.sleep(100); // 3초 기다림
						Process process = runProcessList.poll();
						process.destroy();//정상종료
					}
					runProcessList.clear();
					
					
					//runProcess.destroyForcibly(); // 강제 종료 (필요할 때만)
				}catch(Exception ffe)
				{
					ffe.printStackTrace();
				}
				
			}else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("정보");
				alert.setHeaderText(null); // 헤더는 없애고
				alert.setContentText("자바가 실행중이지 않습니다.");
				alert.showAndWait(); // 확인 버튼만 있는 창 띄우기
				
			}
		});
		optionButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			Stage optionStage = new Stage();
			optionStage.setTitle("옵션");
			//optionStage.setAlwaysOnTop(true); // 항상 위에 표시되도록 설정
			optionStage.initModality(Modality.APPLICATION_MODAL);
			
			VBox root = new VBox(10);
			root.setPadding(new Insets(10));
			
			// 체크박스
			CheckBox useCheckBox = new CheckBox("사용하기");
			
			// 저장 / 불러오기 버튼
			Button osaveButton = new Button("저장");
			Button oloadButton = new Button("불러오기");
			Button odelButton = new Button("모두지우기");
			HBox saveLoadBox = new HBox(10, osaveButton, oloadButton,odelButton);
			
			// 위치 입력
			Label locationLabel = new Label("위치 : ");
			TextField locationField = new TextField();
			locationField.setPrefWidth(400); // 원하는 너비로 설정
			
			Button browseButton = new Button("...");
			browseButton.setOnAction(oe -> {
				//dirFileSet();
				DirectoryChooser directoryChooser = new DirectoryChooser();
				directoryChooser.setTitle("디렉토리 선택");
				
				// 초기 디렉토리 설정
				File initialDir = new File(dirFileSet());
				if (initialDir.exists()) {
					directoryChooser.setInitialDirectory(initialDir);
				} else {
					directoryChooser.setInitialDirectory(new File("."));
					fileDir = ".";
				}
				
				// 디렉토리 선택 창 띄우기
				File selectedDirectory = directoryChooser.showDialog(primaryStage);
				if (selectedDirectory != null) {
					try {
						// 선택된 디렉토리 경로를 사용
						//System.out.println("선택된 디렉토리: " + selectedDirectory.getAbsolutePath());
						// 필요 시 fileDir 업데이트
						//fileDir = selectedDirectory.getAbsolutePath();
						//String filepath = "javaEditerPath.txt";
						//Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
						locationField.setText(selectedDirectory.getAbsolutePath());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			HBox locationBox = new HBox(10, locationLabel, locationField, browseButton);
			
			Button pathBtn1 = new Button("Path");
			Button pathBtn2 = new Button("Path");
			Button pathBtn3 = new Button("Path");
			Button pathBtn4 = new Button("Path");
			// javac 옵션
			Label javacLabel = new Label("javac옵션");
			
			Label javacArg1Label = new Label("명령인수1 : ");
			TextField javacArg1Field = new TextField();
			javacArg1Field.setPrefWidth(400); // 원하는 너비로 설정
			
			HBox javacArg1Box = new HBox(10, javacArg1Label, javacArg1Field,pathBtn1);
			
			Label javacArg2Label = new Label("명령인수2 : ");
			TextField javacArg2Field = new TextField();
			javacArg2Field.setPrefWidth(400); // 원하는 너비로 설정
			HBox javacArg2Box = new HBox(10, javacArg2Label, javacArg2Field,pathBtn2);
			
			Label javacMainLabel = new Label("메인클래스 : ");
			TextField javacMainField = new TextField();
			javacMainField.setPrefWidth(400); // 원하는 너비로 설정
			HBox javacMainBox = new HBox(10, javacMainLabel, javacMainField);
			
			// java 옵션
			Label javaLabel = new Label("java옵션");
			
			Label javaArg1Label = new Label("명령인수1 : ");
			TextField javaArg1Field = new TextField();
			javaArg1Field.setPrefWidth(400); // 원하는 너비로 설정
			HBox javaArg1Box = new HBox(10, javaArg1Label, javaArg1Field,pathBtn3);
			
			Label javaArg2Label = new Label("명령인수2 : ");
			TextField javaArg2Field = new TextField();
			javaArg2Field.setPrefWidth(400); // 원하는 너비로 설정
			HBox javaArg2Box = new HBox(10, javaArg2Label, javaArg2Field,pathBtn4);
			
			Label javaMainLabel = new Label("메인클래스 : ");
			TextField javaMainField = new TextField();
			javaMainField.setPrefWidth(400); // 원하는 너비로 설정
			HBox javaMainBox = new HBox(10, javaMainLabel, javaMainField);
			
			//필드초기화
			useCheckBox.setSelected(optionUseb);
			locationField.setText(optionStr[0]);
			javacArg1Field.setText(optionStr[1]);
			javacArg2Field.setText(optionStr[2]);
			javacMainField.setText(optionStr[3]);
			javaArg1Field.setText(optionStr[4]);
			javaArg2Field.setText(optionStr[5]);
			javaMainField.setText(optionStr[6]);
			int[] caretPos = {0,0,0,0};
			javacArg1Field.caretPositionProperty().addListener((obs, oldPos, newPos) -> {
				if (javacArg1Field.isFocused()) {
					caretPos[0] = newPos.intValue();
					//System.out.println("catePos[0] : " + caretPos[0]);
				}
			});
			javacArg2Field.caretPositionProperty().addListener((obs, oldPos, newPos) -> {
				if (javacArg2Field.isFocused()) {
					caretPos[1] = newPos.intValue();
					//System.out.println("catePos[1] : " + caretPos[1]);
				}
			});
			javaArg1Field.caretPositionProperty().addListener((obs, oldPos, newPos) -> {
				if (javaArg1Field.isFocused()) {
					caretPos[2] = newPos.intValue();
					//System.out.println("catePos[2] : " + caretPos[2]);
				}
			});
			javaArg2Field.caretPositionProperty().addListener((obs, oldPos, newPos) -> {
				if (javaArg2Field.isFocused()) {
					caretPos[3] = newPos.intValue();
					//System.out.println("catePos[3] : " + caretPos[3]);
				}
			});
			pathBtn1.setOnAction(pbe -> {
				String result = handlePath(1,locationField.getText());
				
				String original = (javacArg1Field.getText() == null) ? "" : javacArg1Field.getText();    // 기존 텍스트
				
				// 앞부분 + result + 뒷부분
				String newText = original.substring(0, caretPos[0])
				+ result
				+ original.substring(caretPos[0]);
				
				javacArg1Field.setText(newText);
				
				// 삽입된 문자열 뒤로 커서 이동
				javacArg1Field.positionCaret(caretPos[0] + result.length());
			});
			pathBtn2.setOnAction(pbe -> {
				String result = handlePath(2,locationField.getText());
				
				String original =( javacArg2Field.getText() == null) ? "" : javacArg2Field.getText();    // 기존 텍스트
				
				// 앞부분 + result + 뒷부분
				String newText = original.substring(0,caretPos[1])
				+ result
				+ original.substring(caretPos[1]);
				
				javacArg2Field.setText(newText);
				
				// 삽입된 문자열 뒤로 커서 이동
				javacArg2Field.positionCaret(caretPos[1] + result.length());
			});
			pathBtn3.setOnAction(pbe -> {
				String result = handlePath(3,locationField.getText());
				
				String original = (javaArg1Field.getText() == null ) ? "" : javaArg1Field.getText();    // 기존 텍스트
				
				// 앞부분 + result + 뒷부분
				String newText = original.substring(0, caretPos[2])
				+ result
				+ original.substring(caretPos[2]);
				
				javaArg1Field.setText(newText);
				
				// 삽입된 문자열 뒤로 커서 이동
				javaArg1Field.positionCaret(caretPos[2] + result.length());
			});
			pathBtn4.setOnAction(pbe -> {
				String result = handlePath(4,locationField.getText());
				
				String original = (javaArg2Field.getText() ==  null) ? "" : javaArg2Field.getText();    // 기존 텍스트
				
				// 앞부분 + result + 뒷부분
				String newText = original.substring(0,caretPos[3])
				+ result
				+ original.substring(caretPos[3]);
				
				javaArg2Field.setText(newText);
				
				// 삽입된 문자열 뒤로 커서 이동
				javaArg2Field.positionCaret(caretPos[3] + result.length());
			});
			osaveButton.setOnAction(oe -> {
				StringBuilder sb = new StringBuilder();
				
				// 고정 문자 추가 (char 형 숫자 4글자)
				sb.append((char)4444);
				sb.append((char)4254);
				sb.append((char)3454);
				sb.append((char)1111);
				sb.append(";;");
				// 텍스트 필드 내용 가져오기 + 구분자 ";"로 추가
				String locf = locationField.getText();
				String javaca1 = javacArg1Field.getText();
				String javaca2 = javacArg2Field.getText();
				String javacm = javacMainField.getText();
				String javaa1 = javaArg1Field.getText();
				String javaa2 = javaArg2Field.getText();
				String javam = javaMainField.getText();
				if(locf == null) locf = "";
				if(javaca1 == null) javaca1 = "";
				if(javaca2 == null) javaca2 = "";
				if(javacm == null) javacm = "";
				if(javaa1 == null) javaa1 = "";
				if(javaa2 == null) javaa2 = "";
				if(javam == null) javam = "";
				sb.append(locf).append(";;");
				sb.append(javaca1).append(";;");
				sb.append(javaca2).append(";;");
				sb.append(javacm).append(";;");
				
				
				sb.append(javaa1).append(";;");
				sb.append(javaa2).append(";;");
				sb.append(javam).append(";;");
				//dirFileSet();
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Java File");
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("jeop Files", "*.jeop"));
				File initialDir = new File(dirFileSet());
				if (initialDir.exists()) {
					fileChooser.setInitialDirectory(initialDir);
				}else
				{
					fileChooser.setInitialDirectory(new File("."));
					fileDir = ".";
				}
				
				File selectedFile = fileChooser.showSaveDialog(primaryStage);
				//File selectedFile = fileChooser.showSaveDialog(null);
				if (selectedFile != null) {
					try {
						String rawName = selectedFile.getName();
						
						if (!rawName.toLowerCase().endsWith(".jeop")) {
							selectedFile = new File(selectedFile.getParent(), rawName + ".jeop");
						}
						
						// 경로와 파일명 저장
						//fileDir = selectedFile.getParent();
						
						//String filepath = "javaEditerPath.txt";
						//Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
						// 코드 저장
						
						Files.write(selectedFile.toPath(), sb.toString().getBytes(StandardCharsets.UTF_8));
						
						
					} catch (Exception ex) {
						
					}
				}
			});
			oloadButton.setOnAction(oe -> {
				//dirFileSet();
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open jeop File");
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("jeop Files", "*.jeop"));
				// 초기 디렉토리 설정
				File initialDir = new File(dirFileSet());
				if (initialDir.exists()) {
					fileChooser.setInitialDirectory(initialDir);
				}else
				{
					fileChooser.setInitialDirectory(new File("."));
					fileDir = ".";
				}
				
				File selectedFile = fileChooser.showOpenDialog(primaryStage);
				if (selectedFile != null) {
					try {
						// 경로와 파일명 저장
						//fileDir = selectedFile.getParent();
						
						//String filepath = "javaEditerPath.txt";
						//Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
						// 파일 내용 읽어서 코드 영역에 표시
						StringBuilder content = new StringBuilder();
						BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile), StandardCharsets.UTF_8));
						String line;
						while ((line = reader.readLine()) != null) {
							content.append(line).append("\n");
						}
						reader.close();
						String[] tokens = content.toString().split(";;");
						String sb = tokens[0];
						if ( sb.charAt(0) == (char)4444&&sb.charAt(1) == (char)4254&&sb.charAt(2) == (char)3454&&sb.charAt(3) == (char)1111) {
							locationField.setText(tokens[1]);
							javacArg1Field.setText(tokens[2]);
							javacArg2Field.setText(tokens[3]);
							javacMainField.setText(tokens[4]);
							javaArg1Field.setText(tokens[5]);
							javaArg2Field.setText(tokens[6]);
							javaMainField.setText(tokens[7]);
						}else{
							// 메시지창 띄우기
							Alert alert = new Alert(AlertType.ERROR, "파일을 불러오지 못했습니다.", ButtonType.OK);
							alert.setTitle("오류");
							alert.setHeaderText(null); // 헤더 제거
							alert.showAndWait(); // 사용자 확인 대기
							
						}
						
					} catch (Exception ex) {
						
					}
				}
			});
			odelButton.setOnAction(oe -> {
				locationField.setText("");
				javacArg1Field.setText("");
				javacArg2Field.setText("");
				javacMainField.setText("");
				javaArg1Field.setText("");
				javaArg2Field.setText("");
				javaMainField.setText("");
			});
			// 확인 / 취소 버튼
			Button okButton = new Button("확인");
			Button cancelButton = new Button("취소");
			okButton.setOnAction(oe -> {
				testb = true;
				optionUseb = useCheckBox.isSelected();
				optionStr[0] = locationField.getText() != null ? locationField.getText() : "";
				optionStr[1] = javacArg1Field.getText() != null ? javacArg1Field.getText() : "";
				optionStr[2] = javacArg2Field.getText() != null ? javacArg2Field.getText() : "";
				optionStr[3] = javacMainField.getText() != null ? javacMainField.getText() : "";
				optionStr[4] = javaArg1Field.getText() != null ? javaArg1Field.getText() : "";
				optionStr[5] = javaArg2Field.getText() != null ? javaArg2Field.getText() : "";
				optionStr[6] = javaMainField.getText() != null ? javaMainField.getText() : "";
				// 필요한 작업 수행 후 창 닫기
				jarlist = new ArrayList<>();
				jarClassList = new ArrayList<>();
				if(optionUseb)
				{
					useDir = optionStr[0];
					
					jarlist = extractJarPaths(optionStr[1]);
					for(String jarfile : jarlist)
					{
						try {
							File afile = new File(jarfile);
							
							if(afile.isAbsolute())
							{
								//System.out.println(jarfile);
								jarClassList.addAll(classFileReadFromJar(jarfile));
							}else {
								String tempfile = useDir+File.separator+jarfile;
								jarClassList.addAll(classFileReadFromJar(tempfile));
							}
						} catch (IOException ioe) {
							ioe.printStackTrace(); // 또는 사용자에게 오류 메시지 출력
						}
						
					}
				}else
				{
					jarClassList.clear();
				}
				//System.out.println(jarlist);
				//System.out.println(jarClassList);
				String loadText = codeArea2.getText();
				// 여기서 원하는 이벤트 실행
				onInitialText(loadText);
				optionStage.close();
				
			});
			
			cancelButton.setOnAction(oe -> {
				// 필요한 작업 수행 후 창 닫기
				optionStage.close();
				
			});
			HBox confirmBox = new HBox(10, okButton, cancelButton);
			confirmBox.setAlignment(Pos.CENTER_RIGHT);
			
			// 전체 구성
			root.getChildren().addAll(
				useCheckBox,
				saveLoadBox,
				locationBox,
				javacLabel,
				javacArg1Box,
				javacArg2Box,
				javacMainBox,
				javaLabel,
				javaArg1Box,
				javaArg2Box,
				javaMainBox,
				confirmBox
				);
			
			Scene scene = new Scene(root, 600, 400);
			optionStage.setScene(scene);
			optionStage.show();
		});
		jarTmpStr[0] = "";
		jarTmpStr[1] = "test.jar";
		jarTmpStr[2] = "1.0";
		jarTmpStr[3] = "";
		jarTmpStr[4] = "";
		jarTmpStr[5] = "";
		jarTmpStr[6] = "class파일";
		jarTmpStr[7] = "true";
		jarTmpStr[8] = "java";
		jarTmpStr[9] = "";
		
		jarButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			// 팝업용 Stage 생성
			Stage popupStage = new Stage();
			popupStage.setTitle("JAR실행");
			popupStage.initModality(Modality.APPLICATION_MODAL);
			VBox root = new VBox(10);
			root.setPadding(new Insets(15));
			// 1) JAR 생성 버튼
			
			TextField cmdField = new TextField();//밑에 있으면 컴파일 안되서 올렸음...
			Button createJarBtn = new Button("JAR 생성기");
			Button excuteJarBtn = new Button("JAR 실행");
			
			createJarBtn.setOnAction(je -> {
				
				jarTmpStr = jarCreater.jarTmpStr;
				jarTmpList = jarCreater.jarTmpList;
				
				jarCreater.Showing(true);
				jarCreater.setInitialData(jarTmpStr,jarTmpList);
			});
			excuteJarBtn.setOnAction(je -> {
				String excuteStr = cmdField.getText();
				
				try {
					// 공백 기준으로 명령어 분리
					// 예: ["java", "-jar", "D:/경로/경로.jar"]
					List<String> cmdList = new ArrayList<>();
					
					// 따옴표 포함된 jar 경로 처리
					// "java -jar "D:/경로/경로.jar""
					String[] parts = excuteStr.split(" ");
					
					for (int i = 0; i < parts.length; i++) {
						if (parts[i].startsWith("\"")) {
							// 경로가 " 로 시작하면 끝날 때까지 합침
							StringBuilder sb = new StringBuilder(parts[i]);
							while (!parts[i].endsWith("\"")) {
								i++;
								sb.append(" ").append(parts[i]);
							}
							cmdList.add(sb.toString().replace("\"", "")); // 따옴표 제거
						} else {
							cmdList.add(parts[i]);
						}
					}
					
					// ProcessBuilder 실행
					ProcessBuilder pb = new ProcessBuilder(cmdList);
					
					if(parts[0].trim().equals("java"))
					{
						pb = new ProcessBuilder("cmd.exe", "/c", "start", "cmd.exe", "/c", excuteStr);
					}
					pb.redirectErrorStream(true); // 에러도 출력으로 합침
					
					Process process = pb.start();
					
					// 실행 결과 출력 (콘솔로)
					new Thread(() -> {
						try (BufferedReader br = new BufferedReader(
								new InputStreamReader(process.getInputStream(), "UTF-8"))) {
							
							String line;
							while ((line = br.readLine()) != null) {
								//System.out.println("[JAR] " + line);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}).start();
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
			
			HBox jarBtnBox = new HBox(10,excuteJarBtn, createJarBtn);
			jarBtnBox.setAlignment(Pos.CENTER_LEFT);
			// 2) 라디오 버튼 (java / javaw)
			ToggleGroup tg = new ToggleGroup();
			RadioButton rbJava = new RadioButton("java");
			RadioButton rbJavaw = new RadioButton("javaw");
			rbJava.setToggleGroup(tg);
			rbJavaw.setToggleGroup(tg);
			rbJava.setSelected(true);
			tg.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
				if (newToggle != null) {
					RadioButton oldsel = (RadioButton) oldToggle;
					RadioButton selected = (RadioButton) newToggle;
					//System.out.println("선택된 실행 방식: " + selected.getText());
					String seltgStr = cmdField.getText();
					switch(selected.getText())
					{
						case "java":
						// 현재 명령이 javaw 로 시작하면 java 로 변경
						if (seltgStr.startsWith("javaw")) {
							seltgStr = seltgStr.replaceFirst("javaw", "java");
							cmdField.setText(seltgStr);
						}
						break;
						case "javaw":
						// 현재 명령이 java 로 시작하면 javaw 로 변경
						if (seltgStr.startsWith("java")) {
							seltgStr = seltgStr.replaceFirst("java", "javaw");
							cmdField.setText(seltgStr);
						}
						break;
						default:
						
						break;
					}
					// 예: 선택된 값에 따라 내부 변수 변경
					// runType = selected.getText();  // "java" 또는 "javaw"
				}
			});
			HBox radioBox = new HBox(5, rbJava, rbJavaw);
			
			// 3) 실행명령 입력부 (Label + TextField + jar 버튼)
			Label cmdLabel = new Label("실행명령 : ");
			
			Button jarPathBtn = new Button("jar");
			jarPathBtn.setOnAction(je -> {
				
				// FileChooser 생성
				FileChooser chooser = new FileChooser();
				chooser.setTitle("JAR 파일 선택");
				
				// 초기 디렉토리 설정
				if (fileDir != null) {
					chooser.setInitialDirectory(new File(fileDir));
				}
				
				// jar 파일만 보이도록 필터 추가
				chooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("JAR Files", "*.jar")
					);
				
				// 파일 열기
				File selectedFile = chooser.showOpenDialog(popupStage);
				
				if (selectedFile != null) {
					
					// 선택된 라디오버튼 가져오기
					Toggle selectedToggle = tg.getSelectedToggle();
					String runType = "java"; // 기본값
					
					if (selectedToggle != null) {
						RadioButton rb = (RadioButton) selectedToggle;
						runType = rb.getText();   // "java" 또는 "javaw"
					}
					
					// 실행명령 구성
					String cmd = runType + " -jar \"" + selectedFile.getAbsolutePath() + "\"";
					
					// TextField에 적용
					cmdField.setText(cmd);
				}
			});
			HBox cmdBox = new HBox(10, cmdLabel, cmdField, jarPathBtn);
			cmdBox.setAlignment(Pos.CENTER_LEFT);
			
			Button okBtn = new Button("확인");
			Button cancelBtn = new Button("취소");
			if(jarTmpStr[8].equals("javaw"))
			{
				rbJavaw.setSelected(true);
			}else
			{
				rbJava.setSelected(true);
			}
			cmdField.setText(jarTmpStr[9]);
			okBtn.setOnAction(je -> {
				
				jarTmpStr = jarCreater.jarTmpStr;
				jarTmpList = jarCreater.jarTmpList;
				
				jarTmpStr[8] = rbJava.isSelected() ? "java" : "javaw";
				jarTmpStr[9] = cmdField.getText();
				
				popupStage.close();
			});
			cancelBtn.setOnAction(je -> {
				popupStage.close();
			});
			HBox okBox = new HBox(10,okBtn,cancelBtn);
			okBox.setAlignment(Pos.CENTER_LEFT);
			// 전체 구성
			root.getChildren().addAll(jarBtnBox, radioBox, cmdBox,okBox);
			
			Scene scene = new Scene(root, 350, 200);
			popupStage.setScene(scene);
			popupStage.show();
		});
		exlsButton.setOnAction(e -> {
			
			typeTempStr = "";
			typepos = 0;
			
			if (popup.isShowing()) {
				popup.hide();
			}
			
			// 팝업용 Stage 생성
			Stage popupStage = new Stage();
			popupStage.setTitle("외부리스트");
			popupStage.initModality(Modality.APPLICATION_MODAL);
			
			VBox root = new VBox(10);
			root.setPadding(new Insets(15));
			
			// 라벨
			Label titleLabel = new Label("exls리스트");
			
			// 리스트뷰
			ListView<String> exlsListView = new ListView<>();
			exlsListView.setPrefHeight(250);
			
			// 버튼 4개 (위에서 아래로)
			Button btnCreateAll = new Button("exls모두생성");
			Button btnCreateOne = new Button("exls생성");
			Button btnRename = new Button("이름바꾸기");
			Button btnDelete = new Button("삭제");
			Button btnRenew = new Button("새로고침");
			btnCreateAll.setOnAction(excle -> {
				
				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setTitle("JAR 폴더 선택");
				// 초기 디렉토리 설정
				File initialDir = new File(dirFileSet());
				if (initialDir.exists()) {
					chooser.setInitialDirectory(initialDir);
				}else
				{
					chooser.setInitialDirectory(new File("."));
					fileDir = ".";
				}
				File selectedFolder = chooser.showDialog(popupStage);
				if (selectedFolder == null) {
					return; // 취소
				}
				
				// ExternalList 폴더 생성
				File externalListFolder = new File(System.getProperty("user.dir"),  "ExternalList");
				if (!externalListFolder.exists()) {
					externalListFolder.mkdirs();
				}
				try{
					externalJarCreateAll(selectedFolder.getAbsolutePath());
				}catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
				
				// ListView 갱신: ExternalList 폴더의 exls 파일만 읽기
				exlsListView.getItems().clear();
				
				File[] exlsFiles = externalListFolder.listFiles();
				if (exlsFiles != null) {
					for (File f : exlsFiles) {
						if (f.isFile() && f.getName().toLowerCase().endsWith(".exls")) {
							
							String nameOnly = f.getName().replace(".exls", "");
							exlsListView.getItems().add(nameOnly);
						}
					}
				}
			});
			
			btnCreateOne.setOnAction(excle -> {
				
				FileChooser chooser = new FileChooser();
				chooser.setTitle("JAR 파일 선택");
				
				// 초기 디렉토리 설정
				File initialDir = new File(dirFileSet());
				if (initialDir.exists()) {
					chooser.setInitialDirectory(initialDir);
				} else {
					chooser.setInitialDirectory(new File("."));
					fileDir = ".";
				}
				
				// jar 필터
				chooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("JAR Files", "*.jar")
					);
				
				File selectedJar = chooser.showOpenDialog(popupStage);
				if (selectedJar == null) {
					return; // 취소
				}
				
				try {
					// jar → exls 생성 (앱폴더/ExternalList/ 에 자동 생성됨)
					externalJarCreate(selectedJar.getAbsolutePath());
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				
				// EXLS 리스트 갱신
				exlsListView.getItems().clear();
				
				// 앱 실행 폴더 기준 ExternalList 폴더
				File externalListFolder = new File(System.getProperty("user.dir"), "ExternalList");
				
				File[] exlsFiles = externalListFolder.listFiles();
				if (exlsFiles != null) {
					for (File f : exlsFiles) {
						if (f.isFile() && f.getName().toLowerCase().endsWith(".exls")) {
							
							String nameOnly = f.getName().replace(".exls", "");
							exlsListView.getItems().add(nameOnly);
						}
					}
				}
			});
			
			btnRename.setOnAction(excle -> {
				
				// 리스트에서 선택한 항목 가져오기
				String selectedName = exlsListView.getSelectionModel().getSelectedItem();
				if (selectedName == null) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("알림");
					alert.setHeaderText(null);
					alert.setContentText("리스트를 선택해주세요.");
					alert.showAndWait();
					return; // 선택 안 했으면 아무것도 안 함
				}
				
				// 다이얼로그 Stage
				Stage renameStage = new Stage();
				renameStage.setTitle("이름바꾸기");
				renameStage.initModality(Modality.APPLICATION_MODAL);
				
				VBox renameroot = new VBox(10);
				renameroot.setPadding(new Insets(15));
				
				Label label = new Label("새 이름을 입력하세요:");
				
				TextField nameField = new TextField();
				nameField.setText(selectedName); // 기존 이름을 기본값으로 넣기
				
				HBox btnBox = new HBox(10);
				
				Button btnOk = new Button("바꾸기");
				Button btnCancel = new Button("취소");
				
				btnBox.getChildren().addAll(btnOk, btnCancel);
				
				renameroot.getChildren().addAll(label, nameField, btnBox);
				
				Scene scene = new Scene(renameroot, 300, 150);
				renameStage.setScene(scene);
				renameStage.show();
				
				// 취소 버튼
				btnCancel.setOnAction(ev -> renameStage.close());
				
				// 바꾸기 버튼
				btnOk.setOnAction(ev -> {
					
					String newName = nameField.getText().trim();
					if (newName.isEmpty()) {
						return;
					}
					
					// ExternalList 폴더 (앱 실행 폴더 기준)
					File externalListFolder = new File(System.getProperty("user.dir"), "ExternalList");
					
					// 기존 파일
					File oldFile = new File(externalListFolder, selectedName + ".exls");
					
					// 새 파일
					File newFile = new File(externalListFolder, newName + ".exls");
					
					// 파일 이름 변경
					if (oldFile.exists()) {
						oldFile.renameTo(newFile);
					}
					
					// 리스트뷰 갱신
					exlsListView.getItems().clear();
					
					File[] exlsFiles = externalListFolder.listFiles();
					if (exlsFiles != null) {
						for (File f : exlsFiles) {
							if (f.isFile() && f.getName().toLowerCase().endsWith(".exls")) {
								String nameOnly = f.getName().replace(".exls", "");
								exlsListView.getItems().add(nameOnly);
							}
						}
					}
					
					renameStage.close();
				});
			});
			
			btnDelete.setOnAction(excle -> {
				
				// 리스트에서 선택한 항목 가져오기
				String selectedName = exlsListView.getSelectionModel().getSelectedItem();
				if (selectedName == null) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("알림");
					alert.setHeaderText(null);
					alert.setContentText("리스트를 선택해주세요.");
					alert.showAndWait();
					return;
				}
				
				// ExternalList 폴더 (앱 실행 폴더 기준)
				File externalListFolder = new File(System.getProperty("user.dir"), "ExternalList");
				
				// 삭제할 파일
				File deleteFile = new File(externalListFolder, selectedName + ".exls");
				
				if (deleteFile.exists()) {
					deleteFile.delete();
				}
				
				// 리스트뷰 갱신
				exlsListView.getItems().clear();
				
				File[] exlsFiles = externalListFolder.listFiles();
				if (exlsFiles != null) {
					for (File f : exlsFiles) {
						if (f.isFile() && f.getName().toLowerCase().endsWith(".exls")) {
							String nameOnly = f.getName().replace(".exls", "");
							exlsListView.getItems().add(nameOnly);
						}
					}
				}
			});
			
			btnRenew.setOnAction(excle ->{
				// ExternalList 폴더 생성
				File externalListFolder = new File(System.getProperty("user.dir"),  "ExternalList");
				if (!externalListFolder.exists()) {
					externalListFolder.mkdirs();
				}
				
				
				// ListView 갱신: ExternalList 폴더의 exls 파일만 읽기
				exlsListView.getItems().clear();
				
				File[] exlsFiles = externalListFolder.listFiles();
				if (exlsFiles != null) {
					for (File f : exlsFiles) {
						if (f.isFile() && f.getName().toLowerCase().endsWith(".exls")) {
							
							String nameOnly = f.getName().replace(".exls", "");
							exlsListView.getItems().add(nameOnly);
						}
					}
				}
			});
			VBox btnBox = new VBox(8);
			btnBox.getChildren().addAll(btnCreateAll, btnCreateOne, btnRename, btnDelete, btnRenew);
			
			// 리스트 + 버튼을 가로로 배치
			HBox centerBox = new HBox(10);
			centerBox.getChildren().addAll(exlsListView, btnBox);
			
			// 확인 버튼
			Button okBtn = new Button("확인");
			okBtn.setOnAction(ev -> {
				try{
					exlsListAddAll();
				}catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
				popupStage.close();
				String newText = codeArea2.getText();
				onInitialText(newText);
			});
			
			// 전체 배치
			root.getChildren().addAll(titleLabel, centerBox, okBtn);
			
			Scene scene = new Scene(root, 450, 350);
			popupStage.setScene(scene);
			popupStage.show();
			
			//리스트 초기화...
			// ExternalList 폴더 생성
			File externalListFolder = new File(System.getProperty("user.dir"),  "ExternalList");
			if (!externalListFolder.exists()) {
				externalListFolder.mkdirs();
			}
			
			
			// ListView 갱신: ExternalList 폴더의 exls 파일만 읽기
			
			exlsListView.getItems().clear();
			
			File[] exlsFiles = externalListFolder.listFiles();
			if (exlsFiles != null) {
				for (File f : exlsFiles) {
					if (f.isFile() && f.getName().toLowerCase().endsWith(".exls")) {
						
						String nameOnly = f.getName().replace(".exls", "");
						exlsListView.getItems().add(nameOnly);
					}
				}
			}
		});
		
		styleButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			// 팝업용 Stage 생성
			Stage popupStage = new Stage();
			popupStage.setTitle("스타일 설정");
			popupStage.initModality(Modality.APPLICATION_MODAL);
			VBox root = new VBox(10);
			root.setPadding(new Insets(15));
			
			// 체크박스
			CheckBox consoleVCheck = new CheckBox("콘솔입력 보이기");
			CheckBox popupCheck = new CheckBox("팝업리스트 사용하기");
			CheckBox styleCheck = new CheckBox("스타일 사용하기");
			
			// 공통 색상 옵션
			ObservableList<String> colors = FXCollections.observableArrayList("빨간색", "파란색", "녹색", "핑크색", "보라색","노란색","하늘색","주황색","갈색","회색","검은색","하얀색");
			ObservableList<String> fonts = FXCollections.observableArrayList("Consolas","Serif","Monospace","cursive","fantasy");
			ObservableList<String> fontSizes = FXCollections.observableArrayList("14","18","22","26","30","34");
			// Style 라벨
			Label styleLabel = new Label("Style : ");
			Label fontLabel = new Label("폰트 : ");
			ComboBox<String> fontCombo = new ComboBox<>(fonts);
			
			Label fontSizeLabel = new Label("글자크기 : ");
			ComboBox<String> fontSizeCombo = new ComboBox<>(fontSizes);
			// 커서
			Label caretLabel = new Label("커서 : ");
			ComboBox<String> caretCombo = new ComboBox<>(colors);
			// 백그라운드
			Label bgLabel = new Label("백그라운드 : ");
			ComboBox<String> bgCombo = new ComboBox<>(colors);
			// 일반
			Label normalLabel = new Label("노말 : ");
			ComboBox<String> normalCombo = new ComboBox<>(colors);
			// 키워드
			Label keywordLabel = new Label("키워드 : ");
			ComboBox<String> keywordCombo = new ComboBox<>(colors);
			
			// 클래스
			Label classLabel = new Label("클래스 : ");
			ComboBox<String> classCombo = new ComboBox<>(colors);
			
			// 숫자
			Label numberLabel = new Label("숫자 : ");
			ComboBox<String> numberCombo = new ComboBox<>(colors);
			
			// 문자
			Label charLabel = new Label("문자 : ");
			ComboBox<String> charCombo = new ComboBox<>(colors);
			
			// 코멘트
			Label commentLabel = new Label("코맨트 : ");
			ComboBox<String> commentCombo = new ComboBox<>(colors);
			// 색상 매핑
			Map<String, String> colorMap = new HashMap<>();
			colorMap.put("blue", "파란색");
			colorMap.put("red", "빨간색");
			colorMap.put("purple", "보라색");
			colorMap.put("deeppink", "핑크색");
			colorMap.put("green", "녹색");
			colorMap.put("yellow", "노란색");
			colorMap.put("skyblue", "하늘색");
			colorMap.put("orange", "주황색");
			colorMap.put("brown", "갈색");
			colorMap.put("darkgray", "회색");
			colorMap.put("black", "검은색");
			colorMap.put("white", "하얀색");
			
			try {
				List<String> lines = Files.readAllLines(Paths.get("src/main/resources/style.css"), StandardCharsets.UTF_8);
				List<String> bglines = Files.readAllLines(Paths.get("src/main/resources/bgstyle.css"), StandardCharsets.UTF_8);
				lines.addAll(bglines);
				String currentClass = null;
				
				
				
				for (String raw : lines) {
					String line = raw.trim();
					
					// Start of a class block
					if (line.startsWith(".") && line.contains("{")) {
						int dot = line.indexOf('.');
						int brace = line.indexOf('{');
						currentClass = line.substring(dot + 1, brace).trim();
						continue;
					}
					
					if (currentClass != null) {
						if (line.startsWith("-fx-fill:") || line.startsWith("-fx-background-color:") || line.startsWith("-fx-stroke:")) {
							String cssColor = line.substring("-fx-fill:".length()).replace(";", "").trim().toLowerCase();
							if(currentClass.equals("code-area"))
							{
								
								cssColor = line.substring("-fx-background-color:".length()).replace("!important;", "").trim().toLowerCase();
								
							}
							if(currentClass.equals("code-area .caret"))
							{
								
								cssColor = line.substring("-fx-stroke:".length()).replace("!important;", "").trim().toLowerCase();
								
							}
							String comboColor = colorMap.get(cssColor);
							//System.out.println("match: " + currentClass + " -> " + cssColor + " -> " + comboColor);
							
							if (comboColor != null) {
								switch (currentClass) {
									case "keyword":     keywordCombo.setValue(comboColor); break;
									case "keyword2":    classCombo.setValue(comboColor);   break;
									case "numberic":    numberCombo.setValue(comboColor);  break;
									case "wordsric":    charCombo.setValue(comboColor);    break;
									case "commentric":  commentCombo.setValue(comboColor); break;
									case "code-area":  bgCombo.setValue(comboColor); break;
									case "code-area .caret":  caretCombo.setValue(comboColor); break;
									case "normala":  normalCombo.setValue(comboColor); break;
								}
							}
						} else if (line.equals("}")) {
							currentClass = null; // end block
						}
					}
				}
				lines = Files.readAllLines(Paths.get("src/main/resources/StyleOption.soption"), StandardCharsets.UTF_8);
				for(String line : lines)
				{
					String[] parts = line.split("=");
					if (parts.length == 2) {
						String key = parts[0].trim();
						String value = parts[1].trim();
						switch (key) {
							case "Console":
							if(value.equalsIgnoreCase("yes"))
							{
								useConsoleV = true;
							}else if(value.equalsIgnoreCase("no"))
							{
								useConsoleV = false;
							}else {
								useConsoleV = true;
							}
							break;
							case "Style":
							if(value.equalsIgnoreCase("yes"))
							{
								useStyle = true;
							}else if(value.equalsIgnoreCase("no"))
							{
								useStyle = false;
							}else {
								useStyle = true;
							}
							break;
							case "Popup":
							if(value.equalsIgnoreCase("yes"))
							{
								usePopup = true;
							}else if(value.equalsIgnoreCase("no"))
							{
								usePopup = false;
							}else {
								usePopup = true;
							}
							break;
							case "Font":
							fontCombo.setValue(value);
							break;
							case "FontSize":
							fontSizeCombo.setValue(value);
							break;
						}
					}
					
				}
				
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			// 버튼
			Button okButton = new Button("확인");
			Button cancelButton = new Button("취소");
			okButton.setOnAction(oe -> {
				useConsoleV = consoleVCheck.isSelected();
				useStyle = styleCheck.isSelected();
				usePopup = popupCheck.isSelected();
				
				//폰트사이즈와 폰트 저장...
				String styleTempStr = "-fx-font-family: \'" + fontCombo.getValue() + "\'; -fx-font-size: "+ fontSizeCombo.getValue()+ "px;";
				codeArea2.setStyle(styleTempStr);
				StringBuilder styleSaveStr = new StringBuilder();
				
				styleSaveStr.append("Console = ");
				if(useConsoleV)
				{
					styleSaveStr.append("yes\n");
				}else {
					styleSaveStr.append("no\n");
				}
				styleSaveStr.append("Style = ");
				if(useStyle)
				{
					styleSaveStr.append("yes\n");
				}else {
					styleSaveStr.append("no\n");
				}
				styleSaveStr.append("Popup = ");
				if(usePopup)
				{
					styleSaveStr.append("yes\n");
				}else {
					styleSaveStr.append("no\n");
				}
				
				
				
				styleSaveStr.append("Font = ");
				styleSaveStr.append(fontCombo.getValue());
				styleSaveStr.append("\n");
				styleSaveStr.append("FontSize = ");
				styleSaveStr.append(fontSizeCombo.getValue());
				styleSaveStr.append("\n");
				
				try {
					Files.write(Paths.get("src/main/resources/StyleOption.soption"), Collections.singleton(styleSaveStr.toString()), StandardCharsets.UTF_8 );
					
					
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				
				Map<String, String> reverseColorMap = new HashMap<>();
				reverseColorMap.put("빨간색", "red");
				reverseColorMap.put("파란색", "blue");
				reverseColorMap.put("녹색", "green");
				reverseColorMap.put("핑크색", "deeppink");
				reverseColorMap.put("보라색", "purple");
				reverseColorMap.put("노란색", "yellow");
				reverseColorMap.put("하늘색", "skyblue");
				reverseColorMap.put("주황색", "orange");
				reverseColorMap.put("갈색", "brown");
				reverseColorMap.put("회색", "darkgray");
				reverseColorMap.put("검은색", "black");
				reverseColorMap.put("하얀색", "white");
				String cssContent =
				".keyword {\n" +
				"    -fx-fill: " + reverseColorMap.get(keywordCombo.getValue()) + ";\n" +
				"    -fx-font-weight: bold;\n" +
				"}\n" +
				".keyword2 {\n" +
				"    -fx-fill: " + reverseColorMap.get(classCombo.getValue()) + ";\n" +
				"    -fx-font-weight: bold;\n" +
				"}\n" +
				".numberic {\n" +
				"    -fx-fill: " + reverseColorMap.get(numberCombo.getValue()) + ";\n" +
				"}\n" +
				".wordsric {\n" +
				"    -fx-fill: " + reverseColorMap.get(charCombo.getValue()) + ";\n" +
				"}\n" +
				".commentric {\n" +
				"    -fx-fill: " + reverseColorMap.get(commentCombo.getValue()) + ";\n" +
				"}\n" + ".highlight-char {\n    -fx-fill: red;\n    -rtfx-background-color: yellow;\n" +
				"}\n.normala {\n	-fx-fill: " + reverseColorMap.get(normalCombo.getValue()) + ";\n}";
				String bgcssContent =
				".code-area {\n" +
				"	-fx-background-color: " + reverseColorMap.get(bgCombo.getValue()) + " !important;\n}\n" +
				".code-area .virtual-flow {\n" +
				"	-fx-background-color: " + reverseColorMap.get(bgCombo.getValue()) + " !important;\n}\n" +
				".code-area .virtual-flow .viewport {\n" +
				"	-fx-background-color: " + reverseColorMap.get(bgCombo.getValue()) + " !important;\n}\n" +
				".code-area .virtual-flow .cell {\n" +
				"	-fx-background-color: " + reverseColorMap.get(bgCombo.getValue()) + " !important;\n}\n" +
				".code-area .caret {\n" +
				"	-fx-stroke: " + reverseColorMap.get(caretCombo.getValue()) + " !important;\n" +
				"	-fx-fill: " + reverseColorMap.get(caretCombo.getValue()) + " !important;\n}\n";
				
				try {
					Files.write(Paths.get("src/main/resources/style.css"),cssContent.getBytes(StandardCharsets.UTF_8));
					Files.write(Paths.get("src/main/resources/bgstyle.css"),bgcssContent.getBytes(StandardCharsets.UTF_8));
					//System.out.println("style.css 저장 완료!");
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				// 기존 스타일시트 제거
				mainscene.getStylesheets().clear();
				// 다시 추가 (갱신된 style.css 반영)
				
				mainscene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // 스타일시트 연결 (선택)
				codeArea2.getStylesheets().clear();
				codeArea2.getStylesheets().add("bgstyle.css");
				// CSS 강제 적용
				mainscene.getRoot().applyCss();
				
				popupStage.close(); // 팝업 닫기
				if(useStyle)
				{
					onInitialText(codeArea2.getText());
				}
				
				
				
				
			});
			cancelButton.setOnAction(ce -> {
				popupStage.close(); // 팝업 닫기
				
			});
			HBox buttonBox = new HBox(10, okButton, cancelButton);
			buttonBox.setAlignment(Pos.CENTER_LEFT);
			//코드 작성 추가...
			consoleVCheck.setSelected(useConsoleV);
			popupCheck.setSelected(usePopup); // 기본으로 체크됨
			styleCheck.setSelected(useStyle); // 기본으로 체크됨
			
			// 레이아웃에 추가
			root.getChildren().addAll(
				consoleVCheck,
				popupCheck,
				styleCheck,
				styleLabel,
				new HBox(10, fontLabel , fontCombo),
				new HBox(10, fontSizeLabel, fontSizeCombo),
				new HBox(10, caretLabel,caretCombo),
				new HBox(10, bgLabel,bgCombo),
				new HBox(10, normalLabel, normalCombo),
				new HBox(10, keywordLabel, keywordCombo),
				
				new HBox(10, classLabel, classCombo),
				new HBox(10, numberLabel, numberCombo),
				new HBox(10, charLabel, charCombo),
				new HBox(10, commentLabel, commentCombo),
				buttonBox
				);
			
			Scene scene = new Scene(root, 300, 500);
			popupStage.setScene(scene);
			popupStage.show();
			
		});
		informButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			// 새 창(Stage) 생성
			Stage infoStage = new Stage();
			infoStage.setTitle("제품 정보");
			
			// 라벨
			Label label = new Label("제품 정보 :");
			
			// 텍스트 영역
			TextArea textArea = new TextArea();
			textArea.setEditable(false); // 🔒 텍스트 수정 불가
			
			ScrollPane textScrollPane = new ScrollPane(textArea);
			textScrollPane.setFitToWidth(true);
			textScrollPane.setFitToHeight(true);
			textArea.appendText("만든이 : 컴퓨터판타지연구소\n");
			textArea.appendText("앤트자바에디터\n");
			textArea.appendText("앱 이름 : " + ver.app_Name + "\n");
			textArea.appendText("앱 버전 : " + ver.app_Version + "\n");
			textArea.appendText("앱 코드 : " + ver.app_Code + "\n");
			textArea.appendText("사용언어 : java 21이상\n");
			textArea.appendText("아파치2.0 라이센스를 사용합니다.\n");
			textArea.appendText("다운로드 : http://javafantasy.kr\n");
			textArea.appendText("탭으로 여러작업을 동시에 할 수 있습니다.\n");
			textArea.appendText("이정도면 작품성을 인정받을 수 있습니다.\n");
			textArea.appendText("거의 UI가 완벽하다고 볼 수 있습니다.\n");
			textArea.appendText("이 앱으로 인한 스트레스가 많이 줄었습니다.\n");
			textArea.appendText("콘솔창 영어입력추가되었습니다.\n");
			textArea.appendText("스타일 색깔 다양하게 했습니다.\n");
			textArea.appendText("어느정도 JAVA CODE 작업을 할 수 있습니다.\n");
			textArea.appendText("범위접기는 시도했으나, 확실히 포기합니다.\n");
			textArea.appendText("코드문제로 무겁고 속도가 느렸으나, 해결했습니다.\n");
			textArea.appendText("창 여러개 실행 시, 자바파일 저장 문제 수정했습니다.\n");
			textArea.appendText("스크롤바가 위로 올라가는 문제 수정했고,확인도 했습니다.\n");
			textArea.appendText("stop을 누르면 java실행한것 모두 종료됩니다.\n");
			textArea.appendText("종료를 해도 java실행한 것 모두 종료됩니다.\n");
			textArea.appendText("탭정리도 추가하였습니다.\n");
			textArea.appendText("이 앱 코드수는 모두 합쳐서 8500줄입니다.\n");
			textArea.appendText("계속 오류가 발생되지만, 계속 오류를 찾아서 업데이트하겠습니다.\n");
			textArea.appendText("이 앱은 제가 보기에는 작품입니다. 작품전시회가 있으면 참여를 할 생각입니다.\n");
			textArea.appendText("윈도우뿐만 아니라 리눅스에도 실행이 되도록 업데이트하겠습니다.\n");
			textArea.setPrefRowCount(5);
			textArea.setPrefColumnCount(30);
			Platform.runLater(() -> {
				textArea.setScrollTop(0);   // 맨 위로
			});
			
			// 확인 버튼
			Button okButton = new Button("확인");
			okButton.setOnAction(event -> infoStage.close());
			
			// 레이아웃 구성
			VBox layout = new VBox(10); // 10은 컴포넌트 간 간격
			layout.setPadding(new Insets(10));
			layout.getChildren().addAll(label, textScrollPane, okButton);
			
			// 씬 설정 및 창 띄우기
			Scene scene = new Scene(layout);
			infoStage.setScene(scene);
			infoStage.initModality(Modality.APPLICATION_MODAL); // 모달 창으로 설정 (메인 창 클릭 막기)
			infoStage.showAndWait(); // 창이 닫힐 때까지 대기
		});
		gcButton.setOnAction(e -> {
			System.gc();
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("GC 실행");
			alert.setHeaderText(null);
			alert.setContentText("가비지 컬렉션이 실행되었습니다.");
			alert.showAndWait();
			
		});
		exitButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("프로그램 종료");
			alert.setHeaderText("정말로 종료하시겠습니까?");
			alert.setContentText("확인을 누르면 프로그램이 종료됩니다.");
			
			// 확인 / 취소 버튼 추가
			ButtonType okButton = new ButtonType("확인", ButtonBar.ButtonData.OK_DONE);
			ButtonType cancelButton = new ButtonType("취소", ButtonBar.ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().setAll(okButton, cancelButton);
			
			// 결과 처리
			alert.showAndWait().ifPresent(type -> {
				if (type == okButton) {
					// 안전하게 종료
					if(!runProcessList.isEmpty())
					{
						try{
							//System.out.println("런프로세스정상작동중");
							//runProcess.waitFor();
							
							while(!runProcessList.isEmpty())
							{
								Thread.sleep(100); // 3초 기다림
								Process process = runProcessList.poll();
								process.destroy();//정상종료
							}
							runProcessList.clear();
							
							
							//runProcess.destroyForcibly(); // 강제 종료 (필요할 때만)
						}catch(Exception ffe)
						{
							ffe.printStackTrace();
						}
					}
					List<Tab> tabcopy = new ArrayList<>(tabPane.getTabs());
					List<String> pathlist = new ArrayList<>();
					for (Tab tab : tabcopy) {
						TabInfo info = (TabInfo) tab.getUserData();
						if (info == null || info.file == null) {
							// Welcome 탭이거나 초기화되지 않은 탭
							System.exit(0);
						}
						String welcomes = new File("").getAbsolutePath() + File.separator + "Welcome.txt";
						if(!welcomes.equals(info.file.getAbsolutePath()))
						{
							pathlist.add(info.file.getAbsolutePath());
						}
						if (info != null && info.modified) {
							//tabPane.getSelectionModel().select(tab);
							String currentCode = codeArea2.getText();
							Tab closingTab = tab;   // ← 닫히는 탭
							TabInfo info2 = (TabInfo) closingTab.getUserData();
							if (info.modified) {
								
								Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
								alert2.setTitle("파일 저장");
								alert2.setHeaderText(null);
								alert2.setContentText("파일 '" + info.fileName + "' 을(를) 저장하시겠습니까?");
								
								ButtonType saveBtn = new ButtonType("확인");
								ButtonType cancelBtn = new ButtonType("취소", ButtonBar.ButtonData.CANCEL_CLOSE);
								cancelBtn = new ButtonType("취소");
								alert2.getButtonTypes().setAll(saveBtn, cancelBtn);
								
								Optional<ButtonType> result = alert2.showAndWait();
								
								if (result.isPresent() && result.get() == saveBtn) {
									// 확인 눌렀을 때 → 저장은 네가 처리
									// 예: saveFile(info);
									typeTempStr = "";
									typepos = 0;
									if (popup.isShowing()) {
										
										popup.hide();
									}
									//System.gc();
									File selectedFile;
									String selectedEncoding;
									if(fileName.isEmpty())
									{
										FileChooser fileChooser = new FileChooser();
										fileChooser.setTitle("Save Java File");
										fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Files", "*.java"));
										File initialDir = new File(dirFileSet());
										if (initialDir.exists()) {
											fileChooser.setInitialDirectory(initialDir);
										}else
										{
											fileChooser.setInitialDirectory(new File("."));
											fileDir = ".";
										}
										/*
										// 인코딩 선택 UI
										List<String> encodings = Arrays.asList("UTF-8", "MS949", "EUC-KR", "ISO-8859-1");
										ChoiceDialog<String> dialog = new ChoiceDialog<>("UTF-8", encodings);
										dialog.setTitle("Select Encoding");
										dialog.setHeaderText("Choose file encoding");
										dialog.setContentText("Encoding:");
										
										Optional<String> result0 = dialog.showAndWait();
										if (!result0.isPresent()) {
										return; // 사용자가 취소
										}
										
										selectedEncoding = result0.get();
										*/
										selectedEncoding = "UTF-8";
										selectedFile = fileChooser.showSaveDialog(null);
									}else{
										String fn = fileDir + File.separator + fileName;
										selectedFile = new File(fn);
										selectedEncoding = file_Encoding;
									}
									if (selectedFile != null) {
										try {
											String rawName = selectedFile.getName();
											// 현재 앱 실행 디렉토리
											String appDir = new File("").getAbsolutePath();
											if(fileDir.equals(appDir) && fileName.equals("Welcome.txt"))
											{
												
											}else if (!rawName.toLowerCase().endsWith(".java")) {
												selectedFile = new File(selectedFile.getParent(), rawName + ".java");
											}
											fileDir = selectedFile.getParent();
											fileName = selectedFile.getName();
											file_Encoding = selectedEncoding;
											modified = false;
											String filepath = "javaEditerPath.txt";
											Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
											// 코드 저장
											String code =  currentCode;
											Charset charset = Charset.forName(selectedEncoding);
											Files.write(selectedFile.toPath(), code.getBytes(charset));
											//pathLabel.setText("경로 : " + selectedFile.getAbsolutePath());
											consoleArea.appendText("저장 완료: " + selectedFile.getAbsolutePath() + "\n");
											
										} catch (Exception ex) {
											consoleArea.appendText("저장 오류: " + ex.getMessage() + "\n");
										}
										
										agoFileList = getAgoFile();
										agoFileList = setAddAgoList(selectedFile.getAbsolutePath(),agoFileList);
										setSaveAgoFile(agoFileList);
									}
								}
							}else{
								
							}
							tabPane.getTabs().remove(closingTab);
						}
					}
					SaveTabPathInfo(pathlist);
					Platform.exit();
					System.exit(0);
				}
			});
			
		});
		
		//0.0032버전에 추가
		eraseButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			consoleArea.setText("");
		});
		
		copyButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			Clipboard clipboard = Clipboard.getSystemClipboard();
			ClipboardContent content = new ClipboardContent();
			content.putString(consoleArea.getText() == null ? "" : consoleArea.getText());
			clipboard.setContent(content);
			
			
		});
		agoButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			Stage dialog = new Stage();
			dialog.setTitle("이전 파일 열기");
			dialog.initModality(Modality.APPLICATION_MODAL); // 모달 창
			
			
			dialog.initOwner(primaryStage); // 메인 창 기준으로 중앙 배치
			agoFileList = getAgoFile();
			
			// ▼ 리스트뷰 생성 (String 리스트)
			ListView<String> fileListView = new ListView<>();
			fileListView.setPrefHeight(450); // 적당한 높이
			for (int i = agoFileList.size() - 1; i >= 0; i--) {
				fileListView.getItems().add(agoFileList.get(i));
			}
			/*//스크롤바테스트
			for(int i =0; i < 30; i++)
			{
			fileListView.getItems().add("tesddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddt");
			}*/
			
			Button openDialogButton = new Button("열기");
			Button delDialogButton = new Button("지우기");
			Button alldelDialogButton = new Button("모두 지우기");
			openDialogButton.setOnAction(ev -> {
				String selected = fileListView.getSelectionModel().getSelectedItem();
				File selectedFile = new File(selected);
				if (selectedFile != null) {
					try {
						// 경로와 파일명 저장
						fileDir = selectedFile.getParent();
						fileName = selectedFile.getName();
						String filepath = "javaEditerPath.txt";
						Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
						/*
						// 파일 내용 읽어서 코드 영역에 표시
						String encoding = EncodingDetector.detectEncoding(selectedFile);
						file_Encoding = encoding;
						StringBuilder content = new StringBuilder();
						BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile), encoding));
						String line;
						while ((line = reader.readLine()) != null) {
						content.append(line).append("\n");
						}
						reader.close();
						codeArea2.replaceText(content.toString());
						*/
						tab_OpenFile(selectedFile);
						String labelStr = "경로 : " +((TabInfo)tabPane.getSelectionModel().getSelectedItem().getUserData()).file.getAbsolutePath()
						+ " | encoding : " + ((TabInfo)tabPane.getSelectionModel().getSelectedItem().getUserData()).encoding;
						
						codeArea2.moveTo(0, 0);
						codeArea2.requestFollowCaret();
						//pathLabel.setText("경로 : " + selectedFile.getAbsolutePath());
						consoleArea.clear();
						consoleArea.appendText("파일 로드 완료: " + ((TabInfo)tabPane.getSelectionModel().getSelectedItem().getUserData()).file.getAbsolutePath() + "\n");
						consoleArea.appendText("인코딩 : " + ((TabInfo)tabPane.getSelectionModel().getSelectedItem().getUserData()).encoding + "\n");
					} catch (Exception ex) {
						consoleArea.appendText("로드 오류: " + ex.getMessage() + "\n");
					}
					String loadText = codeArea2.getText();
					// 여기서 원하는 이벤트 실행
					onInitialText(loadText);
					String appDir = new File("").getAbsolutePath();
					if(fileDir.equals(appDir) && fileName.equals("Welcome.txt"))
					{
						
					}else {
						agoFileList = setAddAgoList(selectedFile.getAbsolutePath(),agoFileList);
						setSaveAgoFile(agoFileList);
					}
					
					codeArea2.getUndoManager().forgetHistory();
					
				}
				dialog.close();
			});
			delDialogButton.setOnAction(ev -> {
				String selected = fileListView.getSelectionModel().getSelectedItem();
				if(selected != null)
				{
					fileListView.getItems().remove(selected); // 화면에서 삭제
					agoFileList.remove(selected);             // 실제 데이터에서도 삭제
					setSaveAgoFile(agoFileList);
					
				}
			});
			alldelDialogButton.setOnAction(ev -> {
				fileListView.getItems().clear();  // ListView 비우기
				agoFileList.clear();
				setSaveAgoFile(agoFileList);
			});
			Button exitDialogButton = new Button("닫기");
			exitDialogButton.setOnAction(ev -> {
				String appDir = new File("").getAbsolutePath();
				if(fileDir.equals(appDir) && fileName.equals("Welcome.txt"))
				{
					
				}else {
					
					setSaveAgoFile(agoFileList);
				}
				dialog.close();
			});
			HBox btnBox = new HBox(10,delDialogButton,alldelDialogButton);
			HBox closeBox = new HBox(10,openDialogButton,exitDialogButton);
			
			VBox root = new VBox(10);
			root.setPadding(new Insets(10));
			root.setAlignment(Pos.CENTER); // ★ 전체 가운데 정렬
			root.getChildren().add(new Label("이전 파일 목록"));
			root.getChildren().add(fileListView);  // ★ 리스트뷰 추가됨
			root.getChildren().add(btnBox);
			root.getChildren().add(closeBox);
			
			Scene scene = new Scene(root, 400, 600);
			dialog.setScene(scene);
			dialog.showAndWait(); // 창을 띄우고 닫힐 때까지 기다림
			
		});
		
		tabCleanButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			// 다이얼로그(Stage)
			Stage dialog = new Stage();
			dialog.setTitle("탭정리");
			dialog.initModality(Modality.APPLICATION_MODAL);
			
			// ProgressBar
			ProgressBar progressBar = new ProgressBar(0);
			progressBar.setPrefWidth(300);
			
			// Label
			int codeLength = tabCleanLength(codeArea2.getText());
			Label progressLabel = new Label("진행상황 : "+ tabClean_progress+"/" + tabClean_len);
			
			// 버튼들
			Button startBtn = new Button("정리하기");
			Button cancelBtn = new Button("취소");
			
			HBox btnBox = new HBox(10, startBtn, cancelBtn);
			btnBox.setAlignment(Pos.CENTER);
			
			VBox root = new VBox(15, progressBar, progressLabel, btnBox);
			root.setAlignment(Pos.CENTER);
			root.setPadding(new Insets(20));
			
			Scene scene = new Scene(root);
			dialog.setScene(scene);
			dialog.show();
			
			// 취소 버튼
			cancelBtn.setOnAction(ev -> dialog.close());
			
			// 정리하기 버튼
			startBtn.setOnAction(ev -> {
				// 클릭하자마자 비활성화
				startBtn.setDisable(true);
				cancelBtn.setDisable(true);
				// 실제 작업을 수행하는 Task
				progressBar.progressProperty().unbind();
				progressBar.setProgress((double) tabClean_progress / tabClean_len);
				int caret = codeArea2.getCaretPosition();
				double scrollX = codeArea2.estimatedScrollXProperty().getValue();
				double scrollY = codeArea2.estimatedScrollYProperty().getValue();
				Task<Void> task = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						
						
						
						Thread tabth = new Thread(() -> {
							tabtmp =  tabCleanMain(codeArea2.getText());
						});
						tabth.start();
						
						while(tabClean_progress<tabClean_len)
						{
							//updateProgress(tabClean_progress, tabClean_len);
							progressBar.setProgress((double) tabClean_progress / tabClean_len);
							Platform.runLater(() -> {
								progressLabel.setText("진행상황 : " + tabClean_progress + "/" + tabClean_len);
							});
							
							//Thread.sleep(1);
						}
						
						
						return null;
					}
				};
				// ProgressBar와 Task 연결
				//progressBar.progressProperty().bind(task.progressProperty());
				
				
				// 작업 완료 시
				task.setOnSucceeded(ev2 -> {
					if(tabClean_progress == tabClean_len)
					{
						progressBar.setProgress((double) tabClean_progress / tabClean_len);
						codeArea2.replaceText(tabtmp);
						
						// 기존 스타일시트 제거
						mainscene.getStylesheets().clear();
						// 다시 추가 (갱신된 style.css 반영)
						
						mainscene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // 스타일시트 연결 (선택)
						codeArea2.getStylesheets().clear();
						codeArea2.getStylesheets().add("bgstyle.css");
						// CSS 강제 적용
						mainscene.getRoot().applyCss();
						Platform.runLater(() -> {
							codeArea2.estimatedScrollXProperty().setValue(scrollX);
							codeArea2.estimatedScrollYProperty().setValue(scrollY);
							if(useStyle)
							{
								String newText = codeArea2.getText();
								codeArea2.setStyleSpans(0, computeHighlighting(newText));
							}
						});
						
						tabClean_progress = 0;
						startBtn.setDisable(false);
						cancelBtn	.setDisable(false);
						
						task.cancel();
						
						// --- 메시지창(Alert) 띄우기 ---
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("확인메세지");
						alert.setHeaderText(null);
						alert.setContentText("정리가 완료되었습니다.");
						
						// 확인 버튼 누르면 dialog 닫기
						alert.showAndWait();
						dialog.close();   // 다이얼로그 닫기
						Platform.runLater(() -> {
							codeArea2.estimatedScrollXProperty().setValue(scrollX);
							codeArea2.estimatedScrollYProperty().setValue(scrollY);
							
						});
						
						typeTempStr = "";
						typepos = 0;
						if (popup.isShowing()) {
							
							popup.hide();
						}
						
						
					}
				});
				
				// 백그라운드 실행
				Thread th = new Thread(task);
				th.setDaemon(true);
				th.start();
				
				
				
				
				
				
			});
		});
		
		tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			// oldTab이 null이 아닐 때만 저장
			isSwitchingTab = true;  // 🔥 탭 전환 시작
			if (oldTab != null) {
				TabInfo oldInfo = (TabInfo) oldTab.getUserData();
				oldInfo.content = codeArea2.getText();
				oldInfo.pos= codeArea2.getCaretPosition();   // ← 커서 위치 저장
				oldInfo.scrollX = codeArea2.estimatedScrollXProperty().getValue();
				oldInfo.scrollY = codeArea2.estimatedScrollYProperty().getValue();
			}
			
			// newTab이 null이 아닐 때만 로드
			if (newTab != null) {
				antTab = newTab;
				//System.out.println("test : " + ((TabInfo)antTab.getUserData()).fileName);
				TabInfo newInfo = (TabInfo) newTab.getUserData();
				codeArea2.replaceText(newInfo.content);
				if(newInfo.file != null)
				{
					pathLabel.setText("경로 : " + newInfo.file.getAbsolutePath()
						+ " | encoding : " + newInfo.encoding);
				}else{
					pathLabel.setText("경로 : " + " | encoding : " );
				}
				
				String loadText = codeArea2.getText();
				// 여기서 원하는 이벤트 실행
				onInitialText(loadText);
				codeArea2.moveTo(newInfo.pos);
				Platform.runLater(() -> {
					codeArea2.estimatedScrollXProperty().setValue(newInfo.scrollX);
					codeArea2.estimatedScrollYProperty().setValue(newInfo.scrollY);
				});
				codeArea2.requestFollowCaret();
				codeArea2.getUndoManager().forgetHistory();
			}
			SaveInfoFile();
			isSwitchingTab = false; // 🔥 탭 전환 끝
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
		});
		
		networkButton.setOnAction(e -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			Stage stage = new Stage();
			stage.setTitle("코드전송");
			stage.initModality(Modality.APPLICATION_MODAL);
			// 최상위 VBox
			VBox rootBox = new VBox(10);
			rootBox.setPadding(new Insets(10));
			
			// -------------------------
			// 1) 첫 번째 HBox: Label + Receive 버튼
			// -------------------------
			HBox hbox1 = new HBox(10);
			Label ipconfigLabel = new Label("IP :");
			Button receiveButton = new Button("Receive");
			hbox1.getChildren().addAll(ipconfigLabel, receiveButton);
			try {
				URL url = new URL("https://api.ipify.org");
				BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
				String publicIP = br.readLine();
				//System.out.println(publicIP);
				ipconfigLabel.setText("IP : " + publicIP + " port : 60000");
			}catch (IOException networke)
			{
				networke.printStackTrace();
			}
			receiveButton.setOnAction( ne -> {
				String state = receiveButton.getText();
				if(state.equals("Receive"))
				{
					rtmpstate = "Stop";
					receiveButton.setText(rtmpstate);
					new Thread(() -> {
						final String receive = networkStr.ServerReceiveFile(60000);
						rtmpstate = "Receive";
						
						
						
						if(!receive.isEmpty()) {
							Platform.runLater(() -> {
								TabInfo info = new TabInfo();
								info.file = new File(""); // 빈 파일로라도 초기화
								info.fileName = "";
								info.fileDir = "";
								info.content = receive;
								info.modified = true;
								info.pos = 0;
								info.scrollX = codeArea2.estimatedScrollXProperty().getValue();
								info.scrollY = codeArea2.estimatedScrollYProperty().getValue();
								info.encoding = "";
								
								Tab tab = new Tab();
								Label tablabel = new Label("noname");
								Button closeBtn = new Button("x");
								HBox htabbox = new HBox();
								HBox.setHgrow(tablabel, Priority.ALWAYS);
								tablabel.setMaxWidth(Double.MAX_VALUE);
								
								htabbox.getChildren().addAll(tablabel, closeBtn);
								//htabbox.setAlignment(Pos.CENTER_LEFT);
								
								closeBtn.setOnAction(cbe -> {
									// 저장 여부 묻기
									// 저장
									tabPane.getSelectionModel().select(tab);
									String currentCode = codeArea2.getText();
									Tab closingTab = (Tab) tabPane.getSelectionModel().getSelectedItem();   // ← 닫히는 탭
									TabInfo info2 = (TabInfo) closingTab.getUserData();
									if (info.modified) {
										
										Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
										alert.setTitle("파일 저장");
										alert.setHeaderText(null);
										alert.setContentText("파일 '" + info.fileName + "' 을(를) 저장하시겠습니까?");
										
										ButtonType saveBtn = new ButtonType("확인");
										ButtonType cancelBtn = new ButtonType("취소", ButtonBar.ButtonData.CANCEL_CLOSE);
										cancelBtn = new ButtonType("취소");
										alert.getButtonTypes().setAll(saveBtn, cancelBtn);
										
										Optional<ButtonType> result = alert.showAndWait();
										
										if (result.isPresent() && result.get() == saveBtn) {
											// 확인 눌렀을 때 → 저장은 네가 처리
											// 예: saveFile(info);
											typeTempStr = "";
											typepos = 0;
											if (popup.isShowing()) {
												
												popup.hide();
											}
											//System.gc();
											File selectedFile;
											String selectedEncoding;
											if(fileName.isEmpty())
											{
												FileChooser fileChooser = new FileChooser();
												fileChooser.setTitle("Save Java File");
												fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Files", "*.java"));
												File initialDir = new File(dirFileSet());
												if (initialDir.exists()) {
													fileChooser.setInitialDirectory(initialDir);
												}else
												{
													fileChooser.setInitialDirectory(new File("."));
													fileDir = ".";
												}
												/*
												// 인코딩 선택 UI
												List<String> encodings = Arrays.asList("UTF-8", "MS949", "EUC-KR", "ISO-8859-1");
												ChoiceDialog<String> dialog = new ChoiceDialog<>("UTF-8", encodings);
												dialog.setTitle("Select Encoding");
												dialog.setHeaderText("Choose file encoding");
												dialog.setContentText("Encoding:");
												
												Optional<String> result0 = dialog.showAndWait();
												if (!result0.isPresent()) {
												return; // 사용자가 취소
												}
												*/
												selectedEncoding = "UTF-8";
												selectedFile = fileChooser.showSaveDialog(null);
											}else{
												String fn = fileDir + File.separator + fileName;
												selectedFile = new File(fn);
												selectedEncoding = file_Encoding;
											}
											if (selectedFile != null) {
												try {
													String rawName = selectedFile.getName();
													// 현재 앱 실행 디렉토리
													String appDir = new File("").getAbsolutePath();
													if(fileDir.equals(appDir) && fileName.equals("Welcome.txt"))
													{
														
													}else if (!rawName.toLowerCase().endsWith(".java")) {
														selectedFile = new File(selectedFile.getParent(), rawName + ".java");
													}
													fileDir = selectedFile.getParent();
													fileName = selectedFile.getName();
													file_Encoding = selectedEncoding;
													modified = false;
													String filepath = "javaEditerPath.txt";
													Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
													// 코드 저장
													String code =  currentCode;
													Charset charset = Charset.forName(selectedEncoding);
													Files.write(selectedFile.toPath(), code.getBytes(charset));
													//pathLabel.setText("경로 : " + selectedFile.getAbsolutePath());
													consoleArea.appendText("저장 완료: " + selectedFile.getAbsolutePath() + "\n");
													
												} catch (Exception ex) {
													consoleArea.appendText("저장 오류: " + ex.getMessage() + "\n");
												}
												
												agoFileList = getAgoFile();
												agoFileList = setAddAgoList(selectedFile.getAbsolutePath(),agoFileList);
												setSaveAgoFile(agoFileList);
											}
										}
									}else{
										
									}
									tabPane.getTabs().remove(closingTab);
								});
								//tab.setGraphic(hbox);
								tab.setGraphic(htabbox);
								tab.setClosable(false);
								
								tab.setUserData(info);
								//codeArea2.replaceText(info.content);//이건 넣지 말것...ㅋㅋㅋ
								tabPane.getTabs().add(tab);
								tabPane.getSelectionModel().select(tab);
								
								//SaveInfoFile();
								fileName = "";
								fileDir = "";
								file_Encoding = "";
								pathLabel.setText("경로 : " + " | encoding : ");
								String newText = codeArea2.getText();
								// 여기서 원하는 이벤트 실행
								onInitialText(newText);
								codeArea2.getUndoManager().forgetHistory();
							});
							
						}
						
						
						
					}).start();
					Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), nbe -> {
						receiveButton.setText(rtmpstate);
					}));
					timeline.setCycleCount(Animation.INDEFINITE);
					timeline.play();
					//stage.close();
					
				}else if(state.equals("Stop"))
				{
					receiveButton.setText("Receive");
					new Thread(() -> {
						try{
							networkStr.serverClose();
						}catch(Exception nee)
						{
							nee.printStackTrace();
						}
					}).start();
				}
			});
			// -------------------------
			// 2) 두 번째 HBox: TextField + Send 버튼
			// -------------------------
			HBox hbox2 = new HBox(10);
			TextField ipTextField = new TextField();
			ipTextField.setPromptText("IP 입력");
			ipTextField.setText(ipString);
			Button sendButton = new Button("Send");
			hbox2.getChildren().addAll(ipTextField, sendButton);
			
			sendButton.setOnAction( ne -> {
				String state = sendButton.getText();
				
				if(state.equals("Send"))
				{
					stmpstate = "Stop";
					sendButton.setText(stmpstate);
					new Thread(() -> {
						String send = codeArea2.getText();
						String sendip = ipTextField.getText();
						int sendport = 60000;
						try{
							networkStr.ClientSendFile(sendip,sendport,send);
							stmpstate = "Send";
						}catch(Exception nee)
						{
							nee.printStackTrace();
						}
						
						
					}).start();
					Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), nbe -> {
						sendButton.setText(stmpstate);
					}));
					timeline.setCycleCount(Animation.INDEFINITE);
					timeline.play();
					
					//stage.close();
					
				}else if(state.equals("Stop"))
				{
					sendButton.setText("Send");
					new Thread(() -> {
						try{
							networkStr.clientClose();
						}catch(Exception nee)
						{
							nee.printStackTrace();
						}
					}).start();
				}
			});
			// -------------------------
			// 3) 세 번째 HBox: 확인 + 취소 버튼
			// -------------------------
			HBox hbox3 = new HBox(10);
			Button okButton = new Button("확인");
			Button cancelButton = new Button("취소");
			hbox3.getChildren().addAll(okButton, cancelButton);
			okButton.setOnAction( ne -> {
				ipString = ipTextField.getText();
				new Thread(() -> {
					try{
						networkStr.serverClose();
						networkStr.clientClose();
					}catch(Exception nee)
					{
						nee.printStackTrace();
					}
				}).start();
				stage.close();
			});
			cancelButton.setOnAction( ne -> {
				new Thread(() -> {
					try{
						networkStr.serverClose();
						networkStr.clientClose();
					}catch(Exception nee)
					{
						nee.printStackTrace();
					}
				}).start();
				stage.close();
			});
			// VBox에 모두 추가
			rootBox.getChildren().addAll(hbox1, hbox2, hbox3);
			
			// 예시로 새 창 띄우기
			
			stage.setTitle("Network Settings");
			stage.setScene(new Scene(rootBox, 300, 200));
			stage.show();
		});
		
		
		
		
		
		HBox buttonBox = new HBox(10, newButton,loadButton,saveButton,saveAsButton,agoButton,searchButton,compileButton, runButton,stopButton, tabCleanButton,optionButton,jarButton,exlsButton,styleButton,informButton,gcButton,networkButton, exitButton);
		VBox buttonAreaBox = new VBox();
		buttonAreaBox.setSpacing(5);
		buttonAreaBox.setPadding(new Insets(10));
		//HBox.setHgrow(codeArea2, Priority.ALWAYS); // CodeArea가 VBox 안에서 최대한 공간 차지
		//VBox.setVgrow(buttonAreaBox, Priority.NEVER);
		//VBox.setVgrow(codeScrollPane, Priority.ALWAYS);
		//VBox.setVgrow(codeAreaBox, Priority.ALWAYS);
		//codeArea2.setPrefHeight(500); // 초기 높이 설정
		buttonAreaBox.getChildren().add(buttonBox);
		buttonAreaBox.getChildren().add(tabPane);
		
		
		// TabInfo 객체 생성
		TabInfo info = new TabInfo();
		info.file = new File("Welcome.txt");
		
		// 파일이 없으면 생성
		if (!info.file.exists()) {
			try {
				info.file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try{
			info.fileName = info.file.getName();
			info.fileDir = info.file.getAbsoluteFile().getParent();
			info.encoding = EncodingDetector.detectEncoding(info.file);
		}catch(IOException mie)
		{
			mie.printStackTrace();
		}
		
		// 파일 내용 읽기
		StringBuilder fsb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(info.file))) {
			String line;
			while ((line = br.readLine()) != null) {
				fsb.append(line).append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		info.content = fsb.toString();
		info.modified = false; // 처음엔 수정되지 않은 상태
		//초기설정
		fileName = info.fileName;
		fileDir = info.fileDir;
		file_Encoding = info.encoding;
		modified = info.modified;
		// TabPane 높이 설정
		tabPane.setMinHeight(25);
		
		// Tab 생성
		Label welcomelabel = new Label(info.fileName);
		HBox welcomehbox = new HBox(welcomelabel);
		Tab tab = new Tab();
		tab.setClosable(false);   // ← 닫기 버튼 제거
		tab.setUserData(info);
		tab.setGraphic(welcomehbox);
		// Tab 내용(TextArea 등)
		//codeArea2.replaceText(info.content);
		tab.setContent(codeScrollPane);
		
		// TabPane에 추가
		tabPane.getTabs().add(tab);
		//antTab = tabPane.getSelectionModel().getSelectedItem();
		
		
		//VBox.setVgrow(codeScrollPane, Priority.ALWAYS); // 코드 영역이 나머지 공간 차지
		
		//VBox codeBox = new VBox();
		
		//codeBox.setSpacing(5);
		//codeBox.setPadding(new Insets(10));
		// 스타일 강제 적용
		
		LoadTabPathInfo();
		codeArea2.moveTo(0);
		codeArea2.estimatedScrollXProperty().setValue(0.0);
		codeArea2.estimatedScrollYProperty().setValue(0.0);
		
		
		
		//codeBox.getChildren().add(codeScrollPane);
		//codeBox.getChildren().add(consoleArea);
		VBox.setVgrow(codeScrollPane, Priority.ALWAYS);
		//codeBox.getChildren().add(codeArea2);
		// BorderPane root = new BorderPane();
		//VBox.setVgrow(consoleArea, Priority.NEVER);
		String labelStr = "경로 : " +((TabInfo)tabPane.getSelectionModel().getSelectedItem().getUserData()).file.getAbsolutePath()
		+ " | encoding : " + ((TabInfo)tabPane.getSelectionModel().getSelectedItem().getUserData()).encoding;
		pathLabel.setText(labelStr);
		//bottom...
		
		// 콘솔 영역과 경로 라벨을 담을 VBox 생성
		HBox consoleBtnBox = new HBox(10, eraseButton,copyButton);
		VBox bottomBox = new VBox(5); // 간격 5px
		bottomBox.setPadding(new Insets(5));
		
		// 콘솔 영역
		//bottomBox.getChildren().add(consoleScrollPane);
		//bottomBox.getChildren().add(consoleArea);
		bottomBox.getChildren().add(consoleBtnBox);
		bottomBox.getChildren().add(pathLabel);
		//codeBox.setMinHeight(100);
		bottomBox.setMinHeight(20);
		
		//splitPane.getItems().addAll(codeBox, bottomBox);
		splitPane.getItems().addAll(codeScrollPane, consoleArea);
		splitPane.setDividerPositions(0.8); // 초기 비율
		
		mainroot.setTop(buttonAreaBox);
		
		
		
		//mainroot.setCenter(codeBox);
		mainroot.setCenter(splitPane);
		
		// VBox를 하단에 배치
		mainroot.setBottom(bottomBox);
		
		// 씬 설정
		//Scene scene = new Scene(root, 800, 600);
		mainscene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // 스타일시트 연결 (선택)
		codeArea2.getStyleClass().add("code-area");
		codeArea2.getStylesheets().add("bgstyle.css");
		// 아이콘 설정 (리소스 경로에 있는 이미지 사용)
		try{
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/ant.png")));
		}catch(Exception ite)
		{
			ite.printStackTrace();  // 예외의 전체 스택 트레이스를 콘솔에 출력
			
		}
		// X 버튼 눌렀을 때 동작 정의
		primaryStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
			if (!isNowFocused) {
				// 창이 비활성화될 때 실행됨
				typeTempStr = "";
				typepos = 0;
				if(popup.isShowing())
				{
					popup.hide();
				}
				
			}
		});
		primaryStage.setOnCloseRequest(event -> {
			typeTempStr = "";
			typepos = 0;
			if (popup.isShowing()) {
				
				popup.hide();
			}
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("프로그램 종료");
			alert.setHeaderText("정말로 종료하시겠습니까?");
			alert.setContentText("확인을 누르면 프로그램이 종료됩니다.");
			
			// 확인 / 취소 버튼 추가
			ButtonType okButton = new ButtonType("확인", ButtonBar.ButtonData.OK_DONE);
			ButtonType cancelButton = new ButtonType("취소", ButtonBar.ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().setAll(okButton,cancelButton);
			
			// 결과 처리
			alert.showAndWait().ifPresent(type -> {
				if (type == okButton) {
					// 안전하게 종료
					if(!runProcessList.isEmpty())
					{
						try{
							//System.out.println("런프로세스정상작동중");
							//runProcess.waitFor();
							
							while(!runProcessList.isEmpty())
							{
								Thread.sleep(100); // 3초 기다림
								Process process = runProcessList.poll();
								process.destroy();//정상종료
							}
							runProcessList.clear();
							
							
							//runProcess.destroyForcibly(); // 강제 종료 (필요할 때만)
						}catch(Exception ffe)
						{
							ffe.printStackTrace();
						}
					}
					List<Tab> tabcopy = new ArrayList<>(tabPane.getTabs());
					List<String> pathlistx = new ArrayList<>();
					for (Tab tab2 : tabcopy) {
						TabInfo infox = (TabInfo) tab2.getUserData();
						if (infox == null || infox.file == null) {
							// Welcome 탭이거나 초기화되지 않은 탭
							System.exit(0);
						}
						String welcomex = new File("").getAbsolutePath() + File.separator + "Welcome.txt";
						if(!welcomex.equals(infox.file.getAbsolutePath()))
						{
							pathlistx.add(infox.file.getAbsolutePath());
						}
						if (infox != null && infox.modified) {
							//tabPane.getSelectionModel().select(tab);
							String currentCode = codeArea2.getText();
							Tab closingTab = tab2;   // ← 닫히는 탭
							TabInfo info2 = (TabInfo) closingTab.getUserData();
							if (infox.modified) {
								
								Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
								alert2.setTitle("파일 저장");
								alert2.setHeaderText(null);
								alert2.setContentText("파일 '" + info2.fileName + "' 을(를) 저장하시겠습니까?");
								
								ButtonType saveBtn = new ButtonType("확인");
								ButtonType cancelBtn = new ButtonType("취소", ButtonBar.ButtonData.CANCEL_CLOSE);
								cancelBtn = new ButtonType("취소");
								alert2.getButtonTypes().setAll(saveBtn, cancelBtn);
								
								Optional<ButtonType> result = alert2.showAndWait();
								
								if (result.isPresent() && result.get() == saveBtn) {
									// 확인 눌렀을 때 → 저장은 네가 처리
									// 예: saveFile(info);
									typeTempStr = "";
									typepos = 0;
									if (popup.isShowing()) {
										
										popup.hide();
									}
									//System.gc();
									File selectedFile;
									String selectedEncoding;
									if(fileName.isEmpty())
									{
										FileChooser fileChooser = new FileChooser();
										fileChooser.setTitle("Save Java File");
										fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Files", "*.java"));
										File initialDir = new File(dirFileSet());
										if (initialDir.exists()) {
											fileChooser.setInitialDirectory(initialDir);
										}else
										{
											fileChooser.setInitialDirectory(new File("."));
											fileDir = ".";
										}
										/*
										// 인코딩 선택 UI
										List<String> encodings = Arrays.asList("UTF-8", "MS949", "EUC-KR", "ISO-8859-1");
										ChoiceDialog<String> dialog = new ChoiceDialog<>("UTF-8", encodings);
										dialog.setTitle("Select Encoding");
										dialog.setHeaderText("Choose file encoding");
										dialog.setContentText("Encoding:");
										
										Optional<String> result0 = dialog.showAndWait();
										if (!result0.isPresent()) {
										return; // 사용자가 취소
										}
										
										//selectedEncoding = result0.get();
										*/
										selectedEncoding = "UTF-8";
										selectedFile = fileChooser.showSaveDialog(null);
									}else{
										String fn = fileDir + File.separator + fileName;
										selectedFile = new File(fn);
										selectedEncoding = file_Encoding;
									}
									if (selectedFile != null) {
										try {
											String rawName = selectedFile.getName();
											// 현재 앱 실행 디렉토리
											String appDir = new File("").getAbsolutePath();
											if(fileDir.equals(appDir) && fileName.equals("Welcome.txt"))
											{
												
											}else if (!rawName.toLowerCase().endsWith(".java")) {
												selectedFile = new File(selectedFile.getParent(), rawName + ".java");
											}
											fileDir = selectedFile.getParent();
											fileName = selectedFile.getName();
											file_Encoding = selectedEncoding;
											modified = false;
											String filepath = "javaEditerPath.txt";
											Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
											// 코드 저장
											String code =  currentCode;
											Charset charset = Charset.forName(selectedEncoding);
											Files.write(selectedFile.toPath(), code.getBytes(charset));
											//pathLabel.setText("경로 : " + selectedFile.getAbsolutePath());
											consoleArea.appendText("저장 완료: " + selectedFile.getAbsolutePath() + "\n");
											
										} catch (Exception ex) {
											consoleArea.appendText("저장 오류: " + ex.getMessage() + "\n");
										}
										
										agoFileList = getAgoFile();
										agoFileList = setAddAgoList(selectedFile.getAbsolutePath(),agoFileList);
										setSaveAgoFile(agoFileList);
									}
								}
							}else{
								
							}
							tabPane.getTabs().remove(closingTab);
						}
					}
					SaveTabPathInfo(pathlistx);
					Platform.exit();
					
					System.exit(0);
				}
				if (type == cancelButton) {
					// 닫기 이벤트 소비 → 창 닫히지 않음
					event.consume();
					
				}
			});
		});
		
		primaryStage.setTitle("앤트자바에디터v" + ver.app_Version);
		primaryStage.setScene(mainscene);
		// 시작할 때 최대화
		primaryStage.setMaximized(true);
		
		primaryStage.setFullScreen(false);
		primaryStage.show();
		//여기서부터 처음에 실행할거 넣기...
		
		
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public String dirFileSet()
	{
		String tempDir = "";
		try {
			
			String filepath = "javaEditerPath.txt";
			byte[] bytes = Files.readAllBytes(Paths.get(filepath));
			tempDir = new String(bytes, StandardCharsets.UTF_8).trim(); // 공백 제거
			//consoleArea.appendText("fileDir 로드됨: " + fileDir + "\n");
		} catch (Exception ex) {
			//consoleArea.appendText("fileDir 로드 오류: " + ex.getMessage() + "\n");
		}
		return tempDir;
	}
	public void aclassListInit()
	{
		aclasslist.clear();
		for(String temp : classlist)
		{
			aclasslist.add(temp.substring(temp.lastIndexOf('.')+1));
		}
	}
	public void classListInit(){
		classlist.clear();
		
		
	}
	public void useImportClassInit()
	{
		importlist.clear();
		/*importlist.add("import java.lang.String;");
		importlist.add("import java.lang.StringBuilder;");
		importlist.add("import java.lang.System;");
		importlist.add("import java.lang.Object;");
		importlist.add("import java.lang.Class;");
		importlist.add("import java.lang.StringBuffer;");
		importlist.add("import java.lang.Math;");
		importlist.add("import java.lang.Integer;");
		importlist.add("import java.lang.Long;");
		importlist.add("import java.lang.Double;");
		importlist.add("import java.lang.Float;");
		importlist.add("import java.lang.Short;");
		importlist.add("import java.lang.Byte;");
		importlist.add("import java.lang.Character;");
		importlist.add("import java.lang.Boolean;");
		importlist.add("import java.lang.Enum;");
		importlist.add("import java.lang.Throwable;");
		importlist.add("import java.lang.Exception;");
		importlist.add("import java.lang.RuntimeException;");
		importlist.add("import java.lang.Error;");
		importlist.add("import java.lang.Thread;");
		importlist.add("import java.lang.Runnable;");
		importlist.add("import java.lang.AutoCloseable;");
		importlist.add("import java.lang.Iterable;");
		importlist.add("import java.lang.Comparable;");
		importlist.add("import java.lang.Record;");
		importlist.add("import java.lang.Process;");*/
		importlist.add("import java.lang.*;");
		importlist.add("import java.lang.Object;");
		
	}
	public void keyListInit() {
		keylist.clear();
		keylist.add("record");
		keylist.add("abstract");
		keylist.add("assert");
		keylist.add("boolean");
		keylist.add("break");
		keylist.add("byte");
		keylist.add("case");
		keylist.add("catch");
		keylist.add("char");
		keylist.add("class");
		keylist.add("continue");
		keylist.add("default");
		keylist.add("do");
		keylist.add("double");
		keylist.add("else");
		keylist.add("enum");
		keylist.add("extends");
		keylist.add("final");
		keylist.add("finally");
		keylist.add("float");
		keylist.add("for");
		keylist.add("if");
		keylist.add("implements");
		keylist.add("import");
		keylist.add("instanceof");
		keylist.add("int");
		keylist.add("interface");
		keylist.add("long");
		keylist.add("native");
		keylist.add("new");
		keylist.add("null");
		keylist.add("package");
		keylist.add("private");
		keylist.add("protected");
		keylist.add("public");
		keylist.add("return");
		keylist.add("short");
		keylist.add("static");
		keylist.add("strictfp");
		keylist.add("super");
		keylist.add("switch");
		keylist.add("synchronized");
		keylist.add("this");
		keylist.add("throw");
		keylist.add("throws");
		keylist.add("transient");
		keylist.add("try");
		keylist.add("void");
		keylist.add("volatile");
		keylist.add("while");
		keylist.add("sealed");
		keylist.add("permits");
		keylist.add("true");
		keylist.add("false");
		keylist.add("var");
		keylist.add("yield");
		keylist.add("non-sealed");
		keylist.add("when");
		
	}
	
	private void applyHighlighting(String text) {
		
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		
		// 기본 스타일을 전체에 적용
		spansBuilder.add(Collections.singleton("normala"), text.length());
		
		// 여기에 keyword, numberic 등 추가 하이라이팅 로직을 넣으면 됨
		// spansBuilder.add(...)
		
		codeArea2.setStyleSpans(0, spansBuilder.create());
	}
	
	private boolean testb = false;
	private StyleSpans<Collection<String>> computeHighlighting(String text) {
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		
		// 빈 줄이면 기본 스타일로 전부 처리하거나, 호출 측에서 건너뛰어도 됨
		if (text.isEmpty()) {
			spansBuilder.add(Collections.singleton("normala"),0); // 또는 호출 측에서 skip
			return spansBuilder.create();
		}
		int caret = codeArea2.getCaretPosition();
		double scrollX = codeArea2.estimatedScrollXProperty().getValue();
		double scrollY = codeArea2.estimatedScrollYProperty().getValue();
		Map<Character,Character> quoteMap = Map.of('"', '"', '\'', '\'');
			Set<Character> tokenBoundary = Set.of( ' ', '\t', '\n', ',', '.', ';', '(', ')', '{', '}', '[', ']', '<', '>', '=', '+', '-', '*', '/', '!', '&', '|', '^', '?', ':');
		boolean inIdentifier = false;
		boolean inAnnotation = false;
		
		int i = 0;
		char inTmp = ' ';
		boolean inString = false;
		boolean inChar = false;
		boolean inComLine = false;
		boolean inComField = false;
		boolean inHexNumber = false;
		boolean inBinNumber = false;
		List<String> tempClassList = new ArrayList<>();
		//tempClassList.clear();
		tempClassList.addAll(useStyleClass);
		tempClassList.addAll(fileStyleClass);
		tempClassList.addAll(jarStyleClass);
		
		// tempClassList를 길이 기준으로 정렬 (긴 문자열 먼저)
		//tempClassList.sort((a, b) -> Integer.compare(b.length(), a.length()));
		
		List<String> filtered =		tempClassList.stream()
		.collect(Collectors.toCollection(HashSet::new))   // 중복 제거
		.stream()
		.sorted(Comparator.comparingInt(String::length).reversed())  // 길이 긴 순 정렬
		.collect(Collectors.toList());
		if (testb)
		{
			testb = false;
			/*try{
			Path path = Paths.get("output.txt");
			Files.write(path, filtered, StandardCharsets.UTF_8);
			}catch(IOException ioe)
			{
			
			}*/
			
		}
		keylist.sort((a, b) -> Integer.compare(b.length(), a.length()));
		while (i < text.length()) {
			boolean continueb = false;
			if(text.charAt(i)=='/' && !inString && !inChar)
			{
				if(i+1<text.length())
				{
					if(text.charAt(i+1)=='/' && !inComField)
					{
						inComLine = true;
						spansBuilder.add(Collections.singleton("commentric"), 1);
						i++;
						continue;
					}else if(text.charAt(i+1) == '*' && !inComLine)
					{
						inComField = true;
						spansBuilder.add(Collections.singleton("commentric"), 1);
						i++;
						continue;
					}
				}
				
			}
			if(inComField && i>0)
			{
				boolean closeCom = false;
				if(text.charAt(i)=='*'&& text.charAt(i-1)!='/' )
				{
					if(i+1<text.length())
					{
						if(text.charAt(i+1) == '/')
						{
							closeCom = true;
							spansBuilder.add(Collections.singleton("commentric"), 1);
							i++;
						}
					}
				}
				if(closeCom)
				{
					inComField = false;
				}
				spansBuilder.add(Collections.singleton("commentric"), 1);
				i++;
				continue;
			}
			if(inComLine)
			{
				if(text.charAt(i)=='\n')
				{
					inComLine = false;
					
				}
				spansBuilder.add(Collections.singleton("commentric"), 1);
				i++;
				continue;
			}
			
			
			int backslashCount = 0;
			int j = i - 1;
			while (j >= 0 && text.charAt(j) == '\\') {
				backslashCount++;
				j--;
			}
			boolean escaped = (backslashCount % 2 == 1);
			
			if((text.charAt(i)=='"'||text.charAt(i)=='\'') &&  i>2 && !escaped )
			{
				if(inChar == false)
				{
					
					inChar = true;
					inTmp = text.charAt(i);
					spansBuilder.add(Collections.singleton("wordsric"), 1);
					i++;
					continue;
					
					
				}else{
					if(text.charAt(i) == inTmp )
					{
						inChar = false;
						spansBuilder.add(Collections.singleton("wordsric"), 1);
						inTmp = ' ';
						i++;
						continue;
						
					}
				}
			}
			/*
			if(text.charAt(i)=='\''&& i>2)
			{
			if(inChar == false)
			{
			if(text.charAt(i-1)!='\\'||(text.charAt(i-2)=='\\'&&text.charAt(i-1)=='\\') )
			{
			inChar = true;
			spansBuilder.add(Collections.singleton("wordsric"), 1);
			i++;
			continue;
			
			}
			
			}else {
			
			{
			inChar = false;
			spansBuilder.add(Collections.singleton("wordsric"), 1);
			i++;
			continue;
			
			
			
			}
			}
			}
			if(text.charAt(i)=='"' && !inChar && i>2)
			{
			if(inString == false)
			{
			if(text.charAt(i-1)!='\\'||(text.charAt(i-2)=='\\'&&text.charAt(i-1)=='\\') )
			{
			
			inString = true;
			spansBuilder.add(Collections.singleton("wordsric"), 1);
			i++;
			continue;
			
			}
			
			}else {
			if(text.charAt(i-1)!='\\'||(text.charAt(i-2)=='\\'&&text.charAt(i-1)=='\\'))
			{
			inString = false;
			spansBuilder.add(Collections.singleton("wordsric"), 1);
			i++;
			continue;
			
			
			
			}
			}
			}
			*/
			boolean matched = false;
			//Set<String> set = new HashSet<>();
			//set.addAll(useStyleClass);
			//set.addAll(fileStyleClass);
			//set.addAll(jarStyleClass);
			
			if (text.charAt(i) == '@' && !inAnnotation) {
				inAnnotation = true;
				spansBuilder.add(Collections.singleton("keyword"), 1);
				continueb = true;
				matched = true;
				i++;
			}else if(inAnnotation)
			{
				char c = text.charAt(i);
				if (Character.isLetterOrDigit(c) || c == '_') {
					spansBuilder.add(Collections.singleton("keyword"), 1);
				} else {
					inAnnotation = false;
					spansBuilder.add(Collections.singleton("normala"), 1);
				}
				continueb = true;
				matched = true;
				i++;
				
			}
			
			
			if(continueb) continue;
			for (String keyword : filtered) {
				
				if ( i + keyword.length() <= text.length() && text.substring(i, i + keyword.length()).equals(keyword)) {
					//boolean validPrefix = (i == 0) || (Character.isWhitespace(text.charAt(i - 1))&& !Character.isLetter(text.charAt(i+keyword.length()))) || (!Character.isLetter(text.charAt(i-1)) && !Character.isLetter(text.charAt(i+keyword.length())));
					
					int rightIdx = i + keyword.length();
					
					boolean rightBoundaryOK =(rightIdx >= text.length()) || ((!Character.isLetterOrDigit(text.charAt(rightIdx))) && (text.charAt(rightIdx) != '_')); // 뒤 글자가 있으면 검사,// 키워드가 문자열 끝에 있음 → 경계로 인정
					
					boolean leftBoundaryOK =(i == 0) || ((!Character.isLetterOrDigit(text.charAt(i - 1))) && (text.charAt(i - 1) != '_' )); // 앞 글자가 없거나 글자가 아님
					
					boolean validPrefix = leftBoundaryOK && rightBoundaryOK;
					if (keyword.chars().allMatch(Character::isDigit)) {
						continue; // 숫자-only → 매칭 실패 처리
					}
					
					if(validPrefix)
					{
						if(inString||inChar)
						{
							spansBuilder.add(Collections.singleton("wordsric"), keyword.length());
						}else
						{
							spansBuilder.add(Collections.singleton("keyword2"), keyword.length());
						}
						i += keyword.length();
						continueb = true;
						matched = true;
						break;
					}
				}
			}
			if(continueb) continue;
			
			for (String keyword : keylist) {
				if (i + keyword.length() <= text.length() && text.substring(i, i + keyword.length()).equals(keyword)) {
					//boolean validPrefix = (i == 0) || (Character.isWhitespace(text.charAt(i - 1))&& !Character.isLetter(text.charAt(i+keyword.length()))) || (!Character.isLetter(text.charAt(i-1)) && !Character.isLetter(text.charAt(i+keyword.length())));
					int rightIdx = i + keyword.length();
					
					boolean rightBoundaryOK =(rightIdx >= text.length()) || ((!Character.isLetterOrDigit(text.charAt(rightIdx))) && (text.charAt(rightIdx)!='_')); // 뒤 글자가 있으면 검사,// 키워드가 문자열 끝에 있음 → 경계로 인정
					
					boolean leftBoundaryOK =(i == 0) || ((!Character.isLetterOrDigit(text.charAt(i - 1))) && (text.charAt(i-1)!='_')); // 앞 글자가 없거나 글자가 아님
					
					boolean validPrefix = leftBoundaryOK && rightBoundaryOK;
					if(validPrefix)
					{
						if(inString||inChar)
						{
							spansBuilder.add(Collections.singleton("wordsric"), keyword.length());
						}else
						{
							spansBuilder.add(Collections.singleton("keyword"), keyword.length());
						}
						i += keyword.length();
						continueb = true;
						matched = true;
						break;
					}
				}
			}
			
			if (!matched) {
				
				
				
			}
			
			
			
			
			
			
			
			if(!matched)
			{
				char c = text.charAt(i);
				
				boolean isDigit = Character.isDigit(c);
				// 1) 0x 또는 0X 시작 체크
				if(i>0 && tokenBoundary.contains(text.charAt(i-1)) && !inHexNumber && i + 1 < text.length()) {
					char c2 = text.charAt(i + 1);
					if(c == '0' && (c2 == 'x' || c2 == 'X')) {
						inHexNumber = true;
						spansBuilder.add(Collections.singleton("numberic"), 1); // '0'
						i++;
						spansBuilder.add(Collections.singleton("numberic"), 1); // 'x'
						i++;
						continue;
					}else if(c == '0' && (c2 == 'b' || c2 =='B')){
						inBinNumber = true;
						spansBuilder.add(Collections.singleton("numberic"), 1); // '0'
						i++;
						spansBuilder.add(Collections.singleton("numberic"), 1); // 'b'
						i++;
						continue;
					}
				}
				// 2) 16진수 내부 처리
				if( inHexNumber) {
					if( (c >= '0' && c <= '9') ||
						(c >= 'a' && c <= 'f') ||
						(c >= 'A' && c <= 'F') ) {
						
						spansBuilder.add(Collections.singleton("numberic"), 1);
					} else if(c>='0' && c <= '1'){
						spansBuilder.add(Collections.singleton("numberic"), 1);
					}else {
						// hex 종료
						inHexNumber = false;
						spansBuilder.add(Collections.singleton("normala"), 1);
					}
					i++;
					continue;
				}
				if(i>0 && tokenBoundary.contains(text.charAt(i-1)) && !inIdentifier && !isDigit)
				{
					inIdentifier = true;
				}
				if(tokenBoundary.contains(text.charAt(i)) && inIdentifier)
				{
					inIdentifier = false;
				}
				if(inString)
				{
					spansBuilder.add(Collections.singleton("wordsric"), 1);
				} else if(inChar) {
					spansBuilder.add(Collections.singleton("wordsric"), 1);
				} else if (isDigit && !inIdentifier) {
					spansBuilder.add(Collections.singleton("numberic"), 1);
					
				}else {
					
					
					spansBuilder.add(Collections.singleton("normala"), 1);
				}
				
				i++;
			}
			
			
		}
		
		
		
		Platform.runLater(() -> {
			codeArea2.estimatedScrollXProperty().setValue(scrollX);
			codeArea2.estimatedScrollYProperty().setValue(scrollY);
		});
		
		return spansBuilder.create();
	}
	
	private boolean isStringLiteral(String token) {
		return (token.startsWith("\"") && token.endsWith("\"")) ||(token.startsWith("'") && token.endsWith("'"));
	}
	
	private boolean isNumeric(String token) {
		return token.matches("\\d+(\\.\\d+)?[;,)]?");
	}
	private boolean isCommentric(String token)
	{
		return  token.startsWith("//");
	}
	
	
	private List<String> setUseMethodList(List<String> classList)
	{
		List<String> useClassList = new ArrayList<>();
		
		for(String temp : classList)
		{
			try{
				String className = temp.replace("import","").replace(";","").trim();
				Class<?> clazz = Class.forName(className); // 또는 Class.forName("java.io.File");
				
				Method[] methods = clazz.getDeclaredMethods(); // 모든 선언된 메소드
				for (Method method : methods) {
					useClassList.add(method.getName());
				}
			}catch(ClassNotFoundException cnfe)
			{
				
			}
			
		}
		return useClassList;
	}
	private List<String> setUseClassList(List<String> importClassList)
	{
		List<String> useClassList = new ArrayList<>();
		useStyleClass.clear();
		for(String temp : importClassList)
		{
			temp = temp.replace("import", "").replace(";", "").trim();
			//System.out.println(temp+"hahaha");
			
			// 와일드카드 처리
			if (temp.endsWith(".*")) {
				//System.out.println(temp.replace(".*","").trim());
				String pkg = temp.replace(".*", "").trim();
				
				int dotCount = (int)pkg.chars().filter(ch -> ch == '.').count();
				
				try (ScanResult scanResult = new ClassGraph().enableSystemJarsAndModules().acceptPackages(temp.replace(".*","").trim()).scan()) {
					for (ClassInfo classInfo : scanResult.getAllClasses()) {
						//System.out.println(classInfo.getSimpleName());
						int classDotCount = (int)classInfo.getName().chars().filter(ch -> ch == '.').count();
						if (classDotCount == dotCount + 1) {
							useClassList.add(classInfo.getSimpleName());
							useStyleClass.add(classInfo.getSimpleName());
							try {
								Class<?> clazz = classInfo.loadClass();
								for (Method method : clazz.getDeclaredMethods()) {
									useClassList.add(method.getName());
								}
								// 필드 이름 추가
								for (Field field : clazz.getDeclaredFields()) {
									useClassList.add(field.getName());
								}
								
							} catch (Throwable e) {
								// 로드 실패 시 무시하거나 로그 출력
								System.err.println("메소드 로드 실패: " + classInfo.getName());
							}
							
						}
					}
				}
				
				
			} else {
				// 단일 클래스 처리
				try {
					Class<?> clazz = Class.forName(temp);
					useClassList.add(clazz.getSimpleName());
					useStyleClass.add(clazz.getSimpleName());
					try {
						
						for (Method method : clazz.getDeclaredMethods()) {
							useClassList.add(method.getName());
						}
						// 필드 이름 추가
						for (Field field : clazz.getDeclaredFields()) {
							useClassList.add(field.getName());
						}
					} catch (Throwable e) {
						// 로드 실패 시 무시하거나 로그 출력
						System.err.println("메소드 로드 실패: " + clazz.getName());
					}
					
				} catch (ClassNotFoundException e) {
					// 존재하지 않는 클래스는 무시
				}
			}
			
		}
		//System.out.println(useClassList);
		//System.out.println(importlist);
		return useClassList;
		
	}
	/*private static boolean isPackageExists(String packageName) {
	try {
	// 패키지 안의 대표적인 클래스 하나를 시도해본다
	// 예: java.util 패키지라면 java.util.List 같은 기본 클래스
	// 여기서는 단순히 패키지 이름만으로는 확인 불가하므로,
	// getClassesInPackage 같은 메서드에서 실제 클래스 목록을 가져올 때
	// Class.forName()으로 로드 가능한지 확인하는 방식으로 처리
	Package p = Package.getPackage(packageName);
	return p != null;
	} catch (Exception e) {
	return false;
	}
	}*/
	
	private List<String> setUseClassValList(List<String> classList,List<String> useClassList)
	{
		List<String> classValList = new ArrayList<>();
		for (String line : classList) {
			line = line.trim().replace(";", ""); // 세미콜론 제거
			
			// 클래스명과 변수 선언부 분리
			String[] parts = line.split("\\s+", 2); // 앞은 클래스명, 뒤는 변수들
			if (parts.length < 2) continue;
			
			String classType = parts[0].replace("[]", ""); // 배열이면 [] 제거
			String variables = parts[1];
			
			if (useClassList.contains(classType)) {
				// 변수들 쉼표로 나누고 초기화 제거
				//String[] varParts = variables.split("[(),\\[\\]\"\'+\\-*/%&|!]");
				;				/*
				for (String var : varParts) {
				String[] assignParts = var.split("=");
				String varName = assignParts[0].trim();
				
				
				if (!varName.isEmpty()) {
				classValList.add(varName);
				}
				
				}*/
				Matcher m = Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b").matcher(variables);
				while (m.find()) {
					String token = m.group();
					classValList.add(token);
				}
				
				
				
			}
		}
		
		return classValList;
		
		
	}
	//int,char,float,double,byte,long,short,boolean,enum
	private List<String> setUseIntValList(List<String> intList)
	{
		List<String> intValList = new ArrayList<>();
		for (String line : intList) {
			// 1. 타입 제거 (int 또는 int[])
			line = line.replaceFirst("int\\s*\\[\\s*\\]\\s*", ""); // int[] 제거
			line = line.replaceFirst("int\\s+", "");               // int 제거
			
			// 2. 세미콜론 제거
			line = line.replace(";", "");
			
			// 3. 초기화 부분 제거 (예: = new int[3])
			//String[] parts = line.split("[(),\\[\\]\"\'+\\-*/%&|!]");
			/*for (String part : parts) {
			String var = part.split("=")[0].trim(); // = 앞까지만
			if (!var.isEmpty()) {
			intValList.add(var);
			}
			}*/
			Matcher m = Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b").matcher(line.trim());
			while (m.find()) {
				String token = m.group();
				intValList.add(token);
			}
		}
		
		return intValList;
		
	}
	
	//int,char,float,double,byte,long,short,boolean,enum
	private List<String> setUseCharValList(List<String> charList)
	{
		List<String> charValList = new ArrayList<>();
		for (String line : charList) {
			// 1. 타입 제거 (int 또는 int[])
			line = line.replaceFirst("char\\s*\\[\\s*\\]\\s*", ""); // int[] 제거
			line = line.replaceFirst("char\\s+", "");               // int 제거
			
			// 2. 세미콜론 제거
			line = line.replace(";", "");
			
			// 3. 초기화 부분 제거 (예: = new int[3])
			//String[] parts = line.split("[(),\\[\\]\"\'+\\-*/%&|!]");
			/*for (String part : parts) {
			String var = part.split("=")[0].trim(); // = 앞까지만
			if (!var.isEmpty()) {
			charValList.add(var);
			}
			}*/
			Matcher m = Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b").matcher(line.trim());
			while (m.find()) {
				String token = m.group();
				charValList.add(token);
			}
		}
		
		return charValList;
	}
	
	//int,char,float,double,byte,long,short,boolean,enum
	private List<String> setUseFloatValList(List<String> floatList)
	{
		List<String> floatValList = new ArrayList<>();
		for (String line : floatList) {
			// 1. 타입 제거 (int 또는 int[])
			line = line.replaceFirst("float\\s*\\[\\s*\\]\\s*", ""); // int[] 제거
			line = line.replaceFirst("float\\s+", "");               // int 제거
			
			// 2. 세미콜론 제거
			line = line.replace(";", "");
			
			// 3. 초기화 부분 제거 (예: = new int[3])
			//String[] parts = line.split("[(),\\[\\]\"\'+\\-*/%&|!]");
			/*for (String part : parts) {
			String var = part.split("=")[0].trim(); // = 앞까지만
			if (!var.isEmpty()) {
			floatValList.add(var);
			}
			}*/
			Matcher m = Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b").matcher(line.trim());
			while (m.find()) {
				String token = m.group();
				floatValList.add(token);
			}
		}
		
		return floatValList;
	}
	
	//int,char,float,double,byte,long,short,boolean,enum
	private List<String> setUseDoubleValList(List<String> doubleList)
	{
		List<String> doubleValList = new ArrayList<>();
		for (String line : doubleList) {
			// 1. 타입 제거 (int 또는 int[])
			line = line.replaceFirst("double\\s*\\[\\s*\\]\\s*", ""); // int[] 제거
			line = line.replaceFirst("double\\s+", "");               // int 제거
			
			// 2. 세미콜론 제거
			line = line.replace(";", "");
			
			// 3. 초기화 부분 제거 (예: = new int[3])
			//String[] parts = line.split("[(),\\[\\]\"\'+\\-*/%&|!]");
			/*for (String part : parts) {
			String var = part.split("=")[0].trim(); // = 앞까지만
			if (!var.isEmpty()) {
			doubleValList.add(var);
			}
			}*/
			Matcher m = Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b").matcher(line.trim());
			while (m.find()) {
				String token = m.group();
				doubleValList.add(token);
			}
		}
		
		return doubleValList;
	}
	
	//int,char,float,double,byte,long,short,boolean,enum
	private List<String> setUseByteValList(List<String> byteList)
	{
		List<String> byteValList = new ArrayList<>();
		for (String line : byteList) {
			// 1. 타입 제거 (int 또는 int[])
			line = line.replaceFirst("byte\\s*\\[\\s*\\]\\s*", ""); // int[] 제거
			line = line.replaceFirst("byte\\s+", "");               // int 제거
			
			// 2. 세미콜론 제거
			line = line.replace(";", "");
			
			// 3. 초기화 부분 제거 (예: = new int[3])
			//String[] parts = line.split("[(),\\[\\]\"\'+\\-*/%&|!]");
			/*for (String part : parts) {
			String var = part.split("=")[0].trim(); // = 앞까지만
			if (!var.isEmpty()) {
			byteValList.add(var);
			}
			}*/
			Matcher m = Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b").matcher(line.trim());
			while (m.find()) {
				String token = m.group();
				byteValList.add(token);
			}
		}
		
		return byteValList;
	}
	
	//int,char,float,double,byte,long,short,boolean,enum
	private List<String> setUseLongValList(List<String> longList)
	{
		List<String> longValList = new ArrayList<>();
		for (String line : longList) {
			// 1. 타입 제거 (int 또는 int[])
			line = line.replaceFirst("long\\s*\\[\\s*\\]\\s*", ""); // int[] 제거
			line = line.replaceFirst("long\\s+", "");               // int 제거
			
			// 2. 세미콜론 제거
			line = line.replace(";", "");
			
			// 3. 초기화 부분 제거 (예: = new int[3])
			//String[] parts = line.split("[(),\\[\\]\"\'+\\-*/%&|!]");
			/*for (String part : parts) {
			String var = part.split("=")[0].trim(); // = 앞까지만
			if (!var.isEmpty()) {
			longValList.add(var);
			}
			}*/
			Matcher m = Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b").matcher(line.trim());
			while (m.find()) {
				String token = m.group();
				longValList.add(token);
			}
		}
		
		return longValList;
	}
	
	//int,char,float,double,byte,long,short,boolean,enum
	private List<String> setUseShortValList(List<String> shortList)
	{
		List<String> shortValList = new ArrayList<>();
		for (String line : shortList) {
			// 1. 타입 제거 (int 또는 int[])
			line = line.replaceFirst("short\\s*\\[\\s*\\]\\s*", ""); // int[] 제거
			line = line.replaceFirst("short\\s+", "");               // int 제거
			
			// 2. 세미콜론 제거
			line = line.replace(";", "");
			
			// 3. 초기화 부분 제거 (예: = new int[3])
			//String[] parts = line.split("[(),\\[\\]\"\'+\\-*/%&|!]");
			/*for (String part : parts) {
			String var = part.split("=")[0].trim(); // = 앞까지만
			if (!var.isEmpty()) {
			shortValList.add(var);
			}
			}*/
			Matcher m = Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b").matcher(line.trim());
			while (m.find()) {
				String token = m.group();
				shortValList.add(token);
			}
		}
		
		return shortValList;
	}
	
	//int,char,float,double,byte,long,short,boolean,enum
	private List<String> setUseBooleanValList(List<String> booleanList)
	{
		List<String> booleanValList = new ArrayList<>();
		for (String line : booleanList) {
			// 1. 타입 제거 (int 또는 int[])
			line = line.replaceFirst("boolean\\s*\\[\\s*\\]\\s*", ""); // int[] 제거
			line = line.replaceFirst("boolean\\s+", "");               // int 제거
			
			// 2. 세미콜론 제거
			line = line.replace(";", "");
			
			// 3. 초기화 부분 제거 (예: = new int[3])
			//String[] parts = line.split("[(),\\[\\]\"\'+\\-*/%&|!]");
			/*for (String part : parts) {
			String var = part.split("=")[0].trim(); // = 앞까지만
			if (!var.isEmpty()) {
			booleanValList.add(var);
			}
			}*/
			Matcher m = Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b").matcher(line.trim());
			while (m.find()) {
				String token = m.group();
				booleanValList.add(token);
			}
		}
		
		return booleanValList;
	}
	
	//int,char,float,double,byte,long,short,boolean,enum
	private List<String> setUseEnumValList(List<String> enumList)
	{
		List<String> enumValList = new ArrayList<>();
		for (String line : enumList) {
			// 1. 타입 제거 (int 또는 int[])
			line = line.replaceFirst("enum\\s*\\[\\s*\\]\\s*", ""); // int[] 제거
			line = line.replaceFirst("enum\\s+", "");               // int 제거
			
			// 2. 세미콜론 제거
			line = line.replace(";", "");
			
			// 3. 초기화 부분 제거 (예: = new int[3])
			//String[] parts = line.split("[(),\\[\\]\"\'+\\-*/%&|!]");
			/*for (String part : parts) {
			String var = part.split("=")[0].trim(); // = 앞까지만
			if (!var.isEmpty()) {
			enumValList.add(var);
			}
			}*/
			Matcher m = Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b").matcher(line.trim());
			while (m.find()) {
				String token = m.group();
				enumValList.add(token);
			}
		}
		
		return enumValList;
	}
	private List<String> setFileClassList()
	{
		if (fileDir == null || fileDir.isEmpty()) {
			return fileStyleClass; // 또는 그냥 무시
		}
		List<String> fileClassList = new ArrayList<>();
		fileStyleClass.clear();
		Path currentDir = Paths.get(fileDir); // 현재 디렉토리
		String currentDirName = currentDir.getFileName().toString(); // "test"
		if(currentDirName.equals("Launcher"))
		{
			Path parentDir = currentDir.getParent(); // "c:\\myapp"
			currentDir = parentDir;
			
		}
		
		try (Stream<Path> stream = Files.walk(currentDir)) {
			stream.filter(path -> path.toString().endsWith(".class")).forEach(path -> {
				try{
					fileClassList.addAll(classFileRead(path.toString()));
				}catch(IOException asse)
				{
					asse.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileClassList;
		
	}
	
	private void insertSelectedItem() {
		String selected = listView.getSelectionModel().getSelectedItem();
		if (selected != null && !selected.isEmpty()) {
			int caretPos = codeArea2.getCaretPosition();
			codeArea2.deleteText(caretPos - typepos, caretPos); // "in" 제거
			caretPos = codeArea2.getCaretPosition();
			
			codeArea2.insertText(caretPos, selected);
			popup.hide(); // 선택 후 팝업 닫기
			typeTempStr = ""; // 입력 버퍼 초기화 (선택사항)
		}
	}
	
	private List<String> classFileRead(String filename) throws IOException
	{
		List<String> classFileList = new ArrayList<>();
		ClassReader cr = new ClassReader(new FileInputStream(filename));
		cr.accept(new ClassVisitor(Opcodes.ASM9) {
			@Override
			public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
				classFileList.add(name);
				return super.visitMethod(access, name, descriptor, signature, exceptions);
			}
			@Override
			public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
				// 필드 이름 저장
				classFileList.add(name);
				return super.visitField(access, name, descriptor, signature, value);
			}
			
			@Override
			public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
				// 클래스 이름(SimpleName) 저장
				String simpleName = name.substring(name.lastIndexOf('/') + 1);
				
				// 내부 클래스면 무시하고 바로 종료
				if (simpleName.contains("$")) {
					return;
				}
				
				classFileList.add(simpleName);
				fileStyleClass.add(simpleName);
				super.visit(version, access, name, signature, superName, interfaces);
			}
			@Override
			public void visitInnerClass(String name, String outerName, String innerName, int access) {
				if (innerName != null) {
					classFileList.add(innerName);
					// 내부 클래스 파일 경로 생성
					String innerClassPath = filename.replace(".class", "$" + innerName + ".class");
					
					try {
						fileStyleClass.add(innerName);
						classFileList.addAll(classFileRead(innerClassPath));
						
					} catch (IOException e) {
						// 내부 클래스 파일이 없을 수도 있음
					}
				}
				super.visitInnerClass(name, outerName, innerName, access);
			}
		}, 0);
		
		return classFileList;
	}
	private List<String> classFileReadFromJar(String jarPath) throws IOException {
		List<String> methodNames = new ArrayList<>();
		jarStyleClass.clear();
		
		try (JarFile jarFile = new JarFile(jarPath)) {
			Enumeration<JarEntry> entries = jarFile.entries();
			
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				
				if (entry.getName().endsWith(".class")) {
					try (InputStream is = jarFile.getInputStream(entry)) {
						ClassReader cr = new ClassReader(is);
						cr.accept(new ClassVisitor(Opcodes.ASM9) {
							@Override
							public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
								methodNames.add(name);
								//System.out.println(name);
								return super.visitMethod(access, name, descriptor, signature, exceptions);
							}
							@Override
							public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
								// 필드 이름 저장
								methodNames.add(name);
								return super.visitField(access, name, descriptor, signature, value);
							}
							
							@Override
							public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
								// 클래스 이름(SimpleName) 저장
								String simpleName = name.substring(name.lastIndexOf('/') + 1);
								// 내부 클래스면 무시하고 바로 종료
								if (simpleName.contains("$")) {
									return;
								}
								methodNames.add(simpleName);
								jarStyleClass.add(simpleName);
								super.visit(version, access, name, signature, superName, interfaces);
							}
							
							@Override
							public void visitInnerClass(String name, String outerName, String innerName, int access) {
								if (innerName != null) {
									methodNames.add(innerName);
									// 내부 클래스 파일 경로 생성
									String innerClassPath = entry.getName().replace(".class", "$" + innerName + ".class");
									
									try {
										jarStyleClass.add(innerName);
										methodNames.addAll(classFileRead(innerClassPath));
										
									} catch (IOException e) {
										// 내부 클래스 파일이 없을 수도 있음
									}
								}
								super.visitInnerClass(name, outerName, innerName, access);
							}
						}, 0);
					}
				}
			}
		}
		
		return methodNames;
	}
	public void externalJarCreateAll(String folderPath) throws IOException {
		
		File folder = new File(folderPath);
		
		// 폴더가 존재하는지 확인
		if (!folder.exists() || !folder.isDirectory()) {
			throw new IOException("폴더가 존재하지 않거나 디렉토리가 아닙니다: " + folderPath);
		}
		
		// 폴더 안의 파일 목록 가져오기
		File[] files = folder.listFiles();
		if (files == null) {
			throw new IOException("폴더를 읽을 수 없습니다: " + folderPath);
		}
		
		// jar 파일만 필터링해서 처리
		for (File f : files) {
			if (f.isFile() && f.getName().toLowerCase().endsWith(".jar")) {
				
				try {
					//System.out.println("처리 중: " + f.getName());
					externalJarCreate(f.getAbsolutePath());   // 기존 함수 재사용
				} catch (IOException e) {
					System.err.println("JAR 처리 실패: " + f.getName());
					e.printStackTrace();
				}
			}
		}
		
		//System.out.println("모든 JAR → EXLS 변환 완료");
	}
	public void externalJarCreate(String jarFilePath) throws IOException {
		
		// JAR에서 읽은 전체 이름 목록
		List<String> allNames = classFileReadFromJar(jarFilePath);
		// jar 파일 이름만 추출
		File jarFile = new File(jarFilePath);
		String jarName = jarFile.getName();              // test.jar
		String baseName = jarName.replace(".jar", "");   // test
		
		// 현재 앱 실행 폴더
		String appPath = System.getProperty("user.dir");
		File externalListFolder = new File(appPath, "ExternalList");
		
		if (!externalListFolder.exists()) {
			externalListFolder.mkdirs();
		}
		
		// 최종 출력 경로: ExternalList/test.exls
		String outputPath = new File(externalListFolder, baseName + ".exls").getAbsolutePath();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
			
			
			
			
			
			
			
			// 해당 클래스의 필드/메소드/내부클래스 이름 모두 수집
			List<String> memberList = new ArrayList<>();
			
			for (String n : allNames) {
				
				memberList.add(n);
				
			}
			
			// 한 줄로 출력
			bw.write(String.join(", ", memberList));
			bw.newLine();
			
		}
	}
	public void exlsListAddAll()  throws IOException
	{
		// ExternalList 폴더 (앱 실행 폴더 기준)
		File externalListFolder = new File(System.getProperty("user.dir"), "ExternalList");
		
		if (!externalListFolder.exists()) {
			return;
		}
		
		File[] exlsFiles = externalListFolder.listFiles();
		if (exlsFiles == null) {
			return;
		}
		
		List<String> tempList = new ArrayList<>();
		
		for (File f : exlsFiles) {
			if (f.isFile() && f.getName().toLowerCase().endsWith(".exls")) {
				
				tempList.addAll(externalReadFile(f.getAbsolutePath()));
			}
		}
		// exlsStyleClass가 이미 존재한다고 가정
		// 중복 제거 + 순서 유지
		Set<String> uniqueSet = new LinkedHashSet<>(tempList);
		
		// 다시 List로 돌려놓기
		exlsClassList.clear();
		exlsClassList.addAll(uniqueSet);
	}
	private List<String> externalReadFile(String exlsPath) throws IOException {
		
		List<String> exlsList = new ArrayList<>();
		
		// 파일 전체 읽기
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(exlsPath))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append(" ");
			}
		}
		
		// , 로 split
		String[] tokens = sb.toString().split(",");
		
		// trim 후 리스트에 저장
		for (String t : tokens) {
			String cleaned = t.trim();
			if (!cleaned.isEmpty()) {
				exlsList.add(cleaned);
			}
		}
		return exlsList;
	}
	public static String findJavacFromSystemPath(String exename) {
		String systemPath = System.getenv("PATH"); // 환경변수 PATH 가져오기
		
		if (systemPath == null || systemPath.isEmpty()) {
			return null;
		}
		
		String[] paths = systemPath.split(File.pathSeparator); // 경로 분할 (; 또는 :)
		
		for (String dir : paths) {
			File javaexe = new File(dir, exename); // 각 경로에 javac.exe 존재하는지 확인
			if (javaexe.exists() && javaexe.isFile()) {
				return javaexe.getAbsolutePath(); // 찾으면 해당 경로 반환
			}
		}
		
		return null; // 못 찾은 경우
	}
	public List<String> extractJarPaths(String optionStr) {
		List<String> jarPaths = new ArrayList<>();
		// OS별 classpath 구분자 결정
		String separator = File.pathSeparator; // Windows: ;  Linux/Mac: :
		
		// 1. -cp 또는 --class-path 처리
		Pattern cpPattern = Pattern.compile("-(cp|classpath|class-path)\\s+\"?([^\"\\s]+)\"?");
		Matcher cpMatcher = cpPattern.matcher(optionStr);
		
		
		if (cpMatcher.find()) {
			String cpValue = cpMatcher.group(2);
			String[] cpEntries = cpValue.split(Pattern.quote(separator));
			
			for (String entry : cpEntries) {
				if (entry.endsWith(".jar")) {
					jarPaths.add(entry);
				}
			}
		}
		
		
		// 2. --module-path 처리
		Pattern modulePathPattern = Pattern.compile("--module-path\\s+\"?([^\\s\"]+)\"?");
		Matcher moduleMatcher = modulePathPattern.matcher(optionStr);
		
		
		if (moduleMatcher.find()) {
			String modulePath = moduleMatcher.group(1);
			File dir = new File(modulePath);
			//System.out.println(dir.toString());
			// 2. 해당 디렉토리에서 .jar 파일만 추출
			if (dir.exists() && dir.isDirectory()) {
				File[] jarFiles = dir.listFiles((d, name) -> name.endsWith(".jar"));
				if (jarFiles != null) {
					for (File jar : jarFiles) {
						jarPaths.add(jar.getAbsolutePath().toString());
					}
				}
			}
		}
		
		
		return jarPaths;
	}
	
	private final ScheduledExecutorService scheduler1 = Executors.newScheduledThreadPool(1);
	private final ScheduledExecutorService scheduler2 = Executors.newScheduledThreadPool(2);
	private final ScheduledExecutorService scheduler3 = Executors.newScheduledThreadPool(3);
	private final ScheduledExecutorService scheduler4 = Executors.newScheduledThreadPool(4);
	// 이전 작업을 취소하기 위한 참조
	private java.util.concurrent.ScheduledFuture<?> futureTask1;
	private java.util.concurrent.ScheduledFuture<?> futureTask2;
	private java.util.concurrent.ScheduledFuture<?> futureTask3;
	private java.util.concurrent.ScheduledFuture<?> futureTask4;
	private int lastHash = 0;
	
	List<String> totalTempList = new ArrayList<>();
	public void onTextChanged(String newText) {
		// 이전 예약된 작업이 있으면 취소
		if (futureTask1 != null && !futureTask1.isDone()) {
			futureTask1.cancel(false);
		}
		if (futureTask2 != null && !futureTask2.isDone()) {
			futureTask2.cancel(false);
		}
		if (futureTask3 != null && !futureTask3.isDone()) {
			futureTask3.cancel(false);
		}
		if (futureTask4 != null && !futureTask4.isDone()) {
			futureTask4.cancel(false);
		}
		
		// 700ms 뒤에 실행하도록 예약
		futureTask1 = scheduler1.schedule(() -> {
			Platform.runLater(() -> {
				if(usePopup)
				{
					if(!typeTempStr.trim().isEmpty())
					{
						int caretPos = codeArea2.getCaretPosition();
						Optional<Bounds> caretBounds = codeArea2.getCaretBounds();
						if (caretBounds.isPresent()) {
							Optional<Bounds> charBounds = codeArea2.getCharacterBoundsOnScreen(caretPos,caretPos);
							if (charBounds.isPresent()) {
								Bounds bounds = charBounds.get();
								double x = bounds.getMaxX(); // 커서 오른쪽
								double y = bounds.getMaxY(); // 커서 아래쪽
								//List<String> filtered = classlist.stream().filter(s -> s.startsWith(typeTempStr)).collect(Collectors.toList());
								List<String> filtered =
								totalTempList.stream()
								.filter(s -> s.startsWith(typeTempStr.trim()))	//replace(".", "").
								.collect(Collectors.toCollection(HashSet::new))   // 중복 제거
								.stream()
								.sorted(Comparator.comparingInt(String::length))  // 길이 짧은 순 정렬
								.collect(Collectors.toList());
								
								
								
								//System.out.println("typeTempStr: " + typeTempStr);
								//System.out.println("filtered: " + filtered);
								
								listView.getItems().setAll(filtered);
								popup.getContent().setAll(listView);
								if (!filtered.isEmpty()) {
									popup.show(codeArea2, x, y);
								}else if (filtered.isEmpty()) {
									popup.hide();
								}
								if (isPaste||isRedo) {
									popup.hide();
								}
								
								
							}
						}
					}else {
						popup.hide();
						
					}
				}
				if(isPaste||isRedo)
				{
					isPaste = false; // 다시 초기화
					isRedo = false; // 다시 초기화
					//codeArea2.estimatedScrollXProperty().setValue(getScrollX);
					//codeArea2.estimatedScrollYProperty().setValue(getScrollY);
					if(useStyle)
					{
						// 기존 스타일시트 제거
						mainscene.getStylesheets().clear();
						// 다시 추가 (갱신된 style.css 반영)
						
						mainscene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // 스타일시트 연결 (선택)
						codeArea2.getStylesheets().clear();
						codeArea2.getStylesheets().add("bgstyle.css");
						// CSS 강제 적용
						mainscene.getRoot().applyCss();
						//codeArea2.setStyleSpans(0, computeHighlighting(newText));
					}
				}
			});
		}, 700, TimeUnit.MILLISECONDS);
		// 3s 뒤에 실행하도록 예약
		futureTask2 = scheduler2.schedule(() -> {
			Platform.runLater(() -> {
				if(useStyle)
				{
					/*int totalLines = codeArea2.getParagraphs().size();
					for (int n = 0; n < codeArea2.getParagraphs().size(); n++) {
					String lineText = codeArea2.getParagraph(n).getText();
					int start = codeArea2.getAbsolutePosition(n, 0); // 줄 시작 offset
					StyleSpans<Collection<String>> spans = computeHighlighting(lineText);
					
					// spans 총 길이가 lineText.length()와 같아야 함
					codeArea2.setStyleSpans(start, spans);
					
					//codeArea2.setStyleSpans(start, computeHighlighting(lineText));
					}*/
					
					
				}
			});
		}, 3, TimeUnit.SECONDS);
		// 7s 뒤에 실행하도록 예약
		futureTask3 = scheduler3.schedule(() -> {
			Platform.runLater(() -> {
				
				// 실제 분석 로직
				//System.out.println("분석 실행: " + newText);
				
				
				if(usePopup)
				{
					//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
					int caretPos = codeArea2.getCaretPosition();
					/*if(statusNow.equals("import")&&!typeTempStr.isEmpty())
					{
					Optional<Bounds> caretBounds = codeArea2.getCaretBounds();
					if (caretBounds.isPresent()) {
					Optional<Bounds> charBounds = codeArea2.getCharacterBoundsOnScreen(caretPos,caretPos);
					if (charBounds.isPresent()) {
					Bounds bounds = charBounds.get();
					double x = bounds.getMaxX(); // 커서 오른쪽
					double y = bounds.getMaxY(); // 커서 아래쪽
					//List<String> filtered = classlist.stream().filter(s -> s.startsWith(typeTempStr)).collect(Collectors.toList());
					List<String> filtered = classlist.stream().filter(s -> s.toLowerCase().startsWith(typeTempStr.trim().toLowerCase())).collect(Collectors.toList());
					
					//System.out.println("typeTempStr: " + typeTempStr);
					//System.out.println("filtered: " + filtered);
					
					listView.getItems().setAll(filtered);
					popup.getContent().setAll(listView);
					popup.show(codeArea2, x, y);
					}
					}
					}*/
					//int,char,float,double,byte,long,short,boolean,enum
					
					if(true)
					{
						List<String> importLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("import") && line.endsWith(";")).collect(Collectors.toList());
						importLines.addAll(importlist);
						int newHash = importLines.hashCode();
						
						if(lastHash != newHash)
						{
							lastHash = newHash;
							tempImportList = new ArrayList<>(importLines);
							useClassList = new ArrayList<>(setUseClassList(tempImportList));
							
							//tempImportList.clear();
							//tempImportList.addAll(importLines);
							//useClassList.clear();
							//useClassList.addAll(setUseClassList(tempImportList));
							//fileClassList.clear();
							//fileClassList.addAll(setFileClassList());
						}
						fileClassList = new ArrayList<>(setFileClassList());
						
						/*List<String> intLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("int") && line.endsWith(";")).collect(Collectors.toList());
						List<String> charLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("char") && line.endsWith(";")).collect(Collectors.toList());
						List<String> floatLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("float") && line.endsWith(";")).collect(Collectors.toList());
						List<String> doubleLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("double") && line.endsWith(";")).collect(Collectors.toList());
						List<String> byteLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("byte") && line.endsWith(";")).collect(Collectors.toList());
						List<String> longLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("long") && line.endsWith(";")).collect(Collectors.toList());
						List<String> shortLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("short") && line.endsWith(";")).collect(Collectors.toList());
						List<String> booleanLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("boolean") && line.endsWith(";")).collect(Collectors.toList());
						List<String> enumLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("enum") && line.endsWith(";")).collect(Collectors.toList());*/
						
						List<String> classLines = new ArrayList<>();
						//importlist.clear();
						//useImportClassInit();
						//importlist.addAll(setUseClassList(importLines));
						/*for(String temp : setUseClassList(importLines))
						{
						List<String> tempLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith(temp) && line.endsWith(";")).collect(Collectors.toList());
						classLines.addAll(tempLines);
						
						}*/
						totalTempList = new ArrayList<>();
						
						
						totalTempList.addAll(keylist);
						/*totalTempList.addAll(setUseIntValList(intLines));
						totalTempList.addAll(setUseCharValList(charLines));
						totalTempList.addAll(setUseFloatValList(floatLines));
						totalTempList.addAll(setUseDoubleValList(doubleLines));
						totalTempList.addAll(setUseByteValList(byteLines));
						totalTempList.addAll(setUseLongValList(longLines));
						totalTempList.addAll(setUseShortValList(shortLines));
						totalTempList.addAll(setUseBooleanValList(booleanLines));
						totalTempList.addAll(setUseEnumValList(enumLines));*/
						totalTempList.addAll(useClassList);
						totalTempList.addAll(fileClassList);
						//System.out.println(setFileClassList());
						//totalTempList.addAll(setUseMethodList(importLines));
						totalTempList.addAll(setUseClassValList(classLines,useClassList));
						totalTempList.addAll(jarClassList);
						totalTempList.addAll(exlsClassList);
						/*Optional<Bounds> caretBounds = codeArea2.getCaretBounds();
						if (caretBounds.isPresent()) {
						Optional<Bounds> charBounds = codeArea2.getCharacterBoundsOnScreen(caretPos,caretPos);
						if (charBounds.isPresent()) {
						Bounds bounds = charBounds.get();
						double x = bounds.getMaxX(); // 커서 오른쪽
						double y = bounds.getMaxY(); // 커서 아래쪽
						//List<String> filtered = classlist.stream().filter(s -> s.startsWith(typeTempStr)).collect(Collectors.toList());
						List<String> filtered = totalTempList.stream().filter(s -> s.toLowerCase().startsWith(typeTempStr.trim().toLowerCase())).collect(Collectors.toList());
						
						//System.out.println("typeTempStr: " + typeTempStr);
						//System.out.println("filtered: " + filtered);
						
						listView.getItems().setAll(filtered);
						popup.getContent().setAll(listView);
						if (!filtered.isEmpty()) {
						popup.show(codeArea2, x, y);
						}else if (filtered.isEmpty()) {
						popup.hide();
						}
						
						}
						}*/
						//System.out.println(useStyleClass);
					}
					//이건 거의 안씀.....period이건 거의 안씀...ㅋㅋㅋ
					/*else if(statusNow.equals("period") && !typeTempStr.trim().isEmpty())
					{
					List<String> importLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("import") && line.endsWith(";")).collect(Collectors.toList());
					importLines.addAll(importlist);
					List<String> totalTempList = new ArrayList<>();
					totalTempList.addAll(setUseMethodList(importLines));
					totalTempList.addAll(setFileClassList());
					Optional<Bounds> caretBounds = codeArea2.getCaretBounds();
					if (caretBounds.isPresent()) {
					Optional<Bounds> charBounds = codeArea2.getCharacterBoundsOnScreen(caretPos,caretPos);
					if (charBounds.isPresent()) {
					Bounds bounds = charBounds.get();
					double x = bounds.getMaxX(); // 커서 오른쪽
					double y = bounds.getMaxY(); // 커서 아래쪽
					//List<String> filtered = classlist.stream().filter(s -> s.startsWith(typeTempStr)).collect(Collectors.toList());
					List<String> filtered = totalTempList.stream().filter(s -> s.toLowerCase().startsWith(typeTempStr.replace(".","").trim().toLowerCase())).collect(Collectors.toList());
					
					//System.out.println("typeTempStr: " + typeTempStr);
					//System.out.println("filtered: " + filtered);
					
					listView.getItems().setAll(filtered);
					popup.getContent().setAll(listView);
					if (!filtered.isEmpty()) {
					popup.show(codeArea2, x, y);
					}else if (filtered.isEmpty()) {
					popup.hide();
					}
					
					}
					}
					}*/
				}//usePopup
				
				
			});//platform.runlater
		},7, TimeUnit.SECONDS);
		// 12s 뒤에 실행하도록 예약
		int style_time = 0;
		int totalLines = codeArea2.getParagraphs().size();
		if(totalLines<20 && totalLines >=0)
		{
			style_time = 1;
		}else if(totalLines >= 20 && totalLines < 80)
		{
			style_time = 2;
		}else if(totalLines >= 80 && totalLines < 200)
		{
			style_time = 4;
		}else if(totalLines >= 200 && totalLines < 800)
		{
			style_time = 8;
		}else if(totalLines >= 800 && totalLines < 2000)
		{
			style_time = 16;
		}else if(totalLines >= 2000 && totalLines < 8000)
		{
			style_time = 32;
		}else if(totalLines >=8000)
		{
			style_time = 64;
		}
		
		futureTask4 = scheduler4.schedule(() -> {
			Platform.runLater(() -> {
				onInitialText(newText);
			});
		}, style_time , TimeUnit.SECONDS);
	}
	
	public void onInitialText(String newText) {
		if(usePopup)
		{
			
			List<String> importLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("import") && line.endsWith(";")).collect(Collectors.toList());
			importLines.addAll(importlist);
			int newHash = importLines.hashCode();
			
			if(lastHash != newHash)
			{
				lastHash = newHash;
				tempImportList = new ArrayList<>(importLines);
				useClassList = new ArrayList<>(setUseClassList(tempImportList));
				
				
			}
			fileClassList = new ArrayList<>(setFileClassList());
			/*List<String> intLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("int") && line.endsWith(";")).collect(Collectors.toList());
			List<String> charLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("char") && line.endsWith(";")).collect(Collectors.toList());
			List<String> floatLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("float") && line.endsWith(";")).collect(Collectors.toList());
			List<String> doubleLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("double") && line.endsWith(";")).collect(Collectors.toList());
			List<String> byteLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("byte") && line.endsWith(";")).collect(Collectors.toList());
			List<String> longLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("long") && line.endsWith(";")).collect(Collectors.toList());
			List<String> shortLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("short") && line.endsWith(";")).collect(Collectors.toList());
			List<String> booleanLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("boolean") && line.endsWith(";")).collect(Collectors.toList());
			List<String> enumLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("enum") && line.endsWith(";")).collect(Collectors.toList());*/
			
			List<String> classLines = new ArrayList<>();
			
			totalTempList = new ArrayList<>();
			
			
			totalTempList.addAll(keylist);
			/*totalTempList.addAll(setUseIntValList(intLines));
			totalTempList.addAll(setUseCharValList(charLines));
			totalTempList.addAll(setUseFloatValList(floatLines));
			totalTempList.addAll(setUseDoubleValList(doubleLines));
			totalTempList.addAll(setUseByteValList(byteLines));
			totalTempList.addAll(setUseLongValList(longLines));
			totalTempList.addAll(setUseShortValList(shortLines));
			totalTempList.addAll(setUseBooleanValList(booleanLines));
			totalTempList.addAll(setUseEnumValList(enumLines));*/
			totalTempList.addAll(useClassList);
			totalTempList.addAll(fileClassList);
			
			totalTempList.addAll(setUseClassValList(classLines,useClassList));
			totalTempList.addAll(jarClassList);
			totalTempList.addAll(exlsClassList);
		}
		
		if(useStyle)
		{
			codeArea2.setStyleSpans(0, computeHighlighting(newText));
		}
		
		
		
	}
	//0.0041A추가
	private void cleanProcess() {
		Process p = runProcessList.peek();
		
		// 큐가 비어있으면 종료
		if (p == null) {
			return;
		}
		
		// 프로세스가 종료된 상태라면 제거하고 다음 요소 검사
		if (!p.isAlive()) {
			runProcessList.poll(); // 죽은 프로세스 제거
			cleanProcess();        // 재귀 호출로 다음 요소 검사
		}
		
		// 살아있는 프로세스를 만나면 종료
	}
	
	//0.0032추가....
	public List<String> setAddAgoList(String path, List<String> agolist)
	{
		if (agolist.contains(path)) {
			agolist.remove(path);   // 기존 위치에서 제거
		}
		agolist.add(path);
		return agolist;
		
	}
	public void setSaveAgoFile(List<String> agolist)
	{
		File file = new File("agoList.txt"); // 저장할 파일 이름
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			for (String line : agolist) {
				bw.write(line);
				bw.newLine(); // 줄바꿈
			}
		} catch(IOException e) {
			e.printStackTrace();
			
		}
	}
	
	public List<String> getAgoFile()
	{
		List<String> list = new ArrayList<>();
		File file = new File("agoList.txt");
		// 파일이 없으면 빈 리스트 반환
		if (!file.exists()) {
			return list;
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private int findMatchingBrace(String text, int pos) {
		if (pos < 0 || pos >= text.length()) return -1;
		
		char target = text.charAt(pos);
		if (target != '{' && target != '}') return -1;
		
		boolean inString = false;
		boolean inChar = false;
		boolean inLineComment = false;
		boolean inBlockComment = false;
		boolean escape = false;
		
		// -------------------------
		// 1) pos 이전까지 상태 계산
		// -------------------------
		for (int i = 0; i < pos; i++) {
			char c = text.charAt(i);
			
			if (inLineComment) {
				if (c == '\n' || c == '\r') inLineComment = false;
				continue;
			}
			
			if (inBlockComment) {
				if (c == '*' && i + 1 < text.length() && text.charAt(i + 1) == '/') {
					inBlockComment = false;
					i++;
				}
				continue;
			}
			
			if (escape) {
				escape = false;
				continue;
			}
			if (c == '\\') {
				escape = true;
				continue;
			}
			
			if (!inChar && c == '"') {
				inString = !inString;
				continue;
			}
			
			if (!inString && c == '\'') {
				inChar = !inChar;
				continue;
			}
			
			if (inString || inChar) continue;
			
			if (c == '/' && i + 1 < text.length()) {
				char next = text.charAt(i + 1);
				if (next == '/') {
					inLineComment = true;
					i++;
					continue;
				}
				if (next == '*') {
					inBlockComment = true;
					i++;
					continue;
				}
			}
		}
		
		// -------------------------
		// 2) pos가 문자열/주석 안이면 brace 아님
		// -------------------------
		if (inString || inChar || inLineComment || inBlockComment) {
			return -1;
		}
		
		// -------------------------
		// 3) brace 매칭 (전체 스캔)
		// -------------------------
		java.util.Deque<Integer> stack = new java.util.ArrayDeque<>();
		int result = -1;
		
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			
			// 문자열/주석 상태 다시 계산
			if (inLineComment) {
				if (c == '\n' || c == '\r') inLineComment = false;
				continue;
			}
			
			if (inBlockComment) {
				if (c == '*' && i + 1 < text.length() && text.charAt(i + 1) == '/') {
					inBlockComment = false;
					i++;
				}
				continue;
			}
			
			if (escape) {
				escape = false;
				continue;
			}
			if (c == '\\') {
				escape = true;
				continue;
			}
			
			if (!inChar && c == '"') {
				inString = !inString;
				continue;
			}
			
			if (!inString && c == '\'') {
				inChar = !inChar;
				continue;
			}
			
			if (inString || inChar) continue;
			
			if (c == '/' && i + 1 < text.length()) {
				char next = text.charAt(i + 1);
				if (next == '/') {
					inLineComment = true;
					i++;
					continue;
				}
				if (next == '*') {
					inBlockComment = true;
					i++;
					continue;
				}
			}
			
			// brace 처리
			if (c == '{') {
				stack.push(i);
				
				if (i == pos && target == '{') {
					// 나중에 pop될 때 짝이 나옴
				}
				
			} else if (c == '}') {
				if (!stack.isEmpty()) {
					int openPos = stack.pop();
					
					if (i == pos && target == '}') {
						result = openPos;
						break;
					}
					
					if (openPos == pos && target == '{') {
						result = i;
						break;
					}
				}
			}
		}
		
		return result;
	}
	private int tabCleanLength(String text)
	{
		String thiscode = text;
		String[] setcode = thiscode.split("\n");
		tabClean_len = setcode.length;
		tabClean_progress = 0;
		int len = tabClean_len;
		return len;
	}
	int tabClean_len = 0;
	int tabClean_progress = 0;
	private String tabCleanMain(String text)
	{
		ao=false;
		bo=false;
		tab = 0;
		brace =0;
		paren = 0;
		inString = false;   // "
		inChar = false;     // '
		inLineComment = false; // //
		inBlockComment = false; // /* */
		escape = false;     // \
		tab = 0;
		StringBuilder sb = new StringBuilder();
		String thiscode = text;
		String[] setcode = thiscode.split("\n");
		tabClean_len = setcode.length;
		tabClean_progress = 0;
		for(String str : setcode)
		{
			
			String trimmed = str.trim();
			total = tab+brace+paren;
			if (trimmed.endsWith("{")||trimmed.startsWith("{")) {
				//brace--;
			}
			if ((trimmed.startsWith("}")||trimmed.endsWith("}"))&&!inBlockComment) {
				total--;
			}
			//int openCount = str.length() -str.replace("{", "").length();
			//int closeCount = str.length() - str.replace("}", "").length();
			if(lineBrace(str))
			{
				
				total++;
				if(str.trim().startsWith("}"))
				{
					total--;
				}
				
			}
			/*
			if (trimmed.endsWith("{")||trimmed.startsWith("{")) {
			total--;
			}
			if (trimmed.startsWith("}")||trimmed.endsWith("}")) {
			total--;
			}*/
			
			
			
			if(total<0) { total = 0;}
			String tmp ="";
			
			
			for(int i =0; i<total; i++)
			{
				tmp += "\t";
			}
			
			tmp += str.trim();
			tmp+="\n";
			sb.append(tmp);
			//countStr(str);
			int tmpcount = brace;
			int tmpparen = countParen(str);
			brace += countBrace(str);
			if((tmpcount == brace || tmpparen+paren==0))
			{
				paren += tmpparen;
			}
			tabClean_progress++;
			
			
			//bracket += countBracket(str);
		}
		return sb.toString();
	}
	boolean inStringp = false;   // "
	boolean inCharp = false;     // '
	boolean inLineCommentp = false; // //
	boolean inBlockCommentp = false; // /* */
	boolean escapep = false;     // \
	private int countParen(String code)
	{
		int count = 0;
		for(int i = 0;i<code.length();i++)
		{
			char c = code.charAt(i);
			// -------------------------
			// 1) 한 줄 주석 처리
			// -------------------------
			inLineCommentp = false;
			
			// -------------------------
			// 2) 블록 주석 처리
			// -------------------------
			if (inBlockCommentp) {
				if (c == '*' && i + 1 < code.length() && code.charAt(i + 1) == '/') {
					inBlockCommentp = false;
					i++;
				}
				continue;
			}
			
			// -------------------------
			// 3) escape 처리
			// -------------------------
			if (escapep) {
				escapep = false;
				continue;
			}
			if (c == '\\') {
				escapep = true;
				continue;
			}
			
			// -------------------------
			// 4) 문자열 처리
			// -------------------------
			if (!inCharp && c == '"') {
				inStringp = !inStringp;
				continue;
			}
			
			// -------------------------
			// 5) 문자 처리
			// -------------------------
			if (!inStringp && c == '\'') {
				inCharp = !inCharp;
				continue;
			}
			
			// 문자열/문자 안이면 brace 무시
			if (inStringp || inCharp) {
				continue;
			}
			
			// -------------------------
			// 6) 주석 시작 처리
			// -------------------------
			if (c == '/' && i + 1 < code.length()) {
				char next = code.charAt(i + 1);
				
				if (next == '/') {
					inLineCommentp = true;
					i++;
					break;
				}
				if (next == '*') {
					inBlockCommentp = true;
					i++;
					continue;
				}
			}
			if(c=='(') count++;
			if(c==')') count--;
			
			
		}
		return count;
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
			
			inLineComment = false;
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
	boolean inString2 = false;   // "
	boolean inChar2 = false;     // '
	boolean inLineComment2 = false; // //
	boolean inBlockComment2 = false; // /* */
	boolean escape2 = false;     // \
	private boolean lineBrace(String code) {
		int count = 0;
		boolean  c1 = false, c2 = false;
		for (int i = 0; i < code.length(); i++) {
			char c = code.charAt(i);
			
			// -------------------------
			// 1) 한 줄 주석 처리
			// -------------------------
			
			inLineComment2 = false;
			// -------------------------
			// 2) 블록 주석 처리
			// -------------------------
			if (inBlockComment2) {
				if (c == '*' && i + 1 < code.length() && code.charAt(i + 1) == '/') {
					inBlockComment2 = false;
					i++;
				}
				continue;
			}
			
			// -------------------------
			// 3) escape 처리
			// -------------------------
			if (escape) {
				escape2 = false;
				continue;
			}
			if (c == '\\') {
				escape2 = true;
				continue;
			}
			
			// -------------------------
			// 4) 문자열 처리
			// -------------------------
			if (!inChar2 && c == '"') {
				inString2 = !inString2;
				continue;
			}
			
			// -------------------------
			// 5) 문자 처리
			// -------------------------
			if (!inString2 && c == '\'') {
				inChar2 = !inChar2;
				continue;
			}
			
			// 문자열/문자 안이면 brace 무시
			if (inString2 || inChar2) {
				continue;
			}
			
			// -------------------------
			// 6) 주석 시작 처리
			// -------------------------
			if (c == '/' && i + 1 < code.length()) {
				char next = code.charAt(i + 1);
				
				if (next == '/') {
					inLineComment2 = true;
					i++;
					break;
				}
				if (next == '*') {
					inBlockComment2 = true;
					i++;
					continue;
				}
			}
			
			// -------------------------
			// 7) brace 카운트
			// -------------------------
			if (c == '{') {
				count++;
				c1= true;
			}
			if (c == '}') {
				count--;
				c2 = true;
			}
			
		}
		return (c1&&c2);
	}
	//0.0043추가.....
	private String handlePath(int index, String runPathStr) {
		String[] result = {""};
		//System.out.println("handlePathmainpath : " + runPathStr);
		String runpath = ((runPathStr == null) ? "" : runPathStr).trim();
		
		Stage optionStage = new Stage();
		optionStage.setTitle("옵션");
		optionStage.initModality(Modality.APPLICATION_MODAL);
		
		VBox root = new VBox(10);
		root.setPadding(new Insets(10));
		
		// ---------------------------
		// Path 라벨
		// ---------------------------
		String labelStr = "";
		switch(index) {
			case 1: /* path1 처리 */
			
			labelStr = "(jar파일)";
			break;
			case 2: /* path2 처리 */
			
			labelStr = "(java파일)";
			break;
			case 3: /* path3 처리 */
			
			labelStr = "(jar파일)";
			break;
			case 4: /* path4 처리 */
			
			labelStr = "(class파일)";
			break;
		}
		Label pathLabel = new Label("Path 옵션"+labelStr);
		
		// ---------------------------
		// ListView + 버튼 6개
		// ---------------------------
		HBox listArea = new HBox(10);
		
		ListView<String> listView = new ListView<>();
		listView.setPrefHeight(150);
		listView.setPrefWidth(200);
		
		VBox btnBox = new VBox(5);
		
		Button btnAddFolder = new Button("폴더추가");
		Button btnAddAll = new Button("모두추가");
		Button btnAdd = new Button("추가");
		Button btnDelete = new Button("삭제");
		Button btnDeleteAll = new Button("모두삭제");
		Button btnUp = new Button("위로");
		Button btnDown = new Button("아래로");
		
		btnBox.getChildren().addAll(
			btnAddFolder, btnAddAll,btnAdd, btnDelete, btnDeleteAll, btnUp, btnDown
			);
		
		listArea.getChildren().addAll(listView, btnBox);
		btnAddFolder.setOnAction(te -> {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle("폴더 선택");
			// 초기 경로 설정
			File initDir = null;
			
			if (runpath != null && !runpath.isEmpty()) {
				initDir = new File(runpath);
			} else if (fileDir != null && !fileDir.isEmpty()) {
				initDir = new File(fileDir);
			} else {
				initDir = new File(System.getProperty("user.dir")); // 현재 경로
			}
			
			// 실제로 존재하는 폴더인지 확인
			if (initDir.exists() && initDir.isDirectory()) {
				directoryChooser.setInitialDirectory(initDir);
			}
			File selectedDir = directoryChooser.showDialog(optionStage);
			
			if (selectedDir != null) {
				String basePath = selectedDir.getAbsolutePath().trim();
				if (basePath.startsWith(runpath)) {
					basePath = basePath.substring(runpath.length());
					if(basePath.startsWith(File.separator))
					{
						basePath = basePath.substring(1);
					}
				}
				listView.getItems().add(basePath);
			}
		});
		btnAddAll.setOnAction(te -> {
			String exStr = "";
			switch(index) {
				case 1: /* path1 처리 */
				
				exStr = ".jar";
				break;
				case 2: /* path2 처리 */
				
				exStr = ".java";
				break;
				case 3: /* path3 처리 */
				
				exStr = ".jar";
				break;
				case 4: /* path4 처리 */
				
				exStr = ".class";
				break;
			}
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle("폴더 선택");
			// 초기 경로 설정
			File initDir = null;
			
			if (runpath != null && !runpath.isEmpty()) {
				initDir = new File(runpath);
			} else if (fileDir != null && !fileDir.isEmpty()) {
				initDir = new File(fileDir);
			} else {
				initDir = new File(System.getProperty("user.dir")); // 현재 경로
			}
			
			// 실제로 존재하는 폴더인지 확인
			if (initDir.exists() && initDir.isDirectory()) {
				directoryChooser.setInitialDirectory(initDir);
			}
			File selectedDir = directoryChooser.showDialog(optionStage);
			
			if (selectedDir != null) {
				if (selectedDir.isDirectory()) {
					
					// 폴더면 재귀적으로 다시 탐색
					addAllJarFiles(selectedDir, listView,runpath,exStr);
					
				}
			}
		});
		btnAdd.setOnAction(te -> {
			String exStr = "";
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("파일 또는 폴더 선택");
			switch(index) {
				case 1: /* path1 처리 */
				fileChooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("JAR Files", "*.jar")
					);
				exStr = ".jar";
				break;
				case 2: /* path2 처리 */
				fileChooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("JAVA Files", "*.java")
					);
				exStr = ".java";
				break;
				case 3: /* path3 처리 */
				fileChooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("JAR Files", "*.jar")
					);
				exStr = ".jar";
				break;
				case 4: /* path4 처리 */
				fileChooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("CLASS Files", "*.class")
					);
				exStr = ".class";
				break;
			}
			
			File initDir = null;
			
			if (runpath != null && !runpath.isEmpty()) {
				initDir = new File(runpath);
			} else if (fileDir != null && !fileDir.isEmpty()) {
				initDir = new File(fileDir);
			} else {
				initDir = new File(System.getProperty("user.dir")); // 현재 경로
			}
			
			// 실제로 존재하는 폴더인지 확인
			if (initDir.exists() && initDir.isDirectory()) {
				fileChooser.setInitialDirectory(initDir);
			}
			// 다중 선택 허용
			List<File> selectedFiles = fileChooser.showOpenMultipleDialog(optionStage);
			
			// 파일 선택 안 했으면 종료
			if (selectedFiles == null) return;
			
			for (File f : selectedFiles) {
				
				// 1) 선택한 것이 폴더일 경우 → 폴더 내부 jar 모두 추가
				if (f.isDirectory()) {
					
					// 폴더면 재귀적으로 다시 탐색
					addAllJarFiles(f, listView,runpath,exStr);
					
				} else {
					
					// 2) jar 파일이면 그대로 추가
					if (f.getName().toLowerCase().endsWith(exStr)) {
						String addPath = "";
						if(f.getAbsolutePath().startsWith(runPathStr))
						{
							addPath = f.getAbsolutePath().substring(runPathStr.length());
						} else if (exStr.equals(".class") && f.getAbsolutePath().endsWith(exStr))
						{
							addPath = "";
							
						}else {
							addPath = f.getAbsolutePath();
						}
						if(addPath.startsWith(File.separator))
						{
							addPath = addPath.substring(1);
						}
						if(exStr.equals(".class") && addPath.endsWith(exStr))
						{
							addPath = addPath.substring(0, addPath.length() - exStr.length());
							// 여기서 File.separator → "." 으로 변경
							addPath = addPath.replace(File.separator, ".");
						}
						if(!addPath.isEmpty())
						{
							listView.getItems().add(addPath);
						}
					}
				}
			}
		});
		
		btnDelete.setOnAction(te -> {
			int tindex = listView.getSelectionModel().getSelectedIndex();
			
			// 선택이 없으면 무시
			if (tindex < 0) {
				return;
			}
			
			// 항목 삭제
			listView.getItems().remove(tindex);
			
			// 삭제 후 다시 선택될 인덱스 계산
			int size = listView.getItems().size();
			
			if (size == 0) {
				return; // 더 이상 선택할 항목 없음
			}
			
			// 삭제된 위치에 있는 항목을 다시 선택
			if (tindex >= size) {
				tindex = size - 1; // 마지막 항목 선택
			}
			
			listView.getSelectionModel().select(tindex);
		});
		
		btnDeleteAll.setOnAction(te -> {
			listView.getItems().clear();
		});
		
		btnUp.setOnAction(te -> {
			int tindex = listView.getSelectionModel().getSelectedIndex();
			
			// 선택이 없거나 맨 위면 무시
			if (tindex <= 0) {
				return;
			}
			
			// 현재 항목 가져오기
			String item = listView.getItems().get(tindex);
			
			// 현재 위치에서 제거
			listView.getItems().remove(tindex);
			
			// 한 칸 위에 삽입
			listView.getItems().add(tindex - 1, item);
			
			// 이동한 항목 다시 선택
			listView.getSelectionModel().select(tindex - 1);
		});
		
		btnDown.setOnAction(te -> {
			int tindex = listView.getSelectionModel().getSelectedIndex();
			int size = listView.getItems().size();
			
			// 선택이 없거나 마지막 항목이면 무시
			if (tindex < 0 || tindex >= size - 1) {
				return;
			}
			
			// 현재 항목 가져오기
			String item = listView.getItems().get(tindex);
			
			// 현재 위치에서 제거
			listView.getItems().remove(tindex);
			
			// 한 칸 아래에 삽입
			listView.getItems().add(tindex + 1, item);
			
			// 이동한 항목 다시 선택
			listView.getSelectionModel().select(tindex + 1);
		});
		
		// ---------------------------
		// RadioButton 6개
		// ---------------------------
		Label radioLabel = new Label("구분자 선택");
		
		ToggleGroup tg = new ToggleGroup();
		
		RadioButton r1 = new RadioButton("없음");
		RadioButton r2 = new RadioButton("스페이스");
		RadioButton r3 = new RadioButton(",");
		RadioButton r4 = new RadioButton(":");
		RadioButton r5 = new RadioButton(";");
		
		
		r1.setToggleGroup(tg);
		r2.setToggleGroup(tg);
		r3.setToggleGroup(tg);
		r4.setToggleGroup(tg);
		r5.setToggleGroup(tg);
		
		
		HBox radioBox = new HBox(5, r1, r2, r3, r4, r5);
		r1.setSelected(true);
		String[] tstr = {""};
		tg.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
			if (newToggle == null) return;
			
			RadioButton selected = (RadioButton) newToggle;
			
			switch (selected.getText()) {
				case "없음":     tstr[0]= "";  break;
				case "스페이스": tstr[0]= " "; break;
				case ",":        tstr[0] = ","; break;
				case ":":        tstr[0] = ":"; break;
				case ";":        tstr[0] = ";"; break;
			}
			//System.out.println("선택된 구분자: [" + tstr[0] + "]");
		});
		// ---------------------------
		// 확인 / 취소 버튼
		// ---------------------------
		HBox bottomButtons = new HBox(10);
		bottomButtons.setAlignment(Pos.CENTER_RIGHT);
		
		Button btnOk = new Button("확인");
		Button btnCancel = new Button("취소");
		
		bottomButtons.getChildren().addAll(btnOk, btnCancel);
		btnOk.setOnAction(te -> {
			result[0] = String.join(tstr[0], listView.getItems());
			//System.out.println(result[0]);
			optionStage.close();
		});
		btnCancel.setOnAction(te -> {
			optionStage.close();
		});
		// ---------------------------
		// 전체 레이아웃 구성
		// ---------------------------
		root.getChildren().addAll(
			pathLabel,
			listArea,
			radioLabel,
			radioBox,
			bottomButtons
			);
		
		Scene scene = new Scene(root, 400, 450);
		optionStage.setScene(scene);
		optionStage.showAndWait();
		// index에 따라 다른 결과 처리
		switch(index) {
			case 1: /* path1 처리 */ break;
			case 2: /* path2 처리 */ break;
			case 3: /* path3 처리 */ break;
			case 4: /* path4 처리 */ break;
		}
		return result[0];
	}
	private void addAllJarFiles(File dir, ListView<String> listView,String runPathStr,String exStr) {
		if (dir == null || !dir.exists()) return;
		
		File[] files = dir.listFiles();
		if (files == null) return;
		
		for (File f : files) {
			if (f.isDirectory()) {
				// 폴더면 재귀적으로 다시 탐색
				addAllJarFiles(f, listView,runPathStr,exStr);
			} else {
				// jar 파일이면 추가
				if (f.getName().toLowerCase().endsWith(exStr)) {
					String addPath = "";
					if(f.getAbsolutePath().startsWith(runPathStr))
					{
						addPath = f.getAbsolutePath().substring(runPathStr.length());
					} else if (exStr.equals(".class") && f.getAbsolutePath().endsWith(exStr))
					{
						addPath = "";
						
					}else {
						addPath = f.getAbsolutePath();
					}
					if(addPath.startsWith(File.separator))
					{
						addPath = addPath.substring(1);
					}
					if(exStr.equals(".class") && addPath.endsWith(exStr))
					{
						addPath = addPath.substring(0, addPath.length() - exStr.length());
						// 여기서 File.separator → "." 으로 변경
						addPath = addPath.replace(File.separator, ".");
						
					}
					if(!addPath.isEmpty()) {
						listView.getItems().add(addPath);
					}
				}
			}
		}
	}
	private void tab_OpenFile(File file) throws IOException
	{
		
		// 1. 이미 열린 탭인지 확인
		for (Tab t : tabPane.getTabs()) {
			TabInfo opened = (TabInfo) t.getUserData();
			if (opened != null && opened.file.getAbsolutePath().equals(file.getAbsolutePath())) {
				// 이미 열려 있는 파일 → 그 탭 선택하고 종료
				// 그 탭 선택
				tabPane.getSelectionModel().select(t);
				
				// 파일 내용 다시 읽기
				opened.encoding = EncodingDetector.detectEncoding(file);
				Charset charsetC = Charset.forName(opened.encoding);
				//String newContent = Files.readString(file.toPath(),charsetC);
				byte[] bytes = Files.readAllBytes(file.toPath());
				String newContent = new String(bytes, charsetC);
				//codeArea2.replaceText(newContent);
				
				// TabInfo 갱신
				opened.content = newContent;
				opened.modified = false;
				
				//System.out.println(opened.encoding);
				// CodeArea 내용 갱신
				//codeArea2.replaceText(newContent);
				
				// 라벨 갱신
				pathLabel.setText(
					"경로 : " + opened.file.getAbsolutePath() +
					" | encoding : " + opened.encoding
					);
				return;
			}
		}
		
		// 2. 새로 열기
		
		
		
		
		TabInfo info = new TabInfo();
		info.file = file;
		info.fileName = file.getName();
		info.fileDir = file.getParent();
		
		info.modified = false;
		
		try{
			info.encoding = EncodingDetector.detectEncoding(file);
			
		}catch(IOException mie)
		{
			mie.printStackTrace();
		}
		Charset charsetC = Charset.forName(info.encoding);
		byte[] bytes = Files.readAllBytes(file.toPath());
		String content = new String(bytes, charsetC);
		info.content = content;
		Tab tab = new Tab();
		Label tablabel = new Label(info.fileName);
		Button closeBtn = new Button("x");
		HBox htabbox = new HBox();
		HBox.setHgrow(tablabel, Priority.ALWAYS);
		tablabel.setMaxWidth(Double.MAX_VALUE);
		
		htabbox.getChildren().addAll(tablabel, closeBtn);
		//htabbox.setAlignment(Pos.CENTER_LEFT);
		
		closeBtn.setOnAction(e -> {
			// 저장 여부 묻기
			// 저장
			tabPane.getSelectionModel().select(tab);
			String currentCode = codeArea2.getText();
			Tab closingTab = (Tab) tabPane.getSelectionModel().getSelectedItem();   // ← 닫히는 탭
			TabInfo info2 = (TabInfo) closingTab.getUserData();
			if (info2.modified) {
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("파일 저장");
				alert.setHeaderText(null);
				alert.setContentText("파일 '" + info.fileName + "' 을(를) 저장하시겠습니까?");
				
				ButtonType saveBtn = new ButtonType("확인");
				ButtonType cancelBtn = new ButtonType("취소", ButtonBar.ButtonData.CANCEL_CLOSE);
				cancelBtn = new ButtonType("취소");
				alert.getButtonTypes().setAll(saveBtn, cancelBtn);
				
				Optional<ButtonType> result = alert.showAndWait();
				
				if (result.isPresent() && result.get() == saveBtn) {
					// 확인 눌렀을 때 → 저장은 네가 처리
					// 예: saveFile(info);
					typeTempStr = "";
					typepos = 0;
					if (popup.isShowing()) {
						
						popup.hide();
					}
					//System.gc();
					File selectedFile;
					String selectedEncoding;
					if(fileName.isEmpty())
					{
						FileChooser fileChooser = new FileChooser();
						fileChooser.setTitle("Save Java File");
						fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Files", "*.java"));
						File initialDir = new File(dirFileSet());
						if (initialDir.exists()) {
							fileChooser.setInitialDirectory(initialDir);
						}else
						{
							fileChooser.setInitialDirectory(new File("."));
							fileDir = ".";
						}
						
						// 인코딩 선택 UI
						List<String> encodings = Arrays.asList("UTF-8", "MS949", "EUC-KR", "ISO-8859-1");
						ChoiceDialog<String> dialog = new ChoiceDialog<>("UTF-8", encodings);
						dialog.setTitle("Select Encoding");
						dialog.setHeaderText("Choose file encoding");
						dialog.setContentText("Encoding:");
						
						Optional<String> result0 = dialog.showAndWait();
						if (!result0.isPresent()) {
							return; // 사용자가 취소
						}
						
						selectedEncoding = result0.get();
						selectedFile = fileChooser.showSaveDialog(null);
					}else{
						String fn = fileDir + File.separator + fileName;
						selectedFile = new File(fn);
						selectedEncoding = file_Encoding;
					}
					if (selectedFile != null) {
						try {
							String rawName = selectedFile.getName();
							// 현재 앱 실행 디렉토리
							String appDir = new File("").getAbsolutePath();
							if(fileDir.equals(appDir) && fileName.equals("Welcome.txt"))
							{
								
							}else if (!rawName.toLowerCase().endsWith(".java")) {
								selectedFile = new File(selectedFile.getParent(), rawName + ".java");
							}
							fileDir = selectedFile.getParent();
							fileName = selectedFile.getName();
							file_Encoding = selectedEncoding;
							modified = false;
							String filepath = "javaEditerPath.txt";
							Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
							// 코드 저장
							String code =  currentCode;
							Charset charset = Charset.forName(file_Encoding);
							Files.write(selectedFile.toPath(), code.getBytes(charset));
							//pathLabel.setText("경로 : " + selectedFile.getAbsolutePath());
							consoleArea.appendText("저장 완료: " + selectedFile.getAbsolutePath() + "\n");
							
						} catch (Exception ex) {
							consoleArea.appendText("저장 오류: " + ex.getMessage() + "\n");
						}
						
						agoFileList = getAgoFile();
						agoFileList = setAddAgoList(selectedFile.getAbsolutePath(),agoFileList);
						setSaveAgoFile(agoFileList);
					}
				}
			}else{
				
			}
			tabPane.getTabs().remove(closingTab);
		});
		//tab.setGraphic(hbox);
		tab.setGraphic(htabbox);
		tab.setClosable(false);
		tab.setUserData(info);
		//codeArea2.replaceText(info.content);//이건 넣지 말것...ㅋㅋㅋ
		tabPane.getTabs().add(tab);
		tabPane.getSelectionModel().select(tab);
		
		String labelStr = "경로 : " + info.file.getAbsolutePath()
		+ " | encoding : " + info.encoding;
		pathLabel.setText(labelStr);
		SaveInfoFile();
	}
	private void SaveTabPathInfo(List<String> pathlist) {
		// 현재 실행 경로 기준 파일 객체 생성
		File file = new File("TabCurrentPathInfo.txt");
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			
			// 첫 줄 고정 문구
			writer.write("Save to open tab information!");
			writer.newLine();
			
			// pathlist 내용을 한 줄씩 저장
			for (String path : pathlist) {
				writer.write(path);
				writer.newLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void LoadTabPathInfo() {
		File infoFile = new File("TabCurrentPathInfo.txt");
		
		// 파일이 없으면 생성만 하고 종료
		if (!infoFile.exists()) {
			try {
				infoFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		try (BufferedReader reader = new BufferedReader(new FileReader(infoFile))) {
			
			// 첫 줄 읽기
			String firstLine = reader.readLine();
			if (firstLine == null || !firstLine.equals("Save to open tab information!")) {
				// 형식이 맞지 않으면 아무 것도 하지 않음
				return;
			}
			
			// 두 번째 줄부터 파일 경로 읽기
			String path;
			while ((path = reader.readLine()) != null) {
				path = path.trim();
				if (path.isEmpty()) continue;
				
				// .java 파일만 열기
				if (path.toLowerCase().endsWith(".java")) {
					File f = new File(path);
					if (f.exists()) {
						try {
							tab_OpenFile(f);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			
			
			
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 스타일 강제 적용
		
		PauseTransition delay = new PauseTransition(Duration.seconds(1));
		delay.setOnFinished(e -> {
			String code = codeArea2.getText();
			onInitialText(code);
		});
		delay.play();
		
	}
	
	private void SaveInfoFile()
	{
		fileDir = ((TabInfo)tabPane.getSelectionModel().getSelectedItem().getUserData()).fileDir;
		fileName = ((TabInfo)tabPane.getSelectionModel().getSelectedItem().getUserData()).fileName;
		file_Encoding = ((TabInfo)tabPane.getSelectionModel().getSelectedItem().getUserData()).encoding;
		modified = ((TabInfo)tabPane.getSelectionModel().getSelectedItem().getUserData()).modified;
		//System.out.println("tabfileName : " + fileName);
	}
	private void LoadInfoFile(File file)
	{
		if (file == null) return;
		
		
		
		
		// 2. TabInfo 생성
		TabInfo info = new TabInfo();
		info.file = file;
		info.fileName = fileName;
		info.fileDir = fileDir; // null 방지
		info.content = codeArea2.getText();
		info.modified = false;
		info.pos = 0;
		info.scrollX = codeArea2.estimatedScrollXProperty().getValue();
		info.scrollY = codeArea2.estimatedScrollYProperty().getValue();
		try{
			info.encoding = EncodingDetector.detectEncoding(file);
			
		}catch(IOException mie)
		{
			mie.printStackTrace();
		}
		
		
		
		
		// 4. pathLabel 업데이트
		pathLabel.setText(
			"경로 : " + info.file.getAbsolutePath() +
			" | encoding : " + info.encoding
			);
		
		// 5. 현재 선택된 탭에 info 저장
		Tab selected = tabPane.getSelectionModel().getSelectedItem();
		if (selected != null) {
			selected.setUserData(info);
			//selected.setText(info.fileName); // 탭 제목도 갱신
			HBox box = (HBox) selected.getGraphic();
			Label label = (Label) box.getChildren().get(0); // 첫 번째가 Label
			label.setText(info.fileName);
		}
		
		
	}
	
	
}
