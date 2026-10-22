package AJEStruct;

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



import java.util.List;
import java.io.*;
import java.util.*;
import java.util.jar.*;

public class JARCreater
{
	Stage jarStage;
	VBox root;
	private boolean foldersel = false;
	private TextField pathField,nameField,verField,mainField,cpField,createField;
	ListView<String> fileListView;
	List<File> tmplist;
	CheckBox upperFolderChk;
		
	RadioButton rbClassOnly,rbAllFiles;
		
	private String[] jarTmpStr = new String[10];
	private List<String> jarTmpList = new ArrayList<>();
	public JARCreater(String stageName)
	{
		tmplist = new ArrayList<>();
		jarStage = new Stage();
		jarStage.setTitle(stageName);
		jarStage.initModality(Modality.APPLICATION_MODAL);
		
		root = new VBox(12);
		root.setPadding(new Insets(15));
		
		// -------------------------------
		// 1) 초기화 버튼
		// -------------------------------
		Button resetBtn = new Button("초기화");
		
		// -------------------------------
		// 2) Jar 생성 경로
		// -------------------------------
		Label pathLabel = new Label("Jar 생성경로 : ");
		pathField = new TextField();
		HBox.setHgrow(pathField, Priority.ALWAYS);
		pathField.setMaxWidth(Double.MAX_VALUE);
		Button pathBtn = new Button("...");
		HBox pathBox = new HBox(10, pathLabel, pathField, pathBtn);
		pathBox.setAlignment(Pos.CENTER_LEFT);
		
		// -------------------------------
		// 3) JAR 이름
		// -------------------------------
		Label nameLabel = new Label("JAR 이름 : ");
		nameField = new TextField();
		//nameField.setText("test.jar");
		HBox.setHgrow(nameField, Priority.ALWAYS);
		nameField.setMaxWidth(Double.MAX_VALUE);
		HBox nameBox = new HBox(10, nameLabel, nameField);
		nameBox.setAlignment(Pos.CENTER_LEFT);
		
		// -------------------------------
		// 4) MANIFEST 버전
		// -------------------------------
		Label verLabel = new Label("MANIFEST 버전 : ");
		verField = new TextField();
		//verField.setText("1.0");
		HBox.setHgrow(verField, Priority.ALWAYS);
		verField.setMaxWidth(Double.MAX_VALUE);
		HBox verBox = new HBox(10, verLabel, verField);
		verBox.setAlignment(Pos.CENTER_LEFT);
		
		// -------------------------------
		// 5) Main-Class
		// -------------------------------
		Label mainLabel = new Label("Main_Class : ");
		mainField = new TextField();
		HBox.setHgrow(mainField, Priority.ALWAYS);
		mainField.setMaxWidth(Double.MAX_VALUE);
		HBox mainBox = new HBox(10, mainLabel, mainField);
		mainBox.setAlignment(Pos.CENTER_LEFT);
		
		// -------------------------------
		// 6) Class-Path + cp 선택 버튼
		// -------------------------------
		Label cpLabel = new Label("Class_Path : ");
		cpField = new TextField();
		HBox.setHgrow(cpField, Priority.ALWAYS);
		cpField.setMaxWidth(Double.MAX_VALUE);
		Button cpBtn = new Button("cp 선택");
		HBox cpBox = new HBox(10, cpLabel, cpField, cpBtn);
		cpBox.setAlignment(Pos.CENTER_LEFT);
		
		// -------------------------------
		// 7) Create by
		// -------------------------------
		Label createLabel = new Label("Create by : ");
		createField = new TextField();
		HBox.setHgrow(createField, Priority.ALWAYS);
		createField.setMaxWidth(Double.MAX_VALUE);
		HBox createBox = new HBox(10, createLabel, createField);
		createBox.setAlignment(Pos.CENTER_LEFT);
		// -------------------------------
		// 8) 상위폴더 + class파일/모든파일 선택
		// -------------------------------
		upperFolderChk = new CheckBox("상위폴더");
		
		rbClassOnly = new RadioButton("class파일");
		rbAllFiles = new RadioButton("모든파일");
		
		ToggleGroup fileTypeGroup = new ToggleGroup();
		rbClassOnly.setToggleGroup(fileTypeGroup);
		rbAllFiles.setToggleGroup(fileTypeGroup);
		rbClassOnly.setSelected(true);
		
		HBox fileTypeBox = new HBox(15, upperFolderChk, rbClassOnly, rbAllFiles);
		fileTypeBox.setAlignment(Pos.CENTER_LEFT);
		
		// -------------------------------
		// 9) 모두추가 / 추가 / 지우기 / 모두지우기 버튼
		// -------------------------------
		Button addAllBtn = new Button("모두추가");
		Button addBtn = new Button("추가");
		Button delBtn = new Button("지우기");
		Button delAllBtn = new Button("모두지우기");
		
		HBox fileBtnBox = new HBox(10, addAllBtn, addBtn, delBtn, delAllBtn);
		fileBtnBox.setAlignment(Pos.CENTER_LEFT);
		
		// -------------------------------
		// 10) ListView (ListBox)
		// -------------------------------
		fileListView = new ListView<>();
		fileListView.setPrefHeight(350);
		
		// -------------------------------
		// 11) Jar 생성 / 닫기 버튼
		// -------------------------------
		Button createJarBtn = new Button("Jar 생성");
		Button closeBtn = new Button("닫기");
		
		HBox bottomBtnBox = new HBox(15, createJarBtn, closeBtn);
		bottomBtnBox.setAlignment(Pos.CENTER_RIGHT);
		
		
		resetBtn.setOnAction( je -> {
			pathField.setText("");
			nameField.setText("test.jar");
			verField.setText("1.0");
			mainField.setText("");
			cpField.setText("");
			createField.setText("");
			rbClassOnly.setSelected(true);
			upperFolderChk.setSelected(false);
			fileListView.getItems().clear();
			tmplist.clear();
			
		});
		pathBtn.setOnAction(je -> {
			try {
				String currentDir = null;
				
				// 1) javaEditerPath.txt 읽기
				try (BufferedReader reader = new BufferedReader(new FileReader("javaEditerPath.txt"))) {
					currentDir = reader.readLine();
					System.out.println("불러온 currentDir: " + currentDir);
				} catch (IOException ioe) {
					System.out.println("파일 읽기 오류: " + ioe.getMessage());
				}
				
				// 2) 기본값 처리
				if (currentDir == null || currentDir.trim().isEmpty()) {
					currentDir = ".";
				}
				
				File cfile = new File(currentDir);
				if (!cfile.exists() || !cfile.isDirectory()) {
					cfile = new File(".");
				}
				
				// 3) DirectoryChooser 생성
				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setTitle("폴더 선택");
				chooser.setInitialDirectory(cfile);
				
				// 4) 폴더 선택창 띄우기
				File selectedDir = chooser.showDialog(jarStage);
				
				if (selectedDir != null) {
					/*
					System.out.println("선택한 폴더: " + selectedDir);
					
					// 5) javaEditerPath.txt에 저장
					try (FileWriter writer = new FileWriter("javaEditerPath.txt")) {
					writer.write(selectedDir.getAbsolutePath());
					System.out.println("javaEditerPath.txt 저장 완료!");
					} catch (IOException ioe) {
					System.out.println("파일 저장 오류: " + ioe.getMessage());
					}
					*/
					// 6) TextField에 표시
					pathField.setText(selectedDir.getAbsolutePath());
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		cpBtn.setOnAction(je -> {
			
			Stage cpStage = new Stage();
			cpStage.setTitle("cp 선택");
			cpStage.initModality(Modality.APPLICATION_MODAL);
			
			VBox root = new VBox(12);
			root.setPadding(new Insets(15));
			
			// -------------------------
			// 1. 상단
			// -------------------------
			Label topLabel = new Label("Class_Path : ");
			topLabel.setStyle("-fx-font-size: 16px;");
			HBox topBox = new HBox(topLabel);
			topBox.setAlignment(Pos.CENTER_LEFT);
			
			// -------------------------
			// 2. 중앙
			// -------------------------
			ListView<String> cpListView = new ListView<>();
			cpListView.setPrefHeight(250);
			
			// 버튼 5개
			Button cpfolderAddBtn = new Button("폴더추가");
			Button cpaddAllBtn = new Button("모두추가");
			Button cpaddBtn = new Button("추가");
			Button cpdelBtn = new Button("삭제");
			Button cpclearBtn = new Button("모두삭제");
			
			VBox btnBox = new VBox(10, cpfolderAddBtn, cpaddAllBtn, cpaddBtn, cpdelBtn, cpclearBtn);
			btnBox.setAlignment(Pos.TOP_CENTER);
			
			HBox centerBox = new HBox(10, cpListView, btnBox);
			HBox.setHgrow(cpListView, Priority.ALWAYS);
			
			// -------------------------
			// 3. 하단
			// -------------------------
			Button cpokBtn = new Button("확인");
			Button cpcancelBtn = new Button("취소");
			
			HBox bottomBox = new HBox(10, cpokBtn, cpcancelBtn);
			bottomBox.setAlignment(Pos.CENTER_RIGHT);
			
			// -------------------------
			// root 구성
			// -------------------------
			root.getChildren().addAll(topBox, centerBox, bottomBox);
			
			Scene scene = new Scene(root, 600, 400);
			cpStage.setScene(scene);
			cpStage.show();
			
			
			// ---------------------------------------------------------
			//  폴더추가 (여러 개 선택 가능)
			// ---------------------------------------------------------
			cpfolderAddBtn.setOnAction(ev -> {
				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setTitle("폴더 선택");
				
				File baseDir = new File(pathField.getText());
				if (baseDir.exists()) chooser.setInitialDirectory(baseDir);
				
				File selected = chooser.showDialog(cpStage);
				if (selected == null) return;
				
				String base = pathField.getText();
				String full = selected.getAbsolutePath();
				
				String relative = full.substring(base.length());
				if (relative.startsWith(File.separator)) relative = relative.substring(1);
				
				cpListView.getItems().add(relative + File.separator);
			});
			
			
			// ---------------------------------------------------------
			//  모두추가 (폴더 1개 선택 → 그 안의 모든 jar 파일 추가)
			// ---------------------------------------------------------
			cpaddAllBtn.setOnAction(ev -> {
				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setTitle("모두추가 - 폴더 선택");
				
				File baseDir = new File(pathField.getText());
				if (baseDir.exists()) chooser.setInitialDirectory(baseDir);
				
				File selected = chooser.showDialog(cpStage);
				if (selected == null) return;
				
				List<File> jarFiles = new ArrayList<>();
				getJarFiles(selected, jarFiles);
				
				String base = pathField.getText();
				
				for (File jar : jarFiles) {
					String full = jar.getAbsolutePath();
					String relative = full.substring(base.length());
					if (relative.startsWith(File.separator)) relative = relative.substring(1);
					cpListView.getItems().add(relative);
				}
			});
			
			
			// ---------------------------------------------------------
			//  추가 (파일 여러 개 선택 가능)
			// ---------------------------------------------------------
			cpaddBtn.setOnAction(ev -> {
				FileChooser chooser = new FileChooser();
				chooser.setTitle("파일 선택");
				
				chooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("JAR Files", "*.jar")
					);
				
				File baseDir = new File(pathField.getText());
				if (baseDir.exists()) chooser.setInitialDirectory(baseDir);
				
				List<File> selectedFiles = chooser.showOpenMultipleDialog(cpStage);
				if (selectedFiles == null) return;
				
				String base = pathField.getText();
				
				for (File f : selectedFiles) {
					String full = f.getAbsolutePath();
					String relative = full.substring(base.length());
					if (relative.startsWith(File.separator)) relative = relative.substring(1);
					cpListView.getItems().add(relative);
				}
			});
			
			
			// ---------------------------------------------------------
			//  삭제
			// ---------------------------------------------------------
			cpdelBtn.setOnAction(ev -> {
				String sel = cpListView.getSelectionModel().getSelectedItem();
				if (sel != null) cpListView.getItems().remove(sel);
			});
			
			// ---------------------------------------------------------
			//  모두삭제
			// ---------------------------------------------------------
			cpclearBtn.setOnAction(ev -> cpListView.getItems().clear());
			
			
			// ---------------------------------------------------------
			//  확인 → classpathtxt에 넣기
			// ---------------------------------------------------------
			cpokBtn.setOnAction(ev -> {
				StringBuilder sb = new StringBuilder();
				
				for (String s : cpListView.getItems()) {
					sb.append(s.replace(File.separator, "/")).append(" ");
				}
				
				cpField.setText(sb.toString().trim());
				cpStage.close();
			});
			
			// ---------------------------------------------------------
			//  취소
			// ---------------------------------------------------------
			cpcancelBtn.setOnAction(ev -> cpStage.close());
		});
		
		addAllBtn.setOnAction(je -> {
			try {
				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setTitle("모두추가 - 폴더 선택");
				
				File baseDir = new File(pathField.getText());
				if (baseDir.exists()) chooser.setInitialDirectory(baseDir);
				
				File selectedFolder = chooser.showDialog(jarStage);
				if (selectedFolder == null) return;
				
				// base path
				String base = pathField.getText();
				
				// 하위 모든 파일 가져오기
				List<File> allFiles = new ArrayList<>();
				getAllFiles(selectedFolder, allFiles);
				
				for (File file : allFiles) {
					
					// class파일만 선택하는 경우
					if (!rbAllFiles.isSelected()) {
						if (!file.getName().endsWith(".class")) continue;
					}
					
					String full = file.getAbsolutePath();
					String relative = full.substring(base.length());
					if (relative.startsWith(File.separator)) relative = relative.substring(1);
					
					fileListView.getItems().add(relative);
					tmplist.add(file);
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		addBtn.setOnAction(je -> {
			try {
				FileChooser chooser = new FileChooser();
				chooser.setTitle("파일 선택");
				
				File baseDir = new File(pathField.getText());
				if (baseDir.exists()) chooser.setInitialDirectory(baseDir);
				
				// class파일만 선택
				if (!rbAllFiles.isSelected()) {
					chooser.getExtensionFilters().add(
						new FileChooser.ExtensionFilter("Class Files", "*.class")
						);
				} else {
					chooser.getExtensionFilters().add(
						new FileChooser.ExtensionFilter("모든 파일", "*.*")
						);
				}
				
				// 여러 개 선택 가능
				List<File> selectedFiles = chooser.showOpenMultipleDialog(jarStage);
				if (selectedFiles == null) return;
				
				String base = pathField.getText();
				
				for (File file : selectedFiles) {
					
					// class파일만 선택하는 경우
					if (!rbAllFiles.isSelected()) {
						if (!file.getName().endsWith(".class")) continue;
					}
					
					String full = file.getAbsolutePath();
					String relative = full.substring(base.length());
					if (relative.startsWith(File.separator)) relative = relative.substring(1);
					
					fileListView.getItems().add(relative);
					tmplist.add(file);
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		delBtn.setOnAction(je -> {
			int index = fileListView.getSelectionModel().getSelectedIndex();
			
			if (index >= 0) {
				fileListView.getItems().remove(index);
				tmplist.remove(index);
				
				// 삭제 후 선택 유지
				if (index < fileListView.getItems().size()) {
					fileListView.getSelectionModel().select(index);
				} else {
					fileListView.getSelectionModel().selectLast();
				}
			}
		});
		
		delAllBtn.setOnAction(je -> {
			fileListView.getItems().clear();
			tmplist.clear();
		});
		upperFolderChk.selectedProperty().addListener((obs, oldVal, newVal) -> {
			foldersel = newVal;
		});
		
		createJarBtn.setOnAction(je -> {
			
			try {
				
				String jarDir = pathField.getText().trim();
				File dir = new File(jarDir);
				
				if (!dir.exists()) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("경고");
					alert.setHeaderText(null);
					alert.setContentText("JAR 생성 경로가 존재하지 않습니다.");
					alert.showAndWait();
					return;
				}
				
				String name = nameField.getText().trim();
				if (!name.toLowerCase().endsWith(".jar")) {
					name += ".jar";
				}
				
				String jarPath = jarDir + File.separator + name;
				String basePath = pathField.getText().trim();
				
				System.out.println("생성될 JAR: " + jarPath);
				
				jarOutMethod(jarPath, basePath);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		closeBtn.setOnAction( je -> {
			Showing(false);
		});
		// -------------------------------
		// 전체 구성
		// -------------------------------
		root.getChildren().addAll(
			resetBtn,
			pathBox,
			nameBox,
			verBox,
			mainBox,
			cpBox,
			createBox,
			fileTypeBox,
			fileBtnBox,
			fileListView,
			bottomBtnBox
			);
		
		Scene scene = new Scene(root, 450, 650);
		jarStage.setScene(scene);
		//jarStage.show();
	}
	public void Showing(boolean bshow)
	{
		if(bshow)
		{
			jarStage.show();
		}else {
			jarStage.hide();
		}
	}
	private static void getJarFiles(File dir, List<File> jarList) {
		File[] files = dir.listFiles();
		if (files == null) return;
		
		for (File f : files) {
			if (f.isDirectory()) {
				getJarFiles(f, jarList);
			} else if (f.getName().toLowerCase().endsWith(".jar")) {
				jarList.add(f);
			}
		}
	}
	private static void getAllFiles(File folder, List<File> fileList) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					getAllFiles(file, fileList);
				} else {
					fileList.add(file);
				}
			}
		}
	}
	private Manifest createManifest() {
		Manifest manifest = new Manifest();
		Attributes attr = manifest.getMainAttributes();
		
		attr.put(Attributes.Name.MANIFEST_VERSION, verField.getText());
		attr.put(Attributes.Name.MAIN_CLASS, mainField.getText());
		attr.put(new Attributes.Name("Class-Path"), cpField.getText());
		attr.put(new Attributes.Name("Created-By"), createField.getText());
		
		return manifest;
	}
	private static void addToJar(JarOutputStream jarOut, File source, String basePath) throws IOException {
		
		if (source.isDirectory()) {
			
			for (File file : Objects.requireNonNull(source.listFiles())) {
				
				String entryName = source.getAbsolutePath()
				.substring(basePath.length() + 1)
				.replace(File.separator, "/");
				
				JarEntry entry = new JarEntry(entryName + "/");
				jarOut.putNextEntry(entry);
				jarOut.closeEntry();
				
				addToJar(jarOut, file, basePath);
			}
			
		} else {
			
			String entryName = source.getAbsolutePath()
			.substring(basePath.length() + 1)
			.replace(File.separator, "/");
			
			JarEntry entry = new JarEntry(entryName);
			jarOut.putNextEntry(entry);
			
			try (FileInputStream fis = new FileInputStream(source)) {
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) != -1) {
					jarOut.write(buffer, 0, len);
				}
			}
			
			jarOut.closeEntry();
		}
	}
	private static void folderaddToJar(JarOutputStream jarOut, File source, String basePath) throws IOException {
		
		File parent = new File(basePath).getParentFile();
		
		if (source.isDirectory()) {
			
			for (File file : Objects.requireNonNull(source.listFiles())) {
				
				String entryName = source.getAbsolutePath()
				.substring(parent.getAbsolutePath().length() + 1)
				.replace(File.separator, "/");
				
				JarEntry entry = new JarEntry(entryName + "/");
				jarOut.putNextEntry(entry);
				jarOut.closeEntry();
				
				addToJar(jarOut, file, basePath);
			}
			
		} else {
			
			String entryName = source.getAbsolutePath()
			.substring(parent.getAbsolutePath().length() + 1)
			.replace(File.separator, "/");
			
			JarEntry entry = new JarEntry(entryName);
			jarOut.putNextEntry(entry);
			
			try (FileInputStream fis = new FileInputStream(source)) {
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) != -1) {
					jarOut.write(buffer, 0, len);
				}
			}
			
			jarOut.closeEntry();
		}
	}
	private void jarOutMethod(String jarPath, String basePath) throws IOException {
		
		try (JarOutputStream jarOut =
			new JarOutputStream(new FileOutputStream(jarPath), createManifest())) {
			
			for (File file : tmplist) {
				if (foldersel) {
					folderaddToJar(jarOut, file, basePath);
				} else {
					addToJar(jarOut, file, basePath);
				}
			}
			
			System.out.println("JAR 파일 생성 완료: " + jarPath);
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("알림");
			alert.setHeaderText(null);
			alert.setContentText("JAR 파일이 생성되었습니다.");
			alert.showAndWait();
		}
	}
	public void setInitialData(String[] tmpstr, List<String> tmplist)
	{
		fileListView.getItems().clear();
		fileListView.getItems().addAll(tmplist);
		pathField.setText(tmpstr[0]);
		nameField.setText(tmpstr[1]);
		verField.setText(tmpstr[2]);
		mainField.setText(tmpstr[3]);
		cpField.setText(tmpstr[4]);
		createField.setText(tmpstr[5]);
		if(tmpstr[6].equals("true"))
		{
			upperFolderChk.setSelected(true);
		}else{
			upperFolderChk.setSelected(false);
		}
		if(tmpstr[7].equals("모든파일"))
		{
			rbAllFiles.setSelected(true);
		}else{
			rbClassOnly.setSelected(true);
		}
	}
	public String[] getInitialStr()
	{
		String[] initial = new String[10];
		initial[0] = pathField.getText();
		initial[1] = nameField.getText();
		initial[2] = verField.getText();
		initial[3] = mainField.getText();
		initial[4] = cpField.getText();
		initial[5] = createField.getText();
		initial[6] = upperFolderChk.isSelected() ? "true" : "false";
		initial[7] = rbClassOnly.isSelected() ? "class파일" : "모든파일";

		return initial;
		
	}
	public List<String> getInitialList()
	{
		List<String> initiallist = new ArrayList<>(fileListView.getItems());
		
		
		return initiallist;
	}
	
}
