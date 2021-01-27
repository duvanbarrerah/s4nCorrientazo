package com.s4n.corrientazo.business;

import com.s4n.corrientazo.exceptions.OutOfLimitException;
import com.s4n.corrientazo.model.Drone;
import com.s4n.corrientazo.model.IDrone;
import com.s4n.corrientazo.util.Constants;
import com.s4n.corrientazo.util.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DroneControl  implements Runnable{

    private  final Logger LOGGER = Logger.getLogger(this.getClass());

    private IDrone drone;

    private List<String> resultList = new ArrayList<String>();


    public void buildDrone(int id){
        this.drone = Drone.builder().id(id).build();
    }

    public void moveDrone(List<String> instructions) throws IllegalArgumentException, OutOfLimitException {

        for(String instruction: instructions) {
            for (int i = 0; i < instruction.length(); i++) {
                if ('A' == instruction.charAt(i))
                    drone.move();
                else{
                    drone.changeDirection(instruction.charAt(i));
                }
            }
            if(!drone.isValidPosition())
                throw new OutOfLimitException();

            resultList.add(drone.report());
        }

    }

    public void sendInstructions() throws OutOfLimitException, IllegalArgumentException {

        String fileName =String.format("%s%s%s",Constants.INPUT_FILE_PREFIX, drone.getId(), ".txt");
        List<String> instructions = FileUtils.readFileInList(fileName);
        if(instructions.size() > Constants.MAX_DELIVERIES_PER_DRONE)
            throw new OutOfLimitException(
                    String.format("drone %s cannot handle %s deliveries. max number of deliveries per drone is %s"
            , drone.getId(), instructions.size(), Constants.MAX_DELIVERIES_PER_DRONE));
        moveDrone(instructions);
    }

    public String generateResult() {
        String fileName = String.format("%s%s%s",Constants.OUTPUT_FILE_PREFIX, drone.getId(), ".txt");
        return FileUtils.writeResult(fileName, resultList);
    }

    @Override
    public void run() {
        try {
            sendInstructions();
            LOGGER.info(generateResult());
        } catch (OutOfLimitException e) {
            LOGGER.error(String.format("An error occurred in a business validation. Details: %s",e.getMessage()), e);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
