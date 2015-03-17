<table class="config-table dark">
    <thead>
    <tr>
        <th>Agent Name Pattern</th>
        <th>Free Space Threshold (Mb)</th>
        <th>Deletable Directories</th>
        <th colspan="2">Actions</th>
    </tr>
    </thead>
    <tbody id="sortable-container">
    </tbody>
    <p>
        <a class="btn" href="#" onclick="ConfigDialog.showAddDialog(); return false">
            <span class="addNew">Create new Agent Configuration</span>
        </a>
    </p>
</table>

<div id="editConfig" class="modalDialog">
    <div class="dialogHeader">
        <div class="closeWindow">
            <a class="closeWindowLink" title="Close dialog window" href="#"
               onclick="ConfigDialog.close();; return false">&#xd7;</a>
        </div>
        <div class="dialogHandle">
            <h3 class="dialogTitle" id="editObjectFormTitle">Agent Configuration</h3>
        </div>
    </div>
    <div class="modalDialogBody">
        <table class="agent-configuration">
            <tr class="agent-name-pattern">
                <td>
                    <label for="agent-name-pattern-input">Agent Pattern Name</label>
                </td>
                <td>
                    <input type="text" id="agent-name-pattern-input"/>
                </td>
            </tr>
            <tr class="free-space-threshold">
                <td>
                    <label for="free-space-threshold-input">Free Space Threshold (Mb)</label>
                </td>
                <td>
                    <input type="text" id="free-space-threshold-input"/>
                </td>
            </tr>
            <tr class="directories">
                <td>
                    <label for="agent-name-pattern-input">Deletable Directories</label>

                    <a class="btn add-directory" href="#">
                        <span class="addNew">Add Directory</span>
                    </a>
                </td>
                <td>
                    <div class="directories directories-input"></div>
                </td>
            </tr>
        </table>

        <div class="saveButtonsBlock">
            <input id="agent-id" type="hidden">
            <a href="#" onclick="ConfigDialog.close(); return false" class="btn cancel">Cancel</a>
            <input class="btn btn_primary submitButton" type="submit" name="editObject" value="Save"
                   onclick="ConfigDialog.save()">
        </div>

    </div>
</div>


<script type="text/javascript">
    $j(document).ready(prepareConfigList());
</script>