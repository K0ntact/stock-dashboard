package vn.edu.usth.stockdashboard.utils;

import java.util.HashMap;

/**
 * Implement this interface to get notified when new data from ClientEndpoint is available
 */
public interface DataNotify {
    void onNewData(HashMap<String, CustomCandleData> data);
}
