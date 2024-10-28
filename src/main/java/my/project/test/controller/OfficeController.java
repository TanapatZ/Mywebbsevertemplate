package my.project.test.controller;

import jakarta.servlet.http.HttpServletRequest;
import my.project.test.entities.Office;
import my.project.test.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/offices")
public class OfficeController {
    private OfficeService service;
    @Autowired
    public OfficeController( OfficeService service){
        this.service =service;
    }
    @GetMapping("")
    public  String getAllOffice (ModelMap model){
        List<Office> office = service.getAllOffice();
        model.addAttribute("office",office);
        return "office-home";
    }
    @GetMapping("/create-office-form")
    public String getOfficeForm(ModelMap model){
        return "create_office_form";
    }
    @PostMapping("/create-office")
    public RedirectView createOffice(HttpServletRequest request, ModelMap model){
        Office office = new Office();
        office.setOfficeCode(request.getParameter("officecode"));
        office.setCity(request.getParameter("city"));
        office.setPhone(request.getParameter("phone"));
        office.setAddressLine1(request.getParameter( "address"));
        office.setCountry(request.getParameter("country"));
        office.setPostalCode(request.getParameter("postalcode"));
        office.setTerritory(request.getParameter("territory"));
        service.createOffice(office);
                model.addAttribute("office",office);
        return new RedirectView("/offices");
    }
    @GetMapping("/delete-office")
    public RedirectView deleteOffice(@RequestParam String officeCode, ModelMap model){
        Office office = service.deleteOffice(service.getOfficeById(officeCode));
        model.addAttribute("office",office);
        return new RedirectView("/offices");
    }
    @GetMapping("/office-form-update")
    public String officeFormUpdate(ModelMap model){
        List<Office> office = service.getAllOffice();
        model.addAttribute("office",office);
        return "update-office-form";
    }
    @PostMapping("/update-office")
    public RedirectView updateOffice(HttpServletRequest request, ModelMap model){
        Office office = service.getOfficeById(request.getParameter("officecode"));
        office.setCity(request.getParameter("city"));
        office.setPhone(request.getParameter("phone"));
        office.setAddressLine1(request.getParameter( "address"));
        office.setCountry(request.getParameter("country"));
        service.updateOffice(office);
        model.addAttribute("office",office);
        return new RedirectView("/offices");
    }
    @GetMapping("/search-max-main")
    public String searchmaxmin(@RequestParam String  max,@RequestParam String  min,ModelMap model){
        List<Office> office = service.getOfficeMaxMinById(max,min);
        model.addAttribute("office",office);
        return "office-home";

    }

}
