package com.julio.tgid.service;

import com.julio.tgid.DTO.ClientDTO;
import com.julio.tgid.domain.Client;
import com.julio.tgid.exception.AlreadyInUse;
import com.julio.tgid.exception.ObjectNotFound;
import com.julio.tgid.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository repository;

    private Client client;
    private ClientDTO clientDTO;
    private UUID clientId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        clientId = UUID.randomUUID();
        client = new Client();
        client.setFirstName("User");
        client.setLastName("Example");
        client.setCpf("12345678900");
        client.setEmail("example.user@example.com");
        client.setId(clientId);

        clientDTO = new ClientDTO("User", "Example", "12345678900", "example.user@example.com");
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(List.of(client));

        List<ClientDTO> result = clientService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(clientDTO, result.get(0));
    }

    @Test
    void testFindById() {
        when(repository.findById(clientId)).thenReturn(Optional.of(client));

        ClientDTO result = clientService.findById(clientId);

        assertNotNull(result);
        assertEquals(clientDTO, result);
    }

    @Test
    void testFindByIdThrowsException() {
        when(repository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFound.class, () -> clientService.findById(clientId));
    }

    @Test
    void testFindByCpf() {
        when(repository.findByCpf(client.getCpf())).thenReturn(Optional.of(client));

        ClientDTO result = clientService.findByCpf(client.getCpf());

        assertNotNull(result);
        assertEquals(clientDTO, result);
    }

    @Test
    void testFindByCpfThrowsException() {
        when(repository.findByCpf(client.getCpf())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFound.class, () -> clientService.findByCpf(client.getCpf()));
    }

    @Test
    void testSaveClient() {
        when(repository.findByCpf(client.getCpf())).thenReturn(Optional.empty());
        when(repository.save(any(Client.class))).thenReturn(client);

        ClientDTO result = clientService.saveClient(clientDTO);

        assertNotNull(result);
        assertEquals(clientDTO, result);
    }

    @Test
    void testSaveClientThrowsException() {
        when(repository.findByCpf(client.getCpf())).thenReturn(Optional.of(client));

        assertThrows(AlreadyInUse.class, () -> clientService.saveClient(clientDTO));
    }

    @Test
    void testDeleteClient() {
        when(repository.existsById(clientId)).thenReturn(true);

        clientService.deleteClient(clientId);

        verify(repository, times(1)).deleteById(clientId);
    }

    @Test
    void testDeleteClientThrowsException() {
        when(repository.existsById(clientId)).thenReturn(false);

        assertThrows(ObjectNotFound.class, () -> clientService.deleteClient(clientId));
    }

    @Test
    void testDeleteClientByCpf() {
        when(repository.findByCpf(client.getCpf())).thenReturn(Optional.of(client));

        clientService.deleteClientByCpf(client.getCpf());

        verify(repository, times(1)).delete(client);
    }

    @Test
    void testDeleteClientByCpfThrowsException() {
        when(repository.findByCpf(client.getCpf())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFound.class, () -> clientService.deleteClientByCpf(client.getCpf()));
    }
}
