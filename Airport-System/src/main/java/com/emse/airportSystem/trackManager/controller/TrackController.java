package com.emse.airportSystem.trackManager.controller;

import com.emse.airportSystem.exceptions.ServiceNotAvailableException;
import com.emse.airportSystem.trackManager.model.Track;
import com.emse.airportSystem.trackManager.service.TrackManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
        Track availableTrack = TM.getFreeTrack();
        model.addAttribute("allTracks", allTracks);
        model.addAttribute("availableTracks", availableTracks);
        model.addAttribute("availableTrack", availableTrack);
        return "trackManager";
    }

    @RequestMapping("/assigntrack")
    @ResponseBody
    public ResponseEntity<?> assigntrack() throws ServiceNotAvailableException {
        TM.assignTrack();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void notifyServiceSubscribers() {
        this.template.convertAndSend("/tracks/updates", "Test");
    }

    public void notifyServiceSubscribers(Object obj) {
        this.template.convertAndSend("/tracks/updates", obj);

    }
}
