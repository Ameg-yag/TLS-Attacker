/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.preparator;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.protocol.message.UnknownHandshakeMessage;
import de.rub.nds.tlsattacker.core.workflow.TlsContext;

/**
 *
 * @author Robert Merget - robert.merget@rub.de
 */
public class UnknownHandshakeMessagePreparator extends HandshakeMessagePreparator<UnknownHandshakeMessage> {

    private final UnknownHandshakeMessage msg;

    public UnknownHandshakeMessagePreparator(TlsContext context, UnknownHandshakeMessage message) {
        super(context, message);
        this.msg = message;
    }

    @Override
    public void prepareHandshakeMessageContents() {
        LOGGER.debug("Preparing UnknownHandshakeMessage");
        prepareData(msg);
    }

    private void prepareData(UnknownHandshakeMessage msg) {
        msg.setData(msg.getDataConfig());
        LOGGER.debug("Data: " + ArrayConverter.bytesToHexString(msg.getData().getValue()));
    }

}