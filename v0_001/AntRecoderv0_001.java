import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.*;





class AntRecoderv0_001 
{
	static AntRecoderv0_001 arcr;
	JFrame frame;
	JProgressBar progressBar;
    JTextField fileStatus;
    JButton playButton, stopButton, pauseButton, recordButton, saveButton, loadButton;
	JLabel seclabel,infolabel;
	static final long RECORD_TIME = 10000; // 10초
	File wavFile = new File("recorded_audio.wav");
	AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	AudioFormat format = new AudioFormat(16000, 16, 2, true, true);
	AudioInputStream audioIn;
	byte[] audioData;
	TargetDataLine line;
	Clip clip;
	static boolean stopb = false;
	static boolean recordb = false;
	static boolean playb = false;
	static boolean pauseb = false;
	Thread playThread,recordThread,stopper;
	Timer slidertime,sectime;
	
	void play() {
        if(playb&&pauseb)
		{
			clip.start();
			pauseb = false;
		}else{
			try {
				File soundFile = wavFile; // 파일 경로
				audioIn = AudioSystem.getAudioInputStream(soundFile);
				//arcr.audioformatcheck(audioIn);
				clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			
				// 재생이 끝날 때까지 대기
				Thread.sleep(clip.getMicrosecondLength() / 1000);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}


    }
	volatile boolean isPaused = false;

	void captureAudio() {
		byte[] buffer = new byte[4096];
		
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                return;
            }

            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
			line.start();
			while (!stopb) {
				if (isPaused) {
					Thread.sleep(100); // 일시정지 상태에서는 대기
					continue;
				}
				int count = line.read(buffer, 0, buffer.length);
				if (count > 0) {
					out.write(buffer, 0, count);
					//double volume = calculateStereoVolume(buffer);
                    //System.out.printf("현재 음량: %.2f dB\n", volume);

				}
			}
			line.stop();
			line.close();

			// 저장
			audioData = out.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
			AudioInputStream ais = new AudioInputStream(bais, format, audioData.length / format.getFrameSize());
			AudioSystem.write(ais, AudioFileFormat.Type.WAVE,new File("recorded_audio.wav"));
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    void stop() {
		clip.setMicrosecondPosition(clip.getMicrosecondLength());
        line.stop();
        line.close();
        System.out.println("녹음 종료. 파일 저장됨: " + wavFile.getAbsolutePath());
    }





	
	public void init()
	{
		frame = new JFrame("앤트리코더");
		frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

        // 상단 TextField (비활성화)
        fileStatus = new JTextField(wavFile.getAbsolutePath());
        fileStatus.setEnabled(false);
        frame.add(fileStatus, BorderLayout.NORTH);
		/*
        // Progress Bar
        progressBar = new JProgressBar(0,100);
		progressBar.setValue(0); // 초기값 설정
		progressBar.setStringPainted(true); // 진행률 숫자 표시
        frame.add(progressBar, BorderLayout.CENTER);
		Timer timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					long current = clip.getMicrosecondPosition();
					long total = clip.getMicrosecondLength();
					int percent = (int)((current * 100) / total);
					progressBar.setValue(percent);
				}
			}
		});
		timer.start();
		*/
		JSlider slider = new JSlider(0, 100, 0);
		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setEnabled(true);
		slider.setFocusable(false);
		frame.add(slider, BorderLayout.CENTER);
		
		// 슬라이더로 재생 위치 이동
		slider.addChangeListener(e -> {
			if (clip != null) {
				long total = clip.getMicrosecondLength();
				int percent = slider.getValue();
				long newPosition = (total * percent) / 100;
				//clip.stop();
				clip.setMicrosecondPosition(newPosition);
				//clip.start();
			}
		});

		// 재생 중일 때 슬라이더 업데이트
		slidertime = new Timer(200, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					long current = clip.getMicrosecondPosition();
					long total = clip.getMicrosecondLength();
					int percent = (int)((current * 100) / total);
					slider.setValue(percent);
				}
			}
		});
		
		
		//박스레이아웃패널
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel,BoxLayout.Y_AXIS));
        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3));

        playButton = new JButton("재생");
        stopButton = new JButton("정지");
        pauseButton = new JButton("일시정지");
        recordButton = new JButton("녹음");
        saveButton = new JButton("저장");
        loadButton = new JButton("불러오기");
		playButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				stopb = false;
				
				playb = true;
				isPaused = false;
				if(!recordb)
				{
					
					playThread = new Thread(() -> {
						arcr.play();
					});
					playThread.start();
					
					
					slidertime.start();
					sectime.start();
		
						
				}
				recordb = false;
				
			}
		});
		stopButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				stopb = true;
				
				
				if(playb&&!recordb)
				{
					
					playb = false;
					
					slider.setValue(0);
					pauseb = false;
					slidertime.stop();
					sectime.stop();
					Thread stopThread = new Thread(()->{
						if(clip != null && clip.isRunning())
						{
							clip.stop();
							clip.close();
							
						}
						if(line!=null)
						{
							arcr.stop();	
						}

					});						

					try {
						 System.out.println("Waiting for playThread to finish...");

						//playThread.stop();
						System.out.println("playThread finished. Starting stopThread...");

						stopThread.start();
						stopThread.join();
					} catch (InterruptedException ite) {
						ite.printStackTrace();
					}
					
				}
				if(recordb)
				{
					try {
						
						
						recordThread.join();
					} catch (InterruptedException ite) {
						ite.printStackTrace();
					}
					arcr.stop();
					
						
					wavFile = new File("recorded_audio.wav");
					fileStatus.setText(wavFile.getAbsolutePath());
					slider.setValue(0);
					recordb = false;
					pauseb = false;
				}
				//isPaused = false;
				//
			}
		});
		pauseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				isPaused = true;
				
				if(playb)
				{
					clip.stop();	
					pauseb = true;
				}
			}
		});
		recordButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				stopb = false;
				recordb = true;
				playb = false;
				recordThread = new Thread(()->{
					arcr.captureAudio();
				});
				
				recordThread.start();
				

				
			}
		});
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				// JFileChooser 생성
				JFileChooser fileChooser = new JFileChooser();

				// wav 파일만 저장하도록 필터 설정
				FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV Audio Files", "wav");
				fileChooser.setFileFilter(filter);

				// 저장 다이얼로그 열기
				int result = fileChooser.showSaveDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					String path = selectedFile.getAbsolutePath();

					// 확장자가 없으면 .wav 붙이기
					if (!path.toLowerCase().endsWith(".wav")) {
						selectedFile = new File(path + ".wav");
						
					}
					try{
						wavFile = selectedFile;
						if(audioData != null)
						{
						
							ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
							AudioInputStream ais = new AudioInputStream(bais, format, audioData.length / format.getFrameSize());
							AudioSystem.write(ais, AudioFileFormat.Type.WAVE, wavFile);
						}else 
						{
							
							Path source = Paths.get("recorded_audio.wav");
							Path target = Paths.get(wavFile.getAbsolutePath());

							Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
							System.out.println("파일 복사 완료! "+wavFile.getAbsolutePath());
							

						}
						stopb = false;
						playb = false;
						recordb = false;
						pauseb = false;
					}catch(IOException ioe)
					{
						ioe.printStackTrace();	
					}
					fileStatus.setText(wavFile.getAbsolutePath());
					System.out.println("저장할 파일: " + wavFile.getAbsolutePath());

					// 여기서 파일 저장 로직을 추가하면 됨
				} else {
					System.out.println("저장 취소됨.");
				}


			}
		});
		loadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				// JFileChooser 생성
				JFileChooser fileChooser = new JFileChooser();

				// wav 파일만 저장하도록 필터 설정
				FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV Audio Files", "wav");
				fileChooser.setFileFilter(filter);

				// 저장 다이얼로그 열기
				int result = fileChooser.showOpenDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					String path = selectedFile.getAbsolutePath();
					
					wavFile = selectedFile;
					 try {
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(wavFile);
						AudioFormat format = audioInputStream.getFormat();
						System.out.println("오디오 포맷: " + format);

						// readAllBytes() 대신 직접 읽기
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						byte[] buffer = new byte[4096];
						int bytesRead;
						while ((bytesRead = audioInputStream.read(buffer)) != -1) {
							baos.write(buffer, 0, bytesRead);
						}

						audioData = baos.toByteArray();
						System.out.println("오디오 데이터 크기: " + audioData.length + " bytes");



						playb = false;
						stopb = false;
						recordb = false;
						pauseb = false;
						fileStatus.setText(wavFile.getAbsolutePath());
					} catch (UnsupportedAudioFileException ufe) {
						System.out.println("지원되지 않는 오디오 파일 형식입니다.");
					} catch (IOException ioe) {
						System.out.println("파일을 읽는 중 오류 발생: " + ioe.getMessage());
					}


					
				}
			}
		});
        buttonPanel.add(playButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(recordButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
		southPanel.add(buttonPanel);
		
		JPanel volumePanel = new JPanel(new FlowLayout());
		JLabel volumelabel = new JLabel("음량 : ");
		
		JSlider vslider = new JSlider(-80, 0, 0);
		vslider.setMajorTickSpacing(10);
		vslider.setPaintTicks(false);
		vslider.setPaintLabels(false);
		vslider.setEnabled(true);
		vslider.setFocusable(false);
		
		JCheckBox mutecheck = new JCheckBox("음소거");
		mutecheck.setSelected(false);
		vslider.addChangeListener(e -> {
			//볼륨.....
			FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			// 볼륨 설정 (데시벨 단위)
			int slidervalue = vslider.getValue();
            float volumeInDb = (float)slidervalue; // 0.0f = 최대, -80.0f = 최소 (음소거 수준)
			volumeControl.setValue(volumeInDb);


		});
		mutecheck.addActionListener(e -> {
			boolean ismute = mutecheck.isSelected();
			FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			if(ismute)
			{
				volumeControl.setValue(volumeControl.getMinimum()); // 음소거

			}else
			{
				volumeControl.setValue((float)vslider.getValue()); // 슬라이더 값으로 복원
			}
		});
		volumePanel.add(volumelabel);
		volumePanel.add(vslider);
		volumePanel.add(mutecheck);
		southPanel.add(volumePanel);
		JPanel infoPanel = new JPanel(new FlowLayout());
		
		seclabel = new JLabel("0 / 0");
		
		JButton exitbtn = new JButton("끝내기");
		exitbtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				System.exit(0);
			}
		});
		sectime = new Timer(1000,new ActionListener(){
			public void actionPerformed(ActionEvent ape)
			{
				
				if(clip != null)
				{
					long microseconds = (long)clip.getMicrosecondLength();
					double totalsec = (double)microseconds / 1_000_000.0;
					microseconds = clip.getMicrosecondPosition();
					double currentsec = (double)microseconds / 1_000_000.0;
					
					String strtmp = String.format("%d / %d",(int)currentsec,(int)totalsec);
					seclabel.setText(strtmp);

					
				}else
				{
					seclabel.setText("0 / 0");

				}

			}
		});
		
		if(stopb)
		{
			sectime.stop();
		}
		
		infoPanel.add(seclabel);
		infoPanel.add(exitbtn);
		southPanel.add(infoPanel);
		JPanel verPanel = new JPanel(new FlowLayout());
		JLabel infolabel = new JLabel("만든이 : 컴퓨터판타지연구소   ");
		JLabel verlabel = new JLabel("   버젼 : v0.001");
		verPanel.add(infolabel);
		verPanel.add(verlabel);
		southPanel.add(verPanel);
        frame.add(southPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
	}
	void audioformatcheck(AudioInputStream audioInputStream)
	{
		AudioFormat format = audioInputStream.getFormat();

		System.out.println("샘플 크기 (bits): " + format.getSampleSizeInBits());
		System.out.println("채널 수: " + format.getChannels());
		System.out.println("엔디안: " + (format.isBigEndian() ? "Big Endian" : "Little Endian"));
		System.out.println("인코딩: " + format.getEncoding());
		System.out.println("샘플링 레이트: " + format.getSampleRate());
	
	}
	public static double calculateStereoVolume(byte[] audioBytes) {
		long sum = 0;
		int sampleCount = audioBytes.length / 4; // 2바이트 * 2채널 = 4바이트 per frame

		for (int i = 0; i < audioBytes.length; i += 4) {
			// 왼쪽 채널 샘플 (리틀 엔디안)
			int left = (audioBytes[i + 1] << 8) | (audioBytes[i] & 0xFF);
			// 오른쪽 채널 샘플
			int right = (audioBytes[i + 3] << 8) | (audioBytes[i + 2] & 0xFF);

			// 두 채널의 평균 에너지
			sum += left * left;
			sum += right * right;
		}

		double rms = Math.sqrt(sum / (double)(sampleCount * 2)); // 2채널이니까 *2
		return 20 * Math.log10(rms + 1e-10); // 로그 0 방지용 작은 값 추가
	}


	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run()
			{
				arcr = new AntRecoderv0_001();
				arcr.init();
				
			}
		});
	
		
		System.out.println("Hello World!");
	}
}
