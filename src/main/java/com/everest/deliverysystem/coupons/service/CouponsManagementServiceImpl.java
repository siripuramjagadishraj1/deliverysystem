package com.everest.deliverysystem.coupons.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everest.deliverysystem.exception.BusinessException;

@Service
public class CouponsManagementServiceImpl implements CouponsManagementService{
	
	@Autowired
	private CouponsManagementRepository couponsManagementRepository;

	@Override
	public Double getCoupounsDiscountBy(String offerCode, Double distance, Double weight) throws BusinessException {
		try {
			Double discount = 0d;
			List<Coupons> couponList = couponsManagementRepository.findAll();
			Coupons coupon = 
				couponList.stream().filter(e-> {
												return 
													   offerCode.equals(e.getCoupounName())
													&& distance>=e.getMinDistance() 
													&& distance<=e.getMaxDistance()
													&& weight>=e.getMinWeight() 
													&& weight<=e.getMaxWeight();
									})
								   .findAny().orElse(null);
				if(coupon !=null) {
					discount = coupon.getDiscount();
				}
			return discount;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public void save(Coupons coupouns) throws BusinessException {
		try {
			couponsManagementRepository.save(coupouns);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}
