package com.greenapex.ELibrary.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ExceptionHandling {
	@ExceptionHandler(value=Exception.class)
	public String AnyException(ExceptionHandling e)
	{
	return "error";
	}
	}


