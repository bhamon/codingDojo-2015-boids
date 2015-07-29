package boids;

import java.util.List;

public interface Behavior {
	void compute(Particule ref, List<Particule> aoe, MondeCommande mc) throws Exception;
}
