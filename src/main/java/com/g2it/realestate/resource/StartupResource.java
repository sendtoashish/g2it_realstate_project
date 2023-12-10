package com.g2it.realestate.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.g2it.realestate.service.StartupService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StartupResource {

	@Autowired
	private StartupService startupService;
	
	@EventListener(ApplicationReadyEvent.class)
	private void addDefaultEntry() {
		log.info("Resource Started: Save Default Entry");
		startupService.saveDefaultRoles();
		startupService.saveDefaultCompany();
		startupService.saveDefaultUser();
		log.info("Resource Ended: Default Entry saved Successfully");
	}
}
