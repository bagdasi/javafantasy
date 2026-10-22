package AJEStruct;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JDialog;

public class NetworkString
{
	private ServerSocket serverSocket;
	private Socket clientSocket ;
	private Socket sclientSocket;
	public NetworkString() {
		/*try {
		serverSocket = new ServerSocket(60000);
		clientSocket = new Socket("127.0.0.1", 60000);
		sclientSocket = new Socket("127.0.0.1", 60000);
		} catch (IOException e) {
		e.printStackTrace();
		}*/
	}
	
	public String ClientReceiveFile(String ip,int port)
	{
		sclientSocket = null;
		
		//sclientSocket.close();
		//Socket socket = new Socket("34.171.169.160", 56678); // 서버 IP
		try{
			sclientSocket = new Socket(ip, port); // 서버 IP
			System.out.println("서버에 연결됨!");
			
			InputStream is = clientSocket.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = is.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			
			System.out.println("문자 다운로드 완료!");
			JOptionPane optionPane = new JOptionPane("코드 받기 완료!", JOptionPane.INFORMATION_MESSAGE);
			
			JDialog dialog = optionPane.createDialog("알림");
			dialog.setAlwaysOnTop(true);  // 항상 위에
			dialog.setModal(true);        // 모달 유지
			dialog.setVisible(true);
			
			is.close();
			sclientSocket.close();
			// ★ 인코딩은 반드시 서버가 보내는 인코딩과 맞춰야 함
			return baos.toString("UTF-8");
		}catch(IOException ioe)
		{
			JOptionPane optionPane = new JOptionPane("코드 받기 실패!", JOptionPane.INFORMATION_MESSAGE);
			
			JDialog dialog = optionPane.createDialog("알림");
			dialog.setAlwaysOnTop(true);  // 항상 위에
			dialog.setModal(true);        // 모달 유지
			dialog.setVisible(true);
			ioe.printStackTrace();
		}
		return "";
		
	}
	public void ClientSendFile(String ip,int port,String str)
	{
		//sclientSocket.close();
		//Socket socket = new Socket("34.171.169.160", 56678);
		sclientSocket = null;
		try{
			sclientSocket = new Socket(ip,port);
			System.out.println("서버에 연결됨!");
			OutputStream os = sclientSocket.getOutputStream();
			
			byte[] buffer = str.getBytes("UTF-8");
			os.write(buffer);
			System.out.println("문자 전송 완료!");
			JOptionPane optionPane = new JOptionPane("코드 전송 완료!", JOptionPane.INFORMATION_MESSAGE);
			
			JDialog dialog = optionPane.createDialog("알림");
			dialog.setAlwaysOnTop(true);  // 항상 위에
			dialog.setModal(true);        // 모달 유지
			dialog.setVisible(true);
			os.flush();
			os.close();
			
			sclientSocket.close();
		}catch(IOException ioe)
		{
			JOptionPane optionPane = new JOptionPane("코드 전송 실패!", JOptionPane.INFORMATION_MESSAGE);
			
			JDialog dialog = optionPane.createDialog("알림");
			dialog.setAlwaysOnTop(true);  // 항상 위에
			dialog.setModal(true);        // 모달 유지
			dialog.setVisible(true);
			ioe.printStackTrace();
		}
	}
	public String ServerReceiveFile(int port)
	{
		clientSocket = null;
		serverSocket = null;
		
		try{
			System.out.println("서버 시작...");
			serverSocket = new ServerSocket(port);
			System.out.println("서버 대기 중...");
			clientSocket = serverSocket.accept();
			System.out.println("클라이언트 연결됨!");
			InputStream is = clientSocket.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = is.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			System.out.println("문자다운로드 완료!");
			JOptionPane optionPane = new JOptionPane("코드 받기 완료!", JOptionPane.INFORMATION_MESSAGE);
			
			JDialog dialog = optionPane.createDialog("알림");
			dialog.setAlwaysOnTop(true);  // 항상 위에
			dialog.setModal(true);        // 모달 유지
			dialog.setVisible(true);
			is.close();
			clientSocket.close();
			serverSocket.close();
			// ★ 서버-클라이언트 인코딩 반드시 맞춰야 함
			
			return baos.toString("UTF-8");
		}catch(IOException ioe)
		{
			JOptionPane optionPane = new JOptionPane("코드 받기 실패!", JOptionPane.INFORMATION_MESSAGE);
			
			JDialog dialog = optionPane.createDialog("알림");
			dialog.setAlwaysOnTop(true);  // 항상 위에
			dialog.setModal(true);        // 모달 유지
			dialog.setVisible(true);
			ioe.printStackTrace();
		}
		return "";
	}
	public void ServerSendFile(int port,String str)
	{
		clientSocket = null;
		serverSocket = null;
		try{
			serverSocket = new ServerSocket(port);
			System.out.println("서버 대기 중...");
			clientSocket = serverSocket.accept();
			System.out.println("클라이언트 연결됨!");
			
			
			OutputStream os = clientSocket.getOutputStream();
			
			// 문자열을 UTF-8 바이트로 변환해서 전송
			byte[] data = str.getBytes("UTF-8");
			os.write(data);
			os.flush();
			
			System.out.println("문자 전송 완료!");
			JOptionPane optionPane = new JOptionPane("코드 전송 완료!", JOptionPane.INFORMATION_MESSAGE);
			
			JDialog dialog = optionPane.createDialog("알림");
			dialog.setAlwaysOnTop(true);  // 항상 위에
			dialog.setModal(true);        // 모달 유지
			dialog.setVisible(true);
			os.close();
			clientSocket.close();
			serverSocket.close();
		}catch(IOException ioe)
		{
			JOptionPane optionPane = new JOptionPane("코드 전송 실패!", JOptionPane.INFORMATION_MESSAGE);
			
			JDialog dialog = optionPane.createDialog("알림");
			dialog.setAlwaysOnTop(true);  // 항상 위에
			dialog.setModal(true);        // 모달 유지
			dialog.setVisible(true);
			ioe.printStackTrace();
		}
		
	}
	public void clientClose()
	{
		try{
			if (sclientSocket != null && !sclientSocket.isClosed()){
				sclientSocket.close();
			}
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	public void serverClose()
	{
		try{
			if (clientSocket != null && !clientSocket.isClosed()) {
				clientSocket.close();
			}
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}
