package main;

import java.time.LocalDate;

public record User(String name, Integer age, Boolean blocked, LocalDate birthDate) {
}
