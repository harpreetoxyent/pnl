package com.oxymedical.component.renderer.uiBuilder.zk.library;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.zkoss.calendar.Calendars;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.Session;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;
import org.apache.commons.codec.binary.Base64;

import com.oxymedical.component.renderer.constants.UILibraryConstant;
import com.oxymedical.component.renderer.constants.ZKConstants;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.dateutil.DateUtil;
import com.oxymedical.core.querydata.HibernateQueryResultDataSource;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.userdata.IRights;

/**
 * @author vka
 * Hashtable listValue has been removed, and new code added because we use list for combobox.
 * changes in image segemnt,
 * errorbgColor declare as instance variable
 *
 */
public class UiLibraryUtil {
	private static int treeNodeCount = 0;
	String errorBGColor ="background:#fad8d8";
	String normalBGColor ="background:#FFFFFF";
	String title = "AMS";
	//String title = "NOLIS";

	static public Hashtable<String,String> loginInfo = new Hashtable<String,String>();

	public void showKeyValueData(String[] idArray, Hashtable formValueTable, Window rootFormValue, Hashtable comboTable)
	{

		for (int i=0; i<idArray.length; i++) 
		{
			String formKey = idArray[i];
			System.out.println("formKey = "+formKey);
			Object textObject = rootFormValue.getFellowIfAny(formKey, true);
			if (textObject instanceof Textbox)
			{
				System.out.println("formKey's value = "+formValueTable.get(formKey));
				((Textbox) textObject).setText((String)formValueTable.get(formKey));
			}
			else if (textObject instanceof Button)
			{
				System.out.println("formKey's value = "+formValueTable.get(formKey));
				((Button) textObject).setLabel((String)formValueTable.get(formKey));
				((Button) textObject).setHref((String)formValueTable.get(formKey));
			}
		}
		
//		for (Iterator<KeyValueObject> it = keyValuesList.iterator(); it.hasNext();) 
//		{
//			KeyValueObject keyValues = it.next();
//			Object textObject = rootFormValue.getFellowIfAny((String)keyValues.getKeyId(),true);
//			if (textObject instanceof Textbox)
//			{
//				((Textbox) textObject).setText((String)keyValues.getKeyValue());
//			}
//		}
	}
	
	public void showData(String[][] allValues,Hashtable formValues,Window rootFormValue,String[] txtField, Hashtable comboTable) throws IOException {

		String value=null;
		for(int count=0; count<txtField.length;count++)
		{
			String txtValue = allValues[0][count];
			Object textObject = rootFormValue.getFellowIfAny(txtField[count],true);
			if(textObject instanceof Combobox)
			{	
				if(comboTable!=null)
				{
					List listValue = (List)comboTable.get(((Combobox)textObject).getId());
					if(listValue!=null)
					{
						//Changes done in the showing the combobox value by pra on 28-May-2009
						if(txtValue!=null)
						{
							String[][] comboValue = new QueryData().iterateListData(listValue);
							for(int i=0;i<comboValue.length;i++)
							{   
								if(txtValue.equalsIgnoreCase(comboValue[i][0]))
								{
									if(comboValue[i].length>1)
										value = comboValue[i][1];
								}
							}
						}
						else
							value=" ";
						if(value !=null)
						{
							((Combobox)textObject).setText(value);
							value = null;
						}
						else if(txtValue!=null)
						{
							((Combobox)textObject).setText(txtValue);
							
						}
					}
					else if(txtValue!=null)
					{
						((Combobox)textObject).setText(txtValue);
					}
				}
			}
			else if(textObject instanceof Timebox)
			{//added conition to handle value for timebox done by pra 24-june-2009
				//handle condition for formvalues in case of edit and also to display data in timebox date is hard coded.done by pra on 26-june-2009
				if(null!=txtValue && txtValue!="")
				{ 
					Time timeFormate = Time.valueOf(txtValue);
					formValues.put(txtField[count], timeFormate);
					txtValue = "1970-01-01 " + txtValue;
					Date date = DateUtil.stringToDate(txtValue,
					"yyyy-MM-dd HH:mm");
					((Timebox) textObject).setValue(timeFormate);
					
					txtValue = null;
				}

			}
			else if(textObject instanceof Datebox)
			{
				if(null!=txtValue && txtValue!="")
				{ 
					String date=txtValue +" "+"00:00";
					SimpleDateFormat ds1=new SimpleDateFormat("yyyy-MM-dd hh:mm");             
					Date d1=null;
					try {
						d1 = ds1.parse(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					((Datebox)textObject).setValue(d1);
				
				}
			}
			else if (textObject instanceof Image) {
				byte[] outData = Base64.decodeBase64(txtValue.getBytes());
				if(outData !=null)
				{
					File imageFile = File.createTempFile("image", ".jpg");
					FileOutputStream out = new FileOutputStream(imageFile);
					try {
						out.write(outData);
					} finally {
						out.close();
					}
					AImage aimg = new AImage(imageFile);
					((Image) textObject).setContent(aimg);
					
					imageFile.deleteOnExit();
				}

			} else if (textObject instanceof Textbox) {

				((Textbox) textObject).setText(txtValue);
			}

			else if (textObject instanceof Label) {

				((Label) textObject).setValue(txtValue);
			}
			else if(textObject instanceof Checkbox)
			{
				boolean isChecked=Boolean.parseBoolean(txtValue);
				((Checkbox) textObject).setChecked(isChecked);
			}
			else
			{
				((InputElement) textObject).setText(txtValue);
			}
			if(txtValue!=null)
				formValues.put(txtField[count],txtValue);
		}

	}
	
	
	
	
	
	/**
	 * @param formId
	 * @param rootFormValue
	 * @param formValues
	 * @return
	 * 
	 * This method commented. some proble is exist in this method. 
	 * update version is writen in another param value with same method name.
	 * changes by wasim, 21-May-2009
	 */
	/*public boolean clientSideValidation(String formId, Window rootFormValue,
			Hashtable formValues) {
		boolean valid = true;
		XulElement formObj = (XulElement) rootFormValue.getFellowIfAny(formId,
				true);
		List childElement;
		if (formObj != null) {
			childElement = formObj.getChildren();
			Iterator iter = childElement.iterator();
			while (iter.hasNext()) {
				Object value = iter.next();
				if (value instanceof InputElement) {
					valid = clientSideValidation(
							((InputElement) value).getId(), rootFormValue,
							formValues);
					if (!valid)
						return valid;
					if ((((InputElement) value).getId().indexOf("combobox") >= 0)
							|| (((InputElement) value).getId().indexOf(
									"textbox") >= 0)
							|| (((InputElement) value).getId().indexOf(
									"datepicker") >= 0)) {
						System.out.println("------constraint value--"+((InputElement) value).getConstraint());
						System.out.println("-----formValues.get(((InputElement) value).getId())--"+formValues.get(((InputElement) value).getId()));

						if (((InputElement) value).getConstraint() != null) {
							String comValue = (String) formValues.get(((InputElement) value).getId());
							if ("" == comValue || null == comValue) {
								valid = false;
								((InputElement) value).focus();
								// alert(value.getName()+" can not be empty");
								try {
									Messagebox.show(((InputElement) value)
											.getName()
											+ " can not be empty");
								} catch (InterruptedException e) {

									e.printStackTrace();
								}
								return valid;
							}
						}
					} else if (((InputElement) value).getId().indexOf("time") >= 0) {
						if (((InputElement) value).getConstraint() != null) {
							Time comValue = (Time) formValues
									.get(((InputElement) value).getId());
							if (null == comValue) {
								valid = false;
								((InputElement) value).focus();
								// alert(value.name+" can not be empty");
								try {
									Messagebox.show(((InputElement) value)
											.getName()
											+ " can not be empty");
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								return valid;
							}
						}
					}
				} else {

					valid = clientSideValidation(((XulElement) value).getId(),
							rootFormValue, formValues);
					if (!valid)
						return valid;
				}
			}
		}
		return valid;
	}
	 */

	/**
	 * changes by Wasim, 21-May-2009
	 * Method name and return type changed 
	 *  changes by Wasim, 02-June-2009
	 *  ismandatory and value type both constraints would be application on single control now.
	 *  changes by Wasim Khan, 25-July-2009.
	 *  
	 * @param formObj
	 * @param formValues
	 * @return
	 */
	public String clientValidation(Component formObj,Hashtable formValues)

	{
		boolean valid = true;
		List childElement;
		String alertMsg ="";
		if (formObj != null)
		{
			childElement = formObj.getChildren();
			Iterator iter = childElement.iterator();

			while (iter.hasNext()) {

				Component value = (Component)iter.next();

				if (value instanceof InputElement) {


					alertMsg = alertMsg + clientValidation(value,formValues);

					if (alertMsg=="")
					{
						return alertMsg;
					}

					if ((((InputElement) value) instanceof Combobox)
							|| (((InputElement) value)instanceof Textbox)
							|| (((InputElement) value)instanceof Datebox))
					{

						String comValue = null;
						if (((InputElement) value).getConstraint() != null) {
							comValue = (String) formValues.get(((InputElement) value).getId());
							if ("" == comValue || null == comValue) {
								valid = false;
								//((InputElement) value).focus();
								//background-color:#FF0000;
								//((InputElement) value).setStyle(errorBGColor);
								String id = ((InputElement) value).getId();
								formValues.remove(id);

								if(((InputElement) value).getAttribute("valueType") == null || ("" == comValue || null == comValue))
								{
									String messageId=((InputElement) value).getName();
									if(messageId.lastIndexOf(":")>=0)
										messageId=messageId.replace(":","").trim();
									if(messageId.lastIndexOf("*")>=0)
										messageId=messageId.replace("*","").trim();
									alertMsg = alertMsg + messageId+ " field can not be empty. "+ZKConstants.NEW_LINE;
								}

							}
							else
							{
								//((InputElement) value).setStyle(normalBGColor);
							}
							if(((InputElement) value).getAttribute("valueType") != null)
							{
								String customStr = (String) ((InputElement) value).getAttribute("valueType");
								if(comValue !=null)
								{
									String msgStr = checkValidation((InputElement) value,customStr,comValue);
									String id = ((InputElement) value).getId();
									if(msgStr.trim() != "")
									{
										alertMsg = alertMsg + ((InputElement) value).getName();/*+ " can not be empty. ";*/
										alertMsg = alertMsg + msgStr;
										formValues.remove(id);
									}
								}
							}

						}
						else if(((InputElement) value).getAttribute("valueType") != null)
						{

							String customStr = (String) ((InputElement) value).getAttribute("valueType");
							comValue = (String) formValues.get(((InputElement) value).getId());
							if(comValue !=null)
							{
								String msgStr = checkValidation((InputElement) value,customStr,comValue);
								if(msgStr != "")
								{
									String id = ((InputElement) value).getId();
									formValues.remove(id);
									alertMsg = alertMsg + msgStr;
								}
								else
								{
									//((InputElement) value).setStyle(normalBGColor);
								}

							}


						}
					} else if (((InputElement) value)instanceof Timebox) {
						if (((InputElement) value).getConstraint() != null) {
							Time comValue = (Time) formValues
							.get(((InputElement) value).getId());
							if (null == comValue) {
								valid = false;
								//((InputElement) value).focus();
								// alert(value.name+" can not be empty");
								/*try {
									Messagebox.show(((InputElement) value)
											.getName()
											+ " can not be empty");
								} catch (InterruptedException e) {
									e.printStackTrace();
								}*/
								String id = ((InputElement) value).getId();
								formValues.remove(id);
								//((InputElement) value).setStyle(errorBGColor);
								String messageId=((InputElement) value).getName();
								if(messageId.lastIndexOf(":")>=0)
									messageId=messageId.replace(":","").trim();
								if(messageId.lastIndexOf("*")>=0)
									messageId=messageId.replace("*","").trim();
								alertMsg = alertMsg + messageId+ " field can not be empty. "+ZKConstants.NEW_LINE;

							}
							else
							{
								//((InputElement) value).setStyle(normalBGColor);
							}
						}
					}
				} else {

					alertMsg = alertMsg+clientValidation(value,formValues);
					if (alertMsg =="")
						return alertMsg;
				}
			}
		}
		return alertMsg;
	}

	/**
	 * This method called form uilibrary and it calls clientValidation method.
	 * changes by wasim 2-June-2009
	 * @param formObj
	 * @param formValues
	 * @return
	 * 
	 */
	public String clientSideValidation(Component formObj,Hashtable formValues)
	{
		//boolean isValid=true;
		String msg = this.clientValidation(formObj, formValues);
		/*if(msg != null)
		{
			msg = msg.trim();
		}
		try {
			if(msg !="" && msg.length() !=0)
			{
				msg = msg.substring(0, msg.length()-1);
				Messagebox.show(msg,title,Messagebox.OK, Messagebox.ERROR);
				isValid = false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 */

		return msg;
	}





	public void showJasperReport(String controlObject, String jasperFile,
			Window rootFormValue, IData dataUnit) {
		List listValue = dataUnit.getQueryData().getListData();
		Object displayObject = rootFormValue.getFellowIfAny(controlObject, true);
		Jasperreport jasperReport = (Jasperreport) displayObject;
		String reportQuery = dataUnit.getQueryData().getCondition();
		int getIndex = reportQuery.indexOf("get");
		int fromIndex = reportQuery.indexOf("from");
		String idValue = null;
		StringBuffer idField = new StringBuffer();
		int colKey = 0;

		String[] paramValues = reportQuery.substring(getIndex + 6, fromIndex)
		.trim().split(",");
		String[] fields = new String[paramValues.length];
		for (int i = 0; i < paramValues.length; i++) {
			String[] childValue = paramValues[i].trim().split("\\.");
			fields[i] = childValue[1];
		}
		Map parameters = new HashMap();
		parameters.put("Title", "Report");
		HibernateQueryResultDataSource ds = new HibernateQueryResultDataSource(
				listValue, fields);

		jasperReport.setSrc(jasperFile);
		jasperReport.setParameters(parameters);
		jasperReport.setDatasource(ds);
	}


	public void showExcelReport(String controlObject, String jasperFile,
			Window rootFormValue, List listValue ) {

		Object displayObject = rootFormValue.getFellowIfAny(controlObject, true);
		Jasperreport jasperReport = (Jasperreport) displayObject;
		String idValue = null;
		String[] fields = null;
		fields = UILibraryConstant.FIELDS.trim().split(",");
		StringBuffer idField = new StringBuffer();
		int colKey = 0;

		Map parameters = new HashMap();
		parameters.put("Title", "Report");
		HibernateQueryResultDataSource ds = new HibernateQueryResultDataSource(
				listValue, fields);
		jasperReport.setType("csv");
		jasperReport.setSrc(jasperFile);
		jasperReport.setParameters(parameters);
		jasperReport.setDatasource(ds);
	}



	public boolean selectCurrentOrFutureDate(String datepicker1,
			Window rootFormValue, Hashtable formValues) {
		boolean isDateValid = true;
		Datebox datepickerObj1 = (Datebox) rootFormValue.getFellowIfAny(
				datepicker1, true);
		// String date1=formValues.get(datepicker1);
		String date1 = (new SimpleDateFormat("yyyy-MM-dd")
		.format(datepickerObj1.getValue()));
		if (date1 == null || date1 == "") {
			// alert(datepickerObj1.name +"can not be empty");
			try {
				Messagebox.show(datepickerObj1.getName() + "field can not be empty.",title,Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return false;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedDate1 = null;
		String curDate = "";
		Date currentDate = null;
		try {
			convertedDate1 = dateFormat.parse(date1);
			Date current = new Date();
			curDate = dateFormat.format(current);
			currentDate = dateFormat.parse(curDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		if (convertedDate1.before(currentDate)) {
			// alert("Invalid Date Please select current/advance date");
			try {
				Messagebox
				.show("Invalid Date Please select current/advance date.",title,Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			formValues.remove(datepicker1);
			datepickerObj1.setValue(null);
			return false;
		}
		return isDateValid;
	}

	public boolean validateDate(String datepicker1, String datepicker2,
			Window rootFormValue, Hashtable formValues) {
		boolean isDateValid = false;
		Datebox datepickerObj1 = (Datebox) rootFormValue.getFellowIfAny(
				datepicker1, true);
		Datebox datepickerObj2 = (Datebox) rootFormValue.getFellowIfAny(
				datepicker2, true);
		String date1 = (String) formValues.get(datepicker1);
		String date2 = (String) formValues.get(datepicker2);
		if (date2 == null || date2 == "") {
			try {
				Messagebox.show("Please select " + datepickerObj2.getName()+".",title,Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			formValues.remove(datepicker1);
			datepickerObj1.setValue(null);
			datepickerObj2.focus();
			return false;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedDate1 = null;
		Date convertedDate2 = null;
		Date currentDate = null;
		try {
			convertedDate1 = dateFormat.parse(date1);
			convertedDate2 = dateFormat.parse(date2);
			Date current = new Date();
			String curDate = dateFormat.format(current);
			currentDate = dateFormat.parse(curDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		if (convertedDate2.before(currentDate)) {
			try {
				Messagebox
				.show("Invalid Date Please select current/advance date.",title,Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e) {			
				e.printStackTrace();
			}
			// alert("Invalid Date Please select current/advance date");
			formValues.remove(datepicker2);
			datepickerObj2.setValue(null);
			return false;
		}
		if (convertedDate1.equals(convertedDate2)) {
			isDateValid = true;
		} else if (convertedDate1.after(convertedDate2)) {
			isDateValid = true;
		} else {
			try {
				Messagebox.show("End Date should be greater than Start Date.",title,Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			formValues.remove(datepicker1);
			datepickerObj1.setValue(null);			
			isDateValid = false;
		}
		return isDateValid;
	}

	/*public void showComboData(IData dataUnit, Combobox comboObj,
			Hashtable comboTable) {
		if (null != dataUnit.getQueryData().getRowValues()) {
			System.out.println("dataUnit.getQueryData().getRowValues()"
					+ dataUnit.getQueryData().getRowValues());
		} else {
			System.out.println("null dataUnit.getQueryData()");
		}
		Hashtable<String,List<String>> rowValues = dataUnit.getQueryData().getRowValues();
		if (null != rowValues) {
			Object[] rowIds = rowValues.keySet().toArray();
			System.out.println("showComboData -rowIds size " + rowIds.length);
			Comboitem rowItem = null;
			if (rowIds.length > 0) {
				// comboTable = rowValues;
				comboTable.put(comboObj.getId(),	rowValues);
				for (int i = 0; i < rowIds.length; i++) {
					List<String> displayValues = rowValues.get(rowIds[i]);
					String displayLabel = null;
					if (null != displayValues) {
						for (Iterator itr = displayValues.iterator(); itr.hasNext();) {
							displayLabel = (String) itr.next();
							break;
						}
					}
					rowItem = comboObj.appendItem(displayLabel);
					rowItem.setId(rowIds[i] + "_" + (treeNodeCount++));
				}
			}
		}
	}*/


	/**
	 * old showComboData changed. now it receive only two parameter.
	 * changes by wasim, 20-may-2009
	 * 
	 * @param data
	 * @param comboObj
	 */
	public void showComboData(String[][] data , Combobox comboObj) {

		Comboitem rowItem = null;
		
		for (int i = 0; i < data.length; i++) {

			String id=data[i][0];
			String displayLabel="";
			if(data[i].length >1)
			{
				displayLabel = data[i][1];
			}
			else
			{
				displayLabel = data[i][0];
			}
			rowItem = comboObj.appendItem(displayLabel);
			
			rowItem.setId(id + "_" + (treeNodeCount++));
		}

	}





	public void clearFormData(String formId, Component rootFormValue) {

		Component formObj = (Component) rootFormValue.getFellowIfAny(formId, true);
		List childElement = formObj.getChildren();
		Iterator iter = childElement.iterator();
		while (iter.hasNext()) {
			Object value = iter.next();
			if (value instanceof InputElement) {
				clearFormData(((InputElement) value).getId(), rootFormValue);
				if ((((InputElement) value) instanceof Textbox )|| (((InputElement) value) instanceof Combobox))
				{
					Constraint cons = ((InputElement) value).getConstraint();
					if (cons == null) {
						((InputElement) value).setText("");
					} else {
						((InputElement) value).setConstraint("");
						((InputElement) value).setText("");
						((InputElement) value).setConstraint(cons);
					}
				} else if (((InputElement) value).getId().indexOf("datepicker") >= 0) {
					Constraint consd = ((InputElement) value).getConstraint();
					if (consd == null) {
						((InputElement) value).setText(null);
					} else {
						((InputElement) value).setConstraint("");
						((InputElement) value).setText(null);
						((InputElement) value).setConstraint(consd);
					}
				}
			} else {
				clearFormData(((Component) value).getId(), rootFormValue);
			}
		}
	}



	/**
	 * @param formObj
	 * readOnlyData method has been changed.
	 * Changes by wasim , 21-May-2009
	 * 
	 */
	public void readOnlyData(Component formObj) {

  
		List childElement = formObj.getChildren();
		Iterator iter = childElement.iterator();
		while (iter.hasNext()) {
			Object value = iter.next();
			if (value instanceof InputElement) {
				readOnlyData((org.zkoss.zul.impl.InputElement) value);
				if ((((InputElement) value).getId().indexOf("textbox") >= 0)
						|| (((InputElement) value).getId().indexOf("combobox") >= 0)|| (((InputElement) value).getId().indexOf("datepicker") >= 0)) {

					((InputElement) value).setReadonly(true);
				} 
			}
		/*	else if (value instanceof Calendars)
			{
				((Calendars) value).setReadonly(true);
			}*/
			else {
				readOnlyData(((Component) value));
			}
		}
	}

	//

	/**
	 * @param formObj
	 * editOnlyData method has been changed.
	 * Changes by wasim , 21-May-2009
	 */
	public void editOnlyData(Component formObj) {


		List childElement = formObj.getChildren();
		Iterator iter = childElement.iterator();
		while (iter.hasNext()) {
			Object value = iter.next();
			if (value instanceof InputElement) {
				editOnlyData(((InputElement) value));

				//removed condition of combobox  as combox cant be made editable changdone by pra on 15june2009
				if ((((InputElement) value).getId().indexOf("textbox") >= 0))
						 {
					((InputElement) value).setReadonly(false);
				} 
			}
			/*else if (value instanceof Calendars)
			{
				((Calendars) value).setReadonly(false);
			}*/
			else {
				editOnlyData(((Component) value));
			}
		}
	}



	/**
	 * Method is used to display controls based on rights
	 * Added by pra on 30-may-2009.
	 * @param formObj
	 * @param rights
	 */
	public void displayControlInForm(Component formObj, List rights, List elements) {
		List childElement = formObj.getChildren();
		Iterator iter = childElement.iterator();
		String val = "";
		while (iter.hasNext()) {
			Object value = (Object) iter.next();
			if (value instanceof Menupopup) {
				displayControlInForm(((Component) value), rights,elements);
			} else {

				val = (String) ((Component) value)
				.getAttribute("accessrights");
			}

			if (val != null && val != "") {
				if(value instanceof Calendars)
				{
					((Calendars) value).setVisible(true);
					((Calendars) value).setReadonly(true);
				}
				else
				((Component) value).setVisible(false);
				String[] values = val.split(",");
				for (int j = 0; j < values.length; j++) {
					val = values[j];
					for (int i = 0; i < rights.size(); i++) {
						IRights rig = (IRights) rights.get(i);
						String right = rig.getRightName();
						if (val.equalsIgnoreCase(right)) {							
							if (!(value instanceof Menupopup)) {
								if(value instanceof Calendars)
								{
									((Calendars) value).setVisible(true);
									((Calendars) value).setReadonly(false);
									elements.add(((Component) value).getId());
								}
								else
								{
								((Component) value).setVisible(true);
								  elements.add(((Component) value).getId());
								} 
								makeVisibleParent(((Component) value),elements);							
								break;
							}
						}
					}
				}
				displayControlInForm(((Component) value), rights,elements);

			} else {

				displayControlInForm(((Component) value), rights,elements);
			}
		}
	}
	//
	/**
	 * This is used to make parents visible in  case of rights. 
	 * added by pra on 3-june-2009
	 * @param formObj
	 */ 
	public void makeVisibleParent(Component formObj,List elements)
	{  
		Component parent=(Component)formObj.getParent();
		if(parent!=null)
		{
			if (!(parent instanceof Menupopup)) {
				if(parent instanceof Calendars)
				{
					((Calendars) parent).setReadonly(false);
					((Calendars) parent).setVisible(true);
					elements.add(((Component) parent).getId());
				}
				else
				{
				((Component) parent).setVisible(true);
				  elements.add(((Component) parent).getId());
				} 
			}
			makeVisibleParent(parent,elements);
		}
		else
		{
			return;
		}

	}

	public void showLoginInfo(Label firstObj,Session session,IData dataUnit)
	{

		if(dataUnit ==null)
		{
			return;
		}

		List listValue = dataUnit.getQueryData().getListData();
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		if(allValues==null)
			return;
		//String key = getSessionData("loginKey");
		String user = (String) session.getAttribute("userId");
		String logDate = null;
		String logTime = null;
		String loginValue = null;
		Long lastLoginTime =null;

		for(int row=0;row<allValues.length;row++)
		{
			String user1 = allValues[row][1];

			if(user.equalsIgnoreCase(user1))
			{
				logDate = allValues[row][2];
				logTime = allValues[row][3];
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
				System.out.println("--Lost login time--"+diff);
				System.out.println("--lastLoginTime--"+lastLoginTime);
				if(lastLoginTime<diff)
				{
					lastLoginTime = diff;
				}

			}
		}
		long diffSeconds = lastLoginTime / 1000;
		long diffMinutes = lastLoginTime / (60 * 1000);
		long diffHours = lastLoginTime / (60 * 60 * 1000);
		long diffDays = lastLoginTime / (24 * 60 * 60 * 1000);

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



	public String checkValidation(Component controlObj,String valueType,String value)
	{
		String alphaNPattern = "[a-zA-Z\\s]+[a-zA-Z0-9\\s]*";
		String stringPattern = "[a-zA-Z\\s]*";
		String pNPattern="^\\(?(\\d*)\\)?[- ]?(\\d*)[- ]?(\\d*)$";
		if(controlObj == null || valueType == null || value == null || value =="")
		{
			return "";
		}
		String msgStr = "";
		boolean isValid =true;
		if(valueType.equalsIgnoreCase("Numeric"))
		{
			value = value.trim();
			//((InputElement) controlObj).setStyle(normalBGColor);
			try
			{ 
				//this check is added to check if value is empty or null
				if(!value.trim().equalsIgnoreCase("")&& value.length()>0)
				{
					Double.parseDouble(value);
				
					//min max range check for value;
					String minValue = (String)controlObj.getAttribute("minvalue");
					String maxValue = (String)controlObj.getAttribute("maxvalue");
					try{
						if(minValue!=null && !minValue.equals("") && Double.parseDouble(value) < Double.parseDouble(minValue)){
							//((InputElement) controlObj).setStyle(errorBGColor);
							((InputElement)controlObj).setText("");
							msgStr = " Please enter number in min max range "+ZKConstants.NEW_LINE;
						}
						
						if(maxValue!=null && !maxValue.equals("") && Double.parseDouble(value) > Double.parseDouble(maxValue)){
							//((InputElement) controlObj).setStyle(errorBGColor);
							((InputElement)controlObj).setText("");
							msgStr = " Please enter number in min max range  "+ZKConstants.NEW_LINE;
						}
					}
					catch (Exception e) {
						System.out.println("max or min values are not correctly specified");
					}
				}
				
			}catch(Exception exp)
			{
				//((InputElement) controlObj).setStyle(errorBGColor);
				((InputElement)controlObj).setText("");
				if (((InputElement) controlObj).getConstraint() != null) {
					msgStr = " Please enter number only. "+ZKConstants.NEW_LINE;
				}
				else
				{
					msgStr = ((InputElement) controlObj).getName()+" Please enter number only. "+ZKConstants.NEW_LINE;
				}
			}


		}
		else if(valueType.equalsIgnoreCase("Email"))
		{
			isValid = this.emailCheck(value.trim());
			if(!isValid)
			{
				//((InputElement) controlObj).setStyle(errorBGColor);
				((InputElement)controlObj).setText("");
				if (((InputElement) controlObj).getConstraint() != null) {
					msgStr = " Please enter valid E-mail address only. "+ZKConstants.NEW_LINE;
				}
				else
				{
					msgStr = ((InputElement) controlObj).getName()+" Please enter valid E-mail address only. "+ZKConstants.NEW_LINE;
				}
			}
			else
			{
				//((InputElement) controlObj).setStyle(normalBGColor);
			}

		}
		else if(valueType.equalsIgnoreCase("String"))
		{
			if(Pattern.matches(stringPattern, value.trim()))
			{
				//((InputElement) controlObj).setStyle(normalBGColor);
			}
			else
			{
				//((InputElement) controlObj).setStyle(errorBGColor);
				((InputElement)controlObj).setText("");
				if (((InputElement) controlObj).getConstraint() != null) {
					msgStr= " Please enter string value only. "+ZKConstants.NEW_LINE;
				}
				else
				{
					msgStr= ((InputElement) controlObj).getName()+" Please enter string value only. "+ZKConstants.NEW_LINE;
				}
			}
			return msgStr;

		}
		else if(valueType.equalsIgnoreCase("AlphaNumeric"))
		{
			if(Pattern.matches(alphaNPattern, value.trim()))
			{
				//((InputElement) controlObj).setStyle(normalBGColor);
			}
			else
			{
				//((InputElement) controlObj).setStyle(errorBGColor);
				((InputElement)controlObj).setText("");
				if (((InputElement) controlObj).getConstraint() != null) {
					msgStr= " Please enter alphanumeric value only. First character should be alphabet."+ZKConstants.NEW_LINE;
				}
				else
				{
					msgStr= ((InputElement) controlObj).getName()+" Please enter alphanumeric value only. "+ZKConstants.NEW_LINE +"First character should be alphabet."+ZKConstants.NEW_LINE;
				}
			}			
			return msgStr;
		}
		else if(valueType.equalsIgnoreCase("SpecificNumbers"))
		{
			if(Pattern.matches(pNPattern, value.trim()))
			{
				//((InputElement) controlObj).setStyle(normalBGColor);
			}
			else
			{
				//((InputElement) controlObj).setStyle(errorBGColor);
				((InputElement)controlObj).setText("");
				if (((InputElement) controlObj).getConstraint() != null) {
					msgStr= " Please enter numbers only"+ZKConstants.NEW_LINE;
				}
				else
				{
					msgStr= ((InputElement) controlObj).getName()+" Please enter numbers only. "+ZKConstants.NEW_LINE+ZKConstants.NEW_LINE;
				}
			}			
			return msgStr;
		}

		return msgStr;
	}


	public boolean emailCheck(String str) {
		String at="@";
		String dot=".";
		int lat=str.indexOf(at);
		int lstr=str.length();
		int ldot=str.indexOf(dot);
		if (str.indexOf(at)==-1 || str.indexOf(at)==0 ||
				str.indexOf(at)==lstr){
			return false;
		}

		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 ||
				str.indexOf(dot)==lstr){

			return false;
		}

		if (str.indexOf(at,(lat+1))!=-1){
			return false;
		}

		if (str.substring(lat-1,lat)==dot || 
				str.substring(lat+1,lat+2)==dot){

			return false;
		}
		if (str.indexOf(dot,(lat+2))==-1){

			return false;
		}

		if (str.indexOf(" ")!=-1){
			return false;
		}
		return true;	



	}


	/**
	 * This method check the date of birth is valid or not. it won't allow future date for date of birth.
	 * @param datepicker1
	 * @param rootFormValue
	 * @param formValues
	 * @return
	 */
	public boolean checkDOB(String datepickerId, Window rootFormValue, Hashtable formValues) 
	{
		boolean isDateValid = true;
		Component datepickerObj = rootFormValue.getFellowIfAny(datepickerId, true);
		if(datepickerObj != null){
			Datebox dateBox = (Datebox)datepickerObj;
			String date1 = (String)formValues.get(datepickerId);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date selectedDate = null;
			Date currentDate = null;
			try{
				if(date1 != null && !date1.trim().equalsIgnoreCase("")){	
					selectedDate = dateFormat.parse(date1);
					Date current = new Date();
					String curDate = dateFormat.format(current);
					currentDate = dateFormat.parse(curDate);
				}
			}catch(ParseException ex){
				ex.printStackTrace();
			}
			if(selectedDate != null){
				if (/*selectedDate.after(currentDate)*/selectedDate.before(currentDate)) {
					isDateValid = false;
				} 
			}
		}
		return isDateValid;
	}
}
