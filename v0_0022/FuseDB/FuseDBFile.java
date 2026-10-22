package FuseDB;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;



public class FuseDBFile 
{
	public static String tableName;
    public List<TableData> tables = new ArrayList<>();
	public String logreturn;
    
	public static String FuseDBSaveDataTable(String table, List<Column> dataColumns)
	{
		String tableName = table; // 예시로 테이블 이름을 "users"라고 할게
		StringBuilder sb = new StringBuilder();

		sb.append("table ");
		sb.append(tableName);
		sb.append(" [ \n");
		for(Column column : dataColumns)
		{
			sb.append("{ ");
			sb.append("columnName = ");
			sb.append(column.name);
			sb.append(" , ");
			sb.append("columnType = ");
			sb.append(column.type);
			sb.append(" , ");
			sb.append("primarykey = ");
			if(column.primaryKey) sb.append("yes");
			else sb.append("no");
			sb.append(" , ");
			sb.append("notnull = ");
			if(column.notNull) sb.append("yes");
			else sb.append("no");
			sb.append(" , ");
			sb.append("unique = ");
			if(column.unique) sb.append("yes");
			else sb.append("no");
			sb.append(" } \n");
		}
		sb.append("] \n");
		String result = sb.toString(); // 최종 문자열: "table users"
		
		return result;
	}
	
	public static String FuseDBSaveData(List<Column> dataColumns, List<List<Object>> datalist) {
		StringBuilder sb = new StringBuilder();

		
		for(int j=0;j<datalist.size();j++)
		{
			// 데이터 수와 컬럼 수가 맞는지 확인
			//if (datalist.get(j).size() != dataColumns.size()) {
				//return sb.toString();
			//}
			List<Object> row = datalist.get(j);
			sb.append("{ ");
			// 각 데이터를 컬럼 정의에 따라 확인
			for (int i = 0; i < dataColumns.size(); i++) {
				Column col = dataColumns.get(i);
				Object value = row.get(i);

				// 타입 검사
				boolean typeMatch = false;
				switch (col.type.toLowerCase()) {
					case "int":
					case "integer":
						typeMatch = value instanceof Integer;
						break;
					case "string":
					case "text":
						typeMatch = value instanceof String;
						break;
					case "boolean":
						typeMatch = value instanceof Boolean;
						break;
					case "float":
						typeMatch = value instanceof Float;
						break;
					case "double":
						typeMatch = value instanceof Double;
						break;
					case "char":
						typeMatch = value instanceof Character;
						break;
					default:
						return "Error: 지원하지 않는 타입 '" + col.type + "' 입니다.";
				}

				if (!typeMatch) {
					return sb.toString();
				}

				// 저장 (여기선 문자열로 저장한다고 가정)
				sb.append(col.name).append(" : ").append(value.toString());
				if(i!=dataColumns.size()-1) sb.append(" , ");
			}
			sb.append(" } \n");
		}

		return sb.toString();
	}
	
	public static TableData FuseDBLoadDataTable(String raw) {
		TableData table = new TableData();
		String[] lines = raw.split("\n");

		for (String line : lines) {
			line = line.trim();
			if (line.startsWith("table ")) {
				table.name = line.substring(6, line.indexOf("[")).trim();
			} else if (line.startsWith("{") && line.endsWith("}")) {
				Column col = new Column();
				String[] parts = line.replace("{", "").replace("}", "").split(",");
				for (String part : parts) {
					String[] kv = part.trim().split("=");
					if (kv.length != 2) continue;
					String key = kv[0].trim();
					String value = kv[1].trim();

					switch (key) {
						case "columnName": col.name = value; break;
						case "columnType": col.type = value; break;
						case "primarykey": col.primaryKey = value.equalsIgnoreCase("yes"); break;
						case "notnull": col.notNull = value.equalsIgnoreCase("yes"); break;
						case "unique": col.unique = value.equalsIgnoreCase("yes"); break;
					}
				}
				table.columns.add(col);
			}
		}

		return table;
	}
	
	public static TableData FuseDBLoadData(TableData table, String raw) {
		String[] lines = raw.split("\n");

		for (String line : lines) {
			line = line.trim();
			if (line.startsWith("{") && line.endsWith("}")) {
				List<Object> row = new ArrayList<>();
				String[] parts = line.replace("{", "").replace("}", "").split(",");

				for (int i = 0; i < table.columns.size(); i++) {
					Column col = table.columns.get(i);
					String[] kv = parts[i].trim().split(":");
					if (kv.length != 2) continue;

					String valueStr = kv[1].trim();
					Object value = null;

					switch (col.type.toLowerCase()) {
						case "int":
						case "integer": value = Integer.parseInt(valueStr); break;
						case "string":
						case "text": value = valueStr; break;
						case "boolean": value = Boolean.parseBoolean(valueStr); break;
						case "float": value = Float.parseFloat(valueStr); break;
						case "double": value = Double.parseDouble(valueStr); break;
						case "char": value = valueStr.charAt(0); break;
						default: value = null;
					}

					row.add(value);
				}

				table.datas.add(row);
			}
		}
		return table;
	}
}
