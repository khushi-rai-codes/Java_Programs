import java.util.*;

public class ExpressionTree {

    static class Node {
        String value;
        Node left;
        Node right;

        Node(String value) {
            this.value = value;
        }
    }

    static boolean isOperator(String value) {
        return value.equals("+") ||
               value.equals("-") ||
               value.equals("*") ||
               value.equals("/");
    }

    static Node buildTree(String postfix) {
        Stack<Node> stack = new Stack<>();

        String[] tokens = postfix.trim().split("\\s+");

        for (String token : tokens) {
            Node node = new Node(token);

            if (isOperator(token)) {
                node.right = stack.pop();
                node.left = stack.pop();
            }

            stack.push(node);
        }

        return stack.pop();
    }

    static String inorder(Node root) {
        if (root == null) {
            return "";
        }

        if (isOperator(root.value)) {
            return "(" +
                    inorder(root.left) +
                    " " + root.value + " " +
                    inorder(root.right) +
                    ")";
        }

        return root.value;
    }

    static double evaluate(Node root) {
        if (root == null) {
            return 0;
        }

        if (!isOperator(root.value)) {
            return Double.parseDouble(root.value);
        }

        double left = evaluate(root.left);
        double right = evaluate(root.right);

        switch (root.value) {
            case "+":
                return left + right;

            case "-":
                return left - right;

            case "*":
                return left * right;

            case "/":
                if (right == 0) {
                    throw new ArithmeticException(
                            "Division by zero."
                    );
                }

                return left / right;

            default:
                throw new IllegalArgumentException(
                        "Unknown operator."
                );
        }
    }

    static void preorder(Node root) {
        if (root == null) {
            return;
        }

        System.out.print(root.value + " ");

        preorder(root.left);
        preorder(root.right);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "Enter a postfix expression with spaces."
        );

        System.out.println(
                "Example: 10 5 + 2 *"
        );

        System.out.print("Expression: ");

        String postfix = scanner.nextLine();

        try {
            Node root = buildTree(postfix);

            System.out.println(
                    "\nInfix expression:"
            );

            System.out.println(inorder(root));

            System.out.println(
                    "\nPreorder traversal:"
            );

            preorder(root);

            System.out.println(
                    "\n\nResult: " + evaluate(root)
            );

        } catch (Exception e) {
            System.out.println(
                    "Invalid expression: " + e.getMessage()
            );
        }

        scanner.close();
    }
}
