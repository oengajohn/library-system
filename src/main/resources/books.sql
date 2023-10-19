CREATE TABLE IF NOT EXISTS books(id int not null auto_increment primary key,isbn varchar(50) not null unique,title varchar(100) not null,author varchar(50) not null ,book_edition varchar(20) not null,category ENUM('FICTION', 'NON_FICTION') NOT NULL);
select * from books;
select * from books where isbn=:isbn;
insert into books(isbn,title,author,book_edition,category)values(?,?,?,?,?);

CREATE TABLE IF NOT EXISTS borrowed_books(id int not null auto_increment primary key,book_id int not null,student_id int not null,borrowing_date date,return_date date,FOREIGN KEY (book_id) REFERENCES books(id),FOREIGN KEY (student_id) REFERENCES students(id));


