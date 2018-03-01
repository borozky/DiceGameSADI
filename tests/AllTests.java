import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import modeltests.DicePairImplTests;
import modeltests.GameEngineImplTests;
import modeltests.SimplePlayerTests;

@RunWith(Suite.class)
@SuiteClasses({
	SimplePlayerTests.class,
	GameEngineImplTests.class,
	DicePairImplTests.class,
})
public class AllTests {
	
}
