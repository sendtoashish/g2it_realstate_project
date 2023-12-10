package com.g2it.realestate.service;

import java.util.List;

import com.g2it.realestate.model.Client;
import com.g2it.realestate.model.Property;
import com.g2it.realestate.model.PropertyImage;

public interface PropertyService {

	public void addClient(Client client);

	public List<Client> getAllClient();

	public void deleteClientById(Long clientId);

	public void updateClient(Client client);
	
	public void updateClientStatus(Client client);

	public void addProperty(Property property);

	public List<Property> getAllProperty();

	public void deletePropertyById(Long propertyId);

	public void updateProperty(Property property);

	public void updatePropertyStatus(Property property);
	
	public void updatePropertyImageById(PropertyImage propertyImage);
	
	public void addNewImages(Property property);

	public void deleteImageById(Long imageId, Long propertyId);

	public Client findClientByEmail(String email);

	public Property findPropertyByName(String name);

	public List<Client> getAllClientByUser(Long id);

	public List<Property> getAllPropertyByUser(Long id);
}
