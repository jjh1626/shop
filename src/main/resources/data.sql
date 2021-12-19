INSERT INTO Products (prod_name, prod_price) values ('베베숲 물티슈', 2700);
INSERT INTO Products (prod_name, prod_price) values ('여름 토퍼', 35180);
INSERT INTO Products (prod_name, prod_price) values ('페이크 삭스', 860);
INSERT INTO Products (prod_name, prod_price) values ('우산', 2900);

insert into member (name, city, street, zipcode, gender, hobby) values ('길동이', '서울', '강남', '123456', 'M', '골프,요리');
insert into member (name, city, street, zipcode, gender, hobby) values ('홍길순', '인천', '부평', '452456', 'W', '운동,여행,게임');

insert into item (name, price, stock_quantity, dtype, artist, etc, author, isbn, actor, director)
values ('JPA',15000,10,'B',null,null,'유재석','123456',null,null);
insert into item (name, price, stock_quantity, dtype, artist, etc, author, isbn, actor, director)
values ('Spring',10000,10,'B',null,null,'강호동','654321',null,null);
insert into item (name, price, stock_quantity, dtype, artist, etc, author, isbn, actor, director)
values ('MyBatis',20000,10,'B',null,null,'길동이','1111321',null,null);

insert into boards (writer,title,content,reg_date,hit) values ('정민기', '게시판 제목1 입니다.', '내용1 입니다.', current_date(), 0);
insert into boards (writer,title,content,reg_date,hit) values ('백민기', '게시판 제목2 입니다.', '내용2 입니다.', current_date(), 0);
insert into boards (writer,title,content,reg_date,hit) values ('이민기', '게시판 제목3 입니다.', '내용3 입니다.', current_date(), 0);
insert into boards (writer,title,content,reg_date) values ('김민기', '게시판 제목4 입니다.', '내용4 입니다.', current_date());
insert into boards (writer,title,content,reg_date) values ('홍길동', '게시판 제목5 입니다.', '내용5 입니다.', current_date());
insert into boards (writer,title,content,reg_date) values ('길동이', '게시판 제목6 입니다.', '내용6 입니다.', current_date());
insert into boards (writer,title,content,reg_date) values ('호동이', '게시판 제목7 입니다.', '내용7 입니다.', current_date());
insert into boards (writer,title,content,reg_date) values ('김민기', '게시판 제목8 입니다.', '내용8 입니다.', current_date());
insert into boards (writer,title,content,reg_date) values ('홍길동', '게시판 제목9 입니다.', '내용9 입니다.', current_date());
insert into boards (writer,title,content,reg_date) values ('길동이', '게시판 제목10 입니다.', '내용10 입니다.', current_date());
insert into boards (writer,title,content,reg_date) values ('호동이', '게시판 제목11 입니다.', '내용11 입니다.', current_date());
insert into boards (writer,title,content,reg_date) values ('길동이', '게시판 제목12 입니다.', '내용12 입니다.', current_date());
insert into boards (writer,title,content,reg_date) values ('호동이', '게시판 제목13 입니다.', '내용13 입니다.', current_date());

insert into code (code_group, code, code_name) values ('city', '서울', '서울');
insert into code (code_group, code, code_name) values ('city', '인천', '인천');
insert into code (code_group, code, code_name) values ('city', '경기도', '경기도');
insert into code (code_group, code, code_name) values ('city', '대구', '대구');
insert into code (code_group, code, code_name) values ('city', '부산', '부산');
insert into code (code_group, code, code_name) values ('gender', 'M', '남자');
insert into code (code_group, code, code_name) values ('gender', 'W', '여자');
insert into code (code_group, code, code_name) values ('hobby', '낚시', '낚시');
insert into code (code_group, code, code_name) values ('hobby', '운동', '운동');
insert into code (code_group, code, code_name) values ('hobby', '골프', '골프');
insert into code (code_group, code, code_name) values ('hobby', '여행', '여행');
insert into code (code_group, code, code_name) values ('hobby', '요리', '요리');
insert into code (code_group, code, code_name) values ('hobby', '게임', '게임');