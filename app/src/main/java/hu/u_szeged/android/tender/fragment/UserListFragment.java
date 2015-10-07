package hu.u_szeged.android.tender.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import hu.u_szeged.android.tender.MainActivity;
import hu.u_szeged.android.tender.R;
import hu.u_szeged.android.tender.adapter.UserListAdapter;
import hu.u_szeged.android.tender.gateway.UserGateway;

public class UserListFragment extends Fragment {
    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    public UserListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        //Load the view from the inflated layout
        ListView listView = (ListView) view.findViewById(R.id.users_listview);

        //Set and create the adapter
        listView.setAdapter(new UserListAdapter(getActivity(), UserGateway.getUsers()));

        //Hide the default divider
        listView.setDividerHeight(0);

        //Handle on item click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).moveToDetailFragment(position);
                }
            }
        });
        return view;
    }
}
