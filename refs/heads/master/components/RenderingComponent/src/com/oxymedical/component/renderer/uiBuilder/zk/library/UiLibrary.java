package com.oxymedical.component.renderer.uiBuilder.zk.library;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.zul.ext.Paginal;

import com.oxymedical.component.renderer.RendererComponent;
import com.oxymedical.component.renderer.command.UiLibraryCompositeCommand;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.dateutil.DateUtil;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.router.IDataUnitRouter;
import com.oxymedical.core.userdata.IRights;
import com.oxymedical.core.userdata.IUserPattern;

import com.oxymedical.component.renderer.uiBuilder.zk.library.UiLibraryUtil;

@SuppressWarnings("unused")
public class UiLibrary
{
	private String dataPatternId = "";
	private String formPatternId = "";
	boolean validationValue = true;
	public Hashtable formValues = new Hashtable();
	private String comboSelectedValue;
	boolean validListRequest = false;
	private String pagingId;
	public IHICData returnHicData = null;
	private boolean loginStatus = false;
	String webMessage = null;
	String updatedMsg = "Patient Information has not been updated.";
	List textId = new ArrayList();
	List viewElements;
	UiLibraryUtil uiLibraryUtil = new UiLibraryUtil();
	private String displayLabelPaging=null;
	private int totalSize;
	private Component self;
	private Session session;
	
	private Hashtable comboTable = new Hashtable();
	
	public String invokeComponent(String componentId, String method, String classname, String paramList){
		String isValidStatus = "true";
		IDataUnitRouter router = (IDataUnitRouter)RendererComponent.dataUnitRouter;
		Component rootFormValue = self.getRoot();

		//Before completing any request it will check that session is valid or not.
		checkSessionTime(method);

		//Following code is used when session would be time out
		String logOutValue = getSessionData("LogOut");
		if (logOutValue != null){
			if (logOutValue == "true"){
				return "";
			}
			return "";
		}

		if (method.equalsIgnoreCase("save") || method.equalsIgnoreCase("addUserFromApplication")){
			if (clientSideValidation(formPatternId)){
				validationValue = true;
			}
			else{
				validationValue = false;
				isValidStatus = "false";
			}
		}
		if (validationValue){
			UiLibraryCompositeCommand command = new UiLibraryCompositeCommand();
			command.setMethodName(method);
			command.setRouter(router);
			command.setClassname(classname);
			command.setComponentId(componentId);
			command.setDataPatternId(dataPatternId);
			command.setFormPatternId(formPatternId);
			command.setFormValues(formValues);
			command.setRootFormValue((Window)rootFormValue);
			command.setParamList(paramList);
			command.setSession(session);
			command.setComboSelectedValue(comboSelectedValue);
			command.setValidListRequest(validListRequest);
			command.setPagingId(pagingId);
			command.execute();
			returnHicData = command.getHICData();
			if (returnHicData == null){
				isValidStatus = "false";
				return isValidStatus;
			}
			if (returnHicData.getData() != null){
				if ((returnHicData.getData().getStatus() != null)
						&& ((returnHicData.getData().getStatus().equalsIgnoreCase("error")) || (returnHicData
								.getData().getStatus().equalsIgnoreCase("false")))){
					isValidStatus = "false";
					return isValidStatus;
				}
			}
		}
		else{
			return "false";
		}

		if (method.equalsIgnoreCase("authenticateUserEx")){
			if (returnHicData.getData() != null && returnHicData.getData().getStatus() != null)
			{
				if (returnHicData.getData().getStatus().equalsIgnoreCase("invalid"))
				{
					formValues = new Hashtable();
					return "false";
				}
				else
				{
					loginStatus = true;
				}
			}
		}
		
		/*if(method.equalsIgnoreCase("userDetails") && (paramList.equalsIgnoreCase("ArtworkDraftApproval")))
		{
			if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null) 
			{
				IData data=returnHicData.getData();
				String department=data.getGroupName();
				return department;
			}
		}*/
		
		
		if(method.equalsIgnoreCase("executeList"))
		{
			if(returnHicData.getData()!= null && returnHicData.getData().getStatus() != null) 
			{
				IData data = returnHicData.getData();
				IFormPattern formpattern = data.getFormPattern();
				String formPatternId = formpattern.getFormId();
				Hashtable formvalues = new Hashtable();
				formvalues = data.getFormPattern().getFormValues();
				/*if(formPatternId.equals("changeRequest"))
				{
					formvalues.put("label","");
					formvalues.put("showbox","");
					formvalues.put("packageinsert","");
					formvalues.put("packageoutset","");
					formvalues.put("psbox","");
					formvalues.put("foil","");
					formvalues.put("psfoil","");	
					QueryData querydata = data.getQueryData();
					List linkofFiles = (List) querydata.getListData();
					String[][] iteraterlinkrecords=querydata.iterateListData(linkofFiles);
					rootFormValue = self.getRoot();
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
				}*/
				/*if(formPatternId.equals("ArtworkDraftUpload"))
				{
					Object forexecution = formValues.get("forexecution");
					if(forexecution!=null)
					{
						if(forexecution.toString().equalsIgnoreCase("true"))
						{
							QueryData querydata=data.getQueryData();
							List artworkCodeList=(List)querydata.getListData();
							String[][] iteraterartworkcode=querydata.iterateListData(artworkCodeList);
							formValues.put("size",iteraterartworkcode.length);
							formValues.put("artworkCode",iteraterartworkcode[iteraterartworkcode.length-1][0]);
							formValues.put("productName",iteraterartworkcode[iteraterartworkcode.length-1][1]);
						}
					}
					Object getLinkNotFromSession=formValues.get("getLinkNotFromSession");
					if(getLinkNotFromSession!=null)
					{
						if(getLinkNotFromSession.toString().equalsIgnoreCase("true"))
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
				}*/
				/*if(formPatternId.equals("ArtworkDraftUploadAET"))
				{
					Object aetworkpdfcs4 = formValues.get("aetworkpdfcs4");		
					boolean aetworkpdfcs4result = false;
					if(aetworkpdfcs4 != null){
						aetworkpdfcs4result = equalsTest(aetworkpdfcs4.toString(), "aetworkpdfcs4");
					}
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
				}*/
				
			}
		}
		
		
		/*if(method.equalsIgnoreCase("save") && paramList.equalsIgnoreCase("Artworkrequisition"))
		{
			if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null ) 
		    {
		             String message= returnHicData.getUniqueID();
		             return message;
		             
		    }
		}*/
		
		/*if(method.equalsIgnoreCase("validatingRefNo") && paramList.equalsIgnoreCase("Artworkrequisition"))
		{
			if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null ) 
		    {
		        IData data=returnHicData.getData();
				IFormPattern formpattern=data.getFormPattern();
				String formPatternId=formpattern.getFormId();
				Hashtable formvalues=new Hashtable();
				formvalues=data.getFormPattern().getFormValues();
				
		    }
		}*/
		
		if (method.equalsIgnoreCase("sendandrecieve"))
		{
			if (returnHicData.getData() != null && returnHicData.getData().getStatus() != null)
			{
				String message = returnHicData.getData().getStatus();
				
				String msg = "The return message is:" + message;
				message(msg);
				return message;
			}
		}
		if (method.equalsIgnoreCase("invokeClientCall"))
		{
			webMessage = (String) returnHicData.getMetaData().getCommonObject();
			return webMessage;
		}
		sessionUpdate(returnHicData);
		return isValidStatus;
	}
		
	public boolean checkSessionTime(String method)
	{
		boolean isSession = true;
		if(method.equalsIgnoreCase("logoutUser"))
		{
			return isSession;
		}
		String oldTimeStr = getSessionData("OLDTime");
		String userId = getSessionData("userId");
		
		if (userId == null)
		{
			return isSession;
		}
		if(oldTimeStr!=null)
		{
			Date oldDate = DateUtil.stringToDate(oldTimeStr, "yyyy-MM-dd HH:mm:ss");
			Date newDate = new Date();
			Long oldTime = oldDate.getTime();
			Long currentTime = newDate.getTime();
			Long timeDiff = currentTime - oldTime;
			long totalMinutes = timeDiff / (60 * 1000);
			String time = PropertyUtil.setUpProperties("SESSION_TIME");
			long sessionTime = session.getMaxInactiveInterval();
			
			if (time != null)
			{
				sessionTime = Integer.parseInt(time);
			}
			
			if (totalMinutes > sessionTime)
			{
				isSession = false;
				message("Sorry your session has timed out. Please sign in again.");
				logOut("login");
				updateSession("LogOut", "true");
				session.invalidate();
				return false;
			}
		}
		Date newDate = new Date();	
		String toDate = DateUtil.formatDate(newDate, "yyyy-MM-dd HH:mm:ss");
		updateSession("OLDTime",toDate);
		return isSession;

	}
	
	public String fileLocationPath() throws IOException
	{
		String none = "";
		org.zkoss.util.media.Media media;
		try{
			//media = org.zkoss.zhtml.Fileupload.get();
			media = org.zkoss.zul.Fileupload.get();
			if (media != null) 
			{
				File f = new File(media.getName());
				InputStream inputStream= media.getStreamData();
				OutputStream out;
				try
				{
					out = new FileOutputStream(f);
					byte[] buf = new byte[1024];
					int len;
					while((len = inputStream.read(buf)) > 0)
					{
						out.write(buf,0,len);
					}
					out.close();
					out.flush();
					inputStream.close();
					String filepath = f.getAbsolutePath();
					f.deleteOnExit();
					return filepath;
				} catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("media null");
			}
		} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return none;
	}
	
	public String getSessionData(String rowId)
	{
		String retVal = null;
		Object attrValue = session.getAttribute(rowId);
		if(attrValue != null){
			retVal = attrValue.toString();
		}
		// session.setAttribute(rowId,null);
		return retVal;
	}
	
	public boolean clientSideValidation(String formId){
		boolean isValid = true;
		Component formObj = self.getFellowIfAny(formId, true);
		
		if(formObj != null){
			Window win = (Window)formObj;
			UiLibraryUtil uiLibraryUtil = new UiLibraryUtil();
			String msg = uiLibraryUtil.clientSideValidation(win, formValues);
			
			if (msg != null){
				msg = msg.trim();
			}
	
			if (msg != "" && msg.length() != 0){
				message(msg);
				isValid = false;
			}
		}
	 	return isValid;
	}

	public void message(String value){
		Messagebox.setTemplate("/templates/OMMessagebox.zul"); 
	 	try{
			Messagebox.show(value, "Message", Messagebox.OK, Messagebox.INFORMATION);
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	public void sessionUpdate(IHICData returnData)
	{
		String userkeyId = returnData.getData().getUserId();
		String[] keyArray = null;
		if(userkeyId!=null)
		{
			keyArray = userkeyId.trim().split("-");
		}
		if(session.getAttribute("userId")== null || (session.getAttribute("userId")!=returnData.getData().getUserId()))
		{
			//session.setMaxInactiveInterval(600);
			//Devices.setTimeoutURI("ajax", "/zul/Logout.zul");

			if (keyArray != null)
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
	
	public String logOut(String pageId)
	{
		String userId = getSessionData("userId");
		String retValue = invokeComponent("com.oxymedical.component.useradmin", "logoutUser",
				"com.oxymedical.component.useradmin.UserAdminComponent", userId);
		updateSession("OLDTime", null);
		updateSession("login", null);
		setDataStatus("DEFAULT", pageId, null);
		updateSession("userId", null);
		session.invalidate();
		return retValue;

	}
	
	public void updateSession(String id, Object value){
		session.setAttribute(id, value);
	}
	
	public void updateSessionList(String id, Object value)
	{
		updateSession(id, value);
	}

	public void setDataStatus(String status, String formPattern, String dataPattern)
	{
		invokeComponent(null, "changeDOStatus", null, status + "_SEP_" + formPattern + "_SEP_" + dataPattern);
	}

	public void setDatapatternId(String dataId)
	{
		dataPatternId = dataId;
	}
	
	public void setFormPatternId(String formId)
	{
		formPatternId = formId;
		validationValue = true;
	}

	public String DownloadFile()
	{
		JFileChooser filesave = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("pdf file", "pdf");
		filesave.addChoosableFileFilter(filter);
		int ret = filesave.showSaveDialog(null);
		FileOutputStream fos = null;
		if (ret == JFileChooser.APPROVE_OPTION) {
			if (filesave.getSelectedFile().getName().contains(".pdf")) {
				try {
					
			
					fos = new FileOutputStream(filesave.getSelectedFile()
							.getPath());
				     
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					fos = new FileOutputStream(filesave.getSelectedFile().getPath());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return null;
		
	}
	
	public void createPopUp(String formId)
	{
		Window popUp = (Window) Executions.createComponents(formId+".zul", null, null);
		try{
			popUp.doModal();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	//public void setTextboxId(String id, String value)
	//{
		//checkFormValue(id, value);
		//formValues.put(id, value);
		//textId.add(id);
	//}

	/*public void checkFormValue(String id, String value)
	{
		String oldValue = (String)formValues.get(id);
		if(oldValue != null)
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
	}*/

	public void makeVisible(String id)
	{ 
		viewElements = new ArrayList();
		List rights = (List)session.getAttribute("rights");
		Object rootFormValue = self.getRoot(); 
		if(rights!=null)
		{
			uiLibraryUtil.displayControlInForm((HtmlBasedComponent)rootFormValue,rights,viewElements);
		}
	}
	
	public boolean checkValueWithOperator(Object val1, Object val2, String checkType)
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
	
	public boolean dateValidation(String dobValue)
	{
		String dateFormat = "yyyy-mm-dd";
		boolean yes =DateUtil.validateDate(dobValue,dateFormat);
		return yes;
	}

	public void enableSorting(String listHeaderId, String fieldName)
	{
		//Component rootFormValue = self.getRoot();
		Listheader listheader = (Listheader)self.getFellowIfAny(listHeaderId, true);
		listheader.setSortAscending(new RowLabelComparator(fieldName, true));
		listheader.setSortDescending(new RowLabelComparator(fieldName, false));
	}

	public String getRowData(int index)
	{
		if (self instanceof Listbox)
			return getRowData((Listbox)self,index);
		return null;
	}
	
	//added this method to get selected row based on the index and grid
	public String getRowData(Listbox gridObject,int index)
	{
	    List cell = null;
		String output = "";
		Set selectedItems = gridObject.getSelectedItems();
		if(selectedItems != null && selectedItems.size()>0)
		{
			for(Iterator itr = selectedItems.iterator();itr.hasNext();)
			{
				Listitem listNode = (Listitem) itr.next();
				cell = listNode.getChildren();
			}
			for(Iterator itr = cell.iterator(); itr.hasNext();)
			{
				Listcell lc = (Listcell)itr.next();
				if(lc.getColumnIndex() == index)
				{
					output = (String)lc.getValue();
				}
			}
			return output;
		}
		return null;
	}
	
	public void paginalSetter(String gridName)
	{
		System.out.println("[UiLibrary][paginalSetter][self]" + self.getId());
		//Component rootFormValue = self.getRoot();
		Listbox gridObj = (Listbox)self.getFellowIfAny(gridName, true);
		Paginal paging = (Paginal)self.getFellowIfAny(getPagingId(), true);
		try
		{
			gridObj.setPaginal(paging);
		}
		catch (Exception e)
		{
			System.out.println("!!ERROR!! Either '" + gridName + "' is not a Listbox / Grid or '" + getPagingId() + "' is not a Paging component");
		}
	}

	public boolean displayDataInControl(String controlObj){
		boolean isDisplay = false;
		Component elementObj = self.getFellowIfAny(controlObj, true);
		if(elementObj instanceof Listbox ){
			isDisplay = showListData(null, (Listbox)elementObj);	
		}
		return isDisplay;
	}

	public boolean showListData(IHICData hicData, Listbox listBox)
	{
		Object listValues = session.getAttribute("dbListValue");
		int noOfheader = 0;
		if(listBox != null)
			noOfheader = getNoOfHeader(listBox);
		if(listValues == null){
			if(hicData == null){
				if(returnHicData ==null){
					return false;
				}
				else{
					hicData = returnHicData;
				}
			}
			IData dataUnit = hicData.getData();
			List listValue = null;
			if(dataUnit.getQueryData().getListData()!= null){
				listValue = dataUnit.getQueryData().getListData();
			}
			else
				return false;
			String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
			ListModel myModel = null;
			if(allValues == null){
				myModel = new ListModelList();
				listBox.setModel(myModel);
				listBox.setItemRenderer(new ItemRendererArray());
				return false;
			}
			else{
				myModel = new ListModelList(allValues);
				listBox.setModel(myModel);
				listBox.setItemRenderer(new ItemRendererArray());
			}
		}
		else{
			String[][] allValues = QueryData.iterateListData((List)listValues);
			if(allValues != null){
				allValues = getGridLengthValue(allValues,noOfheader);
				ListModel myModel = new ListModelList(allValues);
				listBox.setModel(myModel);
				listBox.setItemRenderer(new ItemRendererArray());
			}
			else
				return false;
		}
		return true;
	}

	public int getNoOfHeader(Listbox gridObj)
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
						List listHead = ((Listhead)obj).getChildren();
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
	
	public void setDisplayLabelId(String label)
	{
		displayLabelPaging = label;
	}
	
	public String getDisplayLabelId()
	{
		return displayLabelPaging;
	}

	public void setTotalSize()
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
	
	public void setPagingId(String paging)
	{
		pagingId = paging;
	}
	
	public String getPagingId()
	{
		return pagingId;
	}

	public void createPagingEvent(String id)
	{
		validListRequest = true;
		String[] listInfo = PagingInfo.getListInfo();
		final String componentId = listInfo[0];
		final String method = listInfo[1];
		final String classname = listInfo[2];
		final String condition = listInfo[3];
		final String listName = listInfo[4];
		//Component rootFormValue = self.getRoot();
		Paging pag = (Paging) self.getFellowIfAny(pagingId, true);
		String labelId = getDisplayLabelId();
		Label label1 = null;
		if (labelId != null)
		{
			label1 = (Label) self.getFellowIfAny(labelId, true);
		}
		final Label label = label1;
		final Listbox list = (Listbox) self.getFellowIfAny(listName, true);
		invokeComponent(componentId, method, classname, condition);
		setTotalSize();
		pag.setTotalSize(getTotalSize());
		validListRequest = false;
		final int PAGE_SIZE = pag.getPageSize();
		if (list.getChildren().size() > PAGE_SIZE)
		{
			String value = "[ " + 1 + " - " + PAGE_SIZE + " / " + getTotalSize() + " ]";
			if (label != null)
				label.setValue(value);
		}
		else
		{
			int total = list.getChildren().size() - 1;
			if (total > 0)
			{
				String value = "[ " + 1 + " - " + total + " / " + getTotalSize() + " ]";
				if (label != null)
					label.setValue(value);
			}
			else
			{
				if (label != null)
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
				String conditonS = condition + " limit " + ofs + "," + PAGE_SIZE;
				invokeComponent(componentId, method, classname, conditonS);
				displayDataInControl(listName);
				int size = list.getChildren().size();
				int total = ofs + size - 1;
				if (label != null)
				{
					String value = "[ " + (ofs + 1) + " - " + total + " / " + getTotalSize() + " ]";
					label.setValue(value);
				}
			}
		});
	}

	public void createSortingEvent(String listId, String pagingId)
	{
		//Component rootFormValue = self.getRoot();
		Listbox _listBox = (Listbox)self.getFellowIfAny(listId, true);
		setListSortListeners(_listBox);
	}

	//Sets the listeners.. "onSort" for all listheaders that have a sortDirection declared.
	private void setListSortListeners(Listbox listBox) 
	{
		//Add 'onSort' listeners to the used listheader components. All not
		//used Listheaders must me declared as:
		//listheader.setSortAscending("");
		//listheader.setSortDescending("");
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
			//Component rootFormValue = self.getRoot();
		    Paging paging = (Paging)self.getFellowIfAny(pagingId, true);		
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

	public boolean messageWithQuestion(String message)
	{
		try
		{
			if (!(Messagebox.show(message,
					"Confirm", Messagebox.YES | Messagebox.NO,
			"Messagebox.QUESTION") == Messagebox.YES)) {
				return false;				
			}
			else 
				return true;
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	public String getParentFormPattern(String formId)
	{
		 //Component rootFormValue = self.getRoot();
		 Component controlObj = (Component)self.getFellowIfAny(formId,true);
		 String parentId = (String)controlObj.getAttribute("formpattern");
		 return parentId;
	}

	public void setSelf(Component self)
	{
		this.self = self;
	}
	
	public void setSession(Session session)
	{
		this.session = session;
	}

	public void setCurrentDate(Object controlId)
	{
		Date currentDate = new Date();
		String dateString = null;
		if(currentDate != null){
			dateString = (new SimpleDateFormat("yyyy-MM-dd").format(currentDate));
		}
		String strdate = dateString + " " + "00:00";
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm");             
		Date date = null;
		if(controlId instanceof Datebox)
		{
			try {
				date = simpleDate.parse(strdate);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			((Datebox)controlId).setValue(date);
		}
		else if(controlId instanceof Textbox)
		{
			((Combobox)controlId).setText(dateString);
		}
		else if(controlId instanceof Combobox)
		{
			((Combobox)controlId).setText(strdate);
		}
		
	}	

	public void addComponent(int num, Class clazz,Object grid,Class label) throws InstantiationException, IllegalAccessException 
	{
		for(int j=0;j<num;j++) 
		{
			Row row=new Row();
			row.appendChild(new Label(""+ (((Grid)grid).getRows().getChildren().size() + 1)));
			row.appendChild((Component) clazz.newInstance());
			
			Columns columns = row.getGrid().getColumns();
			if (columns.getChildren() == null) return;
			
			int colCount = columns.getChildren().size();
			
			Map params = new HashMap();
			params.put("Home", createStringList(colCount, String.valueOf(grid)));
			row.appendChild(Executions.createComponents("Home.zul", row, params));
		}
	}
	
	public void addComponentRow(int num,Class clazz,Object grid,Class label)
	{
		for(int i=0;i<num;i++)
		{
			Row row=new Row();
			Button btn=new Button();
			
			//btn.setAttribute("onClick", value)
			row.appendChild(new Label(""+(((Grid)grid).getRows().getChildren().size())));
			for(int j=0; j<3;j++)
			{
				if(j==0)
				{
					row.appendChild(new Button());
				}
				row.appendChild(new Textbox());
			}
			((Grid)grid).getRows().appendChild(row);
		}
	}
	
	public void addColumn(Grid controlId)
	{
		//	alert("hello");
			if(controlId instanceof Grid)
			{
			//	alert("inside if");
				Column col = new Column("Edit");
				
				//col.setZindex("1");
				col.setParent(controlId.getColumns());
				//controlId.setRowRenderer(new EditRowBtnRenderer()); 
			}
	}
	
	private List createStringList(int num, String startWith){
		List result = new ArrayList();
		
		for (int i = 0; i < num; i++) {
			result.add(startWith + (i + 1));
		}
		return result;
	}

	public void setFocus(String controlID){
		Component control = self.getFellowIfAny(controlID, true);
		if(control instanceof HtmlBasedComponent){
			((HtmlBasedComponent)control).focus();
		}
	} 

	public String getComboItemId(String comboBoxId)
	{
		String comboSelectedVal = null;
		//Component rootFormValue = self.getRoot();
		Component comboObj = self.getFellowIfAny(comboBoxId, true);
		if(comboObj != null){
			Comboitem selectedComboRow = ((Combobox)comboObj).getSelectedItem(); 
			
			if(selectedComboRow != null){
				if(selectedComboRow.getId().indexOf("_") != -1){
					int idSeperatorPos = selectedComboRow.getId().indexOf("_");
					comboSelectedVal = (String)selectedComboRow.getId().substring(0, idSeperatorPos);
				}else{
					comboSelectedVal = (String)selectedComboRow.getId();
				}
				comboSelectedValue = comboSelectedVal;
				//checkFormValue(self.getId(), comboSelectedValue);
				formValues.put(self.getId(), comboSelectedValue);
				comboSelectedValue = null;
			}
		}
		return comboSelectedVal;
	}

	public void showComboData(String comboid){
	    //Object rootFormValue = self.getRoot();
		Component comboObj = self.getFellowIfAny(comboid, true);
		if(comboObj != null){
			showComboData(null, (Combobox)comboObj);	
		}
	}
	
	public void showComboData(IHICData hicData, Combobox comboObj){
		if(hicData == null){
			if(returnHicData == null){
				return;
			}
			else{
				hicData = returnHicData;
			}
		}
		comboObj.setText("");
		comboObj.getItems().clear();
		IData dataUnit = hicData.getData();
		List listValue = null;
		Hashtable formValues = new Hashtable();
		formValues = dataUnit.getFormPattern().getFormValues();
		String emailCondition = (String)formValues.get("emailCondition");
		if(dataUnit.getQueryData().getListData() != null){
			listValue = dataUnit.getQueryData().getListData();
		}
		else
			return;
		boolean emailConditionResult = equalsTest(emailCondition, "emailCondition");
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		if(allValues == null)
			return;	
		if(emailConditionResult){
			String[][] userIds = new String[allValues.length][1];
			String[][] mailIds = new String[allValues.length][1];
			for(int i = 0; i < allValues.length; i++)
			{
				userIds[i][0] = allValues[i][0];
				mailIds[i][0] = allValues[i][1];
				formValues.put(userIds[i][0], mailIds[i][0]);
			}
			comboTable.put(comboObj.getId(), mailIds);
			uiLibraryUtil.showComboData(userIds, comboObj);
			formValues.put("emailCondition", "");
		}
		else{
			comboTable.put(comboObj.getId(), listValue);
			uiLibraryUtil.showComboData(allValues, comboObj);
		}
	}
	
	public boolean equalsTest(String firstStr, String secondStr){
		if(firstStr != null && secondStr != null){
		      firstStr=firstStr.trim();
		      secondStr=secondStr.trim();
			if (firstStr.equalsIgnoreCase(secondStr))
				return true;
		}
		return false;
	}

	//Method added by pra to check if userId is avaible on 09-june-2009
	public boolean isRecordExist(){
		boolean retVal = false;
		if(returnHicData == null){
			return false;
		}else{
			IData dataUnit = returnHicData.getData();
			List listValue = null;
			if(dataUnit.getQueryData().getListData() != null){
				listValue = dataUnit.getQueryData().getListData();
			}
			else
				return false;
			String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
			if(allValues != null && allValues.length > 0){
				retVal = true;
				return retVal;
			}
		}
		return retVal;
	}

	public boolean checkDOB(String datepickerId){
		boolean isDateValid = true;
		Component rootFormValue = self.getRoot();
		if(rootFormValue instanceof Window){
			isDateValid = uiLibraryUtil.checkDOB(datepickerId, (Window)rootFormValue, formValues);
		}
		return isDateValid;
	}
	
	// This method used to removed window popup
	// id would be popupwindow name.
	public void detachWindow(String windowId){
		Component winObj = self.getFellowIfAny(windowId, true);
		if(winObj != null){
			winObj.detach();
		}
	}
	
	public String getComboBoxValue(String comboBoxId){
		String retVal = null;
		Component comboObj = self.getFellowIfAny(comboBoxId, true);
		if(comboObj != null){
			Comboitem selectedComboRow = ((Combobox)comboObj).getSelectedItem();
			if(selectedComboRow != null){
				retVal = selectedComboRow.getLabel();
			}
		}
		return retVal;
	}
	
	
	//This method check login value is blank or not.
	//if blank show warning msg without sending request to dbcomponent
	public boolean checkLoginBlankValue(String txtOne, String txtTwo){
		Object valueOne = formValues.get(txtOne);
		Object valueTwo = formValues.get(txtTwo);
		boolean retVal = false;
		if(valueOne == null || valueTwo == null){
			formValues.remove(txtOne);
			formValues.remove(txtTwo);
			//return false;
		}else{
			retVal = true;
		}
		return retVal;

	}
	
	public String getDateDiff(String toDate, String fromDate){
		String dateDiff = "";
		String [] fromDateArr = fromDate.split("-");
		String [] toDateArr = toDate.split("-");
		long one_day = 1000*60*60*24;	
		String fromYear = fromDateArr[2];	
		int fromYearInt = Integer.parseInt(fromYear)*1;	
		String fromMonth = (String) fromDateArr[1];
		int fromMonthInt = Integer.parseInt(fromMonth)*1;
		String newfromDate = (String) fromDateArr[0];
		int newfromDateInt = Integer.parseInt(newfromDate)*1;	
		String toYear = (String) toDateArr[2];
		int toYearInt = Integer.parseInt(toYear)*1;	
		String toMonth = (String) toDateArr[1];
		int toMonthInt = Integer.parseInt(toMonth)*1;	
		String newtoDate = (String) toDateArr[0];
		int newtoDateInt = Integer.parseInt(newtoDate)*1;	
		Date newFromDate = new Date(fromYearInt, fromMonthInt, newfromDateInt);
		Date newToDate = new Date(toYearInt, toMonthInt, newtoDateInt);
		int days = (int)Math.ceil(((newToDate.getTime() - newFromDate.getTime()) / (one_day))) + 1;
		dateDiff = Integer.toString(days);
		return dateDiff;
	}
	
	public String getDateString(Object dateControl){
		String dateString = null;
		if(dateControl != null){
			Date currentDate = ((Datebox)dateControl).getValue();
			if(currentDate != null){
				dateString = (new SimpleDateFormat("dd-MM-yyyy").format(currentDate));
			}
		}
		return dateString;
	}
	
	public Date convertStringToDate(String dateStr){
		Date dt = null;
		if(dateStr != null){	
			String date = dateStr + " " + "00:00";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			try{
				dt = dateFormat.parse(date);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return dt;
	}
	
	public void checkFormValue(String id,String value)
	{
		Object oldValueObj = formValues.get(id);
		if(oldValueObj != null)
		{
			if(oldValueObj.toString().equalsIgnoreCase(value))
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
	
	public void setTextboxId(){
		Textbox _self = (Textbox)self;
		checkFormValue(_self.getId(), _self.getValue().toString());
		formValues.put(_self.getId(), _self.getValue());
		textId.add(_self.getId());
	}
	
	void setDateBoxId(){
	    String date = "";
	    Datebox _self = (Datebox)self;
	    if(_self.getValue() != null){
	    	date = (new SimpleDateFormat("yyyy-MM-dd").format(_self.getValue()));
	    	checkFormValue(_self.getId(), date);
		}
		formValues.put(_self.getId(),date);
	}
	
	public void setRadioSelected(Object radioID){
		Radiogroup _self = (Radiogroup)self;
		_self.setSelectedItem((Radio)radioID);
		checkFormValue(_self.getId(), _self.getSelectedItem().getLabel());
		formValues.put(_self.getId(), _self.getSelectedItem().getValue());
 	}
	
	public void setRadioGroupId(){
		Radiogroup _self = (Radiogroup)self;
		checkFormValue(_self.getId(), _self.getSelectedItem().getLabel());
		if(_self.getSelectedItem().getValue() != null && ! _self.getSelectedItem().getValue().equals("")){
			formValues.put(_self.getId(), _self.getSelectedItem().getValue());
		}
		else{
			formValues.put(_self.getId(), _self.getSelectedItem().getLabel());
		}
 	}

	public void setCheckboxId(){
		Checkbox _self = (Checkbox)self;
		checkFormValue(_self.getId(), _self.getLabel());
		String value = String.valueOf(_self.isChecked());
		formValues.put(_self.getId(), value);
	}
	
	public void setCheckboxValue(){
		Checkbox _self = (Checkbox)self;
		String value = String.valueOf(_self.isChecked());
		formValues.put(_self.getId(), value);
	}
}
