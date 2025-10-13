package com.everest.deliverysystem.parcel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface ParcelManagementRepository extends JpaRepository<ParcelPackage, Integer> {

	public Optional<List<ParcelPackage>> findByStatusOrderByWeightDescDistanceDesc(String status);
	
	@Modifying
    @Transactional
    @Query(value = "UPDATE PARCEL_PACKAGE p SET p.STATUS = :status WHERE CURRENT_TIMESTAMP > p.END_DELIVERY_TIME ", nativeQuery = true)
	public int updateStatusWhereCurrentTimeGreatherThanPackageEndTime(@Param("status") String status);

}
