package com.oxymedical.component.renderer.uiBuilder.zk.library;


import java.io.File;
import java.net.InetAddress;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.zk.device.Devices;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;
import org.zkoss.zk.ui.Desktop;





import com.oxymedical.component.renderer.constants.ZKConstants;
import com.oxymedical.core.dateutil.DateUtil;
import com.oxymedical.core.propertyUtil.PropertyUtil;

public class UILibraryTest {
	
	 private static int SUCCESS = 0;
	    private static int FAILURE = 1;
	    private static int MISSING_INFO = -1;
	    public static String ALL_STRING_REGEX = "[/w]+";
	    public static String ALL_NUMBER_REGEX = "[0-9]+";
	     
	public static void main(String[] argv)
	{
	 /* Date currentDate= new Date();
	  String time = DateUtil.formatDate(currentDate,"yyyy-MM-dd,HH:mm:ss").split(",")[1];
	  System.out.println("to date--"+time);
		String aa = null;
		Textbox t= new Textbox();
		//t.setVisible(visible);
		Combobox cb = new Combobox();
		cb.setReadonly(true);*/
		String abc="c:\\abbc\\ss\\dd\\";
		System.out.println(abc.replaceAll("\\\\","/"));
		Radiogroup rga= new Radiogroup();
		rga.setSelectedIndex(0);
	}
	
	public static boolean emailCheck(String str) {
		String at="@";
		String dot=".";
		int lat=str.indexOf(at);
		int lstr=str.length();
		int ldot=str.indexOf(dot);
    
	    // check if '@' is at the first position or 
         //   at last position or absent in given email 
		
		if (str.indexOf(at)==-1 || str.indexOf(at)==0 ||
                str.indexOf(at)==lstr){
		   
		   return false;
		}
        // check if '.' is at the first position or at last 
      //  position or absent in given email
		
		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 ||
                str.indexOf(dot)==lstr){
		    
		    return false;
		}
        // check if '@' is used more than one times in given email
		
		if (str.indexOf(at,(lat+1))!=-1){
		    return false;
		 }
   
         // check for the position of '.'
		
		 if (str.substring(lat-1,lat)==dot || 
                 str.substring(lat+1,lat+2)==dot){
		    
		    return false;
		 }
         // check if '.' is present after two characters 
       //  from location of '@'
		 if (str.indexOf(dot,(lat+2))==-1){
		    
		    return false;
		 }
		
		 // check for blank spaces in given email
		 if (str.indexOf(" ")!=-1){
		    return false;
		 }
 		 return true;					
	}

	public static boolean isString(String textObj) {
		   
		   int newLength = textObj.length();
		   String extraChars=". -,";

		   int search;
		   for(int i = 0; i != newLength; i++) {
		     String aChar = textObj.substring(i,i+1);
		      aChar = aChar.toUpperCase();
		      search = extraChars.indexOf(aChar);
		     /* if(search == -1 && (aChar < "A" || aChar > "Z") ) {
		         return false;
		      }*/
		   }
		   return true;
		}
	
	
	public String getSelectedListData()
	{
		
		
		
		
		Listbox lb = new Listbox();
		Listitem li = lb.getSelectedItem();
		lb.removeChild(li);
		List list =lb.getChildren();
		
		Iterator itr = list.iterator();
		while(itr.hasNext())
		{
			Listitem lis = (Listitem)itr.next();
			String lable = lis.getLabel();
			if(lable.equalsIgnoreCase(""))
			{
				lb.removeChild(lis);
				break;
			}
			
		}
		
		Combobox cbox = new Combobox();
		Comboitem child = new Comboitem();
		child.setLabel("new");
		cbox.appendChild(child);
		
		lb.removeAttribute("");
		String value = li.getLabel();
		String output="";
		
		Listitem clist = new Listitem();
		clist.setLabel("Khan");
		lb.appendChild(clist);
		return output;
	}

	
	int rowValue=121;
	int counter=0;
	Hbox hObj = null;
	
	
	
	
	public void addNewComponent(String winId)
	{
		
		int leftValue=155;
		int marginValue =25;
		/*Object rootFormValue = self.getRoot();
		Object obj =rootFormValue.getVariable(winId, true);
		Object rem =rootFormValue.getVariable(self.id, true);
		
		*/
		Window win = new Window();
		if(hObj==null)
		{
			hObj = new Hbox();
			hObj.setId("horzontalBox");
			win.appendChild(hObj);
			
		}
		else
		{
		//	Object obj =rootFormValue.getVariable("horzontalBox", true);
		}
		
		
		Div div = new Div();
		String id = "div"+counter;
		div.setId(id);
		div.setStyle("position:relative");
		hObj.appendChild(div);
		
		rowValue = rowValue+35;
		String top="top:"+rowValue+"px";
		String left = "left:"+leftValue+"px";
		String style = top +";align:None;margin-top:3px;font:Microsoft Sans Serif;font-style:normal;padding-right:0px;padding-left:0px;padding-top:0px;color:#000000;font-size:8.25pt;margin-bottom:3px;position:absolute;margin-right:3px;margin-left:3px;padding-bottom:0px;visibility:true;"+left;
			
		Button button = new Button();
		button.setLabel("Remove");
		button.setHeight("25px");
		button.setWidth("65px");
		button.setStyle(style);
		id = "remove"+counter;
		button.setId(id);
		button.addEventListener("onClick", new EventListener()
			        {
					    public void onEvent(Event event1) throws Exception 
					    {
					    	//deleteComponent(id);
					    }
	        });
		
		
		div.appendChild(button);
		leftValue = leftValue+ 55+marginValue;
		
		Combobox cbox = new Combobox();
		Comboitem child = new Comboitem();
		child.setLabel("and");
		cbox.appendChild(child);
		Comboitem child1 = new Comboitem();
		child.setLabel("or");
		cbox.appendChild(child);
		left = "left:"+(leftValue)+"px";
		cbox.setWidth("35px");
		id = "logical"+counter;
		cbox.setId(id);
		style = top +";align:None;margin-top:3px;font:Microsoft Sans Serif;font-style:normal;padding-right:0px;padding-left:0px;padding-top:0px;color:#000000;font-size:8.25pt;margin-bottom:3px;position:absolute;margin-right:3px;margin-left:3px;padding-bottom:0px;visibility:true;"+left;

		cbox.setStyle(style);
		div.appendChild(cbox);
		
		leftValue = leftValue+ 43+marginValue;
		Combobox cbox1 = new Combobox();
		left = "left:"+(leftValue)+"px";
		cbox.setWidth("90px");
		id = "category"+counter;
		cbox.setId(id);
		style = top +";align:None;margin-top:3px;font:Microsoft Sans Serif;font-style:normal;padding-right:0px;padding-left:0px;padding-top:0px;color:#000000;font-size:8.25pt;margin-bottom:3px;position:absolute;margin-right:3px;margin-left:3px;padding-bottom:0px;visibility:true;"+left;
		
		cbox.setStyle(style);
		div.appendChild(cbox);
		
		Combobox cbox11 = new Combobox();
		cbox1.setWidth("90px");
		id = "field"+counter;
		cbox1.setId(id);
		leftValue = leftValue+ 100+marginValue;
		left = "left:"+(leftValue)+"px";
		style = top +";align:None;margin-top:3px;font:Microsoft Sans Serif;font-style:normal;padding-right:0px;padding-left:0px;padding-top:0px;color:#000000;font-size:8.25pt;margin-bottom:3px;position:absolute;margin-right:3px;margin-left:3px;padding-bottom:0px;visibility:true;"+left;
		cbox1.setStyle(style);
		div.appendChild(cbox1);
		
		Textbox txtbox = new Textbox();
		leftValue = leftValue+ 98+marginValue;
		left = "left:"+(leftValue)+"px";
		style = top +";align:None;margin-top:3px;font:Microsoft Sans Serif;font-style:normal;padding-right:0px;padding-left:0px;padding-top:0px;font-size:8.25pt;margin-bottom:3px;position:absolute;margin-right:3px;margin-left:3px;padding-bottom:0px;visibility:true;"+left;
		txtbox.setStyle(style);
		txtbox.setWidth("100px");
		txtbox.setHeight("20px");
		id = "variable"+counter;
		txtbox.setId(id);
			
		div.appendChild(txtbox);
		
		
		
		leftValue = leftValue+ 90+marginValue;
		
		Textbox txtbox11 = new Textbox();
		left = "left:"+(leftValue)+"px";
		style = top +";align:None;margin-top:3px;font:Microsoft Sans Serif;font-style:normal;padding-right:0px;padding-left:0px;padding-top:0px;font-size:8.25pt;margin-bottom:3px;position:absolute;margin-right:3px;margin-left:3px;padding-bottom:0px;visibility:true;"+left;
		txtbox.setStyle(style);
		txtbox.setWidth("40px");
		txtbox.setHeight("20px");
		id = "firstrange"+counter;
		txtbox.setId(id);
		
		div.appendChild(txtbox);
		
		Label lab = new Label();
		leftValue = leftValue+ 25+marginValue;
		left = "left:"+(leftValue)+"px";
		style = top +";align:None;margin-top:3px;font:Microsoft Sans Serif;font-style:normal;padding-right:0px;padding-left:0px;padding-top:0px;font-size:8.25pt;margin-bottom:3px;position:absolute;margin-right:3px;margin-left:3px;padding-bottom:0px;visibility:true;"+left;
		lab.setValue("to");
		lab.setWidth("20px");
		lab.setHeight("21px");
		lab.setStyle(style);
		id = "to"+counter;
		lab.setId(id);
		
		div.appendChild(lab);
		leftValue = leftValue+marginValue;
		Textbox txtbox1 = new Textbox();
		left = "left:"+(leftValue)+"px";
		style = top +";align:None;margin-top:3px;font:Microsoft Sans Serif;font-style:normal;padding-right:0px;padding-left:0px;padding-top:0px;color:#000000;font-size:8.25pt;margin-bottom:3px;position:absolute;margin-right:3px;margin-left:3px;padding-bottom:0px;visibility:true;"+left;
		txtbox1.setStyle(style);
		txtbox1.setWidth("40px");
		txtbox1.setHeight("20px");
		id = "secondrange"+counter;
		txtbox1.setId(id);
		div.appendChild(txtbox1);
		counter = counter+1;
			
					
	}

	
	
	
	

}
