package test;

import main.EagerTestPresent;

public class EagerTestPresentTest {

  @Test
  public void test1() {
      new EagerTestPresent().doSomething(1);
      new EagerTestPresent().doSomething(1);
      new EagerTestPresent().doSomething2(22);
      new EagerTestPresent().doSomething2(22);
  }

  @Test
  public void test2() {
      new EagerTestPresent().doSomething(2);
  }

   @Test
  public void test3(){
      new EagerTestPresent().doSomething(22);
  }

}
