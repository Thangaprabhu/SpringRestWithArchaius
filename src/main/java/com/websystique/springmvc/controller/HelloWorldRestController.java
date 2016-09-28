package com.websystique.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.websystique.springmvc.service.UserService;

@RestController
public class HelloWorldRestController {

	@Autowired
	UserService userService; 
	
	/**
	 * Below method can read data from application properties too. But here the challenge is to load the properties which changes it value.
	 * We can not change application properties which is in server folder. 
	 * 
	 * 1) So pass additional files using "archaius.configurationSource.additionalUrls"
	 * 2) First retrieve the value once from API, to add the property to notification mechanism.
	 * 3) change the property value in file, then you can see the updated value here in SOP. 
	 *  
	-Darchaius.configurationSource.additionalUrls=file:////Users/sisuser/log/config.properties
	myProperty = myValue
	myProperty1 = myValue1
	 * @param key
	 * @return
	 */
	
	@RequestMapping(value = "/property/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getValue(@PathVariable("key") String key) {
		DynamicStringProperty myProperty = DynamicPropertyFactory.getInstance().getStringProperty(key,
				"Property Not Present");
		System.out.println(myProperty.getValue());

		/**
		 * The call back will be on a specific property. Ex: If you call
		 * "myProperty" in rest API. Then call back will be initialized only for
		 * "myProperty"
		 * 
		 * Lets say you have 5 properties in config.properties. Then to get
		 * notification on all 5 properties, you need to call rest API 5 times
		 * with the 5 properties.
		 * 
		 * PollProperties.java polls the config.properties every 1 seconds to
		 * get the updated data.
		 * 
		 */
		myProperty.addCallback(new Runnable() {
			public void run() {
				System.out.println("Property " + key + " changed to " + myProperty.getValue());
			}
		});
		return myProperty.getValue();
	}


}
