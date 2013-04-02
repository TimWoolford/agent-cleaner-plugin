<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Test</title>
</head>
<body>

<table cellspacing="0" class="agents dark sortable borderBottom">
    <thead>
        <tr>
            <th>Agent Name</th>
            <th>Percentage Used</th>
            <th>Size</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${agents}" var="agent">
        <tr>
            <td class="buildAgentName sortable"><c:out value="${agent.name}"/></td>
            <td class="percentage"><c:out value="${agent.diskSpaceSummary.percentageUsed}"/>%</td>
            <td class="buildSize"><c:out value="${agent.diskSpaceSummary.freeSpace}"/> / <c:out value="${agent.diskSpaceSummary.totalSpace}"/></td>
        </tr>

    </c:forEach>
    </tbody>
</table>

</body>
</html>
