function prepareConfigList() {
    var listHolder = $j('#sortable-container')

    $j.ajax({
                type: 'GET',
                url: '/agentManagement/config/',
                dataType: 'json',
                success: function (agentDirectories) {
                    agentDirectories.forEach(
                            function (agentDirectory) {
                                var row = $j('<tr/>', {class: 'ui-state-default config-item'})
                                                .append($j('<td/>', {class: 'agent-name-pattern', text: agentDirectory.agentNamePattern}))
                                                .append($j('<td/>', {class: 'directories'}).html(directoryListFor(agentDirectory.directories)))
                                                .append($j('<td/>', {class: 'edit', text: 'edit'}).click())
                                                .append($j('<td/>', {class: 'edit', text: 'delete'}))
                                        ;

                                row.appendTo(listHolder);
                            });
                }
            }
    );

    $j("#sortable-container").sortable({
        placeholder: "ui-state-highlight",
        forceHelperSize: true,
        update: save
    });

    $j("#sortable-container").disableSelection();

}

function save() {
    var data = {agentDirectories: []};

    $j('#sortable-container > tr.config-item').each(function (index, element) {
        data.agentDirectories.push({
            agentNamePattern: $j(element).find('td.agent-name-pattern').text(),
            directories: directoryListFrom($j(element).find('td.directories'))
        });
    });

    $j.ajax({
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        url: '/agentManagement/config/',
        processData: false,
        data: JSON.stringify(data)
    })
}

function directoryListFrom(tdElement) {
    var dirs = [];
    $j(tdElement).find('li.agent-directory').each(function (ind, element) {
        dirs.push($j(element).text());
    });
    return dirs;
}

function directoryListFor(directories) {
    var section = $j('<ol/>', {class: 'directory-list'});

    directories.forEach(function (dir) {
        section.append($j('<li/>', {class: 'agent-directory', text: dir}));
    });

    return section;
}