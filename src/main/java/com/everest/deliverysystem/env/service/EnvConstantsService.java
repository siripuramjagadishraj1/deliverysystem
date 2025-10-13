package com.everest.deliverysystem.env.service;

import java.util.List;

import com.everest.deliverysystem.exception.BusinessException;

public interface EnvConstantsService {
	public List<EnvProperties> findActiveEnvProperties() throws BusinessException;
	public void save(EnvProperties envPros) throws BusinessException;
}
