package az.example.bank.service.impl;

import az.example.bank.dto.request.ReqTransForList;
import az.example.bank.dto.request.ReqTransaction;
import az.example.bank.dto.response.*;
import az.example.bank.entity.Account;
import az.example.bank.entity.Customer;
import az.example.bank.entity.Transaction;
import az.example.bank.enums.EnumAvailableStatus;
import az.example.bank.exception.BankException;
import az.example.bank.exception.ExceptionConstants;
import az.example.bank.repository.AccountRepository;
import az.example.bank.repository.CustomerRepository;
import az.example.bank.repository.TransactionRepository;
import az.example.bank.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Response<RespTransaction> addTransaction(ReqTransaction reqTransaction) {
        Response<RespTransaction> response = new Response<>();
        try {
            Long dtCustomer = reqTransaction.getDtReqCustomerId();
            Long dtAccount = reqTransaction.getDtReqAccountId();
            String crAccount = reqTransaction.getCrAccount();
            Double amount = reqTransaction.getAmount();
            String currency = reqTransaction.getCurrency();
            System.out.println(dtCustomer);
            System.out.println(dtAccount);
            System.out.println(crAccount);
            System.out.println(amount);
            System.out.println(currency);
            if (dtAccount == null || dtCustomer == null || crAccount == null || amount == null || currency == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "invalid data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(dtCustomer, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "customer not found");
            }
            Account account = accountRepository.findAccountByIdAndActive(dtAccount, EnumAvailableStatus.ACTIVE.getValue());
            if (account == null) {
                throw new BankException(ExceptionConstants.ACCOUNT_NOT_FOUND, "account not found");
            }
            if (amount > account.getBalance()) {
                throw new BankException(ExceptionConstants.NOT_ENOUGH_BALANCE, "not enough balance");
            }
            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);
            Transaction transaction = Transaction.builder().dtCustomer(customer).crAccount(crAccount).dtAccount(account).amount(amount).currency(currency).build();
            transactionRepository.save(transaction);
            response.setT(convert(transaction));
            response.setRespStatus(RespStatus.getSuccessMsg());
        } catch (BankException e) {
            e.printStackTrace();
            response.setRespStatus(new RespStatus(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }
        return response;
    }

    @Override
    public Response<List<RespTransaction>> getTransListByCifOrAccId(ReqTransForList reqTransForList) {
        Response<List<RespTransaction>> response = new Response<>();
        List<Transaction> transactions;
        try {
            String cif = reqTransForList.getCif();
            Long accId = reqTransForList.getAccountId();
            if (cif == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "cif is invalid");
            }
            Customer customer = customerRepository.findCustomerByCifAndActive(cif, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "customer not found");
            }
            if (accId != null) {
                Account account = accountRepository.findAccountByCustomerAndId(customer, accId);
                if (account == null) {
                    throw new BankException(ExceptionConstants.ACCOUNT_NOT_FOUND, "account not found");
                }
                transactions = transactionRepository.findAllByDtCustomerAndDtAccount(customer, account);
            } else {
                transactions = transactionRepository.findAllByDtCustomerAAndActive(customer, EnumAvailableStatus.ACTIVE.getValue());
            }
            if (transactions.isEmpty()) {
                throw new BankException(ExceptionConstants.TRANSACTION_NOT_FOUND, "transaction not fond");
            }
            List<RespTransaction> respTransactionList = transactions.stream().map(this::convert).collect(Collectors.toList());
            response.setT(respTransactionList);
            response.setRespStatus(RespStatus.getSuccessMsg());
        } catch (BankException e) {
            e.printStackTrace();
            response.setRespStatus(new RespStatus(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }
        return response;
    }


    private RespTransaction convert(Transaction transaction) {
        RespCustomer respCustomer = RespCustomer.builder()
                .id(transaction.getDtCustomer().getId())
                .name(transaction.getDtCustomer().getName())
                .address(transaction.getDtCustomer().getAddress())
                .mobile(transaction.getDtCustomer().getMobile())
                .cif(transaction.getDtCustomer()
                        .getCif()).build();

        RespAccount respAccount = RespAccount.builder()
                .id(transaction.getDtAccount().getId())
                .name(transaction.getDtAccount().getName())
                .accountNo(transaction.getDtAccount().getAccountNo())
                .iban(transaction.getDtAccount().getIban())
                .currency(transaction.getDtAccount().getCurrency())
                .balance(transaction.getDtAccount().getBalance())
                .respCustomer(respCustomer)

                .build();

        return RespTransaction.builder()
                .id(transaction.getId())
                .dtRespAccount(respAccount)
                .crAccount(transaction.getCrAccount())
                .currency(transaction.getCurrency())
                .amount(transaction.getAmount())
                .dtRespCustomer(respCustomer)
                .build();
    }

}