package com.emse.Airport_System.TrackManager;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackManager {

    private List<Track> tracks = new ArrayList<Track>();

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
        else {
            System.out.println("No free tracks found. ");
        }
        return freeTrack;
    }

    public Track AssignTrack() {
        Track freeTrack = getFreeTrack();
        TrackState state = new Assigned();
        freeTrack.setState(state);
        System.out.println("Assigned track: " + freeTrack.toString());
        return freeTrack;
    }
}
