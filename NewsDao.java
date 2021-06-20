package cn.kgc.dao;

import java.sql.*;

/*使用PreparedStatement*/
public class NewsDao {
    //查询特定新闻的id、标题
    public void getNewsByTitle(String  title){
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //1、加载不同数据库厂商提供的驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2、铺路（获取Connection）
            String url = "jdbc:mysql://127.0.0.1:3306/kgcnews";
            connection = DriverManager.getConnection(url,"root","123456");
            //3、下圣旨（sql命令）,?占位符
            String sql = "SELECT id,title FROM news_detail where title=?";
            System.out.println(sql);
            //3、找一个小太监帮皇上执行圣旨（Statement/PreparedStatement）
            pstmt = connection.prepareStatement(sql);
            //在sql语句的第一个问号的位置填充title
            pstmt.setString(1,title);
            //4、拉回西瓜（返回结果集ResultSet）
            rs = pstmt.executeQuery();
            while (rs.next()){
                /*int id = rs.getInt(1);
                String title = rs.getString(2);*/
                int id = rs.getInt("id");
                String newsTitle = rs.getString("title");
                System.out.println(id+"\t"+newsTitle);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //5、关闭城门（释放资源）
            try {
                rs.close();
                pstmt.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        NewsDao dao = new NewsDao();
//        dao.getNewsByTitle("Java Web开课啦");
        dao.getNewsByTitle("Java Web开课啦' or '1'='1");
    }
}
