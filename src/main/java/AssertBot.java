import org.junit.Assert;

public class AssertBot {

    public AssertBot() {

    }

    public void makeAssertion(String actual, String expected) {

        Assert.assertEquals(expected, actual);

    }

}
