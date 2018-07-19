package src.main.java.com.altervista.org;

import com.altervista.org.utils.Utils;
import com.assetx.libraries.utils.http.HttpRequestInstance;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import static com.google.common.net.HttpHeaders.X_FORWARDED_PROTO;

@WebServlet(
        name = "DownloadZip",
        urlPatterns = "/download_zip/*"
)
public class DownloadAssetMax extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){

        //Controllo se il protocollo è https
        Utils.checkForHttpsProtocol(request, response);

        try {
            OutputStream outputStream = response.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(getClass().getResource("/AssetMax.zip").getFile());

            String filename = "AssetMax.zip";

            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");

            int bytes;
            while ((bytes = fileInputStream.read()) != -1) {
                System.out.println(bytes);
                outputStream.write(bytes);
            }
            fileInputStream.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}