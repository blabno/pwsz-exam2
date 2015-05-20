package pl.labno.bernard;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class TerminalTest {

    @Rule
    public ExpectedException expectedException= ExpectedException.none();

    @Test
    public void sendLine_lineIsNull_IllegalArgumentException(){

        Connection connection = Mockito.mock(Connection.class);
        Terminal terminal = new Terminal(connection);

        expectedException.expect(IllegalArgumentException.class);
        terminal.sendLine(null);

    }

    @Test
    public void sendLine_connectionSendLineException_llegalArgumentException(){

        Connection conection = Mockito.mock(Connection.class);
        Terminal terminal=new Terminal(conection);

        when(conection.isConnected()).thenReturn(true);
        when(conection.sendLine("as")).thenThrow(UnknownCommandException.class);

        expectedException.expect(IllegalStateException.class);
        terminal.sendLine("as");


    }

    @Test
    public void sendLine_unknownCommandMessage_throwException(){

        expectedException.expect(IllegalStateException.class);

        Connection con = Mockito.mock(Connection.class);
        Terminal terminal = new Terminal(con);

        when(con.isConnected()).thenReturn(false);

        terminal.sendLine(".");

        String errorMessage = terminal.getErrorMessage();
        Assert.assertEquals("Terminal is not connected", errorMessage);

        expectedException.expectMessage("Not connected");

    }


    @Test
    public void sendLine_isConnectedSendLineThrowErrorMessage_throwException(){

        expectedException.expect(IllegalStateException.class);

        Connection conection = Mockito.mock(Connection.class);
        Terminal terminal = new Terminal(conection);
        String errorMessage = terminal.getErrorMessage();

        when(conection.isConnected()).thenReturn(true);
        when(conection.sendLine("as")).thenThrow(IllegalStateException.class);

        terminal.sendLine("as");

        Mockito.verify(conection, Mockito.times(1)).sendLine("as");
        Mockito.verify(conection, Mockito.times(1)).isConnected();



        Assert.assertEquals("This command is unknown", errorMessage);

        expectedException.expectMessage(errorMessage);

    }

}
