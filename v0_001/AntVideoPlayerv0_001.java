import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import java.io.File;

public class AntVideoPlayerv0_001 extends Application {

    private MediaPlayer mediaPlayer;
    private Label timeLabel = new Label("0 / 0");
    private Label pathLabel = new Label("파일 경로: 없음");
	private Slider positionSlider = new Slider();
	private Slider volumeSlider = new Slider();
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        VBox controlsBox = new VBox(10);
        HBox buttonBox = new HBox(10);

        // MediaView
        MediaView mediaView = new MediaView();
		mediaView.setFitWidth(1000);
		mediaView.setFitHeight(800);
		//mediaView.setPreserveRatio(true); // 비율 유지
        root.setCenter(mediaView);

        // 파일 열기 버튼
        Button openButton = new Button("파일 열기");
        openButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("비디오 파일 선택");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.m4v", "*.mov"));
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                Media media = new Media(file.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
				
                mediaView.setMediaPlayer(mediaPlayer);
                pathLabel.setText("파일 경로: " + file.getAbsolutePath());

                mediaPlayer.setOnReady(() -> {
                    Duration total = mediaPlayer.getTotalDuration();
                    timeLabel.setText("0 / " + formatTime(total));
                    positionSlider.setMax(total.toSeconds());
                });

                mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                    positionSlider.setValue(newTime.toSeconds());
                    timeLabel.setText(formatTime(newTime) + " / " + formatTime(mediaPlayer.getTotalDuration()));
                });

                volumeSlider.setValue(mediaPlayer.getVolume() * 100);
                mediaPlayer.play();
            }
        });

        // 시작, 정지, 일시정지 버튼
        Button playButton = new Button("시작");
        Button pauseButton = new Button("일시정지");
        Button stopButton = new Button("정지");

        playButton.setOnAction(e -> {
            if (mediaPlayer != null) mediaPlayer.play();
        });

        pauseButton.setOnAction(e -> {
            if (mediaPlayer != null) mediaPlayer.pause();
        });

        stopButton.setOnAction(e -> {
            if (mediaPlayer != null) mediaPlayer.stop();
        });

        // 소리 슬라이더
        //Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (mediaPlayer != null) mediaPlayer.setVolume(newVal.doubleValue() / 100);
        });

        // 재생 위치 슬라이더
        //Slider positionSlider = new Slider();
        positionSlider.setMin(0);
        positionSlider.setValue(0);
        positionSlider.setShowTickLabels(true);
        positionSlider.setShowTickMarks(true);
        positionSlider.setOnMousePressed(e -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(positionSlider.getValue()));
            }
        });

        // 구성
        buttonBox.getChildren().addAll(openButton, playButton, pauseButton, stopButton, new Label("소리:"), volumeSlider);
        controlsBox.getChildren().addAll(pathLabel, timeLabel, positionSlider, buttonBox);
        root.setBottom(controlsBox);

        Scene scene = new Scene(root, 1000, 800);
		// 너비: 창 너비에 바인딩
		mediaView.fitWidthProperty().bind(scene.widthProperty());

		// 높이: 창 높이의 80%에 바인딩
		mediaView.fitHeightProperty().bind(scene.heightProperty().multiply(0.8));

        primaryStage.setTitle("AntVideoPlayerv0_001");
        primaryStage.setScene(scene);
        primaryStage.show();
		primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
			mediaView.fitWidthProperty().bind(scene.widthProperty());
		});

		primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
			mediaView.fitHeightProperty().bind(scene.heightProperty().multiply(0.8));
		});

    }

    private String formatTime(Duration duration) {
        int seconds = (int) duration.toSeconds();
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
