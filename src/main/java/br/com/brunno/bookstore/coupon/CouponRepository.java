package br.com.brunno.bookstore.coupon;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface CouponRepository extends Repository<Coupon, Long> {

    Optional<Coupon> findByCode(String code);

}
