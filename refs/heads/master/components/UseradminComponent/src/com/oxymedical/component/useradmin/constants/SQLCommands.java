package com.oxymedical.component.useradmin.constants;

public class SQLCommands
{
	public static final String List_Company = "from Company";

	public static final String List_Country = "from Country";

	public static final String Select_Company = "from Company as company"
			+ " where company.companyId =:companyId";
	public static final String Select_Container_From_Company = "from CompanyCont as companyCont"
			+ " where companyCont.company.companyId = :companyId"
			+ " and companyCont.container.containerId = :containerId";
	public static final String Select_Company_From_Container = "from CompanyCont as companyCont"
			+ " where companyCont.container.containerId = :containerId";

	public static final String Select_Country = "from Country as country"
			+ " where country.countryId =:countryId";
	public static final String Select_Country_From_Name = "from Country as country"
			+ " where country.name =:name";

	public static final String Select_Region_From_Name = "from Region as region"
			+ " where region.name =:name";

	public static final String List_Region = "from Region";

	public static final String Select_Role = "from Role as role where role.roleId =:roleId";

	public static final String Select_Region = "from Region as region"
			+ " where region.regionId =:regionId";

	public static final String Select_Region_From_Country = "from Region as region"
			+ " where region.countryId =:countryId";

	public static final String Validate_Company = "from Company as company"
			+ " where company.companyId =:companyId";

	public static final String Select_User = "from User as user"
			+ " where user.companyId = :companyId";

	public static final String Find_User_From_Contact = "from Contact as contact"
			+ " where contact.userName = :userName";
	public static final String Find_User_Id_Password = " from User as user where user.userId = :userId"
			+ " and user.password =:password";
	/*
	 * user.deleted condition added in find user query
	 * changes by wasim, 4-june-2009
	 */
	public static final String Find_User = "from User as user "
			+ " where user.userId = :userId"  + " and user.deleted=0";
			
	public static final String Find_Group = "select group from Group as group, User as user,UsersGroup usrgroup  "
		+ " where group.groupId = usrgroup.comp_id.groupId and usrgroup.comp_id.userId = :userId ";//  + " and group.deleted=0";
	
	public static final String Find_UsersGroup = "select usrgroup.comp_id.userId,user.emailAddress from Group as group, User as user,UsersGroup usrgroup  "
		+ " where user.userId=usrgroup.comp_id.userId and group.groupId = usrgroup.comp_id.groupId and group.name= :groupName and user.deleted=0";//  + " and group.deleted=0";
	
	
	
	public static final String Find_LogedUser = "from Logindetail as loginD"
				+ " where loginD.userId = :userId";

	public static final String Find_Right = "from Right as rights where rights.rightId =:rightId";

	public static final String Find_Container = "from Container as container"
			+ " where container.containerId = :containerId";
	public static final String List_Counter = "from Counter as counter"
			+ " where counter.name = :name";
	public static final String Validate_Group = "from Group as group"
			+ " where group.groupId =:groupId";
	public static final String Validate_Role = "from Role as role"
			+ " where role.roleId =:roleId";
	public static final String Select_Role_From_User = " from UsersRole as usersRole "
			+ " where usersRole.comp_id.userId =:userId";

	public static final String FIND_User_Org = " from UsersOrg as usersOrg"
			+ " where usersOrg.comp_id.organizationId = :organizationId";

	public static final String FIND_User_Group = " from UsersGroup as usersGroup"
			+ " where usersGroup.comp_id.groupId = :groupId";

	public static final String Select_Group = "from Group as group"
			+ " where group.groupId =:groupId";
	public static final String Select_Group_C_N = "from Group as group"
			+ " where group.companyId = :companyId " + " and group.name = :name";
	public static final String Find_Organization = "from Organization as organization"
			+ " where organization.organizationId = :organizationId";
	public static final String Find_Organization_From_Company_Name = "from Organization as organization"
			+ " where organization.companyId = :companyId" + " and organization.name = :name";
	public static final String Find_Group_From_Company_Name = "from Group as group"
			+ " where group.companyId = :companyId" + " and group.name = :name";

	public static final String Find_Role_From_Company_Name = "from Role as role"
			+ " where role.companyId = :companyId" + " and role.name = :name";

	public static final String Find_Right_From_Company_Name = "from Right as rights"
			+ " where rights.companyId = :companyId" + " and rights.name = :name";

	public static final String Select_Group_org = "from Organization as organization , GroupsOrg as groupsOrg where groupsOrg.comp_id.organizationId = organization.organizationId and groupsOrg.comp_id.groupId = :groupId";

	public static final String Clear_User_Organization = "delete from UsersOrg as usersOrg where usersOrg.comp_id.userId = :userId";
	public static final String Clear_User_Organization_hql = "delete from UsersOrg as usersOrg where usersOrg.comp_id.userId = ";
	public static final String Clear_User_Organization_basis_Org = "delete from UsersOrg as usersOrg where usersOrg.comp_id.organizationId = ";
	public static final String Append_Organization_basis_User = " and usersOrg.comp_id.userId = ";

	public static final String Clear_User = "delete from User as user where user.userId = :userId";
	public static final String Clear_User_hql = "delete from User as user where user.userId = ";

	public static final String Clear_User_Contact = "delete from Contact as contact where contact.userId = :userId";
	public static final String Clear_User_Contact_hql = "delete from Contact as contact where contact.userId = ";

	public static final String Clear_User_Role = "delete from UsersRole as usersRole where usersRole.comp_id.userId = :userId";
	public static final String Clear_User_Role_hql = "delete from UsersRole as usersRole where usersRole.comp_id.userId = ";

	public static final String Clear_User_UserPattern = "delete from UserpatternUser as c where c.comp_id.userId = :userId";
	public static final String Clear_User_UserPattern_hql = "delete from UserpatternUser as c where c.comp_id.userId = ";
	public static final String Clear_User_UserPattern_basis_UserPat = "delete from UserpatternUser as c where c.comp_id.userPatternId = ";
	public static final String Append_UserPattern_basis_User = " and c.comp_id.userId = ";

	public static final String Clear_User_Group = "delete from UsersGroup as usersGroup where usersGroup.comp_id.userId = :userId";
	public static final String Clear_User_Group_hql = "delete from UsersGroup as usersGroup where usersGroup.comp_id.userId = ";

	public static final String Clear_Organization = "delete from Organization as organization where organization.organizationId = :organizationId";

	public static final String Clear_Group = "delete from Group as group where group.groupId = :groupId and group.className = :className";

	public static final String Select_User_Organizations = "from Organization as organization ," +
			" UsersOrg as usersOrg where usersOrg.comp_id.organizationId = organization.organizationId and usersOrg.comp_id.userId = :userId";

	
	  public static final String Find_Orgs_ForUserId_From_UserOrgs =
	  "select c.comp_id.organizationId from" +
	  " UsersOrg c  where c.comp_id.userId = :userId";
	 

	public static final String Has_Group_Orgs = "select count(*) as count_value from GroupsOrg as groupsOrg where groupsOrg.comp_id.groupId = :groupId and groupsOrg.comp_id.organizationId = :organizationId";

	public static final String Get_Organization_Group = "from Group as group where group.companyId = :companyId "
			+ " and group.className = :className " + " and group.classPk = :classPk";

	public static final String Get_Group_Roles = "from Group as group , GroupsRole as groupsRole where groupsRole.comp_id.groupId = group.groupId"
			+ " and groupsRole.comp_id.roleId = :roleId ";
	public static final String Get_User_Group = "from Group as group where group.companyId = :companyId"
			+ " and group.className = :className " + " and group.classPk = :classPk";
	public static final String Get_Org_By_Company = "from Organization as organization where organization.companyId = :companyId"
			+ " and organization.parentOrganizationId<>organization.name";
	public static final String Get_Org_Structure = "from Organization as organization where organization.parentOrganizationId = :name"
			+ " and organization.name<>organization.parentOrganizationId";

	public static final String Select_Role_From_Name = "from Role as role where role.name =:name ";
	public static final String Select_Rights_From_Role = "select roleRight.comp_id.rightId ,rights.name from RoleRight as roleRight,Right as rights where roleRight.comp_id.roleId = :roleId"
			+ " and rights.rightId =roleRight.comp_id.rightId order by rights.name";

	public static final String Select_Right = "from Right as rights where rights.name = :name";

	public static final String Find_UserPat_ForUserId_From_UserPatUsers = "select c.comp_id.userPatternId from"
			+ " UserpatternUser c  where c.comp_id.userId = :userId";
	
	public static final String Find_UserId_From_UserPatUsers = "select c.comp_id.userId from"
			+ " UserpatternUser c, User u where c.comp_id.userPatternId = :userPatternId and u.userId = c.comp_id.userId and u.deleted = 0";

	public static final String Find_UserPattern_From_Company_Name = "from Userpattern as userpattern"
			+ " where userpattern.companyId = :companyId"
			+ " and userpattern.userPatternId = :userPatternId";

	public static final String Find_UserPattern = "from Userpattern as userpattern"
			+ " where userpattern.userPatternId = :userPatternId";

	public static final String Find_defFormPat_ForUserPatId_From_UserPat = "select c.defaultFormPattern from"
			+ " Userpattern c  where c.userPatternId = :userPatternId";
	// Added query for Soft Delete the user added by pra on 26-may-2009
	
	public static final String Clear_Soft_User_hql = "update User set deleted='1' where userId=";
	
	//Added query to get Role based on userPatternId by Pra on 29-May-2009
	public static final String Find_RoleId_Base_On_UserpatternId = "from Role as Role, UserpatternRole as UserpatternRole"
		+ " where UserpatternRole.comp_id.userPatternId = :userPatternId  and Role.roleId=UserpatternRole.comp_id.roleId";
	//Added query to select fields based on UserPatternId by pra on 11-june-2009
	public static final String Select_Fields_Base_On_UserPatternId="select userfieldsforusertype.comp_id.userFieldId,userfield.fieldName from Userfieldsforusertype as userfieldsforusertype," +
			"Userfield as userfield where userfieldsforusertype.comp_id.userTypeId =:userPatternId and userfieldsforusertype.comp_id.userFieldId=userfield.fieldId";
	//added query for rolerighs and fields in case of edit done by pra on 16 june-2009
	public static final String Find_RoleRights_Based_on_RoleId="from RoleRight as roleRight where roleRight.comp_id.roleId = :roleId";
	public static final String Find_Fields_Based_on_UserPatternId="from Userfieldsforusertype as userfieldsforusertype where userfieldsforusertype.comp_id.userTypeId =:userPatternId ";
	//md
	public static final String Select_Groups = "from Group as group";
	//md
	// Start chnages by netram sahu on 25 jun 2012
	public static final String Find_Login_cntr = "from Logintracker as logintracker"+ " where logintracker.userId = :userId";
	public static final String Find_Lead_User = "from Grouplead as grouplead "+ " where grouplead.comp_id.groupId = :groupId";
	// End chnages by netram sahu on 25 jun 2012
	
}
