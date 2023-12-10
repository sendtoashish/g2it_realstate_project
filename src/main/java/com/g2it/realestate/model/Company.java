package com.g2it.realestate.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "company_details")
public class Company implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "company_id")
	private String companyId;
	
	@Column(name = "vat_id")
	private String vatId;
	
	@Column(name = "tax_id")
	private String taxId;
	
	private String street;
	
	@Column(name = "street_number")
	private String streetNumber;
	
	private String city;
	
	private int zip;
	
	private String email;
	
	private String phone;
	
	private String logo;
	
	private boolean active;
	
	@Column(name = "company_type")
	private String companyType;
	
	@Column(name = "submitted_on")
	private Timestamp submittedOn;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;
}
