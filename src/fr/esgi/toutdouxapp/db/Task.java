package fr.esgi.toutdouxapp.db;

import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class Task implements Parcelable {

    private long id;
    private String title;
    private String description;
    private Date dueDate;
    private int state;
    private Category category;

    public Task() {
        super();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.getId());
        dest.writeString(this.getTitle());
        dest.writeString(this.getDescription());
        dest.writeLong(this.getDueDate().getTime());
        dest.writeInt(this.getState());
        dest.writeParcelable(this.getCategory(), flags);
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
        this.setId(in.readLong());
        this.setTitle(in.readString());
        this.setDescription(in.readString());
        this.setDueDate(new Date(in.readLong()));
        this.setState(in.readInt());
        this.setCategory((Category)in.readParcelable(Category.class.getClassLoader()));
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

    public Boolean isDone() {
        return this.getState() == 1;
    }

    public static ArrayList<Task> findAll (Context context) {
        TasksDataSource tasksDataSource = new TasksDataSource(context);
        tasksDataSource.open();
        ArrayList<Task> tasks = tasksDataSource.getAllTasks();
        tasksDataSource.close();
        return tasks;
    }

    public static Task create (Context context, String title, String description, Date dueDate, long categoryId) {
        TasksDataSource tasksDataSource = new TasksDataSource(context);
        tasksDataSource.open();
        Task task = tasksDataSource.createTask(title, description, dueDate, categoryId);
        tasksDataSource.close();
        return task;
    }

    public static void deleteAll (Context context) {
        TasksDataSource tasksDataSource = new TasksDataSource(context);
        tasksDataSource.open();
        tasksDataSource.resetTable();
        tasksDataSource.close();
    }

}