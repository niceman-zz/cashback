package ru.spbmtsb.cashback.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.spbmtsb.cashback.constants.CashbackType;
import ru.spbmtsb.cashback.entity.Cashback;

@Repository
public interface CashbackRepository extends CrudRepository<Cashback, CashbackType> {
}
