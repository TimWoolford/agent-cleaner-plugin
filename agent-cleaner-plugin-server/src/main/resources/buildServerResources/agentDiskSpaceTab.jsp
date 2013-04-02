<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Test</title>
</head>
<body>

<table>
    <c:forEach items="${agents}" var="agent">
        <tr>
            <td class="buildAgentName"><c:out value="${agent.name}"/></td>
            <td class="buildSize"><c:out value="${agent.diskSpaceSummary.freeSpace}"/> / <c:out value="${agent.diskSpaceSummary.totalSpace}"/></td>
        </tr>

    </c:forEach>
</table>

</body>
</html>
