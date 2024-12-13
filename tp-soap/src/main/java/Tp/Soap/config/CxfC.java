package Tp.Soap.config;

import Tp.Soap.WS.ChambreService;
import Tp.Soap.WS.ClientService;
import Tp.Soap.WS.ReservationService;
import lombok.AllArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class CxfC {
    private ChambreService chambreService;
    private ClientService clientService;
    private ReservationService reservationService;
    private Bus bus;

    @Bean
    public EndpointImpl endpointRoom() {
        EndpointImpl endpoint = new EndpointImpl(bus, chambreService);
        endpoint.publish("/Room");
        return endpoint;
    }
    @Bean
    public EndpointImpl endpointClient() {
        EndpointImpl endpoint = new EndpointImpl(bus, clientService);
        endpoint.publish("/client");
        return endpoint;
    }
    @Bean
    public EndpointImpl endpointReservation() {
        EndpointImpl endpoint = new EndpointImpl(bus, reservationService);
        endpoint.publish("/reservation");
        return endpoint;
    }
}
