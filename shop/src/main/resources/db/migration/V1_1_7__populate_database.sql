INSERT INTO SUPPLIER(name)
VALUES ('mihai');

INSERT INTO PRODUCT_CATEGORY(name, description)
VALUES ('water', 'weet');

INSERT INTO PRODUCT(name, description, price, weight, category_id, supplier_id, image_url)
VALUES ('aqua', 'natural', 12, 1, 1, 1, 'http');

INSERT INTO location(name, country, city, county, street_address)
VALUES ('Location 1', 'Romania', 'Romania', 'Romania', 'Romania'),
       ('Location 2', 'Romania', 'Romania', 'Romania', 'Romania');

INSERT INTO customer(first_name, last_name, username, password, email_address)
VALUES ('name', 'name', 'name', 'name', 'name@gmail.com');

DELETE
FROM STOCK
WHERE PRODUCT_ID = 1
  and LOCATION_ID = 1;
DELETE
FROM STOCK
WHERE PRODUCT_ID = 1
  and LOCATION_ID = 2;

INSERT INTO stock(PRODUCT_ID, LOCATION_ID, QUANTITY)
VALUES (1, 1, 15),
       (1, 2, 20);

UPDATE SUPPLIER
set ID = 1
where NAME = 'mihai';

UPDATE PRODUCT_CATEGORY
set ID = 1
where NAME = 'water';

UPDATE PRODUCT
set ID = 1
where NAME = 'aqua';

UPDATE CUSTOMER
set ID = 1
where FIRST_NAME = 'name';

UPDATE LOCATION
set ID = 1
where NAME = 'location 1';

UPDATE LOCATION
set ID = 2
where NAME = 'location 2';