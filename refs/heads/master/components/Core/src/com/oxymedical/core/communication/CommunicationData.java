package com.oxymedical.core.communication;

import java.util.ArrayList;
import java.util.List;

public class CommunicationData implements ICommunicationData
{
	private List<String> body;
	private List<String> bcc;
	private List<String> cc;
	private List<String> to;
	private String sender;
	private String subject;
	private String attachment;
	private CommunicationType commType;
	private String senderPwd;
	private String senderHost;
	
	
	@Override
	public void addBodyLine(String bodyLine)
	{
		if (body == null) body = new ArrayList<String>();
		body.add(bodyLine);
	}


	@Override
	public List<String> getBlindCopyReceiver()
	{
		return this.bcc;
	}


	@Override
	public List<String> getBody()
	{
		return this.body;
	}


	@Override
	public CommunicationType getCommunicationType()
	{
		return this.commType;
	}


	@Override
	public List<String> getCopyReceiver()
	{
		return this.cc;
	}


	@Override
	public List<String> getReceiver()
	{
		return this.to;
	}


	@Override
	public String getSender()
	{
		return this.sender;
	}
	@Override
	public String getAttachment()
	{
		return this.attachment;
	}

	@Override
	public String getSubject()
	{
		return this.subject;
	}


	@Override
	public void setBlindCopyReceiver(List<String> blindCopyReceiver)
	{
		this.bcc = blindCopyReceiver;
	}


	@Override
	public void setBody(List<String> body)
	{
		this.body = body;
	}


	@Override
	public void setCommunicationType(CommunicationType type)
	{
		this.commType = type;
	}


	@Override
	public void setCopyReceiver(List<String> copyReceiver)
	{
		this.cc = copyReceiver;
	}


	@Override
	public void setReceiver(List<String> receiver)
	{
		this.to = receiver;
	}


	@Override
	public void setSender(String sender)
	{
		this.sender = sender;
	}
	@Override
	public void setAttachment(String attachment)
	{
		this.attachment = attachment;
	}
	

	@Override
	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	
	@Override
	public String getSenderHost()
	{
		return this.senderHost;
		
	}


	@Override
	public String getSenderPassword()
	{
		return this.senderPwd;
	}
	

	@Override
	public void setSenderHost(String host)
	{
		this.senderHost = host;
		
	}


	@Override
	public void setSenderPassword(String password)
	{
		this.senderPwd = password;
	}


	@Override
	public void addBlindCopyReceiver(String blindCopyReceiver)
	{
		if (bcc == null) bcc = new ArrayList<String>();
		bcc.add(blindCopyReceiver);
	}


	@Override
	public void addCopyReceiver(String copyReceiver)
	{
		if (cc == null) cc = new ArrayList<String>();
		cc.add(copyReceiver);
	}


	@Override
	public void addReceiver(String receiver)
	{
		if (to == null) to = new ArrayList<String>();
		to.add(receiver);
	}

}
