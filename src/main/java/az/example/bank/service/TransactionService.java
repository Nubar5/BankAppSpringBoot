package az.example.bank.service;

import az.example.bank.dto.request.ReqTransForList;
import az.example.bank.dto.request.ReqTransaction;
import az.example.bank.dto.response.RespTransaction;
import az.example.bank.dto.response.Response;

import java.util.List;

public interface TransactionService {
    Response<RespTransaction> addTransaction(ReqTransaction reqTransaction);


    Response<List<RespTransaction>> getTransListByCifOrAccId(ReqTransForList reqTransForList);
}
