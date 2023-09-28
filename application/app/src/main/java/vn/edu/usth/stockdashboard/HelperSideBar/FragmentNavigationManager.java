package vn.edu.usth.stockdashboard.HelperSideBar;

import androidx.fragment.app.FragmentManager;

import vn.edu.usth.stockdashboard.FragmentInSlideBar.FavoriteListFragment;
import vn.edu.usth.stockdashboard.InterfaceSideBar.NavigationManager;
import vn.edu.usth.stockdashboard.StockMarketFragment;

public class FragmentNavigationManager implements NavigationManager {
    private static FragmentNavigationManager mInstance;
    private FragmentManager mFragmentManager;
    private StockMarketFragment stockMarketFragment;
    public static FragmentNavigationManager getmInstance(StockMarketFragment stockMarketFragment){
        if(mInstance == null)
            mInstance = new FragmentNavigationManager();
        mInstance.configure(stockMarketFragment);
        return mInstance;
    }
    private void configure(StockMarketFragment stockMarketFragment){
        stockMarketFragment = stockMarketFragment;
        mFragmentManager = stockMarketFragment.getChildFragmentManager();
    }

    @Override
    public void showFragment (String title){
    }

}
