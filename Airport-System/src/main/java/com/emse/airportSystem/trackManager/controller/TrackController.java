package com.emse.airportSystem.trackManager.controller;

import com.emse.airportSystem.exceptions.ServiceNotAvailableException;
import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.states.InAir;
import com.emse.airportSystem.trackManager.model.LandingTrack;
import com.emse.airportSystem.trackManager.model.Track;
import com.emse.airportSystem.trackManager.model.TrackRequest;
import com.emse.airportSystem.trackManager.service.TrackManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Controller
public class TrackController {

    @Autowired
    private TrackManager TM;

    @Autowired
    private SimpMessagingTemplate template;

    public TrackController() {

    }

    @RequestMapping("/trackmanager")
    public String index(Model model) {
        List<Track> allTracks = TM.getTracks();
        List<Track> availableTracks = TM.getFreeTracks();
        List<TrackRequest> newTrackRequests = TM.getNewTrackRequests();
        model.addAttribute("allTracks", allTracks);
        model.addAttribute("availableTracks", availableTracks);
        model.addAttribute("newTrackRequests", newTrackRequests);
        return "trackManager";
    }

    @PatchMapping("/assigntrack/{id}")
    public ResponseEntity<?> assigntrack(@PathVariable int id) throws ServiceNotAvailableException {
        TM.assignTrack(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/mocktrackrequest")
    public ResponseEntity mockPlaneRequest(Model model){
        System.out.println("Mocking track request");
        Plane plane = new Plane("A777", new InAir(), "Test"+System.currentTimeMillis());
        TM.registerNewRequest(plane, new LandingTrack());

        return ResponseEntity.ok().build();
    }

    public void notifyServiceSubscribers() {
        this.template.convertAndSend("/tracks/updates", "Test");
    }

    public void notifyServiceSubscribers(Object obj) {
        this.template.convertAndSend("/tracks/updates", obj);
    }
}
