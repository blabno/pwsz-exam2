package pl.labno.bernard;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;

public class TerminalTest {
  
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Test
    
    public void sendLine_lineIsNull() {
      
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("line param must not be null");
        //Given
        
        Connection connMock = mock(Connection.class);
        Terminal terminal = new Terminal(connMock);
        // When
        
        terminal.sendLine(null);
    }
    
    @Test
    public void sendLine_terminalIsNotConnected() {
      
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Not connected");
        // Given
        
        Connection connMock = mock(Connection.class);
        when(connMock.isConnected()).thenReturn(false);
        Terminal terminal = new Terminal(connMock);
        // When
        
        terminal.sendLine("line");
        String errorMsg = terminal.getErrorMessage();
        // Then
        
        assertEquals("Terminal is not connected", errorMsg);
    }
    @Test
    public void sendLine_connectionIsConnectedAndCommandIsNotValid() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Unknown command");
        // Given
        
        Connection connMock = mock(Connection.class);
        when(connMock.isConnected()).thenReturn(true);
        when(connMock.sendLine("line")).thenThrow(UnknownCommandException.class);
        Terminal terminal = new Terminal(connMock);
        // When
        
        terminal.sendLine("line");
        String errorMsg = terminal.getErrorMessage();
        // Then
        
        assertEquals("This command is unknown", errorMsg);
    }
}
