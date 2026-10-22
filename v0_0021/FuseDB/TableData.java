package FuseDB;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class TableData 
{
	public String name;
	public List<Column> columns = new ArrayList<>();
	public List<List<Object>> datas = new ArrayList<>();
	
	
	public void tableReset()
	{
		name = "";
		columns = new ArrayList<>();
		datas = new ArrayList<>();
	}
	public void tableset(List<Object> data)
	{
		datas.add(data);
	}
}
