package com.oxymedical.component.rulesComponent.util;

import java.util.Hashtable;
import java.util.Stack;

import com.oxymedical.component.rulesComponent.RuleComponent;

public class RuleComponentUtil {
	
	private static Hashtable<String, String> types;
	
	
	public static Hashtable<String, String> getTypes() {
		return types;
	}

	public static void setTypes(Hashtable<String, String> typesTable) {
		types = typesTable;
	}
	
	/**
	 * Gets the name of the class from the variable list for the attribute name specified
	 * 
	 * @param variableList
	 * @param attributeName
	 * @return
	 */
	public static String getClassName(String id) {
		return (String)types.get(id);
	}
	
	/**
	 * Truncates extra space between operators from the given string
	 * 
	 * TODO find an alternate method to remove the space before and after operators
	 * 
	 * @param str
	 * @return
	 */
	public static String truncateExtraSpace(String str) {
		String truncatedStr = str;
		truncatedStr = truncatedStr.replaceAll(" = ", "=");
		truncatedStr = truncatedStr.replaceAll(" =", "=");
		truncatedStr = truncatedStr.replaceAll("= ", "=");
		
		truncatedStr = truncatedStr.replaceAll(" > ", ">");
		truncatedStr = truncatedStr.replaceAll("> ", ">");
		truncatedStr = truncatedStr.replaceAll(" >", ">");
		
		truncatedStr = truncatedStr.replaceAll(" < ", "<");
		truncatedStr = truncatedStr.replaceAll("< ", "<");
		truncatedStr = truncatedStr.replaceAll(" <", "<");
		
		truncatedStr = truncatedStr.replaceAll(" >= ", ">=");
		truncatedStr = truncatedStr.replaceAll(" >=", ">=");
		truncatedStr = truncatedStr.replaceAll(">= ", ">=");
		
		truncatedStr = truncatedStr.replaceAll(" <= ", "<=");
		truncatedStr = truncatedStr.replaceAll("<= ", "<=");
		truncatedStr = truncatedStr.replaceAll(" <=", "<=");
		
		truncatedStr = truncatedStr.replaceAll(" != ", "!=");
		truncatedStr = truncatedStr.replaceAll(" !=", "!=");
		truncatedStr = truncatedStr.replaceAll("!= ", "!=");
		
		truncatedStr = truncatedStr.replaceAll(" == ", "==");
		truncatedStr = truncatedStr.replaceAll(" ==", "==");
		truncatedStr = truncatedStr.replaceAll("== ", "==");
		return truncatedStr;
	}
	
	/**
	 * Adds an extra space before and after operator.
	 * 
	 * @param str
	 * @return
	 */
	public static String addExtraSpaceForOperator(String str) {
		String addedStr = str;
		str.replaceAll("&&", " && ");
		str.replaceAll("||", " || ");
		return addedStr;
	}
	
	/**
	 * Replaces the id of the obejct by the name, i.e the name of the class before the id
	 * 
	 * @param str
	 * @param replaceStr
	 * @return
	 */
	public static String replaceStringForObjectName(String str, String replaceStr)
	{
		return str.replaceFirst(str.substring(0,str.indexOf(".")), replaceStr);
	}

	/**
	 * Evaluates the conditon expression as per the precedence
	 * 
	 * @param expression
	 * @return
	 */
	public static Hashtable<String, String> evaluateExpression(String expression) {
		Hashtable<String, String> expHash = new Hashtable<String, String>();
		Stack s = new Stack();
		char ch[] = expression.toCharArray();
		Object ob;
		for (int i=0, j=1; i<ch.length; i++) 
		{
			if (ch[i] == ')')
			{	
				ob = s.pop().toString();
				if (ob.equals("(")){
					s.push('(');
					s.push(ch[i]);
				} else {		
				
					String st="";
					
					s.push(ob);
					if(st.equals("")){
						st+=(Object)s.pop().toString();
					}
					while ((!((ob=s.pop().toString()).equals("("))) || (st.charAt(st.length()-1)+"").equals(")")) 
					{					
						st+=ob.toString();				
					}
					st = st.trim();
					if (!st.equals("") && st.length()>0) {
						//String newSt = reverse(st);	
						expHash.put("e"+j, reverse(st));
						// RuleComponent.logger.log(0,"e"+j+" = "+ newSt );
						s.push(j+"e");
						j++;
					}
				}
			}
			else 
			{
				s.push(ch[i]);				
			}
		}
		return expHash;
	}

	/**
	 * Reverses a string 
	 * 
	 * @param st
	 * @return
	 */
	private static String reverse(String st) {
		char ch[] = st.toCharArray();
		String str = "";
		for(int i=ch.length-1; i>=0; i--) {
			str+=ch[i];
		}
		return str;
	}


}
