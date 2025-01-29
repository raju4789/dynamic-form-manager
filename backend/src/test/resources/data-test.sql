MERGE INTO app_user (username, first_name, last_name, email, password, role, record_created_date, record_created_by, is_active)
KEY (username)
VALUES
  ('admin', 'admin', 'admin', 'admin@gmail.com', '$2b$04$oZOGGm.sCkWzwqRAzuWpOezA709GaQs1KZV4xPh69DnnCD4GKSrdW', 'ADMIN', NOW(), 'system', true),
  ('user', 'user', 'user', 'user@gmail.com', '$2b$04$oZOGGm.sCkWzwqRAzuWpOezA709GaQs1KZV4xPh69DnnCD4GKSrdW', 'USER', NOW(), 'system', true);



-- Insert Users
MERGE INTO app_user (username, first_name, last_name, email, password, role, record_created_date, record_created_by, is_active)
KEY (username)
VALUES
  ('admin', 'admin', 'admin', 'admin@gmail.com', '$2b$04$oZOGGm.sCkWzwqRAzuWpOezA709GaQs1KZV4xPh69DnnCD4GKSrdW', 'ADMIN', CURRENT_TIMESTAMP, 'system', true),
  ('user', 'user', 'user', 'user@gmail.com', '$2b$04$oZOGGm.sCkWzwqRAzuWpOezA709GaQs1KZV4xPh69DnnCD4GKSrdW', 'USER', CURRENT_TIMESTAMP, 'system', true);

-- Insert Languages
MERGE INTO languages (language_id, language_code, language_name, created_at, updated_at)
KEY (language_id)
VALUES
(1, 'en', 'English', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'ar', 'Arabic', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Services
MERGE INTO services (service_id, name, created_at, updated_at)
KEY (service_id)
VALUES
(101, 'ABC Bank', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(102, 'XYZ Bank', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(103, 'Global Bank', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(501, 'ABC Wallet', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Fields for Each Service
MERGE INTO fields (field_id, service_id, name, type, validation, max_length, default_value, is_required, created_at, updated_at)
KEY (field_id)
VALUES
-- Fields for ABC Bank
(1, 101, 'amount', 'number', '\\d+', 0, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 101, 'bank_account_number', 'text', '^[A-Za-z0-9 ]{1,64}$', 64, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 101, 'firstname', 'text', '^[\\s\\S]*', 250, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 101, 'lastname', 'text', '^[\\s\\S]*', 250, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fields for XYZ Bank
(5, 102, 'amount', 'number', '\\d+', 0, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 102, 'bank_account_number', 'text', '^[A-Za-z0-9 ]{1,64}$', 64, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 102, 'full_name', 'text', '^[\\s\\S]*', 250, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 102, 'province_state', 'option', '^[\\s\\S]*', 0, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fields for Global Bank
(9, 103, 'amount', 'number', '\\d+', 0, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 103, 'bank_account_number', 'text', '^[A-Za-z0-9 ]{1,64}$', 64, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 103, 'date_of_birth', 'date', '^(?:19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$', 0, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 103, 'gender', 'option', '^[\\s\\S]*', 0, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fields for ABC Wallet
(13, 501, 'amount', 'number', '\\d+', 0, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 501, 'msisdn', 'text', '^\\+?[1-9][0-9]{6,14}$', 14, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 501, 'firstname', 'text', '^[\\s\\S]*', 250, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 501, 'lastname', 'text', '^[\\s\\S]*', 250, '', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Field Translations
MERGE INTO field_translations (field_translation_id, field_id, language_id, label, placeholder, validation_error_message, created_at, updated_at)
KEY (field_translation_id)
VALUES
-- Translations for ABC Bank
(1, 1, 1, 'Amount (AED)', 'Enter amount', 'Amount is required', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 2, 'امل بلغ ( درھم)', 'أخدل امل بلغ', 'امل بلغ م ل طوب', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 1, 'Bank Account Number', 'Enter account number', 'Bank account number is required', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 2, 'رقم ال حس اب املصر في', 'أخدل رقم ال حس اب', 'رقم ال حس اب املصر في م ل طوب', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 3, 1, 'First name', 'Enter first name', 'First name is required', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 3, 2, 'اسلا م اولأل', 'أخدل اسلا م اولأل', 'اسلا م اولأل م ل طوب', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 4, 1, 'Last name', 'Enter last name', 'Last name is required', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 2, 'ا م س اعل ائلة', 'أخدل ا م س اعل ائلة', 'ا م س اعل ائلة م ل طوب', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Field Options
MERGE INTO field_options (field_option_id, field_id, name, label, created_at, updated_at)
KEY (field_option_id)
VALUES
-- Options for XYZ Bank (Province State)
(1, 8, 'abu_dhabi', 'Abu Dhabi', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 8, 'dubai', 'Dubai', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Options for Global Bank (Gender)
(3, 12, 'M', 'Male', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 12, 'F', 'Female', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
