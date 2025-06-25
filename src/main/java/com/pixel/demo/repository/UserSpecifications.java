package com.pixel.demo.repository;

import com.pixel.demo.dto.SearchUserDto;
import com.pixel.demo.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserSpecifications {

    public static Specification<User> hasDateOfBirthAfter(LocalDate date) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (date == null) {
                return cb.conjunction();
            }
            return cb.greaterThan(root.get("dateOfBirth"), date);
        };
    }

    public static Specification<User> nameStartsWith(String name) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (name == null || name.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(root.get("name"), name + "%");
        };
    }

    public static Page<User> findUsersWithDateOfBirthAndName(SearchUserDto searchUserDto, UserRepository userRepository) {
        List<Specification<User>> specs = new ArrayList<>();

        if (searchUserDto.dateOfBirth() != null) {
            specs.add(hasDateOfBirthAfter(searchUserDto.dateOfBirth()));
        }

        if (searchUserDto.name() != null && !searchUserDto.name().isEmpty()) {
            specs.add(nameStartsWith(searchUserDto.name()));
        }

        Specification<User> finalSpec = specs.stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());

        Pageable pageable = PageRequest.of(searchUserDto.page(), searchUserDto.size());

        return userRepository.findAll(finalSpec, pageable);
    }
}
