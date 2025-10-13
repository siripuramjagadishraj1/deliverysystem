package com.everest.deliverysystem.coupons.service;

import com.everest.deliverysystem.exception.BusinessException;

public interface CouponsManagementService {
	public Double getCoupounsDiscountBy(String offerCode, Double distance, Double weight) throws BusinessException;
	public void save(Coupons coupouns) throws BusinessException;
}
