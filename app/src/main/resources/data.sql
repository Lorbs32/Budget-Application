INSERT INTO users (first_name, last_name, email_address, username, password)
VALUES ('Josh', 'McGuire', 'joshmcguire14@gmail.com', 'joshmcguire14', 'password');
INSERT INTO users (first_name, last_name, email_address, username, password)
VALUES ('Emma', 'Grove', 'emmagrove20230@gmail.com', 'emmagrove20230', 'password');
INSERT INTO users (first_name, last_name, email_address, username, password)
VALUES ('Kyle', 'Lohrberg', 'lorbs32@gmail.com', 'lorbs32', 'password');
INSERT INTO users (first_name, last_name, email_address, username, password)
VALUES ('Naomi', 'Ellison', 'NRoseE32@yahoo.com', 'NRoseE32', 'password');
INSERT INTO users (first_name, last_name, email_address, username, password)
VALUES ('Etana', 'Lehman', 'enlehman@wccnet.edu', 'enlehman', 'password');
INSERT INTO users (first_name, last_name, email_address, username, password)
VALUES ('Andrew', 'Redding', 'ajredding@wccnet.edu', 'ajredding', 'password');
INSERT INTO users (first_name, last_name, email_address, username, password)
VALUES ('Mazin', 'Iqbal', 'maziniqbal2005@gmail.com', 'maziniqbal2005', 'password');

-- Date
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date)
VALUES (2025, 'Jan', '2025-01-01', '2025-01-31');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date)
VALUES (2025, 'Feb', '2025-02-01', '2025-02-28');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date)
VALUES (2025, 'Mar', '2025-03-01', '2025-03-31');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date)
VALUES (2025, 'Apr', '2025-04-01', '2025-04-30');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date)
VALUES (2025, 'May', '2025-05-01', '2025-05-31');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date)
VALUES (2025, 'Jun', '2025-06-01', '2025-06-30');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date)
VALUES (2025, 'Jul', '2025-07-01', '2025-07-31');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date)
VALUES (2025, 'Aug', '2025-08-01', '2025-08-31');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date)
VALUES (2025, 'Sep', '2025-09-01', '2025-09-30');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date)
VALUES (2025, 'Oct', '2025-10-01', '2025-10-31');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date)
VALUES (2025, 'Nov', '2025-11-01', '2025-11-30');
INSERT INTO budget_dates (budget_year, budget_month, start_date, end_date)
VALUES (2025, 'Dec', '2025-12-01', '2025-12-31');

-- Budget (budget_id autoincrement to 1 (josh))
INSERT INTO budgets (date_id, user_id)
VALUES (4, 1);
INSERT INTO budgets (date_id, user_id)
VALUES (4, 2);
INSERT INTO budgets (date_id, user_id)
VALUES (4, 3);
INSERT INTO budgets (date_id, user_id)
VALUES (4, 4);
INSERT INTO budgets (date_id, user_id)
VALUES (4, 5);
INSERT INTO budgets (date_id, user_id)
VALUES (4, 6);
INSERT INTO budgets (date_id, user_id)
VALUES (4, 7);

--INSERT INTO budgets (date_id, user_id) -- TEMPORARY
--VALUES (5, 1); -- TEMPORARY

-- Josh (1)
INSERT INTO categories (category_name, budget_id)
VALUES ('Housing', 1);
INSERT INTO categories (category_name, budget_id)
VALUES ('Utilities', 1);
INSERT INTO categories (category_name, budget_id)
VALUES ('Groceries', 1);
INSERT INTO categories (category_name, budget_id)
VALUES ('Entertainment', 1);
INSERT INTO categories (category_name, budget_id)
VALUES ('Transportation', 1);

INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (1, FALSE, 'Rent Payment', 1500, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (2, FALSE, 'Electricity Bill', 120, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (3, FALSE, 'Weekly Food Supplies', 250, 'WEEKLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (4, FALSE, 'Gasoline', 200, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (5, FALSE, 'Netflix Subscription', 20, 'MONTHLY');

-- Emma (2)
INSERT INTO categories (category_name, budget_id)
VALUES ('Housing', 2);
INSERT INTO categories (category_name, budget_id)
VALUES ('Utilities', 2);
INSERT INTO categories (category_name, budget_id)
VALUES ('Groceries', 2);
INSERT INTO categories (category_name, budget_id)
VALUES ('Transportation', 2);
INSERT INTO categories (category_name, budget_id)
VALUES ('Medical', 2);

INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (6, FALSE, 'Rent Payment', 1500, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (7, FALSE, 'Water Bill', 50, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (8, FALSE, 'Grocery Delivery', 100, 'BI_WEEKLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (9, FALSE, 'Uber Rides', 75, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (10, FALSE, 'Doctor Appointment Fee', 100, 'ONE_TIME');

-- Kyle (3)
INSERT INTO categories (category_name, budget_id)
VALUES ('Housing', 3);
INSERT INTO categories (category_name, budget_id)
VALUES ('Utilities', 3);
INSERT INTO categories (category_name, budget_id)
VALUES ('Groceries', 3);
INSERT INTO categories (category_name, budget_id)
VALUES ('Vacation', 3);
INSERT INTO categories (category_name, budget_id)
VALUES ('Investments', 3);

INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (11, FALSE, 'Mortgage Payment', 2000, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (12, FALSE, 'Internet Bill', 80, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (13, FALSE, 'Monthly Groceries', 350, 'ONE_TIME');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (14, FALSE, 'Flight Tickets', 500, 'ONE_TIME');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (15, TRUE, 'Stock Dividend', 200, 'YEARLY');

-- Naomi (4)
INSERT INTO categories (category_name, budget_id)
VALUES ('Housing', 4);
INSERT INTO categories (category_name, budget_id)
VALUES ('Utilities', 4);
INSERT INTO categories (category_name, budget_id)
VALUES ('Groceries', 4);
INSERT INTO categories (category_name, budget_id)
VALUES ('Personal Care', 4);
INSERT INTO categories (category_name, budget_id)
VALUES ('Gifts', 4);

INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (16, FALSE, 'Rent Payment', 1500, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (17, FALSE, 'Gas Bill', 70, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (18, FALSE, 'Organic Food Supplies', 300, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (19, FALSE, 'Haircut', 40, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (20, FALSE, 'Birthday Gift', 100, 'ONE_TIME');

-- Etana (5)
INSERT INTO categories (category_name, budget_id)
VALUES ('Housing', 5);
INSERT INTO categories (category_name, budget_id)
VALUES ('Utilities', 5);
INSERT INTO categories (category_name, budget_id)
VALUES ('Groceries', 5);
INSERT INTO categories (category_name, budget_id)
VALUES ('Insurance', 5);
INSERT INTO categories (category_name, budget_id)
VALUES ('Education', 5);

INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (21, FALSE, 'Housing Maintenance', 500, 'YEARLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (22, FALSE, 'Solar Panel Installment', 300, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (23, FALSE, 'Vitamins and Supplements', 50, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (24, FALSE, 'Tuition Fees', 1000, 'YEARLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (25, TRUE, 'Scholarship', 1500, 'YEARLY');

-- Andrew (6)
INSERT INTO categories (category_name, budget_id)
VALUES ('Housing', 6);
INSERT INTO categories (category_name, budget_id)
VALUES ('Utilities', 6);
INSERT INTO categories (category_name, budget_id)
VALUES ('Groceries', 6);
INSERT INTO categories (category_name, budget_id)
VALUES ('Dining Out', 6);
INSERT INTO categories (category_name, budget_id)
VALUES ('Subscriptions', 6);

INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (26, FALSE, 'Dining at Restaurant', 100, 'WEEKLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (27, FALSE, 'Music Streaming', 10, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (28, FALSE, 'Weekend Snacks', 50, 'WEEKLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (29, TRUE, 'Savings Interest', 30, 'MONTHLY');
INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
VALUES (30, TRUE, 'Charity Donation', 100, 'ONE_TIME');

-- Mazin (7)
INSERT INTO categories (category_name, budget_id)
VALUES ('Housing', 7);
INSERT INTO categories (category_name, budget_id)
VALUES ('Utilities', 7);
INSERT INTO categories (category_name, budget_id)
VALUES ('Groceries', 7);
INSERT INTO categories (category_name, budget_id)
VALUES ('Savings', 7);
INSERT INTO categories (category_name, budget_id)
VALUES ('Charity', 7);

-- ADDITION JOSH (1)
--INSERT INTO categories (category_name, budget_id)
--VALUES ('Housing2', 8);
--INSERT INTO categories (category_name, budget_id)
--VALUES ('Utilities2', 8);
--INSERT INTO categories (category_name, budget_id)
--VALUES ('Groceries2', 8);
--INSERT INTO categories (category_name, budget_id)
--VALUES ('Entertainment2', 8);
--INSERT INTO categories (category_name, budget_id)
--VALUES ('Transportation2', 8);
--
--INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
--VALUES (36, FALSE, 'Rent Payment2', 1500, 'MONTHLY');
--INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
--VALUES (37, FALSE, 'Electricity Bill2', 120, 'MONTHLY');
--INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
--VALUES (38, FALSE, 'Weekly Food Supplies2', 250, 'WEEKLY');
--INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
--VALUES (39, FALSE, 'Gasoline2', 200, 'MONTHLY');
--INSERT INTO line_items (category_id, is_income, line_item_name, planned_amount, recurrence)
--VALUES (40, FALSE, 'Netflix Subscription2', 20, 'MONTHLY');

-- Josh Transactions
INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount)
VALUES (1, '2025-04-03', 'Landlord', 'Rent payment', 1500.00);

INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount)
VALUES (2, '2025-04-05', 'Electric Company', 'Electric bill', 120.00);

INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount)
VALUES (3, '2025-04-07', 'Grocery Store', 'Weekly groceries', 80.00);

INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount)
VALUES (4, '2025-04-08', 'Gas Station', 'Fuel refill', 60.00);

--INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount)
--VALUES (5, '2025-04-10', 'Netflix', 'Monthly subscription', 20.00);

-- Kyle Transactions
INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount)
VALUES (11, '2025-04-03', 'Landlord', 'Rent payment', 1500.00);

INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount)
VALUES (12, '2025-04-05', 'Electric Company', 'Electric bill', 80.00);

INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount)
VALUES (13, '2025-04-07', 'Grocery Store', 'Weekly groceries', 80.00);

INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount)
VALUES (14, '2025-04-08', 'Flight Tickets', 'Miami Trip', 375.00);

INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount)
VALUES (15, '2025-04-10', 'Stocks', 'Monthly subscription', 200.00);

-- Emma Transactions
INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount)
VALUES (6, '2025-04-03', 'Landlord', 'Half Rent payment', 750.00);

INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount)
VALUES (7, '2025-04-05', 'Water', 'water bill', 48.00);

INSERT INTO transactions (line_item_id, transaction_date, merchant, note, actual_amount)
VALUES (10, '2025-04-07', 'Checkup', 'Annual Physical', 89.00);
