package boids;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Objects;

import util.Point2D;
import util.Vector2D;

public class Particule extends Objet implements Cloneable {

	private Vector2D vitesse = new Vector2D();

	private double distanceVision = 1.0;

	private boolean shadow = false;

	private Color couleur = Color.RED;

	private double dureeVie = Double.POSITIVE_INFINITY;

	public Particule() {

	}

	public Particule withVitesse(Vector2D vitesse) {
		this.setVitesse(vitesse);
		return this;
	}

	public Particule withPosition(Point2D position) {
		return (Particule) super.withPosition(position);
	}

	public Particule withShadow(boolean shadow) {
		this.setShadow(shadow);
		return this;
	}

	public Particule withDistanceVision(double vision) {
		this.setDistanceVision(vision);
		return this;
	}

	public Particule withCouleur(Color couleur) {
		this.setCouleur(couleur);
		return this;
	}

	public Particule withDureeVie(double dureeVie) {
		this.setDureeVie(dureeVie);
		return this;
	}

	public Vector2D getVitesse() {
		return vitesse;
	}

	public void setVitesse(Vector2D vitesse) {
		Objects.requireNonNull(vitesse);
		this.vitesse = vitesse;
	}

	public boolean isShadow() {
		return shadow;
	}

	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color couleur) {
		Objects.requireNonNull(couleur);
		this.couleur = couleur;
	}

	public double getDistanceVision() {
		return distanceVision;
	}

	public void setDistanceVision(double distanceVision) {
		this.distanceVision = distanceVision;
	}

	public double getDureeVie() {
		return dureeVie;
	}

	public void setDureeVie(double dureeVie) {
		this.dureeVie = dureeVie;
	}

	public boolean particuleVoitAutreParticule(Particule particule) {
		Objects.requireNonNull(particule);
		return position.minus(particule.position).getMagnitude() <= distanceVision;
	}

	@Override
	public Particule clone() throws CloneNotSupportedException {
		return ((Particule) super.clone()).withVitesse(vitesse).withDistanceVision(distanceVision).withDureeVie(dureeVie).withShadow(shadow)
				.withCouleur(couleur);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Particule)) {
			return false;
		}

		Particule p = (Particule) obj;
		return p.uuid == uuid;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int posX = (int) this.getPosition().x;
		int posY = (int) this.getPosition().y;
		int r2 = (int) this.getDistanceVision();
		int d2 = (int) (this.getDistanceVision() / 2.0);
		int vX = (int) (this.getVitesse().x * 10.0);
		int vY = (int) (this.getVitesse().y * 10.0);
		g.setColor(this.getCouleur());
		// g.fillRect(posX, posY, 1, 1);
		g.fillOval(posX - d2, posY - d2, r2, r2);
		g.setColor(Color.WHITE);
		g.drawLine(posX, posY, posX + vX, posY + vY);
	}

}
