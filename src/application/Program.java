package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JOptionPane;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxServices;
import model.services.RentalService;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		JOptionPane.showMessageDialog(null, "Enter rental data:");
		String carModel = JOptionPane.showInputDialog("Car model:");
		LocalDateTime start = LocalDateTime.parse((JOptionPane.showInputDialog("Start date: (dd/MM/yyyy HH:mm)")), dtf);
		LocalDateTime finish = LocalDateTime.parse(JOptionPane.showInputDialog("Finish date: (dd/MM/yyyy HH:mm)"), dtf);
		CarRental cr = new CarRental(start, finish, new Vehicle(carModel));

		double hourPrice = Double.parseDouble(JOptionPane.showInputDialog("Price per hour:"));
		double dayPrice = Double.parseDouble(JOptionPane.showInputDialog("Price per day:"));
		RentalService rentalService = new RentalService(hourPrice, dayPrice, new BrazilTaxServices());
		rentalService.processInvoice(cr);

		StringBuilder sb = new StringBuilder("Invoice:\n");
		sb.append("Basic payment: $ ").append(String.format("%.2f", cr.getInvoice().getBasicPayment()))
				.append("\nTax: $ " + String.format("%.2f", cr.getInvoice().getTax()))
				.append("\nTotal Payment: $ " + String.format("%.2f", cr.getInvoice().getTotalPayment()));

		JOptionPane.showMessageDialog(null, sb);
	}

}
