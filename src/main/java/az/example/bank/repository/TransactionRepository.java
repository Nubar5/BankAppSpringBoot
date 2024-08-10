package az.example.bank.repository;

import az.example.bank.entity.Account;
import az.example.bank.entity.Customer;
import az.example.bank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findAllByDtCustomerAndDtAccount(Customer customer, Account account);
    List<Transaction> findAllByDtCustomerAAndActive(Customer customer,Integer active);
}
