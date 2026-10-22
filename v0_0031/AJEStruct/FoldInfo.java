package AJEStruct;

import java.util.List;
import java.util.ArrayList;

public class FoldInfo {
    
    public int startLine;     // 접힌 영역 시작 절대 위치
    public int lineLength;
    public String text;      // 접힌 텍스트
    public List<FoldInfo> children = new ArrayList<>();
	public FoldInfo parent = null;
	public List<Integer> childrenstartline = new ArrayList<>();
    public FoldInfo(int startLine, int lineLength, String text) {
        this.startLine = startLine;
        this.lineLength = lineLength;
        this.text = text;
    }
    public boolean contains(int line) {
        return line > startLine && line < (startLine+lineLength);
    }

}
