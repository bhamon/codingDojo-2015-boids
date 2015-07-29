package boids;

import java.util.List;

public class BehaviorExplode implements Behavior {

	@Override
	public void compute(Particule ref, List<Particule> aoe, MondeCommande mc) throws Exception {
		Monde.defaultBehavior.compute(ref, aoe, mc);

		for (Particule p : aoe) {
			mc.add(new Particule());
		}
	}
}
