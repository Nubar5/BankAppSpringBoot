package az.example.bank.repository;

import az.example.bank.entity.Account;
import az.example.bank.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByActiveAndCustomer(Integer active, Customer customer);

    Account findAccountByIdAndActive(Long id, Integer active);
    Account findAccountByCustomerAndId(Customer customer,Long id);
}
