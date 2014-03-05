package com.oxymedical.component.renderer.jasperReport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.oxymedical.component.renderer.data.HibernateQueryResultDataSource;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer; 

public class JasperCompiler {

	public static void main(String[] args) throws SQLException, FileNotFoundException,
	IllegalAccessException, ClassNotFoundException,Exception
	{
		
		 try {
	            JasperCompileManager.compileReportToFile(
	                    "src/com/oxymedical/component/renderer/jasperReport/SDVDesign.jrxml",
	                    "src/com/oxymedical/component/renderer/jasperReport/SDV.jasper");
	           /* JasperCompileManager.compileReportToFile(
	                    "src/com/oxymedical/component/renderer/jasperReport/DoctorDesign.jrxml",
	                    "src/com/oxymedical/component/renderer/jasperReport/Physician.jasper");*/
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	public static void compileXML()
	{
		try {
	        JasperCompileManager.compileReportToFile(
	                "src/com/oxymedical/component/renderer/jasperReport/jasperDesign.jrxml",
	                "src/com/oxymedical/component/renderer/jasperReport/Patient.jasper");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
		
}
