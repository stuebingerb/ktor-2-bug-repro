# Ktor 2 Bug Repro

Small repository to reproduce a bug in Ktor 2.0.2.

To reproduce:

```
mvn clean verify
```
```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running de.stuebingerb.ApplicationTest
Tests run: 1, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 1.929 sec <<< FAILURE!
testWithOauth(de.stuebingerb.ApplicationTest)  Time elapsed: 1.839 sec  <<< FAILURE!
java.lang.AssertionError: expected:<200 OK> but was:<500 Internal Server Error>
        at org.junit.Assert.fail(Assert.java:89)
        at org.junit.Assert.failNotEquals(Assert.java:835)
        at org.junit.Assert.assertEquals(Assert.java:120)
        at kotlin.test.junit.JUnitAsserter.assertEquals(JUnitSupport.kt:32)
        at kotlin.test.AssertionsKt__AssertionsKt.assertEquals(Assertions.kt:63)
        at kotlin.test.AssertionsKt.assertEquals(Unknown Source)
        at kotlin.test.AssertionsKt__AssertionsKt.assertEquals$default(Assertions.kt:62)
        at kotlin.test.AssertionsKt.assertEquals$default(Unknown Source)
        at de.stuebingerb.ApplicationTest$testWithOAuth$1.invokeSuspend(ApplicationTest.kt:123)
        at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
        at io.ktor.util.pipeline.SuspendFunctionGun.resumeRootWith(SuspendFunctionGun.kt:138)
        at io.ktor.util.pipeline.SuspendFunctionGun.loop(SuspendFunctionGun.kt:112)
        at io.ktor.util.pipeline.SuspendFunctionGun.access$loop(SuspendFunctionGun.kt:14)
        at io.ktor.util.pipeline.SuspendFunctionGun$continuation$1.resumeWith(SuspendFunctionGun.kt:62)
        at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:46)
        at io.ktor.util.pipeline.SuspendFunctionGun.resumeRootWith(SuspendFunctionGun.kt:138)
        at io.ktor.util.pipeline.SuspendFunctionGun.loop(SuspendFunctionGun.kt:112)
        at io.ktor.util.pipeline.SuspendFunctionGun.access$loop(SuspendFunctionGun.kt:14)
        at io.ktor.util.pipeline.SuspendFunctionGun$continuation$1.resumeWith(SuspendFunctionGun.kt:62)
        at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:46)
        at io.ktor.util.pipeline.SuspendFunctionGun.resumeRootWith(SuspendFunctionGun.kt:138)
        at io.ktor.util.pipeline.SuspendFunctionGun.loop(SuspendFunctionGun.kt:112)
        at io.ktor.util.pipeline.SuspendFunctionGun.access$loop(SuspendFunctionGun.kt:14)
        at io.ktor.util.pipeline.SuspendFunctionGun$continuation$1.resumeWith(SuspendFunctionGun.kt:62)
        at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:46)
        at io.ktor.util.pipeline.SuspendFunctionGun.resumeRootWith(SuspendFunctionGun.kt:138)
        at io.ktor.util.pipeline.SuspendFunctionGun.loop(SuspendFunctionGun.kt:112)
        at io.ktor.util.pipeline.SuspendFunctionGun.access$loop(SuspendFunctionGun.kt:14)
        at io.ktor.util.pipeline.SuspendFunctionGun$continuation$1.resumeWith(SuspendFunctionGun.kt:62)
        at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:46)
        at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:106)
        at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:279)
        at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:85)
        at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:59)
        at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
        at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:38)
        at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
        at io.ktor.server.testing.TestApplicationKt.testApplication(TestApplication.kt:264)
        at de.stuebingerb.ApplicationTest.testWithOAuth(ApplicationTest.kt:33)
        at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
        at java.base/java.lang.reflect.Method.invoke(Method.java:577)
        at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:59)
        at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
        at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:56)
        at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
        at org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)
        at org.junit.runners.BlockJUnit4ClassRunner$1.evaluate(BlockJUnit4ClassRunner.java:100)
        at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:366)
        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:103)
        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:63)
        at org.junit.runners.ParentRunner$4.run(ParentRunner.java:331)
        at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:79)
        at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:329)
        at org.junit.runners.ParentRunner.access$100(ParentRunner.java:66)
        at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:293)
        at org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)
        at org.junit.runners.ParentRunner.run(ParentRunner.java:413)
        at org.apache.maven.surefire.junit4.JUnit4Provider.execute(JUnit4Provider.java:252)
        at org.apache.maven.surefire.junit4.JUnit4Provider.executeTestSet(JUnit4Provider.java:141)
        at org.apache.maven.surefire.junit4.JUnit4Provider.invoke(JUnit4Provider.java:112)
        at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
        at java.base/java.lang.reflect.Method.invoke(Method.java:577)
        at org.apache.maven.surefire.util.ReflectionUtils.invokeMethodWithArray(ReflectionUtils.java:189)
        at org.apache.maven.surefire.booter.ProviderFactory$ProviderProxy.invoke(ProviderFactory.java:165)
        at org.apache.maven.surefire.booter.ProviderFactory.invokeProvider(ProviderFactory.java:85)
        at org.apache.maven.surefire.booter.ForkedBooter.runSuitesInProcess(ForkedBooter.java:115)
        at org.apache.maven.surefire.booter.ForkedBooter.main(ForkedBooter.java:75)


Results :

Failed tests:   testWithOAuth(de.stuebingerb.ApplicationTest): expected:<200 OK> but was:<500 Internal Server Error>

Tests run: 1, Failures: 1, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
```
