package az.example.bank.controller;

import az.example.bank.dto.request.ReqTransForList;
import az.example.bank.dto.request.ReqTransaction;
import az.example.bank.dto.response.RespTransaction;
import az.example.bank.dto.response.Response;
import az.example.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/create")
    public Response<RespTransaction> addTransaction(@RequestBody ReqTransaction reqTransaction) {
        return transactionService.addTransaction(reqTransaction);
    }

    @GetMapping("/list")
    public Response<List<RespTransaction>> getTransationListByCifOrAccId(@RequestBody ReqTransForList reqTransForList){
        return transactionService.getTransListByCifOrAccId(reqTransForList);
    }
}
