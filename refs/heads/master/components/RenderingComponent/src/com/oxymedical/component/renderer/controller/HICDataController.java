package com.oxymedical.component.renderer.controller;

import java.util.Hashtable;

import com.oxymedical.core.commonData.IHICData;

public class HICDataController {

	private static Hashtable<String, IHICData> hicDataTable;
	static{
		hicDataTable = new Hashtable<String, IHICData>();
	}
	
	public static void addHICData(String sessionId, IHICData hicData) {
		hicDataTable.put(sessionId, hicData);
		System.out.println("&&&&&&&&&&&&&&& hicDataTable size &&&&&&&&&&&&&&&  = "+hicDataTable.size());
		System.out.println("&&&&&&&&&&&&&&&  hicDataTable contents &&&&&&&&&&&&&&&  = "+hicDataTable);
	}
	
	public static IHICData getHICData(String sessionId) {
		return hicDataTable.get(sessionId);
	}
}
