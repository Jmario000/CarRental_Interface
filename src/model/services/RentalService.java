package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	private Double pricePerHour;
	private Double pricePerDay;
	private BrazilTaxServices taxServices;

	public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxServices taxServices) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxServices = taxServices;
	}

	public void processInvoice(CarRental carRental) {
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
		double hours = minutes / 60;
		double payment;
		if (hours <= 12) {
			payment = pricePerHour * Math.ceil(hours);
		} else {
			payment = pricePerDay * Math.ceil(hours / 24.0);
		}

		double tax = taxServices.tax(payment);

		carRental.setInvoice(new Invoice(payment, tax));
	}
}
