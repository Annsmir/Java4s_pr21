package com.example.ex21;

import java.util.List;
import java.util.stream.Collectors;

// import javax.annotation.PostConstruct;
// import javax.persistence.criteria.CriteriaBuilder;
// import javax.persistence.criteria.CriteriaQuery;
// import javax.persistence.criteria.Root;

// import org.hibernate.Session;
// import org.hibernate.SessionFactory;
// import org.hibernate.Transaction;
// import org.hibernate.query.Query;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService svc;
    private final BuildingService bsvc;

    // private final SessionFactory sf;
    // private Session ss;
    // @PostConstruct
    // void init() {
    //     ss = sf.openSession();
    // }

    // return a list of all
    @GetMapping
    public ResponseEntity<List<Address.DTO>> getAll() {

        List<Address.DTO> x = svc.getAll().stream().map(e -> ((Address) e).toDTO()).collect(Collectors.toList());
        return  new ResponseEntity<>( x, HttpStatus.OK);
    }

    // filter by building id
    @GetMapping("/forbuilding")
    public ResponseEntity<List<Address.DTO>> filterByBID(@RequestParam(name="bid") long bid) {
        // CriteriaBuilder cb = ss.getCriteriaBuilder();
        // CriteriaQuery<Address> cq = cb.createQuery(Address.class);
        // Root<Address> r = cq.from(Address.class);
        // cq.select(r).where(cb.equal(r.get("building"),bid));
        
        // Query<Address> q = ss.createQuery(cq);


        // return new ResponseEntity<>(q.stream().map(e -> e.toDTO()).collect(Collectors.toList()), 
        //                             HttpStatus.OK);
        
        List<Address.DTO> x = svc.findByBuilding(bid).stream().map(e -> ((Address) e).toDTO()).collect(Collectors.toList());
        return  new ResponseEntity<>( x, HttpStatus.OK);                         
    }

    // filter by zip
    @GetMapping("/forzip")
    public ResponseEntity<List<Address.DTO>> filterByZip(@RequestParam(name = "zip") String zip) {
        // CriteriaBuilder cb = ss.getCriteriaBuilder();
        // CriteriaQuery<Address> cq = cb.createQuery(Address.class);
        // Root<Address> r = cq.from(Address.class);
        // cq.select(r).where(cb.equal(r.get("zipCode"),zip));
        
        // Query<Address> q = ss.createQuery(cq);
        // return new ResponseEntity<>(q.stream().map(e -> e.toDTO()).collect(Collectors.toList()), 
        //                         HttpStatus.OK);

        List<Address.DTO> x = svc.findByZip(zip).stream().map(e -> ((Address) e).toDTO()).collect(Collectors.toList());
        return  new ResponseEntity<>( x, HttpStatus.OK);                         
    }

    // filter by text - to pass wildcard (%) use '%25' in URL
    @GetMapping("/fortext")
    public ResponseEntity<List<Address.DTO>> filterByText(@RequestParam String txt) {
        // CriteriaBuilder cb = ss.getCriteriaBuilder();
        // CriteriaQuery<Address> cq = cb.createQuery(Address.class);
        // Root<Address> r = cq.from(Address.class);
        // cq.select(r).where(cb.like(r.get("addressText"),txt));
        
        // Query<Address> q = ss.createQuery(cq);
        // return new ResponseEntity<>(q.stream().map(e -> e.toDTO()).collect(Collectors.toList()), 
        //                     HttpStatus.OK);

        List<Address.DTO> x = svc.findByText(txt).stream().map(e -> ((Address) e).toDTO()).collect(Collectors.toList());
        return  new ResponseEntity<>( x, HttpStatus.OK);       
    }

    // return a single item
    @GetMapping({"/{id}"})
    public ResponseEntity<Address.DTO> getById(@PathVariable final long id) {

        Address a = svc.getById(id);  // ss.get(Address.class, id);

        if(a == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(a.toDTO(), HttpStatus.OK);
        }
        
    }

    // return related building
    @GetMapping({"/{id}/building"})
    public ResponseEntity<Building> getBuilding(@PathVariable final long id) {

        Address a = svc.getById(id);  // ss.get(Address.class, id);

        if(a == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {

            return new ResponseEntity<>(a.getBuilding(), HttpStatus.OK);
        }
        
    }

    // creates a new item and saves it to the DB and returns the created item
    @PostMapping
    public ResponseEntity<Address.DTO> create(@RequestBody Address.DTO adto) {
        Address a = new Address();
        a.setZipCode(adto.zipCode);
        a.setAddressText(adto.addressText);
        Building b = bsvc.getById(adto.buildingId); // ss.get(Building.class, adto.buildingId);
        a.setBuilding(b);

        // Transaction t = ss.beginTransaction();
        // ss.persist(a);
        // t.commit();
        // ss.refresh(a);
        // ss.refresh(b);

        svc.create(a);

        return new ResponseEntity<>(a.toDTO(), HttpStatus.CREATED);
    }

    // deletes the specified Id and returns the deleted item
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Address.DTO> delete(@PathVariable("id") Long id) {

        Address a = svc.getById(id);  // ss.get(Address.class, id); 

        if(a == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            // Transaction t = ss.beginTransaction();
            // ss.delete(a);
            // t.commit();
            Address.DTO aa = a.toDTO();
            svc.deleteById(id);
            return new ResponseEntity<>(aa, HttpStatus.OK);
        }
    }

    // updates the specified Id and returns the updated item
    @PutMapping({"/{id}"})
    public ResponseEntity<Address.DTO> edit(@PathVariable("id") Long id, @RequestBody Address.DTO changed) {


        Address a = svc.getById(id);  // ss.get(Address.class, id); 

        if(a == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            a.setAddressText(changed.addressText);
            a.setZipCode(changed.zipCode);
            a.setBuilding( bsvc.getById(changed.buildingId)); // ss.get(Building.class, changed.buildingId));
            // Transaction t = ss.beginTransaction();
            // ss.flush(); // persistent object already changed
            // t.commit();
            svc.update(a);
            return new ResponseEntity<>(a.toDTO(), HttpStatus.OK);
        }
    }

}
