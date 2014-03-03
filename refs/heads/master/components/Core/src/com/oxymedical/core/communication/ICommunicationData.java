package com.oxymedical.core.communication;

import java.util.List;

public interface ICommunicationData
{
	public void setCommunicationType(CommunicationType type);
	public void setReceiver(List<String> receivers);
	public void addReceiver(String receiver);
	
	public void setCopyReceiver(List<String> copyReceivers);
	public void addCopyReceiver(String copyReceiver);
	
	public void setBlindCopyReceiver(List<String> blindCopyReceivers);
	public void addBlindCopyReceiver(String blindCopyReceiver);
	
	public void setSender(String sender);
	public void setSenderPassword(String password);
	public void setSenderHost(String host);
	
	public void setBody(List<String> body);
	public void addBodyLine(String bodyLine);
	public void setSubject(String subject);
	
	public CommunicationType getCommunicationType();
	public List<String> getReceiver();
	public List<String> getCopyReceiver();
	public List<String> getBlindCopyReceiver();
	
	public String getSender();
	public String getSenderPassword();
	public String getSenderHost();
	
	public List<String> getBody();
	public String getSubject();
	public void setAttachment(String attachment);
	public String getAttachment();
}
