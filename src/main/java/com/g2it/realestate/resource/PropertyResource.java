package com.g2it.realestate.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.g2it.realestate.exception.ApplicationException;
import com.g2it.realestate.model.Property;
import com.g2it.realestate.model.PropertyImage;
import com.g2it.realestate.service.PropertyService;
import com.g2it.realestate.utils.Secured;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "property", tags = "Property Management")
@RequestMapping(value = "/api/property")
@Slf4j
public class PropertyResource {

	@Autowired
	private PropertyService propertyService;

	@ApiOperation(value = "Get Property Content", notes = "It will return all Property List from the Property.", response = Property.class)
	@GetMapping("/findAll")
	@Secured
	public ResponseEntity<List<Property>> getAllProperty() {
		log.info("Fetching Property List");
		List<Property> property = propertyService.getAllProperty();
		log.info("Property List fetched successfully");
		return ResponseEntity.ok().body(property);
	}
	
	@ApiOperation(value = "Get Property Content", notes = "It will return all Property List from the Property.", response = Property.class)
	@GetMapping("/findAllByUserId")
	@Secured
	public ResponseEntity<List<Property>> getAllProperty(@RequestParam Long id) {
		log.info("Fetching Property List");
		List<Property> property = propertyService.getAllPropertyByUser(id);
		log.info("Property List fetched successfully");
		return ResponseEntity.ok().body(property);
	}

	@ApiOperation(value = "Add new Property", notes = "It will create a new Property in Property List. Please provide unique Property details.")
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	@Secured
	public ResponseEntity<String> addProperty(@ModelAttribute Property property,
			@RequestParam(value = "files", required = false) List<MultipartFile> files) {
		log.info("Adding new Property");
		List<PropertyImage> images = new ArrayList<PropertyImage>();
		if (files != null) {
			for (MultipartFile file : files) {
				PropertyImage image = new PropertyImage();
				try {
					image.setImage(file.getBytes());
					image.setName(file.getOriginalFilename());
				} catch (IOException e) {
					log.error("Image Not Found");
					return new ResponseEntity<>("{\"error\":\"Image Not Found. Please select image.\"}",
							HttpStatus.NOT_FOUND);
				}
				images.add(image);
			}
			property.setPropertyImage(images);
		}
		propertyService.addProperty(property);
		log.info("New Property added successfully");
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Delete Property", notes = "It will delete a Property from Property List. Please provide valid propertyId which you have to delete.")
	@DeleteMapping("{propertyId}")
	@Secured
	public ResponseEntity<String> deleteProperty(@PathVariable Long propertyId) {
		log.info("Deleting Property");
		try {
			propertyService.deletePropertyById(propertyId);
		} catch (ApplicationException ae) {
			return new ResponseEntity<>("{\"error\":\"Property not found: Couldn't delete Property.\"}", HttpStatus.NOT_FOUND);
		}
		log.info("Property deleted successfully");
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Update Property", notes = "It will update the existing property which is already in List. Please provide valid updated details of Property.")
	@PutMapping
	@Secured
	public ResponseEntity<String> updateProperty(@RequestBody Property property) {
		log.info("udpating Property");
		try {
			propertyService.updateProperty(property);
		} catch (ApplicationException ae) {
			return new ResponseEntity<>("{\"error\":\"Property not found: Couldn't update Property Details.\"}", HttpStatus.NOT_MODIFIED);
		}
		log.info("property details updated successfully");
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Update Property Image", notes = "It will update the property Image by image id of property.")
	@PutMapping("/updateImage")
	@Secured
	public ResponseEntity<String> updatePropertyImageById(@ModelAttribute PropertyImage propertyImage,
			@RequestParam(value = "file", required = true) MultipartFile file) {
		log.info("udpating Property Image by Image Id");
		if (file != null) {
			try {
				propertyImage.setImage(file.getBytes());
				propertyImage.setName(file.getOriginalFilename());
			} catch (IOException ae) {
				log.error("Property Image Not Found");
				return new ResponseEntity<>("{\"error\":\"Property Image Not Found. Please select image.\"}",
						HttpStatus.NOT_FOUND);
			}
			try {
				propertyService.updatePropertyImageById(propertyImage);
			} catch (ApplicationException ae) {
				return new ResponseEntity<>("{\"error\":\"Property not found: Couldn't update property Image.\"}", HttpStatus.NOT_FOUND);
			}
			log.info("property image updated successfully");
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Add new Image", notes = "It will add new image to property. Please provide valid propertyId and images.")
	@PostMapping("/addNewImage")
	@Secured
	public ResponseEntity<String> addNewImages(@ModelAttribute Property property,
			@RequestParam(value = "files", required = true) List<MultipartFile> files) {
		log.info("adding new property images");
		List<PropertyImage> images = new ArrayList<PropertyImage>();
		for (MultipartFile file : files) {
			PropertyImage image = new PropertyImage();
			try {
				image.setImage(file.getBytes());
				image.setName(file.getOriginalFilename());
			} catch (ApplicationException | IOException ae) {
				log.error("Property Image Not Found");
				return new ResponseEntity<>("{\"error\":\"Property Image Not Found. Please select image.\"}",
						HttpStatus.NOT_FOUND);
			}
			images.add(image);
		}
		property.setPropertyImage(images);
		try {
			propertyService.addNewImages(property);
		} catch (ApplicationException ae) {
			return new ResponseEntity<>("{\"error\":\"Property not found: Couldn't add new images.\"}", HttpStatus.NOT_FOUND);
		}
		log.info("property Image added successfully");
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Delete Image", notes = "It will delete the image of the property by Image Id. Please provide valid ImageID")
	@DeleteMapping("{propertyId}/{imageId}")
	@Secured
	public ResponseEntity<String> deleteImage(@PathVariable Long propertyId, @PathVariable Long imageId) {
		log.info("Deleting Property Image");
		try {
			propertyService.deleteImageById(imageId, propertyId);
		} catch (ApplicationException ae) {
			return new ResponseEntity<>("{\"error\":\"Property not found: Couldn't delete images.\"}", HttpStatus.NOT_FOUND);
		}
		log.info("Property Image deleted successfully");
			return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "Get Property by name", notes = "It will return Property based on name.", response = Property.class)
	@GetMapping("/byName")
	@Secured
	public ResponseEntity<Property> getPropertyByName(@RequestParam String name) {
		log.info("Resource: Fetching Property based on name");
		Property property = propertyService.findPropertyByName(name);
		log.info("Resource: Property fetched successfully based on name");
		return ResponseEntity.ok().body(property);
	}
}
