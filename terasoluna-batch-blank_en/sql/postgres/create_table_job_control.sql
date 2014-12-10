-- Table: job_control

DROP TABLE job_control;

CREATE TABLE job_control
(
  job_seq_id varchar(10) NOT NULL,
  job_app_cd varchar(10),
  job_arg_nm1 varchar(100),
  job_arg_nm2 varchar(100),
  job_arg_nm3 varchar(100),
  job_arg_nm4 varchar(100),
  job_arg_nm5 varchar(100),
  job_arg_nm6 varchar(100),
  job_arg_nm7 varchar(100),
  job_arg_nm8 varchar(100),
  job_arg_nm9 varchar(100),
  job_arg_nm10 varchar(100),
  job_arg_nm11 varchar(100),
  job_arg_nm12 varchar(100),
  job_arg_nm13 varchar(100),
  job_arg_nm14 varchar(100),
  job_arg_nm15 varchar(100),
  job_arg_nm16 varchar(100),
  job_arg_nm17 varchar(100),
  job_arg_nm18 varchar(100),
  job_arg_nm19 varchar(100),
  job_arg_nm20 varchar(100),
  blogic_app_status varchar(10),
  cur_app_status varchar(1),
  add_date_time timestamp,
  upd_date_time timestamp,
  CONSTRAINT pk_job_control PRIMARY KEY (job_seq_id)
);

