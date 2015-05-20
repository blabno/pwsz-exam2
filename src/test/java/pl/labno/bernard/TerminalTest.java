package pl.labno.bernard;

import org.junit.rules.ExpectedException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import static org.mockito.Mockito.*;

public class TerminalTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();



    @Test
    public void sendLine_connectionNoConnected_throwExceptionAndSetErrorMsg() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Not connected");
        // Given
        Connection conn = mock(Connection.class);
        when(conn.isConnected()).thenReturn(false);
        Terminal terminal = new Terminal(conn);
        // When
        terminal.sendLine("line");
        String errorMsg = terminal.getErrorMessage();
        // Then
        assertEquals("terminal is not connected", errorMsg);
    }

    @Test
    public void sendLine_lineNull_throwException() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("line param must not be null");
        //Given
        Connection conn = mock(Connection.class);
        Terminal terminal = new Terminal(conn);
        // When
        terminal.sendLine(null);
    }
    @Test
    public void sendLine_connectionConnectedAndCommandValid_executeConnectionSendLine() {
        // Given
        Connection conn = mock(Connection.class);
        when(connMock.isConnected()).thenReturn(true);
        when(connMock.sendLine("line")).thenReturn("line");
        Terminal terminal = new Terminal(conn);
        // When
        String line = terminal.sendLine("line");
        // Then
        assertEquals("line", line);
    }

    @Test
    public void sendLine_connectionConnectedAndCommandNoValid_throwExceptionAndSetErrorMsg() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Unknown command");
        // Given
        Connection conn = mock(Connection.class);
        when(conn.isConnected()).thenReturn(true);
        when(conn.sendLine("line")).thenThrow(UnknownCommandException.class);
        Terminal terminal = new Terminal(conn);
        // When
        terminal.sendLine("line");
        String error = terminal.getErrorMessage();
        // Then
        assertEquals("This command is unknown", error);
    }
}