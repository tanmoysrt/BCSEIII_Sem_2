USE ass2;

-- Find the rows with unassigned DEPT_CODE
SELECT * FROM EMP WHERE DEPT_CODE is NULL;

-- Find the rows with BASIC equal to zero
SELECT * FROM EMP WHERE BASIC = 0;

-- Find the rows with unassigned Basic
SELECT * FROM EMP WHERE BASIC is NULL;

-- the average basic of the employees.
SELECT AVG(BASIC) FROM EMP;

-- Replace the BASIC with 0 for the rows with unassigned Basic
UPDATE EMP SET BASIC = NULL WHERE BASIC = 0;

-- find the average Basic
SELECT AVG(BASIC) FROM EMP;

-- Delete rows with unassigned dept code --
DELETE FROM EMP WHERE DEPT_CODE is NULL;

-- Net pay of an employee= Basic+HRA+DA where HRA is 50% of the Basic &amp; DA is 40% of Basic. Show the employee name &amp; Net pay for all employees. --
SELECT EMP_NAME, (BASIC + BASIC*0.5 + BASIC*0.4) AS NET_PAY FROM EMP;

-- EMP_NAME and BASIC in the ascending order of DEPT_CODE . The employee name must appear in uppercase --
SELECT UPPER(EMP_NAME), BASIC FROM EMP ORDER BY DEPT_CODE ASC;

-- Find the employees who have joined after 1 st January 1990
SELECT * FROM EMP WHERE JN_DT > "1990-01-01";

-- Find, how many employees have joined in the month of January
SELECT * FROM EMP WHERE MONTH(JN_DT) = 1;

-- Find the maximum & minimum Basic.
SELECT MIN(BASIC) , MAX(BASIC) FROM EMP;

-- Find how many Female employees are there
SELECT COUNT(BASIC) FROM EMP WHERE SEX = "F";

-- Replace CITY with existing value converted into uppercase for all rows
UPDATE EMP SET CITY=UPPER(CITY);

-- Find in how many different cities various employees are residing
SELECT COUNT(DISTINCT(CITY)) FROM EMP;

-- Display the employee information in the ascending order of DEPT_CODE and with in a Department, it should be in the descending order of BASIC
SELECT * FROM EMP ORDER BY DEPT_CODE ASC, BASIC DESC;