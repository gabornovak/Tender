package hu.u_szeged.android.tender.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hu.u_szeged.android.tender.R;
import hu.u_szeged.android.tender.data.UserData;

public class UserGateway {
    public static final int NUMBER_OF_USERS = 50;
    private static List<UserData> users;

    static {
        users = new ArrayList<>();
        //Fill the user list with random stuff
        Random r = new Random();
        String[] sureNames = new String[]{"Laszlo", "Beata", "Istvan", "Rodrigez", "Emilio", "Un", "Szasa"};
        String[] lastNames = new String[]{"Kim Jong", "Kovacs", "Kiss", "Szurke", "Lakatos"};
        int[] ids = new int[]{R.drawable.profile_picture, R.drawable.profile_picture2, R.drawable.profile_picture3,
                R.drawable.profile_picture4, R.drawable.profile_picture5, R.drawable.profile_picture6, R.drawable.profile_picture7};
        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            users.add(new UserData(sureNames[r.nextInt(sureNames.length)] + " " + lastNames[r.nextInt(lastNames.length)], ids[r.nextInt(ids.length)]));
        }
    }

    public static List<UserData> getUsers() {
        return users;
    }
}
