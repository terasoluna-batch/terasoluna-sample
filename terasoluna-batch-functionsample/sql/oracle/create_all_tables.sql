-- Table: job_control

DROP TABLE job_control;

CREATE TABLE job_control
(
  job_seq_id VARCHAR2(10) NOT NULL,
  job_app_cd VARCHAR2(10),
  job_arg_nm1 VARCHAR2(100),
  job_arg_nm2 VARCHAR2(100),
  job_arg_nm3 VARCHAR2(100),
  job_arg_nm4 VARCHAR2(100),
  job_arg_nm5 VARCHAR2(100),
  job_arg_nm6 VARCHAR2(100),
  job_arg_nm7 VARCHAR2(100),
  job_arg_nm8 VARCHAR2(100),
  job_arg_nm9 VARCHAR2(100),
  job_arg_nm10 VARCHAR2(100),
  job_arg_nm11 VARCHAR2(100),
  job_arg_nm12 VARCHAR2(100),
  job_arg_nm13 VARCHAR2(100),
  job_arg_nm14 VARCHAR2(100),
  job_arg_nm15 VARCHAR2(100),
  job_arg_nm16 VARCHAR2(100),
  job_arg_nm17 VARCHAR2(100),
  job_arg_nm18 VARCHAR2(100),
  job_arg_nm19 VARCHAR2(100),
  job_arg_nm20 VARCHAR2(100),
  blogic_app_status VARCHAR2(10),
  cur_app_status VARCHAR2(1),
  add_date_time timestamp,
  upd_date_time timestamp,
  CONSTRAINT pk_job_control PRIMARY KEY (job_seq_id)
);

-- Table: employee

DROP TABLE employee;

CREATE TABLE employee
(
  employee_id NUMBER NOT NULL,
  employee_family_name varchar(20),
  employee_first_name varchar(20),
  employee_age NUMBER,
  CONSTRAINT pk_employee_id PRIMARY KEY (employee_id)
);

-- Table: employee2

DROP TABLE employee2;

CREATE TABLE employee2
(
  employee_id NUMBER NOT NULL,
  employee_family_name varchar(20),
  employee_first_name varchar(20),
  employee_age NUMBER,
  CONSTRAINT pk_employee2_id PRIMARY KEY (employee_id)
);

-- Table: employee3

DROP TABLE employee3;

CREATE TABLE employee3
(
  employee_id NUMBER NOT NULL,
  employee_family_name varchar(20),
  employee_first_name varchar(20),
  employee_age NUMBER,
  CONSTRAINT pk_employee3_id PRIMARY KEY (employee_id)
);

-- TABLE: ZIP_CODE

DROP TABLE ZIP_CODE;

CREATE TABLE ZIP_CODE (
       GROUP_CODE               VARCHAR(10)  NOT NULL,
       OLD_ZIP_CODE             VARCHAR(5)   NOT NULL,
       ZIP_CODE                 VARCHAR(7)   NOT NULL,
       ADMIN_DIVISIONS_KANA     VARCHAR(400)  NOT NULL,
       MUNICIPAL_DISTRICT_KANA  VARCHAR(400)  NOT NULL,
       TOWN_REGION_KANA         VARCHAR(400)  NOT NULL,
       ADMIN_DIVISIONS          VARCHAR(200)  NOT NULL,
       MUNICIPAL_DISTRICT       VARCHAR(200)  NOT NULL,
       TOWN_REGION              VARCHAR(200)  NOT NULL,
       TOWN_REGION_FLAG1        VARCHAR(1)   NOT NULL,
       TOWN_REGION_FLAG2        VARCHAR(1)   NOT NULL,
       TOWN_REGION_FLAG3        VARCHAR(1)   NOT NULL,
       TOWN_REGION_FLAG4        VARCHAR(1)   NOT NULL,
       UPDATE_FLAG              VARCHAR(1)   NOT NULL,
       CHANGE_FLAG              VARCHAR(1)   NOT NULL
);

ALTER TABLE ZIP_CODE ADD 
    CONSTRAINT ZIP_CODE_PK1 PRIMARY KEY (
        GROUP_CODE,
        ZIP_CODE,
        ADMIN_DIVISIONS_KANA,
        MUNICIPAL_DISTRICT_KANA,
        TOWN_REGION_KANA,
        ADMIN_DIVISIONS,
        MUNICIPAL_DISTRICT,
        TOWN_REGION
    )
;
