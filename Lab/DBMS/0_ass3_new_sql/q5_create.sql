DROP DATABASE ass3_5;
CREATE DATABASE ass3_5;
USE ass3_5;

-- Create tables
CREATE TABLE book(
    book_id INT NOT NULL,
    serial_num INT NOT NULL,
    title VARCHAR(100),
    author VARCHAR(50),
    publisher VARCHAR(60),
    price INT,
    available BOOLEAN DEFAULT true,
    PRIMARY KEY (book_id, serial_num)
);


CREATE TABLE member(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(25),
    email VARCHAR(100),
    member_type CHAR(7),
    max_books INT,
    CONSTRAINT check_type CHECK(member_type IN ('faculty', 'student')),
    CONSTRAINT check_maxbooks CHECK((member_type = 'faculty' AND max_books = 10) OR (member_type = 'student' AND max_books = 4))
);

CREATE TABLE transaction_slip(
    id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT,
    book_id INT,
    book_serial INT,
    issue_date DATE DEFAULT(CURRENT_DATE),
    return_date DATE,
    to_be_returned_by DATE,
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE RESTRICT,
    FOREIGN KEY (book_id, book_serial) REFERENCES book(book_id, serial_num) ON DELETE RESTRICT
);

-- Create trigger to verify all condition of a transaction
delimiter //
CREATE TRIGGER transaction_trigger 
BEFORE INSERT ON transaction_slip
FOR EACH ROW
BEGIN
	DECLARE msg VARCHAR(32) DEFAULT "";
    -- checks for returning books
    IF (NEW.issue_date IS NULL AND NEW.return_date IS NOT NULL) THEN
        IF NOT EXISTS ( SELECT * FROM book  WHERE book_id = NEW.book_id AND serial_num = NEW.book_serial AND available = false) THEN
            set msg = concat('Book is already returned !');
            signal sqlstate '45000' set message_text = msg;
        ELSE
            UPDATE book SET available = NOT available WHERE book_id = NEW.book_id AND serial_num = NEW.book_serial;
            UPDATE transaction_slip SET return_date = NEW.return_date 
            WHERE member_id = NEW.member_id AND book_id = NEW.book_id AND book_serial = NEW.book_serial AND return_date IS NULL;
        END IF;
	-- checks for issuing books
    ELSE
		-- if book not available
        IF NOT EXISTS ( SELECT * FROM book WHERE book_id = NEW.book_id AND serial_num = NEW.book_serial AND available = true) THEN
			set msg = concat('Book Not Available !');
            signal sqlstate '45000' set message_text = msg;
        ELSE
        -- if available, update
			IF (SELECT COUNT(*) FROM transaction_slip t WHERE t.ISSUE_DATE is not NULL and t.RETURN_DATE is NULL and t.member_id = NEW.member_id) >= (SELECT m.max_books FROM member m WHERE m.id = NEW.member_id) THEN
				set msg = concat('Member has issued max books !');
				signal sqlstate '45000' set message_text = msg;
            ELSE
				UPDATE book SET available = NOT available WHERE book_id = NEW.book_id AND serial_num = NEW.book_serial;
				SET NEW.to_be_returned_by =  CAST(DATE_ADD(NEW.issue_date, INTERVAL 7 DAY) as DATE);
            END IF;
        END IF;
    END IF;
END;
//

-- Create trigger to increase serial_no automatically
delimiter //
CREATE TRIGGER before_insert_update_serial_no
BEFORE INSERT ON book
FOR EACH ROW
BEGIN
	SET NEW.serial_num = COALESCE((SELECT MAX(serial_num) FROM book WHERE book_id = NEW.book_id), 0) + 1;
END 
//

