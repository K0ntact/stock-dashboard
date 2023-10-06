package vn.edu.usth.stockdashboard.utils;

import java.util.HashMap;

/**
 * Implement this interface to get notified when new data from WSEndpoint is available
 */
public interface WSDataNotify {
    void onNewData(HashMap<String, CustomCandleData> data);
}
