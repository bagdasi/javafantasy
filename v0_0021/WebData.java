import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

import java.util.Map;
import java.util.HashMap;

import java.io.FileInputStream;

import java.io.RandomAccessFile;
import java.io.DataInputStream;

import java.io.ByteArrayOutputStream;





public class WebData
{
	public static class Dictionary {
		
		private Map<String, DictIndex> indexMap = new HashMap<>();
		private RandomAccessFile dictFile;
		
		// StarDict idx/dict 파일 읽기
		public Dictionary(String idxPath, String dictPath) throws Exception {
			
			// dict 파일은 RandomAccessFile로 읽어야 함
			dictFile = new RandomAccessFile(dictPath, "r");
			
			// idx 파일은 바이너리 구조
			DataInputStream dis = new DataInputStream(new FileInputStream(idxPath));
			
			while (dis.available() > 0) {
				
				// 1) 단어 읽기 (null-terminated string)
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte b;
				
				while ((b = dis.readByte()) != 0) {
					baos.write(b);
				}
				
				String word = baos.toString("UTF-8");
				
				// 2) offset (4 bytes, big-endian)
				int offset = dis.readInt();
				
				// 3) length (4 bytes, big-endian)
				int length = dis.readInt();
				
				// 4) Map에 저장
				indexMap.put(word, new DictIndex(offset, length));
			}
			
			dis.close();
		}
		
		// 단어 검색
		public String search(String word) {
			try {
				DictIndex di = indexMap.get(word);
				
				if (di == null) {
					return "뜻을 찾을 수 없습니다.";
				}
				
				byte[] buffer = new byte[di.length];
				
				dictFile.seek(di.offset);
				dictFile.read(buffer);
				
				String meaning = new String(buffer, "UTF-8");
				
				// ★ HTML 태그 제거
				meaning = meaning.replaceAll("<[^>]*>", "");
				
				return meaning;
				
			} catch (Exception e) {
				return "오류 발생: " + e.getMessage();
			}
		}
		
		
		// offset + length 저장용 클래스
		private static class DictIndex {
			int offset;
			int length;
			
			DictIndex(int o, int l) {
				offset = o;
				length = l;
			}
		}
	}
	
	
	
	
	// 구글 번역 API (무료 비공식 방식)
	public static String googleTransSearch(String text)
	{
		try {
			String urlStr = "https://translate.googleapis.com/translate_a/single?client=gtx"
			+ "&sl=en&tl=ko&dt=t&q=" + URLEncoder.encode(text, "UTF-8");
			
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "UTF-8")
				);
			
			StringBuilder sb = new StringBuilder();
			String line;
			
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			br.close();
			
			// 구글 번역 JSON 구조: [[["번역결과","원문",null,null]],null,"en"]
			String result = sb.toString();
			
			// 번역 결과만 추출
			result = result.substring(result.indexOf("[[[") + 3);
			result = result.substring(0, result.indexOf("\"", 1));
			
			return result;
			
		} catch (Exception e) {
			return "오류 발생: " + e.getMessage();
		}
	}
}
