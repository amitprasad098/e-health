package com.uos.electronic.healthcare.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientPracticeResponseBean {
	
	public int getPracticeId() {
		return practiceId;
	}
	public void setPracticeId(int practiceId) {
		this.practiceId = practiceId;
	}
	public String getPracticeName() {
		return practiceName;
	}
	public void setPracticeName(String practiceName) {
		this.practiceName = practiceName;
	}
	public String getPracticeDescription() {
		return practiceDescription;
	}
	public void setPracticeDescription(String practiceDescription) {
		this.practiceDescription = practiceDescription;
	}
	public String getPracticeAddress() {
		return practiceAddress;
	}
	public void setPracticeAddress(String practiceAddress) {
		this.practiceAddress = practiceAddress;
	}
	public String getPracticeContactDetails() {
		return practiceContactDetails;
	}
	public void setPracticeContactDetails(String practiceContactDetails) {
		this.practiceContactDetails = practiceContactDetails;
	}
	public String getPracticeImageUrl() {
		return practiceImageUrl;
	}
	public void setPracticeImageUrl(String practiceImageUrl) {
		this.practiceImageUrl = practiceImageUrl;
	}
	public String getPatientRegistrationStatus() {
		return patientRegistrationStatus;
	}
	public void setPatientRegistrationStatus(String patientRegistrationStatus) {
		this.patientRegistrationStatus = patientRegistrationStatus;
	}
	private int practiceId;
	private String practiceName;
	private String practiceDescription;
	private String practiceAddress;
	private String practiceContactDetails;
	private String practiceImageUrl;
	private String patientRegistrationStatus;

}
