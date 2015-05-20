package pl.labno.bernard;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class TerminalTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void sendLine_nullLine_exception(){
        //given
        Connection connectionMock = Mockito.mock(Connection.class);
        Terminal terminal = new Terminal(connectionMock);

        //when&then
        thrown.expect(IllegalArgumentException.class);
        terminal.sendLine(null);

    }

    @Test
    public void sendLine_notConnencted_exception(){
        //given
        Connection connectionMock = Mockito.mock(Connection.class);
        Mockito.when(connectionMock.isConnected()).thenReturn(false);
        Terminal terminal = new Terminal(connectionMock);

        //when&then
        thrown.expect(IllegalStateException.class);
        terminal.sendLine("aaa");

        Assert.assertEquals("Terminal is not connected", terminal.getErrorMessage());
    }

    @Test
    public void sendLine_knownCommand_shouldSend(){
        //given
        Connection connectionMock = Mockito.mock(Connection.class);
        Mockito.when(connectionMock.isConnected()).thenReturn(true);
        Mockito.when(connectionMock.sendLine("line")).thenReturn("line");
        Terminal terminal = new Terminal(connectionMock);

        //when
        String result = terminal.sendLine("line");

        //then
        Assert.assertEquals("line", result);
    }

    @Test
    public void sendLine_unknownCommand_exception(){
        //given
        Connection connectionMock = Mockito.mock(Connection.class);
        Mockito.when(connectionMock.isConnected()).thenReturn(true);
        Mockito.when(connectionMock.sendLine("line")).thenThrow(new UnknownCommandException());
        Terminal terminal = new Terminal(connectionMock);

        //when & then
        thrown.expect(IllegalStateException.class);
        terminal.sendLine("line");
    }


    @Test
    public void getMessage_always_shouldReturnString(){
        //given
        Connection connectionMock = Mockito.mock(Connection.class);
        Terminal terminal = new Terminal(connectionMock);

        //when
        String errorMessage = terminal.getErrorMessage();

        //then
        //??
    }

}
