package az.example.bank.controller;

import az.example.bank.dto.request.ReqAccount;
import az.example.bank.dto.response.RespAccount;
import az.example.bank.dto.response.Response;
import az.example.bank.entity.Account;
import az.example.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/list/getBy{cif}")
    public Response<List<RespAccount>> getAccountListByCif(@PathVariable String cif){
        return accountService.getAccountListByCif(cif);
    }

    @PostMapping("/create")
    public Response<RespAccount> addAccount(@RequestBody ReqAccount reqAccount){
        return accountService.addAccount(reqAccount);
    }
}
