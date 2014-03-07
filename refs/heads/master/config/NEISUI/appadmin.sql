drop database if exists appadmin ;
create database appadmin ;
use appadmin ;

create table  workflowinfo (
  id  INT PRIMARY KEY  not null AUTO_INCREMENT ,
  name  VarChar( 150)    ,
  displayname  VarChar( 150)    ,
  deleted  VarChar( 50) default'0',
  isVisual  BOOL    ,
  isCreatedFromUI  BOOL    ,
  visualizerid int,
  INDEX ( id)
) ENGINE =INNODB ;

create table EventType (
  id  INT PRIMARY KEY  not null AUTO_INCREMENT ,
  eventtype  VarChar( 50)    ,
  INDEX ( id)
) ENGINE =INNODB ;

create table NodeDetails (
  id  INT PRIMARY KEY  not null AUTO_INCREMENT ,
  Action  VarChar( 150)    ,
  formpattern  VarChar( 250)    ,
  pacakge  VarChar( 250)  ,
  deleted VarChar(50) default '0',
  nodeDescription  VarChar( 150)    ,
  cateogryid INT,
  isVisualizer  BOOL ,
  inputxml  VarChar( 250)    ,
  
  INDEX ( id)
) ENGINE =INNODB ;

create table NodeInfo (
  id  INT PRIMARY KEY  not null AUTO_INCREMENT ,
  formpatternid  VarChar( 255)    ,
  datapatternid  VarChar( 255)  ,
  userpatternid  VarChar( 255)    ,
  scenario  VarChar( 150)   ,
  info  VarChar( 50)    ,
  data  VarChar( 50)    ,
  nodename  VarChar( 150) ,
  nodeDetail  INT    ,
  nodetype VarChar( 1),
  INDEX ( id)
) ENGINE =INNODB ;

create table NodeEvents (
  id  INT PRIMARY KEY  not null AUTO_INCREMENT ,
  workflownodeid  INT   ,
  params  VarChar( 450)    ,
  eventtype  INT   ,
  INDEX ( id)
) ENGINE =INNODB ;

create table nodeconnector (
  id  INT PRIMARY KEY  not null AUTO_INCREMENT ,
  connectorname  VarChar( 50)    ,
  fromNodeId  INT   ,
  toNodeId  INT,
  workflowpatternid INT   ,
  INDEX ( id)
) ENGINE =INNODB ;

create table workflownodeinfo (
  id  INT PRIMARY KEY  not null AUTO_INCREMENT ,
  workflowid  INT   ,
  nodeid  INT  ,
  isStartNode  BOOL   ,
  status  VarChar( 150)  ,
  INDEX ( id)
) ENGINE =INNODB ;


create table toolcateogry(
  id  INT PRIMARY KEY  not null AUTO_INCREMENT ,
  category VarChar( 150)  ,
  INDEX ( id)
) ENGINE =INNODB ;
create index id_index0 ON nodeconnector (fromNodeId );
create index id_index1 ON nodeconnector (toNodeId );
create index id_index2 ON NodeEvents (workflownodeid );
create index id_index3 ON NodeEvents (eventtype );
create index id_index4 ON workflownodeinfo (workflowid );
create index id_index5 ON workflownodeinfo (nodeid );

alter table NodeInfo  add foreign key ( nodedetail )  references NodeDetails( id );
alter table workflowinfo  add foreign key ( visualizerid )  references NodeDetails( id );
alter table nodeconnector  add foreign key ( fromNodeId )  references NodeInfo( id );
alter table nodeconnector  add foreign key ( toNodeId )  references NodeInfo( id );
alter table nodeconnector  add foreign key ( workflowpatternid )  references workflowinfo( id );
alter table NodeEvents  add foreign key ( workflownodeid )  references Workflownodeinfo( id );
alter table NodeEvents  add foreign key ( eventtype )  references EventType( id );
alter table workflownodeinfo  add foreign key ( workflowid )  references workflowinfo( id );
alter table workflownodeinfo  add foreign key ( nodeid )  references NodeInfo( id );
alter table NodeInfo  add UNIQUE (nodename) ;
alter table nodeconnector  add UNIQUE ( connectorname ) ;
alter table NodeDetails  add foreign key ( cateogryid )  references toolcateogry( id );

CREATE TABLE  appadmin.dataobject (
  id int(11) NOT NULL auto_increment,
  workflownodeid int(11) default NULL,
  status varchar(255) default NULL,
  formpattern varchar(255) default NULL,
  datapattern varchar(255) default NULL,
  userpatterns varchar(255) default NULL,
  userid varchar(255) default NULL,
  uniqueid varchar(255) default NULL,
  nodeexecutionstatus varchar(1) default NULL,
    toolstartdate  DATE,
  toolstarttime  TIME   ,  
  PRIMARY KEY  (`id`),
  KEY `id` (`id`),
  KEY `id_index_workflownodeinfo_dataobject` (`workflownodeid`),
  CONSTRAINT `dataobject_ibfk_1` FOREIGN KEY (`workflownodeid`) REFERENCES `workflownodeinfo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE  appadmin.dataobjectmetadata (
  id int(11) NOT NULL auto_increment,
  dataobject int(11) default NULL,
  datakey varchar(255) default NULL,
  datavalue varchar(255) default NULL,
  datavalueblob varchar(25600) default NULL,
  PRIMARY KEY  (`id`),
  KEY `id` (`id`),
  KEY `id_index_dataobject_dataobjectmetadata` (`dataobject`),
  CONSTRAINT `dataobjectmetadata_ibfk_1` FOREIGN KEY (`dataobject`) REFERENCES `dataobject` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE  appadmin.errorinfo (
 id int(11) PRIMARY KEY NOT NULL auto_increment,
  dataobjectid int(11) default NULL,
  errormessage LongText,
  INDEX ( id)
) ENGINE =INNODB ;

alter table errorinfo  add foreign key ( dataobjectid )  references dataobject( id );
INSERT INTO EventType(id,eventtype) VALUES(3,'EnterNode');
INSERT INTO EventType(id,eventtype) VALUES(1,'OnNode');
INSERT INTO EventType(id,eventtype) VALUES(2,'ExitNode');



INSERT INTO toolcateogry(id,category) VALUES(1,'Artifact Detection');
INSERT INTO toolcateogry(id,category) VALUES(2,'Averaging');
INSERT INTO toolcateogry(id,category) VALUES(3,'Bad Channel Replacement');
INSERT INTO toolcateogry(id,category) VALUES(4,'Baseline Correction');
INSERT INTO toolcateogry(id,category) VALUES(5,'Clinical Segmentation');
INSERT INTO toolcateogry(id,category) VALUES(6,'Combine Files');
INSERT INTO toolcateogry(id,category) VALUES(7,'Difference Wave');
INSERT INTO toolcateogry(id,category) VALUES(8,'Event Wave');
INSERT INTO toolcateogry(id,category) VALUES(9,'File Export');
INSERT INTO toolcateogry(id,category) VALUES(10,'Filtering');
INSERT INTO toolcateogry(id,category) VALUES(11,'First Order Highpass Filtering');
INSERT INTO toolcateogry(id,category) VALUES(12,'Markup From File');
INSERT INTO toolcateogry(id,category) VALUES(13,'Montage Operations');
INSERT INTO toolcateogry(id,category) VALUES(14,'Ocular Artifact Removal');
INSERT INTO toolcateogry(id,category) VALUES(15,'Script');
INSERT INTO toolcateogry(id,category) VALUES(16,'Segementation');
INSERT INTO toolcateogry(id,category) VALUES(17,'Short Epoch Filtering');
INSERT INTO toolcateogry(id,category) VALUES(18,'Statistic Extraction');
INSERT INTO toolcateogry(id,category) VALUES(19,'t-test');
INSERT INTO toolcateogry(id,category) VALUES(20,'Wavelet');


