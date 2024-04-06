package epf;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VehicleTest {

    @Mock
    private VehicleDao vehicleDao;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    public void create_cars_with_valid_data() throws ServiceException, DaoException {
        Vehicle vehicle = new Vehicle(1, "Nissan", "Plaza", 5);
        when(vehicleDao.create(any(Vehicle.class))).thenReturn(1L);

        long result = vehicleDao.create(vehicle);

        assertEquals(1, result);
        verify(vehicleDao, times(1)).create(vehicle);
    }

    @Test
    public void find_cars_by_id() throws ServiceException, DaoException {
        Vehicle vehicle = new Vehicle(1, "Nissan", "Plaza", 5);
        when(vehicleDao.findById(1L)).thenReturn(vehicle);

        Vehicle result = vehicleDao.findById(1);

        assertEquals(vehicle, result);
        verify(vehicleDao, times(1)).findById(1L);
    }

    @Test
    public void find_all_cars() throws ServiceException, DaoException {
        Vehicle vehicle = new Vehicle(1, "Nissan", "Plaza", 5);
        when(vehicleDao.findAll()).thenReturn(List.of(vehicle));

        List<Vehicle> result = vehicleDao.findAll();

        assertEquals(List.of(vehicle), result);
        verify(vehicleDao, times(1)).findAll();
    }

    @Test
    public void delete_cars() throws ServiceException, DaoException {
        Vehicle vehicle = new Vehicle(1L, "Nissan", "Plaza", 5);
        when(vehicleDao.delete(vehicle)).thenReturn(1L);

        long result = vehicleDao.delete(vehicle);

        assertEquals(result, 1L);
        verify(vehicleDao, times(1)).delete(vehicle);
    }

    @Test
    public void count_cars() throws ServiceException, DaoException {
        Vehicle vehicle = new Vehicle(0, "Nissan", "Plaza", 5);

        when(vehicleDao.count()).thenReturn(List.of(vehicle).size());
        Number result = vehicleService.count();

        assertEquals(result, 1);
        verify(vehicleDao, times(1)).count();
    }
}
