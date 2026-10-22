package myPath;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class pathInfoSaveOpen
{
	public void pathSave(File file,String filename)
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
        		// file의 절대경로 가져오기
        		String path = file.getAbsolutePath();

        		// 파일에 쓰기
        		bw.write(path);

    		} catch (IOException e) {
        		e.printStackTrace();
    		}

	}
	
	public String pathOpen(String filename)
	{
		StringBuilder sb = new StringBuilder();

    		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        		String line;
        		while ((line = br.readLine()) != null) {
            			sb.append(line);
            
        		}
    		} catch (IOException e) {
        		e.printStackTrace();
    		}

    		return sb.toString().trim(); // 마지막 줄바꿈 제거

	}
}