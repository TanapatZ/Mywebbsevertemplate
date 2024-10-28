package my.project.test.service;

import my.project.test.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import my.project.test.repository.CustomerRepository;


import java.util.List;

@Service
public class Customerservice {

    private CustomerRepository repo;
    @Autowired
    public Customerservice(CustomerRepository repo){
        this.repo = repo;
    }
    private Customer getCustomer(Integer custId){
      return   repo.findById(custId).orElse(null);
    }
     public List<Customer> getAllCustomer(){
        return repo.findAll();
    }
    public Customer createCustomer(Customer customer){
        Customer isItExsit = getCustomer(customer.getId());
        if(isItExsit != null){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                 String.format("this %d is already create",customer.getId()));
        }
        return repo.save(customer);
    }
    public Customer  updateCustomer(Customer customer){
        Customer isItExsit = getCustomer(customer.getId());
        if(isItExsit == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("this %d is haven't create yet",customer.getId()));
        }

        return repo.save(customer);

    }
    public Customer deleteCustomer(Customer customer){
        Customer isItExsit = getCustomer(customer.getId());
        if(isItExsit == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("this %d is haven't create yet",customer.getId()));
        }
         repo.delete(customer);
        return customer;

    }
    public Customer deleteCustomerbyId(Integer custId){
        Customer isItExsit = getCustomer(custId);
        if(isItExsit == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("this %d is haven't create yet",custId));
        }
        Customer alreadyDelete = getCustomer(custId);
        repo.delete(getCustomer(custId));
        return alreadyDelete;

    }
    public Customer getCustomerByCode(Integer custId){
        return getCustomer(custId);
    }
    public List<Customer> findByName(String name){
       return repo.findCustomersByCustomerNameContaining(name);
    }
    public Page<Customer> findAll(Pageable pageAble){
        return repo.findAll(pageAble);
    }

}
