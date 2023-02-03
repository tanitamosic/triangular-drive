package com.NWT_KTS_project.reservationTests;

import com.NWT_KTS_project.controllers.ClientController;
import com.NWT_KTS_project.model.*;
import com.NWT_KTS_project.model.enums.*;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.repository.AddressRepository;
import com.NWT_KTS_project.repository.ReservationRepository;
import com.NWT_KTS_project.repository.RideRepository;
import com.NWT_KTS_project.repository.RouteRepository;
import com.NWT_KTS_project.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class ReservationClientTest {
    @Mock
    ReservationRepository reservationRepository;

    @Mock
    AddressRepository addressRepository;

    @Mock
    RouteRepository routeRepository;
    @Mock
    RideRepository rideRepository;

    @Mock
    AddressService addressService;

    @Mock
    UserService userService;

    @Mock
    DriverService driverService;

    @Mock
    PaymentService paymentService;

    @Mock
    RideService rideServiceMock;

    Integer RIDE_ID = 25;
    Integer DRIVER_ID = 10;

    Integer PASSENGER_ID = 5;
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    Ride ride;
    @InjectMocks
    RideService rideService;
    @InjectMocks
    private ClientController clientController;


    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Client passenger = createMockClient();
        Client blocked = createMockBlockedClient();
        Car c = createMockCar();
        Driver d = createMockDriver(c);

        Address a1 = createMockAddress("Maksima gorkog", "11", 1, 40.0, 19.0);
        Address a2 = createMockAddress("Bulevar despota Stefana", "7", 2, 39.9, 18.0);
        Address a3 = createMockAddress("Bulevar evrope", "15", 3, 39.0, 20.0);
        List<Address> stops = new ArrayList<>();
        stops.add(a2);
        Route route1 = createMockRoute(a1, a3, stops);
        Ride r1 = createMockRide(d, passenger, route1, RideStatus.ONGOING);
        ride = r1;

        LocalDateTime time = LocalDateTime.of(2023,02,03,19,52,40);

        Reservation res = new Reservation();
        res.setRide(ride);
        res.setTime(time);

        ArrayList<Address> addresses = new ArrayList<Address>();
        addresses.add(a1);
        addresses.add(a2);
        addresses.add(a3);
        when(addressService.getAddressesFromString("test")).thenReturn(addresses);
        when(addressService.getAddressesFromString(null)).thenReturn(null);

        ArrayList<Client> passengers = new ArrayList<Client>();
        passengers.add(passenger);
        when(userService.getClientsFromPassangersString("test")).thenReturn(passengers);
        when(paymentService.processPaymentForRide(passengers,null,d.getCar())).thenReturn(true);

        ArrayList<Client> passengers1 = new ArrayList<Client>();
        passengers1.add(blocked);
        when(userService.getClientsFromPassangersString("failOnPayment")).thenReturn(passengers1);
        when(paymentService.processPaymentForRide(passengers1,null,d.getCar())).thenReturn(false);

        //when(userService.getClientsFromPassangersString("failPassengers")).thenThrow(new Exception());
        when(userService.getUserById(PASSENGER_ID)).thenReturn(passenger);
        when(userService.getUserById(18)).thenReturn(blocked);

        when(driverService.reserveDriver(false,false,
                1,CarType.STANDARD)).thenReturn(d);
        when(driverService.reserveDriver(true,true,
                1,CarType.STANDARD)).thenReturn(null);

        when(rideServiceMock.makeReservation(d,null,passengers,
                RideStatus.RESERVED,time,"test")).thenReturn(res);

        when(rideService.createRide(d,addresses,passengers,RideStatus.RESERVED,
                "100.0")).thenReturn(ride);
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        reservations.add(res);
        when(reservationRepository.findByDriver(DRIVER_ID)).thenReturn(reservations);
        when(reservationRepository.findByDriver(0)).thenReturn(null);

    }
    @Test
    public void shouldCreateReservation() throws Exception {
        int result = clientController.makeReservation(PASSENGER_ID,null,"test", false,
                false,CarType.STANDARD,"2023-02-03T19:52:40.530Z","test");
        Assert.assertEquals(result,RIDE_ID);
    }

    @Test void shouldFailOnBlocked(){
        int result = clientController.makeReservation(18,null,"test", false,
                false,CarType.STANDARD,"2023-02-03T19:52:40.530Z","test");
        Assert.assertEquals(result, -4);
    }

    @Test void shouldFailOnPayment(){
        int result = clientController.makeReservation(PASSENGER_ID,null,"failOnPayment", false,
                false,CarType.STANDARD,"2023-02-03T19:52:40.530Z","test");
        Assert.assertEquals(result, -2);
    }

    @Test
    public void shouldCreateReservationInService(){
        ArrayList<Client> passengers = userService.getClientsFromPassangersString("test");
        Car c = createMockCar();
        Driver d = createMockDriver(c);
        ArrayList<Address> addresses = addressService.getAddressesFromString("test");
        LocalDateTime time = LocalDateTime.of(2023,02,03,18,52,40);
        Reservation res = rideService.makeReservation(d,addresses,passengers,RideStatus.RESERVED,time,"100.0");
        Assert.assertEquals(res.getRide().getId(),0);

    }

    @Test
    public void shouldReturnOneRide(){
        List<Ride> reservedRides = rideService.getDriverReservedRide(DRIVER_ID);
        Assert.assertEquals(reservedRides.size(),1);
    }

    @NotNull
    private Address createMockAddress(String street, String number, Integer id, Double lat, Double lon) {
        Address a = new Address();
        a.setCity(City.NS);
        a.setStreet(street);
        a.setNumber(number);
        a.setId(id);
        a.setLatitude(lat);
        a.setLongitude(lon);
        return a;
    }
    @NotNull
    private Route createMockRoute(Address start, Address finish, List<Address> stops) {
        Route r = new Route();
        r.setStart(start);
        r.setDestination(finish);
        r.setStops(stops);
        r.setId(2);
        r.setDistance(14f);
        return r;
    }
    @NotNull
    private Client createMockClient() {
        Client c = new Client();
        c.setPhone("12345688");
        c.setPhoto(null);
        c.setName("ClientName");
        c.setLastName("ClientLastName");
        c.setId(5);
        c.setCreditAvailable(500.0f);
        c.setSavedRoutes(new ArrayList<>());
        c.setBlocked(false);
        c.setActivated(true);
        c.setPassword("encryptedPassword");
        c.setCity(City.NS);
        c.setEmail("test.client@email.com");
        return c;
    }

    @NotNull
    private Client createMockBlockedClient() {
        Client c = new Client();
        c.setPhone("12345688");
        c.setPhoto(null);
        c.setName("ClientName");
        c.setLastName("ClientLastName");
        c.setId(18);
        c.setCreditAvailable(500.0f);
        c.setSavedRoutes(new ArrayList<>());
        c.setBlocked(true);
        c.setActivated(true);
        c.setPassword("encryptedPassword");
        c.setCity(City.NS);
        c.setEmail("test.client@email.com");
        return c;
    }
    @NotNull
    private Car createMockCar() {
        Car c = new Car();
        c.setId(10);
        c.setMake(CarMake.BUGATTI);
        c.setModel("507V");
        c.setColor(Color.BLUE);
        c.setType(CarType.STANDARD);
        c.setSeats(4);
        c.setBabyFriendly(true);
        c.setPetFriendly(true);
        return c;
    }
    @NotNull
    private Driver createMockDriver(Car c) {
        Driver d = new Driver();
        d.setPhone("123456789");
        d.setPhoto(null);
        d.setEmail("test.driver@email.com");
        d.setStatus(DriverStatus.BUSY);
        d.setName("TestName");
        d.setLastName("TestLastName");
        d.setCity(City.NS);
        d.setLastPasswordResetDate(null);
        d.setActivated(true);
        d.setBlocked(false);
        d.setScore(0.0f);
        d.setCar(c);
        d.setPassword("encryptedPassword");
        d.setId(DRIVER_ID);
        return d;
    }
    @NotNull
    private Ride createMockRide(Driver d, Client passenger, Route route, RideStatus status) {
        Ride r = new Ride();
        r.setId(RIDE_ID);
        r.setStatus(status);
        r.setReviews(new ArrayList<>());
        r.setDriver(d);
        List<Client> passengers = new ArrayList<>();
        passengers.add(passenger);
        r.setPassengers(passengers);
        r.setRoute(route);
        r.setPrice(500.0);
        LocalDateTime dt = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
        r.setDepartureTime(dt);
        r.setArrivalTime(null);
        return r;
    }
}
