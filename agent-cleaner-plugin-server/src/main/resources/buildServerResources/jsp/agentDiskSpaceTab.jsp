<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="agentList" class="refreshable">
    <div id="agentListInner" class="refreshableInner">
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
                <tr class="agentRow">
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
    $j("tr.agentRow > td.percentage").each(function(x, el){
        $j(el).progressbar({
            value: $j(el).text()
        })
    })

</script>
<script type="text/javascript">
    (function () {
        function reSort(event, element) {
            var sortBy = element.id;
            if (!sortBy) {
                element = element.firstChild;
                sortBy = element.id;
            }

            var sortAsc = element.className.indexOf('sorted') == -1 || element.className.indexOf('sortedDesc') != -1;
            $('agentList').refresh(null, "sortBy=" + sortBy + "&sortAsc=" + sortAsc);
        }

        if ($('SORT_BY_NAME')) {
            <c:if test="'SORT_BY_NAME' == ${sortBy}">
            $('SORT_BY_NAME').className = $('SORT_BY_NAME').className + ' sortedAsc';
            </c:if>
            $($('SORT_BY_NAME').parentNode).on("click", reSort);
        }

        if ($('SORT_BY_PERCENTAGE')) {
            <c:if test="'SORT_BY_PERCENTAGE' == ${sortBy}">
            $('SORT_BY_PERCENTAGE').className = $('SORT_BY_PERCENTAGE').className + ' sortedAsc';
            </c:if>
            $($('SORT_BY_PERCENTAGE').parentNode).on("click", reSort);
        }
    })();
</script>