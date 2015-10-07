package hu.u_szeged.android.tender.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import hu.u_szeged.android.tender.fragment.UserDetailFragment;
import hu.u_szeged.android.tender.gateway.UserGateway;

public class UserDetailAdapter extends FragmentStatePagerAdapter {
    public UserDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return UserDetailFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return UserGateway.getUsers().size();
    }
}
