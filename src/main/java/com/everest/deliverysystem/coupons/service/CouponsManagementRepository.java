package com.everest.deliverysystem.coupons.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponsManagementRepository extends JpaRepository<Coupons, Integer> {

}
