
----Insert Customers
--INSERT IGNORE INTO app_user(username, first_name, last_name, email, password, role, record_created_date, record_created_by, is_active)
--VALUES
--  ('admin', 'admin', 'admin', 'admin@gmail.com', '$2b$04$oZOGGm.sCkWzwqRAzuWpOezA709GaQs1KZV4xPh69DnnCD4GKSrdW', 'ADMIN', NOW(), UUID(), true);
--

-- Insert Languages
INSERT IGNORE INTO languages (language_id, language_code, language_name, created_at, updated_at) VALUES
(1, 'en', 'English', NOW(), NOW()),
(2, 'ar', 'Arabic', NOW(), NOW());

-- ============================================
-- Insert Services
-- ============================================
INSERT IGNORE INTO services (service_id, name, created_at, updated_at) VALUES
(101, 'ABC Bank', NOW(), NOW()),
(102, 'XYZ Bank', NOW(), NOW()),
(103, 'Global Bank', NOW(), NOW()),
(501, 'ABC Wallet', NOW(), NOW());

-- ============================================
-- Insert Fields for Each Service
-- ============================================

-- Fields for ABC Bank
INSERT IGNORE INTO fields (field_id, service_id, name, type, validation, max_length, default_value, is_required, created_at, updated_at) VALUES
(1, 101, 'amount', 'number', '\\d+', 0, '', TRUE, NOW(), NOW()),
(2, 101, 'bank_account_number', 'text', '^[A-Za-z0-9 ]{1,64}$', 64, '', TRUE, NOW(), NOW()),
(3, 101, 'firstname', 'text', '^[\\s\\S]*', 250, '', TRUE, NOW(), NOW()),
(4, 101, 'lastname', 'text', '^[\\s\\S]*', 250, '', TRUE, NOW(), NOW());

-- Fields for XYZ Bank
INSERT IGNORE INTO fields (field_id, service_id, name, type, validation, max_length, default_value, is_required, created_at, updated_at) VALUES
(5, 102, 'amount', 'number', '\\d+', 0, '', TRUE, NOW(), NOW()),
(6, 102, 'bank_account_number', 'text', '^[A-Za-z0-9 ]{1,64}$', 64, '', TRUE, NOW(), NOW()),
(7, 102, 'full_name', 'text', '^[\\s\\S]*', 250, '', TRUE, NOW(), NOW()),
(8, 102, 'province_state', 'option', '^[\\s\\S]*', 0, '', TRUE, NOW(), NOW());

-- Fields for Global Bank
INSERT IGNORE INTO fields (field_id, service_id, name, type, validation, max_length, default_value, is_required, created_at, updated_at) VALUES
(9, 103, 'amount', 'number', '\\d+', 0, '', TRUE, NOW(), NOW()),
(10, 103, 'bank_account_number', 'text', '^[A-Za-z0-9 ]{1,64}$', 64, '', TRUE, NOW(), NOW()),
(11, 103, 'date_of_birth', 'date', '^(?:19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$', 0, '', TRUE, NOW(), NOW()),
(12, 103, 'gender', 'option', '^[\\s\\S]*', 0, '', TRUE, NOW(), NOW());

-- Fields for ABC Wallet
INSERT IGNORE INTO fields (field_id, service_id, name, type, validation, max_length, default_value, is_required, created_at, updated_at) VALUES
(13, 501, 'amount', 'number', '\\d+', 0, '', TRUE, NOW(), NOW()),
(14, 501, 'msisdn', 'text', '^\\+?[1-9][0-9]{6,14}$', 14, '', TRUE, NOW(), NOW()),
(15, 501, 'firstname', 'text', '^[\\s\\S]*', 250, '', TRUE, NOW(), NOW()),
(16, 501, 'lastname', 'text', '^[\\s\\S]*', 250, '', TRUE, NOW(), NOW());

-- ============================================
-- Insert Field Translations
-- ============================================

-- Translations for ABC Bank
INSERT IGNORE INTO field_translations (field_translation_id, field_id, language_id, label, placeholder, validation_error_message, created_at, updated_at) VALUES
(1, 1, 1, 'Amount (AED)', 'Enter amount', 'Amount is required', NOW(), NOW()), -- English
(2, 1, 2, 'امل بلغ ( درھم)', 'أخدل امل بلغ', 'امل بلغ م ل طوب', NOW(), NOW()), -- Arabic
(3, 2, 1, 'Bank Account Number', 'Enter account number', 'Bank account number is required', NOW(), NOW()), -- English
(4, 2, 2, 'رقم ال حس اب املصر في', 'أخدل رقم ال حس اب', 'رقم ال حس اب املصر في م ل طوب', NOW(), NOW()), -- Arabic
(5, 3, 1, 'First name', 'Enter first name', 'First name is required', NOW(), NOW()), -- English
(6, 3, 2, 'اسلا م اولأل', 'أخدل اسلا م اولأل', 'اسلا م اولأل م ل طوب', NOW(), NOW()), -- Arabic
(7, 4, 1, 'Last name', 'Enter last name', 'Last name is required', NOW(), NOW()), -- English
(8, 4, 2, 'ا م س اعل ائلة', 'أخدل ا م س اعل ائلة', 'ا م س اعل ائلة م ل طوب', NOW(), NOW()); -- Arabic

-- Translations for XYZ Bank
INSERT IGNORE INTO field_translations (field_translation_id, field_id, language_id, label, placeholder, validation_error_message, created_at, updated_at) VALUES
(9, 5, 1, 'Amount (AED)', 'Enter amount', 'Amount is required', NOW(), NOW()), -- English
(10, 5, 2, 'امل بلغ ( درھم)', 'أخدل امل بلغ', 'امل بلغ م ل طوب', NOW(), NOW()), -- Arabic
(11, 6, 1, 'Bank Account Number', 'Enter account number', 'Bank account number is required', NOW(), NOW()), -- English
(12, 6, 2, 'رقم ال حس اب املصر في', 'أخدل رقم ال حس اب', 'رقم ال حس اب املصر في م ل طوب', NOW(), NOW()), -- Arabic
(13, 7, 1, 'Full Name', 'Enter full name', 'Full name is required', NOW(), NOW()), -- English
(14, 7, 2, 'اسلا م اكل امل', 'أخدل اسلا م اكل امل', 'اسلا م اكل امل م ل طوب', NOW(), NOW()), -- Arabic
(15, 8, 1, 'Province State', 'Select province', 'Please select a province', NOW(), NOW()), -- English
(16, 8, 2, 'ا م لإ ار ة', 'ا ت خر ا م لإ ار ة', 'یجرى ا ت خیار ا م لإ ار ة', NOW(), NOW()); -- Arabic

-- Translations for Global Bank
INSERT IGNORE INTO field_translations (field_translation_id, field_id, language_id, label, placeholder, validation_error_message, created_at, updated_at) VALUES
(17, 9, 1, 'Amount (AED)', 'Enter amount', 'Amount is required', NOW(), NOW()), -- English
(18, 9, 2, 'امل بلغ ( درھم)', 'أخدل امل بلغ', 'امل بلغ م ل طوب', NOW(), NOW()), -- Arabic
(19, 10, 1, 'Bank Account Number', 'Enter account number', 'Bank account number is required', NOW(), NOW()), -- English
(20, 10, 2, 'رقم ال حس اب املصر في', 'أخدل رقم ال حس اب', 'رقم ال حس اب املصر في م ل طوب', NOW(), NOW()), -- Arabic
(21, 11, 1, 'Date of Birth (YYYY-MM-DD)', 'Enter date of birth', 'Date of birth is required', NOW(), NOW()), -- English
(22, 11, 2, 'تا ر یخ امل یلاد ( یمو- شھر- سن ة)', 'أخدل تا ر یخ امل یلاد', 'تا ر یخ امل یلاد م ل طوب', NOW(), NOW()), -- Arabic
(23, 12, 1, 'Gender', 'Select gender', 'Gender is required', NOW(), NOW()), -- English
(24, 12, 2, 'ال ج نس', 'ا ت خر ال ج نس', 'ال ج نس م ل طوب', NOW(), NOW()); -- Arabic

-- ============================================
-- Insert Field Options
-- ============================================

-- Options for XYZ Bank (Province State)
INSERT IGNORE INTO field_options (field_option_id, field_id, name, label, created_at, updated_at) VALUES
(1, 8, 'abu_dhabi', 'Abu Dhabi', NOW(), NOW()),
(2, 8, 'dubai', 'Dubai', NOW(), NOW());

-- Options for Global Bank (Gender)
INSERT IGNORE INTO field_options (field_option_id, field_id, name, label, created_at, updated_at) VALUES
(3, 12, 'M', 'Male', NOW(), NOW()),
(4, 12, 'F', 'Female', NOW(), NOW());