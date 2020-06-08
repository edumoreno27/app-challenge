package com.pruebatecnica.api.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class EvaluacionUtil {

	@Autowired
	private MessageSource messageSource;
	
	public String getMessage(String key, Object... args) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(key, args, locale);
	}
	
	public static LocalDate convertStringToLocalDate(String dateString) {
		   DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_FORMAT);
	     return LocalDate.parse(dateString, formatter);
	}
	
	public static String convertLocalDateToString(LocalDate date) {
		   DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_FORMAT);
	     return formatter.format(date);
	}
	
}
