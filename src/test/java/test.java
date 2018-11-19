import Exceptions.UserAlreadyExists;
import Exceptions.UserDoesNotExists;
import Models.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;



import static org.junit.jupiter.api.Assertions.assertEquals;

public class test {

    private User utilizador;
    private BikeRentalSystem bikeRentalSystem;
    private Deposit deposito;
    private Bike bicicleta;

    @BeforeEach
    public void beforeEach() {
        Lock lock1 = new Lock(1);
        BikeRentalSystem brs = new BikeRentalSystem(1);
        Deposit d1 = new Deposit(1);

        User u1 = new User(1, "Bruno", 2);

        brs.getUsers().add(u1);
        brs.getDeposits().add(d1);

        brs.addBicycle(1,1,1);
        brs.addCredit(1,1);
        brs.addLock(1, 1);
    }

    @Test
    public void t1() throws UserDoesNotExists {
        assertEquals(bikeRentalSystem.getBicycle(1, 1, 1), 1, "Teste que verifica se o user existe");
    }

    @Test
    public void t2() {
        Throwable exception = assertThrows(UserDoesNotExists.class, () -> bikeRentalSystem.getBicycle(1,2,1));
        assertEquals(null, exception.getMessage(), "Teste que verifica se o user nao existe");
    }

    @Test
    public void t3() throws UserDoesNotExists{
        assertEquals(bikeRentalSystem.getBicycle(1, 1, 1), 1, "Teste que verifica se o deposito existe");
    }

    @Test
    public void t4() throws UserDoesNotExists{
        assertEquals(bikeRentalSystem.getBicycle(4,1,1),-1, "Teste que verifica se o deposito nao existe");
    }

    @Test
    public void t5() throws UserDoesNotExists{
        assertEquals(bikeRentalSystem.getBicycle(1,1,1), 1, "Teste que verifica se o credito existe");
    }

    @Test
    public void t6() throws UserDoesNotExists{
        bikeRentalSystem.getUsers().get(0).setCredit(0);
        assertEquals(bikeRentalSystem.getBicycle(1,1,1), -1, "Teste que verifica se o credito nao existe");
    }

    @Test
    public void t7() throws UserDoesNotExists{
        assertEquals(bikeRentalSystem.getBicycle(1,1,1), 1, "Teste que verifica as bicicletas disponiveis e o utilizador não tem aluguer activo");
    }

    @Test
    public void t8() throws UserDoesNotExists{
        bikeRentalSystem.getDeposits().get(0).getLocks().get(0).close();
        assertEquals(bikeRentalSystem.getBicycle(1,1,1), -1, "Teste que verifica se nao existem bicicletas disponiveis");
    }

}
