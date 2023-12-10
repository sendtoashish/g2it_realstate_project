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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity(name = "client_details")
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	private String email;
	
	@Column(name = "mobile_no")
	private String mobileNo;
	
	private boolean active;

	@Column(name = "submitted_on")
	private Timestamp submittedOn;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;
	
	@OneToOne
	@JoinColumn(name = "USER_ID")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	private User user;
}
