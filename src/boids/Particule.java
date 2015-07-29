package boids;

import java.awt.Color;
import java.util.Objects;
import java.util.UUID;

import util.Point2D;
import util.Vector2D;

public class Particule implements Cloneable {

	private final UUID uuid;

	private Vector2D vitesse = new Vector2D();

	private Point2D position = new Point2D();

	private double distanceVision = 1.0;

	private boolean shadow = false;

	private Color couleur = Color.RED;

	private double dureeVie = Double.POSITIVE_INFINITY;

	public Particule() {
		this.uuid = UUID.randomUUID();
	}

	public Particule(UUID uuid) {
		this.uuid = uuid;
	}

	public Particule withVitesse(Vector2D vitesse) {
		this.setVitesse(vitesse);
		return this;
	}

	public Particule withPosition(Point2D position) {
		this.setPosition(position);
		return this;
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

	public UUID getUuid() {
		return uuid;
	}

	public Vector2D getVitesse() {
		return vitesse;
	}

	public void setVitesse(Vector2D vitesse) {
		Objects.requireNonNull(vitesse);
		this.vitesse = vitesse;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		Objects.requireNonNull(position);
		this.position = position;
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
	public Particule clone() {
		return new Particule(uuid).withPosition(position).withVitesse(vitesse).withDistanceVision(distanceVision).withDureeVie(dureeVie)
				.withShadow(shadow).withCouleur(couleur);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Particule)) {
			return false;
		}

		Particule p = (Particule) obj;
		return p.uuid == uuid;
	}
}
