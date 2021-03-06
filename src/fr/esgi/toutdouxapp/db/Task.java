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


    /** Instance methods **/

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
        final TasksDataSource tasksDataSource = new TasksDataSource(context);
        tasksDataSource.open();
        state = tasksDataSource.toggleState(this);
        tasksDataSource.close();
        return this;
    }

    public void update (Context context) {
        final TasksDataSource tasksDataSource = new TasksDataSource(context);
        tasksDataSource.open();
        tasksDataSource.updateTask(this);
        tasksDataSource.close();
    }

    public Task empty(Category c) {
        title = "";
        description = "";
        dueDate = new Date();
        state = 0;
        category = c;
        return this;
    }


    /** Class methods **/

    public static ArrayList<Task> findAll (Context context, String where) {
        final TasksDataSource tasksDataSource = new TasksDataSource(context);
        tasksDataSource.open();
        final ArrayList<Task> tasks = tasksDataSource.getAllTasks(where);
        tasksDataSource.close();
        return tasks;
    }

    public static Task findOne (Context context, long id) {
        final TasksDataSource tasksDataSource = new TasksDataSource(context);
        tasksDataSource.open();
        final Task task = tasksDataSource.getOneTask(id);
        tasksDataSource.close();
        return task;
    }

    public static Task create (Context context, String title, String description, Date dueDate, long categoryId) {
        final TasksDataSource tasksDataSource = new TasksDataSource(context);
        tasksDataSource.open();
        final Task task = tasksDataSource.createTask(title, description, dueDate, categoryId);
        tasksDataSource.close();
        return task;
    }

    public static void deleteOne (Context context, Task task) {
        final TasksDataSource tasksDataSource = new TasksDataSource(context);
        tasksDataSource.open();
        tasksDataSource.deleteTask(task);
        tasksDataSource.close();
    }

}
