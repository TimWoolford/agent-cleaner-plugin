package sns.teamcity.model;

import jetbrains.buildServer.serverSide.comments.Comment;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AgentInfoTest {

    @Test
    public void displaysUptimeOfADay() throws Exception {
        AgentInfo agentInfo = agentInfo(DateUtils.addDays(new Date(), -1));

        assertThat(agentInfo.getFormattedUptime(), is("1 day 0 hours"));
    }

    @Test
    public void displaysUptimeOfAnHour() throws Exception {
        AgentInfo agentInfo = agentInfo(DateUtils.addHours(new Date(), -1));

        assertThat(agentInfo.getFormattedUptime(), is("0 days 1 hour"));
    }

    private AgentInfo agentInfo(Date registrationDate) {
        return new AgentInfo(1, "name", null, true, new MyComment("comment"), registrationDate, false);
    }


    private static class MyComment extends Comment {
        public MyComment(String comment) {
            super(1, null, comment, new Date());
        }

        @Override
        public boolean save() {
            throw new UnsupportedOperationException("Method save() not yet implemented");
        }

        @Override
        public void delete() {
            throw new UnsupportedOperationException("Method delete() not yet implemented");
        }
    }
}