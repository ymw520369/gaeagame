/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package com.gaea.game.logic.sample.impl;

import com.gaea.game.logic.sample.Sample;
import com.gaea.game.logic.sample.SampleFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017/3/17.
 *
 * @author Alan
 * @since 1.0
 */
public class SampleFactoryImpl<T extends Sample> implements SampleFactory<T> {

    private Map<Integer, Sample> sampleMap = new HashMap<Integer, Sample>();

    @Override
    public void addSample(T sample) {
        sampleMap.put(sample.sid, sample);
    }

    @Override
    public void addSamples(List<T> samples) {
        samples.forEach(sample -> addSample(sample));
    }

    @Override
    public void reloadSamples(List<T> samples) {
        sampleMap.clear();
        addSamples(samples);
    }

    @Override
    public T getSample(int sid) {
        return (T) sampleMap.get(sid);
    }

    @Override
    public T newSample(int sid) {
        T sample = getSample(sid);
        if (sample == null)
            return null;
        return (T) sample.clone();
    }

    @Override
    public Collection<T> getAllSamples() {
        return (Collection<T>) sampleMap.values();
    }

}
