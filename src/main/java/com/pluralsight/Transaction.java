package com.pluralsight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

//public record Transaction(LocalDate date, LocalTime time, String description, String vendor, BigDecimal amount) {
//}

public record Transaction(String description, String vendor, BigDecimal amount) {
}
