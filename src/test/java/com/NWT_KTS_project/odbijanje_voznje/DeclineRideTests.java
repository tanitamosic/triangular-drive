package com.NWT_KTS_project.odbijanje_voznje;

import com.NWT_KTS_project.DTO.ReportDTO;
import com.NWT_KTS_project.controllers.DriverController;
import com.NWT_KTS_project.model.*;
import com.NWT_KTS_project.model.enums.*;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.repository.ReportRepository;
import com.NWT_KTS_project.repository.RideRepository;
import com.NWT_KTS_project.service.ReportService;
import com.NWT_KTS_project.service.RideService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;
@SpringBootTest
public class DeclineRideTests {

    @Mock
    private RideRepository rideRepository;

    @Autowired
    private RideService rideServiceMock;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private ReportService reportServiceMock;

    @InjectMocks
    private RideService rideService;

    @InjectMocks
    private ReportService reportService;

    @InjectMocks
    private DriverController driverController;

    Integer RIDE_ID = 100;

    Integer DRIVER_ID = 101;

    Integer PASSENGER_ID = 102;

    Integer ROUTE_ID = 103;

    Ride ride;

    ReportDTO reportDTO;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeMethod
    public void setUp() {
        //-----rideRepository.findByRideId(id)
        //-----rideRepository.findById(id);
        //-----rideRepository.saveAndFlush(ride);
        //-----reportRepository.saveAndFlush(report);


        MockitoAnnotations.initMocks(this);

        Client passenger = createMockClient();
        Car car = createMockCar();
        Driver driver = createMockDriver(car);
        Address start = createMockAddress("street", "number", 101, 1.0, 1.0);
        Address finish = createMockAddress("street", "number", 102, 2.0, 2.0);
        List<Address> stops = new ArrayList<>();
        Route route = createMockRoute(start, finish, stops);
        ride = createMockRide(driver,passenger,route,RideStatus.ONGOING);
        reportDTO = new ReportDTO();
        reportDTO.rideId = RIDE_ID;
        reportDTO.reason = "Reason";
        Report report = getMockReport(passenger,driver);


        when(rideRepository.findByRideId(RIDE_ID)).thenReturn(ride);
        when(rideRepository.saveAndFlush(ride)).thenReturn(ride);
        when(rideRepository.findById(RIDE_ID)).thenReturn(Optional.of(ride));
        when(reportRepository.saveAndFlush(report)).thenReturn(report);

    }


    @Test
    public void RideService_getRideByIdTest_Ok() {
        Ride ride = rideService.getRideById(RIDE_ID);
        assertEquals(ride.getId(),100);
    }

    @Test
    public void RideService_getRideByIdTest_Null() {
        Ride ride = rideService.getRideById(RIDE_ID+1);
        assertEquals(ride,null);
    }

    @Test
    public void RideService_setRideStatusTest_Ok_FINISHED() {
        ride.setStatus(RideStatus.ONGOING);
        rideService.setRideStatus(ride.getId(),RideStatus.FINISHED);
        assertEquals(ride.getStatus(),RideStatus.FINISHED);
    }

    @Test
    public void RideService_setRideStatusTest_Ok_ONGOING() {
        ride.setStatus(RideStatus.ONGOING);
        rideService.setRideStatus(ride.getId(),RideStatus.ONGOING);
        assertEquals(ride.getStatus(),RideStatus.ONGOING);
    }


    @Test
    public void RideService_setRideStatusTest_Ok_RESERVED() {
        ride.setStatus(RideStatus.ONGOING);
        rideService.setRideStatus(ride.getId(),RideStatus.RESERVED);
        assertEquals(ride.getStatus(),RideStatus.RESERVED);
    }

    @Test
    public void RideService_setRideStatusTest_Ok_REJECTED() {
        ride.setStatus(RideStatus.ONGOING);
        rideService.setRideStatus(ride.getId(),RideStatus.REJECTED);
        assertEquals(ride.getStatus(),RideStatus.REJECTED);
    }

    @Test
    public void RideService_setRideStatusTest_Ok_EMERGENCY() {
        ride.setStatus(RideStatus.ONGOING);
        rideService.setRideStatus(ride.getId(),RideStatus.EMERGENCY);
        assertEquals(ride.getStatus(),RideStatus.EMERGENCY);
    }


    @Test
    public void ReportService_makeReportTest_Ok() {
        Client passenger = createMockClient();
        Driver driver = ride.getDriver();
        Report report = getMockReport(passenger,driver);
        Report report1 = reportService.makeReport(passenger,driver,reportDTO.reason);
        assertEquals(report1.getText(),report.getText());
    }

    @Test
    public void DriverController_declineRideTest_Ok() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/driver/reject-ride")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reportDTO));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(driverController).build();
        mockMvc.perform(requestBuilder);

        verify(rideServiceMock).getRideById(RIDE_ID);
        verify(rideServiceMock).setRideStatus(RIDE_ID,RideStatus.REJECTED);
        verify(reportServiceMock).makeReport(ride.getDriver(),ride.getPassengers().get(0),reportDTO.reason);


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
        c.setId(PASSENGER_ID);
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
    @NotNull
    private  Report getMockReport(Client passenger, Driver d) {
        Report report = new Report();
        report.setReporter(d);
        report.setReportee(passenger);
        report.setSolved(false);
        report.setText(reportDTO.reason);
        return report;
    }


}
