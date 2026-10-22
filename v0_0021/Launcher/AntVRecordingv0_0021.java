package Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import qpSystem.*;
import ComMyUtil.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.core.Size;


import org.opencv.core.CvType;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.sound.sampled.*;



class AntVRecordingv0_0021 
{
	public static AntVRecordingv0_0021 avr;
	public static JLabel dirLabel,secLabel,fnameLabel;
	public static String dirstr="",fnamestr="";
	public static BufferedImage screenimage;
	public static int recsec = 0,tmpsec = 0;;
	public static JPanel videoPanel;
	public static JCheckBox modeCheckBox,muteCheckBox;
	public static String mydir,myopencvdll;
	public static qpFileCreate qpfc;
	public static VideoCapture camera;
	public static UtilityClass util;
	public static boolean recording = false,secrelay = false;
	public static VideoWriter writer;
	public static void Init()
	{
		
		mydir = System.getProperty("user.dir");


		myopencvdll = mydir + "\\opencv4110\\x64\\opencv_java4110.dll";
		System.load(myopencvdll);
		
		// 프레임 생성
        JFrame frame = new JFrame("앤트비디오녹음기v0.001");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 600);
        frame.setLayout(new BorderLayout());

        // workPanel
        JPanel workPanel = new JPanel();
        workPanel.setLayout(new BorderLayout());
		workPanel.setPreferredSize(new Dimension(900, 700));
		workPanel.setBackground(Color.WHITE); // 예시용 배경
        // videoPanel
        videoPanel = new JPanel();
		videoPanel.setSize(854,480);
        videoPanel.setPreferredSize(new Dimension(854, 480));
        videoPanel.setBackground(Color.BLACK); // 예시용 배경
		
		dirLabel = new JLabel("디렉토리 : ");
		fnameLabel = new JLabel("파일이름 : ");
		secLabel = new JLabel("recordingTime : 0");
		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BoxLayout(statusPanel,BoxLayout.Y_AXIS));
		statusPanel.add(fnameLabel);
		statusPanel.add(secLabel);
		modeCheckBox = new JCheckBox("캠/화면");
		muteCheckBox = new JCheckBox("음소거");
		Timer screenTimer = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
						
						if(!modeCheckBox.isSelected())
						{
							Robot robot = new Robot();
							Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
							BufferedImage fullImage = robot.createScreenCapture(new Rectangle(screenSize)); // 전체 화면 캡처

							// 이미지 리사이즈: 854x480으로 줄이기
							Image scaledImage = fullImage.getScaledInstance(854, 480, Image.SCALE_SMOOTH);
							BufferedImage resizedImage = new BufferedImage(854, 480, BufferedImage.TYPE_INT_ARGB);

							// 그래픽 객체로 그리기
							Graphics2D g2d = resizedImage.createGraphics();
							g2d.drawImage(scaledImage, 0, 0, null);
							g2d.dispose();

							avr.screenimage = resizedImage;						
							videoPanel.repaint();
							videoPanel.revalidate();
							if(camera != null){
								camera.release();
								camera = null;
								util = null;
							}
						}else if(modeCheckBox.isSelected())
						{
							if(camera==null&&util==null){
							camera = new VideoCapture(0); // 디폴트 캠
							util = new UtilityClass();
							}
							//System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // OpenCV 라이브러리 로딩
							
							/*if (!camera.isOpened()) {
								System.out.println("카메라를 열 수 없습니다.");
								return;
							}*/
							
							Mat mframe = new Mat();
							//boolean success = camera.read(mframe);
							//System.out.println("프레임 읽기 성공 여부: " + success);
							if (camera.read(mframe)&&camera.isOpened()) {
								// Mat → BufferedImage 변환
								
								avr.screenimage = util.matToBufferedImage(mframe);
								
								//System.out.println("캠 이미지 캡처 완료!");
								// 필요시 이미지 화면에 출력하거나 저장하면 돼요
								
							}

							
							videoPanel.repaint();
							videoPanel.revalidate();


						}
						
						if(recording)
						{	
							Mat frameMat = util.bufferedImageToMat(screenimage); // 유틸 함수 필요
							writer.write(frameMat); // 프레임 저장!
							tmpsec++;
							if(tmpsec>=15)
							{
								secrelay = true;
								tmpsec = 0;
								
							}
							recsec++;
							if(secrelay)
							{
								
								String temp = String.format("recordingTime : %d",(int)((float)recsec/15));
								secLabel.setText(temp);
								workPanel.repaint();
								workPanel.revalidate();
								secrelay = false;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
		});
		screenTimer.start();
		videoPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (avr.screenimage != null) {
					g.drawImage(avr.screenimage, 0, 0, 800, 400, null);
					
				}
				
			}
		};
		videoPanel.repaint();
		workPanel.add(dirLabel,BorderLayout.NORTH);
		workPanel.add(videoPanel,BorderLayout.CENTER);
		workPanel.add(statusPanel,BorderLayout.SOUTH);
		
        // rightPanel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
        // 체크박스
		JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        checkPanel.add(modeCheckBox);
		checkPanel.add(muteCheckBox);
		rightPanel.add(checkPanel);
        // 버튼들
        String[] buttons = {"녹화", "image저장", "qp저장", "디렉토리","파일이름", "종료"};
		JButton recordButton = null; // 녹화 버튼 참조 변수

        for (String name : buttons) {
            JButton btn = new JButton(name);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
			if (name.equals("녹화")) {
				recordButton = btn; // 녹화 버튼 참조 저장
				
			}

            btn.addActionListener(e -> {
                String btnText = btn.getText();
                if(btnText.equals("녹화"))
				{
					if(!dirstr.equals("") && !fnamestr.equals(""))
					{
						btn.setText("중지");
						//System.out.println(btnText + " 버튼 눌림");
						if(util == null)
						{
							util = new UtilityClass();	
						}
						String savetemp = dirstr + "\\" + fnamestr+".avi";
						writer = new VideoWriter(
							savetemp,                      // 파일명
							VideoWriter.fourcc('M','J','P','G'), // 코덱
							30,                                // FPS
							new Size(screenimage.getWidth(), screenimage.getHeight()) // 영상 사이즈
						);

						recording = true;
						
						String audiotemp = dirstr+"\\"+fnamestr+".wav";
						voiceRecordstart(audiotemp);
						if(!modeCheckBox.isSelected())
						{
							String soundtemp = dirstr+"\\"+fnamestr+"(screen)"+".wav";
							soundRecordingStart(soundtemp);
						}
						recsec = 0;
					}else{
						JOptionPane.showMessageDialog(
							null,
							"파일이름 또는 디렉토리를 먼저 지정하세요.",
							"알림",
							JOptionPane.INFORMATION_MESSAGE
						);

								
					}
				}else if(btnText.equals("중지"))
				{
					btn.setText("녹화");
					//System.out.println(btnText + " 버튼 눌림");
					writer.release(); // 영상 녹화 종료 및 파일 닫기
					recording = false;
					voiceRecordstop();
					if(!modeCheckBox.isSelected())
					{
						soundRecordingStop();
					}
					String ffmpegexefile = "opencv4110\\ffmpeg-4.4-1.5.6-windows-x86_64\\org\\bytedeco\\ffmpeg\\windows-x86_64\\ffmpeg.exe";
					FFMpegmp4maker(dirstr,(fnamestr+".avi"),(fnamestr+".wav"),"output.mp4",ffmpegexefile);
					
					FFMpegmp4maker2(dirstr,"output.mp4",(fnamestr +".mp4"),ffmpegexefile);
					
					String delfname = dirstr+"\\"+fnamestr+".avi";
					File file = new File(delfname); // 삭제할 파일 경로

					if (file.delete()) {
						System.out.println("파일이 성공적으로 삭제되었습니다.");
					} else {
						System.out.println("파일 삭제에 실패했습니다.");
					}
					delfname = dirstr+"\\"+fnamestr+".wav";
					file = new File(delfname);
					if (file.delete()) {
						System.out.println("파일이 성공적으로 삭제되었습니다.");
					} else {
						System.out.println("파일 삭제에 실패했습니다.");
					}
					delfname = dirstr+"\\"+"output.mp4";
					file = new File(delfname);
					if (file.delete()) {
						System.out.println("파일이 성공적으로 삭제되었습니다.");
					} else {
						System.out.println("파일 삭제에 실패했습니다.");
					}

					
				}else if(btnText.equals("image저장"))
				{
					//System.out.println(btnText + " 버튼 눌림");
					try {
						String temp = dirstr + "\\" + fnamestr + ".png";
						File outputFile = new File(temp);
						ImageIO.write(screenimage, "png", outputFile);
						System.out.println("이미지가 저장되었습니다: " + outputFile.getAbsolutePath());
					} catch (IOException ioe) {
						ioe.printStackTrace();
						System.out.println("이미지 저장에 실패했습니다.");
					}
				}else if(btnText.equals("qp저장"))
				{
					//System.out.println(btnText + " 버튼 눌림");
					String temp = dirstr + "\\" + fnamestr + ".qp";
					File outputFile = new File(temp);
					qpfc = new qpFileCreate(outputFile,screenimage,frame);
				}else if(btnText.equals("디렉토리"))
				{
					//System.out.println(btnText + " 버튼 눌림");
					
					JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir"))); // 현재 디렉토리
					chooser.setDialogTitle("디렉토리를 선택하세요");
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 디렉토리만 선택 가능
					chooser.setAcceptAllFileFilterUsed(false);
					File selectedDir = null;
					int result = chooser.showOpenDialog(null);
					if (result == JFileChooser.APPROVE_OPTION) {
						selectedDir = chooser.getSelectedFile();
						System.out.println("선택한 디렉토리: " + selectedDir.getAbsolutePath());
						// 선택된 디렉토리를 이후 작업에 활용 가능
					} else {
						System.out.println("디렉토리 선택이 취소됨");
					}
					if(selectedDir!=null)
					{
						dirstr = selectedDir.getAbsolutePath();
						String tempstr = String.format("디렉토리 : %s",dirstr);
						dirLabel.setText(tempstr);
					}

				}else if(btnText.equals("파일이름"))
				{
					//System.out.println(btnText + " 버튼 눌림");
					
					JFrame fframe = new JFrame("파일 이름 설정");
					fframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					fframe.setSize(300, 150);
					fframe.setLayout(new BorderLayout());
					fframe.setLocationRelativeTo(null); // 화면 중앙에 위치

					// 상단 패널 (라벨 + 텍스트필드)
					JPanel topPanel = new JPanel();
					JLabel label = new JLabel("파일이름 : ");
					JTextField textField = new JTextField(15);
					topPanel.add(label);
					topPanel.add(textField);

					// 하단 패널 (확인 + 취소 버튼)
					JPanel buttonPanel = new JPanel();
					JButton confirmBtn = new JButton("확인");
					JButton cancelBtn = new JButton("취소");
					buttonPanel.add(confirmBtn);
					buttonPanel.add(cancelBtn);

					fframe.add(topPanel, BorderLayout.CENTER);
					fframe.add(buttonPanel, BorderLayout.SOUTH);
					fframe.setVisible(true);

					// 이벤트 처리
					confirmBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String filename = textField.getText();
							//System.out.println("입력된 파일 이름: " + filename);
							fnamestr = filename;
							
							String temp = String.format("파일이름 : %s",fnamestr);
							fnameLabel.setText(temp);
							fframe.dispose(); // 창 닫기
							// filename을 저장이나 녹화 등에 활용 가능!
						}
					});

					cancelBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							//System.out.println("파일 이름 입력 취소됨");
							fframe.dispose(); // 창 닫기
						}
					});


				}else if(btnText.equals("종료"))
				{
					System.exit(0);
				}

				workPanel.revalidate();



            });
            rightPanel.add(Box.createVerticalStrut(20));
            rightPanel.add(btn);
        }

        // 정보 라벨들
        JLabel versionLabel = new JLabel("버전 : v0.0021");
        JLabel authorLabel = new JLabel("만든이 : 컴퓨터판타지연구소");
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(versionLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(authorLabel);

        // panel들 붙이기
        
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(workPanel, BorderLayout.CENTER);

        frame.setVisible(true);

	}
	
	
	public static void main(String[] args) 
	{
		avr = new AntVRecordingv0_0021();
		SwingUtilities.invokeLater(() -> {
            avr.Init();
        });

	}
	static TargetDataLine line;
	
	public static void voiceRecordstart(String filename)
	{
		try {
            // 녹음 설정
            AudioFormat format = new AudioFormat(16000, 16, 2, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            //System.out.println("🔴 녹음 시작! 5초 동안 녹음 중...");
			
            // 녹음 쓰레드
            Thread stopper = new Thread(() -> {
				try{
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int bufferSize = 4096;
				byte[] buffer = new byte[bufferSize];

				while (recording) {
					int count = line.read(buffer, 0, buffer.length);
					if (count > 0) {
						if(muteCheckBox.isSelected())
						{
							// 🔇 음소거 상태일 때는 무음 데이터를 저장하거나 생략 가능
							byte[] silence = new byte[count]; // 0으로 채워진 무음
							baos.write(silence, 0, count);


						}else{
							baos.write(buffer, 0, count);
						}
					}
				}

				byte[] audioData = baos.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
				AudioInputStream ais = new AudioInputStream(bais, format, audioData.length / format.getFrameSize());
				AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(filename));
				}catch(IOException ioe)
				{
					ioe.printStackTrace();	
				}
            });
            stopper.start();

            

        } catch (Exception ex) {
            ex.printStackTrace();
        }


	}
	public static void voiceRecordstop()
	{
		try{
			// 5초 후 종료
        
			Thread.sleep(5000);
			line.stop();
			line.close();
        System.out.println("✅ 녹음 완료!");
		}catch(InterruptedException e) {
			System.out.println("스레드가 인터럽트됐어요!");
			Thread.currentThread().interrupt(); // 상태 복구
		}


	}
	public static void FFMpegmp4maker(String dir,String aviname,String wavname,String mp4name,String ffmpegexefile)
	{
		// AVI와 WAV 파일 경로
        String videoPath = dir+"\\"+aviname;
        String audioPath = dir+"\\"+wavname;
        String outputPath = dir+"\\"+mp4name;

        // FFmpeg 명령어
        String command = String.format(
            "%s -i \"%s\" -i \"%s\" -c:v copy -c:a aac -strict experimental \"%s\"",
            ffmpegexefile,videoPath, audioPath, outputPath
        );

        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            // FFmpeg 출력 로그 보기
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("✅ 병합 성공! MP4 파일 생성됨: " + outputPath);
            } else {
                System.out.println("❌ 오류 발생! FFmpeg 종료 코드: " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


	}
	public static void FFMpegmp4maker2(String dir,String inmp4name,String outmp4name,String ffmpegexefile)
	{
		// AVI와 WAV 파일 경로
        String inputPath = dir+"\\"+inmp4name;
        
        String outputPath = dir+"\\"+outmp4name;

        // FFmpeg 명령어
        String command = String.format(
            "%s -i \"%s\" -c:v h264 -c:a aac -strict experimental \"%s\"",
            ffmpegexefile,inputPath, outputPath
        );

        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            // FFmpeg 출력 로그 보기
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("✅ 병합 성공! MP4 파일 생성됨: " + outputPath);
            } else {
                System.out.println("❌ 오류 발생! FFmpeg 종료 코드: " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


	}
	private static Process recordingProcess;
	static TargetDataLine cline;
    public static void soundRecordingStart(String filename) {
        
		AudioFormat format = new AudioFormat(16000, 16, 2, true, false);
		try {
			Mixer.Info[] mixers = AudioSystem.getMixerInfo();
			for (Mixer.Info mixerInfo : mixers) {
				System.out.println("mixerInfo : " +mixerInfo.getName());
				if (mixerInfo.getName().contains("fdzfdfdfdsfs")) {
					Mixer mixer = AudioSystem.getMixer(mixerInfo);
					DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
					cline = (TargetDataLine) mixer.getLine(info);
					cline.open(format);
					cline.start();
                // 녹음 쓰레드 시작 (파일 저장 등)
				}
			}
		



            //System.out.println("🔴 녹음 시작! 5초 동안 녹음 중...");
			
            // 녹음 쓰레드
            Thread stopper = new Thread(() -> {
				try{
					if(cline!=null){
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						int bufferSize = 4096;
						byte[] buffer = new byte[bufferSize];

						while (recording) {
							int count = cline.read(buffer, 0, buffer.length);
							if (count > 0) {
								if(muteCheckBox.isSelected())
								{
									// 🔇 음소거 상태일 때는 무음 데이터를 저장하거나 생략 가능
									byte[] silence = new byte[count]; // 0으로 채워진 무음
									baos.write(silence, 0, count);


								}else{
									baos.write(buffer, 0, count);
								}
							}
						}

					byte[] audioData = baos.toByteArray();
					ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
					AudioInputStream ais = new AudioInputStream(bais, format, audioData.length / format.getFrameSize());
					AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(filename));
					}
				}catch(IOException ioe)
				{
					ioe.printStackTrace();	
				}
            });
            stopper.start();

            

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void soundRecordingStop() {
        try{
			// 5초 후 종료
        
			//Thread.sleep(5000);
			cline.stop();
			cline.close();
        System.out.println("✅ 녹음 완료!");
		}catch(Exception dfde) {
			System.out.println("스레드가 인터럽트됐어요!");
			Thread.currentThread().interrupt(); // 상태 복구
		}
    }

}
