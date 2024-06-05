package com.riwi.virtualClasses.utils.exceptions;

public class BadRequestException extends RuntimeException 
{
    public BadRequestException(String error)
    {
        super(error);
    }    
}
