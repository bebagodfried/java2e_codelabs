import java.sql.*;

public class PostDAO {
    private int id;
    private String title, content;

    public PostDAO() {}
    public PostDAO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        System.out.println();

        return "\n{\n   \"title\" : \"" + this.title + "\"\n   \"content\" : \"" + this.content + "\"\n}";
    }
    
    /**
     * 
     */
    public void all() {
        String posts = "select * from POST";

        try (Connection conn = DBase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(posts)) {
            ResultSet rs = stmt.executeQuery(posts);
            
            StringBuilder response = new StringBuilder("");
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");

                response.append("{\n   \"id\" : \"" + id + "\n   \"title\" : \"" + title + ",\"\n   \"content\" : \"" + content + "\"\n},\n");
            }

            if (response.length() > 1) {
                response.setLength(response.length() - 2);
            }

            // response.append("]");

            System.out.println(response.toString());
        } catch (Exception e) {
            e.getCause();
        }
    }
    
    /**
     * Create a specifique resource
     * @param title
     * @param content
     * @return
     */
    public void create(String title, String content) {
        PostDAO postDAO = new PostDAO(title, content);

        String createQuery = "insert into POST (title, content) values (?, ?)";

        try (Connection conn = DBase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(createQuery)) {
            
            stmt.setString(1, title);
            stmt.setString(2, content);
            stmt.executeUpdate();

            System.out.println(postDAO.getCreationResponse());

        } catch (Exception e) {
            e.getCause();
        }
    }

    public String getCreationResponse() {
        System.out.println();

        return "\n{\n   \"massage\": \"created successful\"" 
                + "\n   \"title\": \"" + this.title
                + "\"\n   \"content\": \"" + this.content
                + "\"\n}";
    }

    /**
     * Show a specific resource
     * @param id
     * @return
     */
    public String find(Long id) {
        String findQuery = "select * from POST where id = ?";

        try (Connection conn = DBase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(findQuery)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            // rs.next();

            if (rs.next()) {
                int postId = rs.getInt("id");
                String postTitle = rs.getString("title");
                String postContent = rs.getString("content");

                String response = "\n{"
                    + "\n   \"id\": " + postId + ","
                    + "\n   \"title\": \"" + postTitle + "\","
                    + "\n   \"content\": \"" + postContent
                    + "\"\n}";

                System.out.println(response);
            } else {
                throw new SQLDataException();
            }

        } catch (Exception e) {
            // java.sql.SQLDataException
            System.out.println("\nNot found!");
        }

        return null;
    }

    /**
     * Update a specific resource
     * @param id
     * @param title
     * @param content
     * @return
     */
    public void update(Long id, String title, String content) {
        PostDAO updatedPost = new PostDAO(title, content);
        String updateQuery  = "update POST set title = ?, content = ? where id = ?";

        try (Connection conn = DBase.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, title);
            stmt.setString(2, content);
            stmt.setLong(3, id);
            
            int updated = stmt.executeUpdate();
            System.out.println(updated);

            if(updated > 0) {
                System.out.println(updatedPost.getUpdateResponse());
            } else {
                System.err.println("Uptate failure");
            }
            
        } catch (Exception e) {
            e.getCause();
        }
    }

    public String getUpdateResponse() {
        System.out.println();

        return "\n{\n   \"massage\": \"update successful\""
                + "\n   \"title\": \"" + this.title
                + "\"\n   \"content\": \"" + this.content
                + "\"\n}";
    }

    /**
     * 
     * @param id
     * @return
     */
    public void delete(Long id) {
        String deleteQuery = "delete from POST where id = ?";

        try (Connection conn = DBase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(deleteQuery)
            ) {
                stmt.setLong(1, id);
                int deleted = stmt.executeUpdate();

                if (deleted > 0) {
                    System.err.println("Post deleted successful");
                } else {
                    
                }

        } catch (SQLException e) {
            System.err.println("Bad request, post not found.");
            e.getCause();
        }
    }
}