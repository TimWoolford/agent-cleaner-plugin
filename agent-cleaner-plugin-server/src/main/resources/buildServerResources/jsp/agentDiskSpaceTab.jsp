<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Test</title>
</head>
<body>

<div id="agentSizeList" class="refreshable">
    <div id="agentSizeListInner" class="refreshableInner">
        <table cellspacing="0" class="agents dark sortable borderBottom">
            <thead>
            <tr>
                <th class="buildAgentName sortable"><span id="SORT_BY_NAME">Agent Name</span></th>
                <th class="percentage sortable"><span id="SORT_BY_PERCENTAGE">Percentage Used</span></th>
                <th class="freeSpace">Free Space</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${agents}" var="agent">
                <tr>
                    <td class="buildAgentName"><c:out value="${agent.name}"/></td>
                    <td class="percentage"><c:out value="${agent.diskSpaceSummary.percentageUsed}"/>%</td>
                    <td class="freeSpace"><c:out value="${agent.diskSpaceSummary.formattedFreeSpace}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript">
    (function () {
        function reSort(event, element) {
            var sortBy = element.id;
            if (!sortBy) {
                element = element.firstChild;
                sortBy = element.id;
            }

            var sortAsc = element.className.indexOf('sorted') == -1 || element.className.indexOf('sortedDesc') != -1;
            $('agentSizeList').refresh(null, "sortBy=" + sortBy + "&sortAsc=" + sortAsc);
        }

        if ($('SORT_BY_NAME')) {

            $('SORT_BY_NAME').className = $('SORT_BY_NAME').className + ' sortedAsc';

            $($('SORT_BY_NAME').parentNode).on("click", reSort);
        }

        if ($('SORT_BY_PERCENTAGE')) {

            $($('SORT_BY_PERCENTAGE').parentNode).on("click", reSort);
        }
    })();
</script>
</body>
</html>
