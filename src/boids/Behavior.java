package boids;

import java.util.List;

import util.Vector2D;

public interface Behavior {
	Vector2D computeSpeed(Particule ref, List<Particule> aoe);
}
