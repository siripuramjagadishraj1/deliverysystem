package com.everest.deliverysystem.env.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.everest.deliverysystem.utils.EnvPropertyState;

@Repository
public interface EnvPropertyRepository extends JpaRepository<EnvProperties, Integer> {
	public List<EnvProperties> findByState(EnvPropertyState state);
}
