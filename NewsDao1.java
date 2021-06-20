package cn.kgc.dao;

import java.sql.*;

public class NewsDao1 {

    //查询特定新闻的id、标题
    public void getNewsByTitle(String title){
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            String url = "jdbc:mysql://127.0.0.1:3306/kgcnews";
            connection = DriverManager.getConnection(url,"root","123456");
            //sql命令
            String sql = "SELECT id,title FROM news_detail where title='"+title+"'";
            //3、找一个小太监帮皇上执行圣旨（Statement/PreparedStatement）
            stmt = connection.createStatement();
            //4、拉回西瓜（返回结果集ResultSet）
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                int id = rs.getInt("id");
                String newsTitle = rs.getString("title");
                System.out.println(id+"\t"+newsTitle);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            //5、关闭城门（释放资源）
            try {
                rs.close();
                stmt.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        NewsDao1 dao = new NewsDao1();
//        dao.getNewsByTitle("Java Web开课啦");
        dao.getNewsByTitle("Java Web开课啦' or '1'='1");
    }
}
