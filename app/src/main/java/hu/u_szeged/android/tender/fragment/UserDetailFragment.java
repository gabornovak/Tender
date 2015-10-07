package hu.u_szeged.android.tender.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hu.u_szeged.android.tender.MainActivity;
import hu.u_szeged.android.tender.R;
import hu.u_szeged.android.tender.data.UserData;
import hu.u_szeged.android.tender.gateway.UserGateway;

public class UserDetailFragment extends Fragment {
    private static final String ARG_INDEX = "ARG_INDEX";

    private UserData userData;
    private CoordinatorLayout coordinatorLayout;

    public static UserDetailFragment newInstance(int index) {
        UserDetailFragment fragment = new UserDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    public UserDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);

        int index = 0;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
        }


        //Load the views from the inflated layout
        ImageView profileImage = (ImageView) view.findViewById(R.id.image_profile);
        TextView lastLoginTextView = (TextView) view.findViewById(R.id.text_last_login);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);

        //Get the data from the Intent
        // Previously added with this:
        //      i.putExtra("user", users.get(position));
        userData = UserGateway.getUsers().get(index);

        profileImage.setImageResource(userData.getProfileImageId());
        if (getActivity() instanceof AppCompatActivity && ((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(userData.getName());
        }

        if (userData.isNowOnline()) {
            lastLoginTextView.setText(R.string.now_online_text);
            lastLoginTextView.setTextColor(getResources().getColor(R.color.online_green));
        } else {
            lastLoginTextView.setText(String.format(getString(R.string.last_login_time_text), userData.getLastLogin()));
            lastLoginTextView.setAlpha(0.5f);
        }

        //Setup buttons
        view.findViewById(R.id.friend_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFriendsButtonClick();
            }
        });

        view.findViewById(R.id.post_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPostButtonClick();
            }
        });

        view.findViewById(R.id.photo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPhotoButtonClick();
            }
        });

        return view;
    }

    public void onFriendsButtonClick() {
        //One way to show info to the user
        Toast.makeText(getActivity(), "Yep, the user has this many friends...", Toast.LENGTH_LONG).show();
    }

    public void onPhotoButtonClick() {
        //An other way to show info to the user
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Too much photos... You won't handle it, sorry.")
                .setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public void onPostButtonClick() {
        Snackbar.make(coordinatorLayout, "You did something undoable...", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: Do something if the user presses undo
                    }
                })
                .show();
    }
}
