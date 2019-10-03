package controller;

import entity.Prioryty;
import entity.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/tasks")
public class TaskController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("tasksList") == null) {
            List<Task> tasks = new ArrayList<>();
            session.setAttribute("tasksList", tasks);
        }

        String lang = request.getParameter("lang");
        if (lang != null) {
            Cookie c = new Cookie("lang", lang);
            response.addCookie(c);
            response.sendRedirect("tasks.jsp");
            return;
        }

        request.getRequestDispatcher("tasks.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String taskParam = request.getParameter("taskDescription");
        if (taskParam == null) {
            return;
        }

        Task task1 = new Task();
        task1.setTaskDescription(taskParam);


        String date = request.getParameter("date");
        if (date != null && !date.isEmpty()) {
            LocalDateTime formatedDate = LocalDateTime.parse(
                    date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            task1.setFinishTime(formatedDate);
        }

        String priorityParam = request.getParameter("priority");
        if (priorityParam != null && !priorityParam.isEmpty()) {
            Prioryty priorytyVal = Prioryty.valueOf(priorityParam.toUpperCase());
            task1.setPrioryty(priorytyVal);
        }

        HttpSession session = request.getSession();
        List<Task> taskList = (List<Task>) session.getAttribute("tasksList");
        taskList.add(task1);
        response.sendRedirect("tasks");
    }
}
