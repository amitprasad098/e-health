package com.uos.electronic.healthcare.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "practice")
@Data
public class Practice {

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

	@Id
	@Column(name = "practice_id")
	private int practiceId;
	
	@Column(name = "practice_name")
	private String practiceName;
	
	@Column(name = "practice_description")
	private String practiceDescription;
	
	@Column(name = "practice_address")
	private String practiceAddress;
	
	@Column(name = "practice_contact_details")
	private String practiceContactDetails;
	
	@Column(name = "practice_image_url")
	private String practiceImageUrl;
	
	

}
