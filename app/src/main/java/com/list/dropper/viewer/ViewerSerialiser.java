package com.list.dropper.viewer;

import com.list.dropper.core.Project;
import com.list.dropper.core.Task;

public class ViewerSerialiser {

    public static String serialiseProjects(Project[] projects){
        StringBuilder stringBuilder = new StringBuilder();

        for(Project project : projects){
            if(!project.getTitle().equals("_global_")){
                stringBuilder.append(project.getTitle());
                stringBuilder.append(":\n");
            }
            for(Task task : project.getTasks()){
                stringBuilder.append(" - ");
                stringBuilder.append(task.getName());

                if(task.getDone()) stringBuilder.append(" @done");

                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

}
