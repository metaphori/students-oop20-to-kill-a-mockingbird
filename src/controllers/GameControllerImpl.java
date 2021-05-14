package controllers;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


import model.enemy.VehicleImpl;
import model.map.BoxImpl;
import model.map.StripImpl;
import model.player.Player;
import model.player.PlayerImpl;

public class GameControllerImpl extends JPanel implements GameController, ActionListener {


	private static final long serialVersionUID = 1L;
	protected int NSTRIP = 11;
	protected int iriga = 11;
	protected int BOXFORSTRIP = 8;
	protected int TIMER_DELAY = 10;
	protected int SPAWN_CHARACTER_LINE = 4;

	private StripImpl striscia = new StripImpl();
	private BoxImpl[][] allStrips = new BoxImpl[NSTRIP][BOXFORSTRIP];
	private Timer timer;

	private ArrayList<BoxImpl> cars = new ArrayList<>();
	private ArrayList<BoxImpl> trains = new ArrayList<>();
	private VehicleImpl vehicleManager = new VehicleImpl();
	private Player player = new PlayerImpl("bird.png",400,600);

	//private GameControllerImpl gameContr = new GameControllerImpl();

	public GameControllerImpl() {

		this.timer = new Timer(this.TIMER_DELAY, this);

		this.SetInitialPosition();

		this.setDoubleBuffered(true);

		this.timer.start();

		this.repaint();
	}

	public void startVehicle(VehicleImpl vehicleManager,
			ArrayList<BoxImpl> vehicles, int delay) {
		vehicleManager.moveVehicle(vehicles);
		vehicleManager.restartVehicle(vehicles, delay);
	}

	public void scroolScren(BoxImpl[][] allStrips) {
		for (int y = 0; y < this.NSTRIP; y++) {

			for (int x = 0; x < this.BOXFORSTRIP; x++) {
				allStrips[y][x].setYDir(1);
			}
		}
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g); // Erases the previous screen.

		// Draws strips.
		for (int i = 0; i < NSTRIP; i++) {
			for (int x = 0; x < BOXFORSTRIP; x++) {
				allStrips[i][x].paint(g, this);
			}
		}

		for (BoxImpl s : cars) // da fare un metodo ma problemi con paint
			s.paint(g, this);

		for (BoxImpl s : trains)
			s.paint(g, this);
		
		//spawno il personaggio principale
		this.player.paint(g, this);

	}
	

	@Override
	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < this.NSTRIP; i++) {
			for (int x = 0; x < this.BOXFORSTRIP; x++) {
				allStrips[i][x].move();
			}
		}

		this.scroolScren(allStrips);

		this.repaint();

		this.startVehicle(vehicleManager, cars, 1500);
		this.startVehicle(vehicleManager, trains, 5000);

		this.generateMap();
	}

	 /**
     * Set the initial landscape without vehicles
     */
	public void SetInitialPosition() {
		
		for (int i = 0; i < NSTRIP; i++) {
			
			/**
		     * Set the line where the character will be spawn and the next one without any obstacles 
		     */
			if(i==SPAWN_CHARACTER_LINE|| i == SPAWN_CHARACTER_LINE + 1) {
				allStrips[i] = striscia.getSpecificStrip("Grass.png", i);
			}
			
			else {
				allStrips[i] = striscia.getSpecificStrip("Grass.png", "Tree.png", i);
				vehicleManager.checkOnRoad(allStrips, cars, trains, i);
			}
			
		}
	}

	public void generateMap() {

		for (int i = 0; i < NSTRIP; i++) {
			if (allStrips[i][0].getYLoc() > 800) {
				allStrips[i] = striscia.setRndStrip(iriga);
				vehicleManager.checkOnRoad(allStrips, cars, trains, i);
			}
		}
	}
	/**
	 * 
	 * @return player
	 */
	public Player getPlayer() {
		return this.player;
	}

}
