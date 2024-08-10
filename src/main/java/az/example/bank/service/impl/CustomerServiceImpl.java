package az.example.bank.service.impl;

import az.example.bank.dto.request.ReqCustomer;
import az.example.bank.dto.response.RespCustomer;
import az.example.bank.dto.response.RespStatus;
import az.example.bank.dto.response.Response;
import az.example.bank.entity.Customer;
import az.example.bank.enums.EnumAvailableStatus;
import az.example.bank.exception.BankException;
import az.example.bank.exception.ExceptionConstants;
import az.example.bank.repository.CustomerRepository;
import az.example.bank.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Response<List<RespCustomer>> customerList() {
        Response<List<RespCustomer>> response = new Response<>();
        try {
            System.out.println("repoya girdi");
            List<Customer> customers = customerRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            System.out.println("repodan cixdi");
            if (customers.isEmpty()) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "customer not found");
            }
            List<RespCustomer> respCustomers = customers.stream().map(this::convert).collect(Collectors.toList());
            response.setT(respCustomers);
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
    public Response<RespCustomer> getCustomerById(Long id) {
        Response<RespCustomer> response = new Response<>();
        try {
            if (null == id) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Customer id is null");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "customer is null");
            }
            RespCustomer respCustomer = convert(customer);
            response.setT(respCustomer);
            response.setRespStatus(RespStatus.getSuccessMsg());

        } catch (BankException ex) {
            ex.printStackTrace();
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespCustomer> addCustomer(ReqCustomer reqCustomer) {
        Response<RespCustomer> response = new Response<>();
        try {
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            if (name == null || surname == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "invalid request data");
            }
            Customer customer = Customer.builder()
                    .name(name)
                    .surname(surname)
                    .email(reqCustomer.getEmail())
                    .mobile(reqCustomer.getMobile())
                    .address(reqCustomer.getAddress())
                    .build();
            customerRepository.save(customer);
            // geri datani otureceyim ucun
            RespCustomer respCustomer = convert(customer);
            response.setT(respCustomer);
            response.setRespStatus(RespStatus.getSuccessMsg());
        } catch (BankException ex) {
            ex.printStackTrace();
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }

        return response;
    }

    @Override
    public Response<RespCustomer> updateCustomer(ReqCustomer reqCustomer) {
        Response<RespCustomer> response = new Response<>();
        try {
            Long id = reqCustomer.getId();
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            if (id == null || name == null || surname == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "customer not found");
            }
            customer.setName(reqCustomer.getName());
            customer.setSurname(reqCustomer.getSurname());
            customer.setEmail(reqCustomer.getEmail());
            customer.setAddress(reqCustomer.getAddress());
            customer.setCif(reqCustomer.getCif());
            customer.setSeria(reqCustomer.getSeria());
            customer = customerRepository.save(customer);
            RespCustomer respCustomer = convert(customer);
            response.setT(respCustomer);
            response.setRespStatus(RespStatus.getSuccessMsg());
        } catch (BankException ex) {
            ex.printStackTrace();
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }
        return response;
    }

    @Override
    public Response deleteCustomer(Long id) {
        Response response = new Response();
        try {
            if (id == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "customer id is not valid");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "customer not found");
            }
            customer.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            customer=customerRepository.save(customer);

            response.setRespStatus(RespStatus.getSuccessMsg());

        } catch (BankException ex) {
            ex.printStackTrace();
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }
        return response;
    }


    private RespCustomer convert(Customer customer) {
        return RespCustomer.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .build();

    }
}
