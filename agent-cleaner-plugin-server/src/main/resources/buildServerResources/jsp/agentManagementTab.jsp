<div id="agentManagement">
    <input type="submit" onclick="doBulkAction('disable')" value='Disable ALL'/>
    <input type="submit" onclick="doBulkAction('enable')" value='Enable ALL'/>
</div>


<div>
    <table id="agentTable" cellspacing="0" class="dark sortable borderBottom">
        <thead>
        <tr>
            <th class="buildAgentName sortable"><span>Agent Name</span></th>
            <th class="buildAgentStatus sortable"><span>Status</span></th>
            <th class="uptime sortable"><span>Uptime</span></th>
            <th class="percentage sortable"><span>Percentage Used</span></th>
            <th class="freeSpace sortable"><span>Free Space</span></th>
            <th class="cleanup"><span>Cleanup</span></th>
            <th class="rebuild"><span>Rebuild</span></th>
        </tr>
        </thead>
        <tbody id="agentTableBody"/>
    </table>
</div>


<div id="tooltip-container"/>


<div id="directoryDialog" class="modalDialog">
    <div class="dialogHeader">
        <div class="closeWindow">
            <a class="closeWindowLink" title="Close dialog window" href="#"
               onclick="DirectoryDialog.close();; return false">&#xd7;</a>
        </div>
        <div class="dialogHandle">
            <h3 class="dialogTitle" id="editObjectFormTitle">Clean Directories</h3>
        </div>
    </div>
    <div class="modalDialogBody">
        <div>
            <table>
                <thead>
                <th>Directory Name</th>
                <th>Size</th>
                <th>Clean?</th>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
        <div class="saveButtonsBlock">
            <input id="agent-id" type="hidden">
            <a href="#" onclick="ConfigDialog.close(); return false" class="btn cancel">Cancel</a>
            <input class="btn btn_primary submitButton" type="submit" name="editObject" value="Clean"
                   onclick="DirectoryDialog.clean()">
        </div>

    </div>
</div>

<script type="text/javascript">
    $j(document).ready(prepareAgentList());
</script>
