<div id="agentManagement">
    <input type="submit" onclick="doAction('disable')" value='Disable ALL'/>
    <input type="submit" onclick="doAction('enable')" value='Enable ALL'/>
</div>


<div id="agentList" class="refreshable">
    <div id="agentListInner" class="refreshableInner">
        <table cellspacing="0" class="agents dark sortable borderBottom">
            <thead>
            <tr>
                <th class="buildAgentName"><span id="SORT_BY_NAME" class="sortable">Agent Name</span></th>
                <th class="buildAgentStatus"><span>Status</span></th>
                <th class="percentage"><span id="SORT_BY_PERCENTAGE" class="sortable">Percentage Used</span></th>
                <th class="freeSpace"><span id="SORT_BY_FREE_SPACE" class="sortable">Free Space</span></th>
            </tr>
            </thead>
            <tbody id="agentTableBody"/>
        </table>
    </div>
</div>


<script type="text/javascript">
    $j(document).ready(prepareAgentList());
</script>
