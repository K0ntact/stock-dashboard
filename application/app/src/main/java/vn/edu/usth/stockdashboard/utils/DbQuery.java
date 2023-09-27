package vn.edu.usth.stockdashboard.utils;

public class DbQuery {
    private static DbQuery instance;

    private DbQuery() {
    }

    public static DbQuery getInstance() {
        if (instance == null) {
            instance = new DbQuery();
        }
        return instance;
    }

    public String querySymbol(String symbol) {
        // TODO: Implement this
        return symbol;
    }

    public String queryCategory(String category) {
        // TODO: Implement this
        return category;
    }
}
