package boids;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import util.Point2D;
import util.Vector2D;

public class Monde {

	private Map<UUID, Particule> listParticule;
	private double longueur;
	private double largeur;
	private Point2D spawn;

	public Monde(double longueur, double largeur, Point2D spawn) {
		if (longueur <= 0 || largeur <= 0) {
			throw new IllegalArgumentException("largeur ou longueur négative");
		}

		this.listParticule = new HashMap<UUID, Particule>();
		this.largeur = largeur;
		this.longueur = longueur;
		this.spawn = spawn;
	}

	public Monde() {
		this(10, 10);
	}

	public Monde(double longueur, double largeur) {
		this(longueur, largeur, new Point2D(longueur / 2.0, largeur / 2.0));
	}

	public double getLongueur() {
		return longueur;
	}

	public double getLargeur() {
		return largeur;
	}

	public Particule get(UUID uuid) {
		return listParticule.get(uuid);
	}

	public void add(Particule particule) throws OutOfBoundsException {
		if (particule == null) {
			throw new IllegalArgumentException("Particule ne peut pas être null");
		}

		Particule clonedParticule = (Particule) particule.clone();
		Point2D position = clonedParticule.getPosition();

		if (!isInBounds(position)) {
			throw new OutOfBoundsException("La particule est en dehors du monde");
		}

		listParticule.put(clonedParticule.getUuid(), clonedParticule);
	}

	public boolean contains(Particule particule) {
		if (particule == null) {
			throw new IllegalArgumentException("Particule ne peut pas être null");
		}
		return listParticule.containsKey(particule.getUuid());

	}

	public void print() {
		int iLongueur = (int) longueur;
		int ilargeur = (int) largeur;
		int[][] grilleMonde = new int[iLongueur][ilargeur];

		for (Particule particule : listParticule.values()) {
			int posX = (int) particule.getPosition().x;
			int posY = (int) particule.getPosition().y;
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
		for (Particule particule : listParticule.values()) {
			int posX = (int) particule.getPosition().x;
			int posY = (int) particule.getPosition().y;
			int r2 = (int) particule.getDistanceVision();
			int d2 = (int) (particule.getDistanceVision() / 2.0);
			g.setColor(particule.getCouleur());
			// g.fillRect(posX, posY, 1, 1);
			g.fillOval(posX - d2, posY - d2, r2, r2);
		}
	}

	public void animer() {
		// for (Particule particule : listParticule.values()) {
		// Vitesse vitesseRepulsion = new Vitesse(0, 0);
		// for (Particule particule2 : listParticule.values()) {
		// if (!particule.equals(particule2)
		// && particule.particuleVoitAutreParticule(particule2)) {
		// Vitesse distance = particule.getVecteurDistance(particule2);
		// vitesseRepulsion = vitesseRepulsion.addSpeed(distance);
		// }
		// }
		//
		// particule.setVitesse(particule.getVitesse().addSpeed(
		// vitesseRepulsion));
		// }

		for (Particule particule : listParticule.values()) {
			Vector2D vitesseRepulsion = new Vector2D();
			int nb = 0;
			for (Particule particule2 : listParticule.values()) {
				if (!particule.equals(particule2) && particule.particuleVoitAutreParticule(particule2)) {
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
			}

			if (nb > 0) {
				vitesseRepulsion = vitesseRepulsion.divideBy(nb);
			}

			double vitesse = particule.getVitesse().getMagnitude();
			// particule.setVitesse(particule.getVitesse().plus(vitesseRepulsion.multiplyBy(0.5)));
			particule.setVitesse(particule.getVitesse().plus(vitesseRepulsion.multiplyBy(2.0)).getNormalized().multiplyBy(vitesse));
		}

		for (Particule particule : listParticule.values()) {
			Point2D posFinale = particule.getVitesse().translate(particule.getPosition());
			if (isInBounds(posFinale)) {
				particule.setPosition(posFinale);
			} else {
				particule.setPosition(spawn);
			}
		}
	}

	public boolean checkParticuleAtPosition(Point2D position) {
		return listParticule.values().stream().anyMatch(particule -> particule.getPosition().equals(position));
	}

	public Particule addRandomParticule() {
		Vector2D vitesse = new Vector2D(10 - Math.random() * 20, 10 - Math.random() * 20);
		Point2D position = new Point2D(Math.random() * longueur, Math.random() * largeur);
		double distanceVision = Math.random() * 100.0;
		// Color couleur = new Color((float) Math.random(), (float)
		// Math.random(),
		// (float) Math.random(), 0.4f);
		Color couleur = new Color(0.0f, 0.0f, (float) distanceVision / 100.0f);
		Particule particule = new Particule(vitesse, position, distanceVision, couleur);
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
}
