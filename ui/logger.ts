import log from 'loglevel';

// Set the default log level
log.setDefaultLevel(log.levels.INFO);

// Create a logger instance
const logger = log.getLogger('ui');

// Set the log level for the logger instance
logger.setLevel(log.levels.INFO); // You can adjust this to WARN, ERROR, etc.

// Save the original methodFactory
const originalFactory = log.methodFactory;

// Customize the methodFactory to include a timestamp and log level
log.methodFactory = function (methodName, logLevel, loggerName) {
  const rawMethod = originalFactory(methodName, logLevel, loggerName);
  return function (...messages: any[]) {
    const timestamp = new Date().toISOString(); // Get the current date and time in ISO format
    rawMethod(`[${timestamp}] [${methodName.toUpperCase()}]`, ...messages);
  };
};

// Apply the custom methodFactory
log.setLevel(log.getLevel());

export default logger;