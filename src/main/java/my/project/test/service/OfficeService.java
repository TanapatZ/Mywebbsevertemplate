package my.project.test.service;

import my.project.test.entities.Office;
import my.project.test.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OfficeService {

    private OfficeRepository repo;
    @Autowired
    public OfficeService(OfficeRepository repo){
        this.repo = repo;
    }
    //get only method
    private Office getOffice(String officeId){
        return repo.findById(officeId).orElse(null);
    }
    public List<Office> getAllOffice(){
        return repo.findAll();
    }
    public Office createOffice(Office office){
        Office isExsit = getOffice(office.getOfficeCode());
        if(isExsit != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,String.format("This %s is already created",isExsit.getOfficeCode()));
        }
        return repo.save(office);
    }
    public Office updateOffice(Office office){
        Office isExsit = getOffice(office.getOfficeCode());
        if(isExsit == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("This %s is haven't create yet",office.getOfficeCode()));
        }
        return repo.save(office);
    }
    public Office deleteOffice(Office office){
        Office isExsit = getOffice(office.getOfficeCode());
        if(isExsit == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("This %s is haven't create yet",office.getOfficeCode()));
        }
        repo.deleteById(office.getOfficeCode());
        return isExsit;
    }
    public Office getOfficeById(String officeId){
        return getOffice(officeId);
    }
    public List<Office> getOfficeMaxMinById(String max,String min){
        return repo.findOfficeByCodeMaxMin(max,min);
    }

    }

