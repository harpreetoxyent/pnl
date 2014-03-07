drop database if exists useradmin;
create database useradmin;
use useradmin;
create table Account_ 
(
	accountId varchar(100) not null primary key,
	companyId varchar(100) not null,
	userId varchar(100) not null,
	userName varchar(100) null,
	createDate datetime null,
	modifiedDate datetime null,
	parentAccountId varchar(100) null,
	name varchar(100) null
);

create table Address 
(
	addressId varchar(100) not null primary key,
	companyId varchar(100) not null,
	userId varchar(100) not null,
	userName varchar(100) null,
	street1 varchar(100) null,
	className varchar(100) null,
	classPK varchar(100) null,
	city varchar(100) null,
	zip varchar(100) null,
	regionId varchar(100) null,
	countryId varchar(100) null
);

create table Company 
(
	companyId varchar(100) not null primary key,
	homeURL varchar(100) null
);


create table Contact_ 
(
	contactId varchar(100) not null primary key,
	companyId varchar(100) not null,
	userId varchar(100) not null,
	userName varchar(100) null,
	createDate datetime null,
	modifiedDate datetime null,
	accountId varchar(100) null,
	firstName varchar(100) null,
	middleName varchar(100) null,
	lastName varchar(100) null,
	prefixId varchar(100) null,
	suffixId varchar(100) null,
	male tinyint,
	birthday datetime null,
	jobTitle varchar(100) null,
	employeeNumber varchar(100) null,
	category varchar(100) null,
	Address1 varchar(100) null,
	Address2 varchar(100) null,
	zipCode  bigint,
	telephoneNumber bigint,
	universalProviderIdentificationNumber varchar(100) null,
	nationalProvidedIdentificationNumber varchar(100) null,
	state varchar(100) null,
	city integer
	
);

create table Counter (
	name varchar(100) not null primary key,
	currentId integer
);


create table Country (
	countryId varchar(100) not null primary key,
	name varchar(100) null,
	active_ tinyint
);
create table EmailAddress 
(
	emailAddressId varchar(100) not null primary key,
	companyId varchar(100) not null,
	userId varchar(100) not null,
	userName varchar(100) null,
	className varchar(100) null,
	classPK varchar(100) null,
	address varchar(100) null,
	typeId varchar(100) null,
	primary_ tinyint
);

create table Group_ (
	groupId varchar(100) not null primary key,
	companyId varchar(100) not null,
	parentGroupId varchar(100) null,
	className varchar(100) null,
	classPK varchar(100) null,
	name varchar(100) null,
	description longtext null,
	type_ varchar(100) null
);

create table Groups_Orgs (
	groupId varchar(100) not null,
	organizationId varchar(100) not null,
	primary key (groupId, organizationId)
);

create table Groups_Permissions (
	groupId  varchar(100) not null,
	permissionId varchar(100) not null,
	primary key (groupId, permissionId)
);

create table Groups_Roles (
	groupId varchar(100) not null,
	roleId varchar(100) not null,
	primary key (groupId, roleId)
);

create table Groups_UserGroups (
	groupId varchar(100) not null,
	userGroupId varchar(100) not null,
	primary key (groupId, userGroupId)
);
create table Layout (
	layoutId varchar(100) not null,
	ownerId varchar(100) not null,
	companyId varchar(100) not null,
	parentLayoutId varchar(100) null,
	name longtext null,
	title longtext null,
	type_ varchar(100) null,
	typeSettings longtext null,
	hidden_ tinyint,
	friendlyURL varchar(100) null,
	themeId varchar(100) null,
	colorSchemeId varchar(100) null,
	priority integer,
	primary key (layoutId, ownerId)
);

create table LayoutSet (
	ownerId varchar(100) not null primary key,
	companyId varchar(100) not null,
	groupId varchar(100) not null,
	userId varchar(100) not null,
	privateLayout tinyint,
	themeId varchar(100) null,
	colorSchemeId varchar(100) null,
	pageCount integer,
	virtualHost varchar(100) null
);
create table Organization_ (
	organizationId varchar(100) not null primary key,
	companyId varchar(100) not null,
	parentOrganizationId varchar(100) null,
	name varchar(100) null,
	regionId varchar(100) null,
	countryId varchar(100) null,
	statusId varchar(100) null,
	comments longtext null
);

create table OrgGroupPermission (
	organizationId varchar(100) not null,
	groupId varchar(100) not null,
	permissionId varchar(100) not null,
	primary key (organizationId, groupId, permissionId)
);

create table OrgGroupRole (
	organizationId varchar(100) not null,
	groupId varchar(100) not null,
	roleId varchar(100) not null,
	primary key (organizationId, groupId, roleId)
);
create table PasswordTracker (
	passwordTrackerId varchar(100) not null primary key,
	userId varchar(100) not null,
	createDate datetime null,
	password_ varchar(100) null
);

create table Permission_ (
	permissionId varchar(100) not null primary key,
	companyId varchar(100) not null,
	actionId varchar(100) null,
	resourceId varchar(100) null
);

create table Region (
	regionId varchar(100) not null primary key,
	countryId varchar(100) null,
	regionCode varchar(100) null,
	name varchar(100) null,
	active_ tinyint
);
create table Role_ (
	roleId varchar(100) not null primary key,
	companyId varchar(100) not null,
	name varchar(100) null,
	className varchar(100) null,
	classPK varchar(100) null,
	defaultURL varchar(100) null,
	description longtext null
);

create table Roles_Permissions (
	roleId varchar(100) not null,
	permissionId varchar(100) not null,
	primary key (roleId, permissionId)
);

create table User_ (
	userId varchar(100) not null primary key,
	companyId varchar(100) not null,
	contactId varchar(100) null,
	password_ varchar(100) null,
	emailAddress varchar(100) null,
	languageId varchar(100) null,
	greeting varchar(100) null,
	comments longtext null,
	active_ tinyint,
	deleted varchar(1) default '0',
        inActiveDate date null
);
create table UserGroup (
	userGroupId varchar(100) not null primary key,
	companyId varchar(100) not null,
	parentUserGroupId varchar(100) null,
	name varchar(100) null,
	description longtext null
);


create table UserIdMapper (
	userId varchar(100) not null,
	type_ varchar(100) not null,
	description varchar(100) null,
	externalUserId varchar(100) null,
	primary key (userId, type_)
);

create table Users_Groups (
	userId varchar(100) not null,
	groupId varchar(100) not null,
	primary key (userId, groupId)
);

create table Users_Orgs (
	userId varchar(100) not null,
	organizationId varchar(100) not null,
	primary key (userId, organizationId)
);

create table Users_Permissions (
	userId varchar(100) not null,
	permissionId varchar(100) not null,
	primary key (userId, permissionId)
);

create table Users_Roles (
	userId varchar(100) not null,
	roleId varchar(100) not null,
	primary key (userId, roleId)
);

create table Users_UserGroups (
	userId varchar(100) not null,
	userGroupId varchar(100) not null,
	primary key (userId, userGroupId)
);


create table UserPatterns(
	userPatternId varchar(100) not null primary key,
	companyId varchar(100) not null,
	comments varchar(100) null,
	defaultFormPattern varchar(100) null
);


create table Component(
	componentId varchar(100) , compName varchar(100),
	CONSTRAINT componentId_PK primary key (componentId)
);

create table Container_Type (containerTypeId varchar(100), containerTypeName varchar (50),
CONSTRAINT containerTypeId_PK primary key ( containerTypeId));

create table Container ( containerId  varchar(100) , containerTypeId  varchar(100) ,
CONSTRAINT containerId_PK primary key ( containerId), 
CONSTRAINT containerTypeId_FK FOREIGN KEY (containerTypeId) REFERENCES Container_Type(containerTypeId));

create table Cont_Comp(contCompId int AUTO_INCREMENT , containerId varchar(100),componentId varchar(100),
CONSTRAINT compContId_PK primary key (contCompId), 
CONSTRAINT containerId_FK FOREIGN KEY (containerId) REFERENCES Container (containerId),
CONSTRAINT componentId_FK FOREIGN KEY (componentId) REFERENCES Component (componentId),
CONSTRAINT contCompUnId_UN UNIQUE(containerId,componentId));

create table Company_Cont ( userContId int AUTO_INCREMENT , companyId  varchar(100) , containerId  varchar(100) ,
CONSTRAINT companyContId_PK primary key ( userContId),
CONSTRAINT companyId_FK FOREIGN KEY (companyId) REFERENCES Company (companyId),
CONSTRAINT containerIdCCFK FOREIGN KEY (containerId) REFERENCES Container (containerId),
CONSTRAINT compContUnId_UN UNIQUE(companyId,containerId));


create table Rights_(rightId varchar(100),name varchar(100) ,companyId varchar(100)not null, languageproperties varchar(100),defaultURL varchar(100),
CONSTRAINT rightId_PK primary key (rightId));

create table Role_Rights(rightId varchar(100) not null ,roleId varchar(100) not null,companyId varchar(100)not null,primary key (roleId, rightId));

create table userPattern_Roles(userPatternId varchar(100) not null ,roleId varchar(100) not null,companyId varchar(100)not null,primary key (userPatternId, roleId));
create table userPattern_Users(userPatternId varchar(100) not null ,userId varchar(100) not null,companyId varchar(100)not null,primary key (userPatternId, userId));


insert into Country (countryId, name,  active_) values ('1', 'Canada', 1);
insert into Country (countryId, name,  active_) values ('2', 'China',  1);
insert into Country (countryId, name,  active_) values ('3', 'France', 1);
insert into Country (countryId, name,  active_) values ('4', 'Germany', 1);
insert into Country (countryId, name,  active_) values ('5', 'Hong Kong', 1);
insert into Country (countryId, name,  active_) values ('6', 'Hungary',  1);
insert into Country (countryId, name,  active_) values ('7', 'Israel', 1);
insert into Country (countryId, name,  active_) values ('8', 'Italy', 1);
insert into Country (countryId, name,  active_) values ('9', 'Japan', 1);
insert into Country (countryId, name,  active_) values ('10', 'South Korea', 1);
insert into Country (countryId, name,  active_) values ('11', 'Netherlands',  1);
insert into Country (countryId, name,  active_) values ('12', 'Portugal',  1);
insert into Country (countryId, name,  active_) values ('13', 'Russia',  1);
insert into Country (countryId, name,  active_) values ('14', 'Singapore',  1);
insert into Country (countryId, name,  active_) values ('15', 'Spain',  1);
insert into Country (countryId, name,  active_) values ('16', 'Turkey', 1);
insert into Country (countryId, name,  active_) values ('17', 'Vietnam', 1);
insert into Country (countryId, name,  active_) values ('18', 'United Kingdom', 1);
insert into Country (countryId, name,  active_) values ('19', 'United States', 1);
insert into Country (countryId, name,  active_) values ('20', 'Afghanistan', 1);
insert into Country (countryId, name,  active_) values ('21', 'Albania',  1);
insert into Country (countryId, name,  active_) values ('22', 'Algeria', 1);
insert into Country (countryId, name,  active_) values ('23', 'American Samoa', 1);
insert into Country (countryId, name,  active_) values ('24', 'Andorra', 1);
insert into Country (countryId, name,  active_) values ('25', 'Angola',  1);
insert into Country (countryId, name,  active_) values ('26', 'Anguilla', 1);
insert into Country (countryId, name,  active_) values ('27', 'Antarctica', 1);
insert into Country (countryId, name,  active_) values ('28', 'Antigua', 1);
insert into Country (countryId, name,  active_) values ('29', 'Argentina',1);
insert into Country (countryId, name,  active_) values ('30', 'Armenia', 1);
insert into Country (countryId, name,  active_) values ('31', 'Aruba', 1);
insert into Country (countryId, name,  active_) values ('32', 'Australia', 1);
insert into Country (countryId, name,  active_) values ('33', 'Austria', 1);
insert into Country (countryId, name,  active_) values ('34', 'Azerbaijan', 1);
insert into Country (countryId, name,  active_) values ('35', 'Bahamas', 1);
insert into Country (countryId, name,  active_) values ('36', 'Bahrain', 1);
insert into Country (countryId, name,  active_) values ('37', 'Bangladesh',  1);
insert into Country (countryId, name,  active_) values ('38', 'Barbados', 1);
insert into Country (countryId, name,  active_) values ('39', 'Belarus',1);
insert into Country (countryId, name,  active_) values ('40', 'Belgium',1);
insert into Country (countryId, name,  active_) values ('41', 'Belize', 1);
insert into Country (countryId, name,  active_) values ('42', 'Benin', 1);
insert into Country (countryId, name,  active_) values ('43', 'Bermuda',1);
insert into Country (countryId, name,  active_) values ('44', 'Bhutan',1);
insert into Country (countryId, name,  active_) values ('45', 'Bolivia', 1);
insert into Country (countryId, name,  active_) values ('46', 'Bosnia-Herzegovina', 1);
insert into Country (countryId, name,  active_) values ('47', 'Botswana', 1);
insert into Country (countryId, name,  active_) values ('48', 'Brazil',1);
insert into Country (countryId, name,  active_) values ('49', 'British Virgin Islands',1);
insert into Country (countryId, name,  active_) values ('50', 'Brunei', 1);



insert into Country (countryId, name,  active_) values ('51', 'Bulgaria',1);
insert into Country (countryId, name,  active_) values ('52', 'Burkina Faso', 1);
insert into Country (countryId, name,  active_) values ('53', 'Burma ', 1);
insert into Country (countryId, name,  active_) values ('54', 'Burundi',1);
insert into Country (countryId, name,  active_) values ('55', 'Cambodia', 1);
insert into Country (countryId, name,  active_) values ('56', 'Cameroon',  1);
insert into Country (countryId, name,  active_) values ('57', 'Cape Verde Island',1);
insert into Country (countryId, name,  active_) values ('58', 'Cayman Islands',1);
insert into Country (countryId, name,  active_) values ('59', 'Central African Republic',1);
insert into Country (countryId, name,  active_) values ('60', 'Chad',1);
insert into Country (countryId, name,  active_) values ('61', 'Chile', 1);
insert into Country (countryId, name,  active_) values ('62', 'Christmas Island', 1);
insert into Country (countryId, name,  active_) values ('63', 'Cocos Islands',1);
insert into Country (countryId, name,  active_) values ('64', 'Colombia',1);
insert into Country (countryId, name,  active_) values ('65', 'Comoros',1);
insert into Country (countryId, name,  active_) values ('66', 'Republic of Congo',1);
insert into Country (countryId, name,  active_) values ('67', 'Democratic Republic of Congo', 1);
insert into Country (countryId, name,  active_) values ('68', 'Cook Islands', 1);
insert into Country (countryId, name,  active_) values ('69', 'Costa Rica',1);
insert into Country (countryId, name,  active_) values ('70', 'Croatia',1);
insert into Country (countryId, name,  active_) values ('71', 'Cuba', 1);
insert into Country (countryId, name,  active_) values ('72', 'Cyprus', 1);
insert into Country (countryId, name,  active_) values ('73', 'Czech Republic', 1);
insert into Country (countryId, name,  active_) values ('74', 'Denmark',  1);
insert into Country (countryId, name,  active_) values ('75', 'Djibouti', 1);
insert into Country (countryId, name,  active_) values ('76', 'Dominica', 1);
insert into Country (countryId, name,  active_) values ('77', 'Dominican Republic', 1);
insert into Country (countryId, name,  active_) values ('78', 'Ecuador', 1);
insert into Country (countryId, name,  active_) values ('79', 'Egypt', 1);
insert into Country (countryId, name,  active_) values ('80', 'El Salvador', 1);
insert into Country (countryId, name,  active_) values ('81', 'Equatorial Guinea', 1);
insert into Country (countryId, name,  active_) values ('82', 'Eritrea', 1);
insert into Country (countryId, name,  active_) values ('83', 'Estonia', 1);
insert into Country (countryId, name,  active_) values ('84', 'Ethiopia',  1);
insert into Country (countryId, name,  active_) values ('85', 'Faeroe Islands',1);
insert into Country (countryId, name,  active_) values ('86', 'Falkland Islands', 1);
insert into Country (countryId, name,  active_) values ('87', 'Fiji Islands',1);
insert into Country (countryId, name,  active_) values ('88', 'Finland', 1);
insert into Country (countryId, name,  active_) values ('89', 'French Guiana',1);
insert into Country (countryId, name,  active_) values ('90', 'French Polynesia',1);
insert into Country (countryId, name,  active_) values ('91', 'Gabon',1);
insert into Country (countryId, name,  active_) values ('92', 'Gambia',  1);
insert into Country (countryId, name,  active_) values ('93', 'Georgia', 1);
insert into Country (countryId, name,  active_) values ('94', 'Ghana', 1);
insert into Country (countryId, name,  active_) values ('95', 'Gibraltar',1);
insert into Country (countryId, name,  active_) values ('96', 'Greece', 1);
insert into Country (countryId, name,  active_) values ('97', 'Greenland',1);
insert into Country (countryId, name,  active_) values ('98', 'Grenada', 1);
insert into Country (countryId, name,  active_) values ('99', 'Guadeloupe', 1);
insert into Country (countryId, name,  active_) values ('100', 'Guam', 1);
insert into Country (countryId, name,  active_) values ('101', 'Guatemala',1);
insert into Country (countryId, name,  active_) values ('102', 'Guinea',  1);
insert into Country (countryId, name,  active_) values ('103', 'Guinea-Bissau', 1);
insert into Country (countryId, name,  active_) values ('104', 'Guyana', 1);
insert into Country (countryId, name,  active_) values ('105', 'Haiti',1);
insert into Country (countryId, name,  active_) values ('106', 'Honduras',  1);
insert into Country (countryId, name,  active_) values ('107', 'Iceland', 1);

insert into Country (countryId, name,  active_) values ('108', 'India', 1);

insert into Country (countryId, name,  active_) values ('109', 'Indonesia',1);
insert into Country (countryId, name,  active_) values ('110', 'Iran',  1);
insert into Country (countryId, name,  active_) values ('111', 'Iraq', 1);
insert into Country (countryId, name,  active_) values ('112', 'Ireland', 1);
insert into Country (countryId, name,  active_) values ('113', 'Ivory Coast', 1);
insert into Country (countryId, name,  active_) values ('114', 'Jamaica',1);
insert into Country (countryId, name,  active_) values ('115', 'Jordan',1);
insert into Country (countryId, name,  active_) values ('116', 'Kazakhstan',  1);
insert into Country (countryId, name,  active_) values ('117', 'Kenya',  1);
insert into Country (countryId, name,  active_) values ('118', 'Kiribati', 1);
insert into Country (countryId, name,  active_) values ('119', 'Kuwait',  1);
insert into Country (countryId, name,  active_) values ('120', 'North Korea',  1);
insert into Country (countryId, name,  active_) values ('121', 'Kyrgyzstan',  1);
insert into Country (countryId, name,  active_) values ('122', 'Laos', 1);
insert into Country (countryId, name,  active_) values ('123', 'Latvia',  1);
insert into Country (countryId, name,  active_) values ('124', 'Lebanon',  1);
insert into Country (countryId, name,  active_) values ('125', 'Lesotho',1);
insert into Country (countryId, name,  active_) values ('126', 'Liberia', 1);
insert into Country (countryId, name,  active_) values ('127', 'Libya',1);
insert into Country (countryId, name,  active_) values ('128', 'Liechtenstein',1);
insert into Country (countryId, name,  active_) values ('129', 'Lithuania',  1);


insert into Country (countryId, name,  active_) values ('130', 'Luxembourg',1);
insert into Country (countryId, name,  active_) values ('131', 'Macau', 1);
insert into Country (countryId, name,  active_) values ('132', 'Macedonia',  1);

insert into Country (countryId, name,  active_) values ('133', 'Madagascar',  1);
insert into Country (countryId, name,  active_) values ('134', 'Malawi',1);
insert into Country (countryId, name,  active_) values ('135', 'Malaysia',  1);
insert into Country (countryId, name,  active_) values ('136', 'Maldives', 1);

insert into Country (countryId, name,  active_) values ('137', 'Mali',  1);
insert into Country (countryId, name,  active_) values ('138', 'Malta', 1);
insert into Country (countryId, name,  active_) values ('139', 'Marshall Islands', 1);
insert into Country (countryId, name,  active_) values ('140', 'Martinique',  1);
insert into Country (countryId, name,  active_) values ('141', 'Mauritania', 1);
insert into Country (countryId, name,  active_) values ('142', 'Mauritius', 1);
insert into Country (countryId, name,  active_) values ('143', 'Mayotte Island',  1);
insert into Country (countryId, name,  active_) values ('144', 'Mexico', 1);
insert into Country (countryId, name,  active_) values ('145', 'Micronesia',1);
insert into Country (countryId, name,  active_) values ('146', 'Moldova',1);
insert into Country (countryId, name,  active_) values ('147', 'Monaco',1);
insert into Country (countryId, name,  active_) values ('148', 'Mongolia',1);
insert into Country (countryId, name,  active_) values ('149', 'Montserrat',  1);
insert into Country (countryId, name,  active_) values ('150', 'Morocco',  1);

insert into Country (countryId, name,  active_) values ('151', 'Mozambique', 1);
insert into Country (countryId, name,  active_) values ('152', 'Myanmar (Burma)', 1);
insert into Country (countryId, name,  active_) values ('153', 'Namibia', 1);
insert into Country (countryId, name,  active_) values ('154', 'Nauru',  1);
insert into Country (countryId, name,  active_) values ('155', 'Nepal',  1);
insert into Country (countryId, name,  active_) values ('156', 'Netherlands Antilles', 1);
insert into Country (countryId, name,  active_) values ('157', 'New Caledonia', 1);
insert into Country (countryId, name,  active_) values ('158', 'New Zealand', 1);
insert into Country (countryId, name,  active_) values ('159', 'Nicaragua',1);
insert into Country (countryId, name,  active_) values ('160', 'Niger',1);
insert into Country (countryId, name,  active_) values ('161', 'Nigeria',  1);
insert into Country (countryId, name,  active_) values ('162', 'Niue', 1);
insert into Country (countryId, name,  active_) values ('163', 'Norfolk Island', 1);
insert into Country (countryId, name,  active_) values ('164', 'Norway',  1);
insert into Country (countryId, name,  active_) values ('165', 'Oman',  1);
insert into Country (countryId, name,  active_) values ('166', 'Pakistan', 1);
insert into Country (countryId, name,  active_) values ('167', 'Palau', 1);
insert into Country (countryId, name,  active_) values ('168', 'Palestine',  1);
insert into Country (countryId, name,  active_) values ('169', 'Panama',  1);
insert into Country (countryId, name,  active_) values ('170', 'Papua New Guinea',  1);
insert into Country (countryId, name,  active_) values ('171', 'Paraguay', 1);
insert into Country (countryId, name,  active_) values ('172', 'Peru',  1);
insert into Country (countryId, name,  active_) values ('173', 'Philippines',  1);
insert into Country (countryId, name,  active_) values ('174', 'Poland',  1);
insert into Country (countryId, name,  active_) values ('175', 'Puerto Rico', 1);
insert into Country (countryId, name,  active_) values ('176', 'Qatar',1);
insert into Country (countryId, name,  active_) values ('177', 'Reunion Island', 1);
insert into Country (countryId, name,  active_) values ('178', 'Romania', 1);
insert into Country (countryId, name,  active_) values ('179', 'Rwanda', 1);
insert into Country (countryId, name,  active_) values ('180', 'St. Helena',  1);
insert into Country (countryId, name,  active_) values ('181', 'St. Kitts', 1);
insert into Country (countryId, name,  active_) values ('182', 'St. Lucia', 1);
insert into Country (countryId, name,  active_) values ('183', 'St. Pierre & Miquelon',  1);
insert into Country (countryId, name,  active_) values ('184', 'St. Vincent', 1);
insert into Country (countryId, name,  active_) values ('185', 'San Marino', 1);
insert into Country (countryId, name,  active_) values ('186', 'Sao Tome & Principe', 1);
insert into Country (countryId, name,  active_) values ('187', 'Saudi Arabia', 1);
insert into Country (countryId, name,  active_) values ('188', 'Senegal',1);
insert into Country (countryId, name,  active_) values ('189', 'Serbia',  1);
insert into Country (countryId, name,  active_) values ('190', 'Seychelles',  1);

insert into Country (countryId, name,  active_) values ('191', 'Sierra Leone',  1);
insert into Country (countryId, name,  active_) values ('192', 'Slovakia', 1);
insert into Country (countryId, name,  active_) values ('193', 'Slovenia',  1);
insert into Country (countryId, name,  active_) values ('194', 'Solomon Islands',1);
insert into Country (countryId, name,  active_) values ('195', 'Somalia',  1);
insert into Country (countryId, name,  active_) values ('196', 'South Africa',  1);
insert into Country (countryId, name,  active_) values ('197', 'Sri Lanka', 1);
insert into Country (countryId, name,  active_) values ('198', 'Sudan', 1);
insert into Country (countryId, name,  active_) values ('199', 'Suriname',  1);
insert into Country (countryId, name,  active_) values ('200', 'Swaziland', 1);
insert into Country (countryId, name,  active_) values ('201', 'Sweden',1);
insert into Country (countryId, name,  active_) values ('202', 'Switzerland',  1);
insert into Country (countryId, name,  active_) values ('203', 'Syria',  1);
insert into Country (countryId, name,  active_) values ('204', 'Taiwan', 1);
insert into Country (countryId, name,  active_) values ('205', 'Tajikistan',  1);
insert into Country (countryId, name,  active_) values ('206', 'Tanzania', 1);
insert into Country (countryId, name,  active_) values ('207', 'Thailand',  1);
insert into Country (countryId, name,  active_) values ('208', 'Togo',  1);
insert into Country (countryId, name,  active_) values ('209', 'Tonga',  1);
insert into Country (countryId, name,  active_) values ('210', 'Trinidad & Tobago', 1);
insert into Country (countryId, name,  active_) values ('211', 'Tunisia',  1);
insert into Country (countryId, name,  active_) values ('212', 'Turkmenistan',1);


insert into Country (countryId, name,  active_) values ('213', 'Turks & Caicos', 1);
insert into Country (countryId, name,  active_) values ('214', 'Tuvalu', 1);
insert into Country (countryId, name,  active_) values ('215', 'Uganda', 1);
insert into Country (countryId, name,  active_) values ('216', 'Ukraine', 1);
insert into Country (countryId, name,  active_) values ('217', 'United Arab Emirates', 1);
insert into Country (countryId, name,  active_) values ('218', 'Uruguay', 1);
insert into Country (countryId, name,  active_) values ('219', 'Uzbekistan', 1);
insert into Country (countryId, name,  active_) values ('220', 'Vanuatu', 1);
insert into Country (countryId, name,  active_) values ('221', 'Vatican City', 1);
insert into Country (countryId, name,  active_) values ('222', 'Venezuela', 1);
insert into Country (countryId, name,  active_) values ('223', 'Wallis & Futuna', 1);
insert into Country (countryId, name,  active_) values ('224', 'Western Samoa', 1);
insert into Country (countryId, name,  active_) values ('225', 'Yemen',1);
insert into Country (countryId, name,  active_) values ('226', 'Yugoslavia',1);
insert into Country (countryId, name,  active_) values ('227', 'Zambia',  1);
insert into Country (countryId, name,  active_) values ('228', 'Zimbabwe', 1);

insert into Region (regionId, countryId, regionCode, name, active_) values ('1', '19', 'AL', 'Alabama', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('2', '19', 'AK', 'Alaska', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('3', '19', 'AZ', 'Arizona', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('4', '19', 'AR', 'Arkansas', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('5', '19', 'CA', 'California', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('6', '19', 'CO', 'Colorado', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('7', '19', 'CT', 'Connecticut', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('8', '19', 'DC', 'District of Columbia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('9', '19', 'DE', 'Delaware', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('10', '19', 'FL', 'Florida', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('11', '19', 'GA', 'Georgia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('12', '19', 'HI', 'Hawaii', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('13', '19', 'ID', 'Idaho', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('14', '19', 'IL', 'Illinois', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('15', '19', 'IN', 'Indiana', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('16', '19', 'IA', 'Iowa', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('17', '19', 'KS', 'Kansas', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('18', '19', 'KY', 'Kentucky ', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('19', '19', 'LA', 'Louisiana ', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('20', '19', 'ME', 'Maine', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('21', '19', 'MD', 'Maryland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('22', '19', 'MA', 'Massachusetts', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('23', '19', 'MI', 'Michigan', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('24', '19', 'MN', 'Minnesota', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('25', '19', 'MS', 'Mississippi', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('26', '19', 'MO', 'Missouri', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('27', '19', 'MT', 'Montana', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('28', '19', 'NE', 'Nebraska', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('29', '19', 'NV', 'Nevada', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('30', '19', 'NH', 'New Hampshire', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('31', '19', 'NJ', 'New Jersey', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('32', '19', 'NM', 'New Mexico', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('33', '19', 'NY', 'New York', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('34', '19', 'NC', 'North Carolina', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('35', '19', 'ND', 'North Dakota', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('36', '19', 'OH', 'Ohio', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('37', '19', 'OK', 'Oklahoma ', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('38', '19', 'OR', 'Oregon', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('39', '19', 'PA', 'Pennsylvania', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('40', '19', 'PR', 'Puerto Rico', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('41', '19', 'RI', 'Rhode Island', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('42', '19', 'SC', 'South Carolina', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('43', '19', 'SD', 'South Dakota', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('44', '19', 'TN', 'Tennessee', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('45', '19', 'TX', 'Texas', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('46', '19', 'UT', 'Utah', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('47', '19', 'VT', 'Vermont', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('48', '19', 'VA', 'Virginia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('49', '19', 'WA', 'Washington', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('50', '19', 'WV', 'West Virginia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('51', '19', 'WI', 'Wisconsin', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values ('52', '19', 'WY', 'Wyoming', 1);

insert into container_type(containertypename,containertypeid) values ('Application','CT1');

insert into component(componentId,compName) values ('storage.comp','Storage Component');
insert into component(componentId,compName) values ('com.oxymedical.component.billingTracking','Billing Tracking Component');
insert into component(componentId,compName) values ('maint.comp','Maintenance Component');
insert into component(componentId,compName) values ('com.oxymedical.component.importcomponent','Import Component');
insert into component(componentId,compName) values ('importDBComponent','Database Component');
insert into component(componentId,compName) values ('billingDBComponent','Database Component');
insert into component(componentId,compName) values ('id.rendering','Rendering Component');
insert into component(componentId,compName) values ('dbCompnent','Database Component');
insert into component(componentId,compName) values ('userAdminDBComponent','Database Component');
insert into component(componentId,compName) values ('com.oxymedical.component.useradmin','User Admin Component');


 delete from user_; 
 delete from contact_; 
 delete from role_; 
 delete from rights_; 
 delete from role_rights; 
 delete from group_; 
 delete from organization_; 	
 delete from users_groups;
 delete from users_orgs;
 delete from users_roles;
 delete from groups_orgs;
 delete from groups_roles;
 
 create table loginDetails(
 id int primary key auto_increment,
 userId varchar(25),
 loginDate varchar(25),
 loginTime varchar(25),
 ipAddress varchar(25),
 logoutTime varchar(25),
 logout char(1)
);

create table userfields(
        fieldId varchar(100) not null primary key,
        fieldName varchar(100) not null
	
);

create table admincity ( id  INT PRIMARY KEY  not null AUTO_INCREMENT , cityname  VarChar( 100)    , stateid  VarChar( 50)    , 
 INDEX ( id) ) ENGINE =INNODB ;

create table userfieldsforusertype(userTypeId varchar(100) not null ,userFieldId varchar(100) not null,primary key (userTypeId,userFieldId));
create table preffix(
        
       
        prefixId varchar(100) not null primary key,
        prefixName varchar(100) 

);
create table suffix(
        
       
        suffixId varchar(100) not null primary key,
        suffixName varchar(100) 

);


create table cateogry
(
        cateogryId varchar(100) not null primary key,
        cateogryName varchar(100) 

);



create table active
(
        id  varchar(100) not null primary key,
        value varchar(100) 

);


CREATE TABLE usersignature (
  id int(11) NOT NULL auto_increment,
  userId varchar(100) NULL,
  imagetype varchar(4),
  imagedata longtext,
  imagetagno int(8),
  imageunid varchar(32),
  imagewidth int(11),
  imageheight int(11),
  PRIMARY KEY  (id),
  KEY id (id),
  KEY id_index1 (userId)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



INSERT INTO cateogry ( cateogryId,cateogryName)VALUES('0','UWMF employee');
INSERT INTO cateogry ( cateogryId,cateogryName)VALUES('1','UWemployee');
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame28', 'Prefix' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame29', 'First name' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame30', 'Middle name' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame31', 'Last name' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame32', 'Suffix' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame33', 'Employee number' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame36', 'Category' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame37', 'Active and inactive selection' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame38', 'Address(1)' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame39', 'Address(2)' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame42', 'State/Province' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame40', 'City' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame41', 'ZipCode/PostalCode' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame43', 'Telephone number' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame44', 'Universal provider identification number' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame45', 'National provided identification number' );
INSERT INTO userfields (fieldId,fieldName) VALUES ('frame48', 'E-mail address' );
INSERT INTO preffix (prefixId,prefixName)VALUES( '0','Mr.');
INSERT INTO preffix (prefixId,prefixName)VALUES( '1','Miss.');
INSERT INTO preffix (prefixId,prefixName)VALUES( '2','Mrs.');
INSERT INTO preffix (prefixId,prefixName)VALUES( '3','Ms.');
INSERT INTO preffix (prefixId,prefixName)VALUES( '4','Dr.');
INSERT INTO preffix (prefixId,prefixName)VALUES( '5','Rev.');
INSERT INTO suffix (suffixId,suffixName ) VALUES('0','Jr');
INSERT INTO suffix (suffixId,suffixName ) VALUES('1','II');
INSERT INTO suffix (suffixId,suffixName ) VALUES('2','III');
INSERT INTO suffix (suffixId,suffixName) VALUES('3','IV');
INSERT INTO suffix (suffixId,suffixName) VALUES('4','Sr');
INSERT INTO active (id,value ) VALUES('0', 'inactive');
INSERT INTO active (id,value ) VALUES('1', 'active');




INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame28');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame29');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame30' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame31');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame32' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame33');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame36' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame37' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame38' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame39' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame42' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame40');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame41');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame43' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('SleepTechnician','frame48' );



INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame28');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame29');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame30' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame31');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame32' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame33');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame36' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame37' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame38' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame39' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame42' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame40');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame41');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame43' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame44' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame45' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Administrative','frame48' );



INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame28');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame29');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame30' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame31');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame32' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame33');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame36' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame37' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame38' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame39' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame42' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame40');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame41');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame43' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame44' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame45' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('DayTechnician','frame48' );

INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame28');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame29');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame30' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame31');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame32' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame33');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame36' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame37' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame38' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame39' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame42' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame40');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame41');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame43' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame44' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame45' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('Physician','frame48' );


INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame28');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame29');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame30' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame31');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame32' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame33');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame36' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame37' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame38' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame39' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame42' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame40');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame41');
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame43' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame44' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame45' );
INSERT INTO userfieldsforusertype (userTypeId,userFieldId) VALUES ('ResearchAssistant','frame48' );



Insert into usersignature (userid, imagetype, imagetagno, imageunid, imagedata) values ('phyuserone', 'png', 80449406, '44f9d7550546417ea644aaf49a9bbfcc', '89504E470D0A1A0A0000000D49484452000001EC0000006608020000008614353D000000017352474200AECE1CE90000000970485973000017120000171201679FD2520000000774494D4507D9071C0B0413BD8DB84B0000002474455874436F6D6D656E74004C45414420546563686E6F6C6F6769657320496E632E2056312E30317ED0830D000020004944415478DAED9D49731B5796851340629E099032294BAE0ED7A222BA7BD5FDFFFF412DAB7A7095DB254B9C00624A8C89217BF1154F5C3F104910A24848CEB77050349878F9863B9E7B6E2A8A222F19C9F886C66834AAD56A8BC5229FCF7B9EB7D96CD2E974B22CC9F856472A11E2C9F826471886ABD5AA542A254B918C44882723195FCD0882C0F3BC542A55A9543CCF5B2E97994CC6F3BCC4184FC6B73A92939D8C6F6A54ABD56AB58AE01E0C06D96C369D4EAFD7EB6465929158E2C948C65730A6D3692693211AEE795E1886B95C2E5996642496783292F1758C52A924097E777797CBE5FAFD7E62A92423B1C493918CAFE74CA7529EE7D56AB5E170180441B55A4DD6241989259E8C647C3512FCECECCCF3BCD168542A95AAD5EA7C3E4F96251989107FD1118621BE30FF5CAFD7ABD52A8AA2300C752157AB95E77993C9E4D826AF1CDA66B399CD669EE745511445D17ABDDE6C369EE7CD66337EE033C91164C8235C2C16AC12FF64C797CBA55DDED56AB55EAF7518B4E6D8E03A15E572399D4EFBBE9F2C6F321221FEA203D1D66AB5D6EBF56030C86432BEEFA752A95C2E57281496CBE5743AE5DE96CBE5639B7C269359AFD7BD5E2F9D4E178BC57EBF9F4AA596CBE56AB54AA7D3BD5EAF582CF2435284E258D08BC542AAAE582CF2FB42A120991E86611445ABD5CAF7FD4C26C3EA054190C964A228521465B158804EE976BBCBE5D2F7FD445926E35BBE3BC71613A7DC6E369BA552292EF07ABD1E8D4660C52CF0A0D7EB9D9C9C1CDB822E97CB6C36EB79DE7038ACD7EB0897E572994AA56412F2BFBC043BF15B4B7C369B519BC3B28CC7E36C361B4511C700031C898CB55E2C16D7EB35F2DABBC7868FC763CFF3F2F97C2693994EA7F840FA4C32929158E25F7CD46A352E64A15088A2683C1EA7D3E966B359A954001EC8AA92B17654239BCD2E97CB300CEBF53AAAE8E6E6269BCDFABE7F7B7B8B5159AFD7C3304C24B863894B67A30B2B950A0A9BD0D9783C4640E3E2607C607D2F168BD168F4C73FFE91D3522814168B4514456767677FFFFBDF33990C423F19C9482CF19718EBF5BADFEF371A0DDFF765D58E4623EE33FFEDF7FBCD6653A6D951CD7F369B49BB5C5D5DBD79F3866293743A9D4AA5A228BABEBE3E3F3FE703D3E934A90BB7C6F866B359AD56F97C1E87ECEAEAEAFCFC5CA1129C18DFF709A3713CF8E46834C2B9C9E572D96C76329910D7E278DB272423198910FFE23799FB46B464B1580441D06EB7F1B2178B0588312EF0783CA6BAFAA8C6643221F0CDBB58F1419233954A91F63CC298FE6B0DE9331C14B92983C1A0D1682C974B8269AC1E1192C56291CD66D7EB359A3E954AB55AADBBBB3BD22758E85114CDE773056492918C249CF2C507A0944EA7737676964AA5CECFCF7FFCF1C7542A359BCD72B99C829B83C1C0F3BC2394E0511495CBE5F57A3D1E8FA590DAED367A08637C32996C369B4482DB218F64369BCDE7F35C2EF7D34F3F799ED768343CCFBBBEBECE6432ABD58AF59CCFE75114E19665B359F852B2D9ECDDDD5DB95C8EA2881C69ABD59A4EA724C393154E466289BFE09C52A97C3EEFFBFE7C3E5FAFD7D56A35088242A1707979D96C36A118C5BC3AC2700A132375592C16E7F339825B1F28140AB3D9CCA14B4D06925726F366B369341A48E752A9B45C2E3B9D4EBD5EDF6C36A3D108C98EF1AEF815C706F421B1141E9BE41E929158E25F30EC40AA4A322E8A228CEBF57A3D994CD6EB3549AA542A359FCF8983E7F379DDF69781E81103994EA7FA0D5837FB99300CC9B8160A8520084E4E4E983313C67EC4EB47ACBF7FFF1EA183D78FA9D8EBF5ECA346A391779FD6C3F3F81A078B0034300C43DE9497E2053DCF1B0E870449C09282E42191A0F5A9542AC86552DF82B290B464D130C011F188FE288A12684A321221FE053DE84AA50284A3DBEDA652A9743A4DA546269329954AD96C763E9F4B622E160BFE2FB26F341ABD8010071D984AA51019B3D9ACDBEDE672B9542A351C0E514588E34C2653281452A9D47FFCC77F6C369B42A140AA0DDF9F47351A8D5C2E572A9586C321F1225019C8771093E0C73B9D4EAD56EBF57ABEEF87618860FABA461004C045082BF9BE4FD6D1F33C64347674AFD7FBD39FFE944AA5FEFDDFFF1D239A90C8743A459921BBC5280BA2BC582C523685199ECBE5EEEEEEDAED76B1581C0C06E9747A3A9DF261E230C9554F46124E79E6717373F3E6CD9BF97C5E2C16EBF5BAD063CBE5F2E4E4049BD4BB07FCA6D3E9D96C26A859A9542261F5026165442A39D56C360BF071B3D94451F4F6EDDBDBDBDB72B9BC582C4AA512FA86BF526A8EDF944AA5D56AD5683440197A9E478CC8F33C02E8B6341C80732E97039BE1DDA37130661182C73F3A9DCEE9E9A9FEF9F1E3C7EFBFFFFEF6F6F6ECEC4C3079CFF3CECECE7ABD9E4A2E017AA3DAA328AAD56A6118E203AD56ABD96C56A954144313007C3C1E6F369B5AADF6E9D3A7376FDEB02FCD66B3D7EBDDDDDD856128385032929108F1E71CC3E1B0502810180125C6EF31B290E94285FBBEFFF3CF3FBF7BF74E214EA782E6CB0D8B8A41F778F70865E99B42A180203E3F3FBFBABAE27508D122F17954AD564BA55241109C9E9EDEDCDC28745BA95440CBADD7EBD3D353E2BFC3E1908243D4C6D718160044244828B218531AB5B45C2E8194F0D6A552090B5AFBCEC2920559AFD7CBE572B3D92890A278543A9D46BD21EBABD56AB7DB2599EC990AAC64242311E2CFE96B23163399CC66B349A552D56A553697E779CD66B3DFEF2B78321E8F25AFA15251E1F5171D93C9046319FC32323D9FCF636513F6C1A626E241F03A9BCD12E425B0BBD96C544CA8512E977938E2C99AA2540CB5DBED2008168B451886FC66301858F3F6F8070041CFF310A9B83568232221E02CC576522A95583704B72A3331CCA541790E5A53B9D05EAF97CD663954A49741A926159BC94884F8971A777777A0506AB59A4060568A1176408AAD562B2A386C080521F802531576023F8060EE9B376F6E6E6E3CCF3B3D3D554C209FCF0360B7B194D96C466496A49C7C8E46A381D0C7DEC47EC7329591CE9A743A9DAF11EC4CB2915401C234954A01EE16470AE0D1CD66031E492FAE0C360F9104FFF0E1C3FBF7EF155441A3CBD0E6481093113B4D5254958C6F78BC5A62338AA246A351A95448DCA14B9ACD26E6AAF278EBF53A0CC362B198C9646AB55AB95C0EC350508D1790E0047654B093CBE53E7EFCC82F91E0B55AADD3E9AC562B82F58BC58257D0B4A91B5CAD56AD56EBC3870FF428B8BBBB8BA2E8E3C78FA8A5F97C7E7E7E8E23422B83F57A5DA954B0BBC7E331684571FB7D15E27B381C5222FFE9D32702622C23E2151E85D96C964EA7C19FCC66B3C562815026E7B15EAFAFAFAF178B45BFDF2783E279DEFBF7EF91F5FAAE6C36BBD96CC230ECF7FB1C894AA532994C90E0605492AB9E8CC4127F7E21EEDD474831D0B8E1BEEFAF562B4542099E5E5E5E52B2E1FBBEF085363FF64587BE08375F9AE3D3A74F6FDFBE55684896A60C4C0504E066FAF8F163AD56E36318892A44F4EEC94332990CB105F922E809A01A5F51AF485E138A418224844DF0B49402E1184C261372BFAAC2E5DD6D201BD35B36B50AA9145AE15428650297A1EFFB979797171717C9554F4662893FF3202DA98269CFF37EFDF557AA39B881A4BFB085B99F049A15557819096EBFC8067396CBE5DBB76FE198AD56AB847A844EC1640632089686EA1ECF40E57823C576A1F11B8D46E215E0CF7DDF1F8D46994C461AC2E972709C3CAB28AA6AB54A2C25954A4D26935AAD6621F03220CAE5B20E038502D96CD64945123C515484A580E0578BB05EAF55D743A4CEF3BC472538AA91BD3BB88304891C4EACF8B614373BC0BEF1EEA9E70F63EF5A2E9756E5EBBD9C29C96B8C7F35742AE6C5B3E3354929D9DE00F3F97CB55AE196110DB3CBB26B74BB5D7E50E9007FCBD9984C26FC5EBF79AA51E2DDF3DA6FFF39CBABDFC74C55E526CBE512E9F1150B715DB6300CAFAFAF3DCFFBFEFBEF87C3218E76144544128AC5E2EDEDED11964DC3B284BD8C793E180C72B91C812034CDD5D5D50F3FFC60AB841E7085EED5D270389CCFE7E974FACF7FFE3387804C1D91742E12321135400882937A84721C46F57EBFCF3ED66AB56AB53A1A8D404F82D8190C06B3D9ECB01811A94E6BC81FEC53223E72B91CE6C27ABD3E604ADC46B606022FEFBE2EE9A9EFC569070B9F4EA7F3F93C62EEA9E793C9281EC80C99D26030D86C369C377B191F5406BEEFA35089E93D6F9638080290FEE572192817E5D9BEEFAB6C9B5F7AA6FBC783F3048CC4015BAD561CB352A9B4D96CC894E4F3F9288AF00B0F3B2441102800E8DDB3F5C9F9DBE71A028ED86C36982910B51EB0BF47114E01DAC1CBF0565114F9BE0FB48E4004E52147D8BEC7BB47ADA9E6DB167F7BF7E048A5E66284C22EE2271E05971310BA5AAD4632E0EEEE0E325BD1251E27848E57B30B8286BBBCBC94EAE2761D403FA0F8C960302897CB30A81CD64E733C1EE30A70A3AAD5EAE7D0398085E7F52DFDFD938E1602B7582C7E0EC55BBFDFAFD7EB4270714830ED91CBBC26A5D1BBF40D61B1C16090CD660FDBA9FD672B1CAAECD9ABAB2B39528F365161D1D8387D18880149268A0AD99DCF19965AC3092D28D91E737E38A8B7B7B7F57AFD5916F3352D7159529D4E279D4E23BC008FA3E8369B4DB7DB1D0C0647D82651816CAE2B27461A5EF865DE22460F61428A349537BDBABA82076AB3D9103787F5906F393939A13A148A47EFA5E8079E6AB9C03720DD86121A0C06BEEF3379C09ACBE5F280A32C094EBD2BD8F0037206787EF04A12B847C61DE06E23449011FC7CD815CD66B3C5629175031A7F80274AB9533A9D1E0C061C2DCAA173B95CB55AA5E4F8E4E4E4F2F232DE63E06F1B8D0681C47C3E0F54EC196537FBA839AC562BDDF78B8B0B7CCD4EA713BF08F46E2467A675A3400FC102C4E0ECEC4C7195270DECA72008C0898D4623362808022A45E819309FCF9946FC91E39CE4F37924C367F2DDBFDAE51F8D467A553CEECD66F3E38F3FE2B2799E57AD568112371A8D2344D751C6C976E6F379AE2B421CAF8D57005A178FA201F30E4A8793717E7E8E1F0DE865B3D9C0CAAB14C2EDED2D963E42E70871D0E5721913B2D1680832CF79E52DD05BBEEFAB3AF7A9977F3A9D7236E4061DA0CC6895C70F6AA1779852277B8118A288176286A786A1BCFB26A2D3E9B45EAF6FA707F619676767F87FE42448AB7CFFFDF7142E705CA7D3E9C5C545FCE1C42D40EE506F1C86E13356D8359B4DE828500C1F3F7E44A9A353F9BAC964727A7A1AAF1173B95CA552B9BDBD154F51369BD5C5FCF8F123BF245A75807245C7148BC5D3D353E0188542A152A954ABD59393131486563BDEEDABD7EBE84EE9C8CF25BB8F5E758461381C0EF95992BA5028A8000F7C986067C7337ABD9EFDE7DDDDDDF5F575B95C2662A05D4127112C7A7060A7F333FCD7411040B71204819C15CFF3DAEDB632AB51140155A404263ABE110481F58E95AEE46547A31116E26AB5FA9CCD15819A509B4F1DABD54A112D50315114E94CEE3FD80EA817A8C805387BC000446F57F2B025228F42EE41A72897CBD1A1540EC4783C5682E1D1D526FDCE0FCF3538C093C944AFC9B42F2F2FED3F47A351FCFA6F361B105C1C2A3179707758071EE55CDEFD8FB488F0BEFBEE3BEC868F1F3F7260745B1FDD2C6E37C79548EC67CA37EF18E4E0EDED2DEF532E97BFFBEE3B39955C8CE73D31CF3820E45A2E9712A38A65FBBE5F2A95543DF8E8A6224AB6FF17BF94CDA2300E92EB1FFFF8873D164735307614B5CFE7F3E49D9002BC9A5E1929F3A4717D7DCDCA2095BC7BA2C403A66A93199FF31C76219D4EA368B1420E780E72416B72D8E6225300C58ACA916A29B92074B97BF4A5C6E331B24FA2674FA1BFCF90BAB2DC76A8995AAD1604011BAD09C40FABAE2A958AA58D83F5083BE9B0C1C3C9B82A16DA6EB73B9D8EAD6C78F0223B8784E7E83A7FE61ABE9A10E755A7D3A96A3164899F9D9DE9DD30DC9EF1D03CAF10D7CF7FFBDBDF30361D4FCD9A12F18798DD7DF004C861077200DDA34E032AF0D8865D0AEDAC35BD591342FF077F85775FF075F065B0E916C13008F51C208CF428E0A130383E75D033440288737598252EDB59CF215BAEB81CF72B46B429AB21D12F93F3B984B845F51084D46F6AB51ADFBE8F7BA4C492C5DB80CD55350607EF0051CE9F088B59AD56654E912BB6CE71BC1C977FC0D1E5E7CF59C3578B89931CC7162095A4EBB45C2E1DCCD311C6C4054926B5F8EEDD3B128F9062F38204B59531DF0531B4B9507B61D41F52DB0CF8972C9C4075A2797ADDA1E44CA7D3B1091C365A9BC8A5D29A140A85470394E3F118330D48256FAD25553BCDFDB12816F55C28149452CBE5721072C5249A40947AF7806BBE9737652B011429D94580DB26EBF85E11E9707EF800A1F052A90420841F6C56DC7A668F52CC43C9CBA1828A40C2821D21F800ED8177DF544B5D355485A78C3ACB5EAD569903124D726D1FCA7B8BF8860DC922029435F10C2E5BC82E282DF900CB8B06E2975114918212D6ABD56AF19C42A1003A451824D0873653CA22930EB5B74FDF9ECBE5E0A046C704412052651A51592F19589167E0F9A2D167553930FCF7195009AF68ACC928E3E0B24FCAE1E4F3797994C769892350405958212239051EE0B018AB8C3B81A5B4D9FAA73EF08A8B2041600D16EEB34EB6CA710F5807250CAEAEAE3809EA2C717272224D40B829E639B3D9CC9A9093C9A4D3E980C54688FCE10F7F906ED83372626D2E9C6B9E66ED4A7647F119A01DD88CFFF77FFF674D6F2B5EB57ABC1A26DBAFBFFEEA7C698CC52763D392D1E7723911A86532199C212224449C5964D0845A0D6692CFE765235B4F051A0C6B0BEF1A9A3F9952428EB55ACDF77D1AA30BDB634DE9743A4DCE59EE2C53B5D6B46425DE862AC22A954AB3D99448E1F7329C7FFDF557D52B3812495B86CEEEF7FBEC382B99CFE7ADE4650775507BBD1E0ED072B91C8FC7611812375EAD5682246A86BCE6D71A4EE976BBECC472B9E4A4EAACD46A3549A8E170789C127C3A9D2A73454C9C5D41FF13A865E60787E1BE0A216E55AC528BF29F1CBBFBE0A3C23593BBEAFDB63E85D8C5A3CF5FAD56BD5E0F9B572B4C9F0AF943F57ADDF77D0E678CF2266C4DC246C69A2A626C0A51AAEBF2F25227811F904A4C66B55A2140A5F090B612A654C0F16190DD8FBAFF083B4E4BAD56B32B56AFD7BBDDAE742A980DF91F6C59BD5EE715AC1FCCDBF9BEBF5C2E87C3A124970DC4C72863A41BBDADB72DD07ABDCEBB33557406BE1A4B341C0EED6D22C50AB527F7CE1A52D6C3538716E71C6E361B9BFF008AA3D3E240126C1441F1199A773F78CDED939D136B2D77F8E0BED6C4260BB45EAFEBF5BA6ACAAD63ABCB769C72DC66F66D4844E52D83C1407CE2DFB0256EE3FE93C924088252A9A42B445B0F4C9883C3A992AAC3E150F713F94B34E9FCFC3CFE9058A375BD5E8F46A3FFFAAFFF9278E23A094E133F1925B208EFB4DBED542AC53484AA126ECFC9E13BBE08825BB80B18D36C904D92C2BB6F0A2899FB68EC55814AED8584A3D4A1DD914AA5C2D1CA6432DB6553421C107DB2D81B1AEFC54B223C36F490E28DE9749AE24CFB7532576541D7EB7507821586210E996334088EAC8DF03C0F8223E945D616A522A91D04819DA1CDD3A0ED702E8BC5A2E81C54E5A7FD85AD4FA0CCE81E0F83DD0DFC3F97CB598402ABFAB55AE2F6645B30A60E9C5CE057B734E3CD4F6E9455B3DBB2E01B0EA7C8C89A4EA7AC09913EC5ACB95424630F4359E846594EF962B1280B9AEB1AEFF128F605044D074F84369223A7A7A732B41F1C9893D3E9946F74D0F1124FA42B1CBD853929C13D9D4EF576D68229168BDC7CDFF7ADF12859669725FE7CB205954A05A981D7C236E1DF8C46234BD5A9632681A89C81E438EFF8E1C3071B59225214AF4757AB952D4045AE115E50B99F774F2F6C9516FF4497A3BAA4F97ABDDEC5C5852D95103585A216FABFED765B7253B3C583D4F9D1D2CDE773E909CA3E79A060C416B5E25C733DDCB9B63AAE994C4629EB3DB1374727C49937C14D8A83AD251EDD63C8D8BCE3B4C4E7F339FB47B2B156AB615670F7D85D07F3FB4D0A710C31F6A8DFEF5B90802D5469B7DB42FE3E555F8E4623924288217B54AAD56AABD572FCFA5DE7CD0263ACDC943200D71F2FC111223C4A7F0845848D390862F8F7BFFF5D76B7C49382BC9C22A4CCDBB76F6D665BDBADBC0B7F3B994C1070F150745506CA2542C7F057F29C780BECDF6C36CB0F127FD6EA948844B528422D5CF6E3388A7B4B9FB5E2E144429ACDA68D74494CF35FFB703EC3B1FFE5975FB48FC56251F693A22BD3E994AD91F7C62BC0E761B1E730B4C069CC3FC7E331DFFBE9D3271B709304B74A8B4250A6C763C993BD7FFF5E0A5EAB2A0D7D9865732C425CEF4FBA96C595134ACD9B34F0114A70E9ED7EBFAFDB9BCBE52CF088B7880E85BA7F2D429C73CF86CA2AB711C99393138E3E35174FCD1E4BE672F4718A4BA59282212C2F8DD9F6D932FA762253188249ECE9DB0AA3B29DC9E46799D24E2254594D7EE6B623CB64F0EA6FDFBF7F4F45ABC2384EFCE4D1740B51052BBF608689A2083B1A096823C860C995A5545FA46AB56A9F83EB4339B195B9BB06F470D65E661F39188A2CC966D78A09DB674D5D8C277AF545F74509DBFAC62AB35AADA6B00612593ECD743A7592DEA062B4EC272727386AA7A7A77C0BDD043543896F27BAD06AB51CD41932DDF77D25721EB53F8E3A9C42EC099F426132943C8BA23D3BCE888A6416BA5DBB42B12FC7020174981EFA2A8438AFA9CBAC5C100757310A5B4A73F0220B7E40C001E9039C6B9F00B176C186536410697A8F96F3716E85EC74EA03A02591224716E824F38330F29A86E6600B853425E853EC9AEF930A5BAFD7CC4A118C542A65534D4E2C0819875FA52720012D56641BDAAC958F398AB0FDB050B635AE5D0744B3DD47FD2F2DE0B6DED2E972C437B2587FE204DC59196755ED21E11858465F1DE656AB455261B95C325B5BE88B76B73B48A4C86689ECEB479F5782F4CA425C2941DFF7D9009152FEED6F7FDBBE7BC7698947F7E570B232282E57DAF63021CBE1403CF1704C246BCB208C8E53C371CF4BA51212A45C2E0B4DA5AC1AD2649FFDB5286CCB55F2A0287974D71443C8E572CC13F94288C04E8F156617646D45BF2D65E2075D5AA75C9328BC6EB822E972CE64C148D492B0B5059F07186B36A86523217695584C264C4B52BD207753CAC6C9343AFA585A812F5501571004DA5C2B6489801F9CD0B3421F5884D5A6387CB28E11CD40DDA55F1D93421A82A8BD752C2026B2401A7BEAE6F339DFC28725F1D5FF9D2921FDCBE5B2AD17F91CBCD6B10871892701579D7CE0F5F5358AEE388538D273341AB1C1BC45A552917BC839B6CEE0614BD4EBF564079D9F9F8B1110D94DA6EB089909ACF986B814E95274CF58C2EDDA27E1B12DC4919E954AA5D7EBEDA9C6AC44BEBABA923AB44969F6CB22DF855890BE74605450FDD8EC68ABD5021807FD34E2C6E6FD940A53F456A9143DED336F386535B27925E9A2FB54BCE54228168B6C87B485CA805169F0C112F6540D2715F9FA468942B019E242B17CFAAAC06426079C5BFE240C437E10E4D12A6380CB8E892D63886501F64E487DB3D988C122DA5DFF91CBE574BBEDE4C98B72429ACDA650F9963BC87A007605086A7DE6FD7D654B5C5B6B01F9D7D7D70E5057C6D1510DCEA8E2F84E6296D31004C161B83A9BF8A5DADE0290250D1DA0EBB1C9F17ABD2E252D7E47E7C82AB6F854214E119D8D36C4444290BF36B38A2D2F835A442296C988ECBA48CA904780DF6D50DE3275A87A4842EDE6E686E959DD00C1A1325D0031E57D2B9E4E7AEDE0F52702CBA2315B964B1E92E3D42BAE8D20B3E2AFDD6E5BFA6CE1A389ED28B169B180F25A28529722A719C261596EAB27E4065131E450DF4CA7539BBABCBEBE66559D6C8DF5E1E6F3F9AEFA0F4BC3C2790BC3D046C3159AB7B19776BB6D4922C530CC15E6AF3E1324FE9A429C8089837EAFD56A02EE088CF59901A32F37E4B1DA422C0E681886F6341F40F06473320AC6710AC50221DDF6F9B9912F24C42DB2D842A757AB1502624F4FE54121CE0D74525EF181F5E8BEBECC6178E7B18E08B3AB4AB521AF408F1836A2542A591F19C5A00DC2DFB7F61A3AACDFEF371A0D35819280A856AB4A7965321985650F80A07DFAF4499620E7C7868C554F04AD3F3C598A8A481792390CC350CBFE2FFFF22F8A0C6045DA74B19D27B6BCD659AB747171C1B93D4C782D97CB5EAF27F605874797F9385E85B411D793DB6A7B6548EE2B04FA60FD07509F72B9EC80D6A1215424C72282B8A49AA796B15C2E2BD46633BA5F9F258EAB82A5A6F236C5222CF5D5111A9B480D6B4C699F1CD7EF304B6A3A9DF67A3DB56C970F2B2A5A9DD1E394E06A55654BE92A958AEFFB3737376CAB8A151FD5D30F0AF14EA763A57FFC43E0CAB066972D25656E947B7093ED0C9147DB9DCCAAD52A1E24C2178C8724B20EB962E2B3D98C494A9E361A0D4913A7E38C15FD871D21CBEA65C3D928097E130481A321245BB5500F76C9B07F85596A85B20569006DB47C96D116D673FF21BC804487D0EBAD56CB2A12C79BBFBBBB53CC4A9432F0C0E00EB2E3BBEA3F403171EAF8C0FFFEEFFFEAF3E84B5BB24485ED62B180F7DFBAD1F97CBE542A013C7D160BF53585B8CD18103150BB1F2DE5A32C6BAF3860E15113675BA64451B204C7C1030A24D902088846A311DDE34088DC1DE1E2C89596E9674381D13D2270CF40D9B6104716B0C27BAE80C223F3F9FCF6F6369FCF5B66120BF652D552BFDF475B23F27EF8E1076E75ABD53A3D3DA52D2AB3524F4B851AAEAFAF61B8B692515DBF2956D21B494A32AB743ADDED76998F28479E343A9D4EA3D1A8542A36E6A3530A74DACE2A0882C96462812BE88FE974AA62D44AA5226580A3EC681719B0D638D5B2BC7BF78E17B480CB035EEDEEEE4E1127EB5858A4E0A74F9F74F586C3A14DF3DAC088B587F42E0FD67FD854E4743AB5F619DF2E842800049E2980A6E501B600479BEBFE2A85B8CAC0EC7E28F8309BCDB0059E97FAF2792D71594FAD568B86298E1117DD97083FF5F98BC562381C22F8F816F43CA14C9903B7B7B714FB1EDBFAC8E1E5C8DA244FB3D954FD0B6FFAE865DE16E2ABD54A8545D16FD9C977B9E15AA5C16000DD952DF391C1AE8D534D36824FD290EB2AFC86838EA0ADA84D6C1069E5079583491A5227C115909EB30B7218FAC8064F503C4CCF664D7572ACA94131910343B415ADE572590171B6CFD1C43C53247FD6446DB7DBB8358BC5629F5CC88361B162B1484DA655488E576AE76FA5B3768AE95D5C5CF0492269E2C68A1EAAFFE0EB74DEF813EEBE6C7CDAB668CB68035D2C16C5092366DDE9747A581B932312E283C1E0FBEFBF77E265740AD666903A385A4B5CA6B77277A55249415EEEADF8E52F698B0000200049444154CF3EE7F972E51C0E0D15B51E67440501E778E2487379EE7B7A2ADB42DC02F0F764BB0CC31009228E5C8B1CE051B61A130DC1AD2328E448C0D56A252BDE01290B9E18FDB640942F15B5B70E4F369B9585888894EE8F2FA2895931C8F69861B3D994482297C099E1BFF2EAB480CC591032E9301101324301E1ED2EC00DBB1D82D7D64BF51E1026621A529CB55A8D4BC16AF35FE1B5A5B9D541CC99981399D44E3D58FF41207B3C1E6BFE56B58BEBCDE198748A9BF8BC9509FB40635F59883B992B551BCF66333920AAA47AF57671BB2C4AEB0D70509E71FE4110083845104D4764FB26C4E0798F70D0F1DD46F339E812C1BAC631E7C40604686CFF68988BA739ED322C5711CFB44FD6B75BD4B3D85AACFFAECE448EB41213886211568EAB4CC9D680A8DD25A748AEB7C4CA9E0187F57AAD0554E880BECF483AA91F6154ACCEB345F378F70E7B9FDAD0A069F8BF7C23112D95D5A837265F4D8F5CEBA9102CDEB697B7C371D4C147BFAD8DE2E136C6187FE984F29ACD66588D249FA5C2B502DB195A071D98C96428B8E59CD8BA27743C00279BCAE6DD3918B6259E03A68AF7A46D8F274B7D0C87FB4B0871A62B4682E81E1E141990ACB2BA472886B42568F2CD66A318E5B3CC3F862FDB02E0F88A542AB50BCF7B6C2308025B2957AD562DA6CD6A1D5638FE9CE472B972B9ACEB14036C50B05235D9D16F29460783C1C9C989905E7271EC67F4FC72B95C2814140DC046B6A57D5C632B145AADD6A74F9F1409E1C3D6E8AE542A2A6D0365DB6AB574C35BAD16D3B6EA2D26A2321E8F11850FD661DA3CB0944464D8CA9023AA58B1B81402567A4EA15060DAEC91F23DF25AC6E3B1E41AF68DE5145344B85C2E77BBDD4763446C01BA5CA75DCAC0E26D8AC5628C273A9FCF95E124302DE789866D485B878A808E1FD2131C3F4B4366110DAA44B5C687BE515FC7E29C9C9C288E44EEF451371A491D862161ABC562A10A55FEEFCBC94D416595F2928A6EB7DB07F7A07A31211EFD96F8E219E7FF205F76B55A5561B76C3D5940BBF0BCC7B674BAD892050AB088327F369BD92BED9C93C8D01BA911C4A322401C46B2C2A41B14DE115E5016D97038E463368C609372368445FC811081A8F0152CB6B1E6ED32198B10D5575858CBCF3FFF6C8FDF3E6171F553EE76BB529C3040349B4D4973C434D24ACD9DF5B36D5CCE2B341A0D0419FB4851ABC4F7A74F9FF43185CB893FC8CBD131967097287CB48984F3E2761A12B2FBC412211BD8EE21258E0159B8D29D994C864497F59FB877CD669317C9E5723FFCF083722AC3E1907ECD36AC8427847C28140A2878A8B56C546AD7CCF946DD85D1682473A15AADF2B497909B76956F6F6F7576C1FA585CCE118605585FA4245B4EFDC5F3CEFF41BE6C8C0E7A4A61BE45BF253BB6765374C4C3E966622B6B6CD0E0C173C2BB236A699DF3A8C6929C5DAFD7D28B6AA747132F92634AADDBB20B5B586F490EF40102290A9B2AA8CD0CC5CD84A9EBA4042C2B96E0866A5C671D7C5572C70BA9F97CAEF7BDBBBB03B60B2E458AD3A932572D2546896D67637BFDDCDCDC58052CE23D8703BD5AAD3A6593708308676DABE1149B7A34D02FA5CB2ACD663339A6C4A90125673299F81201FB452CB2A5F3B6CA4FD173AE95A56C445EAB2F9D3502D449159FD8063959347BEC65FB3FA95942100477777736C2C386229AFAFDFE4B0871E007F6EE492BDAE4F5BB77EF3E3FC6FF2506CBAD543237F9B9E6BF8B2FDB0124F9BEEF9CFB6D3CEF510DDB3AD62994E78DAC75ACC29FED73E2D0C564B3D9F800A256A9DFEF8B1DB7DFEFDB7E63BA5702FF80CA57AB721B0CA59C588A969B8360158ACE11C1D6FD874750555AE57219E79D9501A56A01F55114FDCFFFFC8F639AC5E7C6ADD7E234A7AD542AFCA6D96C226B24F119B8F6B64E9DE5E565D932355568B55AD16FCBA7A95FC7431271BCA38F516F98E7B6FA3F3E18C8F1EE76BB8E43E674ABD8C707C5F67224AFF5AB10E27777770A8B3B5FA49643C22641D5A76CA785810A9461CFADE779FFF66FFF469B270B60771A50EC93DE570695994F26932F2EC41D18CD72B9FCCB5FFE62B1160E7CF8D8C67038B4C925D830ACE9F4F9F3DFC5979D4EA7AD8BAD16240FE2798F6DC8BEB8BCBC6CB55AB6E407C424C242E56ABBCE0947E5FDFBF7B261178B45BC716A851A9FE442BE79F306AC98FC7A27D1271B5C3DC9E803F920D9932CBB72B98C88D4C796CBE5E5E5A5E37CE07723346D18C1428C53A9949E7F7373836B1F7FC9A55A6E6F6F7982A48FBC78AAD2253B2E2F2FD155D68625C6ADA08A55BD1697A2CFAB89152901850BD04CB2F421C3B2011911D2C61B1FEBF55AD30B82A05C2E5B352C4A296B04EC92E04C46F81FFA79AAC528FF57E2DB6AF1D3D3532927110658C2DB4C26A31C157AF7FAFADA7264361A8D5C2E27AFC8AEB6C52FC5A7AF793B871A1E6DCA1FBE90E8043FAFE3284F1669C5961C0CC57B99349D2D257012479F33FF5D7CD93AF44EE4C1FEA123CD8F6D389974001E5C456EF2762072D739911B1EDFFD52F781FF22319DF27A850808CEA8F242B7B7D96CDA3EB684B9F95E842FC740F119FB707D75BD5E570848249D4EEF98300C351999CC801F0448D887CA58E1111B8553772105E21155DB0BB86D0C122D945C565524295635ED743AB5974A25602A8E1B311E8FC90FCBDCE1698D46E35188A118C4748494433E3939198FC7A3D1283E9C628BCEE87064A974B5B0969581CFCBFFA0A79AAC78BBE9F2CC1C1614ABBA6C8B38F55CB6FA235E88C3B7A3F6A7E572F9DDBB778AAEBC5062138626ED9610F2B655E8B3B47CFE72C31E35FCB26C36FB8CF37F902F5B7E5F3E9F47BE801E8BC1F31EDB109B9D7C6AC7A5E5F433F95DE7C412764B9FC50B35F51AB7495114AD006AF682F158EE89C5742A01ABECE2743AE567D4032FA5100176E8EDEDAD2C2F21F3ACB12F31079D167DBF142C9204442E0813F5A8DED20BCAAEB745F6F6A4F14C54D17C3E17699F5D5887684CA6283C9AF637D4DD38213EE13D3443A75791BAB2C587E32C37BDDD32519FEF13565E2C160E89AE1863ACA961134B022FD990885C1C2CE2E8B77DDDD499848D63EBA9B0B73ADEDA3736051A9F9313AB5A369BB5C4614CE08B8B4E59AF628D911CB44CCAFBC48638FDDB8DEC784FE26B02E27C89C4ECCDCD8DE517F67DDFF6787C74F2B6AAD056754287640F9910C7B6059F6493E8385410E86872CAC15F5782AB2F8C84A9B53A2D34DBDE40A105168B85CCF0079B60485E0877E5AC8095E0DA26A0720EFEB75C2EC3D3A4B28E183CB595B09A18078019DAA224919EDBE9E90C4C2613095C455A639654E6B9EDD42C3167318EF62C39BCD5FC89A2FF56ED69495522C48AC92374B6CFD2F8A074632C4ACBD128C5065F0D110FA7BA9566D60E2B3AEF98CD66EDEEA8B4C7EE912D63B6674F0D191ECDC3DB57B6349072086C1C66BBBE414D30B653D6319EABA4A5435E4FF74AA27CDB57FB258A7D369B8D9C382096B55ACDBAD596B9F0D113BC5AADBADDAE6484968FD320CAD0E79A3F0538D21CF02C5A73D2C60A63E62F7A4C5041A20DB16DA58AC5A240C4DA756EAC0D324A81ABAD5F744FBC797B7B6B6B5C8F017AA883A878A28C1AEDD4CDCD4DBFDF172ED0C203623A198150E435C517E8F4CFC468B2C6692E97B32A13AC82EFFBC874CC3DD5166EE3A96DEECBEE94CC34AC54E5A59D36608ECF64C1C8A40DE3D11A0E7D2BA1585BF6029EAF52A9C4F356B386777777DD6ED73A2EA22D73981274CE5925B90E36A767291B1F9403EC8202BB4CB55C2EAB48DBB9CBD13DCBCDC9C989C4B7E098D6C092E0B6D79F7FCEE773666B3BA0C677B6C40F962FE2A452159BE262EEAA6FB0FDF01C337C9791A785B27938164DE6BC03887C21216EC3BEDC0759402879FEA9C2AAF8C8CCB6DF24FE5F8182BFD05B58F7DC4253D0B4F4478F718BD8720920C7C4B01786C30D9EC97241E886D8B294ED80CF683412CAED75D1294C98BA09B108C9A22189B75AAD1E84285C5F5FEBF2C4B4A373167C341A6153FBBE4F6A2193C900F1CE6432321DB6033B2A84C1A488C153DBA871A3D110F00ED1C9DF4AA4828A735A3862630A7BA3D882A5C47930C16E7D027D520AC9A12ADCC55B3D9D4E79944DC6A8C7989826C5B068B97685B723A3283F5851A6F8CB2B2B5E3A55A11ECE80657207C83B994C4AA512EBC966E954AC56AB4EA7A3F2E9688B7C825D53E4C7A667638C6216411CE824AEB54D70C83CC82CEDD437D8E6ABFAC618A38AD94A49D333C801B6FFF18F7F7CD8757881FBACC505DFCE0259E388FF1BDFDBD056FAB26DD645B55C480EDBEF7325E88220E8743A7FFAD39FD89B56AB85FAE164477BF023B30EF41011BDA740D09C3339E600E3ACCD1E19FC1C5EA7EDFDC6672C7744B40704F565129B081A5BEAC26F240546A351A7D351DDE0783C16AF4E8C1087927E301890EBE7EBACDDB48D50B6ECAC3CD6AA0A16BFD96C3A09438BA79ECD666C340758C14A0C4CDE51745168628752DCB688ABD7EBBAE7C25AC447A86480A30372B91CED29108882D3C4F356AB15AA4E088938859BB45CBC26294155B4A9C9A774C9A3314CD9402C72A3D190046095F4A8D1684421D8B6B3428CC246F9058E14CFD27038E437D4C5482B5B769A788B536C8B9660554BCAA5B366D383F50DEC8280F3B55AEDA79F7EDA13D0659B0F7350E92D1786E17FFFF77FBF8210D7DBFEB3CCDF6827308FF57A7D9FB234058265B8B17FF1C2EE5962BB223671A00E322D958F8AD1B49BCD460A7CB3D9F0B7D6E9D6DD7BFFFEFD83CA094CAB938BB73900E722BD2E2B165B8065A16E0076F7E5FC6ADA846B1565DAA731F4643271E07AB6AE1DA924AB93CB69B388882A7E6FE116D16E3C35B278BBDC4302DDB20BA81FBC7AB37142884A6BC26FDFBE7DB4E7A465DB180C06523FB62D99DA9200A87890B79AE887525048F65EAFA77D39393969341A36D963BB28287AA08931A57D783431E094789091AB1757E9B2836C21CA5FA9546C70D97E12E67DC941D4B93642ACBF30F1D2F771D724AD3793C964A4EC6DADA9A4ADB4F283F50D3C8A3FACD56A8FAE8FD81AC6E331EBAF17B72DBCB78DDD97B0C4E5FBE3EA6A99B84E854261B95C72C81E05904D2613A9D3376FDEF8BECFEAD8B0C317EA2061793C28D47476771F4F82D8022FAEA09BC5A270326820602F8C73496C2D22F750F251C572D16BF750B6E163DBD94B5984C96462B1748A02095065C58723C47BBD9EDDEB7EBF2FA2412D2CE007C1B924E2D3E9B424084788FF85F9AF3F79104FAD374227D93034AD44F5B3B35F0E5AC356E42B7CFFA828A41BA41C1ACB545C2A95D41E219EB75AAF63030EDB03204ABD5EE7C2CA304271E2EF3A5CE7F14C3EE845949F6D9A9C4AA5E4F444F7058A4AF9580A40859E443030180C6C5A482D5B1515E10FF5758F6689380FD69B7152F12C9A6E5F4C1D0C2C9296692B46BE81CE246AEAFBBED3A83332E5C4DB94AE2F844E6177D7EB3545CF927DA552494C37F1C2B7DFEF6B454E4E4E9C689116689FD8FA532D716E202978DB3112A0A1C868E26F20153D38BF3ABE121F16DFA27A6BDD5B7997D689439675BB5D152338D8B2E8B5A187AC9B0C0A9AB82B7C311A8D58B14EA7C3CBE2A423C485D28DB7C491E63A0C62F0909EB0ED896931E3348D9341E054EBECC253F77A3DA12374D31454E11D91E0B2E5C99738C97976476547C2AA3E1A8E43B155AB557B141DD447B3D9DCC55B2D296601D4EA74C1EEE47239DAB0D9E6D176FE760BC0BC0641B08FFB0BE1972AF5A547A94572F2751C0F5830ADC5C37B6955514E32A1982DCFD49F542A9552A9845F18DF2BD1DE6E591EF66CD8CE2136BCE9D437A85E4F3E53BC7C506851425F6775BBE9B313267D094B5C9830072AC4FE09A9FA682F41CC766539886C5A1B56B2E0D9C115760E9612DAC652E349CF6DACD6E1845209964E98FD5291E8734C6916437056803085ED6C7C2693C9BCAE250E7FBFA299BA54DB403A25F715DFB441C95D421C334D7821C97D8C17D6D06E8AADFEB74F462E805808824086EA8378EA68AB77A8B43232BD542AD9DD7192255C7BF563D24C8000C74754E44C381DDD78A05841784E0C6FB5CA9434730B9C57BF21D0DF4C5EA1674109F98D13587FD42D1B8FC732FC157C97C720360281C4754174CBA082D1E63A9251A53A5C8742A1A08A794B3D1F33619D07CD936DAD56AB777777728BB52031F50D4EA38FE8B1E25BF562C4B743BE6D6BD0578018EAE870042DF9F22E3EDF780DE90C8469B55AD50978766086B0CCD81112DFB6408EE3253799BC13CD9964AD73B0845EC25838801F5C59296B4CC92B873A95C2E2675C04076D2656A007E366CBE53208021932C4A62D48C0892D5A278C34003AAF5EAFCB96B1EC831279DA0B11C6EE5A4F21B8A5E71CBCB3ED2BB6CF52148B454D495DCCB771FDF157838CAB35336D7E488846D495F5CD658647866E49A5A136582179C7E2104AB2A757875344288F4229A2DDF868F27E3257E567ABD05C9DE97562EBF5FA7038D4F557592C5BA659593EFA680BB3CF37F67A3D9D1916219E3F9DBFDA6C3672562C708ED5B624B4FB0FF5CBDEAE239193275090823FC87D76A452A9EC19197E094B9C5E7C93C944985C190B8A2B397CBEFB0B71EB611144DBBFE9E23E438AC132C19F9F9F8B5E43379024B2CD760A76E2D0240969BEDD99FB517E70675F47A39126E074DA7DDE2258812005DAB5529573495B51C2AC6C0A592CBA13586CC036FE9A15502F63199BB650DE96EA58AF4ED8BE4AA512B39EC21D4AF2964A2564BAF29F8ED5B33D482AAABF81139689B670FDF1FB582A95783B45D86559AB45B8CD31DAB275BA2B883557AFC64E390BD56EB76D3F5BD5F2E47239493D7B669C0A9A5D89D607F9DF9DB88D5D07B9E3B26C6C99850A4A24972507F91887EAFDFBF752BAB6F986BD65B6A35EB4833F5D4C93FCB9C8C87450E5A9C443F81F1CD2A33FFEF8A38D1B3B7E9E75D385C842A1EE4335FE42425C61234BDF6CDBAD3E085F7F9210679F842C8CE7523820B02B5CB6AD03E494C08ECD86C9F0A482C9A158A2C4C01A20B20B0EE007A7B36D745F436173539C0002EECFC88D65F5A53DE8AC03D41F3652ECA48684C28EC15F6B11D04C9637993FD76FC85E6EDBD4EA2FB3BD9E4A91D97E05D61D546F5C47186D0F8BECB671CF6807AE7F973290E3AFCE03A55249496F85A114F3714AE15521818328C245A7239AF0E39AB34DC85BEDC872093FB3E7C170F0D11622A5C36FD1845C04E6A02B63B9447829560F2300F24BA795A382FECE9574BAAFED3A6F0F36E1D489923DC4318EC72CECBA2F0E0B71B158746078CC01FDCD170993AE0E4DC762890B9762A91D77F1F9C6DC9C078775EAB5318FA25C0E8808592E0B8C1D1BE6B6969493EE27D365ABC5549D1486E153F9C1A7D3A92A95ED092679C87739C8AD6719C09F55D22DCBC8614CB6C660369B6DB7DB16ACCDB9DCC5676DAD66274664A30440039DB8B6937E7C703D2D9F9C742AA8706B78C6DB134110D8EED8F22C0F83BA5A208DC04572E024FB74A29ACD2666B89E0058DE397B842CA4681517A663BDA2AEF97CBED16890A2B01960A72F447CA2551E8FDE5AD142E68F8B50ABD5141383A85D3C88F6CE5A06CA5F7EF9E5F6F6561E864A28A4B17406E4268A143A9D4EB75A2DF5117DF0BC71FBAC2165DD141E6B796F0EB0C44F4E4E6C6E130F00AC844065823309D3A2CCD09E72FC85C229D9FBD16C36E5E845BBF97C9F6489836790FDF5BCD93C49965EAFC79309A8594C02ADD3714EA557A4D565527162AEAFAFD54679BB4BD9A342C416F888A3D2C196714B7DDF7FC6B09235039D7627521EC03F74A374DF9ACDA650A4BBF0D72C0E26F3683492B610ED9C9EA6EFAD56AB840222C30EB66B3D1513B7132E168BE3F158C00009FD789E6B2725682FF936AE3F4602E28AE1C1D8CA4F9BAD95312B4487423D6A72C6941076B44A70EAC51DD61A3EC00AB338443BEDD9DB071DB00B1F6D4D6CDB544876860D42A2F6EC82B32312AF5A6AC9561ECE99975AB2662CD8A7F8F36683662A83B2BE9A0AC40E4BB06D5793D01E56A7058FD98640B193EC7AEE797F5F42884FA753870B867AD607F97C778DCC8EA1F0960DC03DAF1CEFF7FBF6F916A520DF4D293BB64DBFB72441D634B3B7E5A9FCE0B689843D2E78036FDEBCA1BFCCF38695C84BE772B96D85A1DB25F101653324DA360484D87A107F2D971621F88F7FFCE3E4E4C4A2CBF1AC55C3C29772F92DFE32663D99AA1233B28CECE62E97CB477D67C404766EA15010422306D7FFE00064EDC407D489024DAC960B96FBD439210AB9A8BC3097CBA97689D81A269443FBB37D4D5408CD418DF12476E1A3B9E0A7A7A7E42A9C781AB3AAD7EB8A562B7A8348D5BD98CFE7B25132990CAE834AF09DB3F7F6ED5B2DD10F3FFCE074DFDE85F7B7741D887B7D910E9BE3D2ED3F14B3CAE7F3D2A6D64BDE06AAA979AC501E7BEA8F97C0898B76D25699EFE2F38DC1293E38D0019C80ABAB2BC4C49780D6713284ACA2FCBA5028009650C4BF5AADF2A6C811AE01C791A83D2769FB54EDC90F6EF14C9863300148164486AEFA195FDF76C9D2E5C18EB0646FA7A7A77299E1EAEAF57AAAD27E30D2A50008FE38300629271B39B132DDFA2BEBF59A6A37EB7E3AEB69B15FE974DAF6155262466D0F1F5D0A494CAE3795E23AA80FE2FA1F8C890F0683F1785CAFD7B9E7C22FA9ED9FB02B6221B6DB61498CBFFBEEBBEDB89603A092EE717C022E910CD2FD79EAB7F1D148255BD0777E7EAEE049B95CB60018D19FD967DA7FA22A6C828463C043D4BDD3520AF32E58E26118EE3A6F96C23E324402F669ECD1A3F6658C312E87C34E150506C7BA78F6EBF53A9ECD760CE0282C714BE4A8D85C14CBE77B58EEF4493EC893729B8E156C592C1477E35D56AB9562E87C987BAEDA7D69750EE8C1FCE0883C15AF3BD53D8E5DF38CF6B8E8E56CE68A630A519FAC0927A4A639EFC25F3361A74F9864413E9FB79C4D161F66CDA598F5D49C1D87C6B9A8B61DF62E5D6ECF8073D91C5C7FFCA1726015D8D10EF0DC4E46343EFAEF603010AAC196B7608F8BD1504E038BCF7F796BCB6D2D061227EFF2A051B50B1F5DABD510E59614177A5B8982E8B76CAB5A34BDAC3A2728A3C6DB294FC05740D7954EA7B741EBDAD35DE72D0802A755808D84E8CF0FAE00573DAA9393B735DB8A0DAAB60000F1938461EA51E0C793067D84B9D893C944DB96CFE7B15FF8219FCF2B7C968C64FC0E47F45BCEF4CD66D36834822090384EA5524110D87BBE5C2EA9FE40280F0603BA7F813286153D9BCD5ABACD2F3756AB15957A85428129F5FBFD8B8B0B792A2AE151438FC160904AA52CDBED61EB53AD56F50344864E8CE21806C0F95AAD461C92ECF16AB5AA542A54EA6A1DD0E84E43CE278DF4734D7A381C8A668CB05DB95C2601C8C983458C248C1CD26424E3F7398479A783473A9DFEF39FFF6C43CC9D4E279FCFE77239BC6C4C72B80609E5351A0D02F190264E269356AB45E0827BF745077631C214BADD66B3D9EBF5168B45A9545284FAE4E4A4542A2D160B5C43C26BB434A29CE780F5B9BDBD15B94D3E9F877DFBD8F617E431B5C7E5721998723A9D1E8FC71717179BCDA6DD6E2B3EF63912DCF3BCE7B4C431BD0582CEE572CD6673329910D21273260DBF2D542019C9F8BD8DE9744AD8240C435846B1B8A7D329B8855C2ED7EBF56CB3A16EB76B43AB5C37707B83C1A0582CCE66B3542A35180C1C169D2F31C6E371A552C1394022A372F8B95C2E239808F729B3A7169DFC618C05BA6B7D783EA8FC639621EC1DEF4B9EF3FDFBF71F3E7C20D657AFD7D9CDF57A6D29DB5ED912EFF7FBE572997AB6E572090E17E2632C087078D0EF1DA1E64C46325E72C0B0A8641A26CED5D51551540456A3D1C0561D8FC7CBE5B2DD6E63D98DC7E3F57A4DAC723C1E0F0603B05E102DD46A35DCE22F3A309301C87BF7B54E4045ABD5AA62CDE3F1B85AADF276E20691ED1C6341EE5A9FD56A253E29F8303CCF03E27D5443129C9ADBC562F1D34F3F2D160B6140DBEDB614F69E21A697B0C419777777AD566B369B9D9D9DE1D6D1973A0C431AC4114BD94580998C64FC4E06979CD20F013048A365B3D9D96C86710DF8270C43616A49298D462338BFC02912748E978CCF3B3437D9D79EE731BDE572C96FA0E55B2C1632C025C56E6E6E2C5FDB9EEB339BCDB2D96C1886D8E99F194DFED2CE1664382C08F3473CDA8F915D380A4BDCF3BCBFFCE52F9EE7B55A2D1A7E135A29954A50CE9F9E9E2AA272B4EB9E8C64BCCC50698C1805C6E331C2CE52D3E47239E2CEDC1A7103140A85B3B33331AB1074C61207A2F73242FC9F42249D8605683C1E937405932EC90B4967369B852BA6D3E9785B00C13DD707DE310C41F024C7E9D6931B00208B2663AA48705C875EAF1786A1EFFB9F99C3787E4B1C9DA39EDF3012DCDCDCB4DBED6EB7DBEBF59ACDE668347A81B05D329271CC83167D61183A741F440F44FD48AD296149F9AF3FFFFCF37FFEE77F12CA00F0502A9500E63F8B71B7FF2BACD76BDBDE73B55AA14B648943B78041CAF5E7BDAEAEAEE83BF1A4F5010F636D5878AC8E4D9E10B2BFBDBD3D3B3BC35D4030F6FBFD66B32907021CE736ADDBEB0871ED96EFFB954A857C2655C504F2E4E80D87435BB5918C64FC3E2D7119D1004E6C1B07BC58B0C3D56A55F10A100EFC9C4AA59ACDE67C3E97451CDDF76E7D014F97B4AAB4050012AC37EA9210EBC455A0B657C84592CBA62BF75C1FCFA43A4188DB271F9586A6B51B52DB469F54AC2027E633B76CA71047D4CEE7731ACD81A2270EA58D617DED4EF019ADE966B321AEE7DD5300FBBE6F412CC9484632F619C485317B91838D46832A18897214C076DD6632BEEDB15383612C97CB653830F3F9FC9B376FD08DB3D90CBD914EA77FF9E5172438C962EA47A1AD019F2F09DEED767DDF27AB6EBBFC242319C978740063C866B31F3E7CF03C2F9BCD4E26134851FAFDFE6C3603312DCA8764C57E3F63A7251E040161263A6C0D0603A4332179627652FBE3F1B8D96C964A25617D90ECB809A3D1489F9497912C7D3292F1A4019103B5F810D576BBDD7C3E4F750F494EFAB87E668C3519DF8810F7EED973C230A47FD23FFF2095429A0F8743D8F169C0B15EAF49B0C05E46CA051222CFF31A8D863D82C0F593D54F4632F61CAAB6A7A2A7D56A113AE77EE572399AD0F3199BFD4BC6EF5788D34D4DCDDFDAED36EDC991D7C45B609301B6C9EF9BCDA67A74D9A751458601FE283E3419C948C6F6203346C13DFC1BDE3DA785778FC98348242987FE5D8D9D610D8A627DDF3F393929140ADD6E9774307D0F2E2E2EE86D4A9AB8DD6E5389D0EFF7E196BBBABAC2E2E660351A0D0ED672B94C24783292F1D4A1A2BECD6603AC987A4E49F09B9B9B42A14077DD24269E58E2FF1CDD6EF75FFFF55F6F6F6F1548E1B800EB29168BB0BA6083535A4AEADC82080542A24861B158E0032600956424E3492308826C365B28146C5337C8A4A0CF854054C581C9F8DFABACF40000022449444154BD5BE2B3D9ACDD6EFFF5AF7F05A0E2DDB3008BD801095E2814C48C4C5C65B15820C1A11856D101E81492E989044F46329E3446A351B55A950487186B3A9DFABE0FE84B0519008293154B2C71CF1AD4BD5EAFD56A09F1FD55F0F9262319DFD2C08B2D954AD04BC12A45CA8AFE062005269309ED7592B07822C43D95F09016B7D9C8F97C8EFE878EF2F2F2F2ECECEC65CA7C93918CDFEDA0960756224029945C605AC1205828149C2E94C9F8FD865324C191CE4870986B96CBA5EFFB5114D1B8EFE2E22291E0C948C6171DC3E130954A552A954C2633994CE86E3A994CC230FCEB5FFFBA5AADF2F9BC9A4FF67ABD64C5124BDCF33C8F9C89AA78458FAB4EE1D401178BC584D02A19C9F8A2837B47BF1BC027F453A601109F198FC76010139EE74488FF66C0405FA95410E202861F3F9F6F3292F1ED0D905DB3D92C0CC36AB59A4EA70783C172B93C3D3D95ED95D0A72442FC9F9A3F954A89E54A029D100AD45C6118527190845392918C2F3AE87386F50D12CCF2FC4165F1620CB4C938AAB13326EEFBFE66B3994EA75419903CA9542AB872902BA6D36982E347D81E2919C9F896C666B3A9D56AA2C1FAF8F1632A959A4EA7A078B1A5E888860596AC5862897B96C0573CB4044FBE0A3EDF6424E35B1A94F0F47ABD5AAD86B96D1B0BE034EB63313CDDC9F81D09F164242319C948C6F18FC4824E463292918C4488272319C948463212219E8C64242319C94884783292918C6424423C19C94846329271F4E3FF01CBEF5765E52F8A210000000049454E44AE426082');
Insert into usersignature (userid, imagetype, imagetagno, imageunid, imagedata) values ('jjones', 'png', 80493765, 'fb263bc057fc4911b6225e3254bbe565', '89504E470D0A1A0A0000000D49484452000000C80000006408020000004CE4E85C000000097048597300000ECD00000EC401B2245FD300000A4F6943435050686F746F73686F70204943432070726F66696C65000078DA9D53675453E9163DF7DEF4424B8880944B6F5215082052428B801491262A2109104A8821A1D91551C1114545041BC8A088038E8E808C15512C0C8A0AD807E421A28E83A3888ACAFBE17BA36BD6BCF7E6CDFEB5D73EE7ACF39DB3CF07C0080C9648335135800CA9421E11E083C7C4C6E1E42E40810A2470001008B3642173FD230100F87E3C3C2B22C007BE000178D30B0800C04D9BC0301C87FF0FEA42995C01808401C07491384B08801400407A8E42A600404601809D98265300A0040060CB6362E300502D0060277FE6D300809DF8997B01005B94211501A09100201365884400683B00ACCF568A450058300014664BC43900D82D00304957664800B0B700C0CE100BB200080C00305188852900047B0060C8232378008499001446F2573CF12BAE10E72A00007899B23CB9243945815B082D710757572E1E28CE49172B14366102619A402EC27999193281340FE0F3CC0000A0911511E083F3FD78CE0EAECECE368EB60E5F2DEABF06FF226262E3FEE5CFAB70400000E1747ED1FE2C2FB31A803B06806DFEA225EE04685E0BA075F78B66B20F40B500A0E9DA57F370F87E3C3C45A190B9D9D9E5E4E4D84AC4425B61CA577DFE67C25FC057FD6CF97E3CFCF7F5E0BEE22481325D814704F8E0C2CCF44CA51CCF92098462DCE68F47FCB70BFFFC1DD322C44962B9582A14E35112718E449A8CF332A52289429229C525D2FF64E2DF2CFB033EDF3500B06A3E017B912DA85D6303F64B27105874C0E2F70000F2BB6FC1D4280803806883E1CF77FFEF3FFD47A02500806649927100005E44242E54CAB33FC708000044A0812AB0411BF4C1182CC0061CC105DCC10BFC6036844224C4C24210420A64801C726029AC82422886CDB01D2A602FD4401D34C051688693700E2EC255B80E3D700FFA61089EC128BC81090441C808136121DA8801628A58238E08179985F821C14804128B2420C9881451224B91354831528A542055481DF23D720239875C46BA913BC8003282FC86BC47319481B2513DD40CB543B9A8371A8446A20BD06474319A8F16A09BD072B41A3D8C36A1E7D0AB680FDA8F3E43C730C0E8180733C46C302EC6C342B1382C099363CBB122AC0CABC61AB056AC03BB89F563CFB17704128145C0093604774220611E4148584C584ED848A8201C243411DA093709038451C2272293A84BB426BA11F9C4186232318758482C23D6128F132F107B8843C437241289433227B9900249B1A454D212D246D26E5223E92CA99B34481A2393C9DA646BB20739942C202BC885E49DE4C3E433E41BE421F25B0A9D624071A4F853E22852CA6A4A19E510E534E5066598324155A39A52DDA8A15411358F5A42ADA1B652AF5187A81334759A39CD8316494BA5ADA295D31A681768F769AFE874BA11DD951E4E97D057D2CBE947E897E803F4770C0D861583C7886728199B18071867197718AF984CA619D38B19C754303731EB98E7990F996F55582AB62A7C1591CA0A954A9526951B2A2F54A9AAA6AADEAA0B55F355CB548FA95E537DAE46553353E3A909D496AB55AA9D50EB531B5367A93BA887AA67A86F543FA47E59FD890659C34CC34F43A451A0B15FE3BCC6200B6319B3782C216B0DAB86758135C426B1CDD97C762ABB98FD1DBB8B3DAAA9A13943334A3357B352F394663F07E39871F89C744E09E728A797F37E8ADE14EF29E2291BA6344CB931655C6BAA96979658AB48AB51AB47EBBD36AEEDA79DA6BD45BB59FB810E41C74A275C2747678FCE059DE753D953DDA70AA7164D3D3AF5AE2EAA6BA51BA1BB4477BF6EA7EE989EBE5E809E4C6FA7DE79BDE7FA1C7D2FFD54FD6DFAA7F5470C5806B30C2406DB0CCE183CC535716F3C1D2FC7DBF151435DC34043A561956197E18491B9D13CA3D5468D460F8C69C65CE324E36DC66DC6A326062621264B4DEA4DEE9A524DB9A629A63B4C3B4CC7CDCCCDA2CDD699359B3D31D732E79BE79BD79BDFB7605A785A2CB6A8B6B86549B2E45AA659EEB6BC6E855A3959A558555A5DB346AD9DAD25D6BBADBBA711A7B94E934EAB9ED667C3B0F1B6C9B6A9B719B0E5D806DBAEB66DB67D6167621767B7C5AEC3EE93BD937DBA7D8DFD3D070D87D90EAB1D5A1D7E73B472143A563ADE9ACE9CEE3F7DC5F496E92F6758CF10CFD833E3B613CB29C4699D539BD347671767B97383F3888B894B82CB2E973E2E9B1BC6DDC8BDE44A74F5715DE17AD2F59D9BB39BC2EDA8DBAFEE36EE69EE87DC9FCC349F299E593373D0C3C843E051E5D13F0B9F95306BDFAC7E4F434F8167B5E7232F632F9157ADD7B0B7A577AAF761EF173EF63E729FE33EE33C37DE32DE595FCC37C0B7C8B7CB4FC36F9E5F85DF437F23FF64FF7AFFD100A78025016703898141815B02FBF87A7C21BF8E3F3ADB65F6B2D9ED418CA0B94115418F82AD82E5C1AD2168C8EC90AD21F7E798CE91CE690E85507EE8D6D00761E6618BC37E0C2785878557863F8E7088581AD131973577D1DC4373DF44FA449644DE9B67314F39AF2D4A352A3EAA2E6A3CDA37BA34BA3FC62E6659CCD5589D58496C4B1C392E2AAE366E6CBEDFFCEDF387E29DE20BE37B17982FC85D7079A1CEC2F485A716A92E122C3A96404C884E3894F041102AA8168C25F21377258E0A79C21DC267222FD136D188D8435C2A1E4EF2482A4D7A92EC91BC357924C533A52CE5B98427A990BC4C0D4CDD9B3A9E169A76206D323D3ABD31839291907142AA214D93B667EA67E66676CBAC6585B2FEC56E8BB72F1E9507C96BB390AC05592D0AB642A6E8545A28D72A07B267655766BFCD89CA3996AB9E2BCDEDCCB3CADB90379CEF9FFFED12C212E192B6A5864B572D1D58E6BDAC6A39B23C7179DB0AE315052B865606AC3CB88AB62A6DD54FABED5797AE7EBD267A4D6B815EC1CA82C1B5016BEB0B550AE5857DEBDCD7ED5D4F582F59DFB561FA869D1B3E15898AAE14DB1797157FD828DC78E51B876FCABF99DC94B4A9ABC4B964CF66D266E9E6DE2D9E5B0E96AA97E6970E6E0DD9DAB40DDF56B4EDF5F645DB2F97CD28DBBB83B643B9A3BF3CB8BC65A7C9CECD3B3F54A454F454FA5436EED2DDB561D7F86ED1EE1B7BBCF634ECD5DB5BBCF7FD3EC9BEDB5501554DD566D565FB49FBB3F73FAE89AAE9F896FB6D5DAD4E6D71EDC703D203FD07230EB6D7B9D4D51DD23D54528FD62BEB470EC71FBEFE9DEF772D0D360D558D9CC6E223704479E4E9F709DFF71E0D3ADA768C7BACE107D31F761D671D2F6A429AF29A469B539AFB5B625BBA4FCC3ED1D6EADE7AFC47DB1F0F9C343C59794AF354C969DAE982D39367F2CF8C9D959D7D7E2EF9DC60DBA2B67BE763CEDF6A0F6FEFBA1074E1D245FF8BE73BBC3BCE5CF2B874F2B2DBE51357B8579AAF3A5F6DEA74EA3CFE93D34FC7BB9CBB9AAEB95C6BB9EE7ABDB57B66F7E91B9E37CEDDF4BD79F116FFD6D59E393DDDBDF37A6FF7C5F7F5DF16DD7E7227FDCECBBBD97727EEADBC4FBC5FF440ED41D943DD87D53F5BFEDCD8EFDC7F6AC077A0F3D1DC47F7068583CFFE91F58F0F43058F998FCB860D86EB9E383E3939E23F72FDE9FCA743CF64CF269E17FEA2FECBAE17162F7EF8D5EBD7CED198D1A197F29793BF6D7CA5FDEAC0EB19AFDBC6C2C61EBEC97833315EF456FBEDC177DC771DEFA3DF0F4FE47C207F28FF68F9B1F553D0A7FB93199393FF040398F3FC63332DDB0000000467414D410000B18E7CFB5193000000206348524D00007A25000080830000F9FF000080E9000075300000EA6000003A980000176F925FC546000022EF4944415478DAEC9D675C1457DB87CF99B29D85A52D0B482F62171029F61A1B1654046B8C92449398C457131F35BDE96312A3892562ECBD176C110B82522558905EA4578165EB94F37E5825C482B058F2E8FC3FF97396DD9933D7DCE76EE70C4408014E9C9EB5306E0838716071E2C0E2C481C589130716270E2C4E1C589C38716071E2C0E2C481C589130716270E2C4E1C589C387160717A8DC17A292D394D8D405C47D04B14F15CBF1D42B07BF7350C879327F961D80BB28E898979BB76C5D2343B63465F7F7F37EE1EBF6A601D3C98A852E956AC3CDEA387D39450FF17733D1A8DFEC4C9EB6BD74659599B87B6F147ABAB955BB7C6D0348361F0B1A63734D4DFC9C98A83E6A581A5D7335762333FFA78477151A599CC3474B2022100E18BB81E1E8FB870E1360060F8F0EEFEFEEEADF9931B370A1312724992506B74DF7F7FACB6B6E1B1C65720E46338662E137B7B3BF5ECE9C4A1F3A2C152ABF549C9F9E1537F651966F4183F1F6FE79010BF17763D1919A50CCD1204C1E7137C7E4B575755A58C8FCF26082C3E3EE7ABAF0E420C871013080899B9546A22A419B6E993388EA9553AB546B764C95E86A6BEFC2AB45B37071CE7E29E1705566DADAAA6A6B1BA5A3963C63A8D5AB77DDBBC81033B51142D160B5E8CB90200C4C7672726E5CAAD4DC3C30219867DF4F6E7E6565454341004565E5E3F67EEA6C6468D48C897994B1FD85ACAA183C5E6C8089198D7F4270C83E6BFB72529294F261337366A8F1C491C34B0934C26EEDCD99E03E8B983D5D8A88B8FCF993AED379AA6C78DEBF5ED3793C562BEA9A9F0055F0F8EE3000196451616260F51959F5FC9221015F5D7A74BF60A853C1C87B5B52A2F2FBBB56B661004DE3CA8F4EA642712FD0D164D33DF7D1B8A10AAAC54BEF7FE96DCDCCAA1C3BF7F2762F0D75F4F9648F81C43CF0B2CBD9E81104447DF9E33F7F7BA7B35B3660D5BBE7C828383C58BBF189A66F57A1AC3214DB314C53CB0A38D14C59024BE6143F4E63F2EE138A6D1E835EAC6C1437A7EB86004CBA28000773E9F6C698C083C28C803005055D5F0E3AAA94B97EE2F28AC3A7EE27A5858A0AFAFF30B8B76FF398F37200458969548041289E055030B21841050AB756161BF26A7E461183C7E7C99A3A3A58BCBCB099DAAAA1AF6ED8F67196AE0C04EB6B66614C550147DE9F29DC58B778B44FCF2F2BA9AEAAA88B747F5EEEDA6D352D6D6D2D1A37BB6E9FBADACA463C7FAC4C4646CFCFD425151CDEEDD71BD7AB9BC942BDDBB2F7ED57F4FC8E5A63B77CEF7F050BC526021842084AB569D3A743821213E7DE1C27141419E63C6F47C5957C2B2ECA9A8D484F82C7373E99429FE72B9694D4D634444645E7E656E4E0900F8FB1F8CB09049FA0FF01A30C0CBE85F1189F89326F53E7D26ADB0A0A2AE4E0D5F98F3F84FE974D4DDBB653A1DFD6866E47F1B2C03553FFF7CE6E79F8F5A5ACA56AC983969526F67E79799E3292BABDBB3F79A4AA51D3EBC475090E7DAB567CFFD79333AFA364160EFBD3F0AB168CE5B03BA757368FF0FF9F9B9BD31BCFBC68DA70502D2300E8F208E9AEEF7630388760A21C4E71300E04DD3FDAB03168470DDBAF33FFC70D8DEDE7CE9D2891326F83EC3517BAC1968391386103A71223531391700E0D7DBB5AE4EBD6B575C7272E6C0413D5C5DE44BFF33CEC6C6F4595578C462FEC4897E67CEFE95949C979E5ED2A993FD4327A652E9BEFFE15869E93D92C03FFC70C4330F1E1B1AB4E7CEDD148B4521217E2FCB643E17B062E3B25292F3BFFCEA90BD9D74F96793C78DF57E862704218C8CBC989B578941A8D7D306683C3D15B3670F68E1D18710DEB953A2AC57C9CCA5969626D5D5CAEBA985B676F28F3F1A396A540F964586CF3CA333047DFB76F4F0B0FDF35CDA9123491E1E3624493C94F2D8B6ED4A694915C9E3933C62C6F43EAD4CD5B646DBB75FB979B3F0D6ADA2B1E37CBFFB2ED4CC4CFC8A8045D3ECEF1B2FECD871DCC3C36BC5CA1923DEE8F60CCF2629292F29296FFBF698848434B1C4C2C9C90A21949F5F396B66BFA73A1342210F00D6466E3A36D8272BBBDCC242ECEA623D6080178410C79FF1630D21B0B0906018B8129BB974E9B8878EE6E454949654933C3E8EE3EBD79D1B3CA8CB33F9D11B378BAEC464ECDD772DF64AD2B8F183D6AF9B2D12F10802FB9F070B21949A5A989F5F792536C3C3D36BFDBAB7FAF7F7AAAF572726E549C4825EBD9C9B67838C10C330274E5EFFFAAB3D721BB9B3B3B3B78FF31F9B2300000BFF6F57585860CB536169695D49492D009848CC8710F4E8EE386A947761611549E2CF63D4300C0B0F0B3C7830F1215B0500484CCCCDCE2E939888BB75ED505ED1505C5CCDE7E3008063C752D41A3D06214288E411438774914A850080F2F2BACB97331040104086612DAD4C860DEDFAD077C6C7671714546764947EF9E54167171B2767D7F0B0401313C1BF6A1EACAB53C7C666420882823CCDCC446D004BA5D22F5DB6FFCCE96BF3E68FFDE9C76934CDE0384C4B2B1A3EFC87C993FDF7EC7EAF3DA7555FAF4E4ACA3B7428512412874D09F8F4D360BD9E964804180697FE67AC858549CB16ABA64619752A8D2FE08787058AC5028180FCFCB3090CC300F05C869E203081806459F611EF47BD65EBE50DEB4FF9F6F28A8DFDE2C30FB7AF5913C5E311A5A5F7BEFCF2506A6A1EC9E3517ABA5B77876143EF9B318A62167CB8ADA2BC9E20099AD20507FB89453C1CC7D56A9DE1DC4982D87F20FED7B5517C81C8DDDD76CB960847472B8180FC375045D34C5C5C16CDB01082DA9AC63FB65C4608999808FAF5F36A15587A3D5353A34C4ECE8F8DCD54D8DACE9AD9AFA912B7755B0CC9C3DF7F6F783B4F313BBBFC9D7737DF2DAC5AB870F4DCB983E472D3A6434F6D28A8A8A82F2CAC4688EDD2B94370B08F404002009C9C2C9F7376E331A1405ADADD53A75271422811F3B3B3CB0E1C4CE00B85B5B5AA63C753EE6494CACCA5082115007CFE7D2C6A6A1A73722A241241A34447E01880FCD8B8AC2BC13F058FF1DE7F20DE30CD41084912178AC4B6B6669191733B75B297C95E9A5FC530ECEDDBC54D511042E8D3257B5352F2050212C3208450A9D404F8BBB7162C954A1B16FE6BEAF5AC8F3E1C3B6D5A908DCDDF775DA9D48C0DF6F1F1716ACFF3A356EB0F1E4CCCCB2B5DB674625858908B8B759B7CBE6BF1D9F3E66D51ABF51806EDEDCC5FF05837598E8606CDDE7DF1C54595FDFA77DDBBE77D5353D1A489BDD7AE3DFBF1C29D7A3D636D2D8DDC14B163C7951D3B2F03000C81C88D1B77C3A7FEEAE464B575CB3B2C8B4812FF79F5E903FBAF1E399A44D30C4D3306827F593DBD7B77479A667BF470344CA02F584AA5B6A6466938E74F97EC8B8DCB14097900000C833A1D4592B84E47DF278FD6D334DB2A1F8B61D0E9D3372E5F4A55D8CAA74FEFE3EE2E6F9E02C020E4F1708391304E2A952E2BAB6CC7CE586727C5A449FE5E5EB66D7477E0AE9D7195950D388E8D1EDD9379647A7A7E4208E038A66AD48A250200406666D9B163292CC20080068B2B93891142F7EEA9B41AE5A449A37AF776A568FAC4C9EB00008D460F00D8B72FBEBCBCEE93C563FAF4F1347CA7564B4D0CF18310364DFD34CDFAF838B9B9D9BC48926A6B554D110F84302FAF72C6CCF50D0D1A3E9FA8AB536BD43A65BDFAC167A9A953074C9E1C4051B4C151B6B3B3780A588694D2D6AD319F7FBEE7F3CFC302023C1D1CFE610F6EDD2E4E4ACE5DF3CBCC27259F5A1310A4A4E4CF9BBFC5C9C9F2BDF9C3DDDCE46D0D51699AD168F40C4DF9FA7A8E1BE72B16BFB8AAB050C88B8DCD3C7FE1F6D8601F964514C5A8D53A80C0B4694106AFF1E0A1440CC3DCDCE4F3DE9DEAE56527950ADDDD6C300CCBB85372EEDC4D1717EBA8A854806877F7BFA11938B0D38B37487A3D8D10C2310C3D7856D7AD3BB76367AC582C80002000D46A5D6666194006B3C48C18E93731C4CF900CA228C6D343316C78B736458570C3860BDF7F7F402814BCF3CE301B1BE94387D7FDF6678FEE8E83067536DA91D4EBE98387126FDFCA1A32D42F2C2CC00813BDF893DD376F16410C373313B53FB1CE302C4280203096450CC3360F2A114214C5F0788461340DAEABBDBD5C61636638BA7FFF358D5A1DD4A7637858606565FDECB736E5E757F2056470B0CFBBEF0E357C8940400E1CE075E8D0B5830713ECECCC6B6A950B168CB6B292BE44EF9B20F0A2A29A051F6E57AB74388E1B4293E2E2DAACCC520018001086F3DF9B3F2C3C2CD0801DA5A7BB74E930654A80F1E986E2E2DA9F579FB2B0902E5A3C4E2E3779E8686A6AE1A143710B3E1CD3BCC3A4AD8A8FCF397A24D1DBBBE3B0A15D753AAAE52E8347AD5D5C6CC69123490D0D5A8944D8B5AB834E47B7DCDCF7545554D42F5B7EA0A6A691CF23962C096EDE298A10FAF1C75357AF65238466CDEC676727A369D6D9D9CACFCFD5E0331514566BB51A3737795555C3279FEC898ABA6E6222A4692628D0A3E94B3A74B0F0EA64070E81C4C45C81A08865D1F4E97D7C7C5C5E16588989B9BFFC7206422C262653A5D262180621A0F4340094AB9BFDA8513D755A0AC7B1050BDE6893E3FB14B00E1D4ACCCACC9A376F42D8948047EB218585555555D5ED8C791B1AB54545C543868C5CB468F463E3AC16530C8DBB76C7DDBBA7128B0508B1234774E7F1DA9BB5BA74E9CEE1C349F5754AB158B468D1A87F7893185650507DF2442C003C00908DDC8C2020C3B04D6E80404062386FC890AEF9F9557BF75E12084D10020881872A7A7676E6E61666D5358D3A1DDDA5B39DC148BC60EDDA191777354B24E2555535ECDF7F150044903C836F4E514CB7EE8E6EAE7207078B952BC30D36BBADB7A625B072732B7FFDED8C9B9BA387BBE2B12E9448C407006F4FFDED6A5C56D4896457576785C2AC79E1B6F5E15862521E49120CC3D8DACA48126F676AE7EEDDEA7DFBE3954A8D5822128BF88659EF417281DDBEFD4A7676897D077B9665A34E26E3040F004010F77F342DEDEEDDC26A1E5FC0326C74F42D00304F0F4549E9BDBA3AEAA1BB3266B4F74F3F9E2ABC5B2D109221217E2FA00DB5A2A27EEBB618866131080100248FB8107DEBD4A944C3E2BF8E5E8EB6B63243046A88D0C78CEEF9D96713689A696A3533A287E28960E5E454E018BB70E1848888818F9A2B86613332CB0CFF303623AA39703061E3C6134B964C5DB66C9C31258E1B770D386A34FAA0400F5F5FE776DE00AD968E8FCF26498265118B504A4A41F7EE8E8643595965EBD69F4F4EBABD7C79985AAD3F7BEE665E5E254D33D555CAECEC7277779BB2B2BAA4E47CA1909C39739D89543C68708F19D3FB2E5BBE5F2A153E343BE3383437176767979A9B9B8F1FEFF79C6A0386B9FBC08104A552ABD1E8BFFDF6A8B2A1018007BF0571B98D954221ABAF574F090D58B224B8897E08EF9BD876D6519E0856BF7E1D6FDDFAD9909378D4122004B66F8B2149D2C4C4C8B40ACBB251A7FEC27131416042619BBDB4C4C4DC5DBBE30A0AAAA45221C3107C3E2110F0DA33102CCBDEBE5D4C109844C2E7F1888ACAFA7DFBAE0D19D2D9C1C152A3D69F38917AE3C65D3B7BBB9933FBB9BACA23B2CAFAF5FBAA51A54B4ACE3B7428B1A7B75346468921E742F2C8E1C3BAAE5E3D3D3DBDA4B24A39617CAF3E419ECD7F482693848707666797999A8A9E6D11332BAB2C3DBDA4A97A88E3F88A9527AEA764038CB4B430B172B16FB2021AADDEDBDB79CB1F6F1304CEB2A8B96D0600B4C9D36D33584221090068E1794200286CE5A1938D5C305850508558E4E8249F30C1AFADD98ADADAC65FD69CD9B3E79A4261565FAFB130974C9E1CD0F6071A5CBF9EDFD0A0410098998ABCBD9D4E9F492B2DA91C3B2E502422F7ECB9169F9073F6DCCD3767F52779C4F113297A9D3A2464B0ABAB1C00E0E2623D7468D75D7BAE4AC4FC6FBE3DCAB22C8661061FC5C444E0E565EBEEB110C7310B99646A78A08954F0CFDB468484F88D1CD91321646565D2FE5B98967657A5D212049E9090F3F1C29D4D960642C8E311D6720B954AE7E3E3BC7EDD9BCD271E9645161692769A25239DF7D6A49B452223F346A74FA7E5E7177FF2C9440F0F455B7DA3F8F89CF3E76F49A5C2D050FF0D1BA2114272B9696E6EA54AA5F5F2B26BE5E40221D8BD3B6ECDDA730081850B477A7B3BE10466F027701CC371A8D5EA0F1F4E9C3B67607171ADE1049B7C8E9B378B8E1DBF2E910800BA9FED6B9ECFDCB0319A61181313C1FAF5B37BF5727DF4EAECDA511EA8AB539795D535993A92C4376C3CFFC71F970D755591886F88270C6EB8B9B96473E45C73733186618E8E962FB23DDF48B0F47A9AA2983767F5332ED7A0D3D18D2AAD7D079B49937AB7E91B1886ADA969DCBBEF5A6545FD7F574D3D742851A7D58F9DD10FC7E0EAD5A7131273F7EE794F28E4291466AD7C3C688A36DC1E8D46CF32080038637A9F3E7D3C199ADDB32786655063A3F6E2C5F4A4A47C0B4BF35933FB369D864EA71708782B5684B9B85801086FDF2AFEBF45BB20840CCD7CF9E5C48000778A62FCFC5C2D2D4D9ED5AD62595459595F58583D7DC67AA5524BDC7F0C30BD9EC2305857A76219DD9BB3874C0D0F323849080180909F9FEB4BA92D1A09D6C9A8D49CEC0207C7378C88F01142F97915478FA69004D1A64A054D330D0D9AF73FD8161D7DFBC79FA6F9F776DBBA358620890B176F5FBD96555454A3D73383067FFBCEDB43E6CD1B4210F85391C530083143A804D2D2EE1AA239737389A5A589404002805FBF5E70E6CC0D00804EDB386FDE90A6042C8F47507A6A40FF4EC1C1DEB6B632008093A3A599998820709A667C7D9DBB76757826B7A7A14143924DA1375AB2645FCC958CEA6AA5564BE975FA072E09D3A3A7FB278BC768347A6767EBF674F4BF7CB0EAEB551A4D6353E9B1ADDA7F20213BBBE4BDF96FB0AD2EEDD134535FAF79FFFD6D470E2704F5E938637A5F8180A42806C7F1BB776B745AFDF2E5E373F3AA76EF3ABB79F3C5A853A93FAE9ADABDBB630BF9528ABA1F4E4308F902F2D8F1948282525F5FB74E9DEC00002121BD0F1F496E506A542A6D54D45F00407B7BF3A6496D53E4459148101E1E64A00A00E0E1A17826AB65288AC1B007132B025F7F7D24FAC26DA19084105214939959565F5707007473B77F6FFE30438E8066584B0B93A7A6C2FF37C0C2715CA1B0F5F63626C28710565436D094AE979F5BEB6BF5656575CB961F387E3C3928C8F39DB7075B5A9A5455361004C6308C582C888818386B56FFDCDC0A5B85E9BE7DF1B15732962EDDBF7CD9F8A03E1E4F6A68CECC2CBB78319DC727700C9E3B77B3B6B691C0C9F7DF1F6E98BCBA757340089124BE775FFCED5BC500C0C6465DB3F8AB1C6298BFBFEB33BC138665174545358B3FD9A3D5EA710CE3F1889B378B32330B00C000A0870EF57B3B62100280A6186B6BE9FCF9C3FEB5EDA34682A556EBCF9CB921B7B1F43376551D41600040433DBC95B36774F4EDBD7BE3081C9F376FD8A449BD0100A7CFA655572B5916F9FA387DB2788CADADCCC5C57AD0A0CE62B160C7CED8F3E7D3040252A77F63E8D02E4FB00D744E4E058E6124892526E64008F9027E40C0FDE6748984EFEFEF76E9527A74F42D1CC72C2DCD9B2E363BBB3C3FBF82249FF12299C4C4DC8DBF5F00009C3B7753D9A00680060011A470CA94010201A956EB264FEE1D12D2BBF998807FB18C014BAF672E5FBEA35098D134635C7EAFAD516E6565C3B6ED317C1E1112E2DFAF5F47C37FE6E5555656D40100FDFDDD6D6D6586790A42B87CF9788641BBF7C49D3C798DE4E134CD8C18D1FDB186932471AD8E42C890B9417A3D6DE8660100C864E2007FF77367FF929A4AF47A5A662E1E34A8B3E1504646697656B18DE2D93412EEDF179F9C922712F1F30BAAB66F8B06008318A9B095F9FA38EBF4348E634B96041B7CBB8772D1FFDAF539C68305211008C8A622801119ACDCDC0A005B65C6699AD97F20213BAB3423A32C788CEFEAD5D30D8D316565F772732B7082100A49A954D834CA18061102CB968D85101D3B2E3872249924F08CCCD249137BDBDBFF23C87FA8044451ACA38365F3A959A130138A04082196655D5CAC9B72D32489B7B396151B9B999098CB23711E8F387932F5E4C938000847275BFF804E0CC3EA75B487A72272D35CB198CFB2A8C932FD6FED6F637C2F80D1C9988A8AFAE8E85B10B6EAA70B0B6B56AD8A4ABD7E7BFA8CE16BD7CC94480486F155ABF5E7CEDD4408D9D8988584F8355F6A0121E4F3C98F3F1EF9D6EC0133676D387C38E9F889EB34C5BABA5A4F98D0ABC9A1494D2D60118B10BA5F1752EB478DEED9BC0D3A3434E08715272A2AEAF53A7AE68C7E4DCD8C06CFC688BAECA9537FD5D5A9F97CE272CC9DB56BA20C05168224DD3D5C346AFD471F8E88881864304B34CD88447C1CC75E4685FA258385C68CF636CE1AE338666A2A52A9742D7FACB1519B9C9C9F94949B9757D9A1438729A101A6A6A207191D3637B78224313E9F1489F80201F9E8899899890502DEE6CD116FBDF5FBCD5BC55F7D7DD8DBDB5928E4B12C1A34A89350C83B7BF64655558395951421A0D5EA01048FFA7C069B0100D0373B545DAD04000885BCD6182DC32A2608009F4FFCBCFAF4F93FD3481E0FC3303399A94422A069966598EFBE9DDCA78F2784D088D2D6AB0716DCBEE34A44C44023FED2D5553E7A74CF8D1BCEB45C33CFCBAB7CE7DDCD3939A50B3E183967CE400B0B9366F927ECE8B194D2921A1F1FB74D9BE63EA96B5420203B74B0F8F5D7591806F30BAAE7CED9347EC2CFAE2ED6EBD6CF969A0859162196EADFDF8BA1D963C7531EFD733333D13B6F0F5EB0601B00FF20E8D7DFCE43C84E18EFDBF28E3AE5E5F5A5A5F71A1A34111191E5E5753C1E012134918A711CA328462E37DDB6F51D8944C0B2ACA5A5D4C6C60CBC5A32CEC78208A1D96FF6376ED697C9C442210F00A852E91EBBB5415D9D5AA9D41E3B969299516C6B671112E2E7E565D7FC034AA586A1598410CDB21D3B2A5A283FF37844972E1D00009696D2DDBBE75314F3C182ED2347AE10087818067182072168A1121C1616F8DBBA3F33EEE4364D7C34CD68B57A1B854568A87F93056D2E8D862A2FBF4792C4F5EBF9116F474208351A8A20709D8E0200E8B454DF7E5E3F7C1F4A514CE7CEF6FFCE1D885E1A580CC34208ADACA4466F7562C8ACEEDB7B6DD4C89E5656264DEE0BC3B07575EACCCCD23973379597D57FBA647C6080FB43540100D2D34B4F9F492348DEA490DE0CD32A5FC7DA5A6A6DDD49A3D17FFF5D28453365A5759F7F719020F0B97306FDF4D3A927FD95999968FFBE0FB45A7D934379ECF8F5DBB70AACAC658E8E56FF4C8880DA5A2541E0B5B58D53A7FD5654748FC7C3552A3D45D13AADCEC9D9E6BF2BC3311C221689C5FCC0663DA51C587FEBC0C1848AF24AA3A96259347080D7CE9D57121373DF7AEBF7C8C8390A85AC29B7F4D1C73BAE5ECDCECDB9EBEAE630353CD0606F1EF906B6BE5ECDB2C8DD5DDEA6D51342216FDC385F004052529EC14392CBA56FCEEE1F7DE1F6631BCB701CEBDAF51F27A0D5525AAD7AF4E841869C3E45310F8C2EFAECF383172EA44B24FCBCBCAADA1A25002C004C6F7FAF88B9830080A346F578955CA8E7025645459D56AB351A2C0C8341419E4B3E1DBB7ACDD9ABD7B2E6CC8D640D3715020CC32E5FBED3D3DB69D6AC69E632F1938A2408018D46CF321AAD9632D6645206C421849D1EB1882D18DA43071300801E1E0AC36E1CD5D50DF3E66FADAD6D14897859D9E57939E5003000802953FA75EDD641ABA51C3A58CC9E3D00BC7E32062C82C08D58BAFEEDB7DF6A349A6FBEF90600606B2B5BB0E08DF4F4929D3B2FFD792EB5698E3531319D362D2828C873F69BFD5B482E8BC5BC397306EA7574F305D346CBB08CA99509BCEBA905B67636DDBB3ADCB953F2F5D74720843131776A6BEA0D0E7ECF9EEE030674AC6FD08E1FE7DBB45DA0D16BE35E3BB0288A01806DFD7A98D8D8D8B8B8B80D1B36848787379B95F81326F8DDBBA71608EF3B5834C5585A9AFCF8E3D426A7F849F7A36BD70EEB7E7BD3B864D293F327E8A9845DBD96AD546ACCCD4DCE9CBD515DAD3C723459AFA30000030775B3B692AA54BADEBDDD0C6DD6CD1F89D7902A23C17274B4E4F1844949F9C387B76A0FA3828282356BD64C9C3871C58A15CD9FFE51A37A8C1FEFFB5058D0BAC9D4F826FFC7FA7C7A9D1600BC63C7A72CC28E8FCFAEAD69D06AA9952B8F0200319C7070B4B2B797BDF3F6E0C993FD0D8F9C2155FB7AC2D45EB0268CEF1519792972F3852E5DECEDEDCD7D7C9C5A1EC769D3A6D9D8D8D8D9D93D9A0B78D4597EC1D7CFB2482CE6FBF6F2B0B0904C9BDAE7491F4B4D2D6868D09414D7420C1789F89E9EB60CCD68B4FABE7DBD7EFB75665364FAFC5646BC1660492482D9B3FBCF7EF3B7F1E37F58B172666B96C70C1932E4DF79FD1A0DD5AD9B4352E2D74FB297E9E925188E2D5ABC3BFAFC75A1C88424894E9DEC76EF9A6F08F1689A6DDA3A86537BC1322415F97CA27317E7A9E17D5E99B1686E2F753AAAB2B20101103A654D7A7AA95C2EB5965B197A1FC2C302E572D3E7B70CE1F5050B00307040E7D0D0BE4B968CB5B010BF6223A2D1E819862D2EBE376CF8F73A1DA5D7D300B1AB564D4D4B2BFCEFCA237DFA761D31A20747D5F3024B2E972E5E3CC6CD4DFE6F6E626CAB0C3B642427E74D9DF61B9FCF2B2BBB4753DA7EFDBB6D8E8CB0B393A5A61620844F9B1A646727E3B8795E60F17844E7CE76AFCC281842B97DFBE257AE3C01202CBA5B21128BA34E2E120878A6A64237377969E9BD438712711C7770B0E4DEFBF51CC17A65F460A76EB06DFB95654BF71517170180162D0A1B3CB8F390215D9AF21A6BD69CCDCF2BEBDBAFCB43DD829C38B01E230C836666C275EBFE8CBB9AF5E79FB7ECED655F7CF951DD3DD5B871BE8615CF4D2A2CAC068096CB4DBB74E15E25C781F574B0B04D9117B76FBBE2E1A198362D68407FAFE0609F473F565858753BBD582C9174EA64C7D02C4E70532107D6132414F220000481FDFCD3E1C0C0AE3FAF9EEED7EB896BB98A8A6B6FDE28309349478FEA89E15CCA8A03EB714A48C8B97A35BBB6B6116210D12830A8FBFA756FB6BCC7645656398498A5A50941605C2E9403EB615DBA74A7BCBC2E2929EFA79F0ECBCC2D58165134B36CE9B896A9AAAB53AF597306C7D1C4897E4F2D2672FADBCD784DAEF3DE3D5564E4C5B0B09F7EDF74C1CADA3A6C4A804261A6D753AB7F39DD721A62FF81F8B4BFF25CDDEC264FEAFD5A75EA7116EBE909855BB78A0A0AABA3A36FF105623757EB6DDBDED5EBE9BA7A75C69D829693520CC36EDC780100307E7C2F4F4F05870B07D67D9595DDD3EB99A5CBF65FB992A1D3D1ABFE1BEEEDEDD4B9B37D4A4AFE9FE76E42EC291688A218966501401231DFE89DC0B8A9F0959246A3AFAB531F3D9A1C18F4454C4C8652A9FDE1FBD091237A04067AE038666363D6A76F47C43EA5B3F9E8B1A48C3B2500E046EFABC359AC574A3A1D959F57F5F6BB9B4B4A6A4B4B8A274E1A346278B7C1833B3B3E58E8ECE06021B79602C0B6D041A5546AD6AC39ABD551B676164DAF24E1F4FA8245D34C7A7AC9A245BB63AFDC56D85A7EF1C50C6F6FA73163BC1FF59F20C64B4ECE4F49C97BD216FEA5A57500B12347F4080870E35879BDC142202929EFBBEF8E5D8EC9080DEDE3E565BB6449F0A3ADAAE07E1B312C2DA9292EAE7D2C58172FDEA9AFAB57D85A4C0EF5377A73680EAC57449763EEAC597BF6E4C96B9E1D9DFFF39FB1DDBA393C76C105CB220F0F1BA954A86CD03C7665627DBD66DDBA3F1B94DA8913830203DC39505E6BE7FDECD9B48D1BCF1F3E94E81FD065FCF85E867CE663175C4008C68EF51589780880E494FCC781A5BE1A9F4D10FC51A37AB6E765411C58AF82AEC5E7ECD913336060E75D3BE72F5B3AAE05C7DCB081B6858589A10DEBD10FE4E75762105214CDB22C57C679DDC12249A2474FF73F22E72A1432B198DF32104E4E96C163BC114B21165556D6FFC3FDA2995F7E39A3546AECEC641C551C5860E68CBEFBF77DA0B09509854F7F698740C023499C20F969690527A3FE6A7E282E2EEBD4E9348944B0EEB737B944030716B0B7377777B769E57B841102C1C13E4E4E560030CDB7BD641876EDDAB314452304A45291B5B594A3E47507AB4D8210D8DB9BD30C8BE1E4A64D171B1BB50FCC5566D4C9542B2BE9FA75B39C9C2C394438B0DA2C1E8F1835B207CB82BF520BF6EFFFDB85D7E9B42A95CEDBDBA5F996A49C38B05A2B994C3C2534C0D1C19266D8F5EBCF03001213733FF8608BA999F4B3E5E3DBFFBED6D759F05FBE0FFDF3965AAD5FB478F7860DE725124148885F5E5EE59DF4A2F9F3DF983F7FA8858584E38303CB4821046262EECC99BBA9B8E49E4E4B2156DFA9B37372D2370201C1251AB8A9B05D2E7C509087A3A3A556AD0110D828ACFC7BBBA9D53A8EAA768AE0868061585B5B598F9E2E0080EEDD1D7EF965FA3379772D3715A2D77C08288A51A97486FA0F453152A9E045BE8994038B1327CEC7E2C481C589038B13270E2C4E1C589C38B038716A95FE7F000DDB967FBAF2719B0000000049454E44AE426082');
Insert into usersignature (userid, imagetype, imagetagno, imageunid, imagedata) values ('adminuserone', 'png', 80518641, '24d799573b244a1dac3ac10552792d41', '89504E470D0A1A0A0000000D49484452000000F0000000F008000000001B3EB64E000000017352474200AECE1CE9000000097048597300001F3800001F3801933F27F50000000774494D4507D9071C0B0512D391B99C0000128F4944415478DAEDDC777C95D5FD07F0EF33EECD4D724306D9212524618A8CE0CFC1125151568B601D45A88A760A4AE9506C5510075671408B83AA581C50AB154701C11F20201444F610146218258490759F71D6E7F7C74D8097A226C404E1779E172F5E2FC293FBE47DCFF99EF3FD9E73720DD0FFAFCB240DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600DD6600D3E1D604924098C8442DDBF897C97400091FADE838D067F90A7320C2222611329651309CB80B04C622AC6208271D681A5A55888A455B661F37EC7B03A85DBB64B268AD81443D23478E0AC0333D816957FFAEEB6752514B6150BA7B539BF7F511C11912283CEBA1666412267F5EB2BB705935BE5848573B466EF91A4C27E970F20E986495ADFF718B61BFA0D01AAFE70C1FBBB8345DD2EE9911934CDA3A51BD6AF5FBB6DE5A1ABC2716EACA5CCB3AE4B6F9FF7CFCDA1FE975D9E994A2460C32477D7E2F73EC9F9E50D094ED0FEDEC730A181D7F69F05E8C2E97B017026014070062C1B96D8E99132707CEFAFFA833DC001E65F11CC7870CF8930A500541D1C999B7C570558A37F2009482500280E5FC13FC92D0A00E0FCB70A701BFCFAD67DF58F760468F59F17761F37B295C99575BC8B18061966F7E0B6F5BC530BD1D818368469288B2286292D22E36483A04120AA7E65496C2B1356937569050FD8792BB5F98BA86DD5DAAF4B21A402B0FFFEBC8269A5AAB12DAC6A008003D1DEF295D7930A80E4FEEB45456F0BD6F018AA7783186451D5F497D3A6DE60F11AE3F86C0BD33201223F7BC2EF5216AD6EF4348C7812243911896A52C44F7E93941F1CEED0DD6246534E4BB6FBE20BC1F1C32DE2F12464CC09EF85A9605814FB63677BA4D1A9A54964C18A2D3F929E48A4BE3ED7CF1C3638870256C39FD780DEF046A135BE140020BC1307194008050FD8FACF8FE477316AF9F0EE2D9A5071F2FFAEEDD2BBB75443A0E1A364035A78DFEBBB078D4E233F68E084F635EAFE8A21CA4FB41B9F77489382B468D6FE1810911F73D2F2CEB0AC02228553A8F6EAFD0D1EED5813FBA36E4406C8089273BC8B28A50CD350445E2827A5F1894180381D7E6B7F4019C4E9A48330C8300C1F64DAD2B59A2C86437CFABEE12388078244441477BC858DBA772E44D4F83CCBB728C096CE35797B4101699F2C460D8A7628222BB60907ADF59FA65F962C9B3CF30B1A4A3A6FFA66DC05C9047CF7E575FD63606D49EE65A732D13734F15086B9E2FD80DDB2B7494613ACA0D41F5C8CAC5CB29BBC188267F0179C902A6C4D4464C8D307AE32920DBFE98B1965D2A20F42BE3D309111C1C4E9038718A30089A6065B213E23E2F36EC3A31DDA3E7DE04EC1BD07CDE658C1796785CD5BFFA20D0202369DC618EE9AF4F9EEE658C7666FC38FED3C8A2A895B069DC618CEEBBDEFA5087904400A90E4449228F2B5830F11492E895C45758120414210014A28822252448C88C8F3893C0F90202AFED053E953185AC840F4A6BA095A114044114920621E1149D9F061BCFE9D26B5E792375B8EEE448662019B601944CAA2F8AFAFAE94615924592CC1B05DC429655944369132C930B8619A44268920819B21551D0811B32C10BDB75F65DF921FACCD598F65325ED0241120A258830CCF0C52E9FEE2F2DCEEA94D560F57E1B35B5B1AC3D6D516AB9500C0FC6F2A02B8CFA30B25157B5C48400A0050AE501E2014007009B662D6B43907215C80019CA13FD9B79501BE02A4E4CEB1D7E38C475C080010805C757DE79CB48CDB4A9A6C8947BAD8FFFBD4C4A297B700AC464249008C89AFBB5F0010420A946F7EA8EF3DBB776E2A07F7010F00AAA2358EA710D936E9BC44BBFD1C00BE0F2885F712A9D7762827BAB87042FDEF0000F7C1E13B88CCED9014179F4F05339B6E4D4BB9E0B38B286BD8CB4700CE05F7BFA514640E00F6C1AF3A11158D6CDB7672397C1F40592900A518940B31EF4A3BB64B9235A91AE04039B06700C5BD54DBA49C9DF00809380725A0A4044A1FEF42178E5FB3A7133DD674605F02583E2ACD4ABB797155F42BDF743B5700DCA57FCE4A4B08C587632C6A355340E1B33B870D9C510607F081F23FB7A36E0F7E7C4BF81E171210C0AE3141EA05E5005C0280F28EAF217E76E7B5AFF9D1887A2C95AE5CEEE2C84581494D068E00951C287E7E74A6D561FC87EC08C025E4D716E01540CD9A47FA18A999E1F844FAC11D8369B8C2B6A77A252652FB7F700002958F668407CEAFC228E3011F102E507C874D6933E0FA1C80A866D1C8A86DE23F06698C0328A5DE3BC71CBA4D018FC7E42C6FC21856F00480E299FDE3E37B5CB7E408843AF107FACAB5E17785B1595D8676CDC84ACB9E88478D11FEAAA179943FB273CC9F385C17EEF456E6CD9B818D3D12E681437938342E2ED62EF8BC1A7080C827F3DE29C1F13E7DA03725DD0EA01A077E42BD97426047DFF075954DD7A5EB2213D872FF852DCCF47BF7023C3AB6FA1E00E9030AE022FA2E94CEE866840A7FBEEC8616F9ED6904D8A374ED9C4B8DD49BD71DB9C6BA1D508A4DCFA59F96416241AB56EBC118E08E355352128602950ED4A2D16D62B2FE0870C01700303781BA1F2E07146624B778494878B312B2DE419383C17C60CBC38312132E9B57156D80EA2A20E2F80084E01E1C0654BE3D3095BA4FDE54BD303DF1BCD48C6960D725772CA07EAF547B6278DC9D1CCA792A39F8AB754272755B68302081437751EE15D9F42C0450F358DB98B8D6D46DA5005780F450763525BECD018E0FBBD0840A052CEF65FFA6AA19C00080EAD23F74A3841BD74178223AC58ABA053D17BEFFF9D87C6A37F6A323F0065A45DDE9DAC328EB9C6C05072CE0807349F87E07ECD93CFAD156486057477A140CC09474FAD9EC94562B5081AABBB2E88287DE6C13FA1B1457C201E4FD26FD183500D46DC10ECB00ACBD868A3E69F8225E83C1BE80641CC09C4B3382EDA69646C753AF6ECF05559015F32ECEA2817F3D0CF005B96937E464FE2F76DD13A6F45FAE057CB9FBDCF8190A2BDAD2B51F2B70E0F198C2DD8E005EE942BF3AF070ECE03286CADF66983F59025C603E2104E05663FFE474CA7C033502D8D4256E5219DC0FAEA0FCE744338021040056ED62FF933D8CEC61EF33B008C0C014C08484BB676226E54E2D7120B1E52A1AF2645ADFEDABC7C650FA23FB00092CCA4C5D841DD7C50C580305E51EBD9246575543BED1912EDC864BE80F0ACE132DE26EDF056CCD093F5FAD2080AA292914E87948C2077F3521762DFC577B50FE33E5DF34667E875D5A7A0C901CD8724B02E54F3D742CB5F20550F36E4F3BB6DFDB158072F117A3E53BAB938303DB6711FDC683545C624AE0BC43783A94FD4FC095C0EB99A1D90CEEC28E74CE7C7CD4C69A0F7F52ACF5C3C380F7F794BC658074E04DC90AA7067FE1011CF8BDD507D54FE652F7D79CA6DD3D3CD6C2BC6ECFC741CDF37D635A8D9A5F060830C007764F6C4BB993F74593E5CDBD68BC7B60B065B6480D172E85C3001C1D10F36BAC1F645C5D062925CAAF327A97A0F481AE76D77725661B5D8A9D89B9F680F5A8012626F62E0180D269AD02FD72625F14F05C60A0397EDB1F5AD1887F0352347C83B6C1600580FB02CA1500FCCF6FCCA5B6F77E0A1EED5D5BC725D83DDF52902E7C0F0B53D3FF0D7CF2D89F9E1B12EC7D1815902E76B6C97E4E4D0AA52F802300BC9C9C3C1D7C424BEABC3882CA31317DF07A1E5DBF113E50D52F38069CA9CAA79203A39FCFCD590D1F2E0E9C6F8E1E1D6F0FDB0138806CAE51FA84FEFD97CEC1948B6779A8E1C0A23EF12D7EBBC36780F0E1E3EE60DB520901ECEB117C403206012C4BCF5CB7A163E8764880A3A48F31A242DD1FA4EB3608E050114DD9D08DAEDDEB7248AC6E6DCF96C0A14712E99ABDB342AD8B1514C3C1B4B4C260F2D82F004009A9848444D5921736D56D3A363518D8F56007B3FDA85540C5F482F8DC991B01093040940EB1463330B8989694BF0E50F00466247438743DF53F00C82A1599422DDF13735AC6DFB85102783B33FB851BE9C2B7A2D5E59C60EE06D46C1A976C5FBD0A0F53770F1E57FEFB6D888A5E3C545B5F021C38F2C60DE7F6FCD7510EB06F27371EEC83BF373439AECFB42F1E49A7C2D758B47010805A9693300FA8025B5B44130005A1809B4237BE9AD1F25FAA121058DC81C6576C6F4797EE0000FF77C6FF3CDCD27E4A0210701FA0C11C8B069179CB7F80A9E679FFF505FC55834354F872F4C1D129A9ECADEBD22867C86A4F02A239C08201BB27758C29B8283B66F042008209000AFC9960C72FA4E0F07E195FB8116E041258DB3163C2053137BF36FE89E54770780C9DBB61655FBBE83F8088A07C807DD9A0D0F09D602E04220F509BBBAF49A2BCC9FBA0D82C3A6717F8A67BDA052C1AB2179C09AE007160F3CC2B4D6A7DE55F2BDD7A063435BE812119F8D6E1210AFC783722B5A338189CF1E6602E1D60418BB4FB2494070EFFAE98A221296D170CB229FFB27123C3D9E35EE94C196F70D44061737EEA80AC947F48402AA530BF03919974E94B6500F8C28CC04F7F3BB46D6242BF6BC234B10C12F077BD36B6771B8AC9B86256891022DAE2DF7A02A1D10BA14122934BBB005E3CAB89A71099069122D324F7532AB029968AA7555D3ED220C32650D5EA38FE9973573721C2354B3F7290BBFD85EA0EF79D6FD784C9A092D2D8E2D221979A2C68C2202AFAE9E2F2825EFD3ADB30C83E77E0DCD906AC56E7DD9739EDA1873F2F645294EDD8E6524A9776BD87E693B424591649CBF8CED6B4BEA185231C3B6E8D3D67582773F04A4070195D9A29EE1E9A01E0D0DD66BBE7210109814DEDB233D23BEDAD7E3A353E29D60AB648A0A4AB175521C2C0E0FF3D180E16BC0B781C1C2E80439F1F8DD6674261CFBD7D7BDDF4F4B26A896D13E289A8458828F5E2094FAFA981E4AC76879EA96618A53D0E1C79C84CBA0F2FA4248F2CA9CDA915C48EF6C9F321BCE599F6B8EAE86A1EE43BC9093F4898E9A3F46771A194B8BC5E17FCF01901281F0AF09E0D84CC312E1C48700EA9A06A730B1F909055D5123EA442E5E481E7F7EADA63C8A4A547A180DA932DBE0348A59A102C8504245CE0E8D4B4843B22E073E232EF549080E050D85A90F22AE4F2FE34622F18A412703031258146EC85C0D1A963463FBDDBF76A8EBD6F12B32CEABAAD1EA98F60BEE7AADAAB39130FC184643540E5130981AB0E02F8EFAD31051FC0E7D1E9614376C212ACB9C2E8FA7EEDC90C40DE64875BCE608002BC48DDCA6DDDF522D184FAE47A5270C69A1D1C7D9482849856101EB40D80C0A65EE6585F38005C60CFF9C6D4A56328EB492052BBF058723185FAEC82070EC505B8E39F5076BAABFA0C5E5E8FE72AA59494CD0E964C445BA8ECE124F3E75BE0BA50108F27F7D80B01C0E5BCE20E0AE753FAD48ABAC3373E96E451CCDD1C1E7C009C03CA3F31495FB7FDDBE7D2BA306DFE2ECD98F43D09392397466C81001CF86A43EB968B01115DA65FF9C38450EE9347C13820A194C29336E52F878050BEFAF2A4595389BA1D8D6F04CBC6814F7D1EB60D326D72E73E5839EA8E73A2C78B64302F5C7E549ADC521611F59CBC31D2AEBF418612A649300CBE5E50FF1E242C18A61F3449C23C7E8422A4A4051EAEC791C0E63D3E7C6299A870E46F792D06AF832BA4037086A57979CB8510001C59139D8B3C0E40280885DD1D28F7FD63358D04C0DC2F4FEAF589E1D3D2A5B9845BC3E7FE20F8A3ADD18864F050797BA8E7411C1BCE3CF892038E8280E2124B8CF020C6203C404AEF4B992073010EAF7E83D6F1ABF962987B0C6F76B62F5F17ADD4245CAE666685671F063FD98940098E7B0C5A79E61C10FF32A006D83890BAAD041CCE192200365D42433F037C259C93EC2632F727D4ABF2CC0503CEF509F9F3004F00CC1702AB0650E737A21B60EC64DBA755DDE8373863C180BAB765CB972199807000C5671552E15448E68A9397A6DE2789F4CC990B16589613F87555DDC4C8364CC8A5AE73217D0140FAEE574775BFF81737ED3C73C19C5F6F9FB7B3965BB9E4F1F6611AF6B164603896397F65C639F8059CD30D3EE5C4C35EB8266E6C219944CEA76B96AC28E705578F2940808308274B0E6C61AB74939AFE74EA77FD9B69C74ED5AC28EEDDCB646CE7EAA59BCB2AADB63D475D6411514012298BA4F1E5F350864992C80C9FB160EB63A99E32CA4B3E8D4428A3EF909E1D6AD33EABEECF97C1067D2F7E69ED94C1818E8B3F5C03C3B2B2BBF5EDD5214467CA75EAC5C3B5F1BB0D65A75F94979F6440C13A43C04623CEE77A21880011914F8133E6B3044E19EC86880C288B840C9844CA3CFB5B18060932C83009CCB6CEFA1616CAB00D82C10DCB203A7362F8947BA2110C082250C03614C89474F677E933F3D29FD4A2C11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11AACC11A7C565DFF072DA0744527092D7A0000000049454E44AE426082');