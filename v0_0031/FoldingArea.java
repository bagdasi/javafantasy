import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.PlainTextChange;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.scene.Node;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.Comparator;
import AJEStruct.FoldInfo;

public class FoldingArea extends CodeArea {

    // key: { 가 있는 "현재 라인 번호"
    private Map<Integer, FoldInfo> foldedMap = new HashMap<>();
	private List<Integer> foldlengthlist = new ArrayList<>();
	private List<Integer> foldstartlist = new ArrayList<>();
	private List<FoldInfo> foldedList = new ArrayList<>();
	private List<FoldInfo> foldAllList = new ArrayList<>();
	private List<Integer> foldAllStartList = new ArrayList<>();
	private boolean foldb = false;
	private int clickedLine;
	int init_num = 0,line_number = 0;;
    public FoldingArea() {
		line_number = lineNumbers();
        /*this.setParagraphGraphicFactory(line -> {
    		return createFoldButton(line);
	});*/
        /*
        this.setParagraphGraphicFactory(line -> {
        	
        	

        	
		int realLine = convertScreenLineToDocumentLine(line); // ✅ 이게 핵심

            HBox gutter = new HBox();
            gutter.setPrefWidth(60);
            gutter.setStyle("-fx-background-color: #e0e0e0; -fx-alignment: CENTER_LEFT;");

            // 라인 번호
            Label lineNo = new Label(String.valueOf(line + 1));
            lineNo.setStyle("-fx-text-fill: #555555; -fx-padding: 0 5 0 5;");
            gutter.getChildren().add(lineNo);

            // 현재 줄 텍스트
            String text = "";
            foldb = false;
            if (line < this.getParagraphs().size()) {
                text = this.getParagraph(line).getText().trim();
            }
			
            int braceEndLine = findMatchingBraceLine(line);
            if (("{".equals(text.trim()) && this.getParagraph(braceEndLine).getText().trim().equals("}")) || foldstartlist.contains(line) ) {
		
                Button foldBtn = new Button("-");
                foldBtn.setPrefSize(18, 18);
                
		
                foldBtn.setStyle(
                        "-fx-font-size: 9px;" +
                        "-fx-padding: 0;" +
                        "-fx-background-radius: 3;" +
                        "-fx-background-color: #fefefe;"
                );

                // 이 줄이 이미 접힌 상태인지 확인
                if (foldedMap.containsKey(line)) {
                    foldBtn.setText("+");
                    
                }

                foldBtn.setOnAction(e -> {

                    

                    if (foldBtn.getText().equals("-")) {
                        // 접기
                        int currentStartLine = line;
                    	int currentEndLine = findMatchingBraceLine(currentStartLine);
                    	if (currentEndLine == -1) {
                        	return;
                    	}
                        foldBlock(currentStartLine, currentEndLine,lineNumbers());
                        foldBtn.setText("+");
                        foldb = false;
                    } else if(foldBtn.getText().equals("+")){
                        // 펼치기
                        int currentStartLine = line;
                        unfoldBlock(currentStartLine,lineNumbers());
                        foldBtn.setText("-");
                        foldb = false;
                    }
                    	Platform.runLater(() -> {
    				foldBtn.requestLayout();
    				foldBtn.layout();
    				//this.requestLayout();

    				//this.layout();

			});

                });
		
                gutter.getChildren().add(foldBtn);
                Platform.runLater(() -> {
    			foldBtn.requestLayout();
    			foldBtn.layout();
    			//this.requestLayout();

    			//this.layout();
		});
                
            }

            HBox root = new HBox(gutter);
            HBox.setHgrow(gutter, Priority.NEVER);
            return root;
            
        });*/
        /*this.caretPositionProperty().addListener((obs, oldPos, newPos) -> {
    		clickedLine = this.offsetToPosition(newPos.intValue(), Bias.Forward).getMajor();
    		//System.out.println("현재 커서 줄 = " + clickedLine);
    		Platform.runLater(() -> {
    			//foldBtn.requestLayout();
    			//foldBtn.layout();
    			this.requestLayout();

    			this.layout();
		});
	});*/
        /*this.plainTextChanges().subscribe(change -> {
    			updateFoldMap(change);
		
		});*/

    }

    /** { } 블록의 끝나는 줄 찾기 (현재 문서 기준) */
    private int findMatchingBraceLine(int startLine) {
        if (startLine < 0 || startLine >= getParagraphs().size()) return -1;

        int depth = 0;

        for (int i = startLine; i < this.getParagraphs().size(); i++) {
            String text = this.getParagraph(i).getText();

            for (char c : text.toCharArray()) {
                if (c == '{') depth++;
                else if (c == '}') {
                    depth--;
                    if (depth == 0) return i;
                }
            }
        }
        return -1;
    }

    /** 접기: { 다음 줄부터 } 줄 직전까지 삭제하고 저장 */
    private void foldBlock(int startLine, int endLine, int lineNumber) {
	
		
        // 이미 접혀 있으면 무시
        //if (foldedMap.containsKey(startLine)) return;
		Collections.sort(foldstartlist);
		for(int start :foldstartlist)
		{
			if(start == startLine)
			{
				return;
			}
		}
		
	
        
	int currentContentStartLine = startLine;
        if (currentContentStartLine > this.getParagraphs().size()) {
            currentContentStartLine = this.getParagraphs().size();
        }
        // 접힐 영역의 절대 위치 계산
        int from = this.getAbsolutePosition(currentContentStartLine, 0);
        int to   = this.getAbsolutePosition(endLine, this.getParagraph(endLine).length());

        if (from >= to) return;

        String original = this.getText(from, to);

        // 저장
        FoldInfo info = new FoldInfo((currentContentStartLine), (endLine-currentContentStartLine), original);
        
         // ✅ 부모 FoldInfo 찾기 (중첩 지원)
    	/*FoldInfo parent = findParentFold(currentContentStartLine);
    	if (parent != null) {
        	parent.children.add(info);
        	info.parent = parent;
        	

    	}*/
    	info = updateChildren(info);
        // 부모 안에 새 블록을 넣기 전에 기존 children 정리
        deleteChildren(info);
    	
	
        
        //foldedMap.put((currentContentStartLine), info);
        foldedList.add(info);
        foldstartlist.add((currentContentStartLine));
        
        
        
        // 실제로 삭제
        this.replaceText(from, to, "");
	System.out.println( "start : " + (currentContentStartLine) + "\nlength : " + (endLine-currentContentStartLine) + "\noriginal : " + original);
        
        Platform.runLater(() -> {
        	
        	
            	this.setParagraphGraphicFactory(line2 -> createFoldButton(line2));
            	
            	
        });
        
    }

    /** 펼치기: 저장된 텍스트 다시 삽입 */
    private void unfoldBlock(int startLine, int lineNumber) {

        	
		/*if (!foldedMap.containsKey(startLine)) 
        	{
        		//System.out.println("unfoldBlock");
        		//System.out.println(foldedMap.keySet());
        		//System.out.println(startLine);
        		

        		return;
		}*/        		
		
		
        //FoldInfo info = foldedMap.get(startLine);
        	FoldInfo info = null;
		for(FoldInfo match : foldedList)
		{
			if(match.startLine == startLine)
			{
				info = match;	
				break;
			}	
		}
		if(info == null) return;
        // 현재 문서 기준으로 { 줄이 밀렸을 수 있으니,
        // 다시 contentStartLine 을 계산해서 그 위치에 삽입
        int currentContentStartLine = startLine;
        if (currentContentStartLine > this.getParagraphs().size()) {
            currentContentStartLine = this.getParagraphs().size();
        }

        int insertPos = this.getAbsolutePosition(currentContentStartLine, 0);
	// ✅ children 블록도 다시 foldedMap 에 등록
    	
        this.insertText(insertPos, info.text);
        restoreChildren(info);

        //foldedMap.remove(startLine);
        	int num = 0;
        	List<Integer> deletefoldlist = new ArrayList<>();
        	List<Integer> deletelist = new ArrayList<>();
        	for(FoldInfo foldinfo : foldedList)
        	{
        		if(foldinfo.startLine == info.startLine)
        		{
        			deletefoldlist.add(num);
        			
			}
        		num++;
		}
		num = 0;
		for(int start : foldstartlist)
		{
			if(start == info.startLine)
			{
				deletelist.add(num);
					
			}
			num++;
		}
		for(int i : deletefoldlist)
		{
			if(deletefoldlist.size()>0)
			{
				foldedList.remove(i);	
			}
		}
		for(int i : deletelist)
		{
			if(deletelist.size()>0)
			{
				foldstartlist.remove(i);
			}	
		}
		System.out.println("deleteExcute");
        //foldstartlist.remove(Integer.valueOf(startLine));
        Platform.runLater(() -> {
            	this.setParagraphGraphicFactory(line2 -> createFoldButton(line2));
        });
    }
	/** children FoldInfo 들을 다시 foldedMap 에 등록 */
	private void restoreChildren(FoldInfo parent) {
		
    		for (FoldInfo child : parent.children) {

        		// child 의 startLine 은 부모가 펼쳐지면서 밀렸으므로 다시 계산
        		int newStart = child.startLine;

        		//foldedMap.put(newStart, child);
        		foldedList.add(child);
        		foldstartlist.add(newStart);
			System.out.println("리스토어실행");
        		// 재귀적으로 복원
        		//restoreChildren(child);
    		}
    		//parent.children.clear();
	}
	
	/** 현재 라인이 어떤 FoldInfo 안에 포함되는지 찾기 */
	private FoldInfo findParentFold(int line) {

    		FoldInfo parent = null;
		
    		for (FoldInfo info : foldedList) {
        		if (info.contains(line)) {
            			if (parent == null || info.startLine > parent.startLine) {
                			parent = info;
            			}
        		}
    		}

    		return parent;
	}
	/** children FoldInfo 들을 foldedMap / foldstartlist 에서 삭제 */
	private void deleteChildren(FoldInfo parent) {
		
		List<Integer> deletenum = new ArrayList<>();
		List<Integer> deletestartnum = new ArrayList<>();
    		for(FoldInfo info : parent.children)
    		{
    			int num = 0;
    			
    			for(FoldInfo match : foldedList)
    			{
    				if(match.startLine == info.startLine)
    				{
    					deletenum.add(num);
    					break;		
				}
				num++;
			}
			num = 0;
			for(int start : foldstartlist)
			{
				if(start == info.startLine)
				{
					deletestartnum.add(num);
					break;	
				}
				num++;	
			}
			
			Collections.sort(deletenum, (a, b) -> b - a);
			System.out.println("deletenum : " + deletenum);
			Collections.sort(deletestartnum, (a, b) -> b - a);
			for(int i : deletenum)
			{
				if(deletenum.size()>0 && foldedList.size()>0)
				{
					foldedList.remove(i);	
				}
			}
			for(int i : deletestartnum)
			{
				if(deletestartnum.size()>0 && foldstartlist.size()>0)
				{
					foldstartlist.remove(i);	
				}
			}
    			// 3) 부모 FoldInfo 삭제
    			//foldedMap.remove(info.startLine);
    			//foldstartlist.remove(Integer.valueOf(info.startLine));
    			//System.out.println("list삭제 : " +info.startLine);

    			
		}
	}
	private FoldInfo updateChildren(FoldInfo parent) {
		
		Collections.sort(foldstartlist);
    		for(int start : foldstartlist)
    		{	
    			int num = 0;
    			FoldInfo info = null;
    			for(FoldInfo match : foldedList)
    			{
    				if(match.startLine == start)
    				{
    					info = match;
    					System.out.println("info.startLine : " + info.startLine + " info.EndLine : " + (info.startLine+info.lineLength));
    					break;		
				}
				num++;
			}
    			if(info == null && start == 0)
    			{
    				continue;	
			}
			//System.out.println("parent.startLine : " + parent.startLine + " parent.EndLine : " + (parent.startLine+parent.lineLength));
    			if(info.startLine > parent.startLine && (info.startLine) < (parent.startLine+parent.lineLength))
    			{
    				System.out.println("list추가 : " + info.startLine);
    				parent.children.add(info);
    				parent.childrenstartline.add(start);
			}
		}
		
		return parent;
	}
	private FoldInfo updateChildFold(FoldInfo parent,boolean ChangeB, int insertLine , int removeLine)
	{
		
		for(FoldInfo child : parent.children)
		{
			
			if(ChangeB)
			{
				child.startLine += insertLine;
				child.startLine -= removeLine;
			}
			
			
			child = updateChildFold(child,ChangeB,insertLine,removeLine);
			
			
			
		}
		
		
		return parent;
	}
    public int lineNumbers()
    {	
    		return this.getParagraphs().size();

	}
	private int convertScreenLineToDocumentLine(int screenLine) {
    		int length = 0;

    		// foldstartlist는 반드시 정렬되어 있어야 함
    		for (int start : foldstartlist) {
        		FoldInfo info = foldedMap.get(start);

        		// start < (screenLine + length) 이면
        		// 이 접힌 블록은 화면에서 사라져 있으므로 length에 더해줘야 함
        		if (start < screenLine + length) {
            		length += info.lineLength;
        		}
    		}

    		return screenLine + length;
	}
	private void updateFoldMap(PlainTextChange change)
	{
		// 여기서 foldstartlist, foldedMap의 key들을 전부 업데이트해야 함
    			int insertedLines = (int) change.getInserted().chars().filter(ch -> ch == '\n').count();
			int removedLines  = (int) change.getRemoved().chars().filter(ch -> ch == '\n').count();

    			int pos = change.getPosition(); // ✅ 최신 버전에서는 여기 있음

    			int changedLine = this.offsetToPosition(pos, Bias.Forward).getMajor();

    			//Map<Integer, FoldInfo> newMap = new HashMap<>();
    			List<FoldInfo> newfoldlist = new ArrayList<>();
    			List<Integer> newlist = new ArrayList<>();
			Collections.sort(foldstartlist);
					int length = 0;
					int count = 0;
			for (int start : foldstartlist) {
    				FoldInfo info = null;
    				int num = 0;
    				for(FoldInfo match : foldedList)
    				{
    					if(match.startLine == start)
    					{
    						info = match;
    						break;		
					}
					num++;
				}
    				if (info == null) {
    					System.out.println("[updateFoldMap] foldedList에 " + start + " 없음 → 제거");
    					continue; // 이 값은 무시
				}


    				int newStart = start;
					
					
			
					
						
					
				

				boolean changeb = false;	
						
    				if (start > changedLine) {
    					
        					
    					changeb = true;

        				newStart += insertedLines;
        				newStart -= removedLines;
        				//System.out.println("newStart : " + newStart);
    				}
				newStart = Math.max(0,newStart);
    				info.startLine = newStart;
    				//newMap.put(newStart, info);
    				//info = updateChildFold(info,changedLine, insertedLines ,removedLines);
    				newfoldlist.add(info);
    				newlist.add(newStart);
    				updateChildFold(info, changeb, insertedLines, removedLines);

    				
			}
			
			//foldedMap = newMap;
			foldedList = newfoldlist;
			foldstartlist = newlist;
    			line_number = lineNumbers();
    			System.out.println(foldstartlist);
    			Platform.runLater(() -> {
            			this.setParagraphGraphicFactory(line2 -> createFoldButton(line2));
        		});
	}
	private Node createEmptyGutter(int line) {
    		HBox gutter = new HBox();
    		gutter.setPrefWidth(60);
    		gutter.setStyle("-fx-background-color: #e0e0e0; -fx-alignment: CENTER_LEFT;");

    		Label lineNo = new Label(String.valueOf(line + 1));
    		lineNo.setStyle("-fx-text-fill: #555555; -fx-padding: 0 5 0 5;");
    		

    		gutter.getChildren().add(lineNo);
    		// 현재 줄 텍스트
            	String text = "";
            	foldb = false;
            	if (line < this.getParagraphs().size()) {
                	text = this.getParagraph(line).getText().trim();
            	}
			
            	int braceEndLine = findMatchingBraceLine(line);
    		if (("{".equals(text.trim()) && this.getParagraph(braceEndLine).getText().trim().equals("}")) || foldstartlist.contains(line) ) {
		
                	Button foldBtn = new Button("-");
                	foldBtn.setPrefSize(18, 18);
                
		
                	foldBtn.setStyle(
                        	"-fx-font-size: 9px;" +
                        	"-fx-padding: 0;" +
                        	"-fx-background-radius: 3;" +
                        	"-fx-background-color: #fefefe;"
                	);
                	gutter.getChildren().add(foldBtn);
                
		}

    		return gutter;
	}
	private Node createFoldButton(int line) {

    		HBox gutter = new HBox();
    		gutter.setPrefWidth(60);
    		gutter.setStyle("-fx-background-color: #e0e0e0; -fx-alignment: CENTER_LEFT;");

    		// 라인 번호
    		Label lineNo = new Label(String.valueOf(line + 1));
    		lineNo.setStyle("-fx-text-fill: #555555; -fx-padding: 0 5 0 5;");
    		gutter.getChildren().add(lineNo);
		
		
		// 현재 줄 텍스트
            	String text = "";
            	foldb = false;
            	if (line < this.getParagraphs().size()) {
                	text = this.getParagraph(line).getText().trim();
            	}
			
            	//int braceEndLine = findMatchingBraceLine(line);
            	boolean isFoldStart = false;

		if ("{".equals(text.trim())) {
    			int braceEndLine = findMatchingBraceLine(line);

    			if (braceEndLine != -1) {
        			String endText = this.getParagraph(braceEndLine).getText().trim();
        			if ("}".equals(endText)) {
            			isFoldStart = true;
        			}
    			}
		}


		if (isFoldStart || foldstartlist.contains(line)) {
    		// fold 버튼 생성

    			// fold 버튼
    			Button foldBtn = new Button("-");
    			foldBtn.setPrefSize(18, 18);
    			foldBtn.setStyle(
            			"-fx-font-size: 9px;" +
            			"-fx-padding: 0;" +
            			"-fx-background-radius: 3;" +
            			"-fx-background-color: #fefefe;"
    			);

    			// 이미 접힌 상태라면 + 표시
    			/*if (foldedMap.containsKey(line)) {
        			foldBtn.setText("+");
    			}*/
    			for(int start : foldstartlist)
    			{
    				if(start == line)
    				{
    					foldBtn.setText("+");
    					break;
				}
			}

    			foldBtn.setOnAction(e -> {

        			if (foldBtn.getText().equals("-")) {
            			// 접기
            			int currentStartLine = line;
            			int currentEndLine = findMatchingBraceLine(currentStartLine);
            			if (currentEndLine == -1) return;

            			foldBlock(currentStartLine, currentEndLine, lineNumbers());
            			foldBtn.setText("+");

        			} else {
            			// 펼치기
            			int currentStartLine = line;
            			unfoldBlock(currentStartLine, lineNumbers());
            			foldBtn.setText("-");
        			}

        			Platform.runLater(() -> {
            				this.setParagraphGraphicFactory(line2 -> createFoldButton(line2));
        			});
    			});

    			gutter.getChildren().add(foldBtn);
		}

    		return gutter;
	}
	
	public void InitSet()
	{
		foldstartlist = new ArrayList<>(); 
		foldedList = new ArrayList<>();
		
	}
	String fullText="";
	List<FoldInfo> savefoldedList;
	List<Integer> savefoldstartlist;
	public String getFullCode() {
		savefoldedList = new ArrayList<>(); 
		savefoldstartlist = new ArrayList<>(); 
		savefoldedList = foldedList;
		savefoldstartlist = foldstartlist;
		fullText = this.getText();
		unfoldBlockFullSet();
		
    		return fullText; // fold 여부 상관없이 전체 코드
	}
	private void foldRestoreChildren(FoldInfo parent) {
		
    		for (FoldInfo child : parent.children) {

        		// child 의 startLine 은 부모가 펼쳐지면서 밀렸으므로 다시 계산
        		int newStart = child.startLine;

        		//foldedMap.put(newStart, child);
        		savefoldedList.add(child);
        		savefoldstartlist.add(newStart);
			
        		
    		}
    		//parent.children.clear();
	}  
	private void unfoldBlockFullSet()
	{
		


		
		

        		
        		Collections.sort(savefoldstartlist);

        		for (int i = 0 ; i < savefoldstartlist.size();i++) {
            			unfoldBlockFull(savefoldstartlist.get(i));
        		}
        	if(savefoldstartlist.size()>0)
        	{
        		unfoldBlockFullSet();		
		}else{
			
		}
    		

			
	}  		
	private void unfoldBlockFull(int startLine) {

        	
		     		
		
		
        
        	FoldInfo info = null;
		for(FoldInfo match : savefoldedList)
		{
			if(match.startLine == startLine)
			{
				info = match;	
				break;
			}	
		}
		
      		if(info == null) return;
        	int currentContentStartLine = startLine;
        

      
        	String[] lines = fullText.split("\n",-1);

		int insertPos = 0;
		for (int i = 0; i < currentContentStartLine&& i< lines.length; i++) {
    			insertPos += lines[i].length()+1;
		}

		fullText =fullText.substring(0, insertPos)+ info.text + fullText.substring(insertPos);
		
		// 리스트에서 안전하게 제거
    		savefoldedList.removeIf(f -> f.startLine == startLine);
    		savefoldstartlist.removeIf(s -> s == startLine);
		unfoldUpdate(info.startLine,info.lineLength);
		info = updateChildFold(info,true, info.lineLength , 0);
        	foldRestoreChildren(info);
        

        
        	

		
		
	}
	private void unfoldUpdate(int startLine,int length)
	{
		List<Integer> templist = new ArrayList<>();
		List<FoldInfo> tempfoldlist = new ArrayList<>();
		for(FoldInfo info : savefoldedList)
		{
			if(startLine< info.startLine)
			{
				info.startLine += length;
				tempfoldlist.add(info);
				templist.add(info.startLine);	
			}
		}	
		
		savefoldedList = tempfoldlist;
		savefoldstartlist = templist;
	}
	
	
	

	
}


