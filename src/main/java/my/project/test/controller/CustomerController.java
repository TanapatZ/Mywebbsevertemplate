package my.project.test.controller;

import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;
import my.project.test.entities.Customer;
import my.project.test.service.Customerservice;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.List;

@Controller

@RequestMapping("/customers")
public class CustomerController {

    private Customerservice service;

    public CustomerController(Customerservice service) {
        this.service = service;
    }

    @GetMapping("")
    public String getCustomerbyId(@RequestParam Integer Id, ModelMap model) {
        Customer customer = service.getCustomerByCode(Id);
        model.addAttribute("customer", customer);
        return "customerId";
    }

    @GetMapping("/all")
    public String getAllCustomer(ModelMap model) {
        List<Customer> customer = service.getAllCustomer();
        model.addAttribute("customer", customer);

        return "customer";
    }
    @GetMapping("/delete-customer")
    public String deleteCustomer(@RequestParam Integer Id ,ModelMap model){

        Customer customer = service.deleteCustomerbyId(Id);
        model.addAttribute("customer",customer);
        return "customer_home";
    }
    @PostMapping("/update-customer")
    public RedirectView updateCustomer(HttpServletRequest request, ModelMap model){
        Customer customer = service.getCustomerByCode(Integer.parseInt(request.getParameter("Id")));
        customer.setCustomerName(request.getParameter("name"));
        customer.setPhone(request.getParameter("phone"));
        customer.setCreditLimit(BigDecimal.valueOf(Long.parseLong(request.getParameter("credit"))));
        Customer newcustomer = service.updateCustomer(customer);
        model.addAttribute("customer",newcustomer);
        return new RedirectView("/customers/all");
    }
    @GetMapping("/customer-update-form")
    public String updateForm(@RequestParam Integer Id ,ModelMap model){
        Customer customer = service.getCustomerByCode(Id);
        model.addAttribute("customer",customer);
        return "customer_update_form";
    }
    @GetMapping("/searchCustomerName")
    public String search(@RequestParam String searchParam ,ModelMap model){
        model.addAttribute("customer",service.findByName(searchParam));
        return "customer";
    }
    @GetMapping("/paging")
    public String getProductsPage(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size, ModelMap model) {
        model.addAttribute("page", service.findAll(PageRequest.of(page, size)));
        return "customer-paging";
    }
}


