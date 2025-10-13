package com.everest.deliverysystem.vehicles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface VehicleManagementRepository extends JpaRepository<Vehicles, Integer> {
	public Optional<Vehicles> findById(Integer in);
	
	public Optional<List<Vehicles>> findByStatusOrderByAvailableTimeAsc(String status);
	
	@Modifying
    @Transactional
    @Query(value = "UPDATE VEHICLES v SET v.STATUS = :status WHERE CURRENT_TIMESTAMP > v.available_time ", nativeQuery = true)
	public int updateStatusWhereCurrentTimeGreatherThanPackageEndTime(@Param("status") String status);

	@Modifying
    @Transactional
    @Query(value = "UPDATE VEHICLES v SET v.STATUS = :status ", nativeQuery = true)
	public void updateStatus(String status);
	
}
