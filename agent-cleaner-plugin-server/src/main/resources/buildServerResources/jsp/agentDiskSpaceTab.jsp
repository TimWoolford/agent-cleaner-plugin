<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="agentManagement">
    <input type="submit" onclick="doAction('disable')" value='Disable ALL'/>
    <input type="submit" onclick="doAction('enable')" value='Enable ALL'/>
</div>


<div id="agentList" class="refreshable">
    <div id="agentListInner" class="refreshableInner">
        <table cellspacing="0" class="agents dark sortable borderBottom">
            <thead>
            <tr>
                <th class="buildAgentName"><span id="SORT_BY_NAME">Agent Name</span></th>
                <th class="buildAgentStatus"><span>Status</span></th>
                <th class="percentage"><span id="SORT_BY_PERCENTAGE">Percentage Used</span></th>
                <th class="freeSpace">Free Space</th>
            </tr>
            </thead>
            <tbody id="agentTableBody"/>
        </table>
    </div>
</div>

<script type="text/javascript">
    function doAction(action) {
        $j.ajax({
                    type: 'POST',
                    url: '/agentManagement/action/',
                    data: {'action': action },
                    success: function () {
                        location.reload();
                    }

                }
        );
    }

    function setHeaderStuff(sortBy, sortAsc) {
        if (sortBy == 'SORT_BY_NAME') {
            var element = $j('#SORT_BY_NAME');
            element.removeClass("sortedAsc sortedDesc")
            element.addClass(sortAsc ? "sortedAsc" : "sortedDesc")

            $j('#SORT_BY_PERCENTAGE').removeClass("sortedAsc sortedDesc")
        } else if (sortBy == 'SORT_BY_PERCENTAGE') {
            var element = $j('#SORT_BY_PERCENTAGE');
            element.removeClass("sortedAsc sortedDesc")
            element.addClass(sortAsc ? "sortedAsc" : "sortedDesc")

            $j('#SORT_BY_NAME').removeClass("sortedAsc sortedDesc")
        }
    }

    function populateAgentTable(sortBy, sortAsc) {
        $j.ajax({
            type: 'GET',
            url: '/agentManagement/agents/',
            dataType: 'json',
            data: {
                'sortBy': sortBy,
                'sortAsc': sortAsc
            },
            success: function (agents) {

                setHeaderStuff(sortBy, sortAsc);

                var tableBody = $j('#agentTableBody');
                tableBody.empty();

                agents.forEach(function (agent) {
                    var row = $j('<tr/>').appendTo(tableBody);

                    var agentLink = $j('<a/>', {href: '/agentDetails.html?id=' + agent.id, text: agent.name});

                    $j('<td/>', { class: 'buildAgentName'}).append(agentLink).appendTo(row);
                    $j('<td/>', { class: 'buildAgentStatus', text: agent.status }).appendTo(row);
                    $j('<td/>', { class: 'percentage', text: agent.diskSpaceSummary.percentageUsed }).appendTo(row);
                    $j('<td/>', { class: 'freeSpace', text: agent.diskSpaceSummary.formattedFreeSpace }).appendTo(row);
                });
            }});
    }

    function prepare() {
        $j('#SORT_BY_NAME').click(function () {
            populateAgentTable('SORT_BY_NAME', !$j(this).hasClass("sortedAsc"))
        });

        $j('#SORT_BY_PERCENTAGE').click(function () {
            populateAgentTable('SORT_BY_PERCENTAGE', !$j(this).hasClass("sortedAsc"))
        });

        populateAgentTable('SORT_BY_NAME', true);
    }

    $j(document).ready(prepare());

</script>
