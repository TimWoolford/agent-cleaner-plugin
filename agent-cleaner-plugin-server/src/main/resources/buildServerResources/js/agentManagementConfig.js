function prepareConfigList() {
    var listHolder = $j('#config-list')

    $j.ajax({
            type: 'GET',
            url: '/agentManagement/config/',
            dataType: 'json',
            success: function (agentDirectories) {
                agentDirectories.forEach(
                    function (agentDirectory) {
                        var listItem = $j('<li/>', {class: 'config-item', text: agentDirectory.agentNamePattern})
                            .append($j('<span/>', {class: 'agent-pattern-name'}))
                            .append($j('<span/>', {class: 'directories'}).html(directoryListFor(agentDirectory.directories)));

                        listItem.appendTo(listHolder);
                    });
            }
        }
    );
}


function directoryListFor(directories) {
    var section = $j('<div/>');

    directories.forEach(function (dir) {
        section.append($j('<li/>', {class: 'agent-directory', text: dir}));
    });

    return section;
}