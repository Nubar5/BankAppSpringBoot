package az.example.bank.service;

import az.example.bank.dto.request.ReqAccount;
import az.example.bank.dto.response.RespAccount;
import az.example.bank.dto.response.Response;

import java.util.List;

public interface AccountService {
    Response<List<RespAccount>> getAccountListByCif(String cif);

    Response<RespAccount> addAccount(ReqAccount reqAccount);
}
