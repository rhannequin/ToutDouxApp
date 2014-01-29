package fr.esgi.toutdouxapp.db;

import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class Task implements Parcelable {

    public long id;
    public String title, description;
    public Date dueDate;
    public int state;
    public Category category;

    public Task() {
        super();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(dueDate.getTime());
        dest.writeInt(state);
        dest.writeParcelable(category, flags);
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
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        dueDate = new Date(in.readLong());
        state = in.readInt();
        category = (Category)in.readParcelable(Category.class.getClassLoader());
    }

    public int getDaysLeft() {
        Date now = new Date();
        final long timeNow = now.getTime();
        final long timeDue = dueDate.getTime();
        final long oneDay = 1000 * 60 * 60 * 24;
        final int left = (int) ((timeDue - timeNow) / oneDay);
        return left;
    }

    public String getTimeLeft() {
        final int left = getDaysLeft();

        String result;
        if (left > 1) {
            result = left + " days left";
        } else if (left > 0) {
            result = "Tomorrow";
        } else if (left == 0) {
            result = "Today";
        } else if (left == -1) {
            result = "Yesterday";
        } else {
            result = Math.abs(left) + " days ago";
        }

        return result;
    }

    public Boolean isDone() {
        return state == 1;
    }

    public Task toggleState (Context context) {
        TasksDataSource tasksDataSource = new TasksDataSource(context);
        tasksDataSource.open();
        state = tasksDataSource.toggleState(this);
        tasksDataSource.close();
        return this;
    }

    public static ArrayList<Task> findAll (Context context, String where) {
        TasksDataSource tasksDataSource = new TasksDataSource(context);
        tasksDataSource.open();
        ArrayList<Task> tasks = tasksDataSource.getAllTasks(where);
        tasksDataSource.close();
        return tasks;
    }

    public static Task findOne (Context context, long id) {
        TasksDataSource tasksDataSource = new TasksDataSource(context);
        tasksDataSource.open();
        Task task = tasksDataSource.getOneTask(id);
        tasksDataSource.close();
        return task;
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

    public static void deleteOne (Context context, Task task) {
        TasksDataSource tasksDataSource = new TasksDataSource(context);
        tasksDataSource.open();
        tasksDataSource.deleteTask(task);
        tasksDataSource.close();
    }

}
