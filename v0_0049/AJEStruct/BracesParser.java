

package AJEStruct;

public class BracesParser {

    public static int countDepth(String text) {
    	boolean inChar = false;
        boolean inString = false;
        boolean inLineComment = false;
        boolean inBlockComment = false;
        int depth = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
		 // Determine if current char is escaped by backslashes immediately before it
            boolean escaped = false;
            if (i > 0) {
                int backslashes = 0;
                for (int j = i - 1; j >= 0 && text.charAt(j) == '\\'; j--) {
                    backslashes++;
                }
                escaped = (backslashes % 2 == 1);
            }

            // 문자열 시작/끝
            if (!inLineComment && !inBlockComment && c == '"' &&!inChar && !escaped) {
                inString = !inString;
                continue;
            }
            // 문자열 시작/끝
            if (!inLineComment && !inBlockComment && c == '\'' && !inString && !escaped) {
                inChar = !inChar;
                continue;
            }

            // 한 줄 주석 시작
            if (!inString && !inBlockComment && c == '/' && i + 1 < text.length() && text.charAt(i+1) == '/') {
                inLineComment = true;
                i++;
                continue;
            }

            // 블록 주석 시작
            if (!inString && !inLineComment && c == '/' && i + 1 < text.length() && text.charAt(i+1) == '*') {
                inBlockComment = true;
                i++;
                continue;
            }

            // 한 줄 주석 끝
            if (inLineComment && (c == '\n' || c == '\r')) {
                inLineComment = false;
                continue;
            }

            // 블록 주석 끝
            if (inBlockComment && c == '*' && i + 1 < text.length() && text.charAt(i+1) == '/') {
                inBlockComment = false;
                i++;
                continue;
            }

            // 실제 블록 계산
            if (!inString && !inLineComment && !inBlockComment && !inChar) {
                if (c == '{') depth++;
                else if (c == '}') depth--;
            }
        }

        return depth;
    }
}
