package hellocucumber.runner;

import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("hellocucumber")
@IncludeTags("SmokeTest")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "hellocucumber.ui_steps_definition")
public class RunSmokeTest {
}

