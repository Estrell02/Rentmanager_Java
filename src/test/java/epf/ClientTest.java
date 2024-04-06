package epf;



import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ClientTest
{
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;

    @Test
    public void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.clientDao.findAll()).thenThrow(DaoException.class);

        // Then
        assertThrows(DaoException.class, () -> clientService.findAll());
    }

    @Test
    public void findAll_should_return_a_list_of_clients_when_dao_works() throws DaoException, ServiceException {

        List<Client> listClient = new ArrayList<>();
        // When
        when(this.clientService.findAll()).thenReturn(listClient);

        // Then
        assertEquals(listClient, clientService.findAll());
    }

    @Test
    public void create_client_should_return_a_client_id_when_dao_works() throws DaoException, ServiceException {
        // Given
        long id = 0;
        final Client client = new Client(-1,"esta", "shouki", "esta@gmail.com" ,LocalDate.now());
        // When
        when(this.clientService.create(client)).thenReturn(id);
        // Then
        assertEquals(id, clientService.create(client));
    }

    @Test
    public void findById_should_return_a_client_when_dao_works() throws DaoException, ServiceException {
        // Given
        long id = 2;
        // when
        when(this.clientService.findById(2)).thenReturn(
                new Client(2, "esta", "shouki", "esta@gmail.com", LocalDate.now()));
        // Then
        assertEquals(clientService.findById(2).id(), id);
    }

    @Test
    public void deleteClient_should_return_an_id_when_dao_works() throws DaoException, ServiceException {
        // Given
        long id = 0;
        // when
        when(this.clientService.create(new Client(id, "esta", "shouki", "esta@gmail.com", LocalDate.now()))).thenReturn(id);
        // Then
        assertEquals(clientService.delete(clientService.findById(id)), id);
    }

    @Test
    public void findById_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
        // When
        when(this.clientService.findById(1)).thenThrow(DaoException.class);

        // Then
        assertThrows(DaoException.class, () -> clientService.findById(1));
    }

}