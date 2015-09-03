package boids;

import util.Vector2D;

public interface MondeCommande {

	public void modifySpeed(Particule p1, Vector2D vector2d);

	public void add(Objet objet) throws OutOfBoundsException, CloneNotSupportedException;

}
