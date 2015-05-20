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
    public void sendLine_lineIsNull_IllegalArgumentException()
    {
     Connection con= Mockito.mock(Connection.class);
        Terminal ter=new Terminal(con);
        expectedException.expect(IllegalArgumentException.class);
        ter.sendLine(null);
    }
    @Test
    public void sendLine_noConnection_IllegalArgumentException()
    {
        Connection con= Mockito.mock(Connection.class);
        when(con.isConnected()).thenReturn(false);
        Terminal ter=new Terminal(con);
        expectedException.expect(IllegalStateException.class);
        ter.sendLine("cos");


    }
    @Test
    public void sendLine_connectionSendLineThrowException_llegalArgumentException()
    {
        Connection con= Mockito.mock(Connection.class);
        when(con.isConnected()).thenReturn(true);
        when(con.sendLine("cos")).thenThrow(UnknownCommandException.class);
        Terminal ter=new Terminal(con);
        expectedException.expect(IllegalStateException.class);
        ter.sendLine("cos");


    }
    @Test
    public void sendLine_noConnection_ErrorMassage()
    {
        Connection con= Mockito.mock(Connection.class);
        when(con.isConnected()).thenReturn(false);
        Terminal ter=new Terminal(con);
        try {
            ter.sendLine("cos");
        }catch(IllegalStateException e){}
        Assert.assertEquals("Terminal is not connected",ter.getErrorMessage());
    }

}
