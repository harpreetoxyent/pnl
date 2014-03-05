package com.oxymedical.component.renderer.uiBuilder.zk.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import com.oxymedical.component.renderer.Application;
import com.oxymedical.component.renderer.constants.ZKConstants;
import com.oxymedical.component.renderer.exception.RendererComponentException;
import com.oxymedical.component.renderer.uiBuilder.zk.ZKBuilder;
import com.oxymedical.core.xmlutil.XmlReader;


/**
 * This utility class helps to make the UIBuilder for the user interface
 * 
 */
public class ZKBuilderUtility 
{	
	private XmlReader xmlReader = null;		
	private Document configDoc, document;
	private Document mapFileDoc, mapDocument;
	public boolean isConstraints =false;
	Element elt = null;
	private ZKScriptBuilder zkScriptBuilder = null;
	//private Hashtable<String, String> hashTab = new Hashtable<String, String>();
	private LinkedHashMap<String, String> hashTab = new LinkedHashMap<String, String>();
	
	int i=0;
	
	public ZKBuilderUtility() {
		xmlReader = new XmlReader();
		zkScriptBuilder = new ZKScriptBuilder();
	}
	
	/**
	 * This method is used to Start the application's user interface in ZK
	 * @param Application application
	 * @return 
	 */
	public String startApplication(Application application)
	{
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(ZKConstants.ZK_APP_START_TAG + ZKConstants.NEW_LINE);		
		return buffer.toString();
	}
	
	/**
	 * this method is used to end the application's user interface in ZK
	 * @param Application application
	 * @param String defaultForm
	 * @return
	 */
	public String endApplication(Application application)
	{
		StringBuffer buffer = new StringBuffer();		
		buffer.append(ZKConstants.ZK_APP_END_TAG + ZKConstants.NEW_LINE);
		return buffer.toString();
	}
	
	/**
	 * Include the default zul page in main application page
	 * 
	 * @param formPatternXmlName
	 * @return
	 */
	public String includeDefaultFormPattern(String formPatternXmlName) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(ZKConstants.ZSCRIPT_START_TAG + ZKConstants.NEW_LINE);
		buffer.append("Executions.sendRedirect(\""+ZKConstants.ZK_FOLDER_NAME+"/"+formPatternXmlName+ZKConstants.ZK_EXTN+"\")" + ZKConstants.NEW_LINE);
		buffer.append(ZKConstants.ZSCRIPT_END_TAG + ZKConstants.NEW_LINE);
		return buffer.toString();
	}
	
	/**
	 * Add zscipt refernce in each page
	 * 
	 * @param formPatternXmlName
	 * @return
	 */
	public String addZscriptSrc() {
		StringBuffer buffer = new StringBuffer();	
		buffer.append("<zscript src=\"/"+ZKConstants.LIBRARY_FOLDER_NAME+"/"+ZKConstants.UI_LIBRARY+"\"/>" + ZKConstants.NEW_LINE);
		return buffer.toString();
	}
	
	/**
	 * Add init zscipt refernce in each page
	 * 
	 * @return
	 */
	public String addInitZscriptSrc() {
		StringBuffer buffer = new StringBuffer();	
		buffer.append("<?init zscript=\"/"+ZKConstants.LIBRARY_FOLDER_NAME+"/"+ZKConstants.INIT_ZK_SCRIPT+"\"?>" + ZKConstants.NEW_LINE);
		return buffer.toString();
	}

	
	/**
	 * Add Form tag  in each frame which has to be submitted
	 * 
	 * @param formId
	 * @return String
	 */
	public String addFormSrc(String formId, String appName) 
	{
		StringBuffer buffer = new StringBuffer();	
		buffer.append(ZKConstants.FORM_ID_PART+ formId + ZKConstants.FORM_ACTION_PART + appName + ZKConstants.CONTROLLER_SERVLET + ZKConstants.FORM_METHOD_PART+ZKConstants.FORM_XMLNS_PART );
		return buffer.toString();
	}
	
	/**
	 * Starts an element tag
	 * 
	 * @param tagName
	 * @return
	 */
	public static String startElementTag(String tagName)
	{	
		String retStr = "";
		retStr = retStr + "<" + tagName;
		return retStr;
	}
	
		
	/**
	 * Ends an element tag
	 * 
	 * @param tagName
	 * @param isTreeLeaf - relevant only for tree
	 * @param strValue - retain value for the tabpannels in ZK
	 * @return
	 */
	
	// Implementation change for this method for the tabbox and tabs by pra on 12,13 may 2009.
	public String endElementTag(String tagName, String isTreeLeaf,
			String strValue, LinkedHashMap<String,String> colorValue, Element xmlNode) {
		String retStr = "";
		String tabValue = "", keyValue = "";
		strValue = strValue.trim();
		// in case of grid, rows tag need to be closed before grid tag closing
		//changes done on 12/4/2011 at 6_23 PM 
		if (tagName.equals(ZKConstants.GRID) && !"DGrid".equals(xmlNode.getName()) && xmlNode.attributeValue(ZKConstants.GRID_TYPE) == null)
			retStr = retStr + ZKConstants.ROWS_END_TAG + ZKConstants.NEW_LINE;

		// in case of menu, menupopup tag needs to be closed before menu tag
		// closing
		if (tagName.equals(ZKConstants.MENU))
			retStr = retStr + ZKConstants.MENUPOPUP_END_TAG
					+ ZKConstants.NEW_LINE;

		// this is requred to close tree children tag in case the tree has
		// childrens
		if (null == isTreeLeaf
				&& (tagName.equals(ZKConstants.TREE) || tagName
						.equals(ZKConstants.TREE_ITEM)))
		{
			String istreeLeaf = xmlNode.attributeValue("isleaf");
			if(istreeLeaf != null && istreeLeaf.equalsIgnoreCase(ZKConstants.TRUE)){
				retStr = retStr + ZKConstants.NEW_LINE;
			}else{
				retStr = retStr + ZKConstants.TREECHILDREN_END_TAG+ ZKConstants.NEW_LINE;
			}
		}
			//retStr = retStr + ZKConstants.TREECHILDREN_END_TAG+ ZKConstants.NEW_LINE;

		// This is required to close put the values of the Tab children tag
		if (tagName.equals(ZKConstants.TAB)) {
			if (!strValue.equalsIgnoreCase("")) {
				hashTab.put(xmlNode.attributeValue(ZKConstants.ID), strValue);
				i++;
			}
		}
		// this is required to put the tabpanels in the tabbox
		if (tagName.equals(ZKConstants.TABBOX)) {
			retStr = ZKConstants.TABS_END_TAG + ZKConstants.NEW_LINE;
			Object[] keys = hashTab.keySet().toArray();
			/*
			 * for(int j=0; j<keys.length; j++) { //String key = ZKConstants.TAB
			 * + j; String value = hashTab.get(keys[j]);
			 */
			if (!(xmlNode.getParent() != null && xmlNode.getParent().getName()
					.equalsIgnoreCase(ZKConstants.TAB_PAGE))) {
				List childs = xmlNode.elements();
				if (childs.size() > 1) {
					for (int c = 0; c < childs.size(); c++) {
						String key = ((Element) childs.get(c))
								.attributeValue(ZKConstants.ID);
						if (key != null && key != "") {
							String value = hashTab.get(key);
							String colorVal=colorValue.get(key);
							if(value==null)
							{
								value="";
							}
							if(colorVal==null)
								colorVal="";
							keyValue = keyValue
									+ ZKConstants.TABPANEL_START_TAG
									+ ZKConstants.EMPTY_STRING
									+ colorVal + ZKConstants.END_TAG
									+ value + ZKConstants.TABPANEL_END_TAG;
						}
					}
				}
			}

			if (!(xmlNode.getParent() != null && xmlNode.getParent().getName()
					.equalsIgnoreCase(ZKConstants.TAB_PAGE))) {
				tabValue = ZKConstants.TABPANELS_START_TAG + keyValue
						+ ZKConstants.TABPANELS_END_TAG;
				tabValue = tabValue + ZKConstants.NEW_LINE + "</" + tagName
						+ ">" + ZKConstants.NEW_LINE;
				retStr = retStr + tabValue + ZKConstants.NEW_LINE;
				i = 0;
			} else {
				List child = xmlNode.elements();
				if (child.size() > 1) {
					for (int a = 0; a < child.size(); a++) {
						String keyChild = ((Element) child.get(a))
								.attributeValue(ZKConstants.ID);
						if (keyChild != null && keyChild != "") {
							String valueC = hashTab.get(keyChild);
							String colorChild=colorValue.get(keyChild);
							if(valueC==null)
							{
								valueC="";
							}
							if(colorChild==null)
								colorChild="";
							keyValue = keyValue
									+ ZKConstants.TABPANEL_START_TAG
									+ ZKConstants.EMPTY_STRING+ colorChild
									+ ZKConstants.END_TAG + valueC
									+ ZKConstants.TABPANEL_END_TAG;
						}
					}
				}
				tabValue = ZKConstants.TABPANELS_START_TAG + keyValue
						+ ZKConstants.TABPANELS_END_TAG;
				retStr = retStr + tabValue + "</tabbox>" + ZKConstants.NEW_LINE;
			}

			return retStr;
		} else if (!tagName.equals("")) {
			retStr = retStr + ZKConstants.NEW_LINE + "</" + tagName + ">"
					+ ZKConstants.NEW_LINE;

		}
		return retStr;

	}


//add method to add page code by pra on 12th may2009.
public String addPageTag(String fileName)
{   
	String page="";
	page=page+ZKConstants.PAGE_START_TAG+"\""+fileName+"\""+ZKConstants.PAGE_END_TAG + ZKConstants.NEW_LINE;
	 return page;
}

	/**
	 * Adds a reference to external style sheet to the page
	 * 
	 * @param fileName
	 * @return
	 */
	public String addStyleContent(String fileName) {
		String retStr = "";		
		retStr = retStr + "<style src=\"/css/" + fileName + ZKConstants.CSS_EXTN+"\"/>" + ZKConstants.NEW_LINE;
		return retStr;
	}
	
	/**
	 * Adds a reference to external style sheet to the page
	 * 
	 * @param fileName
	 * @return
	 */
	public String addCommonStyleContent() {
		String retStr = "";
		retStr = retStr + "<style src=\"/css/" + ZKConstants.COMMON_CSS_FILE + "\"/>" + ZKConstants.NEW_LINE;
		return retStr;
	}

	/**
	 * Adds an attribute with its value to a tag
	 * 
	 * @param zkAttrName
	 * @param zkAttrValue
	 * @return
	 */
	public String addAttribute(Element xmlNode, String zkAttrName, String zkAttrValue) {
		String retStr = " ";
		String listStr="";
		String xmlTagName = xmlNode.getName();
		if (zkAttrName.equals(ZKConstants.ATTR_WIDTH) || zkAttrName.equals(ZKConstants.ATTR_HEIGHT)) {
			
			/*
			 * 100% width attribute added for basewindow only
			 *  wasim 13-May-2009
			 * 
			 */
			String popup = xmlNode.attributeValue("popup");
			if(popup==null)
				popup ="False";
			if(xmlTagName.equalsIgnoreCase(ZKConstants.BASE_WINDOW_TAG_NAME) && zkAttrName.equals(ZKConstants.ATTR_WIDTH)&& popup.equalsIgnoreCase(ZKConstants.FALSE) )
			{
				zkAttrValue = ZKConstants.ATTR_PERCENT;
			}
			else
			{
				zkAttrValue = zkAttrValue + ZKConstants.ATTR_PIXEL;
			}
		}
		if(zkAttrName.equalsIgnoreCase(ZKConstants.DATAPATTERN))
		{
			
			//String val = ZKBuilder.winId + ""
			retStr=retStr+" onCreate=" + "'" + "getGridData("+ZKBuilder.element_id + "," + "\""+ZKBuilder.winId+ "\" "  + ")'";
			zkAttrName="";
			zkAttrValue="";
		}
		else if (zkAttrName.equalsIgnoreCase(ZKConstants.VALUE) && zkAttrValue==null)
		{
			
			retStr = retStr + zkAttrName + "=\"" + "" + "\"";
			
			
		}
		else if(zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_CONSTRAINT))
		{
			if(zkAttrValue.equalsIgnoreCase(ZKConstants.VALIDATION_ALPHANUMERIC))
			{
				retStr = retStr + zkAttrName + "=\"" + ZKConstants.REGX_ALPHANUMERIC + "\"";
				isConstraints= true;
			}
			else if(zkAttrValue.equalsIgnoreCase(ZKConstants.VALIDATION_NUMERIC))
			{
				retStr = retStr + zkAttrName + "=\"" + ZKConstants.REGX_NUMERIC + "\"";
				isConstraints= true;
			}
			else if(zkAttrValue.equalsIgnoreCase(ZKConstants.VALIDATION_EMAIL))
			{
				retStr = retStr + zkAttrName + "=\"" + ZKConstants.REGX_EMAIL + "\"";
				isConstraints= true;
			}
			else if(zkAttrValue.equalsIgnoreCase(ZKConstants.VALIDATION_STRING))
			{
				retStr = retStr + zkAttrName + "=\"" + ZKConstants.REGX_STRING + "\"";
				isConstraints= true;
			}
			else if(zkAttrValue.equalsIgnoreCase(ZKConstants.ATTR_ISMANDATORY)&& !isConstraints)
			{
				retStr = retStr + zkAttrName + "=\"" + "" + "\"";
				isConstraints= false;
			}
		}
		/*
		else if(zkAttrName.equalsIgnoreCase(ZKConstants.ID ) && xmlTagName.equalsIgnoreCase(ZKConstants.GRID))
		{
		//retStr = retStr + zkAttrName + "=\"" + zkAttrValue + "\"";
			listStr = listStr + zkAttrName + "=\"" + zkAttrValue + "\"";
		}*/
		else
		{
			retStr = retStr + zkAttrName + "=\"" + zkAttrValue + "\"";
		}
		
		return retStr;
	}
	
	public String createMultilineText(Element xmlNode, String zkAttrName , List multiLineText)
	{
		boolean isMulLineTextNull = false;
		StringBuffer buffer = new StringBuffer();
		String retStr = "";
		if(multiLineText==null)
		{
			multiLineText = new ArrayList();
			isMulLineTextNull = true;
		}
		int multiTextRowLength=0;
		if(multiLineText != null)
			multiTextRowLength = multiLineText.size();
		if(multiTextRowLength==0)
			multiTextRowLength = 2;
		String zkRowCount = String.valueOf(multiTextRowLength);
		//if(zkRowCount =0)
		retStr = addAttribute(xmlNode , zkAttrName , zkRowCount);
		if(isMulLineTextNull)
		{
			buffer.append(retStr + ZKConstants.NEW_LINE + addAttribute(xmlNode , "cols","50"));
		}
		else
		{
			buffer.append(retStr + ZKConstants.NEW_LINE + addAttribute(xmlNode , "cols","50")+">");
		}
		if(multiLineText.size()==0)
			return buffer.toString();
		//buffer.append(">");
		buffer.append("<" + ZKConstants.zkAttributeTag  + addAttribute(xmlNode, "name","value")+ "> " + ZKConstants.NEW_LINE);
		for(int textRowCount = 0; textRowCount < multiTextRowLength ; textRowCount++)
		{
			buffer.append(multiLineText.get(textRowCount) + ZKConstants.NEW_LINE);
		}
		buffer.append("</" + ZKConstants.zkAttributeTag + ">" + ZKConstants.NEW_LINE);
		return buffer.toString();
	}
	
	public List retreiveZKMultlineText(String zkAttrValue)
	{
		String retString = null;
		List multilineText = new ArrayList();
		int strValueLength = zkAttrValue.length();
		int startIndex = 0;
		for(int strCounter = 0; strCounter < strValueLength ;strCounter++)
		{
			
			if(strCounter == strValueLength-1)
			{
				retString = zkAttrValue.substring(startIndex,strCounter+1);
				multilineText.add(retString);
			}
			else
			if(zkAttrValue.charAt(strCounter) == '\n')
			{
				retString = zkAttrValue.substring(startIndex,strCounter-1);
				startIndex = strCounter+1;
				if(retString.equals(""))
				{
					continue;
				}
				multilineText.add(retString);
				
			}
		}
		return multilineText;
	}

	
	/**
	 * Gets the form pattern xml document
	 *  
	 * @param Application application
	 * @param String formPatternId
	 * @return formPatternDoc
	 * @throws RendererComponentException
	 */
	public Document getFormPatternXmlDoc(Application application, String formPatternId) throws RendererComponentException
	{
		Document formPatternDoc = null;		
		try
		{
			String formPatternXmlFileSrc = application.getApplicationFolderPath() + ZKConstants.FORM_PATTERN_FOLDER_NAME + "/" + formPatternId + ZKConstants.PATTERN_EXTN;
			//logger.log(0," formPatternXmlFileSrc = " +formPatternXmlFileSrc);
			formPatternDoc = xmlReader.getDocumentFromPath(formPatternXmlFileSrc);
		}
		catch(Exception fileExp)
		{
			throw (new RendererComponentException(fileExp.getMessage()));
		}		
		return formPatternDoc;
	}
	
	
	/**
	 * Sets the mapfile xml
	 * 
	 * @param configfile
	 * @throws Exception
	 */
	public void setConfigurationXml(String configfile)
	{
		configDoc = xmlReader.getDocumentAsResource(configfile);	
		
	}
	
	public void setMapFileXml(String configfile)
	{
		mapFileDoc = xmlReader.getDocumentFromPath(configfile);	
		
	}
	
	
	/**
	 * Gets the zk value of tag attribute specified by tagName
	 * 
	 * @param tagName
	 * @return
	 */
	public String findDisplayName(String formpatternName ,String controlId)
	{
		String returnValue= null;
		Element root = mapFileDoc.getRootElement();
		List nodes = root.elements("formpattern");
		for(Iterator i = nodes.iterator(); i.hasNext(); )
		{		
			String nodeName = null;
			String formName = null;
			Element nodeElement = (Element) i.next();
			// iterate through attributes of module 
			
			formName = nodeElement.attributeValue("name");
			if(formName.equalsIgnoreCase(formpatternName))
			{
				for (Iterator m = nodeElement.elementIterator(); m.hasNext(); )
				{
					Element nodeChildElement = (Element) m.next();
					String elementId = nodeChildElement.attributeValue("id");
					String display = nodeChildElement.attributeValue("displayName");
					if(elementId.equalsIgnoreCase(controlId))
					{
						returnValue = display;
						break;
					}
					
    				
				}
			}
			if(returnValue!=null)
				break;
			
		}
			return returnValue;
	}
	
	
	
	/**
	 * Gets the zk value of tag attribute specified by tagName
	 * 
	 * @param tagName
	 * @return
	 */
	public String findTag(String tagName)
	{
		elt = configDoc.getRootElement();
		elt = xmlReader.getElementById(elt, tagName);
		
		if(elt!=null)
			return elt.attributeValue(ZKConstants.ZKNAME);
		else
			return "";
	}

	
	/**
	 * @param tagName
	 * @param attrName
	 * @return
	 */
	public String findAttribute(String tagName, String attrName)
	{
		
		elt = configDoc.getRootElement();
		elt = xmlReader.getElementById(elt, tagName);
		//elt = configDoc.elementByID(tagName);
		if(elt!=null)
		{
			elt = xmlReader.getElementById(elt, attrName);
			if(elt!=null)
				return elt.attributeValue(ZKConstants.ZKNAME);
			else
				return "";
		}
		else
			return "";
	}
	
	public String getAttributeName(String objName, String atrName) throws RendererComponentException
	{
		String elementTagName = "";
		Element elt = xmlReader.getElementByAttribute(document.getRootElement(), "name", objName);
		if(elt!=null)
		{
			elementTagName = elt.getName();
			return findAttribute(elementTagName, atrName);
		}
		else
			throw new RendererComponentException("Element '" + objName + "' does not exists.");
	}
	
	/**
	 * Render the serverside events
	 *  
	 * @param String baseWindowName
	 * @param String eventName
	 * @param String elementName
	 * @return retEvent
	 */
	public String serverSideEvent(String eventName, String elementName)
	{
		String retEvent="";
		
		
		return retEvent;
	}
	/**
	 * Render the client side events 
	 * @param eventName
	 * @param clientScript
	 * @return
	 * @throws RendererComponentException
	 */
	public String clientSideEvents(Element xmlNode, String eventName, String clientScript, String formId)  throws RendererComponentException
	{
		
		/*logger.log(0,"--------parseEvents in zkbuilderUtility---clientSideEvents-----"+xmlNode);
		logger.log(0,"--------parseEvents in zkbuilderUtility---eventName-----"+eventName);
		logger.log(0,"--------parseEvents in zkbuilderUtility---clientScript-----"+clientScript);*/
		//logger.log(0,"--------parseEvents in zkbuilderUtility---clientScript--11---"+clientScript);
		StringBuffer str = new StringBuffer();
		String xmlNodeName = xmlNode.getName();
		
		if((eventName.equalsIgnoreCase(ZKConstants.GLOBAL_SCRIPTS) || eventName.equalsIgnoreCase(ZKConstants.STYLE_SCRIPTS) )  && xmlNodeName.equalsIgnoreCase(ZKConstants.BASE_WINDOW_TAG_NAME)){
			if(eventName.equalsIgnoreCase(ZKConstants.GLOBAL_SCRIPTS)){
				str.append(ZKConstants.ZSCRIPT_START_TAG + ZKConstants.CDATA_TAG_START + ZKConstants.NEW_LINE);
				str.append(clientScript);
				str.append(ZKConstants.NEW_LINE + ZKConstants.CDATA_TAG_END + ZKConstants.ZSCRIPT_END_TAG + ZKConstants.NEW_LINE);
			}else{
				str.append(ZKConstants.STYLE_START_TAG + ZKConstants.CDATA_TAG_START + ZKConstants.NEW_LINE);
				str.append(clientScript);
				str.append(ZKConstants.NEW_LINE + ZKConstants.CDATA_TAG_END + ZKConstants.STYLE_END_TAG + ZKConstants.NEW_LINE);
			}
		}else{
			//logger.log(0,"xml node name is "+xmlNodeName + "event name is " + eventName);
			if (eventName.equalsIgnoreCase(ZKConstants.ON_CLICK) && (xmlNodeName.equalsIgnoreCase(ZKConstants.LIST_BOX) || xmlNodeName.equalsIgnoreCase(ZKConstants.GRID) || xmlNodeName.equalsIgnoreCase(ZKConstants.COMBO_BOX)))
			{
				eventName = ZKConstants.ON_SELECT;
			}
			if(eventName.equalsIgnoreCase(ZKConstants.ON_CLICK) && (xmlNodeName.equalsIgnoreCase(ZKConstants.DATE_PICKER)))
			{
				eventName = ZKConstants.ON_SELECTION;
			}
			str.append("<attribute" + " " + "name=\"" + eventName +"\">" + ZKConstants.CDATA_TAG_START + ZKConstants.NEW_LINE);
			String[] subString = clientScript.split(";");
			str.append(ZKConstants.FORMPATTERN_METHOD+ formId + ZKConstants.CLOSE_METHOD + ZKConstants.NEW_LINE);
			str.append(ZKConstants.DATAPATTERN_METHOD+ ZKBuilder.dataPattern + ZKConstants.CLOSE_METHOD + ZKConstants.NEW_LINE);
			for ( int i=0; i<subString.length ; i++)
			{
			//	logger.log(0,"--------parseEvents in zkbuilderUtility-22--subString-----"+subString[i]);
				String checkedString = checkXmlArgs(subString[i]);
				//logger.log(0,"--------parseEvents in zkbuilderUtility---checkedString-----"+checkedString);
				str.append(checkedString +";");
			}
			str.append(ZKConstants.NEW_LINE + ZKConstants.CDATA_TAG_END + "</attribute>" + ZKConstants.NEW_LINE);
		}
		
		return str.toString();
	}
	
	private	String checkXmlArgs(String subClientScript)
	{
		if(subClientScript!=null && subClientScript.trim().length()>0)
		{
			int xmlIndex = subClientScript.indexOf(ZKConstants.XML_TAG_START);
			//logger.log(0,"-----Present in Check XmlAges----Check Xml------  ] "+xmlIndex);
			if(xmlIndex>0)
			{
			//	logger.log(0,"-----Present in Check XmlAges----------]  "+xmlIndex);
				subClientScript = subClientScript.replaceAll(ZKConstants.XML_TAG_START, ZKConstants.CDATA_TAG_START);
				subClientScript = subClientScript.replaceAll(ZKConstants.XML_TAG_END, ZKConstants.CDATA_TAG_END);
			}
		}
		//logger.log(0,"----Returning SubString--------"+subClientScript);
		return subClientScript;
	}
	/**
	 * Render the client side events
	 * 
	 * @param ClientScriptBuilder csBuilder
	 * @param String baseWindowName
	 * @param String eventName 
	 * @param String clientScript 
	 * @param String elementName  
	 * @param String xmlTagName  
	 * @return retEvent
	 */
	public String clientSideEvent(String eventName, String clientScript)  throws RendererComponentException
	{
		String retEvent="";
		retEvent = " " + eventName + "=";	
		if(clientScript.contains("update"))
			retEvent = retEvent + "\"" + zkScriptBuilder.parse(clientScript) + "\"";	
		else
			retEvent = retEvent + "\'" + zkScriptBuilder.parse(clientScript) + "\'";
		//retEvent = retEvent.replaceAll("\"", "&quot;");		
		return retEvent;
	}
	
	public String getAttributeNameByTag(String tagName, String atrName)
	{
		//String elementName = "";
		//Element elt = xmlReader.getElementByAttribute(document.getRootElement(), "id", objName);
		if(tagName!=null && !(tagName.trim().equals("")))
		{
			return findAttribute(tagName, atrName);
		}
		else
			return "";
	}
	
	
	public String getZKMultilineTextBox(String attrValue)
	{
		return null;
	}
	
	/**
	 * Gets the event name from config xml document
	 * @param tagName
	 * @param eventName
	 * @return
	 */
	public String findEvent(String tagName, String eventName)
	{
		elt = configDoc.getRootElement();
		//elt = xmlReader.getElementById(elt, tagName);
		//elt = configDoc.elementByID(tagName);
		if(elt!=null)
		{
			elt = xmlReader.getElementById(elt, eventName);
			if(elt!=null)
				return elt.attributeValue(ZKConstants.ZKNAME);
			else
				return "";
		}
		else
			return "";		
	}

	/**
	 * Starts a css for an element 
	 * 
	 * @param cssClassName
	 * @return
	 */
	public String startElementCSS(String cssClassName) {
		String retStr = "";
		retStr = retStr + "." + cssClassName + "{" + ZKConstants.NEW_LINE;		
		return retStr;
	}
	
	/**
	 * Add style sheets attribues to css
	 *  
	 * @param zkAttrName
	 * @param zkAttrValue
	 * @return
	 */
	public String addStyles(String zkAttrName, String zkAttrValue) {
		String retStr = "";
		if (zkAttrName.equals(ZKConstants.ATTR_MARGIN_TOP)||zkAttrName.equals(ZKConstants.ATTR_PADDING_LEFT)||zkAttrName.equals(ZKConstants.ATTR_PADDING_RIGHT)||zkAttrName.equals(ZKConstants.ATTR_PADDING_TOP)
				||zkAttrName.equals(ZKConstants.ATTR_MARGIN_BOTTOM)||zkAttrName.equals(ZKConstants.ATTR_PADDING_BOTTOM)
				||zkAttrName.equals(ZKConstants.ATTR_LEFT)|| zkAttrName.equals(ZKConstants.ATTR_TOP) 
				|| zkAttrName.equals(ZKConstants.ATTR_MARGIN_LEFT)||zkAttrName.equals(ZKConstants.ATTR_MARGIN_RIGHT))
			retStr = retStr + zkAttrName + ":" + zkAttrValue + ZKConstants.ATTR_PIXEL + ";" ;
		else if (zkAttrName.equals(ZKConstants.ATTR_FONT_SIZE))
			retStr = retStr + zkAttrName + ":" + zkAttrValue + ZKConstants.ATTR_POINT  + ";" ;
		else if (zkAttrName.equals(ZKConstants.ATTR_BACKGROUND_IMAGE)){
		   String attrValue = zkAttrValue.substring(1);
			retStr = retStr + zkAttrName + ":" + "url('../zul/" + attrValue + "')" + ";" ;
		}
		else if (zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_FONT_STYLE) && zkAttrValue.equalsIgnoreCase(ZKConstants.FONT_STYLE_REGULAR))
			retStr = retStr + zkAttrName + ":" + ZKConstants.CSS_STYLE_NORMAL + ";" ;
		else if (zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_FONT_STYLE) && zkAttrValue.equalsIgnoreCase(ZKConstants.FONT_STYLE_ITALIC))
			retStr = retStr + zkAttrName + ":" + ZKConstants.FONT_STYLE_ITALIC + ";" ;
		else if (zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_FONT_STYLE) && zkAttrValue.equalsIgnoreCase(ZKConstants.FONT_STYLE_BOLD))
			retStr = retStr + ZKConstants.ATTR_FONT_WEIGHT + ":" + ZKConstants.FONT_STYLE_BOLD + ";" ;
		else if (zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_FONT_STYLE) && zkAttrValue.equalsIgnoreCase(ZKConstants.FONT_STYLE_UNDERLINE))
			retStr = retStr + ZKConstants.ATTR_TEXTDECORATION + ":" + ZKConstants.FONT_STYLE_UNDERLINE + ";" ;
		else if (zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_FONT_STYLE) && zkAttrValue.equalsIgnoreCase(ZKConstants.FONT_STYLE_BOLD_ITALIC))
			retStr = retStr + ZKConstants.ATTR_FONT_WEIGHT + ":" + ZKConstants.FONT_STYLE_BOLD  + ";" + ZKConstants.NEW_LINE 
			+ zkAttrName + ":" + ZKConstants.FONT_STYLE_ITALIC + ";" ;
		else if (zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_FONT_STYLE) && zkAttrValue.equalsIgnoreCase(ZKConstants.FONT_STYLE_BOLD_UNDERLN))
			retStr = retStr + ZKConstants.ATTR_FONT_WEIGHT + ":" + ZKConstants.FONT_STYLE_BOLD  + ";" + ZKConstants.NEW_LINE 
			+ ZKConstants.ATTR_TEXTDECORATION + ":" + ZKConstants.FONT_STYLE_UNDERLINE + ";" ;
		else if (zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_FONT_STYLE) && zkAttrValue.equalsIgnoreCase(ZKConstants.FONT_STYLE_ITALIC_UNDERLN))
			retStr = retStr + zkAttrName + ":" + ZKConstants.FONT_STYLE_ITALIC  + ";" + ZKConstants.NEW_LINE 
			+ ZKConstants.ATTR_TEXTDECORATION + ":" + ZKConstants.FONT_STYLE_UNDERLINE + ";" ;
		else if (zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_FONT_STYLE) && zkAttrValue.equalsIgnoreCase(ZKConstants.FONT_STYLE_BOLD_ITALIC_UNDERLN))
			retStr = retStr + zkAttrName + ":" + ZKConstants.FONT_STYLE_ITALIC  + ";" + ZKConstants.NEW_LINE 
			+ ZKConstants.ATTR_FONT_WEIGHT + ":" + ZKConstants.FONT_STYLE_BOLD + ";" + ZKConstants.NEW_LINE
			+ ZKConstants.ATTR_TEXTDECORATION + ":" + ZKConstants.FONT_STYLE_UNDERLINE + ";" ;
		else if(zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_IMAGEBACKGROUND_LAYOUT) && zkAttrValue.equalsIgnoreCase(ZKConstants.CENTER)){
			retStr = retStr + zkAttrName+":"+ zkAttrValue+ ";" +ZKConstants.NEW_LINE;
			retStr = retStr + ZKConstants.ATTR_NO_REPEAT+ ";";
		}else if(zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_IMAGEBACKGROUND_LAYOUT) && zkAttrValue.equalsIgnoreCase(ZKConstants.STRETCH)){
			retStr = retStr + ZKConstants.ATTR_BACKGROUND_SIZE + ":" + ZKConstants.STRETCH_VALUE + ";";
		}else if(zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_TEXTALIGN)){
			zkAttrValue = zkAttrValue.replaceAll(ZKConstants.ATTR_TOP, "");
			zkAttrValue = zkAttrValue.replaceAll(ZKConstants.ATTR_MIDDLE, "");
			zkAttrValue = zkAttrValue.replaceAll(ZKConstants.ATTR_BOTTOM, "");
			retStr = retStr + zkAttrName + ":" + zkAttrValue + ";";
		}else if(zkAttrName.equalsIgnoreCase(ZKConstants.STYLE)){
			String []styleParts = zkAttrValue.split(";");
			for(byte i = 0; i < styleParts.length; i++){
				retStr +=  styleParts[i] + ";";
				if(i < styleParts.length - 1){
					retStr += ZKConstants.NEW_LINE;
				}
			}
		}
		else
			retStr = retStr + zkAttrName + ":" + zkAttrValue + ";";
		return retStr;
	}
	
	/**
	 * @return
	 */
	public String endElementCSS() {
		String retStr = "";
		retStr = retStr + "}" + ZKConstants.NEW_LINE;
		return retStr;
	}

	/**
	 * @param elementId
	 * @param zkTagName
	 * @return
	 */
	public String addClassAttribute(String elementId, String zkTagName ) {
		String retStr = " ";
		
		/*
		 * css class added for following control.
		 * wasim 13-May-2009
		 * 
		 */
		
		if(!(/*zkTagName.equalsIgnoreCase(ZKConstants.MENU_BAR) ||*/ zkTagName.equalsIgnoreCase(ZKConstants.MENU) 
				||/* zkTagName.equalsIgnoreCase(ZKConstants.DATE_BOX) || zkTagName.equalsIgnoreCase(ZKConstants.COMBO_BOX) 
				|| zkTagName.equalsIgnoreCase(ZKConstants.GRID) || zkTagName.equalsIgnoreCase(ZKConstants.TREE) 
				|| zkTagName.equalsIgnoreCase(ZKConstants.LIST_BOX) || zkTagName.equalsIgnoreCase(ZKConstants.TABBOX)*/ 
				zkTagName.equalsIgnoreCase(ZKConstants.TAB) || zkTagName.equalsIgnoreCase(ZKConstants.MENU_ITEM) ))
		retStr = retStr + ZKConstants.STYLE_ATTR + "=\"" + elementId + "\"";
		return retStr;
		
	}
	
	//added method to add style in case of popup by pra on 15-may-2009
	public String addStyleForPopup(String cssCode)
	{
	  String style = "";
	  if(cssCode.trim().length() > 0){
		  style = ZKConstants.START_STYLE_POPUP_TAG+cssCode+ZKConstants.END_STYLE_POPUP_TAG;
	  }
	  //System.out.println("-----------------checking for this condition only-----------"+cssCode);
	  //return cssCode;
	  return style;
	}

	/**
	 * This method generate the custom tag attribute.
	 * @param xmlAttrName
	 * @param attrValue
	 * @return
	 */
	public String addCustomTagForValidation(String xmlAttrName,String attrValue)
	{
		String customStr ="";
		if(!attrValue.equalsIgnoreCase(ZKConstants.ANY))
		{
			customStr=ZKConstants.VALUE_TYPE_CUSTOM+xmlAttrName +"=\""+attrValue+ZKConstants.CUSTOME_END;
		}
		return customStr;
	}
}