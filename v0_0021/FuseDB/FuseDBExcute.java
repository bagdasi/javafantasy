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
	public static FuseDBFile fusefile = new FuseDBFile();
	
	public FuseDBExcute(String command)
	{
		File folder = new File("database"); // 검색할 폴더 경로

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
							System.out.println("file read success!");
							String data = sb.toString();
							int index = data.indexOf("data:");
							if (index != -1) {
								String before = data.substring(0, index); // "abcd"
								String after = data.substring(index + "data:".length()); // "ghijk"
								TableData table = fusefile.FuseDBLoadDataTable(before);
								table.datas = fusefile.FuseDBLoadData(table , after).datas;  
								tablelist.add(table);
												
							} else {
								System.out.println("data: is no search.");
							}
						} catch (IOException ioe) {
							System.out.println("file read error " + ioe.getMessage());
						}


					} else if (file.isDirectory()) {
						System.out.println("하위 폴더: " + file.getName());
					}
				}
			} else {
				System.out.println("folder not search");
			}
		} else {
			System.out.println("forder not");
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
			}else if(command.trim().startsWith("drop table"))
			{
				sql.drop(command,tablelist);
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
						System.out.println("test name : " + tablelist.get(num).name);
						if(table.name.equals(tablename))
						{
							tablelist.get(num).tableset(data);
							System.out.println(tablelist.get(num).datas.get(0));
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
			}
		}catch(Exception e)
		{
			
		}
	}
	public FuseDBExcute()
	{
		File folder = new File("database"); // 검색할 폴더 경로

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
							System.out.println("file read success!");
							String data = sb.toString();
							int index = data.indexOf("data:");
							if (index != -1) {
								String before = data.substring(0, index); // "abcd"
								String after = data.substring(index + "data:".length()); // "ghijk"
								TableData table = fusefile.FuseDBLoadDataTable(before);
								table.datas = fusefile.FuseDBLoadData(table , after).datas;  
								tablelist.add(table);
												
							} else {
								System.out.println("data: is no search.");
							}
						} catch (IOException ioe) {
							System.out.println("file read error " + ioe.getMessage());
						}


					} else if (file.isDirectory()) {
						System.out.println("하위 폴더: " + file.getName());
					}
				}
			} else {
				System.out.println("folder not search");
			}
		} else {
			System.out.println("forder not");
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
			}else if(command.trim().startsWith("drop table"))
			{
				sql.drop(command,tablelist);
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
						System.out.println("test name : " + tablelist.get(num).name);
						if(table.name.equals(tablename))
						{
							tablelist.get(num).tableset(data);
							System.out.println(tablelist.get(num).datas.get(0));
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
			}
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
				String filename = "database\\"+table.name+".fdbf";
				File file = new File(filename); // 삭제할 파일 경로

				if (file.exists()) {
					if (file.delete()) {
						System.out.println("파일 삭제 완료!");
					} else {
						System.out.println("파일 삭제 실패!");
					}
				} else {
					System.out.println("파일이 존재하지 않습니다.");
				}


				FileWriter writer = new FileWriter(filename); // 저장할 파일 이름
				writer.write(savesb.toString()); // StringBuilder → String
				writer.close(); // 파일 닫기
				System.out.println("파일 저장 완료!");
			} catch (IOException ioe) {
				System.out.println("파일 저장 중 오류 발생: " + ioe.getMessage());
			}


		}
	}
	
	public void LoadDB()
	{
		File folder = new File("database"); // 검색할 폴더 경로

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
							System.out.println("file read success!");
							String data = sb.toString();
							int index = data.indexOf("data:");
							if (index != -1) {
								String before = data.substring(0, index); // "abcd"
								String after = data.substring(index + "data:".length()); // "ghijk"
								TableData table = fusefile.FuseDBLoadDataTable(before);
								table.datas = fusefile.FuseDBLoadData(table , after).datas;  
								tablelist.add(table);
												
							} else {
								System.out.println("data: is no search.");
							}
						} catch (IOException ioe) {
							System.out.println("file read error " + ioe.getMessage());
						}


					} else if (file.isDirectory()) {
						System.out.println("하위 폴더: " + file.getName());
					}
				}
			} else {
				System.out.println("folder not search");
			}
		} else {
			System.out.println("forder not");
		}
	}
	public static String extractTableName(String command) {
		command = command.trim().toLowerCase();

		String[] tokens = command.split("\\s+");
		String tableName = null;

		if (command.startsWith("create table") ||
			command.startsWith("alter table") ||
			command.startsWith("drop table") ||
			command.startsWith("insert into")) {
        
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
