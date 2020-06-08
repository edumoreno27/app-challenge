package com.pruebatecnica.api.constraints;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateDateValidator implements ConstraintValidator<ValidateDate, String>{

	
	private ValidateDate validateDate;
	
	@Override
	public void initialize(ValidateDate constraintAnnotation) {
		validateDate = constraintAnnotation;
	}
	

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value != null) {
			try { 
			     LocalDate.parse(value, DateTimeFormatter.ofPattern(validateDate.pattern()));
			} catch (DateTimeException e) {
				return false;
			}
		}
		
		return true;
	}

}
