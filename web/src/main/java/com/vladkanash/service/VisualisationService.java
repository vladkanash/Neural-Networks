package com.vladkanash.service;

import com.vladkanash.dto.ChartPoint;

import java.util.List;

/**
 * Created by vladk on 10.06.2017.
 */
public interface VisualisationService {

    List<ChartPoint> getCostValidation();

    void train(double alpha, int count);
}
