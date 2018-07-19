package com.herokuapp.logservice;


import com.assetx.libraries.utils.SqlUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(
        name = "HomeServlet",
        urlPatterns = {"/"}
)
public class Home extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        //Read logs
        try {
            //Mi connetto al db
            Connection connection;
            connection = SqlUtils.getConnectionHeroku();

            //Faccio una chiamata al db
            Statement statement;
            String query;

            //query = "SELECT email,password,attivo FROM assetmaxusers";


            try{
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()){

                }
                connection.close();


            }catch (SQLException sqle){
                sqle.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //Register logs
        InputStream in = req.getInputStream();
        int count;
        byte[] buffer = new byte[25000];
        StringBuilder response = new StringBuilder();
        while ((count = in.read(buffer)) > 0)
        {
            response.append(new String(buffer));
        }

    }
}
