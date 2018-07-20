package com.herokuapp.logservice;


import com.assetx.libraries.utils.SqlUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

@WebServlet(
        name = "HomeServlet",
        urlPatterns = {"/log/*"}
)
public class Home extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //Register logs
        InputStream in = req.getInputStream();
        int count;
        byte[] buffer = new byte[25000];
        StringBuilder response = new StringBuilder();
        while (in.read(buffer) > 0)
        {
            response.append(new String(buffer));
        }

        System.out.println("Response: " + response);


        try {
            JSONObject object = new JSONObject(response.toString());
            JSONArray array = object.getJSONArray("logs");

            for (int i = 0; i< array.length(); i++){
                JSONObject log = array.getJSONObject(i);
                //Salvo nel db
                Connection connection;
                connection = SqlUtils.getConnectionHeroku();
                HashMap<String, String> datas = new HashMap<String, String>();
                datas.put("time", log.getString("time"));
                datas.put("value", log.getString("value"));
                SqlUtils.sqlAdd(connection, datas, "logs");
                connection.close();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
