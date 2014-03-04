package com.oxymedical.component.useradmin.XMLParser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.ldap.LDAPComponent;
import com.oxymedical.component.useradmin.ICompany;
import com.oxymedical.component.useradmin.ICompanyCont;
import com.oxymedical.component.useradmin.IContainer;
import com.oxymedical.component.useradmin.ICountry;
import com.oxymedical.component.useradmin.IGroup;
import com.oxymedical.component.useradmin.IOrganization;
import com.oxymedical.component.useradmin.IRegion;
import com.oxymedical.component.useradmin.IRight;
import com.oxymedical.component.useradmin.IRole;
import com.oxymedical.component.useradmin.IRoleRights;
import com.oxymedical.component.useradmin.IUser;
import com.oxymedical.component.useradmin.IUserPattern;
import com.oxymedical.component.useradmin.IUserPatternRoles;
import com.oxymedical.component.useradmin.IUserPatternUsers;
import com.oxymedical.component.useradmin.UserAdminComponent;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.exception.DuplicateUserIdException;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchContainerException;
import com.oxymedical.component.useradmin.exception.NoSuchGroupException;
import com.oxymedical.component.useradmin.exception.NoSuchOrganizationException;
import com.oxymedical.component.useradmin.exception.NoSuchRoleException;
import com.oxymedical.component.useradmin.exception.NoSuchUserException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.impl.CompanyImpl;
import com.oxymedical.component.useradmin.impl.CountryImpl;
import com.oxymedical.component.useradmin.impl.GroupImpl;
import com.oxymedical.component.useradmin.impl.OrganizationImpl;
import com.oxymedical.component.useradmin.impl.RegionImpl;
import com.oxymedical.component.useradmin.impl.RightImpl;
import com.oxymedical.component.useradmin.impl.RoleImpl;
import com.oxymedical.component.useradmin.impl.RoleRightsImpl;
import com.oxymedical.component.useradmin.impl.UserImpl;
import com.oxymedical.component.useradmin.impl.UserPatternImpl;
import com.oxymedical.component.useradmin.impl.UserpatternRolesImpl;
import com.oxymedical.component.useradmin.impl.UserpatternUsersImpl;
import com.oxymedical.component.useradmin.model.Company;
import com.oxymedical.component.useradmin.model.CompanyCont;
import com.oxymedical.component.useradmin.model.Container;
import com.oxymedical.component.useradmin.model.Country;
import com.oxymedical.component.useradmin.model.Group;
import com.oxymedical.component.useradmin.model.Organization;
import com.oxymedical.component.useradmin.model.Region;
import com.oxymedical.component.useradmin.model.Right;
import com.oxymedical.component.useradmin.model.Role;
import com.oxymedical.component.useradmin.model.User;
import com.oxymedical.component.useradmin.model.Userpattern;
import com.oxymedical.component.useradmin.util.CompanyInfo;
import com.oxymedical.component.useradmin.util.GroupInfo;
import com.oxymedical.component.useradmin.util.GroupUserInfo;
import com.oxymedical.component.useradmin.util.OrgInfo;
import com.oxymedical.component.useradmin.util.RightInfo;
import com.oxymedical.component.useradmin.util.RoleInfo;
import com.oxymedical.component.useradmin.util.RoleRightInfo;
import com.oxymedical.component.useradmin.util.RoleUserInfo;
import com.oxymedical.component.useradmin.util.UserInfo;
import com.oxymedical.component.useradmin.util.UserPatternInfo;
import com.oxymedical.component.useradmin.util.UserPatternRolesInfo;
import com.oxymedical.component.useradmin.util.UserPatternUsersInfo;

public class XMLParser 
{
	
	/**
	 * This function is used to convert &lt operator to less than HTML tag
	 * 
	 * @param wrongString
	 *            The XML document
	 * @return modified String .
	 */
	public String convertLessThen(String wrongString)
	{
		String modifiedString = "";
		modifiedString = wrongString;
		int startIndex = modifiedString.indexOf("&lt;");
		if (startIndex > 0)
		{
			modifiedString = wrongString.substring(0, startIndex) + "<"
					+ wrongString.substring(startIndex + 4);
			modifiedString = convertLessThen(modifiedString);
		}
		else
		{
			return modifiedString;
		}
		return modifiedString;
	}

	/**
	 * This function is used to convert &gt operator to greater than HTML tag
	 * 
	 * @param wrongString
	 *            The XML document
	 * @return modified String .
	 */
	public String convertGreaterThen(String wrongString)
	{
		String modifiedString = "";
		modifiedString = wrongString;
		int startIndex = modifiedString.indexOf("&gt;");
		if (startIndex > 0)
		{
			modifiedString = wrongString.substring(0, startIndex) + ">"
					+ wrongString.substring(startIndex + 4);
			modifiedString = convertGreaterThen(modifiedString);
		}
		else
		{
			return modifiedString;
		}
		return modifiedString;
	}
	/**
	 * This function is used to register users in database from users xml file
	 * which is output of form designer It registers Company , User , Group ,
	 * Role,Organization and contact information.
	 * 
	 * @param usersDoc
	 *            The User document
	 * @return registerUsersFromXML Returns void .
	 * @throws
	 * @throws NoSuchGroupException
	 * @throws NoSuchUserException
	 * @throws NoSuchCompanyException
	 * @throws NoSuchRoleException
	 * @throws NoSuchOrganizationException
	 * @throws NoSuchContainerException
	 * @exception Throws
	 *                UAComponentException , DBComponentException.
	 */
	public void registerUsersFromXML(Document document, String applicationName,
			LDAPComponent ldapComp) throws DBComponentException, NoSuchUserException,
			NoSuchGroupException, NoSuchCompanyException, NoSuchRoleException,
			NoSuchOrganizationException, DuplicateUserIdException, UAComponentException
	{
	// Changes done  Accoridng to the xml created by the Form Desginer for Adding users. Need to be implemented		added by pra 01-june-2009
		IOrganization organizationImpl = null;
		UserInfo userInfo = null;
		OrgInfo orgInfo = null;
		GroupInfo groupInfo = null;
		RightInfo rightInfo = null;
		RoleInfo roleInfo = null;
		RoleRightInfo roleRightInfo = null;
		CompanyInfo companyInfo = null;
		RoleUserInfo roleUserInfo = null;
		GroupUserInfo groupUserInfo = null;

		UserPatternInfo userPatternInfo = null;
		UserPatternRolesInfo userpatternRoleInfo = null;
		UserPatternUsersInfo userpatternUserInfo = null;

		ICountry countryImpl = null;
		ICompany companyImpl = null;
		IRole roleImpl = null;
		IRight rightImpl = null;
		IRegion regionImpl = null;
		IGroup groupImpl = null;
		ICompanyCont companyCont = null;
		IContainer containerImpl = null;
		IRoleRights roleRightsImpl = null;
		IUser userImpl = null;

		IUserPattern userPatternImpl = null;
		IUserPatternUsers userPatternUserImpl = null;
		IUserPatternRoles userPatternRolesImpl = null;

		Container container = null;
		CompanyCont existingCompanyCont = null;
		Country country = null;
		Role role = null;
		Group group = null;
		Region region = null;
		Organization organization = null;
		Company company = null;
		Userpattern userpattern = null;

		ArrayList<Right> rightList = null;
		Hashtable<String, ArrayList<Right>> roleRightsDetails = null;

		ArrayList<Role> roleList = null;
		ArrayList<User> userList = null;
		Hashtable<String, ArrayList<Role>> userpatternRoleDetails = null;
		Hashtable<String, ArrayList<User>> userpatternUserDetails = null;

		try
		{

			/*
			 * UserAdminComponent.logger.log(0,"--------------ldapComp = " + ldapComp); try {
			 * ldapComp = new LDAPComponent(); } catch (LDAPComponentException
			 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
			 */
			// ldapImpl = new LdapImpl();
			// ldapImpl.setLdapComp(ldapComp);
			// ldapImpl.initLdapInfoList();
			// This section will be deleted .This section is just for testing
			// purposes
			/*
			 * String fileName = Constants.USER_ADMIN_USER; SAXReader reader =
			 * new SAXReader(); InputStream modulefileStream =
			 * this.getClass().getResourceAsStream(fileName); Document document
			 * = reader.read(modulefileStream);
			 */
			// Till here
			// document will come from Form Designer
			String strDocument = document.getRootElement().asXML();
			strDocument = convertLessThen(strDocument);
			strDocument = convertGreaterThen(strDocument);
			// Convert String to XML
			document = DocumentHelper.parseText(strDocument);
			Element rootElement = document.getRootElement();
			UserAdminComponent.logger.log(0,"Root Element is  " + rootElement.getName());
			System.out.println("------------------rootElement.getName() ----------"+rootElement.getName());
			
			Iterator rootIterator = rootElement.elementIterator();
			while (rootIterator.hasNext())
			{
				UserAdminComponent.logger.log(0," present in while Loop" + rootIterator.hasNext());
				// Company
				/*
				 * Element nextElement = (Element)rootIterator.next(); { if(null
				 * != nextElement && nextElement.getName().equals("Company")) {
				 * System
				 * .out.println("--------------For Company------------------ "
				 * ); companyInfo = new CompanyInfo(); Iterator companyIterator
				 * = nextElement.elementIterator();
				 * while(companyIterator.hasNext()) { Element element =
				 * (Element)companyIterator.next(); if((null != element) &&
				 * (null != element.getText()) &&
				 * !("".equals(element.getText())) && (null !=
				 * element.getName()) && !("".equals(element.getName()))) {
				 * if(element.getName().equals("companyId")) {
				 * companyInfo.setCompanyId(element.getText().trim()); }
				 * if(element.getName().equals("portalURL")) {
				 * companyInfo.setPortalURL(element.getText().trim()); } } }
				 * 
				 * // Add Company companyImpl = new CompanyImpl();
				 * UserAdminComponent.logger.log(0,
				 * "--------------For Company before add company ------------------ "
				 * ); company =
				 * companyImpl.addCompany(companyInfo.getCompanyId(),
				 * companyInfo.getPortalURL());
				 * 
				 * 
				 * / //Add Container containerImpl = new ContainerImpl();
				 * container = containerImpl.addContainer(applicationName,
				 * Constants.application);
				 * 
				 * try { // Check to see if same container and company already
				 * exists in CompanyCont Table existingCompanyCont =
				 * CompanyContImpl.getCompanyByContainer(applicationName); //
				 * UserAdminComponent.logger.log(0,
				 * "*************try Inside container  UserImpl Add ******"); }
				 * catch(UAComponentException ex) { //UserAdminComponent.logger.log(0,
				 * "*************catch Inside container null  UserImpl Add ******"
				 * ); } if(null == existingCompanyCont) { if(null != company) {
				 * //UserAdminComponent.logger.log(0,
				 * "*************Inside container null UserImpl Add ******"); //
				 * Add CompanyContainer companyCont = new CompanyContImpl();
				 * companyCont.addCompanyContainer(company, container); }
				 * 
				 * } //AddCompany as Organization to maintain parent child
				 * relationship organizationImpl = new OrganizationImpl();
				 * organizationImpl
				 * .addOrganizationByCompany(companyInfo.getCompanyId(),
				 * companyInfo.getCompanyId(),companyInfo.getCompanyId(), null,
				 * null, null);
				 * 
				 * }
				 * 
				 * 
				 * }
				 * 
				 * 
				 * }
				 */

				// Organization
				/*
				 * rootElement = document.getRootElement(); rootIterator =
				 * rootElement.elementIterator(); while(rootIterator.hasNext())
				 * {
				 */
				if (rootElement.getName().equalsIgnoreCase("Organizations"))
				{
					UserAdminComponent.logger.log(0,"--Present in Orgnazation-first If--- ");
					Element orgElement = (Element) rootIterator.next();
					UserAdminComponent.logger.log(0,"--Check element in iterator-- "
							+ orgElement);
					if (null != orgElement && orgElement.getName().equalsIgnoreCase("Organization"))
					{
						UserAdminComponent.logger.log(0,"--element is--For  Organization-- ");
						orgInfo = new OrgInfo();
						for (Iterator modCounter = orgElement.attributeIterator(); modCounter
								.hasNext();)
						{
							Attribute orgAttribute = (Attribute) modCounter.next();
							UserAdminComponent.logger.log(0,"Attribute of user Element  "
									+ orgAttribute.getName());
							if (orgAttribute != null && orgAttribute.getName().equals("OrgName"))
							{
								UserAdminComponent.logger.log(0,"OrgName is " + orgAttribute.getValue());
								orgInfo.setName(orgAttribute.getValue().trim());
							}
							if (orgAttribute != null && orgAttribute.getName().equals("CompanyId"))
							{
								UserAdminComponent.logger.log(0,"CompanyId is " + orgAttribute.getValue());
								/*
								 * Company is not coming from any xml so just
								 * adding the same company which is present in
								 * organization
								 */
								// Add Company
								companyImpl = new CompanyImpl();
								UserAdminComponent.logger.log(0,"-For Company before add company -- ");
								company = companyImpl
										.addCompany(orgAttribute.getValue().trim(), "");
							
								orgInfo.setCompanyId(orgAttribute.getValue().trim());
							}
							if (orgAttribute != null
									&& orgAttribute.getName().equals("ParentOrgName"))
							{
								UserAdminComponent.logger.log(0,"ParentOrgName is " + orgAttribute.getValue());
								orgInfo.setParentOrganizationId(orgAttribute.getValue().trim());
							}
							if (orgAttribute != null && orgAttribute.getName().equals("CountryId"))
							{
								UserAdminComponent.logger.log(0,"CountryId is " + orgAttribute.getValue());
								country = new Country();
								countryImpl = new CountryImpl();
								country = countryImpl.getCountryFromName(orgAttribute.getValue()
										.trim());
								UserAdminComponent.logger.log(0,"Country is --" + country);
								if (null != country)
								{
									orgInfo.setCountryId(country.getCountryId().toString());
								}
							}
							if (orgAttribute != null && orgAttribute.getName().equals("RegionId"))
							{
								UserAdminComponent.logger.log(0,"RegionId is " + orgAttribute.getValue());
								region = new Region();
								regionImpl = new RegionImpl();
								region = regionImpl.getRegionFromName(orgAttribute.getValue()
										.trim());
								if (null != region)
								{
									orgInfo.setRegionId(region.getRegionId());
								}
							}
							if (orgAttribute != null
									&& orgAttribute.getName().equalsIgnoreCase("statusId"))
							{
								UserAdminComponent.logger.log(0,"statusId is " + orgAttribute.getValue());
								orgInfo.setStatusId(orgAttribute.getValue().trim());

							}

						}
						// Add Organization
						organizationImpl = new OrganizationImpl();
						UserAdminComponent.logger.log(0,"--before add organization-- ");
						organizationImpl.addOrganizationByCompany(orgInfo.getCompanyId(), orgInfo
								.getParentOrganizationId(), orgInfo.getName(), orgInfo
								.getRegionId(), orgInfo.getCountryId(), orgInfo.getStatusId());
						UserAdminComponent.logger.log(0,"---after add organization-- ");
					}
				}

				// Group
				/*
				 * rootElement = document.getRootElement(); rootIterator =
				 * rootElement.elementIterator(); while(rootIterator.hasNext())
				 * {
				 */
				else if (rootElement.getName().equalsIgnoreCase("Groups"))
				{
					UserAdminComponent.logger.log(0,"--Present in Groups--else If-- ");
					Element groupElement = (Element) rootIterator.next();
					if (null != groupElement && groupElement.getName().equals("Group"))
					{
						UserAdminComponent.logger.log(0,"-Inside group");
						groupInfo = new GroupInfo();
						for (Iterator modCounter = groupElement.attributeIterator(); modCounter
								.hasNext();)
						{
							Attribute groupAttribute = (Attribute) modCounter.next();
							UserAdminComponent.logger.log(0,"Attribute of user Element  "
									+ groupAttribute.getName());
							if (groupAttribute != null
									&& groupAttribute.getName().equals("GroupId"))
							{
								groupInfo.setName(groupAttribute.getValue().trim());

							}
							if (groupAttribute != null
									&& groupAttribute.getName().equals("CompanyId"))
							{
								groupInfo.setCompanyId(groupAttribute.getValue().trim());

							}
						}

						// Add Group
						groupImpl = new GroupImpl();
						groupImpl.addGroupByCompany(groupInfo.getCompanyId(), groupInfo
								.getClassName(), groupInfo.getClassPK(), groupInfo.getName(),
								groupInfo.getDescription(), groupInfo.getType(), true);
					}
				}

				// Rights
				/*
				 * rootElement = document.getRootElement(); rootIterator =
				 * rootElement.elementIterator(); while(rootIterator.hasNext())
				 * {
				 */
				else if (rootElement.getName().equalsIgnoreCase("Rights"))
				{
					UserAdminComponent.logger.log(0,"-Present in Rights else If- ");
					Element rightElement = (Element) rootIterator.next();
					UserAdminComponent.logger.log(0,"--Check element in iterator--2"
							+ rightElement);
					if (null != rightElement && rightElement.getName().equals("Right"))
					{
						UserAdminComponent.logger.log(0,"-element is right-- ");
						rightInfo = new RightInfo();
						for (Iterator modCounter = rightElement.attributeIterator(); modCounter
								.hasNext();)
						{
							Attribute rightAttribute = (Attribute) modCounter.next();
							if (rightAttribute != null
									&& rightAttribute.getName().equals("rightid"))
							{
								UserAdminComponent.logger.log(0,"Right Id is " + rightAttribute.getValue());
								rightInfo.setName(rightAttribute.getValue().trim());
							}
							if (rightAttribute != null
									&& rightAttribute.getName().equals("CompanyId"))
							{
								UserAdminComponent.logger.log(0,"Company Id is " + rightAttribute.getValue());
								rightInfo.setCompanyId(rightAttribute.getValue().trim());
							}
						}

						// Add Right
						rightImpl = new RightImpl();
						UserAdminComponent.logger.log(0,"-Calling RightImpl for add right with companyId and name:--");
						UserAdminComponent.logger.log(0,"-With CompanyId -and getName-"
								+ rightInfo.getCompanyId() + rightInfo.getName());
						rightImpl.addRight(rightInfo.getCompanyId(), rightInfo.getName(), "");
					}
				}

				// Roles

				/*
				 * rootElement = document.getRootElement();
				 * UserAdminComponent.logger.log(0,"----------root element is---2----"
				 * +rootElement.getName()); rootIterator =
				 * rootElement.elementIterator(); while(rootIterator.hasNext())
				 * {
				 */
				else if (rootElement.getName().equalsIgnoreCase("Roles"))
				{
					UserAdminComponent.logger.log(0,"--Present in Roles--else If-- ");
					Element roleElement = (Element) rootIterator.next();
					UserAdminComponent.logger.log(0,"--Check element in iterator-1-- "
							+ roleElement);
					if (null != roleElement && roleElement.getName().equals("Role"))
					{
						UserAdminComponent.logger.log(0,"-For Roles-2-- ");
						roleInfo = new RoleInfo();
						roleRightInfo = new RoleRightInfo();
						String roleName = null;
						for (Iterator modCounter = roleElement.attributeIterator(); modCounter
								.hasNext();)
						{
							Attribute roleAttribute = (Attribute) modCounter.next();
							UserAdminComponent.logger.log(0,"-For Roles-3 " + roleAttribute);
							if (roleAttribute != null
									&& roleAttribute.getName().equalsIgnoreCase("roleid"))
							{
								UserAdminComponent.logger.log(0,"-Role Id is-" + roleAttribute.getValue());
								roleInfo.setName(roleAttribute.getValue().trim());
								roleRightInfo.setRoleId(roleAttribute.getValue().trim());
								roleName = roleAttribute.getValue();
							}
							if (roleAttribute != null
									&& roleAttribute.getName().equals("CompanyId"))
							{
								UserAdminComponent.logger.log(0,"---Company Id is--- "
										+ roleAttribute.getValue());
								roleInfo.setCompanyId(roleAttribute.getValue().trim());
								roleRightInfo.setCompanyId(roleAttribute.getValue().trim());
							}
						}
						roleImpl = new RoleImpl();
						role = roleImpl.addRole(roleInfo.getCompanyId(), roleInfo.getName(), "");
						UserAdminComponent.logger.log(0,"-After saving Role now do for Role Rights--check role id-"
										+ role.getRoleId());
						UserAdminComponent.logger.log(0,"-After saving Role now do for Role Rights--check companyId-"
										+ role.getCompanyId());
						// Add Rights for Role
						roleRightsDetails = new Hashtable<String, ArrayList<Right>>();
						Iterator roleRightsIterator = roleElement.elementIterator();
						while (roleRightsIterator.hasNext())
						{
							UserAdminComponent.logger.log(0,"--iterator on role element--"
									+ roleRightsIterator.hasNext());
							Element roleRightsElement = (Element) roleRightsIterator.next();
							UserAdminComponent.logger.log(0,"--iterator on role element-check element-"
									+ roleRightsElement);
							if ((null != roleRightsElement)
									&& (null != roleRightsElement.getName())
									&& !("".equals(roleRightsElement.getName())))
							{
								UserAdminComponent.logger.log(0,"--Start check-if 1--"
										+ roleRightsElement.getName());
								if (roleRightsElement.getName().equalsIgnoreCase("Rights"))
								{
									rightList = new ArrayList<Right>();
									UserAdminComponent.logger.log(0,"--Start check-if 2--");
									Iterator roleRightIterator = roleRightsElement
											.elementIterator();
									while (roleRightIterator.hasNext())
									{
										UserAdminComponent.logger.log(0,"--Start check-Iterator on rights-");
										Element roleRightElement = (Element) roleRightIterator
												.next();
										UserAdminComponent.logger.log(0,"---Iterator element on rights-"
												+ roleRightElement);
										for (Iterator modCounter = roleRightElement
												.attributeIterator(); modCounter.hasNext();)
										{
											UserAdminComponent.logger.log(0,"--Start check-if 3--");
											Attribute roleRightAttribute = (Attribute) modCounter
													.next();
											if (roleRightAttribute != null
													&& roleRightAttribute.getName().equals(
															"rightid"))
											{
												UserAdminComponent.logger.log(0,"Right Id is "
														+ roleRightAttribute.getValue());
												roleRightInfo.setRightId(roleRightAttribute
														.getValue().trim());
											}
										}
										UserAdminComponent.logger.log(0,"--RoleRightinfor role id-"
												+ roleRightInfo.getRoleId());
										UserAdminComponent.logger.log(0,"--RoleRightinfor role id--"
												+ roleRightInfo.getCompanyId());
										if (roleRightsDetails.containsKey(role.getRoleId()
												+ roleRightInfo.getRightId()))
										{
											UserAdminComponent.logger.log(0,"--Start check-if 4--");
											rightImpl = new RightImpl();
											rightList = roleRightsDetails.get(roleRightInfo
													.getRoleId()
													+ roleRightInfo.getRightId());
											Right right = new Right();
											right = rightImpl.getRightFromCompanyAndName(
													roleRightInfo.getRightId(), roleRightInfo
															.getCompanyId());
											UserAdminComponent.logger.log(0,"-Check right id fetched from Db-"
															+ right.getRightId());
											right.setRightId(right.getRightId());
											rightList.add(right);
										}
										else
										{
											UserAdminComponent.logger.log(0,"--Start check-if 5--");
											rightImpl = new RightImpl();
											Right right = new Right();
											right = rightImpl.getRightFromCompanyAndName(
													roleRightInfo.getRightId(), roleRightInfo
															.getCompanyId());
											UserAdminComponent.logger.log(0,"Check right id fetched from Db--"
															+ right.getRightId());
											right.setRightId(right.getRightId());
											rightList.add(right);
											roleRightsDetails.put(role.getRoleId()
													+ roleRightInfo.getRightId(), rightList);
										}
									}
									UserAdminComponent.logger.log(0,"-Check Right list size-"
											+ rightList.size());
									roleRightsImpl = new RoleRightsImpl();
									roleRightsImpl.addRoleRights(role.getRoleId(), roleRightInfo
											.getCompanyId(), rightList);
								}
							}

						}

					}

				}

				// User

				/*
				 * rootElement = document.getRootElement();
				 * UserAdminComponent.logger.log(0,"-----call for user root element-----"
				 * +rootElement); rootIterator = rootElement.elementIterator();
				 * 
				 * 
				 * while(rootIterator.hasNext()) {
				 */
				else if (rootElement.getName().equalsIgnoreCase("users"))
				{
					UserAdminComponent.logger.log(0,"-Present in users-else If- ");
					System.out.println("-Present in users-else If-");
					userInfo = new UserInfo();
					Element userElement = (Element) rootIterator.next();
					List elementList = userElement.elements();
					if (null != userElement && userElement.getName().equalsIgnoreCase("User"))
					{
						UserAdminComponent.logger.log(0,"-Inside User tag- "
								+ userElement.getName());
						System.out.println("-Present in -Inside User tag-");
						for (Iterator modCounter = userElement.attributeIterator(); modCounter
								.hasNext();)
						{
							Attribute userAttribute = (Attribute) modCounter.next();
							UserAdminComponent.logger.log(0,"Attribute of user Element  "
									+ userAttribute.getName());
							if (userAttribute != null
									&& userAttribute.getName().equalsIgnoreCase("userid"))
							{
								
								UserAdminComponent.logger.log(0,"User id is " + userAttribute.getValue());
								System.out.println("-Present in -Inside User tag- userid"+userAttribute.getValue().trim());
								userInfo.setUserId(userAttribute.getValue().trim());
							}
							if (userAttribute != null
									&& userAttribute.getName().equalsIgnoreCase("CompanyId"))
							{
								UserAdminComponent.logger.log(0,"Company id is " + userAttribute.getValue());
								userInfo.setCompanyId(userAttribute.getValue().trim());
							}
							if (userAttribute != null
									&& userAttribute.getName().equalsIgnoreCase("password"))
							{
								UserAdminComponent.logger.log(0,"Password id is " + userAttribute.getValue());
								userInfo.setPassword(userAttribute.getValue().trim());
							}
							if (userAttribute != null
									&& userAttribute.getName().equalsIgnoreCase("orgName"))
							{
								UserAdminComponent.logger.log(0,"orgName id is " + userAttribute.getValue());
								organizationImpl = new OrganizationImpl();
								organization = new Organization();
								UserAdminComponent.logger.log(0,"-Check org params companyId-"
										+ userInfo.getCompanyId());
								UserAdminComponent.logger.log(0,"-Check org params userAttribute-"
										+ userAttribute.getValue().trim());
								organization = organizationImpl.getOrganizationbyCompanyAndName(
										userInfo.getCompanyId(), userAttribute.getValue().trim());
								UserAdminComponent.logger.log(0,"-Check org params 3--" + organization);
								if (null != organization)
								{
									UserAdminComponent.logger.log(0,"--Check org params 4--org id-"
											+ organization.getOrganizationId());
									userInfo.setOrganizationId(organization.getOrganizationId());
								}
							}
							if (userAttribute != null
									&& userAttribute.getName().equalsIgnoreCase("groupId"))
							{
								UserAdminComponent.logger.log(0,"groupId id is " + userAttribute.getValue());
								groupImpl = new GroupImpl();
								group = new Group();
								UserAdminComponent.logger.log(0,"-Check group params companyId--"
										+ userInfo.getCompanyId());
								UserAdminComponent.logger.log(0,"-Check group params userAttribute--"
										+ userAttribute.getValue().trim());
								group = groupImpl.getGroupByCompanyAndName(userInfo.getCompanyId(),
										userAttribute.getValue().trim());
								if (null != group)
								{
									groupUserInfo = new GroupUserInfo();
									UserAdminComponent.logger.log(0,"-Check group params -1-"
											+ group.getGroupId());
									UserAdminComponent.logger.log(0,"-Check group params --2-"
											+ userInfo.getUserId());
									groupUserInfo.setGroupId(group.getGroupId());
									groupUserInfo.setUserId(userInfo.getUserId());
									
									//Save groupUserInfo
									userInfo.setGroupId(group.getGroupId());
								}
							}
						}
						for (Iterator userIterator = elementList.iterator(); userIterator.hasNext();)
						{
							Element personalInfoElement = (Element) userIterator.next();
							UserAdminComponent.logger.log(0,"Element Name is  " + personalInfoElement.getName());
							for (Iterator perInfoCounter = personalInfoElement.attributeIterator(); perInfoCounter
									.hasNext();)
							{
								Attribute perInfoAttribute = (Attribute) perInfoCounter.next();
								UserAdminComponent.logger.log(0,"Attribute of personalInfo  "
										+ perInfoAttribute.getName());
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("firstname"))
								{
									UserAdminComponent.logger.log(0,"First Name is "
											+ perInfoAttribute.getValue());
									userInfo.setFirstName(perInfoAttribute.getValue().trim());
								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("middlename"))
								{
									UserAdminComponent.logger.log(0,"Middle Name is "
											+ perInfoAttribute.getValue());
									userInfo.setMiddleName(perInfoAttribute.getValue().trim());
								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("lastname"))
								{
									UserAdminComponent.logger.log(0,"Last Name is "
											+ perInfoAttribute.getValue());
									userInfo.setLastName(perInfoAttribute.getValue().trim());
								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("emailid"))
								{
									UserAdminComponent.logger.log(0,"emailid " + perInfoAttribute.getValue());
									userInfo.setEmailAddress(perInfoAttribute.getValue().trim());

								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("gender"))
								{
									UserAdminComponent.logger.log(0,"gender is " + perInfoAttribute.getValue());
									if (perInfoAttribute.getValue().trim().equalsIgnoreCase("male"))
									{
										userInfo.setMale(true);
									}
									else
									{
										userInfo.setMale(false);
									}
									UserAdminComponent.logger.log(0,"is Male " + userInfo.isMale());

								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("birthday"))
								{
									UserAdminComponent.logger.log(0,"birthday is " + perInfoAttribute.getValue());

									// To be done later on
									/*
									 * DateFormat simpleDateFormat = new
									 * SimpleDateFormat("dd/MM/yyyy"); Date
									 * birthDate =
									 * simpleDateFormat.parse(perInfoAttribute
									 * .getName().trim()); Calendar calendar =
									 * Calendar.getInstance();
									 * calendar.setTime(birthDate);
									 * 
									 * int day = calendar.get(Calendar.DATE);
									 * int month = calendar.get(Calendar.MONTH);
									 * int year = calendar.get(Calendar.YEAR);
									 * 
									 * userInfo.setBirthdayDay(day);
									 * userInfo.setBirthdayMonth(month);
									 * userInfo.setBirthdayYear(year);
									 */
								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("prefix"))
								{
									UserAdminComponent.logger.log(0,"prefix " + perInfoAttribute.getValue());
									userInfo.setPrefixId(perInfoAttribute.getValue().trim());

								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("suffix"))
								{
									UserAdminComponent.logger.log(0,"suffix " + perInfoAttribute.getValue());
									userInfo.setSuffixId(perInfoAttribute.getValue().trim());

								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("category"))
								{
									UserAdminComponent.logger.log(0,"category " + perInfoAttribute.getValue());
									userInfo.setCategory(perInfoAttribute.getValue().trim());

								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("active"))
								{
								//removed true and false and used active and inactive ..done by pra on 15june2009
									if (perInfoAttribute.getValue().trim().equals("inactive"))
									{
										Byte active=0;
										userInfo.setActive_(active);
									}
									else if (perInfoAttribute.getValue().trim().equals("active"))
									{
										Byte active=1;
										userInfo.setActive_(active);
									}
									
								}
								
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("address1"))
								{
									UserAdminComponent.logger.log(0,"address1 " + perInfoAttribute.getValue());
									userInfo.setAddress1(perInfoAttribute.getValue().trim());

								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("address2"))
								{
									UserAdminComponent.logger.log(0,"address2 " + perInfoAttribute.getValue());
									userInfo.setAddress2(perInfoAttribute.getValue().trim());

								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("city"))
								{
									UserAdminComponent.logger.log(0,"city " + perInfoAttribute.getValue());
									region = new Region();
									regionImpl = new RegionImpl();
									region = regionImpl.getRegionFromName(perInfoAttribute.getValue().trim());
									if (null != region)
									{
										//userInfo.setCity(region.getRegionId());
									}
									
								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("state"))
								{
									country = new Country();
									countryImpl = new CountryImpl();
									country = countryImpl.getCountryFromName(perInfoAttribute.getValue().trim());
									UserAdminComponent.logger.log(0,"Country is -" + country);
									if (null != country)
									{
										userInfo.setState(country.getCountryId().toString());
									}
									
								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("phone"))
								{
									UserAdminComponent.logger.log(0,"phone " + perInfoAttribute.getValue());
									userInfo.setTelephoneNumber(Integer.valueOf(perInfoAttribute.getValue().trim()).intValue());

								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("upin"))
								{
									UserAdminComponent.logger.log(0,"upin " + perInfoAttribute.getValue());
									userInfo.setUniversalProviderIdentificationNumber(perInfoAttribute.getValue().trim());

								}

								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("zipcode"))
								{
									UserAdminComponent.logger.log(0,"zipcode " + perInfoAttribute.getValue());
									userInfo.setZipCode(Integer.valueOf(perInfoAttribute.getValue().trim()).intValue());

								}
								if (perInfoAttribute != null
										&& perInfoAttribute.getName().equals("npin"))
								{
									UserAdminComponent.logger.log(0,"npin " + perInfoAttribute.getValue());
									userInfo.setNationalProvidedIdentificationNumber(perInfoAttribute.getValue().trim());

								}


								/*
								 * if(element.getName().equals("roleId")) { //
								 * UserAdminComponent.logger.log(0,"Role roleId is  " +
								 * element.getName()); Iterator roleIterator =
								 * element.elementIterator(); roleImpl = new
								 * RoleImpl(); while(roleIterator.hasNext()) {
								 * Element roleElement =
								 * (Element)roleIterator.next(); //
								 * UserAdminComponent.logger.log(0,"roleElement.getName is  "
								 * + roleElement.getName()); if((null !=
								 * roleElement) && (null !=
								 * roleElement.getText()) &&
								 * !("".equals(roleElement
								 * .getText().trim()))&&(null !=
								 * roleElement.getName())&&!(""
								 * .equals(roleElement.getName().trim()))) {
								 * if(roleElement.getName().equals("role")) { //
								 * UserAdminComponent.logger.log(0,"roleElement.getText is  "
								 * + roleElement.getText()); role = new Role();
								 * role =
								 * roleImpl.getRolebyCompanyAndName(userInfo
								 * .getCompanyId
								 * (),roleElement.getText().trim()); if(null !=
								 * role) { roleUserInfo = new RoleUserInfo();
								 * roleUserInfo.setRoleId(role.getRoleId());
								 * roleUserInfo.setUserId(userInfo.getUserId());
								 * roleIds.add(roleUserInfo.getRoleId()); //
								 * UserAdminComponent.logger.log(0,"Role Array is Size " +
								 * roleIds.size()); } } } } }
								 */

							}
						}
// change implementation for the new fields. added by pra on 22-may-2009
						System.out.println("-before user impl add-");

						userImpl = new UserImpl();
						userImpl.addUser(userInfo.getCreatorUserId(), userInfo
								.getCompanyId(), userInfo.isAutoUserId(), userInfo
								.getUserId(), userInfo.getPassword(), userInfo
								.getEmailAddress(), userInfo.getLocale(), userInfo
								.getFirstName(), userInfo.getMiddleName(), userInfo
								.getLastName(), userInfo.getNickName(), userInfo
								.getPrefixId(), userInfo.getSuffixId(), userInfo.isMale(),
								userInfo.getBirthdayMonth(), userInfo.getBirthdayDay(),
								userInfo.getBirthdayYear(), userInfo.getJobTitle(),
								userInfo.getOrganizationId(), userInfo.getLocationId(),userInfo.getEmployeeNumber(),userInfo.getAddress1(),userInfo.getAddress2()
								,userInfo.getNationalProvidedIdentificationNumber(),userInfo.getUniversalProviderIdentificationNumber(),userInfo.getCategory()
								,userInfo.getCity(),userInfo.getState(),userInfo.getTelephoneNumber(),userInfo.getZipCode(),userInfo.getActive_(),userInfo.getInActiveDate(),userInfo.getGroupId());

					}

				}
				// For User Patterns
				else if (rootElement.getName().equalsIgnoreCase("UserPatterns"))
				{
					Element userPatternElement = (Element) rootIterator.next();
					UserAdminComponent.logger.log(0,"Check element in iterator-1- "
							+ userPatternElement);
					userPatternInfo = new UserPatternInfo();
					if (null != userPatternElement
							&& userPatternElement.getName().equals("UserPattern"))
					{
						UserAdminComponent.logger.log(0,"---For UserPattern-2-- ");
						userpatternUserInfo = new UserPatternUsersInfo();
						userpatternRoleInfo = new UserPatternRolesInfo();
						for (Iterator modCounter = userPatternElement.attributeIterator(); modCounter
								.hasNext();)
						{
							Attribute userPatternAttribute = (Attribute) modCounter.next();
							if (userPatternAttribute != null
									&& userPatternAttribute.getName().equalsIgnoreCase(
											"userpatternid"))
							{
								UserAdminComponent.logger.log(0,"--userpatternid  is-- "
										+ userPatternAttribute.getValue());
								userPatternInfo.setUserPatternId(userPatternAttribute.getValue()
										.trim());
								userpatternRoleInfo.setUserPatternId(userPatternAttribute
										.getValue().trim());
								userpatternUserInfo.setUserPatternId(userPatternAttribute
										.getValue().trim());
							}
							if (userPatternAttribute != null
									&& userPatternAttribute.getName().equalsIgnoreCase("CompanyId"))
							{
								UserAdminComponent.logger.log(0,"-Company Id is- "
										+ userPatternAttribute.getValue());
								userPatternInfo
										.setCompanyId(userPatternAttribute.getValue().trim());
								userpatternRoleInfo.setCompanyId(userPatternAttribute.getValue()
										.trim());
								userpatternUserInfo.setCompanyId(userPatternAttribute.getValue()
										.trim());
							}
							if (userPatternAttribute != null
									&& userPatternAttribute.getName().equalsIgnoreCase(
											Constants.USERPAT_DFP_ATT))
							{
								UserAdminComponent.logger.log(0,"--defaultformpattern is- "
										+ userPatternAttribute.getValue());
								userPatternInfo.setDefaultFormPattern(userPatternAttribute
										.getValue().trim());
							}
						}
						userPatternImpl = new UserPatternImpl();
						userpattern = userPatternImpl.addUserpattern(
								userPatternInfo.getCompanyId(), userPatternInfo.getUserPatternId(),
								"", userPatternInfo.getDefaultFormPattern());
						UserAdminComponent.logger.log(0,"--After saving userpattern now do for userpatternRole --check userpattern id--"
										+ userpattern.getUserPatternId());
						// Add Roles for UserPattern
						userpatternRoleDetails = new Hashtable<String, ArrayList<Role>>();
						userpatternUserDetails = new Hashtable<String, ArrayList<User>>();
						Iterator uproleuserIterator = userPatternElement.elementIterator();
						while (uproleuserIterator.hasNext())
						{
							UserAdminComponent.logger.log(0,"--iterator on userPattern element-"
									+ uproleuserIterator.hasNext());
							Element userRoleElement = (Element) uproleuserIterator.next();
							UserAdminComponent.logger.log(0,"--iterator on  element-check element"
									+ userRoleElement);
							UserAdminComponent.logger.log(0,"--Before Start-if 1--"
									+ userRoleElement.getName());
							if ((null != userRoleElement) && (null != userRoleElement.getName())
									&& !("".equals(userRoleElement.getName())))
							{
								UserAdminComponent.logger.log(0,"--Start check-if 1--"
										+ userRoleElement.getName());
								if (userRoleElement.getName().equalsIgnoreCase("Users"))
								{
									userList = new ArrayList<User>();
									UserAdminComponent.logger.log(0,"--Start check-if Users--");
									Iterator userIterator = userRoleElement.elementIterator();
									while (userIterator.hasNext())
									{
										UserAdminComponent.logger.log(0,"--Start check-Iterator on user-");
										Element userinusers = (Element) userIterator.next();
										UserAdminComponent.logger.log(0,"--Iterator element on users-"
												+ userinusers);
										for (Iterator modCounter = userinusers.attributeIterator(); modCounter
												.hasNext();)
										{
											UserAdminComponent.logger.log(0,"--Start check-if 3--");
											Attribute userAttribute = (Attribute) modCounter.next();
											if (userAttribute != null
													&& userAttribute.getName().equals("userid"))
											{
												UserAdminComponent.logger.log(0,"userid Id is "
														+ userAttribute.getValue());
												userpatternUserInfo.setUserId(userAttribute
														.getValue().trim());
											}
										}
										UserAdminComponent.logger.log(0,"-userpatternUserInfo userpattern id--"
														+ userpatternUserInfo.getUserPatternId());
										UserAdminComponent.logger.log(0,"-userpatternUserInfo user id-"
												+ userpatternUserInfo.getUserId());
										if (userpatternUserDetails.containsKey(userpatternUserInfo
												.getUserPatternId()
												+ userpatternUserInfo.getUserId()))
										{
											UserAdminComponent.logger.log(0,"--Start check-if 4--");
											userList = userpatternUserDetails
													.get(userpatternUserInfo.getUserPatternId()
															+ userpatternUserInfo.getUserId());
											User user = new User();
											user.setUserId(userpatternUserInfo.getUserId());
											userList.add(user);
										}
										else
										{
											UserAdminComponent.logger.log(0,"--Start check-if 5--");
											User user = new User();
											user.setUserId(userpatternUserInfo.getUserId());
											userList.add(user);
											userpatternUserDetails.put(userpatternUserInfo
													.getUserPatternId()
													+ userpatternUserInfo.getUserId(), userList);
										}
									}
									UserAdminComponent.logger.log(0,"-Check user list size-"
											+ userList.size());
									userPatternUserImpl = new UserpatternUsersImpl();
									userPatternUserImpl.addUserpatternUsers(userpatternUserInfo
											.getUserPatternId(),
											userpatternUserInfo.getCompanyId(), userList);
								}
								else if (userRoleElement.getName().equalsIgnoreCase("Role"))
								{

									roleList = new ArrayList<Role>();
									UserAdminComponent.logger.log(0,"--Start check-if Role--");
									for (Iterator modCounter = userRoleElement.attributeIterator(); modCounter
											.hasNext();)
									{
										UserAdminComponent.logger.log(0,"--Start check-if 3--");
										Attribute roleAttribute = (Attribute) modCounter.next();
										if (roleAttribute != null
												&& roleAttribute.getName().equalsIgnoreCase(
														"roleid"))
										{
											UserAdminComponent.logger.log(0,"roleid Id is "
													+ roleAttribute.getValue());
											userpatternRoleInfo.setRoleId(roleAttribute.getValue()
													.trim());
										}
									}
									UserAdminComponent.logger.log(0,"--userpatternRoleInfo userpattern id--"
											+ userpatternRoleInfo.getUserPatternId());
									UserAdminComponent.logger.log(0,"--userpatternRoleInfo Role id-"
											+ userpatternRoleInfo.getRoleId());
									if (userpatternRoleDetails.containsKey(userpatternRoleInfo
											.getUserPatternId()
											+ userpatternRoleInfo.getRoleId()))
									{
										UserAdminComponent.logger.log(0,"--Start check-if 3--");
										roleImpl = new RoleImpl();
										roleList = userpatternRoleDetails.get(userpatternRoleInfo
												.getUserPatternId()
												+ userpatternRoleInfo.getRoleId());
										Role rolele = new Role();
										rolele = roleImpl.getRolebyCompanyAndName(
												userpatternRoleInfo.getCompanyId(),
												userpatternRoleInfo.getRoleId());
										UserAdminComponent.logger.log(0,"Check Role id fetched from Db-"
												+ rolele.getRoleId());
										roleList.add(rolele);
									}
									else
									{
										UserAdminComponent.logger.log(0,"--Start check-if 4--");
										roleImpl = new RoleImpl();
										Role rolele = new Role();
										rolele = roleImpl.getRolebyCompanyAndName(
												userpatternRoleInfo.getCompanyId(),
												userpatternRoleInfo.getRoleId());
										UserAdminComponent.logger.log(0,"-Check Role id fetched from Db-"
												+ rolele.getRoleId());
										roleList.add(rolele);
										userpatternRoleDetails.put(userpatternRoleInfo
												.getUserPatternId()
												+ userpatternRoleInfo.getRoleId(), roleList);
									}
									UserAdminComponent.logger.log(0,"-Check role list size--"
											+ roleList.size());
									userPatternRolesImpl = new UserpatternRolesImpl();
									userPatternRolesImpl.addUserpatternRoles(userpatternRoleInfo
											.getUserPatternId(),
											userpatternRoleInfo.getCompanyId(), roleList);

								}
							}

						}

					}

				}
				else
				{
					rootIterator.next();
				}
			}
		}
  
		catch (DocumentException e)
		{
			e.printStackTrace();
			UserAdminComponent.logger.log(0,"-Exception while parsing xml-");
		}

	}
}
