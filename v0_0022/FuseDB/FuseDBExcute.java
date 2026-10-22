package FuseDB;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
public class FuseDBExcute 
{
	public static List<TableData> tablelist = new ArrayList<>();
	public static List<TableData> viewlist = new ArrayList<>();
	public static FuseDBFile fusefile = new FuseDBFile();
	public static String executeMessage = "";
	public static String sqlMessage = "";
	public static TableData tempData = new TableData();
	public FuseDBExcute(String command)
	{
		File folder = new File("fusedatabase"); // 검색할 폴더 경로

		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles();
			tablelist.clear();
			if (files != null) {
				for (File file : files) {
					if (file.isFile() && file.getName().toLowerCase().endsWith(".fdbf")) {
						StringBuilder sb = new StringBuilder();
						try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
							String line;
							while ((line = reader.readLine()) != null) {
								sb.append(line).append("\n"); // 줄 단위로 읽어서 추가
							}
							//System.out.println("file read success!");
							executeMessage = "file read success!";
							String data = sb.toString();
							int index = data.indexOf("data:");
							if (index != -1) {
								String before = data.substring(0, index); // "abcd"
								String after = data.substring(index + "data:".length()); // "ghijk"
								TableData table = fusefile.FuseDBLoadDataTable(before);
								table.datas = fusefile.FuseDBLoadData(table , after).datas;  
								tablelist.add(table);
												
							} else {
								executeMessage = "data: is no search.";
								//System.out.println("data: is no search.");
							}
						} catch (IOException ioe) {
							executeMessage = "file read error " + ioe.getMessage();
							System.out.println("file read error " + ioe.getMessage());
						}


					}else if (file.isFile() && file.getName().toLowerCase().endsWith(".fvf")) {
						StringBuilder sb = new StringBuilder();
						try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
							String line;
							while ((line = reader.readLine()) != null) {
								sb.append(line).append("\n"); // 줄 단위로 읽어서 추가
							}
							//System.out.println("file read success!");
							executeMessage = "file read success!";
							String data = sb.toString();
							int index = data.indexOf("data:");
							if (index != -1) {
								String before = data.substring(0, index); // "abcd"
								String after = data.substring(index + "data:".length()); // "ghijk"
								TableData viewtable = fusefile.FuseDBLoadDataTable(before);
								viewtable.datas = fusefile.FuseDBLoadData(viewtable , after).datas;  
								viewlist.add(viewtable);
												
							} else {
								executeMessage = "data: is no search.";
								//System.out.println("data: is no search.");
							}
						} catch (IOException ioe) {
							executeMessage = "file read error " + ioe.getMessage();
							System.out.println("file read error " + ioe.getMessage());
						}
					} else if (file.isDirectory()) {
						executeMessage = "하위 폴더: " + file.getName();
						//System.out.println("하위 폴더: " + file.getName());
					}
				}
			} else {
				executeMessage = "folder not search";
				//System.out.println("folder not search");
			}
		} else {
			executeMessage = "forder not";
			//System.out.println("forder not");
		}

		
		FuseDBsql sql = new FuseDBsql();
						
		try{
			String tablename = extractTableName(command);
							
			if(command.trim().startsWith("create table"))
			{
				TableData table = new TableData();
				table = sql.create(command);
				tablelist.add(table);
			}else if(command.trim().startsWith("alter table"))
			{
				sql.alter(command,tablelist);
			}else if(command.trim().startsWith("drop table") || command.trim().startsWith("drop view"))
			{
				sql.drop(command,tablelist, viewlist);
			}else if(command.trim().startsWith("insert into"))
			{
								
				List<Object> data = new ArrayList<>();
				data = sql.insert(command,tablename,tablelist);
				System.out.println(tablename);
				int num = 0;
				if(data != null)
				{
					for(TableData table : tablelist)
					{
						
						if(table.name.equals(tablename))
						{
							System.out.println("여기까지 왔어."+num);
							tablelist.get(num).tableset(data);
							//System.out.println(tablelist.get(num).datas.get(0));
							break;
						}
						num++;
					}
				}
			}else if(command.trim().startsWith("update"))
			{
				sql.update(command,tablename,tablelist);
			}else if(command.trim().startsWith("delete from"))
			{
				sql.delete(command,tablename,tablelist);
			}else if(command.trim().startsWith("select"))
			{
				tempData = sql.select(command,tablename,	tablelist);
			}else if(command.trim().startsWith("create view"))
			{
				String selectstr = sql.createv(command);
				
				String selecttablename = extractTableName(selectstr);
				tempData = sql.select(selectstr,selecttablename,	tablelist);
				tempData.name = tablename;
				viewlist.add(tempData);
			}else if(command.trim().startsWith("show view"))
			{
				tempData = sql.showview(tablename,viewlist);
			}else if(command.trim().startsWith("show column"))
			{
				tempData = sql.showColumn(tablename,tablelist);
			}
			sqlMessage = sql.dbMessage;
		}catch(Exception e)
		{
			
		}
	}
	public FuseDBExcute()
	{
		File folder = new File("fusedatabase"); // 검색할 폴더 경로

		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles();
			tablelist.clear();
			if (files != null) {
				for (File file : files) {
					if (file.isFile() && file.getName().toLowerCase().endsWith(".fdbf")) {
						StringBuilder sb = new StringBuilder();
						try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
							String line;
							while ((line = reader.readLine()) != null) {
								sb.append(line).append("\n"); // 줄 단위로 읽어서 추가
							}
							//System.out.println("file read success!");
							executeMessage = "file read success!";
							String data = sb.toString();
							int index = data.indexOf("data:");
							if (index != -1) {
								String before = data.substring(0, index); // "abcd"
								String after = data.substring(index + "data:".length()); // "ghijk"
								TableData table = fusefile.FuseDBLoadDataTable(before);
								table.datas = fusefile.FuseDBLoadData(table , after).datas;  
								tablelist.add(table);
												
							} else {
								executeMessage = "data: is no search.";
								//System.out.println("data: is no search.");
							}
						} catch (IOException ioe) {
							executeMessage = "file read error " + ioe.getMessage();
							System.out.println("file read error " + ioe.getMessage());
						}


					}else if (file.isFile() && file.getName().toLowerCase().endsWith(".fvf")) {
						StringBuilder sb = new StringBuilder();
						try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
							String line;
							while ((line = reader.readLine()) != null) {
								sb.append(line).append("\n"); // 줄 단위로 읽어서 추가
							}
							//System.out.println("file read success!");
							executeMessage = "file read success!";
							String data = sb.toString();
							int index = data.indexOf("data:");
							if (index != -1) {
								String before = data.substring(0, index); // "abcd"
								String after = data.substring(index + "data:".length()); // "ghijk"
								TableData viewtable = fusefile.FuseDBLoadDataTable(before);
								viewtable.datas = fusefile.FuseDBLoadData(viewtable , after).datas;  
								viewlist.add(viewtable);
												
							} else {
								executeMessage = "data: is no search.";
								//System.out.println("data: is no search.");
							}
						} catch (IOException ioe) {
							executeMessage = "file read error " + ioe.getMessage();
							System.out.println("file read error " + ioe.getMessage());
						}
					} else if (file.isDirectory()) {
						executeMessage = "하위 폴더: " + file.getName();
						//System.out.println("하위 폴더: " + file.getName());
					}
				}
			} else {
				//System.out.println("folder not search");
				executeMessage = "folder not search";
			}
		} else {
			//System.out.println("forder not");
			executeMessage = "forder not";
		}

		
	}
	
	public void excute(String command)
	{
		FuseDBsql sql = new FuseDBsql();
						
		try{
			String tablename = extractTableName(command);
							
			if(command.trim().startsWith("create table"))
			{
				TableData table = new TableData();
				table = sql.create(command);
				tablelist.add(table);
			}else if(command.trim().startsWith("alter table"))
			{
				sql.alter(command,tablelist);
			}else if(command.trim().startsWith("drop table") || command.trim().startsWith("drop view"))
			{
				sql.drop(command,tablelist, viewlist);
			}else if(command.trim().startsWith("insert into"))
			{
								
				List<Object> data = new ArrayList<>();
				data = sql.insert(command,tablename,tablelist);
				System.out.println(tablename);
				int num = 0;
				if(data != null)
				{
					for(TableData table : tablelist)
					{
						//System.out.println("test name : " + tablelist.get(num).name);
						if(table.name.equals(tablename))
						{
							System.out.println("뭐야 이건..."+ num);
							tablelist.get(num).tableset(data);
							System.out.println(tablelist.get(num).datas.get(1));
							break;
						}
						num++;
					}
				}
			}else if(command.trim().startsWith("update"))
			{
				sql.update(command,tablename,tablelist);
			}else if(command.trim().startsWith("delete from"))
			{
				sql.delete(command,tablename,tablelist);
			}else if(command.trim().startsWith("select"))
			{
				tempData = sql.select(command,tablename,	tablelist);
			}else if(command.trim().startsWith("create view"))
			{
				String selectstr = sql.createv(command);
				
				String selecttablename = extractTableName(selectstr);
				tempData = sql.select(selectstr,selecttablename,	tablelist);
				tempData.name = tablename;
				
				viewlist.add(tempData);
			}else if(command.trim().startsWith("show view"))
			{
				tempData = sql.showview(tablename,viewlist);
			}else if(command.trim().startsWith("show column"))
			{
				tempData = sql.showColumn(tablename,tablelist);
			}
			sqlMessage = sql.dbMessage;
		}catch(Exception e)
		{
			
		}
	}
	public void saveDB()
	{
		for(TableData table : tablelist)
		{
			StringBuilder savesb = new StringBuilder();
			savesb.append(fusefile.FuseDBSaveDataTable(table.name, table.columns));
			savesb.append("data:\n");
							
			savesb.append(fusefile.FuseDBSaveData(table.columns, table.datas));
			try {
				String filename = "fusedatabase"+ File.separator +table.name+".fdbf";
				File file = new File(filename); // 삭제할 파일 경로

				if (file.exists()) {
					if (file.delete()) {
						//System.out.println("파일 삭제 완료!");
						executeMessage = "파일 삭제 완료!";
					} else {
						//System.out.println("파일 삭제 실패!");
						executeMessage = "파일 삭제 실패!";
					}
				} else {
					//System.out.println("파일이 존재하지 않습니다.");
					executeMessage = "파일이 존재하지 않습니다.";
				}


				FileWriter writer = new FileWriter(filename); // 저장할 파일 이름
				writer.write(savesb.toString()); // StringBuilder → String
				writer.close(); // 파일 닫기
				//System.out.println("파일 저장 완료!");
				executeMessage = "파일 저장 완료!";
			} catch (IOException ioe) {
				//System.out.println("파일 저장 중 오류 발생: " + ioe.getMessage());
				executeMessage = "파일 저장 중 오류 발생: " + ioe.getMessage();
			}


		}
		for(TableData table : viewlist)
		{
			StringBuilder savesb = new StringBuilder();
			savesb.append(fusefile.FuseDBSaveDataTable(table.name, table.columns));
			savesb.append("data:\n");
							
			savesb.append(fusefile.FuseDBSaveData(table.columns, table.datas));
			try {
				String filename = "fusedatabase"+ File.separator +table.name+".fvf";
				File file = new File(filename); // 삭제할 파일 경로

				if (file.exists()) {
					if (file.delete()) {
						//System.out.println("파일 삭제 완료!");
						executeMessage = "파일 삭제 완료!";
					} else {
						//System.out.println("파일 삭제 실패!");
						executeMessage = "파일 삭제 실패!";
					}
				} else {
					//System.out.println("파일이 존재하지 않습니다.");
					executeMessage = "파일이 존재하지 않습니다.";
				}


				FileWriter writer = new FileWriter(filename); // 저장할 파일 이름
				writer.write(savesb.toString()); // StringBuilder → String
				writer.close(); // 파일 닫기
				//System.out.println("파일 저장 완료!");
				executeMessage = "파일 저장 완료!";
			} catch (IOException ioe) {
				//System.out.println("파일 저장 중 오류 발생: " + ioe.getMessage());
				executeMessage = "파일 저장 중 오류 발생: " + ioe.getMessage();
			}


		}
	}
	
	public void LoadDB()
	{
		File folder = new File("fusedatabase"); // 검색할 폴더 경로

		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles();
			tablelist.clear();
			if (files != null) {
				for (File file : files) {
					if (file.isFile() && file.getName().toLowerCase().endsWith(".fdbf")) {
						StringBuilder sb = new StringBuilder();
						try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
							String line;
							while ((line = reader.readLine()) != null) {
								sb.append(line).append("\n"); // 줄 단위로 읽어서 추가
							}
							//System.out.println("file read success!");
							executeMessage = "file read success!";
							String data = sb.toString();
							int index = data.indexOf("data:");
							if (index != -1) {
								String before = data.substring(0, index); // "abcd"
								String after = data.substring(index + "data:".length()); // "ghijk"
								TableData table = fusefile.FuseDBLoadDataTable(before);
								table.datas = fusefile.FuseDBLoadData(table , after).datas;  
								tablelist.add(table);
												
							} else {
								//System.out.println("data: is no search.");
								executeMessage = "data: is no search.";
							}
						} catch (IOException ioe) {
							//System.out.println("file read error " + ioe.getMessage());
							executeMessage = "file read error " + ioe.getMessage();
						}


					}else if (file.isFile() && file.getName().toLowerCase().endsWith(".fvf")) {
						StringBuilder sb = new StringBuilder();
						try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
							String line;
							while ((line = reader.readLine()) != null) {
								sb.append(line).append("\n"); // 줄 단위로 읽어서 추가
							}
							//System.out.println("file read success!");
							executeMessage = "file read success!";
							String data = sb.toString();
							int index = data.indexOf("data:");
							if (index != -1) {
								String before = data.substring(0, index); // "abcd"
								String after = data.substring(index + "data:".length()); // "ghijk"
								TableData table = fusefile.FuseDBLoadDataTable(before);
								table.datas = fusefile.FuseDBLoadData(table , after).datas;  
								viewlist.add(table);
												
							} else {
								//System.out.println("data: is no search.");
								executeMessage = "data: is no search.";
							}
						} catch (IOException ioe) {
							//System.out.println("file read error " + ioe.getMessage());
							executeMessage = "file read error " + ioe.getMessage();
						}


					} else if (file.isDirectory()) {
						//System.out.println("하위 폴더: " + file.getName());
						executeMessage = "하위 폴더: " + file.getName();
					}
				}
			} else {
				//System.out.println("folder not search");
				executeMessage = "folder not search";
			}
		} else {
			//System.out.println("forder not");
			executeMessage = "forder not";
		}
	}
	public static String extractTableName(String command) {
		command = command.trim().toLowerCase();

		String[] tokens = command.split("\\s+");
		String tableName = null;

		if (command.startsWith("create table") ||
			command.startsWith("alter table") ||
			command.startsWith("drop table") ||
			command.startsWith("drop view") ||
			command.startsWith("insert into") ||
			command.startsWith("create view")||
			command.startsWith("show view")||
			command.startsWith("show column")){
        
			tableName = tokens[2];
		} else if (command.startsWith("update")) {
			tableName = tokens[1];
		} else if (command.startsWith("delete from")) {
			tableName = tokens[2];
		}else if (command.startsWith("select")) {
			// FROM 위치 찾기
			for (int i = 0; i < tokens.length - 1; i++) {
				if (tokens[i].equals("from")) {
					tableName = tokens[i + 1];
					break;
				}
			}
		}



		// 특수문자 제거 (예: {, (, 등)
		if (tableName != null) {
			//tableName = tableName.replaceAll("[^a-zA-Z0-9_]", "");
		}

		return tableName;
	}
}
