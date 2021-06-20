package cn.kgc.dao;

import java.sql.*;

/*使用JDBC实现课工场新闻数据的增删改查*/
public class NewsDao2 {
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    //获取数据库连接
    public void getConnection(){
        //1、加载不同数据库厂商提供的驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //2、铺路（获取Connection）
            String url = "jdbc:mysql://127.0.0.1:3306/kgcnews";
            connection = DriverManager.getConnection(url,"root","123456");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //增加新闻信息
    public void addNews(int id,int categoryid,String title,String summary,String content,String author,Date createdate){
        try {
            this.getConnection();
            //3、下圣旨（sql命令）,?占位符
            String sql = "INSERT INTO news_detail(id,categoryid,title,summary,content,author,createdate) VALUES(?,?,?,?,?,?,?)";
            System.out.println(sql);
            //3、找一个小太监帮皇上执行圣旨（Statement/PreparedStatement）
            pstmt = connection.prepareStatement(sql);
            //在sql语句的第一个问号的位置填充title
            pstmt.setInt(1,id);
            pstmt.setInt(2,categoryid);
            pstmt.setString(3,title);
            pstmt.setString(4,summary);
            pstmt.setString(5,content);
            pstmt.setString(6,author);
            pstmt.setTimestamp(7,new Timestamp(createdate.getTime()));

            int i = pstmt.executeUpdate();
            if (i>0){
                System.out.println("插入新闻成功！");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //5、关闭城门（释放资源）
            try {
                pstmt.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //删除特定新闻的方法
    public void deleteNews(int id){
        try {
            this.getConnection();
            //3、下圣旨（sql命令）,?占位符
            String sql = "DELETE FROM news_detail WHERE id=?";
            //3、找一个小太监帮皇上执行圣旨（Statement/PreparedStatement）
            pstmt = connection.prepareStatement(sql);
            //在sql语句的第一个问号的位置填充title
            pstmt.setInt(1,id);

            int i = pstmt.executeUpdate();
            if (i>0){
                System.out.println("删除新闻成功！");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //5、关闭城门（释放资源）
            try {
                pstmt.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //修改特定新闻标题的方法
    public void updateNews(int id,String title){
        try {
            this.getConnection();
            //3、下圣旨（sql命令）,?占位符
            String sql = "UPDATE news_detail SET title=? WHERE id=?";
            //3、找一个小太监帮皇上执行圣旨（Statement/PreparedStatement）
            pstmt = connection.prepareStatement(sql);
            //在sql语句的第一个问号的位置填充title
            pstmt.setString(1,title);
            pstmt.setInt(2,id);

            int i = pstmt.executeUpdate();
            if (i>0){
                System.out.println("修改新闻成功！");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //5、关闭城门（释放资源）
            try {
                pstmt.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //查询全部新闻信息
    public void getNewsList(){
        try {
            this.getConnection();
            //3、下圣旨（sql命令）,?占位符
            String sql = "SELECT id,categoryid,title,summary,content,author,createdate FROM news_detail";
            System.out.println(sql);
            //3、找一个小太监帮皇上执行圣旨（Statement/PreparedStatement）
            pstmt = connection.prepareStatement(sql);
            //4、拉回西瓜（返回结果集ResultSet）
            rs = pstmt.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                int categoryId = rs.getInt("categoryid");
                String newsTitle = rs.getString("title");
                String summary = rs.getString("summary");
                String content = rs.getString("content");
                String author = rs.getString("author");
                Timestamp createdate = rs.getTimestamp("createdate");

                System.out.println(id+"\t"+categoryId+"\t"+newsTitle+"\t"+summary+"\t"+content+"\t"+author+"\t"+createdate);
            }
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


    //查询特定标题的新闻信息
    public void getNewsByTitle(String  title){
        try {
            this.getConnection();
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
        NewsDao2 dao = new NewsDao2();
//        dao.getNewsByTitle("Java Web开课啦");
//        dao.addNews(3,1,"test","test","test","wq",new Date(System.currentTimeMillis()));
//        dao.updateNews(3,"newTitle");
//        dao.getNewsList();
        dao.deleteNews(3);
    }
}
