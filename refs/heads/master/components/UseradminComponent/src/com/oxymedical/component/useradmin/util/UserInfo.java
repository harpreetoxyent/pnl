package com.oxymedical.component.useradmin.util;

import java.util.Date;
import java.util.Locale;

public class UserInfo 
{

	String creatorUserId = null;
	String companyId= null;
	boolean autoUserId= false; 
	String userId = null; 
	String password = null;
	String emailAddress = null;
	Locale locale = null;
	String firstName = null;
	String middleName = null;
	String lastName = null; 
	String nickName = null;
	String prefixId = null;
	String suffixId = null;
	boolean male = false;
	int birthdayMonth = 0;
	int birthdayDay = 0;
	int birthdayYear = 0;
	String jobTitle = null;
	String organizationId = null;
	String locationId  = null;
	//Change done by netram sahu on 10-Apr-2012
	String groupId= null;
	
	public String getGroupId() {
		return groupId;
	}
	//End Changes
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	//Gettters and Setters are added for the below fields for Newly Added Fields
	//Change done by pra on 222-May-2009
	Byte active_=1;	
	String employeeNumber = null;		
	String category = null;
	String address1 = null;
	String address2 = null;
	long city; 
	String state = null;
	
	//chnage done for inactive date and for zip and telephone done by pra on 16june 2009
	Date inActiveDate=null;
	public Date getInActiveDate() {
		return inActiveDate;
	}
	public void setInActiveDate(Date inActiveDate) {
		this.inActiveDate = inActiveDate;
	}
	long telephoneNumber=0;
	public long getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(long telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public long getZipCode() {
		return zipCode;
	}
	public void setZipCode(long zipCode) {
		this.zipCode = zipCode;
	}
	long zipCode=0;
	String universalProviderIdentificationNumber = null;
	String nationalProvidedIdentificationNumber = null;
	
	
	public Byte getActive_() {
		return active_;
	}
	public void setActive_(Byte active_) {
		this.active_ = active_;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getUniversalProviderIdentificationNumber() {
		return universalProviderIdentificationNumber;
	}
	public void setUniversalProviderIdentificationNumber(
			String universalProviderIdentificationNumber) {
		this.universalProviderIdentificationNumber = universalProviderIdentificationNumber;
	}
	public String getNationalProvidedIdentificationNumber() {
		return nationalProvidedIdentificationNumber;
	}
	public long getCity() {
		return city;
	}
	public void setCity(long city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setNationalProvidedIdentificationNumber(
			String nationalProvidedIdentificationNumber) {
		this.nationalProvidedIdentificationNumber = nationalProvidedIdentificationNumber;
	}
	
	
	/**
	 * @return the autoUserId
	 */
	public boolean isAutoUserId() {
		return autoUserId;
	}
	/**
	 * @param autoUserId the autoUserId to set
	 */
	public void setAutoUserId(boolean autoUserId) {
		this.autoUserId = autoUserId;
	}
	/**
	 * @return the birthdayDay
	 */
	public int getBirthdayDay() {
		return birthdayDay;
	}
	/**
	 * @param birthdayDay the birthdayDay to set
	 */
	public void setBirthdayDay(int birthdayDay) {
		this.birthdayDay = birthdayDay;
	}
	/**
	 * @return the birthdayMonth
	 */
	public int getBirthdayMonth() {
		return birthdayMonth;
	}
	/**
	 * @param birthdayMonth the birthdayMonth to set
	 */
	public void setBirthdayMonth(int birthdayMonth) {
		this.birthdayMonth = birthdayMonth;
	}
	/**
	 * @return the birthdayYear
	 */
	public int getBirthdayYear() {
		return birthdayYear;
	}
	/**
	 * @param birthdayYear the birthdayYear to set
	 */
	public void setBirthdayYear(int birthdayYear) {
		this.birthdayYear = birthdayYear;
	}
	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the creatorUserId
	 */
	public String getCreatorUserId() {
		return creatorUserId;
	}
	/**
	 * @param creatorUserId the creatorUserId to set
	 */
	public void setCreatorUserId(String creatorUserId) {
		this.creatorUserId = creatorUserId;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the jobTitle
	 */
	public String getJobTitle() {
		return jobTitle;
	}
	/**
	 * @param jobTitle the jobTitle to set
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}
	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	/**
	 * @return the locationId
	 */
	public String getLocationId() {
		return locationId;
	}
	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	/**
	 * @return the male
	 */
	public boolean isMale() {
		return male;
	}
	/**
	 * @param male the male to set
	 */
	public void setMale(boolean male) {
		this.male = male;
	}
	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * @return the organizationId
	 */
	public String getOrganizationId() {
		return organizationId;
	}
	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the prefixId
	 */
	public String getPrefixId() {
		return prefixId;
	}
	/**
	 * @param prefixId the prefixId to set
	 */
	public void setPrefixId(String prefixId) {
		this.prefixId = prefixId;
	}
	/**
	 * @return the suffixId
	 */
	public String getSuffixId() {
		return suffixId;
	}
	/**
	 * @param suffixId the suffixId to set
	 */
	public void setSuffixId(String suffixId) {
		this.suffixId = suffixId;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
