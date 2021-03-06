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

    $j("a.add-directory").click(function () {
        $j(".directories-input > ol.directory-list").append(agentDirectoryItem('', true));
    });
}

function updateContents(row, agentConfig) {
    row.empty();
    row.append($j('<td/>', {class: 'agent-name-pattern', text: agentConfig.agentNamePattern}))
        .append($j('<td/>', {class: 'free-space-threshold', text: agentConfig.freeSpaceThreshold}))
        .append($j('<td/>', {class: 'directories'}).html(directoryListFor(agentConfig.directories)))
        .append($j('<td/>', {class: 'edit'}).append($j('<a/>', {href: '#', text: 'edit'}).click(editRow)))
        .append($j('<td/>', {class: 'edit'}).append($j('<a/>', {href: '#', text: 'delete'}).click(deleteRow)))
    ;

}


function directoryListFrom(tdElement) {
    var dirs = [];
    $j(tdElement).find('.agent-directory > input').each(function (ind, element) {
        dirs.push($j(element).val());
    });
    return dirs;
}

function directoryListFor(directories, isEditable) {
    var section = $j('<ol/>', {class: 'directory-list'});

    directories.forEach(function (dir) {
        if (dir.length > 0) {
            section.append(agentDirectoryItem(dir, isEditable));
        }
    });

    return section;
}

function configFrom(configRow) {
    return {
        id: $j(configRow).attr('id'),
        agentNamePattern: $j(configRow).find('.agent-name-pattern').text(),
        freeSpaceThreshold: $j(configRow).find('.free-space-threshold').text(),
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

function editRow(eventData) {
    var configRow = $j(eventData.srcElement).parents('tr.config-item');
    ConfigDialog.showEditDialog(configFrom(configRow));
}

function deleteRow(eventData) {
    var configRow = $j(eventData.srcElement).parents('tr.config-item');
    configRow.remove();
}

function agentDirectoryItem(value, isEditable) {
    var inputElement = $j('<input/>', {type: 'text', value: value});
    if (!isEditable) {
        inputElement.attr('readonly', 'readonly')
    }

    return $j('<li/>', {class: 'agent-directory'})
        .append($j('<div/>', {class: 'handle'}))
        .append(inputElement);
}

ConfigDialog = OO.extend(BS.AbstractModalDialog, {
    beforeShow: function () {
        $j('div.directories-input > ol.directory-list').sortable({
            placeholder: "ui-state-highlight",
            forceHelperSize: true
        });
    },
    afterHide: function () {
        $j('div.directories-input > ol.directory-list').sortable("destroy")
    },
    getContainer: function () {
        return $('editConfig');
    },

    showEditDialog: function (agentConfig) {
        var dialogBody = $j('#editConfig > .modalDialogBody');
        dialogBody.find('input#agent-id').val(agentConfig.id)
        dialogBody.find('input#agent-name-pattern-input').val(agentConfig.agentNamePattern);
        dialogBody.find('input#free-space-threshold-input').val(agentConfig.freeSpaceThreshold);
        dialogBody.find('div.directories').html(directoryListFor(agentConfig.directories, true));

        this.showCentered();
    },

    showAddDialog: function () {
        ConfigDialog.showEditDialog({
            id: ConfigDialog.guid(),
            freeSpaceThreshold: -1,
            agentNamePattern: '',
            directories: []
        });
    },


    save: function () {
        var container = $j('#editConfig > .modalDialogBody')

        var agentConfig = {
            id: container.find('#agent-id').val(),
            agentNamePattern: container.find('#agent-name-pattern-input').val(),
            freeSpaceThreshold: container.find('#free-space-threshold-input').val(),
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