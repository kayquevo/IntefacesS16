package model.services;

import java.time.Duration;
import java.time.LocalDateTime;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	private Double pricePerHour;
	private Double pricePerDay;
	
	private TaxService taxService;
	
	public RentalService() {
		
	}

	public RentalService(Double pricePerHour, Double pricePerDay, TaxService taxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public TaxService getTaxService() {
		return taxService;
	}

	public void setTaxService(TaxService taxService) {
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		LocalDateTime start = carRental.getStart();
		LocalDateTime finish = carRental.getFinish();
		
		double minutes = Duration.between(start, finish).toMinutes();
		
		double hours = minutes / 60.0;
		
		Double basicPayment;
		
		if(hours <= 12) {
			basicPayment = pricePerHour * Math.ceil(hours);
		}
		else {
			basicPayment = pricePerDay * Math.ceil(hours /24.0);
		}
		
		Double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
			
	}
	
	
	
}
