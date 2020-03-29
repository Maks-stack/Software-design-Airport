package com.emse.Airport_System.TrackManager;

import com.emse.Airport_System.MockPlane.Plane;
import com.emse.Airport_System.ServiceManager.Service;
import jdk.nashorn.internal.ir.Assignment;

import java.util.List;

public class TrackManager {


    private List<Track> tracks;

    private static TrackManager firstInstance = null;

    Track track = new Track();

    private TrackManager() {}

    public static TrackManager getInstance() {

        if(firstInstance == null) {
            firstInstance = new TrackManager();
        }

        return firstInstance;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void addTrack(Track track) {
        getTracks().add(track);
        System.out.println("Added track: " + track.toString());
    }

    public void removeTrack(Plane plane, Track track) {
        getTracks().remove(track);
        System.out.println("Removed track: " + track.toString());
    }

    public Track getFreeTrack() {
        System.out.println("Searching free tracks... ");
        Track freeTrack = new Track();
        List<Track> tracks = getTracks();
        if(tracks != null) {
            for (Track tmp : tracks) {
                if (tmp.getState() instanceof Available && tmp.getState().isAvailable()) {
                    freeTrack = tmp;
                    System.out.println("Track found: " + freeTrack.toString());
                    break;
                }
            }
        }
        System.out.println("No free tracks found. ");
        return freeTrack;
    }

    public void AssignTrack(Plane plane) {
        Track freeTrack = getFreeTrack();
        TrackState state = new Assigned();
        freeTrack.setState(state);
        //plane.AssignTrack(freeTrack);
    }

}
