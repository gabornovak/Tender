package hu.u_szeged.android.tender.adapter;

import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hu.u_szeged.android.tender.R;
import hu.u_szeged.android.tender.data.UserData;

public class UserListAdapter extends BaseAdapter {
    private List<UserData> users;
    private Context context;

    public UserListAdapter(Context context, List<UserData> names) {
        this.context = context;
        this.users = names;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        TextView usernameTextView;
        TextView ageTextView;
        TextView lastLoginTextView;
        ImageView profileImage;
        ImageView checkMarkImage;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_list_item, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.usernameTextView = (TextView) convertView.findViewById(R.id.text_username);
            viewHolder.ageTextView = (TextView) convertView.findViewById(R.id.text_age);
            viewHolder.lastLoginTextView = (TextView) convertView.findViewById(R.id.text_last_login);
            viewHolder.profileImage = (ImageView) convertView.findViewById(R.id.image_profile);
            viewHolder.checkMarkImage = (ImageView) convertView.findViewById(R.id.image_check_mark);

            // store the holder with the view.
            convertView.setTag(viewHolder);

        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final UserData currentUser = users.get(i);

        viewHolder.usernameTextView.setText(currentUser.getName());
        viewHolder.ageTextView.setText(String.format(context.getString(R.string.age_text), currentUser.getAge()));

        if (currentUser.isNowOnline()) {
            viewHolder.lastLoginTextView.setText(R.string.now_online_text);
            viewHolder.lastLoginTextView.setTextColor(context.getResources().getColor(R.color.online_green));
            //Full visible
            viewHolder.lastLoginTextView.setAlpha(1f);
        } else {
            viewHolder.lastLoginTextView.setText(currentUser.getLastLogin());
            viewHolder.lastLoginTextView.setTextColor(context.getResources().getColor(R.color.primary_text));
            //Half visible
            viewHolder.lastLoginTextView.setAlpha(0.5f);
        }

        viewHolder.profileImage.setImageResource(users.get(i).getProfileImageId());

        if (currentUser.isChecked()) {
            viewHolder.checkMarkImage.setVisibility(View.VISIBLE);
            convertView.setEnabled(false);
        } else {
            viewHolder.checkMarkImage.setVisibility(View.INVISIBLE);
            convertView.setEnabled(true);
        }

        //Handle onLongClick
        final View finalConvertView = convertView;
        viewHolder.profileImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (currentUser.isChecked()) {
                    viewHolder.checkMarkImage.setVisibility(View.INVISIBLE);
                    finalConvertView.setEnabled(true);
                    currentUser.setIsChecked(false);
                } else {
                    Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(50);
                    viewHolder.checkMarkImage.setVisibility(View.VISIBLE);
                    finalConvertView.setEnabled(false);
                    currentUser.setIsChecked(true);
                }
                return false;
            }
        });
        return convertView;
    }
}