package ru.spbmtsb.cashback.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.spbmtsb.cashback.entity.Bonus;

@Repository
public interface BonusRepository extends CrudRepository<Bonus, Long> {
    Bonus getByCustomerId(long customerId);
}
