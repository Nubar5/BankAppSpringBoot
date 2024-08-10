package az.example.bank.controller;

import az.example.bank.dto.request.ReqCustomer;
import az.example.bank.dto.response.RespCustomer;
import az.example.bank.dto.response.Response;
import az.example.bank.entity.Customer;
import az.example.bank.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/customer")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/list")
    public Response<List<RespCustomer>> customerList() {
        return customerService.customerList();
    }
//pathVariable olan versiyasi
//    @GetMapping("/get/{id}")
//    public Response<RespCustomer> getCustomerById(@PathVariable Long id) {
//        return customerService.getCustomerById(id);
//    }

    //param ve deyisenin adi ferqli olarsa
//    @GetMapping("/get/{id}")
//    public Response<RespCustomer> getCustomerById(@PathVariable(name="id") Long customerIdid) {
//        return customerService.getCustomerById(customerIdid);
//    }


    // request paramla olan versiyasi
    @GetMapping("/get")
    public Response<RespCustomer> getCustomerById(@RequestParam Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/create")
    public Response<RespCustomer> addCustomer(@RequestBody ReqCustomer reqCustomer) {
        return customerService.addCustomer(reqCustomer);
    }

    @PutMapping("/update")
    public Response<RespCustomer> updateCustomer(@RequestBody ReqCustomer reqCustomer) {
        return customerService.updateCustomer(reqCustomer);
    }
    @PutMapping("/delete/{id}")
    public Response deleteCustomer(@PathVariable Long id) {
         return customerService.deleteCustomer(id);
    }
}
