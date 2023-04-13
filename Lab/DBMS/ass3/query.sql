USE ass2;

-- From the EMP table show the minimum, maximum and average basic for each department (show dept. Code).
SELECT MIN(BASIC), MAX(BASIC), AVG(BASIC), DEPT_CODE
FROM EMP
GROUP BY DEPT_CODE;

-- Find the number of female employees in each department (show dept. Code).
SELECT COUNT(*), DEPT_CODE
FROM EMP 
WHERE SEX='F'
GROUP BY DEPT_CODE;

-- Find the city wise no. of employees for each department (show dept. Code)
SELECT COUNT(*), CITY
FROM EMP
GROUP BY CITY;

-- Show the designation wise no of employees who have joined in the year 2000 in each department. The listing should appear in the ascending order of no. of employees.
SELECT COUNT(*) as COUNT, DESIG_CODE
FROM EMP
WHERE year(JN_DT) > 2000 
GROUP BY DESIG_CODE
ORDER BY COUNT ASC;

-- Find the department code wise total basic of male employees only for the departments for which such total is more than 50,000 and the listing should appear in the descending order of total basic.
SELECT SUM(BASIC) as TOTAL_BASIC, DESIG_CODE
FROM EMP
wHERE SEX="M"
GROUP BY DESIG_CODE
HAVING TOTAL_BASIC>50000
ORDER BY TOTAL_BASIC DESC;

-- Show the employee name, Designation description and basic for all employees.
SELECT e.EMP_NAME as NAME, d.DESIG_DESC as DESIGNATION_DESCRIPTION, e.BASIC as BASIC
FROM EMP e
JOIN DESIGNATION d
WHERE e.DESIG_CODE = d.DESIG_CODE;

-- Show the employee name, Designation description, Department Name &amp; Basic for all employees.
SELECT e.EMP_NAME as NAME, d.DESIG_DESC as DESIGNATION_DESCRIPTION, dep.DEPT_NAME as DEPARTMENT_NAME, e.BASIC as BASIC
FROM EMP e
JOIN DESIGNATION d
JOIN DEPARTMENT dep
WHERE e.DESIG_CODE = d.DESIG_CODE and e.DEPT_CODE = dep.DEPT_CODE;

-- Find the department Codes in which no employee works.
SELECT d.DEPT_CODE, d.DEPT_NAME
FROM DEPARTMENT d
LEFT JOIN EMP e ON d.DEPT_CODE = e.DEPT_CODE
GROUP BY d.DEPT_CODE
HAVING COUNT(e.EMP_CODE) = 0;

-- Find the department names where at least one employee works
SELECT d.DEPT_CODE, d.DEPT_NAME
FROM DEPARTMENT d
LEFT JOIN EMP e ON d.DEPT_CODE = e.DEPT_CODE
GROUP BY d.DEPT_CODE
HAVING COUNT(e.EMP_CODE) > 0;

-- Find the department names where at least 10 employees work.
SELECT d.DEPT_CODE, d.DEPT_NAME
FROM DEPARTMENT d
LEFT JOIN EMP e ON d.DEPT_CODE = e.DEPT_CODE
GROUP BY d.DEPT_CODE
HAVING COUNT(e.EMP_CODE) > 9;

-- Find the department code in which employee with highest Basic works.
SELECT d.DEPT_CODE
FROM DEPARTMENT d
LEFT JOIN EMP e ON d.DEPT_CODE = e.DEPT_CODE
WHERE e.BASIC = (
	SELECT MAX(e2.BASIC)
    FROM EMP e2
);

-- Find the Designation description of the employee with highest basic.
SELECT d.DESIG_DESC
FROM DESIGNATION d
LEFT JOIN EMP e ON d.DESIG_CODE = e.DESIG_CODE
WHERE e.BASIC = (
	SELECT MAX(e2.BASIC)
    FROM EMP e2
);

-- Find the no. of managers in each department.
SELECT COUNT(*)
FROM DESIGNATION d
INNER JOIN EMP e ON d.DESIG_CODE = e.DESIG_CODE
WHERE d.DESIG_DESC = "Manager";

-- Find the maximum basic from EMP table without using MAX()
SELECT e1.BASIC
FROM EMP e1
WHERE NOT EXISTS(
	SELECT 1
    FROM EMP e2
    WHERE e2.BASIC > e1.BASIC
);

-- Find the minimum basic from EMP table without using MIN()
SELECT e1.BASIC
FROM EMP e1
WHERE NOT EXISTS(
	SELECT 1
    FROM EMP e2
    WHERE e2.BASIC < e1.BASIC
);

-- Find the name of the department with highest total basic. Do the same for highest average basic and maximum no. of employee.
SELECT d.DEPT_NAME
FROM DEPARTMENT d
INNER JOIN EMP e ON d.DEPT_CODE = e.DEPT_CODE
GROUP BY d.DEPT_CODE
ORDER BY SUM(e.BASIC) DESC
LIMIT 1;

SELECT d.DEPT_NAME
FROM DEPARTMENT d
INNER JOIN EMP e ON d.DEPT_CODE = e.DEPT_CODE
GROUP BY d.DEPT_CODE
ORDER BY AVG(e.BASIC) DESC
LIMIT 1;

SELECT d.DEPT_NAME
FROM DEPARTMENT d
INNER JOIN EMP e ON d.DEPT_CODE = e.DEPT_CODE
GROUP BY d.DEPT_CODE
ORDER BY COUNT(e.EMP_CODE) DESC
LIMIT 1;

-- Insert same rows into EMP table with designation code not existing in DESIGNATION table.
INSERT INTO EMP(EMP_CODE, EMP_NAME, DEPT_CODE, DESIG_CODE, SEX, ADDRESS, CITY, STATE, PIN, BASIC, JN_DT) VALUES
("38", "Rahul22", "1", "62", "M", "Kolkata", "Kolkata", "West Bengal", "700075", 10000, "2002-07-15"),
("39", "Ram22", "1", "62", "M", "Kolkata", "Kolkata", "West Bengal", "700075", 10000, "2003-07-15");

-- Delete the rows from EMP table with invalid DESIG_CODE.
DELETE FROM EMP
WHERE DESIG_CODE NOT IN (SELECT DESIG_CODE FROM DESIGNATION);

-- Find the name of the female employees with basic greater than the average basic of their respective department.
SELECT e.EMP_NAME
FROM  EMP e
WHERE e.SEX = 'F'
  AND BASIC > (
    SELECT AVG(BASIC)
    FROM EMP e2
    WHERE e2.DESIG_CODE = e.DESIG_CODE
      AND e2.DEPT_CODE= e.DEPT_CODE
  );


-- Find the number of female managers.
SELECT COUNT(*)
FROM DESIGNATION d
INNER JOIN EMP e ON d.DESIG_CODE = e.DESIG_CODE
WHERE d.DESIG_DESC = "Manager" and e.SEX = 'F';