<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${empty cookie.lang ? pageContext.response.locale : cookie.lang.value}"/>
<%--<fmt:setLocale value="en_GB"/>--%>
<fmt:setBundle basename="message"/>
<fmt:setBundle basename="language" var="languages"/>

<html>
<head>
    <title><fmt:message key="todo.appName"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/myStyle.css.css">
</head>
<body>
<%--to sprawdza w jakim języku mam przeglądarkę--%>
${pageContext.response.locale}
<p>
    <a href="tasks.jsp?lang=en_GB"><fmt:message key="language.english" bundle="${languages}"/> </a> |
    <a href="tasks.jsp?lang=pl_PL"><fmt:message key="language.polish" bundle="${languages}"/> </a> |
    <a href="tasks.jsp?lang=de_DE"><fmt:message key="language.german" bundle="${languages}"/> </a>
</p>

<div   class=" d-flex flex-column flex-md-row align-items-center p-5 px-md-6 mb-3 bg-white border-bottom shadow-sm" >
    <h5 class="my-0 mr-md-auto font-weight-normal"><fmt:message key="todo.appName"/></h5>

</div>

<%
    String lang = request.getParameter("lang");
    if (lang != null) {
        Cookie c = new Cookie("lang", lang);
        response.addCookie(c);
        response.sendRedirect("tasks.jsp");
        return;
    }
%>
<div class="odstep container">


    <main role="main" class="row inner cover font-black" id="mainFromular">
        <form action="tasks" method="post">
            <div class="row col-md-12">
                <div class="col-1"><label for="textId"><fmt:message key="todo.newTask"/></label></div>
                <div class="col-3"><input id="textId" type="text" name="taskDescription"></div>

                <div class="col-1"><label for="dateId"><fmt:message key="todo.whenDone"/></label></div>
                <div class="col-3"><input id="dateId" type="datetime-local" name="finishDate"></div>

                <div class="col-1"><label for="priorytyId"> <fmt:message key="todo.priority"/> </label></div>
                <div class="col-3"><select id="priorytyId" name="priority"/></div>

                <div class="col-1"><option value="HIGH"><fmt:message key="todo.high"/></option></div>
                <div class="col-1"> <option value="MID" selected><fmt:message key="todo.mid"/></option>
                </div>
                <div class="col-1">
                    <option value="LOW"><fmt:message key="todo.niski"/></option>
                </div>
                </select>
            </div>
            <br><br>
            <input type="submit" value="<fmt:message key="todo.addButton"/>">
        </form>
    </main>
    <table>
        <thead>
        <tr>
            <th><fmt:message key="todo.newTask"/></th>
            <th><fmt:message key="todo.whenDone"/></th>
            <th><fmt:message key="todo.priority"/></th>
        </tr>
        </thead>
        <c:forEach var="myTasks" items="${sessionScope.tasksList}">
            <tr>

                <td>${myTasks.taskDescription}</td>
                <td>${myTasks.finishTime}</td>
                <td>${myTasks.prioryty}</td>

            </tr>
        </c:forEach>
    </table>
    ${pageContext.response.locale}

</div>
<%--Bootstrap--%>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>

</body>
</html>
