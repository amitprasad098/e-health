package com.uos.electronic.healthcare.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Entity
@Table(name = "user_type")
@Data
public class UserType implements GrantedAuthority {
	
	private static final long serialVersionUID = -5095543666283351155L;

	@Id
	@Column(name = "user_type_id")
	private int userTypeId;
	
	public int getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "user_type_name")
	private String userTypeName;

	@Override
	public String getAuthority() {
		return userTypeName;
	}

}
