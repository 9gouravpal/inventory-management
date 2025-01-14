package com.venture.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.venture.exception.InternalServerErrorException;

public class Util {
	private Util() {
		throw new NotImplementedException("All methods are static.");
	}

	public static <K> void checkDTOErrors(K dto, Errors errors) throws MethodArgumentNotValidException {
		BeanPropertyBindingResult errorsToReturn = new BeanPropertyBindingResult(dto, "dto");

		if (errors != null) {
			for (FieldError error : errors.getFieldErrors()) {
				if (!"NotBlank".equals(error.getCode()) && !"NotNull".equals(error.getCode())) {
					errorsToReturn.addError(error);
				}
			}
		}

		if (!errorsToReturn.getAllErrors().isEmpty()) {
			throw new MethodArgumentNotValidException(null, errorsToReturn);
		}
	}

	public static Object createInstance(Class<?> cls) throws InternalServerErrorException {
		try {
			return cls.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException
				| InvocationTargetException e) {
			throw new InternalServerErrorException("Internal error");
		}
	}

	public static List<?> createListInstance(Class<?> cls) throws InternalServerErrorException {
		try {

			return Arrays.asList(cls.getConstructor().newInstance());

		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException
				| InvocationTargetException e) {
			throw new InternalServerErrorException("Internal error");
		}
	}
}
