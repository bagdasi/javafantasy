import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class menuRightPanel extends JPanel 
{
	QPPv0_001 qpp;
	int width=0,height=0;
	newFileButton nfb;
	qpFileOpenButton fob;
	qpFileSaveButton fsb;
	ExitButton exitb;
	SelectButton selb;
	LotateButton lotateb;
	CutButton cutb;
	CopyButton copyb;
	PasteButton pasteb;
	PreEditButton preb;
	NextEditButton nextb;
	PenButton penb;
	FillButton fillb;
	EraseButton eraseb;
	StringButton stringb;
	ColorButton colorb;
	SettingButton setb;
	ImageOptionButton imageob;
	MarkButton markb;
	GridButton gridb;
	StateButton stateb;
	LicenseButton licenseb;
	ColorHelpButton colorhb;
	SupportButton supportb;
	InforButton inforb;
	
	menuRightPanel(int width,int height)
	{
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(width,height));
		this.setBackground(Color.GREEN);
		this.width = width;
		this.height = height;
		
		
	}
	void menuFile()
	{
		nfb = new newFileButton("새로만들기");
		fob = new qpFileOpenButton("qp파일열기");
		fsb = new qpFileSaveButton("qp파일저장");
		exitb = new ExitButton("끝내기");
		this.add(nfb);
		this.add(fob);
		this.add(fsb);
		this.add(exitb);
		
		this.setVisible(true);
	}
	void menuEdit()
	{
		selb = new SelectButton("선택");
		lotateb = new LotateButton("회전");
		cutb = new CutButton("잘라내기");
		copyb = new CopyButton("복사");
		pasteb = new PasteButton("붙여놓기");
		preb = new PreEditButton("이전");
		nextb = new NextEditButton("다음");
		this.add(selb);
		this.add(lotateb);
		this.add(cutb);
		this.add(copyb);
		this.add(pasteb);
		this.add(preb);
		this.add(nextb);
		this.setVisible(true);
	}
	void menuDraw()
	{
		penb = new PenButton("펜");
		fillb = new FillButton("채우기");
		eraseb = new EraseButton("지우개");
		stringb = new StringButton("문자");
		colorb = new ColorButton("색상");
		this.add(penb);
		this.add(fillb);
		this.add(eraseb);
		this.add(stringb);
		this.add(colorb);
		this.setVisible(true);
	}
	void menuOption()
	{
		setb = new SettingButton("기본설정");
		imageob = new ImageOptionButton("이미지속성");
		this.add(setb);
		this.add(imageob);
		this.setVisible(true);
	}
	void menuView()
	{
		markb = new MarkButton("눈금");
		gridb = new GridButton("격자");
		stateb = new StateButton("상태");
		this.add(markb);
		this.add(gridb);
		this.add(stateb);
		this.setVisible(true);
	}
	void menuHelp()
	{
		licenseb = new LicenseButton("약관");
		colorhb = new ColorHelpButton("색코드");
		supportb = new SupportButton("지원");
		inforb = new InforButton("콰라포도정보");
		this.add(licenseb);
		this.add(colorhb);
		this.add(supportb);
		this.add(inforb);
		this.setVisible(true);
		
	}
	
}
