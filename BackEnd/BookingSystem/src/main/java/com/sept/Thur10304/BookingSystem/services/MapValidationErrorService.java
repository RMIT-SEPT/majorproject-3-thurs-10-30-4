package com.sept.Thur10304.BookingSystem.services;

// Error handler from Homy's tutorial: https://web.microsoftstream.com/video/a2eee04a-9636-45c7-aa67-47d934e76acf @ 4:21

import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidationErrorService
{
    public ResponseEntity<?> MapValidationService(BindingResult result)
    {
        if (result.hasErrors())
        {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error: result.getFieldErrors())
            {
                errorMap.put(error.getField(), error.getDefaultMessage());

            }
            return new ResponseEntity<Map <String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
    
}
