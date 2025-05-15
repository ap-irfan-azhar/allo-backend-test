package com.allobank.allobackendtest.specification;

import com.allobank.allobackendtest.model.Caleg;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class CalegSpecification {

    public static Specification<Caleg> hasDapilId(UUID dapilId) {
        return (root, query, criteriaBuilder) -> {
            if (dapilId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("dapil").get("id"), dapilId);
        };
    }

    public static Specification<Caleg> hasPartaiId(UUID partaiId) {
        return (root, query, criteriaBuilder) -> {
            if (partaiId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("partai").get("id"), partaiId);
        };
    }
}