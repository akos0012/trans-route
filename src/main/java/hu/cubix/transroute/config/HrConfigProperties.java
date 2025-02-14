package hu.cubix.transroute.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.TreeMap;

@ConfigurationProperties(prefix = "tr")
@Component
public class HrConfigProperties {

    private JwtData jwtData = new JwtData();

    public JwtData getJwtData() {
        return jwtData;
    }

    public void setJwtData(JwtData jwtData) {
        this.jwtData = jwtData;
    }

    public static class JwtData {
        private String issuer;
        private String secret;
        private String alg;
        private Duration duration;

        public String getIssuer() {
            return issuer;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getAlg() {
            return alg;
        }

        public void setAlg(String alg) {
            this.alg = alg;
        }

        public Duration getDuration() {
            return duration;
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }
    }


    private Delay delay = new Delay();

    public Delay getDelay() {
        return delay;
    }

    public void setDelay(Delay delay) {
        this.delay = delay;
    }

    public static class Delay {
        //Integer: minutes, Double: percent
        private TreeMap<Integer, Double> delayPercentages;

        public TreeMap<Integer, Double> getDelayPercentages() {
            return delayPercentages;
        }

        public void setDelayPercentages(TreeMap<Integer, Double> delayPercentages) {
            this.delayPercentages = delayPercentages;
        }
    }
}
