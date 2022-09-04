package test;

import main.LackOfCohesionOfTestSmell;

import java.io.File;

public class LackOfCohesionOfTestSmellPresentTest {
    private LackOfCohesionOfTestSmell example1 = new LackOfCohesionOfTestSmell();

    @Test
    public void getNome() {
        example1.setNome("Sal");
        example1.setReddito(33.4);
        assertEquals("Sal", example1.getNome());
    }
}
