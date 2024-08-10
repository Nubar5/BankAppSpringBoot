package az.example.bank.service.impl;

import az.example.bank.dto.request.ReqAccount;
import az.example.bank.dto.response.RespAccount;
import az.example.bank.dto.response.RespCustomer;
import az.example.bank.dto.response.RespStatus;
import az.example.bank.dto.response.Response;
import az.example.bank.entity.Account;
import az.example.bank.entity.Customer;
import az.example.bank.enums.EnumAvailableStatus;
import az.example.bank.exception.BankException;
import az.example.bank.exception.ExceptionConstants;
import az.example.bank.repository.AccountRepository;
import az.example.bank.repository.CustomerRepository;
import az.example.bank.service.AccountService;
import az.example.bank.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;


    @Override
    public Response<List<RespAccount>> getAccountListByCif(String cif) {
        Response<List<RespAccount>> response = new Response<>();
        try {
            if (cif == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "cif is not valid");
            }
            Customer customer = customerRepository.findCustomerByCifAndActive(cif, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "customer not found");
            }
            List<Account> accountList = accountRepository.findAllByActiveAndCustomer(EnumAvailableStatus.ACTIVE.getValue(), customer);
            if (accountList.isEmpty()) {
                throw new BankException(ExceptionConstants.ACCOUNT_NOT_FOUND, "account not found");
            }
            List<RespAccount> respAccounts = accountList.stream().map(this::convert).collect(Collectors.toList());
            response.setT(respAccounts);
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
    public Response<RespAccount> addAccount(ReqAccount reqAccount) {
        Response<RespAccount>response = new Response<>();
        try {
            Long id = reqAccount.getId();
            String name = reqAccount.getName();
            if(id==null ||name==null){
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA,"invalid name or id i");
            }
            Customer customer=customerRepository.findCustomerByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            if(customer==null){
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND,"customer not found");
            }
         Account account=Account.builder()
                 .name(reqAccount.getName())
                 .accountNo(reqAccount.getAccountNo())
                 .iban(reqAccount.getIban())
                 .balance(reqAccount.getBalance())
                 .currency(reqAccount.getCurrency())
                 .customer(customer)
                 .build();
            accountRepository.save(account);
            RespAccount respAccount=convert(account);
            response.setT(respAccount);
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


    private RespAccount convert(Account account) {
        RespCustomer respCustomer = RespCustomer.builder()
                .id(account.getCustomer().getId())
                .name(account.getCustomer().getName())
                .address(account.getCustomer().getAddress())
                .mobile(account.getCustomer().getMobile())
                .cif(account.getCustomer().getCif())
                .build();
        return RespAccount.builder()
                .id(account.getId())
                .name(account.getName())
                .accountNo(account.getAccountNo())
                .balance(account.getBalance())
                .iban(account.getIban())
                .currency(account.getCurrency())
                .respCustomer(respCustomer)
                .build();

    }
}
