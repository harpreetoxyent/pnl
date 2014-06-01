<%@ page language="java" import="java.io.*, java.sql.*, java.util.*, java.text.DateFormat, java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.fileupload.*, org.apache.commons.fileupload.disk.*, org.apache.commons.fileupload.servlet.*" %>
<%
	
	// Directory to store all the uploaded files
    String uploadDir_OnServer = "d:/AlfrescoFiles/";
	
	//getting the upload directory defined by the user
	if(session.getAttribute("multiUploadDir") != null){
		String userDefDir = session.getAttribute("multiUploadDir").toString();
		if(userDefDir.length() > 0){
			uploadDir_OnServer = userDefDir;
		}
	}
	
	//creating a directory for todays date
	boolean createdUploadDirSuccess = true;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Calendar currentDate = Calendar.getInstance();
	String todayDateStr = dateFormat.format(currentDate.getTime());
	uploadDir_OnServer += todayDateStr + "/";
	
	//creating a directory with a unique id
	String uniqueDirId = "uploadDir_" + Double.toString(Math.random()).replace("0.", "");
	uploadDir_OnServer += uniqueDirId + "/";
	
	System.out.println("uploadDir_OnServer: " + uploadDir_OnServer);
	
	byte[] cr = {13}; 
	byte[] lf = {10}; 
	String CR = new String(cr);
	String LF = new String(lf);
	String CRLF = CR + LF;
	System.out.println("Before a LF=chr(10)" + LF + "Before a CR=chr(13)" + CR + "Before a CRLF" + CRLF);
	
	//Initialization for chunk management.
	boolean bLastChunk = false;
	int numChunk = 0;
	  
	//CAN BE OVERRIDEN BY THE postURL PARAMETER: if error=true is passed as a parameter on the URL
	boolean generateError = false;  
	boolean generateWarning = false;
	boolean sendRequest = false;  

	response.setContentType("text/plain");
	  
	java.util.Enumeration<String> headers = request.getHeaderNames();
	//System.out.println("[parseRequest.jsp]  ------------------------------ ");
	//System.out.println("[parseRequest.jsp]  Headers of the received request:");
	while (headers.hasMoreElements()) {
		String header = headers.nextElement();
		//System.out.println("[parseRequest.jsp]  " + header + ": " + request.getHeader(header));  	  	
	}
	//System.out.println("[parseRequest.jsp]  ------------------------------ ");
	
	try{
	    // Get URL Parameters.
		Enumeration paraNames = request.getParameterNames();
		//System.out.println("[parseRequest.jsp]  ------------------------------ ");
		//System.out.println("[parseRequest.jsp]  Parameters: ");
		String pname;
		String pvalue;
		while (paraNames.hasMoreElements()){
			pname = (String)paraNames.nextElement();
			pvalue = request.getParameter(pname);
			//System.out.println("[parseRequest.jsp] " + pname + " = " + pvalue);
			if(pname.equals("jufinal")){
			  	bLastChunk = pvalue.equals("1");
			}else if(pname.equals("jupart")){
			  	numChunk = Integer.parseInt(pvalue);
			}

			//For debug convenience, putting error=true as a URL parameter, will generate an error
			//in this response.
			if (pname.equals("error") && pvalue.equals("true")) {
				generateError = true;
			}

			//For debug convenience, putting warning=true as a URL parameter, will generate a warning
			//in this response.
			if (pname.equals("warning") && pvalue.equals("true")) {
				generateWarning = true;
			}
	       
			//For debug convenience, putting readRequest=true as a URL parameter, will send back the request content
			//into the response of this page.
			if (pname.equals("sendRequest") && pvalue.equals("true")) {
				sendRequest = true;
			}
	    }
	    
		//System.out.println("[parseRequest.jsp]  ------------------------------ ");

	    //int ourMaxMemorySize  = 100000000;
		int ourMaxMemorySize  = 1024 * 1024 * 110;
	    //int ourMaxRequestSize = 2000000000;
		int ourMaxRequestSize = 1024 * 1024 * 1024 * 3;

		///////////////////////////////////////////////////////////////////////////////////////////////////////
		//The code below is directly taken from the jakarta fileupload common classes
		//All informations, and download, available here : http://jakarta.apache.org/commons/fileupload/
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		// Set factory constraints
		factory.setSizeThreshold(ourMaxMemorySize);
		factory.setRepository(new File(uploadDir_OnServer));
		
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// Set overall request size constraint
		upload.setSizeMax(ourMaxRequestSize);
		
		// Parse the request
		if(request.getContentType() != null){
			if(!request.getContentType().startsWith("multipart/form-data")){
				//out.println("[parseRequest.jsp] No parsing of uploaded file: content type is " + request.getContentType()); 
			}else{ 
				List /* FileItem */ items = upload.parseRequest(request);
				// Process the uploaded items
				Iterator iter = items.iterator();
				FileItem fileItem;
				File fout;
				System.out.println("[parseRequest.jsp]  Let's read the sent data   (" + items.size() + " items)");
				
				if(items.size() > 0){
					createdUploadDirSuccess = (new File(uploadDir_OnServer)).mkdirs();
				}
				while(iter.hasNext()){
					fileItem = (FileItem) iter.next();
					System.out.println("fileItem: " + fileItem);
					System.out.println("fileItem.size: " + fileItem.getSize());
					if(fileItem.isFormField()){
						//out.println("[parseRequest.jsp] (form field) " + fileItem.getFieldName() + " = " + fileItem.getString());
						
						//If we receive the md5sum parameter, we've read finished to read the current file. It's not
						//a very good (end of file) signal. Will be better in the future ... probably !
						//Let's put a separator, to make output easier to read.
						if(fileItem.getFieldName().equals("md5sum[]")){ 
							//out.println("[parseRequest.jsp]  ------------------------------ ");
						}
					}else{
						//If we are in chunk mode, we add ".partN" at the end of the file, where N is the chunk number.
						String uploadedFilename = fileItem.getName() + ( numChunk>0 ? ".part"+numChunk : "") ;
						fout = new File(uploadDir_OnServer + (new File(uploadedFilename)).getName());
						//out.println("[parseRequest.jsp] File Out: " + fout.toString());
						// write the file
						fileItem.write(fout);	        
						System.out.println("Uploaded file name: " + uploadDir_OnServer + uploadedFilename);
						((ArrayList)session.getAttribute("uploadedFileNames")).add(uploadDir_OnServer + uploadedFilename);
						//////////////////////////////////////////////////////////////////////////////////////
						//Chunk management: if it was the last chunk, let's recover the complete file
						//by concatenating all chunk parts.
						//
						if(bLastChunk){	        
							//out.println("[parseRequest.jsp]  Last chunk received: let's rebuild the complete file (" + fileItem.getName() + ")");
							//First: construct the final filename.
							FileInputStream fis;
							FileOutputStream fos = new FileOutputStream(uploadDir_OnServer + fileItem.getName());
							int nbBytes;
							byte[] byteBuff = new byte[1024];
							String filename;
							for (int i=1; i<=numChunk; i+=1) {
								filename = fileItem.getName() + ".part" + i;
								//out.println("[parseRequest.jsp] " + "  Concatenating " + filename);
								fis = new FileInputStream(uploadDir_OnServer + filename);
								while ( (nbBytes = fis.read(byteBuff)) >= 0) {
									//out.println("[parseRequest.jsp] " + "     Nb bytes read: " + nbBytes);
									fos.write(byteBuff, 0, nbBytes);
								}
								fis.close();
							}
							fos.close();
						}
						
						
						// End of chunk management
						//////////////////////////////////////////////////////////////////////////////////////
						
						fileItem.delete();
					}	    
				}//while
			}
		}
		
	    if(generateWarning){
	    	//System.out.println("WARNING: just a warning message.\\nOn two lines!");
	    }

	    //System.out.println("[parseRequest.jsp] " + "Let's write a status, to finish the server response :");
	    
	    //Let's wait a little, to simulate the server time to manage the file.
	    //Thread.sleep(500);
	    
	    //Do you want to test a successful upload, or the way the applet reacts to an error ?
	    if(generateError){ 
	    	//System.out.println("ERROR: this is a test error (forced in /wwwroot/pages/parseRequest.jsp).\\nHere is a second line!");
	    } else {
	    	out.println("SUCCESS");
	    	//out.println("                        <span class=\"cpg_user_message\">Il y eu une erreur lors de l'exécution de la requête</span>");
	    }
	  	//System.out.println("[parseRequest.jsp] " + "End of server treatment ");

	}catch(Exception e){
		e.printStackTrace();
	}
  
%>