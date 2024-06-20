DROP database electronic_healthcare;

create database electronic_healthcare;

use electronic_healthcare;

create table user_type
(
    user_type_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_type_name varchar(20) NOT NULL
);

create table email_audit_type
(
    email_audit_type_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email_audit_type_name varchar(30) NOT NULL
);

create table practice
(
    practice_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    practice_name varchar(100) NOT NULL,
    practice_description text NOT NULL,
    practice_address text NOT NULL,
    practice_contact_details varchar(50) NOT NULL,
    practice_image_url varchar(250)
);

create table user
(
    user_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_type_id int NOT NULL,
    user_full_name varchar(100) NOT NULL,
    user_email varchar(50) NOT NULL,
    user_password varchar(100) NOT NULL,
    CONSTRAINT `user_fk1` FOREIGN KEY (`user_type_id`) REFERENCES user_type (`user_type_id`)
);

create table admin 
(
    admin_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id int NOT NULL,
    CONSTRAINT `admin_fk1` FOREIGN KEY (`user_id`) REFERENCES user (`user_id`)
);

create table patient 
(
    patient_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id int NOT NULL,
    patient_address text NOT NULL,
    patient_date_of_birth date NOT NULL,
    patient_gender varchar(20) NOT NULL,
    patient_contact_number varchar(20),
    CONSTRAINT `patient_fk1` FOREIGN KEY (`user_id`) REFERENCES user (`user_id`)
);

create table doctor 
(
    doctor_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id int NOT NULL,
    practice_id int NOT NULL,
    doctor_degree varchar(100) NOT NULL,
    doctor_speciality varchar(100) NOT NULL,
    CONSTRAINT `doctor_fk1` FOREIGN KEY (`user_id`) REFERENCES user (`user_id`),
    CONSTRAINT `doctor_fk2` FOREIGN KEY (`practice_id`) REFERENCES practice (`practice_id`)
);

create table practitioner 
(
    practitioner_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id int NOT NULL,
    practice_id int NOT NULL,
    practitioner_degree varchar(100) NOT NULL,
    CONSTRAINT `practitioner_fk1` FOREIGN KEY (`user_id`) REFERENCES user (`user_id`),
    CONSTRAINT `practitioner_fk2` FOREIGN KEY (`practice_id`) REFERENCES practice (`practice_id`)
);

create table patient_practice_registration
(
    patient_practice_registration_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    patient_id int NOT NULL,
    practice_id int NOT NULL,
    registration_status varchar(20) NOT NULL,
    registration_date date NOT NULL,
    CONSTRAINT `patient_practice_registration_fk1` FOREIGN KEY (`patient_id`) REFERENCES patient (`patient_id`),
    CONSTRAINT `patient_practice_registration_fk2` FOREIGN KEY (`practice_id`) REFERENCES practice (`practice_id`)
);

create table appointment_booking
(
    appointment_booking_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    patient_practice_registration_id int NOT NULL,
    practitioner_id int NOT NULL,
    doctor_id int,
    appointment_details text NOT NULL,
    appointment_status varchar(20) NOT NULL,
    practitioner_feedback text,
    appointment_date date NOT NULL,
    referred_doctor_id int,
    CONSTRAINT `appointment_booking_fk1` FOREIGN KEY (`patient_practice_registration_id`) REFERENCES patient_practice_registration (`patient_practice_registration_id`),
    CONSTRAINT `appointment_booking_fk2` FOREIGN KEY (`practitioner_id`) REFERENCES practitioner (`practitioner_id`),
    CONSTRAINT `appointment_booking_fk3` FOREIGN KEY (`doctor_id`) REFERENCES doctor (`doctor_id`),
    CONSTRAINT `appointment_booking_fk4` FOREIGN KEY (`referred_doctor_id`) REFERENCES doctor (`doctor_id`)
);

create table medical_history
(
    medical_history_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    appointment_booking_id int NOT NULL,
    medical_history_details text NOT NULL,
    medical_history_date date NOT NULL,
    CONSTRAINT `medical_history_fk1` FOREIGN KEY (`appointment_booking_id`) REFERENCES appointment_booking (`appointment_booking_id`)
);

create table prescription
(
    prescription_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    appointment_booking_id int NOT NULL,
    prescription_details text NOT NULL,
    prescription_date date NOT NULL,
    CONSTRAINT `prescription_fk1` FOREIGN KEY (`appointment_booking_id`) REFERENCES appointment_booking (`appointment_booking_id`)
);

create table ordered_test
(
    ordered_test_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    appointment_booking_id int NOT NULL,
    ordered_test_details text NOT NULL,
    ordered_test_date date NOT NULL,
    CONSTRAINT `ordered_test_fk1` FOREIGN KEY (`appointment_booking_id`) REFERENCES appointment_booking (`appointment_booking_id`)
);

create table specialist_referral
(
    specialist_referral_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    appointment_booking_id int NOT NULL,
    referred_doctor_id int NOT NULL,
    specialist_referral_details text NOT NULL,
    specialist_referral_date date NOT NULL,
    CONSTRAINT `specialist_referral_fk1` FOREIGN KEY (`appointment_booking_id`) REFERENCES appointment_booking (`appointment_booking_id`),
    CONSTRAINT `specialist_referral_fk2` FOREIGN KEY (`referred_doctor_id`) REFERENCES doctor (`doctor_id`)
);

create table email_audit
(
    email_audit_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email_audit_type_id int NOT NULL,
    email_sender_id int NOT NULL,
    email_recipient_id int NOT NULL,
    email_subject text NOT NULL,
    email_message text NOT NULL,
    CONSTRAINT `email_audit_fk1` FOREIGN KEY (`email_audit_type_id`) REFERENCES email_audit_type (`email_audit_type_id`),
    CONSTRAINT `email_audit_fk2` FOREIGN KEY (`email_sender_id`) REFERENCES user (`user_id`),
    CONSTRAINT `email_audit_fk3` FOREIGN KEY (`email_recipient_id`) REFERENCES user (`user_id`)
);

create table pharmacy
(
    pharmacy_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    pharmacy_opening_times varchar(20) NOT NULL,
    pharmacy_name varchar(50) NOT NULL,
    pharmacy_address varchar(100) NOT NULL,
    pharmacy_contact_number varchar(20) NOT NULL,
    pharmacy_map_url varchar(400) NOT NULL,
    pharmacy_city varchar(20) NOT NULL
);