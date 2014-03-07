drop database if exists neiswispicdb;
create database neiswispicdb;
use neiswispicdb;

create table Patient ( ID  INT PRIMARY KEY  not null AUTO_INCREMENT , Prefix  INT   , FirstName  VarChar( 50)    , MiddleName  VarChar( 50)    , LastName  VarChar( 50)    , Suffix  INT   , MedicalRecordNumber  VarChar(25)   , Address1  VarChar( 50)    , Address2  VarChar( 250)    , City  INT   , State  INT   , ZipCode  VarChar(25)   , EmailAddress  VarChar( 50)    , EmergencyContact  VarChar( 150)   , EmergencyRelationship  VarChar( 250)    , Sex  INT   , DateOfBirth  DATE   , PhoneNo  BIGINT   , MaritalStatus  INT   , HomeTel  BIGINT   , HomeTelExt  INT   , WorkTel  BIGINT   , WorkTelExt  INT   , Cellphone  BIGINT ,PatientAccNo BIGINT  , Physician	VarChar(50)	,	Deleted  Char( 1)    ,  INDEX ( ID) ) ENGINE =INNODB ;
create table PatientImportBraebon ( ID  INT PRIMARY KEY  not null AUTO_INCREMENT , NolisPatientID  INT   , BraebonPatientID  BIGINT   ,  INDEX ( ID) ) ENGINE =INNODB ;
create table PatientImportEpic ( ID  INT PRIMARY KEY  not null AUTO_INCREMENT , SourceID  INT   , DestID  INT   ,SourceTable varchar(50) ,DestTable varchar(50), INDEX ( ID) ) ENGINE =INNODB ;
create table PatientPhoto ( ID  INT PRIMARY KEY  not null AUTO_INCREMENT , PatientID  INT   , FrontView  LongText   , SideView  LongText   , ImageDocument  LongText   ,  INDEX ( ID) ) ENGINE =INNODB ;
create table PatientPhysical ( ID  INT PRIMARY KEY  not null AUTO_INCREMENT , PatientID  INT   , EntryDate  DATE   , Height  INT   , Weight  INT   ,  INDEX ( ID) ) ENGINE =INNODB ;
create table Sex ( id  INT primary key not null AUTO_INCREMENT , SexType  VarChar( 50)    ) ENGINE =INNODB ;
create table City ( id  INT  primary key not null AUTO_INCREMENT , CityName  VarChar( 50) , StateId INT   ) ENGINE =INNODB ;
create table State ( id  INT  primary key  not null AUTO_INCREMENT, StateName  VarChar( 50) , StateAlias  VarChar( 50)   ) ENGINE =INNODB ;
create table PatientPrefix ( id  INT  primary key not null AUTO_INCREMENT , Name  VarChar( 50)    ) ENGINE =INNODB ;
create table PatientSuffix ( id  INT  primary key  not null AUTO_INCREMENT, Name  VarChar( 50)    ) ENGINE =INNODB ;
create table MaritalStatus ( id  INT primary key not null AUTO_INCREMENT  , status  VarChar( 50)    ) ENGINE =INNODB ;

create table QBCategory ( id  INT PRIMARY KEY  not null AUTO_INCREMENT , CategoryName  VarChar( 50)    , deleted  Char( 1)    ,  INDEX ( id) ) ENGINE =INNODB ;
create table QBCategoryDetail( id  INT PRIMARY KEY  not null AUTO_INCREMENT , CategoryId  INT   , UserDefinedFieldId  INT   ,  INDEX ( id) ) ENGINE =INNODB ;
create table QBUserDefinedField( id  INT PRIMARY KEY  not null AUTO_INCREMENT , UserDefinedFieldName  VarChar( 50)    , ClassName  VarChar( 50)    , ClassFieldName  VarChar( 50)    , deleted  Char( 1)    ,  INDEX ( id) ) ENGINE =INNODB ;

create index id_index0 ON PatientPhysical (PatientID );
create index id_index101 ON PatientPhoto (PatientID );
create index id_index3 ON PatientImportBraebon (NolisPatientID );



create table Schedule ( 
	ID  INT PRIMARY KEY  not null AUTO_INCREMENT , 
	PatientID  INT   , 
	StudyType  INT   , 
	ProcedureType  INT , 
	ExportToAlice  INT  , 
	AppointmentStartDate  DATE   , 
	AppointmentStartTime  TIME   , 
	AppointmentEndDate  DATE   , 
	AppointmentEndTime  TIME   , 
	Cancelled  Char( 1)    , 
	visitNo  VarChar( 50) ,
	AssignPatientLoc VarChar( 50)  ,
	Assigntoroom  VarChar( 50) ,  
	Uploading Char( 1)	,
	workingDirectory LongText,
	INDEX ( ID) 
) ENGINE =INNODB ;

CREATE TABLE PatientAcquisition (
  ID int(11) NOT NULL auto_increment,
  ScheduleID int(11) default NULL,
  AcquisitionFilePath varchar(256) default NULL,
  AcquisitionFileType varchar(10) default NULL,
  PRIMARY KEY  (ID),
  KEY ID (ID),
  KEY patientacquisition_ibfk_1 (ScheduleID),
  CONSTRAINT patientacquisition_ibfk_1 FOREIGN KEY (ScheduleID) REFERENCES schedule (ID)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

CREATE TABLE  StorageQueue (
  ID int(11) NOT NULL auto_increment,
  ScheduleID int(11) default NULL,
  SourceFilePath varchar(256) default NULL,
  StorageOperation varchar(1) default NULL,
  PRIMARY KEY  (ID),
  KEY ID (ID),
  KEY storagequeue_ibfk_1 (ScheduleID),
  CONSTRAINT storagequeue_ibfk_1 FOREIGN KEY (ScheduleID) REFERENCES schedule (ID)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

create table StudyType ( Id  INT PRIMARY KEY  not null AUTO_INCREMENT , Type  VarChar( 50)    ,  INDEX ( Id) ) ENGINE =INNODB ;
create table CustomQuery ( queryId  INT PRIMARY KEY  not null AUTO_INCREMENT , CustomQueryName  VarChar( 50)    , deleted  Char( 1)    ,  INDEX ( queryId) ) ENGINE =INNODB ;
create table Logical ( logicalId  INT PRIMARY KEY  not null AUTO_INCREMENT , logicalOperator  VarChar( 50)    , deleted  Char( 1)    ,  INDEX ( logicalId) ) ENGINE =INNODB ;
create table CustomQueryDetail ( id  INT PRIMARY KEY  not null AUTO_INCREMENT , variableValue  VarChar( 50)    , firstRange  VarChar( 50)   , secondRange  VarChar( 50)   , queryId  INT   , categoryName  VarChar( 50)    , fieldName  VarChar( 50)    , logicalValue  VarChar( 50) , checkboxValue  VarChar( 25),  INDEX ( id) ) ENGINE =INNODB ;
create table ProcedureType ( ProcedureId  INT  primary key not null AUTO_INCREMENT , ProcedureName  VarChar( 50)    ) ENGINE =INNODB ;
create table TempQueryDetail ( id  INT PRIMARY KEY  not null AUTO_INCREMENT , variableValue  VarChar( 50)    , firstRange  VarChar( 50)   , secondRange  VarChar( 50)   ,categoryName  VarChar( 50)    , fieldName  VarChar( 50)    , logicalValue  VarChar( 50) , checkboxValue  VarChar( 25),  INDEX ( id) ) ENGINE =INNODB ;

CREATE TABLE  `neiswispicdb`.`PatientNK` (
  `NKId` int(11) NOT NULL auto_increment primary key,
  `patientID` int(11) default NULL,
  `FirstName` varchar(50) default NULL,
  `LastName` varchar(50) default NULL,
  Address1 varchar(100) default NULL,
 Address2 varchar(100) default NULL,
PhoneNo BIGINT, FOREIGN KEY (patientID) REFERENCES patient (ID)) ;


DROP TABLE IF EXISTS `neiswispicdb`.`patientimportepic`;
 CREATE TABLE  `neiswispicdb`.`patientimportepic` (
   `ID` int(11) NOT NULL auto_increment,
   `SourceID` int(11) default NULL,
   `DestID` int(11) default NULL,
   `SourceTable` varchar(45) default NULL,
   `DestTable` varchar(45) default NULL,
   PRIMARY KEY  (`ID`),
   KEY `ID` (`ID`),
   KEY `id_index2` USING BTREE (`DestID`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 
 DROP TABLE IF EXISTS `neiswispicdb`.`patientdemographic`;
 CREATE TABLE  `neiswispicdb`.`patientdemographic` (
   `id` int(11) NOT NULL auto_increment,
   `patientID` int(11) default NULL,
   `FacilityOrganizationName` varchar(50) default NULL,
   `FacilityOrganizationIDNum` int(20) default NULL,
   `PrimaryCarePhysicanId` int(20) default NULL,
   PRIMARY KEY  (`id`),
   KEY `patientID` (`patientID`),
   CONSTRAINT `patientdemographic_ibfk_1` FOREIGN KEY (`patientID`) REFERENCES `patient` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 
 DROP TABLE IF EXISTS `neiswispicdb`.`PrimaryCarePhysician`;
 CREATE TABLE  `neiswispicdb`.`PrimaryCarePhysician` (
   `id` int(11) NOT NULL auto_increment,  
   `PrimaryRefDocFName` varchar(50) default NULL,
   `PrimaryRefDocMName` varchar(50) default NULL,
   `PrimaryRefDocLName` varchar(50) default NULL,
   PRIMARY KEY  (`id`)
   
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 
create table roomtype 
( id  INT PRIMARY KEY  not null AUTO_INCREMENT , roomtype  VarChar( 50)  , deleted  VarChar( 50) , INDEX ( id) ) ENGINE =INNODB ;
create table room 
( id  INT PRIMARY KEY  not null AUTO_INCREMENT , 
RoomDescription  VarChar( 50)    , 
roomtype  INT   ,
roomcolor  VarChar( 50)    , 
basestation VarChar(150) , 
deleted  VarChar( 50)    , 
INDEX ( id) ) ENGINE =INNODB ;

create table StudyPatient1 ( 
	ID  INT PRIMARY KEY  not null AUTO_INCREMENT , 
	StudyID  INT   , 
	Height  FLOAT   , 
	Weight  FLOAT   , 
	BMI  FLOAT   , 
	Physician  VarChar( 50)    , 
	DateOfStudy  DATE    , 
	RecordingTech  VarChar( 50)    , 
	ScoredBy  VarChar( 50)    , 
	LightsOff  VarChar( 50)    , 
	LightsOn  VarChar( 50)    , 
	TotalRecordingTime  FLOAT   , 
	TimeInBed  FLOAT   , 
	NumberOfAwakening  FLOAT   , 
	TotalSleepTime  FLOAT   , 
	WakeAfterSleepOnset  FLOAT   , 
	SleepEfficiency  FLOAT   , 
	SleepLatency  FLOAT   , 
	REMLatency  FLOAT   , 
	WakeDuration  FLOAT   , 
	StageN1Duration  FLOAT   , 
	StageN1SleepTime  FLOAT   , 
	StageN2Duration  FLOAT   , 
	StageN2SleepTime  FLOAT   , 
	StageN3Duration  FLOAT   , 
	StageN3SleepTime  FLOAT   , 
	StageREMDuration  FLOAT   , 
	StageREMSleepTime  FLOAT   , 
	SpontaneousREM  FLOAT   , 
	SpontaneousNonREM  FLOAT   , 
	SpontaneousSleep  FLOAT   , 
	RespiratoryREM  FLOAT   , 
	RespiratoryNonREM  FLOAT   , 
	RespiratorySleep  FLOAT   , 
	RespDesatREM  FLOAT   , 
	RespDesatNonREM  FLOAT   , 
	RespDesatSleep  FLOAT   , 
	PLMREM  FLOAT   , 
	PLMNonREM  FLOAT   , 
	TotalREM  FLOAT   , 
	TotalNonREM  FLOAT   , 
	TotalSleep  FLOAT   , 
	ArousalIndex  FLOAT   , 
	MeanSPO2  FLOAT   , 
	SPO2TimeBelow90  FLOAT   , 
	SPO2TimeBelow80  FLOAT   , 
	SPO2TimeBelow70  FLOAT   , 
	SPO2Nadir  FLOAT   , 
	MeanHeartRateWake  FLOAT   , 
	MeanHeartRateNonREM  FLOAT   , 
	MeanHeartRateREM  FLOAT   , 
	RespNoOfEventsObstructive  FLOAT   , 
	RespNoOfEventsMixed  FLOAT   , 
	RespNoOfEventsCentral  FLOAT   , 
	RespNoOfEventsHypopnea  FLOAT   , 
	RespEventIndexObstructive  FLOAT   , 
	RespEventIndexMixed  FLOAT   , 
	RespEventIndexCentral  FLOAT   , 
	RespEventIndexHypopnea  FLOAT   , 
	RespEventMeanDurationApnea  FLOAT   , 
	RespEventMeanDurationHypopnea  FLOAT   , 
	RespEventMaxDurationApnea  FLOAT   , 
	RespEventMaxDurationHypopnea  FLOAT   , 
	RespNoOfEventsApnea  FLOAT   , 
	RespEventIndexApnea  FLOAT   , 
	RespNoOfEventsApneaREM  FLOAT   , 
	RespNoOfEventsApneaNonREM  FLOAT   , 
	RespNoOfEventsHypopneaREM  FLOAT   , 
	RespNoOfEventsHypopneaNonREM  FLOAT   , 
	RespNoOfEventsReraREM  FLOAT   , 
	RespNoOfEventsReraNonREM  FLOAT   , 
	RespNoOfEventsRera  FLOAT   , 
	RespNoOfEventsApneaHypopneaREM  FLOAT   , 
	RespNoOfEventsApneaHypopneaNonREM  FLOAT   , 
	RespNoOfEventsApneaHypopnea  FLOAT   , 
	RespEventAHIREM  FLOAT   , 
	RespEventAHINonREM  FLOAT   , 
	RespEventAHI  FLOAT   , 
	RespEventReraIndexREM  FLOAT   , 
	RespEventReraIndexNonREM  FLOAT   , 
	RespEventReraIndex  FLOAT   , 
	RespEventRDIREM  FLOAT   , 
	RespEventRDINonREM  FLOAT   , 
	RespEventRDI  FLOAT   ,  
	RespEventSupineObstructive  FLOAT   , 
	RespEventSupineMixed  FLOAT   , 
	RespEventSupineCentral  FLOAT   , 
	RespEventSupineHypopnea  FLOAT   , 
	RespEventSupineIndex  FLOAT   , 
	RespEventLeftObstructive  FLOAT   , 
	RespEventLeftMixed  FLOAT   , 
	RespEventLeftCentral  FLOAT   , 
	RespEventLeftHypopnea  FLOAT   , 
	RespEventLeftIndex  FLOAT   , 
	RespEventRightObstructive  FLOAT   , 
	RespEventRightMixed  FLOAT   , 
	RespEventRightCentral  FLOAT   , 
	RespEventRightHypopnea  FLOAT   , 
	RespEventRightIndex  FLOAT   , 
	RespEventProneObstructive  FLOAT   , 
	RespEventProneMixed  FLOAT   , 
	RespEventProneCentral  FLOAT   , 
	RespEventProneHypopnea  FLOAT   , 
	RespEventProneIndex  FLOAT   , 
	RespNoOfEventsReraSupine  FLOAT   , 
	RespNoOfEventsReraRight  FLOAT   , 
	RespNoOfEventsReraLeft  FLOAT   , 
	RespNoOfEventsReraProne  FLOAT   , 
	RespEventReraIndexSupine  FLOAT   , 
	RespEventReraIndexRight  FLOAT   , 
	RespEventReraIndexLeft  FLOAT   , 
	RespEventReraIndexProne  FLOAT   , 
	NoOfPeriodicMovements  FLOAT   , 
	PLMIndex  FLOAT   , 
	NoOfPeriodicMovementsArousal  FLOAT   , 
	PLMAIIndex  FLOAT   ,  
	INDEX ( ID) 
) ENGINE =INNODB ;

create table StudyPatient2 ( 
	ID  INT PRIMARY KEY  not null AUTO_INCREMENT , 
	StudyID  INT   , 
	DateOfAcquisition  DATE    , 
	LightsOff1  VarChar( 50)    , 
	LightsOff2  VarChar( 50)    , 
	LightsOff3  VarChar( 50)    , 
	LightsOff4  VarChar( 50)    , 
	LightsOff5  VarChar( 50)    , 
	SleepOnset1  VarChar( 50)    , 
	SleepOnset2  VarChar( 50)    , 
	SleepOnset3  VarChar( 50)    , 
	SleepOnset4  VarChar( 50)    , 
	SleepOnset5  VarChar( 50)    , 
	LightsOn1  VarChar( 50)    , 
	LightsOn2  VarChar( 50)    , 
	LightsOn3  VarChar( 50)    , 
	LightsOn4  VarChar( 50)    , 
	LightsOn5  VarChar( 50)    , 
	TimeInBed1  FLOAT   , 
	TimeInBed2  FLOAT   , 
	TimeInBed3  FLOAT   , 
	TimeInBed4  FLOAT   , 
	TimeInBed5  FLOAT   , 
	TotalSleepTime1  FLOAT   , 
	TotalSleepTime2  FLOAT   , 
	TotalSleepTime3  FLOAT   , 
	TotalSleepTime4  FLOAT   , 
	TotalSleepTime5  FLOAT   , 
	SleepLatency1  FLOAT   , 
	SleepLatency2  FLOAT   , 
	SleepLatency3  FLOAT   , 
	SleepLatency4  FLOAT   , 
	SleepLatency5  FLOAT   , 
	AvgSleepLatency  FLOAT   , 
	REMLatency1  FLOAT   , 
	REMLatency2  FLOAT   , 
	REMLatency3  FLOAT   , 
	REMLatency4  FLOAT   , 
	REMLatency5  FLOAT   , 
	AvgREMLatency  FLOAT   , 
	INDEX ( ID) 
) ENGINE =INNODB ; 

create table StudyPatient ( 
	ID  INT PRIMARY KEY  not null AUTO_INCREMENT , 
	PatientID  INT   , 
	ReportType  Char( 1)    , 
	ScheduleID  INT   , 
	ReportPath  VarChar( 256)    , 
	ReportSigned  Char( 1)    ,
	UploadDate	DATE	,
	TechNote 	Char( 1)	,
	INDEX ( ID) 
) ENGINE =INNODB ;

create table TemplateType ( 
	TemplateName  VarChar( 50)    , 
	TemplateId  INT PRIMARY KEY  not null AUTO_INCREMENT ,  
	INDEX ( TemplateId) 
) ENGINE =INNODB ;

create table PatientAlbum ( 
	Photo  LongText    , 
	PatientID INT,
	StudyID INT,
	PhotoId  INT PRIMARY KEY  not null AUTO_INCREMENT ,  
	INDEX ( PhotoId) 
) ENGINE =INNODB ;

create table TechnicianNote ( id INT NOT NULL AUTO_INCREMENT primary key, studyId int,SleepLatency double,EEG DOUBLE ,
Snoring DOUBLE,Plms double,Arousals double,RespiratoryEvent  VarChar( 100),PatientTreatedWith LongText,
Pressures LongText,CflexBiflex VarChar( 100),SleepApneaHandOut VarChar( 50) ,ChinStrap VarChar( 100),GeneralComments LongText,ETCo2PreValue  VarChar( 50),
ETCo2PostValue  VarChar( 50),NoOfBreaths int(4),ApneaLength int(4), EKG DOUBLE, Proceduretype varchar(50), Leak varchar(50),EPOCHNO int, FOREIGN KEY (studyId) REFERENCES StudyPatient (id));

create table MedicationConsume(id INT NOT NULL AUTO_INCREMENT primary key,TechNoteId int,time varchar(100),NameDescription varchar(500),Dose varchar(100),FOREIGN KEY (TechNoteId) REFERENCES TechnicianNote (id));
 
create index id_index1300 ON Schedule (PatientID );

create table roomassginment ( id  INT PRIMARY KEY  not null AUTO_INCREMENT , roomid  INT   , 
schduleid  INT   , physicianid  VarChar( 50)    , sleeptechid  VarChar( 50)    ,
researchid  VarChar( 50)    , researchid2 VarChar( 50), deleted  VarChar( 50)    ,	comments LongText,	stat char,	arrived char,
INDEX ( id) ) ENGINE =INNODB ;


DROP TABLE IF EXISTS `neiswispicdb`.`Schedular`;
 CREATE TABLE  `neiswispicdb`.`Schedular` (
   `ID` int(11) NOT NULL auto_increment,  
   `Method` varchar(50) default NULL,
   `Description` varchar(256) default NULL,
   `ClassName` varchar(100) default NULL,
   `Component` varchar(100) default NULL,
   PRIMARY KEY  (`ID`)
   
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `neiswispicdb`.`SchedularTime`;
 CREATE TABLE  `neiswispicdb`.`SchedularTime` (
   `ID` int(11) NOT NULL auto_increment,  
   `SchedularID` int(11) default NULL,
   `ScheduleTime` varchar(50) default NULL,
   PRIMARY KEY  (`ID`)
   
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE  StudyDiagnosticCode (
	ID	INT PRIMARY KEY  not null AUTO_INCREMENT ,
    StudyID	INT	,
    DiagnosticCode	VarChar(50),
	INDEX ( ID) 
) ENGINE =INNODB ;


create index id_index12 ON roomassginment (roomid );
create index id_index14 ON roomassginment (schduleid );



alter table SchedularTime  add foreign key ( SchedularID )  references Schedular( ID );
alter table PatientPhoto  add foreign key ( PatientID )  references Patient( ID );
alter table QBCategoryDetail  add foreign key ( CategoryId )  references QBCategory( id );
alter table Schedule  add foreign key ( PatientID )  references Patient( ID );
alter table Schedule  add foreign key ( StudyType )  references StudyType( Id);
alter table Schedule  add foreign key ( ProcedureType )  references ProcedureType( ProcedureId);
alter table CustomQueryDetail  add foreign key ( queryId )  references CustomQuery( queryId );
alter table roomassginment  add foreign key ( roomid )  references room( id );
alter table roomassginment  add foreign key ( schduleid )  references Schedule( ID );

alter table StudyPatient  add foreign key ( PatientID )  references Patient( ID );
alter table StudyPatient1  add foreign key ( StudyID )  references StudyPatient( ID );
alter table StudyPatient2  add foreign key ( StudyID )  references StudyPatient( ID );
alter table PatientAlbum  add foreign key ( PatientID )  references Patient( ID );
alter table PatientAlbum  add foreign key ( StudyID )  references StudyPatient( ID );
alter table Patient  add foreign key ( state )  references state( id );
alter table Patient  add foreign key ( city )  references city( id );

alter table StudyDiagnosticCode  add foreign key ( StudyID )  references StudyPatient( ID );

INSERT INTO StudyType(Id,Type) VALUES(1,'Actigraphy - 0089T');
INSERT INTO StudyType(Id,Type) VALUES(2,'MSLT');
INSERT INTO StudyType(Id,Type) VALUES(3,'MWT');
INSERT INTO StudyType(Id,Type) VALUES(4,'unassigned');
INSERT INTO StudyType(Id,Type) VALUES(5,'PSG, Standard');
INSERT INTO StudyType(Id,Type) VALUES(6,'PSG, ASV');
INSERT INTO StudyType(Id,Type) VALUES(7,'PSG, AVAPS');
INSERT INTO StudyType(Id,Type) VALUES(8,'PSG, BiLevel');
INSERT INTO StudyType(Id,Type) VALUES(9,'PSG, CPAP');
INSERT INTO StudyType(Id,Type) VALUES(10,'PSG, Pediatric');
INSERT INTO StudyType(Id,Type) VALUES(11,'PSG, Pediatric, PAP');
INSERT INTO StudyType(Id,Type) VALUES(12,'PSG, Split');
INSERT INTO StudyType(Id,Type) VALUES(13,'PSG, Split Pre CPAP');
INSERT INTO StudyType(Id,Type) VALUES(14,'HD-PSG');
INSERT INTO StudyType(Id,Type) VALUES(15,'HD-PSG, Pediatric');
INSERT INTO StudyType(Id,Type) VALUES(16,'HD-EEG, Pediatric');
INSERT INTO StudyType(Id,Type) VALUES(17,'HD-EEG');

INSERT INTO maritalstatus(status) VALUES('Married');
INSERT INTO maritalstatus(status) VALUES('Divorce');
INSERT INTO maritalstatus(status) VALUES('Single');
INSERT INTO maritalstatus(status) VALUES('Widow');

INSERT INTO sex(SexType) VALUES('M');
INSERT INTO sex(SexType) VALUES('F');

INSERT INTO state(StateName,StateAlias) VALUES('Alabama','AL');
INSERT INTO state(StateName,StateAlias) VALUES('Alaska','AK');
INSERT INTO state(StateName,StateAlias) VALUES('Arizona','AZ');
INSERT INTO state(StateName,StateAlias) VALUES('Arkansas','AR');
INSERT INTO state(StateName,StateAlias) VALUES('California','CA');
INSERT INTO state(StateName,StateAlias) VALUES('Colorado','CO');
INSERT INTO state(StateName,StateAlias) VALUES('Connecticut','CT');
INSERT INTO state(StateName,StateAlias) VALUES('District of Columbia','DC');
INSERT INTO state(StateName,StateAlias) VALUES('Delaware','DE');
INSERT INTO state(StateName,StateAlias) VALUES('Florida','FL');
INSERT INTO state(StateName,StateAlias) VALUES('Georgia','GA');
INSERT INTO state(StateName,StateAlias) VALUES('Hawaii','HI');
INSERT INTO state(StateName,StateAlias) VALUES('Idaho','ID');
INSERT INTO state(StateName,StateAlias) VALUES('Illinois','IL');
INSERT INTO state(StateName,StateAlias) VALUES('Indiana','IN');
INSERT INTO state(StateName,StateAlias) VALUES('Iowa','IA');
INSERT INTO state(StateName,StateAlias) VALUES('Kansas','KS');
INSERT INTO state(StateName,StateAlias) VALUES('Kentucky','KY');
INSERT INTO state(StateName,StateAlias) VALUES('Louisiana','LA');
INSERT INTO state(StateName,StateAlias) VALUES('Maine','ME');
INSERT INTO state(StateName,StateAlias) VALUES('Maryland','MD');
INSERT INTO state(StateName,StateAlias) VALUES('Massachusetts','MA');
INSERT INTO state(StateName,StateAlias) VALUES('Michigan','MI');
INSERT INTO state(StateName,StateAlias) VALUES('Minnesota','MN');
INSERT INTO state(StateName,StateAlias) VALUES('Mississippi','MS');
INSERT INTO state(StateName,StateAlias) VALUES('Missouri','MO');
INSERT INTO state(StateName,StateAlias) VALUES('Montana','MT');
INSERT INTO state(StateName,StateAlias) VALUES('Nebraska','NE');
INSERT INTO state(StateName,StateAlias) VALUES('Nevada','NV');
INSERT INTO state(StateName,StateAlias) VALUES('New Hampshire','NH');
INSERT INTO state(StateName,StateAlias) VALUES('New Jersey','NJ');
INSERT INTO state(StateName,StateAlias) VALUES('New Mexico','NM');
INSERT INTO state(StateName,StateAlias) VALUES('New York','NY');
INSERT INTO state(StateName,StateAlias) VALUES('North Carolina','NC');
INSERT INTO state(StateName,StateAlias) VALUES('North Dakota','ND');
INSERT INTO state(StateName,StateAlias) VALUES('Ohio','OH');
INSERT INTO state(StateName,StateAlias) VALUES('Oklahoma','OK');
INSERT INTO state(StateName,StateAlias) VALUES('Oregon','OR');
INSERT INTO state(StateName,StateAlias) VALUES('Pennsylvania','PA');
INSERT INTO state(StateName,StateAlias) VALUES('Puerto Rico','PR');
INSERT INTO state(StateName,StateAlias) VALUES('Rhode Island','RI');
INSERT INTO state(StateName,StateAlias) VALUES('South Carolina','SC');
INSERT INTO state(StateName,StateAlias) VALUES('South Dakota','SD');
INSERT INTO state(StateName,StateAlias) VALUES('Tennessee','TN');
INSERT INTO state(StateName,StateAlias) VALUES('Texas','TX');
INSERT INTO state(StateName,StateAlias) VALUES('Utah','UT');
INSERT INTO state(StateName,StateAlias) VALUES('Vermont','VT');
INSERT INTO state(StateName,StateAlias) VALUES('Virginia','VA');
INSERT INTO state(StateName,StateAlias) VALUES('Washington','WA');
INSERT INTO state(StateName,StateAlias) VALUES('West Virginia','WV');
INSERT INTO state(StateName,StateAlias) VALUES('Wisconsin','WI');
INSERT INTO state(StateName,StateAlias) VALUES('Wyoming','WY');


INSERT INTO PatientPrefix(name) VALUES('Mr.');
INSERT INTO PatientPrefix(name) VALUES('Miss.');
INSERT INTO PatientPrefix(name) VALUES('Mrs.');
INSERT INTO PatientPrefix(name) VALUES('Ms.');
INSERT INTO PatientPrefix(name) VALUES('Dr.');
INSERT INTO PatientPrefix(name) VALUES('Rev.');

INSERT INTO PatientSuffix(name) VALUES('Jr');
INSERT INTO PatientSuffix(name) VALUES('II');
INSERT INTO PatientSuffix(name) VALUES('III');
INSERT INTO PatientSuffix(name) VALUES('IV');
INSERT INTO PatientSuffix(name) VALUES('Sr');

INSERT INTO roomtype(roomtype,deleted) VALUES('Clinical','0');
INSERT INTO roomtype(roomtype,deleted) VALUES('Research','0');

INSERT INTO ProcedureType(ProcedureName) VALUES('Clinical');
INSERT INTO ProcedureType(ProcedureName) VALUES('Research');

INSERT INTO logical(logicalOperator,deleted) VALUES('and','0');
INSERT INTO logical(logicalOperator,deleted) VALUES('or','0');

INSERT INTO TemplateType(TemplateName) VALUES('DIAG');
INSERT INTO TemplateType(TemplateName) VALUES('MSLT');
INSERT INTO TemplateType(TemplateName) VALUES('MWT');


Insert into City (CityName, StateID) values ('Abbeville', 1);
Insert into City (CityName, StateID) values ('Adamsville', 1);
Insert into City (CityName, StateID) values ('Addison', 1);
Insert into City (CityName, StateID) values ('Akron', 1);
Insert into City (CityName, StateID) values ('Clam Gulch', 2);
Insert into City (CityName, StateID) values ('Clarks Point', 2);
Insert into City (CityName, StateID) values ('Coffman Cove', 2);
Insert into City (CityName, StateID) values ('Cohoe', 2);
Insert into City (CityName, StateID) values ('Guadalupe', 3);
Insert into City (CityName, StateID) values ('Hayden', 3);
Insert into City (CityName, StateID) values ('Heber-Overgaard', 3);
Insert into City (CityName, StateID) values ('Holbrook', 3);
Insert into City (CityName, StateID) values ('Jonesboro', 4);
Insert into City (CityName, StateID) values ('Judsonia', 4);
Insert into City (CityName, StateID) values ('Junction City', 4);
Insert into City (CityName, StateID) values ('Big Pine', 5);
Insert into City (CityName, StateID) values ('Big River', 5);
Insert into City (CityName, StateID) values ('Dinosaur', 6);
Insert into City (CityName, StateID) values ('Dolores', 6);
Insert into City (CityName, StateID) values ('East Dunbar', 10);
Insert into City (CityName, StateID) values ('East Lake', 10);
Insert into City (CityName, StateID) values ('East Lake-Orient Park', 10);
Insert into City (CityName, StateID) values ('East Palatka', 10);
Insert into City (CityName, StateID) values ('Hollister', 13);
Insert into City (CityName, StateID) values ('Homedale', 13);
Insert into City (CityName, StateID) values ('Hope', 13);
Insert into City (CityName, StateID) values ('Hainesville', 14);
Insert into City (CityName, StateID) values ('Hamburg', 14);
Insert into City (CityName, StateID) values ('Markle', 15);
Insert into City (CityName, StateID) values ('Markleville', 15);
Insert into City (CityName, StateID) values ('Riverton', 16);
Insert into City (CityName, StateID) values ('Robins', 16);
Insert into City (CityName, StateID) values ('Rock Falls', 16);
Insert into City (CityName, StateID) values ('Auburn', 18);
Insert into City (CityName, StateID) values ('Audubon Park', 18);
Insert into City (CityName, StateID) values ('Oxon Hill-Glassmanor', 21);
Insert into City (CityName, StateID) values ('Paramount-Long Meadow', 21);
Insert into City (CityName, StateID) values ('Ravenna', 23);
Insert into City (CityName, StateID) values ('Reading', 23);
Insert into City (CityName, StateID) values ('Maplewood', 24);
Insert into City (CityName, StateID) values ('Marble', 24);
Insert into City (CityName, StateID) values ('Bucklin', 26);
Insert into City (CityName, StateID) values ('Buckner', 26);
Insert into City (CityName, StateID) values ('Cascade', 27);
Insert into City (CityName, StateID) values ('Charlo', 27);
Insert into City (CityName, StateID) values ('Bellevue', 28);
Insert into City (CityName, StateID) values ('Bellwood', 28);
Insert into City (CityName, StateID) values ('Beach Haven', 31);
Insert into City (CityName, StateID) values ('Beach Haven West', 31);
Insert into City (CityName, StateID) values ('Newcomb', 32);
Insert into City (CityName, StateID) values ('North Acomita Village', 32);
Insert into City (CityName, StateID) values ('St. Bonaventure', 33);
Insert into City (CityName, StateID) values ('St. James', 33);
Insert into City (CityName, StateID) values ('Mandan', 35);
Insert into City (CityName, StateID) values ('Mandaree', 35);
Insert into City (CityName, StateID) values ('Summerfield', 36);
Insert into City (CityName, StateID) values ('Summerside', 36);
Insert into City (CityName, StateID) values ('Lowell', 38);
Insert into City (CityName, StateID) values ('Lyons', 38);
Insert into City (CityName, StateID) values ('Newell', 39);
Insert into City (CityName, StateID) values ('New Florence', 39);
Insert into City (CityName, StateID) values ('Bethel Springs', 44);
Insert into City (CityName, StateID) values ('Big Sandy', 44);
Insert into City (CityName, StateID) values ('Kennard', 45);
Insert into City (CityName, StateID) values ('Kennedale', 45);
Insert into City (CityName, StateID) values ('Middlebury', 47);
Insert into City (CityName, StateID) values ('Milton', 47);
Insert into City (CityName, StateID) values ('Skokomish', 49);
Insert into City (CityName, StateID) values ('Skykomish', 49);
Insert into City (CityName, StateID) values ('Redgranite', 51);
Insert into City (CityName, StateID) values ('Reedsburg', 51);
Insert into City (CityName, StateID) values ('Reedsville', 51);
Insert into City (CityName, StateID) values ('Reeseville', 51);
Insert into City (CityName, StateID) values ('Reserve', 51);
Insert into City (CityName, StateID) values ('Rewey', 51);
Insert into City (CityName, StateID) values ('Rhinelander', 51);
Insert into City (CityName, StateID) values ('St. Nazianz', 51);
Insert into City (CityName, StateID) values ('Sauk City', 51);
Insert into City (CityName, StateID) values ('Saukville', 51);
Insert into City (CityName, StateID) values ('Waterford North', 51);
Insert into City (CityName, StateID) values ('Waterloo', 51);
Insert into City (CityName, StateID) values ('Isabela', 40);
Insert into City (CityName, StateID) values ('Jagual', 40);
Insert into City (CityName, StateID) values ('Jauca', 40);

Insert into Schedular (Method,Description,ClassName,Component) values ('ImportHL7Data','time after which the reports are read from in folder','com.oxymedical.component.HL7Component.HL7Component', 'com.oxymedical.component.HL7Component');

Insert into SchedularTime(SchedularID,ScheduleTime) values ('1','120');
