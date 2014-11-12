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

function getAgentDetail(agent) {
    $j.ajax({
        type: 'GET',
        url: '/agentManagement/agentDetail/',
        datatype: 'json',
        data: {agentId: agent.id},
        success: function (agent) {
            hydrateRow(agent);
            setTimeout(function () {
                getAgentDetail(agent)
            }, 5000);
        }
    });
}

function doAction(action, agentId) {
    $j.ajax({
                type: 'POST',
                url: '/agentManagement/action/',
                data: {'action': action, 'agentId': agentId},
                success: hydrateRow
            }
    );
}

function updateText(cell, text) {
    var ret = cell.text(text);
    $j('#agentTable').trigger('updateCell', [cell, false]);
    return ret;
}

function updateHtml(cell, html) {
    var ret = cell.html(html);
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

                $j('<td/>', {class: 'build-agent-name'})
                        .append(buildAgentName(agent))
                        .appendTo(row);

                $j('<td/>', {class: 'build-agent-status'})
                        .append(buildAgentStatus(agent))
                        .appendTo(row);

                $j('<td/>', {class: 'uptime sort-data', text: agent.formattedUptime})
                        .appendTo(row);

                $j('<td/>', {class: 'percentage sort-data', text: '--'})
                        .appendTo(row);

                $j('<td/>', {class: 'free-space sort-data', text: '--'})
                        .appendTo(row);

                $j('<td/>', {class: 'cleanup'})
                        .html(cleanupButtonsFor(agent))
                        .appendTo(row);

                $j('<td/>', {class: 'rebuild'})
                        .html(rebuildButtonFor(agent))
                        .appendTo(row);

                row.hover(
                        function () {
                            row.addClass("current-row");
                        }, function () {
                            row.removeClass("current-row");
                        }
                );

                getAgentDetail(agent);
            });

            $j('#agentTable').tablesorter({
                widgets: ["zebra"],
                textExtraction: function (rawNode) {
                    var node = $j(rawNode);
                    if (node.hasClass('sort-data')) {
                        return node.text();
                    } else {
                        return node.find('.sort-data').text();
                    }
                }

            });
        }
    });
}

var hydrateRow = function (agent) {
    var row = $j('tr#' + agent.id);
    updateHtml(row.find('td.build-agent-name'), buildAgentName(agent));
    updateHtml(row.find('td.build-agent-status'), buildAgentStatus(agent));
    updateText(row.find('td.uptime'), agent.formattedUptime);
    updateText(row.find('td.percentage'), agent.diskSpaceSummary.formattedPercentageUsed);
    updateHtml(row.find('td.free-space'), usageFor(agent));
    updateHtml(row.find('td.cleanup'), cleanupButtonsFor(agent));
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
    var button = $j('<input/>', {type: 'submit', value: value});
    if (!disabled) {
        button.click(function () {
            doAction(action, agent.id)
        });
    }
    return button;
}

function buildAgentName(agent) {
    var agentNameElement = $j('<div/>', {class: agent.runningBuildStatus}).append($j('<a/>', {href: '/agentDetails.html?id=' + agent.id, class: 'sort-data', text: agent.name}));

    if (agent.buildName) {
        return attachTooltip(agentNameElement, $j('<span/>', {text: agent.buildName}));
    }

    return agentNameElement;
}

function buildAgentStatus(agent) {
    var statusElement = $j('<div/>', {class: 'build-agent-status  sort-data ' + agent.status.toLowerCase(), text: agent.status});

    if (agent.statusComment) {
        return statusElement.append(attachTooltip($j('<img/>', {class: 'commentIcon', src: '/img/commentIcon.gif', width: "11px", height: "11px"}),
                $j('<span/>', {text: agent.statusComment})
        ));
    }

    return statusElement;
}

function usageFor(agent) {
    return $j('<div/>', {class: 'sort-data', text: agent.diskSpaceSummary.formattedFreeSpace})
            .append(attachTooltip($j('<img/>', {class: 'commentIcon', src: '/img/help.gif', width: "11px", height: "11px"}),
                    $j('<table/>', {class: 'usage-table'})
                            .html(rowsFor(agent.diskUsage))
            )
    );
}

function rowsFor(usage) {
    var html = "";
    usage.fileSummaries.forEach(function (summary) {
        html += "<tr><td class='usage-path'>" + summary.path + "</td><td class='usage-size'>" + summary.formattedSize + "</td></tr>";
    });
    return html;
}

function attachTooltip(triggerElement, tooltipElement) {
    return triggerElement
            .mouseover(function (event) {
                $j('#tooltip-container').html(
                        $j('<div/>', {class: 'tooltip'})
                                .css({'position': 'absolute', 'top': event.pageY + 10, 'left': event.pageX + 18})
                                .append(tooltipElement)
                );
            })
            .mouseout(function () {
                $j('#tooltip-container').empty();
            }
    );
}