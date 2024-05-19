package com.team.bookstore.Exceptions;

import com.team.bookstore.Enums.ErrorCodes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends RuntimeException{
    public ApplicationException(ErrorCodes errorCodes){
        super(errorCodes.getMessage());
         this.errorCodes=errorCodes;
    }
    private ErrorCodes errorCodes;
}
