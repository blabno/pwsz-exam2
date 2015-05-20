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
        public void sendLine_noParam_throwException(){
            expectedException.expect(IllegalArgumentException.class);
            expectedException.expectMessage("line param can't be null);
            //Given

            Connection connnection = mock(Connection.class);
            Terminal term = new Terminal(connnection);
            term.getErrorMessage();
            //Then

            term.sendLine(null);
        }
        @Test
        public void sendLine_parameterIsGiven_returnSendLines(){
            //Given

            Connection connnection = mock(Connection.class);
            Terminal term = new Terminal(connnection);
            when(connnection.sendLine(".")).thenReturn(".");
            when(connnection.isConnected()).thenReturn(true);
            term.getErrorMessage();
            // Then

            term.sendLine(".");
            verify(connnection, times(1)).sendLine(".");
        }
        @Test
        public void sendLine_noConnect_throwException(){
            expectedException.expect(IllegalStateException.class);
            //Given

            Connection connnection = mock(Connection.class);
            Terminal term = new Terminal(connnection);
            when(connnection.isConnected()).thenReturn(true);
            when(term.sendLine(".")).thenThrow(IllegalStateException.class);
            //When

            term.sendLine(".");
            //Then

            verify(connnection, times(1)).isConnected();
            String errorMessage = term.getErrorMessage();
            Assert.assertEquals("This command is unknown", errorMessage);

            expectedException.expectMessage(errorMessage);
        }
        @Test
        public void sendLine_noComm_throwException(){
            expectedException.expect(IllegalStateException.class);
            //Given

            Connection connnectin = mock(Connection.class);
            Terminal term = new Terminal(connnectin);
            when(connnectin.isConnected()).thenReturn(false);
            // When

            term.sendLine(".");
            //Then

            String errorMessage = term.getErrorMessage();

            Assert.assertEquals("Terminal is not connected", errorMessage);
            expectedException.expectMessage("Not connected");
        }
        @Test
        public void sendLine_isConnected_throwException(){
            expectedException.expect(IllegalStateException.class);
            //Given

            Connection connection = mock(Connection.class);

            Terminal term = new Terminal(connection);
            when(connection.isConnected()).thenReturn(true);
            when(connection.sendLine(".")).thenThrow(UnknownCommandException.class);
            //When

            term.sendLine(".");
            //Then

            verify(connection, times(1)).sendLine(".");
            verify(connection, times(1)).isConnected();
            String errorMessage = term.getErrorMessage();
            Assert.assertEquals("This command is unknown", errorMessage);
            expectedException.expectMessage(errorMessage);
        }
    }