package src.main.java.com.altervista.org;

import com.altervista.org.utils.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.common.net.HttpHeaders.X_FORWARDED_PROTO;

@WebServlet(
        name = "ActionServlet",
        urlPatterns = {"/operations/*"}
)
public class DoOperations extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){


        //Controllo se il protocollo è https
        Utils.checkForHttpsProtocol(req, resp);

        try {
            RequestDispatcher view = req.getRequestDispatcher("/index_exemple.jsp");
            view.forward(req,resp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
