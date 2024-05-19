package com.team.bookstore.Enums;

import lombok.Getter;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCodes {
    UN_CATEGORIED(10,"This exception is unknown!",HttpStatus.INTERNAL_SERVER_ERROR),
    REGISTER_DENIED(11,"Your register was denied!",HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(12,"This user is not exist!",HttpStatus.NOT_FOUND),
    ACCESS_DENIED(13,"Your access has been denied!",HttpStatus.NOT_ACCEPTABLE),
    UN_AUTHORISED(14,"You are unauthorised",HttpStatus.UNAUTHORIZED),
    UN_AUTHENTICATED(15,"You are unauthenticated",HttpStatus.NOT_ACCEPTABLE),
    USER_HAS_BEEN_EXIST(16,"This user has been exist!",HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_USER(17,"Invalid user!",HttpStatus.BAD_REQUEST),
    OBJECT_NOT_EXIST(18,"Your required object is not exist!",
            HttpStatus.NOT_FOUND),
    OBJECT_HAS_BEEN_EXISTING(19,"This object has been existing!",
            HttpStatus.BAD_REQUEST),
    INVALID_OBJECT(20,"Your input object is invalid!",HttpStatus.BAD_REQUEST),
    CANNOT_CREATE(21,"Your required object cannot be created!",
            HttpStatus.INTERNAL_SERVER_ERROR),
    CANNOT_UPDATE(22,"Your required object cannot be updated!",
            HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND(23,"Not found this object!",HttpStatus.NOT_FOUND),
    CANNOT_DELETE(24,"Cannot delete this object!",
            HttpStatus.INTERNAL_SERVER_ERROR),
    CANNOT_VERIFY(25,"Cannot verify this object!",
            HttpStatus.INTERNAL_SERVER_ERROR),
    IS_EXPIRED(26,"This object is expired!",HttpStatus.INTERNAL_SERVER_ERROR),
    NULL_FIELD(27,"A field of your object is null!",HttpStatus.BAD_REQUEST)
    ;
    final int code;
    final String message;
    final HttpStatus httpStatus;
    ErrorCodes(int code,String message, HttpStatus httpStatus){
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
