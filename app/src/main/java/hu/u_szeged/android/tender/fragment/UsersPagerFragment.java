package hu.u_szeged.android.tender.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hu.u_szeged.android.tender.MainActivity;
import hu.u_szeged.android.tender.R;
import hu.u_szeged.android.tender.adapter.UserDetailAdapter;
import hu.u_szeged.android.tender.data.UserData;
import hu.u_szeged.android.tender.gateway.UserGateway;
import hu.u_szeged.android.tender.utils.DepthPageTransformer;

public class UsersPagerFragment extends Fragment {
    private static final String ARG_INDEX = "ARG_INDEX";

    private ViewPager viewPager;

    public static UsersPagerFragment newInstance(int index) {
        UsersPagerFragment fragment = new UsersPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    public UsersPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_pager, container, false);

        int index = 0;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
        }

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new UserDetailAdapter(getChildFragmentManager()));
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setCurrentItem(index);

        return view;
    }
}
