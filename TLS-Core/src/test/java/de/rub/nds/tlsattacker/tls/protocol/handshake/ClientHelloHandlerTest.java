/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.tls.protocol.handshake;

import de.rub.nds.tlsattacker.tls.protocol.message.ClientHelloMessage;
import de.rub.nds.tlsattacker.tls.constants.CipherSuite;
import de.rub.nds.tlsattacker.tls.constants.CompressionMethod;
import de.rub.nds.tlsattacker.tls.constants.ExtensionType;
import de.rub.nds.tlsattacker.tls.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.tls.constants.HeartbeatMode;
import de.rub.nds.tlsattacker.tls.constants.NamedCurve;
import de.rub.nds.tlsattacker.tls.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.tls.protocol.handler.ClientHelloHandler;
import de.rub.nds.tlsattacker.tls.workflow.TlsConfig;
import de.rub.nds.tlsattacker.tls.workflow.TlsContext;
import de.rub.nds.tlsattacker.util.ArrayConverter;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * 
 * @author Juraj Somorovsky <juraj.somorovsky@rub.de>
 * @author Florian Pfützenreuter <florian.pfuetzenreuter@rub.de>
 * @author Philip Riese <philip.riese@rub.de>
 */
public class ClientHelloHandlerTest {

//    static byte[] clientHelloWithoutExtensionBytes = ArrayConverter
//            .hexStringToByteArray("01000059030336CCE3E132A0C5B5DE2C0560B4FF7F6CDF7AE226120E4A99C07E2D9B68B275BB20FA6F8E9770106ACE8ACAB73E18B5D867CAF8AF7E206EF496F23A206A379FC7110012C02BC02FC00AC009C013C014002F0035000A0100");
//
//    static byte[] clientHelloWithHeartbeatandEllipticCurvesBytes = ArrayConverter
//            .hexStringToByteArray("0100003A0303561EAC1C71D111AB0186813D11808C3EEA236E37C0110A75B929D6E0A3F53F42000002C0300100000F000F000101000A00060004000F0001");
//
//    static byte[] clientHelloWithHeartbeatBytes = ArrayConverter
//            .hexStringToByteArray("010000300303561EAC1C71D111AB0186813D11808C3EEA236E37C0110A75B929D6E0A3F53F42000002C03001000005000F000101");
//
//    // DTLS clientHello with the dtls handshake fields (messageSeq,
//    // fragmentOffset and fragmentLength) already stripped out.
//    // Thus, only the cookie remains.
//    static byte[] clientHelloDTLS10withExtensions = ArrayConverter
//            .hexStringToByteArray("010000bc00010000000000bcfefdd471fd117e9b65caf6e4ffc649511d33dc3843da212084d82963644915cb16c10014ec6c096cc8fff20f674c7fcc34afb84d4d89cafc0038c02cc030009fcca9cca8ccaac02bc02f009ec024c028006bc023c0270067c00ac0140039c009c0130033009d009c003d003c0035002f00ff01000046000b000403000102000a000a0008001d00170019001800230000000d0020001e0403050306030804080508060401050106010203020102020402050206020016000000170000");
//
//    static byte[] cookie = ArrayConverter.hexStringToByteArray("1122334455667788");
//
//    ClientHelloHandler<ClientHelloDtlsMessage> handler;
//    ClientHelloHandler<ClientHelloDtlsMessage> dtlshandler;
//
//    TlsContext tlsContext = new TlsContext(new TlsConfig());
//    TlsContext dtlsContext = new TlsContext();
//
//    public ClientHelloHandlerTest() {
//        tlsContext.getConfig().setHighestProtocolVersion(ProtocolVersion.TLS12);
//        dtlsContext.setSelectedProtocolVersion(ProtocolVersion.TLS12);
//        handler = new ClientHelloHandler<>(tlsContext);
//        dtlsContext.setDtlsHandshakeCookie(cookie);
//        dtlsContext.getConfig().setHighestProtocolVersion(ProtocolVersion.DTLS12);
//        dtlsContext.setSelectedProtocolVersion(ProtocolVersion.DTLS12);
//        dtlshandler = new ClientHelloHandler<>(dtlsContext);
//        List<CipherSuite> cipherSuites = new ArrayList<>();
//        cipherSuites.add(CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384);
//        dtlsContext.getConfig().setSupportedCiphersuites(cipherSuites);
//        tlsContext.getConfig().setSupportedCiphersuites(cipherSuites);
//        List<CompressionMethod> compressionMethods = new ArrayList<>();
//        compressionMethods.add(CompressionMethod.NULL);
//        tlsContext.getConfig().setSupportedCompressionMethods(compressionMethods);
//        dtlsContext.getConfig().setSupportedCompressionMethods(compressionMethods);
//    }
//
//    /**
//     * Test of prepareMessageAction method, of class ClientHelloHandler.
//     */
//
//    public void testPrepareMessage() {
//        dtlshandler.setProtocolMessage(new ClientHelloDtlsMessage(dtlsContext.getConfig()));
//
//        ClientHelloDtlsMessage message = dtlshandler.getProtocolMessage();
//
//        byte[] returned = dtlshandler.prepareMessageAction();
//        byte[] expected = ArrayConverter.concatenate(new byte[] { HandshakeMessageType.CLIENT_HELLO.getValue() },
//                new byte[] { 0x00, 0x00, 0x32 }, ProtocolVersion.DTLS12.getValue(), message.getUnixTime().getValue(),
//                message.getRandom().getValue(), new byte[] { 0x00, 0x08 }, cookie, new byte[] { 0x00, 0x02 },
//                CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384.getByteValue(), new byte[] { 0x01,
//                        CompressionMethod.NULL.getValue() });
//        assertNotNull("Confirm function didn't return 'NULL'", returned);
//        assertArrayEquals("Confirm returned message equals the expected message", expected, returned);
//    }
//
//    public void testPrepareMessageWithExtensions() {
//        tlsContext.getConfig().setHeartbeatMode(HeartbeatMode.PEER_ALLOWED_TO_SEND);
//        tlsContext.getConfig().setAddHeartbeatExtension(true);
//        tlsContext.getConfig().setAddEllipticCurveExtension(true);
//        List<NamedCurve> curve = new ArrayList<>();
//        curve.add(NamedCurve.SECP160K1);
//        curve.add(NamedCurve.SECT163K1);
//        tlsContext.getConfig().setNamedCurves(curve);
//        handler.setProtocolMessage(new ClientHelloMessage(tlsContext.getConfig()));
//
//        de.rub.nds.tlsattacker.tls.protocol.message.ClientHelloMessage message = handler.getProtocolMessage();
//        byte[] returned = handler.prepareMessageAction();
//
//        byte[] expected = ArrayConverter.concatenate(new byte[] { HandshakeMessageType.CLIENT_HELLO.getValue() },
//                new byte[] { 0x00, 0x00, 0x3A }, ProtocolVersion.TLS12.getValue(), message.getUnixTime().getValue(),
//                message.getRandom().getValue(), ArrayConverter.intToBytes(message.getSessionIdLength().getValue(), 1),
//                new byte[] { 0x00, 0x02 }, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384.getByteValue(),
//                new byte[] { 0x01, CompressionMethod.NULL.getValue() }, new byte[] { 0x00, 0x0F },
//                ExtensionType.HEARTBEAT.getValue(),
//                new byte[] { 0x00, 0x01, HeartbeatMode.PEER_ALLOWED_TO_SEND.getValue() },
//                ExtensionType.ELLIPTIC_CURVES.getValue(), new byte[] { 0x00, 0x06 }, new byte[] { 0x00, 0x04 },
//                NamedCurve.SECP160K1.getValue(), NamedCurve.SECT163K1.getValue());
//
//        assertNotNull("Confirm function didn't return 'NULL'", returned);
//        System.out.println(ArrayConverter.bytesToHexString(expected));
//        System.out.println(ArrayConverter.bytesToHexString(returned));
//        assertArrayEquals("Confirm returned message equals the expected message", expected, returned);
//    }
//
//    public void testParseDTLSMessage() {
//        dtlshandler.setProtocolMessage(new ClientHelloDtlsMessage(new TlsConfig()));
//
//        int endPointer = dtlshandler.parseMessage(clientHelloDTLS10withExtensions, 0);
//        ClientHelloDtlsMessage message = dtlshandler.getProtocolMessage();
//
//        byte[] expectedRandom = ArrayConverter
//                .hexStringToByteArray("d471fd117e9b65caf6e4ffc649511d33dc3843da212084d82963644915cb16c1");
//        byte[] actualRandom = ArrayConverter.concatenate(message.getUnixTime().getValue(), message.getRandom()
//                .getValue());
//        byte[] expectedSessionID = new byte[0];
//        byte[] actualSessionID = message.getSessionId().getValue();
//
//        byte expectedCookieLength = 20;
//        byte actualCookieLength = message.getCookieLength().getValue();
//        byte[] expectedCookie = ArrayConverter.hexStringToByteArray("ec6c096cc8fff20f674c7fcc34afb84d4d89cafc");
//        byte[] actualCookie = message.getCookie().getValue();
//
//        byte[] expectedCipherSuites = ArrayConverter
//                .hexStringToByteArray("c02cc030009fcca9cca8ccaac02bc02f009ec024c028006bc023c0270067c00ac0140039c009c0130033009d009c003d003c0035002f00ff");
//        byte[] actualCipherSuites = message.getCipherSuites().getValue();
//
//        assertEquals("Check message type", HandshakeMessageType.CLIENT_HELLO, message.getHandshakeMessageType());
//        assertEquals("Message length should be 188 bytes", new Integer(188), message.getLength().getValue());
//        assertArrayEquals("Check Protocol Version", ProtocolVersion.DTLS12.getValue(), message.getProtocolVersion()
//                .getValue());
//        assertArrayEquals("Check random", expectedRandom, actualRandom);
//        assertEquals("Check session_id length", new Integer(0), message.getSessionIdLength().getValue());
//        assertArrayEquals("Check session_id", expectedSessionID, actualSessionID);
//
//        assertEquals("Check cookie length", expectedCookieLength, actualCookieLength);
//        assertArrayEquals("Check cookie", expectedCookie, actualCookie);
//
//        assertEquals("Check cipher_suites length", new Integer(56), message.getCipherSuiteLength().getValue());
//        assertArrayEquals("Check cipher_suites", expectedCipherSuites, actualCipherSuites);
//        assertEquals("Check compressions length", new Integer(1), message.getCompressionLength().getValue());
//        assertArrayEquals("Check compressions", new byte[] { 0x00 }, message.getCompressions().getValue());
//        assertEquals("Check protocol message length pointer", clientHelloDTLS10withExtensions.length, endPointer);
//    }
//
//    /**
//     * Test of parseMessageActionwithExtensions method, of class
//     * ClientHelloHandler.
//     */
//
//    public void testParseMessageWithExtensions() {
//        handler.initializeProtocolMessage();
//
//        int endPointer = handler.parseMessageAction(clientHelloWithHeartbeatBytes, 0);
//        de.rub.nds.tlsattacker.tls.protocol.message.ClientHelloMessage message = handler.getProtocolMessage();
//
//        assertEquals("Message type must be ClientHello", HandshakeMessageType.CLIENT_HELLO,
//                message.getHandshakeMessageType());
//        assertEquals("Message length must be 48", new Integer(48), message.getLength().getValue());
//        assertArrayEquals("Protocol version must be TLS 1.2", ProtocolVersion.TLS12.getValue(), message
//                .getProtocolVersion().getValue());
//        assertEquals("Client Session ID Length", new Integer(0), message.getSessionIdLength().getValue());
//        assertArrayEquals(
//                "Client Random",
//                ArrayConverter.hexStringToByteArray("561EAC1C71D111AB0186813D11808C3EEA236E37C0110A75B929D6E0A3F53F42"),
//                tlsContext.getClientRandom());
//        assertEquals("Cipersuite Length", new Integer(2), message.getCipherSuiteLength().getValue());
//        assertArrayEquals("Ciphersuite must be TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
//                CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384.getByteValue(), message.getCipherSuites().getValue());
//        assertEquals("Compression Length", new Integer(1), message.getCompressionLength().getValue());
//        assertArrayEquals("Compression must be null", CompressionMethod.NULL.getArrayValue(), message.getCompressions()
//                .getValue());
//        assertTrue("Extension must be Heartbeat", message.containsExtension(ExtensionType.HEARTBEAT));
//
//        assertEquals("The pointer has to return the length of this message + starting position",
//                clientHelloWithHeartbeatBytes.length, endPointer);
//    }
}
