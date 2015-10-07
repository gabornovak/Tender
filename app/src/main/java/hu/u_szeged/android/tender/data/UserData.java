package hu.u_szeged.android.tender.data;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Gabor Novak on 9/30/2014.
 * <p/>
 * Simple user data container class.
 */
public class UserData implements Serializable {
    private static Random random = new Random();

    private String name;
    private int age;
    private int profileImageId;
    private String lastLogin;
    private boolean nowOnline;
    private boolean isChecked;

    public UserData(String name, int profileImageId) {
        setName(name);
        setProfileImageId(profileImageId);
        setNowOnline(random.nextBoolean());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(int profileImageId) {
        this.profileImageId = profileImageId;
    }

    //FIXME return a valid last login info
    public String getLastLogin() {
        if (lastLogin == null) {
            lastLogin = String.valueOf(random.nextInt(24)) + " h";
        }
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    //FIXME return a proper age info
    public int getAge() {
        if (age == 0) {
            age = random.nextInt(80) + 18;
        }
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isNowOnline() {
        return nowOnline;
    }

    public void setNowOnline(boolean nowOnline) {
        this.nowOnline = nowOnline;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
