package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Form extends HttpServlet {

    // 加载JDBC
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/testmedicine";
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "123456";

    public Form() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = null;
        Statement stmt = null;

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String title = "判断结果";
        String name =new String(request.getParameter("input").getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);


        out.println("<!DOCTYPE html> \n" +
                "<html>\n" +
                "<head>"+"<meta charset=\"utf-8\">"+
                "<title>" + title + "</title></head>\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                "<ul>\n" +
                "  <li><b>结果</b>："
        );


        try{
            // 注册 JDBC 驱动器
            Class.forName("com.mysql.jdbc.Driver");
            // 打开一个连接
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // 执行 SQL 查询
            stmt = conn.createStatement();

            String sql;
            sql = "SELECT medicine,illness url FROM testmedicine";
            ResultSet rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                String url = rs.getString("url");

                // 输出数据
                out.println("ID: " + id);
                out.println(", 站点名称: " + name);
                out.println(", 站点 URL: " + url);
                out.println("<br />");
            }
            out.println("</body></html>");

            rs.close();
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }



        out.println("\n" +
                "</ul>\n" +
                "</body></html>");




    }

    // 处理 POST 方法请求的方法
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}




