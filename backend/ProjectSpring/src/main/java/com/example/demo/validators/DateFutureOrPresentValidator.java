package com.example.demo.validators;

import java.util.Calendar;
import java.util.Date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateFutureOrPresentValidator implements ConstraintValidator<DateFutureOrPresent, Date>{

	@Override
	public boolean isValid(Date value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		if(value == null) return true;
		
		Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        Calendar input = Calendar.getInstance();
        input.setTime(value);
        input.set(Calendar.HOUR_OF_DAY, 0);
        input.set(Calendar.MINUTE, 0);
        input.set(Calendar.SECOND, 0);
        input.set(Calendar.MILLISECOND, 0);

        return !input.before(today);
	}

	

}
