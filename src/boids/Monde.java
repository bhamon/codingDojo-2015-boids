package boids;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Monde {

	private Map<UUID, Particule> listParticule;
	private double longueur;
	private double largeur;
	private Position spawn;

	public Monde(double longueur, double largeur, Position spawn) {
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
		this(longueur, largeur, new Position(longueur / 2, largeur / 2));
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

		Position position = clonedParticule.getPosition();

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
			int posX = (int) particule.getPosition().getX();
			int posY = (int) particule.getPosition().getY();
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
			int posX = (int) particule.getPosition().getX();
			int posY = (int) particule.getPosition().getY();
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
			Vitesse vitesseRepulsion = new Vitesse(0, 0);
			int nb = 0;
			for (Particule particule2 : listParticule.values()) {
				if (!particule.equals(particule2) && particule.particuleVoitAutreParticule(particule2)) {
					Vitesse vtsTemp = particule.getVecteurDistance(particule2);
					double d = particule.getDistance(particule2);
					double r = particule.getDistanceVision() - d;
					// Vitesse v = new Vitesse(vtsTemp.getX()
					// / (particule.getDistanceVision() - d),
					// vtsTemp.getY()
					// / (particule.getDistanceVision() - d));
					Vitesse v = new Vitesse(vtsTemp.getX() / d * r, vtsTemp.getY() / d * r);
					vitesseRepulsion = vitesseRepulsion.addSpeed(v);
					nb++;
				}
			}

			if (nb > 0) {
				vitesseRepulsion = new Vitesse(vitesseRepulsion.getX() / nb, vitesseRepulsion.getY() / nb);
			}

			particule.setVitesse(new Vitesse(vitesseRepulsion.getX() + particule.getVitesse().getX(), vitesseRepulsion.getY()
					+ particule.getVitesse().getY()));
		}

		for (Particule particule : listParticule.values()) {

			Position pi = particule.getPosition();
			Vitesse v = particule.getVitesse();
			Position pF = new Position(pi.getX() + v.getX(), pi.getY() + v.getY());
			if (isInBounds(pF)) {
				particule.setPosition(pF);
			} else {
				particule.setPosition(spawn);
			}
		}
	}

	public boolean checkParticuleAtPosition(Position position) {

		return listParticule.values().stream().anyMatch(particule -> particule.getPosition().equals(position));

	}

	public Particule addRandomParticule() {
		Vitesse vitesse = new Vitesse(5 - Math.random() * 10, 5 - Math.random() * 10);
		Position position = new Position(Math.random() * longueur, Math.random() * largeur);
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

	public boolean isInBounds(Position position) {

		double px = position.getX();
		double py = position.getY();

		if (px < 0 || px >= longueur || py < 0 || py >= largeur) {
			return false;
		}

		return true;

	}

}
