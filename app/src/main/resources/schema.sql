CREATE TABLE IF NOT EXISTS users
(
    user_id       INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    email_address VARCHAR(50) NOT NULL,
    username      VARCHAR(50) NOT NULL,
    password      VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS budget_dates
(
    budget_date_id INT AUTO_INCREMENT PRIMARY KEY,
    budget_year    INT          NOT NULL,
    budget_month   VARCHAR(255) NOT NULL,
    start_date     DATE         NOT NULL,
    end_date       DATE         NOT NULL
);

CREATE TABLE IF NOT EXISTS budgets
(
    budget_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id   INT,
    date_id   INT,

    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (date_id) REFERENCES budget_dates (budget_date_id)
);

CREATE TABLE IF NOT EXISTS categories
(
    category_id   INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL,
    budget_id     INT,

    FOREIGN KEY (budget_id) REFERENCES budgets (budget_id)
);

CREATE TABLE IF NOT EXISTS line_items
(
    category_id INT,
    line_item_id   INT AUTO_INCREMENT PRIMARY KEY,
    line_item_name VARCHAR(255) NOT NULL,
    planned_amount DECIMAL(10, 2) DEFAULT 0.00,
    is_income      BOOLEAN      NOT NULL,
    recurrence     VARCHAR(255) NOT NULL,

    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

CREATE TABLE IF NOT EXISTS transactions
(
    transaction_id   INT AUTO_INCREMENT PRIMARY KEY,
    merchant         VARCHAR(255),
    actual_amount    DECIMAL(10, 2) DEFAULT 0.00,
    transaction_date DATE,
    note             VARCHAR(255),
    line_item_id     INT,

    FOREIGN KEY (line_item_id) REFERENCES line_items (line_item_id)
);

