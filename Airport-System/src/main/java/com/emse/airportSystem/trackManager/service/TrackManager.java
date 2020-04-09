package com.emse.airportSystem.trackManager.service;

import com.emse.airportSystem.observer.Observable;
import com.emse.airportSystem.observer.Observer;
import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.serviceManager.model.ServiceRequest;
import com.emse.airportSystem.trackManager.model.TrackRequest;
import com.emse.airportSystem.trackManager.states.Assigned;
import com.emse.airportSystem.trackManager.states.Available;
import com.emse.airportSystem.trackManager.states.TrackState;
import com.emse.airportSystem.trackManager.model.Track;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackManager implements Observable {

    private List<Track> tracks = new ArrayList<Track>();
    List<Observer> observers = new ArrayList<Observer>();
    List<TrackRequest> newTrackRequests = new ArrayList<TrackRequest>();

    public List<Track> getTracks() {
        return tracks;
    }

    public void addTrack(Track track) {
        tracks.add(track);
        System.out.println("Added track: " + track.toString());
    }

    public void removeTrack(Track track) {
        tracks.remove(track);
        System.out.println("Removed track: " + track.toString());
    }

    public List<Track> getFreeTracks() {
        System.out.println("Searching for all available tracks... ");
        List<Track> availableTracks = new ArrayList<Track>();;
        if(tracks != null) {
            for (Track tmp : tracks) {
                if (tmp.getState() instanceof Available && tmp.getState().isAvailable()) {
                    availableTracks.add(tmp);
                }
            }
            if(availableTracks.equals(new ArrayList<Track>())) {
                System.out.println("No available tracks found. ");
            }
        }
        else {
            System.out.println("No tracks found. ");
        }
        return availableTracks;
    }

    public Track getFreeTrack() {
        System.out.println("Searching for available track... ");
        return getFreeTracks().get(0);
    }

    public Track assignTrack() {
        Track freeTrack = getFreeTrack();
        TrackState state = new Assigned();
        freeTrack.setState(state);
        System.out.println("Assigned track: " + freeTrack.toString());
        return freeTrack;
    }



    public void registerNewRequest(Plane plane){
        newTrackRequests.add(new TrackRequest(plane));
    }

    public List<TrackRequest> getNewTrackRequests(){
        return newTrackRequests;
    }

    @Override
    public void register(Observer obj) {
        observers.add(obj);
    }

    @Override
    public void unregister(Observer obj) {
        observers.remove(obj);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(obj -> obj.update());
    }

    @Override
    public void notifyObservers(Object obj) {
        observers.forEach(observer -> observer.update(obj));
    }
}
