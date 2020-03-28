package com.emse.Airport_System.PlaneManager;

import java.util.ArrayList;

public final class PlaneManager {
	private final static PlaneManager instance = new PlaneManager();
	private PlaneManager() {}
	public static PlaneManager getInstance() {
		return instance;
	}
	
	private ArrayList<Plane> planes = new ArrayList<Plane>();
	void addPlane(String model) {
		instance.planes.add(new Plane(model));
	}
	void removePlane(Plane plane) {
		instance.planes.remove(plane);
	}

}
