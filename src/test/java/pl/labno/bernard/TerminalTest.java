package pl.labno.bernard;

import org.mockito.Mockito;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TerminalTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void sendLine_lineIsNull_throwException(){

        // Given
        Connection connectionMock = Mockito.mock(Connection.class);
        Terminal terminal = new Terminal(connectionMock);

        // When
        thrown.expect(IllegalArgumentException.class);
        terminal.sendLine(null);
    }

    @Test
    public void sendLine_isNotConnencted_throwException(){

        // Given
        Connection connectionMock = Mockito.mock(Connection.class);
        Mockito.when(connectionMock.isConnected()).thenReturn(false);

        // When
        Terminal terminal = new Terminal(connectionMock);
        thrown.expect(IllegalStateException.class);
        terminal.sendLine("Message");

        // Then
        Assert.assertEquals("Terminal is not connected", terminal.getErrorMessage());
    }

    @Test
    public void sendLine_givenCommand_ableToSend(){

        // Given
        Connection connectionMock = Mockito.mock(Connection.class);
        Mockito.when(connectionMock.isConnected()).thenReturn(true);
        Mockito.when(connectionMock.sendLine("line")).thenReturn("line");
        Terminal terminal = new Terminal(connectionMock);

        // When
        String result = terminal.sendLine("line");

        // Then
        Assert.assertEquals("line", result);
    }

    @Test
    public void sendLine_commandIsUnknown_throwException(){

        // Given
        Connection connectionMock = Mockito.mock(Connection.class);
        Mockito.when(connectionMock.isConnected()).thenReturn(true);
        Mockito.when(connectionMock.sendLine("line")).thenThrow(new UnknownCommandException());
        Terminal terminal = new Terminal(connectionMock);

        // When
        thrown.expect(IllegalStateException.class);
        terminal.sendLine("line");
    }

    @Test
    public void getMessage_always_shouldReturnString(){

        // Given
        Connection connectionMock = Mockito.mock(Connection.class);
        Terminal terminal = new Terminal(connectionMock);

        // When
        String errorMessage = terminal.getErrorMessage();

        // Then
        Assert.assertNotNull(errorMessage);
    }
}