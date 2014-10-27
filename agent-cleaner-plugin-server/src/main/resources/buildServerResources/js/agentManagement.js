function doBulkAction(action) {
    $j.ajax({
                type: 'POST',
                url: '/agentManagement/bulkAction/',
                data: {'action': action},
                success: function () {
                    location.reload();
                }
            }
    );
}

function doAction(action, agentId) {
    $j.ajax({
                type: 'POST',
                url: '/agentManagement/action/',
                data: {'action': action, 'agentId': agentId },
                success: function (agent) {
                    var row = $j('tr#' + agent.id);
                    updateCell(row.find('td.percentage'), agent.diskSpaceSummary.formattedPercentageUsed);
                    updateCell(row.find('td.freeSpace'), agent.diskSpaceSummary.formattedFreeSpace).append(usage(agent));
                    updateCell(row.find('td.uptime'), agent.formattedUptime);
                    updateCell(row.find('td.buildAgentStatus'), agent.status).removeClass('enabled disabled').addClass(agent.status.toLowerCase())
                }
            }
    );
}

function updateCell(cell, text) {
    var ret = cell.text(text);
    $j('#agentTable').trigger('updateCell', [cell, false]);
    return ret;
}

function prepareAgentList() {
    $j.ajax({
        type: 'GET',
        url: '/agentManagement/agents/',
        dataType: 'json',
        success: function (agents) {

            var tableBody = $j('#agentTableBody');
            tableBody.empty();

            agents.forEach(function (agent) {
                var row = $j('<tr/>', {id: agent.id}).appendTo(tableBody);

                $j('<td/>', { class: 'buildAgentName ' + agent.runningBuildStatus})
                        .append($j('<a/>', {href: '/agentDetails.html?id=' + agent.id, text: agent.name}))
                        .appendTo(row);

                $j('<td/>', { class: 'buildAgentStatus ' + agent.status.toLowerCase(), text: agent.status})
                        .append(commentTooltip(agent))
                        .appendTo(row);

                $j('<td/>', { class: 'uptime', text: agent.formattedUptime })
                        .appendTo(row);

                $j('<td/>', { class: 'percentage', text: '--' })
                        .appendTo(row);

                $j('<td/>', { class: 'freeSpace', text: '--' })
                        .appendTo(row);

                $j('<td/>', { class: 'cleanup'})
                        .append(cleanupButtonsFor(agent))
                        .appendTo(row);

                $j('<td/>', { class: 'rebuild' })
                        .append(rebuildButtonFor(agent))
                        .appendTo(row);

                $j.ajax({
                    type: 'GET',
                    url: '/agentManagement/agentDetail/',
                    datatype: 'json',
                    data: { agentId: agent.id },
                    success: hydrateRow
                });

                row.hover(
                        function () {
                            row.addClass("current-row");
                        }, function () {
                            row.removeClass("current-row");
                        }
                );
            });

            $j(document).ajaxStop(function () {
                $j('#agentTable').tablesorter({
                    widgets: ["zebra"]
                });
            });
        }
    });
}

var hydrateRow = function (agent) {
    var row = $j('tr#' + agent.id);
    row.find('td.percentage').text(agent.diskSpaceSummary.formattedPercentageUsed);
    row.find('td.freeSpace').text(agent.diskSpaceSummary.formattedFreeSpace)
            .append(usage(agent));

};


function rebuildButtonFor(agent) {
    if (agent.hasPendingRebuild) {
        return button(agent, 'cancelRebuild', 'Cancel');
    } else {
        return button(agent, 'rebuild', 'Rebuild');
    }
}

function cleanupButtonsFor(agent) {
    var span = $j('<span/>');
    var buildStatus = agent.runningBuildStatus;
    span.append(button(agent, 'cleanAppDirs', 'App Dirs', buildStatus != 'no-build').addClass(buildStatus));
    span.append(button(agent, 'cleanMavenRepo', 'Maven Repo', buildStatus != 'no-build').addClass(buildStatus));
    return span;
}

function button(agent, action, value, disabled) {
    var button = $j('<input/>', { type: 'submit', value: value});
    if (!disabled) {
        button.click(function () {
            doAction(action, agent.id)
        });
    }
    return button;
}

function commentTooltip(agent) {
    if (agent.statusComment) {
        return $j('<img/>', {class: 'commentIcon', src: '/img/commentIcon.gif', width: "11px", height: "11px"})
                .mouseover(function (event) {
                    $j('<div/>', {id: 'statusTooltip', class: 'statusTooltip', text: agent.statusComment})
                            .css({'position': 'absolute', 'top': event.pageY + 10, 'left': event.pageX + 18})
                            .appendTo('body');
                }).mouseout(function () {
                    setTimeout(function () {
                        $j('#statusTooltip').remove();
                    }, 250)
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