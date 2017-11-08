package com.reynke.sloud.databaseutilities.logging;

import java.util.logging.Logger;

/**
 * Indicates that this class holds a logger (that may be injected).
 *
 * @author Nicklas Reincke (contact@reynke.com)
 */
public interface ILoggerAware {

    /**
     * @return A logger injected into this class.
     */
    Logger getLogger();
}
