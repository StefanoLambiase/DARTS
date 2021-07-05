package test;

public class TestCodeDuplicationNotPresent {
    private Example1 example1 = new Example1();

    @Test
    public void setNome() throws Exception {
        File file = new File("/Users/salvatorefasano/IdeaProjects/TestDarts/src/main/test.txt");
        String nome = "";
        boolean cond = true;
        boolean ciao1 = true;
        boolean ciao2 = false;
        if (ciao1) {
            nome = "Ciao1";
            System.out.println(nome);
        }
        while (cond) {
            System.out.println("Ciao Sono Alessia");
            nome = "Alessia";
            nome = "Salvatore";
            System.out.println("Ciao Sono Salvatore");
        }
        example1.setNome(nome);
        assertEquals(nome, example1.getNome());
    }
}
