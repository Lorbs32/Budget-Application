INSERT INTO users (first_name,last_name,email_address, username, password)
    VALUES ('Josh','McGuire','joshmcguire14@gmail.com', 'joshmcguire14', 'password');
INSERT INTO users (first_name,last_name,email_address, username, password)
    VALUES ('Emma','Grove','emmagrove20230@gmail.com', 'emmagrove20230', 'password');
INSERT INTO users (first_name,last_name,email_address, username, password)
    VALUES ('Kyle','Lohrberg','lorbs32@gmail.com', 'lorbs32', 'password');
INSERT INTO users (first_name,last_name,email_address, username, password)
    VALUES ('Naomi','Ellison','NRoseE32@yahoo.com', 'NRoseE32', 'password');
INSERT INTO users (first_name,last_name,email_address, username, password)
    VALUES ('Etana','Lehman','enlehman@wccnet.edu', 'enlehman', 'password');
INSERT INTO users (first_name,last_name,email_address, username, password)
    VALUES ('Andrew','Redding','ajredding@wccnet.edu', 'ajredding', 'password');
INSERT INTO users (first_name,last_name,email_address, username, password)
    VALUES ('Mazin','Iqbal','maziniqbal2005@gmail.com', 'maziniqbal2005', 'password');

--Category types 1 (Income), 2 (Expenses)
--INSERT INTO categories (category_label, category_type_id) VALUES ('W2',1);
--INSERT INTO categories (category_label, category_type_id) VALUES ('1099',1);
--INSERT INTO categories (category_label, category_type_id) VALUES ('Housing',2);
--INSERT INTO categories (category_label, category_type_id) VALUES ('Utilities',2);
--INSERT INTO categories (category_label, category_type_id) VALUES ('Transportation',2);
--INSERT INTO categories (category_label, category_type_id) VALUES ('Food',2);
--INSERT INTO categories (category_label, category_type_id) VALUES ('Insurance',2);
--INSERT INTO categories (category_label, category_type_id) VALUES ('Health',2);
--INSERT INTO categories (category_label, category_type_id) VALUES ('Entertainment',2);
--INSERT INTO categories (category_label, category_type_id) VALUES ('Giving',2);
--INSERT INTO categories (category_label, category_type_id) VALUES ('Debt',2);

-- Date
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date) VALUES (2025, 'Jan', '2025-01-01', '2025-01-31');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date) VALUES (2025, 'Feb', '2025-02-01', '2025-02-28');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date) VALUES (2025, 'Mar', '2025-03-01', '2025-03-31');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date) VALUES (2025, 'Apr', '2025-04-01', '2025-04-30');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date) VALUES (2025, 'May', '2025-05-01', '2025-05-31');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date) VALUES (2025, 'Jun', '2025-06-01', '2025-06-30');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date) VALUES (2025, 'Jul', '2025-07-01', '2025-07-31');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date) VALUES (2025, 'Aug', '2025-08-01', '2025-08-31');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date) VALUES (2025, 'Sep', '2025-09-01', '2025-09-30');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date) VALUES (2025, 'Oct', '2025-10-01', '2025-10-31');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date) VALUES (2025, 'Nov', '2025-11-01', '2025-11-30');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date) VALUES (2025, 'Dec', '2025-12-01', '2025-12-31');

-- Budget (budget_id autoincrement to 1 (josh))
INSERT INTO budgets (date_id, user_id) VALUES (3,1);

-- Categories (budget_id = 1 (josh))
INSERT INTO categories (category_name, budget_id,) VALUES ('Income',1);
INSERT INTO categories (category_name, budget_id,) VALUES ('Housing',1);
INSERT INTO categories (category_name, budget_id,) VALUES ('Transportation',1);
INSERT INTO categories (category_name, budget_id,) VALUES ('Food',1);
INSERT INTO categories (category_name, budget_id,) VALUES ('Personal',1);
INSERT INTO categories (category_name, budget_id,) VALUES ('Health',1);
INSERT INTO categories (category_name, budget_id,) VALUES ('Insurance',1);
INSERT INTO categories (category_name, budget_id,) VALUES ('Saving',1);

-- Line Items
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount) VALUES (1,true,'Paycheck 1',2000.00);
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount) VALUES (2,false,'Mortgage/Rent',500.89);
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount) VALUES (2,false,'Electricity',101.05);
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount) VALUES (3,false,'Gas',25.25);
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount) VALUES (4,false,'Groceries',100.00);
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount) VALUES (4,false,'Restaurants',75.00);
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount) VALUES (5,false,'Clothing',20.00);
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount) VALUES (5,false,'Phone',45.00);
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount) VALUES (6,false,'Gym',30.00);
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount) VALUES (6,false,'Doctor/Dentist Visits',50.00);
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount) VALUES (7,false,'Health Insurance',125.00);
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount) VALUES (7,false,'Life Insurance',15.50);
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount) VALUES (7,false,'Auto Insurance',100.00);
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount) VALUES (8,false,'Emergency Fund',200.00);

-- Transactions
INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount) VALUES (2,'2025-03-05','Apartment Complex','Note text apartment',500.89);
INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount) VALUES (6,'2025-03-02','Savvy Sliders','Note text restaurant',25.15);