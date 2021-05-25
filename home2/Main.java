package home2;
import java.util.*;

class Student {
    private String name;
    private int tasks;

    Student(String newName, int newTasks) {
        this.name = newName;
        this.tasks = newTasks;
    }

    void addTask() {
        this.tasks++;
    }

    void printInfo() {
        System.out.print("Student " + this.name + " passed " + this.tasks + " tasks\n");
    }

    int getTasks() {
        return this.tasks;
    }

    String getName() {
        return this.name;
    }
}

class Journal {
    Map<String, Student> students = new HashMap<>();
    List<Student> allStudents = new ArrayList<>();
    List<Student> passedExam = new ArrayList<>();

    Journal() {
        System.out.print("To add student press 'A'\n" +
                         "To view a list of all students press 'B'\n" +
                         "To mark that the student has passed the task press 'C'\n" +
                         "To view an information about student press 'D'\n" +
                         "To see the list of students who have passed the exam press 'E'\n" +
                         "To exit from the program press 'F' \n");
    }

    void addStudent(Scanner in) {
        System.out.print("What is the name of student?\n");
        String name = in.nextLine();
        Student student = new Student(name, 0);
        students.put(name, student);
        allStudents.add(student);
    }

    void printList() {
        System.out.println("List of all students:");
        for (Student e : allStudents) {
            System.out.print(e.getName() + " ");
        }
        System.out.println();
    }

    void passTask(Scanner in) {
        System.out.print("What is the name of student that passed the task?\n");
        String name = in.nextLine();
        boolean hasKey = students.containsKey(name);
        if (!hasKey) {
            System.out.print("There is no such student\n");
        } else {
            students.get(name).addTask();
        }
    }

    void aboutStudent(Scanner in) {
        System.out.print("Information about which student you want to see?\n");
        String name = in.nextLine();
        boolean hasKey = students.containsKey(name);
        if (!hasKey) {
            System.out.print("There is no such student\n");
        } else {
            students.get(name).printInfo();
        }
    }

    void passedExam(Scanner in) {
        passedExam.clear();
        System.out.print("How many tasks student have to solve to pass the exam?\n");
        int limit = in.nextInt();
        for (String key : students.keySet()) {
            Student user = students.get(key);
            if (user.getTasks() >= limit) {
                passedExam.add(user);
            }
        }
        System.out.println("Students that has passed the exam:");
        for (Student e : passedExam) {
            System.out.print(e.getName() + " ");
        }
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        Journal journal = new Journal();
        Scanner in = new Scanner(System.in);
        while (true) {
            String symbol = in.nextLine();
            switch(symbol) {
                case "A":
                    journal.addStudent(in);
                    break;
                case "B":
                    journal.printList();
                    break;
                case "C":
                    journal.passTask(in);
                    break;
                case "D":
                    journal.aboutStudent(in);
                    break;
                case "E":
                    journal.passedExam(in);
                    break;
                case "F":
                    in.close();
                    System.exit(0);
            }
        }
    }
}
