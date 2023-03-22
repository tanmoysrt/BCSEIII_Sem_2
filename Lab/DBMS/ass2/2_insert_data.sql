USE ass2;

INSERT INTO DESIGNATION(DESIG_CODE, DESIG_DESC) VALUES 
("1", "Manager"),
("2", "Executive,"),
("3", "Officer,"),
("4", "Clerk"),
("5", "Helper");

INSERT INTO DEPARTMENT(DEPT_CODE, DEPT_NAME) VALUES
("1", "Personnel"),
("2", "Production"),
("3", "Purchase"),
("4", "Finance"),
("5", "Research");

INSERT INTO EMP(EMP_CODE, EMP_NAME, DEPT_CODE, DESIG_CODE, SEX, ADDRESS, CITY, STATE, PIN, BASIC, JN_DT) VALUES
("1", "Rahul", "1", "1", "M", "Kolkata", "Kolkata", "West Bengal", "700075", 10000, "2002-07-15"),
("2", "Ram", NULL, "1", "M", "Kolkata", "Kolkata", "West Bengal", "700075", 10000, "2003-07-15"),
("3", "Bikash", NULL, "1", "M", "Kolkata", "Kolkata", "West Bengal", "700075", NULL, "2004-07-15"),
("4", "Subir", "2", "1", "M", "Kolkata", "Kolkata", "West Bengal", "700075", 2500, "2005-07-15"),
("5", "Rakesh", "2", "1", "M", "Delhi", "Delhi", "West Bengal", "700075", 2000, "2005-07-15"),
("6", "Adnan", "2", "1", "M", "Delhi", "Delhi", "West Bengal", "700073", 1900, "1998-07-15"),
("7", "Kabir", "2", "1", "M", "Mumbai", "Mumbai", "West Bengal", "700072", 5000, "1950-07-15"),
("8", "Tanmoy", "3", "1", "M", "Mumbai", "Mumbai", "West Bengal", "700072", 3300, "1950-01-01"),
("9", "Rohini", "2", "1", "F", "Karnataka", "Karnataka", "Karnataka", "700042", 5000, "2003-04-04"),
("10", "Lila", "2", "1", "F", "Karnataka", "Karnataka", "Karnataka", "700032", 2000, "2003-05-04"),
("11", "Shila", "3", "2", "F", "Karnataka", "Karnataka", "Karnataka", "700022", 0, "2003-06-04");

