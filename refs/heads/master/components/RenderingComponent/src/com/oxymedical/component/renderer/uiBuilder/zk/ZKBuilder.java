package com.oxymedical.component.renderer.uiBuilder.zk;


import java.io.File;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import java.util.List;


import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;


import com.oxymedical.component.renderer.Application;
import com.oxymedical.component.renderer.IUIBuilder;
import com.oxymedical.component.renderer.RendererComponent;
import com.oxymedical.component.renderer.application.modeler.IBaseModeler;
import com.oxymedical.component.renderer.constants.ZKConstants;

import com.oxymedical.component.renderer.exception.RendererComponentException;
import com.oxymedical.component.renderer.uiBuilder.zk.util.ZKBuilderUtility;
import com.oxymedical.core.ioutil.FileIO;
import com.oxymedical.core.xmlutil.XmlReader;


/**
 * This class is used to generate the User Interface of an Application in ZK
 * 
 */

public class ZKBuilder implements IUIBuilder
{
	private Application application;
	ZKBuilderUtility zkBuilderUtility = null;
	private XmlReader xmlReader = null;
	private String filePath = null;
	private String applicationFolderPath = null;
	private String applicationFileName = null;
	private FileIO fileWriter = null;
	private boolean isForm = false;
	private boolean isVertical = false;
	private boolean isHorizontal = false;
	private IBaseModeler baseModeler;
	private ArrayList<String> dataPatternArray = null;
	public static boolean zkRequest;
	public static String element_id = "";
	public static String winId = "";
	public static String dataPattern = "";
	public String bgColor = "";
	public String tagName = null;
	public String includeStr = "";
	public String formPatternId = "";
	public boolean isPopUp=false;
	public boolean isPlaceholderTagPresent = false;
	String customeStr =null;
	int i = 0;
	//changed for list to linkedhashmap by pra on 12 and 13 may 2009
	private LinkedHashMap<String,String> tabBGColor = new LinkedHashMap<String,String>();
	private List fileList = new ArrayList();
	
	public boolean isSchedular=false;
	public boolean isCurrentFormDefault = false;
	public String pageAlignment = ZKConstants.CONTAINER_ALIGN_NONE;
	

	/*
	 * this attribute is relevant only for tree that stores the information
	 * whether the tree has children
	 */
	private String isTreeLeaf = null;

	/**
	 * Constructor of the class ZKBuilder
	 */
	public ZKBuilder()
	{
		zkBuilderUtility = new ZKBuilderUtility();
		fileWriter = new FileIO();
		xmlReader = new XmlReader();
	}

	/**
	 * This method is used to start rendering the User Interface
	 * 
	 * @param HICData
	 * @return void
	 */
	public void renderUI() throws RendererComponentException
	{
		String appFileName;
		Document document = null;
		Document formPatternDoc = null;
		String defaultForm = "";

		applicationFolderPath = application.getApplicationFolderPath();
		applicationFileName = application.getApplicationFileName();

		if (null == applicationFolderPath
				|| applicationFolderPath.trim().equals("")) throw (new RendererComponentException(
				"Application Source not found"));
		else if (null == applicationFileName
				|| applicationFileName.trim().equals("")) throw (new RendererComponentException(
				"Application Source not found"));
		else
		{
			String applicationFile = applicationFolderPath
					+ applicationFileName;
			try
			{
				createFolders();
				copyImages();
				copyContent();
				copyAppletResources();
				copyVisualizerResources();
				copyResourcess();

				dataPatternArray = new ArrayList<String>();
				document = xmlReader.getDocumentFromPath(applicationFile);
				Element root = document.getRootElement();
				if (root.getName().trim().equalsIgnoreCase(
						ZKConstants.APP_ROOT_TAG_NAME))
				{
					String mainAppDocStr;
					mainAppDocStr = zkBuilderUtility.startApplication(application);
					appFileName = root.attributeValue(ZKConstants.ELEMENT_NAME);
					// List Form Patterns
					Element formPattern = root
							.element(ZKConstants.FORM_PATTERN_ROOT_TAG_NAME);
					List dataList = root.elements(ZKConstants.DATAPATTERN_NAME);
					List formPatternList = formPattern.elements();

					if (dataList.size() > 0)
					{
						Element dataRoot = (org.dom4j.Element) dataList.get(0);
						List dataSubLst = dataRoot
								.elements(ZKConstants.DATAPATTERN);
						for (int i = 0; i < dataSubLst.size(); i++)
						{
							org.dom4j.Element page = (org.dom4j.Element) dataSubLst
									.get(i);

							dataPatternArray.add(page
									.attributeValue(ZKConstants.ELEMENT_NAME));
						}
					}


					if (null != formPatternList && formPatternList.size() > 0)
					{
						int count = 0;
						for (int i = 0; i < formPatternList.size(); i++)
						{
							// intiailize
							isCurrentFormDefault = false;
							
							Element page = (Element) formPatternList.get(i);
							String formPatternXmlName = page
									.attributeValue(ZKConstants.ELEMENT_ID);
							if (null != page
									.attributeValue(ZKConstants.DEFAULT_ARG)
									&& page.attributeValue(
											ZKConstants.DEFAULT_ARG).equals(
											ZKConstants.TRUE))
							{
								defaultForm = page.attributeValue(
										ZKConstants.ELEMENT_NAME).trim();
								mainAppDocStr = mainAppDocStr
										+ zkBuilderUtility
												.includeDefaultFormPattern(formPatternXmlName);
								isCurrentFormDefault = true;
							}

							formPatternDoc 
								= zkBuilderUtility.getFormPatternXmlDoc(
										application, page.attributeValue(ZKConstants.ELEMENT_ID));
							// read mapFile xml 
							String mapFilePath = application.getApplicationFolderPath()+ZKConstants.FORM_PATTERN_FOLDER_NAME+ZKConstants.MAPFILE_NAME;
							renderPage(formPatternDoc,mapFilePath, false);

						}

						mainAppDocStr = mainAppDocStr
								+ zkBuilderUtility.endApplication(application);
						if (!(fileWriter.writeToFile(filePath.trim()
								+ ZKConstants.MAIN_FOLDER_NAME + "/",
								appFileName.trim() + ZKConstants.ZK_EXTN,
								mainAppDocStr)))
						{
							throw (new RendererComponentException(
									"Error writing lzx file"));
						}
						
						// Check for global themes and apply it
					 //updateCommonCss(application);
					}
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new RendererComponentException(e.getMessage());
			}
		}
	}

	/**
	 * Render each form pattern page
	 * 
	 * @param Document
	 * @return String
	 */

	public void renderPage(Document doc,String mapFilePath, boolean isDynamic) throws RendererComponentException
	{
		String renderedPage[] = new String[2];
		String fileName, baseWindowName;
		String pageStr = "", cssStr = "";
		dataPattern ="";
		String popUp="";

		// uiElement.setDocument(doc);
		Element root = doc.getRootElement();
		if (root.getName().trim().equalsIgnoreCase(
				ZKConstants.BASE_WINDOW_TAG_NAME))
		{
			fileName = root.attributeValue(ZKConstants.ELEMENT_ID);
			formPatternId =fileName;
			baseWindowName = root.attributeValue(ZKConstants.ELEMENT_NAME);
				
			zkBuilderUtility.setConfigurationXml(ZKConstants.CONFIG_FILE_PATH);
		
			zkBuilderUtility.setMapFileXml(mapFilePath);
			
			pageAlignment = root.attributeValue(ZKConstants.ALIGN);

			// start tag for ZK
			pageStr = pageStr + zkBuilderUtility.startApplication(application);
			
			//pageStr = pageStr + ZKConstants.CSS_META_IE + ZKConstants.NEW_LINE;

			//change done for popup so that style tag is not generated on 12 and 13 may,2009 by pra
			popUp = root.attributeValue("popup");	
			if(popUp.equalsIgnoreCase(ZKConstants.FALSE))
			{
				// add style class
				pageStr = pageStr + zkBuilderUtility.addStyleContent(fileName);
				pageStr = pageStr + zkBuilderUtility.addCommonStyleContent();
			}
			else if(popUp.equalsIgnoreCase(ZKConstants.TRUE))
			{ 
				isPopUp = true;
			}
			pageStr = pageStr + zkBuilderUtility.addZscriptSrc();
			if (isLoginRequiredForForm(doc)) pageStr = pageStr + zkBuilderUtility.addInitZscriptSrc();

			// change done to generate page tag in all form pattern on 12 and 13 may,2009 by pra.
			pageStr=pageStr+zkBuilderUtility.addPageTag(fileName);
			renderedPage = renderElement(root, true);

			pageStr = pageStr + renderedPage[0];
			
			// For adding form pattern and data pattern for arguments. Only
			// valid for those form pattern that contain PlaceHolder tag
			// Added by HS dated 31-05-2009
			if (isPlaceholderTagPresent)
			{
				pageStr = pageStr.replaceAll(ZKConstants.STRING_FORMPATTERN_TO_REPLACE, this.formPatternId);
				pageStr = pageStr.replaceAll(ZKConstants.STRING_DATAPATTERN_TO_REPLACE, ZKBuilder.dataPattern);
			}
			
			cssStr = cssStr + renderedPage[1];

			// end tag for ZK
			pageStr = pageStr + zkBuilderUtility.endApplication(application);
			//for the template
			createTemplate();
			//this condition is for the schedular component.
			//condition added by pra on june 24 2009
			if(isSchedular)
			{
			  pageStr=pageStr+ZKConstants.NEW_LINE+ZKConstants.TOOLBAR_COMPONENT_ARROW+ZKConstants.NEW_LINE+ZKConstants.TOOLBAR_COMPONET_TAB;
			  isSchedular=false;
			  updateSchedularContents();
			}
			isPopUp = false;
			isPlaceholderTagPresent = false;
			
			if(isDynamic){
				if (!(fileWriter.writeToFile(filePath+"/", ZKConstants.ZK_FOLDER_NAME + "/" + fileName + ZKConstants.ZK_EXTN, pageStr)))
				{
					System.out.println("filePath-is dynamic true-"+filePath);
					
				}
				if (!(fileWriter.writeToFile(filePath+"/", ZKConstants.CSS_FOLDER_NAME + "/" + fileName + ZKConstants.CSS_EXTN, cssStr)))
				{
					//RendererComponent.logger.log(0,"Error writing to file");
				}
				
				System.out.println("mapFilePath--"+mapFilePath);
				System.out.println("formPatternId--"+formPatternId);
				FileOutputStream fos; 
			    DataOutputStream dos;
			    try
			    {
			      File file=null;
			      //if("LinearityAnalysis".equals(formPatternId))
			      {
			    	//  file= new File("C:/glassfish/domains/domain1/applications/j2ee-modules/LIMS/zul/linearanalysiszul.zul");
			      }
			      
			      {
			    	  file= new File("C:/glassfish/domains/domain1/applications/j2ee-modules/LIMS/zul/LineritySpecificationZule.zul");
			      }
			      fos = new FileOutputStream(file);
			      dos=new DataOutputStream(fos);
			      dos.writeBytes(pageStr);

			    } catch (IOException e) {
			      e.printStackTrace();
			    }


			}
			else{
				if (!(fileWriter.writeToFile(filePath
						+ ZKConstants.MAIN_FOLDER_NAME + "/",
						ZKConstants.ZK_FOLDER_NAME + "/" + fileName
								+ ZKConstants.ZK_EXTN, pageStr)))
				{
				}
				if (!(fileWriter.writeToFile(filePath
						+ ZKConstants.MAIN_FOLDER_NAME + "/",
						ZKConstants.CSS_FOLDER_NAME + "/" + fileName
								+ ZKConstants.CSS_EXTN, cssStr)))
				{
					//RendererComponent.logger.log(0,"Error writing to file");
				}
			}
		}
		else
		{
		//	RendererComponent.logger.log(0,"No basewindow tag found in the form pattern ");
			throw (new RendererComponentException(
					"Root element missing for the UI Form pattern"));
		}

	}
	
	private boolean isPageAlligned(Element xmlNode){
		boolean retVal = false;
		if(xmlNode.getParent() != null){
			if(xmlNode.getParent().getName().equalsIgnoreCase(ZKConstants.BASE_WINDOW_TAG_NAME)){
				//System.out.println("Child of main form: " + xmlNode.getName());
				if((!xmlNode.getName().equalsIgnoreCase(ZKConstants.EVENTS)) && !isPopUp){
					if((!pageAlignment.equals(ZKConstants.CONTAINER_ALIGN_NONE))){
						retVal = true;
					}
				}
			}
		}
		return retVal;
	}

	/**
	 * Renders each element of the page
	 * 
	 * Recursive function to render every element of Form Pattern
	 * 
	 * @param Element
	 * @param boolean
	 * @return String
	 * */

	
	public String[] renderElement(Element xmlNode, boolean renderSubElements)
			throws RendererComponentException
	{

		
		String renderedPage[] = new String[2];
		String subElement[] = null;
		String zkCode = ZKConstants.EMPTY_STRING;
		String cssCode = ZKConstants.EMPTY_STRING;
		String childElementName = ZKConstants.EMPTY_STRING;
		String zkTagName = "", elementId;
		Element childElement = null;
		boolean tabPage = false;
		String xmlTagName = xmlNode.getName();
		String strValue = ZKConstants.EMPTY_STRING;
		customeStr = null;
		try
		{
			if(isPageAlligned(xmlNode)){
				String centerDivId = ZKConstants.CENTER_DIV_ID_PREFIX + Double.toString(Math.random()).replace("0.", "");
				zkCode = zkCode + ZKConstants.START_TAG + ZKConstants.DIV + ZKConstants.EMPTY_STRING + ZKConstants.ID + "=\"" + centerDivId + "\"" + ZKConstants.EMPTY_STRING + ZKConstants.ALIGN + "=\"" + pageAlignment + "\"" + ZKConstants.END_TAG + ZKConstants.NEW_LINE;
			}
			
			/*
			 * Following code has been commented for Placeholder. now it is useless
			 * Changes by wasim , 21-May-2009
			 * 
			 */
			/*if (!(xmlTagName.equals(ZKConstants.PLACEHOLDER)))
				zkTagName = zkBuilderUtility.findTag(xmlTagName);*/
			
			
			zkTagName = zkBuilderUtility.findTag(xmlTagName);
			
			//converting listbox to grid
			if(zkTagName.equalsIgnoreCase(ZKConstants.LIST_BOX) || zkTagName.equalsIgnoreCase(ZKConstants.GRID_LISTHEAD) || zkTagName.equalsIgnoreCase(ZKConstants.GRID_LISTHEADER)){
				if(zkTagName.equalsIgnoreCase(ZKConstants.LIST_BOX) && (xmlNode.attributeValue(ZKConstants.GRID_TYPE) != null && xmlNode.attributeValue(ZKConstants.GRID_TYPE).equalsIgnoreCase(ZKConstants.GRID))){
					zkTagName = ZKConstants.GRID;
				}else if(zkTagName.equalsIgnoreCase(ZKConstants.GRID_LISTHEAD) && (xmlNode.getParent() != null && xmlNode.getParent().attributeValue(ZKConstants.GRID_TYPE) != null && xmlNode.getParent().attributeValue(ZKConstants.GRID_TYPE).equalsIgnoreCase(ZKConstants.GRID))){
					zkTagName = ZKConstants.COLUMNS;
				}else if(zkTagName.equalsIgnoreCase(ZKConstants.GRID_LISTHEADER) && (xmlNode.getParent() != null && xmlNode.getParent().getParent() != null && xmlNode.getParent().getParent().attributeValue(ZKConstants.GRID_TYPE) != null && xmlNode.getParent().getParent().attributeValue(ZKConstants.GRID_TYPE).equalsIgnoreCase(ZKConstants.GRID))){
					zkTagName = ZKConstants.COLUMN;
				}
			}
			
			if(zkTagName.equalsIgnoreCase(ZKConstants.MENU)){
				if(xmlNode.getParent() != null && xmlNode.getParent().getName().equalsIgnoreCase(ZKConstants.MENU_BAR)){
					String renderAsMenuItem = xmlNode.attributeValue(ZKConstants.RENDER_AS_MENUITEM);
					if(renderAsMenuItem != null && renderAsMenuItem.equalsIgnoreCase(ZKConstants.TRUE)){
						zkTagName = ZKConstants.MENU_ITEM;
					}
				}
			}else if(zkTagName.equalsIgnoreCase(ZKConstants.DIV)){
				String renderAsIFrame = xmlNode.attributeValue(ZKConstants.RENDER_AS_IFRAME);
				if(renderAsIFrame != null && renderAsIFrame.equalsIgnoreCase(ZKConstants.TRUE)){
					zkTagName = ZKConstants.IFRAME;
				}
			}
			tagName = zkTagName;
			String validationValue = xmlNode.attributeValue("validation");
			
			// code added for the hbox and vboox  by pra.
			
			if(xmlTagName.equalsIgnoreCase(ZKConstants.FRAME))
			 {
				 String value=xmlNode.attributeValue(ZKConstants.BOX_ALIGNMENT);
				 //check for null always
				 if(value!=null)
				 {
					 if(value.equals(ZKConstants.HORIZONTAL))
					 {
						 zkTagName=ZKConstants.HBOX;
					 }
					 else  if(value.equals(ZKConstants.VERTICAL))
					 {
						 zkTagName=ZKConstants.VBOX;
					 }
				 }
				 
			 }
			//adding codition for Panel Children for Hbox and Vbox
			
			
			
			if (null != zkTagName && !(zkTagName.trim().equals("")))
			{

				if (null != validationValue
						&& xmlTagName.equalsIgnoreCase(ZKConstants.TEXTBOX)
						&& (validationValue.equalsIgnoreCase("positive") || validationValue
								.equalsIgnoreCase("negative")))
				{
					zkTagName = "intbox";
					zkCode = zkCode
							+ zkBuilderUtility.startElementTag(zkTagName);
				}
				else
					zkCode = zkCode
							+ zkBuilderUtility.startElementTag(zkTagName);
				elementId = xmlNode.attributeValue(ZKConstants.ELEMENT_ID);
				if (zkTagName.equalsIgnoreCase(ZKConstants.WIN_ID)) winId = elementId;
				// render all the attributes of the tag

				
				String[] resultStr = renderAttribute(xmlNode, xmlTagName,
						isTreeLeaf, zkTagName);			
				
				zkCode = zkCode + resultStr[0];
				cssCode = cssCode + resultStr[1];
				
				if(xmlNode.getName().equalsIgnoreCase(ZKConstants.TREE)){
					
					zkCode = zkCode + ZKConstants.START_TAG
					         + ZKConstants.TREE_COLS +" "+ ZKConstants.ATTR_SIZABLE + "=" +"\"" +ZKConstants.FALSE +"\"" + ZKConstants.END_TAG+ ZKConstants.NEW_LINE +"   "
					         + ZKConstants.START_TAG + ZKConstants.TREE_COL +" "+ZKConstants.ATTR_HFLEX + "=" +"\"" + ZKConstants.MIN +"\""+ ZKConstants.END_TAG+ ZKConstants.NEW_LINE+"    " 
					         + ZKConstants.TREE_COL_END_TAG_CLOSE + ZKConstants.NEW_LINE  
					         + ZKConstants.TREE_COLS_END_TAG_CLOSE + ZKConstants.NEW_LINE ;
					
					zkCode = zkCode + ZKConstants.TREECHILDREN_START_TAG + ZKConstants.NEW_LINE;
				}else if(xmlNode.getName().equalsIgnoreCase(ZKConstants.TREE_NODE)){
					String istreeLeaf = xmlNode.attributeValue("isleaf");
					if (istreeLeaf == null){
						zkCode = zkCode + ZKConstants.TREECHILDREN_START_TAG + ZKConstants.NEW_LINE;
					}
				}
				
				if(zkTagName.equals("zscript") && xmlNode.getName().equals("zscript"))
				{
//					System.out.println("xmlNode.getText() = "+xmlNode.getText());
					zkCode = zkCode + xmlNode.getText();
				}
				// Adding custom attributes for formpattern and datapattern for popup form patterns.
				// Added by HS on 31-05-2009
				if ((zkTagName.equalsIgnoreCase(ZKConstants.WIN_ID)) && (isPopUp))
				{
					zkCode = zkCode + ZKConstants.NEW_LINE + ZKConstants.CUSTOM_ATT_FOR_POPUP + ZKConstants.NEW_LINE;
				}
				
				//removing the css code for columns, column, listhead and listheader
				if(zkTagName.equalsIgnoreCase(ZKConstants.COLUMNS) && (xmlNode.getParent() != null && xmlNode.getParent().attributeValue(ZKConstants.GRID_TYPE) != null && xmlNode.getParent().attributeValue(ZKConstants.GRID_TYPE).equalsIgnoreCase(ZKConstants.GRID))){
					cssCode = "";
				}else if(zkTagName.equalsIgnoreCase(ZKConstants.COLUMN) && (xmlNode.getParent() != null && xmlNode.getParent().getParent() != null && xmlNode.getParent().getParent().attributeValue(ZKConstants.GRID_TYPE) != null && xmlNode.getParent().getParent().attributeValue(ZKConstants.GRID_TYPE).equalsIgnoreCase(ZKConstants.GRID))){
					cssCode = "";
				}else if(zkTagName.equalsIgnoreCase(ZKConstants.GRID_LISTHEAD) && (xmlNode.getParent() != null && xmlNode.getParent().attributeValue(ZKConstants.GRID_TYPE) != null && xmlNode.getParent().attributeValue(ZKConstants.GRID_TYPE).equalsIgnoreCase(ZKConstants.LIST_BOX))){
					cssCode = "";
				}else if(zkTagName.equalsIgnoreCase(ZKConstants.GRID_LISTHEADER) && (xmlNode.getParent() != null && xmlNode.getParent().getParent() != null && xmlNode.getParent().getParent().attributeValue(ZKConstants.GRID_TYPE) != null && xmlNode.getParent().getParent().attributeValue(ZKConstants.GRID_TYPE).equalsIgnoreCase(ZKConstants.LIST_BOX))){
					cssCode = "";
				}
				
				// opening tag end and css for this element ends
				else if (null != elementId && !isPopUp) 
				{
					if(	(	(xmlTagName.equalsIgnoreCase(ZKConstants.DCOLUMN))||
							(xmlTagName.equalsIgnoreCase(ZKConstants.DROWS))||
							(xmlTagName.equalsIgnoreCase(ZKConstants.DROW))||
							(xmlTagName.equalsIgnoreCase(ZKConstants.AUXHEAD))||
							(xmlTagName.equalsIgnoreCase(ZKConstants.AUXHEADER))||
							(xmlTagName.equalsIgnoreCase(ZKConstants.COLUMNS))||
							(xmlTagName.equalsIgnoreCase(ZKConstants.TEXTBOX))||
							(xmlTagName.equalsIgnoreCase(ZKConstants.GRID_LABEL))
						)
						&& (   ((ZKConstants.DGRID.equalsIgnoreCase(xmlNode.getParent().getName()))
							||(ZKConstants.DROWS.equalsIgnoreCase((xmlNode.getParent().getName())))
							||(ZKConstants.AUXHEAD.equalsIgnoreCase((xmlNode.getParent().getName())))
							||(ZKConstants.COLUMNS.equalsIgnoreCase((xmlNode.getParent().getName())))
							||(ZKConstants.DROW.equalsIgnoreCase((xmlNode.getParent().getName()))))
							)
						)	
							
					{
						cssCode = "";
					}
					else{
						cssCode = cssCode + zkBuilderUtility.endElementCSS();
					}
				}
				//}
				// in case of grid an extra rows tag is required by ZK to
				// contain all the rows
				//changes done on 12/4/2011 at 6 PM applying one more condition on Grid && !"DGrid".equals(xmlTagName)
				if (zkTagName.equals(ZKConstants.GRID) && !"DGrid".equals(xmlTagName) && xmlNode.attributeValue(ZKConstants.GRID_TYPE) == null)
				{
					zkCode = zkCode + ZKConstants.ROWS_START_TAG
							+ ZKConstants.NEW_LINE;
				}
				// in case of multitab an extra tabs is required by ZK
				if (zkTagName.equals(ZKConstants.TABBOX))
				{
					zkCode = zkCode + ZKConstants.TABS_START_TAG
							+ ZKConstants.NEW_LINE;
				}
				
				if(customeStr != null){
					zkCode = zkCode + customeStr + ZKConstants.NEW_LINE;
					customeStr = null;
				}
				
				// for menu need to add menupopup tag after menu
				if (zkTagName.equals(ZKConstants.MENU)){
					zkCode = zkCode + ZKConstants.MENUPOPUP_START_TAG + ZKConstants.NEW_LINE;
				}
				
				List subElementList;
				if (renderSubElements){
					subElementList = xmlNode.elements();
					for (int count = 0; count < subElementList.size(); count++){
						childElement = (Element) subElementList.get(count);
						childElementName = childElement.getName();
						if(childElementName.equalsIgnoreCase(ZKConstants.LABEL) && 
								(childElement.getParent() != null && childElement.getParent().getName().equalsIgnoreCase(ZKConstants.GRID_COLUMN)) &&
								(childElement.getParent().getParent() != null && childElement.getParent().getParent().getName().equalsIgnoreCase(ZKConstants.GRID_ROW))){
							continue;
						}
						// attribute relevant only for tree
						isTreeLeaf = childElement
								.attributeValue(ZKConstants.TREE_LEAF_ATTR);
						tabPage = zkTagName.equals(ZKConstants.TAB);
						subElement = renderElement(childElement, true);
						String tabNode = xmlNode.getName();
						// this is required in case of multitab to retain the
						// values in the tabpannels in ZK
						if(tabNode.equals(ZKConstants.TAB_PAGE) && tabPage == true){
							strValue = strValue + subElement[0];
							tabPage = false;
						}else{
							zkCode = zkCode + subElement[0];
						}
						cssCode = cssCode + subElement[1];
					}
				}
			}
			//added condition in case of Schedular as some code in case of schedular is added by default in schdeular for the toolbar
			//condition added by pra on 24-june-2009.
			  if(zkTagName.equalsIgnoreCase(ZKConstants.SCHEDULAR))
			  {
				  String id=xmlNode.attributeValue(ZKConstants.ID);
				  String zkContents="";
				  zkContents = zkContents + fileWriter.readFromFile(ZKConstants.TOOLBAR_SCH_FOLDER_PATH, ZKConstants.TOOLBAR_FILE_NAME);
				  zkContents=zkContents.replace("calendarId", id);
				  zkCode=zkCode+ZKConstants.NEW_LINE+zkContents;
				  String cssContents=fileWriter.readFromFile(ZKConstants.TOOLBAR_SCH_FOLDER_PATH, ZKConstants.TOOLBAR_CSS);
				  cssCode=cssCode+ZKConstants.NEW_LINE+cssContents;
				  isSchedular=true;
			  }
			if (zkTagName.equalsIgnoreCase(ZKConstants.DIV) && isForm) zkCode = zkCode
					+ ZKConstants.END_FORM_TAG;
			if (null != validationValue
					&& xmlTagName.equalsIgnoreCase(ZKConstants.TEXTBOX)
					&& (validationValue.equalsIgnoreCase("positive") || validationValue
							.equalsIgnoreCase("negative")))
			{
				zkTagName = "intbox";
				zkCode = zkCode	+ zkBuilderUtility.endElementTag(zkTagName, isTreeLeaf, strValue, tabBGColor,xmlNode);
				strValue ="";
			}else{
				zkCode = zkCode + zkBuilderUtility.endElementTag(zkTagName, isTreeLeaf, strValue, tabBGColor,xmlNode);
				if(isPageAlligned(xmlNode)){
					zkCode = zkCode + ZKConstants.END_DIV_TAG + ZKConstants.NEW_LINE;
				}
				strValue="";
			}
		
			renderedPage[0] = zkCode;
			renderedPage[1] = cssCode;
			isTreeLeaf = null;
		
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw (new RendererComponentException(exp.getMessage()));
		}
		return renderedPage;
	}

	/**
	 * Render attributes of each element
	 * 
	 * @param xmlNode
	 * @param xmlElementName
	 * @param isTreeLeaf
	 *            - required for tree nodes
	 * @param zkTagName
	 *            - required for tree nodes
	 * @return
	 * @throws RendererComponentException
	 */
	public String[] renderAttribute(Element xmlNode, String xmlElementName,
			String isTreeLeaf, String zkTagName)
			throws RendererComponentException
	{
		String resultStr[] = new String[2];

		try
		{
			Attribute attr = null;
			String xmlAttrName, zkAttrName = "", zkAttrName1, zkName, zkAttrValue, elementName, elementId;
			String zktextboxValue = null;
			int multilineRows = 0;
			Boolean multiline = false;
			Boolean isFrame = false;
			String formId = null;
			List multilineText = null;
			String zkCode = "", cssCode = "";
			String attrValue = "";
			String radioSelectedID = null;
			String radioSelectedMethod = null;
			elementName = xmlNode.attributeValue(ZKConstants.ELEMENT_NAME);
			elementId = xmlNode.attributeValue(ZKConstants.ELEMENT_ID);
			
			// in case of id = null, no need to create a class in stylesheet
			if (null != elementId && !isPopUp)
			{
				
				cssCode = cssCode + zkBuilderUtility.startElementCSS(elementId);
				zkCode = zkCode
						+ zkBuilderUtility.addClassAttribute(elementId,
								zkTagName);
			
			}

			// Attributes => styles in zk
			// header sizable code added in grid and list
		  	if (null != xmlNode.getParent()
					&& (xmlNode.getParent().getName().equalsIgnoreCase(
							ZKConstants.GRID) || xmlNode.getParent().getName()
							.equalsIgnoreCase(ZKConstants.LIST_BOX))
					&& (zkTagName.equalsIgnoreCase(ZKConstants.GRID_LISTHEAD) || zkTagName.equalsIgnoreCase(ZKConstants.COLUMNS))) {
				zkCode = " " + ZKConstants.LISTHEAD_GRID_SIZEABLE;
				
				
			}
			if (null != xmlNode.getParent()
					&& xmlNode.getParent().getName().equalsIgnoreCase(
							ZKConstants.GRID_ROW)
					&& (zkTagName.equalsIgnoreCase(ZKConstants.GRID_LISTHEADER) || zkTagName.equalsIgnoreCase(ZKConstants.COLUMN))) {
				if (null != xmlNode.getParent().getParent()
						&& (xmlNode.getParent().getParent().getName()
								.equalsIgnoreCase(ZKConstants.GRID) || xmlNode
								.getParent().getName().equalsIgnoreCase(
										ZKConstants.LIST_BOX))) {
					zkCode = "";
					
				}
				
			}
			if (null != xmlNode.getParent()
					&& xmlNode.getParent().getName().equalsIgnoreCase(
							ZKConstants.GRID_COLUMN)
					&& zkTagName.equalsIgnoreCase(ZKConstants.GRID_LABEL)) {
				if (null != xmlNode.getParent().getParent().getParent()
						&& (xmlNode.getParent().getParent().getParent()
								.getName().equalsIgnoreCase(ZKConstants.GRID) || xmlNode
								.getParent().getParent().getName()
								.equalsIgnoreCase(ZKConstants.LIST_BOX))) {
					zkCode = "";
					
				}
				
			}
			
			
			List attributeList = xmlNode.attributes();
			if (xmlElementName.equalsIgnoreCase(ZKConstants.FRAME))
			{
				isFrame = true;
				Attribute formElement = xmlNode.attribute(ZKConstants.ISFORM);
				if (formElement != null)
				{
					String makeForm = formElement.getValue();
					if (makeForm != null)
					{
						if (makeForm.equalsIgnoreCase(ZKConstants.TRUE))

						{
							formId = xmlNode.attribute(ZKConstants.ID)
									.getValue();
							isForm = true;
						}
					}
				}
			}
			String mulValue = xmlNode.attributeValue(ZKConstants.MULTILINE);
			if (mulValue != null){
				if(xmlElementName.equalsIgnoreCase(ZKConstants.LABEL)){
					zkCode = zkCode + " " + zkBuilderUtility.addAttribute(xmlNode, ZKConstants.MULTILINE, mulValue);
				}else{
					if(mulValue.equalsIgnoreCase(ZKConstants.TRUE)){
						multiline = true;
					}
				}
			}
			
			//adding the position as a style attribute to a grid if not pop-up
			if(xmlNode.getName().equalsIgnoreCase(ZKConstants.GRID) && (xmlNode.attributeValue(ZKConstants.GRID_TYPE) != null && xmlNode.attributeValue(ZKConstants.GRID_TYPE).equalsIgnoreCase(ZKConstants.GRID))){
				String positionAttrVal = xmlNode.attributeValue(ZKConstants.POSITION);
				String styleStr = zkBuilderUtility.addStyles(ZKConstants.POSITION, positionAttrVal);
				zkCode = zkCode + " " + ZKConstants.START_STYLE_TAG1 + styleStr + ZKConstants.END_STYLE_TAG1;
			}
			
			//adding the style=text-align attribute for a listheader
			//getting this value from the child label attribute in the xml
			if(xmlNode.getName().equalsIgnoreCase(ZKConstants.COLUMN) && (zkTagName.equalsIgnoreCase(ZKConstants.GRID_LISTHEADER) || zkTagName.equalsIgnoreCase(ZKConstants.COLUMN))){
				if(xmlNode.getParent() != null && xmlNode.getParent().getName().equalsIgnoreCase(ZKConstants.GRID_ROW)){
					if(xmlNode.getParent().getParent() != null && xmlNode.getParent().getParent().getName().equalsIgnoreCase(ZKConstants.GRID)){
						//adding the sortAscending and sortDescending to a ListHeader
						String sortAsc = xmlNode.attributeValue(ZKConstants.SORT_ASC);
						String sortDesc = xmlNode.attributeValue(ZKConstants.SORT_DESC);
						if(sortAsc != null && sortAsc.length() > 0){
							zkCode = zkCode + " " + zkBuilderUtility.addAttribute(xmlNode, ZKConstants.SORT_ASC, sortAsc);
						}
						if(sortDesc != null && sortDesc.length() > 0){
							zkCode = zkCode + " " + zkBuilderUtility.addAttribute(xmlNode, ZKConstants.SORT_DESC, sortDesc);
						}
						
						if(xmlNode.elements().size() > 0){
							String txt = ((Element)xmlNode.elements().get(0)).attributeValue(ZKConstants.TEXT);
							if(txt != null && txt.length() > 0){
								zkCode = zkCode + " " + ZKConstants.LABEL.toLowerCase() + "=\"" + txt + "\"";
							}
							String txtAlign = ((Element)xmlNode.elements().get(0)).attributeValue(ZKConstants.TEXTALIGN);
							String fontSize = ((Element)xmlNode.elements().get(0)).attributeValue(ZKConstants.XML_FONT_SIZE);
							String fontFamily = ((Element)xmlNode.elements().get(0)).attributeValue(ZKConstants.XML_FONT_FAMILY);
							String fontStyle = ((Element)xmlNode.elements().get(0)).attributeValue(ZKConstants.XML_FONT_STYLE);
							String styleStr = "";
							String useDefaultColor = xmlNode.getParent().getParent().attributeValue(ZKConstants.USE_DEFAULT_COLOR);
							if(fontSize != null && fontSize.length() > 0){
								styleStr += ZKConstants.ATTR_FONT_SIZE + ":" + fontSize + ";";
							}
							if(fontFamily != null && fontFamily.length() > 0){
								styleStr += ZKConstants.ATTR_FONT_FAMILY + ":" + fontFamily + ";";
							}
							if(fontStyle != null && fontStyle.length() > 0){
								if(fontStyle.equalsIgnoreCase(ZKConstants.FONT_STYLE_BOLD_ITALIC)){
									//fontStyle = ZKConstants.FONT_STYLE_ITALIC;
									styleStr += ZKConstants.ATTR_FONT_STYLE + ":" + ZKConstants.FONT_STYLE_ITALIC + ";";
									styleStr += ZKConstants.ATTR_FONT_WEIGHT + ":" + ZKConstants.FONT_STYLE_BOLD + ";";
								}else if(fontStyle.equalsIgnoreCase(ZKConstants.FONT_STYLE_BOLD)){
									styleStr += ZKConstants.ATTR_FONT_WEIGHT + ":" + ZKConstants.FONT_STYLE_BOLD + ";";
								}
							}
							if(txtAlign != null && txtAlign.length() > 0){
								txtAlign = txtAlign.replaceAll(ZKConstants.ATTR_TOP, "");
								txtAlign = txtAlign.replaceAll(ZKConstants.ATTR_MIDDLE, "");
								txtAlign = txtAlign.replaceAll(ZKConstants.ATTR_BOTTOM, "");
								styleStr += ZKConstants.ATTR_TEXTALIGN + ":" + txtAlign + ";";
							}
							if(useDefaultColor != null && useDefaultColor.equalsIgnoreCase(ZKConstants.FALSE)){
								String bgColHeadColor = ((Element)xmlNode.elements().get(0)).attributeValue(ZKConstants.BACKCOLOR);
								String foreColor = ((Element)xmlNode.elements().get(0)).attributeValue(ZKConstants.FORECOLOR);
								if(bgColHeadColor != null && bgColHeadColor.length() > 0){
									styleStr += ZKConstants.ATTR_BACKGROUND + ":" + bgColHeadColor + ";";
								}
								if(foreColor != null && foreColor.length() > 0){
									styleStr += ZKConstants.ATTR_COLOR + ":" + foreColor + ";";
								}
							}
							styleStr += ZKConstants.ATTR_WHITESPACE + ":" + ZKConstants.CSS_STYLE_NORMAL + ";";
							zkCode = zkCode + " " + ZKConstants.START_STYLE_TAG1 + styleStr + ZKConstants.END_STYLE_TAG1;
						}
					}
				}
			}
			
			//adding family, size and weight of font for check-box not in pop-up
			if(isPopUp == false && xmlElementName.equalsIgnoreCase(ZKConstants.CHECK_BOX)){
				String fontSize = xmlNode.attributeValue(ZKConstants.XML_FONT_SIZE);
				String fontFamily = xmlNode.attributeValue(ZKConstants.XML_FONT_FAMILY);
				String fontStyle = xmlNode.attributeValue(ZKConstants.XML_FONT_STYLE);
				String styleStr = "";
				if(fontSize != null && fontSize.length() > 0){
					styleStr += ZKConstants.ATTR_FONT_SIZE + ":" + fontSize + ";";
				}
				if(fontFamily != null && fontFamily.length() > 0){
					styleStr += ZKConstants.ATTR_FONT_FAMILY + ":" + fontFamily + ";";
				}
				if(fontStyle != null && fontStyle.length() > 0){
					if(fontStyle.equalsIgnoreCase(ZKConstants.FONT_STYLE_BOLD_ITALIC)){
						styleStr += ZKConstants.ATTR_FONT_STYLE + ":" + ZKConstants.FONT_STYLE_ITALIC + ";";
						styleStr += ZKConstants.ATTR_FONT_WEIGHT + ":" + ZKConstants.FONT_STYLE_BOLD + ";";
					}else{
						styleStr += ZKConstants.ATTR_FONT_STYLE + ":" + fontStyle + ";";
					}
				}
				if(styleStr.length() > 0){
					zkCode = zkCode + " " + ZKConstants.START_STYLE_TAG1 + styleStr + ZKConstants.END_STYLE_TAG1;
				}
			}

			zkBuilderUtility.isConstraints =false;
			for (int count = 0; count < attributeList.size(); count++)
			{
				attr = (Attribute) attributeList.get(count);
				xmlAttrName = attr.getName().trim();
				zkAttrValue = attr.getValue().trim();
				zkAttrValue = zkAttrValue.replace('\\', '/');
				zkAttrValue = zkAttrValue.replaceAll("&", "&amp;");
				zkAttrValue = zkAttrValue.replaceAll("<", "&lt;");
				zkAttrValue = zkAttrValue.replaceAll(">", "&gt;");
				zkAttrValue = zkAttrValue.replaceAll("\"", "&quot;");
				zkAttrValue = zkAttrValue.replaceAll("\'", "&apos;");
				
				//replacing the \r and \n in the text of a label with corresponding xml values
				if(xmlElementName.equalsIgnoreCase(ZKConstants.LABEL) && xmlAttrName.equalsIgnoreCase(ZKConstants.TEXT)){
					if(xmlNode.attributeValue(ZKConstants.MULTILINE) != null && xmlNode.attributeValue(ZKConstants.MULTILINE).equalsIgnoreCase(ZKConstants.TRUE)){
						zkAttrValue = zkAttrValue.replaceAll("\r", "&#xD;");
						zkAttrValue = zkAttrValue.replaceAll("\n", "&#xA;");
					}
				}
				
				//filtering out un-required attributes for grid, columns and column
				if(zkTagName.equalsIgnoreCase(ZKConstants.GRID) && (xmlNode.attributeValue(ZKConstants.GRID_TYPE) != null && xmlNode.attributeValue(ZKConstants.GRID_TYPE).equalsIgnoreCase(ZKConstants.GRID))){
					if(xmlAttrName.equalsIgnoreCase(ZKConstants.MULTISELECT) || xmlAttrName.equalsIgnoreCase(ZKConstants.CHECKMARK)){
						continue;
					}
				}else if(zkTagName.equalsIgnoreCase(ZKConstants.COLUMNS) && (xmlNode.getParent() != null && xmlNode.getParent().attributeValue(ZKConstants.GRID_TYPE) != null && xmlNode.getParent().attributeValue(ZKConstants.GRID_TYPE).equalsIgnoreCase(ZKConstants.GRID))){
					if(!(xmlAttrName.equalsIgnoreCase(ZKConstants.HEIGHT))){
						continue;
					}
				}else if(zkTagName.equalsIgnoreCase(ZKConstants.COLUMN) && (xmlNode.getParent() != null && xmlNode.getParent().getParent() != null && xmlNode.getParent().getParent().attributeValue(ZKConstants.GRID_TYPE) != null && xmlNode.getParent().getParent().attributeValue(ZKConstants.GRID_TYPE).equalsIgnoreCase(ZKConstants.GRID))){
					if(!(xmlAttrName.equalsIgnoreCase(ZKConstants.WIDTH))){
						continue;
					}
				}
			
				if(xmlElementName.equalsIgnoreCase(ZKConstants.TIME_PICKER)&&(xmlAttrName.equalsIgnoreCase(ZKConstants.WIDTH)||xmlAttrName.equalsIgnoreCase(ZKConstants.HEIGHT)))
				{
					//dont add width and height property in timebox. There is usability issue
					continue;
				}
				
				// Enabling Width for date picker. Added by HS 29-05-09
				if(xmlElementName.equalsIgnoreCase(ZKConstants.DATE_PICKER)&& ( /*xmlAttrName.equalsIgnoreCase(ZKConstants.WIDTH)||*/xmlAttrName.equalsIgnoreCase(ZKConstants.HEIGHT)))
				{
					//dont add width and height property in datebox. There is usability issue
					continue;
				}
				
				if(xmlAttrName.equalsIgnoreCase(ZKConstants.DATAPATTERN))
				{
					dataPattern = zkAttrValue;
				
					
				}
				/*
				 * PlaceHolder code added by wasim 13-May-2009
				 * 
				 */
				
				if(xmlElementName.equalsIgnoreCase(ZKConstants.PLACE_HOLDER) && xmlAttrName.equalsIgnoreCase(ZKConstants.REFERENCE))
				{
					zkAttrValue = zkAttrValue+ZKConstants.ZUL_EXT+ZKConstants.ARGUMENT_LIST_FOR_POPUP;
					isPlaceholderTagPresent = true;
				}
			
				/* added check for postion relative by pra on 14may 2009*/
				if(xmlNode.attributeValue(ZKConstants.POSITION)!=null && xmlNode.attributeValue(ZKConstants.POSITION).equalsIgnoreCase(ZKConstants.RELATIVE)){
					if(xmlAttrName.equalsIgnoreCase(ZKConstants.X)||xmlAttrName.equalsIgnoreCase(ZKConstants.Y)){
						continue;
					}
				}
				
				/*
				 *  custome code added
				 *  changes by wasim, 28-May-2009
				 */
				
				if(xmlAttrName.equalsIgnoreCase(ZKConstants.VISIBLE_TO)&& !zkAttrValue.equalsIgnoreCase(ZKConstants.CUSTOME_ALL))
				{
					
					customeStr =ZKConstants.CUSTOME_TAG+ zkAttrValue+ZKConstants.CUSTOME_END;
					continue;
				
				}
				
				// add value type custom tag
				/*if(xmlAttrName.equalsIgnoreCase(ZKConstants.VALUE_TYPE))
				{
					if(customeStr == null)
						customeStr = "";
					customeStr = customeStr + zkBuilderUtility.addCustomTagForValidation(ZKConstants.VALUE_TYPE,zkAttrValue);
					continue;
					
					
				}*/
				
				// add max custom tag
				if(xmlAttrName.equalsIgnoreCase(ZKConstants.MIN_TYPE))
				{
					if(customeStr == null)
						customeStr = "";
					customeStr = customeStr + zkBuilderUtility.addCustomTagForValidation(ZKConstants.MIN_TYPE, zkAttrValue);
					continue;
				}
				// add min custom tag
				if(xmlAttrName.equalsIgnoreCase(ZKConstants.MAX_TYPE))
				{	
					if(customeStr == null)
						customeStr = "";
					customeStr = customeStr + zkBuilderUtility.addCustomTagForValidation(ZKConstants.MAX_TYPE, zkAttrValue);
					continue;
				}
				
				
				if(!(xmlElementName.equalsIgnoreCase(ZKConstants.DATE_PICKER)||
						xmlElementName.equalsIgnoreCase(ZKConstants.TIME_PICKER) ||
						xmlElementName.equalsIgnoreCase(ZKConstants.COMBO_BOX)||
						xmlElementName.equalsIgnoreCase(ZKConstants.TEXTBOX)||
						xmlElementName.equalsIgnoreCase(ZKConstants.Button))
						)
				{
					if(xmlAttrName.equalsIgnoreCase(ZKConstants.TAB_INDEX))
						continue;
				}
				
					
				
				// read mapFile attribute value for name display value
				if(xmlElementName.equalsIgnoreCase(ZKConstants.TEXTBOX)|| xmlElementName.equalsIgnoreCase(ZKConstants.COMBO_BOX)
						|| xmlElementName.equalsIgnoreCase(ZKConstants.DATE_PICKER)|| xmlElementName.equalsIgnoreCase(ZKConstants.TIME_PICKER))
				{
					if(xmlAttrName.equalsIgnoreCase(ZKConstants.ELEMENT_NAME))
					{
						String mapValue = zkBuilderUtility.findDisplayName(formPatternId, zkAttrValue);
						if(mapValue!=null)
							zkAttrValue = mapValue;
					}
					
				}
			//added condition for align by pra in case of div on 14th may 2009	
				if(xmlAttrName.equalsIgnoreCase(ZKConstants.ALIGN))
				{
					if(!(xmlElementName.equalsIgnoreCase(ZKConstants.FRAME)))
					{
						continue;
					}
				}
				
				
				if (zkTagName.equalsIgnoreCase(ZKConstants.GRID)
						&& xmlAttrName
								.equalsIgnoreCase(ZKConstants.DATAPATTERN))
				{
					zkCode = zkCode
							+ zkBuilderUtility.addAttribute(xmlNode,
									xmlAttrName, zkAttrValue);
					element_id = "";
				}
			//changed code for default value of textbox by praon 12 and 13 may 2009.
				if (xmlElementName.equalsIgnoreCase(ZKConstants.TEXTBOX)
						&& xmlAttrName.equalsIgnoreCase(ZKConstants.TEXT))
				{
					 
					multilineText = new ArrayList();
					if (zkAttrValue.indexOf(ZKConstants.NEW_LINE) > 0)
					{
						
						multilineText = zkBuilderUtility
								.retreiveZKMultlineText(zkAttrValue);

					}
					else
					{
						
						zktextboxValue = zkAttrValue;
					}
			//continue;
				}
				//change done for vbox and hbox by pra on 12and 13 may 2009.
				if(xmlElementName.equalsIgnoreCase(ZKConstants.FRAME) && xmlAttrName.equalsIgnoreCase(ZKConstants.BOX_ALIGNMENT))
				{
				
					continue;
				}
				//added at 6_50PM 29March2011
				if(xmlElementName.equalsIgnoreCase(ZKConstants.SPLITTER))
				{
					//continue;
				}//finished here on 29March
				
				//adding the rounded mold to the textbox and combobox
				if(xmlAttrName.equalsIgnoreCase(ZKConstants.MOLD)){
					if(xmlElementName.equalsIgnoreCase(ZKConstants.TEXTBOX) || xmlElementName.equalsIgnoreCase(ZKConstants.COMBO_BOX)){					
						if(zkAttrValue.equalsIgnoreCase(ZKConstants.MOLD_ROUNDED)){
							zkCode = zkCode + zkBuilderUtility.addAttribute(xmlNode, xmlAttrName, ZKConstants.MOLD_ROUNDED);
						}
						continue;
					}
				}
				
				//adding the src property incase of iFrame
				if(xmlAttrName.equalsIgnoreCase(ZKConstants.SOURCE) && zkTagName.equalsIgnoreCase(ZKConstants.IFRAME)){
					if(zkAttrValue.trim().length() > 0){
						zkCode = zkCode + zkBuilderUtility.addAttribute(xmlNode, ZKConstants.src, zkAttrValue);
					}
					continue;
				}
				
				//adding the image of the button in the css so as to allow for the text to show through
				if(xmlAttrName.equalsIgnoreCase(ZKConstants.SOURCE) && zkTagName.equalsIgnoreCase(ZKConstants.Button)){
					String renderTxtWImg = xmlNode.attributeValue(ZKConstants.RENDER_TXT_W_IMG);
					if(renderTxtWImg != null && renderTxtWImg.equalsIgnoreCase(ZKConstants.TRUE)){
						if(zkAttrValue.trim().length() > 0){
							cssCode = cssCode + zkBuilderUtility.addStyles(ZKConstants.ATTR_BACKGROUND_IMAGE, zkAttrValue) + ZKConstants.NEW_LINE;
							continue;
						}
					}
				}
				if(xmlAttrName.equalsIgnoreCase(ZKConstants.BACKCOLOR) && zkTagName.equalsIgnoreCase(ZKConstants.Button)){
					String usedefaultcolor = xmlNode.attributeValue(ZKConstants.USE_DEFAULT_COLOR);
					if(usedefaultcolor != null && usedefaultcolor.equalsIgnoreCase(ZKConstants.TRUE)){
						continue;
						}
				}
			
				if(xmlAttrName.equalsIgnoreCase(ZKConstants.BACKCOLOR) && zkTagName.equalsIgnoreCase(ZKConstants.TEXTBOX )){
					continue;
				}
				
				zkAttrName = zkBuilderUtility.findTag(xmlAttrName);
				if (null == zkAttrName || zkAttrName.equals(""))
				{
					zkAttrName = zkBuilderUtility.findAttribute(xmlElementName,
							xmlAttrName);
					if (null == zkAttrName || zkAttrName.equals(""))
					{
						continue;
					}
				}

				// in case of list box to enable multiple select
				if (zkAttrName.equalsIgnoreCase(ZKConstants.MULTIPLE)
						&& xmlElementName
								.equalsIgnoreCase(ZKConstants.LIST_BOX))
				{
					zkAttrValue = ZKConstants.TRUE;
				}

				
				if (multiline
						&& (zkAttrName
								.equalsIgnoreCase(ZKConstants.ATTR_MARGIN_TOP) || zkAttrName
								.equalsIgnoreCase(ZKConstants.ATTR_MARGIN_LEFT))) continue;
				/*if (multiline
						&& zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_LEFT))
				{
					zkAttrName = ZKConstants.ATTR_MARGIN_LEFT;

				}
				if (multiline
						&& zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_TOP))
				{
					zkAttrName = ZKConstants.ATTR_MARGIN_TOP;

				}*/
				//implmentation change by pra on 12 and 13 may2009.
				if (zkTagName.equalsIgnoreCase(ZKConstants.TAB)
						&& zkAttrName
								.equalsIgnoreCase(ZKConstants.ATTR_BACKGROUND_COLOR))
				{

					bgColor = ZKConstants.START_STYLE_TAG1 + zkAttrName + ":"
							+ zkAttrValue + ";" + ZKConstants.POSITION_RELATIVE
							+ ZKConstants.END_STYLE_TAG1;
					tabBGColor.put(xmlNode.attributeValue(ZKConstants.ID), bgColor);
					i++;
					
				}
			
				/*if (xmlElementName.equalsIgnoreCase(ZKConstants.FRAME)
						&& zkAttrName
								.equalsIgnoreCase(ZKConstants.ATTR_BACKGROUND_IMAGE))
				{
					zkCode = zkCode
							+ ZKConstants.EMPTY_STRING
							+ ZKConstants.START_IMAGE_STYLE_TAG
							+ zkBuilderUtility.addStyles(zkAttrName,
									zkAttrValue) + ZKConstants.END_IMAGE_STYLE_TAG;
				}*/
				
				//change done for base window for left and top by pra on 12and 13 may 2009.
				if(xmlElementName.equalsIgnoreCase(ZKConstants.BASE_WINDOW_TAG_NAME)&& (zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_LEFT)||zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_TOP)))
				{
					 continue;
				}
			

				//change done for visbile for tabbox and childs by pra on 12and 13 may 2009.
                if(zkTagName.equalsIgnoreCase(ZKConstants.TABBOX) && zkAttrName.equalsIgnoreCase(ZKConstants.VISIBLE))
                {
                	  continue;
                }
                if(xmlNode.getParent()!=null && (xmlNode.getParent().getName().equalsIgnoreCase(ZKConstants.MULTI_TAB)||xmlNode.getParent().getName().equalsIgnoreCase(ZKConstants.TAB_PAGE))&& zkAttrName.equalsIgnoreCase(ZKConstants.VISIBLE))
                {
                	  continue;
                }

			

				if (ZKConstants.ATTRIBUTE_LIST.contains(zkAttrName))
				{
					if (xmlAttrName.equalsIgnoreCase(ZKConstants.formDesignerPassword))
					{
						if (zkAttrValue.equalsIgnoreCase(ZKConstants.TRUE))
							zkAttrValue = ZKConstants.formDesignerPassword;
						else
							continue;
					}

					// if(zkAttrValue.contains(ZKConstants.NEW_LINE) )
					if (zkAttrName.equalsIgnoreCase(ZKConstants.ROWS)
							&& zkAttrValue.equalsIgnoreCase(ZKConstants.TRUE))
					{
						zkCode = zkCode
								+ zkBuilderUtility.createMultilineText(xmlNode,
										zkAttrName, multilineText);
						continue;
						// break;
					}
					else if (zkAttrName.equalsIgnoreCase(ZKConstants.ROWS)
							&& zkAttrValue.equalsIgnoreCase(ZKConstants.FALSE))
					{
						continue;
					}
					//code for paginal added by pra on 12 and 13 may 2009.
					else if(zkAttrName.equalsIgnoreCase(ZKConstants.PAGINAL)){
						if(zkAttrValue != null && zkAttrValue.trim().length() > 0){
							zkAttrValue = "${"+zkAttrValue+"}";
						}else{
							continue;
						}
					}
					//code for menuicnon added by pra.
					else if(xmlAttrName.equalsIgnoreCase(ZKConstants.MENUICON)&& zkAttrName.equalsIgnoreCase(ZKConstants.src))
					{
						zkAttrValue="../zul"+zkAttrValue;
					}

					// for date picker no height property should to be added as
					// this distroy the alignment of the control
					//TODo datepicker have to write here
					if ((xmlElementName.equalsIgnoreCase(ZKConstants.COMBO_BOX)&& 
							zkAttrName.equals(ZKConstants.TEXT)) || 
							(!((/* xmlElementName.equalsIgnoreCase(ZKConstants.DATE_PICKER) ||*/
							xmlElementName.equalsIgnoreCase(ZKConstants.COMBO_BOX)&& 
							zkAttrName.equals(ZKConstants.ATTR_HEIGHT)) || 
							(xmlElementName.equalsIgnoreCase(ZKConstants.TAB_PAGE)/*||
							xmlElementName.equalsIgnoreCase(ZKConstants.MENU)||
							xmlElementName.equalsIgnoreCase(ZKConstants.MENU_BAR)*/ ||
							xmlElementName.equalsIgnoreCase(ZKConstants.MENU_ITEM))&&
							(zkAttrName.equals(ZKConstants.ATTR_HEIGHT) || 
							zkAttrName.equals(ZKConstants.ATTR_WIDTH)))))
					{
						if (xmlElementName.equals(ZKConstants.Button)
								&& zkAttrName.equals(ZKConstants.image)) zkAttrValue = zkAttrValue
								.substring(1);
						if (xmlElementName.equals(ZKConstants.Picture)
								&& zkAttrName.equals(ZKConstants.src)) zkAttrValue = zkAttrValue
								.substring(1);
						zkCode = zkCode
								+ zkBuilderUtility.addAttribute(xmlNode,
										zkAttrName, zkAttrValue);
					}
				}
				else
				{

					if (xmlElementName
							.equalsIgnoreCase(ZKConstants.RADIO_BUTTON)
							&& (zkAttrName.equals(ZKConstants.ATTR_FONT_SIZE))) 
					{
						//zkCode = zkCode + ZKConstants.EMPTY_STRING + ZKConstants.START_STYLE_TAG1 + zkBuilderUtility.addStyles(zkAttrName, zkAttrValue) + ZKConstants.END_STYLE_TAG1;
						cssCode = cssCode + zkBuilderUtility.addStyles(zkAttrName, zkAttrValue) + ZKConstants.NEW_LINE;
					}
					// render the style, create a css file

					if (!((xmlElementName
							.equalsIgnoreCase(ZKConstants.RADIO_BUTTON) && (zkAttrName
							.equals(ZKConstants.ATTR_FONT_SIZE))))) 
					{
						//Adding here more codition so that no cssCode will implement like for Auxheader,Auxhead,Drows,DColumn,Drows.
						if(isPopUp && ((
							//(xmlElementName.equalsIgnoreCase(ZKConstants.LIST_BOX))||
							//(xmlElementName.equalsIgnoreCase(ZKConstants.GRID)) ||
							(xmlElementName.equalsIgnoreCase(ZKConstants.GRID_LISTHEADER))||
							(xmlElementName.equalsIgnoreCase(ZKConstants.GRID_ROW))||
							(xmlElementName.equalsIgnoreCase(ZKConstants.GRID_LISTHEAD))||
						    (xmlElementName.equalsIgnoreCase(ZKConstants.COLUMN))||
						    (xmlElementName.equalsIgnoreCase(ZKConstants.COLUMNS))|| 
							((xmlElementName.equalsIgnoreCase(ZKConstants.GRID_LABEL) && (xmlNode.getParent() != null && xmlNode.getParent().getName().equalsIgnoreCase(ZKConstants.COLUMN))))							
							))
							
						)
						{
							cssCode = "";
						}//else if code is appended by sumeet singh
						else if(	(	(xmlElementName.equalsIgnoreCase(ZKConstants.DCOLUMN))||
								(xmlElementName.equalsIgnoreCase(ZKConstants.DROWS))||
								(xmlElementName.equalsIgnoreCase(ZKConstants.DROW))||
								(xmlElementName.equalsIgnoreCase(ZKConstants.AUXHEAD))||
								(xmlElementName.equalsIgnoreCase(ZKConstants.AUXHEADER))||
								(xmlElementName.equalsIgnoreCase(ZKConstants.COLUMNS))||
								(xmlElementName.equalsIgnoreCase(ZKConstants.TEXTBOX))||
								(xmlElementName.equalsIgnoreCase(ZKConstants.GRID_LABEL))
							)
							&& (   ((ZKConstants.DGRID.equalsIgnoreCase(xmlNode.getParent().getName()))
								||(ZKConstants.DROWS.equalsIgnoreCase((xmlNode.getParent().getName())))
								||(ZKConstants.AUXHEAD.equalsIgnoreCase((xmlNode.getParent().getName())))
								||(ZKConstants.COLUMNS.equalsIgnoreCase((xmlNode.getParent().getName())))
								||(ZKConstants.DROW.equalsIgnoreCase((xmlNode.getParent().getName()))))
								)
							)	
								
						{
							cssCode = "";
						}else if(zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_BORDER)){
							if((xmlElementName.equalsIgnoreCase(ZKConstants.Button)) && (!isPopUp)){
								cssCode = cssCode + zkBuilderUtility.addStyles(zkAttrName, zkAttrValue) + ZKConstants.NEW_LINE;
							}//adding a border for textbox and listbox(grid)
							else if((xmlElementName.equalsIgnoreCase(ZKConstants.TEXTBOX) || xmlElementName.equalsIgnoreCase(ZKConstants.GRID))){
								String borderSize = zkAttrValue;
								String borderColor = xmlNode.attributeValue(ZKConstants.BORDER_COLOR);
								if(borderSize != null && !borderSize.equalsIgnoreCase("0")){
									if(borderColor != null && borderColor.trim().length() > 0){
										String styleStr = zkBuilderUtility.addStyles(ZKConstants.ATTR_BORDER, borderSize + ZKConstants.ATTR_PIXEL + " " + ZKConstants.BORDER_SOLID + " " + borderColor);
										if(isPopUp == false && xmlElementName.equalsIgnoreCase(ZKConstants.GRID)){
											zkCode = zkCode + " " + ZKConstants.START_STYLE_TAG1 + styleStr + ZKConstants.END_STYLE_TAG1;
										}else{
											//cssCode = cssCode + ZKConstants.ATTR_BORDER + ":" + borderSize + ZKConstants.ATTR_PIXEL + " " + ZKConstants.BORDER_SOLID + " " + borderColor + ";" + ZKConstants.NEW_LINE;
											cssCode = cssCode + styleStr + ZKConstants.NEW_LINE;
										}
									}
								}
							}
						}//adding a text-align for a label
						else if(zkAttrName.equalsIgnoreCase(ZKConstants.ATTR_TEXTALIGN) && (zkAttrValue != null && zkAttrValue.length() > 0)){
							if((xmlElementName.equalsIgnoreCase(ZKConstants.LABEL))){
								cssCode = cssCode + zkBuilderUtility.addStyles(zkAttrName, zkAttrValue) + ZKConstants.NEW_LINE;
							}
						}//adding hand cursor for label and button
						else if(zkAttrName.equalsIgnoreCase(ZKConstants.RENDER_CURSOR)){
							if(zkAttrValue != null && zkAttrValue.equalsIgnoreCase(ZKConstants.TRUE)){
								String cursorType = xmlNode.attributeValue(ZKConstants.ATTR_CURSOR);
								if(cursorType != null && cursorType.length() > 0){
									if(xmlElementName.equalsIgnoreCase(ZKConstants.LABEL) || xmlElementName.equalsIgnoreCase(ZKConstants.Button)){
										if(cursorType.equalsIgnoreCase(ZKConstants.CURSOR_HAND_XML)){
											cssCode = cssCode + zkBuilderUtility.addStyles(ZKConstants.ATTR_CURSOR, ZKConstants.CURSOR_HAND_ZK) + ZKConstants.NEW_LINE;
										}
									}
								}
							}
							continue;
						}
						else if(zkAttrName.equalsIgnoreCase(ZKConstants.STYLE)){
							if(zkAttrValue != null && zkAttrValue.length() > 0){
								if((xmlElementName.equalsIgnoreCase(ZKConstants.FRAME))){
									cssCode = cssCode + zkBuilderUtility.addStyles(zkAttrName, zkAttrValue) + ZKConstants.NEW_LINE;
								}
							}
						}
						else if(zkAttrName.equalsIgnoreCase(ZKConstants.POSITION) && (xmlNode.getName().equalsIgnoreCase(ZKConstants.GRID) && (xmlNode.attributeValue(ZKConstants.GRID_TYPE) != null && xmlNode.attributeValue(ZKConstants.GRID_TYPE).equalsIgnoreCase(ZKConstants.GRID)))){ //not writing out the position attribute for grid in css 
							continue;							
						}
						else{
							cssCode = cssCode + zkBuilderUtility.addStyles(zkAttrName, zkAttrValue) + ZKConstants.NEW_LINE;
						}
					}

				}
			}
			//added style in case of popup by pra on 15-05-2009
			if(isPopUp)
			{
				zkCode=zkCode+ZKConstants.EMPTY_STRING+zkBuilderUtility.addStyleForPopup(cssCode);
			}
			
			// if it is tabbox (MultiTab), add tabscroll="false" by default. Added by HS on 02-06-2009
			if (zkTagName.equalsIgnoreCase(ZKConstants.TABBOX))
			{
				zkCode = zkCode + ZKConstants.DEFAULT_TABBOX_ATTRIBUTE;
				/*try{					
					String typeAttribute = elementName = xmlNode.attributeValue(ZKConstants.MULTITAB_TYPE);
					System.out.println("value of type attribute of tabbox= "+typeAttribute);
					if(typeAttribute != null && typeAttribute.equalsIgnoreCase("accordion")){
						zkCode = zkCode + ZKConstants.MULTITAB_MOLD_ATTRIBUTE;
					}
				}
				catch (Exception e) {
					System.out.println("type attribute not found for tabbox");
				}*/
			}
			
			if (multiline)
			{
				if (xmlNode.getName().equalsIgnoreCase(ZKConstants.TEXTBOX))
				{
					String subString = null;
					if(zkCode.indexOf(">")>0)
					{
						subString =zkCode.substring(0, zkCode.length()-1);
					}
					else
					{
						subString =zkCode;
					}
					subString = subString+" " +ZKConstants.TEXTBOX_METHOD + ">";
					resultStr[0] = subString + ZKConstants.NEW_LINE;
				}
				else
				{
					
					resultStr[0] = zkCode + ZKConstants.NEW_LINE;
				}
			}
				 
			else if (xmlNode.getName().equalsIgnoreCase(ZKConstants.TEXTBOX))
			{
				resultStr[0] = zkCode+ ZKConstants.EMPTY_STRING	+ ZKConstants.TEXTBOX_METHOD
					+ ZKConstants.END_TAG + ZKConstants.NEW_LINE;
			}
			else if (xmlNode.getName().equalsIgnoreCase(ZKConstants.CHECK_BOX)) // 
			resultStr[0] = zkCode + ZKConstants.EMPTY_STRING
					+ ZKConstants.CHECKBOX_METHOD + ZKConstants.END_TAG
					+ ZKConstants.NEW_LINE;
			else if (xmlNode.getName()
					.equalsIgnoreCase(ZKConstants.RADIO_GROUP))
			{
				List<Element> radioList = xmlNode
						.elements(ZKConstants.RADIO_BUTTON);
				for (Iterator itr = radioList.iterator(); itr.hasNext();)
				{
					Element radioElement = (Element) itr.next();
					String radioSelectedValue = radioElement.attribute(
							ZKConstants.ATTR_SELECTED).getValue();

					if (radioSelectedValue.equalsIgnoreCase(ZKConstants.TRUE))
					{
						radioSelectedID = radioElement
								.attribute(ZKConstants.ID).getValue();
						radioSelectedMethod = ZKConstants.RADIO_SELECTED_START_METHOD
								+ radioSelectedID
								+ ZKConstants.RADIO_SELECTED_END_METHOD;
					}
					else
						radioSelectedMethod = "";
				}

				resultStr[0] = zkCode + ZKConstants.EMPTY_STRING
						+ radioSelectedMethod + ZKConstants.EMPTY_STRING
						+ ZKConstants.RADIOGROP_METHOD + ZKConstants.END_TAG
						+ ZKConstants.NEW_LINE;
			}
			else if (xmlNode.getName()
					.equalsIgnoreCase(ZKConstants.DATE_PICKER)) 
			{
					String idStr = ZKConstants.ID +"=\""+elementId+"\" ";
					resultStr[0] = zkCode
					+ ZKConstants.EMPTY_STRING
					/*+ ZKConstants.START_STYLE_TAG
					+ attrValue
					+ ZKConstants.END_STYLE_TAG
					+ idStr */
					+ ZKConstants.DATEBOX_METHOD
					+ ZKConstants.END_TAG
					+ ZKConstants.NEW_LINE;
			}
			else if(xmlNode.getName().equalsIgnoreCase(ZKConstants.TIME_PICKER))
			{
				resultStr[0] = zkCode
				+ ZKConstants.EMPTY_STRING
				+ ZKConstants.TIMEBOX_METHOD
				+ ZKConstants.END_TAG
				+ ZKConstants.NEW_LINE;
			}
			else if (xmlNode.getName().equalsIgnoreCase(ZKConstants.COMBO_BOX)
					|| xmlNode.getName().equalsIgnoreCase(ZKConstants.GRID)
					|| xmlNode.getName().equalsIgnoreCase(ZKConstants.LIST_BOX)/*
					|| xmlNode.getName()
							.equalsIgnoreCase(ZKConstants.MULTI_TAB)*/)
					{
						if((xmlNode.getName().equalsIgnoreCase(ZKConstants.GRID)
								|| xmlNode.getName().equalsIgnoreCase(ZKConstants.LIST_BOX))&& xmlNode.elements().size()>1)
						{
							
							resultStr[0] = zkCode + ZKConstants.EMPTY_STRING	    					
    							/* + ZKConstants.START_STYLE_TAG
    	    					+ attrValue
    	    					+ ZKConstants.END_STYLE_TAG*/
								+ZKConstants.LIST_GRID_FIXEDLAYOUT
    	    					+ ZKConstants.END_TAG + ZKConstants.NEW_LINE;
							//RendererComponent.logger.log(0,"----------grid-222---"+zkCode);
						}
						else
						{
    					resultStr[0] = zkCode + ZKConstants.EMPTY_STRING
    					/*+ ZKConstants.START_STYLE_TAG
    					+ attrValue
    					+ ZKConstants.END_STYLE_TAG*/
    					+ ZKConstants.END_TAG + ZKConstants.NEW_LINE;
						}
					}
			else
			{
				if (xmlNode.getName().equalsIgnoreCase(ZKConstants.TREE)) 
				{	
				  //resultStr[0] = zkCode+ ZKConstants.EMPTY_STRING+ ZKConstants.START_STYLE_TAG+ attrValue+ ZKConstants.END_STYLE_TAG+ ZKConstants.END_TAG+ ZKConstants.NEW_LINE;
				  resultStr[0]=zkCode+ZKConstants.END_TAG+ZKConstants.NEW_LINE;
				}
				else if(xmlNode.getName().equalsIgnoreCase(ZKConstants.TREE_NODE))
				{
					resultStr[0]=zkCode+ZKConstants.END_TAG+ZKConstants.NEW_LINE;
				}
				else
					resultStr[0] = zkCode + ZKConstants.END_TAG
							+ ZKConstants.NEW_LINE;
				
				
				
				if (isFrame && isForm && formId != null)
				{
					// isForm = true;
					resultStr[0] = resultStr[0]
							+ zkBuilderUtility.addFormSrc(formId, application
									.getApplicationName());
				}
			}

			List value = xmlNode.elements("Events");
			if (value.size() > 0) resultStr[0] = resultStr[0]
					+ ZKConstants.EMPTY_STRING
					+ parseEvents(xmlNode, xmlElementName);
			
			/*// for menu need to add menupopup tag after menu
			if (zkTagName.equals(ZKConstants.MENU)) resultStr[0] = resultStr[0]
					+ ZKConstants.MENUPOPUP_START_TAG
					+ ZKConstants.NEW_LINE;*/

			resultStr[1] = cssCode;
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw (new RendererComponentException(exp.getMessage()));
		}
		
		return resultStr;
	}

	/**
	 * Parses the events for an element
	 * 
	 * @param xmlNode
	 * @param xmlElementName
	 * @return
	 * @throws Exception
	 */
	public String parseEvents(Element xmlNode, String xmlElementName)
			throws Exception
	{
		Element eventElement = null;
		Element eventChildElement = null;
		String elName, eventType, eventName = "", clientScript, elementName;
		String eventCode = "";
		List eventList = xmlNode.elements("Events");
		if (null != eventList && eventList.size() > 0)
		{
			Iterator evtIt = eventList.iterator();
			eventElement = (Element) evtIt.next();
			List eventChildList = eventElement
					.elements(ZKConstants.EVENT_TAG_NAME);
			elementName = xmlNode.attributeValue(ZKConstants.ELEMENT_NAME);
			// RendererComponent.logger.log(0,"777"+xmlNode.getParent());
			int eventCounter=0;
			if (null != eventChildList && eventChildList.size() > 0)
			{
				for (Iterator evtIterate = eventChildList.iterator(); evtIterate
						.hasNext();)
				{

					eventChildElement = (Element) evtIterate.next();
					elName = eventChildElement
							.attributeValue(ZKConstants.ELEMENT_NAME);
					eventType = eventChildElement
							.attributeValue(ZKConstants.EVENT_TYPE);
					eventName = zkBuilderUtility.findEvent(xmlElementName, elName);
					clientScript = eventChildElement.getText();
					
					/*
					 * Following code generated for make visible.
					 * when user select visibleto property.
					 * changes by wasim ,29-May-2009
					 */
					if(eventName.equalsIgnoreCase(ZKConstants.ON_CREATE))
					{
						eventCounter++;
					}
					
					if(eventCounter>1)
					{
				
						 if(eventCode.indexOf(ZKConstants.START_ATTRIBUTE)>=0)
						 {
							int index = eventCode.indexOf(ZKConstants.START_ATTRIBUTE);
							index = ZKConstants.START_ATTRIBUTE.length()+1+9;
							clientScript = ZKConstants.START_ATTRIBUTE + ZKConstants.CDATA_TAG_START + ZKConstants.NEW_LINE + clientScript + ";";
							String subString = eventCode.substring(index, eventCode.length());
							eventCode = clientScript + subString;
						 }	
						
						
						eventCounter=0;
						continue;
					}
					
					
					if (eventType != null
							&& eventType.toLowerCase().trim().equalsIgnoreCase(
									ZKConstants.SERVER_EVENT_TYPE_VAL)
							&& (!(eventName.trim().equals("")))) eventCode = eventCode
							+ zkBuilderUtility.serverSideEvent(eventName,
									elementName);
					else
					{
						eventCode = eventCode
								+ zkBuilderUtility.clientSideEvents(xmlNode,
										eventName, clientScript,formPatternId);
					}
				}
			}
		}
		return eventCode;
	}

	/**
	 * This method is used to copy the resources used in the User Interface like
	 * images, etc
	 * 
	 * @return void
	 * @throws RendererComponentException
	 * */

	private void copyImages() throws RendererComponentException
	{
		FileIO fileWriter = new FileIO();
		try
		{
			// Move images to render img folder
			fileWriter.copyDirectory(applicationFolderPath
					+ ZKConstants.IMG_FOLDER_NAME, filePath
					+ ZKConstants.MAIN_FOLDER_NAME + "/"
					+ ZKConstants.ZK_FOLDER_NAME + "/"
					+ ZKConstants.IMG_FOLDER_NAME);
		}
		catch (IOException e)
		{
			RendererComponent.logger.log(0,"No images used");
		}
	}
	
	/**
	 * This method is used to copy the resources used in the User Interface like
	 * content, etc
	 * 
	 * @return void
	 * @throws RendererComponentException
	 * */

	private void copyContent() throws RendererComponentException
	{
		FileIO fileWriter = new FileIO();
		try
		{
			// Move images to render img folder
			fileWriter.copyDirectory(applicationFolderPath
					+ ZKConstants.CONTENT_FOLDER_NAME, filePath
					+ ZKConstants.MAIN_FOLDER_NAME + "/"
					+ ZKConstants.CONTENT_FOLDER_NAME);
		}
		catch (IOException e)
		{
			RendererComponent.logger.log(0,"No content used");
		}
	}

	private void copyAppletResources() throws RendererComponentException
	{
		FileIO fileWriter = new FileIO();
		try
		{
			// Move images to render img folder
			fileWriter.copyDirectory(applicationFolderPath
					+ ZKConstants.FORM_PATTERN_FOLDER_NAME + "/"
					+ ZKConstants.APPLET_FOLDER_NAME, filePath
					+ ZKConstants.MAIN_FOLDER_NAME + "/"
					+ ZKConstants.ZK_FOLDER_NAME + "/"
					+ ZKConstants.APPLET_FOLDER_NAME);
		}
		catch (IOException e)
		{
			RendererComponent.logger.log(0,"No images used");
		}
	}	

	private void copyVisualizerResources() throws RendererComponentException
	{
		FileIO fileWriter = new FileIO();
		try
		{
			// Move images to render img folder
			fileWriter.copyDirectory(applicationFolderPath
					+ ZKConstants.FORM_PATTERN_FOLDER_NAME + "/"
					+ ZKConstants.VISUALIZER_FOLDER_NAME, filePath
					+ ZKConstants.MAIN_FOLDER_NAME + "/"
					+ ZKConstants.ZK_FOLDER_NAME + "/"
					+ ZKConstants.VISUALIZER_FOLDER_NAME);
		}
		catch (IOException e)
		{
			RendererComponent.logger.log(0,"No images used");
		}
	}	
	/** Fast & simple file copy. */
	/*private void copyActualFiles(File source, File dest) throws IOException
	{
		FileChannel in = null, out = null;
		try
		{
			in = new FileInputStream(source).getChannel();
			out = new FileOutputStream(dest).getChannel();

			long size = in.size();
			MappedByteBuffer buf = in.map(FileChannel.MapMode.READ_ONLY, 0,
					size);

			out.write(buf);

		}
		finally
		{
			if (in != null) in.close();
			if (out != null) out.close();
		}
	}
*/
	

	/**
	 * This method is used to copy the resources used in the User Interface like
	 * images, etc
	 * 
	 * @return void
	 * @throws RendererComponentException
	 * */

	private void copyResourcess() throws RendererComponentException
	{
		FileIO fileWriter = new FileIO();
		try
		{
			// Move library to render library folder
			fileWriter.copyFileAsResource(ZKConstants.LIBRARY_FOLDER_PATH + "/"
					+ ZKConstants.UI_LIBRARY, filePath
					+ ZKConstants.MAIN_FOLDER_NAME + "/"
					+ ZKConstants.LIBRARY_FOLDER_NAME + "/"
					+ ZKConstants.UI_LIBRARY);
			
			// Move init zk script to render library folder
			fileWriter.copyFileAsResource(ZKConstants.LIBRARY_FOLDER_PATH + "/"
					+ ZKConstants.INIT_ZK_SCRIPT, filePath
					+ ZKConstants.MAIN_FOLDER_NAME + "/"
					+ ZKConstants.LIBRARY_FOLDER_NAME + "/"
					+ ZKConstants.INIT_ZK_SCRIPT);
			
			//------------------------------------------------------------------
			// Move jUpload.zul to jUpload folder on deploy
			fileWriter.copyFileAsResource(ZKConstants.JUPLOAD_FOLDER_PATH + "/"
					+ ZKConstants.JUPLOAD_ZUL, filePath
					+ ZKConstants.MAIN_FOLDER_NAME + "/"
					+ ZKConstants.JUPLOAD_FOLDER_NAME + "/"
					+ ZKConstants.JUPLOAD_ZUL);
			// Move jUpload.jar to jUpload folder on deploy
			fileWriter.copyFileAsResource(ZKConstants.JUPLOAD_FOLDER_PATH + "/"
					+ ZKConstants.JUPLOAD_JAR, filePath
					+ ZKConstants.MAIN_FOLDER_NAME + "/"
					+ ZKConstants.JUPLOAD_FOLDER_NAME + "/"
					+ ZKConstants.JUPLOAD_JAR);
			// Move parseRequest.jsp to jUpload folder on deploy
			fileWriter.copyFileAsResource(ZKConstants.JUPLOAD_FOLDER_PATH + "/"
					+ ZKConstants.PARSEREQUEST_JSP, filePath
					+ ZKConstants.MAIN_FOLDER_NAME + "/"
					+ ZKConstants.JUPLOAD_FOLDER_NAME + "/"
					+ ZKConstants.PARSEREQUEST_JSP);
			// Move how_to_use.txt to jUpload folder on deploy
			fileWriter.copyFileAsResource(ZKConstants.JUPLOAD_FOLDER_PATH + "/"
					+ ZKConstants.HOW_TO_USE, filePath
					+ ZKConstants.MAIN_FOLDER_NAME + "/"
					+ ZKConstants.JUPLOAD_FOLDER_NAME + "/"
					+ ZKConstants.HOW_TO_USE);
			//------------------------------------------------------------------

			copyFileAsResource(ZKConstants.WEB_FOLDER + "/"
					+ ZKConstants.WEB_XML, application.getOutputPath()
					+ ZKConstants.MAIN_FOLDER_NAME + "/" + ZKConstants.WEB_XML);
			copyFileAsResource(ZKConstants.WEB_FOLDER + "/"
					+ ZKConstants.ZK_XML, application.getOutputPath()
					+ ZKConstants.MAIN_FOLDER_NAME + "/" 
					+ ZKConstants.WEBINF_FOLDER_NAME + "/" + ZKConstants.ZK_XML);
			
			fileWriter.copyFileAsResource(ZKConstants.ZUL_FOLDER_PATH + "/"
					+ ZKConstants.TIMEOUT_PAGE, 
					filePath + ZKConstants.MAIN_FOLDER_NAME + "/"
					+ ZKConstants.ZK_FOLDER_NAME + "/"
					+ ZKConstants.TIMEOUT_PAGE);
			
			/*copyFileAsResource(ZKConstants.WEB_FOLDER + "/"
					+ ZKConstants.WEB_XML, application.getOutputPath()
					+  "/" + ZKConstants.WEB_XML);*/
			
			
			/*
			 	Removing zk jars from server and added them in war only
				which is created using ant from rendering.
				To solve issue of appdesigner publish. the solution 
				can be changed bcoz it is getting jars names  from constants
				so need to check for another good way
			 */
			// Commented by HS since this is not required now as it only
			// increases war size
			/*copyZKJars(application.getApplicationFolderPath()
					+ ZKConstants.MAIN_FOLDER_NAME + "/"
					+ ZKConstants.WEBINF_FOLDER_NAME + "/"
					+ ZKConstants.WEBINF_LIB + "/");
			*/
			// Open this code for copy the jasper reports in the server
			//copyJasperFiles(application.getApplicationFolderPath() + ZKConstants.MAIN_FOLDER_NAME + "/" + ZKConstants.ZK_FOLDER_NAME + "/");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			RendererComponent.logger.log(0,"No libraries found");
		}
	}

	/*
	 * includeFile method is removed. it used in placeholder code earlier  wasim 13-May-2009
	 * 
	 */
	
	
	

	public void copyFileAsResource(String srcFile, String targetLocation)
			throws FileNotFoundException, IOException
	{
		//System.out.println("-----------Inside copyFileAsResource-------srcFile="+srcFile);
		//System.out.println("-----------Inside copyFileAsResource-------targetLocation="+targetLocation);
		//System.out.println("-----------Inside copyFileAsResource-------this.getClass().getPackage()="+this.getClass().getPackage());
		InputStream in = this.getClass().getResourceAsStream(srcFile);
		OutputStream out = null;
		File outFile = null;
		try
		{
			outFile = new File(targetLocation);
			if(!outFile.exists())
			{
				outFile.createNewFile();
				//System.out.println("-----------Inside copyFileAsResource-------srcFile not found so created it ");
			}
			out = new FileOutputStream(outFile);
			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
			{

				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			throw e;
		}
		catch (IOException e)
		{
			throw e;
		}

	}

	/**
	 * Creates folders for UI files
	 * 
	 * @throws RendererComponentException
	 */
	private void createFolders() throws RendererComponentException
	{
		filePath = application.getOutputPath();
		fileWriter.createFolder(filePath + ZKConstants.MAIN_FOLDER_NAME + "/",
				ZKConstants.ZK_FOLDER_NAME);
		fileWriter.createFolder(filePath + ZKConstants.MAIN_FOLDER_NAME + "/",
				ZKConstants.CSS_FOLDER_NAME);
		fileWriter.createFolder(filePath + ZKConstants.MAIN_FOLDER_NAME + "/",
				ZKConstants.CSS_FOLDER_NAME + "/" + ZKConstants.IMG_FOLDER_NAME);
		fileWriter.createFolder(filePath + ZKConstants.MAIN_FOLDER_NAME + "/",
				ZKConstants.LIBRARY_FOLDER_NAME);
		fileWriter.createFolder(filePath + ZKConstants.MAIN_FOLDER_NAME + "/",
				ZKConstants.JUPLOAD_FOLDER_NAME);
		fileWriter.createFolder(filePath + ZKConstants.MAIN_FOLDER_NAME + "/",
				ZKConstants.CONTENT_FOLDER_NAME);
		fileWriter.createFolder(filePath + ZKConstants.MAIN_FOLDER_NAME + "/",
				ZKConstants.WEBINF_LIB);
		fileWriter.createFolder(filePath + ZKConstants.MAIN_FOLDER_NAME + "/",
				ZKConstants.APP_INFO);
		fileWriter.createFolder(filePath + ZKConstants.MAIN_FOLDER_NAME + "/",
				ZKConstants.TEMPLATE_FOLDER_NAME);
		fileWriter.createFolder(filePath + ZKConstants.MAIN_FOLDER_NAME + "/",
				ZKConstants.WEBINF_FOLDER_NAME);
		
	}

	/**
	 * This method is used to copy all the files present in a folder it will
	 * fetch the names of those files from ZKConstants and use
	 * copyFileAsResource method.
	 * 
	 * @param targetLocation
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void copyZKJars(String targetLocation) throws FileNotFoundException,
			IOException
	{
		String classArray[] = ZKConstants.ZK_JARS.split(",");
		for (int count = 0; count < classArray.length; count++)
		{
			copyFileAsResource(ZKConstants.ZKLIB_FOLDER + "/"
					+ classArray[count], targetLocation + classArray[count]);
		}
	}

	
	

	public void copyJasperFiles(String targetLocation) throws FileNotFoundException, IOException
	   {
		if(ZKConstants.JASPER_FILES.trim().length()<=0)return;
		String classArray[] = ZKConstants.JASPER_FILES.split(",");
		if(classArray !=null && classArray.length>0)
		{
			for(int count=0;count<classArray.length;count++)
			{
				copyFileAsResource(ZKConstants.JASPER_FOLDER_PATH + "/" + classArray[count], targetLocation + classArray[count]);
			}
		}
	   }
	
	
	/**
	 * this method is used to set the application
	 * 
	 */
	public void setApplication(Application application)
	{
		this.application = application;
	}

	public void setBaseModeler(IBaseModeler baseModeler)
	{
		this.baseModeler = baseModeler;
	}

	/**
	 * This function applies global themes to the complete application
	 * 
	 * @param application
	 */
	private void updateCommonCss(Application application)
	{
		try
		{
			// read uisettings.xml
			Document uiSettings = zkBuilderUtility.getFormPatternXmlDoc(
					application, ZKConstants.UI_SETTINGS_FILE);
		
			// If any exception like "File Not Found" then return
			if (uiSettings == null) return;
			
			// based on each settings, fetch css from corresponding file
			Element root = uiSettings.getRootElement();
			List<Element> subElementList = root.elements();
	
			String fileContents = "";
			for (int count = 0; count < subElementList.size(); count++)
			{
				// Fetch element
				Element childElement = (Element) subElementList.get(count);
				
				// If no theme is mentioned for current element, move to next element.
				if ((null == childElement.getStringValue()) 
						|| ("".equals(childElement.getStringValue()))) 
					continue;
	
				// Retrieve file name
				String fileName = childElement.getName() 
						+ childElement.getStringValue() + ZKConstants.CSS_EXTN;
	
				// Fetch file contents
				fileContents = fileContents + fileWriter.readFromFile(ZKConstants.STYLE_FOLDER_PATH, fileName);

				try
				{
					fileWriter.copyDirectoryAsResource(
							ZKConstants.STYLE_FOLDER_PATH 
								+ childElement.getName() + childElement.getStringValue() + "/", 
							filePath + ZKConstants.MAIN_FOLDER_NAME 
								+ "/" + ZKConstants.CSS_FOLDER_NAME 
								+ "/" + ZKConstants.IMG_FOLDER_NAME+ "/");
				}
				catch (Exception ioe) 
				{
					ioe.printStackTrace();
					System.out.println("-------Exception caught, handled and moving on-----");
					System.err.println("Source Folder = " + ZKConstants.STYLE_FOLDER_PATH + childElement.getName() + childElement.getStringValue() 
							+ "/ \n Destination Folder = " + filePath + ZKConstants.MAIN_FOLDER_NAME 
							+ "/" + ZKConstants.CSS_FOLDER_NAME 
							+ "/" + ZKConstants.IMG_FOLDER_NAME+ "/");
				}
			}
			
			// write it to common.css
			if (!(fileWriter.writeToFile(filePath
					+ ZKConstants.MAIN_FOLDER_NAME + "/",
					ZKConstants.CSS_FOLDER_NAME + "/" 
							+ ZKConstants.COMMON_CSS_FILE , fileContents)))
			{
				System.err.println("!! Error !! Error writing to common.css file \n FilePath = " 
						+ filePath + ZKConstants.MAIN_FOLDER_NAME + "/ \n TargetFile = "
						+ ZKConstants.CSS_FOLDER_NAME + "/" + ZKConstants.COMMON_CSS_FILE + "\n FileContents = "
						+ fileContents);
			}
			

		}
		catch (RendererComponentException rce)
		{
			// If there is any exception, only the global themes would not
			// apply, this should not stop rendering of application. We will
			// just print the error stack trace
			rce.printStackTrace();
		}
	}
	
	
	/**
	 * This method is used to copy required file in case of the 
	 * schdular control
	 * Added by pra on june-24-2009
	 */
	private void updateSchedularContents()
	{
		
		try{
		String fileContents="";
		fileContents = fileContents + fileWriter.readFromFile(ZKConstants.TOOLBAR_SCH_FOLDER_PATH, ZKConstants.TOOLBAR_COMPONENT_ARROW_FILENAME+ZKConstants.ZK_EXTN);

		if (!(fileWriter.writeToFile(filePath
				+ ZKConstants.MAIN_FOLDER_NAME + "/",
				ZKConstants.ZK_FOLDER_NAME + "/" + ZKConstants.TOOLBAR_COMPONENT_ARROW_FILENAME
						+ ZKConstants.ZK_EXTN, fileContents)))
		{
		}
		fileContents="";
		fileContents = fileContents + fileWriter.readFromFile(ZKConstants.TOOLBAR_SCH_FOLDER_PATH, ZKConstants.TOOLBAR_COMPONET_TAB_FILENAME+ZKConstants.ZK_EXTN);

		if (!(fileWriter.writeToFile(filePath
				+ ZKConstants.MAIN_FOLDER_NAME + "/",
				ZKConstants.ZK_FOLDER_NAME + "/" + ZKConstants.TOOLBAR_COMPONET_TAB_FILENAME
						+ ZKConstants.ZK_EXTN, fileContents)))
		{
		}
		
		}
		catch (Exception e) {
			
			RendererComponent.logger.log(0,"Erro in updating the Schdeular contents");
		}
		
	}


	/**
	 * This function is used for checking if the current form requires user to
	 * log in. Currently this function is hard-coding name of some forms but
	 * ideally it should take from App designer.
	 * 
	 * @param doc
	 * @return
	 */
	private boolean isLoginRequiredForForm(Document doc)
	{
		Element root = doc.getRootElement();
		String fileName = root.attributeValue(ZKConstants.ELEMENT_ID);
		
		// TODO: This should come from App Designer and not hard-coded
		if ((fileName.equalsIgnoreCase("forgotpassword")) || (fileName.equalsIgnoreCase("login")) || (fileName.equalsIgnoreCase("systimeout")))
			return false;
		
		if (root.attributeValue(ZKConstants.ATTR_IS_LOGIN_REQUIRED) != null)
		{
			return (root.attributeValue(ZKConstants.ATTR_IS_LOGIN_REQUIRED).equalsIgnoreCase("true"));
		}
		return true;
	}
	
	public void renderSinglePage(Document doc, String localFilePath, String applicationName) throws RendererComponentException
	{
		System.out.println("localFilePath--"+localFilePath);
		filePath = applicationName;
		String mapFilePath = localFilePath+"MapFile.xml";
		System.out.println("mapFilePath--"+mapFilePath);
		renderPage(doc, mapFilePath, true);
	}
	
	private void createTemplate()
	{
		try
		{
		//copy template file
		String fileContents="";
		fileContents = fileContents + fileWriter.readFromFile(ZKConstants.TEMPLATE_PATH, ZKConstants.TEMPLATE_FILE);

		if (!(fileWriter.writeToFile(filePath
				+ ZKConstants.MAIN_FOLDER_NAME + "/",
				ZKConstants.TEMPLATE_FOLDER_NAME + "/" + ZKConstants.TEMPLATE_FILE, fileContents)))
		{
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		ZKBuilder builder = new ZKBuilder();
		String zkLocationPath = "E:/Development/AppDesigner/NEISUI/";
		try
		{
			builder.copyZKJars(zkLocationPath);
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		builder.filePath = "D:/AD-2_POC/forms";
		String mapFilePath = "C:/NEIS/glassfish/domains/domain1/config/tool-config/MapFile.xml";
		XmlReader xmlReader = new XmlReader();
		Document formPatternDoc = xmlReader.getDocumentFromPath("D:/AD-2_POC/OutBrainkTool.xml");
		try {
			builder.renderPage(formPatternDoc, mapFilePath, true);
		} catch (RendererComponentException e) {
			e.printStackTrace();
		}
		*/
	}
}
