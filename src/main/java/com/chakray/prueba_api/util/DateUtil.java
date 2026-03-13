package com.chakray.prueba_api.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

/**
 * Class of type util
 */
@Component
public class DateUtil {
	
	private static final String MADAGASCAR_ZONE = "Indian/Antananarivo";
	private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
	/**
	 * Method to get de time
	 * @return date
	 */
	public String getCurrentMadagascarTime() {
		ZonedDateTime date = ZonedDateTime.now(ZoneId.of(MADAGASCAR_ZONE));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		return date.format(formatter);
	}
}
