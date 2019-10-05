package controller;

import entity.Prioryty;
import entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger log = LoggerFactory.getLogger(TaskController.class);

    @Override
    public void init() throws ServletException {
// tutaj inicjulizujemy zasoby (np. bazy danych)
//        metoda wywołana przy tworzeniu instancji klasy servletu
    }

    @Override
    public void destroy() {
//        metoda wywoływana przy zamknięciu kontekstu serwletów
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        metoda get zawsze zwraca listę zadań do wyświetlenia

        HttpSession session = request.getSession();

        if (session.getAttribute("tasksList") == null) {
            List<Task> tasks = new ArrayList<>();
            session.setAttribute("tasksList", tasks);
        }

        String lang = request.getParameter("lang");
        if (lang != null) {
            Cookie c = new Cookie("lang", lang);
            response.addCookie(c);
            response.sendRedirect("tasks");
            return;
        }
// przekierowanie po stronie serwera
//        wskazuje tomcatowi jaki widok ma pokazać (jest po przekierowanie po stronie serwera)
//        tutaj wyświetla
        request.getRequestDispatcher("/tasks.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String taskParam = request.getParameter("taskDescription");
        if (taskParam == null) {
//            po wywołaniu metody post wracamy do metody get
            response.sendRedirect("tasks");
            return;
        }
        try {
            if(1==1){
                throw new RuntimeException("some exception");
            }
        }catch (Exception e){
            log.error("Some error occured: "+e);
        }

        Task task1 = new Task();
        task1.setTaskDescription(taskParam);


        String date = request.getParameter("finishDate");
        if (date != null && !date.isEmpty()) {
            LocalDateTime formatedDate = LocalDateTime.parse(
                    date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            task1.setFinishTime(formatedDate);
        }
        log.debug("Creating new task {} with finish date {}: " ,taskParam, date);

        String priorityParam = request.getParameter("priority");
        if (priorityParam != null && !priorityParam.isEmpty()) {
            Prioryty priorytyVal = Prioryty.valueOf(priorityParam.toUpperCase());
            task1.setPrioryty(priorytyVal);
        }
// sesja żyje w tomcacie 30 min, więc zabezpieczeniem przeg wygaśnięciem jest
        HttpSession session = request.getSession();
        List<Task> taskList = (List<Task>) session.getAttribute("tasksList");
        if(task1 == null){
            List<Task> tasks = new ArrayList<>();
            session.setAttribute("tasksList", tasks);
        }

        taskList.add(task1);
//        zmienia po stronie przeglądarki zostanie wyświetlona strona i będzie metoda get
//        odświerzamy stronę za pomocą redirect i kierujemy na tą samą stronę, która jest
//        wzbogacona o nowe dane. Każe przeglądarce wyświetlić stronę o adresie tasks
//        Redirect zawsze wywołuje metodę doGet
        response.sendRedirect("tasks");
    }
}
