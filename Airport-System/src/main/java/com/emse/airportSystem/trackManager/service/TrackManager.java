package com.emse.airportSystem.trackManager.service;

import com.emse.airportSystem.observer.Observable;
import com.emse.airportSystem.observer.Observer;
import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.service.IPlaneManager;
import com.emse.airportSystem.trackManager.model.Track;
import com.emse.airportSystem.trackManager.model.TrackRequest;
import com.emse.airportSystem.trackManager.states.Available;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TrackManager implements Observable {

    @Autowired private IPlaneManager planeManager;

    private List<Track> tracks = new ArrayList<>();
    List<Observer> observers = new ArrayList<>();
    List<TrackRequest> newTrackRequests = new ArrayList<>();

    {
        for (int i = 0; i < 2; i++) {
            tracks.add(new Track(i, new Available()));
        }
        for (int i = 2; i < 4; i++) {
            tracks.add(new Track(i, new Available()));
        }
    }

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
        List<Track> availableTracks = new ArrayList<>();
        if(tracks != null) {
            for (Track tmp : tracks) {
                if (tmp.getState().getState().equals("available")) {
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

    public void assignTrack(int id, String planeId) {
        Track freeTrack = getFreeTracks().stream().parallel()
            .filter(track -> track.getTrackID() == id)
            .findAny()
            .orElseThrow(RuntimeException::new);

        freeTrack.nextState();
        freeTrack.setAssignedPlane(planeManager.findPlane(planeId));
        deleteTrackRequest(planeId);
        notifyObservers(freeTrack);
        newTrackRequests.stream()
            .map(TrackRequest::getAvailableTracks)
            .forEach(list -> list.remove(freeTrack));
        notifyRequestObservers(newTrackRequests);
        System.out.println("Assigned track: " + freeTrack.toString());
    }

    public void unassignTrack(int id) {
        Track assignedTrack = tracks.stream()
            .filter(track -> track.getTrackID() == id)
            .findAny()
            .orElseThrow(RuntimeException::new);
        assignedTrack.nextState();
        assignedTrack.setAssignedPlane(null);
        notifyObservers(assignedTrack);

        newTrackRequests.stream()
            .map(TrackRequest::getAvailableTracks)
            .peek(list -> list.add(assignedTrack))
            .forEach(list -> list.sort(Comparator.comparingInt(Track::getTrackID)));

        notifyRequestObservers(newTrackRequests);
        System.out.println("Assigned track: " + assignedTrack.toString());
    }

    private void deleteTrackRequest(String planeId) {
        TrackRequest request = newTrackRequests.stream().parallel()
            .filter(trackRequest -> trackRequest.getPlane().getPlaneId().equals(planeId))
            .peek(trackRequest -> trackRequest.getPlane().nextState())
            .findAny()
            .orElseThrow(RuntimeException::new);

        newTrackRequests.remove(request);
        notifyRequestObservers(newTrackRequests);
    }

    public void registerNewRequest(Plane plane){
        newTrackRequests.add(new TrackRequest(plane, getFreeTracks()));
        //plane.nextState();
        notifyRequestObservers(newTrackRequests);
    }

    public List<TrackRequest> getNewTrackRequests(){
        return newTrackRequests;
    }

    @Override
    public void registerObserver(Observer obj) {
        observers.add(obj);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    @Override
    public void notifyObservers(Object obj) {
        observers.forEach(observer -> observer.update(obj));
    }

    @Override
    public void notifyRequestObservers(Object obj) {
        observers.forEach(observer -> observer.updateRequest(obj));
    }
}
