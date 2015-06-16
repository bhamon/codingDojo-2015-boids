package boids;

import java.util.List;

public interface Behavior {
	Vitesse compute(Particule ref, List<Particule> aoe);
}
