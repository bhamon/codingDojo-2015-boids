package boids;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import util.Point2D;
import util.Vector2D;
import annotations.ApiObject;
import annotations.ApiResource;

@ApiObject(name = "monde")
public class Monde implements MondeCommande {

	private final Map<UUID, Objet> listobjet = new HashMap<UUID, Objet>();
	private double longueur = 600;
	private double largeur = 600;
	private Point2D spawn = new Point2D(0, 0);
	private Behavior behavior = defaultBehavior;

	public Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}

	public static final Behavior defaultBehavior = new Behavior() {

		@Override
		public void compute(Particule particule, List<Particule> aoe, MondeCommande mc) {

			Vector2D vitesseRepulsion = new Vector2D();
			int nb = 0;

			for (Particule particule2 : aoe) {

				Vector2D vDistance = particule.getPosition().minus(particule2.getPosition());
				double distance = vDistance.getMagnitude();
				if (distance == 0.0) {
					continue;
				}
				// double rest = particule.getDistanceVision() - distance;
				// vitesseRepulsion =
				// vitesseRepulsion.plus(vDistance.divideBy(distance).multiplyBy(rest));
				vitesseRepulsion = vitesseRepulsion.plus(vDistance.getNormalized());
				nb++;

			}

			if (nb > 0) {
				vitesseRepulsion = vitesseRepulsion.divideBy(nb);
			}

			double vitesse = particule.getVitesse().getMagnitude();
			// particule.setVitesse(particule.getVitesse().plus(vitesseRepulsion.multiplyBy(0.5)));
			// TODO Auto-generated method stub
			mc.modifySpeed(particule, particule.getVitesse().plus(vitesseRepulsion.multiplyBy(2.0)).getNormalized().multiplyBy(vitesse));

		}
	};

//	private Monde(double longueur, double largeur, Point2D spawn, Behavior behavior) {
//		if (longueur <= 0 || largeur <= 0) {
//			throw new IllegalArgumentException("largeur ou longueur negative");
//		}
//
//		this.listobjet = new HashMap<UUID, Objet>();
//		this.largeur = largeur;
//		this.longueur = longueur;
//		this.spawn = spawn;
//		this.behavior = behavior;
//	}
//
//	private Monde(double longueur, double largeur) {
//		this(longueur, largeur, new Point2D(longueur / 2.0, largeur / 2.0));
//	}
//
//	private Monde(double longueur, double largeur, Point2D spawn) {
//		this(longueur, largeur, spawn, defaultBehavior);
//	}
//
//	private Monde(double longueur, double largeur, Behavior behavior) {
//		this(longueur, largeur, new Point2D(longueur / 2.0, largeur / 2.0), behavior);
//	}

	public Monde() {
		
	}

	public double getLongueur() {
		return longueur;
	}

	public double getLargeur() {
		return largeur;
	}

	public Objet get(UUID uuid) throws CloneNotSupportedException {
		Objet objet = listobjet.get(uuid);
		if (objet == null) {
			return null;
		}

		return objet.clone();
	}

	@Override
	public void add(Objet objet) throws OutOfBoundsException, CloneNotSupportedException {
		if (objet == null) {
			throw new IllegalArgumentException("Particule ne peut pas etre null");
		}

		Objet clonedObjet = (Objet) objet.clone();
		Point2D position = clonedObjet.getPosition();

		if (!isInBounds(position)) {
			throw new OutOfBoundsException("La particule est en dehors du monde");
		}

		listobjet.put(clonedObjet.getUuid(), clonedObjet);
	}

	public boolean contains(Particule particule) {
		if (particule == null) {
			throw new IllegalArgumentException("Particule ne peut pas etre null");
		}
		return listobjet.containsKey(particule.getUuid());

	}

	public void print() {
		int iLongueur = (int) longueur;
		int ilargeur = (int) largeur;
		int[][] grilleMonde = new int[iLongueur][ilargeur];

		for (Objet objet : listobjet.values()) {
			int posX = (int) objet.getPosition().x;
			int posY = (int) objet.getPosition().y;
			grilleMonde[posX][posY] = 1;
		}

		for (int y = ilargeur - 1; y >= 0; y--) {
			for (int x = 0; x < iLongueur; x++) {
				System.out.print(grilleMonde[x][y]);
			}

			System.out.println();
		}

		System.out.println();
	}

	public void paint(Graphics g) {
		for (Objet objet : listobjet.values()) {
			objet.paint(g);
		}
	}

	public void animer() throws Exception {
		// @formatter:off
		Stream<Particule> listParticules = new ArrayList<Objet>(listobjet.values())
				.stream()
				.filter((objet) -> objet instanceof Particule)
				.map((objet) -> (Particule) objet);
		Stream<Particule> listParticules3 = new ArrayList<Objet>(listobjet.values())
				.stream()
				.filter((objet) -> objet instanceof Particule)
				.map((objet) -> (Particule) objet);
		// @formatter:on

		listParticules.forEach((particule) -> {
			if (!particule.isShadow()) {
				List<Particule> areaOfEffect = new ArrayList<Particule>();
				Stream<Particule> listParticules2 = new ArrayList<Objet>(listobjet.values()).stream().filter((objet) -> objet instanceof Particule)
						.map((objet) -> (Particule) objet);
				listParticules2.forEach((particule2) -> {
					if (!particule2.isShadow() && !particule.equals(particule2) && particule.particuleVoitAutreParticule(particule2)) {
						areaOfEffect.add(particule2);
					}
				});

				try {
					this.behavior.compute(particule, areaOfEffect, this);
				} catch (Exception e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	})	;

		listParticules3.forEach((particule) -> {
			Point2D posFinale = particule.getVitesse().translate(particule.getPosition());
			if (isInBounds(posFinale)) {
				particule.setPosition(posFinale);
			} else {
				// hors du tableau
				if (posFinale.x > longueur || posFinale.x < 0) {
					Vector2D vector2d = particule.getVitesse();
					particule.setVitesse(new Vector2D(-vector2d.x, vector2d.y));
				}
				if (posFinale.y > largeur || posFinale.y < 0) {
					Vector2D vector2d = particule.getVitesse();
					particule.setVitesse(new Vector2D(vector2d.x, -vector2d.y));
				}

				particule.setPosition(particule.getVitesse().translate(particule.getPosition()));
			}
		});
	}

	public boolean checkParticuleAtPosition(Point2D position) {
		return listobjet.values().stream().anyMatch(particule -> particule.getPosition().equals(position));
	}

	public Particule addRandomParticule() throws CloneNotSupportedException {
		Vector2D vitesse = new Vector2D(10 - Math.random() * 20, 10 - Math.random() * 20);
		Point2D position = new Point2D(Math.random() * longueur, Math.random() * largeur);
		double distanceVision = Math.random() * 100.0;
		// Color couleur = new Color((float) Math.random(), (float)
		// Math.random(),
		// (float) Math.random(), 0.4f);
		Color couleur = new Color(0.0f, 0.0f, (float) distanceVision / 100.0f);
		Particule particule = new Particule().withVitesse(vitesse).withPosition(position).withDistanceVision(distanceVision).withCouleur(couleur);
		try {
			add(particule);
		} catch (OutOfBoundsException e) {
			e.printStackTrace();
		}

		return particule.clone();
	}

	public boolean isInBounds(Point2D position) {
		double px = position.x;
		double py = position.y;

		if (px < 0 || px >= longueur || py < 0 || py >= largeur) {
			return false;
		}

		return true;
	}

	@Override
	public void modifySpeed(Particule p1, Vector2D vitesseParticule) {
		Objects.requireNonNull(p1);
		if (listobjet.containsKey(p1.getUuid())) {
			Objet objet = listobjet.get(p1.getUuid());
			if (objet instanceof Particule) {
				((Particule) objet).setVitesse(vitesseParticule);
			}
		}

	}

	public int getParticulesNb() {
		return listobjet.size();
	}

	@ApiResource(name = "particules")
	public List<Objet> getListobjet() {
		List<Objet> result = new ArrayList<>(listobjet.values());
		return result;
	}

	public Monde withLargeur(int i) {
		this.largeur = i;
		return this;
	}

	public Monde withLongueur(int i) {
		this.longueur = i;
		return this;
	}

	public Monde withSpawn(Point2D spawn) {
		this.spawn = spawn;
		return this;
	}
	
}
