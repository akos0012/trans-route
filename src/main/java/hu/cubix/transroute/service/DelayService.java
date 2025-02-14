package hu.cubix.transroute.service;

import hu.cubix.transroute.config.HrConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.TreeMap;

@Service
public class DelayService {

    @Autowired
    private HrConfigProperties config;

    public double getIncomeDecreasePercent(int minutes) {
        TreeMap<Integer, Double> delayPercentages = config.getDelay().getDelayPercentages();

        Optional<Integer> optionalMax = delayPercentages.keySet().stream().filter(k -> k <= minutes).max(Integer::compare);
        return optionalMax.isEmpty() ? 0 : delayPercentages.get(optionalMax.get());
    }
}
