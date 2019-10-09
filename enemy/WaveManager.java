package enemy;

import tile.TileGrid;

public class WaveManager {

	private float timeBetweenEnemies;
	private int waveNumber, enemiesPerWave;
	private Wave currentWave;
	private TileGrid grid;
	
	public WaveManager(float timeBetweenEnemies, int enemiesPerWave, TileGrid grid) {
		this.timeBetweenEnemies = timeBetweenEnemies;
		this.enemiesPerWave = enemiesPerWave;
		this.waveNumber = 0;
		this.grid = grid;
		this.currentWave = null;
 		newWave();
	}
	
	public void update() {
		if(!currentWave.isCompleted()) currentWave.update();
		else newWave();
	}
	
	private void newWave() {
		currentWave = new Wave(timeBetweenEnemies, enemiesPerWave, waveNumber, grid); 
		waveNumber++;
	}
	
	public Wave getCurrentWave() {
		return currentWave;
	}
	
	public int getWaveNumber() {
		return waveNumber;
	}
	
	public void reload() {
		currentWave.reload();
	}
}
