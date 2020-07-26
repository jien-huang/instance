package com.automationtest.instance.services;

import com.automationtest.instance.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ResultsFolder  {

    private static final String RESULTS = Config.getInstance().get("folder.results", "./results/").toString();

    @Autowired
    GitCenter gitCenter;

}
