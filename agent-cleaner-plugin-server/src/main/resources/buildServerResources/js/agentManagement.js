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

function updateTableHeaders(sortBy, sortAsc) {
    $j('table.sortable > thead > tr > th > span.sortable').each(function () {
        var element = $j(this);
        element.removeClass("sortedAsc sortedDesc");
        if (sortBy == this.id) {
            element.addClass(sortAsc ? "sortedAsc" : "sortedDesc")
        }
    });
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

            updateTableHeaders(sortBy, sortAsc);

            var tableBody = $j('#agentTableBody');
            tableBody.empty();

            agents.forEach(function (agent) {
                var row = $j('<tr/>').appendTo(tableBody);

                var agentLink = $j('<a/>', {href: '/agentDetails.html?id=' + agent.id, text: agent.name});

                $j('<td/>', { class: 'buildAgentName'}).append(agentLink).appendTo(row);
                $j('<td/>', { class: 'buildAgentStatus', text: agent.status }).appendTo(row);
                $j('<td/>', { class: 'percentage', text: agent.diskSpaceSummary.formattedPercentageUsed }).appendTo(row);
                $j('<td/>', { class: 'freeSpace', text: agent.diskSpaceSummary.formattedFreeSpace }).appendTo(row);
            });
        }});
}

function prepareAgentList() {
    var sortableElements = $j('table.sortable > thead > tr > th > span.sortable');

    sortableElements.each(function () {
        var element = $j(this);
        element.click(function() {
            populateAgentTable(element.attr('id'), !element.hasClass("sortedAsc"))
        })
    });
    populateAgentTable(sortableElements.first().attr('id'), true);
}