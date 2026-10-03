class BinarySearchTree {

    static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
        }
    }

    Node root;

    Node insert(Node root, int data) {
        if (root == null) {
            return new Node(data);
        }

        if (data < root.data) {
            root.left = insert(root.left, data);
        } else if (data > root.data) {
            root.right = insert(root.right, data);
        }

        return root;
    }

    Node findMinimum(Node root) {
        Node current = root;

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    Node delete(Node root, int data) {
        if (root == null) {
            return null;
        }

        if (data < root.data) {
            root.left = delete(root.left, data);
        } else if (data > root.data) {
            root.right = delete(root.right, data);
        } else {

            // No left child
            if (root.left == null) {
                return root.right;
            }

            // No right child
            if (root.right == null) {
                return root.left;
            }

            // Two children
            Node successor = findMinimum(root.right);
            root.data = successor.data;
            root.right = delete(root.right, successor.data);
        }

        return root;
    }

    void inorder(Node root) {
        if (root == null) {
            return;
        }

        inorder(root.left);
        System.out.print(root.data + " ");
        inorder(root.right);
    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();

        int[] values = {50, 30, 70, 20, 40, 60, 80};

        for (int value : values) {
            tree.root = tree.insert(tree.root, value);
        }

        System.out.println("Inorder traversal:");
        tree.inorder(tree.root);

        tree.root = tree.delete(tree.root, 30);

        System.out.println("\n\nAfter deleting 30:");
        tree.inorder(tree.root);

        tree.root = tree.delete(tree.root, 50);

        System.out.println("\n\nAfter deleting 50:");
        tree.inorder(tree.root);

        System.out.println();
    }
}
