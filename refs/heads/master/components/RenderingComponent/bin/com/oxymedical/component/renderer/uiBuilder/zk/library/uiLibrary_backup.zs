import java.lang.*;
import java.util.*;
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zul.*;
import com.oxymedical.component.renderer.uiBuilder.zk.controlLayout.Controller;
import com.oxymedical.component.renderer.application.router.*;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ComboRenderer;
import org.zkoss.zul.Datebox;
import org.zkoss.zkex.zul.Jasperreport;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IData;
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
import java.text.*;
import java.sql.Time;
import java.util.LinkedHashMap;

//tree variables
private Hashtable formValues = new Hashtable();
private LinkedHashMap columnOrder = new LinkedHashMap();
private Hashtable levelPrimaryId = new Hashtable();
private Hashtable comboTable = new Hashtable();
private int treeNodeCount = 0;
private int lastTreeLevel = -1;
private String dataPatternId ="";
private String formPatternId ="";
private boolean loginStatus = false;
List textId = new ArrayList();
String comboSelectedValue = null;
boolean validRequest = true;
String webMessage = null;
// String deleteRecordId=null;
Object comboObj=null;
LinkedHashMap updateHash= null;
boolean validationValue = true;
String[] queryFields = null; 
IHICData returnHicData = null;
void message(String value)
{
	alert(value);
}

/* 
void setRecordId(String id)
{
	deleteRecordId = id;
}
*/

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

public void showData(String[] txtField)
{
	Object rootFormValue = self.getRoot();
	String[][] allValues =null;
	int j=0;
	LinkedHashMap displayValues;
	if(null != returnHicData)
	{
		IData dataUnit = returnHicData.getData();
		List listValue = dataUnit.getQueryData().getListData();
		allValues = dataUnit.getQueryData().iterateListData(listValue);
		
	}
	String value="";
	for(int count=0; count<txtField.length;count++)
	{
		String txtValue = allValues[0][count];
		Object textObject = rootFormValue.getVariable(txtField[count],true);
		if(textObject instanceof Combobox)
		{	
			if(comboTable!=null)
			{
				Hashtable listValue = comboTable.get(textObject.id);
				System.out.println("--- listValue ---"+listValue);
				if(listValue!=null)
				{
					if(txtValue!=null)
					{
						List dataList = listValue.get(txtValue);
						value = dataList.get(0);
					}
					else
						value=" ";

					textObject.setText(value);
				}
			}
		}
		else if(textObject instanceof Datebox)
		{
		    if(null!=txtValue && txtValue!="")
		    { 
			    String date=txtValue +" "+"00:00";
			    SimpleDateFormat ds1=new SimpleDateFormat("yyyy-MM-dd hh:mm");             
			    Date d1=ds1.parse(date);
				textObject.setValue(d1);
		    }
		}
		else
		{

			textObject.setText(txtValue);
		}
		if(txtValue!=null)
				formValues.put(txtField[count],txtValue);
	}

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

String getCurrentDate() 
{
    Calendar cal = Calendar.getInstance();
    String dateFormat = "yyyy-MM-dd" ;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    return sdf.format(cal.getTime());
}

String getCurrentDate(String dateFormat) 
{
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    return sdf.format(cal.getTime());
}

void printWindow()
{
	//System.out.println("inside the print method ");
	Clients.print();
}

String insert(String fistValue,String secondValue,int index)
{
	String startString;
	String endString;
	String stringValue=null;
	if(index!=null && index>0)
	{
		if(index<fistValue.length())
		{
		    startString=fistValue.substring(0,index);
		    endString = fistValue.substring(index, fistValue.length());
		    stringValue=startString + secondValue + endString;
	   	}
	   	else
	   	{
		    stringValue="";
		    System.out.println("Wrong Index");
	   	}
	}
	else
	{
		stringValue="";
		System.out.println("Wrong Index");
	}
	return stringValue;
}


String del(String value, int index ,int index1)
{
	   String startString;
	   String endString;
	   String stringValue=null;
	   if(index!=null )
	   {
	    if(index<value.length() )
	    {
		     startString=value.substring(0,index);
		     indexValue = startString.length();
		     endString = value.substring(index1+indexValue , value.length());
		     stringValue=startString + endString;
	    }
	    else
	    {
		     stringValue="";
		     System.out.println("Wrong Index");
	    }
	   }
	   else
	   {
		    stringValue="";
		    System.out.println("Wrong Index");
	   }
	   return stringValue;
}


public String splitDateTime(String dateString,String spliter)
{
	spliter = spliter.replaceAll("_SPACE_"," ");
	String[] dvalue = dateString.trim().split(spliter);
	if(dvalue.length==2)
		dateString = dvalue[0];
	
	return dateString;
}

void getComboBoxValue(String comboBox)
{
	   String comboSelectedVal = null;
	   Object rootFormValue = self.getRoot();
	   Object comboObj = rootFormValue.getVariable(comboBox,true);
	   Comboitem selectedComboRow = comboObj.getSelectedItem();
	   String rowValue = selectedComboRow.getLabel();
	   formValues.put(self.id,rowValue);
}

public String getDate()
{
	Date fullDate = self.value;
	String date = null;
	if(fullDate !=null)
		date =(new SimpleDateFormat("yyyy-MM-dd").format(fullDate));
	return date;
}

void setDateBoxId()
	{
		String date= (new SimpleDateFormat("yyyy-MM-dd").format(self.value));
		formValues.put(self.id,date);
	}
	
	
void setTimeId()
{
	 String time = String.valueOf(event.value)+":00";
	 Time timeFormate=Time.valueOf(time);
	 formValues.put(self.id,timeFormate);
 
}

void setRadioSelected(Object radioID)
	{

		self.setSelectedItem(radioID);
		formValues.put(self.id,self.selectedItem.label);
 	}
void setRadioGroupId()
	{
		formValues.put(self.id,self.selectedItem.label);
 	}
void setCheckboxId()
	{
		formValues.put(self.id,self.label);
	}
void setTextboxId()
	{
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
	System.out.println("Inside the refresh method");
}

void setFocus(String controlID)
{
	Object rootFormValue = self.getRoot();
	Object controlObj = rootFormValue.getVariable(controlID,true);
	controlObj.focus();
	
}


void saveForm(String formID)
{
	Object rootFormValue = self.getRoot();
	Object formObj = rootFormValue.getVariable(formID,true);
	String formID = formObj.getId();
	HICRouter router = RendererComponent.router;
	IDataUnit data = new DataUnit();
	data.setFormId(formID);
	data.setDataPatternId(dataPatternId);
	data.setMethodName("saveForm");
	data.setFormValues(formValues);
	IHICData data1 = router.routeToModeler(data);

}

void saveFormEx(String formID)
{
	Object rootFormValue = self.getRoot();
	Object formObj = rootFormValue.getVariable(formID,true);
	String formID = formObj.getId();
	String buttonID = self.getId();
	HICRouter router = RendererComponent.router;
	IDataUnit data = new DataUnit();
	data.setFormId(formID);
	data.setDataPatternId(dataPatternId);
	data.setMethodName("saveForm");
	data.setFormValues(formValues);
	if (!router.routeToWorkflow(data, buttonID)) saveForm(formID);

}


// this method is used to get the date difference between two dates

  Date datediff(Date date1,Date date2,char ch)
	{
	System.out.println("date 1 value is "+date1);
	System.out.println("date 2 value is "+date2);
	System.out.println("char value is "+ch);
	Date value = "10-08-2008";
      /*  Date date1temp, date2temp;
		if (isValidDate(date1))
		{
			String dateValue1=date1.substr(0,2);
    		String monthValue1=date1.substr(3,2);
    		String yearValue1=date1.substr(6,4);
            date1temp = new Date(yearValue1, monthValue1,dateValue1);
           // date1temp.setMonth(date1temp.getMonth() - 1);
            System.out.println(date1temp);
    	}
    	else
			return false; // otherwise exits

		if (isValidDate(date2))          // Validates second date
    	{
    		String dateValue2=date2.substr(0,2);
			String monthValue2=date2.substr(3,2);
    		String yearValue2=date2.substr(6,4);
            date2temp = new Date(yearValue2, monthValue2,dateValue2);
            date2temp.setMonth(date2temp.getMonth() - 1);
            System.out.println(date2temp);
		}
    	else
            return false;

	    if (ch=='y' || ch=='m')
    	{
             String diff_date =  date1temp- date2temp;
             String num_years = Math.abs(diff_date/31536000000);
    		 String num_months = Math.abs((diff_date % 31536000000)/2628000000);
             String num_days = Math.abs(((diff_date % 31536000000) % 2628000000)/86400000);

		     years  = Math.floor(num_years);
    		 months = Math.floor(num_months);
    		 days   = Math.floor(num_days);

  		if(ch=='y')
		{
    		return years;
		}
		else if(ch=='m')
		{
    		return ((years*12)+months);
    	}
	}
	else if(ch=='d')
	{
		var diff = new Date();
		diff.setTime(Math.abs(date1temp.getTime() - date2temp.getTime()));
		timediff = diff.getTime();
		weeks = Math.floor(timediff / (1000 * 60 * 60 * 24 * 7));
		timediff -= weeks * (1000 * 60 * 60 * 24 * 7);
		days = Math.floor(timediff / (1000 * 60 * 60 * 24));
		timediff -= days * (1000 * 60 * 60 * 24);
		return ((weeks*7) + days);
	}*/
	return value;
     }

 void update(var formId) {
  //alert("formID = " + formId);
    //validate data
    //firstName.getValue();
    //lastName.getValue();
    //birthday.getValue();
    //title.getValue();

    //submit the form
    Clients.submitForm(formId);
  }

public class LayoutTree extends Tree {

	public LayoutTree() {
	}

	public void onSelect() {
		Treeitem item = getSelectedItem();

		Treechildren children = item.getTreechildren();

		if (item != null) {
			System.out.println(item.getValue());
             Controller controller = new Controller();
             List list = controller.getList(item.getValue());
             for(int i=0; i<list.size(); i++) {
	             Treeitem newItem = new Treeitem();
	             String str = list.get(i);
				 newItem.setLabel(str);
				 newItem.setValue(str);
	             newItem.setParent(children);
	         }
		}
	}

	public void onClick() {
		Treeitem item = getSelectedItem();

		Treechildren children = item.getTreechildren();

		if (item != null) {
			System.out.println(item.getValue());
			 Treeitem newItem = new Treeitem();
			 newItem.setLabel("TestClick");
			 newItem.setValue("TestClick");
             newItem.setParent(children);
		}
	}
}



	void addData(Object ev,Event even)
			{


			System.out.println(even.value);
			System.out.println(ev.id);


			}



public void executeGridQuery(String sqlQuery,String elementId)
{
		Object rootFormValue = self.getRoot();
		Object elementObj = rootFormValue.getVariable(elementId,true);
		int count=0;
		int j=0;
		Hashtable rowValues=null;
		String[][] allvalue;
		IDataUnitRouter router = RendererComponent.dataUnitRouter;
		IDataUnit dataUnit = setRequestData(sqlQuery);
		dataUnit=router.routeToModeler(dataUnit);
		Hashtable rowValues=null;
		String[][] allvalue;
		if(dataUnit.getQueryData() !=null)
		{
			rowValues = dataUnit.getQueryData().getColOrderValues();
			allvalue=new String[rowValues.size()][];

		}

		if(null != rowValues)
		{
			Object[] rowIds = rowValues.keySet().toArray();
			if(rowIds.length > 0)
		 	{
				for(int i=0; i<rowIds.length; i++ )
				{
					Hashtable displayValues = rowValues.get(rowIds[i]);
					String displayLabel = null;
					id=rowIds[count];
					if (null!= displayValues)
					{
						allvalue[count]=new String[displayValues.size()+1];
						j=0;
						for(int c=0;c<displayValues.size(); c++)
						{
							allvalue[count][j]=displayValues.get(String.valueOf(c));
							j++;
						}
					count++;
					}
				}
			}
		ListModel strset = new SimpleListModel(allvalue);
		elementObj.setModel(strset);
		elementObj.setItemRenderer(new rowRendererArray());

	}
	else
	{
		alert("Table is Empty");
	}
}


public class rowRendererArray implements ListitemRenderer
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
				listcell.setParent(li);
			}
		}
	}
}



public class rowRenderer implements RowRenderer
{
	public void render(Row row, java.lang.Object data) throws Exception
	{
		if(data!=null)
		{
			new Label(data).setParent(row);
		}
	}
}



public void executeListQuery(String sqlQuery,String elementId)
{

		Object rootFormValue = self.getRoot();
		Object elementObj = rootFormValue.getVariable(elementId,true);
		int count=0;
		int j=0;
		IDataUnitRouter router = RendererComponent.dataUnitRouter;
		DataUnit dataUnit = setRequestData(sqlQuery);
		dataUnit=router.routeToModeler(dataUnit);
		Hashtable rowValues=null;
		String[][] allvalue;
		if(dataUnit.getQueryData() !=null)
		{
			rowValues = dataUnit.getQueryData().getColOrderValues();
			allvalue=new String[rowValues.size()][];

		}
		if(null != rowValues)
		{
			 Object[] rowIds = rowValues.keySet().toArray();
			 if(rowIds.length > 0)
			 {
				for(int i=0; i<rowIds.length; i++ )
				{
					Hashtable displayValues = rowValues.get(rowIds[i]);
					String displayLabel = null;
					if (null!= displayValues)
					{
						allvalue[count]=new String[displayValues.size()+1];
						j=0;
						for(int c=0;c<displayValues.size(); c++)
						{
							allvalue[count][j]=displayValues.get(String.valueOf(c));
							j++;
						}
					count++;
					}
				}
		 	}
			ListModel myModel = new ListModelList(allvalue);
			elementObj.setModel(myModel);
			elementObj.setItemRenderer(new itemRendererArray());

		}
		else
		{
			alert("Table is Empty");
		}
}




public class itemRenderer implements ListitemRenderer
{
	public void render(Listitem li, java.lang.Object data) throws Exception
	{
		if(data!=null)
		{
			//new Listcell(data).setParent(li);
			Listcell listcell=new Listcell(data);
			listcell.value=data;
			listcell.setParent(li);
		}
	}
}


public class itemRendererArray implements ListitemRenderer
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
				listcell.setParent(li);
			}
		}
	}
}


private void executeComboQuery(String comboQuery ,String comboBox)
{
		Object rootFormValue = self.getRoot();
		Object comboObj = rootFormValue.getVariable(comboBox,true);
		comboObj.setText("");
		comboObj.getItems().clear();

		if(null != comboSelectedValue){
		int condIndex = comboQuery.indexOf("=");

		System.out.println("inside the comboQuery code:condIndex"+condIndex);
		String comboConditionValue = comboQuery.substring(0,condIndex+1);

		System.out.println("inside the comboQuery code:comboConditionValue"+comboConditionValue);
		comboQuery = comboConditionValue + comboSelectedValue;
		System.out.println("inside the comboQuery code:"+comboQuery);
		comboSelectedValue = null;
	}

	IDataUnit dataUnit = setupRequestData(comboQuery);

	ZKRouter router = new ZKRouter();
	dataUnit = router.routeToModeler(dataUnit);

	/*if (null != comboQuery)
	data.setSqlQuery(comboQuery);
	data=router.routeToModeler(data);*/

	//retrieve tree values
	 if(null != dataUnit.getQueryData().getRowValues()){
	  System.out.println("dataUnit.getQueryData().getRowValues()"+dataUnit.getQueryData().getRowValues());
	 }
	 else {
	  System.out.println("null dataUnit.getQueryData()");
	 }
	 Hashtable rowValues = dataUnit.getQueryData().getRowValues();

	 if(null != rowValues){
		 Object[] rowIds = rowValues.keySet().toArray();

		 System.out.println("executeComboQuery -rowIds size "+rowIds.length);
		 Comboitem rowItem = null;

		 if(rowIds.length > 0){
			for(int i=0; i<rowIds.length; i++ ){

				List displayValues = rowValues.get(rowIds[i]);
				String displayLabel = null;

				if (null!= displayValues){
					for(Iterator itr=displayValues.iterator(); itr.hasNext();){
						displayLabel = itr.next();
					}
				}

				rowItem = comboObj.appendItem(displayLabel);
				rowItem.setId((String)rowIds[i]+"_"+(treeNodeCount++));
			}
		 }
	}
}


private String getComboItemId(String comboBox){

  	 	String comboSelectedVal = null;
  		Object rootFormValue = self.getRoot();
		Object comboObj = rootFormValue.getVariable(comboBox,true);

   		if(null == comboSelectedValue){
	  	 System.out.println("the selected item is" +comboObj.getSelectedItem());
	  	 Comboitem selectedComboRow = comboObj.getSelectedItem();

	  	 int idSeperatorPos = selectedComboRow.getId().indexOf("_");
	  	 System.out.println("seperatorPos:"+idSeperatorPos);
	 	 comboSelectedVal = (String)selectedComboRow.getId().substring(0,idSeperatorPos);

	 	 System.out.println("the selected item id is" +comboSelectedValue);
	   //comboSelectedValue = selectedComboRow.getId();
	   //System.out.println("the selected item id is" +getComboID());

	   comboSelectedValue = comboSelectedVal;
	    formValues.put(self.id,comboSelectedValue);
	   return comboSelectedVal;
   }

}

/*This method is used by the combo,grid and list queries.
This method parses the condition string to retrieve the display name and id fields.It is also used to setup the DataUnit object*/

private IDataUnit setupRequestData(String condStr){

	IDataUnit dataUnit = new DataUnit();

	int getIndex = condStr.indexOf("get");
	int fromIndex = condStr.indexOf("from");

	QueryData requestData = new QueryData();
	String idField = null;
	int colKey=0;

	String[] paramValues = condStr.substring(getIndex+3, fromIndex).trim().split(",");

	System.out.println("index of get" +getIndex);
	System.out.println("index of from" +fromIndex);

	for(int i=0; i<paramValues.length; i++){
		String[] eachParam = paramValues[i].split("\\.");
		if(i == 0){
			idField = eachParam[1];

			}
		k=0;
		for(int j=0; j<eachParam.length; j++)
		{
			k=j+1;
			if(k<eachParam.length)
			{
				columnOrder.put(colKey,eachParam[k]);
				colKey++;
			}
		}
	}
	System.out.println("displayName" +idField);
	System.out.println("substring" +paramValues[0]);

	requestData.setCondition(condStr);
	requestData.setIdField(idField);

	//setting values in DataUnit
	IDataUnit dataUnit = new DataUnit();
	dataUnit.setMethodName("getList");
	dataUnit.setQueryData(requestData);
	dataUnit.setColumnOrder(columnOrder);

	return dataUnit;
}


public void showTree(String treeLevelInfo, String formTreeStr){

 	System.out.println("Inside showTree() child level"+formTree.getItemCount());

 	Object treeRootValue = self.getRoot();
	Object formTree = treeRootValue.getVariable(formTreeStr,true);

 	//split string containing information of each tree-level

	String[] splitTreeLevelInfo = treeLevelInfo.split(",");
	for(int i=0; i<splitTreeLevelInfo.length; i++){

		//split string of each tree-level
		String[] perLevelInfo = splitTreeLevelInfo[i].split(":");
		int firstParamIndex = splitTreeLevelInfo[i].indexOf(":");

		//System.out.println("perLevelInfo[0]" + perLevelInfo[0] + splitTreeLevelInfo[i].substring(firstParamIndex+1));

		levelPrimaryId.put(perLevelInfo[0], splitTreeLevelInfo[i].substring(firstParamIndex+1));

		lastTreeLevel = Integer.parseInt(perLevelInfo[0]);
	}

	//System.out.println("last tree level is"+ lastTreeLevel);

 	if (lastTreeLevel >= 0){
		//setting values in a DataUnit object for db operations
		DataUnit dataUnit = setupNodeLevelDbInfo("0",(String)levelPrimaryId.get("0"), null);


		IDataUnitRouter router = RendererComponent.dataUnitRouter;
		dataUnit = router.routeToModeler(dataUnit);

		//retrieve tree values
		Hashtable treeNodeValues = dataUnit.getQueryData().getRowValues();
		Object[] nodeIds = treeNodeValues.keySet().toArray();


		//displaying tree-nodes of level 0

		if(treeNodeValues.size() > 0){
			Treechildren treeChildren = new Treechildren();
			treeChildren.setParent(formTree);

			Treeitem treeItem = null;

			for(int i=0; i<treeNodeValues.size();i++){
				treeItem = new Treeitem();
				String nodeLabel = null;

				List displayValues = treeNodeValues.get(nodeIds[i]);

				if (null!= displayValues){
					for(Iterator itr=displayValues.iterator(); itr.hasNext();){
						nodeLabel = itr.next();
					}
				}

				treeItem.setId((String)nodeIds[i]+"_"+(treeNodeCount++));
				treeItem.setLabel(nodeLabel);

				treeItem.setParent(treeChildren);

			}
		}else {
			alert("Tree has no nodes");
		}
	}else {
			alert("Tree has no nodes");
	}

 }

 public void displayChildNodes(Tree formTree)
 {
  System.out.println("Inside displayChildNodes");
  Set selectedItems = formTree.getSelectedItems();
	  for(Iterator itr = selectedItems.iterator();itr.hasNext();)
	  {

		 Treeitem treeNode = (Treeitem) itr.next();
		 /*System.out.println("Tree node:"+treeNode);
		 System.out.println("Tree node id:"+treeNode.getId());
		 System.out.println("Tree node id:"+treeNode.getLabel());
		 System.out.println("Tree node level"+treeNode.getLevel());*/

		 //setting values in a DataUnit object for db operations
		 String childLevel = (treeNode.getLevel()+1)+"";

		 int idSeperatorPos = treeNode.getId().indexOf("_");
		 //System.out.println("seperatorPos:"+idSeperatorPos);
		 String parentId = (String)treeNode.getId().substring(0,idSeperatorPos);

		 /*System.out.println("childLevel" +childLevel);
		 System.out.println("level string" +childLevel);
		 System.out.println("parentId" +parentId);*/

		 if(Integer.parseInt(childLevel) <= lastTreeLevel){
			 DataUnit dataUnit = setupNodeLevelDbInfo(childLevel,(String)levelPrimaryId.get(childLevel), parentId);

			 IDataUnitRouter router = RendererComponent.dataUnitRouter;
			 dataUnit = router.routeToModeler(dataUnit);

			 //retrieve tree values
			 /*if(null != dataUnit.getQueryData().getRowValues()){
			  System.out.println("dataUnit.getQueryData()"+dataUnit.getQueryData().getRowValues());
			 }
			 else {
			  System.out.println("null dataUnit.getTreeData()");
			 }*/
			 Hashtable treeNodeValues = dataUnit.getQueryData().getRowValues();


			 if(treeNodeValues.size() > 0){
				 Object[] nodeIds = treeNodeValues.keySet().toArray();

				 //displaying tree-nodes of level 0

				 Treechildren treeChildren = new Treechildren();
				 treeChildren.setParent(treeNode);

				 Treeitem treeItem = null;

				 for(int i=0; i<treeNodeValues.size();i++){
					treeItem = new Treeitem();
					String nodeLabel = null;

					List displayValues = treeNodeValues.get(nodeIds[i]);

					if (null!= displayValues){
						for(Iterator itr=displayValues.iterator(); itr.hasNext();){
							nodeLabel = itr.next();
						}
					}

					treeItem.setId((String)nodeIds[i]+"_"+(treeNodeCount++));
					treeItem.setLabel(nodeLabel);

					treeItem.setParent(treeChildren);
				 }
			   } else {
			   	alert("Level " + treeNode.getLabel() + " has no children");
			   }
			}else {
				alert("Level " + treeNode.getLabel() + " has no children");
			}

	       }

 }

 private IDataUnit setupNodeLevelDbInfo(String level, String perLevelInfo, String condValue){

 	String[] levelInfo = perLevelInfo.split(":");

	 	//setting values in a TreeNode data-object
	 	QueryData queryRequestData = new QueryData();

		String tableName = levelInfo[0];
		String idField = levelInfo[1];
		String displayName = levelInfo[2];
		String parentIdField = levelInfo[3];
		String condition = null;

		if (level.equals("0")){
			condition = "get " + tableName + "." + idField + "," + tableName + "." + displayName + " from " + tableName;
		} else {
			condition = "get " + tableName + "." + idField + "," + tableName + "." + displayName + " from " + tableName + " conditions " + tableName + "." + parentIdField + ":=" + condValue;
		}
	 	//System.out.println(condition);
	 	queryRequestData.setCondition(condition);
	 	queryRequestData.setIdField(idField);

	 	//setting values in DataUnit
	 	IDataUnit dataUnit = new DataUnit();
	 	dataUnit.setMethodName("getList");
	 	dataUnit.setQueryData(queryRequestData);

	 	return dataUnit;
}





	private IDataUnit setRequestData(String condStr)
	{
		IDataUnit dataUnit = new DataUnit();
		int getIndex = condStr.indexOf("get");
		int fromIndex = condStr.indexOf("from");
		QueryData requestData = new QueryData();
		String idField = null;
		int colKey=0;
		String[] paramValues = condStr.substring(getIndex+3, fromIndex).trim().split(",");
		System.out.println("index of get" +getIndex);
		System.out.println("index of from" +fromIndex);
		for(int i=0; i<paramValues.length; i++)
		{
			String[] eachParam = paramValues[i].split("\\.");
			if(i == 0)
			{
				idField = eachParam[1];
			}
			k=0;
			queryFields=new String[eachParam.length+paramValues.length];
			for(int j=0; j<eachParam.length; j++)
			{
				k=j+1;
				if(k<eachParam.length)
				{
					columnOrder.put(colKey,eachParam[k]);
					queryFields[colKey] = eachParam[k];
					colKey++;
				}
			}
		}
		requestData.setCondition(condStr);
		requestData.setIdField(idField);
		//setting values in DataUnit
		dataUnit.setMethodName("getListData");
		dataUnit.setQueryData(requestData);
		System.out.println("columnOrder--"+columnOrder);
		
		dataUnit.setColumnOrder(columnOrder);

		return dataUnit;
}




public String getRowData(int index)
{
	List cell=null;
	String output="";

	Set selectedItems = self.getSelectedItems();
	for(Iterator itr = selectedItems.iterator();itr.hasNext();)
	{

		Listitem listNode = (Listitem) itr.next();
		System.out.println("list node:"+listNode);
		cell =listNode.getChildren();
	}

	for(Iterator itr=cell.iterator(); itr.hasNext();)
	{
		Listcell lc=(Listcell)itr.next();
		if(lc.getColumnIndex()==index)
		{
			output=lc.value;
		}
	}
	return output;
}



// Client side methods

void isMandatoryMethod(Object idValue)
{
	if(idValue.value.trim().equals(""))
	{
		alert(idValue.id +" Field is mandatory");
		idValue.focus();
		System.out.println("inside the blank value ");
	}
}

boolean clientSideValidation(Object formId)
{
	boolean valid = false;
	List childElement = formId.getChildren();
  	Iterator iter  = childElement.iterator();
  	while(iter.hasNext())
	{
		Object value = iter.next();
		if(null != value)
		{
			String textBoxValue = value.id.substring(0,7);
			if(textBoxValue.equalsIgnoreCase("textbox") && (null != value.constraint))
			{
				String comValue = value.getValue();
				if(null != comValue)
				valid = true;

			}
		}
	}
	return valid;
}



private String getComboItemId1(String comboBox){

	String comboSelectedVal = null;
	Object rootFormValue = self.getRoot();
	Object comboObj = rootFormValue.getVariable(comboBox,true);
	Comboitem selectedComboRow = comboObj.getSelectedItem();

	if (selectedComboRow.getId().indexOf("_") != -1)
	{
		int idSeperatorPos = selectedComboRow.getId().indexOf("_");
		comboSelectedVal = (String)selectedComboRow.getId().substring(0,idSeperatorPos);
	}
	else
	{
		comboSelectedVal = (String)selectedComboRow.getId();
	}

	//comboSelectedValue = selectedComboRow.getId();
	//System.out.println("the selected item id is" +getComboID());

	comboSelectedValue = comboSelectedVal;
	formValues.put(self.id,comboSelectedValue);
	comboSelectedValue=null;
	return comboSelectedVal;
}


void printReport(String printQuery ,String report ,String reportfile)
{
     Object rootFormValue = self.getRoot();
	 Object reportObj = rootFormValue.getVariable(report,true);
	System.out.println("Inside the print report method:");
	IDataUnitRouter router = RendererComponent.dataUnitRouter;
	IDataUnit dataUnit = new DataUnit();
	dataUnit.setMethodName("printReport");
	dataUnit.setSqlQuery(printQuery);
	dataUnit.setDataObject(reportObj);
	dataUnit.setParentId(reportfile);
	dataUnit = router.routeToModeler(dataUnit);
	Jasperreport jasperReport = (Jasperreport)dataObject;
	
	Map parameters = new HashMap();
	parameters.put("Title", "Report");
	
	int getIndex = printQuery.indexOf("select");
	int fromIndex = printQuery.indexOf("from");
	String idValue = null;
	StringBuffer idField = new StringBuffer();
	int colKey=0;
	
	String[] paramValues = printQuery.substring(getIndex+6, fromIndex).trim().split(",");
	String[] fields = new String[paramValues.length];
	System.out.println("index of get" +getIndex);
	System.out.println("index of from" +fromIndex);
	
	for(int i=0; i<paramValues.length; i++)
	{
		String[] eachParam = paramValues[i].split("\\.");	
		for(int j=0;j<eachParam.length;j++)
		{
			if(j==0)
			{
			idValue = eachParam[1];
			fields[colKey]=idValue;
			System.out.println("fields value is "  +fields[colKey]);
			colKey++;
		}
		}
	}
	HibernateQueryResultDataSource ds = new HibernateQueryResultDataSource(value, fields);
	jasperReport.setSrc(reportfile);
	jasperReport.setParameters(parameters);
	jasperReport.setDatasource(ds);	
	System.out.println("Inside the uilibrary After printing the file ");

}

String invokeComponent(String componentId ,String method ,String classname, String paramList)
{
	String isValidStatus = "true";
	//IRouter router = RendererComponent.router;
	IDataUnitRouter router = RendererComponent.dataUnitRouter;
	IHICData returnData = null;
	Object elementObj= null;
	String[] strValue=null;
	String reportQuery;
	String reportFile;
	IDataUnit dataUnit = new DataUnit();
	Object rootFormValue = self.getRoot();
	
	System.out.println("=current App Name zs setdataUnit=="+session.getAttribute("currentApplicationName"));
	dataUnit.setCurrentApplicationName(session.getAttribute("currentApplicationName"));
	
	dataUnit.setFormId(formPatternId);
	dataUnit.setDataPatternId(dataPatternId);
	dataUnit.setMethodName(method);
	System.out.println(method + "==============" + dataUnit.getMethodName());

	dataUnit.setInvokeComponentId(componentId);
	System.out.println(componentId + "==============" + dataUnit.getInvokeComponentId());

	dataUnit.setInvokeComponentClass(classname);
	System.out.println(classname + "==============" + dataUnit.getInvokeComponentClass());

	dataUnit.setFormValues(formValues);
	if(method.equalsIgnoreCase("authenticateUserEx"))
	{
		System.out.println(" in side authenticateUserEx----in zs----paramList---- "+paramList);
		String fieldName ;
		String fieldId;
		Hashtable userauth = new Hashtable();
		if(paramList.indexOf("_FIELDSEP_")>=0)
		{
			String[] strValue = paramList.trim().split("_FIELDSEP_");
			if(strValue.length ==2)
			{
				for(int i=0;i<strValue.length;i++)
				{
					String fieldExp = strValue[i];
					if(fieldExp.indexOf("_SEP_")>=0)
					{
						String[] fexp = fieldExp.trim().split("_SEP_");
						fieldName = fexp[0];
						System.out.println("----fieldname----in zs--"+fieldName);
						fieldId = fexp[1];
						System.out.println("----fieldId----in zs--"+fieldId);
						String fieldVal = formValues.get(fieldId);
						System.out.println("----fieldVal----in zs--"+fieldVal);
						if(fieldName!=null && fieldVal!=null)
						{
							userauth.put(fieldName, fieldVal);
						}
					}
					else
					{
						alert("Invalid arguments");
					}
					
				}
	
			}
		}
		else
		{
			alert("Invalid arguments");
		}
		dataUnit.setFormId(formPatternId);
		dataUnit.setDataPatternId(dataPatternId);
		dataUnit.setMethodName(method);
		dataUnit.setInvokeComponentId(componentId);
		dataUnit.setInvokeComponentClass(classname);
		dataUnit.setFormValues(userauth);
		dataUnit.setCurrentApplicationName(session.getAttribute("currentApplicationName"));
	}
	else if(method.equalsIgnoreCase("executeQuery")|| method.equalsIgnoreCase("executeQueryUserAdmin"))
	{
		System.out.println(" in side excute query-------in zs-------- ");
		//dataUnit = splitParamList(paramList,rootFormValue);
		String comboQuery ;
		String comboId;
		String[] strValue = paramList.trim().split("_SEP_");
		if(strValue.length ==2)
		{
			comboQuery =strValue[0];
			comboId = strValue[1];

		}
		else
		{
			alert("Invalid query arguments");
		}
		comboObj = rootFormValue.getVariable(comboId,true);
		dataUnit = setComboQuery(comboQuery,comboId);
		dataUnit.setFormId(formPatternId);
		dataUnit.setMethodName(method);
		dataUnit.setInvokeComponentId(componentId);
		dataUnit.setInvokeComponentClass(classname);
		dataUnit.setFormValues(formValues);
		dataUnit.setCurrentApplicationName(session.getAttribute("currentApplicationName"));
	}
	else if(method.equalsIgnoreCase("moveForm"))
	{
		Hashtable reqData = new Hashtable();
		reqData.put("destinationForm",paramList);
		dataUnit.setFormValues(reqData);
		session.getWebApp().setAttribute("currentPage",paramList);
	}
	else if(method.equalsIgnoreCase("executeList")||method.equalsIgnoreCase("executeListUserAdmin"))
	{
		System.out.println(" in side excute List query-------in zs-------- ");
		String listQuery ;
		String listId;
		paramList = reformatQueryStatement(paramList);
		strValue = paramList.trim().split("_SEP_");
		if(strValue.length ==2)
		{
			listQuery =strValue[0];
			listId = strValue[1];

		}
		else
		{
			listQuery =strValue[0];
		}
		elementObj = rootFormValue.getVariable(listId,true);
		System.out.println("==listquery--"+listQuery);
		
		dataUnit = setRequestData(listQuery);
		
		dataUnit.setFormId(formPatternId);
		dataUnit.setMethodName(method);
		dataUnit.setInvokeComponentId(componentId);
		dataUnit.setInvokeComponentClass(classname);
		dataUnit.setFormValues(formValues);
		dataUnit.setCurrentApplicationName(session.getAttribute("currentApplicationName"));
	}
	else if(method.equalsIgnoreCase("updateRecord"))
	{
		System.out.println(" in side excute updateRecord query-------in zs-------- ");
		QueryData requestData = new QueryData();
		paramList = reformatQueryStatement(paramList);
		String listQuery ;
		String listId;
		String[] strValue = paramList.trim().split("_SEP_");
		if(strValue.length ==2)
		{
			listQuery =strValue[0];
			listId = strValue[1];

		}
		else
		{
			alert("Invalid query arguments");
		}
		if(session.getAttribute("rowId")==null)
		{
			alert("Please select at least one record to delete");
			return "Error";
		}
		else
		{
			if(!(Messagebox.show("Do you want to delete this record?", "Question", Messagebox.YES | Messagebox.NO, "Messagebox.QUESTION")==Messagebox.YES))
				{
					return "Error";
				
				}	
		}
		
		listQuery = listQuery.replaceAll("rowId", session.getAttribute("rowId"));
		requestData.setCondition(listQuery);
		
		dataUnit.setQueryData(requestData);
		dataUnit.setFormId(formPatternId);
		dataUnit.setMethodName(method);
		dataUnit.setInvokeComponentId(componentId);
		dataUnit.setInvokeComponentClass(classname);
		dataUnit.setFormValues(formValues);
		dataUnit.setCurrentApplicationName(session.getAttribute("currentApplicationName"));
	}
	else if(method.equalsIgnoreCase("deleteUserFromApplication")||method.equalsIgnoreCase("getComboDataFromUserAdmin"))
	{
		System.out.println(" in deleteUserFromApplication getComboDataFromUserAdmin-in zs--Check param-- "+paramList);
		QueryData requestData = new QueryData();
		String listQuery ;
		String listId;
		String[] strValue = paramList.trim().split("_SEP_");
		if(strValue.length ==2)
		{
			listQuery =strValue[0];
			System.out.println(" in deleteUserFromApplication-listQuery-- "+listQuery);
			listId = strValue[1];
			System.out.println(" in deleteUserFromApplication-listId-- "+listId);

		}
		else
		{
			alert("Invalid query arguments");
		}
		if(method.equalsIgnoreCase("deleteUserFromApplication"))
		{
			if(session.getAttribute("rowId")==null)
			{
				alert("Please select at least one record to delete");
				return "Error";
			}
		
			listQuery = listQuery.replaceAll("rowId", session.getAttribute("rowId"));
			System.out.println(" in deleteUserFromApplication-after replace listQuery-- "+listQuery);
		}
		System.out.println(" in replace listQuery-- "+listQuery);

		requestData.setCondition(listQuery);
		
		dataUnit.setQueryData(requestData);
		dataUnit.setFormId(formPatternId);
		dataUnit.setMethodName(method);
		dataUnit.setInvokeComponentId(componentId);
		dataUnit.setInvokeComponentClass(classname);
		dataUnit.setFormValues(formValues);
		dataUnit.setCurrentApplicationName(session.getAttribute("currentApplicationName"));
	}
	else if(method.equalsIgnoreCase("changeDOStatus"))
	{
		strValue = paramList.trim().split("_SEP_");

		if (strValue.length == 1)
		{
			dataUnit.setStatus(strValue[0]);
		}
		else if (strValue.length == 2)
		{
			dataUnit.setStatus(strValue[0]);
			dataUnit.setFormId(strValue[1]);
		}
		else if (strValue.length == 3)
		{
			dataUnit.setStatus(strValue[0]);
			dataUnit.setFormId(strValue[1]);
			dataUnit.setDataPatternId(strValue[2]);
			session.getWebApp().setAttribute("currentPage",strValue[1]);
		}
		dataUnit.setInvokeComponentId(null);
		dataUnit.setCurrentApplicationName(session.getAttribute("currentApplicationName"));
	}
	else if(method.equalsIgnoreCase("invokeClientCall"))
	{
		dataUnit.setMetaData(paramList);
		
	}
	else if(method.equalsIgnoreCase("reportData"))
	{
			String[] reportArg = paramList.trim().split("_SEP_");
			
			String reportId;
			
			if(reportArg.length>=3)
			{
				reportQuery = reportArg[0];
				reportFile = reportArg[1];
				reportId = reportArg[2];
			}
				
			elementObj = rootFormValue.getVariable(reportId,true);
			System.out.println("==listquery--"+reportQuery);
			//dataUnit = setRequestData(reportQuery);
			QueryData requestData = new QueryData();
			
			requestData.setCondition(reportQuery);
			dataUnit.setMethodName("getReportData");
			dataUnit.setQueryData(requestData);
			dataUnit.setFormId(formPatternId);
			dataUnit.setMethodName(method);
			dataUnit.setInvokeComponentId(componentId);
			dataUnit.setInvokeComponentClass(classname);
			dataUnit.setFormValues(formValues);
			dataUnit.setCurrentApplicationName(session.getAttribute("currentApplicationName"));	
			
	}
	ISource src = new Source();
	src.setMethodName("invokeComponent");
	dataUnit.setSource(src);

	System.out.println("--Present in invokeComp--Check userId in session----"+session.getAttribute("userId"));
	System.out.println("--Present in invokeComp--Check eibunid in session----"+session.getAttribute("EIBUNID"));

	dataUnit.setUniqueId(session.getAttribute("EIBUNID"));
	dataUnit.setUserId(session.getAttribute("userId"));
	if(session.getAttribute("userPatterns")!=null)
	{
		Set uspset = new HashSet();
		String [] userpatternids = session.getAttribute("userPatterns").split("##");
		for(int i=0;i<userpatternids.length;i++)
		{
			IUserPattern usp = new UserPattern();
			usp.setUserPatternId(userpatternids[i]);
			uspset.add(usp);
		}
		dataUnit.setUserPatterns(uspset);
	}
	else
	{
		System.out.println("=================4==================");
		dataUnit.setUserPatterns(null);
	}

	
	//*************
	if(method.equalsIgnoreCase("save")||method.equalsIgnoreCase("addUserFromApplication"))
	{
		
		if(clientSideValidation(formPatternId))
		{
			validationValue=true;
		}
		else
		{
			validationValue=false;
		}
		  	
	}
	
	if(validationValue)
	{
		returnHicData =  router.routeToModeler(dataUnit);
	}
	else
		return "Error";
	
	
	
	
	
	
	//returnHicData =  router.routeToModeler(dataUnit);

	System.out.println("=================6==================");
	
	if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null) 
	{
		if(returnHicData.getData().getStatus().equalsIgnoreCase("error"))
		{
			isValidStatus = "Error";
			return isValidStatus;
		}
	}
	
	if(method.equalsIgnoreCase("authenticateUserEx"))
	{
		System.out.println("=================7==================");
		if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null) 
		{
			if(returnHicData.getData().getStatus().equalsIgnoreCase("invalid"))
			{
				alert("Login credientials are not valid");
				return "Error";
			}
			else
			{
				loginStatus = true;
								
			}
		}
	}
	if(method.equalsIgnoreCase("executeQuery")|| method.equalsIgnoreCase("executeQueryUserAdmin"))
	{
		showComboData(returnHicData,comboObj);
	}
	if(method.equalsIgnoreCase("executeList")||method.equalsIgnoreCase("executeListUserAdmin")||method.equalsIgnoreCase("getComboDataFromUserAdmin"))
	{
		System.out.println("----------start showlistdata----------------------");
		if(strValue == null)
		{
			IData dataUnit = returnHicData.getData();
			updateHash = dataUnit.getQueryData().getColOrderValues();
			System.out.println("----hashtable-returned--"+updateHash);
			return isValidStatus;
		}
		else if(strValue.length ==1)
		{
			IData dataUnit = returnHicData.getData();
			updateHash = dataUnit.getQueryData().getColOrderValues();
			return isValidStatus;

		}
		showListData(returnHicData,elementObj);
	}
	   if(method.equalsIgnoreCase("sendandrecieve"))
       {
	        System.out.println("================for HL7Component=================");
	        if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null ) 
	        {
	             String message= returnHicData.getData().getStatus();
	             alert("The return message is:"+message);
	             return message;
		        /* if(message.equalsIgnoreCase("Default"))
		         {
		         	alert("Please fill the data ");
		         }
		         else
		         {
		        	alert("The return message is:"+message);
		         }*/
	        }
        
  		}
		
	if(method.equalsIgnoreCase("invokeClientCall"))
	{
		webMessage = (String) returnHicData.getMetaData().getCommonObject();
		//alert("The output is="+webMessage);
		return webMessage;
		
	}
	
	if(method.equalsIgnoreCase("reportData"))
	{
		IData dataUnit = returnHicData.getData();
		List reportList= dataUnit.getQueryData().getListData();
		String reportPath = "/zul/"+reportFile;
		showReport(elementObj,reportList,reportQuery,reportPath);
		return isValidStatus;
		
	}

	System.out.println("Before calling sessionUpdate----  ");
	sessionUpdate(returnHicData);
	System.out.println("Inside the uilibrary After invokecomponent----  "+returnHicData);
	return isValidStatus;

}

void sessionUpdate(IHICData returnHicData)
{
	System.out.println("--Present in session update--Check userId in session----"+session.getAttribute("userId"));
	System.out.println("--Present in session update--Check eibunid in session----"+session.getAttribute("EIBUNID"));
	System.out.println("--Present in session update--Check userpatternString in session----"+session.getAttribute("userPatterns"));
	if(session.getAttribute("userId")== null || (session.getAttribute("userId")!=returnHicData.getData().getUserId()))
	{
		session.setAttribute("userId",returnHicData.getData().getUserId());
		Set usp = returnHicData.getData().getUserPatterns();
		if(usp!=null)
		{
			String userpatterids = "";
			Iterator usrpt = usp.iterator();
			while(usrpt.hasNext())
			{
				System.out.println(" --------1------");
				IUserPattern pt = (IUserPattern)usrpt.next();
				System.out.println(" --------2------");

				if(userpatterids.equals(""))
				{
					userpatterids = pt.getUserPatternId();
				}
				else
				{
					userpatterids = userpatterids+"##"+pt.getUserPatternId();
				}
			}
			System.out.println("--Present in session update--Check userPattern String----"+userpatterids);

			session.setAttribute("userPatterns",userpatterids);
		}

	}
	if(session.getAttribute("EIBUNID")== null || (session.getAttribute("EIBUNID")!=returnHicData.getUniqueID()))
	{
		session.setAttribute("EIBUNID",returnHicData.getUniqueID());
	}
}

void authenticateUserExernally(String userFieldId, String pwdFieldId)
{
System.out.println("-------- Present in authenticateUserExernally--in uiLibrary-userFieldId-"+userFieldId);
System.out.println("-------- Present in authenticateUserExernally--in pwdFieldId---"+pwdFieldId);
 	 HICRouter router = RendererComponent.router;
	 IDataUnit dataUnit = new DataUnit();
	 dataUnit.setMethodName("authenticateUserEx");
	 Hashtable reqData = new Hashtable();
	 reqData.put("userName",formValues.get(userFieldId));
	 reqData.put("password",formValues.get(pwdFieldId));
	 dataUnit.setFormValues(reqData);
	 System.out.println("Inside the uilibrary Calling routeToModeler  ");
	IHICData data1 = router.routeToModeler(dataUnit);
	System.out.println("Inside the uilibrary After authenticateUserExernally  "+data1);

}

private IDataUnit splitParamList(String listArgument,Object rootValue)
{
	String comboQuery ;
	String comboId;
	String[] strValue = listArgument.trim().split("_SEP_");
	if(strValue.length ==2)
	{
		comboQuery =strValue[0];
		comboId = strValue[1];

	}
	else
	{
		alert("Invalid query arguments");
	}
	comboObj = rootValue.getVariable(comboId,true);
	IDataUnit dataUnit = setComboQuery(comboQuery,comboId);
	return dataUnit;

}

private IDataUnit setComboQuery(String comboQuery ,String comboBox)
{

		if(null != comboSelectedValue)
		{
			int condIndex = comboQuery.indexOf("=");

			System.out.println("inside the comboQuery code:condIndex"+condIndex);
			String comboConditionValue = comboQuery.substring(0,condIndex+1);

			System.out.println("inside the comboQuery code:comboConditionValue"+comboConditionValue);
			comboQuery = comboConditionValue + comboSelectedValue;
			System.out.println("inside the comboQuery code:"+comboQuery);
			comboSelectedValue = null;
		}

		IDataUnit dataUnit = setupRequestData(comboQuery);

		return dataUnit;

}

private void showComboData(IHICData hicData,Object comboObj)
{

	comboObj.setText("");
	//alert("in side showcombodata-obj---"+comboObj);
	//alert("in side showcombodata-id---"+comboObj.id);
	comboObj.getItems().clear();
	IData dataUnit = hicData.getData();
	if(null != dataUnit.getQueryData().getRowValues())
	{
	  	System.out.println("dataUnit.getQueryData().getRowValues()"+dataUnit.getQueryData().getRowValues());
	}
	 else
	 {
	  	System.out.println("null dataUnit.getQueryData()");
	 }
	 Hashtable rowValues = dataUnit.getQueryData().getRowValues();

	 if(null != rowValues)
	 {
		 Object[] rowIds = rowValues.keySet().toArray();

		 System.out.println("executeComboQuery -rowIds size "+rowIds.length);
		 Comboitem rowItem = null;

		 if(rowIds.length > 0)
	 	 {
	 	 	//comboTable = rowValues;
	 	 	comboTable.put(comboObj.id,rowValues);
			for(int i=0; i<rowIds.length; i++ )
			{

				List displayValues = rowValues.get(rowIds[i]);
				
				String displayLabel = null;

				if (null!= displayValues)
				{
					for(Iterator itr=displayValues.iterator(); itr.hasNext();)
					{
						displayLabel = itr.next();
					}
				}

				rowItem = comboObj.appendItem(displayLabel);
				rowItem.setId((String)rowIds[i]+"_"+(treeNodeCount++));
			}
		 }
	}
}


public void showListData(IHICData hicData, Object elementObj)
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
		IData dataUnit = hicData.getData();
		List listValue = dataUnit.getQueryData().getListData();
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		ListModel myModel = new ListModelList(allValues);
		elementObj.setModel(myModel);
		elementObj.setItemRenderer(new itemRendererArray());

		
}


void setDataStatus(String status, String formPattern, String dataPattern)
{
	invokeComponent(null, "changeDOStatus", null, status + "_SEP_" + formPattern + "_SEP_" + dataPattern);
}

private String getCurrentUserPattern()
{
	return session.getAttribute("userPatterns");
}


void clearFormData(String formId)
{
	 Object rootFormValue = self.getRoot();
	 Object formObj = rootFormValue.getVariable(formId,true);
	 List childElement = formObj.getChildren();
	 Iterator iter  = childElement.iterator();
	 while(iter.hasNext())
	 {
		  Object value = iter.next();
		  clearFormData(value.id);
		  if ((value.id.indexOf("textbox")>=0) || (value.id.indexOf("combobox")>=0))
		  {
			    Constraint cons=value.getConstraint();
			    if(cons==null|| cons=="")    
			    {
			   		value.setValue("");
			    }
			    else
			    {
			        value.setConstraint("");
			        value.setValue("");
			        value.setConstraint(cons);      
			    }
		  }
		  else  if ((value.id.indexOf("datepicker")>=0))
		  {
		   Constraint consd=value.getConstraint();
			    if(consd==null|| consd=="")    
			    {
			   		value.setValue(null);
			    }
			    else
			    {
			        value.setConstraint("");
			        value.setValue(null);
			        value.setConstraint(consd);      
			    }
		  }
	 }
	 formValues.clear();
}




boolean checkValueWithOperator(Object val1, Object val2, String checkType)
{
	if (checkType.equals("EQUAL"))
	{
		if (val1.equals(val2)) return true;
	}
	return false;
}

Object createArray(String arrayType, String arrayValues)
{
	if (arrayType.equals("String"))
	{
		return arrayValues.split(",");
	}
	return null;
}

String reformatQueryStatement(String query)
{
	System.out.println("[[[[[reformatQueryStatement]]]]][[[[[Before]]]]]" + query);
	String retVal = query.replaceAll("_DATESTART_", "[");
	retVal = retVal.replaceAll("_DATEEND_", "]");
	retVal = retVal.replaceAll("_STRINGSTART_", "[");
	retVal = retVal.replaceAll("_STRINGEND_", "]");
	System.out.println("[[[[[reformatQueryStatement]]]]][[[[[After]]]]]" + retVal);
	return retVal;
}

public boolean clientSideValidation(String formId)
{
	 boolean valid = true;
	 Object rootFormValue = self.getRoot();
	 Object formObj = rootFormValue.getVariable(formId,true);
 	 List childElement;
	 if(formObj!=null)
	 {
		childElement = formObj.getChildren();
	       
	 	Iterator iter  = childElement.iterator();
	
	   	while(iter.hasNext())
	 	{
	
		  Object value = iter.next();
		  valid = clientSideValidation(value.id);
		  if (!valid) return valid;
	
		  if ((value.id.indexOf("combobox")>=0) || (value.id.indexOf("textbox")>=0) || (value.id.indexOf("datepicker")>=0))
		  {
		  		//alert(" value--.constraint"+value.constraint);
				if(value.constraint != null )
				{	
					String comValue = formValues.get(value.id);
					if(""== comValue  || null == comValue)
					{
						valid = false;
						value.focus();
						alert(value.name+" can not be empty");
						return valid;
					}
	
				}
  		   }
  		   else if((value.id.indexOf("time")>=0))
		   {
		   		//alert(" value--.constraint"+value.constraint);
		   	     if(value.constraint != null )
		   		 {	
		   			Time comValue = formValues.get(value.id);
		   			if(""== comValue  || null == comValue)
		   			 {
		   				valid = false;
		   				value.focus();
		   				alert(value.name+" can not be empty");
		   				return valid;
		   			 }
		   	
		   		}
  		   } 		
 		}
 	  }
 	return valid;
}

public boolean validateDate(String datepicker1,String datepicker2)
{
	 Object rootFormValue = self.getRoot();
	 Object datepickerObj1 = rootFormValue.getVariable(datepicker1,true);
	  Object datepickerObj2 = rootFormValue.getVariable(datepicker2,true);
	 String date1=formValues.get(datepicker1);
	 String date2 = formValues.get(datepicker2);
	 if(date2==null || date2=="")
	 {
	 	alert("Please select "+datepickerObj2.name);
	 	formValues.remove(datepicker1);
	  	datepickerObj1.setValue(null);
	  	datepickerObj2.focus();
	 	return false;
	 }
	
	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 Date convertedDate1 = dateFormat.parse(date1); 
	 Date convertedDate2= dateFormat.parse(date2); 
	 Date current = new Date();	
 	 String curDate=dateFormat.format(current);
 	 Date currentDate =dateFormat.parse(curDate);
	 
	 if(convertedDate2.before(currentDate))
	 {
	 	alert("Invalid Date Please select current/advance date");
	 	formValues.remove(datepicker2);
	 	datepickerObj2.setValue(null);
	 	
		return false;	 	
	 }
	
	 if(convertedDate1.equals(convertedDate2))
	 {
	  	isDateValid =true;
	 }
	 else if(convertedDate1.after(convertedDate2))
	 {
	 	 isDateValid =true;
	
	 }
	 else
	 {
	  		alert("End Date should be greater than Start Date");
	  		formValues.remove(datepicker1);
	  		datepickerObj1.setValue(null);
	  		isDateValid =false;
	 }
	 
	 return isDateValid;

}

public boolean equalsTest(String password,String conPassword)
{
	 if (password.equalsIgnoreCase(conPassword))
	 	return true;
	 else 
	 	return false;
}

public boolean selectCurrentOrFutureDate(String datepicker1)
{
	 boolean isDateValid = true;
	 Object rootFormValue = self.getRoot();
	 Object datepickerObj1 = rootFormValue.getVariable(datepicker1,true);
	// String date1=formValues.get(datepicker1);
	 String date1= (new SimpleDateFormat("yyyy-MM-dd").format(self.value));
	 if(date1==null || date1=="")
	 {
	 	alert(datepickerObj1.name +"can not be empty");
	 	
	 	return false;
	 }
	 
	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 Date convertedDate1 = dateFormat.parse(date1); 
	 Date current = new Date();	
 	 String curDate=dateFormat.format(current);
 	 Date currentDate =dateFormat.parse(curDate);
	 
	 if(convertedDate1.before(currentDate))
	 {
	 	alert("Invalid Date Please select current/advance date");
	 	formValues.remove(datepicker1);
	 	datepickerObj1.setValue(null);
	 	return false;	 	
	 }
	
	 return isDateValid;

}
showWebServiceMessage(String displayControl)
{
	 Object rootFormValue = self.getRoot();
	 Object displayObject = rootFormValue.getVariable(displayControl,true);
	 displayObject.setValue(webMessage);
}


void showReport(String controlObject, String jasperFile)
{
     	  	
	if(returnHicData ==null)
	{
		alert("HIC Return data is null");
		return;
	}
	
	jasperFile ="/zul/"+jasperFile;
	IData dataUnit = returnHicData.getData();
	List listValue = dataUnit.getQueryData().getListData();
 	Object rootFormValue = self.getRoot();
	Object displayObject = rootFormValue.getVariable(controlObject,true);
	Jasperreport jasperReport = (Jasperreport)displayObject;
	String reportQuery = dataUnit.getQueryData().getCondition();
	int getIndex = reportQuery.indexOf("get");
	int fromIndex = reportQuery.indexOf("from");
	String idValue = null;
	StringBuffer idField = new StringBuffer();
	int colKey=0;
	
	String[] paramValues = reportQuery.substring(getIndex+6, fromIndex).trim().split(",");
	String[] fields = new String[paramValues.length];
	for(int i=0;i<paramValues.length;i++)
	{
		String[] childValue  = paramValues[i].trim().split("\\.");
		fields[i]=childValue[1] ; 
		
	}
	
	Map parameters = new HashMap();
	parameters.put("Title", "Report");
	HibernateQueryResultDataSource ds = new HibernateQueryResultDataSource(listValue,fields);
	jasperReport.setSrc(jasperFile);
	jasperReport.setParameters(parameters);
	jasperReport.setDatasource(ds);	

}


void displayDataInControl(String controlObj)
{

	Object rootFormValue = self.getRoot();
	Object elementObj = rootFormValue.getVariable(controlObj,true);
	if(elementObj instanceof Grid || elementObj instanceof Listbox )
	{
		showListData(null ,elementObj);	
	}
}















/*
class LiveGroupRenderer implements RowRenderer,ListitemRenderer{
		public void render(Row row, java.lang.Object data) {
			if(data instanceof String[]) {
				String[] ary = (String[]) data;
	            Div div = new Div();
	            Image icon = new Image();
	            icon.setStyle("padding: 0px 10px");
	            icon.setSrc("/img/Centigrade-Widget-Icons/EnvelopeOpen-16x16.png");
	            div.appendChild(icon);
	            new Label(ary[0]).setParent(div);
	            row.appendChild(div);
	            new Label(ary[1]).setParent(row);
	            new Label(ary[2]).setParent(row);
	            new Label(ary[3]).setParent(row);
			} else {
				new Label(data.toString()).setParent(row);
			}
	        
		}
	}
	


public void showModelGrid(String gridObj)
{

	Object rootFormValue = self.getRoot();
	Object elementObj = rootFormValue.getVariable(gridObj,true);

	String[][] data1=new String[][]{new String[]{"BED 1","Wasim Khan","2008/11/18 6:27","16KB"},new String[]{"BED 2","Wasim Khan","2008/11/18 6:27","16KB"},new String[]{"BED 3","Wasim Khan","2008/11/18 6:27","16KB"},new String[]{"BED 4","Wasim Khan","2008/11/18 6:27","16KB"}}; 
	
	String[][] data2 =new String[][] {new String[]{"BED 1","Rohan","2008/11/17 19:44:17","39KB"}};
	String[][] data3 =new String[][] {new String[]{"BED 1","puneet","2008/11/15 4:31:12","14KB"}};
	String[][] data4 =new String[][] {new String[]{"BED 1","puneet","2008/11/15 4:31:12","14KB"}};
	String[][] data5 =new String[][] {new String[]{"BED 1","puneet","2008/11/15 4:31:12","14KB"}};
	
	String[][][] datas = new String[][][]{data1,data2,data3,data4,data5};
			
			String[] date =  new String[]{"JANUARY", "Date: MARCH", "Date: MAY", "Date: JULY", "Date: AUGUST", "Date: October", "Date: December"};
			GroupsModel model = new SimpleGroupsModel(datas,date);
	
			//ListModel myModel = new ListModelList(allvalue);
			//LiveGroupRenderer renderer = new LiveGroupRenderer();
			elementObj.setModel(model);
			//elementObj.setItemRenderer(new LiveGroupRenderer());

}
*/


void move(Component dragged)
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
		else
		{
		alert("else");
			self.appendChild(dragged);
		}
}
	

// for drag and drop code change class name

public class itemRendererArray implements ListitemRenderer
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
		else
		{	  
		 List list=dragged.getChildren();
		 StringBuffer str = new StringBuffer();
		 for(int i=0;i<dragged.getChildren().size();i++)
		 {
		       Listitem li = new Listitem();
		        Listcell listcell=new Listcell(list.get(i).value);
		 	listcell.value=list.get(i).value;	
		 	listcell.setParent(li);	
		 	self.appendChild(li);
		 	 // str=str.append(list.get(i).value);
		  	 // str.append("\n");
		 	 // new Label(list.get(i).value).setParent(self);			
		}
	            //  new Label(str.toString()).setParent(self);
	           // new Label(str.toString()).setParent(self);
	            //  String s=str.append("\n").append("test").toString();
	            //  self.value=s;
	}
}
	
	
	
	//Code to add listbox on click of button
	
	
	
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
		else if((dragged.getParent().id).equals("grid10"))
		{	
		  List list=dragged.getChildren();
		  List listChilds=self.getChildren();
		   List childs=createChildListItems(self);
		  if(listChilds.size()>1)
		 {
		  	
		        removeChildListItems(self);
		       
		 }
		 for(int i=0;i<dragged.getChildren().size();i++)
		 {
		 				       	Listitem li = new Listitem();
		 				       	// li.setId("grid10"+i);
		 				        Listcell listcell=new Listcell(list.get(i).value);
		 				 	listcell.value=list.get(i).value;	
		 				 	listcell.setParent(li);
		 					self.appendChild(li); 		 				 	
		}
		if(childs.size()>0)
		{
		
		for(int j=0;j<childs.size();j++)
		 {
		 
		  Listitem li=childs.get(j);
		  self.appendChild(li); 
		 }
		}
		
		}
		else if((dragged.getParent().id).equals("grid9"))
		{
		
		 
		 List list=dragged.getChildren();
				 for(int i=0;i<dragged.getChildren().size();i++)
		{
				   Listitem li = new Listitem();
				    //li.setId("grid9"+i);
				    Listcell listcell=new Listcell(list.get(i).value);
				 	listcell.value=list.get(i).value;	
				 	listcell.setParent(li);	
					self.appendChild(li);
		}
		}
		else if((dragged.getParent().id).equals("grid8"))
		{
		
		 List list=dragged.getChildren();
		 for(int i=0;i<dragged.getChildren().size();i++)
		 {
		
				     	Listitem li = new Listitem();
				     		// li.setId("grid8"+i);
				        Listcell listcell=new Listcell(list.get(i).value);
				 	listcell.value=list.get(i).value;	
				 	listcell.setParent(li);
				        self.appendChild(li);
					
				 	
		}
		}
		
		
		dragged.getParent().removeChild(dragged);

}



public class itemRendererArray implements ListitemRenderer
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

List list=new ArrayList();
public void removeChildListItems(Object obj)
{
List childs=obj.getChildren();
for(int i=1;i<3;i++)
{
	obj.removeChild(childs.get(1));
	
}
}

public List createChildListItems(Object obj)
{
List childs=obj.getChildren();

for(int i=1;i<childs.size();i++)
{

Listitem li=childs.get(i);
list.add(li);
}
return list;
}



public class ExpandingMenuItemRows extends Window {
 /*
  * This is just an arbitrary custom method that we can call from a zscript.
  * In it we programmatically create all of the elements that appear in the other 
  * rows. The code below simply creates: 
  *  <row><label value=""/><hbox>$<textbox value=""/></hbox></row>
  * and sets the two value attributes whatever was passed into the method. 
  */
 
 int count=1;
 int topmargin=55;
 int leftmargin=10;
 
	public addLists(String i)
	{ 
		int j=Integer.valueOf(i).intValue();
		List l= new ArrayList();
		String top="";
		String left="";
		String id="";
		int p=4;
		for(int a=0;a<j;a++)
		{
		id="list"+count;
		Listheader header = new Listheader();
		header.setLabel("Suite"+count);
		header.setId(id);
		Listhead head = new Listhead();
		head.setId(id);
		top="top:"+topmargin+"px";
		left="left:"+leftmargin+"px";
		Listbox list = new Listbox();
		list.setFixedLayout(true);
		list.setHeight("100px");
		list.setWidth("100px");
		list.setId(id);
		list.setDroppable("true");
		list.setStyle(top +";align:None;background-color:#FFFFFF;margin-top:3px;font:Microsoft Sans Serif;font-style:normal;padding-right:0px;padding-left:0px;padding-top:0px;color:#000000;font-size:8.25pt;margin-bottom:3px;position:absolute;margin-right:3px;margin-left:3px;padding-bottom:0px;visibility:true;"+left);
		leftmargin=leftmargin+125;
		count++;
		if((p-1)==a)
		{
		topmargin=topmargin+120;
		leftmargin=10;
		p=p+4;
		}
		header.setParent(head);
		head.setParent(list);
		list.addEventListener("onDrop", new EventListener(){
		  public void onEvent(Event event)
		  throws Exception {
		  move(event.dragged);
		  	}
		});
		this.appendChild(list); 
		}
	}
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
public void move(Component dragged)
{
	if((dragged.getParent().id).equals("grid10"))
	{	
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
			if(i<dragged.getChildren().size()-1)
			{
				Listitem li = new Listitem();
				Listcell listcell=new Listcell(list.get(i).value);
				listcell.value=list.get(i).value;	
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
		roomTable.put(self.id+"-1",data);
	}
	else if((dragged.getParent().id).equals("grid9"))
	{		 
		List list=dragged.getChildren();
		LinkedList data = new LinkedList();
		for(int i=0;i<dragged.getChildren().size();i++)
		{
			data.add(list.get(i).value);
			if(i<(dragged.getChildren().size()-1))
			{
				Listitem li = new Listitem();
				Listcell listcell=new Listcell(list.get(i).value);
				listcell.value=list.get(i).value;	
				listcell.setParent(li);	
				self.appendChild(li);
			}			 	
		}
		roomTable.put(self.id+"_2",data);
	}
	else if((dragged.getParent().id).equals("grid8"))
	{	
		List list=dragged.getChildren();
		LinkedList data = new LinkedList();
		for(int i=0;i<dragged.getChildren().size();i++)
		{
			data.add(list.get(i).value);
			if(i<(dragged.getChildren().size()-1))
			{
				Listitem li = new Listitem();
				Listcell listcell=new Listcell(list.get(i).value);
				listcell.value=list.get(i).value;	
				listcell.setParent(li);
				self.appendChild(li);
			}		 	
		}
		roomTable.put(self.id+"_3",data);
	}	
	dragged.getParent().removeChild(dragged);
}



public class itemRendererArray implements ListitemRenderer
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





public class ExpandingMenuItemRows extends Window {
 /*
  * This is just an arbitrary custom method that we can call from a zscript.
  * In it we programmatically create all of the elements that appear in the other 
  * rows. The code below simply creates: 
  *  <row><label value=""/><hbox>$<textbox value=""/></hbox></row>
  * and sets the two value attributes whatever was passed into the method. 
  */
 
	int count=1;
	int topmargin=55;
	int leftmargin=10;
	public addLists(String i)
	{
		int j=Integer.valueOf(i).intValue();
		List l= new ArrayList();
		String top="";
		String left="";
		String id="";
		int p=4;
		for(int a=0;a<j;a++)
		{
		id="Suite"+count;
		Listheader header = new Listheader();
		header.setLabel("Suite"+count);
		header.setId(id);
		Listhead head = new Listhead();
		head.setId(id);
		top="top:"+topmargin+"px";
		left="left:"+leftmargin+"px";
		Listbox list = new Listbox();
		list.setFixedLayout(true);
		list.setHeight("100px");
		list.setWidth("100px");
		list.setId(id);
		list.setDroppable("true");
		list.setStyle(top +";align:None;background-color:#FFFFFF;margin-top:3px;font:Microsoft Sans Serif;font-style:normal;padding-right:0px;padding-left:0px;padding-top:0px;color:#000000;font-size:8.25pt;margin-bottom:3px;position:absolute;margin-right:3px;margin-left:3px;padding-bottom:0px;visibility:true;"+left);
		leftmargin=leftmargin+125;
		count++;
			if((p-1)==a)
			{
				topmargin=topmargin+120;
				leftmargin=10;
				p=p+4;
			}
		header.setParent(head);
		head.setParent(list);
		list.addEventListener("onDrop", new EventListener()
		  	{
			public void onEvent(Event event)
			throws Exception {
			move(event.dragged);
			}
			}
		);
		this.appendChild(list);
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	