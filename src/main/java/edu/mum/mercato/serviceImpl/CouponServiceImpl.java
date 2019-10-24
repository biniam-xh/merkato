package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.domain.Coupon;
import edu.mum.mercato.repository.CouponRepository;
import edu.mum.mercato.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponRepository couponRepository;

    @Override
    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon getCoupon(Long userId) {
        return couponRepository.findFirstByBuyer_Id(userId);
    }
}
