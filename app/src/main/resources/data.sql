INSERT INTO users (first_name,last_name,email_address, username, password) VALUES ('Josh','McGuire','joshmcguire14@gmail.com', 'joshmcguire14', 'password');
INSERT INTO users (first_name,last_name,email_address, username, password) VALUES ('Emma','Grove','emmagrove20230@gmail.com', 'emmagrove20230', 'password');
INSERT INTO users (first_name,last_name,email_address, username, password) VALUES ('Kyle','Lohrberg','lorbs32@gmail.com', 'lorbs32', 'password');
INSERT INTO users (first_name,last_name,email_address, username, password) VALUES ('Naomi','Ellison','NRoseE32@yahoo.com', 'NRoseE32', 'password');
INSERT INTO users (first_name,last_name,email_address, username, password) VALUES ('Etana','Lehman','enlehman@wccnet.edu', 'enlehman', 'password');
INSERT INTO users (first_name,last_name,email_address, username, password) VALUES ('Andrew','Redding','ajredding@wccnet.edu', 'ajredding', 'password');
INSERT INTO users (first_name,last_name,email_address, username, password) VALUES ('Mazin','Iqbal','maziniqbal2005@gmail.com', 'maziniqbal2005', 'password');

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

-- Group Type
INSERT INTO group_types (group_label) VALUES ('income');
INSERT INTO group_types (group_label) VALUES ('expense');

-- Budget
INSERT INTO budgets (date_id, user_id) VALUES (1,1);

-- Groups
INSERT INTO groups (group_name, budget_id, group_type_id) VALUES ('Income',1,1);
INSERT INTO groups (group_name, budget_id, group_type_id) VALUES ('Housing',1,2);
INSERT INTO groups (group_name, budget_id, group_type_id) VALUES ('Transportation',1,2);
INSERT INTO groups (group_name, budget_id, group_type_id) VALUES ('Food',1,2);
INSERT INTO groups (group_name, budget_id, group_type_id) VALUES ('Personal',1,2);
INSERT INTO groups (group_name, budget_id, group_type_id) VALUES ('Health',1,2);
INSERT INTO groups (group_name, budget_id, group_type_id) VALUES ('Insurance',1,2);
INSERT INTO groups (group_name, budget_id, group_type_id) VALUES ('Saving',1,2);

-- Line Items
INSERT INTO line_items (group_id, line_item_name, planned_amount) VALUES (1,'Paycheck 1',2000.00);
INSERT INTO line_items (group_id, line_item_name, planned_amount) VALUES (2,'Mortgage/Rent',500.89);
INSERT INTO line_items (group_id, line_item_name, planned_amount) VALUES (2,'Electricity',101.05);
INSERT INTO line_items (group_id, line_item_name, planned_amount) VALUES (3,'Gas',25.25);
INSERT INTO line_items (group_id, line_item_name, planned_amount) VALUES (4,'Groceries',100.00);
INSERT INTO line_items (group_id, line_item_name, planned_amount) VALUES (4,'Restaurants',75.00);
INSERT INTO line_items (group_id, line_item_name, planned_amount) VALUES (5,'Clothing',20.00);
INSERT INTO line_items (group_id, line_item_name, planned_amount) VALUES (5,'Phone',45.00);
INSERT INTO line_items (group_id, line_item_name, planned_amount) VALUES (6,'Gym',30.00);
INSERT INTO line_items (group_id, line_item_name, planned_amount) VALUES (6,'Doctor/Dentist Visits',50.00);
INSERT INTO line_items (group_id, line_item_name, planned_amount) VALUES (7,'Health Insurance',125.00);
INSERT INTO line_items (group_id, line_item_name, planned_amount) VALUES (7,'Life Insurance',15.50);
INSERT INTO line_items (group_id, line_item_name, planned_amount) VALUES (7,'Auto Insurance',100.00);
INSERT INTO line_items (group_id, line_item_name, planned_amount) VALUES (8,'Emergency Fund',200.00);

-- Transactions
INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount) VALUES (2,'2025-03-05','Apartment Complex','Note text apartment',500.89);
INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount) VALUES (6,'2025-03-02','Savvy Sliders','Note text restaurant',25.15);