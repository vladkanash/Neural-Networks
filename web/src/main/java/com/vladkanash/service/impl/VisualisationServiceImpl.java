package com.vladkanash.service.impl;

import com.vladkanash.dto.ChartPoint;
import com.vladkanash.network.Trainer;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import com.vladkanash.service.VisualisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladk on 10.06.2017.
 */

@Service
public class VisualisationServiceImpl implements VisualisationService{

    private final Trainer trainer;
    private final List<ChartPoint> costPoints = new ArrayList<ChartPoint>();

    @Autowired
    public VisualisationServiceImpl(Trainer trainer) {
        this.trainer = trainer;
    }

    private void setCostPoints() {
        final List<Double> costs = trainer.getCosts();

        for (int i = 0; i < costs.size(); i++) {
            ChartPoint point = new ChartPoint();
            point.setX(i);
            point.setY(costs.get(i));
            costPoints.add(point);
        }
        trainer.clearCosts();
    }

    public List<ChartPoint> getCostValidation() {
        return this.costPoints;
    }

    public void train(double alpha, int count) {
        for (int i = 0; i < count; i++) {
            final DataSet trainingInput =
                    new DataSet(new Double[]{-4.56, 9.03, -3.0, -2.3, 5.0, -0.1, -4.9, -2.27, 0.0}, new Dimension(3, 3));
            final DataSet trainingOutput = new DataSet(new Double[]{0.13, 0.44}, new Dimension(1, 1, 2));
            trainer.trainSingle(trainingInput, trainingOutput, alpha);
        }
        this.costPoints.clear();
        setCostPoints();
    }
}
