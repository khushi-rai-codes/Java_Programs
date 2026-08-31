import java.util.Scanner;
public class QuizApplication {
    static String[] questions = {
        "Which data structure uses FIFO?",
        "Which keyword creates a class in Java?",
        "Which method starts a Java program?",
        "Which collection does not allow duplicate elements?"
    };
    static String[][] options = {
        {"A. Stack", "B. Queue", "C. Tree", "D. Graph"},
        {"A. class", "B. define", "C. object", "D. struct"},
        {"A. start()", "B. run()", "C. main()", "D. execute()"},
        {"A. ArrayList", "B. LinkedList", "C. HashSet", "D. Vector"}
    };
    static char[] answers = {'B', 'A', 'C', 'C'};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int score = 0;
        System.out.println("===== JAVA QUIZ =====");
        for (int i = 0; i < questions.length; i++) {
            System.out.println("\nQuestion " + (i + 1));
            System.out.println(questions[i]);
            for (String option : options[i]) {
                System.out.println(option);
            }
            System.out.print("Enter your answer: ");
            char answer =
                Character.toUpperCase(sc.next().charAt(0));
            if (answer == answers[i]) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println(
                    "Wrong! Correct answer: "
                    + answers[i]
                );
            }
        }
        System.out.println("\n===== RESULT =====");
        System.out.println(
            "Score: " + score +
            "/" + questions.length
        );
        double percentage =
            (score * 100.0) / questions.length;
        System.out.printf(
            "Percentage: %.2f%%%n",
            percentage
        );
        sc.close();
    }
}
