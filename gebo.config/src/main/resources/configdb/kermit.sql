CREATE TABLE module_truse (
    id VARCHAR(40) NOT NULL PRIMARY KEY,
	moduleId VARCHAR(140) NOT NULL,
	handlerId VARCHAR(140) NOT NULL,
	specsCode VARCHAR(140) ,
	configNumbers INTEGER,
	infoType VARCHAR(40) ,
	moduleType VARCHAR(30),
	trafficAccounting double,
	trafficSampleStart TIMESTAMP,
	trafficUnity  VARCHAR(40) ,
	used CHAR(1),
	ts TIMESTAMP,
	tenantuuid VARCHAR(40)
);

CREATE TABLE module_use (
    moduleId VARCHAR(140) NOT NULL,
	handlerId VARCHAR(140) NOT NULL,
	specsCode VARCHAR(140) ,
	configNumbers INTEGER ,
	infoType VARCHAR(40) ,
	moduleType VARCHAR(30),
	used CHAR(1),
	ts TIMESTAMP,
	tenantuuid VARCHAR(40),
	PRIMARY KEY (moduleId,handlerId,specsCode,infoType)
);

CREATE table gebo_db_version(version varchar(30) NOT NULL);

CREATE TABLE gebo(tenant_entry varchar(10) NOT NULL PRIMARY KEY,
INSTALL_MODE VARCHAR(40),
SUBSCRIPTION_MODE VARCHAR(40),
tenantuuid varchar(40) ,
pubkey VARCHAR(6000),
privkey VARCHAR(6000),
pubkey2 VARCHAR(6000),
installation timestamp,
refreshed timestamp,
expiring timestamp,
reg_email varchar(150),
registered char(1));