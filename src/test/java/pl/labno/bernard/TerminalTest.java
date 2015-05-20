package pl.labno.bernard;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;
public class TerminalTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Test
    public void sendLine_parameterNull_throwException(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("line param must not be null");
        //Given
        Connection con = mock(Connection.class);
        Terminal terminal = new Terminal(con);
        terminal.getErrorMessage();
        //Then
        terminal.sendLine(null);
    }
    @Test
    public void sendLine_isnotnull_returnsSendLinesParam(){
        //Given
        Connection con = mock(Connection.class);
        Terminal terminal = new Terminal(con);
        when(con.sendLine(".")).thenReturn(".");
        when(con.isConnected()).thenReturn(true);
        terminal.getErrorMessage();
// Then
        terminal.sendLine(".");
        verify(con, times(1)).sendLine(".");
    }
    @Test
    public void sendLine_noconnected_throwException(){
        expectedException.expect(IllegalStateException.class);
//Given
        Connection con = mock(Connection.class);
        Terminal terminal = new Terminal(con);
        when(con.isConnected()).thenReturn(true);
        when(terminal.sendLine(".")).thenThrow(IllegalStateException.class);
//When
        terminal.sendLine(".");
//Then
        verify(con, times(1)).isConnected();
        String errorMessage = terminal.getErrorMessage();
        Assert.assertEquals("command is unknown", errorMessage);
        expectedException.expectMessage(errorMessage);
    }
    @Test
    public void sendLine_Commandunknown_throwException(){
        expectedException.expect(IllegalStateException.class);
//Given
        Connection con = mock(Connection.class);
        Terminal terminal = new Terminal(con);
        when(con.isConnected()).thenReturn(false);
// When
        terminal.sendLine(".");
//Then
        String errorMessage = terminal.getErrorMessage();
        Assert.assertEquals("is not connected", errorMessage);
        expectedException.expectMessage("connected");
    }
    @Test
    public void sendLine_LineThrowError_throwException(){
        expectedException.expect(IllegalStateException.class);
//Given
        Connection con = mock(Connection.class);
        Terminal terminal = new Terminal(con);
        when(con.isConnected()).thenReturn(true);
        when(con.sendLine(".")).thenThrow(IllegalStateException.class);
//When
        terminal.sendLine(".");
//Then
        verify(con, times(1)).sendLine(".");
        verify(con, times(1)).isConnected();
        String errorMessage = terminal.getErrorMessage();
        Assert.assertEquals("command is unknown", errorMessage);
        expectedException.expectMessage(errorMessage);
    }
}