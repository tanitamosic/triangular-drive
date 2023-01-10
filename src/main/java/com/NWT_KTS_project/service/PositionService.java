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


    public double getDistance(Position p1, Position p2) {
        double lat1 = p1.getLatitude();
        double lat2 = p2.getLatitude();
        double lon1 = p1.getLongitude();
        double lon2 = p2.getLongitude();

        double R = 6371e3;
        double phi1 = lat1 * Math.PI/180;
        double phi2 = lat2 * Math.PI/180;
        double deltaPhi = (lat2-lat1) * Math.PI/180;
        double deltaLambda = (lon2-lon1) * Math.PI/180;

        double a = Math.sin(deltaPhi/2) * Math.sin(deltaPhi/2) +
                Math.cos(phi1) * Math.cos(phi2) *
                        Math.sin(deltaLambda/2) * Math.sin(deltaLambda/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c;//meters
    }

    public static final double MAX_DISTANCE = 2000;


}
