package com.everest.deliverysystem.parcel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everest.deliverysystem.coupons.service.CouponsManagementService;
import com.everest.deliverysystem.env.service.EnvConstantsService;
import com.everest.deliverysystem.exception.BusinessException;

@Service
public class ParcelManagementServiceImpl implements ParcelManagementService{
	
	private Double baseDeliveryCost = null;

	@Autowired
	private CouponsManagementService couponsManagementService;
	
	@Autowired
	private ParcelManagementRepository parcelManagementRepository;
	
	@Autowired
	private EnvConstantsService envConstantsService;
	
	private Double computeNoDiscoutPackageCost(ParcelPackage parcel) throws BusinessException{
		if(baseDeliveryCost ==null) {
			String baseDeliveryCosts = envConstantsService.findActiveEnvProperties().stream().filter(e-> "PARCEL_BASE_FARE".equalsIgnoreCase(e.getEnvKey()) ).findAny().get().getEnvValue();
			baseDeliveryCost = Double.parseDouble(baseDeliveryCosts);
		}
		return ( this.baseDeliveryCost + parcel.getWeight()*10 + parcel.getDistance()*5 );
	}
	
	@Override
	public void calculateParcelEssentials(ParcelPackage parcel) throws BusinessException {
		try {
			parcel.setNoDiscoutPackageCost(computeNoDiscoutPackageCost(parcel));
			Double discountPercent = couponsManagementService.getCoupounsDiscountBy(parcel.getCopounCode(), parcel.getDistance(), parcel.getWeight());
			parcel.setDiscountPercent(discountPercent);
			parcel.setDiscountAmount(discountPercent.equals(0d)? 0: parcel.getNoDiscoutPackageCost() * discountPercent/100);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public void saveParcel(ParcelPackage parcel) throws BusinessException {
		try {
			calculateParcelEssentials(parcel);
			parcelManagementRepository.save(parcel);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public List<ParcelPackage> findAll() throws BusinessException {
		try {
			return parcelManagementRepository.findAll();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public Optional<List<ParcelPackage>> findByStatusOrderByWeightDescDistanceDesc(String status) throws BusinessException{
		try {
			return parcelManagementRepository.findByStatusOrderByWeightDescDistanceDesc(status);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}


}
