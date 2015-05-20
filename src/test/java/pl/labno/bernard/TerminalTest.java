package pl.labno.bernard;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TerminalTest {
    @Rule
    public ExpectedException expectedException= ExpectedException.none();

    @Test
    public void sendLine_null_argument()
    {
        Connection connectionMock = Mockito.mock(Connection.class);
        Terminal terminal = new Terminal(connectionMock);
        expectedException.expect(IllegalArgumentException.class);
    }

    @Test
    public void send_line_unknownCommandException()
    {
        Connection connectionMock = Mockito.mock(Connection.class);
        Terminal terminal = new Terminal(connectionMock);

        when(connectionMock.isConnected()).thenReturn(true);

        terminal.sendLine("line");
        String errorMsg = terminal.getErrorMessage();

        assertEquals("This command is unknown", errorMsg);
    }
}
