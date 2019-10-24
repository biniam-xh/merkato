package edu.mum.mercato.service;

import edu.mum.mercato.domain.Coupon;

public interface CouponService {
    Coupon save (Coupon coupon);
    Coupon getCoupon(Long userId);
}
