USE ass3_5;

-- Insert book
INSERT book (book_id, title, author, publisher, price)
VALUES(1, "Book 1", "Author 1", "Publisher 1", 122);


INSERT book (book_id, title, author, publisher, price)
VALUES(2, "Book 2", "Author 2", "Publisher 2", 122);

SELECT * from book;

-- create members
INSERT member(id, name, email, member_type, max_books)
VALUES
(3, "Rahim", "t@ts.com", "student", 4),
(4, "Sir 2", "t@ts.com", "faculty", 10)
;

INSERT transaction_slip(member_id, book_id, book_serial, issue_date)
VALUES (1, 2, 1, "2022-11-12");
SELECT * FROM transaction_slip;

SELECT * from member;

-- Number of copies for each book(all)
SELECT book_id, COUNT(*) FROM book
GROUP BY book_id; 

-- Number of copies for each book(issued)
SELECT book_id, COUNT(*) AS issued_copies FROM book
GROUP BY book_id, available HAVING available = false;

-- student/faculty not making any transactions
SELECT member.* FROM member
LEFT JOIN transaction_slip AS t ON t.member_id = member.id
WHERE t.member_id IS NULL and member.member_type = 'student';

SELECT member.* FROM member
LEFT JOIN transaction_slip AS t ON t.member_id = member.id
WHERE t.member_id IS NULL and member.member_type = 'faculty';

-- members holding a book after due date
SELECT t1.member_id, member.name, member.email, t1.issue_date, t1.to_be_returned_by, t1.return_date FROM
transaction_slip AS t1
JOIN member ON member.id = t1.member_id
WHERE t1.to_be_returned_by < CURRENT_DATE AND t1.return_date IS NULL;

-- transaction details of late book return
SELECT *, DATEDIFF(return_date, to_be_returned_by) AS delayed_by FROM transaction_slip
WHERE to_be_returned_by < return_date ;