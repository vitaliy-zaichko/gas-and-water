package com.coolcompany.gasandwaterusage.controllers;

import com.coolcompany.gasandwaterusage.model.Measurement;
import com.coolcompany.gasandwaterusage.model.User;
import com.coolcompany.gasandwaterusage.repository.MeasurementRepository;
import com.coolcompany.gasandwaterusage.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements/users")
public class GasAndWaterController {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @GetMapping("/{userId}")
    public List<Measurement> getUserMeasurements(@PathVariable("userId") String userId) {
        return measurementRepository.findAllByUserId(userId);
    }

    @PostMapping("/{userId}")
    public ResponseEntity addUserMeasurements(@PathVariable("userId") String userId, @Valid @RequestBody Measurement measurement) {
        measurement.setUser(usersRepository.getOne(userId));
        if (measurement.getMeasurementDate() == null) {
            measurement.setMeasurementDate(LocalDate.now());
        }
        Measurement result = measurementRepository.save(measurement);
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors()
                .stream()
                .map(FieldError.class::cast)
                .collect(Collectors.toMap(FieldError::getField, ObjectError::getDefaultMessage));
    }
}
