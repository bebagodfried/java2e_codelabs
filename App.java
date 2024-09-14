import java.util.*;

public class App {
    /**
     * @param args
     */
    public static void main(String[] args) {
        // Instance of "post"
        PostDAO postDAO = new PostDAO();
        String[] options = {"My posts", "Create a post", "Show a post", "Update a post", "Delete a post", "Exit"};

        // Display post options
        System.out.println("Post man\n");

        for (int i = 0; i < options.length - 1; i++) {
            System.out.println((i+1) + " - " + options[i]);
        }

        System.out.println("0 - Exit");

        System.out.println("\n");

        // Get a user choose option
        int opt = 0;
        Long id;
        String title;
        String content;

        try (Scanner sc = new Scanner(System.in)) {
            // Step #1
            do {
                System.out.print("Choose an option: ");
                opt = sc.nextInt();
            } while (opt > options.length);
            sc.nextLine();

            // Processing
            switch (opt) {
                case 1: // Get all posts
                    System.out.println("My posts");
                    postDAO.all();

                    break;
                case 2: // Create
                    System.out.print("Enter a post title: ");
                    title    = sc.nextLine();

                    System.out.print("Enter a post content: ");
                    content  = sc.nextLine();

                    postDAO.create(title, content);
                    break;

                case 3: // Show
                    System.out.print("Enter the post id you searching for: ");
                    Long postId = sc.nextLong();

                    postDAO.find(postId);
                    break;

                case 4: // Update
                    System.out.print("Enter a post id: ");
                    id = sc.nextLong();
                    sc.nextLine();

                    System.out.print("New title [id=" + id +"]: ");
                    title = sc.nextLine();

                    System.out.print("New content [id=" + id + "]: ");
                    content = sc.nextLine();

                    postDAO.update(id, title, content);
                    break;
            
                case 5: // Delete
                    System.out.print("Delete post id: ");
                    id = sc.nextLong();
                    sc.nextLine();

                    postDAO.delete(id);
                    break;

                default:
                    break;
            }
        }
    }
}