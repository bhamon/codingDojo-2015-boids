package boids;

public class Particule implements Cloneable {

	private Vitesse vitesse;

	private Position position;

	@Override
	public Particule clone() {
		return new Particule(vitesse.clone(), position.clone());
	}

	public void setVitesse(Vitesse vitesse) {
		this.vitesse = vitesse;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Particule(Vitesse vitesse, Position position) {
		this.position = position;
		this.vitesse = vitesse;
	}

	public Particule() {
		position = new Position(0, 0);
		vitesse = new Vitesse(0, 0);
	}

	public Vitesse getVitesse() {
		return vitesse;
	}

	public Position getPosition() {
		return position;
	}

}
