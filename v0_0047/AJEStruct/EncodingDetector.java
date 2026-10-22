package AJEStruct;

import java.io.*;
import org.mozilla.universalchardet.UniversalDetector;

public class EncodingDetector {
	
	public static String detectEncoding(File file) throws IOException {
		byte[] buf = new byte[4096];
		FileInputStream fis = new FileInputStream(file);
		
		UniversalDetector detector = new UniversalDetector(null);
		
		int nread;
		while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}
		detector.dataEnd();
		
		String encoding = detector.getDetectedCharset();
		fis.close();
		
		if (encoding == null) {
			encoding = "UTF-8"; // 기본값
		}
		
		// EUC-JP로 잘못 감지되는 경우 → CP949로 강제
		if (encoding.equalsIgnoreCase("EUC-JP")) {
			encoding = "MS949";  // 또는 "CP949"
		}
		
		// Shift_JIS로 감지되었는데 한글 포함 가능성 → CP949로 강제
		if (encoding.equalsIgnoreCase("SHIFT_JIS")) {
			encoding = "MS949";
		}
		
		/*
		// EUC-KR은 CP949와 거의 동일 → 통일
		if (encoding.equalsIgnoreCase("EUC-KR")) {
			encoding = "MS949";
		}
		*/
		return encoding;
	}
}
