package com.NWT_KTS_project.obavljanje_voznje;
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
public class DoRideTests {

    @Mock
    private RideRepository rideRepository;
    @Mock
    ReportRepository reportRepository;

    @Mock
    private RideService rideServiceMocked;

    @Mock
    private ReportService reportServiceMocked;

    @InjectMocks
    RideService rideService;
    @InjectMocks
    ReportService reportService;
    @InjectMocks
    private DriverController driverController;
    Integer RIDE_ID = 25;
    Integer DRIVER_ID = 10;
    private ObjectMapper objectMapper = new ObjectMapper();

    Ride ride;
    ReportDTO dto;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Client passenger = createMockClient();
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
        dto = new ReportDTO();
        dto.rideId = RIDE_ID;
        dto.reason = "test reason";

        when(rideRepository.findById(RIDE_ID)).thenReturn(Optional.of(r1));
        when(rideRepository.findById(24)).thenReturn(null);
        when(rideRepository.saveAndFlush(r1)).thenReturn(r1);

        Report report = getMockReport(passenger, d);
        when(reportRepository.saveAndFlush(report)).thenReturn(report);
        when(rideServiceMocked.getRideById(RIDE_ID)).thenReturn(r1);
    }


    @Test
    public void testMarkRideAsFinishedController() throws Exception {
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/driver/markRideAsFinished/" + DRIVER_ID + "/" + RIDE_ID);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(driverController).build();
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
        verify(rideServiceMocked).markRideAsFinished(RIDE_ID, RideStatus.FINISHED);
    }
    @Test
    public void testMarkRideAsFinishedService() {
        rideService.markRideAsFinished(RIDE_ID, RideStatus.FINISHED);
    }
    @Test
    public void testForNullPointerException() {
        NullPointerException thrown = Assert.expectThrows(NullPointerException.class, () -> {
            rideService.markRideAsFinished(24, RideStatus.FINISHED);
        });
        Assert.assertNotNull(thrown, "Null pointer exception is thrown for non existent ride_id.");
    }


    @Test
    public void testEndWithEmergencyController() throws Exception {
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/driver/end-with-emergency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(driverController).build();
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(rideServiceMocked).getRideById(RIDE_ID);
        verify(reportServiceMocked).makeReport(ride.getDriver(), ride.getPassengers().get(0),dto.reason);
        verify(rideServiceMocked).markRideAsFinished(RIDE_ID, RideStatus.EMERGENCY);
    }
    @Test
    public void testEndWithEmergencyControllerException() {
        ArrayList<Client> passers = new ArrayList<>();
        Ride rideNoPassengers = ride;
        rideNoPassengers.setPassengers(passers);

        NestedServletException thrown = Assert.expectThrows(NestedServletException.class, () -> {
            RequestBuilder requestBuilder =
                    MockMvcRequestBuilders.post("/api/driver/end-with-emergency")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto));
            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(driverController).build();
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isInternalServerError());
        });
        Assert.assertNotNull(thrown, "NestedServletException is thrown for ride with no passengers.");
    }
    @Test
    public void testReportService() {
        reportService.makeReport(ride.getDriver(), ride.getPassengers().get(0), "test reason");
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
    private static Report getMockReport(Client passenger, Driver d) {
        Report report = new Report();
        report.setReporter(d);
        report.setReportee(passenger);
        report.setSolved(false);
        report.setText("test reason");
        return report;
    }

}
