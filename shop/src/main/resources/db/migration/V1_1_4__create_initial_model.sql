CREATE TABLE IF NOT EXISTS PRODUCT_CATEGORY (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null,
    description varchar(255),
);

CREATE TABLE IF NOT EXISTS SUPPLIER (
    ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null unique
);


CREATE TABLE IF NOT EXISTS CUSTOMER (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    username varchar(255) not null unique,
    password varchar(255) not null,
    email_address varchar(255) not null unique
);

CREATE TABLE IF NOT EXISTS LOCATION (
    id  int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null,
    country varchar(255),
    city varchar(255),
    county varchar(255),
    street_address varchar(255)
);

CREATE TABLE IF NOT EXISTS PRODUCT (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null,
    description varchar(255),
    price decimal(19,2) not null,
    weight double,
    category_id int  NOT NULL REFERENCES PRODUCT_CATEGORY (id),
    supplier_id int NOT NULL REFERENCES SUPPLIER (id),
    image_url varchar(255)
);

CREATE TABLE IF NOT EXISTS REVENUE
(
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    location_id int  NOT NULL REFERENCES LOCATION (id),
    date date NOT NULL,
    sum  decimal(19,2) not null
);

CREATE TABLE IF NOT EXISTS STOCK
(
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id  int NOT NULL REFERENCES PRODUCT (id),
    location_id int NOT NULL REFERENCES LOCATION (id),
    quantity int NOT NULL CHECK (quantity >= 0),
    CONSTRAINT unique_stock UNIQUE (product_id, location_id)
);

CREATE TABLE IF NOT EXISTS ORDER_TABLE
(
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    shipped_from int NOT NULL REFERENCES LOCATION (id),
    customer_id int NOT NULL REFERENCES CUSTOMER (id),
    created_at datetime NOT NULL,
    country varchar(256) NOT NULL,
    city varchar(256) NOT NULL,
    county varchar(256) NOT NULL,
    street_address varchar(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS order_detail
(
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id int NOT NULL REFERENCES ORDER_TABLE (id),
    product_id int NOT NULL REFERENCES PRODUCT (id),
    quantity int NOT NULL CHECK (quantity > 0),
    CONSTRAINT unique_order_detail UNIQUE (order_id, product_id)
);