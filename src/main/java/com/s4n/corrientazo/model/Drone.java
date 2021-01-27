package com.s4n.corrientazo.model;

import com.s4n.corrientazo.enums.Coordinates;
import com.s4n.corrientazo.util.Constants;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Point2D;


@Builder
@Getter
@Setter
@Data
public class Drone implements IDrone {

    private int id;

    @Builder.Default
    private int posX = 0;

    @Builder.Default
    private int posY = 0;

    @Builder.Default
    private Coordinates cardinalDirection = Coordinates.N;

    @Builder.Default
    private Point2D position = new Point2D.Double(0, 0);

    public void move() {
        switch (cardinalDirection) {
            case N:
                posY += 1;
                break;
            case W:
                posX -= 1;
                break;
            case E:
                posX += 1;
                break;
            case S:
                posY -= 1;
                break;
        }
        position.setLocation(posX, posY);
    }

    public void changeDirection(char direction) throws IllegalArgumentException {
        if (direction == 'I') {
            switch (cardinalDirection) {
                case N:
                    cardinalDirection = Coordinates.W;
                    break;
                case W:
                    cardinalDirection = Coordinates.S;
                    break;
                case S:
                    cardinalDirection = Coordinates.E;
                    break;
                case E:
                    cardinalDirection = Coordinates.N;
                    break;
            }
        } else if (direction == 'D') {
            switch (cardinalDirection) {
                case N:
                    cardinalDirection = Coordinates.E;
                    break;
                case E:
                    cardinalDirection = Coordinates.S;
                    break;
                case S:
                    cardinalDirection = Coordinates.W;
                    break;
                case W:
                    cardinalDirection = Coordinates.N;
                    break;
            }
        } else {
            throw new IllegalArgumentException("the direction " + direction + " doesn't exist, select one of these (I - left,D- right)");
        }

    }

    public String report() {
        return "(" + this.posX + ", " + this.posY + ") " +  cardinalDirection.getName() + " direction";
    }

    public boolean isValidPosition(){
        return position.getX() < Constants.LIMIT && position.getX() > - Constants.LIMIT
                && position.getY() < Constants.LIMIT && position.getY() > - Constants.LIMIT;
    }
}

