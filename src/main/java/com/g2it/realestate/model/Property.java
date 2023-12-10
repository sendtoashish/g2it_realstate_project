package com.g2it.realestate.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity(name = "property_details")
public class Property implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "property_owner_name")
	private String propertyOwner;
	
	@OneToOne
	@JoinColumn(name = "USER_ID")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	private User user;
	
	@Column(name = "property_name")
	private String propertyName;
	
	@Column(name = "property_code")
	private String propertyCode;
	
	private String street;
	
	@Column(name = "street_number")
	private String streetNumber;
	
	private String city;
	
	private String zip;
	
	private Long price;
	
	private boolean active;
	
	@Column(name = "property_status")
	private String propertyStatus;
	
	@Column(name = "submitted_on")
	private Timestamp submittedOn;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;
	
	@OneToMany
	private List<PropertyImage> propertyImage;

}
