package com.arun.SpringBoot.demoApplication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {

    private Long id;

    @NotNull
    private String name;

    private Double salary;

    private Integer age;

    @Email(message = "email should be a valid Email")
    private String email;
}
