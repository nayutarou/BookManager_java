import java.sql.*;
import java.util.ArrayList;

public class BookDAO {
    private static final String DB_URL = "jdbc:sqlite:book.sqlite";

    //追加
    public static BookBean insertBooks(int id, String title, String author) {
        String sql = "INSERT INTO Book VALUES (?, ?, ?)";
        try (
                Connection con = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            //プレースホルダに値をセット
            pstmt.setInt(1, id);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            //コミット
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static BookBean SelectById(int id) {
        String sql = "SELECT * FROM Book WHERE id = ?";
        BookBean result = null;
        try (
                Connection con = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) //検索結果があれば返す
                {
                    result = new BookBean(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author")
                    );
//                    rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return result; //結果なしの場合null
    }

    //Select by All
    public static ArrayList<BookBean> selectAll() {
        String sql = "SELECT * FROM Book";
        ArrayList<BookBean> results = new ArrayList<>();

        try (
                Connection con = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) { //次の行がある間繰り返す
                results.add(
                        new BookBean(
                                rs.getInt("Id"),
                                rs.getString("title"),
                                rs.getString("author")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return results;
    }

    //Select like Name(あいまい検索)
    public static ArrayList<BookBean> SelectLikeName(String author) {
        String sql = "SELECT * FROM Book WHERE author LIKE ?";
        ArrayList<BookBean> result1 = new ArrayList<>();

        try (
                Connection con = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setString(1, "%" + author + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {//検索結果があれば返す
                    result1.add(new BookBean(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author")
                    ));
                    rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println("該当する名前はありません");
        }
        return result1; //結果なしの場合null
    }

    //SelectLikeTitle(タイトル検索)
    public static ArrayList<BookBean> SelectLikeTitle(String title) {
        String sql = "SELECT * FROM Book WHERE title LIKE ?";
        ArrayList<BookBean> result2 = new ArrayList<>();

        try (
                Connection con = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setString(1, "%" + title + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {//検索結果があれば返す
                    result2.add(new BookBean(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author")
                    ));
                    rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println("該当する名前はありません");
        }
        return result2; //結果なしの場合null
    }

    //delete(削除)
    public static BookBean Delete_Book(int id) {
        String sql = "DELETE FROM Book WHERE id = (?)";

        try (
                Connection con = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("失敗");
        }
        return null;
    }
}