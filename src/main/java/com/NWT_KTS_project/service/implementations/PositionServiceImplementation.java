package com.NWT_KTS_project.service.implementations;

import com.NWT_KTS_project.model.Position;
import com.NWT_KTS_project.service.PositionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PositionServiceImplementation implements PositionService {

    private Map<Integer, Position> positions;

    public PositionServiceImplementation() {
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
