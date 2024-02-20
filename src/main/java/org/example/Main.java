package org.example;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(random.nextInt(names.size())),
                    families.get(random.nextInt(families.size())),
                    random.nextInt(100),
                    Sex.values()[random.nextInt(Sex.values().length)],
                    Education.values()[random.nextInt(Education.values().length)])
            );
        }

        // Несовершеннолетние
        long minors = persons.stream()
                .filter(p -> p.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + minors);

        // Призывники
        List<String> conscripts = persons.stream()
                .filter(p -> p.getAge() >= 18 && p.getAge() < 27 && p.getSex() == Sex.MAN)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Список фамилий призывников (первые 100):");
        conscripts.stream().limit(100).forEach(System.out::println);

        // Работоспособные с высшим образованием
        List<Person> workable = persons.stream()
                .filter(p -> p.getAge() >= 18 && ((p.getSex() == Sex.MAN && p.getAge() < 65) || (p.getSex() == Sex.WOMAN && p.getAge() < 60)))
                .filter(p -> p.getEducation() == Education.HIGHER)
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println("Список потенциально работоспособных людей с высшим образованием (первые 100):");
        workable.stream().limit(100).forEach(person ->
                System.out.println(person.getFamily() + " " + person.getName() + ", " + person.getAge() + ", " + person.getSex() + ", " + person.getEducation()));
    }
}
