import Exceptions.UserAlreadyExists;
import Exceptions.UserDoesNotExists;
import Models.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;



import static org.junit.jupiter.api.Assertions.assertEquals;

public class test {

    private User utilizador;
    private BikeRentalSystem brs;
    private Deposit deposito;
    private Bike bicicleta;
    private Bike bicicleta2;

    @BeforeEach
    public void beforeEach() {
        Lock lock1 = new Lock(1);
        brs = new BikeRentalSystem(2);
        deposito = new Deposit(1);
        bicicleta = new Bike(1);
        bicicleta2 = new Bike(2);

        utilizador= new User(1, "Bruno", 1);

        brs.getUsers().add(utilizador);
        brs.getDeposits().add(deposito);

        brs.addBicycle(1, 1, 1);
        brs.addCredit(1, 10);
        brs.addLock(1, 1);
        brs.addLock(1, 2);
        utilizador.setBike(bicicleta);
    }

    //Método GetBike


    @Test
    public void t1() throws UserDoesNotExists {
        assertEquals(brs.getBicycle(1, 1, 1), 1, "Teste que verifica se o user existe");
    }

    @Test
    public void t2() {
        Throwable exception = assertThrows(UserDoesNotExists.class, () -> brs.getBicycle(1, 2, 1));
        assertEquals(null, exception.getMessage(), "Teste que verifica se o user nao existe");
    }

    @Test
    public void t3() throws UserDoesNotExists {
        assertEquals(brs.getBicycle(1, 1, 1), 1, "Teste que verifica se o deposito existe");
    }

    @Test
    public void t4() throws UserDoesNotExists {
        assertEquals(brs.getBicycle(4, 1, 1), -1, "Teste que verifica se o deposito nao existe");
    }

    @Test
    public void t5() throws UserDoesNotExists {
        assertEquals(brs.getBicycle(1, 1, 1), 1, "Teste que verifica se o credito existe");
    }

    @Test
    public void t6() throws UserDoesNotExists {
        brs.getUsers().get(0).setCredit(0);
        assertEquals(brs.getBicycle(1, 1, 1), -1, "Teste que verifica se o credito nao existe");
    }

    @Test
    public void t7() throws UserDoesNotExists {
        assertEquals(brs.getBicycle(1, 1, 1), 1, "Teste que verifica as bicicletas disponiveis e o utilizador não tem aluguer activo");
    }

    @Test
    public void t8() throws UserDoesNotExists {
        brs.getDeposits().get(0).getLocks().get(0).close();
        assertEquals(brs.getBicycle(1, 1, 1), 1, "Teste que verifica se nao existem bicicletas disponiveis");
    }

    //Método ReturnBike


    @Test
    public void t9() throws UserDoesNotExists {
        brs.getUsers().get(0).getBike().setInUSe(true);
        assertEquals(brs.returnBicycle(1, 1, 1), 2, "Teste que verifica se o user existe");
    }

    @Test
    public void t10() throws UserDoesNotExists {
        brs.getUsers().get(0).getBike().setInUSe(true);
        assertEquals(brs.returnBicycle(1, 6, 1), -1, "Teste que verifica se o user não existe");
    }

    @Test
    public void t11() throws UserDoesNotExists {
        brs.getUsers().get(0).getBike().setInUSe(true);
        assertEquals(brs.returnBicycle(1, 1, 1), 2, "Teste que verifica se o Depósito existe");
    }

    @Test
    public void t12() throws UserDoesNotExists {
        brs.getUsers().get(0).getBike().setInUSe(true);
        assertEquals(brs.returnBicycle(8, 1, 1), -1, "Teste que verifica se o Depósito não existe");
    }

    @Test
    public void t13() throws UserDoesNotExists {
        brs.getUsers().get(0).getBike().setInUSe(true);
        assertEquals(brs.returnBicycle(1, 1, 1), 2, "Teste que verifica se a bicicleta está associada a um lugar ativo");
    }

    @Test
    public void t14() throws UserDoesNotExists {
        brs.getUsers().get(0).getBike().setInUSe(false);
        assertEquals(brs.returnBicycle(1, 1, 1), -1, "Teste que verifica se a bicicleta não está associada a um lugar ativo");

    }

    @Test
    public void t15() {
        brs.getUsers().get(0).getBike().setInUSe(true);
        assertEquals(brs.returnBicycle(1, 1, 1), 2, "Teste para validar se existe no deposito lugares livres");
    }

    @Test
    public void t16() {
        brs.getUsers().get(0).getBike().setInUSe(true);
        brs.getDeposits().get(0).getLocks().get(1).setInUse(true);
        assertEquals(brs.returnBicycle(1, 1, 1), -1, "Teste para validar se não existe no deposito lugares livres");
    }


    //Método RentalFEE


    @Test
    public void t17(){
        assertEquals(brs.bicycleRentalFee(1,1,11,1),20,"Testar Rental Program = 1");
    }

    @Test
    public void t18(){
        assertEquals(brs.bicycleRentalFee(3,1,11,1),0,"Testar Rental Program != 1");
    }
    @Test
    public void t19(){
        assertEquals(brs.bicycleRentalFee(2,1,11,1),20,"Testar Rental Program = 2 && resto da divisão inteira do nRentals por 10 !=0\n" +
                "é !=0");
    }
    @Test
    public void t20(){
        assertEquals(brs.bicycleRentalFee(2,1,11,0),0,"Testar Rental Program = 2 && resto da divisão inteira do nRentals por 10 é = 0\n" +
                "é !=0");
    }

    @Test
    public void t21(){
        assertEquals(brs.bicycleRentalFee(2,5,10,1),10,"Testar se (endtime-startime) <=10");
    }
    @Test
    public void t22(){
        assertEquals(brs.bicycleRentalFee(2,1,12,1),22,"Testar se (endtime-startime) >10");
    }


    // Testar ADDCredit


    @Test
    public void t23(){

        assertEquals(brs.getUsers().get(0).getCredit(),10,"Testar se o Utilizador 1 tem crédito de 10 euros");
    }
    @Test
    public void t24(){

        assertEquals(brs.getUsers().get(1).getCredit(),0,"Testar se o Utilizador 2 (inexistente) tem crédito de 10 euros");
    }

    // Testar VerifyCredit

    @Test
    public void t25(){

        assertTrue(brs.verifyCredit(1),"Teste para verificar se user existe");
    }
    @Test
    public void t26(){

        assertFalse(brs.verifyCredit(2),"Teste para verificar se user não existe");
    }

    @Test
    public void t27(){
        brs.getUsers().get(0).setCredit(2);
        assertFalse(brs.verifyCredit(1), "Teste para validar se um utilizador existe e tem crédito >=1");
    }
    @Test
    public void t28(){
        brs.getUsers().get(0).setCredit(0.5F);
        assertFalse(brs.verifyCredit(1), "Teste para validar se um utilizador existe e tem crédito <1");
    }

    // Testar Registar user

    @Test
    public void t29() throws UserAlreadyExists {
        brs.registerUser(2,"Miguel",1);
        assertEquals(brs.getUsers().get(1).getIDUser(),2, "Teste para validar se o utilizador é registado com sucesso");

    }

    @Test
    public void t30() throws UserAlreadyExists{
        Throwable exception = assertThrows(UserAlreadyExists.class, () -> brs.registerUser(1,"Bruno",1));
        assertEquals(null, exception.getMessage(), "Teste para validar se o utilizador já existe");
    }


}
