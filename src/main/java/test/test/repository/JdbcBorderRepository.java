package test.test.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import test.test.domain.Border;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcBorderRepository implements BorderRepository{

    private final DataSource dataSource;

    public JdbcBorderRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    //게시글 쓰기
    @Override
    public Border write(Border border) {
        String sql;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        sql = "insert into border(title, contents) values(?,?)";

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, border.getTitle());
            pstmt.setString(2, border.getContents());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
            border.setNum(rs.getInt(1));
            }else{
                throw new SQLException("번호 조회 실패");
            }
            System.out.println(pstmt);
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            close(conn,pstmt, rs);
        }

        return border;
    }


    //게시글 읽기
    @Override
    public Optional<Border> borderRead(int num) {
        String sql;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        sql = "select * from border where num = ?";

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();
            if(rs.next()){
                Border border = new Border();
                border.setNum(rs.getInt("num"));
                border.setTitle(rs.getString("title"));
                border.setContents(rs.getString("contents"));
                return Optional.of(border);
            }
        }catch(SQLException e){
            throw new IllegalStateException(e);
        }finally{
            close(conn,pstmt,rs);
        }

        return Optional.empty();
    }

    //게시글 목록
    @Override
    public List<Border> borderList(String title) {
        String sql;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = getConnection();
            if(title != null) {
                sql = "select * from border where title = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, title);
            }else{
               sql = "select * from border";
               pstmt = conn.prepareStatement(sql);
            }
            rs = pstmt.executeQuery();
            List<Border> borders = new ArrayList<>();
            while(rs.next()){
                Border border = new Border();
                border.setNum(rs.getInt("num"));
                border.setTitle(rs.getString("title"));
                borders.add(border);
            }
            return borders;
        }catch(SQLException e){
            throw new IllegalStateException(e);
        }finally{
            close(conn,pstmt,rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
