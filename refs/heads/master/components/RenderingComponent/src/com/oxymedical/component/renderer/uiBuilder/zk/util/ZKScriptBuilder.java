package com.oxymedical.component.renderer.uiBuilder.zk.util;

import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

import com.oxymedical.component.renderer.RendererComponent;
import com.oxymedical.component.renderer.exception.RendererComponentException;
import com.oxymedical.core.stringutil.Tokenizer;
import com.oxymedical.core.xmlutil.XmlReader;

/**
 * this class is used to parse and generate the Client Script
 *
 */
class ZKScriptBuilder 
{
	XmlReader xmlReader = null;
	Document document = null;
	//UIConfig config = new UIConfig();
	String windowIndex="";
	
	public ZKScriptBuilder() {
		xmlReader = new XmlReader();		
	}
	
	class ObjectProperty
	{
			String objStr;
			String propertyStr;
	}	
	
	String parse(String expresession) throws RendererComponentException
	{
		String retStr="";
		String[] statements;
		
		//start the parsing. Statements are seperated by semi colon
		statements = expresession.split(";");
		
		for(int i=0;i<statements.length;i++)
		{
			if(statements[i].trim().contains("update"))
				retStr = retStr + parseStatement(statements[i]);
			else
			    retStr = retStr + parseStatement(statements[i]) + ";";
			
		}
		return retStr;
	}
	
	private String parseStatement(String statement)  throws RendererComponentException
	{
		if(statement.indexOf("=")>0 && statement.indexOf(".")>0 && (statement.indexOf("==")<0 || statement.indexOf("=")<statement.indexOf("=="))  && (statement.indexOf(":=")<0 || statement.indexOf("=")<statement.indexOf(":=")))
		{
			return parseObjectPropertyAssignment(statement);
		}
		else
		{
			 return parseRhs(statement);
		}
	}
	
	private String parseObjectPropertyAssignment(String statement) throws RendererComponentException
	{
		String retStr = "", lhs, rhs, rhsVal="";
		String[] exprArr;
		ObjectProperty objProp = null, objProp2 = null;
		exprArr = statement.split("=", 2);
		lhs = exprArr[0].trim();
		rhs = exprArr[1].trim();
		
		// parse lhs
		
		if(lhs.indexOf(".")>0)
			objProp = parseObjectProperty(lhs);
		
		//parse rhs
		
		rhsVal = this.parseRhs(rhs); 
		
		/*String[] idenf = p.split(rhs);
		
		for(int i=0;i<idenf.length;i++)
		{
			if(idenf[i].indexOf(".")>0)
			{
				objProp2 = parseObjectProperty(idenf[i]);
				if(rhsVal=="")
					rhsVal = objProp2.objStr + "." + objProp2.propertyStr;
				else
					rhsVal = rhsVal + " " + objProp2.objStr + "." + objProp2.propertyStr;
			}
			else
			{
				if(rhsVal=="")
					rhsVal = rhsVal + idenf[i];
				else
					rhsVal = rhsVal + " " + idenf[i];
			}
		}
		*/
		if(objProp==null)
			retStr = lhs + " = " + rhsVal;
		else
			retStr = objProp.objStr + ".setAttribute(" + objProp.propertyStr + "," + rhsVal + ")";
		//	retStr = objProp.objStr + ".setAttribute(\"" + objProp.propertyStr + "\"," + rhsVal + ")";
		
		return retStr;
	}
	
	private String parseRhs(String rhs) throws RendererComponentException
	{
		String REGEX = "[)/+*(-]";
		ObjectProperty objProp2;
		String rhsVal = "";
		boolean returnDelims = true;
		Tokenizer tokenizer = new Tokenizer(rhs, REGEX, returnDelims);
		
		//Pattern p = Pattern.compile(REGEX);
		String idenf="";
	    for (; tokenizer.hasNext(); ) 
	    {
	    	if(!(tokenizer.isNextToken()))
	    	{
	    		idenf = (String)tokenizer.next();	
	    		if(idenf.indexOf(".")>0 && !(idenf.startsWith("\"")))
				{
	    			objProp2 = parseObjectProperty(idenf);
	    			rhsVal = rhsVal + objProp2.objStr + "." + objProp2.propertyStr;
	    		}
	    		else
	    		{
	    			//if()
	    			if(rhsVal.trim().contains("update"))
	    			{
	    				if(idenf.indexOf('"') >= 0)
	    					idenf = idenf.substring(1, idenf.length()-1);
	    			}
	    			rhsVal = rhsVal + idenf;
	    		}
	    	}
	    	else
			{
	    		idenf = (String)tokenizer.next();
	    		rhsVal = rhsVal + idenf;
	        }
	    }	    
	    return rhsVal;
	}
	private ObjectProperty parseObjectProperty(String statement) throws RendererComponentException
	{
		String REGEX = ".";
		Pattern p = Pattern.compile(REGEX,  Pattern.LITERAL);
		String[] idenf = p.split(statement);
		String objStr="", attributeToSet="";
		String lastObject="";
		for(int i=0;i<idenf.length;i++)
		{
			if(i==idenf.length-1)
			{
				/*if(lastObject.equals("this"))
					//attributeToSet = zkBuilderUtility.getAttributeNameByTag(tagName, idenf[i].trim());
				else
					//attributeToSet = zkBuilderUtility.getAttributeName(lastObject, idenf[i]);
*/				
			}
			else
			{
				if(objStr.equals(""))
				{
					if(idenf[i].trim().equals("this"))
						objStr = idenf[i].trim();
					else
					{
						if(elementExists(idenf[i].trim()))
							objStr = windowIndex + idenf[i].trim();
						else
							throw new RendererComponentException("Element '" + idenf[i] + "' does not exists.");
					}
						
			}
				//else
				//	objStr = objStr + "." + idenf[i].trim();
				
				lastObject = idenf[i].trim();
			}
		}
		ObjectProperty objPr = new ObjectProperty();
		objPr.objStr = objStr;
		objPr.propertyStr = attributeToSet;
		return objPr;
	}
	
	private boolean elementExists(String objName)
	{
		if (null!= document){
		 RendererComponent.logger.log(0,"Inside ZKScriptBuilder :document" + document);
		}else{
			RendererComponent.logger.log(0,"Inside ZKScriptBuilder :document null");
		}
		RendererComponent.logger.log(0,"Inside ZKScriptBuilder :objName" + objName);
		Element elt = xmlReader.getElementByAttribute(document.getRootElement(), "name", objName);
		if(elt==null)
			return false;
		else
			return true;
	}
	
	public static void main(String args[]) throws RendererComponentException
	{
		ZKScriptBuilder cb = new ZKScriptBuilder();
		RendererComponent.logger.log(0,cb.parse("a.b = 5 - text1.showList() ;"));
	}
	
}
