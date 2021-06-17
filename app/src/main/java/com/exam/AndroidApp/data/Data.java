package com.exam.AndroidApp.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable
{

    private String TaskName;
    private String Description;
    private String Status;
    private String Project;
    private int Difficulty;

    public static String Dummy_desc = "Lorem ipsum dolor sit amet, " +
            "consectetur adipiscing elit. Integer nec odio. " +
            "Praesent libero. Sed cursus ante dapibus diam. " +
            "Sed nisi. Nulla quis sem at nibh elementum imperdiet. " +
            "Duis sagittis ipsum. Praesent mauris. Fusce nec tellus " +
            "sed augue semper porta. Mauris massa. Vestibulum lacinia " +
            "arcu eget nulla. Class aptent taciti sociosqu ad " +
            "litora torquent per conubia nostra, per inceptos " +
            "himenaeos. Curabitur sodales ligula in libero. " +
            "Sed dignissim lacinia nunc.";


    public static ArrayList<Data> getData() {
        ArrayList<Data> datas = new ArrayList<>();
        datas.add(new Data("Change tasks status", "To do", "Android Master", 5, Data.Dummy_desc));
        datas.add(new Data("Persist Data", "To do", "Android Master", 8, Data.Dummy_desc));
        datas.add(new Data("Design", "To do", "Android Master", 8, Data.Dummy_desc));
        datas.add(new Data("Dummy data", "Done", "Android Master", 2, Data.Dummy_desc));
        datas.add(new Data("Link to Firebase", "Done", "Android Master", 8, Data.Dummy_desc));
        datas.add(new Data("Google Auth", "Done", "Android Master", 8, Data.Dummy_desc));
        datas.add(new Data("Landscape Mode", "Done", "Android Master", 5, Data.Dummy_desc));
        datas.add(new Data("Camera", "Done", "Android Master", 8, Data.Dummy_desc));
        datas.add(new Data("Share capability", "Done", "Android Master", 8, Data.Dummy_desc));
        datas.add(new Data("Filtering capability", "Done", "Android Master", 5, Data.Dummy_desc));
        datas.add(new Data("Animator", "Done", "Android Master", 2, Data.Dummy_desc));
          return datas;
    }


    public Data(String TaskName, String Status, String Project, int Difficulty, String Desc)
    {
        this.TaskName = TaskName;
        this.Description = Desc;
        this.Status = Status;
        this.Project = Project;
        this.Difficulty = Difficulty;
    }

    public String getTaskName()
    {
        return TaskName;
    }

    public String getDescription()
    {
        return Description;
    }

    public String getDifficulty() {
        return String.valueOf(Difficulty);
    }

    public String getStatus()
    {
        return Status;
    }

    public String getProject() {
        return Project;
    }


}

