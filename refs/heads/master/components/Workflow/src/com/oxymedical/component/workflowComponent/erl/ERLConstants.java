package com.oxymedical.component.workflowComponent.erl;

public interface ERLConstants
{
	static final String workflowNodeSep = "QWERTYUIOP";
	
	int EOF = 0;
	int LA = 4;
	int RA = 5;
	int SLASH = 6;
	int openruleset = 7;
	int openrulesetattr = 8;
	int endruleset = 9;
	int openvariables = 10;
	int endvariables = 11;
	int openvariable = 12;
	int openvariableattr = 13;
	int endvariable = 14;
	int openType = 15;
	int endType = 16;
	int openID = 17;
	int endID = 18;
	int openrule = 19;
	int openruleattr = 20;
	int endrule = 21;
	int opensalience = 22;
	int endsalience = 23;
	int openif = 24;
	int endif = 25;
	int openconditionlist = 26;
	int endconditionlist = 27;
	int opencondition = 28;
	int openconditionattr = 29;
	int endcondition = 30;
	int openoperator = 31;
	int openoperatorattr = 32;
	int endoperator = 33;
	int openthen = 34;
	int endthen = 35;
	int openurl = 36;
	int endurl = 37;
	int openconsequencelist = 38;
	int endconsequencelist = 39;
	int openconsequence = 40;
	int openconsequenceattr = 41;
	int endconsequence = 42;
	int DIGITS = 43;
	int WORD = 44;
	int SPC = 45;
	int CRLF = 46;
	int ESC = 47;

	int DEFAULT = 0;

	String[] tokenImage = {
			"<EOF>",
			"\\n",
			"\\r",
			"\\t",
			"<",
			">",
			"/",
			"<ruleset>",
			"<ruleset name=\"",
			"</ruleset>",
			"<variable-list>",
			"</variable-list>",
			"<variable>",
			"<variable name=\"",
			"</variable>",
			"<type>",
			"</type>",
			"<id>",
			"</id>",
			"<rule>",
			"<rule name=\"",
			"</rule>",
			"<salience>",
			"</salience>",
			"<if>",
			"</if>",
			"<condition-list>",
			"</condition-list>",
			"<condition>",
			"<condition name=\"",
			"</condition>",
			"<operator>",
			"<operator name=\"",
			"</operator>",
			"<then>",
			"</then>",
			"<url>",
			"</url>",
			"<consequence-list>",
			"</consequence-list>",
			"<consequence>",
			"<consequence name=\"",
			"</consequence>",
			"<DIGITS>",
			"<WORD>",
			"<SPC>",
			"<CRLF>",
			"<ESC>",
	};

}
