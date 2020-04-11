package com.emse.airportSystem.planeManager.controller;

import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.service.IPlaneManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/planes")
public class PlaneController {

  @Autowired private IPlaneManager planeManager;

  @GetMapping
  public ResponseEntity<List<Plane>> findAllPlanes() {
    return ResponseEntity.ok(planeManager.getPlanes());
  }

  @GetMapping(path = "/{plane_id}")
  public ResponseEntity<Plane> findPlaneById(@PathVariable("plane_id") String id) {
    return ResponseEntity.ok(planeManager.findPlane(id));
  }

  @DeleteMapping(path = "/{plane_id}")
  public ResponseEntity deletePlaneById(@PathVariable("plane_id") String id) {
    return ResponseEntity.ok().build();
  }
}
