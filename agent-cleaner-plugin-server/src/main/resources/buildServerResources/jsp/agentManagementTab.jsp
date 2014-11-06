<div id="agentManagement">
    <input type="submit" onclick="doBulkAction('disable')" value='Disable ALL'/>
    <input type="submit" onclick="doBulkAction('enable')" value='Enable ALL'/>
</div>


<div id="agentList" class="refreshable">
    <div id="agentListInner" class="refreshableInner">
        <table id="agentTable" cellspacing="0" class="agents dark sortable borderBottom">
            <thead>
            <tr>
                <th class="buildAgentName"><span>Agent Name</span></th>
                <th class="buildAgentStatus"><span>Status</span></th>
                <th class="uptime"><span>Uptime</span></th>
                <th class="percentage"><span>Percentage Used</span></th>
                <th class="freeSpace"><span>Free Space</span></th>
                <th class="cleanup"><span>Cleanup</span></th>
                <th class="rebuild"><span>Rebuild</span></th>
            </tr>
            </thead>
            <tbody id="agentTableBody"/>
        </table>
    </div>
</div>


<div id="tooltip-container"/>

<script type="text/javascript">
    $j(document).ready(prepareAgentList());
</script>
