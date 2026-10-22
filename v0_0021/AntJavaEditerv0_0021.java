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
import javafx.scene.layout.Priority;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import org.fxmisc.flowless.VirtualizedScrollPane;

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
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;
	
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import AJEStruct.*;


public class AntJavaEditerv0_0021 extends Application {
	CodeArea codeArea2;
	Popup popup;
	ListView<String> listView;
	Label pathLabel;
	String fileDir = ".";
	String fileName = "";
	List<String> importlist = new ArrayList<>();
	List<String> keylist = new ArrayList<>();
	List<String> classlist = new ArrayList<>();
	List<String> aclasslist = new ArrayList<>();
	String typeTempStr="";
	List<varSave> varlist = new ArrayList<>();
	String statusNow = "";
	int typepos=0;
	String[] optionStr = new String[7];
	boolean optionUseb = false;
    @Override
    public void start(Stage primaryStage) {
		keyListInit();
		classListInit();
		aclassListInit();
		useImportClassInit();
        
		codeArea2 = new CodeArea();
		popup = new Popup();
		listView = new ListView<>();
		// 경로 라벨
		pathLabel = new Label("경로 : ");

		codeArea2.setParagraphGraphicFactory(LineNumberFactory.get(codeArea2));
		codeArea2.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");
		codeArea2.setMaxWidth(Double.MAX_VALUE);
		codeArea2.setMaxHeight(Double.MAX_VALUE);
		
		// 스크롤 가능한 컨테이너로 감싸기
		VirtualizedScrollPane<CodeArea> codeScrollPane = new VirtualizedScrollPane<>(codeArea2);
		HBox.setHgrow(codeScrollPane, Priority.ALWAYS);

		// 실시간 문법 강조
		codeArea2.textProperty().addListener((obs, oldText, newText) -> {
			codeArea2.setStyleSpans(0, computeHighlighting(newText, keylist,classlist,aclasslist));
			
			int caretPos = codeArea2.getCaretPosition();
			String typedChar = "";
			if (newText.length() > oldText.length() && caretPos > 0) {
				typedChar = newText.substring(caretPos - 1, caretPos);
				
				if (typedChar.matches("[\\w]")) { // 알파벳, 숫자, 언더스코어만 허용
					typeTempStr += typedChar;
					typepos++;
				}

				System.out.println("typeTempStr : " + typeTempStr + "  typepos : " + typepos);
			}
			if(statusNow.equals("import")&&!typeTempStr.isEmpty())
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
			}
			//int,char,float,double,byte,long,short,boolean,enum
			
			if(statusNow.equals("code") && !typeTempStr.trim().isEmpty())
			{
			List<String> importLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("import") && line.endsWith(";")).collect(Collectors.toList());
			importLines.addAll(importlist);
			List<String> intLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("int") && line.endsWith(";")).collect(Collectors.toList());
			List<String> charLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("char") && line.endsWith(";")).collect(Collectors.toList());
			List<String> floatLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("float") && line.endsWith(";")).collect(Collectors.toList());
			List<String> doubleLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("double") && line.endsWith(";")).collect(Collectors.toList());
			List<String> byteLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("byte") && line.endsWith(";")).collect(Collectors.toList());
			List<String> longLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("long") && line.endsWith(";")).collect(Collectors.toList());
			List<String> shortLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("short") && line.endsWith(";")).collect(Collectors.toList());
			List<String> booleanLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("boolean") && line.endsWith(";")).collect(Collectors.toList());
			List<String> enumLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith("enum") && line.endsWith(";")).collect(Collectors.toList());
			
			List<String> classLines = new ArrayList<>();
			for(String temp : setUseClassList(importLines))
			{
				List<String> tempLines = Arrays.stream(newText.split("\n")).map(String::trim).filter(line -> line.startsWith(temp) && line.endsWith(";")).collect(Collectors.toList());
				classLines.addAll(tempLines);

			}
			List<String> totalTempList = new ArrayList<>();
			List<String> useClassList = setUseClassList(importLines);
			totalTempList.addAll(keylist);
			totalTempList.addAll(setUseIntValList(intLines));
			totalTempList.addAll(setUseCharValList(charLines));
			totalTempList.addAll(setUseFloatValList(floatLines));
			totalTempList.addAll(setUseDoubleValList(doubleLines));
			totalTempList.addAll(setUseByteValList(byteLines));
			totalTempList.addAll(setUseLongValList(longLines));
			totalTempList.addAll(setUseShortValList(shortLines));
			totalTempList.addAll(setUseBooleanValList(booleanLines));
			totalTempList.addAll(setUseEnumValList(enumLines));
			totalTempList.addAll(useClassList);
			totalTempList.addAll(setFileClassList());
			totalTempList.addAll(setUseMethodList(importLines));
			totalTempList.addAll(setUseClassValList(classLines,useClassList));
				Optional<Bounds> caretBounds = codeArea2.getCaretBounds();
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
				}
			
			}else if(statusNow.equals("period") && !typeTempStr.trim().isEmpty())
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
			}
		});
		codeArea2.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				int currentParagraph = codeArea2.getCurrentParagraph();
				String previousLine = currentParagraph > 0 ? codeArea2.getParagraph(currentParagraph - 1).getText() : "";
				String indent = previousLine.replaceAll("^([\\t ]*).*", "$1");

				Platform.runLater(() -> {
					int caretPosition = codeArea2.getCaretPosition();
					codeArea2.insertText(caretPosition, indent);
				});
				typeTempStr = "";
				typepos = 0;
				statusNow = "code";
			}
			if (event.getCode() == KeyCode.SPACE) {
				int caretPos = codeArea2.getCaretPosition();
				String textBeforeCaret = codeArea2.getText(caretPos-6, caretPos);
				if (textBeforeCaret.matches(".*\\bimport\\s*$")) {
					/*Optional<Bounds> caretBounds = codeArea2.getCaretBounds();
					if (caretBounds.isPresent()) {
						Optional<Bounds> charBounds = codeArea2.getCharacterBoundsOnScreen(caretPos,caretPos);
						if (charBounds.isPresent()) {
							Bounds bounds = charBounds.get();
							double x = bounds.getMaxX(); // 커서 오른쪽
							double y = bounds.getMaxY(); // 커서 아래쪽

							listView.getItems().setAll(classlist);
							popup.getContent().setAll(listView);
							popup.show(codeArea2, x, y);
							statusNow = "import";
						}
						

					}*/
					statusNow = "import";
				}else{
					
					statusNow = "code";	
					
					

				}
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
				if (!typeTempStr.isEmpty()) {
					typeTempStr = typeTempStr.substring(0, typeTempStr.length() - 1);
					typepos=0;
					typeTempStr = "";
					statusNow = "code";
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
				typeTempStr = ".";
				typepos = 0;
				statusNow = "period";	
				
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
				
				

				
			}



			





		});
		codeArea2.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) {
				typeTempStr = "";
				typepos = 0;
				//statusNow = "code";	
				popup.hide();
			}
		});
		listView.setOnKeyPressed(event -> {
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

		});
		listView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				insertSelectedItem();
			}
		});

		
		dirFileSet();
        codeArea2.replaceText("public class \n{\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World!\");\n\t}\n}");
		
        // 실행 버튼
		Button newButton = new Button("New");
		Button loadButton = new Button("Load");
		Button saveButton = new Button("save");
        Button compileButton = new Button("Compile");
        Button runButton = new Button("Run");
		Button optionButton = new Button("Option");
		Button informButton = new Button("inform");
        Button exitButton = new Button("Exit");


        // 콘솔 출력 영역
        TextArea consoleArea = new TextArea();
		//consoleArea.setStyle("-fx-font-family: 'Malgun Gothic';");
		consoleArea.setFont(Font.font("Serif", 14));
		ScrollPane consoleScrollPane = new ScrollPane(consoleArea);
		consoleScrollPane.setFitToWidth(true);
		consoleScrollPane.setFitToHeight(true);


        consoleArea.setEditable(false);
		//컴파일 버튼 클릭시 동작
		newButton.setOnAction(e -> {
			codeArea2.clear();
			codeArea2.replaceText("public class \n{\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World!\");\n\t}\n}");
			fileName = "";
			fileDir = ".";
			pathLabel.setText("경로 : ");
			
		});
		loadButton.setOnAction(e -> {
			dirFileSet();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Java File");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Files", "*.java"));
			// 초기 디렉토리 설정
			File initialDir = new File(fileDir);
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
					String filepath = "javaEditerPath.txt";
					Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
					// 파일 내용 읽어서 코드 영역에 표시
					StringBuilder content = new StringBuilder();
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile), StandardCharsets.UTF_8));
					String line;
					while ((line = reader.readLine()) != null) {
						content.append(line).append("\n");
					}
					reader.close();
					codeArea2.replaceText(content.toString());
					pathLabel.setText("경로 : " + selectedFile.getAbsolutePath());
					consoleArea.appendText("파일 로드 완료: " + selectedFile.getAbsolutePath() + "\n");
				} catch (Exception ex) {
					consoleArea.appendText("로드 오류: " + ex.getMessage() + "\n");
				}
			}
		});
		saveButton.setOnAction(e -> {
			dirFileSet();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Java File");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Files", "*.java"));
			File initialDir = new File(fileDir);
			if (initialDir.exists()) {
				fileChooser.setInitialDirectory(initialDir);
			}else
			{
				fileChooser.setInitialDirectory(new File("."));
				fileDir = ".";
			}

			File selectedFile = fileChooser.showSaveDialog(primaryStage);
			if (selectedFile != null) {
				try {
					String rawName = selectedFile.getName();

					if (!rawName.toLowerCase().endsWith(".java")) {
						selectedFile = new File(selectedFile.getParent(), rawName + ".java");
					}

					// 경로와 파일명 저장
					fileDir = selectedFile.getParent();
					fileName = selectedFile.getName();
					String filepath = "javaEditerPath.txt";
					Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
					// 코드 저장
					String code = codeArea2.getText();
					Files.write(selectedFile.toPath(), code.getBytes(StandardCharsets.UTF_8));
					pathLabel.setText("경로 : " + selectedFile.getAbsolutePath());
					consoleArea.appendText("저장 완료: " + selectedFile.getAbsolutePath() + "\n");
				} catch (Exception ex) {
					consoleArea.appendText("저장 오류: " + ex.getMessage() + "\n");
				}
			}
		});
		compileButton.setOnAction(e -> {
			consoleArea.clear();
            try {
				// 1. 코드 저장
				String code = codeArea2.getText();
				for (String fontName : javafx.scene.text.Font.getFamilies()) {
					//System.out.println(fontName);
				}


				String filepath = fileDir+"\\"+fileName;
				Files.write(Paths.get(filepath), code.getBytes(StandardCharsets.UTF_8));
				
				if(!optionUseb)
				{
					// 2. 컴파일
					ProcessBuilder compile = new ProcessBuilder("javac", fileName);
					compile.directory(new File(fileDir));
					compile.redirectErrorStream(true);
					Process compileProcess = compile.start();

					BufferedReader compileReader = new BufferedReader(new InputStreamReader(compileProcess.getInputStream(), Charset.forName("MS949")));
					String cline;
					consoleArea.clear();
					consoleArea.appendText("컴파일 : \n");
					while ((cline = compileReader.readLine()) != null) {
						consoleArea.appendText(cline + "\n");
					}
					compileProcess.waitFor();
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
					command.add(javacpath);
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
					command.add(mainClass); // 쉼표 ❌ 확장자 정확히!
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
					ProcessBuilder compile = new ProcessBuilder(command);
					compile.directory(new File(path));
					compile.redirectErrorStream(true);
					Process compileProcess = compile.start();

					BufferedReader compileReader = new BufferedReader(new InputStreamReader(compileProcess.getInputStream(), Charset.forName("MS949")));
					String cline;
					consoleArea.clear();
					consoleArea.appendText("컴파일 : \n");
					while ((cline = compileReader.readLine()) != null) {
						consoleArea.appendText(cline + "\n");
					}
					compileProcess.waitFor();
				}

				

			} catch (Exception ex) {
				consoleArea.appendText("오류 발생: " + ex.getMessage());
			}
		});
        // 실행 버튼 클릭 시 동작
        runButton.setOnAction(e -> {
            consoleArea.clear();
            try {
				// 1. 코드 저장
				String code = codeArea2.getText();
				for (String fontName : javafx.scene.text.Font.getFamilies()) {
					//System.out.println(fontName);
				}


				String filepath = fileDir+"\\"+fileName;
				Files.write(Paths.get(filepath), code.getBytes(StandardCharsets.UTF_8));
				if(!optionUseb)
				{
					// 2. 컴파일
					ProcessBuilder compile = new ProcessBuilder("javac", fileName);
					compile.redirectErrorStream(true);
					compile.directory(new File(fileDir)); // fileDir은 실행할 디렉토리 경로
					
					Process compileProcess = compile.start();

					BufferedReader compileReader = new BufferedReader(new InputStreamReader(compileProcess.getInputStream(), Charset.forName("MS949")));
					String cline;
					consoleArea.clear();
					consoleArea.appendText("컴파일 : \n");
					while ((cline = compileReader.readLine()) != null) {
						consoleArea.appendText(cline + "\n");
					}
					compileProcess.waitFor();

					// 3. 실행
				
					ProcessBuilder run = new ProcessBuilder("java", fileName.replace(".java",""));
					run.redirectErrorStream(true);
					run.directory(new File(fileDir)); // fileDir은 실행할 디렉토리 경로
					Process runProcess = run.start();
					BufferedReader runReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream(), Charset.forName("MS949")));
					String line;
					consoleArea.clear();
					consoleArea.appendText("실행 : \n");
					while ((line = runReader.readLine()) != null) {
						consoleArea.appendText(line + "\n");
					}
					runProcess.waitFor();
				
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
					command.add(javacpath);
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
					command.add(mainClass); // 쉼표 ❌ 확장자 정확히!
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
					ProcessBuilder compile = new ProcessBuilder(command);
					compile.directory(new File(path));
					compile.redirectErrorStream(true);
					Process compileProcess = compile.start();

					BufferedReader compileReader = new BufferedReader(new InputStreamReader(compileProcess.getInputStream(), Charset.forName("MS949")));
					String cline;
					consoleArea.clear();
					consoleArea.appendText("컴파일 : \n");
					while ((cline = compileReader.readLine()) != null) {
						consoleArea.appendText(cline + "\n");
					}
					compileProcess.waitFor();
					
					arg1 = optionStr[4].trim();
					arg2 = optionStr[5].trim();
					mainClass = optionStr[6].trim();
					// 명령어 조립
					List<String> command2 = new ArrayList<>();
					command2.add(javapath);
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
					command2.add(mainClass); // 쉼표 ❌ 확장자 정확히!
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
				
					ProcessBuilder run = new ProcessBuilder(command2);
					run.redirectErrorStream(true);
					run.directory(new File(path)); // fileDir은 실행할 디렉토리 경로
					Process runProcess = run.start();
					BufferedReader runReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream(), Charset.forName("MS949")));
					String line;
					consoleArea.clear();
					consoleArea.appendText("실행 : \n");
					while ((line = runReader.readLine()) != null) {
						consoleArea.appendText(line + "\n");
					}
					runProcess.waitFor();
					
				}
				

			} catch (Exception ex) {
				consoleArea.appendText("오류 발생: " + ex.getMessage());
			}

        });
		optionButton.setOnAction(e -> {
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
			
			HBox saveLoadBox = new HBox(10, osaveButton, oloadButton);

			// 위치 입력
			Label locationLabel = new Label("위치 : ");
			TextField locationField = new TextField();
			locationField.setPrefWidth(400); // 원하는 너비로 설정

			Button browseButton = new Button("...");
			browseButton.setOnAction(oe -> {
				dirFileSet();
				DirectoryChooser directoryChooser = new DirectoryChooser();
				directoryChooser.setTitle("디렉토리 선택");

				// 초기 디렉토리 설정
				File initialDir = new File(fileDir);
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

			// javac 옵션
			Label javacLabel = new Label("javac옵션");

			Label javacArg1Label = new Label("명령인수1 : ");
			TextField javacArg1Field = new TextField();
			javacArg1Field.setPrefWidth(400); // 원하는 너비로 설정

			HBox javacArg1Box = new HBox(10, javacArg1Label, javacArg1Field);

			Label javacArg2Label = new Label("명령인수2 : ");
			TextField javacArg2Field = new TextField();
			javacArg2Field.setPrefWidth(400); // 원하는 너비로 설정
			HBox javacArg2Box = new HBox(10, javacArg2Label, javacArg2Field);

			Label javacMainLabel = new Label("메인클래스 : ");
			TextField javacMainField = new TextField();
			javacMainField.setPrefWidth(400); // 원하는 너비로 설정
			HBox javacMainBox = new HBox(10, javacMainLabel, javacMainField);

			// java 옵션
			Label javaLabel = new Label("java옵션");

			Label javaArg1Label = new Label("명령인수1 : ");
			TextField javaArg1Field = new TextField();
			javaArg1Field.setPrefWidth(400); // 원하는 너비로 설정
			HBox javaArg1Box = new HBox(10, javaArg1Label, javaArg1Field);

			Label javaArg2Label = new Label("명령인수2 : ");
			TextField javaArg2Field = new TextField();
			javaArg2Field.setPrefWidth(400); // 원하는 너비로 설정
			HBox javaArg2Box = new HBox(10, javaArg2Label, javaArg2Field);

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
			
			osaveButton.setOnAction(oe -> {
				StringBuilder sb = new StringBuilder();

				// 고정 문자 추가 (char 형 숫자 4글자)
				sb.append((char)4444);
				sb.append((char)4254);
				sb.append((char)3454);
				sb.append((char)1111);
				sb.append(";;");
				// 텍스트 필드 내용 가져오기 + 구분자 ";"로 추가
				sb.append(locationField.getText().trim()).append(";;");
				sb.append(javacArg1Field.getText().trim()).append(";;");
				sb.append(javacArg2Field.getText().trim()).append(";;");
				sb.append(javacMainField.getText().trim()).append(";;");
				

				sb.append(javaArg1Field.getText().trim()).append(";;");
				sb.append(javaArg2Field.getText().trim()).append(";;");
				sb.append(javaMainField.getText().trim()).append(";;");
				dirFileSet();
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Java File");
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("jeop Files", "*.jeop"));
				File initialDir = new File(fileDir);
				if (initialDir.exists()) {
					fileChooser.setInitialDirectory(initialDir);
				}else
				{
					fileChooser.setInitialDirectory(new File("."));
					fileDir = ".";
				}

				File selectedFile = fileChooser.showSaveDialog(primaryStage);
				if (selectedFile != null) {
					try {
						String rawName = selectedFile.getName();

						if (!rawName.toLowerCase().endsWith(".jeop")) {
							selectedFile = new File(selectedFile.getParent(), rawName + ".jeop");
						}

						// 경로와 파일명 저장
						fileDir = selectedFile.getParent();
					
						String filepath = "javaEditerPath.txt";
						Files.write(Paths.get(filepath), fileDir.getBytes(StandardCharsets.UTF_8));
						// 코드 저장
					
						Files.write(selectedFile.toPath(), sb.toString().getBytes(StandardCharsets.UTF_8));

					
					} catch (Exception ex) {
						
					}
				}
			});
			oloadButton.setOnAction(oe -> {
				dirFileSet();
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open jeop File");
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("jeop Files", "*.jeop"));
				// 초기 디렉토리 설정
				File initialDir = new File(fileDir);
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
			// 확인 / 취소 버튼
			Button okButton = new Button("확인");
			Button cancelButton = new Button("취소");
			okButton.setOnAction(oe -> {
				optionUseb = useCheckBox.isSelected();
				optionStr[0] = locationField.getText() != null ? locationField.getText() : "";
				optionStr[1] = javacArg1Field.getText() != null ? javacArg1Field.getText() : "";
				optionStr[2] = javacArg2Field.getText() != null ? javacArg2Field.getText() : "";
				optionStr[3] = javacMainField.getText() != null ? javacMainField.getText() : "";
				optionStr[4] = javaArg1Field.getText() != null ? javaArg1Field.getText() : "";
				optionStr[5] = javaArg2Field.getText() != null ? javaArg2Field.getText() : "";
				optionStr[6] = javaMainField.getText() != null ? javaMainField.getText() : "";
				// 필요한 작업 수행 후 창 닫기
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
		informButton.setOnAction(e -> {
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
			textArea.appendText("앤트자바에디터v0.0021\n");
			textArea.appendText("계속 테스트하면서 업그레이드하겠습니다.\n");
			textArea.setPrefRowCount(5);
			textArea.setPrefColumnCount(30);

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
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
        // 레이아웃 구성
		
		HBox buttonBox = new HBox(10, newButton,loadButton,saveButton,compileButton, runButton,optionButton,informButton, exitButton);
		
		HBox codeBox = new HBox();
		
		codeBox.setSpacing(5);
		codeBox.setPadding(new Insets(10));
		HBox.setHgrow(codeArea2, Priority.ALWAYS); // CodeArea가 VBox 안에서 최대한 공간 차지

		codeArea2.setPrefHeight(500); // 초기 높이 설정
		codeBox.getChildren().add(codeScrollPane);
        BorderPane root = new BorderPane();
		
		//bottom...
		
		// 콘솔 영역과 경로 라벨을 담을 VBox 생성
		VBox bottomBox = new VBox(5); // 간격 5px
		bottomBox.setPadding(new Insets(5));

		// 콘솔 영역
		bottomBox.getChildren().add(consoleScrollPane);

		
		bottomBox.getChildren().add(pathLabel);


        root.setTop(buttonBox);
		
		

		root.setCenter(codeBox);
		
        
        // VBox를 하단에 배치
		root.setBottom(bottomBox);

        // 씬 설정
        Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // 스타일시트 연결 (선택)

        primaryStage.setTitle("AntJavaEditor v0.0021");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
	public void dirFileSet()
	{
		try {
			String filepath = "javaEditerPath.txt";
			byte[] bytes = Files.readAllBytes(Paths.get(filepath));
			fileDir = new String(bytes, StandardCharsets.UTF_8).trim(); // 공백 제거
			//consoleArea.appendText("fileDir 로드됨: " + fileDir + "\n");
		} catch (Exception ex) {
			//consoleArea.appendText("fileDir 로드 오류: " + ex.getMessage() + "\n");
		}
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
		classlist.add("java.io.BufferedInputStream");
		classlist.add("java.io.BufferedOutputStream");
		classlist.add("java.io.BufferedWriter");
		classlist.add("java.io.ByteArrayInputStream");
		classlist.add("java.io.ByteArrayOutputStream");
		classlist.add("java.io.Closeable");
		classlist.add("java.io.DataInput");
		classlist.add("java.io.DataInputStream");
		classlist.add("java.io.DataOutput");
		classlist.add("java.io.DefaultFileSystem");
		classlist.add("java.io.File");
		classlist.add("java.io.File$PathStatus");
		classlist.add("java.io.FileCleanable");
		classlist.add("java.io.FileDescriptor");
		classlist.add("java.io.FileDescriptor$1");
		classlist.add("java.io.FileInputStream");
		classlist.add("java.io.FileInputStream$1");
		classlist.add("java.io.FileOutputStream");
		classlist.add("java.io.FilePermission");
		classlist.add("java.io.FileSystem");
		classlist.add("java.io.FilterInputStream");
		classlist.add("java.io.FilterOutputStream");
		classlist.add("java.io.Flushable");
		classlist.add("java.io.InputStream");
		classlist.add("java.io.ObjectStreamField");
		classlist.add("java.io.OutputStream");
		classlist.add("java.io.OutputStreamWriter");
		classlist.add("java.io.PrintStream");
		classlist.add("java.io.PrintStream$1");
		classlist.add("java.io.RandomAccessFile");
		classlist.add("java.io.RandomAccessFile$1");
		classlist.add("java.io.RandomAccessFile$2");
		classlist.add("java.io.Serializable");
		classlist.add("java.io.WinNTFileSystem");
		classlist.add("java.io.Writer");
		classlist.add("java.lang.AbstractStringBuilder");
		classlist.add("java.lang.Appendable");
		classlist.add("java.lang.ApplicationShutdownHooks");
		classlist.add("java.lang.ApplicationShutdownHooks$1");
		classlist.add("java.lang.ArithmeticException");
		classlist.add("java.lang.ArrayStoreException");
		classlist.add("java.lang.AssertionStatusDirectives");
		classlist.add("java.lang.AutoCloseable");
		classlist.add("java.lang.BaseVirtualThread");
		classlist.add("java.lang.Boolean");
		classlist.add("java.lang.BootstrapMethodError");
		classlist.add("java.lang.Byte");
		classlist.add("java.lang.CharSequence");
		classlist.add("java.lang.Character");
		classlist.add("java.lang.Character$CharacterCache");
		classlist.add("java.lang.CharacterData");
		classlist.add("java.lang.CharacterData00");
		classlist.add("java.lang.CharacterDataLatin1");
		classlist.add("java.lang.Class");
		classlist.add("java.lang.Class$1");
		classlist.add("java.lang.Class$3");
		classlist.add("java.lang.Class$Atomic");
		classlist.add("java.lang.Class$ReflectionData");
		classlist.add("java.lang.ClassCastException");
		classlist.add("java.lang.ClassLoader");
		classlist.add("java.lang.ClassLoader$ParallelLoaders");
		classlist.add("java.lang.ClassNotFoundException");
		classlist.add("java.lang.ClassValue");
		classlist.add("java.lang.ClassValue$Entry");
		classlist.add("java.lang.ClassValue$Identity");
		classlist.add("java.lang.ClassValue$Version");
		classlist.add("java.lang.Cloneable");
		classlist.add("java.lang.Comparable");
		classlist.add("java.lang.CompoundEnumeration");
		classlist.add("java.lang.Double");
		classlist.add("java.lang.Enum");
		classlist.add("java.lang.Error");
		classlist.add("java.lang.Exception");
		classlist.add("java.lang.Float");
		classlist.add("java.lang.IllegalArgumentException");
		classlist.add("java.lang.IllegalMonitorStateException");
		classlist.add("java.lang.IncompatibleClassChangeError");
		classlist.add("java.lang.Integer");
		classlist.add("java.lang.Integer$IntegerCache");
		classlist.add("java.lang.InternalError");
		classlist.add("java.lang.Iterable");
		classlist.add("java.lang.LinkageError");
		classlist.add("java.lang.LiveStackFrame");
		classlist.add("java.lang.LiveStackFrameInfo");
		classlist.add("java.lang.Long");
		classlist.add("java.lang.Long$LongCache");
		classlist.add("java.lang.Math");
		classlist.add("java.lang.Module");
		classlist.add("java.lang.Module$ArchivedData");
		classlist.add("java.lang.Module$EnableNativeAccess");
		classlist.add("java.lang.Module$ReflectionData");
		classlist.add("java.lang.ModuleLayer");
		classlist.add("java.lang.ModuleLayer$Controller");
		classlist.add("java.lang.NamedPackage");
		classlist.add("java.lang.NoClassDefFoundError");
		classlist.add("java.lang.NoSuchFieldException");
		classlist.add("java.lang.NoSuchMethodError");
		classlist.add("java.lang.NoSuchMethodException");
		classlist.add("java.lang.NullPointerException");
		classlist.add("java.lang.Number");
		classlist.add("java.lang.Object");
		classlist.add("java.lang.OutOfMemoryError");
		classlist.add("java.lang.Package");
		classlist.add("java.lang.Package$VersionInfo");
		classlist.add("java.lang.PublicMethods$Key");
		classlist.add("java.lang.PublicMethods$MethodList");
		classlist.add("java.lang.Readable");
		classlist.add("java.lang.Record");
		classlist.add("java.lang.ReflectiveOperationException");
		classlist.add("java.lang.Runnable");
		classlist.add("java.lang.Runtime");
		classlist.add("java.lang.Runtime$Version");
		classlist.add("java.lang.RuntimeException");
		classlist.add("java.lang.RuntimePermission");
		classlist.add("java.lang.SecurityManager");
		classlist.add("java.lang.Short");
		classlist.add("java.lang.Shutdown");
		classlist.add("java.lang.Shutdown$Lock");
		classlist.add("java.lang.StackFrameInfo");
		classlist.add("java.lang.StackOverflowError");
		classlist.add("java.lang.StackStreamFactory$AbstractStackWalker");
		classlist.add("java.lang.StackTraceElement");
		classlist.add("java.lang.StackWalker");
		classlist.add("java.lang.StackWalker$StackFrame");
		classlist.add("java.lang.StrictMath");
		classlist.add("java.lang.String");
		classlist.add("java.lang.String$CaseInsensitiveComparator");
		classlist.add("java.lang.StringBuffer");
		classlist.add("java.lang.StringBuilder");
		classlist.add("java.lang.StringCoding");
		classlist.add("java.lang.StringConcatHelper");
		classlist.add("java.lang.StringLatin1");
		classlist.add("java.lang.StringLatin1$CharsSpliterator");
		classlist.add("java.lang.StringUTF16");
		classlist.add("java.lang.StringUTF16$CharsSpliterator");
		classlist.add("java.lang.System");
		classlist.add("java.lang.System$2");
		classlist.add("java.lang.System$Logger");
		classlist.add("java.lang.System$LoggerFinder");
		classlist.add("java.lang.Terminator");
		classlist.add("java.lang.Terminator$1");
		classlist.add("java.lang.Thread");
		classlist.add("java.lang.Thread$Constants");
		classlist.add("java.lang.Thread$FieldHolder");
		classlist.add("java.lang.Thread$State");
		classlist.add("java.lang.Thread$ThreadIdentifiers");
		classlist.add("java.lang.Thread$UncaughtExceptionHandler");
		classlist.add("java.lang.ThreadBuilders$BoundVirtualThread");
		classlist.add("java.lang.ThreadGroup");
		classlist.add("java.lang.ThreadLocal");
		classlist.add("java.lang.ThreadLocal$ThreadLocalMap");
		classlist.add("java.lang.ThreadLocal$ThreadLocalMap$Entry");
		classlist.add("java.lang.Throwable");
		classlist.add("java.lang.VersionProps");
		classlist.add("java.lang.VirtualMachineError");
		classlist.add("java.lang.VirtualThread");
		classlist.add("java.lang.Void");
		classlist.add("java.lang.WeakPairMap");
		classlist.add("java.lang.WeakPairMap$Pair");
		classlist.add("java.lang.WeakPairMap$Pair$Lookup");
		classlist.add("java.lang.annotation.Annotation");
		classlist.add("java.lang.constant.Constable");
		classlist.add("java.lang.constant.ConstantDesc");
		classlist.add("java.lang.invoke.AbstractValidatingLambdaMetafactory");
		classlist.add("java.lang.invoke.BootstrapMethodInvoker");
		classlist.add("java.lang.invoke.BoundMethodHandle");
		classlist.add("java.lang.invoke.BoundMethodHandle$Specializer");
		classlist.add("java.lang.invoke.BoundMethodHandle$Specializer$Factory");
		classlist.add("java.lang.invoke.BoundMethodHandle$SpeciesData");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_D");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_DL");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_I");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_IL");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_L");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_LJ");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_LL");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_LLJ");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_LLL");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_LLLJ");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_LLLL");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_LLLLL");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_LLLLLL");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_LLLLLLL");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_LLLLLLLL");
		classlist.add("java.lang.invoke.BoundMethodHandle$Species_LLLLLLLLL");
		classlist.add("java.lang.invoke.CallSite");
		classlist.add("java.lang.invoke.ClassSpecializer");
		classlist.add("java.lang.invoke.ClassSpecializer$1");
		classlist.add("java.lang.invoke.ClassSpecializer$Factory");
		classlist.add("java.lang.invoke.ClassSpecializer$SpeciesData");
		classlist.add("java.lang.invoke.ConstantCallSite");
		classlist.add("java.lang.invoke.DelegatingMethodHandle");
		classlist.add("java.lang.invoke.DelegatingMethodHandle$Holder");
		classlist.add("java.lang.invoke.DirectMethodHandle");
		classlist.add("java.lang.invoke.DirectMethodHandle$2");
		classlist.add("java.lang.invoke.DirectMethodHandle$Accessor");
		classlist.add("java.lang.invoke.DirectMethodHandle$Constructor");
		classlist.add("java.lang.invoke.DirectMethodHandle$Holder");
		classlist.add("java.lang.invoke.DirectMethodHandle$Interface");
		classlist.add("java.lang.invoke.InfoFromMemberName");
		classlist.add("java.lang.invoke.InnerClassLambdaMetafactory");
		classlist.add("java.lang.invoke.InnerClassLambdaMetafactory$ForwardingMethodGenerator");
		classlist.add("java.lang.invoke.InvokerBytecodeGenerator");
		classlist.add("java.lang.invoke.InvokerBytecodeGenerator$1");
		classlist.add("java.lang.invoke.InvokerBytecodeGenerator$ClassData");
		classlist.add("java.lang.invoke.Invokers");
		classlist.add("java.lang.invoke.Invokers$Holder");
		classlist.add("java.lang.invoke.LambdaForm");
		classlist.add("java.lang.invoke.LambdaForm$BasicType");
		classlist.add("java.lang.invoke.LambdaForm$Holder");
		classlist.add("java.lang.invoke.LambdaForm$Kind");
		classlist.add("java.lang.invoke.LambdaForm$Name");
		classlist.add("java.lang.invoke.LambdaForm$NamedFunction");
		classlist.add("java.lang.invoke.LambdaFormBuffer");
		classlist.add("java.lang.invoke.LambdaFormEditor");
		classlist.add("java.lang.invoke.LambdaFormEditor$1");
		classlist.add("java.lang.invoke.LambdaFormEditor$Transform");
		classlist.add("java.lang.invoke.LambdaFormEditor$TransformKey");
		classlist.add("java.lang.invoke.LambdaMetafactory");
		classlist.add("java.lang.invoke.LambdaProxyClassArchive");
		classlist.add("java.lang.invoke.MemberName");
		classlist.add("java.lang.invoke.MemberName$Factory");
		classlist.add("java.lang.invoke.MethodHandle");
		classlist.add("java.lang.invoke.MethodHandleImpl");
		classlist.add("java.lang.invoke.MethodHandleImpl$1");
		classlist.add("java.lang.invoke.MethodHandleImpl$AsVarargsCollector");
		classlist.add("java.lang.invoke.MethodHandleImpl$Intrinsic");
		classlist.add("java.lang.invoke.MethodHandleImpl$IntrinsicMethodHandle");
		classlist.add("java.lang.invoke.MethodHandleInfo");
		classlist.add("java.lang.invoke.MethodHandleNatives");
		classlist.add("java.lang.invoke.MethodHandleNatives$CallSiteContext");
		classlist.add("java.lang.invoke.MethodHandleStatics");
		classlist.add("java.lang.invoke.MethodHandles");
		classlist.add("java.lang.invoke.MethodHandles$1");
		classlist.add("java.lang.invoke.MethodHandles$Lookup");
		classlist.add("java.lang.invoke.MethodHandles$Lookup$ClassDefiner");
		classlist.add("java.lang.invoke.MethodHandles$Lookup$ClassFile");
		classlist.add("java.lang.invoke.MethodHandles$Lookup$ClassOption");
		classlist.add("java.lang.invoke.MethodType");
		classlist.add("java.lang.invoke.MethodType$ConcurrentWeakInternSet");
		classlist.add("java.lang.invoke.MethodType$ConcurrentWeakInternSet$WeakEntry");
		classlist.add("java.lang.invoke.MethodTypeForm");
		classlist.add("java.lang.invoke.MutableCallSite");
		classlist.add("java.lang.invoke.ResolvedMethodName");
		classlist.add("java.lang.invoke.SimpleMethodHandle");
		classlist.add("java.lang.invoke.StringConcatFactory");
		classlist.add("java.lang.invoke.TypeConvertingMethodAdapter");
		classlist.add("java.lang.invoke.TypeDescriptor");
		classlist.add("java.lang.invoke.TypeDescriptor$OfField");
		classlist.add("java.lang.invoke.TypeDescriptor$OfMethod");
		classlist.add("java.lang.invoke.VarForm");
		classlist.add("java.lang.invoke.VarHandle");
		classlist.add("java.lang.invoke.VarHandle$AccessDescriptor");
		classlist.add("java.lang.invoke.VarHandle$AccessMode");
		classlist.add("java.lang.invoke.VarHandle$AccessType");
		classlist.add("java.lang.invoke.VarHandleBooleans$FieldInstanceReadOnly");
		classlist.add("java.lang.invoke.VarHandleBooleans$FieldInstanceReadWrite");
		classlist.add("java.lang.invoke.VarHandleByteArrayAsChars$ArrayHandle");
		classlist.add("java.lang.invoke.VarHandleByteArrayAsChars$ByteArrayViewVarHandle");
		classlist.add("java.lang.invoke.VarHandleByteArrayAsDoubles$ArrayHandle");
		classlist.add("java.lang.invoke.VarHandleByteArrayAsDoubles$ByteArrayViewVarHandle");
		classlist.add("java.lang.invoke.VarHandleByteArrayAsFloats$ArrayHandle");
		classlist.add("java.lang.invoke.VarHandleByteArrayAsFloats$ByteArrayViewVarHandle");
		classlist.add("java.lang.invoke.VarHandleByteArrayAsInts$ArrayHandle");
		classlist.add("java.lang.invoke.VarHandleByteArrayAsInts$ByteArrayViewVarHandle");
		classlist.add("java.lang.invoke.VarHandleByteArrayAsLongs$ArrayHandle");
		classlist.add("java.lang.invoke.VarHandleByteArrayAsLongs$ByteArrayViewVarHandle");
		classlist.add("java.lang.invoke.VarHandleByteArrayAsShorts$ArrayHandle");
		classlist.add("java.lang.invoke.VarHandleByteArrayAsShorts$ByteArrayViewVarHandle");
		classlist.add("java.lang.invoke.VarHandleGuards");
		classlist.add("java.lang.invoke.VarHandleLongs$FieldInstanceReadOnly");
		classlist.add("java.lang.invoke.VarHandleLongs$FieldInstanceReadWrite");
		classlist.add("java.lang.invoke.VarHandleReferences$FieldInstanceReadOnly");
		classlist.add("java.lang.invoke.VarHandleReferences$FieldInstanceReadWrite");
		classlist.add("java.lang.invoke.VarHandles");
		classlist.add("java.lang.invoke.VarHandles$1");
		classlist.add("java.lang.invoke.VolatileCallSite");
		classlist.add("java.lang.module.Configuration");
		classlist.add("java.lang.module.ModuleDescriptor");
		classlist.add("java.lang.module.ModuleDescriptor$1");
		classlist.add("java.lang.module.ModuleDescriptor$Builder");
		classlist.add("java.lang.module.ModuleDescriptor$Exports");
		classlist.add("java.lang.module.ModuleDescriptor$Modifier");
		classlist.add("java.lang.module.ModuleDescriptor$Opens");
		classlist.add("java.lang.module.ModuleDescriptor$Provides");
		classlist.add("java.lang.module.ModuleDescriptor$Requires");
		classlist.add("java.lang.module.ModuleDescriptor$Requires$Modifier");
		classlist.add("java.lang.module.ModuleDescriptor$Version");
		classlist.add("java.lang.module.ModuleFinder");
		classlist.add("java.lang.module.ModuleFinder$1");
		classlist.add("java.lang.module.ModuleFinder$2");
		classlist.add("java.lang.module.ModuleReader");
		classlist.add("java.lang.module.ModuleReference");
		classlist.add("java.lang.module.ResolvedModule");
		classlist.add("java.lang.module.Resolver");
		classlist.add("java.lang.ref.Cleaner");
		classlist.add("java.lang.ref.Cleaner$1");
		classlist.add("java.lang.ref.Cleaner$Cleanable");
		classlist.add("java.lang.ref.FinalReference");
		classlist.add("java.lang.ref.Finalizer");
		classlist.add("java.lang.ref.Finalizer$FinalizerThread");
		classlist.add("java.lang.ref.NativeReferenceQueue");
		classlist.add("java.lang.ref.NativeReferenceQueue$Lock");
		classlist.add("java.lang.ref.PhantomReference");
		classlist.add("java.lang.ref.Reference");
		classlist.add("java.lang.ref.Reference$1");
		classlist.add("java.lang.ref.Reference$ReferenceHandler");
		classlist.add("java.lang.ref.ReferenceQueue");
		classlist.add("java.lang.ref.ReferenceQueue$Null");
		classlist.add("java.lang.ref.SoftReference");
		classlist.add("java.lang.ref.WeakReference");
		classlist.add("java.lang.reflect.AccessFlag");
		classlist.add("java.lang.reflect.AccessFlag$1");
		classlist.add("java.lang.reflect.AccessFlag$10");
		classlist.add("java.lang.reflect.AccessFlag$11");
		classlist.add("java.lang.reflect.AccessFlag$12");
		classlist.add("java.lang.reflect.AccessFlag$13");
		classlist.add("java.lang.reflect.AccessFlag$14");
		classlist.add("java.lang.reflect.AccessFlag$15");
		classlist.add("java.lang.reflect.AccessFlag$16");
		classlist.add("java.lang.reflect.AccessFlag$17");
		classlist.add("java.lang.reflect.AccessFlag$18");
		classlist.add("java.lang.reflect.AccessFlag$2");
		classlist.add("java.lang.reflect.AccessFlag$3");
		classlist.add("java.lang.reflect.AccessFlag$4");
		classlist.add("java.lang.reflect.AccessFlag$5");
		classlist.add("java.lang.reflect.AccessFlag$6");
		classlist.add("java.lang.reflect.AccessFlag$7");
		classlist.add("java.lang.reflect.AccessFlag$8");
		classlist.add("java.lang.reflect.AccessFlag$9");
		classlist.add("java.lang.reflect.AccessFlag$Location");
		classlist.add("java.lang.reflect.AccessibleObject");
		classlist.add("java.lang.reflect.AnnotatedElement");
		classlist.add("java.lang.reflect.Array");
		classlist.add("java.lang.reflect.ClassFileFormatVersion");
		classlist.add("java.lang.reflect.Constructor");
		classlist.add("java.lang.reflect.Executable");
		classlist.add("java.lang.reflect.Field");
		classlist.add("java.lang.reflect.GenericDeclaration");
		classlist.add("java.lang.reflect.Member");
		classlist.add("java.lang.reflect.Method");
		classlist.add("java.lang.reflect.Modifier");
		classlist.add("java.lang.reflect.Parameter");
		classlist.add("java.lang.reflect.RecordComponent");
		classlist.add("java.lang.reflect.ReflectAccess");
		classlist.add("java.lang.reflect.Type");
		classlist.add("java.math.BigInteger");
		classlist.add("java.math.RoundingMode");
		classlist.add("java.net.DefaultInterface");
		classlist.add("java.net.Inet4Address");
		classlist.add("java.net.Inet4AddressImpl");
		classlist.add("java.net.Inet6Address");
		classlist.add("java.net.Inet6Address$Inet6AddressHolder");
		classlist.add("java.net.Inet6AddressImpl");
		classlist.add("java.net.InetAddress");
		classlist.add("java.net.InetAddress$1");
		classlist.add("java.net.InetAddress$InetAddressHolder");
		classlist.add("java.net.InetAddress$PlatformResolver");
		classlist.add("java.net.InetAddressImpl");
		classlist.add("java.net.InterfaceAddress");
		classlist.add("java.net.NetworkInterface");
		classlist.add("java.net.URI");
		classlist.add("java.net.URI$1");
		classlist.add("java.net.URI$Parser");
		classlist.add("java.net.URL");
		classlist.add("java.net.URL$3");
		classlist.add("java.net.URL$DefaultFactory");
		classlist.add("java.net.URLClassLoader");
		classlist.add("java.net.URLStreamHandler");
		classlist.add("java.net.URLStreamHandlerFactory");
		classlist.add("java.net.spi.InetAddressResolver");
		classlist.add("java.net.spi.InetAddressResolver$LookupPolicy");
		classlist.add("java.nio.Bits");
		classlist.add("java.nio.Bits$1");
		classlist.add("java.nio.Buffer");
		classlist.add("java.nio.Buffer$1");
		classlist.add("java.nio.Buffer$2");
		classlist.add("java.nio.ByteBuffer");
		classlist.add("java.nio.ByteOrder");
		classlist.add("java.nio.CharBuffer");
		classlist.add("java.nio.DirectByteBuffer");
		classlist.add("java.nio.DirectByteBufferR");
		classlist.add("java.nio.DirectIntBufferRU");
		classlist.add("java.nio.DirectIntBufferU");
		classlist.add("java.nio.DirectLongBufferU");
		classlist.add("java.nio.HeapByteBuffer");
		classlist.add("java.nio.HeapCharBuffer");
		classlist.add("java.nio.IntBuffer");
		classlist.add("java.nio.LongBuffer");
		classlist.add("java.nio.MappedByteBuffer");
		classlist.add("java.nio.charset.Charset");
		classlist.add("java.nio.charset.CharsetDecoder");
		classlist.add("java.nio.charset.CharsetEncoder");
		classlist.add("java.nio.charset.CoderResult");
		classlist.add("java.nio.charset.CodingErrorAction");
		classlist.add("java.nio.charset.StandardCharsets");
		classlist.add("java.nio.charset.spi.CharsetProvider");
		classlist.add("java.nio.file.CopyOption");
		classlist.add("java.nio.file.FileSystem");
		classlist.add("java.nio.file.FileSystems");
		classlist.add("java.nio.file.FileSystems$DefaultFileSystemHolder");
		classlist.add("java.nio.file.FileSystems$DefaultFileSystemHolder$1");
		classlist.add("java.nio.file.Files");
		classlist.add("java.nio.file.LinkOption");
		classlist.add("java.nio.file.OpenOption");
		classlist.add("java.nio.file.Path");
		classlist.add("java.nio.file.Paths");
		classlist.add("java.nio.file.StandardOpenOption");
		classlist.add("java.nio.file.Watchable");
		classlist.add("java.nio.file.attribute.AttributeView");
		classlist.add("java.nio.file.attribute.BasicFileAttributeView");
		classlist.add("java.nio.file.attribute.BasicFileAttributes");
		classlist.add("java.nio.file.attribute.DosFileAttributes");
		classlist.add("java.nio.file.attribute.FileAttributeView");
		classlist.add("java.nio.file.attribute.FileTime");
		classlist.add("java.nio.file.spi.FileSystemProvider");
		classlist.add("java.security.AccessControlContext");
		classlist.add("java.security.AccessController");
		classlist.add("java.security.AllPermission");
		classlist.add("java.security.BasicPermission");
		classlist.add("java.security.BasicPermissionCollection");
		classlist.add("java.security.CodeSource");
		classlist.add("java.security.Guard");
		classlist.add("java.security.Permission");
		classlist.add("java.security.PermissionCollection");
		classlist.add("java.security.Permissions");
		classlist.add("java.security.Principal");
		classlist.add("java.security.PrivilegedAction");
		classlist.add("java.security.PrivilegedExceptionAction");
		classlist.add("java.security.ProtectionDomain");
		classlist.add("java.security.ProtectionDomain$JavaSecurityAccessImpl");
		classlist.add("java.security.ProtectionDomain$Key");
		classlist.add("java.security.SecureClassLoader");
		classlist.add("java.security.SecureClassLoader$1");
		classlist.add("java.security.SecureClassLoader$CodeSourceKey");
		classlist.add("java.security.SecureClassLoader$DebugHolder");
		classlist.add("java.security.Security");
		classlist.add("java.security.Security$1");
		classlist.add("java.security.UnresolvedPermission");
		classlist.add("java.security.cert.Certificate");
		classlist.add("java.text.AttributedCharacterIterator$Attribute");
		classlist.add("java.text.DateFormat");
		classlist.add("java.text.DateFormat$Field");
		classlist.add("java.text.DateFormatSymbols");
		classlist.add("java.text.DecimalFormat");
		classlist.add("java.text.DecimalFormatSymbols");
		classlist.add("java.text.DigitList");
		classlist.add("java.text.DontCareFieldPosition");
		classlist.add("java.text.DontCareFieldPosition$1");
		classlist.add("java.text.FieldPosition");
		classlist.add("java.text.Format");
		classlist.add("java.text.Format$Field");
		classlist.add("java.text.Format$FieldDelegate");
		classlist.add("java.text.NumberFormat");
		classlist.add("java.text.NumberFormat$Field");
		classlist.add("java.text.SimpleDateFormat");
		classlist.add("java.text.spi.BreakIteratorProvider");
		classlist.add("java.text.spi.CollatorProvider");
		classlist.add("java.text.spi.DateFormatProvider");
		classlist.add("java.text.spi.DateFormatSymbolsProvider");
		classlist.add("java.text.spi.DecimalFormatSymbolsProvider");
		classlist.add("java.text.spi.NumberFormatProvider");
		classlist.add("java.time.Clock");
		classlist.add("java.time.Clock$SystemClock");
		classlist.add("java.time.Duration");
		classlist.add("java.time.Instant");
		classlist.add("java.time.InstantSource");
		classlist.add("java.time.LocalDate");
		classlist.add("java.time.LocalDate$1");
		classlist.add("java.time.LocalDateTime");
		classlist.add("java.time.LocalTime");
		classlist.add("java.time.LocalTime$1");
		classlist.add("java.time.Period");
		classlist.add("java.time.ZoneId");
		classlist.add("java.time.ZoneOffset");
		classlist.add("java.time.ZoneRegion");
		classlist.add("java.time.chrono.AbstractChronology");
		classlist.add("java.time.chrono.ChronoLocalDate");
		classlist.add("java.time.chrono.ChronoLocalDateTime");
		classlist.add("java.time.chrono.ChronoPeriod");
		classlist.add("java.time.chrono.Chronology");
		classlist.add("java.time.chrono.IsoChronology");
		classlist.add("java.time.format.DateTimeFormatter");
		classlist.add("java.time.format.DateTimeFormatterBuilder");
		classlist.add("java.time.format.DateTimeFormatterBuilder$1");
		classlist.add("java.time.format.DateTimeFormatterBuilder$2");
		classlist.add("java.time.format.DateTimeFormatterBuilder$CharLiteralPrinterParser");
		classlist.add("java.time.format.DateTimeFormatterBuilder$CompositePrinterParser");
		classlist.add("java.time.format.DateTimeFormatterBuilder$DateTimePrinterParser");
		classlist.add("java.time.format.DateTimeFormatterBuilder$InstantPrinterParser");
		classlist.add("java.time.format.DateTimeFormatterBuilder$NanosPrinterParser");
		classlist.add("java.time.format.DateTimeFormatterBuilder$NumberPrinterParser");
		classlist.add("java.time.format.DateTimeFormatterBuilder$OffsetIdPrinterParser");
		classlist.add("java.time.format.DateTimeFormatterBuilder$SettingsParser");
		classlist.add("java.time.format.DateTimeFormatterBuilder$StringLiteralPrinterParser");
		classlist.add("java.time.format.DateTimeFormatterBuilder$TextPrinterParser");
		classlist.add("java.time.format.DateTimeFormatterBuilder$ZoneIdPrinterParser");
		classlist.add("java.time.format.DateTimePrintContext");
		classlist.add("java.time.format.DateTimeTextProvider");
		classlist.add("java.time.format.DateTimeTextProvider$1");
		classlist.add("java.time.format.DateTimeTextProvider$LocaleStore");
		classlist.add("java.time.format.DecimalStyle");
		classlist.add("java.time.format.ResolverStyle");
		classlist.add("java.time.format.SignStyle");
		classlist.add("java.time.format.TextStyle");
		classlist.add("java.time.temporal.ChronoField");
		classlist.add("java.time.temporal.ChronoUnit");
		classlist.add("java.time.temporal.IsoFields");
		classlist.add("java.time.temporal.IsoFields$Field");
		classlist.add("java.time.temporal.IsoFields$Field$1");
		classlist.add("java.time.temporal.IsoFields$Field$2");
		classlist.add("java.time.temporal.IsoFields$Field$3");
		classlist.add("java.time.temporal.IsoFields$Field$4");
		classlist.add("java.time.temporal.IsoFields$Unit");
		classlist.add("java.time.temporal.JulianFields");
		classlist.add("java.time.temporal.JulianFields$Field");
		classlist.add("java.time.temporal.Temporal");
		classlist.add("java.time.temporal.TemporalAccessor");
		classlist.add("java.time.temporal.TemporalAdjuster");
		classlist.add("java.time.temporal.TemporalAmount");
		classlist.add("java.time.temporal.TemporalField");
		classlist.add("java.time.temporal.TemporalQueries");
		classlist.add("java.time.temporal.TemporalQueries$1");
		classlist.add("java.time.temporal.TemporalQueries$2");
		classlist.add("java.time.temporal.TemporalQueries$3");
		classlist.add("java.time.temporal.TemporalQueries$4");
		classlist.add("java.time.temporal.TemporalQueries$5");
		classlist.add("java.time.temporal.TemporalQueries$6");
		classlist.add("java.time.temporal.TemporalQueries$7");
		classlist.add("java.time.temporal.TemporalQuery");
		classlist.add("java.time.temporal.TemporalUnit");
		classlist.add("java.time.temporal.ValueRange");
		classlist.add("java.time.zone.ZoneOffsetTransitionRule");
		classlist.add("java.time.zone.ZoneRules");
		classlist.add("java.util.AbstractCollection");
		classlist.add("java.util.AbstractList");
		classlist.add("java.util.AbstractList$RandomAccessSpliterator");
		classlist.add("java.util.AbstractMap");
		classlist.add("java.util.AbstractMap$SimpleImmutableEntry");
		classlist.add("java.util.AbstractSet");
		classlist.add("java.util.ArrayDeque");
		classlist.add("java.util.ArrayDeque$DeqIterator");
		classlist.add("java.util.ArrayList");
		classlist.add("java.util.ArrayList$ArrayListSpliterator");
		classlist.add("java.util.ArrayList$Itr");
		classlist.add("java.util.ArrayList$SubList");
		classlist.add("java.util.Arrays");
		classlist.add("java.util.Arrays$ArrayList");
		classlist.add("java.util.Arrays$LegacyMergeSort");
		classlist.add("java.util.Calendar");
		classlist.add("java.util.Calendar$Builder");
		classlist.add("java.util.Collection");
		classlist.add("java.util.Collections");
		classlist.add("java.util.Collections$1");
		classlist.add("java.util.Collections$3");
		classlist.add("java.util.Collections$EmptyEnumeration");
		classlist.add("java.util.Collections$EmptyIterator");
		classlist.add("java.util.Collections$EmptyList");
		classlist.add("java.util.Collections$EmptyMap");
		classlist.add("java.util.Collections$EmptySet");
		classlist.add("java.util.Collections$SetFromMap");
		classlist.add("java.util.Collections$SingletonMap");
		classlist.add("java.util.Collections$SingletonSet");
		classlist.add("java.util.Collections$SynchronizedCollection");
		classlist.add("java.util.Collections$SynchronizedMap");
		classlist.add("java.util.Collections$SynchronizedSet");
		classlist.add("java.util.Collections$UnmodifiableCollection");
		classlist.add("java.util.Collections$UnmodifiableCollection$1");
		classlist.add("java.util.Collections$UnmodifiableList");
		classlist.add("java.util.Collections$UnmodifiableRandomAccessList");
		classlist.add("java.util.Collections$UnmodifiableSet");
		classlist.add("java.util.Comparator");
		classlist.add("java.util.Date");
		classlist.add("java.util.Deque");
		classlist.add("java.util.Dictionary");
		classlist.add("java.util.EnumMap");
		classlist.add("java.util.EnumMap$1");
		classlist.add("java.util.EnumSet");
		classlist.add("java.util.Enumeration");
		classlist.add("java.util.Formattable");
		classlist.add("java.util.Formatter");
		classlist.add("java.util.Formatter$Conversion");
		classlist.add("java.util.Formatter$Flags");
		classlist.add("java.util.Formatter$FormatSpecifier");
		classlist.add("java.util.Formatter$FormatString");
		classlist.add("java.util.GregorianCalendar");
		classlist.add("java.util.HashMap");
		classlist.add("java.util.HashMap$EntryIterator");
		classlist.add("java.util.HashMap$EntrySet");
		classlist.add("java.util.HashMap$HashIterator");
		classlist.add("java.util.HashMap$HashMapSpliterator");
		classlist.add("java.util.HashMap$KeyIterator");
		classlist.add("java.util.HashMap$KeySet");
		classlist.add("java.util.HashMap$KeySpliterator");
		classlist.add("java.util.HashMap$Node");
		classlist.add("java.util.HashMap$TreeNode");
		classlist.add("java.util.HashMap$ValueIterator");
		classlist.add("java.util.HashMap$ValueSpliterator");
		classlist.add("java.util.HashMap$Values");
		classlist.add("java.util.HashSet");
		classlist.add("java.util.Hashtable");
		classlist.add("java.util.Hashtable$Entry");
		classlist.add("java.util.Hashtable$Enumerator");
		classlist.add("java.util.HexFormat");
		classlist.add("java.util.IdentityHashMap");
		classlist.add("java.util.IdentityHashMap$IdentityHashMapIterator");
		classlist.add("java.util.IdentityHashMap$KeyIterator");
		classlist.add("java.util.IdentityHashMap$KeySet");
		classlist.add("java.util.IdentityHashMap$Values");
		classlist.add("java.util.ImmutableCollections");
		classlist.add("java.util.ImmutableCollections$AbstractImmutableCollection");
		classlist.add("java.util.ImmutableCollections$AbstractImmutableList");
		classlist.add("java.util.ImmutableCollections$AbstractImmutableMap");
		classlist.add("java.util.ImmutableCollections$AbstractImmutableSet");
		classlist.add("java.util.ImmutableCollections$List12");
		classlist.add("java.util.ImmutableCollections$ListItr");
		classlist.add("java.util.ImmutableCollections$ListN");
		classlist.add("java.util.ImmutableCollections$Map1");
		classlist.add("java.util.ImmutableCollections$MapN");
		classlist.add("java.util.ImmutableCollections$MapN$1");
		classlist.add("java.util.ImmutableCollections$MapN$MapNIterator");
		classlist.add("java.util.ImmutableCollections$Set12");
		classlist.add("java.util.ImmutableCollections$Set12$1");
		classlist.add("java.util.ImmutableCollections$SetN");
		classlist.add("java.util.ImmutableCollections$SetN$SetNIterator");
		classlist.add("java.util.Iterator");
		classlist.add("java.util.KeyValueHolder");
		classlist.add("java.util.LinkedHashMap");
		classlist.add("java.util.LinkedHashMap$Entry");
		classlist.add("java.util.LinkedHashMap$LinkedEntryIterator");
		classlist.add("java.util.LinkedHashMap$LinkedEntrySet");
		classlist.add("java.util.LinkedHashMap$LinkedHashIterator");
		classlist.add("java.util.LinkedHashSet");
		classlist.add("java.util.List");
		classlist.add("java.util.ListIterator");
		classlist.add("java.util.ListResourceBundle");
		classlist.add("java.util.Locale");
		classlist.add("java.util.Locale$Builder");
		classlist.add("java.util.Locale$Cache");
		classlist.add("java.util.Locale$Category");
		classlist.add("java.util.Map");
		classlist.add("java.util.Map$Entry");
		classlist.add("java.util.NavigableMap");
		classlist.add("java.util.NavigableSet");
		classlist.add("java.util.Objects");
		classlist.add("java.util.Optional");
		classlist.add("java.util.OptionalInt");
		classlist.add("java.util.Properties");
		classlist.add("java.util.Properties$EntrySet");
		classlist.add("java.util.Properties$LineReader");
		classlist.add("java.util.Queue");
		classlist.add("java.util.Random");
		classlist.add("java.util.RandomAccess");
		classlist.add("java.util.RegularEnumSet");
		classlist.add("java.util.ResourceBundle");
		classlist.add("java.util.ResourceBundle$1");
		classlist.add("java.util.ResourceBundle$2");
		classlist.add("java.util.ResourceBundle$Control");
		classlist.add("java.util.ResourceBundle$Control$CandidateListCache");
		classlist.add("java.util.ResourceBundle$NoFallbackControl");
		classlist.add("java.util.ResourceBundle$ResourceBundleProviderHelper");
		classlist.add("java.util.ResourceBundle$SingleFormatControl");
		classlist.add("java.util.SequencedCollection");
		classlist.add("java.util.SequencedMap");
		classlist.add("java.util.SequencedSet");
		classlist.add("java.util.ServiceLoader");
		classlist.add("java.util.ServiceLoader$1");
		classlist.add("java.util.ServiceLoader$2");
		classlist.add("java.util.ServiceLoader$3");
		classlist.add("java.util.ServiceLoader$LazyClassPathLookupIterator");
		classlist.add("java.util.ServiceLoader$ModuleServicesLookupIterator");
		classlist.add("java.util.ServiceLoader$Provider");
		classlist.add("java.util.ServiceLoader$ProviderImpl");
		classlist.add("java.util.Set");
		classlist.add("java.util.SortedMap");
		classlist.add("java.util.SortedSet");
		classlist.add("java.util.Spliterator");
		classlist.add("java.util.Spliterator$OfDouble");
		classlist.add("java.util.Spliterator$OfInt");
		classlist.add("java.util.Spliterator$OfLong");
		classlist.add("java.util.Spliterator$OfPrimitive");
		classlist.add("java.util.Spliterators");
		classlist.add("java.util.Spliterators$1Adapter");
		classlist.add("java.util.Spliterators$AbstractSpliterator");
		classlist.add("java.util.Spliterators$ArraySpliterator");
		classlist.add("java.util.Spliterators$EmptySpliterator");
		classlist.add("java.util.Spliterators$EmptySpliterator$OfDouble");
		classlist.add("java.util.Spliterators$EmptySpliterator$OfInt");
		classlist.add("java.util.Spliterators$EmptySpliterator$OfLong");
		classlist.add("java.util.Spliterators$EmptySpliterator$OfRef");
		classlist.add("java.util.Spliterators$IteratorSpliterator");
		classlist.add("java.util.StringJoiner");
		classlist.add("java.util.TimSort");
		classlist.add("java.util.TimeZone");
		classlist.add("java.util.TreeMap");
		classlist.add("java.util.TreeMap$Entry");
		classlist.add("java.util.TreeMap$EntryIterator");
		classlist.add("java.util.TreeMap$EntrySet");
		classlist.add("java.util.TreeMap$PrivateEntryIterator");
		classlist.add("java.util.WeakHashMap");
		classlist.add("java.util.WeakHashMap$Entry");
		classlist.add("java.util.WeakHashMap$KeySet");
		classlist.add("java.util.concurrent.AbstractExecutorService");
		classlist.add("java.util.concurrent.ConcurrentHashMap");
		classlist.add("java.util.concurrent.ConcurrentHashMap$BaseIterator");
		classlist.add("java.util.concurrent.ConcurrentHashMap$CollectionView");
		classlist.add("java.util.concurrent.ConcurrentHashMap$CounterCell");
		classlist.add("java.util.concurrent.ConcurrentHashMap$EntryIterator");
		classlist.add("java.util.concurrent.ConcurrentHashMap$EntrySetView");
		classlist.add("java.util.concurrent.ConcurrentHashMap$ForwardingNode");
		classlist.add("java.util.concurrent.ConcurrentHashMap$KeyIterator");
		classlist.add("java.util.concurrent.ConcurrentHashMap$KeySetView");
		classlist.add("java.util.concurrent.ConcurrentHashMap$MapEntry");
		classlist.add("java.util.concurrent.ConcurrentHashMap$Node");
		classlist.add("java.util.concurrent.ConcurrentHashMap$ReservationNode");
		classlist.add("java.util.concurrent.ConcurrentHashMap$Segment");
		classlist.add("java.util.concurrent.ConcurrentHashMap$Traverser");
		classlist.add("java.util.concurrent.ConcurrentHashMap$ValueIterator");
		classlist.add("java.util.concurrent.ConcurrentHashMap$ValuesView");
		classlist.add("java.util.concurrent.ConcurrentMap");
		classlist.add("java.util.concurrent.ConcurrentNavigableMap");
		classlist.add("java.util.concurrent.ConcurrentSkipListMap");
		classlist.add("java.util.concurrent.ConcurrentSkipListMap$Index");
		classlist.add("java.util.concurrent.ConcurrentSkipListMap$Node");
		classlist.add("java.util.concurrent.ConcurrentSkipListSet");
		classlist.add("java.util.concurrent.CopyOnWriteArrayList");
		classlist.add("java.util.concurrent.CopyOnWriteArrayList$COWIterator");
		classlist.add("java.util.concurrent.CountedCompleter");
		classlist.add("java.util.concurrent.Executor");
		classlist.add("java.util.concurrent.ExecutorService");
		classlist.add("java.util.concurrent.ForkJoinPool");
		classlist.add("java.util.concurrent.ForkJoinPool$2");
		classlist.add("java.util.concurrent.ForkJoinPool$DefaultForkJoinWorkerThreadFactory");
		classlist.add("java.util.concurrent.ForkJoinPool$DefaultForkJoinWorkerThreadFactory$1");
		classlist.add("java.util.concurrent.ForkJoinPool$ForkJoinWorkerThreadFactory");
		classlist.add("java.util.concurrent.ForkJoinPool$ManagedBlocker");
		classlist.add("java.util.concurrent.ForkJoinPool$WorkQueue");
		classlist.add("java.util.concurrent.ForkJoinTask");
		classlist.add("java.util.concurrent.ForkJoinTask$Aux");
		classlist.add("java.util.concurrent.ForkJoinWorkerThread");
		classlist.add("java.util.concurrent.Future");
		classlist.add("java.util.concurrent.ThreadFactory");
		classlist.add("java.util.concurrent.ThreadLocalRandom");
		classlist.add("java.util.concurrent.TimeUnit");
		classlist.add("java.util.concurrent.atomic.AtomicInteger");
		classlist.add("java.util.concurrent.atomic.AtomicLong");
		classlist.add("java.util.concurrent.atomic.LongAdder");
		classlist.add("java.util.concurrent.atomic.Striped64");
		classlist.add("java.util.concurrent.atomic.Striped64$Cell");
		classlist.add("java.util.concurrent.locks.AbstractOwnableSynchronizer");
		classlist.add("java.util.concurrent.locks.AbstractQueuedSynchronizer");
		classlist.add("java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionNode");
		classlist.add("java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject");
		classlist.add("java.util.concurrent.locks.AbstractQueuedSynchronizer$ExclusiveNode");
		classlist.add("java.util.concurrent.locks.AbstractQueuedSynchronizer$Node");
		classlist.add("java.util.concurrent.locks.Condition");
		classlist.add("java.util.concurrent.locks.Lock");
		classlist.add("java.util.concurrent.locks.LockSupport");
		classlist.add("java.util.concurrent.locks.ReentrantLock");
		classlist.add("java.util.concurrent.locks.ReentrantLock$NonfairSync");
		classlist.add("java.util.concurrent.locks.ReentrantLock$Sync");
		classlist.add("java.util.function.BiConsumer");
		classlist.add("java.util.function.BiFunction");
		classlist.add("java.util.function.BinaryOperator");
		classlist.add("java.util.function.Consumer");
		classlist.add("java.util.function.Function");
		classlist.add("java.util.function.IntConsumer");
		classlist.add("java.util.function.IntFunction");
		classlist.add("java.util.function.IntPredicate");
		classlist.add("java.util.function.Predicate");
		classlist.add("java.util.function.Supplier");
		classlist.add("java.util.jar.Attributes");
		classlist.add("java.util.jar.Attributes$Name");
		classlist.add("java.util.jar.JarEntry");
		classlist.add("java.util.jar.JarFile");
		classlist.add("java.util.jar.JarFile$JarFileEntry");
		classlist.add("java.util.jar.JarVerifier");
		classlist.add("java.util.jar.JavaUtilJarAccessImpl");
		classlist.add("java.util.jar.Manifest");
		classlist.add("java.util.jar.Manifest$FastInputStream");
		classlist.add("java.util.logging.Handler");
		classlist.add("java.util.logging.Level");
		classlist.add("java.util.logging.Level$KnownLevel");
		classlist.add("java.util.logging.LogManager");
		classlist.add("java.util.logging.LogManager$1");
		classlist.add("java.util.logging.LogManager$2");
		classlist.add("java.util.logging.LogManager$4");
		classlist.add("java.util.logging.LogManager$Cleaner");
		classlist.add("java.util.logging.LogManager$LogNode");
		classlist.add("java.util.logging.LogManager$LoggerContext");
		classlist.add("java.util.logging.LogManager$LoggerContext$1");
		classlist.add("java.util.logging.LogManager$LoggerWeakRef");
		classlist.add("java.util.logging.LogManager$LoggingProviderAccess");
		classlist.add("java.util.logging.LogManager$RootLogger");
		classlist.add("java.util.logging.LogManager$SystemLoggerContext");
		classlist.add("java.util.logging.LogManager$VisitedLoggers");
		classlist.add("java.util.logging.Logger");
		classlist.add("java.util.logging.Logger$ConfigurationData");
		classlist.add("java.util.logging.Logger$LoggerBundle");
		classlist.add("java.util.logging.Logger$SystemLoggerHelper");
		classlist.add("java.util.logging.Logger$SystemLoggerHelper$1");
		classlist.add("java.util.logging.LoggingPermission");
		classlist.add("java.util.random.RandomGenerator");
		classlist.add("java.util.regex.ASCII");
		classlist.add("java.util.regex.CharPredicates");
		classlist.add("java.util.regex.IntHashSet");
		classlist.add("java.util.regex.MatchResult");
		classlist.add("java.util.regex.Matcher");
		classlist.add("java.util.regex.Pattern");
		classlist.add("java.util.regex.Pattern$BackRef");
		classlist.add("java.util.regex.Pattern$Begin");
		classlist.add("java.util.regex.Pattern$BitClass");
		classlist.add("java.util.regex.Pattern$BmpCharPredicate");
		classlist.add("java.util.regex.Pattern$BmpCharProperty");
		classlist.add("java.util.regex.Pattern$BmpCharPropertyGreedy");
		classlist.add("java.util.regex.Pattern$Branch");
		classlist.add("java.util.regex.Pattern$BranchConn");
		classlist.add("java.util.regex.Pattern$CharPredicate");
		classlist.add("java.util.regex.Pattern$CharProperty");
		classlist.add("java.util.regex.Pattern$CharPropertyGreedy");
		classlist.add("java.util.regex.Pattern$Curly");
		classlist.add("java.util.regex.Pattern$Dollar");
		classlist.add("java.util.regex.Pattern$First");
		classlist.add("java.util.regex.Pattern$GroupCurly");
		classlist.add("java.util.regex.Pattern$GroupHead");
		classlist.add("java.util.regex.Pattern$GroupTail");
		classlist.add("java.util.regex.Pattern$LastNode");
		classlist.add("java.util.regex.Pattern$Node");
		classlist.add("java.util.regex.Pattern$Qtype");
		classlist.add("java.util.regex.Pattern$Ques");
		classlist.add("java.util.regex.Pattern$Slice");
		classlist.add("java.util.regex.Pattern$SliceNode");
		classlist.add("java.util.regex.Pattern$Start");
		classlist.add("java.util.regex.Pattern$StartS");
		classlist.add("java.util.regex.Pattern$TreeInfo");
		classlist.add("java.util.spi.CalendarDataProvider");
		classlist.add("java.util.spi.CurrencyNameProvider");
		classlist.add("java.util.spi.LocaleNameProvider");
		classlist.add("java.util.spi.LocaleServiceProvider");
		classlist.add("java.util.spi.TimeZoneNameProvider");
		classlist.add("java.util.stream.AbstractPipeline");
		classlist.add("java.util.stream.AbstractTask");
		classlist.add("java.util.stream.BaseStream");
		classlist.add("java.util.stream.Collector");
		classlist.add("java.util.stream.Collector$Characteristics");
		classlist.add("java.util.stream.Collectors");
		classlist.add("java.util.stream.Collectors$CollectorImpl");
		classlist.add("java.util.stream.Collectors$Partition");
		classlist.add("java.util.stream.FindOps");
		classlist.add("java.util.stream.FindOps$FindOp");
		classlist.add("java.util.stream.FindOps$FindSink");
		classlist.add("java.util.stream.FindOps$FindSink$OfInt");
		classlist.add("java.util.stream.FindOps$FindSink$OfRef");
		classlist.add("java.util.stream.ForEachOps");
		classlist.add("java.util.stream.ForEachOps$ForEachOp");
		classlist.add("java.util.stream.ForEachOps$ForEachOp$OfRef");
		classlist.add("java.util.stream.IntPipeline");
		classlist.add("java.util.stream.IntPipeline$10");
		classlist.add("java.util.stream.IntPipeline$10$1");
		classlist.add("java.util.stream.IntPipeline$Head");
		classlist.add("java.util.stream.IntPipeline$StatelessOp");
		classlist.add("java.util.stream.IntStream");
		classlist.add("java.util.stream.Node");
		classlist.add("java.util.stream.Node$Builder");
		classlist.add("java.util.stream.Node$OfDouble");
		classlist.add("java.util.stream.Node$OfInt");
		classlist.add("java.util.stream.Node$OfLong");
		classlist.add("java.util.stream.Node$OfPrimitive");
		classlist.add("java.util.stream.Nodes");
		classlist.add("java.util.stream.Nodes$ArrayNode");
		classlist.add("java.util.stream.Nodes$EmptyNode");
		classlist.add("java.util.stream.Nodes$EmptyNode$OfDouble");
		classlist.add("java.util.stream.Nodes$EmptyNode$OfInt");
		classlist.add("java.util.stream.Nodes$EmptyNode$OfLong");
		classlist.add("java.util.stream.Nodes$EmptyNode$OfRef");
		classlist.add("java.util.stream.Nodes$FixedNodeBuilder");
		classlist.add("java.util.stream.PipelineHelper");
		classlist.add("java.util.stream.ReduceOps");
		classlist.add("java.util.stream.ReduceOps$3");
		classlist.add("java.util.stream.ReduceOps$3ReducingSink");
		classlist.add("java.util.stream.ReduceOps$AccumulatingSink");
		classlist.add("java.util.stream.ReduceOps$Box");
		classlist.add("java.util.stream.ReduceOps$ReduceOp");
		classlist.add("java.util.stream.ReduceOps$ReduceTask");
		classlist.add("java.util.stream.ReferencePipeline");
		classlist.add("java.util.stream.ReferencePipeline$2");
		classlist.add("java.util.stream.ReferencePipeline$2$1");
		classlist.add("java.util.stream.ReferencePipeline$3");
		classlist.add("java.util.stream.ReferencePipeline$3$1");
		classlist.add("java.util.stream.ReferencePipeline$7");
		classlist.add("java.util.stream.ReferencePipeline$7$1");
		classlist.add("java.util.stream.ReferencePipeline$Head");
		classlist.add("java.util.stream.ReferencePipeline$StatelessOp");
		classlist.add("java.util.stream.Sink");
		classlist.add("java.util.stream.Sink$ChainedInt");
		classlist.add("java.util.stream.Sink$ChainedReference");
		classlist.add("java.util.stream.Sink$OfInt");
		classlist.add("java.util.stream.Stream");
		classlist.add("java.util.stream.Stream$Builder");
		classlist.add("java.util.stream.StreamOpFlag");
		classlist.add("java.util.stream.StreamOpFlag$MaskBuilder");
		classlist.add("java.util.stream.StreamOpFlag$Type");
		classlist.add("java.util.stream.StreamShape");
		classlist.add("java.util.stream.StreamSupport");
		classlist.add("java.util.stream.Streams");
		classlist.add("java.util.stream.Streams$AbstractStreamBuilderImpl");
		classlist.add("java.util.stream.Streams$StreamBuilderImpl");
		classlist.add("java.util.stream.TerminalOp");
		classlist.add("java.util.stream.TerminalSink");
		classlist.add("java.util.zip.CRC32");
		classlist.add("java.util.zip.Checksum");
		classlist.add("java.util.zip.Checksum$1");
		classlist.add("java.util.zip.Inflater");
		classlist.add("java.util.zip.Inflater$InflaterZStreamRef");
		classlist.add("java.util.zip.InflaterInputStream");
		classlist.add("java.util.zip.ZipCoder");
		classlist.add("java.util.zip.ZipCoder$Comparison");
		classlist.add("java.util.zip.ZipCoder$UTF8ZipCoder");
		classlist.add("java.util.zip.ZipConstants");
		classlist.add("java.util.zip.ZipEntry");
		classlist.add("java.util.zip.ZipFile");
		classlist.add("java.util.zip.ZipFile$1");
		classlist.add("java.util.zip.ZipFile$2");
		classlist.add("java.util.zip.ZipFile$CleanableResource");
		classlist.add("java.util.zip.ZipFile$EntrySpliterator");
		classlist.add("java.util.zip.ZipFile$InflaterCleanupAction");
		classlist.add("java.util.zip.ZipFile$Source");
		classlist.add("java.util.zip.ZipFile$Source$End");
		classlist.add("java.util.zip.ZipFile$Source$Key");
		classlist.add("java.util.zip.ZipFile$ZipFileInflaterInputStream");
		classlist.add("java.util.zip.ZipFile$ZipFileInputStream");
		classlist.add("java.util.zip.ZipUtils");
		classlist.add("jdk.internal.access.JavaIOFileDescriptorAccess");
		classlist.add("jdk.internal.access.JavaIOPrintStreamAccess");
		classlist.add("jdk.internal.access.JavaIORandomAccessFileAccess");
		classlist.add("jdk.internal.access.JavaLangAccess");
		classlist.add("jdk.internal.access.JavaLangInvokeAccess");
		classlist.add("jdk.internal.access.JavaLangModuleAccess");
		classlist.add("jdk.internal.access.JavaLangRefAccess");
		classlist.add("jdk.internal.access.JavaLangReflectAccess");
		classlist.add("jdk.internal.access.JavaNetInetAddressAccess");
		classlist.add("jdk.internal.access.JavaNetURLAccess");
		classlist.add("jdk.internal.access.JavaNetUriAccess");
		classlist.add("jdk.internal.access.JavaNioAccess");
		classlist.add("jdk.internal.access.JavaSecurityAccess");
		classlist.add("jdk.internal.access.JavaSecurityPropertiesAccess");
		classlist.add("jdk.internal.access.JavaUtilConcurrentFJPAccess");
		classlist.add("jdk.internal.access.JavaUtilJarAccess");
		classlist.add("jdk.internal.access.JavaUtilResourceBundleAccess");
		classlist.add("jdk.internal.access.JavaUtilZipFileAccess");
		classlist.add("jdk.internal.access.SharedSecrets");
		classlist.add("jdk.internal.foreign.abi.ABIDescriptor");
		classlist.add("jdk.internal.foreign.abi.NativeEntryPoint");
		classlist.add("jdk.internal.foreign.abi.UpcallLinker$CallRegs");
		classlist.add("jdk.internal.foreign.abi.VMStorage");
		classlist.add("jdk.internal.jimage.BasicImageReader");
		classlist.add("jdk.internal.jimage.BasicImageReader$1");
		classlist.add("jdk.internal.jimage.ImageHeader");
		classlist.add("jdk.internal.jimage.ImageLocation");
		classlist.add("jdk.internal.jimage.ImageReader");
		classlist.add("jdk.internal.jimage.ImageReader$SharedImageReader");
		classlist.add("jdk.internal.jimage.ImageReaderFactory");
		classlist.add("jdk.internal.jimage.ImageReaderFactory$1");
		classlist.add("jdk.internal.jimage.ImageStrings");
		classlist.add("jdk.internal.jimage.ImageStringsReader");
		classlist.add("jdk.internal.jimage.NativeImageBuffer");
		classlist.add("jdk.internal.jimage.NativeImageBuffer$1");
		classlist.add("jdk.internal.jimage.decompressor.Decompressor");
		classlist.add("jdk.internal.loader.AbstractClassLoaderValue");
		classlist.add("jdk.internal.loader.AbstractClassLoaderValue$Memoizer");
		classlist.add("jdk.internal.loader.ArchivedClassLoaders");
		classlist.add("jdk.internal.loader.BootLoader");
		classlist.add("jdk.internal.loader.BuiltinClassLoader");
		classlist.add("jdk.internal.loader.BuiltinClassLoader$1");
		classlist.add("jdk.internal.loader.BuiltinClassLoader$2");
		classlist.add("jdk.internal.loader.BuiltinClassLoader$5");
		classlist.add("jdk.internal.loader.BuiltinClassLoader$LoadedModule");
		classlist.add("jdk.internal.loader.ClassLoaderHelper");
		classlist.add("jdk.internal.loader.ClassLoaderValue");
		classlist.add("jdk.internal.loader.ClassLoaders");
		classlist.add("jdk.internal.loader.ClassLoaders$AppClassLoader");
		classlist.add("jdk.internal.loader.ClassLoaders$BootClassLoader");
		classlist.add("jdk.internal.loader.ClassLoaders$PlatformClassLoader");
		classlist.add("jdk.internal.loader.FileURLMapper");
		classlist.add("jdk.internal.loader.NativeLibraries");
		classlist.add("jdk.internal.loader.NativeLibraries$1");
		classlist.add("jdk.internal.loader.NativeLibraries$2");
		classlist.add("jdk.internal.loader.NativeLibraries$3");
		classlist.add("jdk.internal.loader.NativeLibraries$CountedLock");
		classlist.add("jdk.internal.loader.NativeLibraries$LibraryPaths");
		classlist.add("jdk.internal.loader.NativeLibraries$NativeLibraryContext");
		classlist.add("jdk.internal.loader.NativeLibraries$NativeLibraryContext$1");
		classlist.add("jdk.internal.loader.NativeLibraries$NativeLibraryImpl");
		classlist.add("jdk.internal.loader.NativeLibrary");
		classlist.add("jdk.internal.loader.Resource");
		classlist.add("jdk.internal.loader.URLClassPath");
		classlist.add("jdk.internal.loader.URLClassPath$1");
		classlist.add("jdk.internal.loader.URLClassPath$3");
		classlist.add("jdk.internal.loader.URLClassPath$JarLoader");
		classlist.add("jdk.internal.loader.URLClassPath$JarLoader$1");
		classlist.add("jdk.internal.loader.URLClassPath$JarLoader$2");
		classlist.add("jdk.internal.loader.URLClassPath$Loader");
		classlist.add("jdk.internal.logger.BootstrapLogger");
		classlist.add("jdk.internal.logger.BootstrapLogger$BootstrapExecutors");
		classlist.add("jdk.internal.logger.BootstrapLogger$DetectBackend");
		classlist.add("jdk.internal.logger.BootstrapLogger$DetectBackend$1");
		classlist.add("jdk.internal.logger.BootstrapLogger$LoggingBackend");
		classlist.add("jdk.internal.logger.BootstrapLogger$RedirectedLoggers");
		classlist.add("jdk.internal.logger.DefaultLoggerFinder");
		classlist.add("jdk.internal.logger.DefaultLoggerFinder$1");
		classlist.add("jdk.internal.math.DoubleToDecimal");
		classlist.add("jdk.internal.math.FloatToDecimal");
		classlist.add("jdk.internal.math.MathUtils");
		classlist.add("jdk.internal.misc.Blocker");
		classlist.add("jdk.internal.misc.CDS");
		classlist.add("jdk.internal.misc.CarrierThread");
		classlist.add("jdk.internal.misc.CarrierThreadLocal");
		classlist.add("jdk.internal.misc.InnocuousThread");
		classlist.add("jdk.internal.misc.InternalLock");
		classlist.add("jdk.internal.misc.MainMethodFinder");
		classlist.add("jdk.internal.misc.OSEnvironment");
		classlist.add("jdk.internal.misc.PreviewFeatures");
		classlist.add("jdk.internal.misc.ScopedMemoryAccess");
		classlist.add("jdk.internal.misc.Signal");
		classlist.add("jdk.internal.misc.Signal$Handler");
		classlist.add("jdk.internal.misc.Signal$NativeHandler");
		classlist.add("jdk.internal.misc.TerminatingThreadLocal");
		classlist.add("jdk.internal.misc.TerminatingThreadLocal$1");
		classlist.add("jdk.internal.misc.Unsafe");
		classlist.add("jdk.internal.misc.UnsafeConstants");
		classlist.add("jdk.internal.misc.VM");
		classlist.add("jdk.internal.misc.VM$BufferPool");
		classlist.add("jdk.internal.module.ArchivedBootLayer");
		classlist.add("jdk.internal.module.ArchivedModuleGraph");
		classlist.add("jdk.internal.module.Builder");
		classlist.add("jdk.internal.module.Checks");
		classlist.add("jdk.internal.module.DefaultRoots");
		classlist.add("jdk.internal.module.ModuleBootstrap");
		classlist.add("jdk.internal.module.ModuleBootstrap$Counters");
		classlist.add("jdk.internal.module.ModuleBootstrap$SafeModuleFinder");
		classlist.add("jdk.internal.module.ModuleHashes");
		classlist.add("jdk.internal.module.ModuleHashes$HashSupplier");
		classlist.add("jdk.internal.module.ModuleInfo$Attributes");
		classlist.add("jdk.internal.module.ModuleLoaderMap");
		classlist.add("jdk.internal.module.ModuleLoaderMap$Mapper");
		classlist.add("jdk.internal.module.ModuleLoaderMap$Modules");
		classlist.add("jdk.internal.module.ModulePatcher");
		classlist.add("jdk.internal.module.ModulePath");
		classlist.add("jdk.internal.module.ModulePath$Patterns");
		classlist.add("jdk.internal.module.ModuleReferenceImpl");
		classlist.add("jdk.internal.module.ModuleReferences");
		classlist.add("jdk.internal.module.ModuleResolution");
		classlist.add("jdk.internal.module.ModuleTarget");
		classlist.add("jdk.internal.module.Modules");
		classlist.add("jdk.internal.module.Resources");
		classlist.add("jdk.internal.module.ServicesCatalog");
		classlist.add("jdk.internal.module.ServicesCatalog$ServiceProvider");
		classlist.add("jdk.internal.module.SystemModuleFinders");
		classlist.add("jdk.internal.module.SystemModuleFinders$2");
		classlist.add("jdk.internal.module.SystemModuleFinders$SystemImage");
		classlist.add("jdk.internal.module.SystemModuleFinders$SystemModuleFinder");
		classlist.add("jdk.internal.module.SystemModuleFinders$SystemModuleReader");
		classlist.add("jdk.internal.module.SystemModules");
		classlist.add("jdk.internal.module.SystemModules$all");
		classlist.add("jdk.internal.module.SystemModulesMap");
		classlist.add("jdk.internal.org.objectweb.asm.AnnotationVisitor");
		classlist.add("jdk.internal.org.objectweb.asm.AnnotationWriter");
		classlist.add("jdk.internal.org.objectweb.asm.Attribute");
		classlist.add("jdk.internal.org.objectweb.asm.ByteVector");
		classlist.add("jdk.internal.org.objectweb.asm.ClassVisitor");
		classlist.add("jdk.internal.org.objectweb.asm.ClassWriter");
		classlist.add("jdk.internal.org.objectweb.asm.ConstantDynamic");
		classlist.add("jdk.internal.org.objectweb.asm.FieldVisitor");
		classlist.add("jdk.internal.org.objectweb.asm.FieldWriter");
		classlist.add("jdk.internal.org.objectweb.asm.Frame");
		classlist.add("jdk.internal.org.objectweb.asm.Handle");
		classlist.add("jdk.internal.org.objectweb.asm.Handler");
		classlist.add("jdk.internal.org.objectweb.asm.Label");
		classlist.add("jdk.internal.org.objectweb.asm.MethodVisitor");
		classlist.add("jdk.internal.org.objectweb.asm.MethodWriter");
		classlist.add("jdk.internal.org.objectweb.asm.Symbol");
		classlist.add("jdk.internal.org.objectweb.asm.SymbolTable");
		classlist.add("jdk.internal.org.objectweb.asm.SymbolTable$Entry");
		classlist.add("jdk.internal.org.objectweb.asm.Type");
		classlist.add("jdk.internal.perf.Perf");
		classlist.add("jdk.internal.perf.Perf$GetPerfAction");
		classlist.add("jdk.internal.perf.PerfCounter");
		classlist.add("jdk.internal.perf.PerfCounter$CoreCounters");
		classlist.add("jdk.internal.ref.Cleaner");
		classlist.add("jdk.internal.ref.CleanerFactory");
		classlist.add("jdk.internal.ref.CleanerFactory$1");
		classlist.add("jdk.internal.ref.CleanerImpl");
		classlist.add("jdk.internal.ref.CleanerImpl$CleanerCleanable");
		classlist.add("jdk.internal.ref.CleanerImpl$PhantomCleanableRef");
		classlist.add("jdk.internal.ref.PhantomCleanable");
		classlist.add("jdk.internal.reflect.CallerSensitive");
		classlist.add("jdk.internal.reflect.ConstantPool");
		classlist.add("jdk.internal.reflect.ConstructorAccessor");
		classlist.add("jdk.internal.reflect.ConstructorAccessorImpl");
		classlist.add("jdk.internal.reflect.DelegatingClassLoader");
		classlist.add("jdk.internal.reflect.DirectConstructorHandleAccessor");
		classlist.add("jdk.internal.reflect.DirectConstructorHandleAccessor$NativeAccessor");
		classlist.add("jdk.internal.reflect.DirectMethodHandleAccessor");
		classlist.add("jdk.internal.reflect.FieldAccessor");
		classlist.add("jdk.internal.reflect.FieldAccessorImpl");
		classlist.add("jdk.internal.reflect.MagicAccessorImpl");
		classlist.add("jdk.internal.reflect.MethodAccessor");
		classlist.add("jdk.internal.reflect.MethodAccessorImpl");
		classlist.add("jdk.internal.reflect.MethodHandleAccessorFactory");
		classlist.add("jdk.internal.reflect.MethodHandleAccessorFactory$LazyStaticHolder");
		classlist.add("jdk.internal.reflect.MethodHandleFieldAccessorImpl");
		classlist.add("jdk.internal.reflect.MethodHandleObjectFieldAccessorImpl");
		classlist.add("jdk.internal.reflect.NativeConstructorAccessorImpl");
		classlist.add("jdk.internal.reflect.Reflection");
		classlist.add("jdk.internal.reflect.ReflectionFactory");
		classlist.add("jdk.internal.reflect.ReflectionFactory$Config");
		classlist.add("jdk.internal.reflect.ReflectionFactory$GetReflectionFactoryAction");
		classlist.add("jdk.internal.reflect.UnsafeFieldAccessorImpl");
		classlist.add("jdk.internal.reflect.UnsafeStaticFieldAccessorImpl");
		classlist.add("jdk.internal.util.ArraysSupport");
		classlist.add("jdk.internal.util.ByteArray");
		classlist.add("jdk.internal.util.ClassFileDumper");
		classlist.add("jdk.internal.util.EnvUtils");
		classlist.add("jdk.internal.util.Preconditions");
		classlist.add("jdk.internal.util.Preconditions$1");
		classlist.add("jdk.internal.util.Preconditions$2");
		classlist.add("jdk.internal.util.Preconditions$3");
		classlist.add("jdk.internal.util.Preconditions$4");
		classlist.add("jdk.internal.util.StaticProperty");
		classlist.add("jdk.internal.util.SystemProps");
		classlist.add("jdk.internal.util.SystemProps$Raw");
		classlist.add("jdk.internal.util.random.RandomSupport");
		classlist.add("jdk.internal.vm.Continuation");
		classlist.add("jdk.internal.vm.ContinuationScope");
		classlist.add("jdk.internal.vm.FillerObject");
		classlist.add("jdk.internal.vm.PostVMInitHook");
		classlist.add("jdk.internal.vm.PostVMInitHook$1");
		classlist.add("jdk.internal.vm.PostVMInitHook$2");
		classlist.add("jdk.internal.vm.SharedThreadContainer");
		classlist.add("jdk.internal.vm.StackChunk");
		classlist.add("jdk.internal.vm.StackableScope");
		classlist.add("jdk.internal.vm.ThreadContainer");
		classlist.add("jdk.internal.vm.ThreadContainers");
		classlist.add("jdk.internal.vm.ThreadContainers$RootContainer");
		classlist.add("jdk.internal.vm.ThreadContainers$RootContainer$TrackingRootContainer");
		classlist.add("jdk.internal.vm.vector.VectorSupport");
		classlist.add("jdk.internal.vm.vector.VectorSupport$Vector");
		classlist.add("jdk.internal.vm.vector.VectorSupport$VectorMask");
		classlist.add("jdk.internal.vm.vector.VectorSupport$VectorPayload");
		classlist.add("jdk.internal.vm.vector.VectorSupport$VectorShuffle");
		classlist.add("sun.invoke.empty.Empty");
		classlist.add("sun.invoke.util.BytecodeDescriptor");
		classlist.add("sun.invoke.util.ValueConversions");
		classlist.add("sun.invoke.util.ValueConversions$WrapperCache");
		classlist.add("sun.invoke.util.VerifyAccess");
		classlist.add("sun.invoke.util.VerifyType");
		classlist.add("sun.invoke.util.Wrapper");
		classlist.add("sun.invoke.util.Wrapper$Format");
		classlist.add("sun.io.Win32ErrorMode");
		classlist.add("sun.launcher.LauncherHelper");
		classlist.add("sun.net.util.IPAddressUtil");
		classlist.add("sun.net.util.IPAddressUtil$MASKS");
		classlist.add("sun.net.util.URLUtil");
		classlist.add("sun.net.www.ParseUtil");
		classlist.add("sun.net.www.protocol.file.Handler");
		classlist.add("sun.net.www.protocol.jar.Handler");
		classlist.add("sun.nio.ByteBuffered");
		classlist.add("sun.nio.ch.DirectBuffer");
		classlist.add("sun.nio.cs.ArrayDecoder");
		classlist.add("sun.nio.cs.ArrayEncoder");
		classlist.add("sun.nio.cs.HistoricallyNamedCharset");
		classlist.add("sun.nio.cs.ISO_8859_1");
		classlist.add("sun.nio.cs.MS1252");
		classlist.add("sun.nio.cs.MS1252$Holder");
		classlist.add("sun.nio.cs.SingleByte");
		classlist.add("sun.nio.cs.SingleByte$Decoder");
		classlist.add("sun.nio.cs.SingleByte$Encoder");
		classlist.add("sun.nio.cs.StandardCharsets");
		classlist.add("sun.nio.cs.StandardCharsets$Aliases");
		classlist.add("sun.nio.cs.StandardCharsets$Cache");
		classlist.add("sun.nio.cs.StandardCharsets$Classes");
		classlist.add("sun.nio.cs.StreamEncoder");
		classlist.add("sun.nio.cs.US_ASCII");
		classlist.add("sun.nio.cs.UTF_16");
		classlist.add("sun.nio.cs.UTF_16BE");
		classlist.add("sun.nio.cs.UTF_16LE");
		classlist.add("sun.nio.cs.UTF_8");
		classlist.add("sun.nio.cs.Unicode");
		classlist.add("sun.nio.fs.AbstractBasicFileAttributeView");
		classlist.add("sun.nio.fs.AbstractFileSystemProvider");
		classlist.add("sun.nio.fs.DefaultFileSystemProvider");
		classlist.add("sun.nio.fs.DynamicFileAttributeView");
		classlist.add("sun.nio.fs.NativeBuffer");
		classlist.add("sun.nio.fs.NativeBuffer$Deallocator");
		classlist.add("sun.nio.fs.NativeBuffers");
		classlist.add("sun.nio.fs.NativeBuffers$1");
		classlist.add("sun.nio.fs.Util");
		classlist.add("sun.nio.fs.WindowsFileAttributeViews");
		classlist.add("sun.nio.fs.WindowsFileAttributeViews$Basic");
		classlist.add("sun.nio.fs.WindowsFileAttributes");
		classlist.add("sun.nio.fs.WindowsFileSystem");
		classlist.add("sun.nio.fs.WindowsFileSystemProvider");
		classlist.add("sun.nio.fs.WindowsNativeDispatcher");
		classlist.add("sun.nio.fs.WindowsNativeDispatcher$Account");
		classlist.add("sun.nio.fs.WindowsNativeDispatcher$AclInformation");
		classlist.add("sun.nio.fs.WindowsNativeDispatcher$CompletionStatus");
		classlist.add("sun.nio.fs.WindowsNativeDispatcher$DiskFreeSpace");
		classlist.add("sun.nio.fs.WindowsNativeDispatcher$FirstFile");
		classlist.add("sun.nio.fs.WindowsNativeDispatcher$FirstStream");
		classlist.add("sun.nio.fs.WindowsNativeDispatcher$VolumeInformation");
		classlist.add("sun.nio.fs.WindowsPath");
		classlist.add("sun.nio.fs.WindowsPathParser");
		classlist.add("sun.nio.fs.WindowsPathParser$Result");
		classlist.add("sun.nio.fs.WindowsPathType");
		classlist.add("sun.nio.fs.WindowsUriSupport");
		classlist.add("sun.reflect.annotation.AnnotationParser");
		classlist.add("sun.security.action.GetBooleanAction");
		classlist.add("sun.security.action.GetIntegerAction");
		classlist.add("sun.security.action.GetPropertyAction");
		classlist.add("sun.security.util.Debug");
		classlist.add("sun.security.util.FilePermCompat");
		classlist.add("sun.security.util.LazyCodeSourcePermissionCollection");
		classlist.add("sun.security.util.SecurityProperties");
		classlist.add("sun.security.util.SignatureFileVerifier");
		classlist.add("sun.text.resources.cldr.FormatData");
		classlist.add("sun.util.PreHashedMap");
		classlist.add("sun.util.calendar.AbstractCalendar");
		classlist.add("sun.util.calendar.BaseCalendar");
		classlist.add("sun.util.calendar.BaseCalendar$Date");
		classlist.add("sun.util.calendar.CalendarDate");
		classlist.add("sun.util.calendar.CalendarSystem");
		classlist.add("sun.util.calendar.CalendarSystem$GregorianHolder");
		classlist.add("sun.util.calendar.CalendarUtils");
		classlist.add("sun.util.calendar.Gregorian");
		classlist.add("sun.util.calendar.Gregorian$Date");
		classlist.add("sun.util.calendar.ZoneInfo");
		classlist.add("sun.util.calendar.ZoneInfoFile");
		classlist.add("sun.util.calendar.ZoneInfoFile$1");
		classlist.add("sun.util.calendar.ZoneInfoFile$ZoneOffsetTransitionRule");
		classlist.add("sun.util.cldr.CLDRBaseLocaleDataMetaInfo");
		classlist.add("sun.util.cldr.CLDRCalendarDataProviderImpl");
		classlist.add("sun.util.cldr.CLDRLocaleProviderAdapter");
		classlist.add("sun.util.locale.BaseLocale");
		classlist.add("sun.util.locale.BaseLocale$Cache");
		classlist.add("sun.util.locale.BaseLocale$Key");
		classlist.add("sun.util.locale.InternalLocaleBuilder");
		classlist.add("sun.util.locale.InternalLocaleBuilder$CaseInsensitiveChar");
		classlist.add("sun.util.locale.LanguageTag");
		classlist.add("sun.util.locale.LocaleObjectCache");
		classlist.add("sun.util.locale.LocaleObjectCache$CacheEntry");
		classlist.add("sun.util.locale.LocaleUtils");
		classlist.add("sun.util.locale.ParseStatus");
		classlist.add("sun.util.locale.StringTokenIterator");
		classlist.add("sun.util.locale.provider.AvailableLanguageTags");
		classlist.add("sun.util.locale.provider.CalendarDataProviderImpl");
		classlist.add("sun.util.locale.provider.CalendarDataUtility");
		classlist.add("sun.util.locale.provider.CalendarDataUtility$CalendarWeekParameterGetter");
		classlist.add("sun.util.locale.provider.CalendarProviderImpl");
		classlist.add("sun.util.locale.provider.DateFormatProviderImpl");
		classlist.add("sun.util.locale.provider.DateFormatSymbolsProviderImpl");
		classlist.add("sun.util.locale.provider.DecimalFormatSymbolsProviderImpl");
		classlist.add("sun.util.locale.provider.JRELocaleProviderAdapter");
		classlist.add("sun.util.locale.provider.LocaleDataMetaInfo");
		classlist.add("sun.util.locale.provider.LocaleProviderAdapter");
		classlist.add("sun.util.locale.provider.LocaleProviderAdapter$Type");
		classlist.add("sun.util.locale.provider.LocaleResources");
		classlist.add("sun.util.locale.provider.LocaleResources$ResourceReference");
		classlist.add("sun.util.locale.provider.LocaleServiceProviderPool");
		classlist.add("sun.util.locale.provider.LocaleServiceProviderPool$LocalizedObjectGetter");
		classlist.add("sun.util.locale.provider.NumberFormatProviderImpl");
		classlist.add("sun.util.locale.provider.ResourceBundleBasedAdapter");
		classlist.add("sun.util.logging.PlatformLogger$Bridge");
		classlist.add("sun.util.logging.PlatformLogger$ConfigurableBridge");
		classlist.add("sun.util.logging.internal.LoggingProviderImpl");
		classlist.add("sun.util.logging.internal.LoggingProviderImpl$LogManagerAccess");
		classlist.add("sun.util.resources.Bundles");
		classlist.add("sun.util.resources.Bundles$1");
		classlist.add("sun.util.resources.Bundles$BundleReference");
		classlist.add("sun.util.resources.Bundles$CacheKey");
		classlist.add("sun.util.resources.Bundles$CacheKeyReference");
		classlist.add("sun.util.resources.Bundles$Strategy");
		classlist.add("sun.util.resources.LocaleData");
		classlist.add("sun.util.resources.LocaleData$1");
		classlist.add("sun.util.resources.LocaleData$LocaleDataStrategy");
		classlist.add("sun.util.resources.cldr.CalendarData");
		classlist.add("sun.util.spi.CalendarProvider");

	}
	public void useImportClassInit()
	{
		importlist.clear();
		importlist.add("import java.lang.String;");
		importlist.add("import java.lang.StringBuilder;");
		importlist.add("import java.lang.System;");
	}
	public void keyListInit() {
		keylist.clear();
		keylist.add("@Override");
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
	}
	private StyleSpans<Collection<String>> computeHighlighting(String text, List<String> keywords,List<String> classwords,List<String> aclasswords) {
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		List<String> simpleClassNames = classwords.stream().map(full -> full.substring(full.lastIndexOf('.') + 1)).collect(Collectors.toList());

		String[] tokens = text.split("(?<=\\s|[;({])|(?=\\s|[;({])");
		

		for (String token : tokens) {
			
			
			
			if (keywords.contains(token)) {
				spansBuilder.add(Collections.singleton("keyword"), token.length());
			}else if(aclasswords.contains(token)){
				spansBuilder.add(Collections.singleton("keyword2"), token.length());
			}else {
				spansBuilder.add(Collections.emptyList(), token.length());
			}
			
		}
		return spansBuilder.create();
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
		for(String temp : importClassList)
		{
			String tempName = temp.replace("import","").replace(";","").trim();
			String className = tempName.substring(tempName.lastIndexOf('.')+1);
			useClassList.add(className);
		}
		return useClassList;
		
	}
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
				String[] varParts = variables.split(",");
				for (String var : varParts) {
					String varName = var.split("=")[0].trim(); // 초기화 제거
					if (!varName.isEmpty()) {
						classValList.add(varName);
					}
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
			String[] parts = line.split(",");
			for (String part : parts) {
				String var = part.split("=")[0].trim(); // = 앞까지만
				if (!var.isEmpty()) {
					intValList.add(var);
				}
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
			String[] parts = line.split(",");
			for (String part : parts) {
				String var = part.split("=")[0].trim(); // = 앞까지만
				if (!var.isEmpty()) {
					charValList.add(var);
				}
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
			String[] parts = line.split(",");
			for (String part : parts) {
				String var = part.split("=")[0].trim(); // = 앞까지만
				if (!var.isEmpty()) {
					floatValList.add(var);
				}
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
			String[] parts = line.split(",");
			for (String part : parts) {
				String var = part.split("=")[0].trim(); // = 앞까지만
				if (!var.isEmpty()) {
					doubleValList.add(var);
				}
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
			String[] parts = line.split(",");
			for (String part : parts) {
				String var = part.split("=")[0].trim(); // = 앞까지만
				if (!var.isEmpty()) {
					byteValList.add(var);
				}
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
			String[] parts = line.split(",");
			for (String part : parts) {
				String var = part.split("=")[0].trim(); // = 앞까지만
				if (!var.isEmpty()) {
					longValList.add(var);
				}
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
			String[] parts = line.split(",");
			for (String part : parts) {
				String var = part.split("=")[0].trim(); // = 앞까지만
				if (!var.isEmpty()) {
					shortValList.add(var);
				}
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
			String[] parts = line.split(",");
			for (String part : parts) {
				String var = part.split("=")[0].trim(); // = 앞까지만
				if (!var.isEmpty()) {
					booleanValList.add(var);
				}
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
			String[] parts = line.split(",");
			for (String part : parts) {
				String var = part.split("=")[0].trim(); // = 앞까지만
				if (!var.isEmpty()) {
					enumValList.add(var);
				}
			}
		}

		return enumValList;
	}
	private List<String> setFileClassList()
	{
		List<String> fileClassList = new ArrayList<>();
		
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
		}, 0);	
		
		return classFileList;
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

	
}