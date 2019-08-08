# 基于Groovy的Spock测试框架


## spock和junit 的对照
Spock|Junit
:--|:--
Specification|Test class
setup()|@Before
cleanup()|@After
setupspec()|@Beforeclass
cleanupsepc()|@Afterclass
Feature|Test
Feature method|Test method
Data-driven feature|Theory
Condition|Assertion
Exception condition|@Test(expectd=…)
Interaction|Mock expectation