package Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//password class
@Entity(tableName = "password")
public class Password implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "password")
    @SerializedName("password")
    @Expose
    private String password;
    @ColumnInfo(name = "phonetic")
    @SerializedName("phonetic")
    @Expose
    private String phonetic;

    public Password(String password, String phonetic) {
        this.password = password;
        this.phonetic = phonetic;
    }

    protected Password(Parcel in) {
        id = in.readLong();
        password = in.readString();
        phonetic = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(password);
        dest.writeString(phonetic);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Password> CREATOR = new Creator<Password>() {
        @Override
        public Password createFromParcel(Parcel in) {
            return new Password(in);
        }

        @Override
        public Password[] newArray(int size) {
            return new Password[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

}