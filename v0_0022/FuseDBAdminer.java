import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Orientation;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Modality;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import java.io.File;

import FuseDB.*;
import Version.Version;
public class FuseDBAdminer extends Application {
	private TabPane editorTabs = new TabPane();
	private TreeItem<String> tablesNode = new TreeItem<>("Tables");
	private TreeItem<String> viewsNode = new TreeItem<>("Views");
	private TableData tdata = new TableData();
	private FuseDBExcute exc = new FuseDBExcute();
	private TableView<Map<String, Object>> resultTable = new TableView<>();
	private TextArea logConsole = new TextArea();
	private static String commonStyle = "-fx-font-size: 18px;";
	private Thread runThread;
	private volatile boolean stopRequested = false;
	public Version ver = new Version();
	@Override
	public void start(Stage stage) {
		
		// ---------------------------
		// 1) 상단 툴바
		// ---------------------------
		Button btnNew = new Button("New");
		Button btnOpen = new Button("Open");
		Button btnSave = new Button("Save");
		Button btnSaveAs = new Button("Save As");
		Button btnRun = new Button("Run ▶");
		Button btnStop = new Button("Stop ■");
		Button btnSettings = new Button("Information");
		Button btnRefresh = new Button("Refresh");
		Button btnExit = new Button("Exit");
		
		ToolBar toolBar = new ToolBar(
			btnNew,
			btnOpen,
			btnSave,
			btnSaveAs,
			new Separator(),
			btnRun,
			btnStop,
			new Separator(),
			btnSettings,
			btnRefresh,
			btnExit
			);
		btnNew.setOnAction(e -> {
			TextArea ta = new TextArea();
			ta.setStyle(commonStyle);
			Tab newTab = new Tab("noname",ta);
			newTab.setClosable(true);
			editorTabs.getTabs().add(newTab);
			editorTabs.getSelectionModel().select(newTab);
		});
		//열기 버튼을 눌렀을 때...
		btnOpen.setOnAction(e -> {
			
			// FuseSQL 폴더 준비
			File defaultDir = new File("FuseSQL");
			if (!defaultDir.exists()) {
				defaultDir.mkdir();   // 폴더 없으면 자동 생성
			}
			
			// FileChooser 설정
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open FSQL File");
			fileChooser.setInitialDirectory(defaultDir);
			
			// 확장자 필터
			FileChooser.ExtensionFilter extFilter =
			new FileChooser.ExtensionFilter("FuseDB SQL (*.fsql)", "*.fsql");
			fileChooser.getExtensionFilters().add(extFilter);
			
			// 파일 선택
			File file = fileChooser.showOpenDialog(stage);
			if (file == null) return;
			// 파일 이름
			String fileName = file.getName();
			// 파일 내용 읽기
			StringBuilder sb = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				return;
			}
			
			// 🔥 같은 이름의 탭이 있는지 검사
			Tab existingTab = null;
			for (Tab tab : editorTabs.getTabs()) {
				if (tab.getText().equals(fileName)) {
					existingTab = tab;
					break;
				}
			}
			
			if (existingTab != null) {
				// 🔥 이미 같은 이름의 탭이 있으면 → 그 탭에 내용 로드
				if (existingTab.getContent() instanceof TextArea) {
					((TextArea) existingTab.getContent()).setText(sb.toString());
				}
				editorTabs.getSelectionModel().select(existingTab);
				
			} else {
				// 🔥 같은 이름의 탭이 없으면 → 새 탭 생성
				TextArea textArea = new TextArea(sb.toString());
				
				textArea.setStyle(commonStyle);
				Tab loadTab = new Tab(fileName, textArea);
				loadTab.setClosable(true);
				
				editorTabs.getTabs().add(loadTab);
				editorTabs.getSelectionModel().select(loadTab);
			}
		});
		
		//저장버튼을 눌렀을 때....
		btnSave.setOnAction(e -> {
			
			// 현재 선택된 탭 가져오기
			Tab selectedTab = editorTabs.getSelectionModel().getSelectedItem();
			if (selectedTab == null) return;
			String tabName = selectedTab.getText();
			// 탭 안의 TextArea 가져오기
			if (!(selectedTab.getContent() instanceof TextArea)) return;
			TextArea textArea = (TextArea) selectedTab.getContent();
			String content = textArea.getText();
			// 🔥 기본 경로를 FuseSQL 폴더로 설정
			File defaultDir = new File("FuseSQL");
			if (!defaultDir.exists()) {
				defaultDir.mkdir();   // 🔥 폴더 없으면 자동 생성
			}
			File file;
			//System.out.println(tabName);
			if(tabName.equals("noname")){
				// FileChooser 열기
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save FSQL File");
				// 기본 경로 설정
				fileChooser.setInitialDirectory(defaultDir);
				// 기본 확장자 필터
				FileChooser.ExtensionFilter extFilter =
				new FileChooser.ExtensionFilter("FuseDB SQL (*.fsql)", "*.fsql");
				fileChooser.getExtensionFilters().add(extFilter);
				
				// 저장할 파일 선택
				file = fileChooser.showSaveDialog(stage);
				if (file == null) return;
			}else if(tabName.equals("FuseDBInfo.txt")){
				// 🔥 탭 이름으로 파일 자동 지정
				file = new File("FuseSQL/" + tabName);
			}else{
				// 🔥 탭 이름으로 파일 자동 지정
				file = new File("FuseSQL/" + tabName);
				
				// 🔥 확장자 자동 추가
				if (!file.getName().endsWith(".fsql")) {
					file = new File(file.getAbsolutePath() + ".fsql");
				}
			}
			
			// 확장자 자동 추가
			
			if (!file.getName().endsWith(".fsql") && !file.getName().equals("FuseDBInfo.txt")) {
				file = new File(file.getAbsolutePath() + ".fsql");
			}
			
			// 파일에 내용 저장
			try (FileWriter writer = new FileWriter(file)) {
				writer.write(content);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			// 🔥 저장한 파일 이름으로 탭 제목 변경
			selectedTab.setText(file.getName());
		});
		//다른이름으로 저장 버튼을 눌렀을 때...
		btnSaveAs.setOnAction(e -> {
			
			// 현재 선택된 탭 가져오기
			Tab selectedTab = editorTabs.getSelectionModel().getSelectedItem();
			if (selectedTab == null) return;
			
			// 탭 안의 TextArea 가져오기
			if (!(selectedTab.getContent() instanceof TextArea)) return;
			TextArea textArea = (TextArea) selectedTab.getContent();
			String content = textArea.getText();
			// 🔥 기본 경로를 FuseSQL 폴더로 설정
			File defaultDir = new File("FuseSQL");
			if (!defaultDir.exists()) {
				defaultDir.mkdir();   // 🔥 폴더 없으면 자동 생성
			}
			// FileChooser 열기
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save FSQL File");
			// 기본 경로 설정
			fileChooser.setInitialDirectory(defaultDir);
			// 기본 확장자 필터
			FileChooser.ExtensionFilter extFilter =
			new FileChooser.ExtensionFilter("FuseDB SQL (*.fsql)", "*.fsql");
			fileChooser.getExtensionFilters().add(extFilter);
			
			// 저장할 파일 선택
			File file = fileChooser.showSaveDialog(stage);
			if (file == null) return;
			
			// 확장자 자동 추가
			if (!file.getName().endsWith(".fsql")) {
				file = new File(file.getAbsolutePath() + ".fsql");
			}
			
			// 파일에 내용 저장
			try (FileWriter writer = new FileWriter(file)) {
				writer.write(content);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			// 🔥 저장한 파일 이름으로 탭 제목 변경
			selectedTab.setText(file.getName());
		});
		btnRun.setOnAction(e -> {
			// 콘솔 초기화
			logConsole.clear();
			exc.sqlMessage = "";
			runThread = new Thread(() -> {
				// 현재 선택된 탭 가져오기
				Tab selectedTab = editorTabs.getSelectionModel().getSelectedItem();
				if (selectedTab == null) return;
				if(selectedTab.getText().equals("FuseDBInfo.txt")) return;
				// 탭 안의 TextArea 가져오기
				if (!(selectedTab.getContent() instanceof TextArea)) return;
				TextArea textArea = (TextArea) selectedTab.getContent();
				
				
				// 전체 텍스트 가져오기
				String allText = textArea.getText();
				if (allText.trim().isEmpty()) return;
				
				// 줄 단위로 나누기
				String[] lines = allText.split("\n");
				
				// 🔥 모든 줄을 하나씩 실행
				for (String line : lines) {
					if (stopRequested) {
						Platform.runLater(() -> logConsole.appendText("실행 중단됨\n"));
						return; // 스레드 종료
					}
					line = line.trim();
					if (!line.isEmpty()) {
						try {
							//exc = new FuseDBExcute();
							exc.excute(line);
							exc.saveDB();
							// 🔥 실행 메시지를 콘솔에 추가
							if (exc.sqlMessage != null && !exc.sqlMessage.isEmpty()) {
								Platform.runLater(() -> {
									logConsole.appendText(exc.sqlMessage + "\n");
								});
							}
						} catch (Exception ex) {
							Platform.runLater(() -> {
								logConsole.appendText("ERROR: " + ex.getMessage() + "\n");
							});
						}
					}
				}
				
				Platform.runLater(() -> logConsole.appendText("실행 완료\n"));
				
				
				
				
				
				
				Platform.runLater(() ->{
					// 실행 후 DB 저장
					//FuseDBExcute exc = new FuseDBExcute();
					
					resultTable.getColumns().clear();
					resultTable.getItems().clear();
					exc.LoadDB();
					tableFileRefresh();
					viewFileRefresh();
					tdata = exc.tempData;
					// TableView 초기화
					resultTable.getColumns().clear();
					resultTable.getItems().clear();
					// 🔥 컬럼 생성
					for (Column col : tdata.columns) {
						TableColumn<Map<String, Object>, Object> tableColumn =
						new TableColumn<>(col.name);
						
						tableColumn.setCellValueFactory(
							param -> new javafx.beans.property.SimpleObjectProperty<>(
								param.getValue().get(col.name)
								)
							);
						
						resultTable.getColumns().add(tableColumn);
					}
					
					// 🔥 데이터 삽입
					for (List<Object> row : tdata.datas) {
						Map<String, Object> rowMap = new LinkedHashMap<>();
						
						for (int i = 0; i < tdata.columns.size(); i++) {
							String colName = tdata.columns.get(i).name;
							rowMap.put(colName, row.get(i));
						}
						
						resultTable.getItems().add(rowMap);
					}
					
				});
			});
			runThread.start();
		});
		btnStop.setOnAction(e -> {
			stopRequested =true;
		});
		
		btnSettings.setOnAction(e ->{
			// 새 창(Stage) 생성
			Stage infoStage = new Stage();
			infoStage.setTitle("제품 정보");
			
			// 라벨
			Label label = new Label("제품 정보 :");
			
			// 텍스트 영역
			TextArea textArea = new TextArea();
			textArea.setStyle("-fx-font-size: 15px;");
			textArea.setEditable(false); // 🔒 텍스트 수정 불가
			
			ScrollPane textScrollPane = new ScrollPane(textArea);
			textScrollPane.setFitToWidth(true);
			textScrollPane.setFitToHeight(true);
			textArea.appendText("만든이 : 컴퓨터판타지연구소\n");
			textArea.appendText("퓨즈DB관리기\n");
			textArea.appendText("앱 이름 : " + ver.app_Name + "\n");
			textArea.appendText("앱 버전 : " + ver.app_Version + "\n");
			textArea.appendText("앱 코드 : " + ver.app_Code + "\n");
			textArea.appendText("사용언어 : java 21이상\n");
			textArea.appendText("아파치2.0 라이센스를 사용합니다.\n");
			textArea.appendText("다운로드 : http://javafantasy.kr\n");
			textArea.appendText("빅데이터용는 절대 아닙니다.\n");
			textArea.appendText("관계형데이터베이스가 아닙니다.\n");
			textArea.appendText("외래키사용은 없습니다.\n");
			textArea.appendText("간단한 게임제작에 사용목적으로 만들었습니다.\n");
			textArea.appendText("하루만에 만들었습니다.\n");
			textArea.appendText("대문자를 넣으면 안됩니다. 소문자만 됩니다.\n");
			textArea.appendText("정석 sql과 많이 다를 수 있습니다.\n");
			textArea.appendText("오류가 많을 수 있습니다.\n");
			textArea.appendText("AI가 많이 짜주었습니다. 제가 테스트해보고 원형모델로 업데이트 할 생각입니다.\n");
			textArea.appendText("API도 따로 올려놓겠습니다.\n");
			textArea.appendText("fusedatabase폴더에 저장되어야 합니다.\n");
			
			textArea.appendText("이 앱 코드수는 모두 합쳐서 1800줄입니다.\n");
			textArea.appendText("계속 오류가 발생되지만, 계속 오류를 찾아서 업데이트하겠습니다.\n");
			textArea.appendText("리눅스는 귀찮아서 윈도우에서만 구동하도록 만들겠습니다.\n");
			textArea.appendText("최대한 리눅스도 작동되도록 하겠으나 테스트는 안합니다.\n");
			textArea.appendText("0.0022A버전에서는 글자크기를조금 키웠습니다.\n");
			
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
		//새로고침 버튼
		btnRefresh.setOnAction(e-> {
			// TableView 초기화
			resultTable.getColumns().clear();
			resultTable.getItems().clear();
			exc.LoadDB();
			tableFileRefresh();
			viewFileRefresh();
		});
		btnExit.setOnAction(e -> {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("정보");
			alert.setHeaderText(null);
			alert.setContentText("앱 : http://javafantasy.kr ← 감사합니다.");
			
			alert.showAndWait();  // 사용자가 OK 누를 때까지 기다림
			
			// 프로그램 종료
			Platform.exit();
		});
		
		// ---------------------------
		// 2) 좌측 DB 탐색기
		// ---------------------------
		TreeItem<String> rootItem = new TreeItem<>("DB Connections");
		rootItem.setExpanded(true);
		
		
		
		
		
		TreeItem<String> localDB = new TreeItem<>("LocalDB");
		localDB.getChildren().addAll(tablesNode, viewsNode);
		
		rootItem.getChildren().add(localDB);
		
		TreeView<String> dbExplorer = new TreeView<>(rootItem);
		dbExplorer.setPrefWidth(220);
		tableFileRefresh();
		viewFileRefresh();
		// ---------------------------
		// 3) 중앙 쿼리 에디터 (탭 구조)
		// ---------------------------
		
		// FuseSQL 폴더 준비
		File fuseSqlDir = new File("FuseSQL");
		if (!fuseSqlDir.exists()) {
			fuseSqlDir.mkdir(); // 폴더 없으면 생성
		}
		
		// FuseDBInfo.txt 파일 준비
		File infoFile = new File(fuseSqlDir, "FuseDBInfo.txt");
		
		// 파일이 없으면 새로 생성
		if (!infoFile.exists()) {
			try {
				infoFile.createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		// 파일 내용 읽기
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(infoFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		// TextArea에 내용 넣기
		TextArea infoArea = new TextArea(sb.toString());
		infoArea.setStyle("-fx-font-size: 18px;");
		// 탭 생성 (파일 이름으로)
		Tab tab1 = new Tab(infoFile.getName(), infoArea);
		tab1.setClosable(false);
		
		// 탭 추가
		editorTabs.getTabs().add(tab1);
		editorTabs.getSelectionModel().select(tab1);
		
		
		// ---------------------------
		// 4) 하단 결과 패널
		// ---------------------------
		
		resultTable.setPlaceholder(new Label("쿼리 결과가 여기에 표시됩니다."));
		
		// ---------------------------
		// 5) 맨 아래 로그/에러 콘솔
		// ---------------------------
		
		logConsole.setEditable(false);
		logConsole.setPrefHeight(120);
		logConsole.setText("[INFO] FuseDB Manager started.\n");
		
		// ---------------------------
		// 결과 패널 + 로그 콘솔 (수직 SplitPane)
		// ---------------------------
		SplitPane bottomSplit = new SplitPane();
		bottomSplit.setOrientation(Orientation.VERTICAL);
		bottomSplit.getItems().addAll(resultTable, logConsole);
		bottomSplit.setDividerPositions(0.6);
		
		// ---------------------------
		// 좌측 DB 탐색기 + 중앙 에디터 (수평 SplitPane)
		// ---------------------------
		SplitPane centerSplit = new SplitPane();
		centerSplit.getItems().addAll(dbExplorer, editorTabs);
		centerSplit.setDividerPositions(0.22);
		
		// ---------------------------
		// 전체 레이아웃 (BorderPane)
		// ---------------------------
		BorderPane root = new BorderPane();
		root.setTop(toolBar);
		root.setLeft(null); // left는 centerSplit에 포함됨
		root.setCenter(centerSplit);
		root.setBottom(bottomSplit);
		
		Scene scene = new Scene(root, 1400, 900);
		
		stage.setTitle("FuseDB 관리기v" + ver.app_Version);
		System.out.println(getClass().getResource("fuseDBAdminitor.png"));
		//stage.getIcons().add(new Image(getClass().getResource("/icons/fuseDBAdminitor.png").toString()));
		try{
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/fuseDB.png")));
		}catch(Exception ite)
		{
			ite.printStackTrace();  // 예외의 전체 스택 트레이스를 콘솔에 출력
			
		}
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
	private void tableFileRefresh()
	{
		File[] files = null;
		
		File dbFolder = new File("fusedatabase"); // 폴더 이름
		if (dbFolder.exists() && dbFolder.isDirectory()) {
			
			files = dbFolder.listFiles((dir, name) -> name.endsWith(".fdbf"));
			tablesNode.getChildren().clear();
			for(File file : files)
			{
				String table = file.getName().replace(".fdbf","");
				tablesNode.getChildren().add(new TreeItem<>(table));
			}
		}
		
		
		
	}
	private void viewFileRefresh()
	{
		File[] files = null;
		
		File dbFolder = new File("fusedatabase"); // 폴더 이름
		if (dbFolder.exists() && dbFolder.isDirectory()) {
			
			files = dbFolder.listFiles((dir, name) -> name.endsWith(".fvf"));
			viewsNode.getChildren().clear();
			for(File file : files)
			{
				String table = file.getName().replace(".fvf","");
				viewsNode.getChildren().add(new TreeItem<>(table));
			}
		}
		
		
		
	}
}
