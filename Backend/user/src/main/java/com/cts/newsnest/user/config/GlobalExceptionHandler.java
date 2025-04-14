//package com.cts.newsnest.user.config;
////
////import com.cts.newsnest.user.exception.InvalidInputException;
////import org.springframework.http.HttpStatus;
////import org.springframework.web.bind.annotation.ControllerAdvice;
////import org.springframework.web.bind.annotation.ExceptionHandler;
////import org.springframework.web.bind.annotation.ResponseStatus;
////import org.springframework.web.bind.annotation.RestControllerAdvice;
////
////@RestControllerAdvice
////public class GlobalExceptionHandler {
////    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
////    @ExceptionHandler(InvalidInputException.class)
////    public String handleInvalidInput(InvalidInputException ex){
////        return ex.getMessage();
////    }
////}
//
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfiguration {
//
//    @Bean
//    public OpenAPI openAPI(){
//        return new OpenAPI()
//                .info(new Info().title("Patient Restful web Service")
//                        .description("This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")
//                        .version("v1.0.0").contact(new Contact().name("Sachin").email("sachin@gmail.com")
//                                .url("http://www.stackroute.com")).license(new License().name("License to Stackroute")));
//
//    }
//
//}
