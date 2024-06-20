package com.uos.electronic.healthcare.constant;

public class Constants {
	
	private Constants() {}
	
	public static final String ADMINT_USER_ROLE = "Admin";
	public static final String DOCTOR_USER_ROLE = "Doctor";
	public static final String PATIENT_USER_ROLE = "Patient";
	public static final String PRACTITIONER_USER_ROLE = "Practitioner";
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final int FIRST_INDEX_ELEMENT = 0;
	
	public static final String RESET_PASSWORD_EMAIL_SUBJECT = "OTP to Reset Password for E-Health";
	public static final String ALL_DIGITS = "0123456789";
	
	public static final String INVALID_INPUT_EXCEPTION_CODE = "Invalid Input";
	public static final String USER_DOES_NOT_EXIST_MESSAGE = "User does not exist";
	public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid credentials";
	public static final String REQUEST_DOES_NOT_EXIST_MESSAGE = "Request does not exist";
	public static final String PRACTICE_DOES_NOT_EXIST_MESSAGE = "Practice does not exist";
	public static final String DOCTOR_DOES_NOT_EXIST_MESSAGE = "Doctor does not exist";
	public static final String REFERRED_DOCTOR_DOES_NOT_EXIST_MESSAGE = "Referred Doctor does not exist";
	public static final String PATIENT_PRACTICE_REGISTRATION_DOES_NOT_EXIST_MESSAGE = "The Patient is not registered to the Practice";
	public static final String APPOINTMENT_DOES_NOT_EXIST_MESSAGE = "No appointment exists for the provided appointment id";
	public static final String ORDERED_TEST_DOES_NOT_EXIST_MESSAGE = "No ordered test exists for the provided appointment id";
	public static final String EMAIL_ADDRESS_DOES_NOT_EXIST = "Provided email does not exist";
	public static final String EMAIL_AUDIT_TYPE_DOES_NOT_EXIST = "Email audit type does not exist";
	public static final String DATE_EXTRACTION_EXCEPTION_CODE = "Date Extraction Error";
	public static final String DATE_EXTRACTION_EXCEPTION_MESSAGE = "Failed to extract provided date";
	public static final String OTP_VALIDATION_SUCCESS_MESSAGE = "OTP validated successfully";
	public static final String OTP_VALIDATION_FAILED_MESSAGE = "Incorrect OTP";
	public static final String REQUEST_ALREADY_EXIST_EXCEPTION_CODE = "Request Already Exist";
	public static final String PATIENT_ALREADY_REGISTED = "Patient is already registered";
	public static final String NO_AVAILABLE_STAFF_EXCEPTION_CODE = "Unavailable Staff";
	public static final String PRACTITIONER_DOES_NOT_EXIST = "No Practitioner is available for the selected Practice";
	public static final String DOCTOR_DOES_NOT_EXIST = "No Doctor is available for the selected Appointment";
	public static final String DUPLICATE_ENTRY_EXCEPTION_CODE = "Record Already Exist";
	public static final String APPOINTMENT_PRESCRIPTION_ALREADY_EXIST = "Prescription already exists for the give appointment booking";
	public static final String REFERRAL_ALREADY_EXIST = "Specialist referral already exists for the give appointment booking";
	public static final String ORDERED_TEST_ALREADY_EXIST = "Ordered test already exists for the give appointment booking";
	
	public static final String REQUEST_APPROVED = "Request approved";
	public static final String REQUEST_DECLINED = "Request declined";
	
	public static final String PENDING_STATUS = "PENDING";
	public static final String APPROVED_STATUS = "APPROVED";
	public static final String DECLINED_STATUS = "DECLINED";
	public static final String UNAVAILABLE_STATUS = "UNAVAILABLE";
	public static final String ALTERNATE_STATUS = "ALTERNATE";
	public static final String EMPTY_STATUS = "EMPTY";
	public static final String LOGOUT_SUCCESSFUL = "Logout successful";
	
	public static final String EMAIL_SUBJECT_REGISTER_USER = "Thank you for registering to E-Health";
	public static final String EMAIL_SUBJECT_REGISTER_TO_PRACTICE = "Registration to the Practice Request Confirmation";
	public static final String EMAIL_SUBJECT_REGISTRATION_APPROVED = "Registration to the Practice has been Approved";
	public static final String EMAIL_SUBJECT_REGISTRATION_DECLINED = "Registration to the Practice has been Declined";
	public static final String EMAIL_SUBJECT_APPOINTMENT_BOOKING = "E-Health Appointment Booking Confirmation";
	public static final String EMAIL_SUBJECT_GDPR_VIEW_REQUEST = "GDPR - View Patient Data";
	public static final String EMAIL_SUBJECT_GDPR_UPDATE_REQUEST = "GDPR - Update Patient Data";
	public static final String EMAIL_SUBJECT_GDPR_DELETE_REQUEST = "GDPR - Delete Patient Data";
	public static final String EMAIL_SUBJECT_APPOINTMENT_APPROVED = "Appointment with the Practice has been Approved";
	public static final String EMAIL_SUBJECT_APPOINTMENT_DECLINED = "Appointment with the Practice has been Declined";
	public static final String EMAIL_SUBJECT_APPOINTMENT_ALTERNATE = "Alternate Suggested for the Appointment";
	public static final String EMAIL_SUBJECT_MEDICAL_HISTORY_UPLOAD = "E-Health - Medical History Uploaded";
	public static final String EMAIL_SUBJECT_PRESCRIPTION_UPLOAD = "E-Health - Prescription Uploaded";
	public static final String EMAIL_SUBJECT_TEST_ORDERED = "E-Health - Test Ordered";
	public static final String EMAIL_SUBJECT_SPECIALIST_REFERRAL = "E-Health - Specialist Referral";
	public static final String EMAIL_SUBJECT_ORDERED_TEST_RESULT = "E-Health - Ordered Test Result";
	
	
	public static final String EMAIL_AUDIT_TYPE_TEST_RESULT = "Test Result";
	public static final String EMAIL_AUDIT_TYPE_GDPR_VIEW_REQUEST = "GDPR View Request";
	

}
