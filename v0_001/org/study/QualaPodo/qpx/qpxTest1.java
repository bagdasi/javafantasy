package org.study.QualaPodo.qpx;

public class qpxTest1 implements qpxTest0
{
	public qpxTestAction action;
	@Override
	public void setAction(qpxTestAction action){
		this.action = action;
	}
	
	public void fireEvent(qpxTestEvent e) {
		if(action != null) {
			action.handle(e);
		}
	}
	
	public static void main(String[] args){
		qpxTest1 test = new qpxTest1();
		
		
		test.setAction(e -> {
			System.out.println(e.getName());
		});
		test.fireEvent(new qpxTestEvent("민dsafsaerwerwerwe"));
	}
	
}
