package boids.rest;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boids.Monde;
import boids.Objet;
import boids.OutOfBoundsException;
import boids.Particule;
import boids.ui.FrameMain;

@WebServlet(name = "RestApiServlet")
public class RestApi extends HttpServlet {

	private static final long serialVersionUID = 8515537154545142105L;

	Monde monde = FrameMain.getInstance();

	// TODO: définir des ressources grâce aux annotations

	public RestApi(Monde monde2) {
		monde = monde2;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletOutputStream out = resp.getOutputStream();

		String retour = "";

		if ("/listParticules".equals(req.getPathInfo())) {
			retour = listParticules(req, resp);
		} else if ("/addParticule".equals(req.getPathInfo())) {
			retour = addParticule(req, resp);
		}

		out.write(retour.getBytes());

		out.flush();
		out.close();
	}

	protected String listParticules(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		StringBuilder sb = new StringBuilder();
		for (Objet objet : monde.getListobjet()) {

			sb.append(objet.getUuid().toString());
			sb.append("\n");
		}
		return sb.toString();

	}

	protected String addParticule(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String retour = null;

		Particule particule = new Particule(UUID.fromString(req.getParameter("uuid")));
		try {
			monde.add(particule);
		} catch (OutOfBoundsException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "x and y must be lower than world's dimensions!!!!");
			return null;
		} catch (CloneNotSupportedException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "good luck!");
			return null;
		}

		retour = particule.getUuid().toString();
		return retour;
	}

}