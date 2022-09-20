package co.zw.santech.onlinepayments.repositories;

import co.zw.santech.onlinepayments.models.DSTVCharges;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DstvChargesRepository extends JpaRepository<DSTVCharges,String> {
    Optional<DSTVCharges> findDSTVChargesByLowerLimitLessThanEqualAndUpperLimitGreaterThanEqual(int lower, int upper);
}
