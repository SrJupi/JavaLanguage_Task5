package n1e05;

import java.io.Serializable;
import java.util.Random;

public class Person implements Serializable {
    private String name;
    private int age;
    private String job;
    private int salary;

    public Person(){
        Random rand = new Random();

        String [] names = {"Marc", "Leo", "Alex", "Jan", "Nil",
                "Julia", "Martina", "Emma", "Lucia", "Ona"};
        String [] jobs = {"Dentist", "Nurse", "Pharmacist", "Physician", "Computer Analyst",
                "Software Developer", "Teacher", "Veterinarian", "Engineer", "Lawyer"};

        this.name = names[rand.nextInt(0,10)];
        this.age = rand.nextInt(18, 100);
        this.job = jobs[rand.nextInt(0,10)];
        this.salary = rand.nextInt(12000, 100000);

    }

    public Person(String name, int age, String job, int salary){
        this.name = name;
        this.age = age;
        this.job = job;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getJob() {
        return job;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", job='" + job + '\'' +
                ", salary=" + salary +
                '}';
    }
}
