package com.azubike.ellipsis.springsecuritybasic.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.azubike.ellipsis.springsecuritybasic.model.AccountTransactions;

@Repository
public interface AccountTransactionsRepository extends CrudRepository<AccountTransactions, Long> {

	List<AccountTransactions> findByCustomerIdOrderByTransactionDtDesc(int customerId);

}
