package com.emse.airportSystem.trackManager.states;

import com.emse.airportSystem.trackManager.model.Track;

public interface TrackState {

  void proceedToNextStep(Track track);

  String getState();
}
