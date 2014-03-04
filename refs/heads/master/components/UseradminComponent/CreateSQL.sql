drop database useradmin;
CREATE DATABASE `useradmin`;

USE `useradmin`;

/*Table structure for table `account_` */

DROP TABLE IF EXISTS `account_`;

CREATE TABLE `account_` (
  `accountId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  `userId` varchar(100) NOT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `parentAccountId` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `active` */

DROP TABLE IF EXISTS `active`;

CREATE TABLE `active` (
  `id` varchar(100) NOT NULL,
  `value` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `addressId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  `userId` varchar(100) NOT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `street1` varchar(100) DEFAULT NULL,
  `className` varchar(100) DEFAULT NULL,
  `classPK` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `zip` varchar(100) DEFAULT NULL,
  `regionId` varchar(100) DEFAULT NULL,
  `countryId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`addressId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `admincity` */

DROP TABLE IF EXISTS `admincity`;

CREATE TABLE `admincity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cityname` varchar(100) DEFAULT NULL,
  `stateid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `cateogry` */

DROP TABLE IF EXISTS `cateogry`;

CREATE TABLE `cateogry` (
  `cateogryId` varchar(100) NOT NULL,
  `cateogryName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cateogryId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `company` */

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `companyId` varchar(100) NOT NULL,
  `homeURL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`companyId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `company_cont` */

DROP TABLE IF EXISTS `company_cont`;

CREATE TABLE `company_cont` (
  `userContId` int(11) NOT NULL AUTO_INCREMENT,
  `companyId` varchar(100) DEFAULT NULL,
  `containerId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userContId`),
  UNIQUE KEY `compContUnId_UN` (`companyId`,`containerId`),
  KEY `containerIdCCFK` (`containerId`),
  CONSTRAINT `companyId_FK` FOREIGN KEY (`companyId`) REFERENCES `company` (`companyId`),
  CONSTRAINT `containerIdCCFK` FOREIGN KEY (`containerId`) REFERENCES `container` (`containerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `component` */

DROP TABLE IF EXISTS `component`;

CREATE TABLE `component` (
  `componentId` varchar(100) NOT NULL DEFAULT '',
  `compName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`componentId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `cont_comp` */

DROP TABLE IF EXISTS `cont_comp`;

CREATE TABLE `cont_comp` (
  `contCompId` int(11) NOT NULL AUTO_INCREMENT,
  `containerId` varchar(100) DEFAULT NULL,
  `componentId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`contCompId`),
  UNIQUE KEY `contCompUnId_UN` (`containerId`,`componentId`),
  KEY `componentId_FK` (`componentId`),
  CONSTRAINT `componentId_FK` FOREIGN KEY (`componentId`) REFERENCES `component` (`componentId`),
  CONSTRAINT `containerId_FK` FOREIGN KEY (`containerId`) REFERENCES `container` (`containerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `contact_` */

DROP TABLE IF EXISTS `contact_`;

CREATE TABLE `contact_` (
  `contactId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  `userId` varchar(100) NOT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `accountId` varchar(100) DEFAULT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `middleName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `prefixId` varchar(100) DEFAULT NULL,
  `suffixId` varchar(100) DEFAULT NULL,
  `male` tinyint(4) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `jobTitle` varchar(100) DEFAULT NULL,
  `employeeNumber` varchar(100) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `Address1` varchar(100) DEFAULT NULL,
  `Address2` varchar(100) DEFAULT NULL,
  `zipCode` bigint(20) DEFAULT NULL,
  `telephoneNumber` bigint(20) DEFAULT NULL,
  `universalProviderIdentificationNumber` varchar(100) DEFAULT NULL,
  `nationalProvidedIdentificationNumber` varchar(100) DEFAULT NULL,
  `state` varchar(100) DEFAULT NULL,
  `city` int(11) DEFAULT NULL,
  PRIMARY KEY (`contactId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `container` */

DROP TABLE IF EXISTS `container`;

CREATE TABLE `container` (
  `containerId` varchar(100) NOT NULL DEFAULT '',
  `containerTypeId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`containerId`),
  KEY `containerTypeId_FK` (`containerTypeId`),
  CONSTRAINT `containerTypeId_FK` FOREIGN KEY (`containerTypeId`) REFERENCES `container_type` (`containerTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `container_type` */

DROP TABLE IF EXISTS `container_type`;

CREATE TABLE `container_type` (
  `containerTypeId` varchar(100) NOT NULL DEFAULT '',
  `containerTypeName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`containerTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `counter` */

DROP TABLE IF EXISTS `counter`;

CREATE TABLE `counter` (
  `name` varchar(100) NOT NULL,
  `currentId` int(11) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `country` */

DROP TABLE IF EXISTS `country`;

CREATE TABLE `country` (
  `countryId` int(11) DEFAULT NULL,
  `name` varchar(58) DEFAULT NULL,
  `active_` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `emailaddress` */

DROP TABLE IF EXISTS `emailaddress`;

CREATE TABLE `emailaddress` (
  `emailAddressId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  `userId` varchar(100) NOT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `className` varchar(100) DEFAULT NULL,
  `classPK` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `typeId` varchar(100) DEFAULT NULL,
  `primary_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`emailAddressId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `group_` */

DROP TABLE IF EXISTS `group_`;

CREATE TABLE `group_` (
  `groupId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  `parentGroupId` varchar(100) DEFAULT NULL,
  `className` varchar(100) DEFAULT NULL,
  `classPK` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` longtext,
  `type_` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `grouplead` */

DROP TABLE IF EXISTS `grouplead`;

CREATE TABLE `grouplead` (
  `groupId` varchar(100) NOT NULL,
  `userId` varchar(100) NOT NULL,
  PRIMARY KEY (`groupId`,`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `groups_orgs` */

DROP TABLE IF EXISTS `groups_orgs`;

CREATE TABLE `groups_orgs` (
  `groupId` varchar(100) NOT NULL,
  `organizationId` varchar(100) NOT NULL,
  PRIMARY KEY (`groupId`,`organizationId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `groups_permissions` */

DROP TABLE IF EXISTS `groups_permissions`;

CREATE TABLE `groups_permissions` (
  `groupId` varchar(100) NOT NULL,
  `permissionId` varchar(100) NOT NULL,
  PRIMARY KEY (`groupId`,`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `groups_roles` */

DROP TABLE IF EXISTS `groups_roles`;

CREATE TABLE `groups_roles` (
  `groupId` varchar(100) NOT NULL,
  `roleId` varchar(100) NOT NULL,
  PRIMARY KEY (`groupId`,`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `groups_usergroups` */

DROP TABLE IF EXISTS `groups_usergroups`;

CREATE TABLE `groups_usergroups` (
  `groupId` varchar(100) NOT NULL,
  `userGroupId` varchar(100) NOT NULL,
  PRIMARY KEY (`groupId`,`userGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `layout` */

DROP TABLE IF EXISTS `layout`;

CREATE TABLE `layout` (
  `layoutId` varchar(100) NOT NULL,
  `ownerId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  `parentLayoutId` varchar(100) DEFAULT NULL,
  `name` longtext,
  `title` longtext,
  `type_` varchar(100) DEFAULT NULL,
  `typeSettings` longtext,
  `hidden_` tinyint(4) DEFAULT NULL,
  `friendlyURL` varchar(100) DEFAULT NULL,
  `themeId` varchar(100) DEFAULT NULL,
  `colorSchemeId` varchar(100) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`layoutId`,`ownerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `layoutset` */

DROP TABLE IF EXISTS `layoutset`;

CREATE TABLE `layoutset` (
  `ownerId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  `groupId` varchar(100) NOT NULL,
  `userId` varchar(100) NOT NULL,
  `privateLayout` tinyint(4) DEFAULT NULL,
  `themeId` varchar(100) DEFAULT NULL,
  `colorSchemeId` varchar(100) DEFAULT NULL,
  `pageCount` int(11) DEFAULT NULL,
  `virtualHost` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ownerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `logindetails` */

DROP TABLE IF EXISTS `logindetails`;

CREATE TABLE `logindetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(25) DEFAULT NULL,
  `loginDate` varchar(25) DEFAULT NULL,
  `loginTime` varchar(25) DEFAULT NULL,
  `ipAddress` varchar(25) DEFAULT NULL,
  `logoutTime` varchar(25) DEFAULT NULL,
  `logout` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=841 DEFAULT CHARSET=latin1;

/*Table structure for table `logintracker` */

DROP TABLE IF EXISTS `logintracker`;

CREATE TABLE `logintracker` (
  `userId` varchar(200) NOT NULL,
  `counter` int(5) DEFAULT NULL,
  `logindate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `organization_` */

DROP TABLE IF EXISTS `organization_`;

CREATE TABLE `organization_` (
  `organizationId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  `parentOrganizationId` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `regionId` varchar(100) DEFAULT NULL,
  `countryId` varchar(100) DEFAULT NULL,
  `statusId` varchar(100) DEFAULT NULL,
  `comments` longtext,
  PRIMARY KEY (`organizationId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `orggrouppermission` */

DROP TABLE IF EXISTS `orggrouppermission`;

CREATE TABLE `orggrouppermission` (
  `organizationId` varchar(100) NOT NULL,
  `groupId` varchar(100) NOT NULL,
  `permissionId` varchar(100) NOT NULL,
  PRIMARY KEY (`organizationId`,`groupId`,`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `orggrouprole` */

DROP TABLE IF EXISTS `orggrouprole`;

CREATE TABLE `orggrouprole` (
  `organizationId` varchar(100) NOT NULL,
  `groupId` varchar(100) NOT NULL,
  `roleId` varchar(100) NOT NULL,
  PRIMARY KEY (`organizationId`,`groupId`,`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `passwordtracker` */

DROP TABLE IF EXISTS `passwordtracker`;

CREATE TABLE `passwordtracker` (
  `passwordTrackerId` varchar(100) NOT NULL,
  `userId` varchar(100) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `password_` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`passwordTrackerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `permission_` */

DROP TABLE IF EXISTS `permission_`;

CREATE TABLE `permission_` (
  `permissionId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  `actionId` varchar(100) DEFAULT NULL,
  `resourceId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `preffix` */

DROP TABLE IF EXISTS `preffix`;

CREATE TABLE `preffix` (
  `prefixId` varchar(100) NOT NULL,
  `prefixName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`prefixId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `region` */

DROP TABLE IF EXISTS `region`;

CREATE TABLE `region` (
  `regionId` varchar(100) NOT NULL,
  `countryId` varchar(100) DEFAULT NULL,
  `regionCode` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`regionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `rights_` */

DROP TABLE IF EXISTS `rights_`;

CREATE TABLE `rights_` (
  `rightId` varchar(100) NOT NULL DEFAULT '',
  `name` varchar(100) DEFAULT NULL,
  `companyId` varchar(100) NOT NULL,
  `languageproperties` varchar(100) DEFAULT NULL,
  `defaultURL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`rightId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `role_` */

DROP TABLE IF EXISTS `role_`;

CREATE TABLE `role_` (
  `roleId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `className` varchar(100) DEFAULT NULL,
  `classPK` varchar(100) DEFAULT NULL,
  `defaultURL` varchar(100) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `role_rights` */

DROP TABLE IF EXISTS `role_rights`;

CREATE TABLE `role_rights` (
  `rightId` varchar(100) NOT NULL,
  `roleId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  PRIMARY KEY (`roleId`,`rightId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `roles_permissions` */

DROP TABLE IF EXISTS `roles_permissions`;

CREATE TABLE `roles_permissions` (
  `roleId` varchar(100) NOT NULL,
  `permissionId` varchar(100) NOT NULL,
  PRIMARY KEY (`roleId`,`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `suffix` */

DROP TABLE IF EXISTS `suffix`;

CREATE TABLE `suffix` (
  `suffixId` varchar(100) NOT NULL,
  `suffixName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`suffixId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `user_` */

DROP TABLE IF EXISTS `user_`;

CREATE TABLE `user_` (
  `userId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  `contactId` varchar(100) DEFAULT NULL,
  `password_` varchar(100) DEFAULT NULL,
  `emailAddress` varchar(100) DEFAULT NULL,
  `languageId` varchar(100) DEFAULT NULL,
  `greeting` varchar(100) DEFAULT NULL,
  `comments` longtext,
  `active_` tinyint(4) DEFAULT NULL,
  `deleted` varchar(1) DEFAULT '0',
  `inActiveDate` date DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `userfields` */

DROP TABLE IF EXISTS `userfields`;

CREATE TABLE `userfields` (
  `fieldId` varchar(100) NOT NULL,
  `fieldName` varchar(100) NOT NULL,
  PRIMARY KEY (`fieldId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `userfieldsforusertype` */

DROP TABLE IF EXISTS `userfieldsforusertype`;

CREATE TABLE `userfieldsforusertype` (
  `userTypeId` varchar(100) NOT NULL,
  `userFieldId` varchar(100) NOT NULL,
  PRIMARY KEY (`userTypeId`,`userFieldId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `usergroup` */

DROP TABLE IF EXISTS `usergroup`;

CREATE TABLE `usergroup` (
  `userGroupId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  `parentUserGroupId` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`userGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `useridmapper` */

DROP TABLE IF EXISTS `useridmapper`;

CREATE TABLE `useridmapper` (
  `userId` varchar(100) NOT NULL,
  `type_` varchar(100) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `externalUserId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userId`,`type_`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `userpattern_roles` */

DROP TABLE IF EXISTS `userpattern_roles`;

CREATE TABLE `userpattern_roles` (
  `userPatternId` varchar(100) NOT NULL,
  `roleId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  PRIMARY KEY (`userPatternId`,`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `userpattern_users` */

DROP TABLE IF EXISTS `userpattern_users`;

CREATE TABLE `userpattern_users` (
  `userPatternId` varchar(100) NOT NULL,
  `userId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  PRIMARY KEY (`userPatternId`,`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `userpatterns` */

DROP TABLE IF EXISTS `userpatterns`;

CREATE TABLE `userpatterns` (
  `userPatternId` varchar(100) NOT NULL,
  `companyId` varchar(100) NOT NULL,
  `comments` varchar(100) DEFAULT NULL,
  `defaultFormPattern` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userPatternId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `users_groups` */

DROP TABLE IF EXISTS `users_groups`;

CREATE TABLE `users_groups` (
  `userId` varchar(100) NOT NULL,
  `groupId` varchar(100) NOT NULL,
  PRIMARY KEY (`userId`,`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `users_orgs` */

DROP TABLE IF EXISTS `users_orgs`;

CREATE TABLE `users_orgs` (
  `userId` varchar(100) NOT NULL,
  `organizationId` varchar(100) NOT NULL,
  PRIMARY KEY (`userId`,`organizationId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `users_permissions` */

DROP TABLE IF EXISTS `users_permissions`;

CREATE TABLE `users_permissions` (
  `userId` varchar(100) NOT NULL,
  `permissionId` varchar(100) NOT NULL,
  PRIMARY KEY (`userId`,`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `users_roles` */

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `userId` varchar(100) NOT NULL,
  `roleId` varchar(100) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `users_usergroups` */

DROP TABLE IF EXISTS `users_usergroups`;

CREATE TABLE `users_usergroups` (
  `userId` varchar(100) NOT NULL,
  `userGroupId` varchar(100) NOT NULL,
  PRIMARY KEY (`userId`,`userGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `usersignature` */

DROP TABLE IF EXISTS `usersignature`;

CREATE TABLE `usersignature` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(100) DEFAULT NULL,
  `imagetype` varchar(4) DEFAULT NULL,
  `imagedata` longtext,
  `imagetagno` int(8) DEFAULT NULL,
  `imageunid` varchar(32) DEFAULT NULL,
  `imagewidth` int(11) DEFAULT NULL,
  `imageheight` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `id_index1` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;



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



insert into Country (countryId, name,  active_) values ('51`', 'Bulgaria',1);
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
 
 insert into `user_` (`userId`, `companyId`, `contactId`, `password_`, `emailAddress`, `languageId`, `greeting`, `comments`, `active_`, `deleted`, `inActiveDate`) values('admin','SunPharma',NULL,'admin','emailaddress',NULL,'WelcomeAdmin',NULL,'1','0',NULL);