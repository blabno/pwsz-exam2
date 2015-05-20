package pl.labno.bernard;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.rules.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

public class TerminalTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void sendLine_lineNull_throwException() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("line param must not be null");
        //Given
        Connection coMo = mock(Connection.class);
        Terminal terminal = new Terminal(coMo);
        // When
        terminal.sendLine(null);
    }

    @Test
    public void sendLine_connectExistAndCommandIsTrue_executeConnectionSendLine() {
        // Given
        Connection coMo = mock(Connection.class);
        when(coMo.isConnected()).thenReturn(true);
        when(coMo.sendLine("line")).thenReturn("line");
        Terminal terminal = new Terminal(coMo);
        // When
        String line = terminal.sendLine("line");
        // Then
        assertEquals("line", line);
    }

    @Test
    public void sendLine_connectNotExist_throwExceptionAndSetErrorMsg() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Not connected");
        // Given
        Connection coMo = mock(Connection.class);
        when(coMo.isConnected()).thenReturn(false);
        Terminal terminal = new Terminal(coMo);
        // When
        terminal.sendLine("line");
        String errorMsg = terminal.getErrorMessage();
        // Then
        assertEquals("terminal is not connected", errorMsg);
    }

    @Test
    public void sendLine_connectExistAndCommandNotTrue_throwExceptionAndSetErrorMsg() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Unknown command");
        // Given
        Connection coMo = mock(Connection.class);
        when(coMo.isConnected()).thenReturn(true);
        when(coMo.sendLine("line")).thenThrow(UnknownCommandException.class);
        Terminal terminal = new Terminal(coMo);
        // When
        terminal.sendLine("line");
        String errorMsg = terminal.getErrorMessage();
        // Then
        assertEquals("This command is unknown", errorMsg);
    }

}