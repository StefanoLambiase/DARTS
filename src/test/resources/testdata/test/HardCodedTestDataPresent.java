package test;

public class HardCodedTestDataPresent {
    private Example1 example1 = new Example1();

    @Test
    public void getNome() {
        example1.setNome("Sal");
        example1.setReddito(33.4);
        assertEquals("Sal", example1.getNome());
    }
}
