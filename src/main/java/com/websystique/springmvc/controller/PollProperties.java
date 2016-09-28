package com.websystique.springmvc.controller;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.netflix.config.AbstractPollingScheduler;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicConfiguration;
import com.netflix.config.FixedDelayPollingScheduler;
import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;
import com.netflix.config.sources.URLConfigurationSource;

@Component
public class PollProperties implements PolledConfigurationSource {

	@Override
	public PollResult poll(boolean initial, Object checkPoint) throws Exception {
		System.out.println("File Updated");
		return null;
	}

	static {
		PolledConfigurationSource source = null;
		try {
			source = new URLConfigurationSource(new URL("file:////Users/sisuser/log/config.properties"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler(100, 1000, true);
		DynamicConfiguration configuration = new DynamicConfiguration(source, scheduler);
		ConfigurationManager.install(configuration);

		System.out.println("Polling Mechanism Initialized");
	}

}
