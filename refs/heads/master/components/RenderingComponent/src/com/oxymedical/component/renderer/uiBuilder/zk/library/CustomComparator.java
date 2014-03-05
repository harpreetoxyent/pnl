package com.oxymedical.component.renderer.uiBuilder.zk.library;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.oxymedical.component.renderer.data.GridDefConstants;

public class CustomComparator implements Comparator<Object[]> {
	
	private boolean asc;
	private int colIndex;
	private SimpleDateFormat smpdateFormat;
	private String compareType = "String";
	
	
	public CustomComparator(boolean ascending, int columnIndex, String dateFormat, String columnType) {
		asc = ascending;
		colIndex = columnIndex;
		smpdateFormat = null;
		if(dateFormat != null){
			smpdateFormat = new SimpleDateFormat(dateFormat);
		}
		compareType = columnType;
	}
	
	@Override
	public int compare(Object[] o1, Object[] o2) {
		int compareVal = 0;
		if(compareType != null && compareType.equalsIgnoreCase(GridDefConstants.ColDataType_string)){
			String s1 = (String)o1[colIndex];
			String s2 = (String)o2[colIndex];
			compareVal = s1.compareTo(s2);
		}else if(compareType != null && compareType.equalsIgnoreCase(GridDefConstants.ColDataType_date)){
	        String s1 = (String)o1[colIndex];
			String s2 = (String)o2[colIndex];
			Date d1 = null;
			try {
				d1 = smpdateFormat.parse(s1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date d2 = null;
			try {
				d2 = smpdateFormat.parse(s2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			compareVal = d1.compareTo(d2);
		}else if(compareType != null && compareType.equalsIgnoreCase(GridDefConstants.ColDataType_float)){
			Float s1 = Float.valueOf((String) o1[colIndex]); 
			Float s2 = Float.valueOf((String) o2[colIndex]); 
			compareVal = s1.compareTo(s2);
		}else if(compareType != null && compareType.equalsIgnoreCase(GridDefConstants.ColDataType_long)){
			Long s1 = Long.valueOf((String) o1[colIndex]); 
			Long s2 = Long.valueOf((String) o2[colIndex]); 
			compareVal = s1.compareTo(s2);
		}
		return asc ? compareVal: -compareVal;
	}
	
}








