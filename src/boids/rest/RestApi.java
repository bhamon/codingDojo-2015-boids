package boids.rest;

import http.HttpResponse;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Point2D;
import boids.Monde;
import boids.Objet;
import boids.OutOfBoundsException;
import boids.Particule;
import boids.ui.FrameMain;

@WebServlet(
        name = "RestApiServlet",
        urlPatterns = {"/restapi"}
    )
public class RestApi extends HttpServlet {

    private static final long serialVersionUID = 8515537154545142105L;

    Monde monde = FrameMain.getInstance();
    
    // TODO: définir des ressources grâce aux annotations
    
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		ServletOutputStream out = resp.getOutputStream();
        String retour = addParticule(req, resp);
		retour += "\n" + listParticules(req, resp);
		
		out.write(retour.getBytes());
        
        out.flush();
        out.close();
    }
	
	protected String listParticules(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

		StringBuilder sb = new StringBuilder();
		for (Objet objet : monde.getListobjet()){
			
			sb.append(objet.toString());
			sb.append("\n");
		}
		return sb.toString();
        
    
	}
	
	
	protected String addParticule(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
	        String retour = "le nombre de particules : " + monde.getParticulesNb();
	        
	        double x = 0;
	        double y = 0;
	        try {
	        	x = Double.parseDouble(req.getParameter("x"));
	        	y = Double.parseDouble(req.getParameter("y"));
			} catch (NullPointerException e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "x and y must be declared!!!!");
				return null;
			} catch (NumberFormatException e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "x and y must be numbers!!!!");
				return null;
			}
	        
	        Particule particule = new Particule().withPosition(new Point2D(x, y));
	        try {
				monde.add(particule);
			} catch (OutOfBoundsException e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "x and y must be lower than world's dimensions!!!!");
				return null;
			} catch (CloneNotSupportedException e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "good luck!");
				return null;
			}

	        retour += ". Le nombre de particules : " + monde.getParticulesNb();
	        return retour;
	    }

}