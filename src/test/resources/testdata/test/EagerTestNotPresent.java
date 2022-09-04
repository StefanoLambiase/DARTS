package test;

import main.EagerTestNotPresent;

public class EagerTestNotPresentTest {

  @Test
  public void test1() {
      new EagerTestNotPresent().doSomething(1);
  }

  @Test
  public void test2() {
      new EagerTestNotPresent().doSomething(2);
  }

   @Test
  public void test3(){
      new EagerTestNotPresent().doSomething(22);
  }

}
