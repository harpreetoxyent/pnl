package com.oxymedical.component.renderer.constants;

import java.util.ArrayList;

/**
 * This class has all the constants used in the User Interface 
 *
 */
public class ZKConstants 
{
	public static final ArrayList<String> ATTRIBUTE_LIST = new ArrayList<String>();
	public static final ArrayList<String> ATTR_LIST = new ArrayList<String>();
	
	static {
		ATTRIBUTE_LIST.add("title");
		ATTRIBUTE_LIST.add("label");
		ATTRIBUTE_LIST.add("type");
		ATTRIBUTE_LIST.add("rows");
		ATTRIBUTE_LIST.add("src");
		ATTRIBUTE_LIST.add("value");
		ATTRIBUTE_LIST.add("multiple");
		ATTRIBUTE_LIST.add("image");
		ATTRIBUTE_LIST.add("height");
		ATTRIBUTE_LIST.add("width");
		ATTRIBUTE_LIST.add("format");
		ATTRIBUTE_LIST.add("name");
		ATTRIBUTE_LIST.add("Checked");
		ATTRIBUTE_LIST.add("id");
		ATTRIBUTE_LIST.add("constraint");
		ATTRIBUTE_LIST.add("Selected");
		ATTRIBUTE_LIST.add("readonly");
		ATTRIBUTE_LIST.add("ismandatory");
		ATTRIBUTE_LIST.add("tabindex");
		ATTRIBUTE_LIST.add("maxlength");
		//Added attribute for align change done by pra on 14-may-2009
		ATTRIBUTE_LIST.add("align");
		ATTRIBUTE_LIST.add("type");
		/*
		 * following new attribute added in attribute_list
		changes by  puneet 11-May-2009,12-May-2009 and 13-May-2009 to generate the 
		tags in the zkcode.
		*/
		ATTRIBUTE_LIST.add("visible");
		ATTRIBUTE_LIST.add("multiselect");
		ATTRIBUTE_LIST.add("checkmark");
		/*
		 * following new attribute added in attribute_list
		changes by wasim and puneet 11-May-2009,12-May-2009 and 13-May-2009.
		*/
		
		ATTRIBUTE_LIST.add("draggable");
		ATTRIBUTE_LIST.add("droppable");
		ATTRIBUTE_LIST.add("paginal");
		ATTRIBUTE_LIST.add("pageSize");
		ATTRIBUTE_LIST.add("boxalignment");
		ATTRIBUTE_LIST.add("orient");
		/*
		 * following new attribute added in attribute_list
		changes puneet 29-june-2009.
		*/
		ATTRIBUTE_LIST.add("firstDayOfWeek");
		ATTRIBUTE_LIST.add("mold");
		/*
		 * following new attribute added in attribute_list
		changes harish 28-july-2009.
		*/
		ATTRIBUTE_LIST.add("code");
		ATTRIBUTE_LIST.add("archive");
		ATTRIBUTE_LIST.add("name");
		ATTRIBUTE_LIST.add("value");
		ATTRIBUTE_LIST.add("collapse");
		//added on 09/04/2011
		ATTRIBUTE_LIST.add("colspan");
		ATTRIBUTE_LIST.add("spans");
		ATTRIBUTE_LIST.add("text");
		
		ATTR_LIST.add("top");
		ATTR_LIST.add("left");
		ATTR_LIST.add("position");
		ATTR_LIST.add("datapattern");
		
		/*
		 * following new attribute added in attribute_list
		changes  29-March-2011.
		*/
		
	}
	public static final String CONTENT_FOLDER_NAME = "content";
	public static final String ZK_FOLDER_NAME = "zul";
	public static final String IMG_FOLDER_NAME = "img";
	public static final String CSS_FOLDER_NAME = "css";	
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	
	public static final String CONFIG_FILE_PATH = "/com/oxymedical/component/renderer/uiBuilder/zk/conf/configuration_zk.xml";
	public static final String LIBRARY_FOLDER_PATH = "/com/oxymedical/component/renderer/uiBuilder/zk/library";
	public static final String JUPLOAD_FOLDER_PATH = "/com/oxymedical/component/renderer/jUpload";
	public static final String STYLE_FOLDER_PATH = "/com/oxymedical/component/renderer/uiBuilder/zk/style/";
	public static final String ZUL_FOLDER_PATH = "/com/oxymedical/component/renderer/uiBuilder/zk/zul";
	public static final String LIBRARY_FOLDER_NAME = "library";
	public static final String JUPLOAD_FOLDER_NAME = "JUpload";
	public static final String UI_LIBRARY = "uilibrary.zs";
	public static final String INIT_ZK_SCRIPT = "init.zs";
	public static final String JUPLOAD_ZUL = "jUpload.zul";
	public static final String PARSEREQUEST_JSP = "parseRequest.jsp";
	public static final String JUPLOAD_JAR = "wjhk.jupload.jar";
	public static final String HOW_TO_USE = "how_to_use.txt";
	public static final String TIMEOUT_PAGE = "systimeout.zul";
	public static final String WEB_XML = "web.xml";
	public static final String ZK_XML = "zk.xml";
	public static final String WEBINF_LIB = "lib";
	public static final String APP_INFO = "applicationInfo";
	public static final String ZK_APP_START_TAG = "<zk>";
	public static final String ZK_APP_END_TAG = "</zk>";
	public static final String ZSCRIPT_START_TAG = "<zscript>";
	public static final String ZSCRIPT_END_TAG = "</zscript>";
	public static final String STYLE_START_TAG = "<style>";
	public static final String STYLE_END_TAG = "</style>";
	public static final String ZK_EXTN = ".zul";
	public static final String CSS_EXTN = ".css";
	public static final String COMMON_CSS_FILE = "__common" + ZKConstants.CSS_EXTN;
	public static final String STYLE_ATTR = "class";
	public static final String ATTR_WIDTH = "width";
	public static final String ATTR_HEIGHT = "height";
	public static final String ATTR_MARGIN_LEFT = "margin-left";
	public static final String ATTR_MARGIN_RIGHT = "margin-right";
	public static final String ATTR_FONT_SIZE = "font-size";
	public static final String ATTR_MARGIN_TOP = "margin-top";
	public static final String ATTR_MARGIN_BOTTOM = "margin-bottom";
	public static final String ATTR_PADDING_RIGHT = "padding-right";
	public static final String ATTR_PADDING_LEFT = "padding-left";
	public static final String ATTR_PADDING_TOP = "padding-top";
	public static final String ATTR_PADDING_BOTTOM = "padding-bottom";
	public static final String ATTR_BACKGROUND_IMAGE = "background-image";
	public static final String ATTR_BACKGROUND_COLOR = "background-color";
	public static final String ATTR_POSITION = "position";
	public static final String ATTR_FONT_STYLE = "font-style";
	public static final String ATTR_FONT_FAMILY = "font-family";
	public static final String ATTR_FONT_WEIGHT = "font-weight";
	public static final String ATTR_IS_LOGIN_REQUIRED = "isloginrequired";
	public static final String ATTR_TEXTALIGN = "text-align";
	public static final String ATTR_TEXTDECORATION = "text-decoration";
	public static final String ATTR_SIZABLE = "sizable";
	public static final String ATTR_HFLEX = "hflex";
	public static final String TEXTALIGN = "textalign";
	public static final String BACKCOLOR = "backColor";
	public static final String FORECOLOR = "forecolor";
	public static final String ATTR_BACKGROUND = "background";
	public static final String ATTR_COLOR = "color";
	public static final String ATTR_WHITESPACE = "white-space";
	public static final String LABEL = "Label";
	public static final String SORT_ASC = "sortAscending";
	public static final String SORT_DESC = "sortDescending";
	public static final String GRID_TYPE = "gridType";
	public static final String CHECKMARK = "checkmark";
	public static final String MULTISELECT = "multiselect";
	
	public static final String DATE_PICKER = "DatePicker";
	public static final String COMBO_BOX = "Combobox";
	public static final String CHECK_BOX = "Checkbox";
	public static final String RADIO_GROUP = "RadioGroup";
	public static final String MULTI_TAB = "MultiTab";
	public static final String ATTR_CONSTRAINT ="constraint"; 
	public static final String ATTR_ISMANDATORY = "yes";
	public static final String ATTR_SELECTED = "selected";
	
	public static final String ATTR_LEFT = "left";
	public static final String ATTR_TOP = "top";
	public static final String ATTR_MIDDLE = "middle";
	public static final String ATTR_BOTTOM = "bottom";
	public static final String ATTR_PIXEL = "px";
	
	public static final String ATTR_POINT = "pt";
	
	public static final String GRID = "grid";
	public static final String FRAME = "frame";
	public static final String DIV = "div";
	
	public static final String TREE = "tree";
	public static final String MENU = "menu";
	public static final String TREE_ITEM = "treeitem";
	public static final String TREE_LEAF_ATTR = "isleaf";
	public static final String ROWS_START_TAG = "<rows>";
	public static final String ROWS_END_TAG = "</rows>";
	public static final String TREECHILDREN_START_TAG = "<treechildren>";
	public static final String TREECHILDREN_END_TAG = "</treechildren>";	
	public static final String MENUPOPUP_START_TAG = "<menupopup>";
	public static final String MENUPOPUP_END_TAG = "</menupopup>";	
	public static final String COLUMN = "column";
	public static final String MULTIPLE = "multiple";
	public static final String LIST_BOX = "ListBox";
	public static final String MULTILINE = "multiline";
	public static final String VISIBLE = "visible";
	public static final String USE_DEFAULT_COLOR = "useDefaultColor";
	public static final String RENDER_AS_MENUITEM = "renderAsMenuItem";
	public static final String RENDER_AS_IFRAME = "renderAsIFrame";
	public static final String STYLE = "style";
	public static final String MOLD = "mold";
	public static final String MOLD_ROUNDED = "rounded";
	public static final String RENDER_CURSOR = "renderCursor";
	public static final String ATTR_CURSOR = "cursor";
	public static final String CURSOR_HAND_XML = "[Cursor: System.Windows.Forms.Cursor]";
	public static final String CURSOR_HAND_ZK = "pointer";
	public static final String XML_FONT_FAMILY = "font";
	public static final String XML_FONT_STYLE = "fontstyle";
	public static final String XML_FONT_SIZE = "fontsize";
	

	public static final String EVENTS = "Events";
	public static final String TABBOX = "tabbox";
	public static final String TAB_PAGE = "TabPage";
	public static final String TABS_START_TAG = "<tabs>";
	public static final String TABS_END_TAG = "</tabs>";
	public static final String TAB = "tab";
	public static final String TABPANEL_START_TAG = "<tabpanel";
	public static final String TABPANEL_END_TAG = "</tabpanel>";
	public static final String TABPANELS_START_TAG = "<tabpanels>";
	public static final String TABPANELS_END_TAG = "</tabpanels>";

	public static final String formDesignerPassword = "password";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String MIN = "min";
	public static final String VALUE = "value";
	public static final String ISFORM = "isForm";
	public static final String ID = "id";
	public static final String TEXTBOX = "textbox";
	public static final String TEXT = "text";
	public static final String FORM_ID_PART = "<h:form id =\"";
	public static final String FORM_ACTION_PART = "\" action =\"/";
	public static final String FORM_METHOD_PART = "\" method = \"post\"";
	public static final String FORM_XMLNS_PART = "  xmlns:h=\"http://www.w3.org/1999/xhtml\">";	
	public static final String CONTROLLER_SERVLET = "/ControllerServlet";
	public static final String END_FORM_TAG = "</h:form>";
	public static final String END_DIV_TAG = "</div>";
	

	public static final String zkAttributeTag = "attribute";
	
	public static final String MENU_BAR = "menubar";
	public static final String MENU_ITEM = "menuitem";
	public static final String IFRAME = "iframe";
	public static final String DATE_BOX = "datebox";
	public static final String ROWS = "rows";
	public static final String ELEMENT_NAME = "name";
	public static final String appRootTagName = "application";
	public static final String formPatternsRootTagName = "FormPattern";
	public static final String elementId = "id";
	public static final String DATAPATTERN = "datapattern";
	public static final String DATAPATTERN_NAME = "DataPattern";
	public static final String WIN_ID="window";
	public static final String BOX_ALIGNMENT="boxalignment";
	public static final String HORIZONTAL="horizontal";
	public static final String VERTICAL="vertical";
	
 	//default methods constants
	public static final String TEXTBOX_METHOD = "onChange='setTextboxId()'";
	public static final String DATEBOX_METHOD = "onChange='setDateBoxId()'";
	public static final String CHECKBOX_METHOD = "onCheck='setCheckboxId()'";
	public static final String RADIOGROP_METHOD = "onCheck='setRadioGroupId()'";
	public static final String TIMEBOX_METHOD = "onChanging='setTimeId()'";
	public static final String RADIO_SELECTED_START_METHOD = "onCreate='setRadioSelected(";
	public static final String RADIO_SELECTED_END_METHOD = ")'";
	public static final String TEXTBOX_MANDATORY_FIELD = "onBlur='isMandatoryMethod(self);'";
	public static final String POSITION_RELATIVE = "position:relative;";
	public static final String TIME_PICKER = "Time";
	
	
	// style tag 
	
	public static final String START_STYLE_TAG = "style=\"{";
	public static final String END_STYLE_TAG = "}\" ";
	public static final String START_IMAGE_STYLE_TAG = "style=\"";
	public static final String END_IMAGE_STYLE_TAG = "\" ";
	public static final String EMPTY_STRING = " ";
	public static final String START_TAG = "<";
	public static final String END_TAG = ">";
	public static final String EVENT = "event";
	public static final String ITEM = "Item";
	public static final String REFERENCE = "reference";
	public static final String INCLUDE_START_TAG = "<include src=\"";
	public static final String INCLUDE_END_TAG = ".zul\"/>";
	public static final String LIST_GRID_FIXEDLAYOUT = "fixedLayout= \"true\"";
	public static final String START_STYLE_TAG1 = "style=\"";
	public static final String END_STYLE_TAG1 = "\" ";
	public static final String Button="Button";
	public static final String image="image";
	public static final String Picture="Picture";
	public static final String src="src";
	public static final String SOURCE="source";
	public static final String RADIO_BUTTON = "RadioButton";
	public static final String RENDER_TXT_W_IMG = "RenderTextWithImg";
	
	public static final String LISTHEAD_GRID_SIZEABLE = "sizable=\"true\"";
	
	// xml fontstyle values
	public static final String FONT_STYLE_BOLD = "bold";
	public static final String FONT_STYLE_REGULAR = "Regular";
	public static final String FONT_STYLE_ITALIC = "italic";
	public static final String FONT_STYLE_UNDERLINE = "underline";
	public static final String FONT_STYLE_BOLD_ITALIC = "Bold, Italic";
	public static final String FONT_STYLE_BOLD_UNDERLN = "Bold, Underline";
	public static final String FONT_STYLE_ITALIC_UNDERLN = "Italic, Underline";
	public static final String FONT_STYLE_BOLD_ITALIC_UNDERLN = "Bold, Italic, Underline";
	
	
	// css font-style values
	
	public static final String CSS_STYLE_NORMAL = "normal";
//	public static final String CSS_STYLE_
	//public static final String CSS_STYLE
	
	public static final String APPLICATION_EXTN = ".esp";
	public static final String RENDER_ZK = "ZK";
	public static final String MAIN_FOLDER_NAME = "render";
	public static final String WEBINF_FOLDER_NAME = "WEB-INF";
	public static final String NEW_LINE = System.getProperty( "line.separator" );
	public static final String FORM_PATTERN_FOLDER_NAME = "forms";
	public static final String MAPFILE_NAME = "/MapFile.xml";
	public static final String PATTERN_EXTN = ".xml";
	public static final String ZKNAME = "zkname";
	public static final String APP_ROOT_TAG_NAME = "application";
	public static final String FORM_PATTERN_ROOT_TAG_NAME = "FormPattern";
	public static final String ELEMENT_ID = "id";
	public static final String DEFAULT_ARG = "default";
	public static final String EVENT_TAG_NAME = "event";
	public static final String EVENT_TYPE = "type";
	public static final String SERVER_EVENT_TYPE_VAL = "eib";
	public static final String WEB_FOLDER = "/web";
	public static final String ZKLIB_FOLDER = "/zklib";
	
	public static final String BASE_WINDOW_TAG_NAME = "basewindow";
	public static final String FORM_PATTERN_TAG_NAME = "formpattern";
	public static final String DATA_SETTINGS_PATH = "data/datasettings.xml";
	public static final String UI_SETTINGS_FILE = "uisettings";
	public static final String UI_SETTINGS_PATH = FORM_PATTERN_FOLDER_NAME + "/uisettings" + PATTERN_EXTN;
	public static final String FILE_DIR = "render/zul/";
	public static final String FILE_EXT = ".zul";
	public static final String ON_CLICK = "onClick";
	public static final String ON_SELECT = "onSelect"; 
	public static final String ON_SELECTION = "onSelection";
	public static final String GRID_LISTHEAD = "listhead";
	public static final String ATTR_BORDER = "border";
	public static final String GLOBAL_SCRIPTS = "GlobalScripts";
	public static final String STYLE_SCRIPTS = "StyleScripts";
	public static final String CENTER_DIV_ID_PREFIX = "centerDiv_";
	public static final String BORDER_SIZE = "bordersize";
	public static final String BORDER_COLOR = "bordercolor";
	public static final String BORDER_SOLID = "solid";

	
	
	public static final String GRID_ROW = "Row";
	public static final String GRID_COLUMN = "Column";
	public static final String GRID_LABEL = "Label";
	public static final String GRID_LISTHEADER = "listheader";
	
	public static final String CDATA_TAG_START = "<![CDATA[";
	public static final String CDATA_TAG_END = "]]>";
	public static final String XML_TAG_START = "<XML>"; 
	public static final String XML_TAG_END = "</XML>";
	
	public static final String FORMFIELD_IDENTIFIER = "GUID.";
	public static final String FORMPATTERN_METHOD = "setFormPatternId(\"";
	public static final String DATAPATTERN_METHOD = "setDatapatternId(\"";
	public static final String CLOSE_METHOD = "\");"; 
	public static final String ZK_JARS = "bsh.jar,commons-collections.jar,commons-fileupload.jar,commons-io.jar,commons-logging.jar,fckez.jar,Filters.jar,gmapsz.jar,groovy.jar,itext.jar,jasperreports.jar,jcommon.jar,jfreechart.jar,jruby.jar,js.jar,jxl.jar,jython.jar,mvel.jar,ognl.jar,poi.jar,timelinez.jar,timeplotz.jar,basecomponent.jar,core.jar,framework.jar,logging.jar,zcommon.jar,zcommons-el.jar,zhtml.jar,zk.jar,zkex.jar,zkmax.jar,zkplus.jar,zml.jar,zul.jar,zweb.jar";
	public static final String sourcedir = "sourcedir";
	public static final String serverLibDirectory = "serverLibDirectory";
	public static final String applicationName = "ApplicationName";
	public static final String renderOption = "renderOption";
	public static final String APPLICATION_INFO_FILENAME = "applicationinformation.xml";
	public static final String TAB_INDEX = "tabindex";
	
	
	public static final String VALIDATION_ALPHANUMERIC ="AlphaNumeric";
	public static final String REGX_ALPHANUMERIC ="/[a-zA-z0-9\\s]*/:AlphaNumeric value only";
	public static final String VALIDATION_NUMERIC ="Numeric";
	public static final String REGX_NUMERIC ="/((^[0]{1}$|^[-]?[1-9]{1}\\d*$)|)/:Numeric whole value only";
	public static final String VALIDATION_EMAIL ="Email";
	public static final String REGX_EMAIL ="/(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*([,;]\\s*\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)*|)/:Please enter valid e-mail address";
	public static final String VALIDATION_STRING ="String";
	public static final String REGX_STRING ="/[a-zA-Z\\s]*/:Alphabetical value only";
	public static final String ANY ="Any";
	public static final String REDIRECT_PAGE = "<window><attribute name=\"onCreate\">\n String formName =null;\n formName = session.getWebApp().getAttribute(\"currentPage\");";
	public static final String IFCLAUSE = "if(formName!=null){\nString currentForm = \"zul/\" + formName +\".zul\";\nExecutions.sendRedirect(currentForm);\n}\n else\n {";
	public static final String CLOSINGCLAUSE = "}</attribute></window>";
// Jasper Report
	public static final String JASPER_FILES = "";
	public static final String JASPER_FOLDER_PATH = "/com/oxymedical/component/renderer/jasperReport";
	
	/*
	 * following new constant added in this file 
	changes by wasim and puneet 11-May-2009,12-May-2009 and 13-May-2009.
	*/
	public static final String PAGE_START_TAG= "<?page id=";
	public static final String PAGE_END_TAG= "?>";
	public static final String PAGINAL = "paginal";

	public static final String ZUL_EXT = ".zul";
	public static final String ATTR_NO_REPEAT = "background-repeat:no-repeat";
	public static final String CENTER = "center";
	public static final String STRETCH = "stretch";
	public static final String ATTR_IMAGEBACKGROUND_LAYOUT = "background-position";
	public static final String ATTR_BACKGROUND_SIZE = "background-size";
	public static final String STRETCH_VALUE = "100% 100%";
	public static final String PLACE_HOLDER = "PlaceHolder";
	public static final String HBOX = "hbox";
	public static final String VBOX = "vbox";
	public static final String ATTR_PERCENT = "100%";
	//Following are the new constants added by pra on 14-may-12009
	public static final String POSITION = "position";
	public static final String  RELATIVE = "relative";
	public static final String  X = "x";
	public static final String  Y = "y";
	public static final String  ALIGN = "align";
	public static final String  CONTAINER_ALIGN_NONE = "none";
	public static final String  CONTAINER_ALIGN_LEFT = "left";
	public static final String  CONTAINER_ALIGN_RIGHT = "right";
	public static final String  CONTAINER_ALIGN_CENTER = "center";
	public static final String CSS_META_IE = "<?meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" ?>";
	/*
	 * Following constant added for visibleto custome tag
	 * changes by wasim, 28-May-2009
	 */
	public static final String VISIBLE_TO = "visibleto";
	public static final String CUSTOME_TAG = "<custom-attributes accessrights =\"";
	public static final String CUSTOME_END = "\" />";
	public static final String CUSTOME_ALL = "All";
	
	public static final String ON_CREATE = "onCreate";
	public static final String CLOSE_ATTRIBUTE = "</attribute>";
	public static final String START_ATTRIBUTE = "<attribute name=\"onCreate\">";
	public static final String VALUE_TYPE = "valueType";
	public static final String MIN_TYPE = "minvalue";
	public static final String MAX_TYPE = "maxvalue";
	public static final String VALUE_TYPE_CUSTOM = "<custom-attributes  ";
	
	// Custom attribute for popup form pattern. Added by HS on 31-05-2009
	public static final String CUSTOM_ATT_FOR_POPUP = "<custom-attributes formpattern=\"${param.formpattern}\" datapattern=\"${param.datapattern}\"/>";
	public static final String ARGUMENT_LIST_FOR_POPUP = "?formpattern=____FORMPATTERN_TO_REPLACE____&amp;datapattern=____DATAPATTERN_TO_REPLACE____";
	public static final String STRING_FORMPATTERN_TO_REPLACE = "____FORMPATTERN_TO_REPLACE____";
	public static final String STRING_DATAPATTERN_TO_REPLACE = "____DATAPATTERN_TO_REPLACE____";

	// Default attribute for tabbox. Added by HS on 02-06-2009
	public static final String DEFAULT_TABBOX_ATTRIBUTE = " tabscroll=\"false\"";
	public static final String MULTITAB_MOLD_ATTRIBUTE = " mold=\"accordion\"";
	
	//These constants are for the toolbar in case of schedular added by pra on 24-06-2009
	public static final String TOOLBAR_SCH_FOLDER_PATH = "/com/oxymedical/component/renderer/schedular/";
	public static final String TOOLBAR_FILE_NAME = "toolbar.zul";
	public static final String SCHEDULAR = "calendars";
	public static final String TOOLBAR_CSS = "toolbar.css";
	public static final String TOOLBAR_COMPONENT_ARROW = "<?component name=\"divarrow\" macro-uri=\"divarrow.zul\"?>";
	public static final String TOOLBAR_COMPONET_TAB = "<?component name=\"divtab\" macro-uri=\"divtab.zul\"?>";
	public static final String TOOLBAR_COMPONET_TAB_FILENAME = "divtab";
	public static final String TOOLBAR_COMPONENT_ARROW_FILENAME = "divarrow";
	public static final String MENUICON = "menuicon";
	//These constant for the message template folder. changes by Wasim Khan,24-july-2009.
	public static final String TEMPLATE_FOLDER_NAME = "templates";	
	public static final String TEMPLATE_PATH = "/com/oxymedical/component/renderer/template/";
	public static final String TEMPLATE_FILE = "OMMessagebox.zul";
	public static final String APPLET_FOLDER_NAME = "applet";
	//add new constants for popup
	public static final String START_STYLE_POPUP_TAG = "style=\"";
	public static final String END_STYLE_POPUP_TAG = "\" ";
	public static final String VISUALIZER_FOLDER_NAME = "Visualizer";

	// Type of Server
	public static final String GLASSFISH_SERVER_IDENTIFIER = "glassfish";
	public static final String SPLITTER="Splitter";
	
	//Adding condition for DGrid,DRow,Dcolumn
	public static final String DGRID="DGrid";
	public static final String DCOLUMN="Dcolumn";
	public static final String DROWS="Drows";
	public static final String DROW="Drow";
	public static final String AUXHEAD="auxhead";
	public static final String AUXHEADER="auxheader";		
	public static final String COLUMNS="columns";
	
	// Adding constants for Tree_Structure
	public static final String TREE_LEAF="isleaf";
	public static final String TREECHILDREN_END_TAG_CLOSE = "<treechildren/>";
	public static final String TREE_NODE="TreeNode";
	public static final String TREE_COLS="treecols";
	public static final String TREE_COL = "treecol";
	public static final String TREE_COLS_END_TAG_CLOSE = "</treecols>";
	public static final String TREE_COL_END_TAG_CLOSE = "</treecol>";
	
	
	
	
	
	
	
	
	
	
}
