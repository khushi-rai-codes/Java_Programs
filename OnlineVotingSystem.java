import java.util.ArrayList;
import java.util.Scanner;

class Candidate {
    private int id;
    private String name;
    private int votes;

    public Candidate(int id, String name) {
        this.id = id;
        this.name = name;
        this.votes = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }

    public void addVote() {
        votes++;
    }
}

public class OnlineVotingSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Candidate> candidates = new ArrayList<>();

        candidates.add(new Candidate(1, "Candidate A"));
        candidates.add(new Candidate(2, "Candidate B"));
        candidates.add(new Candidate(3, "Candidate C"));

        System.out.println("===== ONLINE VOTING SYSTEM =====");

        System.out.print("Enter your voter ID: ");
        String voterId = scanner.nextLine();

        if (voterId.trim().isEmpty()) {
            System.out.println("Invalid voter ID.");
            scanner.close();
            return;
        }

        System.out.println("\nCandidates:");

        for (Candidate candidate : candidates) {
            System.out.println(
                candidate.getId() + ". " + candidate.getName()
            );
        }

        System.out.print("\nEnter candidate number to vote: ");
        int choice = scanner.nextInt();

        boolean voteCast = false;

        for (Candidate candidate : candidates) {
            if (candidate.getId() == choice) {
                candidate.addVote();
                voteCast = true;
                System.out.println(
                    "\nVote successfully cast by voter " + voterId + "!"
                );
                break;
            }
        }

        if (!voteCast) {
            System.out.println("\nInvalid candidate choice.");
        }

        System.out.println("\n===== CURRENT RESULTS =====");

        for (Candidate candidate : candidates) {
            System.out.println(
                candidate.getName() + " : " + candidate.getVotes() + " vote(s)"
            );
        }

        scanner.close();
    }
}
