drop database if exists FORTIS ;create database FORTIS ;use FORTIS ;


create table INITIALEXAMINATION ( ID  BIGINT PRIMARY KEY  not null AUTO_INCREMENT , 
DOCTOR_EXAMINING  VarChar( 50)   , DATE  DATE   , TIME  TIME  , 
BIRTH_WEIGHT  DOUBLE   , CENTILE_BW  DOUBLE,  OFC  DOUBLE   ,CENTILE_OFC  DOUBLE ,  TEMP  DOUBLE   , EYE_RED_REFLEX  VarChar(50)     ,
 EARS  VarChar(50)     , HANDS  VarChar(50)     , UPPER_LIMBS  VarChar(50)     , TESTIS  VarChar(50)     , HIPS  VarChar(50)     , 
 LEGS  VarChar(50)   , FEET  VarChar(50)     , PALATE  VarChar(50)    , TONE  VarChar(50)     , MOUTH VarChar(50)     , 
 HEART  VarChar(50)     , LIVER  VarChar(50)     , SPLEEN VarChar(50)     , GENITALIA VarChar(50)     , 
 SPINE  VarChar(50)    , PROBLEMS_AT_ADMISSION  VarChar( 1000)    ,  INDEX ( ID) )  ;
 
create table NURSINGPROGRESSREPORT ( ID  BIGINT PRIMARY KEY  not null AUTO_INCREMENT , Name  VarChar( 50)    , 
UID  VarChar( 50)    , BirthWt  DOUBLE   , YesterdayWt  DOUBLE   , PresentWt  DOUBLE   , 
WeightChangeFromBirth  DOUBLE   , WeightChangeFromYesterday  DOUBLE   , 
Urine_ml_kg_hr  DOUBLE   , Time  BIGINT   , MethodId  BIGINT    , 
FeedType  VarChar( 50)    , Volume  DOUBLE   , Total  DOUBLE   , 
IVFluids  VarChar( 50)    , IV_Hr  DOUBLE   , AA_Hr  DOUBLE   , 
Lipid_Hr  DOUBLE   , IVTotal  DOUBLE   , Aspirate_Vomit  DOUBLE   , 
Urine  DOUBLE   , BO  VarChar( 50)    , Sao2  VarChar( 50)    , 
HR  VarChar( 50)    , RR  VarChar( 50)    , BP  VarChar( 50)    , 
FIO2  VarChar( 50)    , TempAxilla  VarChar( 50)    , TempIncBed  VarChar( 50)    , 
CPAP  VarChar( 50)    ,  INDEX ( ID) )  ;

create table AdmissionForm ( ID  BIGINT PRIMARY KEY  not null AUTO_INCREMENT , InfantName  VarChar( 50)    , 
BirthWeight  DOUBLE   , DOB  DATE   , Gestation  Varchar(50)   , EDD  DATE   , FromLMP  DATE   , FromConceptionDate  DATE   , 
DateOfUSS  DATE   , Week  BIGINT   , Days  BIGINT   , Obstetrician  VarChar( 50)    , TimeofBirth  TIME   ,
Sex  VarChar( 50)    , AdmittingDoctor  VarChar( 50)    ,  INDEX ( ID) )  ;

create table LOGIN ( ID  BIGINT PRIMARY KEY  not null AUTO_INCREMENT , UserName  VarChar( 50) , Code  VarChar( 50)  ,  INDEX ( ID) );

create table MasterMethod(ID  BIGINT PRIMARY KEY  not null AUTO_INCREMENT , MethodName VarChar( 50),  INDEX ( ID) )  ;

INSERT INTO MasterMethod(MethodName) VALUES('No Selection');
INSERT INTO MasterMethod(MethodName) VALUES('N/G');
INSERT INTO MasterMethod(MethodName) VALUES('Bottle');
INSERT INTO MasterMethod(MethodName) VALUES('Breast');

alter table NURSINGPROGRESSREPORT  add foreign key ( MethodId )  references MasterMethod( ID );
