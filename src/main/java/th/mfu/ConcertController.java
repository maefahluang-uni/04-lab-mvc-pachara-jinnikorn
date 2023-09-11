package th.mfu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConcertController {
    // TODO: create hashmap of concerts for storing data
    private HashMap<Integer, Concert> concertMap = new HashMap<>();
    private int nextId = 1;
    
    // TODO: add initbinder to convert date
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @GetMapping("/concerts")
    public String listConcerts(Model model) {
        // TODO: add concerts to model
        model.addAttribute("concerts", concertMap.values());
        // TODO: return a template to list concerts
        return "list-concert";
    }

    @GetMapping("/add-concert")
    public String addAConcertForm(Model model) {
        // TODO: pass blank concert to a form
        model.addAttribute("concert", new Concert());
        // TODO: return a template for concert form
        return "add-concert-form"; // Return the template name
    }

    @PostMapping("/concerts")
    public String saveConcert(@ModelAttribute Concert concert) {
        // TODO: add concert to list of concerts
        // TODO: increment nextId
        int id = nextId++; // (unique-id) Get the next available ID and increment it
        concert.setId(id);
        concertMap.put(id, concert); // Add the concert to the map with the new ID
        // TODO: redirect to list concerts
        return "redirect:/concerts"; // Redirect to the list of concerts
    }

    @GetMapping("/delete-concert/{id}")
    public String deleteConcert(@PathVariable int id) {
        // TODO: remove concert from list of concerts
        Concert inst = concertMap.getOrDefault(id, null);
        if (inst != null) {
            concertMap.remove(id);
        }
        // TODO: redirect to list concerts
        return "redirect:/concerts"; // Redirect to the list of concerts
    }

    @GetMapping("/delete-concert")
    public String removeAllConcerts() {
        //TODO: clear all employees and reset id
        concertMap.clear();
        nextId = 1;
        // TODO: redirect to list concerts
        return "redirect:/concerts";
    }
}