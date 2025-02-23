CREATE TABLE categories (
    category_id int NOT NULL AUTO_INCREMENT,
    category_label varchar(50) NOT NULL,
    category_type_id int NOT NULL,
    PRIMARY KEY (category_id)
);