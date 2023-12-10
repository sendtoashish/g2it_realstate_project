package com.g2it.realestate.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.g2it.realestate.utils.PasswordUtils;

import lombok.Data;

@Data
@Entity(name = "user_details")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	private String password;
	
	private String email;
	
	@Column(name = "mobile_no")
	private String mobileNo;
	
	private String address;
	
	private String city;
	
	private String zip;
	
	private boolean active;
	
	@OneToOne
	@JoinColumn(name = "ROLE_ID")
	private Role role;
	
	@OneToOne
	@JoinColumn(name = "COMPANY_ID")
	private Company company;
		
	@PrePersist
    @PreUpdate
    private void preProcess() {
        password = PasswordUtils.encrypt(password);
    }
	
	@Column(name = "submitted_on")
	private Timestamp submittedOn;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;
}
