package enemy;

import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

import tile.TileGrid;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private CopyOnWriteArrayList<Enemy> enemyList;
	private int enemiesPerWave, enemiesSpawned, waveNumber;
	private boolean waveCompleted;
	private TileGrid grid;
	
	public Wave(float spawnTime, int enemiesPerWave, int waveNumber, TileGrid grid) {
		this.spawnTime = spawnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.enemiesSpawned = 0;
		this.waveNumber = waveNumber;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted = false;
		this.grid = grid;
		spawn();
	}

	public void update() {
		boolean allEnemiesDead = true;
		if (enemiesSpawned < enemiesPerWave) {
			timeSinceLastSpawn += Delta();
			if (timeSinceLastSpawn > spawnTime) {
				spawn();
				timeSinceLastSpawn = 0;
			}
		}
		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				allEnemiesDead = false;
				e.update();
				e.draw();
			} else enemyList.remove(e);
		}
		if(allEnemiesDead && enemiesSpawned >= enemiesPerWave) waveCompleted = true;
	}

	/*
	 * New Wave System:
	 * 1: UFO
	 * 2-3: Alien
	 * 4: Plane
	 * 5-9: Ent
	 * 10-11: Human
	 * 12-14: Plane
	 * 15: Zombie
	 * 16-19: Tank
	 * 20-22: Zombie
	 * 23+: Super Tank
	 */
	
	private void spawn() {
		if(waveNumber >= 23) enemyList.add(new EnemySuperTank(grid.getTile(1,  1), grid));
		else if(waveNumber >= 20) enemyList.add(new EnemyZombie(grid.getTile(1,  1), grid));
		else if(waveNumber >= 16) enemyList.add(new EnemyTank(grid.getTile(1,  1), grid));
		else if(waveNumber >= 15) enemyList.add(new EnemyZombie(grid.getTile(1,  1), grid));
		else if(waveNumber >= 12) enemyList.add(new EnemyPlane(grid.getTile(1,  1), grid));
		else if(waveNumber >= 10) enemyList.add(new EnemyHuman(grid.getTile(1,  1), grid));
		else if(waveNumber >= 5) enemyList.add(new EnemyEnt(grid.getTile(1,  1), grid));
		else if(waveNumber >= 4) enemyList.add(new EnemyPlane(grid.getTile(1,  1), grid));
		else if(waveNumber >= 1) enemyList.add(new EnemyAlien(grid.getTile(1,  1), grid));
		else if(waveNumber >= 0) enemyList.add(new EnemyUFO(grid.getTile(1,  1), grid));
		enemiesSpawned++;
	}

	public boolean isCompleted() {
		return waveCompleted;
	}
	
	public CopyOnWriteArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
	
	public void reload() {
		for(Enemy e: enemyList) if (e.isAlive()) {
			e.die();
			enemiesSpawned--;
		}
	}
}
