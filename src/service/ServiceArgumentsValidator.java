package service;

import java.time.LocalDate;

public class ServiceArgumentsValidator {

	public static void checkIfDateIsPastOrPresent(LocalDate date) {
		if (date.isAfter(LocalDate.now())) {
			throw new RuntimeException("You cannot search for course currency in future.");
		}
	}
}
