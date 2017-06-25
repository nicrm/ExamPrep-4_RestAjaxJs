package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nicolai
 */
@WebServlet(name = "CountryProxy", urlPatterns = {"/CountryProxy"})
public class CountryProxy extends HttpServlet {

    //Proxy betyder "på vejne af"(stedfortræder) fx hvis en person skal til et møde men et andet menneske er der i hans sted proxy'er denne person den oprindelige.
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Make a new URL with the sent parameter.
        URL url = new URL("http://restcountries.eu/rest/v1/alpha?codes=" + request.getParameter("cc"));
        //Open a new connection to the URL. 
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        //Set the request to GET.
        con.setRequestMethod("GET");
        //Accept and set to json UTF-8.
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        //Read the answer from server and save it to a BufferedReader in the variable bf. 
        BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
        
        
        //Converting BufferedReader JSONObject
        //Solution found here:
        //http://stackoverflow.com/questions/26358684/converting-bufferedreader-to-jsonobject-or-map
        StringBuilder sb = new StringBuilder();
        String line;
        
        
        while ((line = bf.readLine()) != null) {

            sb.append(line);

        }

        //toString the stringBuilder object 'sb' into the json String.
        String json = sb.toString();
       
        //Det er denne headers information der tillader at man kan komme udenom same-origin policy'en
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        
        //Create new PrintWriter.
        PrintWriter out = response.getWriter();
        //Write the json object.
        out.print(json);
        
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
