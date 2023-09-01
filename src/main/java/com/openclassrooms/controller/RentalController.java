package com.openclassrooms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.model.Rental;
import com.openclassrooms.service.RentalService;

@RestController
@RequestMapping("api/")
public class RentalController {
	
	@Autowired
	private RentalService rentalService;
	
	//Recup de la liste des rentals
	@GetMapping("rentals")
	public List<Rental> getRentals(){
		return rentalService.getRentals();
	}
	
	//Recup d'une rental par id
	@GetMapping("/rentals/{id}")
	public ResponseEntity<Object> findRental(@PathVariable("id")Long id) {
		Optional<Rental> rental = rentalService.findById(id);
		
		if(rental.isEmpty()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cette rental n'existe pas !");
		}
		return ResponseEntity.ok(rental.get());
	}
	
	//Création d'une rental
	@PostMapping("/rentals")
	public ResponseEntity<Rental> createRental(@RequestBody Rental rental){
		try {
			Rental _rental = rentalService.createRental(rental);
			return new ResponseEntity<>(_rental, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	Update d'une rental
//	@PutMapping("/rentals/{id}")
//	public ResponseEntity<Rental> updateRental(@PathVariable("id") Long id, @RequestBody Rental rental){
//		Optional<Rental> rentalData = rentalService.findById(id);
//		
//		if(rentalData.isPresent()) {
//			Rental _rental = rentalData.get();
//			_rental.setName(rental.getName());
//			_rental.setSurface(rental.getSurface());
//			_rental.setPrice(rental.getPrice());
//			_rental.setPicture(rental.getPicture());
//			_rental.setDescription(rental.getDescription());
//			
//			return new ResponseEntity<>(rentalService.UpdateRental(_rental), HttpStatus.OK);
//		}
//		else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
	
	
	@PutMapping("/rentals/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Long id,@RequestBody Rental rental) throws Exception {
		rental.setId(id);
		
        rentalService.UpdateRental(rental);

        return ResponseEntity.ok(rental);
    }

}
