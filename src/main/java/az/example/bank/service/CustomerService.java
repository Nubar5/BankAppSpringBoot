package az.example.bank.service;

import az.example.bank.dto.request.ReqCustomer;
import az.example.bank.dto.response.RespCustomer;
import az.example.bank.dto.response.Response;

import java.util.ArrayList;
import java.util.List;

public interface CustomerService {
    public Response<List<RespCustomer>> customerList() ;

    Response<RespCustomer> getCustomerById(Long id);

    Response<RespCustomer> addCustomer(ReqCustomer reqCustomer);

    Response<RespCustomer> updateCustomer(ReqCustomer reqCustomer);

    Response deleteCustomer(Long id);
}
