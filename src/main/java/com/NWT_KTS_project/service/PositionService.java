package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.Position;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PositionService {

    private Map<Integer, Position> positions;

    public PositionService() {
        positions = new HashMap<Integer,Position>();
    }

    public void updatePosition(int id, Position position) {
        positions.put(id, position);
    }

    public Position getPosition(int id) {
        return positions.get(id);
    }

    public HashMap<Integer, Position> getPositions() {
        return (HashMap<Integer, Position>) positions;
    }



}
