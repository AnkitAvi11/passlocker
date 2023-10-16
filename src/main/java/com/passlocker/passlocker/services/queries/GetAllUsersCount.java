package com.passlocker.passlocker.services.queries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("users_count")
public class GetAllUsersCount {

    private final EntityManager entityManager;

    @Autowired
    public GetAllUsersCount(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public long execute() {
        Query query = this.entityManager.createNamedQuery("COUNT_USERS");
        return (long) query.getSingleResult();
    }
}
