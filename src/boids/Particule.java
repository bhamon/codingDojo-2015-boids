package boids;

import java.awt.Color;
import java.util.Objects;
import java.util.UUID;

import util.Point2D;
import util.Vector2D;

public class Particule implements Cloneable {

	private UUID uuid;

	private Vector2D vitesse;

	private Point2D position;

	private double distanceVision;

	private Color couleur;

	private Particule(UUID uuid, Vector2D vitesse, Point2D position, double distanceVision, Color couleur) {
		this.uuid = uuid;
		this.setVitesse(vitesse);
		this.setPosition(position);
		this.setCouleur(couleur);
		this.setDistanceVision(distanceVision);
	}

	public Particule(Vector2D vitesse, Point2D position, double distanceVision, Color couleur) {
		this(UUID.randomUUID(), vitesse, position, distanceVision, couleur);
		this.setVitesse(vitesse);
		this.setPosition(position);
		this.setCouleur(couleur);
		this.setDistanceVision(distanceVision);
	}

	public Particule(Vector2D vitesse, Point2D position, double distanceVision) {
		this(vitesse, position, distanceVision, Color.RED);
	}

	public Particule(Vector2D vitesse, Point2D position) {
		this(vitesse, position, 1.0);
	}

	public Particule() {
		this(new Vector2D(), new Point2D());
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

	public boolean particuleVoitAutreParticule(Particule particule) {
		Objects.requireNonNull(particule);
		return position.minus(particule.position).getMagnitude() <= distanceVision;
	}

	@Override
	public Particule clone() {
		return new Particule(uuid, vitesse, position, distanceVision, new Color(couleur.getRed(), couleur.getGreen(), couleur.getBlue(),
				couleur.getAlpha()));
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
