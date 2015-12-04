package boids;

import java.awt.Graphics;
import java.util.Objects;
import java.util.UUID;

import util.Point2D;

public abstract class Objet implements Cloneable {

	protected Point2D position = new Point2D();

	protected final UUID uuid;

	public Objet() {
		this.uuid = UUID.randomUUID();
	}

	public Objet(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getUuid() {
		return uuid;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		Objects.requireNonNull(position);
		this.position = position;
	}

	public Objet withPosition(Point2D position) {
		this.position = position.clone();
		return this;
	}

	@Override
	public Objet clone() throws CloneNotSupportedException {
		return ((Objet) super.clone()).withPosition(position);
	}

	public void paint(Graphics g) {
	}

	// public String
}
