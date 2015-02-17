package boids;

import java.util.ArrayList;
import java.util.List;

public class Monde {

	private List<Particule> listParticule = new ArrayList<Particule>();

	private double longueur;
	private double largeur;

	public Monde(double longueur, double largeur) {
		if (longueur <= 0 || largeur <= 0) {
			throw new IllegalArgumentException("largeur ou longueur négative");
		}
		this.largeur = largeur;
		this.longueur = longueur;
	}

	public Monde() {
		this(10, 10);
	}

	public void add(Particule particule) throws OutOfBoundsException {
		if (particule == null) {
			throw new IllegalArgumentException(
					"Particule ne peut pas être null");
		}
		
		Position position = particule.getPosition();
		double px = position.getX();
		double py = position.getY();
		
		if (px < 0 || px >= longueur || py < 0 || py >= largeur) {
			throw new OutOfBoundsException("La particule est en dehors du monde");
		}
		
		listParticule.add(particule);
	}

	public boolean contains(Particule particule) {
		if (particule == null) {
			throw new IllegalArgumentException(
					"Particule ne peut pas être null");
		}
		return listParticule.contains(particule);

	}

	public void print() {
		int iLongueur = (int) longueur;
		int ilargeur = (int) largeur;
		int[][] grilleMonde = new int[iLongueur][ilargeur];
		
		for (Particule particule : listParticule) {
			int posX = (int) particule.getPosition().getX();
			int posY = (int) particule.getPosition().getY();
			grilleMonde[posX][posY] = 1;
		}
	
		
		for (int y =ilargeur-1 ; y >= 0; y--) {
			for (int x = 0; x < iLongueur; x++) {
				System.out.print(grilleMonde[x][y]);
			}
			System.out.println();
		}

		System.out.println();
	}

	public void animer() {
		for (Particule particule : listParticule) {
			Position pi = particule.getPosition();
			Vitesse v = particule.getVitesse();
			particule.setPosition(new Position(
					pi.getX() + v.getX(),
					pi.getY() + v.getY())
			);
		}
	}
}
