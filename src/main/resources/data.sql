INSERT INTO Products (prod_name, prod_price) values ('베베숲 물티슈', 2700);
INSERT INTO Products (prod_name, prod_price) values ('여름 토퍼', 35180);
INSERT INTO Products (prod_name, prod_price) values ('페이크 삭스', 860);
INSERT INTO Products (prod_name, prod_price) values ('우산', 2900);

insert into member (name, city, street, zipcode) values ('길동이', '서울', '강남', '123456');
insert into member (name, city, street, zipcode) values ('홍길동', '인청', '부평', '452456');

insert into item (name, price, stock_quantity, dtype, artist, etc, author, isbn, actor, director)
values ('JPA',15000,10,'B',null,null,'유재석','123456',null,null);
insert into item (name, price, stock_quantity, dtype, artist, etc, author, isbn, actor, director)
values ('Spring',10000,10,'B',null,null,'강호동','654321',null,null);
insert into item (name, price, stock_quantity, dtype, artist, etc, author, isbn, actor, director)
values ('MyBatis',20000,10,'B',null,null,'길동이','1111321',null,null);

insert into boards (writer,title,content,reg_date,hit) values ('김민기', '게시판 제목1 입니다.', '내용1 입니다.', current_date(), 0);
insert into boards (writer,title,content,reg_date,hit) values ('김민기', '게시판 제목2 입니다.', '내용2 입니다.', current_date(), 0);
insert into boards (writer,title,content,reg_date,hit) values ('김민기', '게시판 제목3 입니다.', '내용3 입니다.', current_date(), 0);
insert into boards (writer,title,content,reg_date) values ('김민기', '게시판 제목4 입니다.', '내용4 입니다.', current_date());