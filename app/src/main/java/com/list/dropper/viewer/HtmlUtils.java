package com.list.dropper.viewer;

import com.list.dropper.core.Project;
import com.list.dropper.core.Task;

public class HtmlUtils {

    public static String createHtmlDoc(Project[] projects) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><head><link href='styles.css' rel='stylesheet' type='text/css'><script>function toggleDone(e){e.childNodes[0].classList.toggle('doneTask'); and.toggleState(e.id);}</script></head><body>");

        for(Project project : projects){
            stringBuilder.append(createHtmlProject(project));
        }
        stringBuilder.append("</body></html>");
        return stringBuilder.toString();
    }

    private static String createHtmlProject(Project project) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<span class='project'>");
        stringBuilder.append("<h3 class='projectTitle'>" );
        stringBuilder.append(project.getTitle());
        stringBuilder.append(": </h3>");
        stringBuilder.append("<div class='projectTasks'>");

        for(Task task : project.getTasks()){
            stringBuilder.append(createHtmlTask(task));
        }

        stringBuilder.append("</div>");
        stringBuilder.append("</span>");
        return stringBuilder.toString();
    }

    private static  String createHtmlTask(Task task) {
        String taskDoneClass = "";
        if(task.getDone())taskDoneClass = "doneTask";

        return "<div class='taskContainer' id='"+task.getTaskId()+"' onclick='toggleDone(this)'><p class='taskName " + taskDoneClass + "'>- " + task.toString() + "</p></div>";
    }

}
