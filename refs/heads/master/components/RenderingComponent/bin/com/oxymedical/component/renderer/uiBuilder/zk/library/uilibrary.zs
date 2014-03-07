import java.lang.*;
import java.util.*;
import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zul.*;
import com.oxymedical.component.renderer.application.router.*;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListBox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.image.AImage;
import org.zkoss.zul.ext.Paginal;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ComboRenderer;
import org.zkoss.zul.Datebox;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.impl.XulElement;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zk.device.Devices;
import com.sun.org.apache.xerces.internal.impl.dv.util.*;
import java.io.File;
import java.util.Random;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;
import org.jfree.chart.encoders.*;
import com.oxymedical.core.userdata.IRights;
import org.zkoss.zul.SimpleXYModel;
import org.zkoss.zul.XYModel;
import org.dom4j.Document;
import com.oxymedical.core.xmlutil.XmlReader;
import com.oxymedical.core.commonData.IFormPattern;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zkoss.zul.Div;
import org.zkoss.zul.Iframe;

import com.oxymedical.component.ui.zk.*;

import com.oxymedical.core.stringutil.StringUtil;
import com.oxymedical.component.renderer.uiBuilder.zk.library.RowRendererArray;
import com.oxymedical.component.renderer.data.GridDef;
import com.oxymedical.component.renderer.data.ColumnDef;
import com.oxymedical.component.renderer.data.CustomListModel;
import com.oxymedical.component.renderer.uiBuilder.zk.library.CustomComparator;
import com.oxymedical.component.renderer.uiBuilder.zk.library.ItemRendererArray;
import com.oxymedical.component.renderer.uiBuilder.zk.library.UiLibraryUtil;
import com.oxymedical.component.renderer.uiBuilder.zk.library.SimpleListModelExt;
import com.oxymedical.component.renderer.uiBuilder.zk.library.PagingInfo;
import com.oxymedical.component.renderer.uiBuilder.zk.library.LogOutCommand;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.stringutil.StringUtil;
import com.oxymedical.servlet.HICServlet.HICRouter;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.renderdata.DataUnit;
import com.oxymedical.core.querydata.*;
import com.oxymedical.component.renderer.RendererComponent;
import com.oxymedical.core.router.IRouter;
import com.oxymedical.core.router.IDataUnitRouter;
import com.oxymedical.core.commonData.ISource;
import com.oxymedical.core.commonData.Source;
import com.oxymedical.component.renderer.constants.ZKConstants;
import com.oxymedical.core.renderdata.ExternalData;
import com.oxymedical.core.userdata.IUserPattern;
import com.oxymedical.core.userdata.UserPattern;
import com.oxymedical.component.renderer.util.RenderingUtility;
import com.oxymedical.component.renderer.data.HibernateQueryResultDataSource;
import com.oxymedical.component.renderer.command.UiLibraryCompositeCommand;
import com.oxymedical.component.renderer.uiBuilder.zk.library.UILibraryConstant;
import com.oxymedical.component.renderer.constants.UILibraryConstant;
import com.oxymedical.component.renderer.controller.HICDataController;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.oxymedical.core.dateutil.DateUtil;
import java.text.*;
import java.sql.Time;
import java.util.LinkedHashMap;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import java.io.*;
import com.oxymedical.core.braink.moduleparameterinfo.*;


//tree variables
private Hashtable formValues = new Hashtable();
private LinkedHashMap columnOrder = new LinkedHashMap();
private Hashtable levelPrimaryId = new Hashtable();
private Hashtable comboTable = new Hashtable();
private String comboSelectedValue;
private int treeNodeCount = 0;
private int lastTreeLevel = -1;
private String displayLabelPaging=null;

private String dataPatternId ="";
private String formPatternId ="";
private boolean loginStatus = false;
List textId = new ArrayList();
UiLibraryUtil uiLibraryUtil=new UiLibraryUtil();
boolean validRequest = true;
String webMessage = null;
// String deleteRecordId=null;
Object comboObj=null;
LinkedHashMap updateHash= null;
boolean validationValue = true;
String[] queryFields = null; 
IHICData returnHicData = null;
private String pagingId;
private String listId;
boolean validListRequest = false;
LogOutCommand logout = null;
private int totalSize;

private int cancel = 2;
private int yes = 16;

private Hashtable photoMap = new Hashtable();

String updatedMsg = "Patient Information has not been updated.";

String THEME_COOKIE_KEY = "zktheme";

public Object dynamicObjectCreation(Object controlObject,int columnNoToAdd,String controlIDValue, String displayValue, int selectedRowID, String controlType){
	List obj_child = ((Listbox)controlObject).getChildren();
	String id = "";
	for(int j = 1; j< obj_child.toArray().length ; j++){
		if(selectedRowID == j-1){
			Object obj = obj_child.toArray()[j];
			List lst=((Listitem)obj).getChildren();
			for(int i=1;i<lst.size();i++){
				if(i==columnNoToAdd){
					((Listitem)obj).removeChild((Listcell)lst.get(i));
				}
			}
			for(int i=1;i<lst.size();i++){ 
				if(i==columnNoToAdd-1){
					Listcell listcell=new Listcell("");
					var randomnumber=Math.floor(Math.random()*999999);					
					id = controlIDValue+randomnumber;	
					listcell.value=	id;		
					if(controlType == "button"){
						Button bt = new Button();
						bt.setId(id);
						bt.setLabel(displayValue);
						bt.addEventListener("onClick", new EventListener(){
								public void onEvent(Event event)
								throws Exception {
								}
						});	
						listcell.appendChild(bt);	
						listcell.setParent((Listitem)obj);
						((Listitem)obj).setParent(controlObject);
						return (Object) bt;
					}
					if(controlType== "textbox"){
						Textbox txtbox = new Textbox();
						txtbox.setWidth("100px");
						txtbox.setHeight("20px");						
						txtbox.setId(id);
						listcell.appendChild(txtbox);	
						listcell.setParent((Listitem)obj);
						((Listitem)obj).setParent(controlObject);
						return (Object) txtbox;
					}
				}
			}
		}
	}
	return (Object) bt;
}

public List getGridDynamicControlValue(Object controlObject,String objectName){
	String values = "";
	ArrayList dynamicContValuesList=new ArrayList();
	List obj_child = ((Listbox)controlObject).getChildren();
	String id = "";
	for(int j = 1; j< obj_child.toArray().length ; j++){
		Object obj = obj_child.toArray()[j];
		List lst=((Listitem)obj).getChildren();
		for(int i=0;i<lst.size();i++){ 
			Listcell lc=(Listcell)lst.get(i);
			if(objectName =="txtBox"){
				if(lc.value.contains(objectName)){
					List txt=lc.getChildren();
					Object txtBox  = (Object)txt.get(0);					
					if(values == ""){
						values=txtBox.text;
					}else{
							values=values+ ","+txtBox.text;							
					}
				}else{
					if(lc.value!= ""){
						if(values == ""){
							values=lc.value;
						}else{
								values=values+ ","+lc.value;
						}						
					}					
				}
			}else{
				if(lc.value!= ""){
					if(values == ""){
						values=lc.value;
					}else{
							values=values+ ","+lc.value;
					}						
				}	
			}
		}
		dynamicContValuesList.add(values);
		values = "";
	}
	return dynamicContValuesList;
}

public String getDateDiff(String toDateStr, String fromDateStr){
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
	Date fromDate = (Date)dateFormat.parse(fromDateStr);
	Date toDate = (Date)dateFormat.parse(toDateStr);
	Calendar fromCal = Calendar.getInstance();
	fromCal.setTime(fromDate);
	Calendar toCal = Calendar.getInstance();
	toCal.setTime(toDate);
	long fromMs = fromCal.getTimeInMillis();
	long toMs = toCal.getTimeInMillis();
	long diffMs = toMs - fromMs;
	long daysDiff = (diffMs / (24 * 60 * 60 * 1000)) + 1;
	String dateDiff = Long.toString(daysDiff);
	return dateDiff;
}

public String convertQueryDataToString(String component, String methodName, String componentName, String pageName){	
	String queryData= invokeComponent(component,methodName,componentName,pageName);	
	boolean queryDataCheck = equalsTest(queryData,"true");	
	String queryDataValues = "";
	if(queryDataCheck){
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}		
		if(listValue.size() > 0) {		
			String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);		
			for(i = 0; i < allValues.length; i++){
				for(j = 0; j < allValues[i].length; j++){
					if(queryDataValues!= ""){
						queryDataValues = queryDataValues + ","+ allValues[i][j];
					}else{
						queryDataValues = allValues[i][j];
					}
				}	
			}
		}	
	}
	return queryDataValues;
}

public String getDateString(Object controlId, String dateFormatStr){
	String dateString = null;
	if(controlId != null){
		Date currentDate = ((Datebox)controlId).getValue();
		if(currentDate != null){
			dateString = (new SimpleDateFormat(dateFormatStr).format(currentDate));
		}
	}
	return dateString;
}

public String getDateString(Object controlId)
{
	if(controlId!=null)
	{
		Date currentDate=((Datebox)controlId).getValue();
		String dateString = null;
		if(currentDate !=null)
		dateString=(new SimpleDateFormat("dd-MM-yyyy").format(currentDate));
		return dateString;
	}
	return null;
}

public String getQueryDataToString(String queryValues,int id){	
	String queryValue = "";
	if(queryValues!= ""){
		var queryValuesArr = queryValues.split(",");
		queryValue =(String) queryValuesArr[id];	
	}	
	return queryValue;
}

public  Date convertStringToDate(String dateStr){
	Date dt=null;
	if(dateStr!=null){	
		String date=dateStr +" "+"00:00";
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		dt=dateFormat.parse(date);
		return dt;
	}
	return dt;
}


/*public String fileLocationPath()
{
	String none="";
	org.zkoss.util.media.Media media = org.zkoss.zhtml.Fileupload.get();
	if (media != null) 
	{
		File f=new File(media.getName());
		InputStream inputStream= media.getStreamData();
		OutputStream out=new FileOutputStream(f);
		byte[] buf=new byte[1024];
		int len;
		while((len=inputStream.read(buf))>0)
		out.write(buf,0,len);
		out.close();
		out.flush();
		inputStream.close();
		String filepath = f.getAbsolutePath();
		f.deleteOnExit();
		return filepath;
	}
	else
	{
		System.out.println("media null");
	}
	return none;
}*/

private void showUniqueComboData(IHICData hicData,Object comboObj)
{
	
    
	List values=null;
	
	if(hicData==null)
	{
		if(returnHicData ==null)
		{
				return;
		}
		else
		{
				hicData = returnHicData;
		}
	}
	comboObj.setText("");
	comboObj.getItems().clear();
	IData dataUnit = hicData.getData();
	List listValue = null;
	if(dataUnit.getQueryData().getListData()!=null)
	{
		listValue = dataUnit.getQueryData().getListData();
		Set uniqueValues=new HashSet(listValue);
		alert(" the set is "+uniqueValues);
		Iterator it=uniqueValues.iterator();
		while(it.hasNext())
		{
			String unqValues= it.next();
			values=new ArrayList();
			values.add(unqValues);
			alert("the unique values are"+unqValues);
		}
	}
	else
		return;
	String[][] allValues = dataUnit.getQueryData().iterateListData(values);	
	if(allValues==null)
			return;
	comboTable.put(comboObj.getId(),listValue);
	uiLibraryUtil.showComboData(allValues,comboObj);
}

private void showUniqueComboData(String comboid)
{
    Object rootFormValue = self.getRoot();
	Object elementObj = rootFormValue.getVariable(comboid,true);
	if(elementObj !=null)
	{
		showUniqueComboData(null,elementObj);	
	}
	
}

void message(String value, String titleStr){
	Messagebox.setTemplate("/templates/OMMessagebox.zul"); 
 	Messagebox.show(value, titleStr, Messagebox.OK, Messagebox.INFORMATION);
}

void message(String value){
	Messagebox.setTemplate("/templates/OMMessagebox.zul"); 
 	Messagebox.show(value, "SunPharma", Messagebox.OK, Messagebox.INFORMATION);
}
void setListId(String list)
{
listId = list;
}

void setDisplayLabelId(String label)
{
displayLabelPaging = label;
}

public String getDisplayLabelId()
{

	return displayLabelPaging;
}
public String getListId()
{

	return listId;
}


void setPagingId(String paging)
{
pagingId = paging;
}

public String getPagingId()
{
	return pagingId;
}


//Changes done for implementing paging done by pra on 27May2009
void setTotalSize()
{
	if(returnHicData ==null)
	{
		return;
	}
	else
	{			
		IData dataUnit = returnHicData.getData();
		if(dataUnit.getQueryData().getListData()!=null)
		{
		totalSize=dataUnit.getQueryData().getListData().size();
		}
	}
}

public int getTotalSize()
{
	return totalSize;
}


public void updateSession(String id, String value)
{
	session.setAttribute(id,value);
}

public String getSessionData(String rowId)
{
	String idValue = session.getAttribute(rowId);
	// session.setAttribute(rowId,null);
	return idValue;
}

//file upload 14 Oct 2010
public String fileLocationPath()
{
	String none="";
	org.zkoss.util.media.Media media = org.zkoss.zhtml.Fileupload.get(true);
	if (media != null) 
	{
		File f=new File(media.getName());
		InputStream inputStream= media.getStreamData();
		OutputStream out=new FileOutputStream(f);
		byte[] buf=new byte[1024];
		int len;
		while((len=inputStream.read(buf))>0)
		out.write(buf,0,len);
		out.close();
		inputStream.close();
		String filepath = f.getAbsolutePath();
		return filepath;
	}
	else
	{
		System.out.println("media null");
	}
	return none;
}

public void showData(String[] txtField)
{
	Object rootFormValue = self.getRoot();
	String[][] allValues =null;
	int j=0;
	LinkedHashMap displayValues;
	if(null != returnHicData)
	{
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return;
		allValues = dataUnit.getQueryData().iterateListData(listValue);
		if(allValues==null)
			return;
		uiLibraryUtil.showData(allValues,formValues,rootFormValue,txtField,comboTable);
		updateSession("updatedMsg",updatedMsg);
	}


}

public void showDataForKeyValue(Object idArray)
{
    IHICData localHicData = HICDataController.getHICData(""+session.hashCode());
	Hashtable formValueTable = localHicData.getData().getFormPattern().getFormValues();
	uiLibraryUtil.showKeyValueData((String[])idArray, formValueTable, self.getRoot(), comboTable);
}

public void setFormValuesDataWhileOpening(String fieldName, String fieldValue, String fieldType)
{
	if (fieldValue != null)
	{
		formValues.put(fieldName, fieldValue);
		if (fieldType.equals("textbox")) textId.add(fieldName);
	}
}
void setDatapatternId(String dataId)
{
	dataPatternId = dataId;
}
void setFormPatternId(String formId)
{
	formPatternId = formId;
	validationValue=true;
}

void move(String formID)
{
	formID = formID+".zul";
	Executions.sendRedirect(formID);
}
void printWindow()
{
	//System.out.println("inside the print method ");
	Clients.print();
}
public String getComboBoxValue(String comboBox){
	String comboSelectedVal = null;
	Component rootFormValue = self.getRoot();
	Combobox comboObj = (Combobox)rootFormValue.getVariable(comboBox, true);
	if(comboObj == null){
		comboObj = getComponentIfInPlaceHolder(comboBox);
	}
	if(comboObj != null){
		Comboitem selectedComboRow = comboObj.getSelectedItem();
		if(selectedComboRow != null){
			comboSelectedVal = selectedComboRow.getLabel();
		}
	}
	return comboSelectedVal;
}
/*public String getComboBoxValue(String comboBox)
{
	String comboSelectedVal = null;
	Object rootFormValue = self.getRoot();
	Object comboObj = rootFormValue.getVariable(comboBox,true);
	if(comboObj == null)return null;
	Comboitem selectedComboRow = comboObj.getSelectedItem();
	if(selectedComboRow == null)return null;
	String rowValue = selectedComboRow.getLabel();
	return rowValue;
}*/
//Added null check.done by pra on 15 june2009	
void setDateBoxId()
{
    String date="";
    if(self.value!=null)
	{
	 date= (new SimpleDateFormat("yyyy-MM-dd").format(self.value));
	checkFormValue(self.id,date);
	}
	formValues.put(self.id,date);
}
public String getDate()
{
	Date fullDate = self.value;
	String date = null;
	if(fullDate !=null)
		date =(new SimpleDateFormat("yyyy-MM-dd").format(fullDate));
	return date;
}
void setTimeId()
{
	 String time = String.valueOf(event.value)+":00";
	  Time timeFormate=Time.valueOf(time);
	 //This line is commented and its implemenation will done later.done by pra on 24-june-2009
	  //checkFormValue(self.id,timeFormate);
	 formValues.put(self.id,timeFormate);
}

void setRadioSelected(Object radioID)
	{

		self.setSelectedItem(radioID);
		checkFormValue(self.id,self.selectedItem.label);
		formValues.put(self.id,self.selectedItem.value);
 	}
void setRadioGroupId()
	{
		checkFormValue(self.id,self.selectedItem.label);
		if(self.selectedItem.value != null && !self.selectedItem.value.equals("")){
			formValues.put(self.id,self.selectedItem.value);
		}
		else{
			formValues.put(self.id,self.selectedItem.label);
		}
 	}
void setCheckboxId()
	{
		checkFormValue(self.id,self.label);
		String value = String.valueOf(self.isChecked());
		formValues.put(self.id,value);
	}


void setCheckboxValue()
	{
		String value = String.valueOf(self.isChecked());
		formValues.put(self.id,value);
	}
void setTextboxId()
	{
		checkFormValue(self.id,self.value);
		formValues.put(self.id,self.value);
		textId.add(self.id);
	}

void setListItemId(String listBox)
{
	String listSelectedVal = null;
	Object rootFormValue = self.getRoot();
	Object listObj = rootFormValue.getVariable(listBox,true);
	Listitem selectedListRow = listObj.getSelectedItem();
	if (selectedListRow.getId().indexOf("_") != -1)
	{
		int idSeperatorPos = selectedListRow.getId().indexOf("_");
		listSelectedVal = (String)selectedListRow.getId().substring(0,idSeperatorPos);
	}
	else
	{
		listSelectedVal = (String)selectedListRow.getId();
	}
	listSelectedValue = listSelectedVal;
	formValues.put(self.id,listSelectedValue);
}

void refresh(String formID)
{
	Executions.getCurrent().sendRedirect(formID+".zul");
}

void setFocus(String controlID){
	Component rootFormValue = self.getRoot();
	Component controlObj = rootFormValue.getFellowIfAny(controlID, true);
	if(controlObj == null){
		controlObj = getComponentIfInPlaceHolder(controlID);
	}
	if(controlObj != null){
		controlObj.setFocus(true);
	}
}
/*void setFocus(String controlID)
{
	Object rootFormValue = self.getRoot();
	Object controlObj = rootFormValue.getVariable(controlID,true);
	controlObj.focus();
	
}*/
 void update(var formId) {
    Clients.submitForm(formId);
  }

private String getComboItemId(String comboBox)
{
	String comboSelectedVal = null;
  	Object rootFormValue = self.getRoot();
	Object comboObj = rootFormValue.getVariable(comboBox,true);
   	if(null == comboSelectedValue)
   	{
	  	Comboitem selectedComboRow = comboObj.getSelectedItem();
	  	int idSeperatorPos = selectedComboRow.getId().indexOf("_");
	 	comboSelectedVal = (String)selectedComboRow.getId().substring(0,idSeperatorPos);
	   	comboSelectedValue =comboSelectedVal;
	    formValues.put(self.id,comboSelectedValue);
	   	return comboSelectedVal;
	}
}
public String getRowData(int index)
{
	getRowData(self,index);
}
//added this method to get selected row based on the index and grid
public String getRowData(Object gridObject,int index)
{
    List cell=null;
	String output="";
	Set selectedItems = gridObject.getSelectedItems();
	if(selectedItems!=null && selectedItems.size()>0)
	{
	for(Iterator itr = selectedItems.iterator();itr.hasNext();)
	{
		Listitem listNode = (Listitem) itr.next();
		cell =listNode.getChildren();
	}
	for(Iterator itr=cell.iterator(); itr.hasNext();)
	{
		Listcell lc=(Listcell)itr.next();
		if(lc.getColumnIndex()==index)
		{
			output=lc.value;
			//output = getRowItemData(lc);
		}
	}
	return output;
	}
	return null;
}


public void setCurrentDate(Object controlId)
	{
		Date currentDate=new Date();
		String dateString = null;
		if(currentDate !=null)
		dateString=(new SimpleDateFormat("dd-MM-yyyy").format(currentDate));
		String strdate=dateString +" "+"00:00";
		SimpleDateFormat simpleDate=new SimpleDateFormat("yyyy-MM-dd hh:mm");             
		Date date=null;
		try 
		{
			if(controlId instanceof Datebox)
			{
				//date = simpleDate.parse(strdate);
				((Datebox)controlId).setValue(currentDate);
			}
			else if(controlId instanceof Textbox)
			{
				controlId.setText(dateString);
			}
			else if(controlId instanceof Combobox)
			{
				controlId.setText(strdate);
			}
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
	}



private String getComboItemId1(String comboBox)
{
	String comboSelectedVal = null;
	Object rootFormValue = self.getRoot();
	Object comboObj = rootFormValue.getVariable(comboBox,true);
	if(comboObj ==null)return null;
	Comboitem selectedComboRow = comboObj.getSelectedItem(); 
	//Added null check by pra on 25 may 2009
	if(selectedComboRow!=null)
	{
		if (selectedComboRow.getId().indexOf("_") != -1)
		{
			int idSeperatorPos = selectedComboRow.getId().indexOf("_");
			comboSelectedVal = (String)selectedComboRow.getId().substring(0,idSeperatorPos);
		}
		else
		{
			comboSelectedVal = (String)selectedComboRow.getId();
		}
		comboSelectedValue = comboSelectedVal;
		checkFormValue(self.id,comboSelectedValue);
		formValues.put(self.id,comboSelectedValue);
		comboSelectedValue=null;
	}
	return comboSelectedVal;
}
String invokeComponent(String componentId ,String method ,String classname, String paramList)
{
	String isValidStatus = "true";
	IDataUnitRouter router = RendererComponent.dataUnitRouter;
	Object rootFormValue = self.getRoot();
	/*
	*Before completing any request it will check that session is valid or not.
	*
	*/
	checkSessionTime(method);
	
	/*
	*Following code is used when session would be time out
	*/
	String logOutValue = getSessionData("LogOut");		
	if(logOutValue!=null)
	{
	
		if(logOutValue=="true")
		{
			return "";
		}
		return "";
	}
	
    if(method.equalsIgnoreCase("save")||method.equalsIgnoreCase("addUserFromApplication"))
	{		
		if(clientSideValidation(formPatternId))
		{
		  
		 	validationValue=true;
		}
		else
		{
			validationValue=false;
			isValidStatus = "false";
		}
	}
	if(validationValue)
	{
    UiLibraryCompositeCommand command=new UiLibraryCompositeCommand();
	command.setMethodName(method);
	command.setRouter(router);
    command.setClassname(classname);
	command.setComponentId(componentId);
    command.setDataPatternId(dataPatternId);
    command.setFormPatternId(formPatternId);
    command.setFormValues(formValues);
	command.setRootFormValue(rootFormValue);
	command.setParamList(paramList);
	command.setSession(session);
	command.setComboSelectedValue(comboSelectedValue);
	command.setValidListRequest(validListRequest);
	command.setPagingId(pagingId);
	command.execute();
	returnHicData=command.getHICData();
	if(returnHicData==null)
	{
		isValidStatus = "false";
		return isValidStatus;
	}
	if (returnHicData.getData()!=null) 
    {
		if ((returnHicData.getData().getStatus()!=null) && ((returnHicData.getData().getStatus().equalsIgnoreCase("error")) || (returnHicData.getData().getStatus().equalsIgnoreCase("false"))) )
		{
			isValidStatus = "false";
			return isValidStatus;
		}
	}
  }
  else
  {
 	  return "false";
  }

	if(method.equalsIgnoreCase("authenticateUserInLDAP")){
		if(returnHicData.getLdapData() != null) {
			return (String)returnHicData.getLdapData().getAttributes().get("UserAuthenticatedInLDAP");
		}
	}else if(method.equalsIgnoreCase("searchInLDAP")){
		if(returnHicData.getLdapData() != null) {
			org.dom4j.Document resultDoc = returnHicData.getLdapData().getLdapDoc();
			if(resultDoc != null){
				return resultDoc.asXML();
			}else{
				return null;
			}
		}
	}
	if(method.equalsIgnoreCase("authenticateUserEx"))
	{
		if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null) 
		{
			if(returnHicData.getData().getStatus().equalsIgnoreCase("invalid"))
			{
				//formValues =new Hashtable();
				return "false";
			}
			else
			{
				loginStatus = true;
				
								
			}
		}
	}
	if(method.equalsIgnoreCase("userDetails") && (paramList.equalsIgnoreCase("ArtworkDraftApproval")))
	{
		if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null) 
		{
			IData data=returnHicData.getData();
			String department=data.getGroupName();
			return department;
		}
	}
	if(method.equalsIgnoreCase("executeList"))
	{
		if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null) 
		{
			IData data=returnHicData.getData();
			IFormPattern formpattern=data.getFormPattern();
			String formPatternId=formpattern.getFormId();
			Hashtable formvalues=new Hashtable();
			formvalues=data.getFormPattern().getFormValues();
			if(formPatternId.equals("changeRequest"))
			{
				formvalues.put("label","");
				formvalues.put("showbox","");
				formvalues.put("packageinsert","");
				formvalues.put("packageoutset","");
				formvalues.put("psbox","");
				formvalues.put("foil","");
				formvalues.put("psfoil","");	
				QueryData querydata=data.getQueryData();
				List linkofFiles=(List) querydata.getListData();
				String[][] iteraterlinkrecords=querydata.iterateListData(linkofFiles);
				Object rootFormValue = self.getRoot();
				for(int i=0;i<iteraterlinkrecords.length;i++)
				{
					if(!(iteraterlinkrecords[i][5].equals("")))
					{
						if(iteraterlinkrecords[i][5].equals("Label"))
						{
							formvalues.put("label",iteraterlinkrecords[i][8]);
							formvalues.put("artworkcodelabel",iteraterlinkrecords[i][11]);
						}
						else if(iteraterlinkrecords[i][5].equals("ShowBox"))
						{
							formvalues.put("showbox",iteraterlinkrecords[i][8]);
							formvalues.put("artworkcodeshowbox",iteraterlinkrecords[i][11]);
						}
						else if(iteraterlinkrecords[i][5].equals("PackageInsert"))
						{
							formvalues.put("packageinsert",iteraterlinkrecords[i][8]);
							formvalues.put("artworkcodepackageinsert",iteraterlinkrecords[i][11]);
						}
						else if(iteraterlinkrecords[i][5].equals("PackageOutsert"))
						{
							formvalues.put("packageoutset",iteraterlinkrecords[i][8]);
							formvalues.put("artworkcodepackageoutset",iteraterlinkrecords[i][11]);
						}
						else if(iteraterlinkrecords[i][5].equals("PsBox"))
						{
							formvalues.put("psbox",iteraterlinkrecords[i][8]);
							formvalues.put("artworkcodepsbox",iteraterlinkrecords[i][11]);
						}
						else if(iteraterlinkrecords[i][5].equals("FOIL"))
						{
							formvalues.put("foil",iteraterlinkrecords[i][8]);
							formvalues.put("artworkcodefoil",iteraterlinkrecords[i][11]);
						}
						else if(iteraterlinkrecords[i][5].equals("PsFOIL"))
						{
							formvalues.put("psfoil",iteraterlinkrecords[i][8]);
							formvalues.put("artworkcodepsfoil",iteraterlinkrecords[i][11]);
						}
					}
				}
			}
			if(formPatternId.equals("ArtworkDraftUpload"))
			{
				String forexecution=formValues.get("forexecution");
				if(forexecution!=null)
				{
					if(forexecution.equalsIgnoreCase("true"))
					{
						QueryData querydata=data.getQueryData();
						List artworkCodeList=(List)querydata.getListData();
						String[][] iteraterartworkcode=querydata.iterateListData(artworkCodeList);
						formValues.put("size",iteraterartworkcode.length);
						formValues.put("artworkCode",iteraterartworkcode[iteraterartworkcode.length-1][0]);
						formValues.put("productName",iteraterartworkcode[iteraterartworkcode.length-1][1]);
					}
				}
				String getLinkNotFromSession=formValues.get("getLinkNotFromSession");
				if(getLinkNotFromSession!=null)
				{
					if(getLinkNotFromSession.equalsIgnoreCase("true"))
					{
						QueryData querydata=data.getQueryData();
						List linkPDFList=(List)querydata.getListData();
						String[][] iteraterLinkPDF=querydata.iterateListData(linkPDFList);
						if(!(linkPDFList.size()==0))
						{
							if(null==iteraterLinkPDF[iteraterLinkPDF.length-1][10] || null==iteraterLinkPDF[iteraterLinkPDF.length-1][11])
							{
								formValues.put("link","");
								formValues.put("linkofCS4","");
							}
							else
							{
							
							formValues.put("link",iteraterLinkPDF[iteraterLinkPDF.length-1][10]);
							formValues.put("linkofCS4",iteraterLinkPDF[iteraterLinkPDF.length-1][11]);
							}	
						}
						
					}
				}	
			}
			if(formPatternId.equals("ArtworkDraftUploadAET"))
			{
				String aetworkpdfcs4=formValues.get("aetworkpdfcs4");		
				boolean aetworkpdfcs4result=equalsTest(aetworkpdfcs4,"aetworkpdfcs4");
				if(aetworkpdfcs4result)
				{
						QueryData querydata=data.getQueryData();
						List linkPDFList=(List)querydata.getListData();
						String[][] iteraterLinkPDF=querydata.iterateListData(linkPDFList);
						if(null==iteraterLinkPDF[iteraterLinkPDF.length-1][5] || null==iteraterLinkPDF[iteraterLinkPDF.length-1][6])
						{
							formValues.put("link","");
							formValues.put("linkofCS4","");
						}
						else
						{
							formValues.put("link",iteraterLinkPDF[iteraterLinkPDF.length-1][5]);
							formValues.put("linkofCS4",iteraterLinkPDF[iteraterLinkPDF.length-1][6]);
						}	
				}
			}
			
		}
	}
	if(method.equalsIgnoreCase("save") && paramList.equalsIgnoreCase("Artworkrequisition"))
	{
		if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null ) 
	    {
	             String message= returnHicData.getUniqueID();
	             return message;
	             
	    }
	}
	if(method.equalsIgnoreCase("validatingRefNo") && paramList.equalsIgnoreCase("Artworkrequisition"))
	{
		if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null ) 
	    {
	        IData data=returnHicData.getData();
			IFormPattern formpattern=data.getFormPattern();
			String formPatternId=formpattern.getFormId();
			Hashtable formvalues=new Hashtable();
			formvalues=data.getFormPattern().getFormValues();
			
	    }
		
	}
    if(method.equalsIgnoreCase("sendandrecieve"))
    {
	      if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null ) 
	      {
	            String message= returnHicData.getData().getStatus();
	            String msg = "The return message is:"+message;
	            message(msg);
	            return message;
	       }
  	}
	if(method.equalsIgnoreCase("invokeClientCall"))
	{
		webMessage = (String) returnHicData.getMetaData().getCommonObject();
		return webMessage;
	}
	sessionUpdate(returnHicData);
	return isValidStatus;
}
//added argument as formdesginer not allowing method without argument.
void displayErrorMessageFromComponent(String id)
{
	if ((returnHicData.getData() != null) && (returnHicData.getData().getReturnMessage() != null))
	{
		message( returnHicData.getData().getReturnMessage() );
	}
}

void sessionUpdate(IHICData returnData)
	{
		String userkeyId = returnData.getData().getUserId();
		String[] keyArray;
		if(userkeyId!=null)
		{
			keyArray = userkeyId.trim().split("-");
		}
		if(session.getAttribute("userId")== null || (session.getAttribute("userId")!=returnData.getData().getUserId()))
		{
			//session.setMaxInactiveInterval(600);
			//Devices.setTimeoutURI("ajax", "/zul/Logout.zul");
			
			if(keyArray!=null)
			{
				session.setAttribute("userId",keyArray[0]);
				if(keyArray.length>1)
				{
					session.setAttribute("loginKey",keyArray[1]);
				}
			}
			Set usp = returnData.getData().getUserPatterns();
				if(usp!=null)
				{ 
				
				        List rights=null;
					String userpatterids = "";
					Iterator usrpt = usp.iterator();
					while(usrpt.hasNext())
					{
						IUserPattern pt = (IUserPattern)usrpt.next();
						if(userpatterids.equals(""))
						{
							userpatterids = pt.getUserPatternId();
						    if(pt.getRoles()!=null && pt.getRoles().getRights()!=null)
							{
							 rights=pt.getRoles().getRights();
							 session.setAttribute("rights",rights);
							 }
							
						}
						else
						{
							userpatterids = userpatterids+"##"+pt.getUserPatternId();
							if(pt.getRoles()!=null && pt.getRoles().getRights()!=null)
							{
							 rights=pt.getRoles().getRights();
							 session.setAttribute("rights",rights);
							 }
						}
					}
					session.setAttribute("userPatterns",userpatterids);
					
				}
	
		}
		if(session.getAttribute("EIBUNID")== null || (session.getAttribute("EIBUNID")!=returnData.getUniqueID()))
		{
			session.setAttribute("EIBUNID",returnData.getUniqueID());
		}
	}
void authenticateUserExernally(String userFieldId, String pwdFieldId)
{
 	HICRouter router = RendererComponent.router;
	IDataUnit dataUnit = new DataUnit();
	dataUnit.setMethodName("authenticateUserEx");
	Hashtable reqData = new Hashtable();
	reqData.put("userName",formValues.get(userFieldId));
	reqData.put("password",formValues.get(pwdFieldId));
	dataUnit.setFormValues(reqData);
	IHICData data1 = router.routeToModeler(dataUnit);
}

/*
*showComboData method changed, now it receive parameter double array and combobox object obly
changes by wasim, 20-May-2009
*
*/
private void showComboData(IHICData hicData,Object comboObj)
{
	if(hicData==null)
	{
		if(returnHicData ==null)
		{
				return;
		}
		else
		{
				hicData = returnHicData;
		}
	}
	comboObj.setText("");
	comboObj.getItems().clear();
	IData dataUnit = hicData.getData();
	List listValue = null;
	Hashtable formValues=new Hashtable();
	formValues=dataUnit.getFormPattern().getFormValues();
	String emailCondition=(String)formValues.get("emailCondition");
	if(dataUnit.getQueryData().getListData()!=null)
	{
		listValue = dataUnit.getQueryData().getListData();
		
	}
	else
		return;
	boolean emailConditionResult=equalsTest(emailCondition,"emailCondition");
	String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
	if(allValues==null)
		return;	
	if(emailConditionResult)	
	{
		String[][] userIds=new String[allValues.length][1];
		String[][] mailIds=new String[allValues.length][1];
		for(int i=0;i<allValues.length;i++)
		{
			userIds[i][0] =allValues[i][0];
			mailIds[i][0] =allValues[i][1];
			formValues.put(userIds[i][0],mailIds[i][0]);
		}
		comboTable.put(comboObj.getId(),mailIds);
		uiLibraryUtil.showComboData(userIds,comboObj);
		formValues.put("emailCondition","");
	}
	else
	{
		comboTable.put(comboObj.getId(),listValue);
		uiLibraryUtil.showComboData(allValues,comboObj);
	}
}





public boolean showListData(IHICData hicData, Object elementObj)
{
		
		Object listValues = session.getAttribute("dbListValue");
		int noOfheader = 0;
		if(elementObj !=null)
			noOfheader = getNoOfHeader(elementObj);
		if(listValues==null)
		{
			if(hicData==null)
			{
				if(returnHicData ==null)
				{
					return false;
				}
				else
				{
					hicData = returnHicData;
				}
			}
			IData dataUnit = hicData.getData();
			List listValue = null;
			if(dataUnit.getQueryData().getListData()!=null)
			{
				listValue = dataUnit.getQueryData().getListData();
			}
			else
				return false;
			String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
			ListModel myModel = null;
			if(allValues==null)
			{
				myModel = new ListModelList();
				elementObj.setModel(myModel);
				elementObj.setItemRenderer(new ItemRendererArray());
				return false;
			}
			else
			{
				myModel = new ListModelList(allValues);
				elementObj.setModel(myModel);
				elementObj.setItemRenderer(new ItemRendererArray());
			}
		}
		else
		{
			String[][] allValues = QueryData.iterateListData(listValues);
			if(allValues!=null)
			{
				allValues = getGridLengthValue(allValues,noOfheader);
				ListModel myModel = new ListModelList(allValues);
				elementObj.setModel(myModel);
				elementObj.setItemRenderer(new ItemRendererArray());
			}
			else
				return false;
		}
		return true;
		
}

void setDataStatus(String status, String formPattern, String dataPattern)
{
	invokeComponent(null, "changeDOStatus", null, status + "_SEP_" + formPattern + "_SEP_" + dataPattern);
}

void processNextWorkflowTool(String formPattern, String dataPattern)
{
	String patientId = null; // getSessionData("rowId");
	String patientMRN = null; // getSessionData("patientmrn");
	String status = getSessionData("currentworkflownodestatus");
	
	invokeDynamicWorkflow(
		"com.oxyentmedical.component.workflowcomponent", 
		"com.oxymedical.component.workflowComponent.WorkflowComponent", 
		"ProcessNextWorkflowTool", status, formPattern, dataPattern, patientId, patientMRN, null, null);
}

void invokeDynamicWorkflow(String status, String formPattern, String dataPattern, String patientId, String patientMRN, String scheduleId, String scheduleWorkArea)
{
	System.out.println("****************************************\n***************" 
		+ status 
		+ "***************\n****************************************");
	invokeDynamicWorkflow(null, null, "changedostatusdynamic", status, formPattern, dataPattern, patientId, patientMRN, scheduleId, scheduleWorkArea);
}

void invokeDynamicWorkflow(String compId, String compClass, String methodName, String status, String formPattern, String dataPattern, String patientId, String patientMRN, String scheduleId, String scheduleWorkArea)
{
	if (dataPattern == null) dataPattern = "_NULL_";
	if (patientMRN == null) patientMRN = "_NULL_";
	if (status == null) status = "_NULL_";
	if (formPattern == null) formPattern = "_NULL_";
	if (patientId == null) patientId = "_NULL_";
	if (scheduleId == null) scheduleId = "_NULL_";
	if (scheduleWorkArea == null) scheduleWorkArea = "_NULL_";
	
	invokeComponent(compId, methodName, compClass, status + "_SEP_" + formPattern + "_SEP_" + dataPattern + "_SEP_" + patientId + "_SEP_" + patientMRN+ "_SEP_" + scheduleId+ "_SEP_" + scheduleWorkArea);
}

private String getCurrentUserPattern()
{
	return session.getAttribute("userPatterns");
}
void clearFormData(String formId)
{
	Object rootFormValue = self.getRoot();
	Object formObj = rootFormValue.getVariable(formId,true);
    uiLibraryUtil.clearFormData(formId,rootFormValue);
	formValues.clear();
}

Object createArray(String arrayType, String arrayValues)
{
	if (arrayType.equals("String"))
	{
		return arrayValues.split(",");
	}
	return null;
}

public boolean clientSideValidation(String formId)
{
	boolean isValid = true;
	Object rootFormValue = self.getRoot();
	Window formObj = rootFormValue.getVariable(formId,true);
	if(formObj == null){
		if(rootFormValue instanceof Window){
			formObj = (Window)rootFormValue; //just incase the window is a pop-up
		}
	}
	//alert("formId: " + formId);
	//alert("rootFormValue: " + rootFormValue);
	//alert("formObj: " + formObj);
	String msg=uiLibraryUtil.clientSideValidation(formObj,formValues );
	
	if(msg != null)
		{
			msg = msg.trim();
		}
		try {
			if(msg !="" && msg.length() !=0)
			{
				//Messagebox.show(msg,title,Messagebox.OK, Messagebox.ERROR);
				message(msg);
				isValid = false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
 	return isValid;
}


public boolean validateDate(String datepicker1,String datepicker2)
{
    boolean isDateValid = false;
	Object rootFormValue = self.getRoot();
	 isDateValid=uiLibraryUtil.validateDate(datepicker1,datepicker2,
			rootFormValue,formValues);
	return isDateValid;
}
public boolean equalsTest(String firstStr,String secondStr)
{
	
	if(firstStr!=null && secondStr!= null)
	{
	      firstStr=firstStr.trim();
	      secondStr=secondStr.trim();
		if (firstStr.equalsIgnoreCase(secondStr))
			return true;
	}
	return false;
}
public boolean selectCurrentOrFutureDate(String datepicker1)
{
	 boolean isDateValid = true;
	 Object rootFormValue = self.getRoot();
	 isDateValid= uiLibraryUtil.selectCurrentOrFutureDate(datepicker1,rootFormValue,formValues);
	 return isDateValid;
}
public showWebServiceMessage(String displayControl)
{
	 Object rootFormValue = self.getRoot();
	 Object displayObject = rootFormValue.getVariable(displayControl,true);
	 if(displayObject instanceof Textbox || displayObject instanceof Label )
	 {
	 displayObject.setValue(webMessage);
	 }
	 else  if(displayObject instanceof Listbox )
	 {
	  // showListData(hicData,displayObject)
	 }
}
boolean checkValueWithOperator(Object val1, Object val2, String checkType)
{
	if (checkType.equals("EQUAL"))
	{
		if(val1!=null && val2!=null)
		{
			if (val1.equals(val2)) return true;
		}
	}
	return false;
}

void showReport(String controlObject, String jasperFile)
{ 	  
	Object listData = session.getAttribute("dbListValue");
	if(listData==null)
	{
	
		if(returnHicData ==null)
		{
			
			message("HIC Return data is null.");
			return;
		}
		
		String configPath=PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME")+"config/";
		jasperFile =configPath+jasperFile;
		IData dataUnit = returnHicData.getData();
		Object rootFormValue = self.getRoot();
		uiLibraryUtil.showJasperReport(controlObject,jasperFile,rootFormValue,dataUnit);
	}
	else
	{
		String configPath=PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME")+"config/";
		jasperFile =configPath+jasperFile;
		Object rootFormValue = self.getRoot();
		uiLibraryUtil.showExcelReport(controlObject,jasperFile,rootFormValue,listData);
		session.setAttribute("dbListValue",null);
	}
}
private void showComboData(String comboid)
{
    Object rootFormValue = self.getRoot();
	Object elementObj = rootFormValue.getVariable(comboid,true);
	if(elementObj !=null)
	{
		showComboData(null,elementObj);	
	}
	
}


boolean displayImageArray(String parentId, int hiddenobjects)
{
	boolean isDisplay = true;
	IHICData hicData = null;
	Object listValues = session.getAttribute("dbListValue");
	if(listValues==null)
	{
		if(hicData==null)
		{
			if(returnHicData ==null)
			{
				return false;
			}
			else
			{
				hicData = returnHicData;
			}
		}
		IData dataUnit = hicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return false;
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		Object rootFormValue = self.getRoot();
		Object displayObject = rootFormValue.getVariable(parentId,true);
		List children = displayObject.getChildren();
		if(children!=null && children.size() > hiddenobjects)
		{
			Object[] charr = children.toArray();
			for(int i = hiddenobjects; i < charr.length; i++)
			{
					displayObject.removeChild(charr[i]);
			}
		}
		if(allValues==null)
			return false;
		for(int i = 0; i < allValues.length; i++)
		{
			byte[] imagedata = Base64.decode(allValues[i][0]);
			AImage imgcontent = new AImage("",imagedata);
			Image img = new Image();
			img.setHeight("150px");
			img.setWidth("200px");
			photoMap.put(img, allValues[i][1]);
			img.addEventListener("onClick",new EventListener()
			{
				public void onEvent(Event event)
				{
					List ch = displayObject.getChildren();
					if(ch!=null && ch.size() > hiddenobjects)
					{
						Object[] arr = ch.toArray();
						for(int i = hiddenobjects; i < arr.length ; i++)
						{
							if(arr[i] instanceof Image )
							{
								arr[i].setHeight("150px");
								arr[i].setWidth("200px");
								arr[i].setStyle("border:2px solid;margin-bottom:3px;margin-right:3px;margin-left:5px;margin-top:3px;");
							}
						}
					}
					Image target = event.getTarget();
					updateSession("imageid", photoMap.get(target));
					target.setHeight("142px");
					target.setWidth("192px");
					target.setStyle("border:6px solid;margin-bottom:3px;margin-right:3px;margin-left:5px;margin-top:3px;");
				}
			});
			img.setContent(imgcontent);
			img.setStyle("border:2px solid;margin-bottom:3px;margin-right:3px;margin-left:5px;margin-top:3px;");
			displayObject.appendChild(img);
		}
		children = displayObject.getChildren();
	}
	return isDisplay;
}

public void displayImageContent(Object displayObject, int x, int y)
{
	
	String value = getDataFromAllValuesVar(x, y);
	try
	{
	if(value==null || value.equals("null"))
	{
		displayObject.setSrc("/zul/img/silhouette.JPG");
	}
	else
	{
		byte[] imagedata = Base64.decode(value);
		AImage imgcontent = new AImage("",imagedata);
		displayObject.setContent(imgcontent);
	}
	}
	catch(Exception e)
	{
	}
}

boolean displayDataInControl(String controlObj)
{
	boolean isDisplay = true;
	Object rootFormValue = self.getRoot();
	Object elementObj = rootFormValue.getVariable(controlObj,true);
	if(elementObj instanceof Grid || elementObj instanceof Listbox )
	{
		isDisplay = showListData(null ,elementObj);	
	}
	else if(elementObj instanceof Label)
	{
		
	}
	return isDisplay;
}


public String uploadFileToolXML() 
{
	String path=null;
	Object media = Fileupload.get();
	if(media!=null)
	{
		String inputStream = media.getStringData();
		String fileName = media.getName();
		File xmlFile = File.createTempFile(fileName, ".xml");
		FileOutputStream out = new FileOutputStream(xmlFile);
		try {
				out.write(inputStream.getBytes());
			} finally  {
					     out.close();
		}
		path=xmlFile.getPath();
		return path;
	}
	return path;
}


public String uploadImage(String parentId) 
{
	String retvalue = null;
	Object rootFormValue = self.getRoot();
	Object displayObject = rootFormValue.getVariable(parentId,true);
	Object media = Fileupload.get();
	if (media instanceof org.zkoss.image.Image)
	{
		byte[] fi = media.getByteData();
		String value=Base64.encode(fi);
		formValues.put(parentId,value);
		retvalue = value;
		displayObject.setContent(media);
	}
	else if (media != null)
	{
	Messagebox.show("Not an image: "+media, "Error",
	Messagebox.OK, Messagebox.ERROR);
	retvalue = null;
	}
	else if(media==null || media=="")
	{
	retvalue = null;
		
	}
	return retvalue;
}


public boolean uploadSignature(String parentId) 
{
	boolean isUploaded = true;
	Object rootFormValue = self.getRoot();
	Object media = Fileupload.get();
	if (media instanceof org.zkoss.image.Image)
	{
		String imagetype = null;
		byte[] fi = media.getByteData();
		String value = HexBin.encode(fi);
		formValues.put("userId",parentId);
		formValues.put("imagedata",value);
		if(media.getFormat()!=null)
		imagetype = media.getFormat();	
		formValues.put("imagetype",imagetype);		
	}
	else if (media != null)
	{
	Messagebox.show("Not an image: "+media, "Error",
	Messagebox.OK, Messagebox.ERROR);
	isUploaded =false;
	}
	else if(media==null || media=="")
	{
	isUploaded =false;
		
	}
	return isUploaded;
}
//Changes done for implementing paging done by pra on 27May2009
public void createPagingEvent(String id)
{  	
	validListRequest=true;
	String[] listInfo=PagingInfo.getListInfo();
	final String componentId= listInfo[0];
	final String method=listInfo[1];
	final String classname= listInfo[2];
	final String condition= listInfo[3];
	final String listName=  listInfo[4];
	Object rootFormValue = self.getRoot();
	Paging pag = rootFormValue.getVariable(pagingId,true);
	String labelId=getDisplayLabelId();
	Object label1=null;
	if(labelId!=null)
	{
	 label1=rootFormValue.getVariable(labelId,true);
	}
	final Object label=label1;
	final Object list = rootFormValue.getVariable(listName,true);
	invokeComponent(componentId,method,classname,condition); 
	setTotalSize();
	pag.setTotalSize(getTotalSize());
	validListRequest=false;
	final int PAGE_SIZE = pag.getPageSize(); 
    if(list.getChildren().size()>PAGE_SIZE)
	{
	String value="[ "+1+" - "+PAGE_SIZE.toString()+" / "+getTotalSize()+" ]";
	if(label!=null)
	label.setValue(value);
	}
	else
	{
	int total=list.getChildren().size()-1;
	if(total>0)
	{
	String value="[ "+1+" - "+total.toString()+" / "+getTotalSize()+" ]";
	if(label!=null)
	label.setValue(value);
	}
    else
    {
    if(label!=null)
    label.setValue("");
    }
	}
	pag.addEventListener("onPaging", new EventListener()
	{
		public void onEvent(Event event)
		{
			PagingEvent pe = (PagingEvent) event;
			int pgno = pe.getActivePage();
			int ofs = pgno * PAGE_SIZE;
			String conditonS=condition+" limit "+ofs+","+PAGE_SIZE;
			invokeComponent(componentId,method,classname,conditonS);
			displayDataInControl(listName);
			int size=list.getChildren().size();
			int total=ofs+size-1;
			if(label!=null)
			{
			String value="[ "+(ofs+1).toString()+" - "+total.toString()+" / "+getTotalSize()+" ]";
			label.setValue(value);
			}
		}
	}); 
}
public String uploadFile(String serverPath) 
{
    String path=null;
	Object media = Fileupload.get(true);
	if(media!=null)
	{
		String contentType=media.getContentType();
		InputStream inputStream=media.getStreamData();
		String fileName=media.getName();
		String folderPath=serverPath;
		path=writeToFile(folderPath,fileName,inputStream);
		return path;
	}
	return path;
}
public String uploadFile(String serverpath, String file) 
{
    String path=null;
	Object media = Fileupload.get(true);
	if(media!=null)
	{
		String contentType=media.getContentType();
		InputStream inputStream=media.getStreamData();
		String fileName=media.getName();
		String newfilename=file+fileName.substring(fileName.lastIndexOf("."));
		String folderPath=serverpath;
		path=writeToFile(folderPath,newfilename,inputStream);
		return path;
	}
	return path;
}

public String uploadTemplate(String temptype)
{
	if(temptype == null || temptype.isEmpty())
	{
		return null;
	}
	String path=null;
	Object media = Fileupload.get();
	if(media!=null)
	{
	InputStream inputStream=media.getStreamData();
    String fileName=media.getName();
	String folderPath=PropertyUtil.setUpProperties("PSG_REPORT_TEMPLATE_DIR");
	path=writeToFile(folderPath+"/"+temptype+"/",fileName,inputStream);
	return path;
	}
	else
		return null;
}		

//this method is added to write file on the server. added by pra  on july 06 2009
public String writeToFile(String folder, String fileName,InputStream inputStream)
{
	File file = new File(folder);
	if(!file.exists())
	{
		file.mkdirs();
	}
    File f=new File(folder+fileName);   
    OutputStream out=new FileOutputStream(f);
    byte[] buf=new byte[1024];
    int len;
    while((len=inputStream.read(buf))>0)
    out.write(buf,0,len);
    out.close();
    inputStream.close();   
    return folder+fileName; 
}

public void downloadFile() 
{    
	Object obj =null;
	if(null != returnHicData)
	{
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return;
		obj=listValue.get(0);
	}
	if(obj!=null)
	{
		String txtValue = (String)obj;
		Filedownload.save(txtValue,"text/plain","test.txt");
	}
}

void createParameterComponents(Object obj)
{
	if(obj !=null)
	{
		obj.setStyle("overflow:auto;");
	}
	Object gridObj = obj.getChildren().get(0);
	Object rowsObj = gridObj.getChildren().get(0);
	IHICData localHicData = HICDataController.getHICData(""+session.hashCode());
	if(localHicData.getData().getReturnedData() != null){
		Hashtable paramTable = (Hashtable)localHicData.getData().getReturnedData();
		Enumeration htEnum = paramTable.keys();
		while(htEnum.hasMoreElements()){
			String key = htEnum.nextElement();
			String value = paramTable.get(key);
			
			// create row, key label and value label objects
			Row row = new Row();
			rowsObj.appendChild(row);
			Label keyLabel = new Label();
			keyLabel.setHeight("18px");
			keyLabel.setValue(key.replaceAll("_", " "));
			row.appendChild(keyLabel);
			
			Label valueLabel = new Label();
			valueLabel.setHeight("18px");
			valueLabel.setValue(value);
			row.appendChild(valueLabel);
		}
	}
}

int counter=1;
public void addNewComponent(String frameId)
{
	String idCounter = getSessionData("IDCOUNTER");
	if(idCounter !=null)
	{
		counter = Integer.parseInt(idCounter);
	}
	
	counter = counter+1;
	Object rootFormValue = self.getRoot();
	Object obj =rootFormValue.getVariable(frameId, true);
	if(obj !=null)
	{
		obj.setStyle("overflow:auto;");
	}
	Grid grid= new Grid();
	Rows rws = new Rows();
	Row row = new Row();
	grid.appendChild(rws);
	rws.appendChild(row);
	grid.setWidth("580px");
	String gridId = "autogrid"+counter;
	grid.setId(gridId);
	String gridStyle = "background-color:#DDD9C3;border:0px";
	grid.setStyle(gridStyle);
	rws.setStyle(gridStyle);
	row.setStyle(gridStyle);
	grid.setFixedLayout(true);
	//grid.setHeight("");
	Hbox hObj = new Hbox();
	String hboxId = "hbox"+counter;
	hObj.setId(hboxId);
	String hstyle = "background-color:#DDD9C3;";
	hObj.setStyle(hstyle);
	hObj.setHeight("33px");
	
	obj.appendChild(grid);
	row.appendChild(hObj);
	
	Imagemap button = new Imagemap();
	//button.setLabel("Remove");
	button.setHeight("10px");
	button.setWidth("25px");
	String remId = "remove"+counter;
	button.setId(remId);
	button.setSrc("../zul/img/delete.jpg");
	button.addEventListener("onClick", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
				    	deleteComponent(self.id);
				    }
        });
	hObj.appendChild(button);
	
	// Logical combobox
	Combobox cbox = new Combobox();
	Comboitem child = new Comboitem();
	child.setLabel("and");
	cbox.appendChild(child);
	Comboitem child = new Comboitem();
	child.setLabel("or");
	cbox.appendChild(child);
	cbox.setWidth("55px");
	cbox.setHeight("14px");
	String logId = "logical"+counter;
	cbox.setId(logId);
	cbox.setReadonly(true);
	hObj.appendChild(cbox);
	addFormValue(logId,"and");
	cbox.addEventListener("onSelect", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
				    	String logValue = getComboBoxValue(logId);
				    	addFormValue(logId,logValue);
				    }
        });
	//Category Combobox
	
	Combobox cbox = new Combobox();
	cbox.setWidth("80px");
	cbox.setHeight("14px");
	String catId = "category"+counter;
	cbox.setId(catId);
	cbox.setReadonly(true);
	hObj.appendChild(cbox);
	invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get qbcategory.Id,qbcategory.categoryName from neiswispicdb.qbcategory conditions qbcategory.deleted:=[0] orderby qbcategory.categoryName");
	showComboData(catId);
	cbox.addEventListener("onSelect", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
				    	String id =getComboItemId1(catId);
invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get qbuserdefinedfield.id,qbuserdefinedfield.UserDefinedFieldName from neiswispicdb.qbcategorydetail,neiswispicdb.qbuserdefinedfield conditions qbcategorydetail.categoryId:="+id+" and qbuserdefinedfield.id:=qbcategorydetail.UserDefinedFieldId orderby qbuserdefinedfield.UserDefinedFieldName");
showComboData(fieldId);
						String catValue = getComboBoxValue(catId);
				    	addFormValue(catId,catValue);
				    }
        });
	
	//Field combobox
	Combobox cbox1 = new Combobox();
	cbox1.setWidth("80px");
	cbox1.setHeight("14px");
	String fieldId = "field"+counter;
	cbox1.setId(fieldId);
	cbox1.setReadonly(true);
	hObj.appendChild(cbox1);
	cbox1.addEventListener("onSelect", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
				    	String fieldValue =getComboBoxValue(fieldId);
				    	addFormValue(fieldId,fieldValue);
				    }
        });
		
	// checkbox control
	Checkbox checkbox = new Checkbox();
	checkbox.setWidth("20px");
	checkbox.setHeight("30px");
	String cboxId = "check"+counter;
	checkbox.setId(cboxId);
	hObj.appendChild(checkbox);
	checkbox.addEventListener("onCheck", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
				    	setCheckboxValue();				    	
				    }
        });
		
	
	//Variable textbox
	
	Textbox txtbox = new Textbox();
	txtbox.setWidth("100px");
	txtbox.setHeight("18px");
	String textId = "variable"+counter;
	txtbox.setId(textId);		
	hObj.appendChild(txtbox);
	txtbox.addEventListener("onChange", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
					
						setTextboxId();
				    }
        });
    createToolTip(textId,"For date, value should be in format yyyy-mm-dd."); 
//Rangeone textbox	
	Textbox txtbox = new Textbox();
	txtbox.setWidth("61px");
	txtbox.setHeight("20px");
	String textId1 = "firstrange"+counter;
	txtbox.setId(textId1);	
	hObj.appendChild(txtbox);
	txtbox.addEventListener("onChange", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
						setTextboxId();
				    }
        });
	
	//Label control
	
	String labelStyle = "font-family:Microsoft Sans Serif;font-style:normal;";	
	Label lab = new Label();
	lab.setValue("to");
	lab.setWidth("20px");
	lab.setHeight("21px");
	String id = "to"+counter;
	lab.setId(id);
	lab.setStyle(labelStyle);	
	hObj.appendChild(lab);
	
	//Range2 textbox
	
	Textbox txtbox1 = new Textbox();
	txtbox1.setWidth("61px");
	txtbox1.setHeight("20px");
	String textId2 = "secondrange"+counter;
	txtbox1.setId(textId2);
	hObj.appendChild(txtbox1);
	txtbox1.addEventListener("onChange", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
						setTextboxId();
				    }
        });
		formValues.put("counter",String.valueOf(counter));
		session.setAttribute("CatFormValue",formValues);
		updateSession("IDCOUNTER",String.valueOf(counter));
				
}

public void deleteComponent(String str)
{
	Object rootFormValue = self.getRoot();
	Object obj =rootFormValue.getVariable(str, true);	
	Object div =rootFormValue.getVariable(obj.getParent().getParent().getParent().getParent().getId(), true);
	obj =rootFormValue.getVariable(div.getParent().getId(), true);
	obj.removeChild(div);
	String numStr = str.substring(str.length()-1);
	formValues.remove("secondrange"+numStr);
	formValues.remove("firstrange"+numStr);
	formValues.remove("variable"+numStr);
	formValues.remove("field"+numStr);
	formValues.remove("logical"+numStr);
	formValues.remove("category"+numStr);
	
		
}

public void readOnly(String formId)
{
	Object rootFormValue = self.getRoot();
   	Window obj = rootFormValue.getVariable(formId,true);
	uiLibraryUtil.readOnlyData(obj);
}
public void editOnly(String formId)
{
    Object rootFormValue = self.getRoot();
    Window obj = rootFormValue.getVariable(formId,true);
	uiLibraryUtil.editOnlyData(obj);
}

public class itemRendererArrayDragAndDrop implements ListitemRenderer
{
	public void render(Listitem li, java.lang.Object data) throws Exception
	{
		String[] _data = (String[])data;
		if(_data!=null)
		{
			for(int i=0;i<_data.length;i++)
			{
				//new Listcell(_data[i]).setParent(li);
				Listcell listcell=new Listcell(_data[i]);
				listcell.value=_data[i];
				li.setDraggable("true");
				li.setDroppable("true");
				li.addEventListener("onDrop", new EventListener(){
				public void onEvent(Event event)
				 throws Exception {
				move(event.dragged);
				}
				});
				listcell.setParent(li);
			}
		}
	}
}

public createPopUp(String formId)
{
    Window popUp = (Window) Executions.createComponents(formId+".zul", null, null);
    popUp.doModal();
}

public createPopUp(String formId, org.zkoss.zk.ui.event.EventListener onCloseEventListener){
	Window popUp = (Window)Executions.createComponents(formId + ".zul", null, null);
	popUp.setClosable(true);
	popUp.addEventListener(Events.ON_CLOSE, onCloseEventListener);
	popUp.doModal();
}

//changed implementation for multiselect , done by pra on 09-june-2009
public String getSelectedListData()
{
	 String output="";
	 Object[] items=self.getSelectedItems().toArray();
	 for(int i=0;i<items.length;i++)
	 {
	Listitem list = items[i];
	output=output+self.id;
	output =output+","+self.getSelectedIndex();
	output = output+","+list.getLabel();
	if(list.getValue()!="")
	{
		output=output+","+list.getValue()+"##";
	}
	}	
	return output;
}

// Changes implemented for Selected Questions by dsi
public void setListBoxValues(String controlId)
{
	Object rootFormValue = self.getRoot();
	Object obj = rootFormValue.getVariable(controlId,true);
	List ListItems = obj.getItems();
	Iterator iter = ListItems.iterator();
	List items = new ArrayList();
	while(iter.hasNext()){
		Listitem value = (Listitem)iter.next();
		System.out.println("label = "+value.getLabel());
		items.add(value.getLabel());
	}
	formValues.put("selectedQuestions", items);
}

// Changes implemented for Selected Questions by dsi
public String getSelectedListBoxValue(String controlId)
{
	Object rootFormValue = self.getRoot();
	Object obj = rootFormValue.getVariable(controlId,true);
	String value = obj.getSelectedItem().getLabel();
	return value;
}

//changed implementation for multiselect , done by pra on 09-june-2009
//method implementation changed  for removing child and adding child by pra	
public void moveValue(String itemValue,String parentControl, String controlId)
{
	Object rootFormValue = self.getRoot();
	Object obj = rootFormValue.getVariable(controlId,true);
	Object parentObj = rootFormValue.getVariable(parentControl,true);
	if(itemValue!=null)
	{
	String []itemValues=itemValue.split("##");
	for(int i=0;i<itemValues.length;i++)
	{
	String value=itemValues[i];
	String [] values= value.split(",");
	boolean present=true;
	String val="";
	String label="";
	String id="";
	String pid="";
	if(values.length==4)
	{ 
	    pid=values[0];
	    id=values[1];
		label=values[2];
		val=values[3];
		
	}
	else if(values.length==3)
	{	pid=values[0];
	 	id=values[1];
		label=values[2];
		
	}
	if(pid.equalsIgnoreCase(parentControl) && !(pid.equalsIgnoreCase(controlId)))
	{	
		
	List list =obj.getChildren();
	Listitem child = new Listitem();
	child.setLabel(label);
	if(val!="")
	{
		child.setValue(val);
	}
	child.setDraggable("true");
	child.setDroppable("true");
	child.addEventListener("onDrop", new EventListener(){
	public void onEvent(Event event)
				 throws Exception {
	move(event.dragged);
				}
		});		
    int rId=Integer.parseInt(id);
	parentObj.removeItemAt(rId);	
	obj.appendChild(child);
	}
	}
	}
	else
	{
	
	 List list=parentObj.getChildren();
	 if(list!=null && list.size()>0)
	 {
	 Listitem child = list.get(0);
	 obj.appendChild(child);
	 parentObj.removeChild(child);
	 }
	 
	}
			
}
//Added method to move all items to other list added by pra
 public void moveAllItems(String parentControl,String controlId)
{ 

    Object rootFormValue = self.getRoot();   	
   	Object controlObj = rootFormValue.getVariable(controlId,true);
	Object parentObj = rootFormValue.getVariable(parentControl,true);
   	List list =parentObj.getChildren();
   	List newList=new ArrayList(list);	   
   	int size=newList.size();
   	for(int i=0;i<size;i++)
   	{
   	Listitem child = (Listitem)newList.get(i);	
   	controlObj.appendChild(child);
   	}
   	 
     
 }
  
public updateRefferences(String id)
{
	Object rootFormValue = self.getRoot();
	Object obj = rootFormValue.getVariable(id,true);
	if(obj.text=="")
	{
	formValues.put(id,"");
	}
}


public void showLoginInfo(String firstLbl)
{
	Object rootFormValue = self.getRoot();
	Object firstObj = rootFormValue.getVariable(firstLbl,true);
	if(firstObj==null)
	{
	return;
	}
	if(returnHicData ==null)
		{
			return;
		}
	IData dataUnit = returnHicData.getData();
	List listValue = null;
	if(dataUnit.getQueryData().getListData()!=null)
	{
		listValue = dataUnit.getQueryData().getListData();
	}
	else
		return;
	String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
	if(allValues==null)
			return;
	//String key = getSessionData("loginKey");
	String user = getSessionData("userId");
	String logDate = null;
	String logTime = null;
	String loginValue = null;
	
	for(int row=0;row<allValues.length;row++)
		{
			String user = allValues[row][1];
						
			if(user.equalsIgnoreCase(user))
			{
				if(row ==(allValues.length-2))
				{
					logDate = allValues[row][2];
					logTime = allValues[row][3];
				}
			}
		}
	
	String date = logDate+" "+logTime;
	
	if(logDate !=null)
	{
		Date date1 = DateUtil.stringToDate(date, "yyyy-MM-dd HH:mm:ss");
		Date toDayDate =new Date();
		String toDate = DateUtil.formatDate(toDayDate, "yyyy-MM-dd HH:mm:ss");
		Date currentDate = DateUtil.stringToDate(toDate, "yyyy-MM-dd HH:mm:ss");
		Long loggedTime = date1.getTime();
		Long currentTime = currentDate.getTime();
		Long diff = currentTime-loggedTime;	
		long diffSeconds = diff / 1000;
	   	long diffMinutes = diff / (60 * 1000);
	   	long diffHours = diff / (60 * 60 * 1000);
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		if(diffDays!=0)
		{
			logTime =diffDays + " days ago";
		}
		else if(diffHours!=0)
		{
			logTime =diffHours + " hours ago";
		}
		else if(diffMinutes!=0)
		{
			logTime =diffMinutes + " minutes ago";
		}
		else 
		{
			logTime =diffSeconds + " Seconds ago";
		}
		
		loginValue = "last account login:"+logTime;
	}
	String ipStr=" at IP :";
	String host=null;
	for(int row=0;row<allValues.length;row++)
	{
		if(row ==(allValues.length-2))
		{
			host = allValues[row][4];
			String logoutValue = allValues[row][5];
			if(logoutValue.equalsIgnoreCase("0"))
			{
				String curr =session.getRemoteHost();
				if(!(host.equalsIgnoreCase(curr)) )
				{
				
					host="this account is active on IP location: "+host+", "+curr;
					loginValue="";
					ipStr="";
				}
				else
				{
					host="this account is active on IP location: "+host;
					loginValue="";
					ipStr="";
				}
			}
		}
	}
	
	if(host!=null)
	{
		ipStr = ipStr + host+"";
	}
	else
	{
		ipStr = ipStr +session.getRemoteHost();
	}
	
	if(loginValue !=null)
	{
	loginValue = loginValue +ipStr;
	}
	else
	{
		loginValue="";
	}
	firstObj.setValue(loginValue);
}


//These method are for the UserAdmin Component for Adding New UserPattern and
//for hiding and displaying the fields in the AddUser Form.
//added by pra on 22-May-2009
public void createPrivilegesList(String id)
{
	Object rootFormValue = self.getRoot();
	Object obj = rootFormValue.getVariable(id,true);
	List list =obj.getChildren();
	LinkedHashMap privileges = new LinkedHashMap();
	if(list!=null && list.size()>0)
	{
		Iterator itr = list.iterator();
		while(itr.hasNext())
		{
			Listitem child = (Listitem)itr.next();
			privileges.put(child.getLabel(),child.getValue());
		}
	}
	updateSessionList("privilege",privileges);
}

public void createFieldsList(String id)
{
	Object rootFormValue = self.getRoot();
	Object obj = rootFormValue.getVariable(id,true);
	List list =obj.getChildren();
	LinkedHashMap fields = new LinkedHashMap();
	if(list!=null && list.size()>0)
	{
		Iterator itr = list.iterator();
		while(itr.hasNext())
		{
			Listitem child = (Listitem)itr.next();
			fields.put(child.getLabel(),child.getValue());
		}
	}
	updateSessionList("fields",fields);
}

public void setDefaultValue(LinkedHashMap fieldValues ,String id)
{
  Object rootFormValue = self.getRoot();
  Object obj = rootFormValue.getVariable(id,true);
  if(fieldValues!=null && fieldValues.size()>0)
  {

	  Object[] keys=fieldValues.keySet().toArray();
	  for(int i=0;i<keys.length;i++)
	  {
		   String value=(String)fieldValues.get(keys[i]);
		   Listitem li = new Listitem();
		   li.setLabel((String)keys[i]);
		   li.setValue(value);
		   obj.appendChild(li);	   
	   }
  }
}
public  createFormValuesForUserPattern(String defaultFormPattern)
 {
	
	 List fields=createListValues(session.getAttribute("fields"));
	 List privilege=createListValues(session.getAttribute("privilege"));
	 formValues.put("userPatternId",getSessionData("userPatternId"));
	 formValues.put("defaultFormPattern",defaultFormPattern);
	 formValues.put("privileges",privilege);
	 formValues.put("fields",fields); 
	 formValues.put("companyId",getSessionData("companyId")); 
	 updateSessionList("fields",null);
	 updateSessionList("privilege",null);
 }
public LinkedList createListValues(LinkedHashMap fieldValues)
{
	LinkedList result = new LinkedList();
	if(fieldValues!=null && fieldValues.size()>0 )
	{
		Object[] keys=fieldValues.keySet().toArray();
		for(int i=0;i<keys.length;i++)
		{
			String value=(String)fieldValues.get(keys[i]);
			if(value!=null)
			{
				result.add(value);
			}
		}
	}
	return result;
}
 
public LinkedList createListKeys(LinkedHashMap fieldValues)
{
	LinkedList result = new LinkedList();
	if(fieldValues!=null && fieldValues.size()>0 )
	{
		Object[] keys=fieldValues.keySet().toArray();
		for(int i=0;i<keys.length;i++)
		{
			result.add(keys[i]);
		}
	}
	return result;
}

public setLabelValue(String id)
{
	Object rootFormValue = self.getRoot();
	Object obj = rootFormValue.getVariable(id,true);
	String label=getSessionData("userPatternId");
	if(label!=null)
	{
		obj.setValue(label);
		formValues.put(id,label);
	}
	
}
 public updateSessionList(String id,Object value)
 {
	session.setAttribute(id,value);
 }
public void displayFieldDataInControl(String id)
{

	if(returnHicData ==null)
	{
		return;
	}
	else
	{
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		
		if (dataUnit.getQueryData() == null) return;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return;
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		Object rootFormValue = self.getRoot();
		Object obj = rootFormValue.getVariable(id,true);
		List child = obj.getChildren();
		List childData = new ArrayList(child);
		Iterator itr = childData.iterator();
		while(itr.hasNext())
		{
			Listitem listitem =  (Listitem)itr.next();
			obj.removeChild(listitem);
		}
				
		if(allValues !=null)
		{
			for(int i=0;i<allValues.length;i++)
			{  
				String name=allValues[i][1];
				String value=allValues[i][0];
				Listitem li = new Listitem();
				li.setLabel(name);
				li.setValue(value);
				obj.appendChild(li);
			}
		}
	}
}

public hideControls(String[] arg)
{
   Object rootFormValue = self.getRoot();
   for(int i=0;i<arg.length;i++)
   {
   Object obj = rootFormValue.getVariable(arg[i],true);
   obj.setVisible(false);
   }
}

public showButtonControls(String[] arg)
{
   Object rootFormValue = self.getRoot();
   for(int i=0;i<arg.length;i++)
   {
   Object obj = rootFormValue.getVariable(arg[i],true);
   obj.setVisible(true);
   }
}

public void showControls(String id,String[] arg)
{
	if(returnHicData ==null)
	{
		return;
	}
	else
	{
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return;
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		Object rootFormValue = self.getRoot();		
		int topMargin=183;
		if(allValues==null)
		{
			for(int i=0;i<arg.length;i++)
			{
				String top=topMargin+"px";
				Object obj = rootFormValue.getVariable(arg[i],true);
				obj.setLeft("80px");
				obj.setTop(top);
				obj.setVisible(true);
				topMargin=topMargin+30;
			}
		}
		else
		{		
			for(int i=0;i<allValues.length;i++)
			{
				String id=allValues[i][0];
				String top=topMargin+"px";
				Object obj = rootFormValue.getVariable(id,true);
				obj.setLeft("80px");
				obj.setTop(top);
				obj.setVisible(true);
				topMargin=topMargin+30;
			}
		}
	}
}
// Above methods are for UseraAdmin Component.................



public void addFormValue(String id, Object value)
{

	if(id!=null && value!=null)
	{
		formValues.put(id,value);	
	}

}

/*
public String createSearchQuery(String lastNameId,String firstNameId,String dobId, String mrnId)
{
	String firstNameValue = formValues.get(firstNameId);
	String lastNameValue = formValues.get(lastNameId);
	String dobIdValue = formValues.get(dobId);
	String mrnValue = formValues.get(mrnId);
	return createQueryForSearch(lastNameValue, firstNameValue, dobIdValue,mrnValue);
}

public String createQueryForSearch(String lastNameValue, String firstNameValue, String dobIdValue, String mrnValue)
{
	String conditionStr ="";
	formValues = new Hashtable();
	if(firstNameValue!=null)
	{
		conditionStr = conditionStr +"Patient.firstname:=["+firstNameValue+"] and ";
	}
	if(lastNameValue!=null)
	{
		conditionStr = conditionStr +"Patient.lastname:=[" +lastNameValue+"] and ";
	}
	if(dobIdValue!=null)
	{
		conditionStr = conditionStr + "patient.Dateofbirth:=["+dobIdValue+"] and ";
	}
	if(mrnValue!=null)
	{
		conditionStr = conditionStr + "patient.MedicalRecordNumber:=["+mrnValue+"] and ";
	}
	conditionStr = conditionStr + "patient.deleted:=[0]";
	conditionStr = conditionStr.trim();
	return conditionStr;
}
*/

//HardCoding table Name and field name removed. Wasim Khan, 4-Aug-2009.
public String createQueryForSearch(String fieldValue,String fieldName,String TableName,String conditionStr)
{
	if(fieldValue!=null)
	{
		conditionStr = conditionStr +TableName+"."+fieldName+":=["+fieldValue+"] and ";
	}
	return conditionStr;
}

//Method implemented makeVisible for showing controls added by pra on 30-May-2009	
List viewElements;
public void makeVisible(String id)
{ 
	viewElements= new ArrayList();
	List rights=session.getAttribute("rights");
	Object rootFormValue = self.getRoot(); 
	if(rights!=null)
	{
		uiLibraryUtil.displayControlInForm(rootFormValue,rights,viewElements);
	}
}
	  
	  
  
  public String logOut(String pageId)
{
	String userId = getSessionData("userId");
	String retValue = invokeComponent("com.oxymedical.component.useradmin","logoutUser","com.oxymedical.component.useradmin.UserAdminComponent",userId);
	updateSession("OLDTime",null);
	updateSession("login", null);
	setDataStatus("DEFAULT", pageId, null);
	updateSession("userId",null);
	session.invalidate();
	return retValue;

}

public boolean checkSessionTime(String method)
{
	boolean isSession = true;
	if(method.equalsIgnoreCase("logoutUser"))
	{
		return isSession;
	}
	String oldTime = getSessionData("OLDTime");
	String userId = getSessionData("userId");
	
	if(userId==null)
	{
		return isSession;
	}
	if(oldTime!=null)
	{
		Date oldDate = DateUtil.stringToDate(oldTime, "yyyy-MM-dd HH:mm:ss");
		Date newDate = new Date();
		Long oldTime = oldDate.getTime();
		Long currentTime = newDate.getTime();
		Long timeDiff = currentTime-oldTime;
		long totalMinutes = timeDiff / (60 * 1000);
		String time=PropertyUtil.setUpProperties("SESSION_TIME");
		long sessionTime=session.getMaxInactiveInterval();
		if(time!=null)
		{
		
			sessionTime = Integer.parseInt(time);
		}
		if(totalMinutes>sessionTime)
		{
			isSession=false;
			message("Sorry your session has timed out. Please sign in again.");		
			logOut("login");
			updateSession("LogOut","true");
			session.invalidate();
			return false;
		}
	}
	Date newDate = new Date();	
	String toDate = DateUtil.formatDate(newDate, "yyyy-MM-dd HH:mm:ss");
	updateSession("OLDTime",toDate);
	return isSession;

}

public void checkFormValue(String id,String value)
{
	String oldValue = formValues.get(id);
	if(oldValue !=null)
	{
		if(oldValue.equalsIgnoreCase(value))
		{
			updatedMsg = "Patient Information has not been updated";
		}
		else
		{
			updatedMsg = null;
		}
	}
	else
	{
		updatedMsg = null;	
	}
	updateSessionList("formValues",formValues);
	updateSession("updatedMsg",updatedMsg);
}

public String getUpdatedMessage()
{

	return updatedMsg;
}
public String setUpdatedMessage(String mesg)
{
	 updatedMsg=mesg;
}
 
public void paginalSetter(String gridName)
{
	Object rootFormValue = self.getRoot();
	Object gridObj = rootFormValue.getVariable(gridName, true);
	try
	{
		gridObj.setPaginal(self);
	}
	catch (Exception e)
	{
		System.out.println("!!ERROR!! Either '" + gridName + "' is not a Listbox / Grid or '" + self.id + "' is not a Paging component");
	}
}
//Added method to set height by pra on 04-june-2009
public void setHeight(String controlId,String height)
{
	Object rootFormValue = self.getRoot();
	Object controlObj = rootFormValue.getVariable(controlId, true);
	controlObj.setHeight(height+"px");
}
//Used to break the String according to pattern and return the rquired string .added by pra
public String splitString(String value,String pattern)
{  
	if (value == null) return null;
	String[] values= StringUtil.splitString(value,pattern);
	if(values.length>1)
	{
		value=values[0];
	}	
	return value;
}

public boolean messageWithQuestion(String message, String titleStr){
	Messagebox.setTemplate("/templates/OMMessagebox.zul");
	if (!(Messagebox.show(message, 
					titleStr, Messagebox.YES | Messagebox.NO,
					"Messagebox.QUESTION") == Messagebox.YES)) {
		return false;				
	}else{ 
		return true;
	}
}

//added method to display question done by pra on 5-june-2009
public boolean messageWithQuestion(String message){
	Messagebox.setTemplate("/templates/OMMessagebox.zul");
	if (!(Messagebox.show(message, 
					"SunPharma", Messagebox.YES | Messagebox.NO,
					"Messagebox.QUESTION") == Messagebox.YES)) {
		return false;				
	}else{ 
		return true;
	}
}

/*
*This method check loging value is blank or not.
if blank show worning msg without sending request to dbcomponent
*/

public boolean checkLoginBlankValue(String txtOne,String txtTwo)
{
	String valueOne = formValues.get(txtOne);
	String valueTwo = formValues.get(txtTwo);
	if(valueOne == null || valueTwo ==null)
	{
		formValues.remove(txtOne);
		formValues.remove(txtTwo);
		return false;
	}
	return true;

}

/*
Following methods are used for disable and enable control

*/
public void setDisable(String[] control)
{
	Object rootFormValue = self.getRoot();
	
	for(int counter=0; counter<control.length;counter++)
	{
		String controlId = control[counter];
		Object controlObj = rootFormValue.getVariable(controlId, true);
		controlObj.setDisabled(true);
		
	}


}


public void setEnable(String[] control)
{
	Object rootFormValue = self.getRoot();
	
	for(int counter=0; counter<control.length;counter++)
	{
		String controlId = control[counter];
		Object controlObj = rootFormValue.getVariable(controlId, true);
		controlObj.setDisabled(false);
		
	}


}
//Method added by pra to check if userId is avaible on 09-june-2009
public boolean isRecordExist()
{
boolean retVal=false;

	if(returnHicData ==null)
	{
		return false;
	}
	else
	{
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return false;
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		if(allValues!=null && allValues.length>0)
		{
		 retVal=true;
		 return retVal;
		}
	}
	return retVal;
}
//Method added by pra to set backround color in case of error avaible on 09-june-2009
public void setErrorColor(String[] control)
{
	Object rootFormValue = self.getRoot();
	String errorBGColor ="background:#fad8d8";
	for(int counter=0; counter<control.length;counter++)
	{
		String controlId = control[counter];
		Object controlObj = rootFormValue.getVariable(controlId, true);
		controlObj.setStyle(errorBGColor);		
	}

}


//this method is used to remove childs from the list if they are present in the list from which the data is moved from one list to second list
//added by pra 11 june 2009
public removeChilds(String parentControl,String controlId)
{  

   Object rootFormValue = self.getRoot();   	
   Object controlObj = rootFormValue.getVariable(controlId,true);
 	Object parentObj = rootFormValue.getVariable(parentControl,true);
   	List list =parentObj.getChildren();
   	List controlList =controlObj.getChildren();
   	for(int i=0;i<list.size();i++)
   	{
   	Listitem parentChild = (Listitem)list.get(i);
   	String pLabel=parentChild.getLabel();
   	  for(int j=0;j<controlList.size();j++)
   	  {
   	     Listitem controlChild = (Listitem)controlList.get(j);
   	     String cLabel=controlChild.getLabel();
   	     if(pLabel.equalsIgnoreCase(cLabel))
	     {
	     controlObj.removeChild(controlChild);
	     }				
	 
   	  }
   	}
 }
 
 
 //This method is used to setUpdatedvalue in case if user is on the same form and formvalues is not changed.
//Added by pra on 15june2009
 public void setUpdatedValue(String formId)
 {
   updatedMsg =formId;
 }

 //this added for the search of user. 
 //Added by pra on 15june2009
public String createUserQueryForSearch(String lastNameValue, String firstNameValue, String userIdValue)
{
	String conditionStr ="";
	formValues = new Hashtable();
	if(userIdValue!=null)
	{
		conditionStr = conditionStr + "user_.userId:=["+userIdValue+"] and ";
	}
	
	if(lastNameValue!=null)
	{
		conditionStr = conditionStr +"contact_.lastName:=["+lastNameValue+"] and ";
	}
	if(firstNameValue!=null)
	{
		conditionStr = conditionStr +"contact_.firstname:=[" +firstNameValue+"] and ";
	}
	
	conditionStr = conditionStr + "user_.userId:=contact_.userId and user_.deleted:=[0]";
	conditionStr = conditionStr.trim();
	return conditionStr;
}
//this method is used to get ParentForm in case of placeholder .added by pra on 16-june-2009
public String getParentFormPattern(String formId)
{
	 Object rootFormValue = self.getRoot();
	 Object controlObj = rootFormValue.getVariable(formId,true);
	 String parentId=controlObj.getAttribute("formpattern");
	 return parentId;
}
//this method is used to  add formValues  .added by pra on 16-june-2009
public void addFormValues(Hashtable values)
{ 
	formValues=values;
}
//this is method used to display logo question added by pra on 16-june-2009
public String messageLogoQuestion(String message)
{
	int value=(Messagebox.show(message,
						"NOLIS", Messagebox.YES |Messagebox.NO|Messagebox.CANCEL,
						"Messagebox.QUESTION")) ;
	if(value==cancel)
	{								
		return "cancel";
	}
	else if(value==yes)
	{
	return "yes";
	}
	else 
	return "no";	

}		


public void setListDataInSession(String id)
{
		if(returnHicData ==null)
		{
			return;
		}
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return;
			
		if(listValue!=null)
		{
			updateSessionList(id,listValue);
		}
}


public void clearQueryValue(String variable)
 {
	Object rootFormValue = self.getRoot();    
	String countStr = formValues.get("counter");
	if(countStr!=null)
	{
		int value = Integer.parseInt(countStr);
		String id = variable.substring(0, variable.length()-1);;
		
		for(int counter=1;counter<=value;counter++)
		{
			Object controlObj = rootFormValue.getVariable(id+counter,true);
			if(controlObj instanceof Checkbox)
			{
				controlObj.setChecked(false);
				formValues.remove(controlObj.getId());
			}
			else if(controlObj !=null)
			{
				controlObj.setText("");
				formValues.remove(controlObj.getId());
			}
		}
	}
	else
	{
		Object controlObj = rootFormValue.getVariable(variable,true);
		if(controlObj instanceof Checkbox)
			{
				controlObj.setChecked(false);
				formValues.remove(controlObj.getId());
			}
			else if(controlObj!=null)
			{
				controlObj.setText("");
				formValues.remove(controlObj.getId());
			}
	}
 }
 
 public void clearRangeValue(String range)
 {
	Object rootFormValue = self.getRoot();    
	String countStr = formValues.get("counter");
	if(countStr!=null)
	{
		int value = Integer.parseInt(countStr);
		String range1 = "firstrange";
		String range2 = "secondrange";
		for(int counter=1;counter<=value;counter++)
		{
			Object controlObj = rootFormValue.getVariable(range1+counter,true);
			if(controlObj !=null)
			{
				controlObj.setText("");
				formValues.remove(controlObj.getId());
			}
			controlObj = rootFormValue.getVariable(range2+counter,true);
			if(controlObj !=null)
			{
				controlObj.setText("");
				formValues.remove(controlObj.getId());
			}
		}
	}
	else
	{
		Object controlObj = rootFormValue.getVariable("firstrange1",true);
		controlObj.setText("");
		controlObj = rootFormValue.getVariable("secondrange1",true);
		controlObj.setText("");
	}
 }
 
 // This method used to removed window popup
 // id would be popupwindow name.
 public void detachWindow(String id)
{
	Object rootFormValue = self.getRoot();    
	Object controlObj = rootFormValue.getVariable(id,true);
	if(controlObj !=null)
	{
	controlObj.detach();
	}

}
 
 public void setAllTo(String variable)
 {
	Object rootFormValue = self.getRoot();    
	String catName = getSessionData("CategoryName");
	String fieldName = getSessionData("FieldName");
	String variableValue = getSessionData("VariableValue");
	String logValue = getSessionData("LogicalName");
	String realValue = null;
	if(catName!=null)
	{
		realValue = catName;
	}
	else if(fieldName!=null)
	{
		realValue = fieldName;
	}
	else if(variableValue!=null)
	{
		realValue = variableValue;
	}
	else if(logValue!=null)
	{
		realValue = logValue;
	}
	
	Hashtable newformValues = session.getAttribute("CatFormValue");
	String countStr = null;
	if(newformValues!=null)
	{
		countStr = newformValues.get("counter");
			formValues = newformValues;
	}
	else
	{
		countStr = formValues.get("counter");
			
	}
	if(countStr!=null)
	{
		int value = Integer.parseInt(countStr);
		String id = variable.substring(0, variable.length()-1);;
		
		for(int counter=1;counter<=value;counter++)
		{
			Object controlObj = rootFormValue.getVariable(id+counter,true);
				if(controlObj instanceof Checkbox)
			{
				controlObj.setChecked(true);
			}
			else if(realValue!=null && controlObj!=null)
			{
				controlObj.setText(realValue);
				formValues.put(id+counter,realValue);
			}
		}
	}
	else
	{
		Object controlObj = rootFormValue.getVariable(variable,true);
		
		if(controlObj instanceof Checkbox)
			{
				controlObj.setChecked(true);
			}
			else if(realValue!=null && controlObj!=null)
			{
				controlObj.setText(realValue);
				formValues.put(id+counter,realValue);
			}
	}
	updateSession("FieldName",null);
	updateSession("CategoryName",null);
	updateSession("VariableValue",null);
	updateSession("LogicalName",null);
	session.setAttribute("CatFormValue",formValues);
 }
 
 
 public Object getListValue(String str)
 {
 return session.getAttribute(str);
 }
 
 // this method return list selected data id.
 
  public String getSelectedListId()
  {
	 Listitem list = self.getSelectedItem();
	 String id = list.getValue();
	 return id;
  }
  
   public String getSelectedListLabel()
  {
	 Listitem list = self.getSelectedItem();
	 String label = list.getLabel();
	 return label;
  }
  
  
  public void setRangeTo(String range)
 {
	Object rootFormValue = self.getRoot();    
	String range1 = getSessionData("range1");
	String range2 = getSessionData("range2");
	String countStr = null;
	countStr = formValues.get("counter");
	if(countStr!=null)
	{
		int value = Integer.parseInt(countStr);
		String id1 = "firstrange";
		String id2 = "secondrange";
		for(int counter=1;counter<=value;counter++)
		{
			Object controlObj = rootFormValue.getVariable(id1+counter,true);
			if(controlObj !=null  && range1!=null && range1!="")
			{
				controlObj.setText(range1);
				formValues.put(id1+counter,range1);
			}
			controlObj = rootFormValue.getVariable(id2+counter,true);
			if(controlObj !=null  && range2!=null && range2!="")
			{
				controlObj.setText(range2);
				formValues.put(id2+counter,range2);
			}
		}
	}
	else
	{
		Object controlObj = rootFormValue.getVariable("firstrange1",true);
		if(controlObj !=null && range1!=null && range1!="")
		{
			controlObj.setText(range1);
			formValues.put("firstrange1",range1);
			
		}
		controlObj = rootFormValue.getVariable("secondrange1",true);
		if(controlObj !=null && range2!=null && range2!="")
		{
			controlObj.setText(range2);
			formValues.put("secondrange1",range2);
		}
	}
	updateSession("range1",null);
	updateSession("range2",null);
	session.setAttribute("CatFormValue",formValues);
 }
 
 
//this method is used to set Data on the schedular added by pra on june 24-2009
 
  public void setDataInCalendars(Object obj)
 {
 

 if(returnHicData ==null)
		{
			return;
		}
		else
	{

		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return;
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);		
		SimpleCalendarModel cm = new SimpleCalendarModel();
        String[]  colors={"#D96666","#0D7813","#B373B3"};
		int count=0;
		
		if(allValues !=null)
		{
			for(int i=0;i<allValues.length;i++)
			{   
				if(count>2)
				{
				count=0;
				}
			    SimpleCalendarEvent sce = new SimpleCalendarEvent();
				String startDate=allValues[i][0];
				String endDate=allValues[i][1];
				String startTime=allValues[i][2];
				String endTime=allValues[i][3];
				String studyType=null;
				if(startDate!=null && endDate!=null && startTime!=null && endTime!=null )
				{
				startDate=startDate.replaceAll("-","/");
				endDate=endDate.replaceAll("-","/");	
				String[]  stimes=startTime.split(":");
				String[]  etimes=endTime.split(":");
				startDate=startDate+" "+stimes[0]+":"+stimes[1];
				endDate=endDate+" "+etimes[0]+":"+etimes[1];
				SimpleDateFormat dataSDF = new SimpleDateFormat("yyyy/MM/dd HH:mm");	
				sce.setBeginDate(dataSDF.parse(startDate));
				sce.setEndDate(dataSDF.parse(endDate));	
				studyType=allValues[i][7];	
				if(studyType==null)
				{
				studyType="";
				}			
				sce.setContent("Name: "+allValues[i][6]+" "+allValues[i][5]+"\n"+"Studytype: "+studyType);
				sce.setTitle("Appointment Id: "+allValues[i][4]);
				String color=colors[count];
				sce.setHeaderColor(color);
	            sce.setContentColor(color);
				cm.add(sce);
				count++;
				}			
			}
		}
		
		obj.setModel(cm);
	}
}
 //This method is for schedular to retrive information of id.Added by pra on june-24-2009
public String onEditEventCalendar()
{
CalendarsEvent evt = (CalendarsEvent) event;
CalendarEvent ce = evt.getCalendarEvent();
String title=ce.getTitle();
String[] ids=title.split(":");
String id=ids[1];
return id;
}

 
  
  
//This Method used to show saved query details
  
public void showQueryUI(String frameId)
{
		if(returnHicData ==null)
		{
			return false;
		}
		Object rootFormValue = self.getRoot(); 
		//first remove added component
		deleteAllComponent("remove");
		
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return;
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		if(allValues==null)
		{
			return;
		}
		for(int counter=0;counter<allValues.length;counter++)
		{
		if(counter==0)
		{
		showQueryComponent(rootFormValue,allValues[counter][0],allValues[counter][1],allValues[counter][2],allValues[counter][3],allValues[counter][4],allValues[counter][6]);
		}
		else
		{
				addQueryComponent(frameId,allValues[counter][0],allValues[counter][1],allValues[counter][2],allValues[counter][3],allValues[counter][4],allValues[counter][5],allValues[counter][6]);
		}
		}
			
}
  
  //This method show the data into condition component which are added at design time
  
  
 public void showQueryComponent(Object rootFormValue,String categoryValue,String fieldValue,String variableValue,String firstRange,String secondRange,String checkValue)
{ 
	Object controlObj = rootFormValue.getVariable("category1",true);
	if(controlObj!=null && categoryValue!=null)
	{
		controlObj.setText(categoryValue);
		formValues.put("category1",categoryValue);
	}
	controlObj = rootFormValue.getVariable("field1",true);
	if(controlObj!=null && fieldValue!=null)
	{
		String id2 =getComboItemId1("category1");
		invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get qbuserdefinedfield.id,qbuserdefinedfield.UserDefinedFieldName from neiswispicdb.qbcategorydetail,neiswispicdb.qbuserdefinedfield conditions qbcategorydetail.categoryId:="+id2+" and qbuserdefinedfield.id:=qbcategorydetail.UserDefinedFieldId orderby qbuserdefinedfield.UserDefinedFieldName");
		showComboData("field1");
		controlObj.setText(fieldValue);
		formValues.put("field1",fieldValue);
	}
	controlObj = rootFormValue.getVariable("variable1",true);
	if(controlObj!=null && variableValue!=null)
	{
		controlObj.setText(variableValue);
		formValues.put("variable1",variableValue);
	}
	controlObj = rootFormValue.getVariable("firstrange1",true);
	if(controlObj!=null && firstRange!=null)
	{
		controlObj.setText(firstRange);
		formValues.put("firstrange1",firstRange);
	}
	controlObj = rootFormValue.getVariable("secondrange1",true);
	if(controlObj!=null && secondRange!=null)
	{
		controlObj.setText(secondRange);
		formValues.put("secondrange1",secondRange);
	}
	controlObj = rootFormValue.getVariable("check1",true);
	
	if(controlObj!=null && checkValue!=null)
	{
		boolean yes = equalsTest(checkValue,"true");
		controlObj.setChecked(yes);
		formValues.put("check1",checkValue);
	}
  
}
// This method used to display saved query details, which added at run time


public void addQueryComponent(String frameId,String categoryValue,String fieldValue,String variableValue,String firstRange,String secondRange,String logical,String checkValue)
{
	String idCounter = getSessionData("IDCOUNTER");
	if(idCounter !=null)
	{
		counter = Integer.parseInt(idCounter);
	}
	
	counter = counter+1;
	Object rootFormValue = self.getRoot();
	Object obj =rootFormValue.getVariable(frameId, true);
	if(obj !=null)
	{
		obj.setStyle("overflow:auto;");
	}
	
	Grid grid= new Grid();
	Rows rws = new Rows();
	Row row = new Row();
	grid.appendChild(rws);
	rws.appendChild(row);
	grid.setWidth("580px");
	String gridId = "autogrid"+counter;
	grid.setId(gridId);
	String gridStyle = "background-color:#DDD9C3;border:0px";
	grid.setStyle(gridStyle);
	rws.setStyle(gridStyle);
	row.setStyle(gridStyle);
	Hbox hObj = new Hbox();
	String hboxId = "hbox"+counter;
	hObj.setId(hboxId);
	hObj.setHeight("33px");	
	obj.appendChild(grid);
	row.appendChild(hObj);
	
	
	Imagemap button = new Imagemap();
	//button.setLabel("Remove");
	button.setHeight("15px");
	button.setWidth("30px");
	String remId = "remove"+counter;
	button.setId(remId);
	button.setSrc("../zul/img/delete.jpg");
	button.addEventListener("onClick", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
				    	deleteComponent(self.id);
				    }
        });
	
	
	hObj.appendChild(button);
	
	// Logical combobox
	
	
	Combobox cbox = new Combobox();
	Comboitem child = new Comboitem();
	child.setLabel("and");
	cbox.appendChild(child);
	Comboitem child = new Comboitem();
	child.setLabel("or");
	cbox.appendChild(child);
	
	cbox.setWidth("55px");
	cbox.setHeight("14px");
	String logId = "logical"+counter;
	cbox.setId(logId);
	cbox.setReadonly(true);
	
	hObj.appendChild(cbox);
	
	if(logical!=null && logical!="")
	{
		cbox.setText(logical);
		formValues.put(logId,logical);
	}
	
	cbox.addEventListener("onSelect", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
				    	String logValue = getComboBoxValue(logId);
				    	addFormValue(logId,logValue);
				    }
        });
	//Category Combobox
	
	Combobox cbox = new Combobox();
	cbox.setWidth("80px");
	cbox.setHeight("14px");
	String catId = "category"+counter;
	cbox.setId(catId);
	cbox.setReadonly(true);
	hObj.appendChild(cbox);
	invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get qbcategory.Id,qbcategory.categoryName from neiswispicdb.qbcategory conditions qbcategory.deleted:=[0] orderby qbcategory.categoryName");
	showComboData(catId);
	
	if(categoryValue!=null && categoryValue!="")
	{
		cbox.setText(categoryValue);
		formValues.put(catId,categoryValue);
	}
	cbox.addEventListener("onSelect", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
				    	String id =getComboItemId1(catId);
invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get qbuserdefinedfield.id,qbuserdefinedfield.UserDefinedFieldName from neiswispicdb.qbcategorydetail,neiswispicdb.qbuserdefinedfield conditions qbcategorydetail.categoryId:="+id+" and qbuserdefinedfield.id:=qbcategorydetail.UserDefinedFieldId orderby qbuserdefinedfield.UserDefinedFieldName");
showComboData(fieldId);
						String catValue =getComboBoxValue(catId);
				    	addFormValue(catId,catValue);
				    }
        });
	
	//Field combobox
	Combobox cbox1 = new Combobox();
	cbox1.setWidth("80px");
	cbox1.setHeight("14px");
	String fieldId = "field"+counter;
	cbox1.setId(fieldId);
	cbox1.setReadonly(true);
	hObj.appendChild(cbox1);
	if(fieldValue!=null && fieldValue!="")
	{
		String id2 =getComboItemId1(catId);
		invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get qbuserdefinedfield.id,qbuserdefinedfield.UserDefinedFieldName from neiswispicdb.qbcategorydetail,neiswispicdb.qbuserdefinedfield conditions qbcategorydetail.categoryId:="+id2+" and qbuserdefinedfield.id:=qbcategorydetail.UserDefinedFieldId orderby qbuserdefinedfield.UserDefinedFieldName");
		showComboData(fieldId);
		formValues.put(fieldId,fieldValue);
	}
	cbox1.setText(fieldValue);
	cbox1.addEventListener("onSelect", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
				    	String fieldValue =getComboBoxValue(fieldId);
				    	addFormValue(fieldId,fieldValue);
				    }
        });
		
		
	// checkbox control
	Checkbox checkbox = new Checkbox();
	checkbox.setWidth("20px");
	checkbox.setHeight("30px");
	String cboxId = "check"+counter;
	checkbox.setId(cboxId);
	
	hObj.appendChild(checkbox);
	if(checkValue!="" && checkValue!=null)
	{
		boolean yes = equalsTest(checkValue,"true");
		checkbox.setChecked(yes);
		formValues.put(cboxId,checkValue);
	}
	checkbox.addEventListener("onCheck", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
				    	setCheckboxValue();				    	
				    }
        });
			
	
	//Variable textbox
	
	Textbox txtbox = new Textbox();
	txtbox.setWidth("100px");
	txtbox.setHeight("20px");
	String textId = "variable"+counter;
	txtbox.setId(textId);	
	
	if(variableValue!=null && variableValue!="")
	{
		txtbox.setText(variableValue);
		formValues.put(textId,variableValue);
	}
	hObj.appendChild(txtbox);
	txtbox.addEventListener("onChange", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
					
						setTextboxId();
				    }
        });
//Rangeone textbox	
	
	
	
	Textbox txtbox = new Textbox();
	txtbox.setWidth("61px");
	txtbox.setHeight("20px");
	String textId1 = "firstrange"+counter;
	txtbox.setId(textId1);	
	
	if(firstRange!=null && firstRange!="")
	{
	txtbox.setText(firstRange);	
	formValues.put(textId1,firstRange);
	}
	hObj.appendChild(txtbox);
	txtbox.addEventListener("onChange", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
						setTextboxId();
				    }
        });
	
	//Label control
	
	String labelStyle = "font-family:Microsoft Sans Serif;font-style:normal;";	
	Label lab = new Label();
	lab.setValue("to");
	lab.setWidth("20px");
	lab.setHeight("21px");
	String id = "to"+counter;
	lab.setId(id);
	lab.setStyle(labelStyle);	
	hObj.appendChild(lab);
	
	//Range2 textbox
	

	Textbox txtbox1 = new Textbox();
	txtbox1.setWidth("61px");
	txtbox1.setHeight("20px");
	String textId2 = "secondrange"+counter;
	txtbox1.setId(textId2);
	
	if(secondRange!=null && secondRange!="")
	{
		txtbox1.setText(secondRange);
		formValues.put(textId2,secondRange);	
	}
	hObj.appendChild(txtbox1);
	txtbox1.addEventListener("onChange", new EventListener()
		        {
				    public void onEvent(Event event1) throws Exception 
				    {
						setTextboxId();
				    }
        });
		formValues.put("counter",String.valueOf(counter));
		updateSession("IDCOUNTER",String.valueOf(counter));
		session.setAttribute("CatFormValue",formValues);
				
}

//This method delete all condition component which are added after selecting the custom query

public void deleteAllComponent(String str)
{
	Object rootFormValue = self.getRoot();
	String count = formValues.get("counter");
	if(count!=null)
	{
	
		int countValue = Integer.parseInt(count);
		for(int loopCounter=1; loopCounter<=countValue;loopCounter++)
		{
			Object obj =rootFormValue.getVariable(str+loopCounter, true);
			if(obj==null)continue;
			Object div =rootFormValue.getVariable(obj.getParent().getParent().getParent().getParent().getId(), true);
			obj =rootFormValue.getVariable(div.getParent().getId(), true);
			obj.removeChild(div);			
			formValues.remove("secondrange"+loopCounter);
			formValues.remove("firstrange"+loopCounter);
			formValues.remove("variable"+loopCounter);
			formValues.remove("field"+loopCounter);
			formValues.remove("logical"+loopCounter);
			formValues.remove("category"+loopCounter);
			formValues.remove("check"+loopCounter);
			
		}
	}
	Object obj =rootFormValue.getVariable("category1", true);
	if(obj!=null)obj.setText("");
	Object obj =rootFormValue.getVariable("field1", true);
	if(obj!=null)obj.setText("");
	Object obj =rootFormValue.getVariable("variable1", true);
	if(obj!=null)obj.setText("");
	Object obj =rootFormValue.getVariable("firstrange1", true);
	if(obj!=null)obj.setText("");
	Object obj =rootFormValue.getVariable("secondrange1", true);
	if(obj!=null)obj.setText("");
	Object obj =rootFormValue.getVariable("check1", true);
	if(obj!=null)obj.setChecked(false);
	formValues.remove("secondrange1");
	formValues.remove("firstrange1");
	formValues.remove("variable1");
	formValues.remove("field1");
	formValues.remove("logical1");
	formValues.remove("category1");
	formValues.remove("check1");
		
}  

//this method is used to compare the times.added by pra on june 26 2009.
public boolean validateTime(String startId,String endId,String startDateId,String endDateId)
{
	Object rootFormValue = self.getRoot();
	Object startTime  = rootFormValue.getVariable(startId,true);
	Object endTime  = rootFormValue.getVariable(endId,true);
	Object startDate  = rootFormValue.getVariable(startDateId,true);
	Object endDate = rootFormValue.getVariable(endDateId,true);
	Date sDate=startDate.getValue();
	Date eDate=endDate.getValue();
	String sTimeValue=formValues.get(startId).toString();
	String eTimeValue=formValues.get(endId).toString();
	int result=DateUtil.compareDates(sDate,eDate);	
	if((sTimeValue)!=null && (eTimeValue)!=null)
	{
	String[] startTimeValue=(sTimeValue).split(":");
	String[] endTimeValue=(eTimeValue).split(":");	
	String sValue=startTimeValue[0]+"."+startTimeValue[1];
	String eValue=endTimeValue[0]+"."+endTimeValue[1];
	double sTime= Double.valueOf(sValue).doubleValue();
	double eTime=Double.valueOf(eValue).doubleValue();	
	if(eTime>sTime && result==0)
	{
		if((eTime-sTime)>.29)
		{
			return true;
		}
		else
		{
			message("End time should be 30 minutes greater than start time.");
			return false;
		}
	}
	else if(result<0 )
	{
		return true;
	}		
	else
	{
		message("End time should be greater than start time.");
		return false;
	}
	}
	else
	{
		message("Either start time or end time is blank.");
		return false;
	}
	
	return false;
}
  

public showCSVFile(String id)
{
	List list = session.getAttribute("dbListValue");
	if(list == null || list.size()<=0)
	{
		message("No record found.");
		return;
	}
	String filePath = PropertyUtil.setUpProperties("CSV_DIR")+"/Report.csv";
	File file = new File(filePath);
	Filedownload.save(file,"text/plain");
}



public void createConditionList(String str)
{
	List categoryList=new ArrayList();
	List fieldList=new ArrayList();
	List variableList=new ArrayList();
	List firstrangeList=new ArrayList();
	List secondrangeList=new ArrayList();
	List logicalList=new ArrayList();
	
	Object rootFormValue = self.getRoot();    
	String countStr = null;
	countStr = formValues.get("counter");
	if(countStr!=null)
	{
		int value = Integer.parseInt(countStr);
		for(int counter=1;counter<=value;counter++)
		{
			String categoryValue = formValues.get("category"+counter);	
			if(categoryValue!=null)
			{
				categoryList.add(categoryValue);
			}
		}
		formValues.put("combobox6",categoryList);
		
		for(int counter=1;counter<=value;counter++)
		{
			String fieldValue = formValues.get("field"+counter);	
			if(fieldValue!=null)
			{
				fieldList.add(fieldValue);
			}
		}
		formValues.put("combobox7",fieldList);
		for(int counter=1;counter<=value;counter++)
		{
			String variableValue = formValues.get("variable"+counter);
			if(variableValue !=null)
			{
				variableList.add(variableValue);
			}
		}
		formValues.put("textbox38",variableList);
		
		for(int counter=1;counter<=value;counter++)
		{
			String range1 = formValues.get("firstrange"+counter);
			if(range1 !=null)
			{
				firstrangeList.add(range1);
			}
		}
		formValues.put("textbox39",firstrangeList);
		for(int counter=1;counter<=value;counter++)
		{	
			String range2 = formValues.get("secondrange"+counter);
			if(range2 !=null)
			{
				secondrangeList.add(range2);
			}
		}
		formValues.put("textbox40",secondrangeList);
		for(int counter=1;counter<=value;counter++)
		{
			String logicalValue = formValues.get("logical"+counter);
			if(logicalValue !=null)
			{
				logicalList.add(logicalValue);
			}
		}
		formValues.put("log1",logicalList);
		
	}
	else
	{
			String categoryValue = formValues.get("category1");
			if(categoryValue !=null)
			{
				categoryList.add(categoryValue);
				formValues.put("combobox6",categoryList);
			}
			String fieldValue = formValues.get("field1");
			if(fieldValue !=null)
			{
				fieldList.add(fieldValue);
				formValues.put("combobox7",fieldList);
			}
			String variableValue = formValues.get("variable1");
			if(variableValue !=null)
			{
				variableList.add(variableValue);
				formValues.put("textbox38",variableList);
			}
			String range1 = formValues.get("firstrange1");
			if(range1 !=null)
			{
				firstrangeList.add(range1);
				formValues.put("textbox39",firstrangeList);
			}
			String range2 = formValues.get("secondrange1");
			if(range2 !=null)
			{
				secondrangeList.add(range2);
				formValues.put("textbox40",secondrangeList);
			}
			String logicalValue = formValues.get("logical1");
			if(logicalValue !=null)
			{
				logicalList.add(logicalValue);
				formValues.put("log1",logicalList);
			}
			
	}
	
}


public boolean searchInputCheck(String firstName,String lastname,String mrn,String dob)
{
	if(firstName !=null && firstName !="")return true;
	if(lastname!=null && lastname !="")return true;
	if(mrn!=null && mrn !="")return true;	
	if(dob!=null && dob !="")return true;	
	return false;
}

//this method is used to return unqiue number .added by pra on 29-june-2009
public int generateUniqueNumber()
{
Random generator = new Random();
int r = Math.abs(generator.nextInt()) % 1000000;
return r;
}


// This method used to save custom query in databases.


public void saveConditionList(String str)
{
	List categoryList=new ArrayList();
	List fieldList=new ArrayList();
	List variableList=new ArrayList();
	List firstrangeList=new ArrayList();
	List secondrangeList=new ArrayList();
	List logicalList=new ArrayList();
	
	Object rootFormValue = self.getRoot();    
	String countStr = null;
	countStr = formValues.get("counter");
	String id = formValues.get("grid40");
	if(countStr!=null)
	{
		int value = Integer.parseInt(countStr);
		
		
		if(id!=null)
		{
invokeComponent("dbComponent","updateRecord","com.oxymedical.component.db.DBComponent","delete from neiswispicdb.customquerydetail conditions customquerydetail.queryId:="+id);
}
		boolean flag = false;
		boolean isVariable = true;
		formValues.remove("combobox6");	
		formValues.remove("combobox7");
		formValues.remove("log1");
		formValues.remove("textbox40");
		formValues.remove("textbox39");
		formValues.remove("textbox38");
		formValues.remove("checkbox1");
		for(int counter=1;counter<=value;counter++)
		{
			String categoryValue = formValues.get("category"+counter);
			if(categoryValue !=null)
			{
				formValues.put("combobox6",categoryValue);
				flag = true;
			}
			String fieldValue = formValues.get("field"+counter);
			if(fieldValue !=null)
			{
				
				formValues.put("combobox7",fieldValue);
				flag = true;
			}
			String variableValue = formValues.get("variable"+counter);
			if(variableValue !=null)
			{
			variableValue =variableValue.trim();
			
				if(variableValue.length() >0)
				{
					formValues.put("textbox38",variableValue);
					flag = true;
					isVariable = false;
				}
			}
			if(isVariable)
			{
				String range1 = formValues.get("firstrange"+counter);
				if(range1 !=null)
				{
					
					formValues.put("textbox39",range1);
					flag = true;
				}
				String range2 = formValues.get("secondrange"+counter);
				if(range2 !=null)
				{
					
					formValues.put("textbox40",range2);
					flag = true;
				}
			}
			String logicalValue = formValues.get("logical"+counter);
			if(logicalValue !=null)
			{
				formValues.put("log1",logicalValue);
				
			}
			String checkBoxValue = formValues.get("check"+counter);
			if(checkBoxValue !=null)
			{
				formValues.put("checkbox1",checkBoxValue);
				
			}
			
			if(flag)
			{	
			invokeComponent("dbComponent","save","com.oxymedical.component.db.DBComponent","QueryBuilder");
			formValues.remove("combobox6");	
			formValues.remove("combobox7");
			formValues.remove("log1");
			formValues.remove("textbox40");
			formValues.remove("textbox39");
			formValues.remove("textbox38");
			formValues.remove("checkbox1");
			}
			isVariable = true;
			flag = false;
		}
		
	}
	else
	{
			boolean flag = false;
			boolean isVariable = true;
			formValues.remove("combobox6");	
			formValues.remove("combobox7");
			formValues.remove("log1");
			formValues.remove("textbox40");
			formValues.remove("textbox39");
			formValues.remove("textbox38");
			formValues.remove("checkbox1");
			if(id!=null)
			{
				invokeComponent("dbComponent","updateRecord","com.oxymedical.component.db.DBComponent","delete from neiswispicdb.customquerydetail conditions  customquerydetail.queryId:="+id);
			}
			String categoryValue = formValues.get("category1");
			if(categoryValue !=null)
			{
				formValues.put("combobox6",categoryValue);
			}
			String fieldValue = formValues.get("field1");
			if(fieldValue !=null)
			{
				flag = true;
				formValues.put("combobox7",fieldValue);
			}
			String variableValue = formValues.get("variable1");
			if(variableValue !=null)
			{
				
				formValues.put("textbox38",variableValue);
				flag = true;
				isVariable = false;
			}
			if(isVariable)
			{
				String range1 = formValues.get("firstrange1");
				if(range1 !=null)
				{
					
					formValues.put("textbox39",range1);
				}
				String range2 = formValues.get("secondrange1");
				if(range2 !=null)
				{
					
					formValues.put("textbox40",range2);
				}
			}
			String logicalValue = formValues.get("logical1");
			if(logicalValue !=null)
			{
				formValues.put("log1",logicalValue);
			}
			String checkBoxValue = formValues.get("check"+counter);
			if(checkBoxValue !=null)
			{
				formValues.put("checkbox1",checkBoxValue);
				
			}
			if(flag)
			{
				invokeComponent("dbComponent","save","com.oxymedical.component.db.DBComponent","QueryBuilder");
				formValues.remove("combobox6");	
				formValues.remove("combobox7");
				formValues.remove("log1");
				formValues.remove("textbox40");
				formValues.remove("textbox39");
				formValues.remove("textbox38");
				formValues.remove("checkbox1");
				
			}
			
	}
	
}



public void clearFieldData(String[] data)
{
	Object rootFormValue = self.getRoot();
	
	for(int counter=0;counter<data.length;counter++)
	{
		Object obj =rootFormValue.getVariable(data[counter], true);
		if(obj!=null)
		{
			obj.setText("");
		}
		formValues.remove(data[counter]);		
	}
}	
//method added to retrive the file path done by pra
public String getReportFilePath()
{
	String reportFilePath=null;
	if(returnHicData ==null)
			{
				return;
			}
			else
	{

		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return null;
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		if(allValues!=null)
		{
		reportFilePath=allValues[0][0];
		}
	}
	return reportFilePath;
}

// This method returns the current date as String.
public String getCurrentDate(String dateFormat)
{
	Date toDayDate =new Date();
	String toDate = DateUtil.formatDate(toDayDate, dateFormat);
	return toDate;
}

public Date convertIntoDate(String dob,String dateId)
{
	String dateStr=dob +" "+"00:00";
	Date date = DateUtil.stringToDate(dateStr,"yyyy-MM-dd hh:mm");
	Object rootFormValue = self.getRoot();
	Object obj =rootFormValue.getVariable(dateId, true);
	if(obj!=null)
	{
		obj.setValue(date);
	}
	return date;
}

//this method remove the all list children
public removeChildFromList(String controlId)
{  
   Object rootFormValue = self.getRoot();   	
   Object controlObj = rootFormValue.getVariable(controlId,true);
   List controlList =controlObj.getChildren();
   List dupList = new ArrayList(controlList);
   for(int j=0;j<dupList.size();j++)
   {
		if(dupList.get(j) instanceof Listitem)
		{
		     Listitem controlChild = (Listitem)dupList.get(j);
		     controlObj.removeChild(controlChild);
		}
  	}
}

//this method is added to get the selected date from the calendar added by pra on july-14-2009
public String getSelectedDate(Date selectedDate,format)
{     
	String selDate = DateUtil.formatDate(selectedDate,format);
	return selDate; 
}
//this method is used to set the date in schdular.added by pra on july-14-2009
public void setCalendarDate(String selectedDate,String formId)
{

	SimpleDateFormat dataSDF = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	Object rootFormValue = Path.getComponent("//"+formId+"//"+formId);
	obj =getSelectedObject(rootFormValue,Calendars.class);
	obj.setCurrentDate(dataSDF.parse(selectedDate));
	Date b = obj.getBeginDate();
	Date e = obj.getEndDate();
	java.text.DateFormat sdfV= DateFormat.getDateInstance();
	sdfV.setTimeZone(obj.getDefaultTimeZone());
	String[] begin=sdfV.format(b).split(",");
	Object label=rootFormValue.getVariable("label", true);
	label.setValue(begin[0] + " - " + sdfV.format(e));
}
//this method is used to get the object of calendar or other required object added by pra on july-14-2009
public Object getSelectedObject(HtmlBasedComponent formObj,Class requiredObject)
{ 
	Object val=null;
	String requiredClass=requiredObject.toString();
	List childElement = formObj.getChildren();
	Iterator iter = childElement.iterator();
	while (iter.hasNext())
	{
		Object value = iter.next();
		String valueClass=value.getClass().toString();
		if(valueClass.equalsIgnoreCase(requiredClass))
		{
			val=value;
			return value;
		}
		else 
		{
			Object values= getSelectedObject(((HtmlBasedComponent) value),requiredObject);
			if(values!=null)
			{
				val=values;
				return val;
			}
		}
	}
return val;
}
LinkedList roomList= new LinkedList();


//This method is added to create the room object.Added by pra on 16 july 2009.
public String addVisitList(String description,String parentId,String formId)
{ 	
	int roomCount=1;
	int topmargin=120;
	int leftmargin=20;
	int rooms=1;
	Object rootFormValue = Path.getComponent("//"+formId+"//"+formId);
	Object parentObj = rootFormValue.getVariable(parentId,true);
	List listRoom=getRoomList(parentObj);
	if(listRoom!=null)
	{
		roomCount=listRoom.size()+1;
		int a=(listRoom.size())/4;
		if(a==0)
		{
			rooms=listRoom.size();
			leftmargin=leftmargin+125*listRoom.size();
		}
		else
		{
			rooms=listRoom.size()%4;
			topmargin=topmargin+120*a;
			leftmargin=leftmargin+125*rooms;
		}
	}
	String top="";
	String left="";
	String idList="";
	idList="Suite"+roomCount;
	String headerId="header"+roomCount;
	top="top:"+topmargin+"px";
	left="left:"+leftmargin+"px";
	Checkbox checkbox = new Checkbox();
	checkbox.setWidth("20px");
	checkbox.setHeight("30px");
	String cboxId = "check"+roomCount;
	checkbox.setId(cboxId);
	
	String leftCheck = "left:"+(leftmargin-20)+"px";
	style = top +UILibraryConstant.STYLE_STRING+leftCheck;
	checkbox.setStyle(style);
	parentObj.appendChild(checkbox);
	Listbox list = new Listbox();
	Listheader header = new Listheader();
	header.setLabel(description);
	header.setId(headerId);
	Listhead head = new Listhead();
	list.setFixedLayout(true);
	list.setHeight("100px");
	list.setWidth("100px");
	list.setId(idList);
	list.setDroppable("true");
	list.setStyle(top +";align:None;background-color:#FFFFFF;margin-top:3px;font:Microsoft Sans Serif;font-style:normal;padding-right:0px;padding-left:0px;padding-top:0px;color:#000000;font-size:8.25pt;margin-bottom:3px;position:absolute;margin-right:3px;margin-left:3px;padding-bottom:0px;visibility:true;"+left);
	leftmargin=leftmargin+125;
	roomCount++;
	Listitem li = new Listitem();
	Listcell listcell=new Listcell("empty");
	listcell.value="empty";								
	listcell.setParent(li);
	header.setParent(head);
	head.setParent(list);
	li.setParent(list);
	list.addEventListener("onDrop", new EventListener(){
	public void onEvent(Event event)
	throws Exception
	{
	move(event.dragged);
	}
	});
	parentObj.appendChild(list);
	return idList;
}
//This method is get the room list.Added by pra on 16 july 2009.
public List getRoomList(Object formObj) 
{

  	List childElement;
	if (formObj != null) 
	{
		childElement = formObj.getChildren();
		Iterator iter = childElement.iterator();
		while (iter.hasNext())
		{
			Object value = iter.next();
			if (value instanceof Listbox) 
			{
			if (((Listbox) value).getId().indexOf("Suite") >= 0)
				roomList.add(value.id);
			}
			else
			{
				roomList = getRoomList(((XulElement) value));
			}
		}
	}
return roomList;
}
//added code to save to get Description of room added by pra on 16 july 2009
public String getDescription(String id)
{
	String desc="";
	String pid="";
	Object rootFormValue = self.getRoot();
	Object formObj = rootFormValue.getVariable(id, true);
	
	if(formObj.getChildren().size()>0)
	{
		Object obj=formObj.getChildren().get(0);
		if(obj.getChildren().size()>0)
		{  
			pid=obj.getChildren().get(0).getId();
			desc=obj.getChildren().get(0).getLabel();
			desc=desc+","+pid;
		}
	}
return  desc;
}

//added code remove the rooms added by pra on 16 july 2009
public void removeRooms(Object parentObj)
{
	List listRoom=getRoomList(parentObj);
	List newList=new ArrayList(listRoom);	
	if (newList!=null && newList.size()>0)
	{
		for(int i=0;i<newList.size();i++)
		{
			String id=newList.get(i);
			String[] ids=id.split("Suite");
			String checkId="check"+ids[1];
			Object formObj = parentObj.getVariable(id, true);
			Object checkformObj = parentObj.getVariable(checkId, true);
			if(formObj!=null)
			{			
			parentObj.removeChild(formObj);
			parentObj.removeChild(checkformObj);
			}
		}
	}
	listRoom.clear();
	roomList.clear();
}

//This is temporary method.
public void showFormValues()
{
	alert(formvalues);
}

//Following method used to store and restore formvalues in session. When you need old FormValues.

public storFormValue(String id)
{
	session.setAttribute(id,formValues);

}

public restorFormValue(String id)
{
	if(session.getAttribute(id) !=null)
	formValues = session.getAttribute(id);
	updatedMsg = null;

}

//method added to create room for hicdata added by pra on 20-07-2009
public boolean createRooms(String parentId)
{
	if(returnHicData ==null)
	{
		return false;
	}	
	else
	{
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return false;
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);   
		Object rootFormValue = self.getRoot();
		Object parentObj = rootFormValue.getVariable(parentId,true);
		if(allValues==null)
		return false;
		int topmargin=120;
		int leftmargin=20;
		int roomCount=1;
		int rooms=1;
		String top="";
		String left="";
		for(int i=0;i<allValues.length;i++)
		{  	
			String id=allValues[i][0];
			String description=allValues[i][1];
			String listId="Suite"+roomCount;
	
			Listheader header = new Listheader();
			header.setLabel(description);
			header.setId(id);			
			Listhead head = new Listhead();
			top="top:"+topmargin+"px";
			left="left:"+leftmargin+"px";
			Checkbox checkbox = new Checkbox();
			checkbox.setWidth("20px");
			checkbox.setHeight("30px");
			String cboxId = "check"+roomCount;
			checkbox.setId(cboxId);
			String leftCheck = "left:"+(leftmargin-20)+"px";
			style = top +UILibraryConstant.STYLE_STRING+leftCheck;
			checkbox.setStyle(style);
			parentObj.appendChild(checkbox);
			Listbox list = new Listbox();
			list.setFixedLayout(true);
			list.setHeight("100px");
			list.setWidth("100px");
			list.setId(listId);
			list.setDroppable("true");
			list.setStyle(top +";align:None;background-color:#FFFFFF;margin-top:3px;font:Microsoft Sans Serif;font-style:normal;padding-right:0px;padding-left:0px;padding-top:0px;color:#000000;font-size:8.25pt;margin-bottom:3px;position:absolute;margin-right:3px;margin-left:3px;padding-bottom:0px;visibility:true;"+left);
			leftmargin=leftmargin+125;
			Listitem li = new Listitem();
			Listcell listcell=new Listcell("empty");
			listcell.value="empty";								
			listcell.setParent(li);
			header.setParent(head);
			head.setParent(list);
			li.setParent(list);
			list.addEventListener("onDrop", new EventListener(){
			public void onEvent(Event event)
			throws Exception
			{
			move(event.dragged);
			}
			});	
			list.addEventListener("onSelect", new EventListener(){
			public void onEvent(Event event)
			throws Exception
			{
			x=self.id;
			updateSession("rowId",x);
			}
			});		
			rooms++;
			if(5==rooms)
	        {
				topmargin=topmargin+120;
				leftmargin=20;
				rooms=1;
	      	}
			roomCount++;
			parentObj.appendChild(list); 
		}
	}
	return true;	
}

//This method is added to change the description of room added by pra on 20-july-2009
public void setDescription(String desc,String parentForm,String id)
{

   Object rootFormValue = Path.getComponent("//"+parentForm+"//"+parentForm);
   Object formObj = rootFormValue.getVariable(id,true);
	if(formObj.getChildren().size()>0)
	{
		Object obj=formObj.getChildren().get(0);
		if(obj.getChildren().size()>0)
		{
		obj.getChildren().get(0).setLabel(desc);
		}
	}	
}

//This method is added to delete room added by pra on 20-july-2009
public boolean deleteRoom(List idList,String parentId)
{  
    boolean isDeleted=false;
	Object rootFormValue = self.getRoot(); 
		if(idList!=null && idList.size()>0)
		{
			  for(int i=0;i<idList.size();i++)
			 {
				String checkId=idList.get(i);	 
				String[] checkIds=checkId.split("check");		
				String id="Suite"+checkIds[1];
				Object parent = rootFormValue.getVariable(parentId,true);  	
				Object parentObj = rootFormValue.getVariable(id,true);
				Object checkObj = rootFormValue.getVariable(checkId,true);
				String descrId=getDescription(id);
				String[] ids=descrId.split(",");
				String roomId=ids[1];
					if(roomId.indexOf("header")<0)
					{
						invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get room.id from neiswispicdb.roomassginment,neiswispicdb.schedule joins leftjoin roomassginment.roomid:=room.id,leftjoin schedule.PatientID:=patient.id,leftjoin schedule.StudyType:=studytype.id conditions roomassginment.schduleid:=schedule.ID and schedule.Cancelled:=["+0+"] and roomassginment.deleted:=["+0+"] and roomassginment.roomid:="+roomId+" and patient.deleted:=["+0+"]");
					boolean retVal=isRecordExist();
							if(retVal)
							{
							    selectRooms.clear();
								message(ids[0]+": cannot delete this room."+"\n"+"This room is scheduled."+"\n"+"Please unselect this room.");
								return false;
							}
							else
							{
								 invokeComponent("dbComponent","updateRecord","com.oxymedical.component.db.DBComponent","update neiswispicdb.room set deleted:=1 conditions room.id:="+roomId);
								 parent.removeChild(parentObj);
								 parent.removeChild(checkObj);
								 isDeleted=true;
							}
					}
			}
	}
	selectRooms.clear();
	return isDeleted;
}


int roomCounter=1;
private Hashtable roomIdInfo = new Hashtable();
public boolean createRoomAssignment(Object parentObj)
{
    updateSessionList("roomIdInfo",null);
	if(returnHicData ==null)
	{
		return false;
	}	
	else
	{
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return false;
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		if(allValues==null)
		return false;
		int topmargin=15;
		int leftmargin=13;
		
		int rooms=1;
		String top="";
		String left="";
		for(int i=0;i<allValues.length;i++)
		{  	
			String id=allValues[i][0];
			String description=allValues[i][1];
			String listId="Suite"+roomCounter;
			roomIdInfo.put(listId,id);
			Listheader header = new Listheader();
			header.setLabel(description);
			header.setId("header"+roomCounter+"##"+id);			
			Listhead head = new Listhead();
			top="top:"+topmargin+"px";
			left="left:"+leftmargin+"px";
			Checkbox checkbox = new Checkbox();
			checkbox.setWidth("20px");
			checkbox.setHeight("30px");
			String cboxId = "check"+roomCounter;
			checkbox.setId(cboxId);
			String leftCheck = "left:"+(leftmargin-18)+"px";
			style = top +UILibraryConstant.STYLE_STRING+leftCheck;
			checkbox.setStyle(style);
			parentObj.appendChild(checkbox);
			Listbox list = new Listbox();
			list.setFixedLayout(true);
			list.setHeight("140px");
			list.setWidth("115px");
			list.setId(listId);
			list.setDroppable("true");
			list.setStyle(top +";align:None;background-color:#FFFFFF;margin-top:5px;font:Microsoft Sans Serif;font-style:normal;padding-right:0px;padding-left:0px;padding-top:0px;color:#000000;font-size:8.25pt;margin-bottom:5px;position:absolute;margin-right:5px;margin-left:5px;padding-bottom:0px;visibility:true;"+left);
			leftmargin=leftmargin+138;
			Listitem li = new Listitem();
			Listcell listcell=new Listcell("empty");
			listcell.value="empty";								
			listcell.setParent(li);
			header.setParent(head);
			head.setParent(list);
			li.setParent(list);
			list.addEventListener("onDrop", new EventListener(){
			public void onEvent(Event event)
			throws Exception
			{
			move(event.dragged);
			}
			});	
			list.addEventListener("onSelect", new EventListener(){
			public void onEvent(Event event)
			throws Exception
			{
			x=self.id;
			updateSession("rowRoomId",x);
			}
			});	
			rooms++;
			if(5==rooms)
	        {
				topmargin=topmargin+155;
				leftmargin=13;
				rooms=1;
	      	}
			roomCounter++;
			parentObj.appendChild(checkbox); 
			parentObj.appendChild(list); 
		}
		updateSessionList("roomIdInfo",roomIdInfo);
	}
	return true;	
}				


//this method is added for drag and drop room items added by pra on 22-june-2009
void displayDataInControlDragAndDrop(String controlObj)
{

	Object rootFormValue = self.getRoot();
	Object elementObj = rootFormValue.getVariable(controlObj,true);
	if(elementObj instanceof Grid || elementObj instanceof Listbox )
	{
		showListDataDragAndDrop(null ,elementObj);	
	}
}
//this method is show data for drag and drop room items added by pra on 22-june-2009
public boolean showListDataDragAndDrop(IHICData hicData, Object elementObj)
{
			if(hicData==null)
			{
				if(returnHicData ==null)
				{
					return false;
				}
				else
				{
					hicData = returnHicData;
				}
			}
			IData dataUnit = hicData.getData();
			List listValue = null;
			if(dataUnit.getQueryData().getListData()!=null)
			{
				listValue = dataUnit.getQueryData().getListData();
			}
			else
				return false;
			String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
			
			if(allValues==null)
				return false;
			ListModel myModel = new ListModelList(allValues);
			elementObj.setModel(myModel);
			elementObj.setItemRenderer(new itemRendererArrayDragAndDrop());
		return true;
		
}
//this method is added to create the list added by pra on 22-june-2009
public List createChildListItems(Object obj)
{
	List list=new ArrayList();
	List childs=obj.getChildren();
	for(int i=1;i<childs.size();i++)
	{
		Listitem li=childs.get(i);
		List nextchilds=li.getChildren();	
		for(int j=0;j<nextchilds.size();j++)
		{
			Listcell cell=nextchilds.get(j);
			if(cell.getLabel()!="empty")
			{
				list.add(li);
			}
		}	
	}
return list;
}
//this method is added to remove child list items added by pra on 22-june-2009
public void removeChildListItems(Object obj)
{
	List childs=obj.getChildren();
	List newList=new ArrayList(childs);	
	for(int i=1;i<newList.size();i++)
	{
		obj.removeChild(newList.get(i));
	}
}
//this method is added to drag component added by pra on 22-june-2009
private Hashtable roomTable = new Hashtable();
public void move(Component dragged)
{
      if (self instanceof Listitem)
		{
			
			if (dragged.getParent().getId().equals("right"))
			{
				self.parent.insertBefore(dragged, self.getNextSibling());
			} else
			{
				self.parent.insertBefore(dragged, self.getNextSibling());
			}
		}
	else if((dragged.getParent().id).equals("grid20"))
	{	
	    List presentList=roomTable.get(self.id+"_pat");
	    boolean isUpdate=false;
	    String date=dragged.getChildren().get(2).value;
		Date dateValue=DateUtil.stringToDate(date,"yyyy-MM-dd");
	    String currentDate = getCurrentDate("yyyy-MM-dd");
		Date currentDateValue=DateUtil.stringToDate(currentDate,"yyyy-MM-dd");
		int resultCompare=DateUtil.compareDates(dateValue,currentDateValue);	
		Hashtable roomInfo=session.getAttribute("roomIdInfo");
        String roomId=roomInfo.get(self.id);		
		if(resultCompare >=0)
		{
			invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get room.id from neiswispicdb.roomassginment,neiswispicdb.schedule joins leftjoin roomassginment.roomid:=room.id,leftjoin schedule.PatientID:=patient.id,leftjoin schedule.StudyType:=studytype.id conditions roomassginment.schduleid:=schedule.ID and schedule.Cancelled:=["+0+"] and roomassginment.deleted:=["+0+"] and schedule.AppointmentStartDate:=["+date+"] and roomassginment.roomid:="+roomId+" and patient.deleted:=["+0+"]");
		boolean retVal=isRecordExist();
			if(retVal)
			{
			message("This room is booked for given date.");
			}
			else
			{				
			isUpdate=true;
			}
		}
		else
		{
		message( "This room cannot be assigned for previous date schedule.");
		}	
		if(presentList!=null && presentList.size()>0 && isUpdate)
		{
		removeChildListItems(self);
	    roomTable.remove(self.id+"_pat");
		roomTable.remove(self.id+"_slt");
		roomTable.remove(self.id+"_phy");
		roomTable.remove(self.id+"_res");
		roomTable.remove(self.id+"_ras");	
		}

	   if(isUpdate)
	   {
	    removeEmpty(self,null);
		List list=dragged.getChildren();
		List listChilds=self.getChildren();
		List childs=createChildListItems(self);
		LinkedList data = new LinkedList();
		if(listChilds.size()>1)
		{
			removeChildListItems(self);		       
		}
		for(int i=0;i<dragged.getChildren().size();i++)
		{		
			data.add(list.get(i).value);
			
			 if (i>0)
			  { 
			    String value=list.get(i).value;
				Listitem li = new Listitem();
				li.setId("grid20_"+self.id+i);
				Listcell listcell=new Listcell(value);
				listcell.value=value;	
				listcell.setParent(li);	 
				self.appendChild(li); 
				}	
				
			
		}
		if(childs.size()>0)
		{	
			for(int j=0;j<childs.size();j++)
			{ 
			Listitem li=childs.get(j);
			self.appendChild(li); 
			}
		}
		Object rootFormValue = self.getRoot();   	
		Object parentObj = rootFormValue.getVariable((dragged.getParent().id),true);
		parentObj.removeChild(dragged);		
		roomTable.put(self.id+"_pat",data);
	}
	}
	else if((dragged.getParent().id).equals("grid19"))
	{	
        List presentList=roomTable.get(self.id+"_res");
		boolean isUpdate=false;
		if(presentList!=null && presentList.size()>0)
		{
		    if(presentList.size()>2)
			{
				isUpdate=messageWithQuestion("This room is already assigned with Research Assisant."+"\n"+"Do you want to update Research Assisant?");
				if(isUpdate)
				{
				   boolean valid=validateResearchAssistant(dragged.getChildren(),presentList);
				   if(valid)
				   {
				   presentList.remove(3);
				   presentList.remove(2);
				   removeEmpty(self,"RA2:");
				   }
				   else
				   {
				    message("This research assistant is already assigned to the patient.");
					isUpdate=false;
				   }
				}
			}
			else
			{
			  boolean valid=validateResearchAssistant(dragged.getChildren(),presentList);
			  if(valid)
			  isUpdate=true;
			  else
			  message("This research assistant is already assigned to the patient.");
			}
		}
	   if(presentList==null || isUpdate)
	   {	
		List list=dragged.getChildren();
		LinkedList data=null;
		removeEmpty(self,null);
		if(!isUpdate)
		data= new LinkedList();
		else
		{
		data=roomTable.get(self.id+"_res");
		if(data==null)
		data= new LinkedList();
		}
		for(int i=0;i<dragged.getChildren().size();i++)
		{
			data.add(list.get(i).value);
			if(i==1)
			{
				Listitem li = new Listitem();
				if(!isUpdate)
				{
				Listcell listcell=new Listcell("RA1: "+list.get(1).value);
				listcell.value="RA1: "+list.get(i).value;	
				listcell.setParent(li);	
				self.appendChild(li);
				}
				else
				{
				Listcell listcell=new Listcell("RA2: "+list.get(1).value);
				listcell.value="RA2: "+list.get(i).value;
				listcell.setParent(li);	
				self.appendChild(li);				
				}
				
			}			 	
		}
		roomTable.put(self.id+"_res",data);
	  }
	  
	}
	else if((dragged.getParent().id).equals("grid18"))
	{	
	   List presentList=roomTable.get(self.id+"_slt");
	   boolean isUpdate=false;
		if(presentList!=null && presentList.size()>0)
		{
		isUpdate=messageWithQuestion("This room is already assigned with Sleep technician."+"\n"+"Do you want to update Sleep technician?");
			if(isUpdate)
			{
			roomTable.remove(self.id+"_slt");
			removeEmpty(self,"ST:");
			}
		}
	   if(presentList==null || isUpdate)
	   {
		List list=dragged.getChildren();
		removeEmpty(self,null);
		LinkedList data = new LinkedList();
		for(int i=0;i<dragged.getChildren().size();i++)
		{
			data.add(list.get(i).value);
			if(i==1)
			{
				Listitem li = new Listitem();
				li.setId("grid18_"+self.id+i);
				Listcell listcell=new Listcell("ST: "+list.get(1).value);
				listcell.value="ST: "+list.get(i).value;	
				listcell.setParent(li);
				self.appendChild(li);
			}		 	
		}
		roomTable.put(self.id+"_slt",data);
	}
		
	}
	else if((dragged.getParent().id).equals("grid17"))
		{	
		 List presentList=roomTable.get(self.id+"_phy");
		 boolean isUpdate=false;
		if(presentList!=null && presentList.size()>0)
		{
		isUpdate=messageWithQuestion("This room is already assigned with Physician."+"\n"+"Do you want to update Physician?");
			if(isUpdate)
			{
			roomTable.remove(self.id+"_phy");
			removeEmpty(self,"PH:");
			}
		}
	   if(presentList==null || isUpdate)
	   {
			List list=dragged.getChildren();
			removeEmpty(self,null);
			LinkedList data = new LinkedList();
			for(int i=0;i<dragged.getChildren().size();i++)
			{
				data.add(list.get(i).value);
				if(i==1)
				{
					 Listitem li = new Listitem();
					 li.setId("grid17_"+self.id+i);
					 Listcell listcell=new Listcell("PH: "+list.get(1).value);
					 listcell.value="PH: "+list.get(i).value;	
					 listcell.setParent(li);
					 self.appendChild(li);
				}		 	
			}
			roomTable.put(self.id+"_phy",data);
		}
		
	}	
	else
	{
	self.appendChild(dragged);
	}
}
public boolean validateResearchAssistant(List newRA,List exRA )
{
	String newResAss=newRA.get(0).value;
	if(exRA.size()> 2)
	{
	String oldRas=(String)exRA.get(2);
	if(newResAss.equalsIgnoreCase(oldRas))	
	return false;	
	}
	String oldRa=(String)exRA.get(0);
	if(newResAss.equalsIgnoreCase(oldRa))	
	return false;
	else
	return true;
	
	return false;
}
public void removeEmpty(Object obj,String removeString)
{
	List childs=obj.getChildren();
	List newList=new ArrayList(childs);	
	if(removeString!=null)
	{
	
	 for(int i=1;i<newList.size();i++)
		{
			List nextChilds=newList.get(i).getChildren();
			for(int j=0;j<nextChilds.size();j++)
			{
				Object objChild=nextChilds.get(j);
				String value=objChild.getValue();
				if(value!=null && value.indexOf(removeString)>=0)
				{
					obj.removeChild(newList.get(i));
				}
			}
		}
	}
	else
	{
		for(int i=1;i<newList.size();i++)
		{
			List nextChilds=newList.get(i).getChildren();
			for(int j=0;j<nextChilds.size();j++)
			{
				Object objChild=nextChilds.get(j);
					String value=objChild.getValue();
					if(value!=null && (value.equalsIgnoreCase("empty")|| value.equalsIgnoreCase("Room Booked.")))
					{
						obj.removeChild(newList.get(i));
					}
			}
		}
	}
}
//this method is used to save the patient assginment.Added by pra on 22-07-2009
public String saveRoomAssignment(String parentId)
{
    Object rootFormValue = self.getRoot();   	
	Object parentObj = rootFormValue.getVariable(parentId,true);
	String displayMessage="";
	boolean isSaved=false;
	boolean isMessageCreated=false;
	if(roomTable==null && roomTable.size()==0)
	{
	  displayMessage="No room assignments to save.";
      return displayMessage;
    }
	List listRoom=getRoomList(parentObj);
	LinkedList schInfo=null;
	LinkedList sleepInfo=null;
	LinkedList phyInfo=null;
	LinkedList resInfo=null;
	if (listRoom!=null && listRoom.size()>0)
	{
		for(int i=0;i<listRoom.size();i++)
		{
			boolean isPresent=false;
			boolean isSchedulePresent=false;
			boolean isFromDb=false;
			String id=roomList.get(i);
			Hashtable roomInfo=session.getAttribute("roomIdInfo");
            String roomId=roomInfo.get(id);
			String descr=getDescription(id);
			descr=splitString(descr,",");
			boolean infoPresent=false;
			String date=null;
			if(roomTable!=null && roomTable.size()>0)
			{
			
			        formValues.remove("textbox53");
					formValues.remove("textbox63");
					formValues.remove("textbox64");
					formValues.remove("textbox58");
					formValues.remove("textbox66");
					formValues.remove("textbox54");
					formValues.remove("textbox55");
					schInfo=roomTable.get(id+"_pat");
					sleepInfo=roomTable.get(id+"_slt");
					phyInfo=roomTable.get(id+"_phy");
					resInfo=roomTable.get(id+"_res");
					rasInfo=roomTable.get(id+"_ras");	
					if(schInfo!=null && schInfo.size()>0)
					{
						if(schInfo.get(0)!=null)
						{
						formValues.put("textbox53",schInfo.get(0));
						isSchedulePresent=true;					
						}		
					}
					if( sleepInfo!=null && sleepInfo.size()>0)
					{
					infoPresent=true;
					if(sleepInfo.get(0)!=null)
					formValues.put("textbox63",sleepInfo.get(0));
					}
					if(phyInfo!=null && phyInfo.size()>0)
					{
					infoPresent=true;
					if(phyInfo.get(0)!=null)
					formValues.put("textbox64",phyInfo.get(0));		  
					}
					if(resInfo!=null && resInfo.size()>0)			   
					{
					infoPresent=true;
					if(resInfo.get(0)!=null)
					formValues.put("textbox58",resInfo.get(0));
					if(resInfo.size()>2)
					formValues.put("textbox189",resInfo.get(2));
					}
					formValues.put("textbox66","0");
                    formValues.put("textbox55",roomId);	   
                    if(rasInfo!=null && rasInfo.size()>0)			   
					{
					isFromDb=true;
					formValues.put("textbox54",rasInfo.get(0));	
					}
								
					if(formValues!=null && isSchedulePresent)
					{
						
						    isPresent=true;
							isSaved=true;
							String retValue=invokeComponent("dbComponent","save","com.oxymedical.component.db.DBComponent","");
							if(retValue.equalsIgnoreCase("true"))
							{
							invokeComponent("dbComponent","updateRecord","com.oxymedical.component.db.DBComponent","update neiswispicdb.schedule set Assigntoroom:=1 conditions schedule.ID:="+schInfo.get(0));
							}
					}
					if(infoPresent && !isPresent)
					{
					isMessageCreated=true;
					displayMessage=displayMessage+"Please add schedule to room " +descr+"."+"\n";
					}
				}
			}
	}
		if(!isMessageCreated)
		{
		displayMessage="Room assignments has been saved.";
		}
		if(!isSaved)
		{
		displayMessage="No room assignments to save.";
		}
		roomList.clear();
	return displayMessage; 
}
//this method is used to show  the patient assginment from db.Added by pra on 22-07-2009
public void createRoomData(Object parentObj)
{
    roomTable.clear();
    updateSessionList("dbListValue",null);
	if(returnHicData ==null)
	{
			
		return;
	}
	else
	{   String roomAssginments=getSessionData("roomassign");
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return;
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		if(allValues==null)
		{
		return;
		}
		else
		{
			List listRoom=getRoomList(parentObj);
			List roomsObject=new ArrayList();
			for(int i=0;i<allValues.length;i++)
			{
				String roomAssginId=allValues[i][0];
				String roomId=allValues[i][1];
				String scheduleId=allValues[i][2];
				String phyId=allValues[i][3];
				String sleepTechId=allValues[i][4];
				String researchId=allValues[i][5];
				String studyType=allValues[i][6];
				String firstName=allValues[i][7];
				String startTime=allValues[i][8];
				String lastName=allValues[i][9];
				String startDate=allValues[i][10];
				String researchId2=allValues[i][11];
				Object[] roomsArray=new Object[9];
				
				for(int j=0;j<listRoom.size();j++)
				{
					String id=roomList.get(j);
					Hashtable roomInfo=session.getAttribute("roomIdInfo");
                    String idRoom=roomInfo.get(id);
					if(idRoom.equalsIgnoreCase(roomId))
					{
					Object formObj=parentObj.getVariable(id,true);
					removeEmpty(formObj,null);
					String phyName=getUserNameBasedOnUserId(phyId);
					String sleepTechName=getUserNameBasedOnUserId(sleepTechId);
					String researchName=getUserNameBasedOnUserId(researchId);
					String researchName2=getUserNameBasedOnUserId(researchId2);
				    String descr=formObj.getChildren().get(0).getChildren().get(0).getLabel();
					roomsArray[0]=descr;
					roomsArray[1]=firstName;
					roomsArray[2]=lastName;
					roomsArray[3]=startDate;
					roomsArray[4]=startTime;
					roomsArray[5]=studyType;
					if(roomAssginments!=null)
				    {
					setListItem(lastName+","+firstName,formObj);
					}
					setListItem(startTime,formObj);
					setListItem(startDate,formObj);
					setListItem(studyType,formObj);	
					if(sleepTechName!=null)
					{
					setListItem("ST: "+sleepTechName,formObj);
					roomsArray[6]=sleepTechName;
					}
					else
					{
					roomsArray[6]="";
					}
					if(phyName!=null)
					{
					setListItem("PH: "+phyName,formObj);
					roomsArray[7]=phyName;
					}
					else
					{
					roomsArray[7]="";
					}
					if(researchName!=null)
					{
					setListItem("RA1: "+researchName,formObj);
					roomsArray[8]=researchName;
					}
					else
					{
					roomsArray[8]="";
					}
					if(researchName2!=null)
					{
					setListItem("RA2: "+researchName2,formObj);
					}
					
					LinkedList schInfo= new LinkedList();
					LinkedList phyInfo= new LinkedList();
					LinkedList sleepInfo= new LinkedList();
					LinkedList resInfo= new LinkedList();
					LinkedList roomAssgin= new LinkedList();
									
					phyInfo.add(phyId);
					phyInfo.add(phyName);
					sleepInfo.add(sleepTechId);
					sleepInfo.add(sleepTechName);
					if(researchId!=null)
					{
					resInfo.add(researchId);
					resInfo.add(researchName);
					}
					if(researchId2!=null)
					{
					resInfo.add(researchId2);
					resInfo.add(researchName2);
					}

					schInfo.add(scheduleId);					
					schInfo.add(startTime);
					schInfo.add(startDate);
					schInfo.add(studyType);
					roomAssgin.add(roomAssginId);
				   
					if(sleepTechId!=null)
					{
					 roomTable.put(id+"_slt",sleepInfo);
					}
					if(researchId!=null)
					{
					 	roomTable.put(id+"_res",resInfo);
					}
					if(phyId!=null)
					{
					 	roomTable.put(id+"_phy",phyInfo);
					}
					roomTable.put(id+"_pat",schInfo);
					roomTable.put(id+"_ras",roomAssgin);
					roomsObject.add(roomsArray);
					}
				}
				
			}
			    roomList.clear();
				if(roomAssginments!=null)
				{
				updateSessionList("dbListValue",roomsObject);
				}
		}
	}
}
//used to create the listitem for room added by pra on 22/7/2009
public void setListItem(String value,Object formObj)
{
	if(value!=null)
	{
		Listitem li = new Listitem();
		Listcell listcell=new Listcell(value);
		listcell.value=value;	
		listcell.setParent(li);	 	
		formObj.appendChild(li);
	}
}
//used to get username based on userid added by pra on 22/07/2009
public String getUserNameBasedOnUserId(String userId)
{
	invokeComponent("com.oxymedical.component.useradmin","GetListUserAdmin","com.oxymedical.component.useradmin.UserAdminComponent","get contact_.userName from useradmin.contact_ conditions contact_.userId:=["+userId+"]");
	String userName=getUserName();
	return userName;
}
//used to get username based on userid added by pra on 22/07/2009	
public String getUserName()
{
	String userName=null;
	if(returnHicData ==null)
	{
		return null;
	}
	else
	{
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return null;
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		if(allValues==null)
		{
		return null;
		}
		else
		{
			for(int i=0;i<allValues.length;i++)
			{
			userName=allValues[i][0];
			}
		}
	}
return 	userName;	
}
//this method is used to  remove room assginment.added by pra on 22/7/2009
public void clearRoomAssignment(List deletedIdList)
{
	Object rootFormValue = self.getRoot();
	if(deletedIdList!=null && deletedIdList.size()>0)
	{
		  for (int i=0;i<deletedIdList.size();i++)
		  {
		   	String checkId=deletedIdList.get(i);	 
			String[] checkIds=checkId.split("check");		
			String deleteId="Suite"+checkIds[1];
			Object formObj = rootFormValue.getVariable(deleteId, true);
			Object checkformObj = rootFormValue.getVariable(checkId, true);
			boolean isPresent=false;
			if(roomTable!=null && roomTable.size()>0 && !isPresent)
			{  
				 List roomAssign=roomTable.get(deleteId+"_ras");
				List schInfo=roomTable.get(deleteId+"_pat");
				if(roomAssign!=null && roomAssign.size()>0)
				{
				invokeComponent("dbComponent","updateRecord","com.oxymedical.component.db.DBComponent","update neiswispicdb.roomassginment set deleted:=1 conditions roomassginment.id:="+roomAssign.get(0));
				roomTable.remove(deleteId+"_ras");
				invokeComponent("dbComponent","updateRecord","com.oxymedical.component.db.DBComponent","update neiswispicdb.schedule set Assigntoroom:=0 conditions schedule.ID:="+schInfo.get(0));
				}
			roomTable.remove(deleteId+"_pat");
			roomTable.remove(deleteId+"_slt");
			roomTable.remove(deleteId+"_phy");
			roomTable.remove(deleteId+"_res");	
			}
		removeChildListItems(formObj);		
		Listitem li = new Listitem();
		Listcell listcell=new Listcell("empty");
		listcell.value="empty";
		listcell.setParent(li);
		formObj.appendChild(li);
		checkformObj.setChecked(false);
		}
	}
	selectRooms.clear();
}
//method to display data from calendaer panel in room assginment.added by pra on 24/7/2009
public setDataForRoomManagement(String parentForm,String selectedDate,parentId)
{
	Object rootFormValue = Path.getComponent("//"+parentForm+"//"+parentForm);
	Object parentObj=rootFormValue.getVariable(parentId, true);
	removeRooms(parentObj);
	invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get room.id,room.RoomDescription,room.roomtype from neiswispicdb.room ,neiswispicdb.roomtype conditions room.deleted:=["+0+"] and room.roomtype:=roomtype.id and roomtype.deleted:=["+0+"]");
	boolean result=createRoomAssignment(parentObj);
	if(result)
	{
	invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get roomassginment.id,room.id,schedule.id,roomassginment.physicianid,roomassginment.sleeptechid,roomassginment.researchid,studytype.Type,patient.FirstName,schedule.AppointmentStartTime,patient.lastName,schedule.AppointmentStartDate,roomassginment.researchid2 from neiswispicdb.roomassginment,neiswispicdb.schedule joins leftjoin roomassginment.roomid:=room.id,leftjoin schedule.PatientID:=patient.id,leftjoin schedule.StudyType:=studytype.id conditions roomassginment.schduleid:=schedule.ID and schedule.Cancelled:=["+0+"] and roomassginment.deleted:=["+0+"] and schedule.AppointmentStartDate:=["+selectedDate +"] and patient.deleted:=["+0+"]");
	createRoomData(parentObj);
	}
}
//this method is used to update room if room is booked.added by pra on 25/7/2009
public void updateRoomData(Object parentObj)
{
	if(returnHicData ==null)
	{
			
		return;
	}
	else
	{
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return;
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		if(allValues==null)
		{
			return;
		}
		else
		{
			List listRoom=getRoomList(parentObj);
			for(int i=0;i<allValues.length;i++)
			{
				
				String roomId=allValues[i][0];
				for(int j=0;j<listRoom.size();j++)
				{
					String id=roomList.get(j);
					
					Hashtable roomInfo=session.getAttribute("roomIdInfo");
                    String idRoom=roomInfo.get(id);
					if(idRoom.equalsIgnoreCase(roomId))
					{
						Object formObj=parentObj.getVariable(id,true);
						List childs=formObj.getChildren();
						if(childs.size()>2)
						{
							continue;
						}
						else
						{
							removeEmpty(formObj,null);
							Listitem li = new Listitem();
							Listcell listcell=new Listcell("Room Booked.");
							listcell.value="Room Booked.";	
							listcell.setParent(li);	 	
							formObj.appendChild(li);
						}
					}
				}
			}
		roomList.clear();	
		}
	}	
}

//This method is used to store combodata only in uilibry at run time.
public void storeComboData(String comboId)
{
	IHICData hicData = null;
	if(returnHicData ==null)
	{
			return;
	}
	else
	{
			hicData = returnHicData;
	}
	Object rootFormValue = self.getRoot();
	Object comboObj = rootFormValue.getVariable(comboId, true);
	IData dataUnit = hicData.getData();
	List listValue = null;
	if(dataUnit.getQueryData().getListData()!=null)
	{
		listValue = dataUnit.getQueryData().getListData();
	}
	else
		return;
	if(listValue !=null)
	{
	comboTable.put(comboObj.getId(),listValue);
	}
}

public String getDataFromAllValuesVar(int firstIndex, int secondIndex)
{
	String retVal = null;
	String[][] allValues = null;
	if(null != returnHicData)
	{	
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return null;
		allValues = dataUnit.getQueryData().iterateListData(listValue);
	if (null != allValues && allValues.length>0)
		{ 
		  if(allValues[firstIndex]!=null && allValues[firstIndex].length>0)
			retVal = allValues[firstIndex][secondIndex];
		}
	}
	return retVal;
}


public String getDataFromFormValues(String id)
{
	String value = null;
	if(id !=null)
	{
		value = formValues.get(id);
	}
	return value;
	
}


//This method check that date is valid or not.
public boolean dateValidation(String dobValue)
{
	String dateFormat = "yyyy-mm-dd";
	boolean yes =DateUtil.validateDate(dobValue,dateFormat);
	return yes;

}

public void showDataInGrid(String id)
{
	Object rootFormValue = self.getRoot();
	Object gridObj = rootFormValue.getVariable(id, true);
	if(gridObj == null)
	return;
	String name = getSessionData("name");
	String dose = getSessionData("dose");
	String timeValue =getSessionData("time");
	Listitem li = new Listitem();
	Listcell lc = new Listcell();
	boolean isChild = false;
	if(timeValue !=null && timeValue !="")
	{
		lc.setLabel(timeValue);
		li.appendChild(lc);
		isChild = true;
	}
	lc = new Listcell();
	if(name !=null && name !="")
	{
		lc.setLabel(name);
		li.appendChild(lc);
		isChild = true;
	}
	if(dose !=null && dose !="")
	{
		lc = new Listcell();
		lc.setLabel( dose);
		li.appendChild(lc);
		isChild = true;
	}
	if(isChild)
	gridObj.appendChild(li);
	
}

// save foreig table value after getting parent table id.
public void saveForeignTable(Object gridObj){

	if(gridObj == null || returnHicData == null)
	{
		return;
	}
	String parentId = returnHicData.getUniqueID();
	if(parentId == null)return;
	if(parentId!=null)
		{
invokeComponent("dbComponent","updateRecord","com.oxymedical.component.db.DBComponent","delete from neiswispicdb.medicationconsume conditions medicationconsume.TechNoteId:="+parentId);
}
		formValues = new Hashtable();
		List list =gridObj.getChildren();
		if(list!=null && list.size()>0)
		{
			formValues.put("textbox127",parentId);
			Iterator itr = list.iterator();
			while(itr.hasNext())
			{
				Object obj = itr.next();
				if(obj instanceof Listitem)
				{
				Listitem child = (Listitem)obj;
				List cellList = child.getChildren();
				int valueCounter=1;
				if(cellList !=null)
				{
				Iterator cellItr = cellList.iterator();
				while(cellItr.hasNext())
				{
					Listcell cell = (Listcell)cellItr.next();
					String value = cell.getLabel();
					if(value !=null)
					{
						if(valueCounter ==1)
							formValues.put("textbox117",value);
						if(valueCounter ==2)
							formValues.put("textbox118",value);
						if(valueCounter ==3)
							formValues.put("textbox119",value);
								
					}
					valueCounter = valueCounter +1;
					
				}
				
				setFormPatternId("MedicationForm");
				invokeComponent("dbComponent","save","com.oxymedical.component.db.DBComponent","MedicationForm");
						formValues.remove("textbox119");
						formValues.remove("textbox117");
						formValues.remove("textbox118");
				
				}
				
			}
			}
		}

}

//this method is used to delete all the rooms. added by pra on 30/7/2009.
public String deleteAllRooms(String parentId)
{
    Object rootFormValue = self.getRoot(); 
    Object parent = rootFormValue.getVariable(parentId,true); 
    List listRoom=getRoomList(parent);
	String messageDisplay="These are the following rooms which cannot be deleted as they are scheduled: ";
	boolean isMessage=false;

	if(listRoom!=null && listRoom.size()>0)
	{
		List roomList=new ArrayList(listRoom);  
		 for(int i=0;i<roomList.size();i++)
		 { 
			String id=roomList.get(i);
			String[] ids=id.split("Suite");
			String checkId="check"+ids[1];
			Object parentObj = rootFormValue.getVariable(id,true);
			Object checkObj = rootFormValue.getVariable(checkId,true);
			String descrId=getDescription(id);
			String[] ids=descrId.split(",");
			String roomId=ids[1];
			String desc=ids[0];
			if(roomId.indexOf("header")<0)
			{
				invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get room.id from neiswispicdb.roomassginment,neiswispicdb.schedule joins leftjoin roomassginment.roomid:=room.id,leftjoin schedule.PatientID:=patient.id,leftjoin schedule.StudyType:=studytype.id conditions roomassginment.schduleid:=schedule.ID and schedule.Cancelled:=["+0+"] and roomassginment.deleted:=["+0+"] and roomassginment.roomid:="+roomId+" and patient.deleted:=["+0+"]");
			boolean retVal=isRecordExist();
				if(retVal)
				{
				 isMessage=true;
				 messageDisplay= messageDisplay +"\n"+desc+".";
				}
				else
				{
				 invokeComponent("dbComponent","updateRecord","com.oxymedical.component.db.DBComponent","update neiswispicdb.room set deleted:=1 conditions room.id:="+roomId);
				 parent.removeChild(parentObj);
				 parent.removeChild(checkObj);
				}
			}
		}	

	}
	else
	{
	messageDisplay="No rooms present in system.";
	}
	if(!isMessage)
	{
	messageDisplay="All the rooms are deleted.";
	}
	roomList.clear();
	return messageDisplay;
}

// This method return check field name in query builder.

public String getCheckedField()
{
	Object rootFormValue = self.getRoot();    
	String fieldList = "";
	String countStr = null;
	countStr = formValues.get("counter");
	if(countStr!=null)
	{
		int value = Integer.parseInt(countStr);
		for(int counter=1;counter<=value;counter++)
		{
			Object controlObj = rootFormValue.getVariable("check"+counter,true);
			if(controlObj!=null)
			{
				boolean checkValue = controlObj.isChecked();
				if(checkValue)
				{
					String fieldName = formValues.get("field"+counter);
					if(fieldName !=null)
					{
						fieldList =fieldList+fieldName;
						if(counter<value)
						{
						fieldList =fieldList+",";
						}
					}
				}
			}
		}
	}
	else
	{
		Object controlObj = rootFormValue.getVariable("check1",true);
		if(controlObj!=null)
		{
			boolean value = controlObj.isChecked();
				if(value)
				{
					String fieldName = formValues.get("field1");
					if(fieldName !=null)
					{
						fieldList =fieldList+fieldName;
					}
				}
		}
	}
	return fieldList;
}

// concatenate string 
public String addString(String first,String second,String third)
{
	String result = first+ second+third;
	return result;
}

// This method return no of header of list or grid.
public int getNoOfHeader(Object gridObj)
{
	List list =gridObj.getChildren();
	int headerNo=0;
		if(list!=null && list.size()>0)
		{
			Iterator itr = list.iterator();
			while(itr.hasNext())
			{
				Object obj = itr.next();
				if(obj instanceof Listhead)
				{
					List listHead =obj.getChildren();
					headerNo = listHead.size();
					break;
				}
			}
		}
	return headerNo;
}

public String[][] getGridLengthValue(String[][] allValues, int len)
	{
		String[][] dupValue = new String[allValues.length][];;
		for(int valueCounter =0;valueCounter<allValues.length;valueCounter++)
		{
			dupValue[valueCounter] = new String[len];
			for(int col =0;col<len;col++)
			{
				dupValue[valueCounter][col] = allValues[valueCounter][col];
			}
		
		}
		return dupValue;

	}

	
//method is used to get select startDate,startTime,endDate,endTime from schedular.added by pra on 1/8/2009
public boolean getDefaultValuesForSchedular(String id)
{
	boolean isValid=false;
	CalendarsEvent evt = (CalendarsEvent) event;
	List list = new ArrayList();
	Date beginDate=evt.getBeginDate();

	String date=DateUtil.formatDate(beginDate,"yyyy-MM-dd");
	Date dateValue=DateUtil.stringToDate(date,"yyyy-MM-dd");
	String currentDate = getCurrentDate("yyyy-MM-dd");
	Date currentDateValue=DateUtil.stringToDate(currentDate,"yyyy-MM-dd");
	int resultCompare=DateUtil.compareDates(dateValue,currentDateValue);
	if(resultCompare>=0)
	{
		isValid=true;
		Date endDate=evt.getEndDate();
		String startDate=DateUtil.formatDate(beginDate,"yyyy-MM-dd,HH:mm:ss");
		String endDateTime=DateUtil.formatDate(endDate,"yyyy-MM-dd,HH:mm:ss");
		String[] startTime=startDate.split(",");
		String[] endTime=endDateTime.split(",");
		list.add(startTime[0]);
		list.add(endTime[0]);
		list.add(startTime[1]);
		list.add(endTime[1]);
		updateSessionList("defaultValues",list);
	}
	else
	{
		message("Please select current or future date.");
	}
	return isValid;
}
//method to set default values in appointment.added by pra on 1/08/2009
public void setDefaultForSchedule(String startId,String endId,String sTimeId,String eTimeId)
{
	Object rootFormValue = self.getRoot();   
	List defaultValues=session.getAttribute("defaultValues");
	if(defaultValues!=null && defaultValues.size()>0)
	{
	Object startDateObject=rootFormValue.getVariable(startId,true);
	Object endDateObject=rootFormValue.getVariable(endId,true);
	Object startTimeObject=rootFormValue.getVariable(sTimeId,true);
	Object endTimeObject=rootFormValue.getVariable(eTimeId,true);
	SimpleDateFormat ds1=new SimpleDateFormat("yyyy-MM-dd hh:mm");  
	String sDate=defaultValues.get(0)+" "+"00:00";
	String eDate=defaultValues.get(1)+" "+"00:00";
	Date startDate = ds1.parse(sDate);
	Date endDate = ds1.parse(eDate);
	startDateObject.setValue(startDate);
	endDateObject.setValue(endDate);
	Time startTime = Time.valueOf(defaultValues.get(2));
	Time endTime = Time.valueOf(defaultValues.get(3));
	startTimeObject.setValue(startTime);
	endTimeObject.setValue(endTime);
	formValues.put(startId,defaultValues.get(0));
	formValues.put(endId,defaultValues.get(1));
	formValues.put(sTimeId,startTime);
	formValues.put(eTimeId,endTime);
	updateSessionList("defaultValues",null);
	}	
}	
//method is used to get the room id.added by pra.
public String getRoomId(String id)
{
	String pid="";
	Object rootFormValue = self.getRoot();
	Object formObj = rootFormValue.getVariable(id, true);
	
	if(formObj.getChildren().size()>0)
	{
		Object obj=formObj.getChildren().get(0);
		if(obj.getChildren().size()>0)
		{  
			pid=obj.getChildren().get(0).getId();
			
		}
	}
return  pid;
}
//this method is used to set the controls visible based on the rights.Added by pra on 3 Aug 2009.
//use this method always to make any control visible instead of using directly zk method 
//in case some rights are applied on the control.
public void visibleControl(String id)
{
	Object rootFormValue = self.getRoot();
	Object formObj = rootFormValue.getVariable(id, true);
	String rightId=formObj.getAttribute("accessrights");
	String[] rightValues=rightId.split(",");
	List rights=session.getAttribute("rights");
	if(rightValues!=null && rights!=null)
	{
		for (int i = 0; i < rights.size(); i++)
		{
			IRights rig = (IRights) rights.get(i);
			String right = rig.getRightName();
			for(int j=0;j<rightValues.length;j++)
			{
				if (rightValues[j].equalsIgnoreCase(right))
				{
					 formObj.setVisible(true);
				}
			}
		}
	}	
}

List selectRooms=new ArrayList();
//method added to get selected list of rooms.added by pra.
public List getSelectedRooms(Object formObj)
{
	List childElement;
	if (formObj != null) 
	{
		childElement = formObj.getChildren();
		Iterator iter = childElement.iterator();
		while (iter.hasNext())
		{
			Object value = iter.next();
			if (value instanceof Checkbox) 
			{
			
			if (((Checkbox) value).getId().indexOf("check") >= 0)
			{
				 if(((Checkbox) value).isChecked())
				 {
				  selectRooms.add(value.id);
				 }
			}	 
				
			}
			else
			{
				selectRooms = getSelectedRooms(((XulElement) value));
			}
		}
	}
 return selectRooms;
}
//method added to get select id of room.added by pra.
public String getSelectedId(List idList)
{
 String id=null;
 if(idList!=null && idList.size()>0)
 {  if(idList.size()>1)
	 {
	   selectRooms.clear();
	   return null;
	 }
	 else
	 {
	 String checkId=idList.get(0);
	 if(checkId.indexOf("check")>=0)
	 { 
		String[] checkIds=checkId.split("check");		
		id="Suite"+checkIds[1];
	 }
	 
	 }
 }
 selectRooms.clear();
 return id;
}
//to check the list. added by pra.
public boolean checkListSizeValid(List objects,int checkValue)
{
	if(objects!=null && objects.size()>checkValue)
	{
	return true;
	}
	else
	return false;
}

public boolean clearSelectedData(String value,List idList)
{
  boolean isCleared=false;
        Object rootFormValue = self.getRoot(); 
		if(idList!=null && idList.size()>0)
		{
			  for(int i=0;i<idList.size();i++)
			 {
				String checkId=idList.get(i);	 
				String[] checkIds=checkId.split("check");		
				String id="Suite"+checkIds[1];
				Object parent = rootFormValue.getVariable(id,true); 
				Object checkformObj = rootFormValue.getVariable(checkId,true); 
				if(value.equalsIgnoreCase("clear physician"))
				{
				  if(roomTable.containsKey(id+"_phy"))
				  {
					roomTable.remove(id+"_phy");
					removeEmpty(parent,"PH:");
					isCleared=true;
				 }
				}
				else if(value.equalsIgnoreCase("clear sleep technician"))
				{
				  if(roomTable.containsKey(id+"_slt"))
				  {
					roomTable.remove(id+"_slt");
					removeEmpty(parent,"ST:");
					isCleared=true;
				 }
				}
				else
				{
				  if(roomTable.containsKey(id+"_res"))
				  {
					 roomTable.remove(id+"_res");
					 removeEmpty(parent,"RA1:");
					 removeEmpty(parent,"RA2:");
					 isCleared=true;
				}
				}
				
			    checkformObj.setChecked(false);
			 }
		
		}
selectRooms.clear();		
return isCleared;		
}

//This method validate that selected dob is valid or not. It don't allow future date.

public boolean checkDOB(String datepicker1) {
		
	boolean isDateValid = true;
	Object rootFormValue = self.getRoot();
	isDateValid =uiLibraryUtil.checkDOB(datepicker1,rootFormValue,formValues); 
	return isDateValid;
}


// SORTING EVENT START
public void enableSorting(String listHeaderId, String fieldName)
{
	Object rootFormValue = self.getRoot();
	Listheader listheader = rootFormValue.getVariable(listHeaderId, true);
	listheader.setSortAscending(new RowLabelComparator(fieldName, true));
	listheader.setSortDescending(new RowLabelComparator(fieldName, false));
}

public class RowLabelComparator extends FieldComparator 
{
	public RowLabelComparator(String orderBy, boolean asc) { super(orderBy, asc); }
	public int compare(Object o1, Object o2) { return 0; }
}

public void createSortingEvent(String listId, String pagingId)
{
	
	Object rootFormValue = self.getRoot();
	Listbox _listBox = rootFormValue.getVariable(listId, true);
	setListSortListeners(_listBox);
}

/*
 * Sets the listeners. <br>
 * <br>
 * 1. "onSort" for all listheaders that have a sortDirection declared. <br>
 */
private void setListSortListeners(Listbox listBox) 
{
	/*
	 * Add 'onSort' listeners to the used listheader components. All not
	 * used Listheaders must me declared as:
	 * listheader.setSortAscending(""); <br>
	 * listheader.setSortDescending(""); <br>
	 */
	Listhead listhead = listBox.getListhead();
	List list = listhead.getChildren();
	for (Object object : list)
	{
		if (object instanceof Listheader)
		{
			Listheader lheader = (Listheader) object;
			if (lheader.getSortAscending() != null || lheader.getSortDescending() != null)
			{
				lheader.addEventListener("onSort", new OnSortEventListener());
			}
		}
	}
}

public class OnSortEventListener implements EventListener
{
	public void onEvent(Event event) throws Exception
	{
		String[] listInfo = PagingInfo.getListInfo();
		final String componentId = listInfo[0];
		final String method = listInfo[1];
		final String classname = listInfo[2];
		final String condition = listInfo[3];
		final String listName = listInfo[4];
		final String pagingId=listInfo[5];
		Object rootFormValue = self.getRoot();
	    Object paging = rootFormValue.getVariable(pagingId, true);		
		paging.setActivePage(0);
		String orderBy = "", conditions = condition;
		final Listheader lh = (Listheader) event.getTarget();
		final String sortDirection = lh.getSortDirection();
		if (sortDirection.equalsIgnoreCase("ascending"))
		{ 
		    
			final Comparator cmpr = lh.getSortDescending();
			if (cmpr instanceof FieldComparator)
			{
				orderBy = ((FieldComparator) cmpr).getOrderBy();
				orderBy = orderBy.replace("DESC", "desc").trim();
				orderBy = orderBy.replace("ASC", "asc").trim();
			}
		}
		else if (sortDirection.equalsIgnoreCase("descending") || sortDirection.equalsIgnoreCase("natural") || sortDirection.equalsIgnoreCase(""))
		{
			final Comparator cmpr = lh.getSortAscending();
			if (cmpr instanceof FieldComparator)
			{
				orderBy = ((FieldComparator) cmpr).getOrderBy();
				orderBy = orderBy.replace("DESC", "desc").trim();
				orderBy = orderBy.replace("ASC", "asc").trim();
			}
		}
		
		if (condition.indexOf(" orderby ") > 0)
		{
			conditions = conditions.substring(0, condition.indexOf(" orderby "));
		}
	
		if(orderBy!=null && !orderBy.equalsIgnoreCase(""))
		{
		conditions = conditions + " orderby " + orderBy;
		}	
		invokeComponent(componentId, method, classname, conditions);
		displayDataInControl(listName);		
		createPagingEvent(listName);
	}
}

//for removing given id from formvalues hashtable
public void removeFromFormValue(String id)
{
	formValues.remove(id);
}
// SORTING EVENT END	 

//this method is added to validate the formValues in case user has clicked the logo
//added by pra on 10 aug 2009.
public boolean clientSideValidationForLogo(String formId)
{
	boolean isValid = true;
	Object rootFormValue = Path.getComponent("//"+formId+"//"+formId);
	Window formObj = rootFormValue.getVariable(formId,true);
	formValues=createFormValues(formObj,formValues);
	String msg=uiLibraryUtil.clientSideValidation(formObj,formValues );
	
	if(msg != null)
		{
			msg = msg.trim();
		}
		try {
			if(msg !="" && msg.length() !=0)
			{
				//Messagebox.show(msg,title,Messagebox.OK, Messagebox.ERROR);
				//message(msg);
				isValid = false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 	return isValid;
}

//this is added to get the room assginment id.
public String getRoomAssginmentId(String id)
{
	String assginId=null;
	List roomAssign=roomTable.get(id+"_ras");
	if(roomAssign!=null)
	{
		assginId=roomAssign.get(0);
	}
	return assginId;
}
public void clearRoomList(String id)
{
selectRooms.clear();
}

public List getAllRowsListData(String gridId)
{
	List returnDataList=new ArrayList();
	Object rootFormValue = self.getRoot();
	Object listObject = rootFormValue.getVariable(gridId,true);
	List childrenObjects=listObject.getChildren();
	if(childrenObjects!=null && childrenObjects.size()>1)
	{
		for(int i=1;i<childrenObjects.size();i++)
		{
			Object obj=childrenObjects.get(i);
			List rowData=new ArrayList();
			List nextChilds=obj.getChildren();
			for(int j=0;j<nextChilds.size();j++)
			{ 
			rowData.add(nextChilds.get(j).getValue());
			}
			returnDataList.add(rowData);
		}
	}
	return returnDataList;
}


public void saveTempQuery(String str)
{
		
	Object rootFormValue = self.getRoot();    
	String countStr = null;
	countStr = formValues.get("counter");
	formValues.remove("grid40");
	formValues.remove("textbox42");
	formValues.remove("textbox41");	
	formValues.remove("textbox43");		
	if(countStr!=null)
	{
		int value = Integer.parseInt(countStr);
		
invokeComponent("dbComponent","updateRecord","com.oxymedical.component.db.DBComponent","delete from neiswispicdb.tempquerydetail");

		boolean flag = false;
		boolean isVariable = true;
		formValues.remove("combobox38");	
		formValues.remove("combobox39");
		formValues.remove("combobox37");
		formValues.remove("textbox161");
		formValues.remove("textbox160");
		formValues.remove("textbox159");
		formValues.remove("checkbox3");
		
		for(int counter=1;counter<=value;counter++)
		{
			String categoryValue = formValues.get("category"+counter);
			if(categoryValue !=null)
			{
				formValues.put("combobox38",categoryValue);
				flag = true;
			}
			String fieldValue = formValues.get("field"+counter);
			if(fieldValue !=null)
			{
				
				formValues.put("combobox39",fieldValue);
				flag = true;
			}
			String variableValue = formValues.get("variable"+counter);
			if(variableValue !=null)
			{
			variableValue =variableValue.trim();
			
				if(variableValue.length() >0)
				{
					formValues.put("textbox159",variableValue);
					flag = true;
					isVariable = false;
				}
			}
			if(isVariable)
			{
				String range1 = formValues.get("firstrange"+counter);
				if(range1 !=null)
				{
					
					formValues.put("textbox160",range1);
					flag = true;
				}
				String range2 = formValues.get("secondrange"+counter);
				if(range2 !=null)
				{
					
					formValues.put("textbox161",range2);
					flag = true;
				}
			}
			String logicalValue = formValues.get("logical"+counter);
			if(logicalValue !=null)
			{
				formValues.put("combobox37",logicalValue);
				
			}
			String checkBoxValue = formValues.get("check"+counter);
			if(checkBoxValue !=null)
			{
				formValues.put("checkbox3",checkBoxValue);
				
			}
			
			if(flag)
			{	
			
			invokeComponent("dbComponent","save","com.oxymedical.component.db.DBComponent","QueryBuilder");
			formValues.remove("combobox39");	
			formValues.remove("combobox37");
			formValues.remove("combobox38");
			formValues.remove("textbox161");
			formValues.remove("textbox160");
			formValues.remove("textbox159");
			formValues.remove("checkbox3");
			isVariable = true;
			flag = false;
			}
			
		}
		
	}
	else
	{
	       
			boolean flag = false;
			boolean isVariable = true;
			formValues.remove("combobox39");	
			formValues.remove("combobox37");
			formValues.remove("combobox38");
			formValues.remove("textbox161");
			formValues.remove("textbox160");
			formValues.remove("textbox159");
			formValues.remove("checkbox3");
			invokeComponent("dbComponent","updateRecord","com.oxymedical.component.db.DBComponent","delete from neiswispicdb.tempquerydetail");
		
			String categoryValue = formValues.get("category1");
			if(categoryValue !=null)
			{
				formValues.put("combobox38",categoryValue);
			}
			String fieldValue = formValues.get("field1");
			if(fieldValue !=null)
			{
				flag = true;
				formValues.put("combobox39",fieldValue);
			}
			String variableValue = formValues.get("variable1");
			
			if(variableValue !=null)
			{
				
				formValues.put("textbox159",variableValue);
				flag = true;
				isVariable = false;
			}
			
			if(isVariable)
			{
				String range1 = formValues.get("firstrange1");
				if(range1 !=null)
				{
					
					formValues.put("textbox160",range1);
				}
				String range2 = formValues.get("secondrange1");
				if(range2 !=null)
				{
					formValues.put("textbox161",range2);
				}
			}
			String logicalValue = formValues.get("logical1");
			if(logicalValue !=null)
			{
				formValues.put("combobox37",logicalValue);
			}
			String checkBoxValue = formValues.get("check"+counter);
			if(checkBoxValue !=null)
			{
				formValues.put("checkbox3",checkBoxValue);
				
			}
			if(flag)
			{
			
				invokeComponent("dbComponent","save","com.oxymedical.component.db.DBComponent","QueryBuilder");
			formValues.remove("combobox39");	
			formValues.remove("combobox37");
			formValues.remove("combobox38");
			formValues.remove("textbox161");
			formValues.remove("textbox160");
			formValues.remove("textbox159");
			formValues.remove("checkbox3");
				
			}
			
	}
}

//to check if a record exists with id = value. call this method after "execlist"
public boolean isRecordExistCompareValues(String value)
{
	boolean result=false;
	if(returnHicData ==null)
	{
				
			return;
	}
	else
	{  
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return false;
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		if(allValues==null)
		{
		return false;
		}
		else if(allValues!=null && allValues.length>0)
		{  
			String dbValue=allValues[0][0];
			 if(value.equalsIgnoreCase(dbValue))
			 {
			 result=true;
			 }
		}
	}
	return result;
}

// This Method Clear all condition value in query builder.
public void clearAllQueryField(String str)
{
	clearQueryValue("logical2");
	clearQueryValue("category1");
	clearQueryValue("field1");
	clearQueryValue("check1");
	clearQueryValue("variable1");
	clearRangeValue("range");
}

public showFileDownload(String filePath)
{
	if(filePath==null || filePath.isEmpty())
		return false;
	File file = new File(filePath);
	Filedownload.save(file,"text/plain");
	return true;
}
public void displayRoomDetailsInPopUp(List details,String[] data)
{
	
	Object rootFormValue = self.getRoot();
	for(int i=0;i<details.size();i++)
	{
		String value=details.get(i).get(0);
		if(value!=null)
		{
			if(value.indexOf("PH:")>=0)
			{
			Object textObject = rootFormValue.getVariable(data[0],true);
			textObject.text=value;
			}
			else if(value.indexOf("ST:")>=0)
			{
			Object textObject = rootFormValue.getVariable(data[1],true);
			textObject.text=value;
			}
			else if(value.indexOf("RA1:")>=0)
			{
			Object textObject = rootFormValue.getVariable(data[2],true);
			textObject.text=value;
			}
			else if(value.indexOf("RA2:")>=0)
			{
			Object textObject = rootFormValue.getVariable(data[5],true);
			textObject.text=value;
			}
			else if(value.indexOf(":")>=0)
			{
			Object textObject = rootFormValue.getVariable(data[4],true);
			textObject.text=value;
			}
			else 
			{
			Object textObject = rootFormValue.getVariable(data[3],true);
			textObject.text=value;
			}
		}
	}				
}
public storRoomValue(String id)
{
	session.setAttribute(id,roomTable);
}
public restorRoomValue(String id)
{
	if(session.getAttribute(id) !=null)
	roomTable = session.getAttribute(id);
}

public void validateDataInQueryObject(HtmlBasedComponent formObj) {
		List childElement = formObj.getChildren();
		Iterator iter = childElement.iterator();
		while (iter.hasNext()) {
			Object value = iter.next();
			if (value instanceof InputElement) {
				validateDataInQueryObject(((InputElement) value));
			
				if ((((InputElement) value).getId().indexOf("category") >= 0)||
				(((InputElement) value).getId().indexOf("field") >= 0) ||
				(((InputElement) value).getId().indexOf("variable") >= 0)||
				(((InputElement) value).getId().indexOf("firstrange") >= 0)||
				(((InputElement) value).getId().indexOf("secondrange") >= 0)||
				(((InputElement) value).getId().indexOf("logical") >= 0)
				)	 
				{
					String valueObject=((InputElement) value).getText();
					if(valueObject==null || valueObject.isEmpty())
					{
					String errorBGColor ="background:#fad8d8";
					((InputElement) value).setStyle(errorBGColor);
					}
					else
					{
						String normalBGColor ="background:#FFFFFF";
					   ((InputElement) value).setStyle(normalBGColor);
					}
				} 
			}
			
			else {
				validateDataInQueryObject(((HtmlBasedComponent) value));
			}
		}
	}
public Object getComponentFromPage(String formId,String componentId)
{
	Object rootFormValue = Path.getComponent("//"+formId+"//"+formId);
	Object formObj = rootFormValue.getVariable(componentId,true);
	return formObj;
}
Hashtable rowmap = new Hashtable();
public void addGridToFrame(Object obj, String headers, String types, String defaultvalues)
{
	boolean error = false;


	if(obj !=null)
	{
		obj.setStyle("overflow:auto;");
	}
	else
	{
		alert("Error in PatientAssignment:Parent is null.");
		return;
	}
	
	List children = obj.getChildren();
	if(children!=null && children.size() > 0)
	{
		Object[] charr = children.toArray();
		for(int i = 0; i < charr.length; i++)
		{
				obj.removeChild(charr[i]);
		}
	}
	
	Grid grid= new Grid();
	grid.setId("PatientAssignmentGrid");
	Columns clmns = new Columns();
	clmns.setSizable(true);
	Rows rws = new Rows();
	grid.setHeight("600px");
	grid.appendChild(clmns);
	grid.appendChild(rws);
	String gridStyle = "background-color:#FFFFFF;border:1px solid";
	grid.setStyle(gridStyle);
	rws.setStyle(gridStyle);
	grid.setFixedLayout(true);
	
	String[] heads = headers.split(",");
	String[] componentNames = types.split(",");
	String[] defaults = defaultvalues.split(":");

	if(heads != null && heads.length > 0)
	{
		for(int i = 0; i < heads.length; i++)
		{
			Column clmn = new Column();
			String Style = "sort:auto;height:30px;width:100px";
			clmn.setStyle(Style);
			clmn.setLabel(heads[i]);
			clmns.appendChild(clmn);
		}
	}
	if(heads.length == componentNames.length)
	{

		String[][] allValues = null;
		if(null != returnHicData)
		{	
			IData dataUnit = returnHicData.getData();
			List listValue = null;
			if(dataUnit.getQueryData().getListData()!=null)
			{
				listValue = dataUnit.getQueryData().getListData();
				allValues = dataUnit.getQueryData().iterateListData(listValue);
			}
			else
			{
				error = true;
			}
		}


		if(!error && allValues != null && allValues.length > 0 && allValues[0].length >= componentNames.length)
		{
			int totalfields = allValues[0].length;
			String selected = "null";
			for(int j = 0; j < allValues.length; j++)
			{
				Row row = new Row();
				row.setId("row_pa_"+j);
				rowmap.put(row.getId(), allValues[j]);
				for(int i = 0; i < componentNames.length; i++)
				{
					if(componentNames[i].equalsIgnoreCase("label"))
					{
						Label label = null;
						if(defaults[i] != null && defaults[i].equalsIgnoreCase("user"))
						{
							String name = getUserNameBasedOnUserId(allValues[j][i]);
							label = new Label(name);
						}
						else
						{
							label = new Label(allValues[j][i]);
						}
						row.appendChild(label);
					}
					else if(componentNames[i].equalsIgnoreCase("textbox"))
					{
						Textbox txtbox = new Textbox(allValues[j][i]);
						txtbox.setRows(4);
						txtbox.setWidth("98%");
						txtbox.setId("auto_textbox"+j+""+i);
						row.appendChild(txtbox);
					}
					else if(componentNames[i].equalsIgnoreCase("Checkbox"))
					{
						Checkbox chkbox = new Checkbox();
						if(allValues[j][i]!=null && allValues[j][i].equalsIgnoreCase("1"))
							chkbox.setChecked(true);
						row.appendChild(chkbox);
						chkbox.setId("auto_checkbox"+j+""+i);
					}
					else if(componentNames[i].equalsIgnoreCase("Combobox"))
					{
						Combobox cmbbox = new Combobox();
						cmbbox.setWidth("70px");
						cmbbox.setReadonly(true);
						cmbbox.setId("auto_combobox"+j+""+i);
						row.appendChild(cmbbox);
						if(defaults[i] != null && !defaults[i].isEmpty())
						{
							String retval = invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent",defaults[i]);
							showComboData(null,cmbbox);
							cmbbox.setValue(allValues[j][i]);
						}
					}
				}

				row.addEventListener("onClick",new EventListener()
				{
					public void onEvent(Event event)
					{
					
						List ch = rws.getChildren();
						if(ch!=null && ch.size() > 0 && !selected.equals(row.getId()))
						{
							Object[] arr = ch.toArray();
							for(int i = 0; i < arr.length ; i++)
							{
								if(arr[i] instanceof Row )
								{
									arr[i].setStyle("background-color:#FFFFFF;border:1px solid");
								}
							}
							row.setStyle("background-color:#DDD9C3;border:1px solid");
							selected = row.getId();
						}
						String[] data = rowmap.get(row.getId());
						String patientid = data[totalfields-2];
						String scheduleid = data[totalfields-1];
						updateSession("patientid", patientid);
						updateSession("scheduleid", scheduleid);
					}
				});
				row.setStyle("background-color:#FFFFFF;border:1px solid");
				rws.appendChild(row);
			}
		}
	}
	else
	{
		alert("Error in PatientAssignment:Headers count is not equal to types count.");
	}
	obj.appendChild(grid);
}

public void saveDataFromGrid(String GridId)
{
	Object rootFormValue = self.getRoot();
	Object obj =rootFormValue.getVariable(GridId, true);
	
	Grid grid = (Grid)obj;
	List grid_child = grid.getChildren();
	Rows rws = null;
	if(grid_child!=null && grid_child.size() == 2)
	{
		if(grid_child.toArray()[1] instanceof Rows)
			rws = grid_child.toArray()[1];
	}
	
	if(rws!=null)
	{
		List ch = rws.getChildren();
		if(ch!=null && ch.size() > 0)
		{
			Object[] arr = ch.toArray();
			for(int i = 0; i < arr.length ; i++)
			{
				if(arr[i] instanceof Row )
				{
					List elements = arr[i].getChildren();
					Object[] elements_arr = elements.toArray();
					String[] data = rowmap.get("row_pa_"+i);
					String patientid = "";
					String scheduleid = "";
					if(data!=null)
					{
						scheduleid = data[data.length-1];
						String query1 = "update neiswispicdb.roomassginment set stat:=#stat#,arrived:=#arrived#,comments:=#comments# conditions roomassginment.schduleid:="+scheduleid;
						String query2 = "update neiswispicdb.schedule set studytype:=#studytype#,assigntoroom:=#assigntoroom# conditions schedule.id:="+scheduleid;
						if(12 < elements_arr.length)
						{
							Checkbox Stat = (Checkbox)elements_arr[5];
							Combobox type = (Combobox)elements_arr[7];
							Checkbox Arrived = (Checkbox)elements_arr[9];
							Checkbox PtinRoom = (Checkbox)elements_arr[10];
							Textbox Comments = (Textbox)elements_arr[11];
							if(Stat.isChecked())
								query1 = query1.replace("#stat#","1");
							else
								query1 = query1.replace("#stat#","0");
								
							
							if(Arrived.isChecked())
								query1 = query1.replace("#arrived#","1");
							else
								query1 = query1.replace("#arrived#","0");
								
							query1 = query1.replace("#comments#",Comments.getText());
							
							if(PtinRoom.isChecked())
								query2 = query2.replace("#assigntoroom#","1");
							else
								query2 = query2.replace("#assigntoroom#","0");

							query2 = query2.replace("#studytype#",getComboItemId1(type.getId()));
						}
						String retval1 = invokeComponent("dbComponent","updateRecord","com.oxymedical.component.db.DBComponent",query1);
						String retval2 = invokeComponent("dbComponent","updateRecord","com.oxymedical.component.db.DBComponent",query2);
								
					}
				}
			}
		}
	}
	message("Fields updated.");
}


public String checkIntegerValueWithOperator(Object val1, Object val2, String checkType)
{  
	try
	{
	    int value1=Integer.parseInt(val1);
	    int value2=Integer.parseInt(val2);
	    if(checkType.equals("EQUAL"))
	    {
	      if(value1==value2)
	      return "true";
		  else
		  message("Please select value equal to "+value2+".");
	    }
	    else if(checkType.equals("GREATERTHAN"))
	    {
	     if(value1>=value2)
	      return "true";
		  else
		  message("Please select value greater than or equal to "+value2+".");
		 
	    }
	    else if(checkType.equals("SMALLERTHAN"))
	    {
	     if(value1<=value2)
	      return "true";
		  else
		  message("Please select value smaller than or equal to "+value2+".");
	    }
	}
	catch(Exception)
	{
	message("Please give numeric value only.");
	}	
	    return null;
}
public void createDataInChart(String barchart,String textboxId, String textboxId1,String textboxId2)
{
Object rootFormValue = self.getRoot();
Object obj =rootFormValue.getVariable(barchart, true);
Object objText =rootFormValue.getVariable(textboxId, true);
Object objText2 =rootFormValue.getVariable(textboxId1, true);
Object objText3 =rootFormValue.getVariable(textboxId2, true);


if(returnHicData ==null)
	{
				
	return;
	}
	else
	{
  	  List returnList=returnHicData.getData().getList();
		if(returnList!=null && returnList.size()>0)
	{
      	 int value=(Integer)returnList.get(0);
	int value2=(Integer)returnList.get(2);
	objText.setValue(value.toString());
	objText2.setValue(value2.toString());
	double[] errorVector=returnList.get(1);
	double[] errorVector2=returnList.get(3);

    String value3=(String)returnList.get(4);
    objText3.setValue(value3);
	XYModel xymodel = new SimpleXYModel();
	for(int i=0;i<errorVector.length;i+=10)
	{
	xymodel.addValue("Start error", new Integer(i), new Double(errorVector[i]));
	xymodel.addValue("End error", new Integer(i), new Double(errorVector2[i]));
	}

	obj.setModel(xymodel);

	}

	}
}
public String uploadPSGFile(String id)
{
 String path=null;
 org.zkoss.util.media.Media media = Fileupload.get();
 if (media != null) 
 { 
  String contentType=media.getContentType();
  InputStream inputStream=media.getStreamData();
  String fileName=media.getName();
  String folderPath=PropertyUtil.setUpProperties("PSG_FILE_LOC");
  path=writeToFile(folderPath,fileName,inputStream);
  return path;
 }
 return null;
}

public void setWorkFlowData(String workFlowName,String workFlowId,String isVisual,String isWorkflowNameChanged )
 {
	
	 LinkedList nodeSelected=createListValues(session.getAttribute("fields"));
	 formValues.put("nodeSelected",nodeSelected);
	 formValues.put("workFlowName",workFlowName);
	 formValues.put("workFlowId",workFlowId);
	 formValues.put("isVisual",isVisual);
	 formValues.put("isWorkflowNameChanged",isWorkflowNameChanged);
	 invokeComponent("com.oxyentmedical.component.workflowcomponent","SaveWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","");
	 updateSessionList("fields",null);
 }

public void displayWorkflowStatus(String controlObj,String patientId,String patientMRN,String nodeStatus)
{
    Object rootFormValue = self.getRoot();
	Object elementObj = rootFormValue.getVariable(controlObj,true);
	if(patientId!=null)
	formValues.put( "PATIENTID", patientId);	
	if(patientMRN!=null)
	formValues.put( "PATIENTMRN", patientMRN);
	if(nodeStatus!=null && nodeStatus.equalsIgnoreCase("Completed") )
	formValues.put( "NODESTATUS","C");
	if(nodeStatus!=null && nodeStatus.equalsIgnoreCase("Exception") )
	formValues.put( "NODESTATUS","E");
	if(nodeStatus!=null && nodeStatus.equalsIgnoreCase("Waiting") )
	formValues.put( "NODESTATUS","1");
	if(nodeStatus!=null && nodeStatus.equalsIgnoreCase("Processing") )
	formValues.put( "NODESTATUS","2");
	if((nodeStatus!=null && nodeStatus.equalsIgnoreCase("All"))|| nodeStatus==null)
	formValues.remove("NODESTATUS");

invokeComponent("com.oxyentmedical.component.workflowcomponent","getworkflowtoolstatus","com.oxymedical.component.workflowComponent.WorkflowComponent","workflowinfo.name,nodeinfo.nodename,patient.PATIENTID,patient.PATIENTMRN,nodedetails.nodeDescription,dataobject.nodeexecutionstatus,schedule.SCHEDULEID,dataobjectmetadata.datavalue,dataobject.toolstartdate,dataobject.toolstarttime");
	IData dataUnitQuery = returnHicData.getData();
	List listValueQuery = null;
	if(dataUnitQuery.getQueryData().getListData()!=null)
	{
		listValueQuery = dataUnitQuery.getQueryData().getListData();
	}
	else
	{
		ListModel myModel = new ListModelList();
		elementObj.setModel(myModel);
		elementObj.setItemRenderer(new ItemRendererArray());
		return;
	}
	String[][] allValues= dataUnitQuery.getQueryData().iterateListData(listValueQuery);
	if(allValues==null)
	{
	ListModel myModel = new ListModelList();
	elementObj.setModel(myModel);
	elementObj.setItemRenderer(new ItemRendererArray());
	return;
	}
	String[][] displayResult = new String[allValues.length][11];
	for(int j = 0; j < allValues.length; j++)
			{
			   String[] values=allValues[j];
					for(int i=0;i<values.length;i++)
					{
						 if(values[i]!=null)
						 {
							 if(values[i].equalsIgnoreCase("1") && i==5)
							 {
							  displayResult[j][i]="Waiting";
							 }
							 else if((values[i].equalsIgnoreCase("2") || values[i].equalsIgnoreCase("0")) && i==5 )
							 {
							 displayResult[j][i]="Processing";
							 }
							 else if(values[i].equalsIgnoreCase("c") && i==5 )
							 {
							 displayResult[j][values.length-1]="100%";
							 displayResult[j][i]="Completed";
							 }
							 else if(values[i].equalsIgnoreCase("E") && i==5 )
							 {
							 displayResult[j][i]="Exception";
							 }
							 else if(i==(values.length-1))
							 {
							 if(values[5].equalsIgnoreCase("c"))
							 {
							  displayResult[j][i]="100%";
							 }
							 else
							 {
							 displayResult[j][i]=values[i]+"%";
							 }
							 }
							 else
							 displayResult[j][i]=values[i];
						 }
					}
			}
	ListModel myModel = new ListModelList(displayResult);
	elementObj.setModel(myModel);
	elementObj.setItemRenderer(new ItemRendererArray());		
}

public void displayWorkflowStatusForVisulaizer(String controlObj,String patientId,String patientMRN, String scheduleID)
{
	Object rootFormValue = self.getRoot();
	Object elementObj = rootFormValue.getVariable(controlObj,true);
	String userId = getSessionData("userId");
	invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get workflowinfo.name,nodeinfo.nodename,nodedetails.nodeDescription,dataobject.nodeexecutionstatus" +
				" from appadmin.dataobjectmetadata,appadmin.workflownodeinfo,appadmin.nodeinfo joins " +
				"leftjoin dataobjectmetadata.dataobject:=dataobject.id," +
				"leftjoin workflownodeinfo.workflowid:=workflowinfo.id," +
				"leftjoin nodeinfo.nodeDetail:=nodedetails.id " +
				"conditions dataobject.nodeexecutionstatus:=[C] and dataobject.userid:=["+userId+"]" +
						" and dataobjectmetadata.datakey:=[PATIENTID] and workflownodeinfo.nodeid:=nodeinfo.id and dataobject.workflownodeid:=workflownodeinfo.id and dataobjectmetadata.datavalue:="+patientId);
	IData dataUnitQuery1 = returnHicData.getData();
	List listValueQuery1 = null;
	if(dataUnitQuery1.getQueryData().getListData()!=null)
	{
		listValueQuery1 = dataUnitQuery1.getQueryData().getListData();
	}
	else
	{
			return ;
	}
	String[][] allValuesQuery1 = dataUnitQuery1.getQueryData().iterateListData(listValueQuery1);
	if(allValuesQuery1==null)return;
	
	invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get workflowinfo.name,nodeinfo.nodename,nodedetails.nodeDescription,dataobject.nodeexecutionstatus" +
				" from appadmin.dataobjectmetadata,appadmin.workflownodeinfo,appadmin.nodeinfo joins " +
				"leftjoin dataobjectmetadata.dataobject:=dataobject.id," +
				"leftjoin workflownodeinfo.workflowid:=workflowinfo.id," +
				"leftjoin nodeinfo.nodeDetail:=nodedetails.id " +
				"conditions dataobject.nodeexecutionstatus:=[C] and dataobject.userid:=["+userId+"]" +
						" and dataobjectmetadata.datakey:=[PATIENTMRN] and workflownodeinfo.nodeid:=nodeinfo.id and dataobject.workflownodeid:=workflownodeinfo.id and dataobjectmetadata.datavalue:="+patientMRN);			
	IData dataUnitQuery2 = returnHicData.getData();
	List listValueQuery2 = null;
	if(dataUnitQuery2.getQueryData().getListData()!=null)
	{
		listValueQuery2 = dataUnitQuery2.getQueryData().getListData();
	}
	else
	{
		return ;
	}
	String[][] allValuesQuery2 = dataUnitQuery2.getQueryData().iterateListData(listValueQuery2);
	if(allValuesQuery2==null) return;	
	
	
	invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get workflowinfo.name,nodeinfo.nodename,nodedetails.nodeDescription,dataobject.nodeexecutionstatus" +
				" from appadmin.dataobjectmetadata,appadmin.workflownodeinfo,appadmin.nodeinfo joins " +
				"leftjoin dataobjectmetadata.dataobject:=dataobject.id," +
				"leftjoin workflownodeinfo.workflowid:=workflowinfo.id," +
				"leftjoin nodeinfo.nodeDetail:=nodedetails.id " +
				"conditions dataobject.nodeexecutionstatus:=[C] and dataobject.userid:=["+userId+"]" +
						" and dataobjectmetadata.datakey:=[SCHEDULEID] and workflownodeinfo.nodeid:=nodeinfo.id and dataobject.workflownodeid:=workflownodeinfo.id and dataobjectmetadata.datavalue:="+scheduleID);			
	IData dataUnitQuery3 = returnHicData.getData();
	List listValueQuery3 = null;
	if(dataUnitQuery3.getQueryData().getListData()!=null)
	{
		listValueQuery3 = dataUnitQuery3.getQueryData().getListData();
	}
	else
	{
		return ;
	}
	String[][] allValuesQuery3 = dataUnitQuery3.getQueryData().iterateListData(listValueQuery3);
	if(allValuesQuery3==null) return;	
	
	int stringLength = allValuesQuery3.length;
	String[][] displayResult = new String[stringLength][4];
	int counter=0;
	for(int i=0;i<allValuesQuery3.length;i++)
	{
	
		if(allValuesQuery1[i][1].indexOf("inputscreen")>=0)
		{
		continue;
		}
		else
		{
		displayResult[counter][0] = allValuesQuery1[i][0];
		displayResult[counter][1] = allValuesQuery1[i][1];
		displayResult[counter][2] = allValuesQuery1[i][2];
		displayResult[counter][3] = "Completed";
		counter++;
		}
	}
	ListModel myModel = new ListModelList(displayResult);
	elementObj.setModel(myModel);
	elementObj.setItemRenderer(new ItemRendererArray());
}



void setPACSInfo(){
	addFormValue("pacsIpAddress", PropertyUtil.setUpProperties("PACS_IPADDRESS"));
	addFormValue("pacsPortNumber", PropertyUtil.setUpProperties("PACS_PORTNUMBER"));
	addFormValue("pacsServerPath", PropertyUtil.setUpProperties("PACS_SERVERPATH"));
}

void getPatientInfo()
{
	Object rootFormValue = self.getRoot();
	
	Object pMRN = rootFormValue.getVariable("PATIENTMRN",true);
	pMRN.setValue(getSessionData("patientmrn"));
	
	Object pID = rootFormValue.getVariable("PATIENTID",true);
	pID.setValue(getSessionData("rowId"));

	Object pSCHEDULEID = rootFormValue.getVariable("SCHEDULEID",true);
	pSCHEDULEID.setValue(getSessionData("scheduleId"));

	Object pWORKINGAREA = rootFormValue.getVariable("WORKINGAREA",true);
	pWORKINGAREA.setValue(getSessionData("workingarea"));
	
	addFormValue("PATIENTMRN", getSessionData("patientmrn"));
	addFormValue("PATIENTID", getSessionData("rowId"));
	addFormValue("SCHEDULEID", getSessionData("scheduleId"));
	addFormValue("SCHEDULEWORKAREA", getSessionData("workingarea"));
}
public void updateSessionDataForPatient()
{
	updateSession("rowId", null);
	updateSession("patientmrn", null);
	updateSession("scheduleId", null);
	updateSession("workingarea", null);
	updateSession("workflowname", null);
	updateSession("nodename", null);
}
	

public void displayWorkflowErrorStatus(String controlObj)
{
	Object rootFormValue = self.getRoot();
	Object elementObj = rootFormValue.getVariable(controlObj,true);
	
	String userId = getSessionData("userId");
	invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get workflowinfo.name,nodedetails.nodeDescription,dataobjectmetadata.datavalue,errorinfo.errormessage," +
				"dataobject.nodeexecutionstatus,nodeinfo.nodename from appadmin.dataobjectmetadata,appadmin.workflownodeinfo,appadmin.nodeinfo," +
				"appadmin.errorinfo joins leftjoin dataobjectmetadata.dataobject:=dataobject.id,leftjoin workflownodeinfo.workflowid:=workflowinfo.id," +
				"leftjoin nodeinfo.nodeDetail:=nodedetails.id conditions dataobject.nodeexecutionstatus:=[E] and dataobject.userid:=["+userId+"]" +
				" and dataobjectmetadata.datakey:=[PATIENTID] and workflownodeinfo.nodeid:=nodeinfo.id and " 
						+"dataobject.workflownodeid:=workflownodeinfo.id and errorinfo.dataobjectid:=dataobject.id");
	IData dataUnitQuery1 = returnHicData.getData();
	List listValueQuery1 = null;
	if(dataUnitQuery1.getQueryData().getListData()!=null)
	{
		listValueQuery1 = dataUnitQuery1.getQueryData().getListData();
	}
	else
	{
			return ;
	}
	String[][] allValuesQuery1 = dataUnitQuery1.getQueryData().iterateListData(listValueQuery1);
	if(allValuesQuery1==null)
	if(allValuesQuery1==null)
	{
	ListModel myModel = new ListModelList();
	elementObj.setModel(myModel);
	elementObj.setItemRenderer(new ItemRendererArray());
	return;
	}
	invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get workflowinfo.name,nodedetails.nodeDescription,dataobjectmetadata.datavalue,errorinfo.errormessage," +
				"dataobject.nodeexecutionstatus,nodeinfo.nodename from appadmin.dataobjectmetadata,appadmin.workflownodeinfo,appadmin.nodeinfo," +
				"appadmin.errorinfo joins leftjoin dataobjectmetadata.dataobject:=dataobject.id,leftjoin workflownodeinfo.workflowid:=workflowinfo.id," +
				"leftjoin nodeinfo.nodeDetail:=nodedetails.id conditions dataobject.nodeexecutionstatus:=[E] and dataobject.userid:=["+userId+"]" +
				" and dataobjectmetadata.datakey:=[PATIENTMRN] and workflownodeinfo.nodeid:=nodeinfo.id and " 
						+"dataobject.workflownodeid:=workflownodeinfo.id and errorinfo.dataobjectid:=dataobject.id");
	IData dataUnitQuery2 = returnHicData.getData();
	List listValueQuery2 = null;
	if(dataUnitQuery2.getQueryData().getListData()!=null)
	{
		listValueQuery2 = dataUnitQuery2.getQueryData().getListData();
	}
	else
	{
		return ;
	}
	String[][] allValuesQuery2 = dataUnitQuery2.getQueryData().iterateListData(listValueQuery2);
	if(allValuesQuery2==null)
	{
	ListModel myModel = new ListModelList();
	elementObj.setModel(myModel);
	elementObj.setItemRenderer(new ItemRendererArray());
	return;
	}
		invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get workflowinfo.name,nodedetails.nodeDescription,dataobjectmetadata.datavalue,errorinfo.errormessage," +
				"dataobject.nodeexecutionstatus,nodeinfo.nodename from appadmin.dataobjectmetadata,appadmin.workflownodeinfo,appadmin.nodeinfo," +
				"appadmin.errorinfo joins leftjoin dataobjectmetadata.dataobject:=dataobject.id,leftjoin workflownodeinfo.workflowid:=workflowinfo.id," +
				"leftjoin nodeinfo.nodeDetail:=nodedetails.id conditions dataobject.nodeexecutionstatus:=[E] and dataobject.userid:=["+userId+"]" +
				" and dataobjectmetadata.datakey:=[SCHEDULEID] and workflownodeinfo.nodeid:=nodeinfo.id and " 
						+"dataobject.workflownodeid:=workflownodeinfo.id and errorinfo.dataobjectid:=dataobject.id");
	IData dataUnitQuery3 = returnHicData.getData();
	List listValueQuery3 = null;
	if(dataUnitQuery3.getQueryData().getListData()!=null)
	{
		listValueQuery3 = dataUnitQuery3.getQueryData().getListData();
	}
	else
	{
		return ;
	}
	String[][] allValuesQuery3 = dataUnitQuery3.getQueryData().iterateListData(listValueQuery3);
	if(allValuesQuery3==null) return;		
	int stringLength = allValuesQuery3.length;
	String[][] displayResult = new String[stringLength][8];
	for(int i=0;i<allValuesQuery3.length;i++)
	{
		displayResult[i][0] = allValuesQuery1[i][0];
		displayResult[i][1] = allValuesQuery1[i][1];
		displayResult[i][2] = allValuesQuery1[i][2];
		displayResult[i][3] = allValuesQuery2[i][2];
		displayResult[i][4] = allValuesQuery2[i][3];
		displayResult[i][5] = "Exception";
		displayResult[i][6] = allValuesQuery2[i][5];
		displayResult[i][7] = allValuesQuery3[i][2];
		
	}
	ListModel myModel = new ListModelList(displayResult);
	elementObj.setModel(myModel);
	elementObj.setItemRenderer(new ItemRendererArray());
		
}

public void attachEventListener(String objectID,String eventName)
{
	Object rootFormValue = self.getRoot();
	Object obj =rootFormValue.getVariable(objectID, true);
	if(obj == null)
	{
		alert("object is null");
		return;
	}
	

	if(objectID == "radiobutton2")
	{
		obj.addEventListener(eventName, new OnCustomizedParamEventListener());
    }
	else if(objectID == "radiobutton1")
	{
		obj.addEventListener(eventName, new EventListener()
								{
									public void onEvent(Event event)
									{
								//String mes="Customized settings would be lost.Are you sure you want to continue" ;
										//boolean result=messageWithQuestion(mes);
										//if(result)
										//{
											Object rootFormValue = self.getRoot();
											Object obj =rootFormValue.getVariable("frame40", true);
											List children = obj.getChildren();
											if(children!=null && children.size() > 0)
											{
												Object[] charr = children.toArray();
												for(int i = 0; i < charr.length; i++)
												{
														obj.removeChild(charr[i]);
												}
											}
											obj.setVisible(false);
										//}
									}
								}
							);
	}
      
}

	public void createDataInChart(String barchart,String textboxId, String textboxId1,String textboxId2)
	{

		Object rootFormValue = self.getRoot();
		Object obj =rootFormValue.getVariable(barchart, true);
		Object objText =rootFormValue.getVariable(textboxId, true);
		Object objText2 =rootFormValue.getVariable(textboxId1, true);
		Object objText3 =rootFormValue.getVariable(textboxId2, true);


		if(returnHicData ==null)
		{

			return;
		}
		else
		{

			List returnList=returnHicData.getData().getList();
			if(returnList!=null && returnList.size()>0)
			{
				Long value=(Long)returnList.get(0);
				Long value2=(Long)returnList.get(2);
				objText.setValue(value.toString());
				objText2.setValue(value2.toString());
				double[] errorVector=returnList.get(1);
				double[] errorVector2=returnList.get(3);
				String value3=(String)returnList.get(4);
				objText3.setValue(value3);
				XYModel xymodel = new SimpleXYModel();
				for(int i=0;i<errorVector.length;i+=20)
				{
					xymodel.addValue("Start error", new Integer(i), new Double(errorVector[i]));
					xymodel.addValue("End error", new Integer(i), new Double(errorVector2[i]));
				}

				obj.setModel(xymodel);

			}

		}
	}
public void createHypnogram(String hypnogram) { 

Object rootFormValue = self.getRoot();
Object obj =rootFormValue.getVariable(hypnogram, true);

if(returnHicData ==null)

{
 return;
}
else

{
 List returnList=returnHicData.getData().getList();
 if(returnList!=null && returnList.size()>0)
 {
  int value=(Integer)returnList.get(0);
  Object[] stages = returnList.get(1);
 
    
  XYModel xymodel = new SimpleXYModel();
  for(int i=0;i<stages.length;i+=1)
  {
  if(stages[i].equals(255)) stages[i]=(Integer)20;
   xymodel.addValue("Stages",new Integer(i), ((Integer) stages[i]));

}
  obj.setModel(xymodel);
  obj.setXAxis("Time. Sampling of stages every " + value + " seconds");
  obj.setYAxis("Stage Value");
 }
}
}


	public String uploadPSGFile(String id)
	{
		String path=null;
		org.zkoss.util.media.Media media = Fileupload.get();
		if (media != null) 
		{ 
			String contentType=media.getContentType();


			if (!media.inMemory() && !media.isBinary()){
				InputStreamReader inputStream=media.getReaderData();

				String fileName=media.getName();
				String folderPath=PropertyUtil.setUpProperties("PSG_FILE_LOC");
				File file = new File(folderPath);
				if(!file.exists())
				{
					file.mkdirs();
				}



				File f=new File(folderPath+fileName);   
				OutputStream out=new FileOutputStream(f);
				OutputStreamWriter o= new OutputStreamWriter(out);
				char[] buf=new char[1024];
				int len;
				while((len=inputStream.read(buf,0,1024))>0)
					o.write(buf,0,len);
				o.close();
				inputStream.close();   
				return folderPath+fileName; 
			}
			else if(media.inMemory() && !media.isBinary()) {
				String str=media.getStringData();
				String fileName=media.getName();
				String folderPath=PropertyUtil.setUpProperties("PSG_FILE_LOC");

				File file = new File(folderPath);
				if(!file.exists())
				{
					file.mkdirs();
				}

				File f=new File(folderPath+fileName);   

				BufferedWriter out = new BufferedWriter(new FileWriter(f));
				out.write(str);
				out.close();

				return folderPath + fileName;
			}
			else {
				InputStream inputStream=media.getStreamData();

				String fileName=media.getName();
				String folderPath=PropertyUtil.setUpProperties("PSG_FILE_LOC");
				path=writeToFile(folderPath,fileName,inputStream);
				return path;
			}
		}
		return null;
	}


public class OnCustomizedParamEventListener implements EventListener
{

	public void onEvent(Event event) throws Exception
	{
		
		showCustomizedParamTabbox("");
		
		
	}
}
public void showCustomizedParamTabbox(String noUse)
{

		String selectedModule = getSessionData("selectedModule");
		if(selectedModule == null)
		{
			message("select a module!");
			return;
		}

		invokeComponent("com.oxymedical.component.BrainKComponent","BrainKParamInfo","com.oxymedical.component.braink.BrainKComponent",selectedModule);
		if(returnHicData == null || returnHicData.getData().getList() == null)
		{
			System.out.println("returnhicdata null");
			return;
		}


		List infoList = returnHicData.getData().getList();

		Object rootFormValue = self.getRoot();
		Object obj =rootFormValue.getVariable("frame40", true);
		List children = obj.getChildren();
		if(children!=null && children.size() > 0)
		{
			Object[] charr = children.toArray();
			for(int i = 0; i < charr.length; i++)
			{

					obj.removeChild(charr[i]);
			}
		}
		
		obj.setVisible(true);
		obj.setStyle("background:white;");
		Tabbox tabbox = new Tabbox();
		tabbox.setMold("accordion");
		Tabs tabs =  new Tabs();
		Tabpanels tpanels = new Tabpanels();
		
		for(int i=0;i<infoList.size();i++)
		{
			BrainKModuleParam moduleNameParaInfo = infoList.get(i)[0];
			String moduleName = moduleNameParaInfo.getModuleName();
			

			Tab tab = new Tab();
			tab.setLabel(moduleName);
			
			tabs.appendChild(tab);
			
			List paraInfoList = moduleNameParaInfo.getParamInfoList();
			
			Tabpanel tpanel = new Tabpanel();
			tpanel.setStyle("overflow:auto;");
			for(int j=0;j<paraInfoList.size();j++)
			{

				BrainKParam paraInfo = paraInfoList.get(j);
				
				String parameterName = paraInfo.getParameterName();
				String dataType = paraInfo.getDataType();
				String defaultValue = paraInfo.getDefaultValue();
				String rangeStartValue = paraInfo.getRangeStartValue();
				String rangeEndValue = paraInfo.getRangeEndValue();
				
				Groupbox gbox = new Groupbox();
				Caption cp = new Caption();
				cp.setLabel(parameterName);
				gbox.appendChild(cp);
				Textbox tbox = new Textbox();
				tbox.setName(parameterName);
				gbox.appendChild(tbox);
				
				
				if(dataType.equalsIgnoreCase("Integer"))
				{
					Slider slider = new Slider(Integer.parseInt(defaultValue));
					slider.setMaxpos(Integer.parseInt(rangeEndValue));
					slider.setMold("scale");
					gbox.appendChild(slider);
					
					
					Integer sliderValue = (Integer)slider.getCurpos();
					String value = String.valueOf(sliderValue);
					tbox.setValue(value);
					slider.addEventListener("onScroll",new EventListener()
												{
													public void onEvent(Event event)
													{
														Integer startValue = Integer.parseInt(rangeStartValue); 
														Integer sliderValue = (Integer)slider.getCurpos();
														Integer actualValue;
														if(sliderValue<startValue)
														{
															actualValue = startValue;
															alert("minimun possible value is:"+startValue);
														}
														else
														{
															actualValue = sliderValue; 
														}
														String value = String.valueOf(actualValue);
														tbox.setValue(value);
													}
												}
											);
				}
				else if(dataType.equalsIgnoreCase("Boolean"))
				{
					Slider slider = new Slider(Integer.parseInt(defaultValue));
					slider.setMaxpos(Integer.parseInt(rangeEndValue));
					gbox.appendChild(slider);
					Integer sliderValue = (Integer)slider.getCurpos();
					String value = String.valueOf(sliderValue);
					tbox.setValue(value);
					slider.addEventListener("onScroll",new EventListener()
												{
													public void onEvent(Event event)
													{
														
														Integer sliderValue = (Integer)slider.getCurpos();
														String value = String.valueOf(sliderValue);
														tbox.setValue(value);
													}


												}


											);
																
				}
				else if(dataType.equalsIgnoreCase("Float"))
				{
					
					
					
					float fnum = Float.valueOf(defaultValue).floatValue();  
					Integer inum = (int)(fnum*10); 
					Slider slider = new Slider(inum);
					fnum = Float.valueOf(rangeEndValue).floatValue()*10; 
					inum = (int)fnum;
					slider.setMaxpos(inum);
					slider.setMold("scale");
					gbox.appendChild(slider);
					
					
					float sliderValue = (Float)slider.getCurpos();
					float deciValue = sliderValue/10;
					String value = String.valueOf(deciValue);
					tbox.setValue(value);
					slider.addEventListener("onScroll",new EventListener()
												{
													public void onEvent(Event event)
													{
												float startValue = Float.valueOf(rangeStartValue).floatValue()*10;
														float sliderValue = (Float)slider.getCurpos();
														
														Float actualValue;
														
														if(sliderValue<startValue)
														{
															actualValue = startValue;
															alert("minimun possible value is:"+(startValue/10));
														}


														else
														{
															actualValue = sliderValue; 
														}
														
														String value = String.valueOf(actualValue);
														tbox.setValue(value);
														float deciValue = actualValue/10;
														String value = String.valueOf(deciValue);
														tbox.setValue(value);
													}

												}
											);
				}
				tpanel.appendChild(gbox);
				tpanel.setHeight("300px");
				tpanel.setStyle("overflow:auto;");
				
				
			}
			
			tpanels.appendChild(tpanel);
			
		}
		tabbox.appendChild(tabs);
		tabbox.appendChild(tpanels);
		Div div = new Div();
		div.appendChild(tabbox);
		div.setHeight("350px");
		div.setStyle("overflow:auto;");
		obj.appendChild(div);
}

//this method is used to display tool tip on any component and given the id of component and display text in the tool tip.
public void createToolTip(String id, String displayText){
	Component rootFormValue = self.getRoot();
	Component ctrl = rootFormValue.getFellowIfAny(id, true);
	if(ctrl == null){
		ctrl = getComponentIfInPlaceHolder(id);
	}
	if(ctrl != null){
		/*Popup popup_tt = new Popup();
	    Label lbl = new Label();
	    lbl.setValue(displayText);
		popup_tt.appendChild(lbl);
		rootFormValue.appendChild(popup_tt);
		ctrl.setTooltip(popup_tt);*/
		ctrl.setTooltiptext(displayText);
	}
}

List categoryList=new ArrayList();
public List getCategoryList(Object formObj) 
{
  	List childElement;
	if (formObj != null) 
	{
		childElement = formObj.getChildren();
		Iterator iter = childElement.iterator();
		while (iter.hasNext())
		{
			Object value = iter.next();
			if (value instanceof Combobox) 
			{
			if (((Combobox) value).getId().indexOf("category") >= 0)
				categoryList.add(value.id);
			}



			else
			{
				categoryList = getCategoryList(((XulElement) value));
			}


		}
	}
return categoryList;
}



public void showQueryComboData(String id)
{
   Object rootFormValue=self.getRoot();
   Object obj =rootFormValue.getVariable(id, true);
   List ids=getCategoryList(obj);
   if(ids!=null && ids.size()>0)
   {
      for(int i=0;i<ids.size();i++)
	  {
	  String catId=ids.get(i);
	  showComboData(catId);
	  }
   }
 categoryList.clear();  
}



public void showDataInPage(String[] txtField,String formId)
{
	Object rootFormValue = Path.getComponent("//"+formId+"//"+formId);
	String[][] allValues =null;
	int j=0;
	LinkedHashMap displayValues;
	if(null != returnHicData)
	{
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return;
		allValues = dataUnit.getQueryData().iterateListData(listValue);
		if(allValues==null)
			return;
		uiLibraryUtil.showData(allValues,formValues,rootFormValue,txtField,comboTable);
		updateSession("updatedMsg",updatedMsg);
	}
}



public Hashtable iterateObject(Object obj,Hashtable hashtable)
{
	List children = obj.getChildren();
	if(children!=null && children.size() > 0)
	{
		int j = 0;
		while(j<children.size())
		{
			Object childObject = children.get(j);
			if(childObject instanceof Textbox)
			{
				 hashtable.put(childObject.getName(),childObject.getValue());
			}
			else
			{
			 hashtable = iterateObject(childObject,hashtable);
			}
			j++;
		}
		return hashtable;
	}
	return hashtable;
}


public String storeBrainKInputData(String NEISpatientID,String PACSpatientID, String seriesID, String moduleRun, String frameWithParamTextbox)
{
	
	Object rootFormValue = self.getRoot();
	Object obj =rootFormValue.getVariable(frameWithParamTextbox, true);
	Hashtable hashtable = new Hashtable();
	hashtable = iterateObject(obj,hashtable);
	hashtable.put("NEISpatientID",NEISpatientID);
	hashtable.put("PACSpatientID",PACSpatientID);
	hashtable.put("InputFile",seriesID);
	hashtable.put("ModuleRun",moduleRun);
	
	formValues=hashtable;
	invokeComponent("com.oxymedical.component.BrainKComponent","StoreBrainKInputData","com.oxymedical.component.braink.BrainKComponent","");	
	String BKP_ID = returnHicData.getMetaData().getCommonObject();


	return 	BKP_ID;
	
}

public String downloadFolder(String ID,String button)
{

		String fileFormat = null;
		if(button == "Log")
		{
			fileFormat = "txt";
			invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get BrainKProvenance.LogFile from brainkdb.BrainKProvenance conditions BrainKProvenance.ID:="+ID);
		}
		else
		{
			fileFormat = "rar";
			invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get BrainKProvenance.OutputFile from brainkdb.BrainKProvenance conditions BrainKProvenance.ID:="+ID);
		}
		if(returnHicData ==null)
		{
			return "false";
		}
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return "false";
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		String filePath = allValues[0][0]; 
				
		InputStream fis = new FileInputStream(filePath);
		int indexLog = filePath.lastIndexOf("/");
		String fileName = filePath.substring(indexLog+1); 
		Filedownload.save(fis,fileFormat,fileName);
	
	return "a";
}
public Hashtable createFormValues(Object formObj,Hashtable formValues) 
{

  	List childElement;
	if (formObj != null) 
	{
		childElement = formObj.getChildren();
		Iterator iter = childElement.iterator();
		while (iter.hasNext())
		{
			Object value = iter.next();
			if (value instanceof InputElement) 
			{	
				if (value instanceof Combobox) 
				{
				  String id=((Combobox) value).getId();			  
				  getComboItemId1(id);
				}				else if(value instanceof Textbox)
				{
					if(((Textbox) value).getValue()!=null && !(((Textbox) value).getValue().trim().isEmpty()))
					{
						checkFormValue(((Textbox) value).getId(),((Textbox) value).getValue());
						formValues.put(((Textbox) value).getId(),((Textbox) value).getValue());
						textId.add(((Textbox) value).getId());
					}
				} 
				else if(value instanceof Datebox)
				{
					String date="";
					if(((Datebox) value)!=null)
					{
					date= (new SimpleDateFormat("yyyy-MM-dd").format(((Datebox) value).getValue()));
					checkFormValue(((Datebox) value).getId(),date);
					}



					formValues.put(((Datebox) value).getId(),date);
				}
			}
			else
			{
			 formValues = createFormValues(value,formValues);
			}
		}
	}
	return formValues;
}

public void getFilesfromFolder(String directoryName,Object comboObj)
{
    File dir = new File(directoryName);
    String[] children = dir.list();
    if (children == null) {
        // Either dir does not exist or is not a directory
    } else {
	   ArrayList fileList=new ArrayList();
	   int counter=0;
        for (int i=0; i<children.length; i++) {
            // Get filename of file or directory
            String filename = children[i];
            File tempFile = new File(dir,filename);
			if(!tempFile.isHidden())
			{
				fileList.add(filename);
			}
        }
		Collections.sort(fileList,String.CASE_INSENSITIVE_ORDER);
		String[][] displayFiles=new String[fileList.size()][2];
        for(int j=0;j<fileList.size();j++)
        {
		   displayFiles[j][0]=fileList.get(j);
		   displayFiles[j][1]=fileList.get(j);
        }
		if(displayFiles!=null && displayFiles.length>0)
		uiLibraryUtil.showComboData(displayFiles,comboObj);
    }    
}

public void XMLtoTools(String path,String formpatternId,String packageId,String actionId,String descriptionId,String categoryId, String visualizerId, String xmlId)
{
		XmlReader xmlReader= new XmlReader();
		String returnMessage="";
	    org.dom4j.Document domDoc =xmlReader.getDocumentFromPath(path);
		if(domDoc!=null)
		{
			org.dom4j.Element rootElement  = domDoc.getRootElement();
			List elements0 = rootElement.elements();
			if(elements0!=null && elements0.size()>0)
			{
				for(int i=0 ; i<elements0.size() ; i++)
				{
					org.dom4j.Element element= (org.dom4j.Element)elements0.get(i);
					List elements1 = element.elements();

					for(int j=0 ; j<elements1.size() ; j++)
					{
						org.dom4j.Element element1= (org.dom4j.Element)elements1.get(j);
						List elements2 = element1.elements();

						for(int k=0 ; k<elements2.size() ; k++)
						{
							org.dom4j.Element element2= (org.dom4j.Element)elements2.get(k);
							List elements3 = element2.elements();
							if(element2.getName().equalsIgnoreCase("Tool"))
							{
								formValues = new Hashtable();
								String formPattern=null;
								String pacakageName=null;
								boolean classFound=false;
								boolean isCategoryFound=false;
								for(int l=0 ; l<elements3.size() ; l++)
								{
									org.dom4j.Element element3= (org.dom4j.Element)elements3.get(l);
									if(element3.getName().equalsIgnoreCase("ZUL"))
									{
									    formPattern=element3.getText().trim();
										formValues.put( formpatternId,formPattern);
									}
									else if(element3.getName().equalsIgnoreCase("Package"))
									{
									    pacakageName=element3.getText().trim();
										formValues.put( packageId,pacakageName);
										try
										{
											Class clazz=Class.forName(pacakageName);
											classFound=true;
										}
										catch(Exception e)
										{
										 classFound=false;
										}
										
									}
									else if(element3.getName().equalsIgnoreCase("Action"))
									{
										formValues.put( actionId, element3.getText().trim() );
									}
									else if(element3.getName().equalsIgnoreCase("Description"))
									{
										formValues.put( descriptionId, element3.getText().trim());	
									}
									else if(element3.getName().equalsIgnoreCase("Tool-Cateogry"))
									{
									    String category=element3.getText().trim();
								invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get toolcateogry .id,toolcateogry.category from appadmin.toolcateogry conditions toolcateogry.category:=["+category+"]");	
								      String id=getDataFromAllValuesVar(0, 0);
									  if(id!=null)
									  {
									    isCategoryFound=true;
										formValues.put(categoryId,id);	
									  }
									}
									else if(element3.getName().equalsIgnoreCase("IS-VISUALIZER"))
									{
										formValues.put( visualizerId, element3.getText().trim());	
									}
                                    else if(element3.getName().equalsIgnoreCase("INPUT-XML"))
									{
									    String inputXml=element3.getText()!=null ?element3.getText().trim():null;
									    if(inputXml!=null && !inputXml.equalsIgnoreCase(""))
										formValues.put( xmlId, element3.getText().trim());	
									}										
								 }
								if(classFound && formValues!=null && isCategoryFound && formValues.size()>0)  
								{
									setFormPatternId("AddTool");  
									invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get nodedetails.id from appadmin.nodedetails conditions nodedetails.formpattern:=["+formPattern+"]");									 if(isRecordExist())
									{
									  returnMessage = returnMessage+" Record for :"+formPattern+" already exists \n";
									 }
									 else
								    {   
								 invokeComponent("com.oxyentmedical.component.workflowcomponent","AddNewNodeFromUI","com.oxymedical.component.workflowComponent.WorkflowComponent","");
									}
								 }
								 else if(!classFound)
								 {
								 returnMessage=returnMessage+ " Class not found for :"+formPattern+" \n";
								 }
								 else if(!isCategoryFound)	
								 {
								  returnMessage=returnMessage+ " Category not found for :"+formPattern+" \n";
								 }																	
							}
						}
					}
				}
			}
			
			if(!returnMessage.trim().equals(""))
			{
			message(returnMessage);
			}
			
		}
		else
		{
		message("File not found.");
		}
}
public String splitStringForRequiredIndex(String inputValue,String pattern,int index)
{
    String returnValue=null;
	if(inputValue!=null)
	{
		 String[] values=StringUtil.splitString(inputValue.trim(),pattern);
		 if(values.length>index)
		 {
		    returnValue=values[index];
		 }
	}
  return returnValue;
}
public void getCSVFilesFromFolder(String directoryName,Object comboObj)
{
    File dir = new File(directoryName);
    String[] children = dir.list();
    if (children == null) 
    {
        // Either dir does not exist or is not a directory
    } 
    else 
    {  
       ArrayList fileList=new ArrayList();
	   int counter=0;
        for (int i=0; i<children.length; i++) 
        {
          String filename = children[i];
			  if(filename.lastIndexOf(".csv") != -1)
			{
				File tempFile = new File(dir,filename);
				if(!tempFile.isHidden())
				{
					   //Get filename of file or directory
				fileList.add(filename);
					counter++;
				}
			}
            
        }
        Collections.sort(fileList,String.CASE_INSENSITIVE_ORDER);
        String[][] displayFiles=new String[fileList.size()][2];
        for(int j=0;j<fileList.size();j++)
        {
		   displayFiles[j][0]=fileList.get(j);
		   displayFiles[j][1]=fileList.get(j);
        }
	if(displayFiles!=null && displayFiles.length>0)
	uiLibraryUtil.showComboData(displayFiles,comboObj);
    }    
}

public boolean checkWorkflowStopped(String id)
{ 
	boolean isStopped=false;
	if(returnHicData!=null)
	{
	  if(returnHicData.getData().getReturnedData()!=null)
	  {
	  isStopped=returnHicData.getData().getReturnedData();
	  }
	}
	return isStopped;
}

public String replaceStringValue(String replaceString,String replaceWith,String value)
{
 if(value!=null)
 {
 value=value.replaceAll(replaceString,replaceWith);
 }
 return value;
}

public void displayFieldDataInControlForDragAndDrop(String id)
{

	if(returnHicData ==null)
	{
		return;
	}
	else
	{
		IData dataUnit = returnHicData.getData();
		List listValue = null;
		
		if (dataUnit.getQueryData() == null) return;
		if(dataUnit.getQueryData().getListData()!=null)
		{
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return;
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		Object rootFormValue = self.getRoot();
		Object obj = rootFormValue.getVariable(id,true);
		List child = obj.getChildren();
		List childData = new ArrayList(child);
		Iterator itr = childData.iterator();
		while(itr.hasNext())
		{
			Listitem listitem =  (Listitem)itr.next();
			obj.removeChild(listitem);
		}
				
		if(allValues !=null)
		{
			for(int i=0;i<allValues.length;i++)
			{  
				String name=allValues[i][1];
				String value=allValues[i][0];
				Listitem li = new Listitem();
				li.setLabel(name);
				li.setValue(value);
				li.setDraggable("true");
				li.setDroppable("true");
				li.addEventListener("onDrop", new EventListener(){
				public void onEvent(Event event)
				 throws Exception {
				move(event.dragged);
				}
				});
				obj.appendChild(li);
			}
		}
	}
}

static String winname="";
static String windowUrl="";
public void openNewWindow(String windowName,Object button)
{
	 Object rootFormValue=self.getRoot();
	 winname = "browser"+ generateUniqueNumber();
	 windowUrl=windowName;
	 rootFormValue.getDesktop().enableServerPush(true);
	 button.setAction("onClick: newWindow=window.open( #{windowUrl},   #{winname},'scrollbars=yes,resizable=yes,height=500px,width=500px');newWindow.focus();");
	 button.setParent(rootFormValue);
}

public void openVisulaizer()
{
	String patientId=getSessionData("rowId");
	String nodeName=getSessionData("nodename");
	String workflowName=getSessionData("workflowname");
	String patientMRN=getSessionData("patientmrn");
	String userId=getSessionData("userId");
	String scheduleId=getSessionData("scheduleId");
	addFormValue("PATIENTMRN", patientMRN);
	addFormValue("PATIENTID", patientId);
	addFormValue("SCHEDULEID", scheduleId);
	addFormValue("WORKFLOWNAME", workflowName);
	addFormValue("NODENAME", nodeName);
	invokeComponent("com.oxyentmedical.component.workflowcomponent","OpenVisualiser","com.oxymedical.component.workflowComponent.WorkflowComponent",workflowName+"."+nodeName);
}
public void getFilesBasedOnFileType(String directoryName,Object comboObj, String fileType)
{
    File dir = new File(directoryName);
    String[] children = dir.list();
	
	if(comboObj.getChildren()!=null && comboObj.getChildren().size()>0)
	{
		List list = new ArrayList(comboObj.getChildren());
		for(int i=0;i<list.size();i++)
		{
			comboObj.removeChild(list.get(i));
		}
	}
	
    if (children == null) 
    {
        // Either dir does not exist or is not a directory
    } 
    else 
    {  
       ArrayList fileList=new ArrayList();
	   int counter=0;
        for (int i=0; i<children.length; i++) 
        {
          String filename = children[i];
          String fileCon="."+fileType; 
			  if(filename.lastIndexOf(fileCon) != -1)
			{
				File tempFile = new File(dir,filename);
				if(!tempFile.isHidden())
				{
					   //Get filename of file or directory
				fileList.add(filename);
					counter++;
				}
			}
            
        }
        Collections.sort(fileList,String.CASE_INSENSITIVE_ORDER);
        String[][] displayFiles=new String[fileList.size()][2];
        for(int j=0;j<fileList.size();j++)
        {
		   displayFiles[j][0]=fileList.get(j);
		   displayFiles[j][1]=fileList.get(j);
        }
	if(displayFiles!=null && displayFiles.length>0)
	uiLibraryUtil.showComboData(displayFiles,comboObj);
    }    
}

private String getNextFormId()
{
String formCounter = session.getAttribute("formCounter");
String visitId = session.getAttribute("visitId");
invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get VisitForm.FormName from agedcdb.VisitForm conditions VisitForm.VisitId:="+visitId);
String formName = null;
try{
	formName = getDataFromAllValuesVar(Integer.parseInt(formCounter)+1, 0);
	session.setAttribute("formCounter", ""+(Integer.parseInt(formCounter)+1));
}
catch(Exception e){
}
return formName;
}

private String getNextVisitId()
{
String visitCounter = session.getAttribute("visitCounter");
String studyId = session.getAttribute("studyId");
invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get Visit.VisitId from agedcdb.Visit conditions Visit.StudyName:=["+studyId+"]");
String visitId = null;

try{
	visitId = getDataFromAllValuesVar(Integer.parseInt(visitCounter)+1, 0);
	
	invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get VisitForm.FormName from agedcdb.VisitForm conditions VisitForm.VisitId:=["+visitId+"]");
	
	IData dataUnit = returnHicData.getData();
	listValue = dataUnit.getQueryData().getListData();
	String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
	if(allValues == null || allValues.length <= 0)
		return "false";
		
	session.setAttribute("visitId",visitId);
	session.setAttribute("visitCounter",""+(Integer.parseInt(visitCounter)+1));
	session.setAttribute("formCounter","-1");
}
catch(Exception e){
}
return visitId;
}

private String getPreviousFormId()
{
String formCounter = session.getAttribute("formCounter");
String visitId = session.getAttribute("visitId");
invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get VisitForm.FormName from agedcdb.VisitForm conditions VisitForm.VisitId:="+visitId);
String formName = null;
try{
	formName = getDataFromAllValuesVar(Integer.parseInt(formCounter)-1, 0);
	session.setAttribute("formCounter", ""+(Integer.parseInt(formCounter)-1));
}
catch(Exception e){
}
return formName;
}

private String getPreviousVisitId()
{
String visitCounter = session.getAttribute("visitCounter");
String studyId = session.getAttribute("studyId");
invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get Visit.VisitId from agedcdb.Visit conditions Visit.StudyName:=["+studyId+"]");
String visitId = null;

try{
	visitId = getDataFromAllValuesVar(Integer.parseInt(visitCounter)-1, 0);
	
	invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get VisitForm.FormName from agedcdb.VisitForm conditions VisitForm.VisitId:=["+visitId+"]");
	
	IData dataUnit = returnHicData.getData();
	listValue = dataUnit.getQueryData().getListData();
	String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
	if(allValues == null || allValues.length <= 0)
		return "false";

	session.setAttribute("visitId",visitId);
	session.setAttribute("visitCounter",""+(Integer.parseInt(visitCounter)-1));
	session.setAttribute("formCounter",""+allValues.length);
}
catch(Exception e){
}
return visitId;
}
String invokeComponentDownloadFile(String componentId ,String method ,String classname, String paramList)
{
	String stringValues="";
	alert("inside invokecomponent download");
	String isValidStatus = "true";
	IDataUnitRouter router = RendererComponent.dataUnitRouter;
	Object rootFormValue = self.getRoot();
	/*
	*Before completing any request it will check that session is valid or not.
	*
	*/
	checkSessionTime(method);
	/*
	*Following code is used when session would be time out
	*/
	String logOutValue = getSessionData("LogOut");		
	if(logOutValue!=null)
	{
	
		if(logOutValue=="true")
		{
			return "";
		}
		return "";
	}

	
    UiLibraryCompositeCommand command=new UiLibraryCompositeCommand();
	command.setMethodName(method);
	command.setRouter(router);
    command.setClassname(classname);
	command.setComponentId(componentId);
    command.setDataPatternId(dataPatternId);
    command.setFormPatternId(formPatternId);
    command.setFormValues(formValues);
	command.setRootFormValue(rootFormValue);
	command.setParamList(paramList);
	command.setSession(session);
	command.setComboSelectedValue(comboSelectedValue);
	command.setValidListRequest(validListRequest);
	command.setPagingId(pagingId);
	command.execute();
	returnHicData=command.getHICData();
	if(method.equalsIgnoreCase("searchDb"))
	{
		alert("inside SearchDb====");
		if(returnHicData==null)
		{
			alert("if hicdata is not there");
		}
		else
		{
			IData data=	returnHicData.getData();
			IFormPattern formPattern=data.getFormPattern();
			Hashtable formValues=formPattern.getFormValues();
			List lst=formValues.get("FileValues");
			stringValues =formValues.get("Headers")+"\n";
			alert("the headers are"+stringValues);
			for(int i=0;i<lst.size();i++)
			{
				stringValues=stringValues+lst.get(i)+"\n";
	
			}
			Filedownload.save(stringValues,"csv/plain","report.csv");
		}
	}
	else if(method.equalsIgnoreCase("generatePDF"))
	{
		alert("inside generatePDF====");
		if(returnHicData==null)
		{
			alert("if hicdata is not there");
		}
		else
		{
			IData data=	returnHicData.getData();
			IFormPattern formPattern=data.getFormPattern();
			Hashtable formValues=formPattern.getFormValues();
			byte[] bytes=formValues.get("StreamData");
			Filedownload.save(bytes,"application/pdf","reports.pdf");
		}
	}
		
	sessionUpdate(returnHicData);
	return isValidStatus;
}










// Added by dsi for setting selected item of combobox
private void setSelectedComboboxItemId(String comboBox, String id)
{
	String comboSelectedVal = null;
	Object rootFormValue = self.getRoot();
	Object comboObj = rootFormValue.getVariable(comboBox,true);
	List items = comboObj.getItems();
	List items = comboObj.getItems();
	for(Iterator iter = items.iterator(); iter.hasNext();)
	{
		Comboitem comboItem = (Comboitem)iter.next();
		int idSeperatorPos = comboItem.getId().indexOf("_");
		String comboSelectedVal = (String)comboItem.getId().substring(0,idSeperatorPos);
		if(id.equals(comboSelectedVal))
		{
			comboObj.setSelectedItem(comboItem);
			return;
		}
	}
}

Object getFormValues()
{
	return formValues;
}

setFormValues(Object lastFormValues)
{
	formValues = lastFormValues;
}

public String fileLocationPath(String fileType)
{
	String none="";
	org.zkoss.util.media.Media[] media = org.zkoss.zul.Fileupload.get(1, true);
	if (media != null) 
	{
		File f = new File(media[0].getName());
		int indexOfDot = f.getName().lastIndexOf(".");
		if(fileType != null && (indexOfDot >= 0 && !media[0].getName().substring(indexOfDot+1).equalsIgnoreCase(fileType))){
			alert("Please select the "+fileType+" file");
			return none;
		}

	 if (media[0].isBinary()) {
		 File file = new File(media[0].getName());
		 org.zkoss.io.Files.copy(file, media[0].getStreamData());
		}
	  else {
		 BufferedWriter writer = new BufferedWriter(new FileWriter(media[0].getName()));
		 org.zkoss.io.Files.copy(writer, media[0].getReaderData());
		 writer.flush();
	   }
		
		/*File f = new File(media.getName());
		Reader reader;
		try{
			InputStream inputStream = media.getStreamData();
			reader = new InputStreamReader(inputStream);
		}catch(Exception e){
			reader = media.getReaderData();
		}
		BufferedReader br = new BufferedReader(reader);
		FileOutputStream outFile = new FileOutputStream(f);
		PrintWriter textFileWriter = new PrintWriter(outFile);
		String line;
		while ((line = br.readLine()) != null )
		{
			textFileWriter.println(line);
		}
		textFileWriter.close();
		br.close();*/
		String filepath = f.getAbsolutePath();
		return filepath;
	}
	else
	{
		System.out.println("media null");
	}
	return none;
}

public void showMultiUpload(org.zkoss.zk.ui.event.EventListener onMultiUploadCloseListener, String allowedExtensions){
	ArrayList al_uploadedFileNames = new ArrayList();
	session.setAttribute("uploadedFileNames", al_uploadedFileNames);
	Window win = (Window)Executions.createComponents("../JUpload/jUpload.zul", null, null);
	win.setAttribute("allowedExtensions", allowedExtensions);
	win.setMaximizable(false);
	win.setMinimizable(false);
	win.setClosable(true);
	win.addEventListener(Events.ON_CLOSE, onMultiUploadCloseListener);
	win.doModal();
}

public void setComboModel(Combobox cmb, List dataList){
	ListModel cityModel = new SimpleListModelExt(dataList);
	cmb.setModel(cityModel);
}

public void setCookie(String name, String value){
	Cookie ck = new Cookie(name, value);
	//sec in min * min in hour * hour in day * day in year * no of years
	ck.setMaxAge(60 * 60 * 24 * 365 * 4);
	((HttpServletResponse)Executions.getCurrent().getNativeResponse()).addCookie(ck);
}

public static String getCookie(String name){
	Cookie[] cookies = ((HttpServletRequest)Executions.getCurrent().getNativeRequest()).getCookies();
	String cookieValue = null;
	if(cookies != null){
		for(Cookie cookie : cookies){
			if(cookie.getName().equals(name)){
				cookieValue = cookie.getValue();
				break;
			}
		}
	}
	return cookieValue;
}

private String getRowType(Component dRow){
	String rowType = "";
	if(dRow.getAttribute("RowEdited") != null && dRow.getAttribute("RowEdited").toString().equalsIgnoreCase("true")){
		rowType = "Edited";
	}else if(dRow.getAttribute("RowSelected") != null && dRow.getAttribute("RowSelected").toString().equalsIgnoreCase("true")){
		rowType = "Selected";
	}
	return rowType;
}

private String getRowItemData(Component rowItem){
	String itemData = "";
	if(rowItem instanceof Label){
		itemData = ((Label)rowItem).getValue();
	}else if(rowItem instanceof Textbox){
		itemData = ((Textbox)rowItem).getValue();
	}else if(rowItem instanceof Datebox){
		itemData = ((Datebox)rowItem).getText();
	}else if(rowItem instanceof Cell || rowItem instanceof Listcell){
		List cellItems = rowItem.getChildren();
		for(Object cellItemObj : cellItems){
			Component cellItem = (Component)cellItemObj;
			if(cellItem.getAttribute("OnlyForSelect") != null && cellItem.getAttribute("OnlyForSelect").toString().equalsIgnoreCase("true")){
				
			}else{
				itemData = itemData + getRowItemData(cellItem) + "|";
			}
		}
		if(itemData.length() > 0){
			itemData = itemData.substring(0, itemData.length()-1);
		}
	}
	return itemData;
}

public List getDataFromListBox(String lBoxID, String dataType){
	List editedRows = null;
	Component gridComp = getComponentInPage(lBoxID);
	if(gridComp != null && gridComp instanceof Listbox){
		editedRows = new ArrayList();
		Listbox dGrid = (Listbox)gridComp;
		for(int i = 0; i < dGrid.getItems().size(); i++){
			Listitem listItem = (Listitem)dGrid.getItems().get(i);
			if(dataType.equalsIgnoreCase("All")
					|| (dataType.equalsIgnoreCase("SelectedOrEdited") && (listItem.isSelected() || getRowType(listItem).equalsIgnoreCase("Edited")))
					|| (dataType.equalsIgnoreCase("Selected&Edited") && (listItem.isSelected() && getRowType(listItem).equalsIgnoreCase("Edited")))
					|| (dataType.equalsIgnoreCase("Selected") && listItem.isSelected())
					|| (dataType.equalsIgnoreCase("Edited") && getRowType(listItem).equalsIgnoreCase("Edited"))){
				List rowData = new ArrayList();
				for(int j = 0; j < listItem.getChildren().size(); j++){
					Component rowItem = (Component)listItem.getChildren().get(j);
					rowData.add(getRowItemData(rowItem));
				}
				editedRows.add(rowData);
			}
		}
	}
	return editedRows;
}

public List getDataFromGrid(String dGridID, String dataType){
	List editedRows = null;
	Component gridComp = getComponentInPage(dGridID);
	if(gridComp != null && gridComp instanceof Grid){
		editedRows = new ArrayList();
		Grid dGrid = (Grid)gridComp;
		for(int i = 0; i < dGrid.getRows().getChildren().size(); i++){
			Row dRow = (Row)dGrid.getRows().getChildren().get(i);
			if(dataType.equalsIgnoreCase("All")
					|| (dataType.equalsIgnoreCase("Selected") && getRowType(dRow).equalsIgnoreCase("Selected"))
					|| (dataType.equalsIgnoreCase("Edited") && getRowType(dRow).equalsIgnoreCase("Edited"))){
				List rowData = new ArrayList();
				for(int j = 0; j < dRow.getChildren().size(); j++){
					Component rowItem = (Component)dRow.getChildren().get(j);
					rowData.add(getRowItemData(rowItem));
				}
				editedRows.add(rowData);
			}
		}
	}
	return editedRows;
}

public void displayDataInGrid(String dGridID, GridDef gridDef, String[][] _gridData){
	Component gridComp = getComponentInPage(dGridID);
	if(gridComp != null && (gridComp instanceof Grid || gridComp instanceof Listbox)){
		String[][] gridData = _gridData;
		if(gridData == null){
			gridData = getDataForGrid();
		}
		CustomListModel clm = new CustomListModel(gridData, gridComp);
		ListModel lm = new ListModelList(gridData);
		if(gridComp instanceof Grid){
			Grid dGrid = (Grid)gridComp;
			dGrid.setModel(clm);
			if(gridDef.getIsSortable() == true && gridDef != null){
				Columns cols = dGrid.getColumns();
				List colList = cols.getChildren();
				for(int i=0;i<colList.size();i++){
					String colDatatype = "String";
					if(gridDef.getColumns()[i].getType().contains("textbox")){
						Column col = (Column) cols.getChildren().get(i);
						if(gridDef.getColumns()[i].getDataType() != null){
							colDatatype = gridDef.getColumns()[i].getDataType();
						}
						CustomComparator asce = new CustomComparator(true, i, null, colDatatype);
						CustomComparator dsce = new CustomComparator(false, i, null, colDatatype);
						col.setSortAscending(asce);
						col.setSortDescending(dsce);
					}else if(gridDef.getColumns()[i].getType().contains("label")){
						Column col = (Column) cols.getChildren().get(i);
						if(gridDef.getColumns()[i].getDataType() != null){
							colDatatype = gridDef.getColumns()[i].getDataType();
						}
						CustomComparator asce = new CustomComparator(true, i, null, colDatatype);
						CustomComparator dsce = new CustomComparator(false, i, null, colDatatype);
						col.setSortAscending(asce);
						col.setSortDescending(dsce);
					}else if(gridDef.getColumns()[i].getType().equalsIgnoreCase("datebox")){
						Column col = (Column) cols.getChildren().get(i);
						colDatatype = "date";
						String dateFormat = gridDef.getColumns()[i].getFormat();
						CustomComparator asce = new CustomComparator(true, i, dateFormat, colDatatype);
						CustomComparator dsce = new CustomComparator(false, i, dateFormat, colDatatype);
						col.setSortAscending(asce);
						col.setSortDescending(dsce);
					}
				}
			}
			RowRendererArray rowRendArr = new RowRendererArray();
			if(gridDef != null){
				rowRendArr.setGridDef(gridDef);
			}
			dGrid.setRowRenderer(rowRendArr);
		}else{
			dGrid = (Listbox)gridComp;
			dGrid.setModel(lm);
			ItemRendererArray ira = new ItemRendererArray();
			if(gridDef != null){
				ira.setGridDef(gridDef);
			}
			dGrid.setItemRenderer(ira);
		}
	}
}


private String[][] getDataForGrid(){
	Object listValues = session.getAttribute("dbListValue");
	String[][] gridData = null;
	if(listValues == null){
		if(returnHicData != null){
			hicData = returnHicData;
			
			IData dataUnit = hicData.getData();
			List listValue = null;
			if(dataUnit.getQueryData().getListData() != null){
				listValue = dataUnit.getQueryData().getListData();
				
				gridData = dataUnit.getQueryData().iterateListData(listValue);
			}
		}
	}else{
		gridData = QueryData.iterateListData(listValues);
	}
	return gridData;
}

public void setEnableListBox(String lBoxID, boolean status){
	Component lBoxComp = getComponentInPage(lBoxID);
	if(lBoxComp != null && lBoxComp instanceof Listbox){
		Listbox lBox = (Listbox)lBoxComp;
		List listItems = lBox.getItems();
		for(Listitem lItem : listItems){
			if(status == true){
				lItem.setStyle(null);
			}
			lItem.setDisabled(!status);
			if(status == false && lItem.isSelected() == true){
				lItem.setStyle("background: #3e697c");
			}
		}
	}
}

public Component getComponentInPage(String compId){
	Component comp = null;
	Component rootFormValue = self.getRoot();
	comp = rootFormValue.getFellowIfAny(compId, true);
	if(comp == null){
		comp = getComponentIfInPlaceHolder(compId);
	}
	return comp;
}

public Component getComponentIfInPlaceHolder(String compId){
	Component comp = null;
	if(self.getParent() != null){
		comp = self.getParent().getFellowIfAny(compId, true);
	}
	return comp;
}

public void setTheme (Execution exe, String theme) {                
	Cookie cookie = new Cookie(THEME_COOKIE_KEY, theme);                
	cookie.setMaxAge(60*60*24*30); //store 30 days                
	String cp = exe.getContextPath();                
	// if path is empty, cookie path will be request path, which causes problems                
	if(cp.length() == 0)                        
		cp = "/";                
	cookie.setPath(cp);                
	((HttpServletResponse)exe.getNativeResponse()).addCookie(cookie);        
}

public String getTheme (Execution exe) {                
	Cookie[] cookies = ((HttpServletRequest)exe.getNativeRequest()).getCookies();                
	if(cookies == null)                         
		return "";                
	for(int i=0; i < cookies.length; i++){                        
		Cookie c = cookies[i];                        
		if(THEME_COOKIE_KEY.equals(c.getName())) {                                
			String theme = c.getValue();                                
			if(theme != null)                                         
				return theme;                        
			}                
		}                
	return "";        
}

public void clearListboxHighlight(String lBoxID){
	List editedRows = null;
	Component gridComp = getComponentInPage(lBoxID);
	if(gridComp != null && gridComp instanceof Listbox){
		Component listRow = null;
		for(Iterator itr_listItems = gridComp.getChildren().iterator(); itr_listItems.hasNext();){
			listRow = itr_listItems.next();
			if(listRow instanceof Listitem){
				((Listitem)listRow).setStyle(null);
			}
		}
	}
}

public org.dom4j.Element createXmlElement(org.dom4j.Element parentElement, String nodeName, Map attributes, org.dom4j.Element addBefore_childElement){
	org.dom4j.Element newElement = org.dom4j.DocumentHelper.createElement(nodeName);
	for(Iterator itr_attrs = attributes.keySet().iterator(); itr_attrs.hasNext();){
		String attrName = (String)itr_attrs.next();
		String attrVal = (String)attributes.get(attrName);
		newElement.addAttribute(attrName, attrVal);
	}
	if(parentElement != null){
		if(addBefore_childElement == null){
			parentElement.add(newElement);
		}else{
			List childElements = parentElement.elements();
			for(org.dom4j.Element childElement : childElements){
				if(childElement.equals(addBefore_childElement)){
					int addBeforeIndex = childElements.indexOf(childElement);
					childElements.add(addBeforeIndex, newElement);
					break;
				}
			}
		}
	}

	return newElement;
}

public String matchAndGetFileNameWithVersion(String fileNameWithPath, String alfrescoFileNameWithPath, boolean isMinor)
{
	int index = fileNameWithPath.lastIndexOf("/");
	if(index == -1)
		index = fileNameWithPath.lastIndexOf("\\");
	String fileName = fileNameWithPath.substring(index+1);
	System.out.println(fileName);
	String filePath = fileNameWithPath.substring(0, index+1);
	String alfrescoFileName = alfrescoFileNameWithPath.substring(alfrescoFileNameWithPath.lastIndexOf(":")+1);

	String prefixWithVer = alfrescoFileName.substring(0, alfrescoFileName.lastIndexOf("_ver")+4);
	String prefix = alfrescoFileName.substring(0, alfrescoFileName.lastIndexOf("_ver"));
	String suffix = alfrescoFileName.substring(alfrescoFileName.lastIndexOf("."));
	String versionNumber = alfrescoFileName.replace(prefixWithVer, "").replace(suffix, "");
	Float versionNumberInDouble = Float.parseFloat(versionNumber);
	if(isMinor){
		versionNumberInDouble = versionNumberInDouble+0.1f;
	}else{
		versionNumberInDouble = Math.round(versionNumberInDouble)+1.0f;
	}

	if(fileName.indexOf("_ver") != -1){
		if(!fileName.equalsIgnoreCase(alfrescoFileName)){
			message("Please select the same file with or without version");
			return null;
		}
	}else{
		System.out.println(prefix+suffix);
		if(!fileName.equalsIgnoreCase(prefix+suffix)){
			message("Please select the same file with or without version");
			return null;
		}
	}
	System.out.println("1 = "+filePath+prefixWithVer+versionNumberInDouble+suffix);

	System.out.println("2 = "+filePath+fileName);
	File file = new File(filePath+fileName);
	File verFile = new File(filePath+prefixWithVer+versionNumberInDouble+suffix);
	if(verFile.exists())
		verFile.delete();
	file.renameTo(verFile);
	return verFile.getAbsolutePath();
}

public void makeGridForm(String dGridID, List formDataValues){
	Component gridComp = getComponentInPage(dGridID);
	Listbox dGrid = (Listbox)gridComp;
	Iterator itr_formDatavalues = formDataValues.iterator();
	while(itr_formDatavalues.hasNext()){
		Object[] formFieldValues = itr_formDatavalues.next();
		String labelName = (String)formFieldValues[0];
		Object control = (Object)formFieldValues[1];
		Object controlDataValue = (Object)formFieldValues[2];
		String controlValue=null;
		List comboItemList=null;
		if(controlDataValue  instanceof List){
			comboItemList =(List)formFieldValues[2];
		}else{
			controlValue =(String)formFieldValues[2];
		}
		Listitem listItem =new Listitem(); 
		Listcell formNameField = new Listcell();
		formNameField.setLabel(labelName);
		formNameField.setParent(listItem);
		Listcell formListCell = new Listcell();
		if(control instanceof Combobox){
			Combobox  ctrl = new Combobox();
			for(int i=0; i < comboItemList.size(); i++){
				String comboItemValue =comboItemList.get(i);
				String[] keyValuePair = comboItemValue.split("\\|");
				String comboItemKey =keyValuePair[0];
				String comboItemLabel =keyValuePair[1];
				formListCell.setStyle("text-align:center");
				ctrl.setWidth("80%");
				Comboitem comItem = new Comboitem();
				comItem.setLabel(comboItemLabel);
				comItem.setStyle("text-align:center");
				comItem.setValue(comboItemKey);
				ctrl.setText("-Select-");
				ctrl.appendChild(comItem);
				ctrl.setReadonly(true);
				ctrl.setStyle("text-align:center");
				ctrl.setParent(formListCell);
				formListCell.setParent(listItem);	
			}
		}else if(control instanceof Datebox){
			formListCell.setStyle("text-align:center");
			Datebox  ctrl = new Datebox();
			ctrl.setWidth("80%");
			ctrl.setFormat("yyyy-MM-dd");
			ctrl.setText(controlValue);
			ctrl.setInplace(true);
			ctrl.setStyle("text-align:center");
			ctrl.setParent(formListCell);
			formListCell.setParent(listItem);	
		}else if(control instanceof Textbox){
			formListCell.setStyle("text-align:center");
			Textbox  ctrl = new Textbox();
			ctrl.setWidth("80%");
			ctrl.setText(controlValue);
			ctrl.setInplace(true);
			ctrl.setStyle("text-align:center");
			ctrl.setParent(formListCell);
			formListCell.setParent(listItem);
		}else if(control instanceof Label){
			formListCell.setStyle("text-align:center");
			Label  ctrl = new Label();
			ctrl.setWidth("80%");
			ctrl.setValue(controlValue);
			ctrl.setStyle("text-align:center");
			ctrl.setParent(formListCell);
			formListCell.setParent(listItem);
		}
		listItem.setParent(dGrid);
	}
}