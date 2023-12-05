package ru.spbmtsb.cashback.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.spbmtsb.cashback.constants.PriceType;
import ru.spbmtsb.cashback.entity.PriceThreshold;

@Repository
public interface PriceThresholdRepository extends CrudRepository<PriceThreshold, PriceType> {
}
