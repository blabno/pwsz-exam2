package pl.labno.bernard;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;
public class TerminalTest {

    @Rule
    public ExpectedException e = ExpectedException.none();

    @Test
    public void sendLine_isNull_throwException() {
        e.expect(IllegalArgumentException.class);
        Connection mk = mock(Connection.class);
        Terminal terminal = new Terminal(mk);

        terminal.sendLine(null);
    }

    @Test
    public void sendLine_connectionIsNotConnected_throwException() {
        e.expect(IllegalStateException.class);
        Connection mk = mock(Connection.class);
        when(mk.isConnected()).thenReturn(false);
        Terminal terminal = new Terminal(mk);
        terminal.sendLine("line");
        String er = terminal.getErrorMessage();

        assertEquals("no connection", er);
    }

    @Test
    public void sendLine_connectionCommandNotValid_throwException() {
        e.expect(IllegalStateException.class);
        Connection mk = mock(Connection.class);
        when(mk.isConnected()).thenReturn(true);
        when(mk.sendLine("line")).thenThrow(UnknownCommandException.class);
        Terminal terminal = new Terminal(mk);
        terminal.sendLine("line");
        String er = terminal.getErrorMessage();

        assertEquals("Command is unknown", er);
    }


}
