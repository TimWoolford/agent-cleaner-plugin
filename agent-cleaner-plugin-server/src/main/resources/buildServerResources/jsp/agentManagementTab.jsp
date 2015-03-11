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

<script type="text/javascript">
    $j(document).ready(prepareAgentList());
</script>
