CREATE DATABASE ass2;

USE ass2;
CREATE TABLE EMP(
	EMP_CODE char(5) primary key,
	EMP_NAME char(20),
	DEPT_CODE char(5),
	DESIG_CODE char(5),
	SEX char(1),
	ADDRESS char (25),
	CITY char (20),
	STATE char (20),
	PIN char (6),
	BASIC int,
	JN_DT Date
);

CREATE TABLE DESIGNATION(
	DESIG_CODE char(5) primary key,
	DESIG_DESC char(20)
);

CREATE TABLE DEPARTMENT(
	DEPT_CODE char(5) primary key,
	DEPT_NAME char(15)
)

