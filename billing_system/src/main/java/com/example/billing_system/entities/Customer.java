package com.example.billing_system.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class Customer {

    private String name;
    private String mobileNumber;
    private String address;
    private String email;

}
