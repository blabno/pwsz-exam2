package pl.labno.bernard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.internal.matchers.Null;

import static org.mockito.Mockito.*;

public class TerminalTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private String errorMessage;
    private Connection connection;
    private String line;

    @Test
    public void sendline_null_line() {
        // Given
        connection = mock(Connection.class);
        Terminal terminal = new Terminal(connection);
        // When
        // Then
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("line param must not be null");
        terminal.sendLine(null);
    }

    @Test
    public void sendline_not_connected() {
        // Given
        connection = mock(Connection.class);
        when(connection.isConnected()).thenReturn(false);
        Terminal terminal = new Terminal(connection);
        // When
        // Then
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Not connected");
        terminal.sendLine("test_line");

    }

    @Test
    public void sendline_not_connected_comunicat() {
        // Given
        connection = mock(Connection.class);
        when(connection.isConnected()).thenReturn(false);
        Terminal terminal = new Terminal(connection);
        // When
        // Then
        try {
            terminal.sendLine("test_line");
        } catch (Exception e) {

        }
        Assert.assertEquals("Terminal is not connected", terminal.getErrorMessage());
    }

    @Test
    public void sendline_is_connected() {
        // Given
        connection = mock(Connection.class);
        when(connection.isConnected()).thenReturn(true);
        when(connection.sendLine("test_line")).thenReturn("test_line");
        Terminal terminal = new Terminal(connection);
        // When
        line = terminal.sendLine("test_line");
        // Then
        Assert.assertEquals("test_line", line);
    }

    @Test
    public void sendline_is_connected_error() {
        // Given
        connection = mock(Connection.class);
        when(connection.isConnected()).thenReturn(true);
        when(connection.sendLine("test_line")).thenThrow(UnknownCommandException.class);
        Terminal terminal = new Terminal(connection);
        // When
        // Then
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Unknown command");
        line = terminal.sendLine("test_line");
    }

    @Test
    public void sendline_is_connected_errormassage() {
        // Given
        connection = mock(Connection.class);
        when(connection.isConnected()).thenReturn(true);
        when(connection.sendLine("test_line")).thenThrow(UnknownCommandException.class);
        Terminal terminal = new Terminal(connection);
        // When
        // Then
        try {
            line = terminal.sendLine("test_line");
        } catch (Exception e) {
            Assert.assertEquals("This command is unknown", terminal.getErrorMessage());
        }
    }
}
