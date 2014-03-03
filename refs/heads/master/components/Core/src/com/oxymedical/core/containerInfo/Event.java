package com.oxymedical.core.containerInfo;


public class Event {
	
	String method;
	Long volume;
	String eventId;
	String auditPattern;
	
	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}
	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return the volume
	 */
	public Long getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(Long volume) {
		this.volume = volume;
	}
	/**
	 * @return the auditPattern
	 */
	public String getAuditPattern() {
		return auditPattern;
	}
	/**
	 * @param auditPattern the auditPattern to set
	 */
	public void setAuditPattern(String auditPattern) {
		this.auditPattern = auditPattern;
	}
	
}
