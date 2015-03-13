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
                            .append($j('<td/>', {class: 'agent-pattern-name', text: agentDirectory.agentNamePattern}))
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
        forceHelperSize: true
    });

    $j("#sortable-container").disableSelection();

}


function directoryListFor(directories) {
    var section = $j('<ol/>', {class: 'directory-list'});

    directories.forEach(function (dir) {
        section.append($j('<li/>', {class: 'agent-directory', text: dir}));
    });

    return section;
}