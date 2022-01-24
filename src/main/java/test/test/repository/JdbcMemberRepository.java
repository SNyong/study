package test.test.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import test.test.domain.Member;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.datasource.DataSourceUtils;

public class JdbcMemberRepository implements MemberRepository{

  private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        sql = "insert into members(id, pw) values(?,?)";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Statement.RETURN_GENERATED_KEYS -> 자동으로 번호를 생성

            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getPw());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                member.setNum(rs.getInt(1));
            } else {
                throw new SQLException("회원번호 조회 실패");
            }
            System.out.println(pstmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return member;
    }

    @Override
    public Optional<Member> findByNum(int num) {

        String sql = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        sql = "select * from members where num = ?";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();
            System.out.println(pstmt);
            if(rs.next()){
                Member member = new Member();
                member.setNum(rs.getInt("num"));
                member.setId(rs.getString("id"));
                member.setPw(rs.getString("pw"));
                return Optional.of(member);
            }else{
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findById(String id) {
        String sql = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        sql = "select * from members where id = ?";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            System.out.println(pstmt);
            if(rs.next()){
                Member member = new Member();
                member.setNum(rs.getInt("num"));
                member.setId(rs.getString("id"));
                member.setPw(rs.getString("pw"));
                return Optional.of(member);
            }else{
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }


    @Override
    public List<Member> findAll() {

        String sql = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        sql = "select * from members";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println(pstmt);
            List<Member> members = new ArrayList<>();
            while (rs.next()){
                Member member = new Member();
                member.setNum(rs.getInt("num"));
                member.setId((rs.getString("id")));
                member.setPw(rs.getString("pw"));
                members.add(member);

            }
                return members;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }finally {
            close(conn,pstmt,rs);
        }
    }




    /** getConnection, close 셋팅
     */
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
