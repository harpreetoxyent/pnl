package com.oxymedical.component.db.application.register;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;

import com.oxymedical.component.db.application.register.pattern.DataPattern;
import com.oxymedical.component.db.application.register.pattern.FormPattern;
import com.oxymedical.component.db.application.register.pattern.SearchPattern;
import com.oxymedical.component.db.exception.DBComponentException;

/**
 * This interface states that RegisterWindow implements 
 * all methods of IRegisterWindow.
 * @author      Oxyent Medical
 * @version     1.0.0
 */
public interface IRegisterWindow 
{
	 public void registerBaseWindow(Document patternXML,String applicationNname) throws DBComponentException;
	 public Hashtable<String, List<String>> getApplicationPatternHash();
	 public void setApplicationPatternHash(Hashtable<String, List<String>> applicationPatternHash); 
	 public Hashtable<String, Hashtable> getBaseFormPatternHash(); 
	 public void setBaseFormPatternHash(Hashtable<String, Hashtable> baseFormPatternHash); 
	 public Hashtable<String, LinkedList<DataPattern>> getDataPatternHash(); 
	 public void setDataPatternHash(Hashtable<String,LinkedList<DataPattern>> dataPatternHash); 
	 public Hashtable<String, FormPattern> getFormPatternHash();
	 public void setFormPatternHash(Hashtable<String, FormPattern> formPatternHash);
	 public Hashtable<String, List<SearchPattern>> getSearchHash();
     public void setSearchHash(Hashtable<String, List<SearchPattern>> searchHash);
     public List<String> getApplicationPatternList();
     public void setApplicationPatternList(List<String> applicationPatternList);
     public Hashtable<String, Hashtable> getBaseDataPatternHash();
     
}
