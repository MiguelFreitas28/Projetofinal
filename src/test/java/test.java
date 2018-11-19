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
        brs = new BikeRentalSystem(1);
        deposito = new Deposit(1);
        bicicleta = new Bike(1);
        bicicleta2 = new Bike(2);

        utilizador= new User(1, "Bruno", 2);

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
        assertEquals(brs.getBicycle(1, 1, 1), -1, "Teste que verifica se nao existem bicicletas disponiveis");
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



}
