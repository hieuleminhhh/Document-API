// package com.example.documentapi.exception;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.MethodArgumentNotValidException;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.ResponseStatus;
// import org.springframework.validation.FieldError;

// @ControllerAdvice
// public class ValidationExceptionHandler {

//     @ResponseStatus(HttpStatus.BAD_REQUEST)
//     @ExceptionHandler(MethodArgumentNotValidException.class)
//     public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
//         Map<String, String> errors = new HashMap<>();

//         for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//             errors.put(error.getField(), error.getDefaultMessage());
//         }

//         Map<String, Object> response = new HashMap<>();
//         response.put("success", false);
//         response.put("message", "Validation failed");
//         response.put("errors", errors);

//         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//     }
// }
