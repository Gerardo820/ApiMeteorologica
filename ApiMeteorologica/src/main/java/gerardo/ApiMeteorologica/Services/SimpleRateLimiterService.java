package gerardo.ApiMeteorologica.Services;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class SimpleRateLimiterService {


    private final ConcurrentHashMap<String, UserRequestInfo> userAccessMap = new ConcurrentHashMap<>();

    private static final int REQUEST_LIMIT = 5;
    private static final long TIME_WINDOW = 60;

    private static class UserRequestInfo {
        private int requestCount;
        private Instant windowStart;

        public UserRequestInfo() {
            this.requestCount = 0;
            this.windowStart = Instant.now();
        }

        public void resetWindow() {
            this.windowStart = Instant.now();
            this.requestCount = 0;
        }

        public boolean isWithinTimeWindow() {
            return Instant.now().isBefore(windowStart.plusSeconds(TIME_WINDOW));
        }

        public void incrementRequestCount() {
            this.requestCount++;
        }

        public int getRequestCount() {
            return requestCount;
        }
    }

    public boolean isAllowed(String userId) {
        UserRequestInfo userRequestInfo = userAccessMap.getOrDefault(userId, new UserRequestInfo());

        if (!userRequestInfo.isWithinTimeWindow()) {
            userRequestInfo.resetWindow();
        }

        if (userRequestInfo.getRequestCount() < REQUEST_LIMIT) {
            userRequestInfo.incrementRequestCount();
            userAccessMap.put(userId, userRequestInfo);
            return true;
        }

        return false;
    }
}

