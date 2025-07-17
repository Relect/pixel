package com.pixel.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    LocalDate dateOfBirth;

    @Size(min = 8, max = 500, message = "Password must be between 8 and 500 characters")
    String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @NotEmpty
    @JsonIgnoreProperties("user")
    List<EmailData> emailDataList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @NotEmpty
    @JsonIgnoreProperties("user")
    List<PhoneData> phoneDataList = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    @NotNull
    @JsonIgnoreProperties("user")
    Account account;
}
