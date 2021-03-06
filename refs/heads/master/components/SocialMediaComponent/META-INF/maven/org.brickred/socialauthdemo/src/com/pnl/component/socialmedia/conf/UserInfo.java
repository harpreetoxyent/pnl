package com.pnl.component.socialmedia.conf;

import java.sql.Date;
import java.util.List;

import org.brickred.socialauth.Contact;
import org.brickred.socialauth.util.BirthDate;

public class UserInfo 
{
	
	
	public UserInfo() {
    }

    public UserInfo(String firstName, String lastName, String email, String gender,String country, String location, String language, int day, int month , int year, String profileImageURL , int age , List<Contact> userContacts)
    {
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;   
        this.gender = gender;
        this.country = country;
        this.location = location;
        this.language = language;
        this.profileImageURL = profileImageURL;
       // this.dob = dob;
        this.userContacts = userContacts;
        this.day = day;
        this.month = month;
        this.year = year;
        this.age = age;
        
        
    }
	/**
	 * Email
	 */
	private String email;

	/**
	 * First Name
	 */
	private String firstName;

	/**
	 * Last Name
	 */
	private String lastName;

	/**
	 * Country
	 */
	private String country;

	/**
	 * Language
	 */
	private String language;

	/**
	 * Full Name
	 */
	private String name;

	/**
	 * Display Name
	 */
	private String displayName;

	/**
	 * Date of Birth
	 */
	private BirthDate dob;

	/**
	 * Gender
	 */
	private String gender;

	/**
	 * Location
	 */
	private String location;

	/**
	 * profile image URL
	 */
	private String profileImageURL;

	/**
	 * provider id with this profile associates
	 */
	private String providerId;

	/**
	 * Unique id
	 */
	private String uniqueId;

	/**
	 * List Of Contacts
	 */
	private List<Contact> userContacts;
	

	/**
	 * Date of Birth Day
	 */
	private int day;
	
	/**
	 * Date of Birth Month
	 */
	private int month;
	
	/**
	 * Date of Birth Year
	 */
	private int year;
	
	/**
	 * Age to be calculated
	 */
	
	private int age;
	/**
	 * Retrieves the first name
	 * 
	 * @return String the first name
	 */
	
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Updates the first name
	 * 
	 * @param firstName
	 *            the first name of user
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Retrieves the last name
	 * 
	 * @return String the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Updates the last name
	 * 
	 * @param lastName
	 *            the last name of user
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the email address.
	 * 
	 * @return email address of the user
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Updates the email
	 * 
	 * @param email
	 *            the email of user
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Retrieves the display name
	 * 
	 * @return String the display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Updates the display name
	 * 
	 * @param displayName
	 *            the display name of user
	 */
	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Retrieves the country
	 * 
	 * @return String the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Updates the country
	 * 
	 * @param country
	 *            the country of user
	 */
	public void setCountry(final String country) {
		this.country = country;
	}

	/**
	 * Retrieves the language
	 * 
	 * @return String the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Updates the language
	 * 
	 * @param language
	 *            the language of user
	 */
	public void setLanguage(final String language) {
		this.language = language;
	}

	/**
	 * Retrieves the full name
	 * 
	 * @return String the full name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Updates the name
	 * 
	 * @param name
	 *            the full name of user
	 */
	public void setName(final String name) {
		this.name = name;
	}

//	/**
//	 * Retrieves the date of birth
//	 * 
//	 * @return the date of birth different providers may use different formats
//	 */
//	public BirthDate getDob() {
//		return dob;
//	}
//
//	/**
//	 * Updates the date of birth
//	 * 
//	 * @param birthDate
//	 *            the date of birth of user
//	 */
//	public void setDob(final BirthDate birthDate) {
//		this.dob = birthDate;
//	}

	/**
	 * Retrieves the gender
	 * 
	 * @return String the gender - could be "Male", "M" or "male"
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Updates the gender
	 * 
	 * @param gender
	 *            the gender of user
	 */
	public void setGender(final String gender) {
		this.gender = gender;
	}

	/**
	 * Retrieves the location
	 * 
	 * @return String the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Updates the location
	 * 
	 * @param location
	 *            the location of user
	 */
	public void setLocation(final String location) {
		this.location = location;
	}

	/**
	 * Retrieves the profile image URL
	 * 
	 * @return String the profileImageURL
	 */
	public String getProfileImageURL() {
		return profileImageURL;
	}

	/**
	 * Updates the profile image URL
	 * 
	 * @param profileImageURL
	 *            profile image URL of user
	 */
	public void setProfileImageURL(final String profileImageURL) {
		this.profileImageURL = profileImageURL;
	}

	/**
	 * Retrieves the provider id with this profile associates
	 * 
	 * @return the provider id
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * Updates the provider id
	 * 
	 * @param providerId
	 *            the provider id
	 */
	public void setProviderId(final String providerId) {
		this.providerId = providerId;
	}

	/**
	 * Retrieves the contacts
	 * 
	 * @return String the contacts
	 */
	public List<Contact> getUserContacts() {
		return userContacts;
	}

	/**
	 * Updates the contacts
	 * 
	 * @param contacts
	 *            the contacts of user
	 */
	public void setUserContacts(final List<Contact> contacts) {
		this.userContacts = contacts;
	}
	
	/**
	 * Retrieves the day
	 * 
	 * @return int day
	 */
	
	public int getDay() {
		return day;
	}

	/**
	 * Updates the day
	 * 
	 * @param day
	 *            the day of date of birth
	 */
	public void setDay(final int day) {
		this.day = day;
	}
	
	/**
	 * Retrieves the month
	 * 
	 * @return int month
	 */
	
	public int getMonth() {
		return month;
	}

	/**
	 * Updates the month
	 * 
	 * @param day
	 *            the month of date of birth
	 */
	public void setMonth(final int month) {
		this.month = month;
	}
	
	/**
	 * Retrieves the year
	 * 
	 * @return int year
	 */
	
	public int getYear() {
		return year;
	}

	/**
	 * Updates the age
	 * 
	 * @param year
	 *            the age
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * Retrieves the age
	 * 
	 * @return int age
	 */
	
	public int getAge() {
		return age;
	}

	/**
	 * Updates the year
	 * 
	 * @param year
	 *            the year of date of birth
	 */
	public void setYear(final int year) {
		this.year = year;
	}
	
	/**
	 * Retrieves the profile info as a string
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String NEW_LINE = System.getProperty("line.separator");
		result.append(this.getClass().getName() + " Object {" + NEW_LINE);
		result.append(" email: " + email + NEW_LINE);
		result.append(" firstName: " + firstName + NEW_LINE);
		result.append(" lastName: " + lastName + NEW_LINE);
		result.append(" country: " + country + NEW_LINE);
		result.append(" language: " + language + NEW_LINE);
		result.append(" name: " + name + NEW_LINE);
		result.append(" displayName: " + displayName + NEW_LINE);
		//result.append(" dob: " + dob + NEW_LINE);
		result.append(" gender: " + gender + NEW_LINE);
		result.append(" location: " + location + NEW_LINE);
		result.append(" uniqueId: " + uniqueId + NEW_LINE);
		result.append(" profileImageURL: " + profileImageURL + NEW_LINE);
		result.append(" dob: " + day + month + year + NEW_LINE);
		result.append(" providerId: " + providerId + NEW_LINE);
		result.append("}");

		return result.toString();

	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(final String uniqueId) {
		this.uniqueId = uniqueId;
	}
}
