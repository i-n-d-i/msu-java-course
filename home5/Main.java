import java.util.*;
import java.sql.*;


class Journal {
    int id = 0;
    String sql = null;
    Connection connect = null;
    Statement stmt = null;

    Journal() {
        System.out.print("To add student press 'A'\n" +
                         "To view a list of all students press 'B'\n" +
                         "To mark that the student has passed the task press 'C'\n" +
                         "To view an information about student press 'D'\n" +
                         "To see the list of students who have passed the exam press 'E'\n" +
                         "To exit from the program press 'F' \n");
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:test.db");
            connect.setAutoCommit(false);

            stmt = connect.createStatement();
            sql = "CREATE TABLE JOURNAL " +
                  "(ID             INT     NOT NULL," +
                  " NAME           TEXT    NOT NULL," +
                  " TASKS          INT     NOT NULL)";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    void addStudent(Scanner in) {
        System.out.print("What is the name of student?\n");
        String name = in.nextLine();
        id += 1;
        try {
            PreparedStatement statement = this.connect.prepareStatement(
                        "INSERT INTO JOURNAL(ID, NAME, TASKS) " + "VALUES(?, ?, ?)");
            statement.setObject(1, id);
            statement.setObject(2, name);
            statement.setObject(3, 0);
            statement.execute();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    void printList() {
        System.out.println("List of all students:");
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM JOURNAL;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println(id + " " + name);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    void passTask(Scanner in) {
        System.out.print("What is the name of student that passed the task?\n");
        String studentName = in.nextLine();
        boolean hasKey = false;
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM JOURNAL;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String tableName = rs.getString("name");
                int tasks = rs.getInt("tasks");
                if (tableName.equals(studentName)) {
                    hasKey = true;
                    tasks += 1;
                    sql = "UPDATE JOURNAL set TASKS =" + tasks + " where ID =" + id;;
                    stmt.executeUpdate(sql);
                    connect.commit();
                }

            }
            rs.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        if (!hasKey) {
            System.out.print("There is no such student\n");
        }
    }

    void aboutStudent(Scanner in) {
        System.out.print("Information about which student you want to see?\n");
        String studentName = in.nextLine();
        boolean hasKey = false;
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM JOURNAL;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String tableName = rs.getString("name");
                int tasks = rs.getInt("tasks");
                if (tableName.equals(studentName)) {
                    hasKey = true;
                    System.out.println("Student " + tableName + " passed " + tasks + " tasks");
                }

            }
            rs.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        if (!hasKey) {
            System.out.print("There is no such student\n");
        }
    }

    void passedExam(Scanner in) {
        System.out.print("How many tasks student have to solve to pass the exam?\n");
        int limit = in.nextInt();
        int num = 0;

        System.out.println("Students that has passed the exam: ");
        try {
            ResultSet rs = stmt.executeQuery( "SELECT * FROM JOURNAL;" );
            while (rs.next()) {
                int id = rs.getInt("id");
                String tableName = rs.getString("name");
                int tasks = rs.getInt("tasks");
                if (tasks >= limit) {
                    System.out.println("Name: " + tableName + " Passed tasks: " + tasks);
                    num += 1;
                }

            }
            rs.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        if (num == 0) {
            System.out.println("No one :(");
        }
    }

    void exit() {
        try {
            stmt.close();
            connect.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
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
                    journal.exit();
                    in.close();
                    System.exit(0);
            }
        }
    }
}
