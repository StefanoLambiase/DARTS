package test;

public class HardCodedTestDataNotPresent {
    private Example1 example1 = new Example1();

    @Test
    public void getNome() {
        String nome = "Sal";
        example1.setNome(nome);
        double reddito = 33.4;
        example1.setReddito(reddito);
        assertEquals(nome, example1.getNome());
    }
}
