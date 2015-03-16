function prepareConfigList() {
    var listHolder = $j('#sortable-container');

    $j.ajax({
            type: 'GET',
            url: '/agentManagement/config/',
            dataType: 'json',
            success: function (agentConfigurations) {
                agentConfigurations.forEach(
                    function (agentConfig) {
                        var row = $j('<tr/>', {class: 'ui-state-default config-item', id: agentConfig.id});

                        updateContents(row, agentConfig);

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

    $j("#add-directory").click(function () {
        $j(".directories-input > ol.directory-list").append(agentDirectoryItem(''));
    });

}

function updateContents(row, agentConfig) {
    row.empty();
    row.append($j('<td/>', {class: 'agent-name-pattern', text: agentConfig.agentNamePattern}))
        .append($j('<td/>', {class: 'free-space-threshold', text: agentConfig.freeSpaceThreshold}))
        .append($j('<td/>', {class: 'directories'}).html(directoryListFor(agentConfig.directories)))
        .append($j('<td/>', {class: 'edit', text: 'edit'}).click(editRow))
        .append($j('<td/>', {class: 'edit', text: 'delete'}).click(deleteRow))
    ;

}


function directoryListFrom(tdElement) {
    var dirs = [];
    $j(tdElement).find('.agent-directory > input').each(function (ind, element) {
        dirs.push($j(element).val());
    });
    return dirs;
}

function directoryListFor(directories) {
    var section = $j('<ol/>', {class: 'directory-list'});

    directories.forEach(function (dir) {
        if (dir.length > 0) {
            section.append(agentDirectoryItem(dir));
        }
    });

    return section;
}

function configFrom(configRow) {
    return {
        id: $j(configRow).attr('id'),
        agentNamePattern: $j(configRow).find('.agent-name-pattern').text(),
        directories: directoryListFrom($j(configRow).find('td.directories'))
    };
}


function save() {
    var data = {agentConfigurations: []};

    $j('#sortable-container > tr.config-item').each(function (index, element) {
        data.agentConfigurations.push(configFrom(element));
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

function editRow() {
    var configRow = $j(this).parent('tr.config-item');
    ConfigDialog.showEditDialog(configFrom(configRow));
}

function deleteRow() {
    var configRow = $j(this).parent('tr.config-item');
    configRow.remove();
}

function agentDirectoryItem(value) {
    return $j('<li/>', {class: 'agent-directory'})
        .append($j('<div/>', {class: 'handle'}))
        .append($j('<input/>', {type: 'text', value: value}));
}

ConfigDialog = OO.extend(BS.AbstractModalDialog, {
    getContainer: function () {
        return $('editConfig');
    },

    showEditDialog: function (agentConfig) {
        var dialogBody = $j('#editConfig > .modalDialogBody');
        dialogBody.find('input.agent-id').val(agentConfig.id)
        dialogBody.find('input.agent-name-pattern').val(agentConfig.agentNamePattern);
        dialogBody.find('input.free-space-threshold').val(agentConfig.freeSpaceThreshold);
        dialogBody.find('div.directories').html(directoryListFor(agentConfig.directories));

        this.showCentered();
    },

    showAddDialog: function () {
        ConfigDialog.showEditDialog({id: ConfigDialog.guid(), agentNamePattern: '', directories: []});
    },


    save: function () {
        var container = $j('#editConfig > .modalDialogBody')

        var agentConfig = {
            id: container.find('input.agent-id').val(),
            agentNamePattern: container.find('input.agent-name-pattern').val(),
            freeSpaceThreshold: container.find('input.free-space-threshold').val(),
            directories: directoryListFrom(container.find('div.directories'))
        };

        var row = $j('#' + agentConfig.id);
        if (!row.length) {
            var newRow = $j('<tr/>', {class: 'ui-state-default config-item', id: agentConfig.id});
            newRow.appendTo($j('#sortable-container'));
            updateContents(newRow, agentConfig)
        } else {
            updateContents(row, agentConfig);
        }
        save();

        this.close();
    },

    guid: function () {
        function s4() {
            return Math.floor((1 + Math.random()) * 0x10000)
                .toString(16)
                .substring(1);
        }

        return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
            s4() + '-' + s4() + s4() + s4();
    }
});