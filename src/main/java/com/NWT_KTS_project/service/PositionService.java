package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.Position;

import java.util.HashMap;

public interface PositionService {

    public void updatePosition(int id, Position position);

    public Position getPosition(int id);

    public HashMap<Integer, Position> getPositions();
}
