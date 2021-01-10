package com.okotheffie.remotemonioringapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.okotheffie.remotemonioringapp.CommunicationFragment;
import com.okotheffie.remotemonioringapp.ReportsFragment;
import com.okotheffie.remotemonioringapp.TasksFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new TasksFragment();
        }
        else if (position == 1)
        {
            fragment = new CommunicationFragment();
        }
        else if (position == 2)
        {
            fragment = new ReportsFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Tasks";
        }
        else if (position == 1)
        {
            title = "Communication";
        }
        else if (position == 2)
        {
            title = "Reports";
        }
        return title;
    }
}
