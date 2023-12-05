package ru.spbmtsb.cashback.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.spbmtsb.cashback.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
}
