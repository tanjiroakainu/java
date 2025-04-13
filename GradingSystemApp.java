import java.util.ArrayList;
import java.util.Scanner;

class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class GradingSystem {
    private ArrayList<User> users;
    private String[] students = {"Aldrin", "Kisha", "Malong", "Jefferson", "Theressa"};
    private String[] subjects = {"Science", "Math", "English"};
    private int[][][] scores = new int[5][3][3];

    public GradingSystem() {
        users = new ArrayList<>();
        users.add(new User("Aldrin", "pass123"));
        users.add(new User("Kisha", "kisha321"));
        users.add(new User("Malong", "malong444"));
        users.add(new User("Jefferson", "jeff123"));
        users.add(new User("Theressa", "theressa567"));
    }

    public boolean register(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return false;
            }
        }
        users.add(new User(username, password));
        return true;
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return true;
            }
        }
        return false;
    }

    public String[] getStudents() {
        return students;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void inputScores(Scanner sc) {
        boolean[] completed = new boolean[students.length];

        while (true) {
            System.out.println("Choose a student to assign scores:");
            for (int i = 0; i < students.length; i++) {
                System.out.println((i + 1) + ". " + students[i] + " - " + 
                                  String.join(" - ", subjects) + 
                                  (completed[i] ? " > Completed" : ""));
            }
            System.out.println("6. Exit");

            int studentChoice = -1;
            while (true) {
                System.out.print("Enter your choice: ");
                if (sc.hasNextInt()) {
                    studentChoice = sc.nextInt() - 1;
                    sc.nextLine();
                    if (studentChoice >= 0 && studentChoice <= 5) {
                        break;
                    }
                } else {
                    sc.next();
                }
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
            }

            if (studentChoice == 5) break;

            if (completed[studentChoice]) {
                System.out.println("Re-assign the recently assigned score? 1.Yes 2.No");
                int reassignChoice = -1;
                while (true) {
                    System.out.print("Enter your choice: ");
                    if (sc.hasNextInt()) {
                        reassignChoice = sc.nextInt();
                        sc.nextLine();
                        if (reassignChoice == 1 || reassignChoice == 2) {
                            break;
                        }
                    } else {
                        sc.next();
                    }
                    System.out.println("Invalid input. Please enter 1 for Yes or 2 for No.");
                }
                if (reassignChoice == 2) {
                    continue;
                }
            }

            for (int subj = 0; subj < subjects.length; subj++) {
                System.out.println("Assigning Score for " + subjects[subj] + ":");
                int quiz, task, exam;

                while (true) {
                    System.out.print("Quiz: ");
                    String input = sc.nextLine();
                    try {
                        quiz = Integer.parseInt(input);
                        if (input.startsWith("-") || quiz < 0 || quiz > 30) {
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        // Invalid input, continue loop
                    }
                }

                while (true) {
                    System.out.print("Performance Task: ");
                    String input = sc.nextLine();
                    try {
                        task = Integer.parseInt(input);
                        if (input.startsWith("-") || task < 0 || task > 50) {
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        // Invalid input, continue loop
                    }
                }

                while (true) {
                    System.out.print("Exam: ");
                    String input = sc.nextLine();
                    try {
                        exam = Integer.parseInt(input);
                        if (input.startsWith("-") || exam < 0 || exam > 60) {
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        // Invalid input, continue loop
                    }
                }

                scores[studentChoice][subj][0] = quiz;
                scores[studentChoice][subj][1] = task;
                scores[studentChoice][subj][2] = exam;
            }

            completed[studentChoice] = true;
        }
    }

    public void generateReport() {
        for (int i = 0; i < students.length; i++) {
            System.out.println("\nReport for " + students[i] + ":");
            double totalScore = 0;

            for (int subj = 0; subj < subjects.length; subj++) {
                int quiz = scores[i][subj][0];
                int task = scores[i][subj][1];
                int exam = scores[i][subj][2];
                double subjectTotal = quiz + task + exam;
                totalScore += subjectTotal;
            }

            double percentage = (totalScore / 210) * 70;

            System.out.println("Final Grade: " + percentage + "%");
            System.out.println(percentage >= 60 ? "You passed!" : "You failed!");
        }
    }
}

public class GradingSystemApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GradingSystem gradingSystem = new GradingSystem();
        boolean loggedIn = false;

        String newUsername;
        String newPassword;

        while (true) {
            System.out.print("Enter new username: ");
            newUsername = sc.nextLine();

            if (!containsLetter(newUsername)) {
                System.out.println("Username must contain at least one letter. Please try again.");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Enter new password: ");
            newPassword = sc.nextLine();

            if (!containsLetter(newPassword)) {
                System.out.println("Password must contain at least one letter. Please try again.");
            } else {
                gradingSystem.register(newUsername, newPassword);
                break;
            }
        }

        while (!loggedIn) {
            System.out.println("Login");
            System.out.println("Please enter your 2 recent username and password.");

            System.out.print("Enter username: ");
            String username = sc.nextLine();

            if (!username.equals(newUsername)) {
                System.out.println("Invalid username. Please try again.");
                continue;
            }

            System.out.print("Enter password: ");
            String password = sc.nextLine();

            if (!password.equals(newPassword)) {
                System.out.println("Invalid password. Please try again.");
                continue;
            }

            if (gradingSystem.login(username, password)) {
                loggedIn = true;
                System.out.println("Login successful. Welcome!");
            } else {
                System.out.println("Invalid username or password. Try again.");
            }
        }

        while (true) {
            System.out.println("\nChoose an option from 1 to 4:");
            System.out.println("1. Student & Subject Information");
            System.out.println("2. Class Record");
            System.out.println("3. Generate Report of the Grades");
            System.out.println("4. Log out");

            System.out.print("Enter your choice: ");
            
            // Check if input is a number, exit if not
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Program will exit.");
                sc.close();
                return;  // Exit the program immediately
            }
            
            int choice = sc.nextInt();
            sc.nextLine();  // Clear buffer
            
            // If choice is out of range, exit program
            if (choice < 1 || choice > 4) {
                System.out.println("Invalid choice. Program will exit.");
                sc.close();
                return;  // Exit the program immediately
            }

            switch (choice) {
                case 1:
                    String[] students = gradingSystem.getStudents();
                    String[] subjects = gradingSystem.getSubjects();
                    for (int i = 0; i < students.length; i++) {
                        System.out.println((i + 1) + ". " + students[i] + " - " + 
                                          String.join(" - ", subjects));
                    }
                    break;
                case 2:
                    gradingSystem.inputScores(sc);
                    break;
                case 3:
                    gradingSystem.generateReport();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private static boolean containsLetter(String str) {
        return str.matches(".*[a-zA-Z].*");
    }
}
2.no