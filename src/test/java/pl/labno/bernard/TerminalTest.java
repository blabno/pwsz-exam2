package pl.labno.bernard;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.junit.Assert;

public class TerminalTest {

     @Rule
     public ExpectedException except = ExpectedException.none();

     @Test
     public void sendLine_isNull_Exception() {
         except.expect(IllegalArgumentException.class);
         except.expectMessage("line param must not be null");
         Connection connMock = mock(Connection.class);
         Terminal terminal = new Terminal(connMock);
         terminal.sendLine(null);
     }

     @Test
     public void sendLine_noConnection_Exception() {
         Connection connMock = Mockito.mock(Connection.class);
         when(connMock.isConnected()).thenReturn(false);
         Terminal terminal = new Terminal(connMock);
         expectedException.expect(IllegalStateException.class);
         terminal.sendLine("Terminal is not connected");
     }


     @Test
     public void sendLine_isConnected_Exception() {
         except.expect(IllegalStateException.class);
         except.expectMessage("Unknown command");
         Connection connMock = mock(Connection.class);
         when(connMock.isConnected()).thenReturn(true);
         when(connMock.sendLine("line")).thenThrow(UnknownCommandException.class);
         Terminal terminal = new Terminal(connMock);
         terminal.sendLine("line");
         String error = terminal.getErrorMessage();
         assertEquals("This command is unknown", error);
     }

}

