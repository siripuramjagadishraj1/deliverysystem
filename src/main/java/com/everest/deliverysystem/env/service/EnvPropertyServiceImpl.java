package com.everest.deliverysystem.env.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everest.deliverysystem.exception.BusinessException;
import com.everest.deliverysystem.utils.EnvPropertyState;

@Service
public class EnvPropertyServiceImpl implements EnvConstantsService {
	
	@Autowired
	private EnvPropertyRepository envPropertyRepository;

	@Override
	public List<EnvProperties> findActiveEnvProperties() throws BusinessException{
		try {
			return envPropertyRepository.findByState(EnvPropertyState.ACTIVE);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void save(EnvProperties envProp) throws BusinessException{
		try {
			envPropertyRepository.save(envProp);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	

}
