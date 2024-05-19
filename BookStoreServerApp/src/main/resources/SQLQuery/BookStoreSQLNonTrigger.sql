
CREATE TABLE IF NOT EXISTS "customer_information" (
  customer_id SERIAL PRIMARY KEY,
  fullname VARCHAR(50),
  email VARCHAR(50),
  gender VARCHAR(7),
  birthday VARCHAR(50),
  phonenumber CHAR(10),
  address VARCHAR(500),
  avatar VARCHAR(200),
  create_at VARCHAR(50),
  update_at VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS "staff_information"(
	staff_id SERIAL PRIMARY KEY,
	fullname VARCHAR(50),
	email VARCHAR(50),
	gender VARCHAR(7),
	birthday VARCHAR(50),
	phonenumber CHAR(10),
	address VARCHAR(500),
	initiate_time VARCHAR(50),
	salary INT,
	avatar VARCHAR(200),
	create_at VARCHAR(50),
	update_at VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS "user" (
  id SERIAL PRIMARY KEY,
username VARCHAR(50),
password VARCHAR(50),
	token VARCHAR(100),
	create_at VARCHAR(50),
	update_at VARCHAR(50),
  FOREIGN KEY (id) REFERENCES "customer_information"(customer_id),
	FOREIGN KEY(id) REFERENCES "staff_information" (staff_id)
	
);

CREATE TABLE IF NOT EXISTS "role" (
  rolename VARCHAR(50) PRIMARY KEY,
	description VARCHAR(100),
	create_at VARCHAR(50),
	update_at VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS user_role (
  userid INT REFERENCES "user"(id),
  rolename VARCHAR(50) REFERENCES "role"(rolename),
  PRIMARY KEY (userid,rolename)
);


CREATE TABLE IF NOT EXISTS "permission" (
  permissionname VARCHAR(50) PRIMARY KEY,
	description VARCHAR(100),
	create_at VARCHAR(50),
	update_at VARCHAR(50)
);


CREATE TABLE IF NOT EXISTS role_permission (
  rolename VARCHAR(50) REFERENCES "role"(rolename) ,
  permissionname VARCHAR(50) REFERENCES "permission"(permissionname) ,
  PRIMARY KEY (rolename, permissionname)
	
);


									--PRODUCT
									
									
									
-- Tạo bảng Category
CREATE TABLE IF NOT EXISTS category (
  category_id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  hot SMALLINT,
  avatar VARCHAR(300),
  create_at VARCHAR(20),
  update_at VARCHAR(20)
);


-- Tạo bảng Book_language
CREATE TABLE IF NOT EXISTS book_language (
  language_id SERIAL PRIMARY KEY,
  language_name VARCHAR(40),
  create_at VARCHAR(20),
  update_at VARCHAR(20)
	
);


-- Tạo bảng Publisher
CREATE TABLE IF NOT EXISTS publisher (
  publisher_id SERIAL PRIMARY KEY,
  publisher_name VARCHAR(100),
  create_at VARCHAR(20),
  update_at VARCHAR(20)

);


-- Tạo bảng Book
CREATE TABLE IF NOT EXISTS book (
  book_id SERIAL PRIMARY KEY,
  category_id INT REFERENCES category(category_id),
  title VARCHAR(250),
  language_id INT REFERENCES book_language(language_id), -- Liên kết với bảng Book_language
  num_pages SMALLINT,
  publication_date VARCHAR(20),
  publisher_id INT REFERENCES publisher(publisher_id),
  bookQuantity FLOAT,
  price INT,
  discount INT,
  description TEXT,
  hot INT,
  total_pay INT, --số lượt bán ra
  available INT,
  create_at VARCHAR(20),
  update_at VARCHAR(20),
  status INT--kiểm duyệt
  
);

-- Tạo bảng Feedback
CREATE TABLE IF NOT EXISTS feedback (
  feedback_id SERIAL PRIMARY KEY,
  feedback_comment VARCHAR(250),
  rating INT,
  book_id INT REFERENCES book(book_id),
  customer_id INT REFERENCES "customer_information" (customer_id),
  create_at VARCHAR(20),
  update_at VARCHAR(20)
);



-- Tạo bảng KeyWord
CREATE TABLE IF NOT EXISTS keyword (
  keyword_id SERIAL PRIMARY KEY,
  name VARCHAR(300),
  description VARCHAR(350),
  status INT,
  hot INT,
  create_at VARCHAR(20),
  update_at VARCHAR(20)
	
);


-- Tạo bảng Product_KeyWord (bảng nối giữa Book và KeyWord)
CREATE TABLE IF NOT EXISTS book_keyword (
  book_id INT REFERENCES book(book_id),
  keyword_id INT REFERENCES keyword(keyword_id),
	PRIMARY KEY(book_id,keyword_id)
	
);



-- Tạo bảng Author
CREATE TABLE IF NOT EXISTS author (
  author_id SERIAL PRIMARY KEY,
  author_name VARCHAR(50),
  create_at VARCHAR(20),
  update_at VARCHAR(20)
	
);





-- Tạo bảng Book_Author (bảng nối giữa Book và Author)
CREATE TABLE IF NOT EXISTS book_author (
  book_id INT REFERENCES book(book_id),
  author_id INT REFERENCES author(author_id),
  PRIMARY KEY (book_id, author_id)
);


-- Tạo bảng Provider
CREATE TABLE IF NOT EXISTS provider (
  provider_id SERIAL PRIMARY KEY,
  providername VARCHAR(100),
  address VARCHAR(500),
  representativename VARCHAR(50),
	create_at VARCHAR(20),
  update_at VARCHAR(20)
	
);

-- Tạo bảng PhieuNhap
CREATE TABLE IF NOT EXISTS "import" (
  import_id SERIAL PRIMARY KEY,
  staff_id INT,
  import_day VARCHAR(20),
  create_at VARCHAR(20),
  update_at VARCHAR(20),
  FOREIGN KEY (staff_id) REFERENCES "staff_information"(staff_id) ON DELETE CASCADE
  --CONSTRAINT fk_nhanvien_phieunhap CHECK (type_id = 2) -- Đảm bảo chỉ có nhân viên được thêm vào PhieuNhap (cái này đang lỗi á, fix giùm tui)
);

-- Tạo bảng CTPN 
CREATE TABLE IF NOT EXISTS import_detail (
  import_id INT,
  book_id INT,
  quantity INT,
  import_cost MONEY,
  provider_id INT,
  FOREIGN KEY (book_id) REFERENCES book(book_id),
  FOREIGN KEY (provider_id) REFERENCES provider(provider_id),
  FOREIGN KEY (import_id) REFERENCES import(import_id),
	PRIMARY KEY(import_id,book_id)
	
  --CONSTRAINT fk_nhanvien_ctpn CHECK (type_id = 2) -- Đảm bảo chỉ có nhân viên được thêm vào CTPN (cái này y vậy)
);



-- Tạo bảng Order
CREATE TABLE IF NOT EXISTS "order" (
  order_id SERIAL PRIMARY KEY,
  customer_id INT REFERENCES "customer_information"(customer_id),
  order_note VARCHAR(30),
  order_date VARCHAR(20),
  price INT,
  total_dis INT,
  status_trans INT, -- trạng thái đơn hàng: pending, approved, ...
  total_price INT, -- price - total dis
  method_payment INT,
  address VARCHAR(500),
  create_at VARCHAR(20),
  update_at VARCHAR(20)
);

-- Tạo bảng Order_Detail
CREATE TABLE IF NOT EXISTS order_detail (
order_id INT REFERENCES "order"(order_id),
  book_id INT REFERENCES book(book_id),
  avatar VARCHAR(300),
  status INT,
  price INT,
  quantity INT, -- số lượng sản phẩm
  discount INT,
  total_money INT, -- price * num - discount
	PRIMARY KEY(order_id, book_id)
);



-- Tạo lại bảng Gallery với ràng buộc khóa ngoại
CREATE TABLE IF NOT EXISTS gallery_management (
  id_gallery SERIAL PRIMARY KEY,
  book_id INT REFERENCES book(book_id),
  thumbnail VARCHAR(500),
  create_at VARCHAR(20),
  update_at VARCHAR(20)
);

--Tao bang shift
CREATE TABLE IF NOT EXISTS shift(
	shift_id INT PRIMARY KEY,
	start_time VARCHAR(50),
	end_time VARCHAR(50),
	description VARCHAR(100),
	create_at VARCHAR(50),
	update_at VARCHAR(50)
);
-- Tạo bảng LichLamViec
CREATE TABLE IF NOT EXISTS staff_shift (
  staff_id INT REFERENCES staff_information(staff_id),
	shift_id INT REFERENCES shift(shift_id),
 hasWorkThisShift BOOLEAN,
  PRIMARY KEY (staff_id,shift_id)
);


CREATE TABLE IF NOT EXISTS "message" (
   message_id SERIAL PRIMARY KEY, 
   message_content VARCHAR(250),
   create_at VARCHAR(20),
   message_status INT 

);

CREATE TABLE IF NOT EXISTS user_message (
   user_id SERIAL REFERENCES "user" (id), 
   message_id SERIAL REFERENCES "message" (message_id),
   PRIMARY KEY (user_id, message_id)

);
