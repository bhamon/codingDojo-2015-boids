package boids;

import java.awt.Color;
import java.util.Objects;
import java.util.UUID;

public class Particule implements Cloneable {

	private UUID uuid;

	private Vitesse vitesse;

	private Position position;

	private double distanceVision;

	private Color couleur;

	private Particule(UUID uuid, Vitesse vitesse, Position position,
			double distanceVision, Color couleur) {
		this.uuid = uuid;
		this.setVitesse(vitesse);
		this.setPosition(position);
		this.setCouleur(couleur);
		this.setDistanceVision(distanceVision);
	}

	public Particule(Vitesse vitesse, Position position, double distanceVision,
			Color couleur) {
		this(UUID.randomUUID(), vitesse, position, distanceVision, couleur);
		this.setVitesse(vitesse);
		this.setPosition(position);
		this.setCouleur(couleur);
		this.setDistanceVision(distanceVision);
	}

	public Particule(Vitesse vitesse, Position position, double distanceVision) {
		this(vitesse, position, distanceVision, Color.RED);
	}

	public Particule(Vitesse vitesse, Position position) {
		this(vitesse, position, 1);
	}

	public Particule() {
		this(new Vitesse(0, 0), new Position(0, 0));
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

	@Override
	public Particule clone() {
		return new Particule(uuid, vitesse, position, distanceVision,
				new Color(couleur.getRed(), couleur.getGreen(),
						couleur.getBlue(), couleur.getAlpha()));
	}

	public void setVitesse(Vitesse vitesse) {
		Objects.requireNonNull(vitesse);
		this.vitesse = vitesse;
	}

	public void setPosition(Position position) {
		Objects.requireNonNull(position);
		this.position = position;
	}

	public UUID getUuid() {
		return uuid;
	}

	public Vitesse getVitesse() {
		return vitesse;
	}

	public Position getPosition() {
		return position;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Particule)) {
			return false;
		}

		Particule p = (Particule) obj;
		return p.uuid == uuid;
	}

	public boolean particuleVoitAutreParticule(Particule particule) {
		return getDistance(particule) <= distanceVision;
	}

	public double getDistance(Particule particule) {
		Objects.requireNonNull(particule);

		double p1x = this.getPosition().getX();
		double p1y = this.getPosition().getY();
		double p2x = particule.getPosition().getX();
		double p2y = particule.getPosition().getY();

		double dx = p1x - p2x;
		double dy = p1y - p2y;

		return Math.sqrt(dx * dx + dy * dy);
	}

	public Vitesse getVecteurDistance(Particule particule) {
		Objects.requireNonNull(particule);

		return new Vitesse(particule.position.getX() - position.getX(),
				particule.position.getY() - position.getY());
	}

}
