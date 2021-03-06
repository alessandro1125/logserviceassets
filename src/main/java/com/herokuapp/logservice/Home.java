package com.herokuapp.logservice;


import com.assetx.libraries.utils.SqlUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.Any;

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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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
                HashMap<String, String> datas = new HashMap();

                LocalDateTime dateTime = LocalDateTime.ofEpochSecond(log.getInt("time"), 0, ZoneOffset.ofHours(2));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE,MMMM d,yyyy h:mm,a", Locale.ENGLISH);
                String formattedDate = dateTime.format(formatter);

                datas.put("time", Integer.toString(log.getInt("time")));
                datas.put("timeread", formattedDate);
                datas.put("value", log.getString("value"));
                datas.put("id_device", log.getString("id_device"));
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
