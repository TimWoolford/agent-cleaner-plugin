function doAction(action, agentId) {
    $j.ajax({
                type: 'POST',
                url: '/agentManagement/action/',
                data: {'action': action, 'agentId': agentId },
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

function commentTooltip(agent) {
    if (agent.statusComment) {
        return $j('<img/>', {class: 'commentIcon', src: '/img/commentIcon.gif', width: "11px", height: "11px"})
                .mouseover(function (event) {
                    $j('#statusTooltip').remove();
                    $j('<div/>', {id: 'statusTooltip', class: 'statusTooltip', text: agent.statusComment})
                            .css({'position': 'absolute', 'top': event.pageY + 10, 'left': event.pageX + 18})
                            .appendTo('body');
                }).mouseout(function () {
                    setTimeout(function () {
                        $j('#statusTooltip').remove();
                    }, 500)
                });
    } else {
        return $j('<span/>');
    }
}

function usage(agent) {
    return $j('<img/>', {class: 'commentIcon', src: '/img/help.gif', width: "11px", height: "11px"})
            .mouseover(function (event) {
                var html = "<table>" +
                        "<tr><td><em>/data/apps<em></td><td>" + agent.diskUsage.formattedDataApps + "</td></tr>" +
                        "<tr><td><em>/logs/apps</em></td><td>" + agent.diskUsage.formattedLogsApps + "</td></tr>" +
                        "<tr><td><em>.m2/repository</em></td><td>" + agent.diskUsage.formattedMavenRepo + "</td></tr>" +
                        "</table>";

                $j('<div/>', {id: 'usageTooltip', class: 'usageTooltip', html: html})
                        .css({'position': 'absolute', 'top': event.pageY + 10, 'left': event.pageX + 18})
                        .appendTo('body');
            }).mouseout(function () {
                setTimeout(function () {
                    $j('#usageTooltip').remove();
                }, 500)
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

                $j('<td/>', { class: 'buildAgentName'})
                        .append($j('<a/>', {href: '/agentDetails.html?id=' + agent.id, text: agent.name}))
                        .appendTo(row);

                $j('<td/>', { class: 'buildAgentStatus ' + agent.status.toLowerCase(), text: agent.status})
                        .append(commentTooltip(agent))
                        .appendTo(row);

                $j('<td/>', { class: 'uptime', text: agent.formattedUptime })
                        .appendTo(row);

                $j('<td/>', { class: 'percentage', text: agent.diskSpaceSummary.formattedPercentageUsed })
                        .appendTo(row);

                $j('<td/>', { class: 'freeSpace', text: agent.diskSpaceSummary.formattedFreeSpace })
                        .append(usage(agent))
                        .appendTo(row);

                $j('<td nowrap/>', { class: 'cleanup'})
                        .append(cleanupButtonsFor(agent))
                        .appendTo(row);

                $j('<td/>', { class: 'rebuild' })
                        .append(rebuildButtonFor(agent))
                        .appendTo(row);

            });
        }});
}

function rebuildButtonFor(agent) {
    if (agent.hasPendingRebuild) {
        return button(agent, 'cancelRebuild', 'Cancel');
    } else {
        return button(agent, 'rebuild', 'Rebuild');
    }
}

function cleanupButtonsFor(agent) {
    var span = $j('<span/>');
    span.append(button(agent, 'cleanAppDirs', 'App Dirs'));
    span.append(button(agent, 'cleanMavenRepo', 'Maven Repo'));
    return span;
}

function button(agent, action, value) {
    return $j('<input/>', { type: 'submit', value: value})
            .click(function () {
                doAction(action, agent.id)
            })
}

function prepareAgentList() {
    var sortableElements = $j('table.sortable > thead > tr > th > span.sortable');

    sortableElements.each(function () {
        var element = $j(this);
        element.click(function () {
            populateAgentTable(element.attr('id'), !element.hasClass("sortedAsc"))
        })
    });
    populateAgentTable(sortableElements.first().attr('id'), true);
}