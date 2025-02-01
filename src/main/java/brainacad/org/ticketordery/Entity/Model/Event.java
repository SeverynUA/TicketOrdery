package brainacad.org.ticketordery.Entity.Model;

import brainacad.org.ticketordery.DTO.Model.TicketPackDTO;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Event")
public class Event
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id", referencedColumnName = "id", nullable = false)
    private Place place;

    public String getName() {return name;}
    public LocalDate getEventDate() {return eventDate;}
    public Place getPlace() {return place;}
    public List<Ticket> getTickets() {return tickets;}

    public void setName(String name) {this.name = name;}
    public void setEventDate(LocalDate eventDate) {this.eventDate = eventDate;}
    public void setPlace(Place place) {this.place = place;}
    public void setTickets(List<Ticket> tickets) {this.tickets = tickets;}
}
