package fr.esgi.toutdouxapp;

import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class Task implements Parcelable {

    private String title;
    private String description;
    private Date dueDate;

    public Task(
            String title,
            String description,
            Date dueDate) {
        super();
        this.setTitle(title);
        this.setDescription(description);
        this.setDueDate(dueDate);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getTitle());
        dest.writeString(this.getDescription());
        dest.writeLong(this.getDueDate().getTime());
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public Task(Parcel in) {
        this.setTitle(in.readString());
        this.setDescription(in.readString());
        this.setDueDate(new Date(in.readLong()));
    }

    public String getTimeLeft() {
        Date now = new Date();

        final long timeNow = now.getTime();
        final long timeDue = this.getDueDate().getTime();
        final long oneDay = 1000 * 60 * 60 * 24;
        final int left = (int) ((timeDue - timeNow) / oneDay);

        String result;
        if (left > 1) {
            result = left + " days left";
        } else if (left > 0) {
            result = "Tomorrow";
        } else {
            result = "Today";
        }

        return result;
    }

}
