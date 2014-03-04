/**
 * 
 */
package com.oxymedical.component.db.utilities;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.constants.RegisterConstants;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.renderdata.DataUnit;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.xmlutil.XmlReader;

/**
 * @author vka
 * 
 */
public class DBUtilities {
	private XmlReader xmlReader = null;
	private Document configDoc, document;
	Element elt = null;
	protected LinkedHashMap colOrder;

	private Hashtable<String, String> hashTab = new Hashtable<String, String>();

	int i = 0;

	public DBUtilities() {
		xmlReader = new XmlReader();

	}

	/**
	 * Gets the form pattern xml document
	 * 
	 * @param IApplication
	 *            application
	 * @param String
	 *            formPatternId
	 * @return formPatternDoc
	 * @throws DBComponentException
	 */
	public Document getFormPatternXmlDoc(IApplication application,
			String formPatternId) throws DBComponentException {
		Document formPatternDoc = null;
		try {
			String formPatternXmlFileSrc = application
					.getApplicationFolderPath()
					+ RegisterConstants.FORM_PATTERN_FOLDER_NAME
					+ "/"
					+ formPatternId + RegisterConstants.PATTERN_EXTN;
			// DBComponent.logger.log(0," formPatternXmlFileSrc = "
			// +formPatternXmlFileSrc);
			formPatternDoc = xmlReader
					.getDocumentFromPath(formPatternXmlFileSrc);
		} catch (Exception fileExp) {
			throw (new DBComponentException(fileExp.getMessage()));
		}
		return formPatternDoc;
	}

	public static String[] getConnectionSettings(String dbSettingsFile)
			throws DBComponentException {
		String connectionSettings[] = { "", "", "", "" };
		String driverClass, serverName, user, pwd;

		XmlReader xmlReader = new XmlReader();
		Document databaseConfig = xmlReader.getDocumentFromPath(dbSettingsFile);

		Element el = databaseConfig.getRootElement();
		// connUrl = "jdbc:mysql://localhost:3306/datapattern0";
		driverClass = "com.mysql.jdbc.Driver";
		serverName = el.element("servername").getTextTrim();
		user = el.element("user").getTextTrim();
		pwd = el.element("password").getTextTrim();
		connectionSettings[0] = serverName;
		connectionSettings[1] = driverClass;
		connectionSettings[2] = user;
		connectionSettings[3] = pwd;

		return connectionSettings;
	}

	public Hashtable<String, List<String>> retrieveRowValuesFromXML(
			String responseXML, IData requestData) throws DBComponentException {

		Hashtable<String, List<String>> rowValues = new Hashtable<String, List<String>>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		colOrder = requestData.getColumnOrder();

		Hashtable<String, Hashtable<String, String>> colRecord = new Hashtable<String, Hashtable<String, String>>();

		DocumentBuilder parser = null;
		try {
			parser = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new DBComponentException(
					"Unable to create DocumentBuilder object:" + e);
		}

		// Parse the file and build a Document tree to represent its content
		org.w3c.dom.Document document = null;
		try {
			document = parser.parse(new InputSource(new StringReader(
					responseXML)));
		} catch (SAXException e) {
			throw new DBComponentException("Unable to parse document:"
					+ e.getMessage());
		} catch (IOException e) {
			throw new DBComponentException("Unable to parse document:"
					+ e.getMessage());
		}

		org.w3c.dom.NodeList root = document.getElementsByTagName("records");
		org.w3c.dom.Node rootElem = root.item(0);
		org.w3c.dom.NodeList recordList = rootElem.getChildNodes();

		for (int i = 0; i < recordList.getLength(); i++) {
			org.w3c.dom.Node recordElem = recordList.item(i);
			org.w3c.dom.NamedNodeMap recordAttrList = recordElem
					.getAttributes();
			List<String> displayValues = new ArrayList<String>();
			String idValue = null;

			for (int j = 0; j < recordAttrList.getLength(); j++) {

				org.w3c.dom.Node attr = recordAttrList.item(j);

				if (attr.getNodeName().equals(
						requestData.getQueryData().getIdField())) {
					DBComponent.logger.log(0, "id-field name is:"
							+ attr.getNodeValue());
					idValue = attr.getNodeValue();

				} else {
					DBComponent.logger.log(0, "display-field :" + j + " is :"
							+ attr.getNodeValue());
					displayValues.add(attr.getNodeValue());
				}

			}
			
			if (idValue == null) {
				
				rowValues.put(displayValues.get(0), displayValues);
			} else {
				rowValues.put(idValue, displayValues);
			}
		}

		return rowValues;

	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> retrieveValuesFromXML(
			String responseXML, IData requestData) throws DBComponentException {
		int colIndex = 0;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		colOrder = requestData.getColumnOrder();
		// Hashtable<String, Hashtable<String, String>> colRecord = new
		// Hashtable<String, Hashtable<String, String>>();
		LinkedHashMap<String, LinkedHashMap<String, String>> colRecord = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		DocumentBuilder parser = null;
		try {
			parser = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new DBComponentException(
					"Unable to create DocumentBuilder object:" + e);
		}

		// Parse the file and build a Document tree to represent its content
		org.w3c.dom.Document document = null;
		try {
			document = parser.parse(new InputSource(new StringReader(
					responseXML)));
		} catch (SAXException e) {
			throw new DBComponentException("Unable to parse document:"
					+ e.getMessage());
		} catch (IOException e) {
			throw new DBComponentException("Unable to parse document:"
					+ e.getMessage());
		}

		org.w3c.dom.NodeList root = document.getElementsByTagName("records");
		org.w3c.dom.Node rootElem = root.item(0);
		org.w3c.dom.NodeList recordList = rootElem.getChildNodes();
		DBComponent.logger
				.log(
						0,
						"===============================ListOutput in xml formate===============================");
		DBComponent.logger.log(0, "==responseXML==" + responseXML);

		int counterValue = 0;
		for (int i = (recordList.getLength() - 1); i >= 0; i--) {
			org.w3c.dom.Node recordElem = recordList.item(i);

			org.w3c.dom.NamedNodeMap recordAttrList = recordElem
					.getAttributes();
			// Hashtable<String, String> rowData = new Hashtable<String,
			// String>();
			LinkedHashMap<String, String> rowData = new LinkedHashMap<String, String>();
			colIndex = 0;
			// counterValue=0;
			Object[] rowIds = colOrder.keySet().toArray();
			for (int k = 0; k < rowIds.length; k++) {
				String field = (String) colOrder.get(rowIds[k]);
				for (int j = 0; j < recordAttrList.getLength(); j++) { // counterValue=0;
					org.w3c.dom.Node attr = recordAttrList.item(j);
					// Object[] rowIds = colOrder.keySet().toArray();
					if (attr.getNodeName().equals(field)) {
						rowData.put(field, attr.getNodeValue());
						// counterValue++;
						colIndex++;
					} else {

						if (attr.getNodeName().equalsIgnoreCase("text")) {

							rowData.put(field, attr.getNodeValue());
						}
						colIndex++;
					}
				}
				/*
				 * for (int k = 0; k < colOrder.size(); k++) { if
				 * (attr.getNodeName().equals(colOrder.get(colIndex))) {
				 * rowData.put(String.valueOf(colIndex), attr.getNodeValue());
				 * //counterValue++; colIndex++; } else {
				 * 
				 * if(attr.getNodeName().equalsIgnoreCase("text")) {
				 * 
				 * rowData.put(String.valueOf(colIndex), attr.getNodeValue()); }
				 * colIndex++; } }
				 */
			}
			colRecord.put(String.valueOf(i), rowData);
		} 
		DBComponent.logger.log(0, "--********colRecord value****------"
				+ colRecord);
		return colRecord;
	}

}
