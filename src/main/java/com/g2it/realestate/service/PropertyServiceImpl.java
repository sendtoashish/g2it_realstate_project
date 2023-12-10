package com.g2it.realestate.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.g2it.realestate.exception.ApplicationException;
import com.g2it.realestate.exception.ErrorStatus;
import com.g2it.realestate.model.Client;
import com.g2it.realestate.model.Property;
import com.g2it.realestate.model.PropertyImage;
import com.g2it.realestate.model.User;
import com.g2it.realestate.repository.ClientDAO;
import com.g2it.realestate.repository.PropertyDAO;
import com.g2it.realestate.repository.PropertyImageDAO;
import com.g2it.realestate.repository.UserDAO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PropertyServiceImpl implements PropertyService {

	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private PropertyDAO propertyDAO;
	
	@Autowired
	private PropertyImageDAO propertyImageDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Transactional("transactionManager")
	public void addClient(Client client) {
		log.info("Service started: save client details");
		clientDAO.save(client);
		log.info("Service end: Client details saved successfully");
	}
	
	@Override
	@Transactional("transactionManager")
	public Client findClientByEmail(String email) {
		log.info("Service started: get client details by email");
		Client client = clientDAO.findByEmail(email);
		log.info("Service end: client details fetched successfully  by email");
		return client;
	}

	@Override
	@Transactional("transactionManager")
	public List<Client> getAllClient() {
		log.info("Service started: get all client details");
		List<Client> clientList = clientDAO.findAll();
		log.info("Service end: client details fetched successfully");
		return clientList;
	}
	
	@Override
	@Transactional("transactionManager")
	public List<Client> getAllClientByUser(Long id) {
		log.info("Service started: get all client details by user");
		List<Client> clientList = null;
		User user = userDAO.getById(id);
		if(user != null && user.getRole().getRoleId() == 1) {
			clientList = clientDAO.findAll();
		}else if(user != null && user.getRole().getRoleId() == 2) {
			clientList = clientDAO.findByCompanyId(user.getCompany().getId());
		}else {
			clientList = clientDAO.findByUserId(user.getId());
		}
		log.info("Service end: client details fetched successfully by user");
		return clientList;
	}

	@Override
	@Transactional("transactionManager")
	public void deleteClientById(Long clientId) {
		log.info("Service started: delete client details by client id: " + clientId);
		clientDAO.deleteById(clientId);
		log.info("property deleted successfully : " + clientId);

	}
	
	@Override
	@Transactional("transactionManager")
	public void updateClient(Client client) {
		log.info("Service started: update client details");
		Optional<Client> cli = clientDAO.findById(client.getId());
		if(cli.isEmpty()) {
            throw new ApplicationException(ErrorStatus.NOT_FOUND, "client details not found by property id: " + client.getId());
        }
		cli.get().setFirstName(client.getFirstName());
		cli.get().setLastName(client.getLastName());
		cli.get().setEmail(client.getEmail());
		cli.get().setMobileNo(client.getMobileNo());
		cli.get().setModifiedOn(new Timestamp(System.currentTimeMillis()));
		clientDAO.save(cli.get());
		log.info("Service end: client details updated successfully");
	}
	
	@Override
	@Transactional("transactionManager")
	public void updateClientStatus(Client client) {
		log.info("Service started: update client status");
		Optional<Client> cli = clientDAO.findById(client.getId());
		if(cli.isEmpty()) {
            throw new ApplicationException(ErrorStatus.NOT_FOUND, "client details not found by property id: " + client.getId());
        }
		cli.get().setActive(client.isActive());
		clientDAO.save(cli.get());
		log.info("Service end: client status updated successfully");
	}
	
	
	@Override
	@Transactional("transactionManager")
	public void addProperty(Property property) {
		log.info("Service started: save property details");
		property.setSubmittedOn(new Timestamp(System.currentTimeMillis()));
		propertyDAO.save(property);
		propertyImageDAO.saveAll(property.getPropertyImage());
		log.info("Service end: Property details saved successfully");
	}
	
	@Override
	@Transactional("transactionManager")
	public Property findPropertyByName(String name) {
		log.info("Service started: get property details by name");
		Property property = propertyDAO.findByName(name);
		log.info("Service end: property details fetched successfully by name");
		return property;
	}

	@Override
	@Transactional("transactionManager")
	public List<Property> getAllProperty() {
		log.info("Service started: get all property details");
		List<Property> propertyList = propertyDAO.findAll();
		log.info("Service end: property details fetched successfully");
		return propertyList;
	}

	@Override
	@Transactional("transactionManager")
	public void deletePropertyById(Long propertyId) {
		log.info("Service started: delete property details by property id: " + propertyId);
		Optional<Property> pro = propertyDAO.findById(propertyId);
		if(pro.isEmpty()) {
            throw new ApplicationException(ErrorStatus.NOT_FOUND, "property details not found by property id: " + propertyId);
        }
		List<PropertyImage> dbimages = pro.get().getPropertyImage();
		if(dbimages!=null) {
		List<Long> ids = new ArrayList<Long>();
		for(PropertyImage i: dbimages) {
			ids.add(i.getId());
		}
		propertyImageDAO.deleteAllById(ids);
		}
		propertyDAO.deleteById(propertyId);
		log.info("property deleted successfully : " + propertyId);

	}
	
	@Override
	@Transactional("transactionManager")
	public void updateProperty(Property property) {
		log.info("Service started: update property details");
		Optional<Property> pro = propertyDAO.findById(property.getId());
		if(pro.isEmpty()) {
            throw new ApplicationException(ErrorStatus.NOT_FOUND, "property details not found by property id: " + property.getId());
        }
		
		pro.get().setPropertyOwner(property.getPropertyOwner());
		pro.get().setUser(property.getUser());
		pro.get().setPropertyName(property.getPropertyName());
		pro.get().setStreet(property.getStreet());
		pro.get().setStreetNumber(property.getStreetNumber());
		pro.get().setCity(property.getCity());
		pro.get().setZip(property.getZip());
		pro.get().setPrice(property.getPrice());
		pro.get().setModifiedOn(new Timestamp(System.currentTimeMillis()));
		
		propertyDAO.save(pro.get());
		
		log.info("Service end: property details updated successfully");
	}
	
	@Override
	@Transactional("transactionManager")
	public void updatePropertyStatus(Property property) {
		log.info("Service started: update property status");
		Optional<Property> pro = propertyDAO.findById(property.getId());
		if(pro.isEmpty()) {
            throw new ApplicationException(ErrorStatus.NOT_FOUND, "property details not found by property id: " + property.getId());
        }
		pro.get().setActive(property.isActive());
		propertyDAO.save(pro.get());
		log.info("Service end: property status updated successfully");
	}

	@Override
	@Transactional("transactionManager")
	public void updatePropertyImageById(PropertyImage propertyImage) {
		log.info("Service started: update property Image");
		Optional<PropertyImage> proImg = propertyImageDAO.findById(propertyImage.getId());
		if(proImg.isEmpty()) {
            throw new ApplicationException(ErrorStatus.NOT_FOUND, "property image not found by id: " + propertyImage.getId());
        }
		proImg.get().setImage(propertyImage.getImage());
		proImg.get().setName(propertyImage.getName());
		propertyImageDAO.save(proImg.get());
	}

	@Override
	@Transactional("transactionManager")
	public void addNewImages(Property property) {
		log.info("Service started: add new property image");
		Optional<Property> pro = propertyDAO.findById(property.getId());
		if(pro.isEmpty()) {
            throw new ApplicationException(ErrorStatus.NOT_FOUND, "property details not found by property id: " + property.getId());
        }
		List<PropertyImage> propimg = new ArrayList<PropertyImage>();
		log.debug("adding new image in property table");
		propimg.addAll(pro.get().getPropertyImage());
		propimg.addAll(property.getPropertyImage());
		pro.get().setPropertyImage(propimg);
		log.debug("saving new image in DB");
		propertyImageDAO.saveAll(property.getPropertyImage());
		propertyDAO.save(pro.get());
		log.info("Service end: property image added successfully");
		
	}

	@Override
	@Transactional("transactionManager")
	public void deleteImageById(Long imageId, Long propertyId) {
		log.info("Service started: delete property Image by imageId: " + imageId);
		Optional<Property> pro = propertyDAO.findById(propertyId);
		if(pro.isEmpty()) {
            throw new ApplicationException(ErrorStatus.NOT_FOUND, "property details not found by id: " + propertyId);
        }
		List<PropertyImage> proimages = pro.get().getPropertyImage();
		for(PropertyImage p: proimages) {
			if(p.getId()==imageId) {
				log.debug("removing image from property table");
				proimages.remove(p);
				break;
			}
		}
		pro.get().setPropertyImage(proimages);
		propertyImageDAO.deleteById(imageId);
		propertyDAO.save(pro.get());
		log.info("property image deleted successfully : " + imageId);	
	}
	
	@Override
	@Transactional("transactionManager")
	public List<Property> getAllPropertyByUser(Long id) {
		log.info("Service started: get all Property details by user");
		List<Property> propertyList = null;
		User user = userDAO.getById(id);
		if(user != null && user.getRole().getRoleId() == 1) {
			propertyList = propertyDAO.findAll();
		}else if(user != null && user.getRole().getRoleId() == 2) {
			propertyList = propertyDAO.findByCompanyId(user.getCompany().getId());
		}else {
			propertyList = propertyDAO.findByUserId(user.getId());
		}
		log.info("Service end: Property details fetched successfully by user");
		return propertyList;
	}
}
