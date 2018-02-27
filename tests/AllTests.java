import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import modeltests.DicePairImplTests;
import modeltests.SimplePlayerTests;

@RunWith(Suite.class)
@SuiteClasses({
	DicePairImplTests.class,
	SimplePlayerTests.class
})
public class AllTests {

}
