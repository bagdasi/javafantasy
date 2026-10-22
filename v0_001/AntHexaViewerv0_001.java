import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.*;


public class AntHexaViewerv0_001 {
	String filename;
	JLabel filelabel;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AntHexaViewerv0_001().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("앤트헥사뷰어");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        // 상단 버튼 패널
        JPanel topPanel = new JPanel();
        JButton openFileButton = new JButton("파일 열기");
        JButton exitButton = new JButton("종료하기");
		JLabel versionlabel = new JLabel("버전 : v0.001");
        topPanel.add(openFileButton);
        topPanel.add(exitButton);
		topPanel.add(versionlabel);
        // JTable 설정
        String[] columns = new String[17];
		columns[0] = "n";
        for (int i = 0; i < 16; i++) {
            columns[i+1] = String.valueOf(i);
        }

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        for (int i = 0; i < 1; i++) { // 예제용으로 20개 행 추가
            Object[] rowData = new Object[17];
            rowData[0] = i * 16;
            for (int j = 1; j <= 16; j++) {
                rowData[j] = ""; // 데이터 추가 가능
            }
            tableModel.addRow(rowData);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // 버튼 이벤트
        exitButton.addActionListener(e -> System.exit(0));
        openFileButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // 모든 파일 선택 가능
                
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    //System.out.println("선택된 파일: " + selectedFile.getAbsolutePath());
					filename = selectedFile.getAbsolutePath();
					try (FileInputStream fis = new FileInputStream(filename)) {
						System.out.println("파일을 분석 중: " + filename);
						int byteValue;
						int count = 0;
						tableModel.setRowCount(0);
						Object[] tmpdata = new Object[17];
						while ((byteValue = fis.read()) != -1) {
							
							
							
							tmpdata[count%16+1] = String.format("%02X",byteValue); // 데이터 추가 가능
							
							//System.out.printf("%02X ", byteValue);
							
							
							if (count % 16 == 0&&count!=0) {
								tmpdata[0] = String.valueOf(count - 16);
								tableModel.addRow(tmpdata);
							}
							count++;
						}
					} catch (IOException e) {
						System.err.println("파일을 읽는 중 오류 발생: " + e.getMessage());
					}
					
					filelabel.setText(filename);
					frame.revalidate();
                }

			}
		});
		filelabel = new JLabel();
        // 레이아웃 구성
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(filelabel,BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }
}